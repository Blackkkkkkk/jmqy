<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>订单管理</title>
	<style>
		.button_right1{
			float:;
			margin-right: 10px;
		}
	</style>
</head>
<body>
<section>
	<div class="container">
	<jsp:include page="/WEB-INF/layouts/left_menu.jsp"/>
		<main>
		<div class="position_all">
			<div class="position">您所在的位置：首页 -> 管理中心 -> 订单管理</div>
		</div>
		<div class="info">
			<form id="searchForm" class="layui-form" action="${ctx}/transport/order/list" method="post">
				<input type="hidden" name="pageNum"  id="pageNum" value="${order.pageNum}"/>
				<input type="hidden" name="pageSize" id="pageSize" value="${order.pageSize}"/>
				<input type="hidden" id="order" name="order" value="${order.order}"/>
				<input type="hidden" id="sortType" name="sortType" value="${order.sortType}"/>


				<input type="hidden" id="orderCodeSe" name="orderCodeSe" value="${order.orderCodeSe}"/>
				<input type="hidden" id="searchPlace" name="searchPlace" value="${order.searchPlace}"/>
				<input type="hidden" id="carType" name="carType" value="${order.carType}"/>
				<input type="hidden" id="driverName" name="driverName" value="${order.driverName}"/>
				<input type="hidden" id="linkMan" name="linkMan" value="${order.linkMan}"/>
				<input type="hidden" id="searchType" name="searchType" value="0">
				<div class="button_right1 john-edge" >

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
						<select name="status" id="status">
							<option value="">订单状态</option>
							<option value="1" <c:if test="${order.status == 1}">selected = "selected"</c:if>>等待上车</option>
							<option value="2" <c:if test="${order.status == 2}">selected = "selected"</c:if>>在途</option>
							<option value="3" <c:if test="${order.status == 3}">selected = "selected"</c:if>>完成</option>
						</select>
					</div>


					<div class="layui-inline">
						<%-- <input type="text" name="orderCodeSe" value="${order.orderCodeSe}" class="layui-input" placeholder="请输订单号"> --%>
						<a class="layui-btn layui-btn-danger layui-btn-small" href="javascript:selfSubmit();">搜索</a>
					</div>
					<div class="cl">
					</div>
					<label class="layui-form-label">查询内容选项:</label>
					<div class="layui-inline">
						<select name="dim" id="dim" lay-filter="dim">
							<option value="0" <c:if test="${order.orderCodeSe != null and order.orderCodeSe !=''}">selected="selected"</c:if>>订单号</option>
							<option value="1" <c:if test="${order.searchPlace != null and order.searchPlace !=''}">selected="selected"</c:if>>地点</option>
							<option value="2" <c:if test="${order.carType != null and order.carType !=''}">selected="selected"</c:if>>车辆</option>
							<option value="3" <c:if test="${order.driverName != null and order.driverName !=''}">selected="selected"</c:if>>司机</option>
							<option value="4" <c:if test="${order.linkMan != null and order.linkMan !=''}">selected="selected"</c:if>>购买人</option>
						</select>
					</div>
                    <div class="layui-inline">
						<input class="search_text" id="dimValue" name="dimValue"  type="text"  style="cursor: pointer;" placeholder="输入查询内容"/>
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
								<th><a id="t.car_num" onclick="sort1(this);" style="cursor: pointer;">车辆</a></th>
								<th><a id="d.name" onclick="sort1(this);" style="cursor: pointer;">司机</a></th>
								<th><a id="amount" onclick="sort1(this);" style="cursor: pointer;">金额</a></th>
								<th><a id="t.status" onclick="sort1(this);" style="cursor: pointer;">状态</a></th>
								<th><a id="settlement" onclick="sort1(this);" style="cursor: pointer;">结算</a></th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
                        <c:forEach items="${orders.list}" var="order" varStatus="status">
                            <tr>
                                 <td><a href="javascript:detail('${order.orderCode}');" style="color: orange;">${order.orderCode}</a></td>
                                <td>${order.startPoint}</td>
                                <td>${order.endPoint}</td>
                                <td><fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value="${order.boardingTime}" /></td>
                                <td>${order.carNum}</td>
                                <td>${order.driverName}</td>
                                <td>${order.amount}</td>
                                <td>
                                	<c:if test="${order.status == -4}">已取消</c:if>
									<c:if test="${order.status == -3}">已拒绝</c:if>
                                	<c:if test="${order.status == -2}">待匹配</c:if>
									<c:if test="${order.status == -1}">匹配中</c:if>
									<c:if test="${order.status == 0}">待接受</c:if>
                                	<c:if test="${order.status == 1}">待上车</c:if>
									<c:if test="${order.status == 2}">在途</c:if>
									<c:if test="${order.status == 3}">完成</c:if>
									<c:if test="${order.status == 5}">待出发</c:if>
									<c:if test="${order.status == 7}">已返回</c:if>
                                </td>
                                 <td>
                                	<c:if test="${empty order.settlement || order.settlement == 1}">未结算</c:if>
									<c:if test="${order.settlement == 2}">已结算</c:if>
                                </td>
								 <td>
								 	<%-- <a href="javascript:detail('${order.orderCode}');" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-file-alt"></i>详情</a> --%>
								 	<c:if test="${order.status == 1}">
								 		<a href="javascript:delay('${order.orderCode}');" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-check"></i> 延误</a>
								 	</c:if>
								 	<c:if test="${order.status == 1 || order.status == 2 || order.status == 5}">
									 	<a href="javascript:accept('${order.orderCode}');" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-edit"></i> 修改</a>
									 	<a href="javascript:finish('${order.orderCode}');" class="layui-btn layui-btn-primary layui-btn-mini tourism_b"><i class="icon-check"></i> 结束</a>
									</c:if>
									<c:if test="${order.status == 3}">
									 	<a href="javascript:evaluate('${order.orderCode}');" class="layui-btn layui-btn-primary layui-btn-mini tourism_b"><i class="icon-edit"></i> 评价</a>
									</c:if>
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


    //搜索框的状态记忆
    var selectValue = $("#dim").val();
    if(selectValue==null && selectValue==''){
        selectValue='0';
    }
    layui.use('form', function() {

        var form = layui.form;


        //各种基于事件的操作，下面会有进一步介绍
        form.on('select(dim)', function(data){
            selectValue=data.value; //得到被选中的值
        });
    })




    //提交搜索表单
function selfSubmit() {

        if($("#dimValue").val() == null || $("#dimValue").val()==''){
            $("#searchType").val('0');

            $("#orderCodeSe").val('');
            $("#searchPlace").val('');
            $("#carType").val('');
            $("#driverName").val('');
            $("#linkMan").val('');
		}else{
            $("#searchType").val("1");
            var setValue = $("#dimValue").val();
            if(selectValue =='0'){
                set('orderCodeSe',setValue);
			}else if(selectValue =='1'){
                set('searchPlace',setValue);
			}else if(selectValue =='2'){
                set('carType',setValue);
			}else if(selectValue =='3'){
                set('driverName',setValue);
			}else if(selectValue =='4'){
                set('linkMan',setValue);
			}
		}
    $("#pageNum").val("1");
    $("#pageSize").val("10");
	$("#searchForm").submit();
}

function  set(id,value) {

    $("#orderCodeSe").val('');
    $("#searchPlace").val('');
    $("#carType").val('');
    $("#driverName").val('');
    $("#linkMan").val('');

    $("#"+id).val(value);


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

//接收订单的窗口
function accept(code){
	layer.open({
	  type: 2,
	  title: '接收',
	  shadeClose: true,
	  shade: 0.5,
	  btn: ['提交', '取消'],
	  yes: function(index, layero){
		//var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
		var body = layer.getChildFrame('body', index);
		var carCodeStr = body.find('#carCode').val();
		//var carNum = body.find('#carCode').find("option:selected").text();
		if(carCodeStr == ''){
			layer.msg('车辆不能为空', {icon: 2,time:500});
			body.find('#carCode').focus();
			return;
		}
		var driverCode = body.find('#driverCode').val();
		if(driverCode == ''){
			layer.msg('司机不能为空', {icon: 2,time:500});
			body.find('#driverCode').focus();
			return;
		}
		var viceDriverCode = body.find('#viceDriverCode').val();
		$.ajax({
	        type: "POST",
	        url: "${ctx}/transport/order/accept",
	        data: {'orderCode': code, 'carCode': carCodeStr.split(',')[0], 
	        	'carNum': carCodeStr.split(',')[1], 'driverCode': driverCode, 'viceDriverCode':viceDriverCode},
	        dataType: "json",
	        success: function(data){
		       	 if(data==-1){
		       		 layer.msg('系统错误', {icon: 2,time:1000});
		       	 }
		       	 if(data==0){
		       		 layer.msg('提交成功', {icon: 1,time:500});
		       		setTimeout(function(){
		       			selfSubmit();
	       			}, 500);
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

//结束订单的窗口
function finish(code){
	layer.open({
	  type: 2,
	  title: '结束订单',
	  shadeClose: true,
	  shade: 0.5,
	  btn: ['提交', '取消'],
	  yes: function(index, layero){
		//var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
		var body = layer.getChildFrame('body', index);
		var actualUpTime = body.find('#actualUpTime').val();
		if(actualUpTime == ''){
			layer.msg('实际上车时间不能为空', {icon: 2,time:500});
			body.find('#actualUpTime').focus();
			return;
		}
		var actualOffTime = body.find('#actualOffTime').val();
		if(actualOffTime == ''){
			layer.msg('实际下车时间不能为空', {icon: 2,time:500});
			body.find('#actualOffTime').focus();
			return;
		}
          var dataJson = {"orderCode":code};
          var result = check(dataJson, '${ctx}/transport/order/chatOrderStatus');

          if(!result){
              layer.msg('任务结束时间还未到，暂时无法结束时间。', {icon: 2,time:2000});
              return;
          }

		$.ajax({
	        type: "POST",
	        url: "${ctx}/transport/order/finish",
	        data: {'orderCode': code, 'actualUpTime': actualUpTime, 'actualOffTime': actualOffTime},
	        dataType: "json",
	        success: function(data){
		       	 if(data==-1){
		       		 layer.msg('系统错误', {icon: 2,time:1000});
		       	 }
		       	 if(data==0){
		       		 layer.msg('提交成功', {icon: 1,time:500});
		       		setTimeout(function(){
		       			evaluate(code);
	       			}, 500);
		        }
		       	layer.close(index);
	        }
	    });
	  },
	  btn2: function(index, layero){
	    //按钮【按钮二】的回调
	  },
	  area: ['680px','450px'],
	  content: '${ctx}/transport/order/finish?orderCode='+code
	})
}

//评价订单的窗口
function evaluate(code){
	layer.open({
	  type: 2,
	  title: '对包车方评价',
	  shadeClose: true,
	  shade: 0.5,
	  btn: ['提交', '取消'],
	  yes: function(index, layero){
		//var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
		var body = layer.getChildFrame('body', index);
		var content = body.find('#content').val();
		if(content == ''){
			layer.msg('评价内容不能为空', {icon: 2,time:500});
			body.find('#content').focus();
			return;
		}
		$.ajax({
	        type: "POST",
	        url: "${ctx}/transport/order/evaluate",
	        data: {'orderCode': code, 'toCharterContent': content},
	        dataType: "json",
	        success: function(data){
		       	 if(data==-1){
		       		 layer.msg('系统错误', {icon: 2,time:1000});
		       	 }
		       	 if(data==0){
		       		 layer.msg('提交成功', {icon: 1,time:500});
		       		setTimeout(function(){
		       			selfSubmit();
	       			}, 500);
		        }
	        }
	    });
	  },
	  btn2: function(index, layero){
		  selfSubmit();
	  },
	  cancel: function(index, layero){ 
		  selfSubmit();
	  },
	  area: ['680px','450px'],
	  content: '${ctx}/transport/order/evaluate?orderCode='+code
	})
}

/*****处理延误*****/
function delay(code){
  layer.open({
    type: 2,
    title: '延误处理',
    shadeClose: true,
    shade: 0.5,
    btn: ['提交', '取消'],
    yes: function(index, layero){
    	var body = layer.getChildFrame('body', index);
		var predictTime = body.find('#predictTime').val();
		if(predictTime == ''){
			layer.msg('延误时间不能为空', {icon: 2,time:500});
			body.find('#predictTime').focus();
			return;
		}
		var delayReason = body.find('#delayReason').val();
		if(delayReason == ''){
			layer.msg('延误原因不能为空', {icon: 2,time:500});
			body.find('#delayReason').focus();
			return;
		}
		$.ajax({
	        type: "POST",
	        url: "${ctx}/transport/order/doDelay",
	        data: {'orderCode': body.find('#orderCode').val(), 'boardingTime': predictTime, 'remark': delayReason},
	        dataType: "json",
	        success: function(data){
		       	 if(data==-1){
		       		 layer.msg('系统错误', {icon: 2,time:1000});
		       	 }
		       	 if(data==0){
		       		 layer.msg('提交成功', {icon: 1,time:1000});
		       		layer.close(index);
		        }
		       
	        }
	    });
    },
    btn2: function(index, layero){
    	
    },
    cancel: function(index, layero){
    	
    },
    area: ['550px','300px'],
    content: '${ctx}/transport/order/delay?orderCode='+code
  })
}
</script>
</body>
</html>