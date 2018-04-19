<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>添加用户</title>
    <style>
        body{
            min-width: 0;
        }
    </style>
</head>
<body class="window">
<div class="layui-form" style="margin-top: 10px;">
	<form id="inputForm" class="layui-form" action="" lay-filter="inputForm">
		<input id="id" type="hidden" name="id" value="${user.id}"/>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">账号:</label>
				<div class="layui-input-inline">
					<input name="userAccount" lay-verify="required<c:if test='${empty user.id}'>|loginName</c:if>" autocomplete="off" value="${user.userAccount}" placeholder="账号" class="layui-input" type="text"
					<c:if test="${not empty user.id}">disabled</c:if>>
				</div>
				<label class="layui-form-label">用户名:</label>
				<div class="layui-input-inline">
					<input name="userName" lay-verify="required" autocomplete="off" value="${user.userName}" placeholder="用户名" class="layui-input" type="text">
				</div>
			</div>
		</div>
		<div class="layui-form-item" pane="">
		    <label class="layui-form-label">性别：</label>
		    <div class="layui-input-block">
			    <input name="sex" value="1" title="男" <c:if test="${empty user.sex || user.sex == 1}">checked="checked"</c:if> type="radio">
			    <input name="sex" value="0" title="女" <c:if test="${user.sex == 0}">checked="checked"</c:if> type="radio">
		    </div>
	  	</div>
	  	<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">手机号码:</label>
				<div class="layui-input-inline">
					<input name="phone" lay-verify="phone|checkPhone" autocomplete="off" value="${user.phone}"  placeholder="手机号码" class="layui-input" type="text">
				</div>
				<label class="layui-form-label">身份证号:</label>
				<div class="layui-input-inline">
					<input type="text" name="idCard" id="idCard" value="${user.idCard}" lay-verify="identity" placeholder="身份证" autocomplete="off" class="layui-input"> 
				</div>
			</div>
		</div>
		<div class="layui-form-item" style="width: 504px;">
			<label class="layui-form-label">角色:</label>
			<div class="layui-input-block">   
				<select name=role lay-verify="required" lay-search="">
					<c:forEach items="${roles}" var="role" varStatus="status" >
						<option value="${role.id}" <c:if test="${role.id == user.role}">selected = "selected"</c:if>>${role.roleDescribe}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="layui-form-item" style="width: 504px;">
			<label class="layui-form-label">邮箱:</label>
			<div class="layui-input-block">   
				<input type="text" name="email" id="email" value="${user.email}" lay-verify="" placeholder="邮箱" autocomplete="off" class="layui-input"/>
			</div>
		</div>
		<c:if test="${not empty user.id}">
		<div class="layui-form-item" style="width: 504px;">
			<label class="layui-form-label">创建日期:</label>
			<div class="layui-input-block">   
				<input name="registerDate" autocomplete="off" class="layui-input" type="text" disabled
				value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${user.registerDate}"/>'/>
			</div>
		</div>
		</c:if>
        <div style="display: none;">
            <button id="submit_btn" class="layui-btn layui-btn-normal submit" lay-submit lay-filter="inputForm"></button>
        </div>
	</form>
</div>
<script type="text/javascript">

//加载表单
layui.use(['form'], function() {
    var form = layui.form;
  	//监听提交
    form.on('submit(inputForm)', function(data){
        submit_(data.field);
      return true;
    });
    //自定义验证规则
    form.verify({

        loginName: function (value,item) {
            var dataJson = {'id':$('#id').val(),"userAccount":value};
            var result = check(dataJson, '${ctx}/user/userAccountUnique');
            if(!result){
                return "账号已被注册！";
            }
        },
        checkPhone: function (value,item) {
            var dataJson = {'id':$('#id').val(),"userAccount":value};
            var result = check(dataJson, '${ctx}/user/userAccountUnique');
            if(!result){
                return "手机号已被注册！";
            }
        }
    });
});
function submit_(dataJson) {
    $('.layui-layer-btn0').css('pointer-events','none');
    $.ajax({
        type:"post",
        url:"${ctx}/sys/user/saveOrUpdate",
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