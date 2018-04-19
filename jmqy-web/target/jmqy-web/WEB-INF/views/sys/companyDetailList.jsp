<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>运输方管理</title>
    <style type="text/css">
        .button_left{
            margin-top: 0px;
        }
    </style>
    <script>
        function examine(userId, companyName) {
            parent.layer.open({
                type: 2,
                title: "【"+companyName+'】审核信息',
                shadeClose: true,
                shade: 0.1,
                btn: ['同意', '拒绝'],
                btn1: function(index, layero){
                    var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
                    iframe.doExamine(0,2);
                },
                btn2: function(index, layero){
                    var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
                    iframe.doExamine(2,null);
                    return false;
                },
                skin: "layui-layer-molv",
                area: ["750px", "500px"],
                content: "${ctx}/permission/examine?id="+userId
            })
        }
    </script>
</head>
<body>
<form id="searchForm" action="${ctx}/sys/company/detail" method="post" class="layui-form">
    <input type="hidden" id="companyCode" name="companyCode" value="${company.companyCode}">

    <c:if test="${company.selectType == 1 ||company.selectType == null}">
        <input type="hidden" name="pageNum" value="${driver.pageNum}"/>
        <input type="hidden" name="pageSize" value="${driver.pageSize}"/>
        <input type="hidden" id="order" name="order" value="${driver.order}"/>
        <input type="hidden" id="sortType" name="sortType" value="${driver.sortType}"/>

        <div class="button_left">
            <label class="layui-form-label">运输方查看:</label>
            <div class="layui-input-block">
                <input type="radio" name="selectType" value="1" title="司机情况" <c:if test="${company.selectType == 1 ||company.selectType == null }">checked</c:if> lay-filter="companyType">
                <input type="radio" name="selectType" value="2" title="车辆情况 " <c:if test="${company.selectType == 2}">checked</c:if> lay-filter="companyType">

            </div>
        </div>
        <div class="button_right">
            <input type="text" name="param" class="layui-input" placeholder="请输入关键字" value="${credit.param}">
            <button id="submitBtn" class="layui-btn layui-btn-small">搜索</button>
        </div>
        <div class="cl"></div>
    <table class="layui-table">
        <colgroup>
            <col width="150">
            <col width="150">
            <col width="100">
            <col width="200">
            <col width="200">
            <col width="150">
            <col width="120">
        </colgroup>
        <thead>
        <tr>
            <th><a id="name" onclick="sort(this);" style="cursor: pointer;">姓名</a></th>
            <th><a id="sex" onclick="sort(this);" style="cursor: pointer;">性别</a></th>
            <th><a id="job_num" onclick="sort(this);" style="cursor: pointer;">工号</a></th>
            <th><a id="phone" onclick="sort(this);" style="cursor: pointer;">手机号码</a></th>
            <th><a id="id_card" onclick="sort(this);" style="cursor: pointer;">身份证</a></th>
            <th><a id="status" onclick="sort(this);" style="cursor: pointer;">状态</a></th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${drivers.list}" var="driver" varStatus="status">
            <tr>
                <td>${driver.name}</td>
                <td>${driver.sex}</td>
                <td>${driver.jobNum}</td>
                <td>${driver.phone}</td>
                <td>${driver.idCard}</td>
                <td>
                    <c:if test="${driver.status == 0}">在职</c:if>
                    <c:if test="${driver.status == 1}">休假</c:if>
                    <c:if test="${driver.status == 2}">离职</c:if>
                </td>
                <td>
                    <a href="javascript:Driver('${driver.id}');" class="layui-btn layui-btn-primary layui-btn-mini caradmin_a"><i class="icon-edit"></i> 查看详情</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
        <tags:page pages="${drivers}"/>
    </c:if>

    <c:if test="${company.selectType == 2}">

        <input type="hidden" name="pageNum" value="${car.pageNum}"/>
        <input type="hidden" name="pageSize" value="${car.pageSize}"/>
        <input type="hidden" id="order" name="order" value="${car.order}"/>
        <input type="hidden" id="sortType" name="sortType" value="${car.sortType}"/>
        <div class="button_left">
            <label class="layui-form-label">运输方查看:</label>
            <div class="layui-input-block">
                <input type="radio" name="selectType" value="1" title="司机情况" <c:if test="${company.selectType == 1 ||company.selectType == null }">checked</c:if> lay-filter="companyType">
                <input type="radio" name="selectType" value="2" title="车辆情况 " <c:if test="${company.selectType == 2}">checked</c:if> lay-filter="companyType">

            </div>
        </div>
        <div class="button_right">
            <div class="layui-inline">
                <label class="layui-form-label">出厂日期:</label>
                <div class="layui-input-inline" style="top: 5px;">
                    <input class="layui-input search_text input_date" id="beginTime" name="beginTime" type="text"
                           value='<fmt:formatDate value="${order.beginTime}" pattern="yyyy-MM-dd"/>'
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})"/>_
                </div>
                <div class="layui-input-inline" style="top: 5px;">
                    <input class="layui-input search_text input_date" id="endTime" name="endTime" type="text"
                           value='<fmt:formatDate value="${order.endTime}" pattern="yyyy-MM-dd"/>'
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginTime\')}'})"/>
                </div>
            </div>
            <div class="layui-inline">
                <input type="text" name="carNum" value="${car.carNum}" class="layui-input" placeholder="请输入车牌号">
                <button id="submitBtn" class="layui-btn layui-btn-small">搜索</button>
            </div>
        </div>
        <div class="cl"></div>
        <div class="layui-form">
            <table class="layui-table">
                <colgroup>
                    <col width="50">
                    <col width="150">
                    <col width="200">
                    <col width="100">
                    <col width="200">
                    <col width="200">
                    <col width="150">
                    <col width="200">
                    <col>
                </colgroup>
                <thead>
                <tr>
                    <th><input name="" lay-skin="primary" lay-filter="allChoose" type="checkbox"></th>
                    <th><a id="car_num" onclick="sort(this);" style="cursor: pointer;">车牌号</a></th>
                    <th><a id="car_type" onclick="sort(this);" style="cursor: pointer;">车型</a></th>
                    <th><a id="d.name" onclick="sort(this);" style="cursor: pointer;">司机</a></th>
                    <th><a id="seat_number" onclick="sort(this);" style="cursor: pointer;">客座数</a></th>
                    <th><a id="made_date" onclick="sort(this);" style="cursor: pointer;">出厂日期</a></th>
                    <th><a id="c.status" onclick="sort(this);" style="cursor: pointer;">状态</a></th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${cars.list}" var="car" varStatus="status">
                    <tr>
                        <td><input name="" lay-skin="primary" type="checkbox"></td>
                        <td>${car.carNum}</td>
                        <td>${car.carType}</td>
                        <td>${car.driverName}</td>
                        <td>${car.seatNumber}</td>
                        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${car.madeDate}" /></td>
                        <td>
                            <c:if test="${car.status == 0}">正常</c:if>
                            <c:if test="${car.status == 1}">停用</c:if>
                        </td>
                        <td>
                            <a href="javascript:Car('${car.id}');" class="layui-btn layui-btn-primary layui-btn-mini caradmin_a"><i class="icon-edit"></i> 查看详情</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <tags:page pages="${cars}"/>
    </c:if>


</form>
<script type="text/javascript">
    //提交搜索表单
    function selfSubmit() {
        $("#searchForm").submit();
    }
    //加载表单
    layui.use(['form','element','laydate'], function() {

        var form = layui.form;

        form.on('radio(companyType)', function(data){
            //console.log(data.elem); //得到radio原始DOM对象
            //console.log(data.value); //被点击的radio的value值
            var type = '${company.selectType}';

            if(type != data.value){
                $("[name='pageNum']").val(1);//第一页
            }
            $('#submitBtn').click();
        });
    });

    /*****查看司机*****/
    function Driver(id){
        top.layer.open({
            type: 2,
            title: '司机添加修改',
            shadeClose: true,
            shade: 0.5,
            btn: ['关闭'],
            yes: function(index, layero){
              top.layer.close(index)
            },
            area: ['650px','600px'],
            content: '${ctx}/sys/company/detail/form?id='+id
        })
    }


    /***** 查看车辆*****/
    function Car(id){
        top.layer.open({
            type: 2,
            title: '车辆添加修改',
            shadeClose: true,
            shade: 0.5,
            btn: ['提交', '取消'],
            yes: function(index, layero){
                top.layer.close(index)
            },

            area: ['650px','600px'],
            content: '${ctx}/sys/company/detail/carForm?id='+id
        })
    }



</script>
</body>
</html>