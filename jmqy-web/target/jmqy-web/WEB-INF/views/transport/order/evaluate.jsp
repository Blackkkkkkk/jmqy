<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>评价订单</title>
</head>
<body class="window">
<form id="evaluate" action="${ctx}/transport/order/evaluate" class="layui-form" method="post">
<input type="hidden" name="orderCode" value="${order.orderCode}"/>
	<div class="torm2" style="margin: 10px;display: block;">
		<label class="layui-form-label"></label>
 		<div class="layui-input-block">
			<a href="javascript:addPj('乘客人很好，很讲卫生！');" class="layui-btn layui-btn-primary layui-btn-mini">乘客人很好，很讲卫生！</a>
			<a href="javascript:addPj('乘客脾气差，喜欢讲粗口！');" class="layui-btn layui-btn-primary layui-btn-mini">乘客脾气差，喜欢讲粗口！</a>
			<a href="javascript:addPj('乘客爱迟到，不准时！');" class="layui-btn layui-btn-primary layui-btn-mini">乘客爱迟到，不准时！</a>
 		</div>

		<label class="layui-form-label">评语</label>
 		<div class="layui-input-block">
			<textarea id="content" class="layui-textarea" id="" cols="30" rows="10"></textarea>
		</div>
	</div> 
</form>
<script type="text/javascript">
//文档加载完毕执行
$(function() {
	var message = '${message}';
	if (message != null && "" != message) {
		var msg = message.split("@");
		setTimeout(function() {
			layer.msg(msg[0], {
				icon : msg[1]
			});
		}, 500);
	}
});
//提交表单
function laySubmit() {
	$('#evaluate').submit();
}

function addPj(content){
	$('#content').val($('#content').val()+content);
}
</script>
</body>
</html>