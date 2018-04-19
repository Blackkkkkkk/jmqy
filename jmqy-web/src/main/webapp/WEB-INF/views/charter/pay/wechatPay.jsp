<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>扫描支付</title>
<script type="text/javascript" src="${ctx}/static/js/qrcode.js"></script>
</head>
<body>
<section>
	<div class="container">
		<div class="searh searh1 integral">
			<div class="quota" style="margin-left: 120px;">
				<div class="quota_title">扫描二维码微信支付</div>
				<!--<div id="qrcode">
				</div> -->
				<img id="myimg" src="">

			</div>
		</div>
	</div>
</section>
<script type="text/javascript">
	// 定时器
    var t;
$(function(){
	/*var return_code = '${result.return_code}';
	if(return_code == 'SUCCESS'){
		var result_code = '${result.result_code}';
		/*if(result_code == 'FAIL'){
			alert('${result.err_code_des}');
			parent.layer.msg('${result.err_code_des}', {icon: 2,time:1000}, function(){
				parent.layer.closeAll();
			});
		}
	}else{
		parent.layer.msg('请求支付失败', {icon: 2,time:1000}, function(){
			parent.layer.closeAll();
		});
	}*/
})
/*var qrcode = new QRCode(document.getElementById("qrcode"), {
	width : 100,
	height : 100
});*/
function makeCode () {

    var src = "http://219.130.135.53:8090/"
    src = src+'${src}'
    $("#myimg").attr('src',src);

	t=setInterval("GetRe()", 1000)
//	qrcode.makeCode('${codeUrl}');
//    qrcode.makeCode('${src}');
}




//定时器调用函数，1秒请求一次后台，查看是否支付成功
function  GetRe() {

    clearInterval(t);

    var url ; // 支付地址
	var reurl ; //支付成功后的重定向地址
    var dataJson;//参数

    if('${actiontype}'==1){ // 1是下单支付
        url = "${ctx}/charter/wechat/NewPayResult";
        reurl = '${ctx}/charter/order/list/0'
		dataJson = {"bigOrderCode": '${code}', "prices": '${prices}'};
    }else if ('${actiontype}'==2){ //2是额度结算

        url = "${ctx}/charter/wechat/creDitPay";
        reurl = '${ctx}/charter/center/creditQuery'
        dataJson = {"bigOrderCode": '${code}', "prices": '${prices}'};
    }else  if('${actiontype}'==3){ // 3是费用结算

        url = "${ctx}/charter/wechat/costPay";
        reurl = '${ctx}/charter/order/balanceList'
        dataJson = {"bigOrderCode": '${code}', "prices": '${prices}'};
    }

    $.ajax({
        type:"post",
        url:url,
        data:dataJson,
        success:function(data) {
            console.log(data)

			if(data.state=="success"){
                parent.layer.msg('支付成功', {icon: 1,time:1000},function(){
                    parent.location.replace(reurl);
                });
            }else{
			    //重新启动定时器
                t=setInterval("GetRe()", 1000)
                 //   parent.layer.msg('支付失败', {icon: 2,time:1000});
            }
        },
        error: function(dataRes) {
            alert(dataRes)
            //alert("error cause by ajax error:" + dataRes.responseText );
        }

    })


}

makeCode();
/*function nativeNotify(data){
	var d = data.split(",");
	if(d[0] == "SUCCESS"){
		parent.layer.msg('支付成功', {icon: 1,time:1000},function(){
			parent.location.replace('${ctx}/charter/order/list/0');
		});
	}else{
		parent.layer.msg('支付失败', {icon: 2,time:1000});
	}
	/* parent.layer.closeAll();
	parent.location.reload(); */
//}
</script>
</body>
</html>