<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>个人中心</title>
    <script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=1w2xcsGWIGbPyhIngYF2uBK0"></script>
    <script type="text/javascript"
            src="https://api.map.baidu.com/getscript?v=2.0&ak=1w2xcsGWIGbPyhIngYF2uBK0&services=&t=20140411133140"></script>
    <style type="text/css">
        .order_number {
            width: 326px !important;
        }

        .searh2 table td:nth-child(1) {
            width: 80px;
        }

        .anchorBL {
            display: none !important;
        }

        .searh label {
            width: 70px;
        }

        #releaseDetailForm .div_input {
            width: 315px;
        }
    </style>
</head>
<body>
<section>
    <div class="container">
        <jsp:include page="/WEB-INF/layouts/left_menu.jsp"/>
        <main>
            <div class="position_all">
                <div class="position">您所在的位置：首页 -> 个人中心 -> 立即预定</div>
            </div>
            <form id="releaseDetailForm">
                <input type="hidden" name="orderCode" value="${order.orderCode }"/>
                <input type="hidden" id="bigOrderCode" name="bigOrderCode" value="${order.bigOrderCode}"/>
                <input type="hidden" name="backOrderCode" value="${order.backOrderCode }"/>
                <input type="hidden" name="carCode" value="${order.carCode }"/>
                <input type="hidden" id="viaPoint" name="viaPoint" value="${order.viaPoint}"/>
                <input type="hidden" id="startLng" name="startLng" value="${order.startLng}"/>
                <input type="hidden" id="startLat" name="startLat" value="${order.startLat}"/>
                <input type="hidden" id="endLng" name="endLng" value="${order.endLng}"/>
                <input type="hidden" id="endLat" name="endLat" value="${order.endLat}"/>
                <input type="hidden" id="amount" name="amount" value="${order.amount}"/>
                <input type="hidden" name="distance" id="distance" value="${order.distance}"/>
                <input type="hidden" name="prices" id="prices" value="${order.prices}"/>
                <div class="title_order">订单信息</div>
                <div class="searh searh1 height1" style="position:relative;overflow:visible;">
                    <div class="searh_box" style="width: 611px;height: 490px;border-left: 1px solid #ccc;">

                        <div style="width:100%;" id="dituContent"></div>
                        <p><span>起点：${order.startPoint}</span><span>终点：${order.endPoint}</span><span
                                id="lineLength">里程：</span><span id="lineNeedHours">总时长：</span></p>
                        <p>此规划的路线为程序自动规划，可能与实际行驶路线有所偏差，如有问题，请与客服联系</p>
                    </div>
                    <div class="div_input">
                        <label>起始点：</label>${order.startPoint}
                    </div>
                    <c:if test="${not empty order.endPoint}">
                        <div class="div_input">
                            <label>下车点：</label>${order.endPoint}
                        </div>
                    </c:if>

                    <c:if test="${not empty order.charterDays}">
                        <div class="div_input">
                            <label>包车天数：</label>${order.charterDays}
                        </div>
                    </c:if>
                    <div class="div_input">
                        <label>包车行程：</label><c:if test="${order.tripType ==1}">单程</c:if><c:if
                            test="${order.tripType ==2}">往返</c:if>
                    </div>
                    <div class="div_input">
                        <label>上车时间：</label><fmt:formatDate value="${order.boardingTime}"
                                                            pattern="yyyy-MM-dd HH:mm:ss"/>
                    </div>
                    <div class="div_input">
                        <label>返程时间：</label><fmt:formatDate value="${order.trackBoardTime}"
                                                            pattern="yyyy-MM-dd HH:mm:ss"/>
                    </div>
                    <div class="div_input">
                        <label>途经点:</label> <span id="span"></span>
                    </div>
                    <div class="div_input">
                        <label>车型：</label>
                        <c:forEach items="${order.matchList}" var="m" varStatus="status">
                            <c:choose>
                                <c:when test="${order.distance > 300000}">
                                    ${fn:replace(m.carType,fn:substring(m.carType, 2, m.carType.length()-1),(fn:substring(m.carType, 2, m.carType.length()-1))-2)}
                                </c:when>
                                <c:when test="${order.distance < 300000}">
                                    ${fn:replace(m.carType,fn:substring(m.carType, 2, m.carType.length()-1),(fn:substring(m.carType, 2, m.carType.length()-1))-1)}
                                </c:when>
                            </c:choose>:${m.carAmount}辆;
                        </c:forEach>
                        <c:if test="${order.distance > 300000}">
                            <br>
                            <a style="color: #ff0000;">根据《道路旅客运输企业安全管理规范》相关法规规定:＂对于单程运行里程超过300公里的客运车辆，企业应当配备双班客运驾驶员＂。本次订单车座须预留一个副班座位，敬请核对实际用车
                                <c:forEach items="${order.matchList}" var="m" varStatus="status">
                                    <c:choose>
                                        <c:when test="${order.distance > 300000}">
                                            ${fn:replace(m.carType,fn:substring(m.carType, 2, m.carType.length()-1),(fn:substring(m.carType, 2, m.carType.length()-1))-2)}
                                        </c:when>
                                        <c:when test="${order.distance < 300000}">
                                            ${fn:replace(m.carType,fn:substring(m.carType, 2, m.carType.length()-1),(fn:substring(m.carType, 2, m.carType.length()-1))-1)}
                                        </c:when>
                                    </c:choose>
                                </c:forEach>

                                客座数。</a>
                        </c:if>
                    </div>
                </div>
                <div class="searh searh2 height2">
                    <div class="div_input div_dashed">
                        <label>附加选项：</label>
                        <c:if test="${empty order.additional}">
                            &nbsp;
                        </c:if>
                        <c:if test="${not empty order.additional}">
                            ${order.additional}
                        </c:if>
                    </div>
                    <%-- <div class="div_input">
                        <label>包车类型：</label>
                        <c:if test="${order.charterType ==1}">旅游包车</c:if><c:if test="${order.charterType ==2}">企业包车</c:if><c:if test="${order.charterType ==3}">个人包车</c:if>
                    </div> --%>
                </div>
                <div class="searh searh2 height3">
                    <div class="div_input div_dashed">
                        <label>联系人：</label>
                        <input class="search_text" type="text" name="linkMan" value="${user.userName}"
                               lay-verify="required"/>&nbsp;
                    </div>
                    <div class="div_input div_dashed">
                        <label>企业名称：</label>
                        <input class="search_text" type="text" name="companyName" value="${company.companyName}"
                               lay-verify="required"/>&nbsp;
                    </div>
                    <div class="div_input">
                        <label>联系电话：</label>
                        <input class="search_text" type="text" name="linkPhone" value="${user.phone}"
                               lay-verify="required|phone"/>&nbsp;&nbsp;&nbsp;
                    </div>
                </div>
                <div class="searh searh4 order_last">
                    <div class="order_number" style="width: 386px;">包车费共：<span>¥${order.prices}</span></div>
                    <c:if test="${order.status == -1}">
                        <div class="order_submit">
                            <input type="button" style="float:right;margin-top:10px;margin-left: 5px;" id="cancelBtn"
                                   onclick="cancelPay();" class="layui-btn layui-btn-primary" value="取消支付"/>
                            <input type="button" style="float:right;margin-top:10px;" id="payBtn"
                                   onclick="releaseDetailSubmit();" class="layui-btn layui-btn-danger" value="立即支付"/>
                            <div class="radio3">
                              <!--  <input name="payType" type="radio" value="1" checked="checked"/> 全额支付 -->
                                <input name="payType" type="radio" value="2" checked="checked"/> 余额支付
                                <!-- 						<input name="payType" type="radio" value="2"/> 预约金支付 -->
                                <input name="payType" type="radio" value="3"/> 记账支付
                            </div>

                        </div>
                        <div>
                            <p class="time2" style="padding-top: 15px;padding-right: 30px;">剩余支付时间 <strong
                                    id="minute_show">30分</strong><strong id="second_show">00秒</strong>.</p>
                        </div>
                    </c:if>

                </div>
                <div class="searh searh2 yuding" style="display: none">
                    <h1>预订须知</h1>
                    <table>
                        <tr>
                            <td>价格包含:</td>
                            <td>包含(8小时100公里)/天×1天司导服务费/小费、司导餐费、司导住宿费、燃油费、长途费、返空费、夜间服务费。</td>
                        </tr>
                        <tr>
                            <td>价格不含:</td>
                            <td>由乘客临时增加用车需求而产生的费用按如下标准收费。<span class="show_table">详情 <i
                                    class="icon-sort-down"></i></span></td>
                        </tr>
                        <tr class="hide_table">
                            <td colspan="2">
                                <table class="table2" border="1" bordercolor="#ccc">
                                    <tr>
                                        <td>超公里费</td>
                                        <td>当日用车总里程超过100公里后，乘客主动要求增加用车里程，将按1元/公里收取超公里费。</td>
                                    </tr>
                                    <tr>
                                        <td>超时长费</td>
                                        <td>当日用车总时长超过8小时后，乘客主动要求增加用车时长，将按30元/小时标准收取超时长费。</td>
                                    </tr>
                                    <tr>
                                        <td>第三方收费</td>
                                        <td>因第三方收取的停车费、高速路桥费由乘客现场自付，小票可自行保留。</td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td>开具发票:</td>
                            <td>如需发票，由携程统一开具。请在服务完成后至订单详情页填写开票信息，开票总金额包含超时超公里费用（发票金额不含礼品卡支付金额以及客人自付第三方停车、路桥费金额）。</td>
                        </tr>
                        <tr>
                            <td>套餐期限:</td>
                            <td>预订多日包车，每日套餐内容不累计，当日24点仍未使用自动作废。建议每日最长包车时长不超过12小时。</td>
                        </tr>
                    </table>
                </div>
            </form>
        </main>
    </div>
</section>
</div>
<script type="text/javascript">

    $(document).ready(function () {


        if ('${order.viaPoint}' != null & '${order.viaPoint}' != '') {
            var splitArray = new Array();
            var regex = /;/;
            var string = '${order.viaPoint}';
            var restring = '';
            splitArray = string.split(regex);
            for (i = 0; i < splitArray.length - 1; i++) {
                var num = splitArray[i].indexOf("@");
                restring = restring + splitArray[i].substring(0, num) + "、";
            }

            $('#span').html(restring);
            //   $("#lab").append(restring);
        }

        var splitArray = new Array();


    })

    function releaseDetailSubmit() {

        var orderCode = $("[name='orderCode']").val();
        var bigOrderCode = $("[name='bigOrderCode']").val();
        var linkMan = $("[name='linkMan']").val();
        var amount = $("[name='amount']").val();
        var prices = $("[name='prices']").val();

        var linkPhone = $("[name='linkPhone']").val();
        var payType = $("[name='payType']:checked").val();
        if (typeof(payType) == "undefined" || payType == 'undefined') {
            var payType = 1;
        }

        var reg = /^1[0-9]{10}$/;

        if (null == linkMan || "" == linkMan) {
            layer.msg("联系人不能为空!", {icon: 2, time: 1000});
            return;
        }
        if (null == linkPhone || "" == linkPhone) {
            layer.msg("联系方式不能为空!", {icon: 2, time: 1000});
            return;
        }
        if (!reg.test(linkPhone)) {
            layer.msg("联系方式格式不正确!", {icon: 2, time: 1000});
            return;
        }
        var dataJson = {
            "bigOrderCode": bigOrderCode, "linkMan": linkMan, "linkPhone": linkPhone, "payType": payType,
            "prices": prices
        };
        var content = '确定提交吗？';
        layer.confirm(content, {icon: 3, title: '提示'}, function (index) {
            $('.layui-layer-btn0').css('pointer-events', 'none');
            $.ajax({
                type: "post",
                url: "${ctx}/charter/order/saveReleaseDetail",
                dataType: "json",
                data: dataJson,
                success: function (data) {
                    if (data.state == 1) {
                        layer.open({
                            icon: 1,
                            title: '信息',
                            skin: 'layer-ext-myskin',
                            shade: 0, //不显示遮罩
                            content: "操作成功！",
                            yes: function () {
                                location.replace('${ctx}/charter/order/list/0');
                            },
                        })
                    } else if (data.state == 2) {
                        parent.layer.closeAll();
                        wechatPay(bigOrderCode, prices);
                    } else if (data.state == 3) {
                        layer.msg("信用额度不够!", {icon: 2, time: 1000})
                    } else if (data.state == 4) {
                            layer.msg("余额额度不够!", {icon: 2, time: 1000});
                    } else if (data.state == 99) {
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

    //取消支付
    function cancelPay() {
        layer.confirm('确实取消支付吗？', {icon: 3, title: '提示'}, function (index) {
            var bigOrderCode = $("[name='bigOrderCode']").val();
            var orderCode = $("[name='orderCode']").val();
            var backOrderCode = $("[name='backOrderCode']").val();
            var carCode = $("[name='carCode']").val();
            var dataJson = {"bigOrderCode": bigOrderCode, "backOrderCode": backOrderCode, "carCode": carCode};
            $.ajax({
                type: "post",
                url: "${ctx}/charter/order/cancelPay",
                dataType: "json",
                data: dataJson,
                success: function (data) {
                    if (data) {
                        layer.alert('取消成功！', {icon: 1}, function () {
                           // window.location.replace('${ctx}/charter/order/pushOrder?bigOrderCode=' + bigOrderCode);
                            window.location.replace('${ctx}/charter/order/pushOrder?bigOrderCode');
                        });
                    } else {
                        layer.alert('取消失败！', {icon: 2}, function () {
                            //window.location.replace('${ctx}/charter/order/pushOrder?orderCode='+orderCode);
                        });
                    }
                }
            });
        });
    }

    function wechatPay(bigOrderCode, prices) {
        var width = "400px";
        var height = "300px;"
        var title = "订单支付";
        layer.open({
            type: 2,
            title: title,
            shadeClose: true,
            shade: 0.5,
            yes: function (index, layero) {
                var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
                iframeWin.laySubmit();//执行iframe页的方法：
            },
            area: [width, height],
            // content: '${ctx}/charter/wechat/scanPay?bigOrderCode='+bigOrderCode
            content: '${ctx}/charter/wechat/newscanPay?bigOrderCode=' + bigOrderCode + '&prices=' + prices + '&actiontype=1'
        })
    }

    //文档加载完毕执行
    $(function () {
        var startPoint = null, endPoint = null, waypoints = [], via_Points = "";

        /**初始化途经点**/
        var viaPoint = $('#viaPoint').val();
        if (viaPoint != null && viaPoint != '') {
            var viaPoints = viaPoint.split(';');
            for (var i = 0; i < viaPoints.length; i++) {
                if (viaPoints[i] != null && viaPoints[i] != '') {
                    if (i == 0) {
                        via_Points = viaPoints[i].split('@')[0];
                    } else {
                        via_Points = via_Points + "&nbsp;&nbsp;" + viaPoints[i].split('@')[0];
                    }
                    waypoints[i] = new BMap.Point(viaPoints[i].split('@')[1].split(',')[0], viaPoints[i].split('@')[1].split(',')[1]);
                }
            }
        }
        startPoint = new BMap.Point($('#startLng').val(), $('#startLat').val());
        endPoint = new BMap.Point($('#endLng').val(), $('#endLat').val());
        searchLine(startPoint, endPoint, waypoints);
        $('#via_Points').html(via_Points);
    });

    //地图加载》》》》》》start
    var xPoint = 0;
    var yPoint = 0;

    //创建地图实例
    var map = new BMap.Map("dituContent");

    //创建点坐标
    var point = new BMap.Point(xPoint, yPoint);

    var markers = [];//标注数组

    //初始化地图，设置中心点坐标和地图级别
    map.centerAndZoom(point, 13);
    //添加默认比例尺控件
    map.addControl(new BMap.ScaleControl());
    //滚轮缩放事件
    map.enableScrollWheelZoom();
    //开启连续缩放效果
    map.enableContinuousZoom();
    //地图加载》》》》》》end

    //获取线路规划
    function searchLine(startPoint, endPoint, waypoints) {
        var route = 0;
        var driving = new BMap.DrivingRoute(map, {
            renderOptions: {map: map, enableDragging: false},
            onSearchComplete: searchComplete,
            policy: route
        });
        map.clearOverlays();
        driving.search(startPoint, endPoint, {waypoints: waypoints});//waypoints表示途经点
    }

    var searchComplete = function (results) {
        var plan = results.getPlan(0);
        var lineLength = plan.getDistance(true);
        var lineNeedHours = plan.getDuration(true);
        $("#distance").val(plan.getDistance(false));
        $("#lineLength").html("总路程：" + lineLength);
        var duration = Math.ceil(plan.getDuration(false) * 1.15);
        $("#lineNeedHours").html("总时长约：" + lineNeedHours + " ~ " + secondFormat(duration));
    };

    /***倒计时***/
    var intDiff = parseInt(300);//倒计时总秒数量 300
    function timer(intDiff) {
        window.setInterval(function () {
            var day = 0,
                hour = 0,
                minute = 0,
                second = 0;//时间默认值
            if (intDiff > 0) {
                day = Math.floor(intDiff / (60 * 60 * 24));
                hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
                minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
                second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
            }
            if (minute <= 9) minute = '0' + minute;
            if (second <= 9) second = '0' + second;
            $('#minute_show').html('<s></s>' + minute + '分');
            $('#second_show').html('<s></s>' + second + '秒');
            if (intDiff == 0) {
                $("#payBtn").attr("disabled", true);
                $("#payBtn").css("background-color", "#CCC");
                clearInterval(timer);
                layer.alert('订单支付超时！', {icon: 0}, function () {
                    window.location.replace('${ctx}/charter/order/pushOrder?orderCode=' + $("[name='orderCode']").val());
                });
            }
            intDiff--;
        }, 1000)
    }

    $(function () {
        timer(intDiff);
    });
    /******线路地图高度******/
    var addHeight = $('.height1').height() + $('.height2').height() + $('.height3').height();
    $('#dituContent').height(addHeight - 50)
    $('.searh_box').height(addHeight + 42)
</script>
</body>
</html>