<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>订单投诉</title>
</head>
<body class="window">
	<form id="complaintForm" class="layui-form" action="" method="post" lay-filter="complaintForm">
	<input type="hidden" name="orderCode" value="${order.orderCode}"/>
	<table class="layui-table" style="height: 450px; margin-top: 50px;">
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
		  <td>途经点：</td>
		  <td colspan="3">${order.viaPoint}</td>
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
		  <td><fmt:formatDate value="${ord.boardingTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		  <td>返程时间：</td>
		  <td><fmt:formatDate value="${ord.trackBoardTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		  <td>车型：</td>
		  <td style="width: 30px;">${order.carType}</td>
		</tr>
		<tr>
			<td>包车天数：</td>
			<td>${order.charterDays }</td>
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

			</td>
		</tr>
		<c:if test="${not empty complaint.replyContent}">
		<tr>
			<td>运输方<br>投诉回复：</td>
			<td colspan="7">
				<span class="red">${complaint.replyContent}</span>
			</td>
		</tr>
		</c:if>
		<tr>
			<td>投诉：</td>
			<td colspan="7">
			  <textarea class="layui-textarea" placeholder="请输入投诉内容" name="toComplaintContent" maxlength="500" lay-verify="required"></textarea>
			</td>
		</tr>
        <tr>
            <td colspan="8" style="border: none; text-align: right;">
                <a href="javascript:complaintSubmit();" class="layui-btn layui-btn-danger" lay-submit>提交</a>
                <a href="javascript:complaintClose();" class="layui-btn layui-btn-primary">取消</a>
            </td>
        </tr>
	</table>
	</form>
	<script type="text/javascript">
	//校验
    layui.use(['form'], function() {
        var form = layui.form;
        //监听提交
        form.on('submit(complaintForm)', function(data){
            return false;
        });
    });
    function complaintClose(){
		parent.layer.closeAll();
	}
	function complaintSubmit() {
		var orderCode =$("[name='orderCode']").val();
        var toComplaintContent =$("[name='toComplaintContent']").val();
        var dataJson = {"orderCode": orderCode, "toComplaintContent": toComplaintContent};
        var content = '确定提交吗？';
        layer.confirm(content, {icon: 3, title:'提示'}, function(index){
            $('.layui-layer-btn0').css('pointer-events','none');
            $.ajax({
                type:"post",
                url:"${ctx}/charter/complaint/saveComplaint",
                dataType:"json",
                data:dataJson,
                success:function(data) {
                    if(data.state){
                        layer.open({
                            icon: 1,
                            title: '信息',
                            skin: 'layer-ext-myskin',
                            shade: 0, //不显示遮罩
                            content: "操作成功！",
                            yes: function(){
                            	parent.layer.closeAll();
                            	parent.location.reload();
                            },
                        });
                    }else{
                        layer.open({
                            icon: 2,
                            title: '信息',
                            skin: 'layer-ext-myskin',
                            shade: 0, //不显示遮罩
                            content: "操作失败！",
                        });
                    }
                }
            });
        });
    }
	</script>
</body>
</html>