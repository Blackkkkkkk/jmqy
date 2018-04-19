<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>Title</title>
    <style type="text/css">
        .button_left label{float: left;}
        .button_left span{float: left;}

        .kdspan{
            float:right;
            background:#76838f;
            color:#fff;
            padding:4px 10px;
            margin-right: 5px
        }
        .lookp{
            float:right;
            color:#38baf4;
            margin-bottom:10px;
        }

    </style>
</head>
<body>
    <img src="${ctx}/static/images/${invoice.orderStatus}.jpg" alt="">
    <div class="cl"></div>
    <div class="kdspan" <c:if test="${invoice.orderStatus != 3}">style="display: none"</c:if>>
        快递名称：${invoice.courierCompany} 货运单号：${invoice.courierNumber}
    </div>
    <div class="cl"></div>
    <table class="layui-table">
        <tr>
            <td>发票类型</td>
            <td>
                <c:if test="${invoice.invoiceType == 1}">
                    电子发票
                </c:if>
                <c:if test="${invoice.invoiceType == 2}">
                    纸质增值税普票
                </c:if>
                <c:if test="${invoice.invoiceType == 3}">
                    纸质增值税专票
                </c:if>
            </td>
            <td>发票抬头</td>
            <td>
               ${invoice.invoiceTitle}
            </td>
        </tr>
        <tr>
            <td>发票总额</td>
            <td>${invoice.invoiceAmount}</td>
            <td>提交时间</td>
            <td><fmt:formatDate value="${invoice.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <td>收件人</td>
            <td>${invoice.recipient}</td>
            <td>联系方式</td>
            <td>${invoice.contactWay}</td>
        </tr>
        <tr>
            <td>收件地址</td>
            <td colspan="3">
                ${invoice.address}
            </td>

        </tr>
    </table>

</div>
<script>

</script>

</body>
</html>
