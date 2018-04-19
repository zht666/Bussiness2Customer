<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"  %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";%>
<% String ran = System.currentTimeMillis()+""; %><!-- 用时间戳随机生成的订单号 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath %>">
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	$(function (){
		$("#a").submit();//提交form表单
	});
</script>
<title>京东商城</title>
</head>
<body>
	<form id="a" action="http://localhost:58080/payservice/payment"  method="POST" target="_blank">
		<input type="hidden" name="trade_no" id="out_trade_no" value="京东商城订单<%=ran%>">
		<input type="hidden" name="total_fee" value="0.01">
		<!-- 订单和支付服务之间重定向和通知接口 busi_return_url  busi_notify_url -->
		<input type="hidden" name="busi_return_url" value="http://localhost:48080/order_test/order_success.do">
		<input type="hidden" name="busi_notify_url" value="http://localhost:18080/tmall_sale/real_pay_success.do"><!-- 调通知接口 -->
		<input type="hidden" name="subject" value="京东支付测试专用">
		<input type="hidden" name="body" value="即时到账测试">
	</form>
	
</body>
</html>