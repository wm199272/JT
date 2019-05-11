package com.jt.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class SysResult {
	private Object data;
	private String msg;
	private Integer status;		//200成功	201失败
	public SysResult(Integer status,String msg, Object data ) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}
	public SysResult() {

	}
	public static SysResult ok() {
		return new SysResult(200,null,null);
	}
	public static SysResult ok(Object data) {
		return new SysResult(200,null,data);
	}
	public static SysResult ok(String msg,Object data) {
		return new SysResult(200,msg,data);
	}
	public static SysResult fail() {
		return new SysResult(201,null,null);
	}
	public static SysResult fail(String msg) {
		return new SysResult(201,msg,null);
	}
	public static SysResult fail(Object data) {
		return new SysResult(201,null,data);
	}
	public static SysResult fail(String msg,Object data) {
		return new SysResult(201,msg,data);
	}
	
}
