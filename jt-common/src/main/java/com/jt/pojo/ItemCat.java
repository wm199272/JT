package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
@Data
@TableName("tb_item_cat")
@Accessors(chain=true)
public class ItemCat extends BasePojo{
	private Long id;
	private Long parentId;
	private String name;
	private Integer status;
	private Integer sortOrder;
	private Boolean isParent;
	public ItemCat() {
	}
	
	
	
}
