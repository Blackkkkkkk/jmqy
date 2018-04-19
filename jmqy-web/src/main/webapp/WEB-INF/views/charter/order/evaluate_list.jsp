<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>个人中心</title>
<style type="text/css">
	.button_left label{float: left;}
	.button_left span{float: left;}
</style>
</head>
<body>
	<section>
		<div class="container">
		<jsp:include page="/WEB-INF/layouts/left_menu.jsp"/>
			<main>
			<div class="position_all">
				<div class="position">您所在的位置：首页 -> 个人中心 -> 评价打分</div>
			</div>
			<div class="info">
				<form id="searchForm" action="${ctx}/charter/order/evaluateList" method="post">
                    <input type="hidden" name="pageNum" value="${order.pageNum}"/>
                    <input type="hidden" name="pageSize" value="${order.pageSize}"/>
					<div class="button_left">
					</div>
					<div class="button_right">
						<div class="layui-inline">
							<label class="layui-form-label">出发日期：</label>
							<div class="layui-input-inline" style="top: 5px;">
								<input class="layui-input search_text input_date" id="beginTime" name="beginTime" type="text" 
								value='<fmt:formatDate value="${order.beginTime}" pattern="yyyy-MM-dd"/>'
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})"/>_
							</div>
							<div class="layui-input-inline" style="top: 5px;">
								<input class="layui-input search_text input_date" id="endTime" name="endTime" type="text"
								value='<fmt:formatDate value="${order.endTime}" pattern="yyyy-MM-dd"/>'
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginTime\')}'})"/>
							</div>
						</div>
						<div class="layui-inline">
							<a class="layui-btn layui-btn-danger layui-btn-small" href="#" onclick="submitCharterForm();">搜索</a>
						</div>
					</div>
					<div class="cl"></div>
					<div class="layui-form">
                        <c:forEach items="${orders.list}" var="ord">
                            <table class="layui-table" lay-even="" lay-skin="nob" style="border: 1px solid #CCC;">
							<colgroup>
								<col width="250">
							    <col width="250">
							    <col width="100">
							    <col width="100">
								<col width="200"/>
							</colgroup>
                            	<thead>
							    <tr>
							      <th colspan="5">
									订单号：<a href="javascript:query('${ord.orderCode}',1);" style="color: orange;">${ord.orderCode}</a>
									<c:if test="${ord.recordStatus == 0 }">
										<a href="javascript:orderCharterDel('${ctx}','${ord.orderCode}');">删除订单</a>
									</c:if>
									<c:if test="${ord.recordStatus == 1 }">
										<a href="#" style="color: red;">已删除</a>
									</c:if>
							      </th>
							      
							    </tr> 
							  </thead>
							  <tbody>
							    <tr>
							      <td>${ord.startPoint} — ${ord.endPoint}</td>
							      <td><fmt:formatDate value="${ord.placeTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							      <td><span class="orange"><b>
								      <c:choose>
								      	<c:when test="${ord.amount != null}">￥${ord.amount}</c:when>
								      	<c:when test="${ord.amount == null}">￥0</c:when>
								      </c:choose>
								      </b></span>
							      </td>
							      <td>
								      <c:choose>
								      	<c:when test="${ord.status == -4}">已取消</c:when>
								      	<c:when test="${ord.status == -3}">被拒绝</c:when>
								      	<c:when test="${ord.status == -2}">待匹配</c:when>
								      	<c:when test="${ord.status == -1}">匹配中</c:when>
								      	<c:when test="${ord.status == 0}">待接受</c:when>
								      	<c:when test="${ord.status == 1}">等待上车</c:when>
								      	<c:when test="${ord.status == 2}">在途</c:when>
								      	<c:when test="${ord.status == 3}">已完成</c:when>
								      </c:choose>
							      </td>
							      <td>
							      	<c:if test="${ord.recordStatus == 0}">
							      		<a href="javascript:query('${ord.orderCode}',1);" class="layui-btn layui-btn-primary layui-btn-mini">订单详情</a>
							     	</c:if>
							     	<c:if test="${ord.recordStatus == 0 && ord.evaluateId == null}">
							     		<a href="javascript:query('${ord.orderCode}',2);"  class="layui-btn layui-btn-normal layui-btn-mini">评价</a>
							     	</c:if>
							     	<c:if test="${ord.recordStatus == 0 && ord.evaluateId != null}">
							     		<a href="javascript:query('${ord.orderCode}',2);"  class="layui-btn layui-btn-normal layui-btn-mini">查看评价</a>
							     	</c:if>
							      </td>
							    </tr>
							    </tbody>
							</table>    
                       </c:forEach>
					</div>
				</form>
			</div>
            <tags:page pages="${orders}"/>
			</main>
		</div>
	</section>
	<script type="text/javascript">
		//文档加载完毕执行
		$(function() {
			var message = '${message}';
			if(message != null && "" != message){
				var msg = message.split("@");
				setTimeout(function(){
					layer.alert(msg[0], {icon: msg[1]});
				}, 500);
			}
		});
		function submitForm(){
			var beginTime = $("#test1").val();
			var endTime = $("#test2").val();
			if((beginTime == null || '' == beginTime) && (endTime != null && '' != endTime)){
	            layer.tips('开始日期不能为空！', '#beginTime', {tips: [3, '#78BA32']});
	            return false;
	        }
			$("#searchForm").submit();
		}
		function query(orderCode,type){
			var width ="900px";
			var height = "250px;"
			var title ="订单详情";
			if(type ==1){
				height = "250px;"
				title ="订单详情";
			}else if(type ==2){
				height = "600px;"
				title ="订单评价";
			}
			  layer.open({
			    type: 2,
			    title: title,
			    shadeClose: true,
			    shade: 0.5,
			    yes: function(index, layero){
			    	 var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
			    	 iframeWin.laySubmit();//执行iframe页的方法：
			    },
			    area: [width,height],
			    content: '${ctx}/charter/order/query?orderCode='+orderCode+'&type='+type
			  })
		}
		//查询
		function submitCharterForm(){
			var beginTime = $("#test1").val();
			var endTime = $("#test2").val();
			if((beginTime == null || '' == beginTime) && (endTime != null && '' != endTime)){
			    layer.tips('开始日期不能为空！', '#beginTime', {tips: [3, '#78BA32']});
			    return false;
			}
			$("#searchForm").submit();
		}
	</script>
</body>
</html>