package com.zht.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.zht.bean.T_MALL_PRODUCT;
import com.zht.service.SpuService;
import com.zht.util.MyFileUpload;
/**
* @ClassName: SpuController
* @Description: TODO spu商品信息控制层
* @author zht
*
 */
@Controller
public class SpuController {

	@Autowired
	private SpuService spuService;
	
	/**
	 * 
	* @Title: goto_spu_add
	* @Description: TODO 跳转到spu商品信息管理页面，选择分类级别
	* @param @param map
	* @param @param spu
	* @param @return    参数
	* @return String    返回类型
	* @throws
	 */
	@RequestMapping("/goto_spu_add")
	public String goto_spu_add(ModelMap map, T_MALL_PRODUCT spu) {
		map.put("spu", spu);
		return "spuAdd";
	}
	
	/**
	* @Title: get_spu_list
	* @Description: TODO  通过品牌Id和分类编号2获取商品信息表集合
	* @param @param pp_id
	* @param @param flbh2
	* @param @return    参数
	* @return List<T_MALL_PRODUCT>    返回类型
	* @throws
	 */
	@RequestMapping("/get_spu_list")
	@ResponseBody
	public List<T_MALL_PRODUCT> get_spu_list(int pp_id, int flbh2) {
		List<T_MALL_PRODUCT> list_spu = spuService.get_spu_list(pp_id,flbh2);
		return list_spu;
	}
	
	/**
	* @Title: spu_add
	* @Description: TODO  跳转到spu信息添加页面，上传图片和表单信息
	* @param @param cover
	* @param @param files
	* @param @param spu
	* @param @return    参数
	* @return ModelAndView    返回类型
	* @throws
	* mvc中建议用ModelAndView进行重定向操作
	 */
	@RequestMapping("/spu_add")
	public ModelAndView spu_add(@RequestParam String cover, @RequestParam("files") MultipartFile[] files, T_MALL_PRODUCT spu) {
		//上传图片
		List<String> list_image = MyFileUpload.upload_image(files);
		
		//保存商品信息
		spuService.save_spu(list_image, spu, Integer.parseInt(cover));
		
		//ModelAndView让参数和重定向地址分开
		ModelAndView mv = new ModelAndView("redirect:/index.do");//goto_spu_add.do
//		mv.addObject("flbh1", spu.getFlbh1());
//		mv.addObject("flbh2", spu.getFlbh2());
//		mv.addObject("pp_id", spu.getPp_id());
		mv.addObject("url", "goto_spu_add.do?flbh1="+spu.getFlbh1()+"&flbh2="+spu.getFlbh2()+"&pp_id="+spu.getPp_id());
		mv.addObject("title", "添加商品信息");
		//return走springmvc的dispatcher，redirect:/goto_spu_add.do按url去读，参数会重新放到参数域里面
		return mv;
	}
//	@RequestMapping("/spu_add")
//	public ModelAndView spu_add(@RequestParam String cover, @RequestParam("files") MultipartFile[] files, T_MALL_PRODUCT spu) {
//		//上传图片
//		List<String> list_image = MyFileUpload.upload_image(files);
//		
//		//保存商品信息
//		spuService.save_spu(list_image, spu, Integer.parseInt(cover));
//		
//		//ModelAndView让参数和重定向地址分开
//		ModelAndView mv = new ModelAndView("redirect:/goto_spu_add.do");
//		mv.addObject("flbh1", spu.getFlbh1());
//		mv.addObject("flbh2", spu.getFlbh2());
//		mv.addObject("pp_id", spu.getPp_id());
//		//return走springmvc的dispatcher，redirect:/goto_spu_add.do按url去读，参数会重新放到参数域里面
//		return mv;
//	}
}
