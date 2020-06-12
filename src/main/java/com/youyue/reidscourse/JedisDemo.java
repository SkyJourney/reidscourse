//package com.youyue.reidscourse;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
//public class JedisDemo {
//
//    public static void main(String[] args) {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(10);
//        jedisPoolConfig.setMaxTotal(300);
//        jedisPoolConfig.setMaxWaitMillis(10000);
//        JedisPool jedisPool = new JedisPool(jedisPoolConfig,
//                "192.168.1.150",
//                6379);
//        Jedis jedis = jedisPool.getResource();
//        jedis.auth("123456");
//        jedis.set("c", "3");
//    }
//
//}
