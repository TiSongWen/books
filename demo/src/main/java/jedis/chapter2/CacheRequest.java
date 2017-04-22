package jedis.chapter2;


import redis.clients.jedis.Jedis;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by tisong on 4/22/17.
 */
public class CacheRequest {

    public void testCacheRequest(Jedis conn) {
        System.out.println("\n----- 网页缓存 -----");

        String token = UUID.randomUUID().toString();

        updateToken(conn, token, "username", "itemX");

        String url = "http://test.com/?item=itemX";

        Callback callback = new Callback() {
            // 把Callback 看成动态生成页面内容的功能
            @Override
            public String call(String request) {
                return "content for " + request;
            }
        };
        String result = cacheRequest(conn, url, callback);
    }

    public String cacheRequest(Jedis conn, String request, Callback callback) {
        if (!canCache(conn, request)) {
            return callback != null ? (String) callback.call(request) : null;
        }

        /**
         * key - value
         * cache:request.hashCode - request content
         */
        String pageKey = "cache:" + hashRequest(request);
        String content = conn.get(pageKey);

        if (content == null && callback != null) {
            // 页面未被缓存, 那么生成页面, 并将页面设置保存300ms
            content = callback.call(request);
            conn.setex(pageKey, 300, content);
        }

        return content;
    }

    public String hashRequest(String request) {
        return String.valueOf(request.hashCode());
    }

    /**
     * 判断一个请求是否可以被缓存. 那么: 哪些请求是可以被缓存的呢?
     */
    private boolean canCache(Jedis conn, String request) {
        try {
            URL url = new URL(request);
            Map<String, String> params = new HashMap<>();
            if (url.getQuery() != null) {
                for (String param : url.getQuery().split("&")) {
                    String[] pair = param.split("=", 2);
                    params.put(pair[0], pair.length == 2 ? pair[1] : null);
                }
            }

            // 判断页面是否可以被缓存, 以及这个页面是否是商品页面
            String itemId = extractItemId(params);
            if (itemId == null || isDynamic(params)) {
                return false;
            }

            // 获取商品的浏览次数排名
            Long rank = conn.zrank("viewed:", itemId);
            // 根据商品的浏览次数排名来判断是否需要缓存这个页面
            // TODO 为什么是小于1000
            return rank != null && rank < 1000;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private boolean isDynamic(Map<String,String> params) {
        return params.containsKey("_");
    }

    public String extractItemId(Map<String,String> params) {
        return params.get("item");
    }

    public void updateToken(Jedis conn, String token, String user, String item) {
        long timestamp = System.currentTimeMillis() / 1000;

        conn.hset("login:", token, user);
        conn.zadd("recent:", timestamp, token);
        if (item != null) {
            conn.zadd("viewed:" + token, timestamp, item);
            // TODO 为什么这里能保证: 只保留用户最近浏览过的25个商品
            conn.zremrangeByRank("viewed:" + token, 0, -26);
            conn.zincrby("viewed:" + token, -1, item);
        }
    }


    interface Callback {
        // TODO 和 javax.*** Callback 接口的区别(或者说javax Callback的意义在哪里)
        String call(String request);
    }

    public static void main(String[] args) {
        Jedis conn = new Jedis();
        conn.select(15);
        new CacheRequest().testCacheRequest(conn);
    }
}
