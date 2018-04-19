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
<%-- 	${keywords} --%>

	<hr>
	<c:forEach items="${list_sku}" var="sku">
		<div style="margin-top:10px; margin-left:10px; float:left; border: 1px red solid; width:200px; height:200px">
			<center>
				<img style="margin-left:10px; with:110px;height:110px" alt="" src="upload/image/${sku.shp_tp}"><br>
				<a href="goto_sku_detail.do?sku_id=${sku.id}&spu_id=${sku.shp_id}" target="_blank">${sku.sku_mch}</a><br>
				<div style="color: red">价格：${sku.jg}</div>
				销量：${sku.sku_xl}<br>
			</center>
		</div>
	</c:forEach>
	
</body>
</html>