<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>权限管理</title>
    <style type="text/css">
        .button_left {
            margin-top: 0px;
        }
    </style>
    <script>
        function examine(userId, companyName) {
            parent.layer.open({
                type: 2,
                title: "【" + companyName + '】审核信息',
                shadeClose: true,
                shade: 0.1,
                btn: ['同意', '拒绝'],
                btn1: function (index, layero) {
                    var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
                    iframe.doExamine(0, 2);
                },
                btn2: function (index, layero) {
                    var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
                    iframe.doExamine(2, null);
                    return false;
                },
                skin: "layui-layer-molv",
                area: ["750px", "500px"],
                content: "${ctx}/permission/examine?id=" + userId
            })
        }
    </script>
</head>
<body>
<form id="searchForm" action="${ctx}/money/MoneyDetail" method="post" class="layui-form">

    <input type="hidden" name="pageNum" value="${money.pageNum}"/>
    <input type="hidden" name="pageSize" value="${money.pageSize}"/>
    <input type="hidden" name="companyCode" value="${money.companyCode}">
    <input type="hidden" name="actionTypa" value="${money.actionTypa}">
    <div class="button_left">
        <label class="layui-form-label">消费类型:</label>
        <div class="layui-input-block">
            <input type="radio" name="TabNum" value="1" title="消费情况"
                   <c:if test="${TabNum == 1}">checked</c:if> lay-filter="companyType">
            <input type="radio" name="TabNum" value="2" title="结算情况 "
                   <c:if test="${TabNum == 2}">checked</c:if> lay-filter="companyType">
            <input type="radio" name="TabNum" value="3" title="调整情况"
                   <c:if test="${TabNum == 3}">checked</c:if> lay-filter="companyType">
        </div>
    </div>
    <div class="button_right">
        <input type="text" name="param" class="layui-input" placeholder="请输入关键字" value="${money.param}">
        <button id="submitBtn" class="layui-btn layui-btn-small">搜索</button>
    </div>
    <c:if test="${TabNum == 1}">
        <div class="cl"></div>
        <table class="layui-table">
            <colgroup>
                <col width="150">
                <col width="300">
                <col width="250">
                <col width="250">
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>订单号</th>
                <th>订单金额</th>
                <th>操作用户</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${moneys.list}" var="money" varStatus="vs">
                <tr>
                    <td>${vs.index+1}</td>
                    <td>${money.orderCode}</td>
                    <td>
                            ${money.money}
                    </td>
                    <td> ${money.userAccount}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <tags:page pages="${moneys}"/>
    </c:if>


    <c:if test="${TabNum == 2}">
        <div class="cl"></div>
        <table class="layui-table">
            <colgroup>
                <col width="60">
                <col width="300">
                <col width="250">
                <col width="250">
                <col width="250">
                <col width="250">
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>时间</th>
                <th>操作用户</th>
                <th>调整前</th>
                <th>调整额</th>
                <th>调整后</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${moneyDetilBillPageInfo.list}" var="money" varStatus="vs">
                <tr>
                    <td>${vs.index+1}</td>
                    <td>
                        <fmt:formatDate value="${money.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>
                            ${money.userAccount}
                    </td>
                    <td> ${money.moneyBefore}</td>
                    <td> ${money.money}</td>
                    <td> ${money.moneyAfter}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <tags:page pages="${moneyDetilBillPageInfo}"/>
    </c:if>


    <c:if test="${TabNum == 3}">

        <div class="cl"></div>
        <table class="layui-table">
            <colgroup>
                <col width="60">
                <col width="300">
                <c:if test="${money1.actionTypa != 2}">
                    <col width="250">
                </c:if>
                <col width="250">
                <col width="250">
                <col width="250">
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>时间</th>
                <c:if test="${money1.actionTypa != 2}">
                    <th>操作用户</th>
                </c:if>
                <th>调整前</th>
                <th>调整额</th>
                <th>调整后</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${moneyDetilBillPageInfo.list}" var="money" varStatus="vs">
                <tr>
                    <td>${vs.index+1}</td>
                    <td>
                        <fmt:formatDate value="${money.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <c:if test="${money1.actionTypa != 2}">
                        <td>
                                ${money.userAccount}
                        </td>
                    </c:if>

                    <td> ${money.moneyBefore}</td>
                    <td> ${money.money}</td>
                    <td> ${money.moneyAfter}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <tags:page pages="${moneyDetilBillPageInfo}"/>
    </c:if>


</form>
<script type="text/javascript">
    //提交搜索表单
    function selfSubmit() {
        $("#searchForm").submit();
    }


    var radiovaLue;
    layui.use(['form', 'element', 'laydate'], function () {

        var form = layui.form;

        form.on('radio(companyType)', function (data) {
            //console.log(data.elem); //得到radio原始DOM对象
            //console.log(data.value); //被点击的radio的value值
            var type = '${TabNum}';
            if (type != data.value) {
                $("[name='pageNum']").val(1);//第一页
            }
            $('#submitBtn').click();

        });
    });


</script>
</body>
</html>