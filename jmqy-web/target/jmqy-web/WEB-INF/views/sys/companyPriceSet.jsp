<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>

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
    <input type="hidden" name="companyCode"  id="companyCode" value="${map.companyCode}">
    <div class="layui-inline">
        <label class="layui-form-label">餐费：</label>
        <div class="layui-input-inline">
            <input type="text"  id="meals" name="meals" value="${map.meals}" class="layui-input" lay-verify="required|number">
        </div>
    </div>
    <br/><br/>
    <div class="layui-inline">
        <label class="layui-form-label">住宿费：</label>
        <div class="layui-input-inline">
            <input type="text"  id="accommodation" name="accommodation" value="${map.accommodation}" class="layui-input" lay-verify="required|number">
        </div>
    </div>
    <br/><br/>
    <div class="layui-inline">
        <label class="layui-form-label">高速路费：</label>
        <div class="layui-input-inline">
            <input type="text"  id="highway" name="highway" value="${map.highway}" class="layui-input" lay-verify="required|number">
        </div>
    </div>
    <br/><br/>
    <div class="layui-inline">
        <label class="layui-form-label">保险费：</label>
        <div class="layui-input-inline">
            <input type="text"  id="insurance" name="insurance" value="${map.insurance}" class="layui-input" lay-verify="required|number">
        </div>
    </div>
    <br/><br/>
    <div class="layui-inline">
        <label class="layui-form-label">水：</label>
        <div class="layui-input-inline">
            <input type="text"  id="water" name="water" value="${map.water}" class="layui-input" lay-verify="required|number">
        </div>
    </div>
    <br/><br/>

    <div style="display:none;">
        <button id="submit_btn" class="layui-btn layui-btn-normal submit" lay-submit></button>
    </div>
</form>
</div>
<script>


    function  a() {

        var re = /^[1-9]+[0-9]*]*$/;   //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/

        var companycode =$("[name='companyCode']").val();
        var meals =$("[name='meals']").val();
        if(meals == '' || meals ==null || !re.test(meals)){layer.msg("餐费不能为空,且只能为正整数！"); return false;}
        var accommodation =$("[name='accommodation']").val();
        if(accommodation == '' || accommodation ==null || !re.test(accommodation)){layer.msg("住宿费不能为空,且只能为正整数！"); return false;}
        var highway =$("[name='highway']").val();
        if(highway == '' || highway ==null || !re.test(highway)){layer.msg("高速路费不能为空,且只能为正整数！"); return false;}
        var insurance =$("[name='insurance']").val();
        if(insurance == '' || insurance ==null || !re.test(insurance)){layer.msg("保险费不能为空,且只能为正整数！"); return false;}
        var water =$("[name='water']").val();
        if(water == '' || water ==null || !re.test(water)){layer.msg("水费不能为空,且只能为正整数！"); return false;}


        var dataJson = {"companycode":companycode,"meals":meals,"accommodation":accommodation,"highway":highway,"insurance":insurance,"water":water};
        var content = '确定提交吗？';
        layer.confirm(content, {icon: 3, title:'提示'}, function(index){
            $('.layui-layer-btn0').css('pointer-events','none');
            $.ajax({
                type:"post",
                url:"${ctx}/sys/company/savePriceSet",
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
                                parent.location.reload();
                            },
                        });
                    }else{
                        layer.open({
                            icon: 2,
                            title: '信息',
                            skin: 'layer-ext-myskin',
                            shade: 0, //不显示遮罩
                            content: "操作失败！",
                        });
                    }
                }
            });
        });
    }



</script>
</body>
</html>
