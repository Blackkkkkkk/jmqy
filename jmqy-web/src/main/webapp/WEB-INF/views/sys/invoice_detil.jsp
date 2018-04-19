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

        <tr>
            <td colspan="4" align="center" id="btn1">
                <a href="javascript:invoiceSubmit('${invoice.orderCode}',2);" class="layui-btn layui-btn-danger" lay-submit>审核通过</a>
            </td>
        </tr>

        <tr id="text1" style="display:none;">
            <td>快递公司</td>
            <td colspan="3">
                <input type="text" class="layui-input" name="courierCompany" id="courierCompany" value="${invoice.courierCompany}"/>
            </td>
        </tr>

        <tr id="text2" style="display:none;">
            <td>快递单号</td>
            <td colspan="3">
                <input type="text" class="layui-input" name="courierNumber" id="courierNumber" value="${invoice.courierNumber}"/>
            </td>

        </tr>

        <tr>
            <td colspan="4" align="center" id="btn2" style="display: none">
                <a href="javascript:invoiceSubmit('${invoice.orderCode}',3);" class="layui-btn layui-btn-danger" lay-submit>保存订单</a>
            </td>
        </tr>

    </table>


</div>
<script>

    $(document).ready(function () {
        if('${invoice.orderStatus}'=='2' || '${invoice.orderStatus}'=='3'){
            $("#btn1").attr("style", "display: none;")
            $("#btn2").attr("style", "display: block;")
            $("#text1").attr("style", "display: block;")
            $("#text2").attr("style", "display: block;")
        }
    })


    function  invoiceSubmit(orderCode,actiontype) {

        var courierCompany = $("#courierCompany").val();
        var courierNumber = $("#courierNumber").val();

        if(actiontype=='3'){
           if(courierCompany == '') {
               alert("快递公司不能为空！")
               return false;
           }
               if(courierNumber == ''){
                   alert("快递单号不能为空！")
                   return false;
               }
        }
        $.ajax({
            url:'${ctx}/sys/saveinvoice',
            type:'POST', //GET
            async:true,    //或false,是否异步
            data:{
                orderCode:orderCode,actiontype:actiontype,courierNumber:courierNumber,courierCompany:courierCompany
            },
            success:function(data){
                if(data.state=='success'){
                    if(data.actiontype=='2') {
                        layer.open({
                            content: '是否录入快递单号'
                            , btn: ['确定', '取消']
                            , yes: function (index, layero) {
                                //按钮【按钮一】的回调
                                layer.closeAll();
                                //lay-verify="required"
                                $("#btn1").attr("style", "display: none;")
                                $("#btn2").attr("style", "display: block;")
                                $("#text1").attr("style", "display: block;")
                                $("#text2").attr("style", "display: block;")

                            }
                            , btn2: function (index, layero) {
                                //按钮【按钮二】的回调
                                //return false 开启该代码可禁止点击该按钮关闭
                            }
                            , cancel: function () {
                                //右上角关闭回调
                                //return false 开启该代码可禁止点击该按钮关闭
                            }
                        });
                    }else{
                        parent.layer.closeAll();
                        parent.location.reload('${ctx}/sys/invoice');

                    }

                }


            }
        })

    }

</script>

</body>
</html>
