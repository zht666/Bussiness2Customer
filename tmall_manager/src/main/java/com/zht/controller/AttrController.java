package com.zht.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zht.bean.MODEL_T_MALL_ATTR;
import com.zht.bean.OBJECT_T_MALL_ATTR;
import com.zht.service.AttrService;

/**
 * 
* @ClassName: AttrController
* @Description: TODO 属性控制层
* @author zht
*
 */
@Controller
public class AttrController {

	@Autowired
	AttrService attrService;
	
	//datagrid要求返回的类型为json格式
	@RequestMapping("get_attr_list_json")
	@ResponseBody
	public List<OBJECT_T_MALL_ATTR> get_attr_list_json(int flbh2) {
		
		List<OBJECT_T_MALL_ATTR> list_attr = new ArrayList<OBJECT_T_MALL_ATTR>();
		//通过flbh2二级分类编号ID获得属性集合
		list_attr = attrService.get_attr_list(flbh2);
		return list_attr;
	}
	
	
	@RequestMapping("get_attr_list")
	public String get_attr_list(ModelMap map, int flbh2) {
		
		List<OBJECT_T_MALL_ATTR> list_attr = new ArrayList<OBJECT_T_MALL_ATTR>();
		//通过flbh2二级分类编号ID获得属性集合
		list_attr = attrService.get_attr_list(flbh2);
		
//		MODEL_T_MALL_ATTR list_attr = new MODEL_T_MALL_ATTR();
		map.put("flbh2", flbh2);
		map.put("list_attr", list_attr);
		return "attrListInner";
	}
	
	@RequestMapping("goto_attr_add")
	public String goto_attr_add(ModelMap map, int flbh2) {
		map.put("flbh2", flbh2);
		return "attrAdd";
	}
	
	/**
	* @Title: attr_add
	* @Description: TODO  上传属性和属性值信息
	* @param @param flbh2
	* @param @param list_attr
	* @param @return    参数
	* @return ModelAndView    返回类型
	* @throws
	* mvc如何封装，看页面上所关联的属性和对象，有setter就能封装上，没有setter就不能封装上
	* Setter：所关联的属性和对象
	 */
//	@RequestMapping("attr_add")
//	public ModelAndView attr_add(int flbh2, MODEL_T_MALL_ATTR list_attr) {//封装级联属性
//		
//		//保存属性
//		attrService.save_attr(flbh2, list_attr.getList_attr());
//		
//		//ModelAndView让参数和重定向地址分开
//		ModelAndView mv = new ModelAndView("redirect:/goto_attr_add.do");
//		mv.addObject("flbh2", flbh2);
//		//return走springmvc的dispatcher，redirect:/goto_spu_add.do按url去读，参数会重新放到参数域里面
//		return mv;
//	}
	
	@RequestMapping("attr_add")
	public ModelAndView attr_add(int flbh2, MODEL_T_MALL_ATTR list_attr) {//封装级联属性
		
		//保存属性
		attrService.save_attr(flbh2, list_attr.getList_attr());
		
		//ModelAndView让参数和重定向地址分开
		ModelAndView mv = new ModelAndView("redirect:/index.do");//goto_attr_add.do
//		mv.addObject("flbh2", flbh2);
		mv.addObject("url", "goto_attr_add.do?flbh2="+flbh2);
		mv.addObject("title", "添加属性");
		//return走springmvc的dispatcher，redirect:/goto_spu_add.do按url去读，参数会重新放到参数域里面
		return mv;
	}
}
