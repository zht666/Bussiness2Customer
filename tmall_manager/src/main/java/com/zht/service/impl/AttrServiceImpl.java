package com.zht.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zht.bean.OBJECT_T_MALL_ATTR;
import com.zht.bean.T_MALL_VALUE;
import com.zht.mapper.AttrMapper;
import com.zht.service.AttrService;

@Service
public class AttrServiceImpl implements AttrService {

	@Autowired
	AttrMapper attrMapper;
	
	@Override
	public void save_attr(int flbh2, List<OBJECT_T_MALL_ATTR> list_attr) {
		for (int i = 0; i < list_attr.size(); i++) {
			//插入属性，返回主键
			OBJECT_T_MALL_ATTR attr = list_attr.get(i);
			attrMapper.insert_attr(flbh2, attr);
			
			//获得返回主键批量插入属性值
			attrMapper.insert_values(attr.getId(), attr.getList_value());
		}
	}

	@Override
	public List<OBJECT_T_MALL_ATTR> get_attr_list(int flbh2) {
		List<OBJECT_T_MALL_ATTR> list_attr = attrMapper.select_attr_list(flbh2);
		return list_attr;
	}

//	@Override
//	public List<OBJECT_T_MALL_ATTR> get_attr_list(int flbh2) {
//		//查询属性名表
//		List<OBJECT_T_MALL_ATTR> list_attr = attrMapper.select_attr_list(flbh2);
//		//查询属性值表
//		List<T_MALL_VALUE> list_value = attrMapper.select_value_list(flbh2);
//		//遍历属性名集合
//		for (OBJECT_T_MALL_ATTR object_T_MALL_ATTR : list_attr) {
//			List<T_MALL_VALUE> list = new ArrayList<T_MALL_VALUE>();
//			//遍历属性值集合
//			for (T_MALL_VALUE t_MALL_VALUE : list_value) {
//				//如果属性名表中的id等于属性值表中的属性名id，把这部分数据封装到新集合中
//				if(object_T_MALL_ATTR.getId() == t_MALL_VALUE.getShxm_id()) {
//					list.add(t_MALL_VALUE);
//				}
//			}
//			//把新的属性值集合设置到属性名对象中
//			object_T_MALL_ATTR.setList_value(list);
//		}
//		
//		return list_attr;
//	}


}
