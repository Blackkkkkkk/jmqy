<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>信用管理</title>
</head>
<body>
<form id="searchForm" action="${ctx}/credit/userList" method="post" class="layui-form">
    <input type="hidden" name="pageNum" value="${credit.pageNum}"/>
    <input type="hidden" name="pageSize" value="${credit.pageSize}"/>
    <%-- <div class="button_left">
    	<div class="layui-form-item">
	    <label class="layui-form-label">企业类型:</label>
	    <div class="layui-input-block">
	      <input type="radio" name="companyType" value="1" title="个人包主" <c:if test="${credit.companyType == 1}">checked</c:if> lay-filter="companyType">
	      <input type="radio" name="companyType" value="2" title="包车企业 " <c:if test="${credit.companyType == 2}">checked</c:if> lay-filter="companyType">
	      <input type="radio" name="companyType" value="3" title="运输企业" <c:if test="${credit.companyType == 3}">checked</c:if> lay-filter="companyType">
	    </div>
	  </div>
	</div> --%>
    <div class="button_right">
        <input type="text" name="param" class="layui-input" placeholder="请输入关键字" value="${credit.param}">
        <button id="submitBtn" class="layui-btn layui-btn-small">搜索</button>
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
            <th>账号</th>
            <th>企业名称</th>
            <th>总信用额度(元)</th>
            <th>已消费额度(元)</th>
            <th>剩余额度(元)</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${credits.list}" var="credit" varStatus="vs">
            <tr>
                <td>${vs.index+1}</td>
                <td>${credit.userAccount}</td>
                <td><a href="javascript:;" style="color: #1E9FFF;" onclick="detail('${credit.companyCode}');">${credit.companyName}</a></td>
                <td>
                    <fmt:formatNumber value="${credit.totalCredit}" pattern="#.##" minFractionDigits="2" />
                </td>
                <td>
                    <fmt:formatNumber value="${credit.consumeCredit}" pattern="#.##" minFractionDigits="2" />
                </td>
                <td>
                    <fmt:formatNumber value="${credit.stockCredit}" pattern="#.##" minFractionDigits="2" />
                </td>
                <td style="white-space: nowrap;">
                    <a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini plat2_a" onclick="set('${credit.userId}','${credit.companyCode}','${credit.companyName}','${ctx}/credit/setCredit')">调整</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <tags:page pages="${credits}"/>
</form>
<script>
    function detail(companyCode){

        layer.open({
            type: 2,
            title: '用户评价',
            shadeClose: true,
            shade: 0.5,
            area: ['90%', '80%'],
            content: "${ctx}/credit/CreditDetail?companyCode=" + companyCode
        })

    }


    /***信用管理***/
    function set(ids,companyCode,companyName, url){

        parent.layer.open({
            type: 2,
            title: '调整【'+companyName+'】信用额度',
            shadeClose: true,
            shade: 0.1,
            btn: ['确定', '取消'],
            yes: function(index, layero){
                var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
                iframe.$("#submit_btn").click();
            },
            skin: "layui-layer-molv",
            area: ['500px','400px'],
            content: url+"?userId="+ids+"&companyCode="+companyCode
        })
    }

</script>
</body>
</html>