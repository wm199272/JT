package com.jt.vo;
import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
public class FileVo {
	private Integer error=0;
	private String url;
	private Integer width;
	private Integer height;
	public FileVo(Integer error, String url, Integer width, Integer height) {
		super();
		this.error = error;
		this.url = url;
		this.width = width;
		this.height = height;
	}
	public FileVo() {

	}
}
