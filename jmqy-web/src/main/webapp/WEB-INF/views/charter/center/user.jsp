<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>个人中心</title>
</head>
<body>
	<section>
		<div class="container">
		<jsp:include page="/WEB-INF/layouts/left_menu.jsp"/>
			<main>
			<div class="position_all">
				<div class="position">您所在的位置：首页 -> 个人中心 -> 账号管理</div>
			</div>
			<form  action="" method="post">
				<input type="hidden" name="id" value="${user.id }"/>
		        <div class="searh searh1">
		        	<p>
		        	<label>用户名：</label><input class="search_text" type="text" value="${user.userAccount}" readonly="readonly"/>
		        	</p>
		        	<div class="div_input">
		         	 <label>性别：</label>
		         	 <c:if test="${user.sex == 1 }">
		         	 	<input name="sex" type="radio" value="1" checked="checked"/> 男
		         	 </c:if>
		         	  <c:if test="${user.sex != 1 }">
		         	 	<input name="sex" type="radio" value="1"/> 男
		         	 </c:if>
		         	 &nbsp;&nbsp;
		         	 <c:if test="${user.sex == 0 }">
		         	 	<input name="sex" type="radio" value="0" checked="checked"/> 女
		         	 </c:if>
		         	 <c:if test="${user.sex != 0 }">
		         	 	<input name="sex" type="radio" value="0"/> 女
		         	 </c:if>
		        	</div>
		      		<div class="div_input">
		        		<label>密码：</label><input class="search_text" type="password" name="userPassword"/>
		       		</div>
		         	<div class="div_input"> 
		        		<label>确认密码：</label><input class="search_text" type="password" name="newPassword"/>
					</div>
		       		<div class="div_input" style="margin-left:100px;"> 
		       			<a class="layui-btn layui-btn-normal" onclick="modifyUser();">确定</a>
		      		 	<a class="layui-btn layui-btn-primary">取消</a>
		       		</div> 
		        </div>
		      </form>
			</main>
		</div>
	</section>
	<script type="text/javascript">
	function modifyUser() {
        var id =$("[name='id']").val();
        var sex =$("[name='sex']:checked").val();
        var userPassword =$("[name='userPassword']").val();
        var newPassword =$("[name='newPassword']").val();
        var dataJson = {"id": id, "sex": sex, "userPassword": userPassword, "newPassword": newPassword};
        var content = '确定提交吗？';
        parent.layer.confirm(content, {icon: 3, title:'提示'}, function(index){
            $('.layui-layer-btn0').css('pointer-events','none');
            $.ajax({
                type:"post",
                url:"${ctx}/charter/center/modifyUser",
                dataType:"json",
                data:dataJson,
                success:function(data) {
                    if(data.state ==1 ){
                        layer.open({
                            icon: 1,
                            title: '信息',
                            skin: 'layer-ext-myskin',
                            shade: 0, //不显示遮罩
                            content: "账号修改操作成功！",
                            yes: function(){
                              location.reload();
                            },
                        });
                    }else if(data.state ==2 ){
                        layer.open({
                            icon: 2,
                            title: '信息',
                            skin: 'layer-ext-myskin',
                            shade: 0, //不显示遮罩
                            content: "账号修改操作失败！",
                            yes: function(){
                               location.reload();
                            },
                        });
                    }else if(data.state ==3 ){
                        layer.open({
                            icon: 2,
                            title: '信息',
                            skin: 'layer-ext-myskin',
                            shade: 0, //不显示遮罩
                            content: "原始密码不对！",
                            yes: function(){
                              location.reload();
                            },
                        });
                    }
                }
            });
        });
    }
	</script>
</body>
</html>