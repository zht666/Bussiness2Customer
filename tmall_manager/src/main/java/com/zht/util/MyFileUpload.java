package com.zht.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class MyFileUpload {

	public static List<String> upload_image(MultipartFile[] files) {
		//获取保存路径
		String path = MyPropertyUtil.getProperty("myUpload.properties","window_path");
		
		List<String> list_image = new ArrayList<String>();
		//遍历每一个文件
		for (int i = 0; i < files.length; i++) {
			//判断文件是否为空
			if(!files[i].isEmpty()) {
				//获取文件的原始文件名
				String originalFilename = files[i].getOriginalFilename();
				//获得毫秒时间戳，目的保证每次存储的图片名称不同
//				UUID randomUUID = UUID.randomUUID();
				String name = System.currentTimeMillis()+originalFilename;
				//拼接路径和文件名
				String upload_name = path + "/" + name;
				try {
					//将文件上传，把内存中图片写入磁盘
					files[i].transferTo(new File(upload_name));
					//每次上传成功之后，把文件名放到list中
					list_image.add(name);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list_image;
	}

}
