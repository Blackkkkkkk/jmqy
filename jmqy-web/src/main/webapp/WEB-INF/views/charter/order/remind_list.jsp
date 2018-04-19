<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>个人中心</title>
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
				<div class="position">您所在的位置：首页 -> 个人中心 -> 提醒通知</div>
			</div>
			<form id="searchForm" action="${ctx}/charter/order/remindList" method="post">
            	<input type="hidden" name="pageNum" value="${rmd.pageNum}"/>
              	<input type="hidden" name="pageSize" value="${rmd.pageSize}"/>
			   <div class="info info-padding0">
				<ol class="remid_ol">
				  <c:forEach items="${reminds.list}" var="rmd" varStatus="status">
					  <li id="${rmd.id}">
					    <c:if test="${rmd.type == 0}">
						    <h1>${status.index+1}、订单未支付提醒
						    <c:if test="${rmd.status == 0}"><a style="color: red;">[未读]</a></c:if> <c:if test="${rmd.status == 1}"><a>[已读]</a></c:if>
						    <span>展开</span>
								<a style="color: #0C0C0C;text-decoration: none; float: right;"><fmt:formatDate value="${rmd.remindTime}" pattern="yyyy-MM-dd HH:mm:ss"/></a>
							</h1>
						    <div class="remid_info">
					    	您于<fmt:formatDate value="${rmd.placeTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/>发布的从${rmd.startPoint}到${rmd.endPoint}的订单
					    	【<a href="${ctx}/charter/order/list/-1?orderCodeSe=${rmd.orderCode}" style="color: red;">${rmd.orderCode}</a>】尚未付款, 请及时支付。
					    	</div>
					    </c:if>
					    <c:if test="${rmd.type == 2}">
						    <h1>${status.index+1}、订单提醒 
						    <c:if test="${rmd.status == 0}"><a style="color: red;">[未读]</a></c:if> <c:if test="${rmd.status == 1}"><a>[已读]</a></c:if>
						    <span>展开</span>
								<a style="color: #0C0C0C;text-decoration: none; float: right;"><fmt:formatDate value="${rmd.remindTime}" pattern="yyyy-MM-dd HH:mm:ss"/></a>
							</h1>
						    <div class="remid_info">
					    	您于<fmt:formatDate value="${rmd.placeTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/>发布的从${rmd.startPoint}到${rmd.endPoint}的订单已经已删除了。
					    	</div>
					    </c:if>
					    <c:if test="${rmd.type == 3}">
						    <h1>${status.index+1}、订单提醒 
						    <c:if test="${rmd.status == 0}"><a style="color: red;">[未读]</a></c:if> <c:if test="${rmd.status == 1}"><a>[已读]</a></c:if>
									<span>展开</span>
									<a style="color: #0C0C0C;text-decoration: none; float: right;"><fmt:formatDate value="${rmd.remindTime}" pattern="yyyy-MM-dd HH:mm:ss"/></a>
							</h1>
						    <div class="remid_info">
					    	您已有积分即将过期了。
					    	</div>
					    </c:if>
					    <c:if test="${rmd.type == 4}">
						    <h1>${status.index+1}、订单提醒
						    <c:if test="${rmd.status == 0}"><a style="color: red;">[未读]</a></c:if> <c:if test="${rmd.status == 1}"><a>[已读]</a></c:if>
						     <span>展开</span>
								<a style="color: #0C0C0C;text-decoration: none; float: right;"><fmt:formatDate value="${rmd.remindTime}" pattern="yyyy-MM-dd HH:mm:ss"/></a>
							</h1>
						    <div class="remid_info">
					    	您于<fmt:formatDate value="${rmd.placeTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/>发布的从${rmd.startPoint}到${rmd.endPoint}的订单已被投诉了。

					    	</div>
					    </c:if>
					    <c:if test="${rmd.type == 5}">
						    <h1>${status.index+1}、订单提醒 
						    <c:if test="${rmd.status == 0}"><a style="color: red;">[未读]</a></c:if> <c:if test="${rmd.status == 1}"><a>[已读]</a></c:if>
						    <span>展开</span>
								<a style="color: #0C0C0C;text-decoration: none; float: right;"><fmt:formatDate value="${rmd.remindTime}" pattern="yyyy-MM-dd HH:mm:ss"/></a>
							</h1>
						    <div class="remid_info">
					    	您于<fmt:formatDate value="${rmd.placeTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/>月的记账帐单需要付款了。

					    	</div>
					    </c:if>
					    <c:if test="${rmd.type == 6}">
						    <h1>${status.index+1}、订单被接受提醒
						    <c:if test="${rmd.status == 0}"><a style="color: red;">[未读]</a></c:if> <c:if test="${rmd.status == 1}"><a>[已读]</a></c:if>
						    <span>展开</span>
								<a style="color: #0C0C0C;text-decoration: none; float: right;"><fmt:formatDate value="${rmd.remindTime}" pattern="yyyy-MM-dd HH:mm:ss"/></a>
							</h1>
						    <div class="remid_info">
					    	您于<fmt:formatDate value="${rmd.placeTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/>发布的从【${rmd.startPoint}】
					    	<c:if test="${not empty rmd.endPoint}">
					    		到【${rmd.endPoint}】
					    	</c:if>
					    	<c:if test="${empty rmd.endPoint}">
					    		出发
					    	</c:if>
					    	的订单【<a href="${ctx}/charter/order/list/2?orderCodeSe=${rmd.orderCode}" style="color: red;">${rmd.orderCode}</a>】被运输方接受了！

					    	</div>
					    </c:if>
					    <c:if test="${rmd.type == 8}">
						    <h1>${status.index+1}、订单被拒绝提醒
						    <c:if test="${rmd.status == 0}"><a style="color: red;">[未读]</a></c:if> <c:if test="${rmd.status == 1}"><a>[已读]</a></c:if>
						    <span>展开</span>
								<a style="color: #0C0C0C;text-decoration: none; float: right;"><fmt:formatDate value="${rmd.remindTime}" pattern="yyyy-MM-dd HH:mm:ss"/></a>
							</h1>
						    <div class="remid_info">
					    	您于<fmt:formatDate value="${rmd.placeTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/>发布的从【${rmd.startPoint}】
					    	<c:if test="${not empty rmd.endPoint}">
					    		到【${rmd.endPoint}】
					    	</c:if>
					    	<c:if test="${empty rmd.endPoint}">
					    		出发
					    	</c:if>
					    	的订单【<a href="${ctx}/charter/order/list/-3?orderCodeSe=${rmd.orderCode}" style="color: red;">${rmd.orderCode}</a>】被运输方拒绝了！

					    	</div>
					    </c:if>
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

$(document).ready(function () {

})

$('.remid_ol li h1 span').click(function(){
    $('#time').removeClass('')
	$(this).parent().next('.remid_info').toggle();
    if($(this).text()=='展开'||$(this).text()=='收起') {
        if ($(this).text() == '展开') {
            $(this).text('收起')
        } else {
            $(this).text('展开')
        }
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