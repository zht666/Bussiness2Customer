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
	function b(){}
</script>
<title>京东商城</title>
</head>
<body>
	属性列表内嵌页<br><!-- 遍历双重属性集合 -->
	<!-- 遍历属性名列表，得到属性名 -->
	<c:forEach items="${list_attr}" var="attr">
	    ${attr.shxm_mch}: &nbsp
	    <!-- 遍历属性值列表，得到每个属性名对应的属性值和属性值名称 -->
		<c:forEach items="${attr.list_value}" var="val">
			${val.shxzh}${val.shxzh_mch}&nbsp
		</c:forEach>
		<br>
	</c:forEach>
	
</body>
</html>