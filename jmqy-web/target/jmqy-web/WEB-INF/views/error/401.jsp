<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="NewErrorPageTemplate.css" >
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>无法显示此页</title>
</head>
<body>
    <div id="contentContainer" class="mainContent" style="margin-top: 100px; margin-left: 200px;">
        <div id="mainTitle" class="title" style="font-size: 40px; color: #0099ff;">无法显示此页</div>
        <div class="taskSection" id="taskSection" style="margin-left: 40px;">
            <div id="task1-1" style="margin-top: 20px; margin-bottom: 20px;">很抱歉，登录超时或未登录，请重新登录。</div>
            <div id="task1-2" style="margin-top: 20px; margin-bottom: 20px;">使用搜索引擎查找页面。</div>
            <div id="task1-3" style="margin-top: 20px; margin-bottom: 20px;">请过几分钟后刷新页面。</div>
        </div>
    </div>
</body>
</html>
