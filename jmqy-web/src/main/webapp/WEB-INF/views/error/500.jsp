<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%@ page session="false" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%response.setStatus(200);%>
<!DOCTYPE html>
<html>
<head>
	<title>无法显示此页</title>
	<script type="text/javascript">
	function back(){
        top.location.href = "${ctx}/index";
	}
	</script>
</head>
<body>
    <div id="contentContainer" class="mainContent" style="margin-top: 100px; margin-left: 200px;">
        <div id="mainTitle" class="title" style="font-size: 40px; color: #0099ff;">无法显示此页</div>
        <div class="taskSection" id="taskSection" style="margin-left: 40px;">
            <div id="task1-1" style="margin-top: 20px; margin-bottom: 20px;">很抱歉，系统出错啦。请记录下功能，并联系咨询系统管理员。请点击<a href="javascript:;" onclick="back();" style="color: red;">返回首页</a>。</div>
            <div id="task1-2" style="margin-top: 20px; margin-bottom: 20px;">使用搜索引擎查找页面。</div>
            <div id="task1-3" style="margin-top: 20px; margin-bottom: 20px;">请过几分钟后刷新页面。</div>
        </div>
    </div>
</body>
</html>
