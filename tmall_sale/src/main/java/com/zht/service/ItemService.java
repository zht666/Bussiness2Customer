package com.zht.service;

import java.util.List;

import com.zht.bean.DETAIL_T_MALL_SKU;
import com.zht.bean.T_MALL_SKU;

public interface ItemService {

	DETAIL_T_MALL_SKU get_sku_detail(int sku_id);

	List<T_MALL_SKU> get_sku_list_by_spu(int spu_id);

}
