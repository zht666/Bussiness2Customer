<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"  %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath %>">
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>京东商城</title>
</head>
<body>
<form action="save_sku.do">
	<input type="hidden" value="${flbh1}" name="flbh1"/>
	<input type="hidden" value="${flbh2}" name="flbh2"/>
	<div style="margin-top:10px; margin-left:10px">
		品牌：<select id="sku_tm_select" name="pp_id" onchange="get_spu_list(this.value)"></select>
		商品：<select id="spu_list" name="id"></select>
	</div>
	<hr>
	<div style="margin-top:10px; margin-left:10px">
		分类属性：<br>
		<c:forEach items="${list_attr}" var="attr" varStatus="status">
		    <input value="${attr.id}" name="list_attr[${status.index}].shxm_id" type="checkbox" onclick="show_val(${attr.id},this.checked)"/>${attr.shxm_mch} &nbsp
		</c:forEach>
		<br>
		<c:forEach items="${list_attr}" var="attr" varStatus="status">
			<div id="val_${attr.id}" style="display: none;">
				<c:forEach items="${attr.list_value}" var="val">
					<input value="${val.id}" name="list_attr[${status.index}].shxzh_id" type="radio"/>${val.shxzh}${val.shxzh_mch}&nbsp
				</c:forEach>
			</div>
		</c:forEach>
		颜色：<input type="text" /><br>
		版本：<input type="text" /><br>
		商品库存名称：<input type="text" name="sku_mch"/><br>
		商品库存数量：<input type="text" name="kc"/><br>
		商品库存价格：<input type="text" name="jg"/><br>
		商品库存地址：<input type="text" name="kcdz"/><br>
		<input type="submit" value="提交"/>
	</div>
</form>	

<script type="text/javascript">
	//js是跑在客户端上的，EL表达式是跑在服务器上的，在js页面加载之前，EL表达式就已经执行完了，在客户端上根本不存在EL表达式说法
	//js函数可以使用EL表达式，但是EL表达式不能使用js变量
	//客户端在编译EL表达式结果的时候，不会给结果任何类型的，如果结果是中文的话，服务器直接编译成中文var zh = 中文 ;在客户端会直接报错，
	//所以在js页面的EL表达式一定要加双引号，双引号可以导致没有结果的话是空字符串，不会让整个js函数全都因为EL表达式报错
	$(function (class_1_id){
		var flbh1 = "${flbh1}";
		$.getJSON("js/json/tm_class_1_"+flbh1+".js", function(data){
			$("#sku_tm_select").empty();
			$(data).each(function(i,json){
				$("#sku_tm_select").append("<option value="+json.id+">"+json.ppmch+"</option>");
			});
		});
	});
	
	function get_spu_list(pp_id){
		var flbh2 = "${flbh2}";
		$.post("get_spu_list.do", {pp_id:pp_id,flbh2:flbh2}, function(data){
			$("#spu_list").empty();
			$(data).each(function(i, json){
				$("#spu_list").append("<option value="+json.id+">"+json.shp_mch+"</option>")
			});
		});
	}
	
	function show_val(attr_id,checked){
// 		alert(checked);
		if(checked){
			$("#val_"+attr_id).show();
		}else{
			$("#val_"+attr_id).hide();
		}
	}
	
</script>
	
</body>
</html>