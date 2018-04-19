package com.zht.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zht.bean.OBJECT_T_MALL_FLOW;
import com.zht.bean.OBJECT_T_MALL_ORDER;
import com.zht.bean.T_MALL_ADDRESS;
import com.zht.bean.T_MALL_ORDER_INFO;
import com.zht.exception.OverSaleException;
import com.zht.mapper.OrderMapper;
import com.zht.service.OrderService;
import com.zht.util.MyDateUtil;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderMapper orderMapper;
	
	@Override //保存订单
	public void save_order(T_MALL_ADDRESS get_address, OBJECT_T_MALL_ORDER order) {
		List<Integer> list_id = new ArrayList<Integer>();
		
		//保存订单
		Map<Object, Object> map_ordder = new HashMap<Object, Object>();
		map_ordder.put("order", order);
		map_ordder.put("address", get_address);
		orderMapper.insert_order(map_ordder);//插入订单，返回主键，主键返回给order
		
		List<OBJECT_T_MALL_FLOW> list_flow = order.getList_flow();
		for (int i = 0; i < list_flow.size(); i++) {
			//保存物流
			// 根据物流插入信息返回主键，MyBatis中批量插入无法返回主键，所以需要循环插入
			OBJECT_T_MALL_FLOW flow = list_flow.get(i);
			flow.setMdd(get_address.getYh_dz());
			Map<Object, Object> map_flow = new HashMap<Object, Object>();
			map_flow.put("flow", flow);
			map_flow.put("dd_id", order.getId());
			//根据order返回的主键，循环插入物流信息
			orderMapper.insert_flow(map_flow); //插入之后通过map_flow把主键返回给flow
			
			//保存订单信息
			//每返回一个主键就批量插入info信息
			List<T_MALL_ORDER_INFO> list_info = flow.getList_info();
			Map<Object, Object> map_info = new HashMap<Object, Object>();
			map_info.put("list_info", list_info);
			map_info.put("dd_id", order.getId());
			map_info.put("flow_id", flow.getId());
			//通过flow返回的主键，批量插入订单信息表
			orderMapper.insert_infos(map_info);//为什么要有订单信息表：订单里的商品数据属于用户信息，不能把他和平台的商品数据关联起来
			
			for (int j = 0; j < list_info.size(); j++) {
				list_id.add(list_info.get(i).getGwch_id());//把购物车id放到集合中
			}
		}
		
		//删除已经生成订单的购物车对象
		Map<Object, Object> map_cart = new HashMap<Object, Object>();
		map_cart.put("list_id", list_id); //把购物车id集合放到购物车集合中
		orderMapper.delete_carts(map_cart);//通过购物车集合删除购物车
	}

	//原则：但凡用户的整个操作过程中，有一个小小的行为出现瑕疵了，整个行为都需要回滚，应该让客户重新选择商品，系统不能为用户做决定
	@Override
	public void pay_success(OBJECT_T_MALL_ORDER order) throws OverSaleException {
		//修改订单状态，已支付，系统不能为用户做决定，所以这一步不能省
		order.setJdh(2);
		orderMapper.update_order(order);
		
		//修改物流信息
		List<OBJECT_T_MALL_FLOW> list_flow = order.getList_flow();
		for (int i = 0; i < list_flow.size(); i++) { //第一层for循环，修改物流信息
			OBJECT_T_MALL_FLOW flow = list_flow.get(i);
			//修改物流的业务方法  (暂时修改配送时间，配送描述，目前地址，业务员，联系方式)
			flow.setPsshj(MyDateUtil.getMyDate(1));
			flow.setPsmsh("商品已出库");
			flow.setMqdd("昌平区北七家大仓库");
			flow.setYwy("小丽");
			flow.setLxfsh("18866886868");
			orderMapper.update_pay_flow(flow);
			
			List<T_MALL_ORDER_INFO> list_info = flow.getList_info();
			//修改sku数量和销量等信息
			for (int j = 0; j < list_info.size(); j++) { //第二层for循环修改库存信息
				//必须确定每件商品是可以买的
				T_MALL_ORDER_INFO info = list_info.get(j);
				
				//判断库存警戒线
				long count = orderMapper.select_count_kc(info.getSku_id());
				
				//查询库存的业务
				long kc = getKc(count, info.getSku_id());
				
//				HashMap<Object, Object> map = new HashMap<Object,Object>();
//				map.put("count", count);
//				map.put("sku_id", info.getSku_id());
//				kc = orderMapper.select_kc(map);
				
//				if(count == 0) {
//					kc = 1; //执行带锁sql
//				}else {
//					kc = 1; //执行不带锁sql
//				}
				
				if(kc >= info.getSku_shl()) {  //先确定kc数量大于购买数量
					//对库存进行修改
					orderMapper.update_kc(info);
				}else {
					throw new OverSaleException("over sale");
				}
			}
		}
		
		//修改订单信息  (暂时只修改预计送达时间)
        order.setYjsdshj(MyDateUtil.getMyDate(3));
        orderMapper.update_order(order);
		
		//修改订单状态，出库
		order.setJdh(3);
		orderMapper.update_order_jdh(order);
	}

	private long getKc(long count, int sku_id) {
		HashMap<Object, Object> map = new HashMap<Object,Object>();
		map.put("count", count);
		map.put("sku_id", sku_id);
		long kc = orderMapper.select_kc(map);

		return kc;
	}
	
//	//原则：但凡用户的整个操作过程中，有一个小小的行为出现瑕疵了，整个行为都需要回滚，应该让客户重新选择商品，系统不能为用户做决定
//	@Override
//	public void pay_success(OBJECT_T_MALL_ORDER order) throws OverSaleException {
//		//修改订单状态，已支付，系统不能为用户做决定，所以这一步不能省
//		order.setJdh(2);
//		orderMapper.update_order_jdh(order);
//		
//		//修改订单信息  (暂时只修改预计送达时间)
//		Calendar calendar = Calendar.getInstance();  
//		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 7); //获取7天之后的时间 
//		Date today = calendar.getTime();  
////        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
////        String result = format.format(today);
//		order.setYjsdshj(today);
//		orderMapper.update_order_yjsdshj(order);
//		
//		//修改物流信息
//		List<OBJECT_T_MALL_FLOW> list_flow = order.getList_flow();
//		for (int i = 0; i < list_flow.size(); i++) { //第一层for循环，修改物流信息
//			OBJECT_T_MALL_FLOW flow = list_flow.get(i);
//			//修改物流的业务方法  (暂时修改配送时间，配送描述，目前地址，业务员，联系方式)
//			flow.setPsshj(new Date());
//			flow.setPsmsh("商品已出库");
//			flow.setMqdd("昌平区北七家大仓库");
//			flow.setYwy("小丽");
//			flow.setLxfsh("18866886868");
//			flow.setYh_id(order.getYh_id());
//			orderMapper.update_pay_flow(flow);
//			
//			List<T_MALL_ORDER_INFO> list_info = flow.getList_info();
//			//修改sku数量和销量等信息
//			for (int j = 0; j < list_info.size(); j++) { //第二层for循环修改库存信息
//				//必须确定每件商品是可以买的
//				T_MALL_ORDER_INFO info = list_info.get(j);
//				//查询库存的业务
//				long kc = 0;
//				kc = orderMapper.select_kc(info.getSku_id());
//				
//				//判断库存警戒线
//				long count = 0;
//				count = orderMapper.select_count_kc(info.getSku_id());
//				
////				if(count == 0) {
////					kc = 1; //执行带锁sql
////				}else {
////					kc = 1; //执行不带锁sql
////				}
//				if(kc > info.getSku_shl()) {  //先确定kc数量大于购买数量
//					//对库存进行修改
//					orderMapper.update_kc(info);
//				}else {
//					throw new OverSaleException("over sale");
//				}
//			}
//		}
//		
//		//修改订单状态，出库
//		order.setJdh(3);
//		orderMapper.update_order_jdh(order);
//	}


}
