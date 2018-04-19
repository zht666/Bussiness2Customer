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
	添加商品属性
	<hr>
	<form action="attr_add.do">
		<input type="text" value="${flbh2}" name="flbh2"/>
		<table border="1" id="t_0">
			<tr><td>属性名：<input class="title" type="text" name="list_attr[0].shxm_mch"/></td><td></td><td><a href="javascript:;" onclick="click_add_tr(0)">添加属性值</a></td></tr>
			<tr id="r_0"><td>属性值：<input type="text" name="list_attr[0].list_value[0].shxzh"/></td><td>单位：<input type="text" name="list_attr[0].list_value[0].shxzh_mch"/></td><td><center>删除</center></td></tr>
			<tr><td>属性值：<input id="r_0" type="text" name="list_attr[0].list_value[1].shxzh"/></td><td>单位：<input type="text" name="list_attr[0].list_value[1].shxzh_mch"/></td><td><center>删除</center></td></tr>
		</table><br>
		
		<table border="1">
			<tr><td>属性名：<input type="text" name="list_attr[1].shxm_mch"/></td><td></td><td>添加属性值</td></tr>
			<tr><td>属性值：<input type="text" name="list_attr[1].list_value[0].shxzh"/></td><td>单位：<input type="text" name="list_attr[1].list_value[0].shxzh_mch"/></td><td><center>删除</center></td></tr>
			<tr><td>属性值：<input type="text" name="list_attr[1].list_value[1].shxzh"/></td><td>单位：<input type="text" name="list_attr[1].list_value[1].shxzh_mch"/></td><td><center>删除</center></td></tr>
		</table>
		<input onclick="click_button()" type="button" value="添加属性" style="cursor: pointer;width: 80px">&nbsp&nbsp&nbsp&nbsp
		<input type="submit" value="提交" style="cursor: pointer;width: 80px"/>
	</form>
</body>

<script type="text/javascript">
	var index = 0;
	var len = 0;
	function click_button(){
		//添加属性表格
		var a = '<br><table border="1" id="t_'+(index+1)+'">';
		var b = '<tr><td>属性名：<input class="title" type="text" name="list_attr['+(index+1)+'].shxm_mch"/></td><td></td><td><a href="javascript:;" onclick="click_add_tr()">添加属性值</a></td></tr>';
		var c = '<tr id="r_0"><td>属性值：<input type="text" name="list_attr['+(index+1)+'].list_value[0].shxzh"/></td><td>单位：<input type="text" name="list_attr['+(index+1)+'].list_value[0].shxzh_mch"/></td><td><center>删除</center></td></tr>';
		var d = '<tr><td>属性值：<input type="text" name="list_attr['+(index+1)+'].list_value[1].shxzh"/></td><td>单位：<input type="text" name="list_attr['+(index+1)+'].list_value[1].shxzh_mch"/></td><td><center>删除</center></td></tr>';
		var e = '</table>';
		
		$("#t_"+index).after(a+b+c+d+e);
		index = index+1;
	}
	
	var row = 0;
	function click_add_tr(row){
		//添加属性值
		var f = '<tr id="r_'+(len+1)+'"><td>属性值：<input type="text" name="list_attr['+index+'].list_value['+(len+1)+'].shxzh"/></td><td>单位：<input type="text" name="list_attr['+index+'].list_value['+(len+1)+'].shxzh_mch"/></td><td><center>删除</center></td></tr>';

		$("#t_"+index).find("#r_"+row).append(f);
		row = row+1;
		len = len+1;
	}
	
	
</script>


</html>