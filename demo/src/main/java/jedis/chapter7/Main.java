package jedis.chapter7;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Transaction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tisong on 5/6/17.
 */
public class Main {

    public static void main(String[] args) {
        new Main().run();
    }


    public void run() {
        Jedis conn = new Jedis("localhost");

        conn.select(15);

        conn.flushDB();

        testIndexDocument(conn);

        testSetOperations(conn);

        testParseQuery(conn);

        testParseAndSearch(conn);

        testSearchWithSort(conn);
    }


    public void testIndexDocument(Jedis conn) {
        System.out.println("\n----- testIndexDocument -----");

        Set<String> tokens = tokenize(CONTENT);
        System.out.println("Those tokens are: " +
                Arrays.toString(tokens.toArray()));
        assert tokens.size() > 0;

        System.out.println("And now we are indexing that content...");

        int count = indexDocument(conn, "test", CONTENT);
        assert count == tokens.size();

        Set<String> test = new HashSet<>();
        test.add("test");
        for (String t : tokens) {
            assert test.equals(conn.smembers("idx:" + t));
        }

        System.out.println("\n");
    }


    public void testSetOperations(Jedis conn) {
        System.out.println("\n----- testSetOperations -----");

        indexDocument(conn, "test", CONTENT);

        Set<String> test = new HashSet<>();
        test.add("test");

        // 交集测试
        Transaction trans = conn.multi();
        String id = intersect(trans, 30, "content", "indexed");
        trans.exec();
        assert test.equals(conn.smembers("idx:" + id));

        // 并集测试
        trans = conn.multi();
        id = union(trans, 30, "content", "ignored");
        trans.exec();
        assert test.equals(conn.smembers("idx:" + id));

        // 差集测试
        trans = conn.multi();
        id = difference(trans, 30, "content", "indexed");
        trans.exec();
        assert conn.smembers("idx:" + id).isEmpty();

        System.out.println("\n");
    }


    public void testParseQuery(Jedis conn) {
        System.out.println("\n----- testParseQuery -----");

        String queryString = "test query without stopwords";
        Query query = parse(queryString);

        String[] words = queryString.split(" ");
        for (int i = 0; i < words.length; i++) {
            List<String> word = new ArrayList<>();
            word.add(words[i]);
            assert word.equals(query.all.get(i));
        }
        assert query.unwanted.isEmpty();


        queryString = "test +query without -stopwords";
        query = parse(queryString);
        assert "test".equals(query.all.get(0).get(0));
        assert "query".equals(query.all.get(0).get(1));
        assert "without".equals(query.all.get(1).get(0));
        assert "stopwords".equals(query.unwanted.toArray()[0]);

        System.out.println("\n");
    }

    public void testParseAndSearch(Jedis conn) {

        indexDocument(conn, "test", CONTENT);

        Set<String> test = new HashSet<>();
        test.add("test");

        String id = parseAndSearch(conn, "content", 30);

        id = parseAndSearch(conn, "content indexed random", 30);

        id = parseAndSearch(conn, "content +indexed random", 30);


    }

    public void testSearchWithSort(Jedis conn) {
        System.out.println("\n----- testSearchWithSort -----");
        System.out.println("And now let's test searching with sorting...");

        indexDocument(conn, "test", CONTENT);
        indexDocument(conn, "test2", CONTENT);

        HashMap<String,String> values = new HashMap<String,String>();
        values.put("updated", "12345");
        values.put("id", "10");
        conn.hmset("kb:doc:test", values);

        values.put("updated", "54321");
        values.put("id", "1");
        conn.hmset("kb:doc:test2", values);

        SearchResult result = searchAndSort(conn, "content", "-updated");
        assert "test2".equals(result.results.get(0));
        assert "test".equals(result.results.get(1));

        result = searchAndSort(conn, "content", "-id");
        assert "test".equals(result.results.get(0));
        assert "test2".equals(result.results.get(1));

        System.out.println("Which passed!");
    }


    private static final Pattern WORDS_RE = Pattern.compile("[a-z']{2,}");

    private static final Set<String> STOP_WORDS = new HashSet<>();
    static {
        for (String word :
                ("able about across after all almost also am among " +
                        "an and any are as at be because been but by can " +
                        "cannot could dear did do does either else ever " +
                        "every for from get got had has have he her hers " +
                        "him his how however if in into is it its just " +
                        "least let like likely may me might most must my " +
                        "neither no nor not of off often on only or other " +
                        "our own rather said say says she should since so " +
                        "some than that the their them then there these " +
                        "they this tis to too twas us wants was we were " +
                        "what when where which while who whom why will " +
                        "with would yet you your").split(" "))
        {
            STOP_WORDS.add(word);
        }
    }

    private static String CONTENT =
            "this is some random content, look at how it is indexed.";

    // ------------------- 1. 基本索引操作


    /**
     * Set集合
     * ind:wordA - 文档A, 文档B
     * ind:wordB - 文档A, 文档B
     */

    public Set<String> tokenize (String content) {
        Set<String> words = new HashSet<>();
        Matcher matcher = WORDS_RE.matcher(content);
        while (matcher.find()) {
            String word = matcher.group().trim();
            if (word.length() > 2 && !STOP_WORDS.contains(word)) {
                words.add(word);
            }
        }

        return words;
    }

    /**
     * idx:word1 - docIdA, docIdB
     *
     * @param conn
     * @param docId 文档ID
     * @param content
     * @return
     */
    public int indexDocument(Jedis conn, String docId, String content) {
        Set<String> words = tokenize(content);

        // 开启一个事务
        Transaction trans = conn.multi();

        for (String word : words) {
            trans.sadd("idx:" + word, docId);
        }

        return trans.exec().size();
    }


    // ---------------------- 2. 基本搜索操作


    /**
     * 求交集
     * @param items KEY KEY1...KEYN
     * @return DESTINATION_KEY
     */
    public String intersect(Transaction trans, int ttl, String... items) {
        // SINTERSTORE myset myset1 myset2 将集合的交集传到另一个集合中
        return setCommon(trans, "sinterstore", ttl, items);
    }

    /**
     * 求并集
     */
    public String union(Transaction trans, int ttl, String... items) {
        return setCommon(trans, "sunionstore", ttl, items);
    }

    /**
     * 求差集
     */
    public String difference(Transaction trans, int ttl, String...items) {
        return setCommon(trans, "sdiffstore", ttl, items);
    }

    private String setCommon(Transaction trans, String method, int ttl, String... items) {
        String[] keys = new String[items.length];
        for (int i = 0; i < items.length; i++){
            keys[i] = "idx:" + items[i];
        }

        String id = UUID.randomUUID().toString();
        try{
            trans.getClass()
                    .getMethod(method, String.class, String[].class)
                    .invoke(trans, "idx:" + id, keys);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        trans.expire("idx:" + id, ttl);
        return id;
    }


    // -------------------------- 3. 分析并执行搜索操作


    private static final Pattern QUERY_RE = Pattern.compile("[+-]?[a-z']{2,}");

    public class Query {
        public final List<List<String>> all = new ArrayList<>();
        public final Set<String> unwanted = new HashSet<>();
    }

    /**
     * 对查询字符串的解析
     * wordA wordB  : 一篇文章同时包含两个词
     * wordA +wordB : 同义词 (一篇文章包含A或者包含B)
     * wordA -wordB : 不包含 (一篇文章包含A但不包含B)
     */
    public Query parse(String queryString) {
        Query query = new Query();
        Set<String> current = new HashSet<>();

        Matcher matcher = QUERY_RE.matcher(queryString.toLowerCase());
        while (matcher.find()) {
            String word = matcher.group().trim();
            char prefix = word.charAt(0);
            if (prefix == '+' || prefix == '-') {
                word = word.substring(1);
            }

            if (word.length() < 2 || STOP_WORDS.contains(word)) {
                continue;
            }

            if (prefix == '-') {
                query.unwanted.add(word);
                continue;
            }

            current.add(word);
        }

        if (!current.isEmpty()) {
            query.all.add(new ArrayList<String>(current));
        }
        return query;
    }

    public String parseAndSearch(Jedis conn, String queryString, int ttl) {
        Query query = parse(queryString);

        if (query.all.isEmpty()) {
            return null;
        }

        List<String> toIntersect = new ArrayList<>();
        for (List<String> syn : query.all) {
            if (syn.size() > 1) {
                Transaction trans = conn.multi();
                // TODO 或者再准确说一点: 这里的交集结果返回什么? 似乎还是交集本身的字符串
                toIntersect.add(union(trans, ttl, syn.toArray(new String[syn.size()])));
                trans.exec();
            } else {
                toIntersect.add(syn.get(0));
            }
        }

        String intersectResult = null;
        if (toIntersect.size() > 1) {
            Transaction trans = conn.multi();
            intersectResult = intersect(trans, ttl, toIntersect.toArray(new String[toIntersect.size()]));
            trans.exec();
        } else {
            intersectResult = toIntersect.get(0);
        }

        if (!query.unwanted.isEmpty()) {
            String[] keys = query.unwanted.toArray(new String[query.unwanted.size()+1]);
            keys[query.unwanted.size()] = intersectResult;
            Transaction trans = conn.multi();
            difference(trans, ttl, keys);
            trans.exec();
        }

        return intersectResult;
    }

    public class SearchResult {
        public final String id;
        public final long total;
        public final List<String> results;

        public SearchResult(String id, long total, List<String> results) {
            this.id = id;
            this.total = total;
            this.results = results;
        }
    }

    /**
     *
     * @param conn
     * @param query
     * @param sort -字段 或者 字段
     * @return
     */
    public SearchResult searchAndSort(Jedis conn, String query, String sort) {
        boolean desc = sort.startsWith("-"); // 是否是降序
        if (desc) {
            sort = sort.substring(1);
        }

        // 当sort排序字段不是 updated, id, created这些数字字段的时候, alpha为true, 即意味着是字母排序
        boolean alpha = !"updated".equals(sort) && !"id".equals(sort) && !"created".equals(sort); // 是数值排序还是字母排序
        // 排序的具体字段
        String by = "kb:doc:*->" + sort;

        String id = parseAndSearch(conn, query, 100);

        Transaction trans = conn.multi();
        trans.scard("idx:" + id);

        SortingParams params = new SortingParams();
        if (desc) {
            params.desc();
        }
        if (alpha) {
            params.alpha();
        }

        params.by(by);
        params.limit(0, 20);

        trans.sort("idx:" + id, params);

        List<Object> results = trans.exec();

        return new SearchResult(id, ((Long)results.get(0)).longValue(),
                ((List<String>) results.get(1)));
    }
}
