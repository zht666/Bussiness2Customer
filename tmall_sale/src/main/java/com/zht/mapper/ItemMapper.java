package com.zht.mapper;

import java.util.List;
import java.util.Map;

import com.zht.bean.DETAIL_T_MALL_SKU;
import com.zht.bean.T_MALL_SKU;

public interface ItemMapper {

	DETAIL_T_MALL_SKU select_detail_sku(Map<Object, Object> map);
	
	List<T_MALL_SKU> select_skuList_by_spu(int spu_id);

}
