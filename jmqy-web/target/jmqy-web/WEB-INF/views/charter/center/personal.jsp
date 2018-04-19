<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>个人中心</title>
</head>
<body>
<section>
	<div class="container">
		<jsp:include page="/WEB-INF/layouts/left_menu.jsp" />
		<main>
		<div class="position_all">
			<div class="position">您所在的位置：首页 -> 管理中心 -> 个人中心</div>
		</div>
			<div class="layui-tab layui-tab-card">
			
				<ul class="layui-tab-title">
					<li class="layui-this">个人信息</li>
					<c:if test="${user.companyType == 2}"><li>企业信息</li></c:if>
					<li>修改密码</li>
				</ul>
				
				<!--主体内容-->
				<div class="layui-tab-content">
				
					<!-- 个人信息 start-->
					<div class="layui-tab-item layui-show layui-form">
						<form class="layui-form layui-form-pane" action="${ctx}/charter/center/updateUser" method="post">
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
								<c:if test="${company.type == 1 && company.status != -1}">
									<button type="button" class="layui-btn layui-btn-normal" onclick="beCompany();">注册为企业</button>
								</c:if>
								<c:if test="${company.status == -1}">
									<button type="button" class="layui-btn layui-btn-warm" onclick="">企业注册审批中</button>
								</c:if>
							</div>
						</form>
					</div>
					<!-- 个人信息 end-->
					
					<c:if test="${user.companyType == 2}">
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
							<div class="layui-form-item" style="top:5px;">
								<label class="layui-form-label">营业执照:</label>
								<div class="layui-input-inline" style="margin-left: 20px;">
									<img class="img-mini zheng_a" src="${ctx}${company.businessPic}" onclick="fun_zheng(this);" alt="营业执照" id="businessPicImg">
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
					</c:if>
					
					<!-- 修改密码 start-->
					<div class="layui-tab-item layui-form">
						<form class="layui-form layui-form-pane" action="${ctx}/charter/center/updatePw" method="post">
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
			layer.msg(msg[0], {icon: msg[1],time:1000});
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

//图片放大
function fun_zheng(obj){
	  layer.open({
	    type: 2,
	    title: false,
	    shadeClose: false,
	    shade: 0,
	    shift:-1,
	    area: ['570px','390px'],
	    content: obj.src
	  })
}

function selfSubmit(){
	location.reload(true);
}

/*****包车企业认证*****/
function beCompany(){
  layer.open({
    type: 2,
    title: '包车企业认证',
    shadeClose: true,
    shade: 0.5,
    btn: ['提交', '取消'],
    yes: function(index, layero){
    	 var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
    	 iframeWin.laySubmit();//执行iframe页的方法：
    },
    btn2: function(index, layero){
    	
    },
    cancel: function(index, layero){
    	
    },
    area: ['550px','300px'],
    content: '${ctx}/charter/center/beCompany'
  })
}

//提交修改密码
function submitPw(){
	var userPassword = $('[name=userPassword]').val();
	if(userPassword == ''){
		layer.msg('原始密码不能为空', {icon: 2,time:1000});
		$('[name=userPassword]').focus();
		return;
	}
	var newPassword = $('[name=newPassword]').val();
	if(newPassword == ''){
		layer.msg('新密码不能为空', {icon: 2,time:1000});
		$('[name=newPassword]').focus();
		return;
	}
	var confirmPassword = $('[name=confirmPassword]').val();
	if(confirmPassword == ''){
		layer.msg('确认密码不能为空', {icon: 2,time:1000});
		$('[name=confirmPassword]').focus();
		return;
	}
	if(confirmPassword != newPassword){
		layer.msg('新密码不一致', {icon: 2,time:1000});
		$('[name=newPassword]').focus();
		return;
	}
	 $.ajax({
         type: "POST",
         url: "${ctx}/charter/center/updatePw",
         data: {'userPassword': userPassword, 'newPassword': newPassword},
         dataType: "json",
         success: function(data){
        	 if(data==-2){
        		 layer.msg('系统错误', {icon: 2,time:1000});
        	 }
        	 if(data==-1){
        		 layer.msg('原始密码不正确', {icon: 2,time:1000});
        	 }
        	 if(data==0){
        		 layer.msg('密码修改成功,请重新登陆', {icon: 1,time:1000},function(){
        			 //location.reload();
        			 fun_loging('${ctx}/login');
        		 });
        	 }
         }
     });
}
</script>
</body>
</html>