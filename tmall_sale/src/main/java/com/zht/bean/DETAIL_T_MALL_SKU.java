package com.zht.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain=true)
//一个复合型的java对象一定对应着resultMap，后台一定对应着多表联合的查询
public class DETAIL_T_MALL_SKU extends T_MALL_SKU {

	private T_MALL_PRODUCT spu;
	private List<T_MALL_PRODUCT_IMAGE> list_image;
	private List<OBJECT_AV_NAME> list_av_name;
	

	
	
}
