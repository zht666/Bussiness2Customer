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
	$(function(){
		var yh_nch = getMyCookie("yh_nch");
// 		alert(yh_mch);
		$("#show_name").text(decodeURIComponent(yh_nch));
	});
	
	function getMyCookie(key){
		var val = "";
		//对cookie操作
		var cookies = document.cookie;
		cookies= cookies.replace(/\s/,"");//先替换空格为空字符串，/\s/代表空格
		var cookie_array = cookies.split(";");//通过分号查分，得到java的js数组
		for (var i = 0; i < cookie_array.length; i++) {
			//yh_mch=lilei
			var cook = cookie_array[i];
			var array = cook.split("=");
			if(array[0] == key){
				val = array[1];
			}
		}
		return val;
	}
</script>
<title>京东商城</title>
</head>
<body>

	<div class="top">
		<div class="top_text">
			<c:if test="${empty user}">
				<a href="" style="color: red;">免费注册</a>
				<a href="goto_login.do"><span id="show_name" style="color: red">${yh_nch}</span>&nbsp你好，请登录&nbsp</a>
			</c:if>
			
			<c:if test="${not empty user}">
				<a href="goto_logout.do">退出</a>
				<a href="">用户订单</a>
				<a href="" style="color: red;">用户名称：${user.yh_nch}</a>
			</c:if>
		</div>
	</div>
	
</body>
</html>