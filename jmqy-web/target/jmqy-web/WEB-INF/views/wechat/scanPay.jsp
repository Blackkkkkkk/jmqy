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
		<jsp:include page="/WEB-INF/layouts/left_menu.jsp" />
		<main>
		<div class="position_all">
			<div class="position">您所在的位置：首页 -> 管理中心 -> 扫描支付</div>
		</div>
		<div class="searh searh1 integral">
			<div class="quota">
				<div class="quota_title">扫描二维码微信支付demo</div>
				<div id="qrcode"></div>
			</div>
		</div>
		</main>
	</div>
</section>
<script type="text/javascript">
var qrcode = new QRCode(document.getElementById("qrcode"), {
	width : 100,
	height : 100
});
function makeCode () {		
	qrcode.makeCode('${codeUrl}');
}
makeCode();

function nativeNotify(data){
	alert(data);
}
</script>
</body>
</html>