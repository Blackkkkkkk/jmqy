<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>添加角色</title>
    <style>
        body{
            min-width: 0;
        }
    </style>
</head>
<body>
<div class="layui-form" style="margin-top: 10px; margin-left: 50px;">
	<form id="inputForm" class="layui-form" action="" lay-filter="inputForm">
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
        <div style="display: none;">
            <button id="submit_btn" class="layui-btn layui-btn-normal submit" lay-submit lay-filter="inputForm"></button>
        </div>
	</form>
</div>
<script>
//加载表单
layui.use(['form'], function() {
    var form = layui.form;
  	//监听提交
    form.on('submit(inputForm)', function(data){
      submit_(data.field);
      return false;
    });
});
function submit_(dataJson) {
    $('.layui-layer-btn0').css('pointer-events','none');
    $.ajax({
        type:"post",
        url:"${ctx}/sys/role/saveOrUpdate",
        dataType:"json",
        data:dataJson,
        success:function(data) {
            var id = $("[name='id']").val();
            var title = (id!=''?'修改':'新增');
            if(data.state){
                parent.layer.open({
                    icon: 1,
                    title: '信息',
                    skin: 'layer-ext-myskin',
                    shade: 0, //不显示遮罩
                    content: title+"成功！",
                    yes: function(){
                        parent.layer.closeAll();
                        parent.refresh();
                    },
                });
            }else{
                parent.layer.open({
                    icon: 2,
                    title: '信息',
                    skin: 'layer-ext-myskin',
                    shade: 0, //不显示遮罩
                    content: title+"失败！"
                });
            }
        }
    });
}
</script>
</body>
</html>