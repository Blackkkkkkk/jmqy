<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>包车企业认证</title>
</head>
<body class="window">
<div class="layui-form" style="margin-top: 10px;">
	<form id="saveOrUpdate" class="layui-form" action="${ctx}/charter/center/beCompany" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${company.id}"/>
		<input type="hidden" name="companyCode" value="${company.companyCode}"/>
		<div class="layui-form-item" style="width: 500px;">
			<label class="layui-form-label">企业名称:</label>
			<div class="layui-input-block">
				<input name="companyName" lay-verify="required" autocomplete="off" value="${company.companyName}" class="layui-input" type="text"/>
			</div>
		</div>
		<div class="layui-form-item" style="width: 500px;">
			<label class="layui-form-label">营业执照:</label>
			<div class="layui-input-block">
				<img class="img-mini zheng_a" src="
				<c:if test="${not empty company.businessPic}">${ctx}${company.businessPic}</c:if>
				<c:if test="${empty company.businessPic}">${ctx}/static/images/zhen.jpg</c:if>
				" onclick="fun_zheng(this);" alt="正面照" id="businessPicImg">
				<div class="upload">
					<i class="icon-zoom-in zheng_a" onclick="fun_zheng(document.getElementById('businessPicImg'));"></i>
					<i class="icon-cloud-upload" style="cursor:pointer;">
						<input id="businessPic" name="businessPic" type="hidden" value="${company.businessPic}"/>
						<input type="file" class="upload-button" id="businessPicFile" name="businessPicFile" onchange="previewImg(this);"/>
					</i>
				</div>
			</div>
		</div>
		<div class="layui-form-item" style="width: 500px;">
			<label class="layui-form-label">企业地址:</label>
			<div class="layui-input-block">
				<input name="companyAddress" lay-verify="required" autocomplete="off" value="${company.companyAddress}" class="layui-input" type="text"/>
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
//加载表单
layui.use(['form','element','laydate'], function() {
	
	var laydate = layui.laydate;
 
    var form = layui.form;
    
  	//监听提交
    form.on('submit(submitBtn)', function(data){
      //layer.msg(JSON.stringify(data.field));
    	if($('#businessPic').val() == '' && $('#businessPicFile').val() == ''){
      	  layer.msg('营业执照不能为空！', {icon: 2,time:1000});
      	  return false;
        }
      return true;
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
        }
    });
});
</script>
</body>
</html>