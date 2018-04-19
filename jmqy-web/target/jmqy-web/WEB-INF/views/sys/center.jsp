<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>个人中心</title>
</head>
<body>
<div class="layui-tab">
    <ul class="layui-tab-title">
        <li class="layui-this">个人信息</li>
        <li>企业信息</li>
        <li>修改密码</li>
    </ul>
    <div class="layui-tab-content">
        <!-- 个人信息 start-->
        <div class="layui-tab-item layui-show layui-form">
            <form class="layui-form layui-form-pane" action="${ctx}/sys/updateUser" method="post">
                <div class="layui-form-item">
                    <label class="layui-form-label">姓名</label>
                    <div class="layui-input-inline">
                        <input name="userName" lay-verify="required" value="${user.userName}" autocomplete="off" class="layui-input" type="text">
                    </div>
                </div>
                <div class="layui-form-item" style="width: 298px;" pane="">
                    <label class="layui-form-label">性别</label>
                    <div class="layui-input-inline">
                        <input name="sex" value="1" title="男" <c:if test="${empty user.sex || user.sex == 1}">checked</c:if> type="radio">
                        <input name="sex" value="0" title="女" <c:if test="${user.sex == 0}">checked</c:if> type="radio">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">身份证</label>
                    <div class="layui-input-inline">
                        <input name="idCard" lay-verify="identity" value="${user.idCard}" autocomplete="off" class="layui-input" type="text">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">手机号</label>
                    <div class="layui-input-inline">
                        <input name="phone" lay-verify="phone" value="${user.phone}" autocomplete="off" class="layui-input" type="text">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">创建日期</label>
                    <div class="layui-input-inline">
                        <input name="registerDate" autocomplete="off" class="layui-input" type="text" disabled
                               value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${user.registerDate}"/>'/>
                    </div>
                </div>

                <div class="layui-form-item">
                    <button class="layui-btn layui-btn-normal">确认修改</button>
                </div>
            </form>
        </div>
        <!-- 个人信息 end-->
        <!-- 企业信息 start-->
        <div class="layui-tab-item layui-form">
            <form class="layui-form layui-form-pane" action="">

                <div class="layui-form-item">
                    <label class="layui-form-label">企业名称</label>
                    <div class="layui-input-inline">
                        <input name="companyName" lay-verify="required" value="${company.companyName}" autocomplete="off" class="layui-input" type="text" disabled>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">企业编码</label>
                    <div class="layui-input-inline">
                        <input name="companyCode" lay-verify="required" value="${company.companyCode}" autocomplete="off" class="layui-input" type="text" disabled>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">联系电话</label>
                    <div class="layui-input-inline">
                        <input name="phone" lay-verify="required" value="" autocomplete="off" class="layui-input" type="text" disabled>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">地址</label>
                    <div class="layui-input-inline">
                        <input name="companyAddress" lay-verify="required" value="${company.companyAddress}" autocomplete="off" class="layui-input" type="text" disabled>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">邮箱</label>
                    <div class="layui-input-inline">
                        <input name="email" lay-verify="required" value="" autocomplete="off" class="layui-input" type="text" disabled>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">类型</label>
                    <div class="layui-input-inline">
                        <input name="type" lay-verify="required" value="股份有限公司" autocomplete="off" class="layui-input" type="text" disabled>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">法定代表人</label>
                    <div class="layui-input-inline">
                        <input name="username" lay-verify="required" value="" autocomplete="off" class="layui-input" type="text" disabled>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">注册时间</label>
                    <div class="layui-input-inline">
                        <input name="registerDate" lay-verify="required"  autocomplete="off" class="layui-input" type="text" disabled
                               value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${company.registerDate}"/>'/>
                    </div>
                </div>
            </form>
        </div>
        <!-- 企业信息 end-->
        <!-- 修改密码 start-->
        <div class="layui-tab-item layui-form">
            <form class="layui-form layui-form-pane" action="${ctx}/sys/updatePw" method="post">
                <div class="layui-form-item">
                    <label class="layui-form-label">原始密码</label>
                    <div class="layui-input-inline">
                        <input name="userPassword" lay-verify="required" autocomplete="off" class="layui-input" type="password">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">新密码</label>
                    <div class="layui-input-inline">
                        <input name="newPassword" lay-verify="required" autocomplete="off" class="layui-input" type="password">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">确认密码</label>
                    <div class="layui-input-inline">
                        <input name="confirmPassword" lay-verify="required" autocomplete="off" class="layui-input" type="password">
                    </div>
                </div>
                <div class="layui-form-item">
                    <button type="button" class="layui-btn layui-btn-normal" onclick="submitPw();">确认修改</button>
                </div>
            </form>
        </div>
        <!-- 修改密码 end-->
    </div>
</div>
<script>
//文档加载完毕执行
$(function() {
	var message = '${message}';
	if(message != null && "" != message){
		var msg = message.split("@");
		setTimeout(function(){
            layer.open({
                icon: msg[1],
                title: '信息',
                skin: 'layer-ext-myskin',
                shade: 0, //不显示遮罩
                content: "&nbsp;&nbsp;"+msg[0]+"&nbsp;&nbsp;",
                yes: function(){
                    layer.closeAll();
                },
            });
		}, 500);
	}
});
//加载表单
layui.use(['form','element','laydate'], function() {
	
    var form = layui.form;
    
  	//监听提交
    form.on('submit(pwForm)', function(data){
      layer.msg(JSON.stringify(data.field));
      return false;
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

//提交修改密码
function submitPw(){
	var userPassword = $('[name=userPassword]').val();
	if(userPassword == ''){
		layer.msg('原始密码不能为空', {icon: 2,timeout:1000});
		$('[name=userPassword]').focus();
		return;
	}
	var newPassword = $('[name=newPassword]').val();
	if(newPassword == ''){
		layer.msg('新密码不能为空', {icon: 2,timeout:1000});
		$('[name=newPassword]').focus();
		return;
	}
	var confirmPassword = $('[name=confirmPassword]').val();
	if(confirmPassword == ''){
		layer.msg('确认密码不能为空', {icon: 2,timeout:1000});
		$('[name=confirmPassword]').focus();
		return;
	}
	if(confirmPassword != newPassword){
		layer.msg('新密码不一致', {icon: 2,timeout:1000});
		$('[name=newPassword]').focus();
		return;
	}
	 $.ajax({
         type: "POST",
         url: "${ctx}/sys/updatePw",
         data: {'userPassword': userPassword, 'newPassword': newPassword},
         dataType: "json",
         success: function(data){
        	 if(data==-2){
                 layer.open({
                     icon: 2,
                     title: '信息',
                     skin: 'layer-ext-myskin',
                     shade: 0, //不显示遮罩
                     content: "操作失败！",
                     yes: function(){
                         layer.closeAll();
                     },
                 });
        	 }
        	 if(data==-1){
                 layer.open({
                     icon: 2,
                     title: '信息',
                     skin: 'layer-ext-myskin',
                     shade: 0, //不显示遮罩
                     content: "原始密码不正确！",
                     yes: function(){
                         layer.closeAll();
                     },
                 });
        	 }
        	 if(data==0){
                 layer.open({
                     icon: 1,
                     title: '信息',
                     skin: 'layer-ext-myskin',
                     shade: 0, //不显示遮罩
                     content: "密码修改成功,请重新登陆！",
                     yes: function(){
                         layer.closeAll();
                         top.location.href = "${ctx}/main";
                     },
                 });
        	 }
         }
     });
}
</script>
</body>
</html>