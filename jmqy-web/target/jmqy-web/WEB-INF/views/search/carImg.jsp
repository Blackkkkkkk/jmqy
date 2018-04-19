<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
  <style>
  </style>
  <title></title>
</head>
<body >
<div class="layui-carousel" id="carImg">
	<div carousel-item>
		<c:if test="${not empty car.carPhoto}">
			<div onclick="openWin(this);"><img src="${ctx}${car.carPhoto}"></div>
		</c:if>
		<c:if test="${not empty car.carPhoto1}">
			<div onclick="openWin(this);"><img src="${ctx}${car.carPhoto1}"></div>
		</c:if>
		<c:if test="${not empty car.carPhoto2}">
			<div onclick="openWin(this);"><img src="${ctx}${car.carPhoto2}"></div>
		</c:if>
		<c:if test="${not empty car.carPhoto3}">
			<div onclick="openWin(this);"><img src="${ctx}${car.carPhoto3}"></div>
		</c:if>
	</div>
</div>
<script>
layui.use('carousel', function(){
	var carousel = layui.carousel;
	//建造实例
	carousel.render({
		elem: '#carImg'
		,width: '100%' //设置容器宽度   
		,height: '455px'
		,arrow: 'always' //始终显示箭头    
		//,anim: 'updown' //切换动画方式
	});
});

function openWin(obj){
	var img = $(obj).children();
	var winHeight = screen.availHeight;
	var winWidth = screen.availWidth;
	window.open(img.attr('src'),"_blank","left="+(winWidth-img.width())/2+",top="+(winHeight-img.height())/2+",toolbar=no,location=no,height="+img.height()+",width="+img.width());
}
</script>
</body>
</html>