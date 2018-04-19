package com.zht.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zht.service.LogService;

@Controller
public class IndexCotroller {

	@Autowired
	LogService logService;
	
	@RequestMapping("index")
	public String index(String string) {
		logService.log(string);
		return "";
	}
}
