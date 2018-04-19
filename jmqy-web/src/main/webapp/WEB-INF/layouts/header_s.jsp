<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<header>
    <div class="logo_pla">
        <h1><img src="${ctx}/static/images/logo_s.png" style="cursor: pointer;" onclick="window.location.href='${ctx}/main'"/>马上走平台管理系统</h1>
    </div>
    <div class="header_right">
        <span>欢迎光临，<i class="icon-user-circle-o">&nbsp;&nbsp;</i><shiro:principal property="name"/></span>
        <a href="${ctx}/login/out"><i class="icon-off"></i>退出</a>
        <a href="${ctx}/index" style="margin-left: 10px;"><i class="icon-mail-reply"></i>网站</a>
    </div>
</header>
