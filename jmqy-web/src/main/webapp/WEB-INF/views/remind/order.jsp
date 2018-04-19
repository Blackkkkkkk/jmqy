<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>订单提醒</title>
    <script>
        //type 1: 批量  2：单个
        function remind(type, ids) {
            $.ajax({
                type: "post",
                url: "${ctx}/remind/remind",
                dataType: "json",
                data: {"ids": ids, "type": type},
                success: function (data) {
                    if (data.state) {
                        layer.msg('提醒成功！', {
                            time: 1000,
                            skin: 'layui-layer-molv',
                            area: '20px',
                            icon: 1
                        }, function () {
                        });
                    } else {
                        layer.open({
                            icon: 2,
                            title: '信息',
                            skin: 'layer-ext-myskin',
                            shade: 0, //不显示遮罩
                            content: "提醒失败！"
                        });
                    }
                }
            });
        }
    </script>
</head>
<body>
<form id="searchForm" action="${ctx}/remind/order" method="post" class="layui-form">
    <input type="hidden" name="pageNum" value="${order.pageNum}"/>
    <input type="hidden" name="pageSize" value="${order.pageSize}"/>
    <input type="hidden" id="order" name="order" value="${order.order}"/>
    <input type="hidden" id="sortType" name="sortType" value="${order.sortType}"/>
    <input type="hidden" name="remindType" value="${order.remindType}"/>
    <div class="button_left">
        <!-- <a class="layui-btn layui-btn-primary layui-btn-mini" href="javascript:;" onclick="remind(1, null)">批量提醒</a> -->
    </div>
    <div class="button_right">
        <div class="layui-inline">
            <label class="layui-form-label">出发日期：</label>
            <div class="layui-input-inline" style="top: 5px;">
                <input class="layui-input search_text input_date" id="beginTime" name="beginTime" type="text"
                       value='<fmt:formatDate value="${order.beginTime}" pattern="yyyy-MM-dd"/>'
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})"/>_
            </div>
            <div class="layui-input-inline" style="top: 5px;">
                <input class="layui-input search_text input_date" id="endTime" name="endTime" type="text"
                       value='<fmt:formatDate value="${order.endTime}" pattern="yyyy-MM-dd"/>'
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginTime\')}'})"/>
            </div>
        </div>
        <div class="layui-inline">
            <input type="text" name="param" class="layui-input" placeholder="请输入关键字" value="${order.param}">
        </div>
        <button class="layui-btn layui-btn-small" id="submitBtn">搜索</button>
    </div>
    <div class="cl"></div>
    <table class="layui-table">
        <colgroup>
            <col width="50">
            <col width="180">
            <col width="160">
            <col width="160">
            <col width="200">
            <col width="200">
            <col width="170">
            <col width="100">
            <col width="120">
            <col width="100">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th><input name="" lay-skin="primary" lay-filter="allChoose" type="checkbox"></th>
            <th><a id="order_code" onclick="sort(this);" style="cursor: pointer;">订单号</a></th>
            <th><a id="u.user_account" onclick="sort(this);" style="cursor: pointer;">包车方</a></th>
            <th><a id="c.company_name" onclick="sort(this);" style="cursor: pointer;">运输方</a></th>
            <th><a id="start_point" onclick="sort(this);" style="cursor: pointer;">上车地点</a></th>
            <th><a id="end_point" onclick="sort(this);" style="cursor: pointer;">下车地点</a></th>
            <th><a id="boarding_time" onclick="sort(this);" style="cursor: pointer;">上车时间</a></th>
            <th><a id="t.status" onclick="sort(this);" style="cursor: pointer;">订单状态</a></th>
            <th><a id="payment_status" onclick="sort(this);" style="cursor: pointer;">支付情况</a></th>
            <th><a id="settlement" onclick="sort(this);" style="cursor: pointer;">结算情况</a></th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders.list}" var="info" varStatus="vs">
            <tr>
                <td>
                    <input name="singleCheck" lay-skin="primary" lay-filter="singleChoose" type="checkbox"/>
                </td>
                <td><a href="javascript:query('${info.orderCode}',1);" style="color: orange;">${info.orderCode}</a></td>
                <td>${info.placerAccount}</td>
                <td>${info.companyName}</td>
                <td>${info.startPoint}</td>
                <td>${info.endPoint}</td>
                <td><fmt:formatDate value="${info.boardingTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>
                    <c:choose>
                        <c:when test="${info.status == -4}">
                            <c:choose>
                                <c:when test="${info.refundStatus ==1 }">
                                    已退款
                                </c:when>
                                <c:when test="${info.refundStatus ==0}">
                                    退款中
                                </c:when>
                                <c:otherwise>
                                    已取消
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:when test="${info.status == -3}">被拒绝</c:when>
                        <c:when test="${info.status == -2}">待匹配</c:when>
                        <c:when test="${info.status == -1}">匹配中</c:when>
                        <c:when test="${info.status == 0}">待确定</c:when>
                        <c:when test="${info.status == 1}">等待上车</c:when>
                        <c:when test="${info.status == 2}">在途</c:when>
                        <c:when test="${info.status == 3}">已完成</c:when>
                        <c:when test="${info.status == 4}">支付超时</c:when>
                        <c:when test="${info.status == 5}">待出发</c:when>
                        <c:when test="${info.status == 6}">退款</c:when>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${info.refundMoney !=null && info.refundMoney !=''}">
                               扣除手续费：${info.refundMoney}
                        </c:when>
                        <c:otherwise>
                            <c:if test="${info.paymentStatus == 1 && info.paymentType == 1}">未支付-现付</c:if>
                            <c:if test="${info.paymentStatus == 1 && info.paymentType == 2}">未支付-月结</c:if>
                            <c:if test="${info.paymentStatus == 2}">已支付</c:if>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:if test="${info.settlement == 1}">未结算</c:if>
                    <c:if test="${info.settlement == 2}">已结算</c:if>
                </td>
                <td>
                    <c:if test="${info.paymentStatus == 2 && info.settlement == 1 }">
                        <a href="javascript:;" class="layui-btn layui-btn-danger layui-btn-mini"
                           onclick="Settlement('${info.id}')">结算</a>
                    </c:if>
                    <c:if test="${info.status == -1}">
                        <a href="javascript:;" class="layui-btn layui-btn-danger layui-btn-mini"
                           onclick="remind(0, '${info.orderPlacer}@${info.id}')">提醒支付</a>
                    </c:if>
                    <c:if test="${not empty info.orderReceiver && (info.status == 0 || info.status == 1)}">
                        <a href="javascript:;" class="layui-btn layui-btn-danger layui-btn-mini"
                           onclick="remind(1, '${info.orderReceiver}@${info.id}')">提醒执行</a>
                    </c:if>
                    <c:if test="${empty info.orderReceiver && (info.status == 0 || info.status == 1)}">
                        <a href="javascript:;" class="layui-btn layui-btn-danger layui-btn-mini"
                           onclick="remind(1, '${info.transporterCode}@${info.id}')">提醒执行</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <tags:page pages="${orders}"/>
</form>
<script type="text/javascript">
    function query(orderCode, type) {
        var width = "900px";
        var height = "350px;"
        var title = "订单详情";
        if (type == 1) {
            height = "350px;"
            title = "订单详情";
        } else if (type == 3) {
            height = "560px;"
            title = "订单投诉";
        }
        layer.open({
            type: 2,
            title: title,
            shadeClose: true,
            shade: 0.5,
            yes: function (index, layero) {
                var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
                iframeWin.laySubmit();//执行iframe页的方法：
            },
            area: [width, height],
            content: '${ctx}/charter/order/query?orderCode=' + orderCode + '&type=' + type
        })


    }


    //订单结算
    function Settlement(id) {
        $.ajax({
            type: "post",
            url: "${ctx}/sys/Settlement",
            dataType: "json",
            data: {id: id},
            success: function (data) {
                if (data == '1') {
                    layer.msg('结算成功！', {icon: 0});
                    setTimeout(function () {
                        $('#submitBtn').click()
                    }, 1000)

                }
            }
        });
    }

</script>
</body>
</html>