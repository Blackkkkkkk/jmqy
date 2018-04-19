<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <style type="text/css">
        body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
    </style>
    <script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=1w2xcsGWIGbPyhIngYF2uBK0"></script>
    <script type="text/javascript" src="https://api.map.baidu.com/getscript?v=2.0&ak=1w2xcsGWIGbPyhIngYF2uBK0&services=&t=20140411133140"></script>
    <script src="${ctx}/images/jquery-3.2.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/images/css/LocationChoice.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/images/css/css.css">
    <title></title>

    <style>
        .div1{display:inline;}
        .anchorBL{display: none !important;}
        .tangram-suggestion-main{
            width: 100% !important;
            height:216px;
            display:block !important;
            top:auto !important;
            bottom:-39px;
        }
        #tangram-suggestion--TANGRAM__1g{
            position:absolute !important;
            bottom:39px;
            width:100%;
            left:0px;
            top:auto !important;
        }
        .displayNone{
            display:none !important;
            position:fixed !important;
        }
        .tangram-suggestion table {
            width: 100%;
            font-size: 12px;
            cursor: default;
            overflow: auto;
            height: 200px;
            display: block;
        }
    </style>


</head>
<body>

<div  id='msg4'>
  <div >
        <div class="div1" >
            <input id="inputplace" type="text" placeholder="请输入地点"  oninput="OnInput (event)"  style="height: 40px;">
            <button id="btn1" onclick="searchMap();">查找</button>
            <button id="btn2" onclick="Sure();">确定</button>
            <button id="btn3" onclick="Cancel();">取消</button>
        </div>
             <div id="allmap"  style="display: block;"></div>


</div>
</body>
</html>

<script type="text/javascript">

    $(document).ready(function () {
    })

    function GetQueryString(name)
    {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null)return  unescape(r[2]); return null;
    }


    var map = new BMap.Map("allmap");
    window.map;
    var point;
    var marker;


    var x;
    var y;
    var geolocation = new BMap.Geolocation();
    geolocation.getCurrentPosition(function(r){
        if(this.getStatus() == BMAP_STATUS_SUCCESS){
            x=r.point.lng;
            y=r.point.lat

            creMap(x,y);

        }
        else {
            alert('failed'+this.getStatus());
        }
    },{enableHighAccuracy: true})



   // BMap_scaleBar BMap_scaleRBar
    function creMap(x,y) {
        point = new BMap.Point(x,y);
        map.centerAndZoom(point, 13);
        marker = new BMap.Marker(point);  // 创建标注
        map.addOverlay(marker);
        marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
        // 将标注添加到地图中
        map.centerAndZoom(point, 13);
        //添加默认比例尺控件
     //   map.addControl(new BMap.ScaleControl());
        //滚轮缩放事件
        map.enableScrollWheelZoom();
        //开启连续缩放效果
        map.enableContinuousZoom();
    }
//113.098904, 22.574192

    // 百度地图API功能



 //   map.disableDragging()


//全局变量


       var startProvince;
       var startCity;
       var startDistrict;
       var startStreet
       var startStreetNumber;
       var startLng;
       var starLat;
       var starPoint;







//鼠标点击事件
        function showInfo(e){
            map.clearOverlays();
            var point = new BMap.Point(e.point.lng,e.point.lat);
            map.centerAndZoom(point, 13);
            var marker = new BMap.Marker(point);  // 创建标注
            map.addOverlay(marker);               // 将标注添加到地图中
            marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
            map.disableDragging();
            map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
            map.enableScrollWheelZoom();//启用地图滚轮放大缩小

            startLng=e.point.lng;
            starLat=e.point.lat;

            // 根据经纬度，地名逆解析
            var geoc = new BMap.Geocoder();
            var pt = e.point;

            geoc.getLocation(pt, function(rs){
                var addComp = rs.addressComponents;
                // alert(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber)
                // alert( addComp.city + ", " + addComp.district );
                var value=addComp.province+addComp.city+addComp.district+addComp.street+addComp.streetNumber;
                var num=value.length;

                $("#inputplace").val(addComp.province+addComp.city+addComp.district+addComp.street);
                $("#inputplace").width(15*num);
                startProvince=addComp.province;
                startCity=addComp.city;
                startDistrict=addComp.district;
                startStreet=addComp.street;
                startStreetNumber=addComp.streetNumber;
                starPoint=addComp.province+addComp.city+addComp.district+addComp.street+addComp.streetNumber;

            });



        } map.addEventListener("click", showInfo);



    //取消值
    function Cancel(){
            $('#inputplace').val("");
    }



    $(function(){
        initMap();
    })





    //地图搜索
    function searchMap() {


       var myValue =$('#inputplace').val();

        if (myValue == '') return;
        function myFun() {
            var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
            var gc = new BMap.Geocoder();
            gc.getLocation(pp, function (rs) {

                var addComp = rs.addressComponents;
                var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber

                startProvince = addComp.province;
                startCity = addComp.city;
                startDistrict = addComp.district;
                startStreet = addComp.street;
                startStreetNumber = addComp.streetNumber;


            });

            starPoint = local.getResults().getPoi(0).title;


            startLng = pp.lng;
            starLat = pp.lat;

            map.clearOverlays();
            map.centerAndZoom(pp, 18);
            var mark = new BMap.Marker(pp);
            map.addOverlay(mark);    //添加标注
            mark.enableDragging();
            mark.addEventListener("dragend", function (d) {
                paser(d.point);
            });
        }

        var local = new BMap.LocalSearch(map, { //智能搜索
            onSearchComplete: myFun
        });
        local.search(myValue);

     /*   var area;

        if(value== "undefined" || typeof(value) == "undefined") {
             area = document.getElementById("inputplace").value; //得到地区
        }else {
            area=value
        }
        var ls = new BMap.LocalSearch(map);
        ls.setSearchCompleteCallback(function(rs) {
            if (ls.getStatus() == BMAP_STATUS_SUCCESS) {
                var poi = rs.getPoi(0);
                if (poi) {


                    map.clearOverlays();
                    var point = new BMap.Point(poi.point.lng,poi.point.lat);
                    map.centerAndZoom(point, 13);
                    var marker = new BMap.Marker(point);  // 创建标注
                    map.addOverlay(marker);               // 将标注添加到地图中
                    marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
                    map.disableDragging();
                    map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
                    map.enableScrollWheelZoom();//启用地图滚轮放大缩小



                }
            }
        });
        ls.search(area);*/
    }

    //创建地图函数：
    function createMap(x, y) {

        map = new BMap.Map("allmap");//在百度地图容器中创建一个地图
        var point = new BMap.Point(x, y);
        map.centerAndZoom(point, 13);
        var marker = new BMap.Marker(point);  // 创建标注
        map.addOverlay(marker);               // 将标注添加到地图中
        //  marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
       map.addEventListener("click", showInfo);
    }

    function initMap() {

        setMapEvent();//设置地图事件
        addMapControl();//向地图添加控件

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
        var ctrl_nav = new BMap.NavigationControl( {
            anchor : BMAP_ANCHOR_TOP_LEFT,
            type : BMAP_NAVIGATION_CONTROL_LARGE
        });
        map.addControl(ctrl_nav);
        //向地图中添加缩略图控件
        var ctrl_ove = new BMap.OverviewMapControl( {
            anchor : BMAP_ANCHOR_BOTTOM_RIGHT,
            isOpen : 1
        });
        map.addControl(ctrl_ove);
        //向地图中添加比例尺控件
      /*  var ctrl_sca = new BMap.ScaleControl( {
            anchor : BMAP_ANCHOR_BOTTOM_LEFT
        });
        map.addControl(ctrl_sca);*/
    }

    initMap();//创建和初始化地图

    //只能搜索框

    var myGeo = new BMap.Geocoder();

  function  Sure() {

      var myValue =$('#inputplace').val();

      if (myValue == '') return;
      function myFun() {


          var  pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果

          var gc = new BMap.Geocoder();
          gc.getLocation(pp, function (rs) {



              var addComp = rs.addressComponents;
              var  address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber
              startProvince = addComp.province;
              console.log(startProvince)
              startCity = addComp.city;
              startDistrict = addComp.district;
              startStreet = addComp.street;
              startStreetNumber = addComp.streetNumber;


              starPoint = local.getResults().getPoi(0).title;
              startLng = pp.lng;
              starLat = pp.lat;


              if(startProvince != "广东省" && startProvince != "海南省"  && startProvince != "江西省" && startProvince != "湖南省" && startProvince != "福建省" && startProvince != "广西壮族自治区") {
                  alert("该省份暂时无法选择，请重新选取地点！")
                  return false;
              }

              var uri = window.location.search;
              var num = uri.indexOf("&")
              uri = uri.substring(uri.indexOf("=")+1,num);

              var str = GetQueryString("str");

              if(((uri.indexOf("viaPoints"))==0)) {
                  uri=uri.substring(9)
                  var address=starPoint;
                  var viaPoint=uri+","+address+","+startLng+","+starLat+","+startDistrict+";";

                  if('${locationPostBase.viaPoint}'!=null && '${locationPostBase.viaPoint}'!=""){
                      //   viaPoint='${locationPostBase.viaPoint}'+viaPoint;
                      viaPoint='${sessionScope.str}'+viaPoint;
                  }
                  window.location.href='${ctx}/search/LocationSetValue?viaPoint='+viaPoint
                  // window.location.href='${ctx}/search/LocationSetValue?viaPoint='+viaPoint+"&str='${sessionScope.str}'";
              }
              else
              {
                  window.location.href='${ctx}/search/LocationSetValue?${requestScope.state}Province='+startProvince+'&${requestScope.state}City='+startCity+'&${requestScope.state}District='+startDistrict+'&${requestScope.state}Street='+startStreet+'&${requestScope.state}StreetNumber='+startStreetNumber+'&${requestScope.state}Lng='+startLng+'&${requestScope.state}Lat='+starLat+'&state=${requestScope.state}'+'&${requestScope.state}Point='+starPoint;
              }
          });

      }

      var local = new BMap.LocalSearch(map, { //智能搜索
          onSearchComplete: myFun
      });
      local.search(myValue);
  }
  //获取URL参数
    function GetQueryString(name)
    {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null)return  unescape(r[2]); return null;
    }

    //监控输入框输入变化
    // Firefox, Google Chrome, Opera, Safari, Internet Explorer from version 9
    function OnInput (event) {


    }

    var ac = new BMap.Autocomplete(    //建立一个自动完成的对象

        {
            "input": "inputplace",
            "location": map,
            onSearchComplete: function (results) {

                $('.tangram-suggestion-main').removeClass('displayNone')
            }


        }
    );

    function G(id) {
        return document.getElementById(id);
    }

    ac.addEventListener("onconfirm", function (e) {    //鼠标点击下拉列表后的事件
        var _value = e.item.value;
        var myValue = _value.province + _value.city + _value.district + _value.street + _value.business;

        //  var addre = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;

        //  G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
        setPlace(myValue);


       })



        function setPlace(myValue) {
            if (myValue == '') return;
            function myFun() {
                var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
                var gc = new BMap.Geocoder();
                gc.getLocation(pp, function (rs) {

                    var addComp = rs.addressComponents;
                    var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber
                    startProvince = addComp.province;
                    startCity = addComp.city;
                    startDistrict = addComp.district;
                    startStreet = addComp.street;
                    startStreetNumber = addComp.streetNumber;


                });

                starPoint = local.getResults().getPoi(0).title;

                startLng = pp.lng;
                starLat = pp.lat;

                map.clearOverlays();
                map.centerAndZoom(pp, 18);
                var mark = new BMap.Marker(pp);
                map.addOverlay(mark);    //添加标注
                mark.enableDragging();
                mark.addEventListener("dragend", function (d) {
                    paser(d.point);
                });
            }

            var local = new BMap.LocalSearch(map, { //智能搜索
                onSearchComplete: myFun
            });
            local.search(myValue);
        }

  /*  $('.tangram-suggestion-main div>span').unbind()
    $('.tangram-suggestion-main div>span').on('click',function(){
        alert()
    })*/

    $("body").delegate(".tangram-suggestion-main div>span","click",function(){  // 重新绑定
        $('.tangram-suggestion-main').addClass('displayNone')
    });

</script>

