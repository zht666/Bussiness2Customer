package com.zht.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 
* @ClassName: IndexCotroller
* @Description: TODO 首页控制层
* @author zht
*
 */
@Controller
public class IndexCotroller {

	//去主页面
	@RequestMapping(value = { "", "/", "/index", "/index/"})
	public String getIndex(String url, String title, ModelMap map) {
		map.put("url", url);
		map.put("title", title);
		return "main";
	}
	
	@RequestMapping("/goto_spu")
	public String goto_spu() {
		return "spu";
	}
	
	@RequestMapping("/goto_attr")
	public String goto_attr() {
		return "attr";
	}
	
	@RequestMapping("/goto_sku")
	public String goto_sku() {
		return "sku";
	}
}
