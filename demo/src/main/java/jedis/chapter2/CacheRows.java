package jedis.chapter2;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * 数据行缓存
 */
public class CacheRows {



    public void testCacheRows(Jedis conn) throws InterruptedException {

        scheduleRowCache(conn, "itemX", 5);

        Set<Tuple> tuples = conn.zrangeByScoreWithScores("schedule:", 0, -1);
        for (Tuple tuple : tuples) {
            System.out.println("  " + tuple.getElement() + ", " + tuple.getScore());
        }

        CacheRowsThread thread = new CacheRowsThread();
        thread.start();

        Thread.sleep(1000);
        String r = conn.get("inv:itemX");
        System.out.println(r);
    }

    public void scheduleRowCache(Jedis conn, String rowId, int delay) {
        conn.zadd("delay:", delay, rowId); // 数据行的延迟值
        conn.zadd("schedule:", System.currentTimeMillis() / 1000, rowId);
    }

    class CacheRowsThread extends Thread {
        private Jedis conn;
        private boolean quit;

       public CacheRowsThread() {
           this.conn = new Jedis("localhost");
           this.conn.select(15);
       }

       public void quit() {
           this.quit = true;
       }

       @Override
       public void run() {
           Gson gson = new Gson();
           while(!quit) {
               Set<Tuple> range = conn.zrangeByScoreWithScores("schedule:", 0, 0);
               Tuple next = range.size() > 0 ? range.iterator().next() : null;
               long now = System.currentTimeMillis() / 1000;
               if (next == null || next.getScore() > now) {
                   try {
                       sleep(50);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   continue;
               }

               String rowId = next.getElement();
               double delay = conn.zscore("delay:", rowId);
               if (delay <= 0) {
                   conn.zrem("delay:", rowId);
                   conn.zrem("schedule:", rowId);
                   conn.del("inv:", rowId);
                   continue;
               }

               Inventory row = Inventory.get(rowId);
               conn.zadd("schedule:", now + delay, rowId);
               conn.set("inv:" + rowId, gson.toJson(row));
           }

       }
    }

    static class Inventory {
        private String id;
        private String data;
        private long   time;

        private Inventory (String id) {
            this.id = id;
            this.data = "data to cache...";
            this.time = System.currentTimeMillis() / 1000;
        }

        public static Inventory get(String id) {
            return new Inventory(id);
        }
    }
}
