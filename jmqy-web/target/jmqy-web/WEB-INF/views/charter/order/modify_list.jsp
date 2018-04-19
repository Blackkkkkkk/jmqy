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
				<div class="position">您所在的位置：首页 -> 个人中心 -> 修改订单</div>
			</div>
			<div class="info">
				<form id="searchForm" action="${ctx}/charter/order/modifyList" method="post">
                    <input type="hidden" name="pageNum" value="${order.pageNum}"/>
                    <input type="hidden" name="pageSize" value="${order.pageSize}"/>
					<div class="button_left">
						<label>出发日期：</label>
						<input class="layui-input search_text input_date" id="beginTime" name="beginTime" type="text" value="${order.beginTime}"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})"/> 
						<span>—</span> 
						<input class="layui-input search_text input_date" id="endTime" name="endTime" value="${order.endTime}" type="text"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginTime\')}'})"/>
					</div>
					<div class="button_right john-edge">
                    <div class="layui-inline">
                       <input type="text" name="orderCodeSe" value="${order.orderCodeSe}" class="layui-input" placeholder="请输订单号">
                    </div>
                    <div class="button_right">
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
									订单号：<span>${ord.orderCode}</span>
							      </th>
							      
							    </tr> 
							  </thead>
							  <tbody>
							    <tr>
							      <td>${ord.startPoint} — ${ord.endPoint}</td>
							      <td><fmt:formatDate value="${ord.boardingTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
							      	<a href="javascript:query('${ord.orderCode}',1);" class="layui-btn layui-btn-primary layui-btn-mini">详情</a>
							      	<c:if test="${ord.status != -1 && ord.status != 0 && ord.status !=1 && ord.status != 2 && ord.status !=3 && ord.recordStatus == 0}">
							      		<a href="${ctx}/charter/order/pushOrder?orderCode=${ord.orderCode}" class="layui-btn layui-btn-normal layui-btn-mini">修改</a>
							      	</c:if>
							      	<c:if test="${ord.status == -1 && ord.recordStatus == 0}">
							      	 <a href="${ctx}/charter/order/detailRelease?orderCode=${ord.orderCode}" class="layui-btn layui-btn-normal layui-btn-mini">付款</a>
							      	</c:if>
							      	<c:if test="${(ord.status == -3 || ord.status == -2 || ord.status == -1) && ord.recordStatus == 0}">
							      		<a href="javascript:orderCharterCancel(2,'${ctx}','${ord.orderCode}');" class="layui-btn layui-btn-danger layui-btn-mini">取消</a>
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
		function query(orderCode,type){
			var width ="900px";
			var height = "250px;"
			var title ="订单详情";
			if(type ==1){
				height = "250px;"
				title ="订单详情";
			}else if(type ==3){
				height = "560px;"
				title ="订单投诉";
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
	</script>
</body>
</html>