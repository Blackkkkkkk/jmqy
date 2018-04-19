<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title><c:if test="${backOrder.type==1}">空程车</c:if><c:if test="${backOrder.type==2}">单程车</c:if></title>
</head>
<body>
<section>
	<div class="container">
	<jsp:include page="/WEB-INF/layouts/left_menu.jsp"/>
		<main>
		<div class="position_all">
			<div class="position">您所在的位置：首页 -> 管理中心 -> <c:if test="${backOrder.type==1}">空程车</c:if><c:if test="${backOrder.type==2}">单程车</c:if></div>
		</div>
		<div class="info">
			<form id="searchForm" action="${ctx}/transport/backOrder/list/${backOrder.type}" method="post">
                <input type="hidden" name="pageNum" value="${backOrder.pageNum}"/>
                <input type="hidden" name="pageSize" value="${backOrder.pageSize}"/>
                <input type="hidden" name="type" value="${backOrder.type}"/>
                <input type="hidden" id="order" name="order" value="${backOrder.order}"/>
				<input type="hidden" id="sortType" name="sortType" value="${backOrder.sortType}"/>
				<div class="button_left">
					<c:if test="${backOrder.type==2}">
						<a href="javascript:update('');" class="layui-btn layui-btn-primary layui-btn-mini car_a"><i class="icon-plus-sign"></i> 新增</a>
					</c:if>
				</div>
				<div class="button_right">
					<div class="layui-inline">
						<label class="layui-form-label">出发时间:</label>
						<div class="layui-input-inline" style="top: 5px;">
							<input class="layui-input search_text input_date" id="beginTime" name="beginTime" type="text" value='${backOrder.beginTime}'
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>_
						</div>
						<div class="layui-input-inline" style="top: 5px;">
							<input class="layui-input search_text input_date" id="endTime" name="endTime" type="text" value='${backOrder.endTime}'
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						</div>
					</div>
					<div class="layui-inline">
						<input type="text" name="orderCodeSe" value="${backOrder.orderCodeSe}" class="layui-input" placeholder="请输订单号">
						<a class="layui-btn layui-btn-danger layui-btn-small" href="javascript:selfSubmit();">搜索</a>
					</div>
				</div>
				<div class="cl"></div>
				<div class="layui-form">
					<table class="layui-table">
						<colgroup>
							<col width="150">
							<col width="150">
							<col width="150">
							<col width="150">
							<col width="150">
							<col width="100">
							<col width="100">
							<col width="100">
							<col>
						</colgroup>
						<thead>
							<tr>
								<th><a id="order_code" onclick="sort(this);" style="cursor: pointer;">订单号</a></th>
						        <th><a id="start_point" onclick="sort(this);" style="cursor: pointer;">上车点</a></th>
						        <th><a id="end_point" onclick="sort(this);" style="cursor: pointer;">下车点</a></th>
						        <th><a id="earliest_departure_time" onclick="sort(this);" style="cursor: pointer;">最早出发时间</a></th>
						        <th><a id="latest_departure_time" onclick="sort(this);" style="cursor: pointer;">最晚出发时间</a></th>
						        <th><a id="b.seat_number" onclick="sort(this);" style="cursor: pointer;">客座数</a></th>
						        <th><a id="discount_prices" onclick="sort(this);" style="cursor: pointer;">优惠价</a></th>
						        <th><a id="status" onclick="sort(this);" style="cursor: pointer;">状态</a></th>
						        <th>操作</th>
							</tr>
						</thead>
						<tbody>
                           <c:forEach items="${backOrders.list}" var="backOrder" varStatus="status">
                               <tr>
								   <c:if test="${backOrder.type==1}">
									   <td><a href="javascript:query('${backOrder.orderCode}',1);" style="color: orange;">${backOrder.orderCode}</a></td>
								   </c:if>
								   <c:if test="${backOrder.type==2}">
									   <td><a href="javascript:query('${backOrder.orderCode}',2);" style="color: orange;">${backOrder.orderCode}</a></td>
								   </c:if>
                                   <td>${backOrder.startPoint}</td>
                                   <td>${backOrder.endPoint}</td>
                                   <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${backOrder.earliestDepartureTime}" /></td>
                                   <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${backOrder.latestDepartureTime}" /></td>
                                   <td>${backOrder.seatNumber}</td>
                                   <td>${backOrder.discountPrices}</td>
                                   <td>
                                   		<c:if test="${backOrder.status==-1}">已取消</c:if>
                                   		<c:if test="${backOrder.status==0}">待匹配</c:if>
                                   		<c:if test="${backOrder.status==1}">已匹配</c:if>
                                   		<c:if test="${backOrder.status==2}">已完成</c:if>
                                   </td>
                                   <td style="white-space: nowrap;">
									   <c:if test="${backOrder.status !=1}">
	                                   	<a href="javascript:update('${backOrder.orderCode}');" class="layui-btn layui-btn-primary layui-btn-mini caradmin_a"><i class="icon-edit"></i> 修改</a>
	                                   	<a href="javascript:updateById('${backOrder.id}',1);" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-remove"></i> 删除</a>
									   </c:if>
                                   </td>
                               </tr>
                           </c:forEach>
						</tbody>
					</table>
				</div>
			</form>
		</div>
        <tags:page pages="${backOrders}"/>
		</main>
	</div>
</section>
<script type="text/javascript">

//查看订单详情
    function query(orderCode,type){
        var width ="900px";
        var height = "350px;"
        var title ="订单详情";
        if(type ==1){
            height = "350px;"
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
            content: '${ctx}/transport/backOrder/query?orderCode='+orderCode+'&type='+type
        })
    }


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

/*****、新增、修改/行程*****/
function update(orderCode){
	window.location.href = '${ctx}/transport/backOrder/form'+(orderCode!=''?('?orderCode='+orderCode):'');
}

/*
* status:
* 0在职，1休假，2离职
* recordStatus:
* 0正常，1删除，
*/
function updateById(id, recordStatus){
	var typeStr = '删除';
    layer.confirm('真的'+typeStr+'这个数据么?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type: "POST",
            url: "${ctx}/transport/backOrder/updateById",
            data: {'id': id, 'recordStatus': recordStatus},
            dataType: "json",
            success: function(data){
            	var msg = '';
            	var icon = 1;
            	if(data < 0){
            		msg = '失败，系统错误！';
            		icon = 2;//失败
            	}else{
            		if(data == 0){
            			msg = '失败';
            		}else{
            			msg = '成功';
            		}
            	}
                layer.msg(typeStr+msg,{
                    time: 1000,
                    skin: 'layui-layer-molv',
                    area: '20px',
                    icon: icon
                }, function(){
                	selfSubmit();
                });
            }
        });
        layer.close(index);
        //向服务端发送操作指令
    });

}
</script>
</body>
</html>