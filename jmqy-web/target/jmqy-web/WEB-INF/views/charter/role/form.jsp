<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>添加角色</title>
</head>
<body class="window">
<div class="layui-form" style="margin-top: 10px;">
	<form id="saveOrUpdate" class="layui-form" action="${ctx}/charter/role/saveOrUpdate" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${role.id}"/>
		<div class="layui-form-item" style="width: 504px;">
				<label class="layui-form-label">角色名称:</label>
				<div class="layui-input-block">
					<input name="roleName" lay-verify="required" autocomplete="off" value="${role.roleName}" placeholder="角色名称" class="layui-input" type="text"
					<c:if test="${not empty role.id}">disabled</c:if>>
				</div>
		</div>
		<div class="layui-form-item" style="width: 504px;">
				<label class="layui-form-label">角色描述:</label>
				<div class="layui-input-block">
					<input name="roleDescribe" lay-verify="required" autocomplete="off" value="${role.roleDescribe}" placeholder="角色描述" class="layui-input" type="text">
				</div>
		</div>
		<div class="layui-form-item" style="width: 504px;">
			<label class="layui-form-label">角色备注:</label>
			<div class="layui-input-block">   
				 <textarea name="remark" placeholder="请输入内容" class="layui-textarea">${role.remark}</textarea>
			</div>
		</div>
	</form>
</div>
<script type="text/javascript">
//文档加载完毕执行
$(function() {
	var message = '${message}';
	if(message != null && "" != message){
		var msg = message.split("@");
		setTimeout(function(){
			layer.msg(msg[0], {icon: msg[1]});
		}, 100);
		setTimeout(function(){
			parent.location.reload(true);
		}, 500);
	}
});
//提交表单
function laySubmit(){
	$('#saveOrUpdate').submit();
}
//加载表单
layui.use(['form','element','laydate'], function() {
	
  	//监听提交
    form.on('submit(formDemo)', function(data){
      //layer.msg(JSON.stringify(data.field));
      return true;
    });
  	
    //自定义验证规则
    form.verify({
        //只能输入数字
        integer:function (value,item) {
            if(!/^[0-9]*$/.exec(value)){
                return "只能输入数字";
            }
        }

    });
});
</script>
</body>
</html>