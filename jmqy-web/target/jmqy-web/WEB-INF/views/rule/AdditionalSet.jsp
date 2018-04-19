<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>时间设置</title>

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
            width: 200px;
        }
    </style>
</head>
<body>
<script src="${ctx}/static/layui/layui.js" type="text/javascript"></script>
<div class="plat_box">
<form id="inputForm" action="${ctx}/rule/saveadditionalrule" class="layui-form" lay-filter="inputForm">
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
            <input type="text"  id="insurance" name="insurance" value="${map.insurance}" class="layui-input" lay-verify="required">
        </div>
    </div>
    <br/><br/>

    <div class="layui-inline">
        <label class="layui-form-label">水费：</label>
        <div class="layui-input-inline">
            <input type="text"  id="water" name="water" value="${map.water}" class="layui-input" lay-verify="required">
        </div>
    </div>
    <br/><br/>

    <div >
        <button id="submit_btn" class="layui-btn layui-btn-normal submit" lay-submit>保存</button>
    </div>
</form>
</div>
<script>

    $(document).ready(function () {

        if('${requestScope.state}'){
            alert("保存成功！")
           // layer.msg("保存成功！")
        }

    })


</script>
</body>
</html>
