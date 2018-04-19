<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title></title>
<style type="text/css">
html,body{margin:0;padding:0;width:100%;height:100%;}
.iw_poi_title {color:#CC5522;font-size:14px;font-weight:bold;overflow:hidden;padding-right:13px;white-space:nowrap}
.BMap_omCtrl{
    left:0 !important;
    right:auto !important;
    width: 152px !important;
}
.BMap_omInnFrame{
    left:0 !important;
}
.BMap_omOutFrame{
    border-right:1px solid #999;
    width: 150px !important;
}
.iw_poi_content {font:12px arial,sans-serif;overflow:visible;padding-top:4px;white-space:-moz-pre-wrap;word-wrap:break-word}
</style>
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=1w2xcsGWIGbPyhIngYF2uBK0"></script>
<script type="text/javascript" src="https://api.map.baidu.com/getscript?v=2.0&ak=1w2xcsGWIGbPyhIngYF2uBK0&services=&t=20140411133140"></script>
<script type="text/javascript" src="${ctx}/static/gis/js/MarkerTool_min.js"></script>
</head>

<body>
<input type="hidden" id="site" name="site"/>
<input type="hidden" id="lng" name="lng"/>
<input type="hidden" id="lat" name="lat"/>
<input type="hidden" id="area" name="area"/>
<input type="hidden" id="city" name="city"/>
<input type="hidden" id="province" name="province"/>
<div style="width:100%;height:100%;" id="map"></div>
<div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
<script type="text/javascript">
$(function(){
	if('${order.site}' != ''){
		$('#sekey').val('${order.site}');
		 $('#site').val('${order.site}');
		 $('#lng').val('${order.lng}');
		 $('#lat').val('${order.lat}');
		 $('#area').val('${order.area}');
		 $('#city').val('${order.city}');

		 var pp = new BMap.Point('${order.lng}', '${order.lat}'); 
		 map.centerAndZoom(pp, 18);
		 var mark = new BMap.Marker(pp);
		 markers.push(mark);
		 map.addOverlay(mark);    //添加标注
		 mark.enableDragging();    
			mark.addEventListener("dragend", function(d){
				paser(d.point);
			});
	 }
	$('#sekey').focus();
});
var xPoint = 113.098904;
var yPoint = 22.574192;

//创建地图实例 
var map = new BMap.Map("map", {enableMapClick:false}); 

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
/* //地图类型控件
map.addControl(new BMap.MapTypeControl({ anchor: BMAP_ANCHOR_TOP_RIGHT, offset: new BMap.Size(100, 10) })); 
//向地图中添加缩放控件
var ctrl_nav = new BMap.NavigationControl({ anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_LARGE });
map.addControl(ctrl_nav);
//向地图中添加缩略图控件
var ctrl_ove = new BMap.OverviewMapControl({ anchor: BMAP_ANCHOR_BOTTOM_RIGHT, isOpen: 1 });
map.addControl(ctrl_ove); */


//删除之前的标注
function removeMarkers(){
	if(markerArray != null && markerArray.length > 0){
		for(var i=0; i<markerArray.length; i++){
			map.removeOverlay(markerArray[i]);
		}
		markerArray = [];
	}
}

//自定义控件
function searchControl(){
    this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
    this.defaultOffset = new BMap.Size(5,5);
}
//继承Control的API
searchControl.prototype = new BMap.Control();
//初始化控件
searchControl.prototype.initialize=function(map){
    var div = document.createElement('div');
    var e1 = document.createElement('input');
    e1.setAttribute("type", "text");
    e1.setAttribute("id", "sekey");
    e1.setAttribute("style", "border-width:1px;width:250px;height:25px;line-height:25px;float: left;margin-right:5px;");
    e1.setAttribute("class", "layui-input");
    e1.setAttribute("onkeypress", "EnterPress(event);");
    e1.setAttribute("onkeydown", "EnterPress();");
    e1.setAttribute("placeholder", "请输入地点");
    div.appendChild(e1);
    var e2 = document.createElement('input');
    e2.setAttribute("type", "button");
    e2.setAttribute("id", "seButton");
    e2.setAttribute("style", "height:25px;line-height:25px;padding:0 10px;float: left;");
    e2.setAttribute("value", "搜索");
    e2.setAttribute("class", "layui-btn layui-btn-primary");
    e2.setAttribute("onclick", "setPlace($('#sekey').val());");
    div.appendChild(e2);
    var e3 = document.createElement('input');
    e3.setAttribute("type", "button");
    e3.setAttribute("id", "seButton");
    e3.setAttribute("style", "height:25px;line-height:25px;padding:0 10px;float: left;");
    e3.setAttribute("value", "选点");
    e3.setAttribute("class", "layui-btn layui-btn-primary");
    e3.setAttribute("onclick", "openMkrTool();");
    //div.appendChild(e3);
    //添加DOM元素到地图中
    map.getContainer().appendChild(div);
    //e1.focus();
    return div;
};

var gc = new BMap.Geocoder();
var mkrTool = new BMapLib.MarkerTool(map, {followText: "选点"});
mkrTool.addEventListener("markend", function(e) {
	removeMarkers();
	markers.push(e.marker);
	var point = e.marker.getPosition();
	paser(point);
	e.marker.enableDragging();    
	e.marker.addEventListener("dragend", function(d){
		paser(d.point);
	}); 
});

function paser(pt){
	map.panTo(pt);
	gc.getLocation(pt, function(rs){
		var surroundingPois = rs.surroundingPois;
        if(surroundingPois != null && surroundingPois.length > 0){
			$("#site").val(surroundingPois[0].title);
			$("#sekey").val(surroundingPois[0].title);
			$("#lng").val(surroundingPois[0].point.lng);
			$("#lat").val(surroundingPois[0].point.lat);
        }else{
	      	var addComp = rs.addressComponents;
		    var address = addComp.city + addComp.district + addComp.street + addComp.streetNumber;
			$("#site").val(address);
			$("#sekey").val(address);
			$("#lng").val(pt.lng);
			$("#lat").val(pt.lat);


        }
        $("#area").val(rs.addressComponents.district);
        $("#city").val(rs.addressComponents.city);
        $("#province").val(rs.addressComponents.province);
	}); 
}

function openMkrTool(){
	mkrTool.open();
}

function EnterPress(e){ //传入 event

	var e = e || window.event;
	if(e.keyCode == 13){ 
		setPlace($("#sekey").val());
	}

}

function setkey(){
	if($("#sekey").val() == "输入案件地关键字"){
		$("#sekey").val("");
	}
}

//创建控件实例
var searchCtrl = new searchControl();
//添加到地图当中
map.addControl(searchCtrl);

//百度地图API功能
function G(id) {
	return document.getElementById(id);
}

var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
	{"input" : "sekey"
	,"location" : map
});

ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
	var str = "";
	var _value = e.fromitem.value;
	var value = "";
	if (e.fromitem.index > -1) {
		value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
	}    
	str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
	
	value = "";
	if (e.toitem.index > -1) {
		_value = e.toitem.value;
		value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
	}    
	str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
	G("searchResultPanel").innerHTML = str;
});

ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
var _value = e.item.value;
	myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
	G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
	
	setPlace(myValue);
});

function setPlaceMk(startway, lng, lat){
	removeMarkers();
	if(lng == 0 || lat ==0){
		$("#startway").val('');
		return;
	}
	var pp = new BMap.Point(lng, lat); 
	$("#site").val(startway);
	$("#lng").val(lng);
	$("#lat").val(lat);
	map.centerAndZoom(pp, 18);
	var mark = new BMap.Marker(pp);
	markers.push(mark);
	map.addOverlay(mark);    //添加标注
	mark.enableDragging();    
	mark.addEventListener("dragend", function(d){
		paser(d.point);
	}); 
}

function setPlace(myValue){
	if(myValue =='')return;
	removeMarkers();
	function myFun(){
		var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
		gc.getLocation(pp, function(rs){
	         	var addComp = rs.addressComponents;
			    var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
			    $("#area").val(addComp.district);
			    $("#city").val(addComp.city);
                $("#province").val(addComp.province);
		});
		$("#site").val(local.getResults().getPoi(0).title);
		$("#lng").val(pp.lng);
		$("#lat").val(pp.lat);
		map.centerAndZoom(pp, 18);
		var mark = new BMap.Marker(pp);
		markers.push(mark);
		map.addOverlay(mark);    //添加标注
		mark.enableDragging();    
		mark.addEventListener("dragend", function(d){
			paser(d.point);
		});
	}
	var local = new BMap.LocalSearch(map, { //智能搜索
	  onSearchComplete: myFun
	});
	local.search(myValue);
}

map.addEventListener("dragend",function(){
});

map.addEventListener("zoomend",function(){
});

map.addEventListener("click",function(e){
	removeMarkers();
	var mark = new BMap.Marker(e.point);
	paser(e.point);
	markers.push(mark);
	map.addOverlay(mark);    //添加标注
	mark.enableDragging();    
	mark.addEventListener("dragend", function(d){
		paser(d.point);
	});
});

function removeMarkers(){
	if(markers != null && markers.length > 0){
		for(var i=0; i<markers.length; i++){
			map.removeOverlay(markers[i]);
		}
		markers = [];
	}
}
</script>
</body>
</html>