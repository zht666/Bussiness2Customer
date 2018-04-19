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
	<!-- fit：让内部布局的大小适应外部布局的大小 -->
	<div class="easyui-layout" data-options="fit:true"> <!-- data-options是easyUI的扩展属性 -->
		<div data-options="region:'north',split:true,border:false" style="height:50px">
			<div style="margin-top:10px; margin-left:10px">
				一级：<select data-options="width:80" class="easyui-combobox" id="attr_class_1_select" onchange="get_attr_class_2(this.value);"><option>请选择</option></select>&nbsp&nbsp 
				二级：<select data-options="width:80" class="easyui-combobox" id="attr_class_2_select" onchange="get_attr_list_json(this.value)"><option>请选择</option></select>&nbsp&nbsp
				<a href="javascript:goto_attr_add();">添加</a>
			</div>
		</div>
		<div data-options="region:'west',split:true,border:false" style="width:100px">
			<div style="margin-top:10px; margin-left:30px">
				查询<br>
				删除<br>
				编辑<br>
			</div>
		</div>
		<div data-options="region:'center',border:false">
			<div id="attrListInner" class="easyui-datagrid"><!-- 内嵌页 --></div>	
		</div>
	</div>
	
	
<script type="text/javascript">
	$(function (){
// 		$.getJSON("js/json/class_1.js",function(data){
// 			$(data).each(function(i,json){
// 				$("#attr_class_1_select").append("<option value="+json.id+">"+json.flmch1+"</option>");
// 			});
// 		});
		//下拉输入框
		$('#attr_class_1_select').combobox({    
		    url:'js/json/class_1.js',    
		    valueField:'id',   //属性值 
		    textField:'flmch1',//文本值
// 		    width:80,
		    onChange:function get_attr_class_2(){
		    	//获取当前的下拉列表被选中的id
		    	var class_1_id = $(this).combobox("getValue");
// 				$.getJSON("js/json/class_2_"+class_1_id+".js", function(data){
// 					$("#attr_class_2_select").empty();
// 					$(data).each(function(i,json){
// 						$("#attr_class_2_select").append("<option value="+json.id+">"+json.flmch2+"</option>");
// 					});
// 				});
				
				$('#attr_class_2_select').combobox({    
				    url:"js/json/class_2_"+class_1_id+".js",    
				    valueField:'id',   //属性值 
				    textField:'flmch2',//文本值
// 				    width:80
				    onChange:function (){
				    	var flbh2 = $(this).combobox("getValue");
				    	get_attr_list_json(flbh2);
				    }
				});
			}
		});
	});
	

	
	function goto_attr_add(){
		var class_2_id = $("#attr_class_2_select").combobox("getValue"); //$("#attr_class_2_select").val();
// 		window.location.href="goto_attr_add.do?flbh2="+class_2_id;
		add_tab("goto_attr_add.do?flbh2="+class_2_id,"添加属性");
	}
	
	//属性列表异步内嵌页
	function get_attr_list(flbh2){
		//异步查询
		$.post("get_attr_list.do",{flbh2:flbh2}, function(data){
			//添加内嵌页
			$("#attrListInner").html(data);
		});
	}
	
	function get_attr_list_json(flbh2){
		//异步查询
		$("#attrListInner").datagrid({
			url:'get_attr_list_json.do',
			queryParams:{
				flbh2:flbh2
			},
		    columns:[[//列属性    
		        {field:'id',title:'id',width:100,align:'center'},    
		        {field:'shxm_mch',title:'属性名',width:150,align:'center'},    
		        {field:'list_value',title:'属性值',width:380,align:'center',
					formatter: function(value,row,index){//单元格格式化函数
						var str = "";
						//处理字段值的代码
						$(value).each(function(i,json){
							str = str + json.shxzh + json.shxzh_mch + " ";
						});
						return str;
					}
		        },
		        {field:'chjshj',title:'创建时间',width:250,align:'center',
		        	formatter:function(value,row,index){
		        		var d = new Date(value);
		        		var str = d.toLocaleString();//js日期格式化
		        		return str;
		        	}
		        	
		        }
		    ]] 
		})
	}

</script>	
	
</body>
</html>