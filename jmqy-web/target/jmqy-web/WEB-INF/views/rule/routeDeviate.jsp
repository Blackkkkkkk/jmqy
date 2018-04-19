
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>线路偏差值设置</title>

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
<div class="plat_box">
<form id="inputForm" action="${ctx}/rule/setRouteDeviate" class="layui-form" lay-filter="inputForm">
    <div class="layui-inline">
        <label class="layui-form-label">线路偏差值设置(单位：百分比)：</label>
        <div class="layui-input-inline">
            <input type="text"  id="routeDeviateValue" name="routeDeviateValue" value="${map.routeDeviateValue}" class="layui-input" lay-verify="required|number">
        </div>
    </div>
    <br/><br/>
    <div class="layui-inline">
        <label class="layui-form-label">线路偏差距离设置(单位：公里)：</label>
        <div class="layui-input-inline">
            <input type="text"  id="routeDeviateDistance" name="routeDeviateDistance" value="${map.routeDeviateDistance}" class="layui-input" lay-verify="required|number">
        </div>
    </div>
    <br/><br/>

    <div >
        <button id="submit_btn" class="layui-btn layui-btn-normal submit" >保存</button>
        <!-- <button  id="submit_btn" class="layui-btn layui-btn-normal submit" onclick="save();">保存</button> -->
    </div>
</form>
</div>
<script>


    layui.use('layer', function(){
        var layer = layui.layer;
        var state='${state}';
      if(state=='true'){
            parent.layer.msg("保存成功！")
        }
    });

</script>
</body>
</html>
