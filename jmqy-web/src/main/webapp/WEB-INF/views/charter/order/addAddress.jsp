<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        table tr{
            line-height: 50px;
        }
        table td{
            color:#999;
        }
        table td:nth-child(odd){
            text-align:right;
            color:#333;
        }
    </style>
</head>
<body class="window">
<form class="layui-form" action="${ctx}/charter/invoice/Address/saveAddress" id="addressForm">
    <table width="80%">

        <tr>
            <td>姓名：</td>
            <td><input type="text" class="layui-input" name="recipient" id="recipient" value="${addressmap.recipient}"></td>
        </tr>
        <tr>
            <td>详细地址：</td>
            <td><input type="text" class="layui-input" name="address" id="address"  value="${addressmap.address}"/></td>
        </tr>
        <tr>
            <td>联系方式：</td>
            <td><input type="text" class="layui-input" name="contactWay" id="contactWay" value="${addressmap.contactWay}"/></td>
            <td><input type="hidden" class="layui-input" name="actionType" id="actionType"  value="${requestScope.actionType}"/></td>
            <td><input type="hidden" class="layui-input" name="id" id="id"  value="${addressmap.id}"/></td>
        </tr>
        <tr >

            <input type="submit" style="display: none" id="sub"/>
        </tr>
    </table>

</form>

<script type="text/javascript">

    $(document).ready(function () {


    })

    function sub() {
        $("#addressForm").submit();
    }




</script>

</body>
</html>
<script src="../images/layui.john.js"></script>
<script src="../images/john.js"></script>
