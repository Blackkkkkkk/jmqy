<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/layui/css/layui.css">
    <script src="${ctx}/static/js/jquery-3.2.1.min.js"></script>
    <script src="${ctx}/static/layui/layui.js"></script>
    <link rel="stylesheet" href="${ctx}/static/css/index.css">
    <link rel="stylesheet" href="${ctx}/static/css/platform.css">
    <link rel="stylesheet" href="${ctx}/static/css/style.css">
    <link rel="stylesheet" href="${ctx}/static/css/transport.css">

    <title></title>
    <style type="text/css">
        html,body{margin:0;padding:0;}
        .iw_poi_title {color:#CC5522;font-size:14px;font-weight:bold;overflow:hidden;padding-right:13px;white-space:nowrap}
        .iw_poi_content {font:12px arial,sans-serif;overflow:visible;padding-top:4px;white-space:-moz-pre-wrap;word-wrap:break-word}
        .BMap_stdMpCtrl,.anchorBL{
            display:none;
        }
        .linetd1{
            width:60%;
        }
        .linetd2{
            width:40%;
        }
        .line{
            width:100%;
            height:557px;
        }
        .tb3 td:nth-child(odd){
            text-align:right;
            width:150px;
        }
        input[name="pc"]{
            margin-right:3px;
            margin-left:5px;
            position:relative;
            top:2px;
        }
        .layui-table td, .layui-table th {

            line-height: 14px ;
        }
        .layui-table{
            margin:2px 0;
            position:relative;
        }
        textarea{
            width:100%;
        }

    </style>
    <script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=1w2xcsGWIGbPyhIngYF2uBK0"></script>
    <script type="text/javascript" src="https://api.map.baidu.com/getscript?v=2.0&ak=1w2xcsGWIGbPyhIngYF2uBK0&services=&t=20140411133140"></script>
    <script type="text/javascript" src="${ctx}/static/gis/js/MarkerTool_min.js"></script>
</head>
<body>
<form action="" class="layui-form">
    <table class="line" border="1px" borderColor="#aaa">
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
                        <td>文雅苑</td>
                        <td>目的地</td>
                        <td>江门第一中学</td>
                        <td>途经点</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>订单开始时间</td>
                        <td>2018-01-03 12:00:00</td>
                        <td>订单结束时间</td>
                        <td>2018-01-03 12:00:00</td>
                        <td>预估路程</td>
                        <td>20公里</td>
                    </tr>
                    <tr>
                        <td>预估时长</td>
                        <td>25分钟</td>
                        <td>订单路程</td>
                        <td>20公里</td>
                        <td>订单时长</td>
                        <td>25分钟</td>
                    </tr>
                    <tr>
                        <td>上车时间</td>
                        <td colspan="2">2018-01-03 12:00:00</td>
                        <td>返程时间</td>
                        <td colspan="2">2018-01-03 12:00:00</td>
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
                        <td>是否存在偏差：<input name="pc" checked="" lay-ignore type="radio">是 <input name="pc" lay-ignore type="radio">否</td>
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
                        <td><textarea placeholder="此处展示以往处理意见，格式如:处理意见  2018-01-22 14:30:00" name="" id="1" rows="2"></textarea> </td>
                    </tr>
                    <tr>
                        <td>修改意见:</td>
                        <td><textarea placeholder="此处为本次修改意见" name="" id="2" rows="2"></textarea> </td>
                    </tr>
                    <tr>
                        <td>处理日期:</td>
                        <td>2018-01-23 12:00:00</td>
                    </tr>
                    <tr>
                        <td>责任人:</td>
                        <td>admin</td>
                    </tr>
                    </tbody>
                </table>
            </td>
            <td class="linetd2">
                <div style="width:100%;height:550px;" id="dituContent"></div>
            </td>
        </tr>
    </table>
</form>

<script src="${ctx}/static/js/layui.john.js"></script>
<script src="${ctx}/static/js/john.js"></script>
<script type="text/javascript">


    initMap();//创建和初始化地图

    $('input[name="pc"]').click(function(){
        if($(this).index() == 0){
            $(this).parents('.layui-table').next('.tb3').show()
        }
        if($(this).index() == 1){
            $(this).parents('.layui-table').next('.tb3').hide()
        }
    })
    //创建和初始化地图函数：
    function initMap(){

        createMap();//创建地图
        setMapEvent();//设置地图事件
        addMapControl();//向地图添加控件
    }
    //创建地图函数：
    function createMap(){

        var map = new BMap.Map("dituContent");//在百度地图容器中创建一个地图
        var point = new BMap.Point(113.0917,22.58274);//定义一个中心点坐标
        map.centerAndZoom(point,18);//设定地图的中心点和坐标并将地图显示在地图容器中
        window.map = map;//将map变量存储在全局
    }
    //地图事件设置函数：
    function setMapEvent(){
        map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
        map.enableScrollWheelZoom();//启用地图滚轮放大缩小
        map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
        map.enableKeyboard();//启用键盘上下左右键移动地图
    }

    //地图控件添加函数：
    function addMapControl(){
        //向地图中添加缩放控件
        var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
        map.addControl(ctrl_nav);
        //向地图中添加缩略图控件
        // var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
        // map.addControl(ctrl_ove);
        //向地图中添加比例尺控件
        var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
        map.addControl(ctrl_sca);
    }



</script>
</body>
</html>
