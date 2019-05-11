package com.jt.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class TestRedisSentinel {
	@Test
	public void test01() {
		HostAndPort msg = new HostAndPort("192.168.106.131", 26379);
		System.out.println("工具api得结果"+msg);
		Set<String>sentinels = new HashSet<>();
		sentinels.add("192.168.106.131:26379");
		JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels);
		Jedis jedis = pool.getResource();
		jedis.set("1812", "哨兵测试成功");
		System.out.println(jedis.get("1812"));
		jedis.close();
	}
}
