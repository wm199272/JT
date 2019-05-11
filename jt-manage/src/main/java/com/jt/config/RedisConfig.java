package com.jt.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
//	@Value("${redis.shards}")
//	private String redisShards;
	/*
	 * @Bean public ShardedJedis shardedJedis() { List<JedisShardInfo> shards = new
	 * ArrayList<JedisShardInfo>(); String[] nodes = redisShards.split(","); for
	 * (String node : nodes) { String[] host_port = node.split(":"); JedisShardInfo
	 * info = new JedisShardInfo(host_port[0],host_port[1]); shards.add(info); }
	 * return new ShardedJedis(shards); }
	 */
	/*
	 * @Value("${redis.nodes}") private String nodes;
	 * 
	 * @Value("${redis.masterName}") private String masterName;
	 * 
	 * //获取的是哨兵的链接池
	 * 
	 * @Bean public JedisSentinelPool jedisSentinelPool() { Set<String> sentinels =
	 * new HashSet<>(); sentinels.add(nodes); return new
	 * JedisSentinelPool(masterName, sentinels); }
	 */
	
	
	//实现redis集群引入
		@Value("${redis.nodes}")
		private String nodes; //ip:port,iP:port....
		
		@Bean
		public JedisCluster jedisCluster() {
			Set<HostAndPort> nodesSet = new HashSet<>();
			String[] node = nodes.split(",");
			for (String h_pNode : node) {
				//ip:port
				String[] args = h_pNode.split(":");
				int port = Integer.parseInt(args[1]);
				HostAndPort hostAndPort 
				= new HostAndPort(args[0], port);
				nodesSet.add(hostAndPort);
			}
			return new JedisCluster(nodesSet);
		}
}
