package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUIList;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private ItemCatMapper itemCatMapper;
//	@Autowired
//	private ShardedJedis jedis;
	
//	@Autowired(required = false)
//	private RedisService jedis;
	
	@Autowired
	private JedisCluster jedis;
	@Override
	public String findItemCatNameById(Long itemCatId) {
		ItemCat itemCat = itemCatMapper.selectById(itemCatId);
		return itemCat.getName();
	}

	@Override
	public List<EasyUITree> fingItemCatList(Long parentId) {
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);
		List<ItemCat> itemCatList = itemCatMapper.selectList(queryWrapper);
		ArrayList<EasyUITree> treeList = new ArrayList<>();
		for (ItemCat itemCat : itemCatList) {
			EasyUITree uiTree = new EasyUITree();
			uiTree.setId(itemCat.getId()).setText(itemCat.getName());	
			String state = itemCat.getIsParent()?"closed":"open";
			uiTree.setState(state);
			treeList.add(uiTree);
		}
		return treeList;
	}

	@Override
	public List<EasyUITree> fingItemCatCacheList(Long parentId) {
		List<EasyUITree> treeList = new ArrayList<>();
		String key = "ITEM_CAT_"+parentId;
		String result = jedis.get(key);
		if(StringUtils.isEmpty(result)) {
			treeList = fingItemCatList(parentId);
			String json = ObjectMapperUtil.toJSON(treeList);
			jedis.set(key,json);
			System.out.println("查询到数据库");
		}else {
			ObjectMapperUtil.toObject(result, treeList.getClass());
			System.out.println("查询到缓存");
		}
		return treeList;
	}









}
