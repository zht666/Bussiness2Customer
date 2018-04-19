package com.zht.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.zht.bean.OBJECT_T_MALL_FLOW;
import com.zht.bean.OBJECT_T_MALL_ORDER;
import com.zht.bean.T_MALL_ADDRESS;
import com.zht.bean.T_MALL_ORDER_INFO;
import com.zht.bean.T_MALL_SHOPPINGCAR;
import com.zht.bean.T_MALL_USER_ACCOUNT;
import com.zht.exception.OverSaleException;
import com.zht.server.AddressServerInf;
import com.zht.service.CartService;
import com.zht.service.OrderService;

//ModelMap和@SessionAttributes配合使用，map中所放入的order都会转入到session中，在其他方法中可以直接用@ModelAttribute拿
@Controller
@SessionAttributes("order")
public class OrderController {
	
	@Autowired
	AddressServerInf addressServerInf;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	CartService cartService;

	/*
	 * t_mall_flow 送货清单
	 * t_mall_order 主订单表
	 * t_mall_order_info 用户自己的数据，订单信息表
	 */
	//去结算页页面
	@RequestMapping("goto_checkOrder")
	public String goto_checkOrder(HttpSession session, ModelMap map) {
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
		
		//从session中获取用户数据
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT)session.getAttribute("user");
		//判断用户是否为空，如果为空代表没登录
		if(user == null) { //没登录跳到结算登录页面
			return "redirect:/goto_login_checkOrder.do";
		}else { //用户已登录
			//购物车列表，session中的数据，list_cart直接从session中拿，不用通过form表单传递
			list_cart = (List<T_MALL_SHOPPINGCAR>)session.getAttribute("list_cart_session");
			
			OBJECT_T_MALL_ORDER order = new OBJECT_T_MALL_ORDER();//主订单对象
			order.setYh_id(user.getId());//设置用户id
			order.setJdh(1); //设置进度号
			order.setZje(get_sum(list_cart)); //设置总金额
			//结算业务
			
			//根据购物车的选中状态，获得库存地址信息
			HashSet<String> set_kcdz = new HashSet<String>();//利用set去重的特点，把地址加进去
			for (int i = 0; i < list_cart.size(); i++) {
				if (list_cart.get(i).getShfxz().equals("1")) {
					//去重
					set_kcdz.add(list_cart.get(i).getKcdz());
				}
			}
			
			//根据库存地址，封装送货清单，有一个库存地址就有一个送货清单（flow对象）
			List<OBJECT_T_MALL_FLOW> list_flow = new ArrayList<OBJECT_T_MALL_FLOW>();
			
			Iterator<String> iterator = set_kcdz.iterator();
			//整个while循环的目的就是为了封装list_flow对象
			while (iterator.hasNext()) { //每循环一次产生一个flow送货清单对象
				String kcdz = (String) iterator.next();//遍历每个库存地址
				
				OBJECT_T_MALL_FLOW flow = new OBJECT_T_MALL_FLOW(); //送货清单对象
				flow.setMqdd("商品未出库");//设置目前地点
				flow.setPsfsh("顺丰快递"); //设置配送方式
				flow.setYh_id(user.getId()); //设置用户id
				
				List<T_MALL_ORDER_INFO> list_info = new ArrayList<T_MALL_ORDER_INFO>();//送货清单里包含的子订单信息
				//根据库存地址生成送货清单
				//循环购物车，将购物车对象转换成订单信息
				for (int i = 0; i < list_cart.size(); i++) {
					if(list_cart.get(i).getShfxz().equals("1") && list_cart.get(i).getKcdz().equals(kcdz)) {
						
						T_MALL_SHOPPINGCAR cart = list_cart.get(i); //购物车
						T_MALL_ORDER_INFO info = new T_MALL_ORDER_INFO(); //订单信息
						
						//将购物车转为订单信息
						info.setGwch_id(cart.getId()); //设置购物车id
						info.setShp_tp(cart.getShp_tp()); //设置商品图片
						info.setSku_id(cart.getSku_id()); //设置库存单元id
						info.setSku_jg(cart.getSku_jg()); //设置价格
						info.setSku_kcdz(cart.getKcdz()); //设置库存地址
						info.setSku_mch(cart.getSku_mch()); //设置sku名称
						info.setSku_shl(cart.getTjshl()); //设置添加数量
						list_info.add(info);
					}
				}
				flow.setList_info(list_info);
				//将送货清单放入送货清单集合
				list_flow.add(flow);
			}
			
			//送货清单放入主订单
			order.setList_flow(list_flow); // 内存中的对象，游离状态对象
			//调用第三方应用程序时，都要处理异常
			try {
				//调用第三方应用程序，通过用户获取地址集合
				List<T_MALL_ADDRESS> list_address = addressServerInf.get_addresses(user);
				map.put("list_address", list_address);
			} catch (Exception e) {
				e.printStackTrace();
				//处理用户系统调用异常
				return "redirect:/order_fail.do";
			}
			map.put("order", order);
		}
		return "checkOrder"; //结算列表页面
	}
	
	@RequestMapping("save_order")
	public String save_order(HttpSession session, @ModelAttribute("order") OBJECT_T_MALL_ORDER order, T_MALL_ADDRESS address) {
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT)session.getAttribute("user");
		
		//获取地址信息
		T_MALL_ADDRESS get_address = addressServerInf.get_address(address.getId());
		
		//调用保存订单的业务
		orderService.save_order(get_address, order);
		
		//重新同步session
		session.setAttribute("list_cart_session", cartService.get_list_cart_by_user(user));
		
		//重定向到支付服务，传入订单号和交易金额
//		return "redirect:/goto_pay.do";
		return "realPay";
	}
	
	@RequestMapping("goto_pay")
	public String goto_pay() {
		//伪支付服务
		return "pay";
	}
	
	@RequestMapping("pay_success")
	public String pay_success(@ModelAttribute("order") OBJECT_T_MALL_ORDER order) throws OverSaleException {
		//支付成功
		try {
			orderService.pay_success(order);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/order_fail.do";
		}
		return "redirect:/order_success.do";
	}
	
	@RequestMapping("real_pay_success")
	@ResponseBody
	public String real_pay_success(@ModelAttribute("order") OBJECT_T_MALL_ORDER order) throws OverSaleException {
		//支付成功
		try {
			orderService.pay_success(order);
		} catch (Exception e) {
			e.printStackTrace();
			return "success";
		}
		return "success"; //返回字符串
	}
	
	@RequestMapping("order_success")
	public String order_success() {
		//去支付成功页面
		return "orderSuccess";
	}
	
	@RequestMapping("order_fail")
	public String order_fail() {
		//去支付失败页面
		return "orderFail";
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
}
