<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>额度设置</title>
    <style>
        body{
            min-width: 0;
        }
        .plat_box{
            margin-top: 50px;
            margin-left: 50px;
        }
        .plat_box input{
            width: 250px;
        }
        .layui-form-label{
            width: 100px;
        }
    </style>
</head>
<body>
<div class="plat_box">
    <form id="inputForm" action="" class="layui-form" lay-filter="inputForm">
        <input type="hidden" name="userId" value="${money.userId}">
        <div class="layui-inline">
            <label class="layui-form-label">充值金额(元)：</label>
            <div class="layui-input-inline">
                <input type="text" name="money" class="layui-input" maxlength="15" lay-verify="required|double|stock|length"
                       value='' onchange="changeMoney();">
            </div>
        </div>
        <br/><br/>
        <div class="layui-inline">
            <label class="layui-form-label">总信用额度(元)：</label>
            <div class="layui-input-inline">
                <input type="text" name="totalMoney" class="layui-input"  readonly="readonly" lay-verify="required|double|equals|stock|length"
                value='<c:if test="${empty money.totalMoney}">0</c:if><c:if test="${not empty money.totalMoney}">${money.totalMoney}</c:if>'>
            </div>
        </div>
        <br/><br/>
        <div class="layui-inline">
            <label class="layui-form-label">已消费额度(元)：</label>
            <div class="layui-input-inline">
                <input type="text" name="consumeMoney" class="layui-input" readonly="readonly" lay-verify="required|double|length"
                 value='<c:if test="${empty money.consumeMoney}">0</c:if><c:if test="${not empty money.consumeMoney}">${money.consumeMoney}</c:if>'>
            </div>
        </div>
        <br/><br/>
        <div class="layui-inline">
            <label class="layui-form-label">剩余额度(元)：</label>
            <div class="layui-input-inline">
                <input type="text" name="stockMoney" class="layui-input" readonly="readonly" lay-verify="required|double|length"
                 value='<c:if test="${empty money.stockMoney}">0</c:if><c:if test="${not empty money.stockMoney}">${money.stockMoney}</c:if>'>
            </div>
        </div>
        <div style="display: none;">
            <button id="submit_btn" class="layui-btn layui-btn-normal submit" lay-submit></button>
        </div>
    </form>
</div>
<script>

    //校验
    layui.use(['form'], function() {
        var form = layui.form;
        //监听提交
        form.on('submit(inputForm)', function(data){
            setCredit();
            return false;
        });
        //自定义验证规则
        form.verify({
            length: function (value,item) {
                if(value.length>15){
                    return "超出额度范围！";
                }
            },
            double: function (value,item) {
                if(!/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.exec(value)){
                    return "请输入正确的额度！";
                }
            },
            equals: function (value,item) {
                var consumeMoney = parseFloat($("[name='consumeMoney']").val());
                if(consumeMoney > parseFloat(value))
                    return "总金额不能小于已消费金额！";
            },
            stock: function (value,item) {
                var totalMoney = parseFloat($("[name='totalMoney']").val());
                var consumeMoney = parseFloat($("[name='consumeMoney']").val());
                $("[name='stockMoney']").val(totalMoney-consumeMoney);
                return "";
            }
        });
    });


    function changeMoney() {
        var money = $("[name='money']").val();
        var totalMoney = $("[name='totalMoney']").val();
        var stockMoney = $("[name='stockMoney']").val();


        var totalMoneyNum='<c:if test="${empty money.totalMoney}">0</c:if><c:if test="${not empty money.totalMoney}">${money.totalMoney}</c:if>';
        var stockMoneyNum='<c:if test="${empty money.stockMoney}">0</c:if><c:if test="${not empty money.stockMoney}">${money.stockMoney}</c:if>';


        $("[name='totalMoney']").val(parseInt(money)+parseInt(totalMoneyNum));
        $("[name='stockMoney']").val(parseInt(money)+parseInt(stockMoneyNum));

    }

    function setCredit() {
        var userId = $("[name='userId']").val();
        var companyCode = '${money.companyCode}';
        var money = $("[name='money']").val();
        var totalMoney = $("[name='totalMoney']").val();
        var consumeMoney = $("[name='consumeMoney']").val();
        var stockMoney = $("[name='stockMoney']").val();
        var dataJson = {"userId": userId, "totalMoney": totalMoney, "consumeMoney": consumeMoney, "stockMoney": stockMoney,"companyCode":companyCode,"money":money};
        var content = '确定提交吗？';
        parent.layer.confirm(content, {icon: 3, title:'提示'}, function(index){
            $('.layui-layer-btn0').css('pointer-events','none');
            $.ajax({
                type:"post",
                url:"${ctx}/sys/charter/doSetMoney",
                dataType:"json",
                data:dataJson,
                success:function(data) {
                    if(data.state){
                        layer.open({
                            icon: 1,
                            title: '信息',
                            skin: 'layer-ext-myskin',
                            shade: 0, //不显示遮罩
                            content: "操作成功！",
                            yes: function(){
                                parent.layer.closeAll();
                                parent.refresh();
                            },
                        });
                    }else{
                        layer.open({
                            icon: 2,
                            title: '信息',
                            skin: 'layer-ext-myskin',
                            shade: 0, //不显示遮罩
                            content: "操作失败！",
                            yes: function(){
                                layer.closeAll();
                            },
                        });
                    }
                }
            });
            parent.layer.close(index);
            //向服务端发送操作指令
        });
    }
</script>
</body>
</html>