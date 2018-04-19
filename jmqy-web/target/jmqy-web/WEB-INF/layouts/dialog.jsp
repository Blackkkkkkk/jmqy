<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>马上走-<sitemesh:title /></title>
<!-- css -->
<link href="${ctx}/static/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="${ctx}/static/layui/css/layui.css" rel="stylesheet" type="text/css">
<link href="${ctx}/static/css/style.css" rel="stylesheet" type="text/css">
<link href="${ctx}/static/css/transport.css" rel="stylesheet" type="text/css">
<!-- js -->
<script src="${ctx}/static/js/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="${ctx}/static/layui/layui.js" type="text/javascript"></script>
<script src="${ctx}/static/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

<!-- dwr -->
<script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
<script type="text/javascript" src="${ctx}/dwr/util.js"></script>
<script type="text/javascript" src="${ctx}/dwr/interface/MessagePush.js"></script>
<sitemesh:head />
<style>
.anchorBL{display: none !important;}
</style>
<script type="text/javascript">
//dwr
function onPageLoad(){
	MessagePush.onPageLoad('<%=session.getAttribute("userId")%>');
}
</script>
</head>
<body onload="dwr.engine.setActiveReverseAjax(true);dwr.engine.setNotifyServerOnPageUnload(true);onPageLoad();">
<sitemesh:body />
<script src="${ctx}/static/js/layui.john.js" type="text/javascript"></script>
<script src="${ctx}/static/js/john.js" type="text/javascript"></script>
<script src="${ctx}/static/js/index.js" type="text/javascript"></script>
</body>
</html>