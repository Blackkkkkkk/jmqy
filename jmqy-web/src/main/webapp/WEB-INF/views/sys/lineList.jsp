<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>线路分析</title>
</head>
<body>
<form id="searchForm" action="" method="post" class="layui-form">
    <div class="button_right">
        <input type="text" name="orderCode" class="layui-input" placeholder="请输入关键字" value="">
        <button class="layui-btn layui-btn-small">搜索</button>
    </div>
    <div class="cl"></div>
    <table class="layui-table">
        <colgroup>
            <col width="60">
            <col width="200">
            <col width="200">
            <col width="250">
            <col width="250">
            <col width="150">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>序号</th>
            <th>起点</th>
            <th>终点</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>次数</th>
            <th>订单状态</th>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>
    <tags:page pages="${pageInfo}"/>
</form>
</body>
</html>