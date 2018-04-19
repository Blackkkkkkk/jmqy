<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>登陆1</title>
    <style>
        body{background: #ffffff;}
    </style>
    <script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
</head>
<body>
<div class="logout_left"></div>
<div class="logout_right">
    <div class="logout_box">
        <%--<ul class="login-tab">--%>
            <%--<li class="loginpic"></li>--%>
            <%--<li class="loginma"></li>--%>
        <%--</ul>--%>
        <div class="logout_info">
            <h2></h2>
            <form id="login_form" action="${ctx}/login" method="post">
                <div class="input-prepend" style="margin-top: 25px;">
                    <span class="add-on"><i class="icon-user icon-large"></i></span>
                    <input class="span2" id="username" name="username" type="text" placeholder="请输入账号">
                </div>
                <div class="input-prepend" style="margin-top: 25px;">
                    <span class="add-on"><i class="icon-key icon-large"></i></span>
                    <input class="span2" id="password" name="password" type="password" placeholder="请输入密码">
                </div>
                <p style="text-align:center;margin-left:0;margin-top:50px">
                    <a class="layui-btn layui-btn-normal" onclick="login();">登&nbsp;录</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a class="layui-btn layui-btn-primary" onclick="register();">注&nbsp;册</a>
                </p>
            </form>
        </div>
        <div class="logout_info1">
            <br/><br/>
            <div id="di"></div>
            <!--<img src="${ctx}/static/images/wx.jpg" alt=""> -->
            <div class="cl"></div>
            <br/>
            手机扫码，安全登录
        </div>
    </div>
</div>
<script>

    var obj = new WxLogin({
        id:"di",
        appid: "wxb2acefba99a3fbcf",
        scope: "snsapi_login",
        redirect_uri: "http%3a%2f%2ftravelbus.jmqyjt.com%2fWechatLogin%2fwechatLogin",
        state: "",
        style: "black",
        href: ""
    });




    $('.login-tab li').click(function(){
        $(this).css('display','none');
        $(this).siblings().css('display','block');
        if($(this).index() == 1){
            $('.logout_info1').show();
            $('.logout_info').hide();
        }else{
            $('.logout_info').show();
            $('.logout_info1').hide();
        };
    });
    document.onkeydown=function(e){
        if((e.keyCode || e.which) == 13){
            login();
        }
    }

    function login() {
        var username = $("#username").val();
        if(!(username != null && '' != username)){
            layer.tips('请输入账号！', '#username', {tips: [3, '#78BA32']});
            return false;
        }
        var password = $("#password").val();
        if(!(password != null && '' != password)){
            layer.tips('请输入密码！', '#password', {tips: [3, '#78BA32']});
            return false;
        }
        $("#login_form").submit();
    }

    function register(){
    	parent.location.href="${ctx}/register";
    }

    layui.use(['form'], function(){
        var message = '${message}';
        if(message != null && '' != message){
            if(message=='0'){
                layer.msg('登陆成功！',{icon:1, time:500},function(){
                	  var role = '${role}';
                      if(role.indexOf('sys') != -1){
                          top.location.href = "${ctx}/main";
                          return false;
                      }else if(parent.location.href.indexOf('search/charter') != -1){
                     		parent.loginAfter();
                         	//parent.layer.closeAll();
                      }else{
                      	top.location.href = "${ctx}/user/main";
                      }
                });
            }
            if(message=='1'){
                layer.tips('请输入正确的账号！', '#username', {tips: [3, '#78BA32']});
                return false;
            }
            if(message=='2'){
                layer.tips('请输入正确的密码！', '#password', {tips: [3, '#78BA32']});
                return false;
            }
            if(message=='3'){
                layer.msg('账号被禁用或在审核中！', function () {});
                return false;
            }
            if(message=='4'){
                layer.msg('账号无分配权限，请联系管理员！', function () {});
                return false;
            }
        }
    });

    $('body').click(function(){
        if($('#dl_show').next('dl').css('display') == 'block'){
            $('#dl_show').next('dl').slideUp();
        }
    })


</script>
</body>
</body>
</html>