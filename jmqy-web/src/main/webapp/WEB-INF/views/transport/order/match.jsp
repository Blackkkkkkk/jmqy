<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>未接订单</title>
</head>
<body>
<section>
	<div class="container">
	<jsp:include page="/WEB-INF/layouts/left_menu.jsp"/>
		<main>
		<div class="position_all">
			<div class="position">您所在的位置：首页 -> 管理中心 -> 匹配订单</div>
		</div>
		<div class="info">
			<form id="searchForm" class="layui-form" action="${ctx}/transport/order/match" method="post">
                <input type="hidden" name="pageNum" value="${order.pageNum}"/>
                <input type="hidden" name="pageSize" value="${order.pageSize}"/>
                <input type="hidden" id="order" name="order" value="${order.order}"/>
				<input type="hidden" id="sortType" name="sortType" value="${order.sortType}"/>
				<div class="button_right john-edge">
					<div class="layui-inline">
						<label class="layui-form-label">上车时间:</label>
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
						<select name="tripType" id="tripType">
							<option value="">包车行程</option>
							<option value="1" <c:if test="${order.tripType == 1}">selected = "selected"</c:if>>单程</option>
							<option value="2" <c:if test="${order.tripType == 2}">selected = "selected"</c:if>>往返</option>
							<option value="3" <c:if test="${order.tripType == 3}">selected = "selected"</c:if>>返程</option>
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
								<th><a id="order_code" onclick="sort1(this);" style="cursor: pointer;">订单号</a></th>
							    <th><a id="start_point" onclick="sort1(this);" style="cursor: pointer;">上车点</a></th>
							    <th><a id="end_point" onclick="sort1(this);" style="cursor: pointer;">下车点</a></th>
							    <th><a id="boarding_time" onclick="sort1(this);" style="cursor: pointer;">上车时间</a></th>
							    <th><a id="trip_type" onclick="sort1(this);" style="cursor: pointer;">包车行程</a></th>
							    <th><a id="population" onclick="sort1(this);" style="cursor: pointer;">包车座数</a></th>
							    <!-- <th>操作</th> -->
							</tr>
						</thead>
						<tbody>
                        <c:forEach items="${orders.list}" var="order" varStatus="status">
                            <tr>
                                 <td><a href="javascript:detail('${order.orderCode}');" style="color: orange;">${order.orderCode}</a></td>
                                <td>${order.startPoint}</td>
                                <td>${order.endPoint}</td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${order.boardingTime}" /></td>
                                <td>
								  	<c:choose>
								  		<c:when test="${order.tripType ==1}">单程</c:when>
								  		<c:when test="${order.tripType ==2}">往返</c:when>
								  		<c:when test="${order.tripType ==3}">返程</c:when>
								  	</c:choose>
								 </td>
                                <td>${order.carTypes}</td>
								<%-- <td>
									<a href="javascript:detail('${order.orderCode}');" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-file-alt"></i>详情</a>
								</td> --%>
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
<!-- 拒绝接收订单的理由填写的弹出窗 start-->
<div id="refuseDiv" style="display: none;padding: 10px;">
<table class="layui-table" >
	<colgroup>
		<col width="100">
		<col width="400">
		<col>
	</colgroup>
	<tr>
		<td>回复：</td>
		<td colspan="6">
		<textarea placeholder="内容" class="layui-textarea" id="refuseReason"></textarea>
		</td>
	</tr>
	<tr>
		<td colspan="6" align="center">
			<button class="layui-btn layui-btn-normal" onclick="saveRefuse();">提交</button>
			<button class="layui-btn layui-btn-primary" onclick="layer.closeAll();">取消</button>
		</td>
	</tr>
</table>
</div>
<!-- 接收订单的弹出窗口 end-->
<script type="text/javascript">

    function sort1(boj) {


        var order = $("#order").val();
        if(order == "DESC"){
            order = "ASC";
        }else{
            order = "DESC";
        }
        $("#order").val(order);
        if(boj.id == 'order_code'){
            $("#sortType").val('T.order_code');
        }else{
            $("#sortType").val(boj.id);
		}
        $("#searchForm").submit();
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
var orderCode;
//提交拒绝理由
function saveRefuse(){
	var refuseReason = $('#refuseReason').val();
	if(refuseReason == ''){
		layer.msg('拒绝理由不能为空', {icon: 2,timeout:500});
		$('#refuseReason').focus();
		return;
	}
	$.ajax({
        type: "POST",
        url: "${ctx}/transport/order/refuse",
        data: {'orderCode': orderCode, 'refuseReason': refuseReason},
        dataType: "json",
        success: function(data){
	       	 if(data==-1){
	       		 layer.msg('系统错误', {icon: 2,timeout:1000});
	       	 }
	       	 if(data==0){
	       		 layer.msg('提交成功', {icon: 1,timeout:500});
	       		setTimeout(function(){
	       			selfSubmit();
       			}, 500);
	        }
        }
    });
}

//弹出拒绝理由填写
function refuse(code){
	orderCode = code;
	layer.open({
	  type: 1,
	  title: '拒绝的理由',
	  shadeClose: true,
	  shade: 0.5,
	  area: ['640px','260px'],
	  content: $('#refuseDiv')
	})
}

//接收订单的窗口
function accept(id, code, tripType){
	layer.open({
	  type: 2,
	  title: '接收',
	  shadeClose: true,
	  shade: 0.5,
	  btn: ['提交', '取消'],
	  yes: function(index, layero){
		//var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
		var body = layer.getChildFrame('body', index);
		var carCode = body.find('#carCode').val();
		var carNum = body.find('#carCode').find("option:selected").text();
		if(carCode == ''){
			layer.msg('车辆不能为空', {icon: 2,timeout:500});
			body.find('#carCode').focus();
			return;
		}
		var driverCode = body.find('#driverCode').val();
		if(driverCode == ''){
			layer.msg('司机不能为空', {icon: 2,timeout:500});
			body.find('#driverCode').focus();
			return;
		}
		$.ajax({
	        type: "POST",
	        url: "${ctx}/transport/order/accept",
	        data: {'orderCode': code, 'carCode': carCode, 'carNum': carNum, 'driverCode': driverCode},
	        dataType: "json",
	        success: function(data){
		       	 if(data==-1){
		       		 layer.msg('系统错误', {icon: 2,timeout:1000});
		       	 }
		       	 if(data==0){
		       		 if(tripType == 1){
		       			createBackOrder(id);
		       		 }else{
		       			layer.msg('提交成功', {icon: 1,timeout:500});
			       		setTimeout(function(){
			       			selfSubmit();
		       			}, 500);
		       		 }
		        }
	        }
	    });
	  },
	  btn2: function(index, layero){
	    //按钮【按钮二】的回调
	  },
	  area: ['1050px','400px'],
	  content: '${ctx}/transport/order/accept?orderCode='+code
	})
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

function createBackOrder(id){
	layer.confirm('提交成功,是否生成一条回程单？', {icon: 3, title:'提示'}, function(index){
		window.location.href = '${ctx}/transport/backOrder/form?id='+id;
	    layer.close(index);
	    //向服务端发送操作指令
	}, function(){
		location.reload(true);
	});
}
</script>
</body>
</html>