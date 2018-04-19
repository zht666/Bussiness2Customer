package com.zht.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zht.bean.DETAIL_T_MALL_SKU;
import com.zht.bean.T_MALL_SKU;
import com.zht.mapper.ItemMapper;
import com.zht.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemMapper itemMapper;
	
	@Override
	public DETAIL_T_MALL_SKU get_sku_detail(int sku_id) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("sku_id", sku_id);
		DETAIL_T_MALL_SKU obj_sku = itemMapper.select_detail_sku(map);
		return obj_sku;
	}

	@Override
	public List<T_MALL_SKU> get_sku_list_by_spu(int spu_id) {
		List<T_MALL_SKU> list_sku = itemMapper.select_skuList_by_spu(spu_id);
		return list_sku;
	}

}
