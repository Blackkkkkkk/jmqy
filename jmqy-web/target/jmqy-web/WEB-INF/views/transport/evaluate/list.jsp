<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>评价分析</title>
</head>
<body>
<section>
    <div class="container">
        <jsp:include page="/WEB-INF/layouts/left_menu.jsp"/>
        <main>
            <div class="position_all">
                <div class="position">您所在的位置：首页 -> 管理中心 -> 评价分析</div>
            </div>
            <div class="info">
                <form id="searchForm" class="layui-form" action="${ctx}/transport/evaluate/list" method="post">
                    <input type="hidden" name="pageNum" value="${evaluate.pageNum}"/>
                    <input type="hidden" name="pageSize" value="${evaluate.pageSize}"/>
                    <input type="hidden" id="order" name="order" value="${evaluate.order}"/>
                    <input type="hidden" id="sortType" name="sortType" value="${evaluate.sortType}"/>
                    <div class="button_right john-edge">
                        <div class="layui-inline">
                            <label class="layui-form-label">评价时间:</label>
                            <div class="layui-input-inline" style="top: 5px;">
                                <input class="layui-input search_text input_date" id="beginTime" name="beginTime"
                                       type="text" value='${evaluate.beginTime}'
                                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>_
                            </div>
                            <div class="layui-input-inline" style="top: 5px;">
                                <input class="layui-input search_text input_date" id="endTime" name="endTime"
                                       type="text" value='${evaluate.endTime}'
                                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <input type="text" name="carNum" value="${evaluate.carNum}" class="layui-input"
                                   placeholder="请输车牌号">
                            <a class="layui-btn layui-btn-danger layui-btn-small" href="javascript:selfSubmit();">搜索</a>
                        </div>
                    </div>
                    <div class="cl"></div>
                    <div class="layui-form">
                        <table class="layui-table">
                            <colgroup>
                                <col width="100">
                                <col width="150">
                                <col width="100">
                                <col width="100">
                                <col width="100">
                                <col width="120">
                                <col width="300">
                                <col width="100">
                                <col width="100">
                                <col width="100">
                                <col>
                            </colgroup>
                            <thead>
                            <tr>
                                <th><a id="c.company_name" onclick="sort(this);" style="cursor: pointer;">客户</a></th>
                                <th><a id="e.order_code" onclick="sort(this);" style="cursor: pointer;">订单号</a></th>
                                <th><a id="d.name" onclick="sort(this);" style="cursor: pointer;">司机</a></th>
                                <th><a id="d.job_num" onclick="sort(this);" style="cursor: pointer;">工号</a></th>
                                <th><a id="o.car_num" onclick="sort(this);" style="cursor: pointer;">车辆</a></th>
                                <th><a id="e.create_time" onclick="sort(this);" style="cursor: pointer;">时间</a></th>
                                <th><a id="e.to_car_score" onclick="sort(this);" style="cursor: pointer;">投诉内容</a></th>
                                <th>司机评价分</th>
                                <th>车辆评价分</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${evaluates.list}" var="evaluate" varStatus="status">
                                <tr>
                                    <td>${evaluate.charterName}</td>
                                    <td><a href="javascript:detail('${evaluate.id}');"
                                           style="color: orange;"> ${evaluate.orderCode}</a></td>
                                    <td>${evaluate.driverName}</td>
                                    <td>${evaluate.jobNum}</td>
                                    <td>${evaluate.carNum}</td>
                                    <td><fmt:formatDate pattern="yyyy-MM-dd" value="${evaluate.createTime}"/></td>
                                    <td>对司机：${evaluate.toDriverContent}，对车辆：${evaluate.toCarContent}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${evaluate.toDriverScore != null }">
                                                ${evaluate.toDriverScore}分
                                            </c:when>
                                            <c:otherwise>
                                                暂未评价！
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${evaluate.toCarScore != null }">
                                                ${evaluate.toCarScore}分
                                            </c:when>
                                            <c:otherwise>
                                                暂未评价！
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a href="javascript:detail('${evaluate.id}');"
                                           class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-edit"></i>
                                            详情</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </form>
            </div>
            <tags:page pages="${evaluates}"/>
        </main>
    </div>
</section>
<script type="text/javascript">
    //文档加载完毕执行
    $(function () {
        var message = '${message}';
        if (message != null && "" != message) {
            var msg = message.split("@");
            setTimeout(function () {
                layer.msg(msg[0], {icon: msg[1]});
            }, 500);
        }
    });

    //提交搜索表单
    function selfSubmit() {
        $("#searchForm").submit();
    }

    //跳到第一页
    function selfSearch() {
        $("[name='pageNum']").val(1);//第一页
        selfSubmit();
    }

    //查看订单评价详情
    function detail(id) {
        var width = "900px";
        var height = "250px;"
        var title = "订单评价详情";
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
            content: '${ctx}/transport/evaluate/detail?id=' + id
        })
    }
</script>
</body>
</html>