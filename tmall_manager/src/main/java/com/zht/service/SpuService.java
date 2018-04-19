package com.zht.service;

import java.util.List;

import com.zht.bean.T_MALL_PRODUCT;

public interface SpuService {

	void save_spu(List<String> list_image, T_MALL_PRODUCT spu, Integer cover);

	List<T_MALL_PRODUCT> get_spu_list(int pp_id, int flbh2);
	
}
