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
        <a href="javascript:fun_plat7('','${ctx}/rule/form')" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-plus-sign"></i> 新增</a>
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
            <th style="text-align: center;">系数类型</th>
            <th style="text-align: center;">系数值</th>
            <th style="text-align: center;">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${rules.list}" var="rule" varStatus="vs">
            <tr>
                <td>${vs.index+1}</td>
                <td>${rule.ruleValue}</td>
                <td>${rule.coefficient}</td>
                <td style="white-space: nowrap;">
                    <c:if test="${rule.ruleType == 1}">
                        <a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini plat2_a" onclick="fun_plat7('${rule.id}','${ctx}/rule/form')">出发地调整</a>
                    </c:if>
                    <c:if test="${rule.ruleType == 10}">
                        <a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini plat2_a" onclick="fun_plat7('${rule.id}','${ctx}/rule/form')">目的地调整</a>
                    </c:if>
                    <c:if test="${rule.ruleType == 1}">
                    <a href="${ctx}/rule/detail/${rule.id}" class="layui-btn layui-btn-primary layui-btn-mini plat2_a">详情</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <tags:page pages="${rules}"/>
</form>
<script>
function update_(id) {
    var coefficient = $("#"+id).val();
    if(!/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.exec(coefficient)){
        layer.msg("请输入正确的系数！", {icon: 5}, function () {
            parent.refresh();
        });
    }else{
        $.ajax({
            type:"post",
            url:"${ctx}/rule/doSetRule",
            dataType:"json",
            data:{"id": id, "coefficient": coefficient},
            success:function(data) {
                if(data.state){
                    layer.open({
                        icon: 1,
                        title: '信息',
                        skin: 'layer-ext-myskin',
                        shade: 0, //不显示遮罩
                        content: "设置成功！",
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
                        content: "设置失败！"
                    });
                }
            }
        });
    }
}
</script>
</body>
</html>