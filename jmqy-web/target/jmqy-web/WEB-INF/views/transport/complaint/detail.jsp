<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>投诉详情</title>
</head>
<body class="window">
<div class="layui-form" style="margin: 10px;">
	<table class="layui-table">
		<colgroup>
			<col width="150">
			<col width="150">
			<col width="150">
			<col width="200">
			<col width="200">
			<col width="200">
			<col width="200">
			<col>
		</colgroup>
		<tr>
			<td>客户：</td>
			<td>${complaint.charterCompany}</td>
			<td>订单号：</td>
			<td>${complaint.orderCode}</td>
			<td>司机：</td>
			<td>${complaint.driverName}</td>
			<td>司机工号：</td>
			<td>${complaint.jobNum}</td>
		</tr>
		<tr>
			<td>车辆：</td>
			<td>${complaint.orderCode}</td>
			<td>时间：</td>
			<td colspan="5"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${complaint.createTime}" /></td>
		</tr>
		<tr>
			<td>投诉内容：</td>
			<td colspan="7">
				<span class="red">${complaint.toComplaintContent}</span>
			</td>
		</tr>
		<tr>
			<td>回复：</td>
			<td colspan="7">
				<textarea id="replyContent" placeholder="内容" class="layui-textarea">${complaint.replyContent}</textarea>
			</td>
		</tr>
	</table>
</div>
</body>
</html>