<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>添加返程单</title>
<style>
.searh label{
  width:100px;
}
.searh .search_text{
  width:300px;
  border-radius:4px;
}
.searh1 {
  background: #fff;
  overflow: visible;
}
.searh_box{
  height:504px !important;
}
.search_text{
	padding:0 10px;
}
.input_date{
	width:302px !important;
	padding-left:30px;
}
.searh .search_text {
  width: 280px;
}
</style>
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=1w2xcsGWIGbPyhIngYF2uBK0"></script>
<script type="text/javascript" src="https://api.map.baidu.com/getscript?v=2.0&ak=1w2xcsGWIGbPyhIngYF2uBK0&services=&t=20140411133140"></script>
</head>
<body>
<section>
	<div class="container">
		<jsp:include page="/WEB-INF/layouts/left_menu.jsp" />
		<main>
		<div class="position_all">
			<div class="position">您所在的位置：首页 -> 管理中心 -> 添加 单程订单</div>
		</div>
		<form id="saveOrUpdate" class="layui-form" action="${ctx}/transport/backOrder/saveOrUpdate" method="post">
		<input type="hidden" id="id" name="id" value="${backOrder.id}"/>
		<input type="hidden" name="type" value="${backOrder.type}"/>
		<input type="hidden" id="orderCode" name="orderCode" value="${backOrder.orderCode}"/>
		<input type="hidden" id="viaPoint" name="viaPoint" value="${backOrder.viaPoint}"/>
		<input type="hidden" id="distance" name="distance" value="${backOrder.distance}">
		<input type="hidden" id="duration" name="duration" value="${backOrder.duration}">
		<input type="hidden" id="charterOrderCode" name="charterOrderCode" value="${backOrder.charterOrderCode}"/>
		<div class="searh searh1 padding10">
		<c:if test="${backOrder.type == 1}">
			<div style="float: left;width: 50%;">
				<div class="div_input" style="padding: 10px 0 0 0;">
					<label>上车点</label>
					<input class="search_text" value="${order.startPoint}" type="text" disabled/>
				</div>
				<div class="div_input">
					<label>下车点</label>
					<input class="search_text" value="${order.endPoint}" type="text" disabled>
				</div>
				<div class="div_input">
					<label>上车时间</label>
					<input type="text" class="search_text" id="boardingTime"
					value="<fmt:formatDate type='date' pattern='yyyy-MM-dd HH:mm:ss' value='${order.boardingTime}'/>" disabled/>
				</div>
				<div class="div_input">
					<label>返程时间</label>
					<input type="text" class="search_text"
					value="<fmt:formatDate type='date' pattern='yyyy-MM-dd HH:mm:ss' value='${order.trackTime}'/>" disabled/>
				</div>
				<div class="div_input">
					<label>车辆</label>
					<input type="text" class="search_text" value="${order.carNum}" disabled>
				</div>
				<div class="div_input">
					<label>司机</label>
					<input type="text" class="search_text" value="${order.driverName}: ${order.driverPhone}" disabled>
				</div>
				<div class="div_input">
					<label>运费</label>
					<input type="text" class="search_text" value="${order.amount}元" disabled>
				</div>
				<div class="div_input">
					<label>预估路程</label>
					<input type="text" class="search_text" value='<fmt:formatNumber type="number" value="${order.distance/1000.0}" pattern="0.00" maxFractionDigits="2"/>(公里)' disabled>
				</div>
				<div class="div_input">
					<label>预估时长</label>
					<input type="text" class="search_text" value='<fmt:formatNumber type="number" value="${order.duration/60.0}" pattern="0.00" maxFractionDigits="2"/>(分钟)' disabled>
				</div>
			</div>
			</c:if>
			<div style="float: left;width:50%">
				<div class="div_input" style="padding: 10px 0 0 0;">
					<label>上车点</label>
					<input class="search_text" lay-verify="required" id="startPoint" name="startPoint" value="${backOrder.startPoint}" onclick="showMap(this);" type="text">
					<input type="hidden" id="startLng" name="startLng" value="${backOrder.startLng}"/>
					<input type="hidden" id="startLat" name="startLat" value="${backOrder.startLat}"/>
					<input type="hidden" id="startArea" name="startArea" value="${backOrder.startArea}">
					<input type="hidden" id="startCity" name="startCity" value="${backOrder.startCity}">
					<i class="icon-plus-sign icon-add"></i>
				</div>
				<div class="div_input" id="endPointDiv">
					<label>下车点</label>
					<input class="search_text" lay-verify="required" id="endPoint" name="endPoint" value="${backOrder.endPoint}" onclick="showMap(this);" type="text">
					<input type="hidden" id="endLng" name="endLng" value="${backOrder.endLng}"/>
					<input type="hidden" id="endLat" name="endLat" value="${backOrder.endLat}"/>
					<input type="hidden" id="endArea" name="endArea" value="${backOrder.endArea}">
					<input type="hidden" id="endCity" name="endCity" value="${backOrder.endCity}">
				</div>
				<div class="div_input">
					<label>最早出发时间</label>
					<input class="layui-input search_text input_date" lay-verify="required" id="earliestDepartureTime" name="earliestDepartureTime" type="text"
					<c:if test="${backOrder.type == 2}">onfocus="WdatePicker({onpicked:searchCarsByBack,dateFmt:'yyyy-MM-dd HH:mm:ss'})"</c:if>
					<c:if test="${backOrder.type == 1}">readonly</c:if>
					value="<fmt:formatDate type='date' pattern='yyyy-MM-dd HH:mm:ss' value='${backOrder.earliestDepartureTime}'/>"/>
				</div>
				<div class="div_input">
					<label>最晚出发时间</label>
					<input class="layui-input search_text input_date" lay-verify="required" id="latestDepartureTime" name="latestDepartureTime" type="text"
					onfocus="WdatePicker({<c:if test='${backOrder.type == 2}'>onpicked:searchCarsByBack,</c:if>dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'earliestDepartureTime\')||\'%y-%M-%d %H:{%m-5}:%s\'}'})"
					value="<fmt:formatDate type='date' pattern='yyyy-MM-dd HH:mm:ss' value='${backOrder.latestDepartureTime}'/>"/>
				</div>
				<c:if test="${backOrder.type == 1}">
				<div class="div_input">
					<label>车辆</label>
					<input class="search_text" value="${backOrder.carNum}" type="text" readonly/>
				</div>
				</c:if>
				<c:if test="${backOrder.type == 2}">
				<div class="div_input">
					<label>选择车辆</label>
					<div class="search_text" style="border:none;padding:0;width:302px;">
					<select id="carInfo" name="carInfo" lay-verify="required" lay-search="" lay-filter="carFilter">
						<option value="">选择车辆</option>
						<%-- <c:forEach items="${cars}" var="c" varStatus="status" >
							<option value="${c.carCode},${c.carNum},${c.driverCode},${c.seatNumber},${c.carType}" <c:if test="${c.carCode == backOrder.carCode}">selected = "selected"</c:if>>${c.carNum}(${c.carType})</option>
						</c:forEach> --%>
					</select>
					</div>
				</div>
				</c:if>
				<input type="hidden" id="carType" name="carType" value="${backOrder.carType}">
				<input type="hidden" id="carCode" name="carCode" value="${backOrder.carCode}"/>
				<input type="hidden" id="carNum" name="carNum" value="${backOrder.carNum}"/>
				<input type="hidden" id="driverCode" name="driverCode" value="${backOrder.driverCode}"/>
				<div class="div_input">
					<label>客座数</label>
					<input class="search_text" lay-verify="required" id="seatNumber" name="seatNumber" value="${backOrder.seatNumber}" type="text" readonly/>
				</div>
				<div class="div_input">
					<label>优惠价格(元)</label>
					<input class="search_text" lay-verify="required" id="discountPrices" name="discountPrices" value="${backOrder.discountPrices}" type="text">
				</div>
				<c:if test="${backOrder.type==1}">
					<div class="div_input">
						<label>有效接单时间</label>
						<input class="search_text" lay-verify="required|checkNumber" id="effeTime" name="effeTime" value="${backOrder.effeTime}" type="text" onkeyup="setEffeSeTime(this);">
						<input type="text" style="margin-left: 115px;" class="search_text" id="effeSeTime" value="<fmt:formatDate type='date' pattern='yyyy-MM-dd HH:mm:ss' value='${backOrder.effeSeTime}'/>" disabled>
					</div>
				</c:if>
				<div class="div_input">
					<label>允许偏离公里数</label>
					<input class="search_text" lay-verify="required|checkFloat" id="range" name="range" value="${backOrder.range}" type="text">
				</div>
			</div>
			<div class="cl"></div>
		</div>
		<div style="width:100%;height:400px;display:none;" id="map"></div>
		<div class="searh searh2">
			<div class="div_input">
				<button type="button" class="layui-btn" style="float: right;margin-left: 10px;" onclick="back();">返回</button>
				<button class="layui-btn layui-btn-danger" lay-submit lay-filter="submitBtn" style="float: right;">发布</button>
				<div class="cl"></div>
			</div>
		</div>
		</form>
		</main>
	</div>
</section>
<div id="via" style="display:none;">
<div class="div_input remove_input">
	<label>途经点</label>
	<div>
		<div class="add_input">
			<input class="search_text" id="viaPoints" name="viaPoints" value="" onclick="showMap(this);" type="text"> 
			<input type="hidden" id="viaLngs" name="viaLngs" value=""/>
			<input type="hidden" id="viaLats" name="viaLats" value=""/>
			<i class="icon-minus-sign icon-move"></i>
		</div>
	</div>
</div>
</div>
<script type="text/javascript">
//搜索车辆
function searchCarsByBack(){
	var latestDepartureTime = $("#latestDepartureTime").val();
	var earliestDepartureTime = $("#earliestDepartureTime").val();
	var duration = $("#duration").val();
	if(latestDepartureTime != '' && earliestDepartureTime != '' && duration != ''){
		$.ajax({
            type:"post",
            url:"${ctx}/transport/backOrder/searchCarsByBack",
            dataType:"json",
            data:{"boardingTime":earliestDepartureTime,"debusTime": latestDepartureTime, "duration":duration,"carCode": '${backOrder.carCode}'},
            success:function(data) {
              var str='<option value="">选择车辆</option>';
              var cars = data.cars;
              if(null != cars && cars.length>0){
	              for(i=0;i<cars.length;i++){
	            	  var c = cars[i];
	            	  str += '<option value="'+c.carCode+','+c.carNum+','+c.driverCode+','+c.seatNumber+','+c.carType+'"';
	            	  if('${backOrder.carCode}' == c.carCode){
	            		  str += 'selected = "selected"';
	            	  }
	            	  str += '>'+c.carNum+'('+c.carType+')</option>';
	              }
              }
             $("#carInfo").html(str);
           	//加载表单
             layui.use(['form'], function() {
                var form = layui.form;
             	form.render('select'); //刷新select选择框渲染
             });
            }
        });
	}
}

//显示具体接单时间
function setEffeSeTime(obj){
	var effeTime = $(obj).val();
	if(effeTime != ''){
		if(effeTime.substring(0,1) == '-'){
			effeTime = effeTime.substring(1)
    	}
		if(/^[0-9]*$/.exec(effeTime)){
			var dataJson = {'effeTime':$(obj).val(),'boardingTime':$('#boardingTime').val()};
            var url = '${ctx}/transport/backOrder/effeSeTime';
		    $.ajax({
		        async:false,
		        type:"post",
		        url:url,
		        dataType:"json",
		        data:dataJson,
		        success:function(data) {
		        	$('#effeSeTime').val(data);
		        }
		    });
	   }  
	}
}
//文档加载完毕执行
$(function(){
	/**增减途经点**/
	$("body").undelegate(); // 解除之前的绑定
	$("body").delegate("i.icon-move","click",function(){  // 重新绑定
	    $(this).parents('.remove_input').remove();
	 });
	$('.searh1 i.icon-add').click(function(){
		//$(this).parent().after($('#via').html());
		$('#endPointDiv').before($('#via').html());
	})
	
	/* var startPoint = null, endPoint = null, waypoints = []; */
	
	/**初始化途经点**/
	var viaPoint = $('#viaPoint').val();
	var id = $('#id').val();
	if(viaPoint != null && viaPoint != ''){
		var viaPoints = viaPoint.split(';');
		if(id != null && id != ''){
			for(var i = viaPoints.length-1; i > -1; i--){
				if(viaPoints[i] != null && viaPoints[i] != ''){
			  		 $('.searh1 i.icon-add').parent().after($('#via').html());
			  		 $('#viaPoints').val(viaPoints[i].split('@')[0]);
			  		 $('#viaLngs').val(viaPoints[i].split('@')[1].split(',')[0]);
			  		 $('#viaLats').val(viaPoints[i].split('@')[1].split(',')[1]);
			  		 //waypoints[i] = new BMap.Point(viaPoints[i].split('@')[1].split(',')[0],viaPoints[i].split('@')[1].split(',')[1]);
			  	  }
			}
		}else{
			for(var i = 0; i< viaPoints.length; i++){
				if(viaPoints[i] != null && viaPoints[i] != ''){
			  		 $('.searh1 i.icon-add').parent().after($('#via').html());
			  		 $('#viaPoints').val(viaPoints[i].split('@')[0]);
			  		 $('#viaLngs').val(viaPoints[i].split('@')[1].split(',')[0]);
			  		 $('#viaLats').val(viaPoints[i].split('@')[1].split(',')[1]);
			  		 //waypoints[i] = new BMap.Point(viaPoints[i].split('@')[1].split(',')[0],viaPoints[i].split('@')[1].split(',')[1]);
			  	  }
			}
		}
	}
	/* if($('#startPoint').val() != null && $('#startPoint').val() != '' && $('#endPoint').val() != null && $('#endPoint').val() != ''){
		startPoint = new BMap.Point($('#startLng').val(),$('#startLat').val());
		endPoint = new BMap.Point($('#endLng').val(),$('#endLat').val());
		searchLine(startPoint, endPoint, waypoints);
	} */
	initLenAndHour();
});

//弹出地图窗口
function showMap(obj){
    obj.blur();
	layer.open({
	    type: 2,
        title: "请搜索地点",
	    shadeClose: false,
	    shade: 0.5,
	   /*  shift:-1, */
	    area: ['570px','490px'],
	    content: '${ctx}/transport/car/map?'
	    		+'site='+$(obj).val()
	    		+'&lng='+$(obj).next().val()
	    		+'&lat='+$(obj).next().next().val()
	    		+'&area='+$(obj).next().next().val()
	    		+'&city='+$(obj).next().next().next().next().val(),
	    btn: ['确定', '取消'],
	    yes: function(index, layero){
	    	setTimeout(function(){
		    	 var body = layer.getChildFrame('body', index);
		    	 if(body.find('#site').val() != ''){
		    		 $(obj).val(body.find('#site').val());
		    		 $(obj).next().val(body.find('#lng').val());
		    		 $(obj).next().next().val(body.find('#lat').val());
		    		 $(obj).next().next().next().val(body.find('#area').val());
		    		 $(obj).next().next().next().next().val(body.find('#city').val());
		    		 initLenAndHour();
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

//提交表单
function laySubmit(){
	$('#saveOrUpdate').submit();
}

//加载表单
layui.use(['form','element','laydate'], function() {
	
	/* var laydate = layui.laydate;
	//执行laydate实例
	laydate.render({
		elem: '#earliestDepartureTime' //指定元素
		,type: 'datetime'
		,theme: '#0071bf'
	});
	//日期时间选择器
	laydate.render({
		elem: '#latestDepartureTime'
		,type: 'datetime'
		,theme: '#0071bf'
	});
  */
    var form = layui.form;
    
    form.on('select(carFilter)', function(data){
    	  /* console.log(data.elem); //得到select原始DOM对象
    	  console.log(data.value); //得到被选中的值
    	  console.log(data.othis); //得到美化后的DOM对象 */
    	  var carInfo = data.value.split(',');
    	  $('#carCode').val(carInfo[0]);
    	  $('#carNum').val(carInfo[1]);
    	  $('#driverCode').val(carInfo[2]);
    	  $('#seatNumber').val(carInfo[3]);
    	  $('#carType').val(carInfo[4]);
    	}); 
    
  	//监听提交
    form.on('submit(submitBtn)', function(data){
      //layer.msg(JSON.stringify(data.field));
      	var viaPoint = '';
	    $("[name='viaPoints']").each(function(index,element){
	  	   if($(this).val() != null && $(this).val() != ''){
	  		   viaPoint = viaPoint + $(this).val()+'@'+$(this).next().val()+','+$(this).next().next().val()+';';
	  	   }
	    });
	    $('#viaPoint').val(viaPoint);
      	return true;
    });
  	
    //自定义验证规则
    form.verify({
        //只能输入正整数
        integer:function (value,item) {
            if(!/^[0-9]*$/.exec(value)){
                return "只能输入数字";
            }
        },
      	//只能输入整数
        checkNumber:function (value,item) {
        	if(value.substring(0,1) == '-'){
        		value = value.substring(1)
        	}
       	   if(!/^[0-9]*$/.exec(value)){
                 return "只能输入整数";
           }  
        },
      	//只能输入数值
        checkFloat:function (value,item) {
       	   if(!/^[0-9]+\.?[0-9]*$/.exec(value)){
       		  return "只能输入数字";
           }  
        }

    });
});
function isInteger(obj) {
	 return parseInt(obj, 10) === obj;
	}
//计算起始点的路程和时间
function initLenAndHour(){
	if(($('#startPoint').val() != null && $('#startPoint').val() != '') && ($('#endPoint').val() != null && $('#endPoint').val() != '')){
		var startPoint = new BMap.Point($('#startLng').val(),$('#startLat').val());
		var endPoint = new BMap.Point($('#endLng').val(),$('#endLat').val());
		var viaPoint = '';
		var waypoints = [];
		$("[name='viaPoints']").each(function(index,element){
	  	   if($(this).val() != null && $(this).val() != ''){
	  		   viaPoint = viaPoint + $(this).val()+'@'+$(this).next().val()+','+$(this).next().next().val()+';';
	  		  waypoints[index] = new BMap.Point($(this).next().val(),$(this).next().next().val());
	  	   }
	    });
	    $('#viaPoint').val(viaPoint);
	    searchLine(startPoint, endPoint, waypoints);
	}
}
//返回订单列表
function back(){
	//history.back();
	window.location.replace('${ctx}/transport/backOrder/list/'+'${backOrder.type}');
}

///地图加载》》》》》》start
var xPoint = 113.098904;
var yPoint = 22.574192;

//创建地图实例 
var map = new BMap.Map("map"); 

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
function searchLine(startPoint, endPoint, waypoints){
	var route = 0;
	var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, enableDragging: false}, onSearchComplete: searchComplete, policy: route});
	map.clearOverlays();
	driving.search(startPoint, endPoint,{waypoints:waypoints});//waypoints表示途经点
}

var searchComplete = function (results){
	var plan = results.getPlan(0);
	var lineLength = plan.getDistance(true);
	var lineNeedHours = plan.getDuration(true);
	$('#range').val(Math.round((plan.getDistance(false)*0.0003)*100)/100);
	$("#distance").val(plan.getDistance(false));
	$("#duration").val(Math.ceil(plan.getDuration(false)*1.15));
	if('${backOrder.type}' == 2){
		searchCarsByBack();
	}
	//$("#lineLength").html("总路程："+lineLength+", 米："+plan.getDistance(false));
	//$("#lineNeedHours").html("总时长："+lineNeedHours+", 秒："+plan.getDuration(false));
};
</script>
</body>
</html>