<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>角色管理</title>
</head>
<body>
<section>
	<div class="container">
	<jsp:include page="/WEB-INF/layouts/left_menu.jsp"/>
		<main>
		<div class="position_all">
			<div class="position">您所在的位置：首页 -> 个人中心 -> 角色管理</div>
		</div>
		<div class="info">
			<form id="searchForm" action="${ctx}/charter/role/list" method="post">
                <input type="hidden" name="pageNum" value="${role.pageNum}"/>
                <input type="hidden" name="pageSize" value="${role.pageSize}"/>
                <input type="hidden" id="order" name="order" value="${role.order}"/>
				<input type="hidden" id="sortType" name="sortType" value="${role.sortType}"/>
				<div class="button_left">
					<a href="javascript:update('');" class="layui-btn layui-btn-primary layui-btn-mini car_a"><i class="icon-plus-sign"></i> 新增</a>
				</div>
				<div class="button_right">
					<input type="text" name=roleName value="${role.roleName}" class="layui-input" placeholder="请输入角色名">
					<a class="layui-btn layui-btn-danger layui-btn-small" href="javascript:selfSubmit();">搜索</a>
				</div>
				<div class="cl"></div>
				<div class="layui-form">
					<table class="layui-table">
						<colgroup>
							<col width="50">
							<col width="150">
							<col width="150">
							<col width="300">
							<col width="100">
							<col width="300">
							<col>
						</colgroup>
						<thead>
							<tr>
								<th><input name="" lay-skin="primary" lay-filter="allChoose" type="checkbox"></th>
								<th><a id="role_name" onclick="sort(this);" style="cursor: pointer;">角色名称</a></th>
						        <th><a id="role_describe" onclick="sort(this);" style="cursor: pointer;">角色描述</a></th>
						        <th><a id="remark" onclick="sort(this);" style="cursor: pointer;">备注</a></th>
						       	<th><a id="status" onclick="sort(this);" style="cursor: pointer;">状态</a></th>
						        <th>操作</th>
							</tr>
						</thead>
						<tbody>
                           <c:forEach items="${roles.list}" var="role" varStatus="status">
                               <tr>
                                   <td><input name="" lay-skin="primary" type="checkbox"></td>
                                   <td>${role.roleName}</td>
                                   <td>${role.roleDescribe}</td>
                                   <td>${role.remark}</td>
                                   <td>
	                                   	<c:if test="${role.status == 0}">启用</c:if>
	                                   	<c:if test="${role.status == 1}">禁用</c:if>
                                   </td>
                                   <td>
	                                   	<a href="javascript:update('${role.id}');" class="layui-btn layui-btn-primary layui-btn-mini caradmin_a"><i class="icon-edit"></i> 修改</a>
	                                   	<a href="javascript:authorize('${role.id}','${role.roleDescribe}');" class="layui-btn layui-btn-primary layui-btn-mini caradmin_a"><i class="icon-edit"></i> 授权</a>
	                                   	<c:if test="${role.status == 0}"><a href="javascript:updateById('${role.id}',1);" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-remove"></i> 禁用</a></c:if>
                                   		<c:if test="${role.status == 1}"><a href="javascript:updateById('${role.id}',0);" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-remove"></i> 启用</a></c:if>
                                   </td>
                               </tr>
                           </c:forEach>
						</tbody>
					</table>
				</div>
			</form>
		</div>
        <tags:page pages="${roles}"/>
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
    title: '添加/修改',
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
    area: ['550px','400px'],
    content: '${ctx}/charter/role/form?id='+id
  })
}

/****给角色授权页面*****/
function authorize(id, roleName){
  parent.layer.open({
    type: 2,
    title: '给角色['+roleName+']分配菜单',
    shadeClose: true,
    shade: 0.1,
    btn: ['确定', '取消'],
    yes: function(index, layero){
        var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
        iframe.setPer();
    },
    area: ["750px", "500px"],
    content: '${ctx}/charter/role/authorize?id='+id
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
    layer.confirm('真的'+typeStr+'这个角色么?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type: "POST",
            url: "${ctx}/charter/role/updateById",
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