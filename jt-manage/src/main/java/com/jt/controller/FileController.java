package com.jt.controller;
import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.FileVo;

@Controller
public class FileController {
	@Autowired
	private FileService fileService;
	@RequestMapping("/file")
	public String fileImage(MultipartFile fileImage) throws IllegalStateException, IOException {
		//获取文件的名称
		String fileName = fileImage.getOriginalFilename();
		//指定文件路径
		String filePath="D:/workspace/jt-upload";
		File dirFile = new File(filePath);
		if(!dirFile.exists()) {
			dirFile.mkdirs();
		}
		//实现文件上传
		String realName = filePath +"/"+ fileName;
		fileImage.transferTo(new File(realName));
		return "redirect:/file.jsp";
	}
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public FileVo uploadFile(MultipartFile uploadFile) {
		return fileService.upload(uploadFile);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
