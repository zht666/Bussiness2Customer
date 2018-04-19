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
<link rel="stylesheet" type="text/css" href="css/css.css">
<link rel="stylesheet" href="css/style.css">
<style type="text/css">
body a{
	font-weight: 400;
 	color: #999999; 
/* 	display: inline-block; */
/* 	height: 22px; */
/* 	line-height: 22px; */
/* 	border: 1px solid #DDD; */
/* 	font-size: 12px; */
/* 	vertical-align: top; */
/* 	margin: 0 5px 5px 0; */
/* 	padding: 0 26px 0 4px; */
/* 	cursor: pointer; */
	text-decoration:none  /* 去掉超链接的下划线 */
}
body a:hover{/* 悬停变色 */
	color:red;
}
</style>
<script type="text/javascript">
	$(function (){
// 		var li = ${gotoLi};
// 		alert(li);
	});
</script>
<title>京东商城</title>
</head>
<body>

	<jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="searchArea.jsp"></jsp:include>
	<jsp:include page="attrList.jsp"/>
	<hr>
	
	<div id="skuListInner">
		<!-- 用分类编号2检索 -->
		<c:if test="${gotoLi==1}">
			<jsp:include page="skuList.jsp"/>
		</c:if>
		<!-- 用关键词检索 -->
		<c:if test="${gotoLi==2}">
			<jsp:include page="search.jsp"/>
		</c:if>
	
	</div>
</body>
</html>