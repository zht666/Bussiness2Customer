package com.zht.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zht.bean.T_MALL_SHOPPINGCAR;
import com.zht.bean.T_MALL_USER_ACCOUNT;
import com.zht.server.LoginServer;
import com.zht.server.TestServer;
/**
 * 
* @ClassName: IndexCotroller
* @Description: TODO 首页控制层
* @author zht
*
 */
import com.zht.service.CartService;
import com.zht.util.MyJsonUtil;
@Controller
public class LoginCotroller {

	@Autowired
	CartService cartService;
	
	@Autowired
	LoginServer loginServer; // = MyWsFactoryBean.getMyWs(MyPropertiesUtil.getMyProperty("ws.properties", "login_url"), LoginServer.class);
	
	@Autowired
	TestServer testServer;
	
	@Autowired
	JmsTemplate jmsTemplate;//操作mq的对象
	
	@Autowired
	ActiveMQQueue queueDestination;//消息的类型
	
	//增删改，除了查询的写入操作，return结果一定是重定向的，防止重复提交
	//查询操作，return结果不用重定向，因为查询的目的就是查询最新信息
	//但是登录例外，登录属于特殊的查询操作，认证操作，需要重定向
	@RequestMapping("login")
	public String goto_login(@RequestParam(value="redirect", required=false) String redirect , String dataSource_type, HttpServletResponse response, HttpSession session, T_MALL_USER_ACCOUNT user,
			 @CookieValue(value="list_cart_cookie",required=false) String list_cart_cookie) {
		
		T_MALL_USER_ACCOUNT select_user = new T_MALL_USER_ACCOUNT(); //loginMapper.select_user(user);
		
		//登录，远程用户认证接口
		String loginJson = "";
		//选择数据源
		if ("1".equals(dataSource_type)) {
			loginJson = loginServer.login(user);
			testServer.ping("hello");
			
		}else if("2".equals(dataSource_type)){
			loginJson = loginServer.login2(user);
		}
		
		select_user = MyJsonUtil.json_to_object(loginJson, T_MALL_USER_ACCOUNT.class);
		
		if(select_user == null) {
			return "redirect:/login.do";
		}else {
			
			//只要登录成功就记录日志
			//异步调用消息队列，发布日志的消息
			// 发送mq消息，MessageCreator消息制造器，destination消息的类型
			final String message = select_user.getId() + " | "+select_user.getYh_mch()+" | 登录";
			jmsTemplate.send(queueDestination, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(message);
				}
			});

			
			session.setAttribute("user", select_user);
			
			try {
				//在客户端存储用户个性化信息，方便用户下次再访问网站时使用
				Cookie cookie = new Cookie("yh_mch", URLEncoder.encode(select_user.getYh_mch(), "utf-8"));
				
//				cookie.setPath("/");//默认根路径
				cookie.setMaxAge(60*60*24);//过期时间，默认24小时
				
				response.addCookie(cookie);
				
				Cookie cookie2 = new Cookie("yh_nch", URLEncoder.encode("刘德华", "utf-8"));
//				cookie2.setPath("/");//默认根路径
				cookie2.setMaxAge(60*60*24);//过期时间，默认24小时
				response.addCookie(cookie2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//用户登录，同步购物车数据
			combine_cart(select_user, response, session,list_cart_cookie);
			
			//判断用户是否携带redirect重定向参数，如果不携带直接进入主页
			if (StringUtils.isBlank(redirect)) {
				return "redirect:/index.do";
			}else { //如果携带参数，跳到指定结算页面
				return "redirect:" + redirect;
			}
		}
		
	}

	//合并购物车，用户登录后，把保存到cookie中的数据保存到数据库，并同步session，删除cookie，优化后
	private void combine_cart(T_MALL_USER_ACCOUNT user, HttpServletResponse response, HttpSession session, String list_cart_cookie) {
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();//创建购物车集合
		// 判断cookie是否为空
		if(StringUtils.isBlank(list_cart_cookie)) {
			//如果cookie为空
		}else {
			//如果cookie不为空，判断db是否为空
			List<T_MALL_SHOPPINGCAR> list_cart_db = cartService.get_list_cart_by_user(user);
			list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
			
			//优化后，先写循环
			for (int i = 0; i < list_cart.size(); i++) { 
				T_MALL_SHOPPINGCAR cart = list_cart.get(i);
				cart.setYh_id(user.getId());//必须先设置用户id
				boolean b = cartService.if_cart_exists(list_cart.get(i)); //判断购物车是否存在，如果存在，返回true
				
				if (b) { //购物车重复，有该数据，更新购物车
					for (int j = 0; j < list_cart_db.size(); j++) {//遍历购物车集合
						if (cart.getSku_id() == list_cart_db.get(j).getSku_id()) { //如果重复
							cart.setTjshl(cart.getTjshl() + list_cart_db.get(j).getTjshl()); //更新添加数量
							cart.setHj(cart.getTjshl() * cart.getSku_jg()); //更新合计价格
							// 老车，更新
							cartService.update_cart(cart);
						}
					}
				}else { //数据库没有改数据，添加
					cartService.add_cart(list_cart.get(i));
				}
			}
		}
		// 同步session，清空cookie
		session.setAttribute("list_cart_session", cartService.get_list_cart_by_user(user));
		response.addCookie(new Cookie("list_cart_cookie", ""));
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
	
	// 合并购物车，优化前
//	private void combine_cart(T_MALL_USER_ACCOUNT user, HttpServletResponse response, HttpSession session, String list_cart_cookie) {
//		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();//创建购物车集合
//		// 判断cookie是否为空
//		if(StringUtils.isBlank(list_cart_cookie)) {
//			//如果cookie为空
//		}else {
//			//如果cookie不为空，判断db是否为空
//			List<T_MALL_SHOPPINGCAR> list_cart_db = cartService.get_list_cart_by_user(user);
//			list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
//			
//			if(list_cart_db == null || list_cart_db.size() == 0) {
//				//如果db为空
//				for (int i = 0; i < list_cart.size(); i++) {
//					list_cart.get(i).setYh_id(user.getId());//必须先设置用户id
//					cartService.add_cart(list_cart.get(i));
//				}
//			}else {
//				//如果db不为空，先判断是否重复
//				for (int i = 0; i < list_cart.size(); i++) {
//					boolean b = if_new_cart(list_cart_db, list_cart.get(i));//如果不重复，是新车，返回true
//					if (b) {  //如果不重复，直接添加
//						list_cart.get(i).setYh_id(user.getId());//必须先设置用户id
//						cartService.add_cart(list_cart.get(i));
//					}else { //如果重复，就更新
//						for (int j = 0; j < list_cart_db.size(); j++) {//遍历购物车集合
//							if (list_cart.get(i).getSku_id() == list_cart_db.get(j).getSku_id()) { //如果重复
//								list_cart.get(i).setTjshl(list_cart.get(i).getTjshl() + list_cart_db.get(j).getTjshl()); //更新添加数量
//								list_cart.get(i).setHj(list_cart.get(i).getTjshl() * list_cart.get(i).getSku_jg()); //更新合计价格
//								// 老车，更新
//								cartService.update_cart(list_cart.get(i));
//							}
//						}
//					}
//				}
//			}
//		}
//	}


}
