package com.zht.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zht.bean.MODEL_T_MALL_SKU_ATTR_VALUE;
import com.zht.bean.T_MALL_USER_ACCOUNT;

@Controller
public class LoginController {
	
	//去主页面
	@RequestMapping(value = { "", "/", "/index", "/index/"})
	public String getIndex(HttpServletRequest request, ModelMap map) {

		return "index";
	}

	//ajax异步提交参数的方式三：表单序列化
	@RequestMapping(value="login",produces="text/html; charset=UTF-8")
	@ResponseBody
	public String login(MODEL_T_MALL_SKU_ATTR_VALUE list_attr, T_MALL_USER_ACCOUNT user) {
//		return "success";
		return "成功";
	}
}
