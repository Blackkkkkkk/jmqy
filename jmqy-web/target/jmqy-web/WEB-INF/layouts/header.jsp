<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<header>
    <div class="container">
        <div class="logo" onclick="forward('${ctx}/index', 0);">
            <h1>马上走</h1>
        </div>
        <%--<div class="search">--%>
        	<%--扫一扫关注微信&nbsp;<img src="${ctx}/static/images/wx.jpg" title="扫一扫关注微信" style="width: 65px; height: 65px;">--%>
        <%--</div>--%>
    </div>
</header>
<div class="nav_t"></div>
<nav>
    <div class="container">
        <ul class="menu">
            <li id="首页" onclick="forward('${ctx}/index', 0);"><a href="javascript:;">首页</a></li>
            <%--<li id="租赁包车" class="chartered"><a href="javascript:forward('${ctx}/search/charter',0);">租赁包车</a></li>--%>
            <%--<li id="定制巴士"><a href="javascript:;">定制巴士</a> </li>--%>
            <%--<li id="客运班车"><a href="javascript:;">客运班车</a></li>--%>
            <%--<li id="周边游"><a href="javascript:;">周边游</a></li>--%>
            <li id="会员中心" onclick="forward('${ctx}/user/main', 1);"><a href="javascript:;">会员中心</a></li>
        </ul>
        <div class="logout" style="font-size: 13px;">
            <shiro:guest>
                <a class="loging" href="javascript:;">登录</a> | <a id="register" href="javascript:;">注册</a>
            </shiro:guest>
            <shiro:user>
            <div style="display: block;" class="logout1">
				 欢迎您：<i class="icon-user-circle-o">&nbsp;&nbsp;</i><shiro:principal property="name"/>
				<span>
					<a id="logout" href="javascript:;">退出</a><br>
					<a href="${ctx}/remind/list"><span id="reminds">0</span><i class="icon-envelope icon-large"></i></a>
				</span>
			</div>
            </shiro:user>
        </div>
    </div>
</nav>
<script>
    //导航栏
    $(document).ready(function() {
        var top_menu = $("#top_menu").val();
        if(top_menu == '首页'){
            $("#"+top_menu).addClass('menu_action');
        }
        var position = $(".position").text();
        if(position.indexOf('租赁包车') > 0){
            $("#租赁包车").addClass('menu_action');
        }
        if(position.indexOf('中心') > 0){
            $("#会员中心").addClass('menu_action');
        }
        var shiroUser = '<shiro:principal/>';
        if(shiroUser != null && '' != shiroUser){
        	setTimeout(function(){
        		remind();
        	}, 500);
        }
    });
    //url: 跳转路径  type: 0不需要登陆 1需要登陆
    function forward(url, type) {
        var shiroUser = '<shiro:principal/>';
        if(type == 0){
            window.location.href = url;
            return false;
        }
        if(shiroUser != null && '' != shiroUser){
            window.location.href = url;
        }else{
            fun_loging('${ctx}/login');
        }
    }
    //登陆
    $('.loging').click(function () {
        fun_loging('${ctx}/login');
    });
    //退出
    $('#logout').click(function () {
        top.location.href = "${ctx}/login/out";
    });
    //注册
    $('#register').click(function () {
        top.location.href = "${ctx}/register";
    });
    //显示提醒消息条数
    function remind(){
        $.ajax({
            type: "POST",
            url: "${ctx}/remind/statistics",
            data: {},
            dataType: "json",
            success: function(data){
                $('#reminds').html(data);
                var remindAmount = $("#提醒通知").text();
                if(remindAmount != null && '' != remindAmount){
                	$('#'+remindAmount).append("<span class='red'>("+data+")</span>");
                }
            }
        });
    }
</script>
