<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>订单详情</title>
</head>
<body class="window">
	<table class="layui-table">
		 <colgroup>
		      <col width="110">
		      <col width="100">
		      <col width="110">
		      <col width="180">
		      <col width="110">
		      <col width="180">
		      <col>
	    </colgroup>
		<tr>
		  <td>起始点：</td>
		  <td>${order.startPoint}</td>
		  <td>目的地：</td>
		  <td>${order.endPoint}</td>
		  <td>途经点：</td>
		  <td colspan="2">${order.viaPointStr}</td>
		</tr>
		<tr>
			<td>预估路程：</td>
			<td><fmt:formatNumber type="number" value="${order.distance/1000.0}" pattern="0.00" maxFractionDigits="2"/>(公里)</td>
			<td>预估时长：</td>
		  	<td><fmt:formatNumber type="number" value="${order.duration/60.0}" pattern="0.00" maxFractionDigits="2"/>(分钟)</td>
			<td>金额：</td>
			<td>
				${order.amount}
			</td>
		</tr>
		<tr>
		  <td>包车行程：</td>
		  <td>
		  	<c:choose>
		  		<c:when test="${order.tripType ==1}">单程</c:when>
		  		<c:when test="${order.tripType ==2}">往返</c:when>
		  		<c:when test="${order.tripType ==3}">返程</c:when>
		  	</c:choose>
		  </td>
		  <td>上车时间：</td>
		  <td><fmt:formatDate value="${order.boardingTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		  <td>返程上车时间：</td>
		  <td><fmt:formatDate value="${order.trackBoardTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td>车型：</td>
		  	<td>${order.carType}</td>
			<td>包车天数：</td>
			<td>${order.charterDays}</td>
			<td>包车类型：</td>
			<td>
				<c:choose>
			  		<c:when test="${order.tripType ==1}">旅游包车</c:when>
			  		<c:when test="${order.tripType ==2}">企业包车</c:when>
			  		<c:when test="${order.tripType ==3}">私人包车</c:when>
			  	</c:choose>
			</td>
		</tr>
		<tr>
		  	<td>附加选项：</td>
		  	<td colspan="6">${order.additional}</td>
		</tr>
		<c:if test="${order.status >= 0}">
		<c:if test="${order.status == 2 || order.status == 5 || order.status == -4 ||order.status == 3 ||order.status == 4 ||order.status == 7 ||order.status == 6 }">
			<tr>
			  	<td><c:if test="${order.status == 2 || order.status == 5}">拟安排</c:if>车辆：</td>
			  	<td colspan="2">${order.carNum}</td>
			 	<td><c:if test="${order.status == 2 || order.status == 5}">拟安排</c:if>司机：</td>
			 	<td colspan="2">${order.driverName}: ${order.driverPhone}</td>
			</tr>
		</c:if>
		</c:if>
		<c:if test="${order.status == -3}">
			<tr>
			  	<td>被运输拒绝理由：</td>
			  	<td colspan="6">${order.refuseReason}</td>
			</tr>
		</c:if>
	</table>
</body>
</html>