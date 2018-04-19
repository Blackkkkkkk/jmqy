<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <title>马上走-<sitemesh:title /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
    <meta name="referrer" content="always">
    <link rel="icon" href="${ctx}/static/images/favicon.ico" type="image/x-icon">
    <!-- css -->
    <link href="${ctx}/static/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/static/layui/css/layui.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/static/css/style.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/static/css/platform.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/static/css/transport.css" rel="stylesheet" type="text/css">
    <!-- js -->
    <script src="${ctx}/static/js/jquery-3.2.1.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/layui/layui.js" type="text/javascript"></script>

    <!-- dwr -->
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/interface/MessagePush.js"></script>
    <sitemesh:head />
    <style>
        body {
            overflow: hidden;
        }
    </style>
</head>
<body onload="dwr.engine.setActiveReverseAjax(true);dwr.engine.setNotifyServerOnPageUnload(true);onPageLoad();">
<%@ include file="/WEB-INF/layouts/header_s.jsp"%>
<sitemesh:body />
<%@ include file="/WEB-INF/layouts/footer.jsp"%>
<script src="${ctx}/static/js/layui.john.js" type="text/javascript"></script>
<script src="${ctx}/static/js/platform.js" type="text/javascript"></script>


</body>
<script type="text/javascript">
    //dwr
    function onPageLoad(){
        MessagePush.onPageLoad('<%=session.getAttribute("userId")%>');
      //  alert('<%=session.getAttribute("userId")%>')
    }
    function logout(){
        alert("您的账号在另一个地点登录，您被迫下线。\n\n如果不是您本人的操作，那么您的密码很可\n 能已泄露。\n 建议您修改密码。");
        top.location.href = "${ctx}/login/out";
    }
</script>
</html>