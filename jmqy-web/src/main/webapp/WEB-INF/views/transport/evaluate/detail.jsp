<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>评价详情</title>
</head>
<body class="window">
	<table class="layui-table">
		<colgroup>
		      <col width="110">
		      <col width="200">
		      <col width="110">
		      <col width="200">
		      <col width="110">
		      <col width="200">
		      <col>
	    </colgroup>
		<tr>
		  <td>订单编号：</td>
		  <td>${evaluate.orderCode}</td>
		  <td>客户名称：</td>
		  <td>${evaluate.charterName}</td>
		  <td>评价时间：</td>
		  <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${evaluate.createTime}" /></td>
		</tr>
		<tr>
		  <td>司机名称：</td>
		  <td>${evaluate.driverName}</td>
		  <td>司机工号：</td>
		  <td>${evaluate.jobNum}</td>
		  <td>车牌号码：</td>
		  <td>${evaluate.carNum}</td>  
	    </tr>
		<tr>
			<td>评价内容：</td>
			<td colspan="5" rowspan="4">
				对司机：${evaluate.toDriverContent}
				<br>
				对车辆：${evaluate.toCarContent}
			</td>
		</tr>
	</table>
</body>
</html>