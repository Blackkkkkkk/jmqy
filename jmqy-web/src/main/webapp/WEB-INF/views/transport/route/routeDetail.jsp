<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="now" class="java.util.Date" scope="page"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <style type="text/css">
        html, body {
            margin: 0;
            padding: 0;
        }

        .iw_poi_title {
            color: #CC5522;
            font-size: 14px;
            font-weight: bold;
            overflow: hidden;
            padding-right: 13px;
            white-space: nowrap
        }

        .iw_poi_content {
            font: 12px arial, sans-serif;
            overflow: visible;
            padding-top: 4px;
            white-space: -moz-pre-wrap;
            word-wrap: break-word
        }

        .BMap_stdMpCtrl, .anchorBL {
            display: none;
        }

        .line {
            width: 100%;
            height: 557px;
        }

        .tb3 td:nth-child(odd) {
            text-align: right;
            width: 150px;
        }

        input[name="pc"] {
            margin-right: 3px;
            margin-left: 5px;
            position: relative;
            top: 2px;
        }

        .layui-table td, .layui-table th {

            line-height: 14px;
        }

        .layui-table {
            margin: 2px 0;
            position: relative;
        }

        textarea {
            width: 100%;
        }

        .info {
            padding: 0px;
            width: 1020px;
        }
    </style>
    <script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=1w2xcsGWIGbPyhIngYF2uBK0"></script>
    <script type="text/javascript"
            src="https://api.map.baidu.com/getscript?v=2.0&ak=1w2xcsGWIGbPyhIngYF2uBK0&services=&t=20140411133140"></script>
    <script type="text/javascript" src="${ctx}/static/gis/js/MarkerTool_min.js"></script>
</head>
<body>
<section>
    <div class="container">
        <jsp:include page="/WEB-INF/layouts/left_menu.jsp"/>
        <main>
            <div class="position_all">
                <div class="position">您所在的位置：首页 -> 管理中心 -> 线路管理</div>
            </div>

            <div class="info">
                <form class="layui-form" action="" name="myform">
                    <input id="orderCode" name="orderCode" type="hidden" value="${ordercode}">
                    <table class="line">
                        <tr>
                            <td class="linetd2">
                                <div style="width:100%;height:350px;" id="dituContent"></div>
                            </td>
                        </tr>
                        <tr>
                            <td class="linetd1" valign="top">
                                <table class="layui-table">
                                    <thead>
                                    <tr>
                                        <th colspan="6"><b>订单详情</b></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>起始点</td>
                                        <td>${order.startCity}</td>
                                        <td>目的地</td>
                                        <td>${order.endCity}</td>
                                        <td>途经点</td>
                                        <td>${order.viaPoint}</td>
                                    </tr>
                                    <tr>
                                        <td>上车时间</td>
                                        <td>
                                            <fmt:formatDate value="${order.boardingTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                           </td>
                                        <td>下车时间</td>
                                        <td>
                                            <fmt:formatDate value="${order.debusTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                            </td>
                                        <td>预估路程</td>
                                        <td>${order.distance}</td>
                                    </tr>
                                    <tr>
                                        <td>预估时长</td>
                                        <td>${order.duration}</td>
                                        <td>返程上车时间</td>
                                        <td>
                                            <c:if test="${order.trackBoardTime !=null && order.trackBoardTime !=''} ">
                                                <fmt:formatDate value="${order.trackBoardTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                            </c:if>

                                        </td>
                                        <td>返程时间</td>
                                        <td>
                                            <c:if test="${order.trackDebusTime !=null && order.trackDebusTime !=''} ">
                                                <fmt:formatDate value="${order.trackDebusTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                            </c:if>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <table class="layui-table">
                                    <thead>
                                    <tr>
                                        <th colspan="4"><b>附加信息</b></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>车牌号</td>
                                        <td>粤A12345</td>
                                        <td>司机</td>
                                        <td>张小小</td>
                                    </tr>
                                    </tbody>
                                </table>
                                <table class="layui-table">
                                    <thead>
                                    <tr>
                                        <th><b>其它</b></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>是否存在偏差：<input name="deviation" checked="" lay-ignore type="radio" value="1">是
                                            <input name="deviation" lay-ignore type="radio" value="0">否
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <table class="layui-table tb3">
                                    <thead>
                                    <tr>
                                        <th colspan="2"><b>审核详情</b></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>历史处理意见:</td>
                                        <td><textarea placeholder="此处展示以往处理意见，格式如:处理意见  2018-01-22 14:30:00" name=""
                                                      id="" rows="2" disabled="true"
                                                      style="border:none;background-color:transparent;">${handlingRemark}</textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>修改意见:</td>
                                        <td><textarea placeholder="此处为本次修改意见" name="handlingRemark" id="handlingRemark"
                                                      rows="2" required="required"></textarea></td>
                                    </tr>
                                    <tr>
                                        <td>处理日期:</td>
                                        <td><input id="creationDate" name="creationDate" type="text"
                                                   value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm:ss" />"
                                                   disabled="true" style="border:none;background-color:transparent;">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>责任人:</td>
                                        <td><input name="responsible" id="responsible" value="${username}"
                                                   disabled="true" style="border:none;background-color:transparent;">
                                        </td>
                                    </tr>
                                    <tr id="butt">
                                        <td align="center" colspan="2" style="text-align:center;"><a
                                                class="layui-btn layui-btn-normal submit" onclick="save();">保存</a></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </main>
    </div>
</section>
<script src="${ctx}/static/js/layui.john.js"></script>
<script src="${ctx}/static/js/john.js"></script>
<script type="text/javascript">


    $(document).ready(function () {
    })

    initMap();//创建和初始化地图

    $('input[name="pc"]').click(function () {
        if ($(this).index() == 0) {
            $(this).parents('.layui-table').next('.tb3').show()
        }
        if ($(this).index() == 1) {
            $(this).parents('.layui-table').next('.tb3').hide()
        }
    })

    //创建和初始化地图函数：
    function initMap() {

        createMap();//创建地图
        setMapEvent();//设置地图事件
        addMapControl();//向地图添加控件


        //根据条数判断是否隐藏保存按钮
        var row = document.getElementById("butt");
        if ('${list}' > 2) {
            row.style.display = "none";
        } else {
            row.style.display = "";
        }

        //为radio赋值

        var sex =${radioValue};

        if (sex == 2) {

        } else {

            var ridaolen = document.myform.deviation.length;
            for (var i = 0; i < ridaolen; i++) {
                if (sex == document.myform.deviation[i].value) {
                    document.myform.deviation[i].checked = true
                }
            }
        }


    }

    //保存值
    function save() {

        var creationDate = $("#creationDate").val();
        var handlingRemark = $("#handlingRemark").val();

        if (handlingRemark == '' || handlingRemark == null) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg("修改意见不能为空！")
            });
            return false;
        }

        var deviation;
        var orderCode = $("#orderCode").val();

        var radio = document.getElementsByName("deviation");
        for (i = 0; i < radio.length; i++) {
            if (radio[i].checked) {
                deviation = radio[i].value;
            }
        }
        $.ajax({
            type: "post",
            url: "${ctx}/transport/route/saveRouteDetail",
            dataType: "json",
            data: {
                "creationDate": creationDate,
                "handlingRemark": handlingRemark,
                "deviation": deviation,
                "orderCode": orderCode
            },
            success: function (data) {
                if (data.state) {

                    layui.use('layer', function () {
                        var layer = layui.layer;

                        layer.alert('保存成功！', {icon: 1}, function () {
                            window.location.replace('${ctx}/transport/route/routeDetail?orderCode=' + orderCode);
                        });
                    });


                }
            }
        })
    }

    //创建地图函数：
    function createMap() {


        var map = new BMap.Map("dituContent");    // 创建Map实例
        map.centerAndZoom(new BMap.Point(113.088883, 22.623836), 14);  // 初始化地图,设置中心点坐标和地图级别
        map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

        //   map.centerAndZoom(point, 18);//设定地图的中心点和坐标并将地图显示在地图容器中
        window.map = map;//将map变量存储在全局

        var sy = new BMap.Symbol(BMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW, {
            scale: 0.6,//图标缩放大小
            strokeColor: '#fff',//设置矢量图标的线填充颜色
            strokeWeight: '2',//设置线宽
        });
        var icons = new BMap.IconSequence(sy, '10', '30');
        // 创建polyline对象

        var longLatPlan = '${map.longLatPlan}';
        var longLatPlantArray = new Array();
        var regex = /;/;

        var pois = [];

        longLatPlantArray = longLatPlan.split(regex);
        for (i = 0; i < longLatPlantArray.length; i++) {
            if (longLatPlantArray[i].length > 5) {
                var lLtArray = new Array();
                var regex = /,/;
                lLtArray = longLatPlantArray[i].split(regex);
                for (j = 0; j < lLtArray.length; j++) {
                    pois.push(new BMap.Point(lLtArray[0], lLtArray[1]));
                }

            }

        }


        var longLat = '${map.longLat}';
        var longLatArray = new Array();
        var regex = /;/;

        var pois1 = [];

        longLatArray = longLat.split(regex);
        for (i = 0; i < longLatArray.length; i++) {
            if (longLatArray[i].length > 5) {
                var lLtArray = new Array();
                var regex = /,/;
                lLtArray = longLatArray[i].split(regex);
                for (j = 0; j < lLtArray.length; j++) {
                    pois1.push(new BMap.Point(lLtArray[0], lLtArray[1]));
                }

            }

        }


        var polyline = new BMap.Polyline(pois, {
            enableEditing: false,//是否启用线编辑，默认为false
            enableClicking: true,//是否响应点击事件，默认为true
            icons: [icons],
            strokeWeight: '8',//折线的宽度，以像素为单位
            strokeOpacity: 0.8,//折线的透明度，取值范围0 - 1
            strokeColor: "#18a45b" //折线颜色
        });


        var polyline1 = new BMap.Polyline(pois1, {
            enableEditing: false,//是否启用线编辑，默认为false
            enableClicking: true,//是否响应点击事件，默认为true
            icons: [icons],
            strokeWeight: '8',//折线的宽度，以像素为单位
            strokeOpacity: 0.8,//折线的透明度，取值范围0 - 1
            strokeColor: "#FF00FF" //折线颜色
        });

        map.addOverlay(polyline);          //增加折线
        map.addOverlay(polyline1);          //增加折线

    }

    //地图事件设置函数：
    function setMapEvent() {
        map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
        map.enableScrollWheelZoom();//启用地图滚轮放大缩小
        map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
        map.enableKeyboard();//启用键盘上下左右键移动地图
    }

    //地图控件添加函数：
    function addMapControl() {
        //向地图中添加缩放控件
        var ctrl_nav = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_LEFT, type: BMAP_NAVIGATION_CONTROL_LARGE});
        map.addControl(ctrl_nav);
        //向地图中添加缩略图控件
        // var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
        // map.addControl(ctrl_ove);
        //向地图中添加比例尺控件
        var ctrl_sca = new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_LEFT});
        map.addControl(ctrl_sca);
    }


    setInterval("setTime()", 1000)


    function setTime() {

        $("#creationDate").val(getNowFormatDate());

    }

    // 获取当前系统时间格式化为 yyyy-MM-dd HH:mm:ss
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
        return currentdate;
    }

</script>

</body>
</html>
