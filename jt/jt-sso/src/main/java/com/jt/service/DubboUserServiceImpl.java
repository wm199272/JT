package com.jt.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;
@Service
public class DubboUserServiceImpl implements DubboUserService {
@Autowired
private UserMapper userMapper;
@Autowired
private JedisCluster jedisCluster;
	@Override
	@Transactional
	public void saveUser(User user) {
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		user.setEmail(user.getPhone());
		user.setCreated(new Date());
		user.setUpdated(user.getUpdated());
		userMapper.insert(user);

	}

	@Override
	public String findUserByUP(User user) {
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", user.getUsername()).eq("password",md5Pass);
		User userDB = userMapper.selectOne(queryWrapper);
		if (userDB==null) {
			return null;
		}
		String token = "JT_TICKET"+System.currentTimeMillis()+user.getUsername();
		token = DigestUtils.md5DigestAsHex(token.getBytes());
		userDB.setPassword("fake password");
		String userJSON = ObjectMapperUtil.toJSON(userDB);
		jedisCluster.setex(token, 60*60*24*7, userJSON);
		return token;
	}
}
