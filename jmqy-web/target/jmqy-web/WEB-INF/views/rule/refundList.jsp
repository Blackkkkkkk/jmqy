<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>平台设置</title>
    <style type="text/css">
        table tr th, table td{
            text-align: center;
        }
    </style>
</head>
<body>
<form id="searchForm" action="${ctx}/rule/ruleList" method="post" class="layui-form">
    <input type="hidden" name="pageNum" value="${rule.pageNum}"/>
    <input type="hidden" name="pageSize" value="${rule.pageSize}"/>
    <div class="button_left">
        <a href="javascript:save()" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-plus-sign"></i> 新增</a>
    </div>
    <div class="button_right">
        <input type="text" name="ruleValue" class="layui-input" placeholder="请输入关键字" value="${rule.ruleValue}">
        <button class="layui-btn layui-btn-small">搜索</button>
    </div>
    <div class="cl"></div>
    <table class="layui-table">
        <colgroup>
            <col width="60">
            <col width="500">
            <col width="350">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th style="text-align: center;">序号</th>
            <th style="text-align: center;">方案名称</th>
            <th style="text-align: center;">企业类型</th>
            <th style="text-align: center;">企业名称</th>
            <th style="text-align: center;">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${rules.list}" var="rule" varStatus="vs">
            <tr>
                <td>${vs.index+1}</td>
                <td>
                        ${rule.ruleValue}
                </td>
                <td>
                    <c:if test="${rule.type ==0}">
                        平台方
                    </c:if>
                    <c:if test="${rule.type ==1}">
                        个人包主
                    </c:if>
                    <c:if test="${rule.type ==2}">
                        包车企业
                    </c:if>
                    <c:if test="${rule.type ==3}">
                        运输企业
                    </c:if>
                </td>
                <td style="white-space: nowrap;">
                        ${rule.companyName}
                </td>
                <td>
                    <a href="${ctx}/rule/refundDetail?id=${rule.id}" class="layui-btn layui-btn-primary layui-btn-mini refunXQ">详情</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <tags:page pages="${rules}"/>
</form>
<script>



    function save(){

        parent.layer.open({
            type: 2,
            title: '退款方案新增',
            shadeClose: true,
            shade: 0.1,
            btn: ['确定', '取消'],
            yes: function(index, layero){
                var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象

              iframe.save(index);

            },
            skin: "layui-layer-molv",
            area: ['500px','450px'],
            content: '${ctx}/rule/refundSavePage',
            end: function(){
                window.location.href='${ctx}/rule/refund';
            }
        })

    }


</script>
</body>
</html>