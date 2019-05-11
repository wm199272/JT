package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUIList;

public interface ItemService {

	EasyUIList findItemByPage(Integer page, Integer rows);

	void saveItem(Item item, ItemDesc itemDesc);

	void updateStatus(Long[] ids,Integer status);

	void updateItem(Item item, ItemDesc itemDesc);

	void deleteItem(Long[] ids);

	ItemDesc findItemDescById(Long itemId);

	Item findItemById(Long itemId);
	
}
