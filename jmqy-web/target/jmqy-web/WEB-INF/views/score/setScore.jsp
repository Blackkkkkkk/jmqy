<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>积分设置</title>
    <style>
        body{
            min-width: 0;
            overflow: hidden;
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
        <input type="hidden" name="userId" value="${score.userId}">
        <div class="layui-inline">
            <label class="layui-form-label">奖励积分数：</label>
            <div class="layui-input-inline">
                <input type="text" name="totalScore" value="${score.totalScore}" class="layui-input" lay-verify="required|double|equals">
            </div>
        </div>
        <br/><br/>
        <div class="layui-inline">
            <label class="layui-form-label">有效期：</label>
            <div class="layui-input-inline">
                <input type="text" name="expireDate" value="${score.expireDate}" class="layui-input input_date" id="test5" lay-verify="required">
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
            setScore();
            return false;
        });
        //自定义验证规则
        form.verify({
            double: function (value,item) {
                if(!/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.exec(value)){
                    return "请输入正确的积分数！";
                }
            },
            equals: function (value,item) {
                var totalScore = parseFloat($("[name='totalScore']").val());
                if(!(totalScore > 0))
                    return "请输入正确的积分数！";
            }
        });
    });

    function setScore() {
        var userId = $("[name='userId']").val();
        var totalScore = $("[name='totalScore']").val();
        var expireDate = $("[name='expireDate']").val();
        var dataJson = {"userId": userId, "totalScore": totalScore, "consumeScore": 0, "expireDate": expireDate};
        var content = '确定提交吗？';
        parent.layer.confirm(content, {icon: 3, title:'提示'}, function(index){
            $('.layui-layer-btn0').css('pointer-events','none');
            $.ajax({
                type:"post",
                url:"${ctx}/score/doSetScore",
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