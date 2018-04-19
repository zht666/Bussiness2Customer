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
<script type="text/javascript">

//ajax异步提交参数的方式一：用数组方式异步传参
//	function get_list_by_attr(){
//		//获得参数
//		var param_array = Array();
//		$("#paramArea input[name='shxparam']").each(function(i,data){
//			param_array[i] = data.value;
//		});
//		//异步请求提交
//// 		alert("异步调用")
//		//刷新商品类表
//		$.post("get_list_by_attr.do",{param_array:param_array}, function(data){
//			$("#skuListInner").html(data);
//		})
//	}

// 	function get_list_by_attr(){
// 		传json数组
// 		var attrJson = {};
// 		$("#paramArea input[name='shxparam']").each(function(i,data){
// 			var json = $.parseJSON(data.value);
// 			attrJson["flbh2"] = ${flbh2};
// 			attrJson["list_attr["+i+"].shxm_id"] = json.shxm_id;
// 			attrJson["list_attr["+i+"].shxzh_id"] = json.shxzh_id;
// 			var str = JSON.stringify(attrJson);//把json转换为字符串
// 		});
// 		$.post("get_list_by_attr.do",attrJson, function(data){
// 			$("#skuListInner").html(data);
// 		})
// 	}
	
// 	$(function(){
// 		var flag = $("div").children().is("button");
// 		alert(flag);
// 		if(!flag){
// 			$(element).parent().hide();
// 		}
// 	});	

	
	//保存属性参数
	function save_param(shxm_id, shxzh_id, shxmch, element){
		//用数组方式异步传参
// 		$("#paramArea").append("<input name='shxparam' type='text' value='"+shxm_id+"_"+shxzh_id+"' />"+shxmch);
// 		alert("保存参数")
		
		//用json方式异步传参
		$("#paramArea").append("<input name='shxparam' type='hidden' value='{\"shxm_id\":"+shxm_id+",\"shxzh_id\":"+shxzh_id+"}'/><span onclick='remove_param(this,"+element.id+")'>"+shxmch+"&nbsp&nbsp</span>");
		//调用ajax异步请求
		get_list_by_attr();
		//传递this获取当前元素，每次点击当前元素就隐藏
		$(element).parent().hide();
	}
	
	//ajax异步提交参数的方式二：用json方式异步传参
	function get_list_by_attr(){
		//获得参数，传json字符串
		var jsonStr = "flbh2="+${flbh2};
		$("#paramArea input[name='shxparam']").each(function(i,data){
			var json = $.parseJSON(data.value);//接受一个JSON字符串，返回解析后的对象，必须包围双引号
			jsonStr = jsonStr + "&list_attr["+i+"].shxm_id="+json.shxm_id+"&list_attr["+i+"].shxzh_id="+json.shxzh_id;
		});
// 		alert(jsonStr);
		//异步请求提交
		
		//刷新商品类表
		$.post("get_list_by_attr.do",jsonStr, function(data){
			$("#skuListInner").html(data);
		})
	}
	
	//点击移除筛选项，并把相应隐藏的属性列表显现出来
	function remove_param(ele, btnId){
		$(btnId).parent().show();//把相应隐藏的属性列表显现出来
		$(ele).prev().remove();//移除筛选项
		$(ele).remove();
		get_list_by_attr();
	}

	//添加复选框
	function attr_check(check){
		$(check).attr("onclick","return false;");//多选取消单击事件
		$(check).prevAll().attr("onclick","return false;");//多选之前所有元素取消单击事件
		
		$(check).prevAll().before("<input id='checkboxs' type='checkbox'/>");//添加复选框
		
		$(check).after("&nbsp<button onclick='cancel_check(this)' style='border:0px;background-color:transparent;color: blue;'>×取消</button>");//添加取消按钮
		$(check).next().after("<button style='border:0px;background-color:transparent;color: green;'>√确定</button>");//添加确定按钮
	}
	
	//取消复选框
	function cancel_check(cancel){
		$(cancel).prev().attr("onclick","attr_check(this)");//给多选添加单击事件
		$(cancel).prevAll("#checkboxs").remove();//移除复选框
		$(cancel).next().remove();//移除增加按钮
		$(cancel).remove();//移除取消按钮
	}
	
	
</script>
<title>京东商城</title>
</head>
<body>
	<div id="paramArea"></div>
	<hr>
	<div style="margin-left: 20px">
	<h3>属性列表</h3>
	<!-- 遍历属性名列表，得到属性名 -->
	<c:forEach items="${list_attr}" var="attr">
	<div id="attr_div" style="height: 30px">
	    ${attr.shxm_mch}: &nbsp;
	    <!-- 遍历属性值列表，得到每个属性名对应的属性值和属性值名称 -->
		<c:forEach items="${attr.list_value}" var="val">
<%-- 			<a href="javascript:save_param(${attr.id},${val.id},'${val.shxzh}${val.shxzh_mch}');">${val.shxzh}${val.shxzh_mch}&nbsp</a> --%>
			<button class="btn" id="btn_${val.id}" style="border:0px;background-color:transparent;color: #A758A7;" onclick="save_param(${attr.id},${val.id},'${val.shxzh}${val.shxzh_mch}',this);">${val.shxzh}${val.shxzh_mch}</button>&nbsp;
		</c:forEach>
		<!-- 添加多选 -->
		<button onclick="attr_check(this)" style="border:0px;background-color:transparent;color: red;">＋多选</button>
	</div>
	</c:forEach>
	</div>
<!-- 	<input type="checkbox"/> -->
</body>
</html>