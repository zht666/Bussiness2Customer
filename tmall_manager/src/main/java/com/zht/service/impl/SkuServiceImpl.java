package com.zht.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zht.bean.T_MALL_PRODUCT;
import com.zht.bean.T_MALL_SKU;
import com.zht.bean.T_MALL_SKU_ATTR_VALUE;
import com.zht.mapper.SkuMapper;
import com.zht.service.SkuService;

@Service
public class SkuServiceImpl implements SkuService {

	@Autowired
	SkuMapper skuMapper;
	
	@Override
	public void save_sku(T_MALL_SKU sku, T_MALL_PRODUCT spu, List<T_MALL_SKU_ATTR_VALUE> list_attr) {
		//为了防止前台传过来有0值，新建一个list，把非0值存到新list里，再把新list插入数据库
		List<T_MALL_SKU_ATTR_VALUE> list = new ArrayList<T_MALL_SKU_ATTR_VALUE>();
		for (int i = 0; i < list_attr.size(); i++) {
			if(list_attr.get(i).getShxm_id() != 0 && list_attr.get(i).getShxzh_id() != 0) {
//				list_attr.remove(i);
				list.add(list_attr.get(i));
			}
		}
		//保存sku表，返回sku主键
		sku.setShp_id(spu.getId());
		skuMapper.insert_sku(sku);
		
		//根据sku主键，批量保存属性关联表
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("shp_id", spu.getId());
		map.put("sku_id", sku.getId());
		map.put("list_av", list);
		skuMapper.insert_sku_av(map);
	}

}
