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
<link rel="stylesheet" type="text/css" href="css/css.css">
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/css.css">
<link rel="stylesheet" href="css/style.css">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <link href="" rel="stylesheet">
<script type="text/javascript">
	function cart_submit(){
		
		$("#cart_form").submit();
	}
</script>
<title>京东商城</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="searchArea.jsp"></jsp:include>
	<div class="Dbox">
		<div class="box">
			<div class="left">
				<div style="margin-top: 15px" class="timg"><img src="images/1509704778747a.gif" alt=""></div>
				<div class="timg2">
					<div class="lf"><img src="images/lf.jpg" alt=""></div>
					<div class="center">
						<span><img src="images/icon_2.jpg" alt=""></span>
						<span><img src="images/icon_3.jpg" alt=""></span>
						<span><img src="images/icon_2.jpg" alt=""></span>
						<span><img src="images/icon_3.jpg" alt=""></span>
						<span><img src="images/icon_2.jpg" alt=""></span>
					</div>
					<div class="rg"><img src="images/rg.jpg" alt=""></div>
				</div>
				<div class="goods"><img src="images/img_6.jpg" alt=""></div>
			</div>
			<div class="cent" style="margin-left: 200px;">
				<div class="title">${obj_sku.sku_mch}</div>
				<div class="bg">
					<p>市场价：<strong>￥${obj_sku.jg}</strong></p>
					<p>促销价：<strong>￥${obj_sku.jg}</strong></p>
				</div>
				<div class="clear">
					<div class="min_t">选择版本：</div>
						<c:forEach items="${list_sku}" var="sku">
							<div class="min_mx"><a href="goto_sku_detail.do?sku_id=${sku.id}&spu_id=${sku.shp_id}" >${sku.sku_mch}</a></div>
						</c:forEach>
				</div>
				<div class="clear">
					<div class="min_t" >服务：</div>
					<div class="min_mx" >服务1号1</div>
					<div class="min_mx" >服务二号1112</div>
					<div class="min_mx" >55英服务二号1111寸活动中3</div>
					<div class="min_mx" >4</div>
					<div class="min_mx" >呃呃呃5</div>
					<div class="min_mx" >55英寸活动中6</div>
				</div>
				<div class="clear" style="margin-top:20px;">
					<div class="min_t" style="line-height:36px">数量：</div>
					<div class="num_box">
						<input type="text" name="num" value="1" style="width:40px;height:32px;text-align:center;float:left">
						<div class="rg">
							<img src="images/jia.jpg" id='jia' alt="">
							<img src="images/jian.jpg" id='jian' alt="">
						</div>
					</div>
				</div>
				<div class="clear" style="margin-top:20px;">
						<form  id="cart_form" action="add_cart.do" method="post">
							<input type="hidden" name="sku_mch" value="${obj_sku.sku_mch}" />
							<input type="hidden" name="sku_jg" value="${obj_sku.jg}" />
							<input type="hidden" name="tjshl" value="1" />
							<input type="hidden" name="hj" value="${obj_sku.jg}" />
							<input type="hidden" name="shp_id" value="${obj_sku.shp_id}" />
							<input type="hidden" name="sku_id" value="${obj_sku.id}" />
							<input type="hidden" name="shp_tp" value="${obj_sku.spu.shp_tp}" />
							<input type="hidden" name="shfxz" value="1" />
							<input type="hidden" name="kcdz" value="${obj_sku.kcdz}" />
							<c:if test="${not empty user}">
								<input type="hidden" name="yh_id" value="${user.id}" />
							</c:if>
							<img src="images/shop.jpg" onclick="cart_submit()" alt="" style="cursor:pointer;">
						</form>		
					
				</div>
			</div>
		</div>
	</div>
	<div class="Dbox1">
		<div class="boxbottom">
			<div class="top">
				<span>商品详情</span>


				<span>评价</span>
			</div>
			<div class="btm">
				${obj_sku.spu.shp_msh}<br>
				<center>
				<c:forEach items="${obj_sku.list_image}" var="image">
					<img style="width: 200px" src="upload/image/${image.url}" height="200px"/>
				</c:forEach>
				</center><br>		
				<c:forEach items="${obj_sku.list_av_name}" var="av_name">
					${av_name.shxm_mch}:${av_name.shxzh_mch}<br>
				</c:forEach>
			</div>
		</div>
	</div>
	
	<div class="Dbox1">
		<div class="boxbottom">
			<div class="top">
				<span>商品详情</span>
				<span>评价</span>
			</div>
			<div class="btm">
				学习使我快乐(*^▽^*)！
			</div>
		</div>
	</div>
	<div class="footer">
		<div class="top"></div>
		<div class="bottom"><img src="images/foot.jpg" alt=""></div>
	</div>
	

<script>
!function(){function n(n,e,t){return n.getAttribute(e)||t}function e(n){return document.getElementsByTagName(n)}function t(){var t=e("script"),o=t.length,i=t[o-1];return{l:o,z:n(i,"zIndex",-1),o:n(i,"opacity",.5),c:n(i,"color","0,0,0"),n:n(i,"count",99)}}function o(){a=m.width=window.innerWidth||document.documentElement.clientWidth||document.body.clientWidth,c=m.height=window.innerHeight||document.documentElement.clientHeight||document.body.clientHeight}function i(){r.clearRect(0,0,a,c);var n,e,t,o,m,l;s.forEach(function(i,x){for(i.x+=i.xa,i.y+=i.ya,i.xa*=i.x>a||i.x<0?-1:1,i.ya*=i.y>c||i.y<0?-1:1,r.fillRect(i.x-.5,i.y-.5,1,1),e=x+1;e<u.length;e++)n=u[e],null!==n.x&&null!==n.y&&(o=i.x-n.x,m=i.y-n.y,l=o*o+m*m,l<n.max&&(n===y&&l>=n.max/2&&(i.x-=.03*o,i.y-=.03*m),t=(n.max-l)/n.max,r.beginPath(),r.lineWidth=t/2,r.strokeStyle="rgba("+d.c+","+(t+.2)+")",r.moveTo(i.x,i.y),r.lineTo(n.x,n.y),r.stroke()))}),x(i)}var a,c,u,m=document.createElement("canvas"),d=t(),l="c_n"+d.l,r=m.getContext("2d"),x=window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||window.oRequestAnimationFrame||window.msRequestAnimationFrame||function(n){window.setTimeout(n,1e3/45)},w=Math.random,y={x:null,y:null,max:2e4};m.id=l,m.style.cssText="position:fixed;top:0;left:0;z-index:"+d.z+";opacity:"+d.o,e("body")[0].appendChild(m),o(),window.onresize=o,window.onmousemove=function(n){n=n||window.event,y.x=n.clientX,y.y=n.clientY},window.onmouseout=function(){y.x=null,y.y=null};for(var s=[],f=0;d.n>f;f++){var h=w()*a,g=w()*c,v=2*w()-1,p=2*w()-1;s.push({x:h,y:g,xa:v,ya:p,max:6e3})}u=s.concat([y]),setTimeout(function(){i()},100)}();
</script>

</body>
</html>