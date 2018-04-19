<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>订单统计</title>
</head>
<body>
<form id="searchForm" action="${ctx}/sys/statistic/order" method="post" class="layui-form">
    <input type="hidden" name="pageNum" value="${order.pageNum}"/>
    <input type="hidden" name="pageSize" value="${order.pageSize}"/>
    <div class="button_right">
        <input type="text" name="orderCode" class="layui-input" placeholder="请输入关键字" value="${order.orderCode}">
        <button class="layui-btn layui-btn-small">搜索</button>
    </div>
    <div class="cl"></div>
    <table class="layui-table">
        <colgroup>
            <col width="60">
            <col width="150">
            <col width="150">
            <col width="150">
            <col width="150">
            <col width="150">
            <col width="150">
            <col width="150">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>序号</th>
            <th>订单号</th>
            <th>起点</th>
            <th>终点</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>运输企业</th>
            <th>车牌</th>
            <th>价格</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders.list}" var="info" varStatus="vs">
            <tr>
                <td>${vs.index+1}</td>
                <td><a href="javascript:query('${info.orderCode}',1);" style="color: orange;">${info.orderCode}</a></td>
                <td>${info.startPoint}</td>
                <td>${info.endPoint}</td>
                <td>
                    <fmt:formatDate value="${info.boardingTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td>
                    <fmt:formatDate value="${info.reserveOffTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td>${info.companyName}</td>
                <td>${info.carNum}</td>
                <td>
                    <fmt:formatNumber value="${info.amount}" pattern="#.##" minFractionDigits="2" />
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <tags:page pages="${orders}"/>
</form>
<script type="text/javascript">

    function query(orderCode,type){
        var width ="900px";
        var height = "350px;"
        var title ="订单详情";
        if(type ==1){
            height = "350px;"
            title ="订单详情";
        }else if(type ==3){
            height = "560px;"
            title ="订单投诉";
        }
        layer.open({
            type: 2,
            title: title,
            shadeClose: true,
            shade: 0.5,
            yes: function(index, layero){
                var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
                iframeWin.laySubmit();//执行iframe页的方法：
            },
            area: [width,height],
            content: '${ctx}/charter/order/query?orderCode='+orderCode+'&type='+type
        })
    }

</script>
</body>
</html>
