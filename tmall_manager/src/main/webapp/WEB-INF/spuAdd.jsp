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
	<div style="margin-top:10px; margin-left:10px">
		spu信息添加 ${spu.flbh1} | ${spu.flbh2} | ${spu.pp_id}
	</div>
	<hr>
	<div style="margin-top:10px; margin-left:10px">
		<form action="spu_add.do" enctype="multipart/form-data" method="post">
			<input type="hidden" name="flbh1" value="${spu.flbh1}">
			<input type="hidden" name="flbh2" value="${spu.flbh2}">
			<input type="hidden" name="pp_id" value="${spu.pp_id}">
			商品名称：<input type="text" name="shp_mch"/><br><br>
			商品描述：<textarea rows="10" cols="50" name="shp_msh"></textarea><br><br>
			商品图片：<br>
			<!-- float：漂移；margin-left：左边距；border：边框；1px：厚度；red：颜色；solid：材质实心 -->
			<div id="d_0" style="float:left;margin-right: 10px;border: 1px red solid;">
				<input id="file_0" type="file" name="files" style="display: none;" onChange="replace_image(0)"/>
				<img id="image_0" onclick="click_image(0)" style="cursor: pointer;" src="image/upload_hover.png" width="80px" height="80px"/><br>
				<input type="radio" name="cover" value="0" checked="checked">设置封面
			</div>
			<br><br><br><br><br><br><br>
			颜色：<input type="text" /><br>
			版本：<input type="text" /><br>
			<input type="submit" value="提交">
		</form>
	</div>
<script type="text/javascript">
    //index动态参数变量，可以动态插入图片
	function click_image(index){
		$("#file_"+index).click();//会启动子线程，函数的执行顺序要按照子线程的完成顺序触发
	}
    
    //等图片加载完成之后，才能获取图片
    function replace_image(index){
    	//获得图片对象
		var blob_image = $("#file_"+index)[0].files[0];//blob对象是浏览器中缓存的blob对象
		var url = window.URL.createObjectURL(blob_image);//获得浏览器中URL对象，这个URL对象指向浏览器中blob图片对象
		//替换image
		$("#image_"+index).attr("src",url);
		
		var length = $(":file").length;
		if((index+1) == length && length <=5){
			//用户选择的是最后一张图片，才追加图片
			add_image(index);
		}
    }
    
    //追加函数
    function add_image(index){
    	var a = '<div id="d_'+(index+1)+'" style="float:left;margin-right: 10px;border: 1px red solid;">';
    	var b = '<input id="file_'+(index+1)+'" type="file" name="files" style="display: none;" onChange="replace_image('+(index+1)+')"/>';
    	var c = '<img id="image_'+(index+1)+'" onclick="click_image('+(index+1)+')" style="cursor: pointer;" src="image/upload_hover.png" width="80px" height="80px"/><br>';
    	var d = '<input type="radio" name="cover" value="'+(index+1)+'">设置封面';
    	var e = '</div>';
    	
    	$("#d_"+index).after(a+b+c+d+e);

    }
</script>
	
</body>
</html>