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
	spu商品信息管理
	<hr>
	<div style="margin-top:10px; margin-left:10px">
		一级：<select data-options="width:80" class="easyui-combobox" id="class_1_select" onchange="get_class_2(this.value);"><option>请选择</option></select>&nbsp&nbsp 
		二级：<select data-options="width:80" class="easyui-combobox" id="class_2_select"><option>请选择</option></select>&nbsp&nbsp 
		品牌：<select data-options="width:80" class="easyui-combobox" id="tm_select"><option>请选择</option></select><br>
		查询<br>
		<a href="javascript:goto_spu_add();">添加</a><br>
		删除<br>
		编辑<br>
	</div>

<script type="text/javascript">
	$(function (){
// 		$.getJSON("js/json/class_1.js",function(data){
// 			$(data).each(function(i,json){
// 				$("#class_1_select").append("<option value="+json.id+">"+json.flmch1+"</option>");
// 			});
// 		});

		$('#class_1_select').combobox({    
		    url:'js/json/class_1.js',    
		    valueField:'id',   //属性值 
		    textField:'flmch1',//文本值
		    onChange:function get_class_2(){
				var class_1_id = $(this).combobox("getValue");
// 		    	$.getJSON("js/json/class_2_"+class_1_id+".js", function(data){
// 					$("#class_2_select").empty();
// 					$(data).each(function(i,json){
// 						$("#class_2_select").append("<option value="+json.id+">"+json.flmch2+"</option>");
// 					});
// 				});

				$('#class_2_select').combobox({    
				    url:"js/json/class_2_"+class_1_id+".js",    
				    valueField:'id',   //属性值 
				    textField:'flmch2',//文本值
				    onChange:function get_tm(){
// 						$.getJSON("js/json/tm_class_1_"+class_1_id+".js", function(data){
// 							$("#tm_select").empty();
// 							$(data).each(function(i,json){
// 								$("#tm_select").append("<option value="+json.id+">"+json.ppmch+"</option>");
// 							});
// 						});
				    	$('#tm_select').combobox({    
						    url:"js/json/tm_class_1_"+class_1_id+".js",    
						    valueField:'id',   //属性值 
						    textField:'ppmch'//文本值
				    	});
					}
				    
				});
// 				get_tm(class_1_id);
			}
		});
	});
	
	function goto_spu_add(){
		var class_1_id = $("#class_1_select").combobox("getValue"); //$("#class_1_select").val();
		var class_2_id = $("#class_2_select").combobox("getValue"); //$("#class_2_select").val();
		var tm_id = $("#tm_select").combobox("getValue"); //$("#tm_select").val();
		
// 		window.location.href="goto_spu_add.do?flbh1="+class_1_id+"&flbh2="+class_2_id+"&pp_id="+tm_id;
		add_tab("goto_spu_add.do?flbh1="+class_1_id+"&flbh2="+class_2_id+"&pp_id="+tm_id, "添加商品信息");
	}
</script>	
	
</body>
</html>