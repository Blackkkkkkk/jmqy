<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>地区系数设置</title>
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
        <input type="hidden" name="id" value="${rule.id}">
        <div class="layui-inline">
            <label class="layui-form-label">地区名称：</label>
            <div class="layui-input-inline">
                <select name="quiz1" lay-filter="quiz1_select" style="width: 20px;">
                    <option value="">请选择省</option>
                </select>
                <select name="quiz2" lay-filter="quiz2_select">
                    <option value="">请选择市</option>
                </select>
                <select name="quiz3">
                    <c:if test="${rule.ruleValue != null && rule.ruleValue !='' }">
                        <option value="${rule.ruleValue}">${rule.ruleValue}</option>
                    </c:if>>
                    <option value="">请选择县/区</option>
                </select>
            </div>
        </div>
        <br/><br/>
        <div class="layui-inline">
            <label class="layui-form-label">地区系数：</label>
            <div class="layui-input-inline">
                <input type="text" name="coefficient" class="layui-input" lay-verify="required|double"
                 value='${rule.coefficient}'>
            </div>
        </div>
        <br/><br/>
        <div class="layui-inline">
            <label class="layui-form-label">类型：</label>
            <div class="layui-input-inline">
            <select name="ruleType">

                <option value="1" <c:if test="${rule.ruleType == 1}">selected="selected" </c:if>>起始点系数</option>
                <option value="10" <c:if test="${rule.ruleType == 10}">selected="selected" </c:if>>目的地系数</option>
            </select>
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
            setCredit();
            return false;
        });
        //自定义验证规则
        form.verify({
            double: function (value,item) {
                if(!/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.exec(value)){
                    return "请输入正确的系数！";
                }
            }
        });
    });

    function setCredit() {
        var id = $("[name='id']").val();
        var ruleValue = $("[name='quiz3']").val();
        var coefficient = $("[name='coefficient']").val();

        var ruleType = $("[name='ruleType']").val();

        if(ruleValue ==null || ruleValue ==''){
            ruleValue = $("[name='quiz2']").val();
        }


        var dataJson = {"id": id, "ruleValue": ruleValue, "coefficient": coefficient,"ruleType": ruleType, "parentId": 0};
        var content = '确定提交吗？';
        parent.layer.confirm(content, {icon: 3, title:'提示'}, function(index){
            $('.layui-layer-btn0').css('pointer-events','none');
            $.ajax({
                type:"post",
                url:"${ctx}/rule/save",
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


    layui.use(['form'], function(){
        var form = layui.form;

        //初始化
        init_province(form);
        //下拉
        form.on('select(quiz1_select)', function(data) {

            init_city(form, data.value, 'quiz2');
            init_area(form, data.value, 'quiz3');
        })
        form.on('select(quiz2_select)', function(data) {
            init_area(form, data.value, 'quiz3');
        })
        form.on('select(quiz4_select)', function(data) {
            init_city(form, data.value, 'quiz5');
            init_area(form, data.value, 'quiz6');
        })
        form.on('select(quiz5_select)', function(data) {
            init_area(form, data.value, 'quiz6');
        })
    });
    function init_province(form) {
        $.ajax({
            type:"post",
            url:"${ctx}/area/province",
            dataType:"json",
            success:function(data) {
                if(data.state){
                    var provinceList = eval(data.provinceList);
                    if(provinceList != null && provinceList.length > 0){
                        var html = '<option value="">请选择省</option>';
                        for (var i=0; i<provinceList.length; i++){
                            var province = provinceList[i];
                            var id = province.id;
                            var name = province.name;
                            html += '<option value="'+id+'">'+name+'</option>';
                        }
                        $("[name='quiz1']").html(html);
                        $("[name='quiz4']").html(html);
                        form.render('select');
                    }
                }else{
                    var html = '<option value="">请选择省</option>';
                    $("[name='quiz1']").html(html);
                    $("[name='quiz4']").html(html);
                    form.render('select');
                }
            }
        });
    }
    function init_city(form, id, obj) {
        $.ajax({
            type:"post",
            url:"${ctx}/area/city",
            dataType:"json",
            data:{"parentId":id},
            success:function(data) {
                if(data.state){
                    var cityList = eval(data.cityList);
                    if(cityList != null && cityList.length > 0){
                        var html = '<option value="">请选择市</option>';
                        for (var i=0; i<cityList.length; i++){
                            var city = cityList[i];
                            var id = city.cityId;
                           // var id = city.cityName;
                            var name = city.cityName;
                            html += '<option value="'+id+'">'+name+'</option>';
                        }
                        $("[name='"+obj+"']").html(html);
                        form.render('select');
                    }
                }else{
                    var html = '<option value="">请选择市</option>';
                    $("[name='"+obj+"']").html(html);
                    form.render('select');
                }
            }
        });
    }
    function init_area(form, id, obj) {
        $.ajax({
            type:"post",
            url:"${ctx}/area/area",
            dataType:"json",
            data:{"parentId":id},
            success:function(data) {
                if(data.state){
                    var areaList = eval(data.areaList);
                    if(areaList != null && areaList.length > 0){
                        var html = '<option value="">请选择县/区</option>';
                        for (var i=0; i<areaList.length; i++){
                            var area = areaList[i];
                            var id = area.areaName;
                            var name = area.areaName;
                            html += '<option value="'+id+'">'+name+'</option>';
                        }
                        $("[name='"+obj+"']").html(html);
                        form.render('select');
                    }
                }else{
                    var html = '<option value="">请选择市</option>';
                    $("[name='"+obj+"']").html(html);
                    form.render('select');
                }
            }
        });
    }


</script>
</body>
</html>