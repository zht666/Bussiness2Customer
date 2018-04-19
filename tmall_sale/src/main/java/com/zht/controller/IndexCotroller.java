package com.zht.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zht.bean.OBJECT_T_MALL_ATTR;
import com.zht.bean.OBJECT_T_MALL_SKU;
import com.zht.service.AttrService;
import com.zht.service.ListService;
/**
 * 
* @ClassName: IndexCotroller
* @Description: TODO 首页控制层
* @author zht
*
 */
@Controller
public class IndexCotroller {
	
	@Autowired
	AttrService attrService;
	
	@Autowired
	ListService listService;

	//去主页面
	@RequestMapping(value = { "", "/", "/index", "/index/"})
	public String getIndex(HttpServletRequest request, ModelMap map) {

//		String yh_mch = "";
//		Cookie[] cookies = request.getCookies();
//		if(cookies != null && cookies.length > 0) {
//			for (int i = 0; i < cookies.length; i++) {
//				String name = cookies[i].getName();
//				if("yh_mch".equals(name)) {
//					yh_mch = cookies[i].getValue();
//				}
//			}
//		}
//		map.put("yh_mch", yh_mch);
		return "index";
	}
	
	//普通登录
	@RequestMapping("goto_login")
	public String goto_login() {
		return "login";
	}
	
	//结算登录
	@RequestMapping("goto_login_checkOrder")
	public String goto_login_checkOrder() {
		return "loginOrder";
	}
	
	@RequestMapping("goto_logout")
	public String goto_logout(HttpSession session) {
		session.invalidate();
		return "redirect:/goto_login.do";
	}
	
	@RequestMapping("orderErr")
	public String orderErr(HttpSession session) {
		return "orderErr";
	}
	
//	@RequestMapping("goto_list")
//	public String goto_list(int flbh2, ModelMap map) {
//		
//		//flbh2属性的集合
//		List<OBJECT_T_MALL_ATTR> list_attr = attrService.get_attr_list(flbh2);
//		
//		//flbh2商品列表
//		List<OBJECT_T_MALL_SKU> list_sku = listService.get_list_by_flbh2(flbh2);
//		map.put("list_attr", list_attr);
//		map.put("list_sku", list_sku);
//		map.put("flbh2", flbh2);
//		return "list";
//	}

}
