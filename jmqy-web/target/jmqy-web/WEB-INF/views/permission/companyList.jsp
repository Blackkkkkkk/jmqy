<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>权限管理</title>
    <style type="text/css">
        .button_left {
            margin-top: 0px;
        }
    </style>
    <script>
        function examine(userId, companyName) {
            parent.layer.open({
                type: 2,
                title: "【" + companyName + '】审核信息',
                shadeClose: true,
                shade: 0.1,
                btn: ['同意', '拒绝'],
                btn1: function (index, layero) {
                    var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
                    iframe.doExamine(0, 2);
                },
                btn2: function (index, layero) {
                    var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
                    iframe.doExamine(2, null);
                    return false;
                },
                skin: "layui-layer-molv",
                area: ["750px", "500px"],
                content: "${ctx}/permission/examine?id=" + userId
            })
        }
    </script>
</head>
<body>
<form id="searchForm" action="${ctx}/permission/setType" method="post" class="layui-form">
    <input type="hidden" name="type" value="${type}"/>
    <input type="hidden" name="companyCode" value="${companyCode}"/>
    <input type="hidden" name="companyId" value="${companyId}"/>

    <div class="button_left">
        <label class="layui-form-label">类别:</label>
        <div class="layui-input-block">
            <input type="radio" name="companyType" value="1" title="车辆"
                   <c:if test="${type == 1}">checked</c:if> lay-filter="companyType">
            <input type="radio" name="companyType" value="2" title="司机"
                   <c:if test="${type == 2}">checked</c:if> lay-filter="companyType">
        </div>
    </div>
    <div class="button_right">
        <input type="text" name="param" class="layui-input" placeholder="请输入关键字" value="">
        <button id="submitBtn" class="layui-btn layui-btn-small">搜索</button>
    </div>

    <c:if test="${type == 1 || type == 3}">

    <input type="hidden" name="pageNum" value="${car.pageNum}"/>
    <input type="hidden" name="pageSize" value="${car.pageSize}"/>
    <input type="hidden" id="order" name="order" value="${car.order}"/>
    <input type="hidden" id="sortType" name="sortType" value="${car.sortType}"/>

    <input type="hidden" id="carNum" name="carNum" value="${order.carNum}"/>
    <input type="hidden" id="carType" name="carType" value="${order.carType}"/>
    <input type="hidden" id="seatNumber" name="seatNumber" value="${order.seatNumber}"/>
    <input type="hidden" id="driverName" name="driverName" value="${order.driverName}"/>

    <input type="hidden" id="searchType" name="searchType" value="0">


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
                <th><a id="made_date" onclick="sort(this);" style="cursor: pointer;">驻场地点</a></th>
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
                    <td>${car.site}</td>
                    <td>
                        <c:if test="${car.status == 0}">正常</c:if>
                        <c:if test="${car.status == 1}">停用</c:if>
                    </td>
                    <td>
                        <!--   <a href="javascript:update('${car.id}');"
                           class="layui-btn layui-btn-primary layui-btn-mini caradmin_a"><i class="icon-edit"></i>
                            修改</a>  -->
                        <c:if test="${car.status == 0}"><a href="javascript:updateCarById('${car.id}',1);"
                                                           class="layui-btn layui-btn-primary layui-btn-mini"><i
                                class="icon-remove"></i> 冻结</a></c:if>
                        <c:if test="${car.status == 1}"><a href="javascript:updateCarById('${car.id}',0);"
                                                           class="layui-btn layui-btn-primary layui-btn-mini"><i
                                class="icon-remove"></i> 启用</a></c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</form>
</div>
<tags:page pages="${cars}"/>
</c:if>
<c:if test="${type == 2}">
        <input type="hidden" name="pageNum" value="${driver.pageNum}"/>
        <input type="hidden" name="pageSize" value="${driver.pageSize}"/>
        <input type="hidden" id="order" name="order" value="${driver.order}"/>
        <input type="hidden" id="sortType" name="sortType" value="${driver.sortType}"/>
        <div class="cl"></div>
        <div class="layui-form">
            <table class="layui-table">
                <colgroup>
                    <col width="50">
                    <col width="150">
                    <col width="150">
                    <col width="100">
                    <col width="200">
                    <col width="200">
                    <col width="150">
                    <col width="120">
                    <col>
                </colgroup>
                <thead>
                <tr>
                    <th><input name="" lay-skin="primary" lay-filter="allChoose" type="checkbox"></th>
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
                        <td><input name="" lay-skin="primary" type="checkbox"></td>
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
                            <a href="javascript:update('${driver.id}','2','${companyId}');"
                               class="layui-btn layui-btn-primary layui-btn-mini caradmin_a"><i class="icon-edit"></i>
                                修改</a>
                            <a href="javascript:updateDriverById('${driver.id}',1);"
                               class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-remove"></i> 删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <tags:page pages="${drivers}"/>
</c:if>
</form>
<script type="text/javascript">
    //提交搜索表单
    $(function () {

    })

    function selfSubmit() {
        $("#searchForm").submit();
    }

    //加载表单

    var radiovaLue;
    layui.use(['form', 'element', 'laydate'], function () {

        var form = layui.form;

        form.on('radio(companyType)', function (data) {
            //console.log(data.elem); //得到radio原始DOM对象
            //console.log(data.value); //被点击的radio的value值
            var type = '${type}';
            if (type != data.value) {
                $("[name='pageNum']").val(1);//第一页
                $("[name='type']").val(data.value);
            }
            $("#searchForm").submit();
        });
    });


    function updateCarById(id, status) {
        var typeStr = status == 0 ? '启用' : '冻结';
        layer.confirm('真的' + typeStr + '这辆车辆么?', {icon: 3, title: '提示'}, function (index) {
            $.ajax({
                type: "POST",
                url: "${ctx}/transport/car/updateById",
                data: {'id': id, 'status': status},
                dataType: "json",
                success: function (data) {
                    var msg = '';
                    var icon = 1;
                    if (data < 0) {
                        msg = '失败，系统错误！';
                        icon = 2;//失败
                    } else {
                        if (data == 0) {
                            msg = '失败';
                        } else {
                            msg = '成功';
                        }
                    }
                    layer.msg(typeStr + msg, {
                        time: 1000,
                        skin: 'layui-layer-molv',
                        area: '20px',
                        icon: icon
                    }, function () {
                        selfSubmit();
                    });
                }
            });
            layer.close(index);
            //向服务端发送操作指令
        });

    }


    /*
* status:
* 0在职，1休假，2离职
* recordStatus:
* 0正常，1删除，
*/
    function updateDriverById(id, recordStatus) {
        var typeStr = '删除';
        layer.confirm('真的' + typeStr + '这个司机么?', {icon: 3, title: '提示'}, function (index) {
            $.ajax({
                type: "POST",
                url: "${ctx}/transport/driver/updateById",
                data: {'id': id, 'recordStatus': recordStatus},
                dataType: "json",
                success: function (data) {
                    var msg = '';
                    var icon = 1;
                    if (data < 0) {
                        msg = '失败，系统错误！';
                        icon = 2;//失败
                    } else {
                        if (data == 0) {
                            msg = '失败';
                        } else {
                            msg = '成功';
                        }
                    }
                    layer.msg(typeStr + msg, {
                        time: 1000,
                        skin: 'layui-layer-molv',
                        area: '20px',
                        icon: icon
                    }, function () {
                        selfSubmit();
                    });
                }
            });
          //  layer.close(index);
            //向服务端发送操作指令
        });

    }

    /*****、新增、修改/司机*****/
    function update(id,changeType,companyId){

        parent.layer.open({
            type: 2,
            title: '司机添加修改',
            shadeClose: true,
            shade: 0.5,
            btn: ['提交', '取消'],
            yes: function(index, layero){
                var iframeWin = parent.window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
                iframeWin.laySubmit();//执行iframe页的方法：
            },
            btn2: function(index, layero){
                selfSearch();
            },
            cancel: function(index, layero){
                selfSubmit();
            },
            area: ['650px','600px'],
            content: '${ctx}/transport/driver/form?id='+id+"&changeType="+changeType+"&companyId="+companyId
        })
    }
    //跳到第一页
    function selfSearch() {

        $("#searchForm").submit();
    }

</script>
</body>
</html>