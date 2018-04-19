<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>订单评价</title>
	<style type="text/css">
		.driver >span {
		    color: #eeb829;
		    cursor: pointer;
		    display: inline-block;
		    font-size: 25px;
		    position: relative;
		    width: 25px;
		}
		.car >span {
		    color: #eeb829;
		    cursor: pointer;
		    display: inline-block;
		    font-size: 25px;
		    position: relative;
		    width: 25px;
		}
	</style>
</head>
<body class="window">
	<form id="evaluateForm" class="layui-form" action="" method="post" enctype="multipart/form-data">
	<input type="hidden" name="orderCode" value="${order.orderCode}"/>
	<table class="layui-table" style="height: 450px; margin: 0px; margin-top: 25px;">
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
		  <td><fmt:formatDate value="${order.boardingTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		  <td>返程时间：</td>
		  <td><fmt:formatDate value="${order.trackTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
				<c:choose>
			  		<c:when test="${order.additional ==0}">餐费、住宿费、高速路费、保险、水</c:when>
			  		<c:when test="${order.additional ==1}">餐费</c:when>
			  		<c:when test="${order.additional ==2}">住宿费</c:when>
			  		<c:when test="${order.additional ==3}">高速路费</c:when>
			  		<c:when test="${order.additional ==4}">保险</c:when>
			  		<c:when test="${order.additional ==5}">水</c:when>
			  	</c:choose>
			</td>
		</tr>
		<tr>
			<td colspan="8">
				<div class="layui-tab-item layui-show">
                    <input type="hidden" name="toDriverScore" id="toDriverScore" lay-verify="required"/>
                    <div style="margin: 10px 0; float: left; margin-right: 10px; font-size: 18px;">司机:</div>
					<div class="driver rating" style="padding-right: 50px;">
						<span>☆</span><span>☆</span><span>☆</span><span>☆</span><span>☆</span>
					</div>
					<c:if test="${empty evaluate}">
					<div class="layui-input-block" style="top: 7px;margin-left: 170px;">
						<a href="javascript:addPjToSj('司机开车技术好，准时！');" class="layui-btn layui-btn-primary layui-btn-mini">司机开车技术好，准时！</a>
						<a href="javascript:addPjToSj('司机脾气差，喜欢讲粗口！');" class="layui-btn layui-btn-primary layui-btn-mini">司机脾气差，喜欢讲粗口！</a>
						<a href="javascript:addPjToSj('司机迟到，不准时！');" class="layui-btn layui-btn-primary layui-btn-mini">司机迟到，不准时！</a>
			 		</div>
			 		</c:if>
					<textarea <c:if test="${not empty evaluate}">readonly</c:if> placeholder="请输入内容" class="layui-textarea" 
					lay-verify="required" name="toDriverContent" maxlength="500">${evaluate.toDriverContent}</textarea>
				</div>
				<br/>
				<div class="layui-tab-item layui-show">
                    <input type="hidden" name="toCarScore" id="toCarScore" lay-verify="required"/>
                    <div style="margin: 10px 0; float: left; margin-right: 10px; font-size: 18px;">车辆:</div>
					<div class="car rating" style="padding-right: 50px;">
						<span>☆</span><span>☆</span><span>☆</span><span>☆</span><span>☆</span>
					</div>
					<c:if test="${empty evaluate}">
					<div class="layui-input-block" style="top: 7px;margin-left: 170px;">
						<a href="javascript:addPjToCl('车辆很新，美观！');" class="layui-btn layui-btn-primary layui-btn-mini">车辆很新，美观！</a>
						<a href="javascript:addPjToCl('车辆陈旧，有异味！');" class="layui-btn layui-btn-primary layui-btn-mini">车辆陈旧，有异味！</a>
						<a href="javascript:addPjToCl('车辆有故障，行驶不正常！');" class="layui-btn layui-btn-primary layui-btn-mini">车辆有故障，行驶不正常！</a>
			 		</div>
			 		</c:if>
					<textarea <c:if test="${not empty evaluate}">readonly</c:if> placeholder="请输入内容" class="layui-textarea"
					 lay-verify="required" name="toCarContent" maxlength="500">${evaluate.toCarContent}</textarea>
				</div>
			</td>
		</tr>
		<c:if test="${empty evaluate}">
        <tr>
            <td colspan="8" style="text-align: right; border: none;">
                <a href="javascript:evaluateSubmit();" class="layui-btn layui-btn-danger" lay-submit>提交</a>
                <a href="javascript:evaluateClose();" class="layui-btn layui-btn-primary">取消</a>
            </td>
        </tr>
       </c:if>
	</table>
	</form>
	<script src="http://libs.baidu.com/jquery/1.7.0/jquery.min.js"></script>
	<script type="text/javascript">
	
	function addPjToSj(content){
		$("[name='toDriverContent']").val($("[name='toDriverContent']").val()+content);
	}
	function addPjToCl(content){
		$("[name='toCarContent']").val($("[name='toCarContent']").val()+content);
	}
	//校验
    layui.use(['form'], function() {
        var form = layui.form;
        //监听提交
        form.on('submit(evaluateForm)', function(data){
            return false;
        });
    });
	function evaluateClose(){
		parent.layer.closeAll();
	}
	function evaluateSubmit() {
		var orderCode =$("[name='orderCode']").val();
        var toDriverScore =$("[name='toDriverScore']").val();
        var toDriverContent =$("[name='toDriverContent']").val();
        var toCarScore =$("[name='toCarScore']").val();
        var toCarContent =$("[name='toCarContent']").val();
        var dataJson = {"orderCode": orderCode, "toDriverScore": toDriverScore, "toDriverContent": toDriverContent,
        		"toCarScore": toCarScore,"toCarContent":toCarContent};
        var content = '确定提交吗？';
        layer.confirm(content, {icon: 3, title:'提示'}, function(index){
            $('.layui-layer-btn0').css('pointer-events','none');
            $.ajax({
                type:"post",
                url:"${ctx}/charter/evaluate/saveEvaluate",
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
     $(function () {
     if('${evaluate}' == null || '${evaluate}' == ''){
        $('.rating').mouseover(function(){
            $(this).mouseout(function(){
                var number = 5 - $(this).find('.star').length ;
                $(this).find('span').text('☆');
                $(this).each(function(){
                    $('span',this).text('★');
                    $('span:lt('+ number +')',this).text('☆');
                })

            })
            $(this).find('span').text('☆');
            $(this).find('span').click(function(){
                $(this).siblings().removeClass('star');
                $(this).nextAll('span').text('★').addClass('star');
                $(this).text('★').addClass('star');
                var number = $(this).parent().find('.star').length;
                $(this).parent().parent().find('input').val(number);
            })
        })
     }
        if('${evaluate}' != null){
        	var toDriverScore = '${evaluate.toDriverScore}';
        	var toCarScore = '${evaluate.toCarScore}';
             $('.driver').find('span').each(function(index){
            	 if(5-index <= toDriverScore){
            		 $(this).text('★');
            	 }else{
            		 $(this).text('☆');
            	 }
             })
             $('.car').find('span').each(function(index){
            	 if(5-index <= toCarScore){
            		 $(this).text('★');
            	 }else{
            		 $(this).text('☆');
            	 }
             })
        }
    })
	</script>
</body>
</html>