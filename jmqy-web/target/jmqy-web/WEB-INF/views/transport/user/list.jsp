<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>用户管理</title>
</head>
<body>
<section>
	<div class="container">
	<jsp:include page="/WEB-INF/layouts/left_menu.jsp"/>
		<main>
		<div class="position_all">
			<div class="position">您所在的位置：首页 -> 管理中心 -> 用户管理</div>
		</div>
		<div class="info">
			<form id="searchForm" action="${ctx}/transport/user/list" method="post">
                <input type="hidden" name="pageNum" value="${user.pageNum}"/>
                <input type="hidden" name="pageSize" value="${user.pageSize}"/>
                <input type="hidden" id="order" name="order" value="${user.order}"/>
				<input type="hidden" id="sortType" name="sortType" value="${user.sortType}"/>
				<div class="button_left">
					<a href="javascript:update('');" class="layui-btn layui-btn-primary layui-btn-mini car_a"><i class="icon-plus-sign"></i> 新增</a>
				</div>
				<div class="button_right">
					<input type="text" name="userName" value="${user.userName}" class="layui-input" placeholder="请输入用户名">
					<a class="layui-btn layui-btn-danger layui-btn-small" href="javascript:selfSubmit();">搜索</a>
				</div>
				<div class="cl"></div>
				<div class="layui-form">
					<table class="layui-table">
						<colgroup>
							<col width="50">
							<col width="150">
							<col width="150">
							<col width="100">
							<col width="100">
							<col width="200">
							<col width="100">
							<col width="300">
							<col>
						</colgroup>
						<thead>
							<tr>
								<th><input name="" lay-skin="primary" lay-filter="allChoose" type="checkbox"></th>
								<th><a id="user_name" onclick="sort(this);" style="cursor: pointer;">姓名</a></th>
						        <th><a id="user_account" onclick="sort(this);" style="cursor: pointer;">账号</a></th>
						        <th><a id="sex" onclick="sort(this);" style="cursor: pointer;">性别</a></th>
						        <th><a id="phone" onclick="sort(this);" style="cursor: pointer;">手机号</a></th>
						        <th><a id="id_card" onclick="sort(this);" style="cursor: pointer;">身份证</a></th>
						       	<th><a id="u.status" onclick="sort(this);" style="cursor: pointer;">状态</a></th>
						        <th>操作</th>
							</tr>
						</thead>
						<tbody>
                           <c:forEach items="${users.list}" var="user" varStatus="status">
                               <tr>
                                   <td><input name="" lay-skin="primary" type="checkbox"></td>
                                   <td>${user.userName}</td>
                                   <td>${user.userAccount}</td>
                                   <td>
	                                   <c:if test="${user.sex == 0}">女</c:if>
		                               <c:if test="${user.sex == 1}">男</c:if>
                                   </td>
                                   <td>${user.phone}</td>
								   <td>${user.idCard}</td>
                                   <td>
	                                   	<c:if test="${user.status == 0}">启用</c:if>
	                                   	<c:if test="${user.status == 1}">禁用</c:if>
	                                   	<c:if test="${user.status == 2}">不通过</c:if>
                                   </td>
                                   <td>
	                                   	<a href="javascript:update('${user.id}');" class="layui-btn layui-btn-primary layui-btn-mini caradmin_a"><i class="icon-edit"></i> 修改</a>
	                                   	<c:if test="${user.status == 0}"><a href="javascript:updateById('${user.id}',1);" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-remove"></i> 禁用</a></c:if>
                                   		<c:if test="${user.status == 1}"><a href="javascript:updateById('${user.id}',0);" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-remove"></i> 启用</a></c:if>
                                   </td>
                               </tr>
                           </c:forEach>
						</tbody>
					</table>
				</div>
			</form>
		</div>
        <tags:page pages="${users}"/>
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
//提交搜索表单
function selfSubmit() {
	$("#searchForm").submit();
}

//跳到第一页
function selfSearch() {
	$("[name='pageNum']").val(1);//第一页
	selfSubmit();
}

/*****、新增、修改*****/
function update(id){
  layer.open({
    type: 2,
    title: '添加修改',
    shadeClose: true,
    shade: 0.5,
    btn: ['提交', '取消'],
    yes: function(index, layero){
    	 var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
    	 iframeWin.laySubmit();//执行iframe页的方法：
    },
    btn2: function(index, layero){
    	selfSearch();
    },
    cancel: function(index, layero){ 
    	selfSearch();
    },
    area: ['650px','450px'],
    content: '${ctx}/transport/user/form?id='+id
  })
}

/*
* status:
* 0:启用 1:禁用 2:不通过
*/
function updateById(id, status){
	var typeStr = '禁用';
	if(status == 0){
		typeStr = '启用';
	}
	if(status == 1){
		typeStr = '禁用';
	}
    layer.confirm('真的'+typeStr+'这个用户么?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type: "POST",
            url: "${ctx}/transport/user/updateById",
            data: {'id': id, 'status': status},
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