<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/layui/css/layui.css">
    <script src="${ctx}/static/js/jquery-3.2.1.min.js"></script>
    <script src="${ctx}/static/layui/layui.js"></script>
    <link rel="stylesheet" href="${ctx}/static/css/index.css">
    <link rel="stylesheet" href="${ctx}/static/css/platform.css">
    <link rel="stylesheet" href="${ctx}/static/css/style.css">
    <link rel="stylesheet" href="${ctx}/static/css/transport.css">
</head>
<body>

<form action="" class="layui-form">
    <input type="hidden" name="pageNum" value="${user.pageNum}"/>
    <input type="hidden" name="pageSize" value="${user.pageSize}"/>

    <div class="button_right">
        <input type="text" id="test6" class="layui-input input_date" placeholder="订单开始时间">
        <input type="text" id="tiime_john" class="layui-input input_date" placeholder="订单结束时间">
        <input type="text" class="layui-input" placeholder="请输入车牌号">
        <input type="text" class="layui-input" placeholder="请输入司机名字">
        <input type="text" class="layui-input" placeholder="请输入关键字"> <button class="layui-btn layui-btn-small">搜索</button>
    </div>
    <div class="cl"></div>


    <table class="layui-table">

        <thead>
        <tr>
            <th>订单号</th>
            <th>路线</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>司机</th>
            <th>车牌号</th>
            <th>订单状态</th>
            <th>线路偏差</th>
            <th>审核状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>DH-201801020914046_01</td>
            <td>文雅苑-江门第一中学</td>
            <td>2017-01-03 12:00:00</td>
            <td>2017-01-03 12:00:00</td>
            <td>司机</td>
            <td>粤A12345</td>
            <td>完成</td>
            <td>是</td>
            <td>已审核</td>
            <td><a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini examine">审核</a></td>
        </tr>
        <tr>
            <td>DH-201801020914046_01</td>
            <td>文雅苑-江门第一中学</td>
            <td>2017-01-03 12:00:00</td>
            <td>2017-01-03 12:00:00</td>
            <td>司机</td>
            <td>粤A12345</td>
            <td>完成</td>
            <td>是</td>
            <td>已审核</td>
            <td><a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini examine">审核</a></td>
        </tr>
        <tr>
            <td>DH-201801020914046_01</td>
            <td>文雅苑-江门第一中学</td>
            <td>2017-01-03 12:00:00</td>
            <td>2017-01-03 12:00:00</td>
            <td>司机</td>
            <td>粤A12345</td>
            <td>完成</td>
            <td>是</td>
            <td>已审核</td>
            <td><a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini examine">审核</a></td>
        </tr>
        <tr>
            <td>DH-201801020914046_01</td>
            <td>文雅苑-江门第一中学</td>
            <td>2017-01-03 12:00:00</td>
            <td>2017-01-03 12:00:00</td>
            <td>司机</td>
            <td>粤A12345</td>
            <td>完成</td>
            <td>是</td>
            <td>已审核</td>
            <td><a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini examine">审核</a></td>
        </tr>
        <tr>
            <td>DH-201801020914046_01</td>
            <td>文雅苑-江门第一中学</td>
            <td>2017-01-03 12:00:00</td>
            <td>2017-01-03 12:00:00</td>
            <td>司机</td>
            <td>粤A12345</td>
            <td>完成</td>
            <td>是</td>
            <td>已审核</td>
            <td><a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini examine">审核</a></td>
        </tr>
        <tr>
            <td>DH-201801020914046_01</td>
            <td>文雅苑-江门第一中学</td>
            <td>2017-01-03 12:00:00</td>
            <td>2017-01-03 12:00:00</td>
            <td>司机</td>
            <td>粤A12345</td>
            <td>完成</td>
            <td>是</td>
            <td>已审核</td>
            <td><a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini">审核</a></td>
        </tr>
        <tr>
            <td>DH-201801020914046_01</td>
            <td>文雅苑-江门第一中学</td>
            <td>2017-01-03 12:00:00</td>
            <td>2017-01-03 12:00:00</td>
            <td>司机</td>
            <td>粤A12345</td>
            <td>完成</td>
            <td>是</td>
            <td>已审核</td>
            <td><a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini">审核</a></td>
        </tr>
        <tr>
            <td>DH-201801020914046_01</td>
            <td>文雅苑-江门第一中学</td>
            <td>2017-01-03 12:00:00</td>
            <td>2017-01-03 12:00:00</td>
            <td>司机</td>
            <td>粤A12345</td>
            <td>完成</td>
            <td>是</td>
            <td>已审核</td>
            <td><a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini">审核</a></td>
        </tr>



        </tbody>
    </table>


</form>
<script>
    function examine(){
        parent.layer.open({
            type: 2,
            title: '审核信息',
            shadeClose: true,
            shade: 0.5,
            area: ['1230px','600px'],
            content: '${ctx}/sys/routeExamine'
        })
    }
    $('.examine').click(examine)
</script>


</body>
</html>
<script src="${ctx}/static/js/layui.john.js"></script>
<script src="${ctx}/static/js/john.js"></script>
