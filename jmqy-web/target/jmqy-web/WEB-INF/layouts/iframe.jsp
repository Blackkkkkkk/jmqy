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
    <script src="http://echarts.baidu.com/build/dist/echarts.js" type="text/javascript"></script>
    <script src="${ctx}/static/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <sitemesh:head />
</head>
<style>
    body {
        background-color:#ffffff;
    }
</style>
<body>
<sitemesh:body />
<script src="${ctx}/static/js/layui.john.js" type="text/javascript"></script>
<script src="${ctx}/static/js/platform.js" type="text/javascript"></script>
<script src="${ctx}/static/js/john.js" type="text/javascript"></script>
</body>
</html>