package com.zht.service;

import com.zht.bean.OBJECT_T_MALL_ORDER;
import com.zht.bean.T_MALL_ADDRESS;
import com.zht.exception.OverSaleException;

public interface OrderService {

	void save_order(T_MALL_ADDRESS get_address, OBJECT_T_MALL_ORDER order);

	void pay_success(OBJECT_T_MALL_ORDER order) throws OverSaleException;

}
