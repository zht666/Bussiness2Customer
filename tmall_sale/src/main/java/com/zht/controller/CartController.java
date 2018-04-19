package com.zht.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zht.bean.T_MALL_SHOPPINGCAR;
import com.zht.bean.T_MALL_USER_ACCOUNT;
import com.zht.service.CartService;
import com.zht.util.MyJsonUtil;

@Controller
public class CartController {

	@Autowired
	CartService cartService;
	
	//设置shfxz，优化后
	@RequestMapping("change_shfxz")
	public String change_shfxz(HttpServletResponse response, T_MALL_SHOPPINGCAR cart,HttpSession session, @CookieValue(value="list_cart_cookie",required=false) String list_cart_cookie,ModelMap map) {
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();//创建购物车集合
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT)session.getAttribute("user");
		
		//购物车修改业务
		if(user == null) { //用户未登录
			//修改cookie，把cookie字符串转换为list
			list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
		}else { //用户已登录
			//修改db
			list_cart = (List<T_MALL_SHOPPINGCAR>)session.getAttribute("list_cart_session"); //数据库
		}
		
		for (int i = 0; i < list_cart.size(); i++) {
			//如果cookie中的sku_id 与 传过来的sku_id 相等
			if (list_cart.get(i).getSku_id() == cart.getSku_id()) {
				list_cart.get(i).setShfxz(cart.getShfxz());
				if(user == null) {
					//覆盖cookie
					Cookie cookie = new Cookie("list_cart_cookie", MyJsonUtil.list_to_json(list_cart));//把cookie购物车集合转换成json字符串放到cookie里
					cookie.setMaxAge(60 * 60 * 24); //设置cookie时间
					response.addCookie(cookie); //设置cookie
				}else {
					cartService.update_cart(list_cart.get(i));//修改数据库shfxz
					//同步session
					session.setAttribute("list_cart_session", list_cart); 
				}
			}
		}
		
		map.put("list_cart", list_cart);
		map.put("sum", get_sum(list_cart));
		return "cartListInner";
	}
	
	@RequestMapping("goto_cart_list")
	public String goto_cart_list(HttpSession session, @CookieValue(value="list_cart_cookie",required=false) String list_cart_cookie,ModelMap map) {
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();//创建购物车集合
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT)session.getAttribute("user");
		
		//通过cookie或者session获取购物车数据
		if(user == null) { //cookie
			list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
		}else { //session
			list_cart = (List<T_MALL_SHOPPINGCAR>)session.getAttribute("list_cart_session"); 
		}
		map.put("list_cart", list_cart);
		map.put("sum", get_sum(list_cart));
		return "cartList";
	}
	
	//计算购物车总金额
	private BigDecimal get_sum(List<T_MALL_SHOPPINGCAR> list_cart) {
		BigDecimal sum = new BigDecimal("0"); //初始化
		for (int i = 0; i < list_cart.size(); i++) {
			if ("1".equals(list_cart.get(i).getShfxz())) { //如果已选中
				sum = sum.add(new BigDecimal(list_cart.get(i).getHj()+""));//计算购物车总金额
			}
		}
		return sum;
	}

	@RequestMapping("miniCart")
	public String miniCart(HttpSession session, @CookieValue(value="list_cart_cookie",required=false) String list_cart_cookie,ModelMap map) {
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();//创建购物车集合
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT)session.getAttribute("user");
		
		//通过cookie或者session获取购物车数据
		if(user == null) { //cookie
			list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
		}else { //session
			list_cart = (List<T_MALL_SHOPPINGCAR>)session.getAttribute("list_cart_session"); 
		}
		map.put("list_cart", list_cart);
		return "miniCartList";
	}
	
	//购物车：优化后
	//@CookieValue 可让处理方法入参绑定某个 Cookie 值
	@RequestMapping("add_cart") //为了防止空指针异常：required=false
	public String add_cart(HttpSession session, HttpServletResponse response, @CookieValue(value="list_cart_cookie",required=false) String list_cart_cookie, T_MALL_SHOPPINGCAR cart) {
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();//创建购物车集合
		int yh_id = cart.getYh_id();
		
		//添加购物车操作
		if(yh_id == 0) {
			//用户未登录，操作cookie
			if(StringUtils.isBlank(list_cart_cookie)) { //判断是否为空，如果cookie为空
				list_cart.add(cart); //把购物车添加到集合
			}else { //cookie不为空
				//把list_cart_cookie字符串转换成java对象
				list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
				//根据sku_id 判断购物车是否重复
				boolean b = if_new_cart(list_cart, cart);//如果是新购物车
				if(b) {
					// 新车，添加
					list_cart.add(cart);
				}else {
					// 老车，更新
					//同步session
					for (int i = 0; i < list_cart.size(); i++) {//遍历购物车集合
						if (list_cart.get(i).getSku_id() == cart.getSku_id()) { //如果重复
							list_cart.get(i).setTjshl(list_cart.get(i).getTjshl() + cart.getTjshl()); //更新添加数量
							list_cart.get(i).setHj(list_cart.get(i).getTjshl() * list_cart.get(i).getSku_jg()); //更新合计价格
						}
					}
				}
			}
			//覆盖cookie
			Cookie cookie = new Cookie("list_cart_cookie", MyJsonUtil.list_to_json(list_cart));//把cookie购物车集合转换成json字符串放到cookie里
			cookie.setMaxAge(60 * 60 * 24); //设置cookie时间
			response.addCookie(cookie);
		}else {
			list_cart = (List<T_MALL_SHOPPINGCAR>)session.getAttribute("list_cart_session"); //数据库
			//用户已登录，操作db
			boolean b = cartService.if_cart_exists(cart); //判断db中是否有该购物车，db为空或没有该商品返回false
			if(!b) { //如果数据库中没有该商品就添加
				cartService.add_cart(cart); 
				if(list_cart == null || list_cart.isEmpty()) {
					//同步session
					list_cart = new ArrayList<T_MALL_SHOPPINGCAR>(); //防止空指针异常
//					list_cart.add(cart);
//					session.setAttribute("list_cart_session", list_cart);
				} //else {
					list_cart.add(cart);
				//}
			}else { //如果数据库有该商品就更新
				//同步session
				for (int i = 0; i < list_cart.size(); i++) {//遍历购物车集合
					if (list_cart.get(i).getSku_id() == cart.getSku_id()) { //如果重复
						list_cart.get(i).setTjshl(list_cart.get(i).getTjshl() + cart.getTjshl()); //更新添加数量
						list_cart.get(i).setHj(list_cart.get(i).getTjshl() * list_cart.get(i).getSku_jg()); //更新合计价格
						// 老车，更新
						cartService.update_cart(list_cart.get(i));
					}
				}
			}
			session.setAttribute("list_cart_session", list_cart);

		}
		return "redirect:/goto_cart_list.do";
	}
	
	private boolean if_new_cart(List<T_MALL_SHOPPINGCAR> list_cart, T_MALL_SHOPPINGCAR cart) {
		boolean b = true;
		for (int i = 0; i < list_cart.size(); i++) {
			if (list_cart.get(i).getSku_id() == cart.getSku_id()) {
				b = false;
			}
		}
		return b;
	}

	@RequestMapping("cart_success")
	public String cart_success() {
		return "cart_success";
	}
	
	
	
	//购物车：优化前
	//@CookieValue 可让处理方法入参绑定某个 Cookie 值
//	@RequestMapping("add_cart") //为了防止空指针异常：required=false
//	public String add_cart(HttpSession session, HttpServletResponse response, @CookieValue(value="list_cart_cookie",required=false) String list_cart_cookie, T_MALL_SHOPPINGCAR cart) {
//		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();//创建购物车集合
//		int yh_id = cart.getYh_id();
//		
//		//添加购物车操作
//		if(yh_id == 0) {
//			//用户未登录，操作cookie
//			if(StringUtils.isBlank(list_cart_cookie)) { //判断是否为空，如果cookie为空
//				list_cart.add(cart); //把购物车添加到集合
//			}else { //cookie不为空
//				//把list_cart_cookie字符串转换成java对象
//				list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
//				//根据sku_id 判断购物车是否重复
//				boolean b = if_new_cart(list_cart, cart);//如果是新购物车
//				if(b) {
//					// 新车，添加
//				}else {
//					// 老车，更新
//				}
//			}
//		}else {
//			list_cart = (List<T_MALL_SHOPPINGCAR>)session.getAttribute("list_cart_session"); //数据库
//			//用户已登录，操作db
//			if(list_cart == null || list_cart.isEmpty()) {
//				//db中没有数据，添加购物车到数据库
//				cartService.add_cart(cart);
//				
//				//同步session
//				list_cart = new ArrayList<T_MALL_SHOPPINGCAR>(); //防止空指针异常
//				list_cart.add(cart);
//				session.setAttribute("list_cart_session", list_cart);
//			}else { //db中有数据
//				//根据sku_id 判断购物车是否重复
//				boolean b = if_new_cart(list_cart, cart);
//				if(b) {
//					// 新车，添加
//					cartService.add_cart(cart);
//					//同步session
//					list_cart.add(cart); //如果list_cart不为空，说明cart里的数据已经指向了session中的一个地址
//				}else {
//					//同步session
//					for (int i = 0; i < list_cart.size(); i++) {//遍历购物车集合
//						if (list_cart.get(i).getSku_id() == cart.getSku_id()) { //如果重复
//							list_cart.get(i).setTjshl(list_cart.get(i).getTjshl() + cart.getTjshl()); //更新添加数量
//							list_cart.get(i).setHj(list_cart.get(i).getTjshl() * list_cart.get(i).getSku_jg()); //更新合计价格
//							// 老车，更新
//							cartService.update_cart(list_cart.get(i));
//						}
//					}
//					
//				}
//			}
//		}
//		
//		//覆盖cookie
//		Cookie cookie = new Cookie("list_cart_cookie", MyJsonUtil.list_to_json(list_cart));//把cookie购物车集合转换成json字符串放到cookie里
//		cookie.setMaxAge(60 * 60 * 24); //设置cookie时间
//		response.addCookie(cookie);
//		return "redirect:/cart_success.do";
//	}
	
	
	//设置shfxz，优化前
//	@RequestMapping("change_shfxz")
//	public String change_shfxz(HttpServletResponse response, T_MALL_SHOPPINGCAR cart,HttpSession session, @CookieValue(value="list_cart_cookie",required=false) String list_cart_cookie,ModelMap map) {
//		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();//创建购物车集合
//		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT)session.getAttribute("user");
//		
//		//购物车修改业务
//		if(user == null) { //用户未登录
//			//修改cookie，把cookie字符串转换为list
//			list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
//			for (int i = 0; i < list_cart.size(); i++) {
//				//如果cookie中的sku_id 与 传过来的sku_id 相等，就把最新的shfxz设置到cookie中
//				if (list_cart.get(i).getSku_id() == cart.getSku_id()) {
//					list_cart.get(i).setShfxz(cart.getShfxz());
//				}
//			}
//			
//			//覆盖cookie
//			Cookie cookie = new Cookie("list_cart_cookie", MyJsonUtil.list_to_json(list_cart));//把cookie购物车集合转换成json字符串放到cookie里
//			cookie.setMaxAge(60 * 60 * 24); //设置cookie时间
//			response.addCookie(cookie);
//		}else { //用户已登录
//			//修改db
//			list_cart = (List<T_MALL_SHOPPINGCAR>)session.getAttribute("list_cart_session"); //数据库
//			for (int i = 0; i < list_cart.size(); i++) {
//				if (list_cart.get(i).getSku_id() == cart.getSku_id()) {
//					list_cart.get(i).setShfxz(cart.getShfxz());
//					cartService.update_cart(list_cart.get(i));//修改数据库shfxz
//				}
//			}
//		}
//		map.put("list_cart", list_cart);
//		return "cartListInner";
//	}

}
