<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<style type="text/css">
    .searchType{
        position: absolute;
        top: 0;
        right:250px;
    }
</style>
<head>
    <title>评价处理</title>
    <script>

        //订单详情
        function query(orderCode,type){
            var width ="900px";
            var height = "350px;"
            var title ="订单详情";
            if(type ==1){
                height = "350px;"
                title ="订单详情";
            }else if(type ==3){
                height = "560px;"
                title ="订单评价";
            }
            layer.open({
                type: 2,
                title: title,
                shadeClose: true,
                shade: 0.5,
                yes: function(index, layero){
                    var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
                    iframeWin.laySubmit();//执行iframe页的方法：
                },
                area: [width,height],
                content: '${ctx}/charter/order/query?orderCode='+orderCode+'&type='+type
            })
        }



        //type 1: 批量  2：单个
        function remind(type, ids) {
            if(type == 1){
                ids = checkedIdsArray.join();
                var length = checkedIdsArray.length;
                if(length<1){
                    parent.layer.msg('请选择！',{
                        time: 800,
                        skin: 'layui-layer-molv',
                        area: '20px',
                        icon: 0
                    });
                    return false;
                }
            }
            $.ajax({
                type:"post",
                url:"${ctx}/tackle/remind",
                dataType:"json",
                data:{"ids": ids, "type": 4},
                success:function(data) {
                    if(data.state){
                    	layer.msg('提醒成功！',{
                            time: 1000,
                            skin: 'layui-layer-molv',
                            area: '20px',
                            icon: 1
                        }, function(){
                        });
                    }else{
                        layer.open({
                            icon: 2,
                            title: '信息',
                            skin: 'layer-ext-myskin',
                            shade: 0, //不显示遮罩
                            content: "提醒失败！"
                        });
                    }
                }
            });
        }
    </script>
</head>
<body>
<form id="searchForm" action="${ctx}/tackle/list" method="post" class="layui-form">
    <input type="hidden" name="pageNum" value="${complaint.pageNum}"/>
    <input type="hidden" name="pageSize" value="${complaint.pageSize}"/>
    <div class="button_left">
        <a class="layui-btn layui-btn-primary layui-btn-mini" href="javascript:;" onclick="remind(1, null)">批量提醒</a>
        <div class="searchType" style="position: absolute;top: 0;right:250px;">
            <label class="layui-form-label" style="float: left;">查询内容选项:</label>
            <div style="float: left;" id="tackle_select">
                <select name="searchType" lay-verify="required">
                    <option value="0" <c:if test="${complaint.searchType == 0}">selected="selected"</c:if>>订单号</option>
                    <option value="1" <c:if test="${complaint.searchType == 1}">selected="selected"</c:if>>包车企业</option>
                    <option value="2" <c:if test="${complaint.searchType == 2}">selected="selected"</c:if>>运输企业</option>
                    <option value="3" <c:if test="${complaint.searchType == 3}">selected="selected"</c:if>>评价人</option>
                    <option value="4" <c:if test="${complaint.searchType == 4}">selected="selected"</c:if>>评价内容</option>
                </select>
                <div class="layui-unselect layui-form-select">
                    <div class="layui-select-title">
                        <input type="text" value="" readonly="" class="layui-input layui-unselect">
                        <i class="layui-edge"></i>
                    </div>
                    <dl class="layui-anim layui-anim-upbit">
                        <dd lay-value="0">订单号</dd>
                        <dd lay-value="1">包车企业</dd>
                        <dd lay-value="2">运输企业</dd>
                        <dd lay-value="3">评价人</dd>
                        <dd lay-value="4">评价内容</dd>
                    </dl>
                </div>
            </div>
        </div>
    </div>
    <div class="button_right">
        <input type="text" name="param" class="layui-input" placeholder="请输入关键字" value="${complaint.param}">
        <button class="layui-btn layui-btn-small">搜索</button>
    </div>
    <div class="cl"></div>
    <table class="layui-table">
        <colgroup>
            <col width="50">
            <col width="60">
            <col width="180">
            <col width="180">
            <col width="180">
            <col width="180">
            <col width="300">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th><input name="" lay-skin="primary" lay-filter="allChoose" type="checkbox"></th>
            <th>序号</th>
            <th>订单号</th>
            <th>包车企业</th>
            <th>运输企业</th>
            <th>评价人</th>
            <th>评价内容</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${complaints.list}" var="info" varStatus="vs">
            <tr>
                <td>
                    <input name="singleCheck" lay-skin="primary" lay-filter="singleChoose" type="checkbox" value="${info.complaintId}@${info.orderId}">
                </td>
                <td>${vs.index+1}</td>
                <td><a href="javascript:query('${info.orderCode}',3);" style="color: orange;">${info.orderCode}</a></td>
                <td>${info.charterCompany}</td>
                <td>${info.transportCompany}</td>
                <td>${info.complainant}</td>
                <td>${info.toComplaintContent}</td>
                <td>
                    <a href="javascript:;" class="layui-btn layui-btn-danger layui-btn-mini" onclick="remind(2, '${info.complaintId}@${info.orderId}')">提醒企业</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <tags:page pages="${complaints}"/>
</form>
</body>
</html>