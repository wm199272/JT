package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.anno.CaCheAnnotation;
import com.jt.anno.CaCheAnnotation.CACHE_TYPE;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;

@RestController
@RequestMapping("/item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/queryItemName")
	public String findItemCatNameById(Long itemCatId) {
		return itemCatService.findItemCatNameById(itemCatId);
	}
	
	@RequestMapping("/list")
	@CaCheAnnotation(index=0,cacheType=CACHE_TYPE.FIND)
	public List<EasyUITree> findItemCatList(@RequestParam(name="id",defaultValue ="0")Long parentId){
		//return itemCatService.fingItemCatCacheList(parentId);
		return itemCatService.fingItemCatList(parentId);
	}
}
