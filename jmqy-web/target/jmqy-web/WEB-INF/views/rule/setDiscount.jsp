<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>包车方优惠设置</title>
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
        <input type="hidden" name="companyCode" value="${rule.companyCode}">
        <input type="hidden" name="id" value="${rule.id}">
        <div class="layui-inline">
            <label class="layui-form-label">优惠率：</label>
            <div class="layui-input-inline">
                <input type="text" name="coefficient" value="${rule.coefficient}" class="layui-input" lay-verify="required|double">
            </div>
        </div>
        <br/><br/>
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
        	doSetDiscount();
            return false;
        });
        //自定义验证规则
        form.verify({
            double: function (value,item) {
                if(!/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.exec(value)){
                    return "请输入正确的优惠率！";
                }
            }
        });
    });

    function doSetDiscount() {
        var id = $("[name='id']").val();
        var coefficient = $("[name='coefficient']").val();
        var dataJson = {"id": id, "coefficient": coefficient};
         $.ajax({
             type:"post",
             url:"${ctx}/rule/doSetDiscount",
             dataType:"json",
             data:dataJson,
             success:function(data) {
                 if(data){
                     layer.msg("操作成功！",{
                         icon: 1,
                         time:1000},
                         function(){

                         	parent.layer.closeAll();

                         }
                     );
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
    }
</script>
</body>
</html>