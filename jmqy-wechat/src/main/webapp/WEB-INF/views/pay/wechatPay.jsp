<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>扫描支付</title>
<script type="text/javascript" src="${ctx}/images/js/qrcode.js"></script>
	<script type="text/javascript" src="${ctx}/images/jquery-3.2.1.min.js"></script>



	<style>
		#qrcode {
			position: absolute;
			top: 30%;
			height: 240px;
			margin-top: -120px; /* 盒子本身高度的一半 */
		}

		.quota_title{
			text-align:center;

			margin-top:20px; /* 盒子本身高度的一半 */
		}

	</style>
</head>
<body>
<section>
	<div class="container">
		<div class="searh searh1 integral">
			<div class="quota" style="margin-left: 120px;">
				<div class="quota_title">扫描二维码微信支付</div>
				<div id="qrcode"></div>
			</div>
		</div>
	</div>
</section>

<script type="text/javascript">



    var iHeight = $(window).height();
    var iWidth = $(document).height();
    var iTop = (window.screen.availHeight-30-iHeight)/2;       //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth-10-iWidth)/2;
    var qrcode = new QRCode(document.getElementById("qrcode"), {

        width : 200,
        height :200

    });
    function makeCode () {

        qrcode.makeCode('${codeUrl}');
    }
    makeCode();
    function nativeNotify(data){
        var d = data.split(",");
        if(d[0] == "SUCCESS"){
            parent.layer.msg('支付成功', {icon: 1,time:1000},function(){
                parent.location.replace('${ctx}/charter/order/list/0');
            });
        }else{
            parent.layer.msg('支付失败', {icon: 2,time:1000});
        }

    }
</script>




</body>
</html>