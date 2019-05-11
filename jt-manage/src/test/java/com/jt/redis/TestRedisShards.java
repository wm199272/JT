package com.jt.redis;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

public class TestRedisShards {
	
	@Test
	public void test01() {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo("192.168.106.131",6379));
		shards.add(new JedisShardInfo("192.168.106.131",6380));
		shards.add(new JedisShardInfo("192.168.106.131",6381));
		ShardedJedis jedis = new ShardedJedis(shards);
		jedis.set("1812", "分片机制成功");
		System.out.println(jedis.get("1812"));
	}
	
	@Autowired
	private ShardedJedis jedis;
	@Test
	public void test02() {
		jedis.set("1812", "spring整合成功!!!!!");
		System.out.println(jedis.get("1812"));
	}
}
