<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>班车延误</title>
</head>
<body class="window">
<div class="layui-form" style="margin-top: 10px;">
	<form id="form" class="layui-form" action="${ctx}/transport/order/doDelay" method="post" enctype="multipart/form-data">
		<input type="hidden" id="orderCode" name="orderCode" value="${order.orderCode}"/>
		<div class="layui-form-item" style="width: 500px;">
			<label class="layui-form-label">预计到达时间:</label>
			<div class="layui-input-block">
				<input class="layui-input search_text input_date" lay-verify="required" id="predictTime" name="predictTime" type="text"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d %H:{%m-5}:%s'})"
				value="<fmt:formatDate type='date' pattern='yyyy-MM-dd HH:mm:ss' value='${order.boardingTime}'/>"/>
			</div>
		</div>
		<div class="layui-form-item" style="width: 500px;">
			<label class="layui-form-label">班车延误原因:</label>
			<div class="layui-input-block">
				<textarea id="delayReason" lay-verify="required" placeholder="延误原因" class="layui-textarea"></textarea>
			</div>
		</div>
		<button class="layui-btn layui-btn-danger" style="float:right;display: none;" lay-submit lay-filter="submitBtn" id="submitBtn">发布</button>
	</form>
</div>
<script type="text/javascript">
//文档加载完毕执行
$(function() {
	var message = '${message}';
	if(message != null && "" != message){
		var msg = message.split("@");
		setTimeout(function(){
			layer.msg(msg[0], {icon: msg[1],time: 1000}, function(){
				parent.closeAll();
			});
		}, 100);
	}
});
//提交表单
function laySubmit(){
	$('#submitBtn').click();
}
//加载表单
layui.use(['form','element','laydate'], function() {
	
	var laydate = layui.laydate;
 
    var form = layui.form;
    
  	//监听提交
    form.on('submit(submitBtn)', function(data){
      //layer.msg(JSON.stringify(data.field));
      return true;
    });
  	
    //自定义验证规则
    form.verify({
    });
});
</script>
</body>
</html>