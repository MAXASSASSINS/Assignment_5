package org.example;

import redis.clients.jedis.UnifiedJedis;

public class MyRedis {
    public static void main(String[] args) {
        UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");
        jedis.set("name", "shadab");
        System.out.println(jedis.get("name"));
        jedis.close();
    }
}
