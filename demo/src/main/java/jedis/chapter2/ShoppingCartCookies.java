package jedis.chapter2;

import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.UUID;

/**
 * Created by tisong on 4/22/17.
 */
public class ShoppingCartCookies {

    public void testShoppintCartCookies(Jedis conn) {
        System.out.println("\n----- testShopppingCartCookies -----");

        String token = UUID.randomUUID().toString();

        updateToken(conn, token, "username", "itemX");

        addToCart(conn, token, "itemY", 3);

        Map<String, String> items = getCartItems(conn, token);
        for (String item: items.keySet()) {
            System.out.println(" " + item + " : " + items.get(item));
        }


        System.out.println("Let's clean out our sessions and carts");
    }

    /**
     * cart:session
     *      item1 - count1
     *      item2 - count2
     */
    private void addToCart(Jedis conn, String session, String item, int count) {
        if (count <= 0) {
            conn.hdel("cart:" + session, item);
        } else {
            conn.hset("cart:" + session, item, String.valueOf(count));
        }
    }

    private Map<String, String> getCartItems(Jedis conn, String session) {
        return conn.hgetAll("cart:" + session);
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
}
