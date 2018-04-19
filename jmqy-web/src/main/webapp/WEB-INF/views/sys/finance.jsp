<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>财务管理</title>
    <script>
        //type 5: 额度付款提醒
        function remind(type, ids) {


            $.ajax({
                type:"post",
                url:"${ctx}/sys/finance/remind",
                dataType:"json",
                data:{"ids": ids, "type": type},
                success:function(data) {
                    if(data.state){
                        layer.open({
                            icon: 1,
                            title: '信息',
                            skin: 'layer-ext-myskin',
                            shade: 0, //不显示遮罩
                            content: "提醒成功！",
                            yes: function(){
                                parent.refresh();
                            },
                        });
                    }else{
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
<form id="searchForm" action="${ctx}/sys/finance" method="post" class="layui-form">
    <input type="hidden" name="pageNum" value="${finance.pageNum}"/>
    <input type="hidden" name="pageSize" value="${finance.pageSize}"/>
    <div class="button_right">
        <input type="text" name="param" class="layui-input" placeholder="请输入关键字" value="${finance.param}">
        <button class="layui-btn layui-btn-small">搜索</button>
    </div>
    <div class="cl"></div>
    <table class="layui-table">
        <colgroup>
            <col width="60">
            <col width="270">
            <col width="270">
            <col width="270">
            <col width="270">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>序号</th>
            <th>企业名称</th>
            <th>应收(元)</th>
            <th>应付(元)</th>
            <th>佣金(元)</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${finances.list}" var="finance" varStatus="vs">
            <tr>
                <td>${vs.index+1}</td>
                <td>${finance.companyName}</td>
                <td>
                    <fmt:formatNumber value="${finance.amount}" pattern="#.##" minFractionDigits="2" />
                </td>
                <td>
                    <fmt:formatNumber value="${finance.payable}" pattern="#.##" minFractionDigits="2" />
                </td>
                <td>
                    <fmt:formatNumber value="${finance.commission}" pattern="#.##" minFractionDigits="2" />
                </td>
                <td style="white-space: nowrap;">
                    <c:if test="${finance.amount > 0}">
                        <a href="javascript:;" class="layui-btn layui-btn-danger layui-btn-mini" onclick="remind(5, '${finance.userId}');">提醒付款</a>
                    </c:if>
                    <c:if test="${!(finance.amount > 0)}">
                        <a href="javascript:;" class="layui-btn layui-btn-mini layui-btn-disabled">提醒付款</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <tags:page pages="${finances}"/>
</form>
</body>
</html>