<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>积分管理</title>
    <script>
        //type 3: 积分过期提醒
        function remind(type, ids) {
            $.ajax({
                type:"post",
                url:"${ctx}/score/remind",
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
<form id="searchForm" action="${ctx}/score/userList" method="post" class="layui-form">
    <input type="hidden" name="pageNum" value="${score.pageNum}"/>
    <input type="hidden" name="pageSize" value="${score.pageSize}"/>
    <div class="button_right">
        <input type="text" name="param" class="layui-input" placeholder="请输入关键字" value="${score.param}">
        <button class="layui-btn layui-btn-small">搜索</button>
    </div>
    <div class="cl"></div>
    <table class="layui-table">
        <colgroup>
            <col width="60">
            <col width="270">
            <col width="270">
            <col width="180">
            <col width="180">
            <col width="180">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>序号</th>
            <th>企业名称</th>
            <th>企业账号</th>
            <th>总积分</th>
            <th>已消费积分</th>
            <th>即将过期积分</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${scores.list}" var="score" varStatus="vs">
            <tr>
                <td>${vs.index+1}</td>
                <td>${score.companyName}</td>
                <td>${score.userAccount}</td>
                <td>
                    <fmt:formatNumber value="${score.totalScore}" pattern="#.##" minFractionDigits="2" />
                </td>
                <td>
                    <fmt:formatNumber value="${score.consumeScore}" pattern="#.##" minFractionDigits="2" />
                </td>
                <td>
                    <fmt:formatNumber value="${score.expireScore}" pattern="#.##" minFractionDigits="2" />
                </td>
                <td style="white-space: nowrap;">
                    <a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini" onclick="fun_plat4('${score.userId}', '${score.companyName}', '${ctx}/score/setScore');">积分奖励</a>
                    <c:if test="${score.expireScore > 0}">
                        <a href="javascript:;" class="layui-btn layui-btn-danger layui-btn-mini" onclick="remind(3, '${score.userId}')">过期提醒</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <tags:page pages="${scores}"/>
</form>
</body>
</html>