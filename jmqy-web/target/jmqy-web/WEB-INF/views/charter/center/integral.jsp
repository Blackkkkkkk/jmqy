<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>个人中心</title>
</head>
<body>
	<section>
		<div class="container">
		<jsp:include page="/WEB-INF/layouts/left_menu.jsp"/>
			<main>
			<div class="position_all">
				<div class="position">您所在的位置：首页 -> 个人中心 -> 积分查询</div>
			</div>
			<div class="info">
				<div class="searh searh1 integral">
					<p><h1>用户信息</h1></p>
					<p>欢迎您！<shiro:principal property="name"/>,</p>
					<c:choose>
						
						<c:when test="${empty score}">
							<p>您是普通包车主用户，暂时无积分，谢谢！</p>
						</c:when>
						<c:when test="${not empty score}">
							<p>以下是您的积分情况汇报，可进行<a href="exchange.html">兑换</a>！</p>
							<div class="cl"></div>
							<ul>
							<li><span class="orange"><b>
								<c:if test="${empty score.totalScore}">
									0
								</c:if>
								<c:if test="${not empty score.totalScore}">
									${score.totalScore}
								</c:if>
							</b></span><br/><img src="${ctx}/static/images/icon1.png" alt=""><br/>可用积分</li>
							<li><span class="orange"><b>
								<c:if test="${empty score.consumeScore}">
									0
								</c:if>
								<c:if test="${not empty score.consumeScore}">
									${score.consumeScore}
								</c:if>
							</b></span><br/><img src="${ctx}/static/images/icon3.png" alt=""><br/>已用积分</li>
							<li><span class="orange"><b>
								<c:if test="${empty score.expireScore}">
									0
								</c:if>
								<c:if test="${not empty score.expireScore}">
									${score.expireScore}
								</c:if>
							</b></span><br/><img src="${ctx}/static/images/icon2.png" alt=""><br/>即将过期积分</li>
							<li><span class="orange"><b>0</b></span><br/><img src="${ctx}/static/images/icon4.png" alt=""><br/>优惠卷</li>
							</ul>
						</c:when>
					</c:choose>
			    </div>
			</div>
			</main>
		</div>
	</section>
</body>
</html>