<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>车辆任务表</title>
</head>
<body>
<section>
	<div class="container">
	<jsp:include page="/WEB-INF/layouts/left_menu.jsp"/>
		<main>
		<div class="position_all">
			<div class="position">您所在的位置：首页 -> 管理中心 -> 车辆任务表</div>
		</div>
		<div class="info">
			<form id="searchForm" class="layui-form" action="${ctx}/transport/car/task" method="post">
                <input type="hidden" name="pageNum" value="${order.pageNum}"/>
                <input type="hidden" name="pageSize" value="${order.pageSize}"/>
                <input type="hidden" id="order" name="order" value="${order.order}"/>
				<input type="hidden" id="sortType" name="sortType" value="${order.sortType}"/>
				<div class="button_right john-edge">
					<div class="layui-inline">
						<label class="layui-form-label">任务日期:</label>
						<div class="layui-input-inline" style="top: 5px;">
							<input class="layui-input search_text input_date" id="taskTime" name="taskTime" type="text"
							value="<fmt:formatDate type='date' pattern='yyyy-MM-dd' value='${order.taskTime}'/>"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						</div>
					</div>
					<div class="layui-inline">
						<select name="status" id="status">
							<option value="">任务状态</option>
							<option value="1" <c:if test="${order.status == 1}">selected = "selected"</c:if>>等待上车</option>
							<option value="2" <c:if test="${order.status == 2}">selected = "selected"</c:if>>在途</option>
						</select>
					</div>
					<div class="layui-inline">
						<%-- <input type="text" name="orderCodeSe" value="${order.orderCodeSe}" class="layui-input" placeholder="请输订单号"> --%>
						<a class="layui-btn layui-btn-danger layui-btn-small" href="javascript:selfSubmit();">搜索</a>
					</div>
				</div>
				<div class="cl"></div>
				<div class="layui-form">
					<table class="layui-table">
						<thead>
							<tr>
								<th><a id="t.car_num" onclick="sort(this);" style="cursor: pointer;">车辆</a></th>
								<th><a id="order_code" onclick="sort(this);" style="cursor: pointer;">订单号</a></th>
								<th><a id="boarding_time" onclick="sort(this);" style="cursor: pointer;">开始时间</a></th>
								<th><a id="reserve_off_time" onclick="sort(this);" style="cursor: pointer;">结束时间</a></th>
								<th><a id="t.status" onclick="sort(this);" style="cursor: pointer;">状态</a></th>
								<th><a id="t.tripType" onclick="sort(this);" style="cursor: pointer;">单双程</a></th>
							</tr>
						</thead>
						<tbody>
                        <c:forEach items="${orders.list}" var="order" varStatus="status">
                            <tr>
                            	<td>${order.carNum}</td>
                                <td><a href="javascript:detail('${order.orderCode}');" style="color: orange;">${order.orderCode}</a></td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${order.boardingTime}" /></td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${order.debusTime}" /></td>
                                <td>
                                	<c:if test="${order.status == -4}">已取消</c:if>
									<c:if test="${order.status == -3}">已拒绝</c:if>
                                	<c:if test="${order.status == -2}">待匹配</c:if>
									<c:if test="${order.status == -1}">匹配中</c:if>
									<c:if test="${order.status == 0}">待接受</c:if>
                                	<c:if test="${order.status == 1}">待上车</c:if>
									<c:if test="${order.status == 2}">在途</c:if>
									<c:if test="${order.status == 3}">完成</c:if>
                                </td>
								<td>
									<c:if test="${order.tripType == 1}">单程</c:if>
									<c:if test="${order.tripType == 2}">双程</c:if>
										</td>
							</tr>
                        </c:forEach>
						</tbody>
					</table>
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
			layer.msg(msg[0], {icon: msg[1]});
		}, 500);
	}
});
//提交搜索表单
function selfSubmit() {
	$("#searchForm").submit();
}

//跳到第一页
function selfSearch() {
	$("[name='pageNum']").val(1);//第一页
	selfSubmit();
}

//查看订单详情
function detail(orderCode){
	var width ="900px";
	var height = "310px;"
	var title ="订单详情";
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
	  content: '${ctx}/transport/order/detail?orderCode='+orderCode
	})
}
</script>
</body>
</html>