package jedis.chapter2;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Cookies :
 *      1. 签名cookie
 *      2. 令牌cookie
 * Redis 功能
 *      1. 保存cookie ()
 * login:token - user name
 */
public class LoginCookies {


    public void testLoginCookies(Jedis conn) throws InterruptedException {

        String token = UUID.randomUUID().toString();

        updateToken(conn, token, "username", "itemX");

        String userName = checkToToken(conn, token);

        System.out.println("userName: " + userName + "\n");
        assert userName != null;

        CleanSessions cleanSessionsRunnable = new CleanSessions(0);
        Thread cleanSessionsThread = new Thread(cleanSessionsRunnable);

        cleanSessionsThread.start();
        Thread.sleep(1000); // TODO Thread sleep 与 Object Sleep 有什么区别
        cleanSessionsRunnable.quit();
        Thread.sleep(2000);

        if (cleanSessionsThread.isAlive()) {
            throw new RuntimeException("The clean sessions thread is still alive?!?");
        }

        long s = conn.hlen("login:"); // 散列
        System.out.println("The current number of sessions still available is: " + s);
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


    public String checkToToken(Jedis conn, String token) {
        return conn.hget("login:", token);
    }


    /**
     * 散列: key-name : key1 - value1, key2 - value2
     *      login: token1 - userName1, token2 - userName2 (用户登录信息)
     * 有序集合 key : value1 (score1), value2(score2), value3(score3)
     *      recent: token1 - 登录时间; token2 - 登录时间;  token3 - 登录时间 (不可重复)
     *      viewed:token  item1 - 浏览时间, item2 - 浏览时间
     */
    public class CleanSessions implements Runnable {
        private Jedis conn;

        private int limit;

        private boolean quit;

        public CleanSessions(int limit) {
            this.conn = new Jedis("localhost");
            this.conn.select(15); // TODO 15是什么意思
            this.limit = limit;
        }

        public void quit() {
            quit = true;
        }

        @Override
        public void run() {
            while(!quit) {
                long size = conn.zcard("recent:"); // 有序集合: recent: value1, value2
                if (size <= limit) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                long endIndex = Math.min(size - limit, 100); // 不可超过100
                Set<String> tokenSet = conn.zrange("recent:", 0, endIndex - 1);
                String[] tokens = tokenSet.toArray(new String[tokenSet.size()]);

                List<String> sessionKeys = new ArrayList<>();
                for (String token : tokens) {
                    sessionKeys.add("viewed:" + token);
                }

                conn.del(sessionKeys.toArray(new String[sessionKeys.size()]));
                conn.hdel("login:", tokens);
                conn.zrem("recent:", tokens);
            }
        }
    }


}
