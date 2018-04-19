<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>首页</title>
<style>
/*.layui-input-block {*/
    /*margin-left: 80px;*/
/*}*/
/*.index_box {*/
	/*width:430px;*/
/*}*/
/*.layui-input{*/
	/*width: 85%;*/
/*}*/
div[contype="string"] {
    border: 1px solid #e7f4f1;
    border-radius: 4px;
}
    #reminds{
        color:#fc0b27;
    }
</style>
    <link rel="icon" href="${ctx}/static/images/favicon.ico" type="image/x-icon">
    <script src="${ctx}/static/js/jquery-3.2.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctx}/static/swiper/swiper.min.css">
    <link rel="stylesheet" href="${ctx}/static/swiper/animate.min.css">
    <link rel="stylesheet"  type="text/css" href="${ctx}/static/css/swiperindex.css">
    <link rel="stylesheet"  type="text/css" href="${ctx}/static/css/swiper.css">
    <!-- js -->
    <script src="${ctx}/static/js/jquery-3.2.1.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/layui/layui.js" type="text/javascript"></script>
    <script src="http://echarts.baidu.com/build/dist/echarts.js" type="text/javascript"></script>
    <script src="${ctx}/static/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script src="${ctx}/static/layui/layui.js"></script>
    <script src="${ctx}/static/layui/layui.all.js"></script>
    <script src="${ctx}/static/js/layui.john.js" type="text/javascript"></script>
    <script src="${ctx}/static/layui/john.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/charter.js" type="text/javascript"></script>
    <!-- dwr -->
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/interface/MessagePush.js"></script>
</head>
<body class="index_banner">

<header>
    <div class="header-container">
        <div class="logo"><a href="${ctx}/index"><img src="${ctx}/static/images/logo.png"> <h1>马上走</h1></a></div>
        <div class="login-index">
            <shiro:guest>
                <a class="loging" href="javascript:;">登录</a> | <a id="register" href="javascript:;">注册</a>
            </shiro:guest>
            <shiro:user>
                <div style="display: block;" class="logout1">
                  <shiro:principal property="name"/>
                    <span>
					<a id="logout" href="javascript:;">退出</a>
					<a href="${ctx}/remind/list"><span id="reminds">0</span></a>
				</span>
                </div>
            </shiro:user>
        </div>

        <nav>
            <ul>

                <li id="首页" onclick="forward('${ctx}/index', 0);"><span><a href="javascript:;">首页</a></span></li>
                <%--<li id="租赁包车" class="chartered"><span><a href="javascript:forward('${ctx}/search/charter',0);">租赁包车</a></span></li>--%>
                <%--<li id="定制巴士"><span><a href="javascript:;">定制巴士</a> </span></li>--%>
                <%--<li id="客运班车"><span><a href="javascript:;">客运班车</a></span></li>--%>
                <%--<li id="周边游"><span><a href="javascript:;">周边游</a></span></li>--%>
                <li id="会员中心" onclick="forward('${ctx}/user/main', 1);"><span><a href="javascript:;">会员中心</a></span></li>
                <%--<li><a href="javascript:;">关注我们</a></li>--%>
            </ul>
        </nav>
    </div>
</header>
<div class="swiper-container  swiper-container-v">
    <div class="swiper-wrapper">
        <div class="swiper-slide slide3">
            <ul id="banner_lun">
                <li><div class="banner1"></div></li>
                <li><div class="banner2"></div></li>
                <li><div class="banner3"></div></li>
            </ul>

            <div class="ani col-md-635" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s">
                <div class="index-search">
                    <div class="john-tab">
                        <ul class="john-tab-title">
                            <li class="john-this">租赁包车</li>
                          <!--  <li>定制巴士</li> -->
                        </ul>
                        <div class="john-tab-content">
                            <div class="john-tab-item john-tab-show">
                                <br/><br/>
                                <form id="searchForm" action="${ctx}/search/charter" method="post">
                                    <div class="john-item">
                                        <label class="john-label">上车点</label>
                                        <div class="john-inline">
                                            <input type="text" class="john-input" id="startPoint" name="startPoint" value="" onclick="showMap(this);" type="text" placeholder="点击在地图选点" readonly style="cursor: pointer;"/>
                                        	<input type="hidden" id="startLng" name="startLng" value=""/>
											<input type="hidden" id="startLat" name="startLat" value=""/>
											<input type="hidden" id="startArea" name="startArea" value="">
											<input type="hidden" id="startCity" name="startCity" value="">
                                        </div>
                                    </div>
                                    <br/>
                                    <div class="john-item">
                                        <label class="john-label">下车点</label>
                                        <div class="john-inline">
                                            <input type="text" class="john-input" id="endPoint" name="endPoint" value="" onclick="showMap(this);" type="text" placeholder="点击在地图选点" readonly style="cursor: pointer;"/>
                                        	<input type="hidden" id="endLng" name="endLng" value=""/>
											<input type="hidden" id="endLat" name="endLat" value=""/>
											<input type="hidden" id="endArea" name="endArea" value="">
											<input type="hidden" id="endCity" name="endCity" value="">
                                        </div>
                                    </div>
                                    <a class="index-btn" href="javascript:;"  onclick="search_();">立即选车</a>

                                </form>
                            </div>
                            <div class="john-tab-item">

                                <br/><br/>
                                <form action="">
                                    <div class="john-item">
                                        <label class="john-label">上车点</label>
                                        <div class="john-inline">
                                            <input type="text" class="john-input">
                                        </div>
                                    </div>
                                    <br/>
                                    <div class="john-item">
                                        <label class="john-label">下车点</label>
                                        <div class="john-inline">
                                            <input type="text" class="john-input">
                                        </div>
                                    </div>
                                    <a class="index-btn" href="chartered.html">立即选车</a>

                                </form>

                            </div>
                        </div>
                    </div>

                </div>





            </div>
            <div class="ani col-md-465" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s">
                <img src="${ctx}/static/images/aboutus1.png">
            </div>
        </div>
        <%--<div class="swiper-slide slide2">--%>

            <%--<div class="ani col-md-4" swiper-animate-effect="fadeInLeft" swiper-animate-duration="1s" swiper-animate-delay="0s">--%>
                <%--<div class="col-bg clo-md-4-1"></div>--%>
                <%--<div class="col-md-4-info">--%>
                    <%--<img src="${ctx}/static/images/slide2_1.png"><br/>--%>
                    <%--<h1>租赁包车</h1>--%>
                    <%--<h3>Rental Car</h3>--%>
                    <%--<ul>--%>
                        <%--<li><a href="">江门至台山往返只需<span>￥450</span></a></li>--%>
                        <%--<li><a href="">江门至台山往返只需<span>￥450</span></a></li>--%>
                        <%--<li><a href="">江门至台山往返只需<span>￥450</span></a></li>--%>
                        <%--<li><a href="">江门至台山往返只需<span>￥450</span></a></li>--%>
                    <%--</ul>--%>


                    <%--<a class="show_a" href="javascript:void(0)">更多</a>					</div>--%>
            <%--</div>--%>

            <%--<div class="ani col-md-4" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s">--%>
                <%--<div class="col-bg clo-md-4-2"></div>--%>
                <%--<div class="col-md-4-info">--%>
                    <%--<img src="${ctx}/static/images/slide2_2.png"><br/>--%>
                    <%--<h1>定制巴士</h1>--%>
                    <%--<h3>Custom Bus</h3>--%>
                    <%--<ul>--%>
                        <%--<li><a href="">江门至台山往返只需<span>￥450</span></a></li>--%>
                        <%--<li><a href="">江门至台山往返只需<span>￥450</span></a></li>--%>
                        <%--<li><a href="">江门至台山往返只需<span>￥450</span></a></li>--%>
                        <%--<li><a href="">江门至台山往返只需<span>￥450</span></a></li>--%>
                    <%--</ul>--%>
                    <%--<a class="show_a"  href="javascript:void(0)">更多</a>					</div>--%>
            <%--</div>--%>

            <%--<div class="ani col-md-4" swiper-animate-effect="fadeInUpBig" swiper-animate-duration="1s" swiper-animate-delay="0s">--%>
                <%--<div class="col-bg clo-md-4-3"></div>--%>
                <%--<div class="col-md-4-info">--%>
                    <%--<img src="${ctx}/static/images/slide2_3.png"><br/>--%>
                    <%--<h1>客运班车</h1>--%>
                    <%--<h3>Passenger Train</h3>--%>
                    <%--<ul>--%>
                        <%--<li><a href="">江门至台山往返只需<span>￥450</span></a></li>--%>
                        <%--<li><a href="">江门至台山往返只需<span>￥450</span></a></li>--%>
                        <%--<li><a href="">江门至台山往返只需<span>￥450</span></a></li>--%>
                        <%--<li><a href="">江门至台山往返只需<span>￥450</span></a></li>--%>
                    <%--</ul>--%>
                    <%--<a class="show_a"  href="javascript:void(0)">更多</a>					</div>--%>
            <%--</div>--%>

            <%--<div class="ani col-md-4" swiper-animate-effect="fadeInRight" swiper-animate-duration="1s" swiper-animate-delay="0s">--%>
                <%--<div class="col-bg clo-md-4-4"></div>--%>
                <%--<div class="col-md-4-info">--%>
                    <%--<img src="${ctx}/static/images/slide2_4.png"><br/>--%>
                    <%--<h1>周边游</h1>--%>
                    <%--<h3>Around</h3>--%>
                    <%--<ul>--%>
                        <%--<li><a href="">江门至台山往返只需<span>￥450</span></a></li>--%>
                        <%--<li><a href="">江门至台山往返只需<span>￥450</span></a></li>--%>
                        <%--<li><a href="">江门至台山往返只需<span>￥450</span></a></li>--%>
                        <%--<li><a href="">江门至台山往返只需<span>￥450</span></a></li>--%>
                    <%--</ul>--%>
                    <%--<a class="show_a"  href="javascript:void(0)">更多</a>					</div>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="swiper-slide slide1">--%>
            <%--<div class="swiper-slide banner4">--%>
                <%--<div class="ani john4" swiper-animate-effect="fadeInUpBig" swiper-animate-duration="1s" swiper-animate-delay="0s">--%>
                    <%--<div class="john-tab">--%>
                        <%--<ul class="john-tab-title">--%>
                            <%--<li class="john-this">租赁包车</li>--%>
                            <%--<li>定制巴士</li>--%>
                            <%--<li>客运班车</li>--%>
                            <%--<li>周边游</li>--%>
                        <%--</ul>--%>
                        <%--<div class="john-tab-content">--%>
                            <%--<div class="john-tab-item john-tab-show">--%>
                                <%--<div class="cl"></div>--%>
                                <%--<div class="detail-box-left" >--%>
                                    <%--<ul>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>

                                    <%--</ul>--%>

                                <%--</div>--%>


                            <%--</div>--%>
                            <%--<div class="john-tab-item">--%>

                                <%--<div class="cl"></div>--%>
                                <%--<div class="detail-box-left" >--%>
                                    <%--<ul>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>

                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>

                                    <%--</ul>--%>

                                <%--</div>--%>


                            <%--</div>--%>
                            <%--<div class="john-tab-item">--%>

                                <%--<div class="cl"></div>--%>
                                <%--<div class="detail-box-left" >--%>
                                    <%--<ul>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>

                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>

                                    <%--</ul>--%>

                                <%--</div>--%>


                            <%--</div>--%>
                            <%--<div class="john-tab-item">--%>

                                <%--<div class="cl"></div>--%>
                                <%--<div class="detail-box-left" >--%>
                                    <%--<ul>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<p class="p1">开平 <i class="icon-exchange">—</i> 恩平</p>--%>
                                            <%--<p>9月08日 - 9月10日</p><br>--%>
                                            <%--<p><sup>￥</sup><span class="orange">300</span>起<a class="layui-btn layui-btn-danger layui-btn-small" href="chartered.html">预定</a></p>--%>
                                        <%--</li>--%>


                                    <%--</ul>--%>

                                <%--</div>--%>


                            <%--</div>--%>

                        <%--</div>--%>


                    <%--</div>--%>



                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>


        <%--<div class="swiper-slide slide4">--%>
            <%--<div class="ani col-md-comnew" swiper-animate-effect="fadeInLeft" swiper-animate-duration="1s" swiper-animate-delay="0s">--%>
                <%--<img src="${ctx}/static/images/slide4.png">				</div>--%>
            <%--<div class="ani col-md-44" swiper-animate-effect="fadeInLeft" swiper-animate-duration="1s" swiper-animate-delay="0s">--%>
                <%--<h2>定制巴士狂返1000万积分!</h2>--%>
                <%--<h3>2016-6-17</h3>--%>
                <%--<p>--%>
                    <%--定制巴士狂返1000积分,定制巴士狂返1000积分,定制巴士狂返1000积分--%>
                <%--</p>--%>
                <%--<a href="javascript:void(0)"><img src="${ctx}/static/images/slide_a.png"></a>				</div>--%>
            <%--<div class="ani col-md-44" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s">--%>
                <%--<h2>定制巴士狂返1000万积分!</h2>--%>
                <%--<h3>2016-6-17</h3>--%>
                <%--<p>--%>
                    <%--定制巴士狂返1000积分,定制巴士狂返1000积分,定制巴士狂返1000积分--%>
                <%--</p>--%>
                <%--<a href="javascript:void(0)"><img src="${ctx}/static/images/slide_a.png"></a>				</div>--%>
            <%--<div class="ani col-md-44" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s">--%>
                <%--<h2>定制巴士狂返1000万积分!</h2>--%>
                <%--<h3>2016-6-17</h3>--%>
                <%--<p>--%>
                    <%--定制巴士狂返1000积分,定制巴士狂返1000积分,定制巴士狂返1000积分--%>
                <%--</p>--%>
                <%--<a href="javascript:void(0)"><img src="${ctx}/static/images/slide_a.png"></a>				</div>--%>
            <%--<div class="ani col-md-44" swiper-animate-effect="fadeInRight" swiper-animate-duration="1s" swiper-animate-delay="0s">--%>
                <%--<h2>定制巴士狂返1000万积分!</h2>--%>
                <%--<h3>2016-6-17</h3>--%>
                <%--<p>--%>
                    <%--定制巴士狂返1000积分,定制巴士狂返1000积分,定制巴士狂返1000积分--%>
                <%--</p>--%>
                <%--<a href="javascript:void(0)"><img src="${ctx}/static/images/slide_a.png"></a>				</div>--%>
        <%--</div>--%>
        <div class="swiper-slide slide5">
            <div class="ani col-md-439" swiper-animate-effect="fadeInRight" swiper-animate-duration="1s" swiper-animate-delay="0s">
                <div class="aboutus">
                    <h1>关于我们 | <span>About Us</span></h1>
                    <p>广东省江门市汽运集团有限公司是目前广东省六家全国道路旅客运输一级企业之一，是交通运输部、广东省交通运输厅重点联系道路运输企业 。
                        <br><br>
                        广东省江门市汽运集团有限公司是目前广东省六家全国道路旅客运输一级企业之一，是交通运输部和广东省交通运输厅重点联系道路运输企业， 2004年－2009年连续六年入选“中国道路运输百强诚信企业”。
                        <br><br>
                        集团核心成员包括：江门、江门市新会区、台山、鹤山、开平、恩平等六个汽车总站和汽车保修厂，拥有江门汽车制修总厂、江门市公共汽车有限公司、江门市旅游汽车有限公司、江门市零担货运有限公司、新会公汽等多家全资子公司。集团总资产达9.34亿元，公司拥有各类营运车辆2000多辆。2009年日发客运班次13200多班，年客运量13600多万人，年客运周转量31亿多人公里，客运班线覆盖广东各市及主要县、镇，并开通广西、江西、湖南、湖北、河南、四川、重庆、安徽、浙江、福建、贵州等跨省班车及港、澳直通班车。<br><br></p>

                </div>

            </div>
            <div class="ani foot" swiper-animate-effect="fadeInUpBig" swiper-animate-duration="1s" swiper-animate-delay="0s">
                <div class="foot-contaier">
                    <div class="foot-left">
                        <br/> <br/>
                        <p>技术支持：广州德亨信息技术有限公司</p>
                    </div>
                    <div class="foot-right"><img src="${ctx}/static/images/wx.jpg"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="swiper-pagination swiper-pagination-v"></div>
</div>


<script type="text/javascript" src="${ctx}/static/swiper/swiper.min.js"></script>
<script type="text/javascript" src="${ctx}/static/swiper/swiper.animate.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/indexswiper.js"></script>
<script>

    var layer;

      layui.use('layer', function() {
        layer = layui.layer;
    });


    $(document).ready(function () {
        var message = '${message}';

        if(message=='1'){

            layer.msg("该微信并未注册账号！",{icon: 6,time:3000})
          //  layer.msg('该微信并未注册账号！', '#banner_lun', {tips: [3, '#78BA32']});
        }
        if(message=='2'){
            layer.msg("该微信并未注册账号！",{icon: 6,time:3000})
        }
        if(message=='3'){
            layer.msg("账号被禁用或在审核中！",{icon: 6,time:3000})
        }
        if(message=='4'){
            layer.msg("账号无分配权限，请联系管理员！",{icon: 6,time:3000})
        }
    })

    if (window != top){//超时跳转
        top.location.href = location.href;
    }
    function search_() {
        var title = $(".john-this").text();
        if(title.indexOf('租赁包车') != -1){
            $("#searchForm").submit();
        }
    }
    layui.use(['form'], function(){
        var form = layui.form;
        //初始化
        init_province(form);
        //下拉
        form.on('select(quiz1_select)', function(data) {
            $("[name='startProvince']").val($(this).text());
            init_city(form, data.value, 'quiz2');
            init_area(form, data.value, 'quiz3');
        })
        form.on('select(quiz2_select)', function(data) {
            $("[name='startCity']").val($(this).text());
            init_area(form, data.value, 'quiz3');
        })
        form.on('select(quiz3_select)', function(data) {
            $("[name='startArea']").val($(this).text());
        })
        form.on('select(quiz4_select)', function(data) {
            $("[name='endProvince']").val($(this).text());
            init_city(form, data.value, 'quiz5');
            init_area(form, data.value, 'quiz6');
        })
        form.on('select(quiz5_select)', function(data) {
            $("[name='endCity']").val($(this).text());
            init_area(form, data.value, 'quiz6');
        })
        form.on('select(quiz6_select)', function(data) {
            $("[name='endArea']").val($(this).text());
        })
    });
    function init_province(form) {
        $.ajax({
            type:"post",
            url:"${ctx}/area/province",
            dataType:"json",
            success:function(data) {
                if(data.state){
                    var provinceList = eval(data.provinceList);
                    if(provinceList != null && provinceList.length > 0){
                        var html = '<option value="">请选择省</option>';
                        for (var i=0; i<provinceList.length; i++){
                            var province = provinceList[i];
                            var id = province.id;
                            var name = province.name;
                            html += '<option value="'+id+'">'+name+'</option>';
                        }
                        $("[name='quiz1']").html(html);
                        $("[name='quiz4']").html(html);
                        form.render('select');
                    }
                }else{
                    var html = '<option value="">请选择省</option>';
                    $("[name='quiz1']").html(html);
                    $("[name='quiz4']").html(html);
                    form.render('select');
                }
            }
        });
    }
    function init_city(form, id, obj) {
        $.ajax({
            type:"post",
            url:"${ctx}/area/city",
            dataType:"json",
            data:{"parentId":id},
            success:function(data) {
                if(data.state){
                    var cityList = eval(data.cityList);
                    if(cityList != null && cityList.length > 0){
                        var html = '<option value="">请选择市</option>';
                        for (var i=0; i<cityList.length; i++){
                            var city = cityList[i];
                            var id = city.cityId;
                            var name = city.cityName;
                            html += '<option value="'+id+'">'+name+'</option>';
                        }
                        $("[name='"+obj+"']").html(html);
                        form.render('select');
                    }
                }else{
                    var html = '<option value="">请选择市</option>';
                    $("[name='"+obj+"']").html(html);
                    form.render('select');
                }
            }
        });
    }
    function init_area(form, id, obj) {
        $.ajax({
            type:"post",
            url:"${ctx}/area/area",
            dataType:"json",
            data:{"parentId":id},
            success:function(data) {
                if(data.state){
                    var areaList = eval(data.areaList);
                    if(areaList != null && areaList.length > 0){
                        var html = '<option value="">请选择县/区</option>';
                        for (var i=0; i<areaList.length; i++){
                            var area = areaList[i];
                            var id = area.areaId;
                            var name = area.areaName;
                            html += '<option value="'+id+'">'+name+'</option>';
                        }
                        $("[name='"+obj+"']").html(html);
                        form.render('select');
                    }
                }else{
                    var html = '<option value="">请选择市</option>';
                    $("[name='"+obj+"']").html(html);
                    form.render('select');
                }
            }
        });
    }

    //弹出地图窗口
    function showMap(obj){

        var url =encodeURI('${ctx}/search/map?'
            +'site='+$(obj).val()
            +'&lng='+$(obj).next().val()
            +'&lat='+$(obj).next().next().val()
            +'&area='+$(obj).next().next().next().val()
            +'&city='+$(obj).next().next().next().next().val());

        obj.blur();
        layer.open({
            type: 2,
            title: "请搜索地点",
            shadeClose: false,
            shade: 0.5,
            /*  shift:-1, */
            area: ['570px','490px'],
            content:url,
            btn: ['确定', '取消'],
            yes: function(index, layero){
            	setTimeout(function(){
	                var body = layer.getChildFrame('body', index);

	                if(body.find('#site').val() != ''){
                        var province = body.find('#province').val()
                        if(province != "广东省" && province != "海南省"  && province != "江西省" && province != "湖南省" && province != "福建省" && province != "广西壮族自治区"){
                            layer.msg("该省份暂时无法选择，请重新选取地点！")
                            return false;
                        }else {
                            $(obj).val(body.find('#site').val());
                            $(obj).next().val(body.find('#lng').val());
                            $(obj).next().next().val(body.find('#lat').val());
                            $(obj).next().next().next().val(body.find('#area').val());
                            $(obj).next().next().next().next().val(body.find('#city').val());
                        }
	                }
	                layer.close(index); //再执行关闭
            	}, 500);
            },
            btn2: function(index, layero){
            },
            cancel: function(index, layero){
            }
        })
    }
    $(document).ready(function() {
        var top_menu = $("#top_menu").val();
        if(top_menu == '首页'){
            $("#"+top_menu).addClass('menu_action');
        }
        var position = $(".position").text();
        if(position.indexOf('租赁包车') > 0){
            $("#租赁包车").addClass('menu_action');
        }
        if(position.indexOf('中心') > 0){
            $("#会员中心").addClass('menu_action');
        }
        var shiroUser = '<shiro:principal/>';
        if(shiroUser != null && '' != shiroUser){
            setTimeout(function(){
                remind();
            }, 500);
        }
    });
    //url: 跳转路径  type: 0不需要登陆 1需要登陆
    function forward(url, type) {
        var shiroUser = '<shiro:principal/>';
        if(type == 0){
            window.location.href = url;
            return false;
        }
        if(shiroUser != null && '' != shiroUser){
            window.location.href = url;
        }else{
            fun_loging('${ctx}/login');
        }
    }
    //登陆
    $('.loging').click(function () {
        fun_loging('${ctx}/login');
    });
    //退出
    $('#logout').click(function () {
        top.location.href = "${ctx}/login/out";
    });
    //注册
    $('#register').click(function () {
        top.location.href = "${ctx}/register";
    });
    //显示提醒消息条数
    function remind(){
        $.ajax({
            type: "POST",
            url: "${ctx}/remind/statistics",
            data: {},
            dataType: "json",
            success: function(data){
                $('#reminds').html(data);
                var remindAmount = $("#提醒通知").text();
                if(remindAmount != null && '' != remindAmount){
                    $('#'+remindAmount).append("<span class='red'>("+data+")</span>");
                }
            }
        });
    }
</script>
</body>
</html>