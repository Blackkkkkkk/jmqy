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
    <form id="inputForm" action="${ctx}/rule/refundSave" class="layui-form" lay-filter="inputForm">
        <input type="hidden" id="parentId" name="parentId" value="0">
        <div class="layui-inline">
            <label class="layui-form-label">方案名称：</label>
            <div class="layui-input-inline">
                <input type="text" id="ruleValue" name="ruleValue" class="layui-input" lay-verify="required"
                       >
            </div>
        </div>
        <br/><br/>
        <div class="layui-inline">
            <label class="layui-form-label">企业名称：</label>
            <div class="layui-input-inline">

                <select name="companyCode" id="companyCode">
                    <c:forEach var="value" items="${companyList}" varStatus="vs">
                        <option value="${value.companyCode}">${value.companyName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <br/><br/>


         <br/><br/>
        <div style="display: none;">
            <button id="submit_btn" class="layui-btn layui-btn-normal submit" lay-submit></button>
        </div>
    </form>
</div>
<script>



function  save(index) {


    var ruleValue = $("#ruleValue").val();
    if(ruleValue == null ||ruleValue ==''){
        parent.layer.msg("方案名称不能为空！")
        return false;
    }
    var companyCode = $("#companyCode").val();

    $.ajax({
        type:"post",
        url:"${ctx}/rule/refundSave",
        dataType:"json",
        data:{"ruleValue": ruleValue, "companyCode": companyCode,"parentId":"0"},
        success:function(data) {

            if(data.state == 2){
                parent.layer.msg("该企业已经存在优惠方案！")
            }else if(data.state == 1){
                parent.layer.msg("新增成功！")
                parent.layer.close(index)
               // window.parent.contents().find('.pla_iframe').contentWindow.a()
               // window.parent.$('.pla_iframe').contentWindow.a()
            }else if(data.state == 0){
                parent.layer.msg("保存错误！")
              //  window.parent.contents().find('#dd').
            }


        }
    });

}


</script>
</body>
</html>