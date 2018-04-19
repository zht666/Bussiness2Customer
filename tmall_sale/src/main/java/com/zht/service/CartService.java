package com.zht.service;

import java.util.List;

import com.zht.bean.T_MALL_SHOPPINGCAR;
import com.zht.bean.T_MALL_USER_ACCOUNT;

public interface CartService {

	void add_cart(T_MALL_SHOPPINGCAR cart); //添加购物车

	void update_cart(T_MALL_SHOPPINGCAR cart); //更新购物车

	boolean if_cart_exists(T_MALL_SHOPPINGCAR cart); //判断db中是否有该购物车
	
	//根据用户查询购物车
	List<T_MALL_SHOPPINGCAR> get_list_cart_by_user(T_MALL_USER_ACCOUNT user);

}
