package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.jt.vo.FileVo;

@Service
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {
	@Value("${image.dirPath}")
	private String localPath;
	@Value("${image.urlPath}")
	private String urlPath;
	
	@Override
	public FileVo upload(MultipartFile uploadFile) {
		FileVo fileVo = new FileVo();
		//获取文件名
		String fileName = uploadFile.getOriginalFilename();
		//将文件名全部转化为小写
		fileName = fileName.toLowerCase();
		//利用正则表达式判断
		if(!fileName.matches("^.+\\.(png|jpg|gif)$")) {
			fileVo.setError(1);
			return fileVo;
		}
		//判断是否为恶意程序
		try {
			BufferedImage image = ImageIO.read(uploadFile.getInputStream());
			//获取宽度和高度
			int width = image.getWidth();
			int height = image.getHeight();
			//判断属性是否为0
			if(width==0||height==0) {
				fileVo.setError(1);
				return fileVo;
			}
			//根据时间生成文件夹
			String dateDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			String localDir = localPath +dateDir;
			File dirFile = new File(localDir);
			if(!dirFile.exists()) {
				dirFile.mkdirs();
			}
			//防止文件名称重复
			String uuidName = UUID.randomUUID().toString().replace("-","");
			//获取文件类型
			String fileType = fileName.substring(fileName.lastIndexOf("."));
			String realName = uuidName +fileType;
			//实现文件上传
			File realFile = new File(localDir+"/"+realName);
			uploadFile.transferTo(realFile);
			fileVo.setHeight(height);
			fileVo.setWidth(width);
			String realUrlPath = urlPath + dateDir +"/"+ realName;
			
			fileVo.setUrl(realUrlPath);
		} catch (Exception e) {
			e.printStackTrace();
			fileVo.setError(1);
			return fileVo;
		}
		return fileVo;
	}
	
}










