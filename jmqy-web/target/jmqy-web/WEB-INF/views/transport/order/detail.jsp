<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>订单详情</title>
</head>
<body class="window">
<div class="layui-form" style="margin: 10px;">
	<table class="layui-table">
		 <colgroup>
		      <col width="110">
		      <col width="100">
		      <col width="110">
		      <col width="180">
		      <col width="110">
		      <col width="180">
		      <col width="80">
		      <col>
	    </colgroup>
		<tr>
		  <td>起始点：</td>
		  <td>${order.startPoint}</td>
		  <td>目的地：</td>
		  <td>${order.endPoint}</td>
		  <%-- <td>途经点：</td>
		  <td colspan="3">${order.viaPointStr}</td> --%>
		  <td>途经点：</td>
		  <td colspan="2">${order.viaPointStr}</td>
		</tr>
		<tr>
			<td>预估路程：</td>
			<td><fmt:formatNumber type="number" value="${order.distance/1000.0}" pattern="0.00" maxFractionDigits="2"/>(公里)</td>
			<td>预估时长：</td>
		  	<td><fmt:formatNumber type="number" value="${order.duration/60.0}" pattern="0.00" maxFractionDigits="2"/>(分钟)</td>
			<td>金额：</td>
			<td colspan="3">
				${order.amount}
			</td>
		</tr>
		<c:if test="${order.status > -1}">
			<tr>
			<td>车辆：</td>
			<td>${order.carNum}</td>
			<td>车型：</td>
		  	<td>${order.carType}</td>
			<td>司机：</td>
			<td colspan="3">
				正班:${order.driverName}(${order.driverPhone})
				<c:if test="${not empty order.viceDriverName}"><br>副班:${order.viceDriverName}(${order.viceDriverPhone})</c:if>
			</td>
		</tr>
		</c:if>
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
		  <td>下车时间：</td>
		  <td><fmt:formatDate value="${order.debusTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td> 
		</tr>
		<tr>
			<td>返程上车时间：</td>
			<td><fmt:formatDate value="${order.trackBoardTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>返程下车时间：</td>
			<td><fmt:formatDate value="${order.trackDebusTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td>包车人数：</td>
			<td>${order.population }</td>
			<td>包车类型：</td>
			<td>
				<c:choose>
			  		<c:when test="${order.tripType ==1}">旅游包车</c:when>
			  		<c:when test="${order.tripType ==2}">企业包车</c:when>
			  		<c:when test="${order.tripType ==3}">私人包车</c:when>
			  	</c:choose>
			</td>
			<td>附加选项：</td>
			<td colspan="3">
				${order.additional}
<%-- 				<c:choose> --%>
<%-- 			  		<c:when test="${order.additional ==0}">餐费、住宿费、高速路费、保险、水</c:when> --%>
<%-- 			  		<c:when test="${order.additional ==1}">餐费</c:when> --%>
<%-- 			  		<c:when test="${order.additional ==2}">住宿费</c:when> --%>
<%-- 			  		<c:when test="${order.additional ==3}">高速路费</c:when> --%>
<%-- 			  		<c:when test="${order.additional ==4}">保险</c:when> --%>
<%-- 			  		<c:when test="${order.additional ==5}">水</c:when> --%>
<%-- 			  	</c:choose> --%>
			</td>
		</tr>
		<c:if test="${order.status > 0}">
			<tr>
			  	<td>联系人：</td>
			  	<td colspan="1">${order.linkMan}</td>
			 	<td>联系人电话：</td>
			 	<td colspan="1">${order.linkPhone}</td>
				<td>企业名称：</td>
				<td colspan="2">${order.charterCompanyName}</td>
			</tr>
		</c:if>
	</table>
	</div>
</body>
</html>