<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>结束订单</title>
</head>
<body class="window">
<form id="finish" action="${ctx}/transport/order/finish" class="layui-form" method="post">
<input type="hidden" name="orderCode" value="${order.orderCode}"/>
	<div class="torm1" style="margin: 10px;">
	
		<div class="layui-form-item">
			<label class="layui-form-label">实际上车时间</label>
			<div class="layui-input-inline">
				<input name="actualUpTime" id="actualUpTime" lay-verify="title" autocomplete="off" class="layui-input" type="text"
				value="<fmt:formatDate type='date' pattern='yyyy-MM-dd HH:mm:ss' value='${order.boardingTime}'/>"/>
			</div>
			<label class="layui-form-label">实际下车时间</label>
			<div class="layui-input-inline">
				<input name="actualOffTime" id="actualOffTime" lay-verify="title" autocomplete="off" id="test5" class="layui-input" type="text"
				value="<fmt:formatDate type='date' pattern='yyyy-MM-dd HH:mm:ss' value='${order.boardingTime}'/>"/>
			</div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label">公里数</label>
			<div class="layui-input-inline">
				<input name="distance" lay-verify="title" autocomplete="off"  class="layui-input" type="text" disabled
				value='<fmt:formatNumber type="number" value="${order.distance/1000}" maxFractionDigits="2"/>'
				/>
			</div>
			<label class="layui-form-label">消费总金额</label>
			<div class="layui-input-inline">
				<input name="amount" lay-verify="title" value="${order.amount}" autocomplete="off" class="layui-input" type="text" disabled/>
			</div>
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
	$('#finish').submit();
}

//加载表单
layui.use(['form','element','laydate'], function() {
	
	var laydate = layui.laydate;
	//执行laydate实例
	laydate.render({
		elem: '#actualUpTime' //指定元素
		,type: 'datetime'
		,theme: '#0071bf'
	});
	//日期时间选择器
	laydate.render({
		elem: '#actualOffTime'
		,type: 'datetime'
		,theme: '#0071bf'
	});
 
    var form = layui.form;
    
  	//监听提交
    form.on('submit(formDemo)', function(data){
      //layer.msg(JSON.stringify(data.field));
      return true;
    });
  	
    //自定义验证规则
    form.verify({
        //只能输入数字
        integer:function (value,item) {
            if(!/^[0-9]*$/.exec(value)){
                return "只能输入数字";
            }
        }

    });
});
</script>
</body>
</html>