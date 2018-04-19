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
			<div class="position">您所在的位置：首页 -> 个人中心 -> 历史订单</div>
		</div>
		<div class="info">
			<form id="searchForm" class="layui-form" action="${ctx}/charter/order/historyList" method="post">
                <input type="hidden" name="pageNum" value="${order.pageNum}"/>
                <input type="hidden" name="pageSize" value="${order.pageSize}"/>
				<div class="button_left">
					<label>出发日期：</label><input class="layui-input search_text input_date" id="test1" name="beginTime" type="text" value="${order.beginTime}"/> <span>—</span> <input class="layui-input search_text input_date" id="test2" name="endTime" value="${order.endTime}" type="text"/>
				</div>
                <div class="button_right john-edge">
                    <div class="layui-inline">
                        <select name="status" id="status">
                            <option value="">订单状态</option>
                            <option value="-4" <c:if test="${order.status == -4}">selected = "selected"</c:if>>已取消</option>
                            <option value="-3" <c:if test="${order.status == -3}">selected = "selected"</c:if>>被拒绝</option>
                            <option value="-2" <c:if test="${order.status == -2}">selected = "selected"</c:if>>待匹配</option>
                            <option value="-1" <c:if test="${order.status == -1}">selected = "selected"</c:if>>匹配中</option>
                            <option value="0" <c:if test="${order.status == 0}">selected = "selected"</c:if>>待接受</option>
                            <option value="1" <c:if test="${order.status == 1}">selected = "selected"</c:if>>等待上车</option>
                            <option value="2" <c:if test="${order.status == 2}">selected = "selected"</c:if>>在途</option>
                            <option value="3" <c:if test="${order.status == 3}">selected = "selected"</c:if>>完成</option>
                        </select>
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
								<c:if test="${ord.recordStatus == 0 }">
									<a href="javascript:orderCharterDel('${ctx}','${ord.orderCode}');">删除订单</a>
								</c:if>
								<c:if test="${ord.recordStatus == 1 }">
									<a href="#" style="color: red;">已删除</a>
								</c:if>
								<a style="color: orange;" href="javascript:collectLine('${ord.orderCode}');">收藏线路</a>
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
							      <a href="javascript:query('${ord.orderCode}',1);" class="layui-btn layui-btn-primary layui-btn-mini">详情</a>
							      <c:if test="${ord.recordStatus == 0 && ord.complaintId == null}">  	
							        <a href="javascript:query('${ord.orderCode}',3);" class="layui-btn layui-btn-danger layui-btn-mini">投诉</a>
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
//收藏线路
function collectLine(code){
	$.ajax({
        type: "POST",
        url: "${ctx}/line/collect",
        data: {'orderCode': code},
        dataType: "json",
        success: function(data){
	       	 if(data==-1){
	       		 layer.msg('系统错误', {icon: 2,timeout:1000});
	       	 }
	       	 if(data==0){
	       		 layer.msg('收藏成功', {icon: 1,timeout:500});
	        }
        }
    });
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