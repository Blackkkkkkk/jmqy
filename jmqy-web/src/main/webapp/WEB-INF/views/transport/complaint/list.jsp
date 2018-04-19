<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>投诉</title>
</head>
<body>
<section>
	<div class="container">
	<jsp:include page="/WEB-INF/layouts/left_menu.jsp"/>
		<main>
		<div class="position_all">
			<div class="position">您所在的位置：首页 -> 管理中心 -> 投诉管理</div>
		</div>
		<div class="info">
			<form id="searchForm" class="layui-form" action="${ctx}/transport/complaint/list" method="post">
                <input type="hidden" name="pageNum" value="${complaint.pageNum}"/>
                <input type="hidden" name="pageSize" value="${complaint.pageSize}"/>
                <input type="hidden" id="order" name="order" value="${complaint.order}"/>
				<input type="hidden" id="sortType" name="sortType" value="${complaint.sortType}"/>
				<div class="button_right john-edge">
					<div class="layui-inline">
						<label class="layui-form-label">投诉时间:</label>
						<div class="layui-input-inline" style="top: 5px;">
							<input class="layui-input search_text input_date" id="beginTime" name="beginTime" type="text" value='${complaint.beginTime}'
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>_
						</div>
						<div class="layui-input-inline" style="top: 5px;">
							<input class="layui-input search_text input_date" id="endTime" name="endTime" type="text" value='${complaint.endTime}'
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						</div>
					</div>	
					<div class="layui-inline">
						<input type="text" name="param" value="${complaint.param}" class="layui-input" placeholder="请输关键字">
						<a class="layui-btn layui-btn-danger layui-btn-small" href="javascript:selfSubmit();">搜索</a>
					</div>
				</div>
				<div class="cl"></div>
				<div class="layui-form">
					<table class="layui-table">
						<colgroup>
							<col width="100">
							<col width="150">
							<col width="100">
							<col width="100">
							<col width="100">
							<col width="120">
							<col width="300">
							<col width="300">
							<col width="100">
							<col>
						</colgroup>
						<thead>
							<tr>
								<th><a id="c1.company_name" onclick="sort(this);" style="cursor: pointer;">客户</a></th>
						        <th><a id="t.order_code" onclick="sort(this);" style="cursor: pointer;">订单号</a></th>
						        <th><a id="d.name" onclick="sort(this);" style="cursor: pointer;">司机</a></th>
						        <th><a id="d.job_num" onclick="sort(this);" style="cursor: pointer;">工号</a></th>
						        <th><a id="o.car_num" onclick="sort(this);" style="cursor: pointer;">车辆</a></th>
						        <th><a id="t.create_time" onclick="sort(this);" style="cursor: pointer;">时间</a></th>
						        <th><a id="t.to_complaint_content" onclick="sort(this);" style="cursor: pointer;">投诉内容</a></th>
								<th><a id="t.reply_content" onclick="sort(this);" style="cursor: pointer;">投诉回复</a></th>
						        <th>操作</th>
							</tr>
						</thead>
						<tbody>
                        <c:forEach items="${complaints.list}" var="complaint" varStatus="status">
                            <tr>
                                <td>${complaint.charterCompany}</td>
								<td><span><a href="javascript:query('${complaint.orderCode}',3);" style="color: orange;">${complaint.orderCode}</a></span></td>
                                <td>${complaint.driverName}</td>
                                <td>${complaint.jobNum}</td>
                                <td>${complaint.carNum}</td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd" value="${complaint.createTime}" /></td>
                                <td><span class="red" title="${complaint.toComplaintContent}">
                                	<c:if test="${fn:length(complaint.toComplaintContent)>20}">
									${fn:substring(complaint.toComplaintContent, 0, 20)} ...
									</c:if>
									<c:if test="${fn:length(complaint.toComplaintContent)<=20}">
									${complaint.toComplaintContent}
									</c:if>
                                </span></td>
								<td><span class="red" title="${complaint.replyContent}">
                                	<c:if test="${fn:length(complaint.replyContent)>20}">
										${fn:substring(complaint.replyContent, 0, 20)} ...
									</c:if>
									<c:if test="${fn:length(complaint.replyContent)<=20}">
										${complaint.replyContent}
									</c:if>
                                </span></td>
								<td>
									<a href="javascript:detail('${complaint.id}');" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-edit"></i> 回复</a>
								</td>
							</tr>
                        </c:forEach>
						</tbody>
					</table>
				</div>
			</form>
		</div>
        <tags:page pages="${complaints}"/>
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
//查看订单投诉详情
function detail(id){
	var width ="900px";
	var height = "500px"
	var title ="订单投诉详情";
	layer.open({
	  type: 2,
	  title: title,
	  shadeClose: true,
	  shade: 0.5,
	  btn: ['提交', '取消'],
	  yes: function(index, layero){
			//var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
			var body = layer.getChildFrame('body', index);
			var replyContent = body.find('#replyContent').val();
			if(replyContent == ''){
				layer.msg('回复内容不能为空', {icon: 2,timeout:500});
				body.find('#replyContent').focus();
				return;
			}
			$.ajax({
		        type: "POST",
		        url: "${ctx}/transport/complaint/reply",
		        data: {'id': id, 'replyContent': replyContent},
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
		  },
		  btn2: function(index, layero){
			  selfSubmit();
		  },
		  cancel: function(index, layero){ 
			  selfSubmit();
		  },
	  area: [width,height],
	  content: '${ctx}/transport/complaint/detail?id='+id
	})
}

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
        content: '${ctx}/charter/order/query?orderCode='+orderCode+'&type='+type
    })
}

</script>
</body>
</html>