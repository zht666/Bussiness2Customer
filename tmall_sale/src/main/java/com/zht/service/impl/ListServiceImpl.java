package com.zht.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zht.bean.OBJECT_T_MALL_SKU;
import com.zht.bean.T_MALL_SKU_ATTR_VALUE;
import com.zht.mapper.ListMapper;
import com.zht.service.ListService;

@Service
public class ListServiceImpl implements ListService {

	@Autowired
	private ListMapper listMapper;

	@Override
	public List<OBJECT_T_MALL_SKU> get_list_by_flbh2(int flbh2) {
		List<OBJECT_T_MALL_SKU> list_sku = listMapper.select_list_by_flbh2(flbh2);
		return list_sku;
	}

	/**动态sql
	 * and sku.id in ( select sku0.sku_id from (select sku_id from
	 * t_mall_sku_attr_value where shxm_id = 13 and shxzh_id in (30,31,32,..)) sku0,
	 * (select sku_id from t_mall_sku_attr_value where shxm_id = ? and shxzh_id = ?)
	 * sku1, (select sku_id from t_mall_sku_attr_value where shxm_id = ? and
	 * shxzh_id = ?) sku2 where sku0.sku_id = sku1.sku_id and sku1.sku_id =
	 * sku2.sku_id )
	 * 
	 */
	@Override
	public List<OBJECT_T_MALL_SKU> get_list_by_attr(List<T_MALL_SKU_ATTR_VALUE> list_attr, int flbh2) {

		StringBuffer subSql = new StringBuffer("");

		// 根据属性集合动态拼接条件过滤的sql语句

		if (list_attr != null && list_attr.size() > 0) {
			subSql.append(" and sku.id in ( select sku0.sku_id from ");
			for (int i = 0; i < list_attr.size(); i++) {
				//循环每个sku
				subSql.append(" (select sku_id from t_mall_sku_attr_value where shxm_id=" + list_attr.get(i).getShxm_id()
						+ " and shxzh_id = " + list_attr.get(i).getShxzh_id() + ") sku" + i + " ");

				if ((i + 1) < list_attr.size() && list_attr.size() > 1) {// 添加逗号
					subSql.append(" , ");
				}
			}
			
			//过个sku大于1，拼接where
			if (list_attr.size() > 1) {
				subSql.append(" where ");
				
				//拼接where后面的过滤语句
				for (int i = 0; i <list_attr.size(); i++) {
					if ((i + 1) < list_attr.size() ) {
						subSql.append(" sku"+i+".sku_id="+"sku"+(i+1)+".sku_id");
						if(list_attr.size() > 2 && i < (list_attr.size()-2)) {//拼接and
							subSql.append(" and ");
						}
					}
				}
			}
			subSql.append(" ) ");
		}

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("flbh2", flbh2);
		map.put("subSql", subSql.toString());
		List<OBJECT_T_MALL_SKU> list_sku = listMapper.select_list_by_attr(map);
		return list_sku;
	}

}
