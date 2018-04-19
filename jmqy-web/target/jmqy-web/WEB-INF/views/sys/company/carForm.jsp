<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>添加车辆</title>
</head>
<body class="window">
<div class="layui-form" style="margin-top: 10px;">
	<form id="saveOrUpdate" class="layui-form" action="${ctx}/transport/car/saveOrUpdate" method="post" enctype="multipart/form-data">
		<input type="hidden" id="id" name="id" value="${car.id}"/>
		<c:if test="${not empty car.id}">
		<div class="layui-form-item">
			<label class="layui-form-label">车辆编码:</label>
			 <div class="layui-input-block" style="padding-right: 40px;">
				<input name="carCode" autocomplete="off" value="${car.carCode}" class="layui-input" type="text"
				readonly="readonly" style="border:none; ">
			</div>
		</div>
		</c:if>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">车牌号:</label>
				<div class="layui-input-inline">
					<input  style="border:none; " name="carNum" lay-verify="required<c:if test='${empty car.id}'>|carNumUnique</c:if>" autocomplete="off" value="${car.carNum}" class="layui-input" type="text"
					<c:if test="${not empty car.id}"></c:if>>
				</div>
				<label class="layui-form-label">司机:</label>
				<div class="layui-input-inline">

					<input  style="border:none; " name="driverCode"  autocomplete="off"
					<c:forEach items="${drivers}" var="d" varStatus="status" >
							<c:if test="${d.code == car.driverCode}">value="${d.name}"</c:if>
					</c:forEach>
							class="layui-input" type="text">

				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">车型:</label>
				<div class="layui-input-inline">

					<input  style="border:none; " name="carNum" autocomplete="off"
							<c:if test="${car.carType == '小车4座'}">value="小车4座"</c:if>
							<c:if test="${car.carType == '小车6座'}">value="小车6座"</c:if>
							<c:if test="${car.carType == '小车4座'}">value="小车4座"</c:if>
							<c:if test="${car.carType == '中巴19座'}">value="中巴19座"</c:if>
							<c:if test="${car.carType == '中巴14座'}">value="中巴14座"</c:if>
							<c:if test="${car.carType == '大巴49座'}">value="大巴49座"</c:if>
							<c:if test="${car.carType == '大巴50座'}">value="大巴50座"</c:if>
							<c:if test="${car.carType == '大巴51座'}">value="大巴51座"</c:if>

							class="layui-input" type="text">


				</div>
				<label class="layui-form-label">客座数:</label>
				<div class="layui-input-inline">
					<input style="border:none; " id="seatNumber" name="seatNumber" value="${car.seatNumber}" lay-verify="required|lowerThan" autocomplete="off" class="layui-input" type="tel">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">行驶证:</label>
				<div class="layui-input-inline" style="width: 100px;">      
					<input type="text" name="drivingLicenseEffect" id="drivingLicenseEffect" placeholder="生效日期" autocomplete="off" class="layui-input input_date"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'drivingLicenseInvalid\')}'})"
					value="<fmt:formatDate type='date' pattern='yyyy-MM-dd' value='${car.drivingLicenseEffect}'/>">
				</div>
				<div class="layui-form-mid">-</div>
			    <div class="layui-input-inline" style="width: 100px;">
			    	<input type="text" name="drivingLicenseInvalid" id="drivingLicenseInvalid" placeholder="截止日期" autocomplete="off" class="layui-input input_date"
			    	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'drivingLicenseEffect\')}'})"
			    	value="<fmt:formatDate type='date' pattern='yyyy-MM-dd' value='${car.drivingLicenseInvalid}'/>">    
			    </div>
				<div class="layui-input-inline">
					<img class="img-mini zheng_a" src="
					<c:if test="${not empty car.drivingLicensePros}">${ctx}${car.drivingLicensePros}</c:if>
					<c:if test="${empty car.drivingLicensePros}">${ctx}/static/images/zhen.jpg</c:if>
					" onclick="fun_zheng(this);" alt="正面照" id="drivingLicenseProsImg">
					<div class="upload">
						<i class="icon-zoom-in zheng_a" onclick="fun_zheng(document.getElementById('drivingLicenseProsImg'));"></i>

					</div>
					<img class="img-mini zheng_a" src="
					<c:if test="${not empty car.drivingLicenseCons}">${ctx}${car.drivingLicenseCons}</c:if>
					<c:if test="${empty car.drivingLicenseCons}">${ctx}/static/images/fan.jpg</c:if>
					" onclick="fun_zheng(this);" alt="反面照" id="drivingLicenseConsImg">
					<div class="upload">
						<i class="icon-zoom-in zheng_a" onclick="fun_zheng(document.getElementById('drivingLicenseConsImg'));"></i>

					</div>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">营运证:</label>
				<div class="layui-input-inline" style="width: 100px;">      
					<input type="text" name="operationLicenseEffect" id="operationLicenseEffect" placeholder="生效日期" autocomplete="off" class="layui-input input_date"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'operationLicenseInvalid\')}'})"
					value="<fmt:formatDate type='date' pattern='yyyy-MM-dd' value='${car.operationLicenseEffect}'/>">
				</div>
				<div class="layui-form-mid">-</div>
			    <div class="layui-input-inline" style="width: 100px;">
			    	<input type="text" name="operationLicenseInvalid" id="operationLicenseInvalid" placeholder="截止日期" autocomplete="off" class="layui-input input_date"
			    	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'operationLicenseEffect\')}'})"
			    	value="<fmt:formatDate type='date' pattern='yyyy-MM-dd' value='${car.operationLicenseInvalid}'/>">  
			    </div>
				<div class="layui-input-inline">
					<img class="img-mini zheng_a" src="
					<c:if test="${not empty car.operationLicense}">${ctx}${car.operationLicense}</c:if>
					<c:if test="${empty car.operationLicense}">${ctx}/static/images/zhen.jpg</c:if>
					" onclick="fun_zheng(this);" alt="正面照" id="operationLicenseImg">
					<div class="upload">
						<i class="icon-zoom-in zheng_a" onclick="fun_zheng(document.getElementById('operationLicenseImg'));"></i>

					</div>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">线路牌:</label>
				<div class="layui-input-inline" style="width: 100px;">
					<img class="img-mini zheng_a" src="
					<c:if test="${not empty car.xianlupaiPros}">${ctx}${car.xianlupaiPros}</c:if>
					<c:if test="${empty car.xianlupaiPros}">${ctx}/static/images/zhen.jpg</c:if>
					" onclick="fun_zheng(this);" alt="正面照" id="xianlupaiProsImg">
					<div class="upload">
						<i class="icon-zoom-in zheng_a" onclick="fun_zheng(document.getElementById('xianlupaiProsImg'));"></i>

					</div>
					</div>
					<div class="layui-input-inline" style="width: 100px;">
					<img class="img-mini zheng_a" src="
					<c:if test="${not empty car.xianlupaiCons}">${ctx}${car.xianlupaiCons}</c:if>
					<c:if test="${empty car.xianlupaiCons}">${ctx}/static/images/fan.jpg</c:if>
					" onclick="fun_zheng(this);" alt="反面照" id="xianlupaiConsImg">
					<div class="upload">
						<i class="icon-zoom-in zheng_a" onclick="fun_zheng(document.getElementById('xianlupaiConsImg'));"></i>

					</div>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">车辆照片:</label>
				<div class="layui-input-inline" style="width: 100px;">
					<img class="img-mini zheng_a" src="
					<c:if test="${not empty car.carPhoto}">${ctx}${car.carPhoto}</c:if>
					<c:if test="${empty car.carPhoto}">${ctx}/static/images/zhen.jpg</c:if>
					" onclick="fun_zheng(this);" alt="正面照" id="carPhotoImg">
					<div class="upload">
						<i class="icon-zoom-in zheng_a" onclick="fun_zheng(document.getElementById('carPhotoImg'));"></i>

					</div>
				</div>
				<div class="layui-input-inline" style="width: 100px;">
					<img class="img-mini zheng_a" src="
					<c:if test="${not empty car.carPhoto1}">${ctx}${car.carPhoto1}</c:if>
					<c:if test="${empty car.carPhoto1}">${ctx}/static/images/zhen.jpg</c:if>
					" onclick="fun_zheng(this);" alt="正面照" id="carPhoto1Img">
					<div class="upload">
						<i class="icon-zoom-in zheng_a" onclick="fun_zheng(document.getElementById('carPhoto1Img'));"></i>

					</div>
				</div>
				<div class="layui-input-inline" style="width: 100px;">
					<img class="img-mini zheng_a" src="
					<c:if test="${not empty car.carPhoto2}">${ctx}${car.carPhoto2}</c:if>
					<c:if test="${empty car.carPhoto2}">${ctx}/static/images/zhen.jpg</c:if>
					" onclick="fun_zheng(this);" alt="正面照" id="carPhoto2Img">
					<div class="upload">
						<i class="icon-zoom-in zheng_a" onclick="fun_zheng(document.getElementById('carPhoto2Img'));"></i>

					</div>
				</div>
				<div class="layui-input-inline" style="width: 100px;">
					<img class="img-mini zheng_a" src="
					<c:if test="${not empty car.carPhoto3}">${ctx}${car.carPhoto3}</c:if>
					<c:if test="${empty car.carPhoto3}">${ctx}/static/images/zhen.jpg</c:if>
					" onclick="fun_zheng(this);" alt="正面照" id="carPhoto3Img">
					<div class="upload">
						<i class="icon-zoom-in zheng_a" onclick="fun_zheng(document.getElementById('carPhoto3Img'));"></i>

					</div>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">车辆内景:</label>
				<div class="layui-input-inline" style="width: 100px;">
					<img class="img-mini zheng_a" src="
					<c:if test="${not empty car.carPhoto4}">${ctx}${car.carPhoto4}</c:if>
					<c:if test="${empty car.carPhoto4}">${ctx}/static/images/zhen.jpg</c:if>
					" onclick="fun_zheng(this);" alt="正面照" id="carPhoto4Img">
					<div class="upload">
						<i class="icon-zoom-in zheng_a" onclick="fun_zheng(document.getElementById('carPhoto4Img'));"></i>

					</div>
				</div>
				<div class="layui-input-inline" style="width: 100px;">
					<img class="img-mini zheng_a" src="
					<c:if test="${not empty car.carPhoto5}">${ctx}${car.carPhoto5}</c:if>
					<c:if test="${empty car.carPhoto5}">${ctx}/static/images/zhen.jpg</c:if>
					" onclick="fun_zheng(this);" alt="正面照" id="carPhoto5Img">
					<div class="upload">
						<i class="icon-zoom-in zheng_a" onclick="fun_zheng(document.getElementById('carPhoto5Img'));"></i>

					</div>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">保险单:</label>
				<div class="layui-input-inline" style="width: 100px;">
					<input type="text" name="policyEffect" id="policyEffect" placeholder="生效日期" autocomplete="off" readonly="true" class="layui-input input_date"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'operationLicenseInvalid\')}'})"
					value="<fmt:formatDate type='date' pattern='yyyy-MM-dd' value='${car.policyEffect}'/>">
				</div>
				<div class="layui-form-mid">-</div>
			    <div class="layui-input-inline" style="width: 100px;">
			    	<input type="text" name="policyInvalid" id="policyInvalid" placeholder="截止日期" autocomplete="off" class="layui-input input_date"
			    	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'policyEffect\')}'})"
			    	value="<fmt:formatDate type='date' pattern='yyyy-MM-dd' value='${car.policyInvalid}'/>">   
			    </div>
				<div class="layui-input-inline">
					<img class="img-mini zheng_a" src="
					<c:if test="${not empty car.policy}">${ctx}${car.policy}</c:if>
					<c:if test="${empty car.policy}">${ctx}/static/images/zhen.jpg</c:if>
					" onclick="fun_zheng(this);" alt="正面照" id="policyImg">
					<div class="upload">
						<i class="icon-zoom-in zheng_a" onclick="fun_zheng(document.getElementById('policyImg'));"></i>

					</div>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">出厂日期:</label>
				<div class="layui-input-inline" style="width: 100px;">      
					<input type="text" name="madeDate" id="madeDate" lay-verify="required" placeholder="出厂日期" autocomplete="off" class="layui-input input_date"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
					value="<fmt:formatDate type='date' pattern='yyyy-MM-dd' value='${car.madeDate}'/>"> 
				</div>
				<label class="layui-form-label">停场地点:</label>
				<div class="layui-input-inline">
					<input id="site" name="site" lay-verify="required" autocomplete="off" value="${car.site}" class="layui-input" type="text" readonly onclick="showMap();" style="cursor: pointer;border:none;"/>
					<input id="lng" name="lng" type="hidden" value="${car.lng}"/>
					<input id="lat" name="lat" type="hidden" value="${car.lat}"/>
					<input id="area" name="area" type="hidden" value="${car.area}"/>
					<input id="city" name="city" type="hidden" value="${car.city}"/>
				</div>
			</div>
		</div>
		<button class="layui-btn layui-btn-danger" style="float:right;display: none;" lay-submit lay-filter="submitBtn" id="submitBtn">发布</button>
	</form>
</div>
<script type="text/javascript">
//文档加载完毕执行
$(function() {

	var message = '${message}';
	if(message != null && "" != message){
		var msg = message.split("@");
		setTimeout(function(){
			layer.msg(msg[0], {icon: msg[1],time: 1000}, function(){
				parent.selfSubmit();
			});
		}, 100);
	}
});
//预览图片
function previewImg(obj){
	var url = getFileUrl(obj.id);
	$('#'+obj.id.replace('File','Img')).attr('src', url);
}
//提交表单
function laySubmit(){
	$('#submitBtn').click();
}
//图片放大
function fun_zheng(obj){
	  parent.layer.open({
	    type: 2,
	    title: false,
	    shadeClose: false,
	    shade: 0,
	    shift:-1,
	    area: ['570px','390px'],
	    content: obj.src
	  })
}
function showMap(){
	parent.layer.open({
	    type: 2,
        title: "请搜索地点",
	    shadeClose: false,
	    shade: 0,
	    shift:-1,
	    area: ['650px','600px'],
	    content: '${ctx}/transport/car/map?site='+$('#site').val()+'&lng='+$('#lng').val()
	    		+'&lat='+$('#lat').val()+'&area='+$('#area').val()+'&city='+$('#city').val(),
	    btn: ['确定', '取消'],
	    yes: function(index, layero){
	    	 //var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
	    	 setTimeout(function(){
		    	 var body = parent.layer.getChildFrame('body', index);
		    	 if(body.find('#site').val() != ''){
		    		 $('#site').val(body.find('#site').val());
		    		 $('#lng').val(body.find('#lng').val());
		    		 $('#lat').val(body.find('#lat').val());
		    		 $('#area').val(body.find('#area').val());
		    		 $('#city').val(body.find('#city').val());
		    	 }
		    	 parent.layer.close(index); //再执行关闭
	    	 }, 500);
	    },
	    btn2: function(index, layero){
	    },
	    cancel: function(index, layero){ 
	    }
	  })
}
//加载表单
layui.use(['form','element','laydate'], function() {
	
	var laydate = layui.laydate;
	//执行laydate实例
	/* laydate.render({
	  elem: '#drivingLicenseEffect' //指定元素
	}); */
	//日期时间选择器
	/* laydate.render({
	 elem: '#drivingLicenseInvalid'
	});
	laydate.render({
	  elem: '#operationLicenseEffect' //指定元素
	});
	//日期时间选择器
	laydate.render({
	 elem: '#operationLicenseInvalid'
	});
	laydate.render({
	  elem: '#policyEffect' //指定元素
	});
	//日期时间选择器
	laydate.render({
	 elem: '#policyInvalid'
	});
	//日期时间选择器
	laydate.render({
	 elem: '#madeDate'
	}); */
 
    var form = layui.form;
    
  	//监听提交
    form.on('submit(submitBtn)', function(data){
      //layer.msg(JSON.stringify(data.field));
     /* if($('#drivingLicensePros').val() == '' && $('#drivingLicenseProsFile').val() == ''){
    	  layer.msg('行驶证正面照不能为空！', {icon: 2,time:1000});
    	  return false;
      }
      if($('#drivingLicenseCons').val() == '' && $('#drivingLicenseConsFile').val() == ''){
    	  layer.msg('行驶证反面照不能为空！', {icon: 2,time:1000});
    	  return false;
      }
      if($('#operationLicense').val() == '' && $('#operationLicenseFile').val() == ''){
    	  layer.msg('营运证照片不能为空！', {icon: 2,time:1000});
    	  return false;
      }
      if($('#xianlupaiPros').val() == '' && $('#xianlupaiProsFile').val() == ''){
    	  layer.msg('线路牌正面照不能为空！', {icon: 2,time:1000});
    	  return false;
      }
      if($('#xianlupaiCons').val() == '' && $('#xianlupaiConsFile').val() == ''){
    	  layer.msg('线路牌反面照不能为空！', {icon: 2,time:1000});
    	  return false;
      }
      if($('#carPhoto').val() == '' && $('#carPhotoFile').val() == ''){
    	  layer.msg('车辆第一张照片不能为空！', {icon: 2,time:1000});
    	  return false;
      }
      if($('#carPhoto1').val() == '' && $('#carPhoto1File').val() == ''){
    	  layer.msg('车辆第二张照片不能为空！', {icon: 2,time:1000});
    	  return false;
      }
      if($('#carPhoto2').val() == '' && $('#carPhoto2File').val() == ''){
    	  layer.msg('车辆第三张照片不能为空！', {icon: 2,time:1000});
    	  return false;
      }
      if($('#carPhoto3').val() == '' && $('#carPhoto3File').val() == ''){
    	  layer.msg('车辆第四张照片不能为空！', {icon: 2,time:1000});
    	  return false;
      }
      if($('#policy').val() == '' && $('#policyFile').val() == ''){
    	  layer.msg('保险单照片不能为空！', {icon: 2,time:1000});
    	  return false;
      }*/
      return true;
    });
    form.on('select(carTypeFilter)', function(data){
  	  /* console.log(data.elem); //得到select原始DOM对象
  	  console.log(data.value); //得到被选中的值
  	  console.log(data.othis); //得到美化后的DOM对象 */

        var carType = $("#carType").find("option:selected").val();

	    var reg = /\d+/g;
	    var ms = data.value.match(reg);
  	    $('#seatNumber').val(ms-1);
  	}); 
    //自定义验证规则
    form.verify({
    	//不能高于车型并且要大于零
        lowerThan:function (value,item) {
            if(!/^[0-9]*$/.exec(value)){
                return "只能输入数字";
            }
            var carType = $("#carType").find("option:selected").val();
            var reg = /\d+/g;
      	    var ms = carType.match(reg);
            if(value > ms){
                return "客座数不可以比车座数大";
            }
            if(value < 1){
                return "客座数不能小于1";
            }

        },
    	carNumUnique:function (value,item) {

            if(value != '' && value != null){
            	var dataJson = {'carNum':value};
            	console.log(dataJson)
                var result = check(dataJson, '${ctx}/transport/car/carNumUnique');
                if(!result){
                	return "车牌号已经存在";
                }
            }
        }
    });
});
function check(dataJson, url) {
    var result = true;
    $.ajax({
        async:false,
        type:"post",
        url:url,
        dataType:"json",
        data:dataJson,
        success:function(data) {
            result = data;
        }
    });
    return result;
}
</script>
</body>
</html>