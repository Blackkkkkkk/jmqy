<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>提醒通知</title>
<style type="text/css">
	.button_left label{float: left;}
	.button_left span{float: left;}
</style>
</head>
<body>
	<section>
		<div class="container">
		<jsp:include page="/WEB-INF/layouts/left_menu.jsp"/>
			<main>
			<div class="position_all">
				<div class="position">您所在的位置：首页 -> 管理中心 -> 提醒通知</div>
			</div>
			<form id="searchForm" action="${ctx}/transport/remind/list" method="post">
            	<input type="hidden" name="pageNum" value="${remind.pageNum}"/>
              	<input type="hidden" name="pageSize" value="${remind.pageSize}"/>
			<div class="info info-padding0">
				<ol class="remid_ol">
				  <c:forEach items="${reminds.list}" var="rmd" varStatus="status">
					  <li id="${rmd.id}">
					    <h1>${status.index+1}、
						    <c:if test="${rmd.type == 1}">订单执行提醒</c:if> 
						    <c:if test="${rmd.type == 4}">订单投诉提醒</c:if>
							<c:if test="${rmd.type == 9}">订单支付提醒</c:if>
							<c:if test="${rmd.type == 10}">订单已支付</c:if>
							<c:if test="${rmd.type == 0}">司机已阅提醒</c:if><c:if test="${rmd.status == 0}"><a style="color: red;">[未读]</a></c:if> <c:if test="${rmd.status == 1}"><a>[已读]</a></c:if>
						    <span>展开</span>
						    <a style="color: #0C0C0C;text-decoration: none; float: right;"><fmt:formatDate value="${rmd.remindTime}" pattern="yyyy-MM-dd HH:mm:ss"/></a>
						 </h1>
						    <div class="remid_info">
							    <c:if test="${rmd.type == 1}">
							    	您有一条从【${rmd.startPoint}】到【${rmd.endPoint}】的订单【<a href="${ctx}/transport/order/list?orderCodeSe=${rmd.orderCode}" style="color: red;">${rmd.orderCode}</a>】还未执行，请及时安排车辆。
							    </c:if>
							    <c:if test="${rmd.type == 4}">
							    	您有一条从【${rmd.startPoint}】到【${rmd.endPoint}】的订单【<a href="${ctx}/transport/order/list?orderCodeSe=${rmd.orderCode}" style="color: red;">${rmd.orderCode}</a>】被客户投诉了。
							    </c:if>
								<c:if test="${rmd.type == 9}">
									您有一条从【${rmd.startPoint}】到【${rmd.endPoint}】的订单【<a href="${ctx}/transport/order/list?orderCodeSe=${rmd.orderCode}" style="color: red;">${rmd.orderCode}</a>】已支付。
								</c:if>
								<c:if test="${rmd.type == 10}">
									一条从【${rmd.startPoint}】到【${rmd.endPoint}】的订单【<a href="${ctx}/transport/order/list?orderCodeSe=${rmd.orderCode}" style="color: red;">${rmd.orderCode}</a>】司机已阅。
								</c:if>
						    </div>
					  </li>
				  </c:forEach>
				</ol>
				</div>
				</form>
            <tags:page pages="${reminds}"/>
			</main>
		</div>
	</section>
	<script type="text/javascript">
/******订单提醒*****/
$('.remid_ol li h1 span').click(function(){
	$(this).parent().next('.remid_info').toggle();
	if($(this).text() == '展开'){
		$(this).text('收起')
	}else{
		$(this).text('展开')
	}
	if($(this).prev('a').html() == '[未读]'){
		$(this).prev('a').html('[已读]');
		$(this).prev('a').css('color','');
		updateRemind('${ctx}/remind/update', {'id': $(this).parent().parent().attr('id'),'status':1});
	}
});
</script>
</body>
</html>