<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>接收订单</title>
</head>
<body class="window">
<div class="layui-form" style="margin: 10px;">
<form id="accept" class="layui-form" action="${ctx}/transport/order/accept" method="post">
<input type="hidden" name="id" value="${order.id}"/>
<input type="hidden" name="orderCode" value="${order.orderCode}"/>
  <table class="layui-table" id="table">
     <thead>
      <tr>
        <th>订单号</th>
        <th>起点</th>
        <th>终点</th>
        <th>上车时间</th>
        <th>车辆</th>
        <th>司机</th>
        <th>副班司机</th>
      </tr> 
    </thead>
    <tbody>
      <tr>
        <td>${order.orderCode}</td>
        <td>${order.startPoint}</td>
       	<td>${order.endPoint}</td>
        <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${order.boardingTime}" /></td>
        <td>
	      	<select id="carCode" name="carCode" lay-search="" lay-filter="carFilter">
				<option value="">选择车辆</option>
				<c:forEach items="${cars}" var="c" varStatus="status" >${c.carCode},${c.carNum}
					<option value="${c.carCode},${c.carNum},${c.driverCode}" <c:if test="${c.carCode == order.carCode}">selected = "selected"</c:if>>${c.carNum}(近一年接单数:${c.counts})</option>
				</c:forEach>
			</select>
     	 </td>
     	 <td>
	      	<select id="driverCode" name="driverCode" lay-search=""  lay-filter="driverCode">
				<option value="1">选择司机</option>
				<c:forEach items="${drivers}" var="d" varStatus="status" >
					<option value="${d.code}" <c:if test="${d.code == order.driverCode}">selected = "selected"</c:if>>${d.name}</option>
				</c:forEach>
			</select>
     	</td>
      	<td id="td">
			<select id="viceDriverCode" name="viceDriverCode" lay-search="" lay-filter="viceDriverCode">
				<option value="2">选择副班司机</option>
				<c:forEach items="${drivers}" var="d" varStatus="status" >
					<c:if test="${d.code != order.driverCode}">
					<option value="${d.code}" <c:if test="${d.code == order.viceDriverCode}">selected = "selected"</c:if>>${d.name}</option>
					</c:if>
				</c:forEach>
			</select>
     	</td>
      </tr>
    </tbody>
  </table>
  </form>
</div>
<script type="text/javascript">
    var layer;
    layui.use('layer', function(){
         layer = layui.layer;
         var distance = ${order.distance}/1000;

        if(distance>300){
            parent.layer.msg("该订单号里程数超过了300公里，请记得分配副司机选项！")
        }
    });


  /*  $(document).ready(function () {

	    var distance = ${order.distance}/1000;

		if(distance>300){
            layer.msg("该订单号里程数超过了300公里，请记得分配副司机选项！")
        }
    })*/
    layui.use('form', function(){
        var form = layui.form;
        form.on('select(driverCode)', function(data){
			//console.log(data.value); //得到被选中的值
            //清空副班司机的选项
            $("table tr:eq(1) td:eq(6)>div>dl").empty()
            //清空副班司机select的选项
			$("#viceDriverCode").empty();

         //   $("table tr:eq(1) td:eq(6)>div>dl").append('<dd lay-value="" class="layui-this">选择副班司机</dd>')
            $("#viceDriverCode").append("<option value=''>选择副司机</option>");
            var obj=document.getElementById('viceDriverCode');
            var driverobj = document.getElementById('driverCode');

            var array = new Array(); //定义数组
            $("#driverCode option").each(function() { //遍历全部option

                if (data.value == $(this).val() || $(this).val()=='1') {
                } else{
                    var txt = $(this).text(); //获取option的内容
                    var val = $(this).val();
                    var htm='<dd lay-value="'+val+'"  class="">'+txt+'</dd>'

                    $("#viceDriverCode").append("<option value='" + val + "'>" + txt + "</option>");
                    $("table tr:eq(1) td:eq(6)>div>dl").append(htm)
				}
            });

            form.render('select'); //刷新select选择框渲染
        });
    });

//文档加载完毕执行
$(function() {


  //  alert("1="+$("#driverCode").val())


	var message = '${message}';
	if(message != null && "" != message){
		var msg = message.split("@");
		setTimeout(function(){
			layer.msg(msg[0], {icon: msg[1]});
		}, 500);
	}
});
//提交表单
function laySubmit(){
	$('#accept').submit();
}

//加载表单
layui.use(['form','element','laydate'], function() {
	
    var form = layui.form;
    form.on('select(carFilter)', function(data){
		var driverCode = data.value.split(',')[2];
		$("#driverCode").val(driverCode);   //设置Select的Text值为jQuery的项选中
		$("#driverCode option[value='"+driverCode+"']").attr("selected", true);   //设置Select的Text值为jQuery的项选中
	   	form.render('select'); //刷新select选择框渲染
   	}); 
    
});
</script>
</body>
</html>