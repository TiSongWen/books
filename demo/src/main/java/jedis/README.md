# Jedis 笔记

## 相关操作

Redis一共提供了5种结构
1. STRING
2. SET
3. LIST
4. HASH
5. ZSET(有序集合)

### 字符串命令 (key - value)

1. SET key value
2. GET key
3. DEL key

### 列表操作 (key - value1, value2, value3)

1. RPUSH key value : 推入列表的右端
2. LRANGEE 0 -1    : 获取某个范围的值
3. LINEDX 0        : 获取给定位置上的元素
4. LPOP            : 最左侧的弹出


### 集合 (key - value1, value2, value3)  (S开头)

> (不同与列表,集合中的元素不可重复)

1. SADD key value
2. SMEMBERS key
3. SISMEMBER key value : 判断该key valuee是否存在集合中
4. SREM  key value : 删除


> 可以看到: 集合是可以根据value单独删除集合中的某个元素,但是列表是只能根据 index 进行删除

### 散列 (key-name - key : value, key2 : value2, key3 : value3 )

1. HSET key-name key value
2. HGETALL key-name
3. HDEL key-name key
4. HGET key-name key

### 有序集合 (Z开头)

1. ZADD key score value
2. ZRANGE key 0 -1 [withscores] (一并返回分值)
3. ZRANGEBYSOCRE key 0 800 [withscores] 
4. ZREM key value
