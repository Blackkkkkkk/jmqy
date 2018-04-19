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
<div class="plat_box">
<form id="inputForm" action="${ctx}/rule/saverule" class="layui-form" lay-filter="inputForm">
    <div class="layui-inline">
        <label class="layui-form-label">单前时间设置(单位：小时)：</label>
        <div class="layui-input-inline">
            <input type="text"  id="beforesettime" name="beforesettime" value="${map.beforesettime}" class="layui-input" lay-verify="required|number">
        </div>
    </div>
    <br/><br/>
    <div class="layui-inline">
        <label class="layui-form-label">单中时间设置(单位：百分比)：</label>
        <div class="layui-input-inline">
            <input type="text"  id="middlesettime" name="middlesettime" value="${map.middlesettime}" class="layui-input" lay-verify="required|number">
        </div>
    </div>
    <br/><br/>
    <div class="layui-inline">
        <label class="layui-form-label">单后时间设置(单位：小时)：</label>
        <div class="layui-input-inline">
            <input type="text"  id="aftersettime" name="aftersettime" value="${map.aftersettime}" class="layui-input" lay-verify="required|number">
        </div>
    </div>
    <br/><br/>
    <div class="layui-inline">
        <label class="layui-form-label">默认不接单起始时间设置：</label>
        <div class="layui-input-inline">
            <input type="text"  id="ordernotsetStarttime" name="ordernotsetStarttime" value="${map.ordernotsetStarttime}" class="layui-input" lay-verify="required">
        </div>
    </div>
    <br/><br/>

    <div class="layui-inline">
        <label class="layui-form-label">默认不接单结束时间设置：</label>
        <div class="layui-input-inline">
            <input type="text"  id="ordernotsetEndtime" name="ordernotsetEndtime" value="${map.ordernotsetEndtime}" class="layui-input" lay-verify="required">
        </div>
    </div>
    <br/><br/>

    <div >
        <button id="submit_btn" class="layui-btn layui-btn-normal submit" lay-submit>保存</button>
        <!-- <button  id="submit_btn" class="layui-btn layui-btn-normal submit" onclick="save();">保存</button> -->
    </div>
</form>
</div>
<script>




    layui.use('form', function () {
        var form = layui.form;

        //各种基于事件的操作，下面会有进一步介绍
        form.on('submit', function (data) {


             console.log(data.field)

            var ordernotsetStarttime = (data.field).ordernotsetStarttime;
            var ordernotsetEndtime = (data.field).ordernotsetEndtime;

          //  console.log((data.field).ordernotsetStarttime)
          //  console.log((data.field).ordernotsetEndtime)
            var orS = ordernotsetStarttime.split(":");
            var orE = ordernotsetEndtime.split(":");

            if (orS[0] > orE[0]) {

                layer.msg("【时】结束时间必须比起始时间大！")
                return false
                if (orS[1] > orE[1]) {

                    layer.msg("【分】结束时间必须比起始时间大！")
                    return false
                    if (orS[2] > orE[2]) {

                        layer.msg("【秒】结束时间必须比起始时间大！")
                        return false
                    }
                }

            } else {
                if (orS[1] > orE[1]) {

                    layer.msg("【分】结束时间必须比起始时间大！")
                    return false
                    if (orS[2] > orE[2]) {

                        layer.msg("【秒】结束时间必须比起始时间大！")
                        return false
                    }
                } else {
                    if (orS[2] > orE[2]) {

                        layer.msg("【秒】结束时间必须比起始时间大！")
                        return false
                    }
                }

            }

        });

    });






    $(document).ready(function () {
        layui.use('laydate', function(){
        if('${requestScope.state}'){
            alert("保存成功！")
        }
        })
    })


    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#ordernotsetStarttime' ,//指定元素
            type: 'time'
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#ordernotsetEndtime' ,//指定元素
            type: 'time'
        });

    });



</script>
</body>
</html>
