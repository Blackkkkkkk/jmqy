<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>额度查询</title>
</head>
<body>
<section>
	<div class="container">
		<jsp:include page="/WEB-INF/layouts/left_menu.jsp" />
		<main>
		<div class="position_all">
			<div class="position">您所在的位置：首页 -> 管理中心 -> 余额查询</div>
		</div>
		<form id="searchForm" class="layui-form" action="${ctx}/transport/finance/balance" method="post">
		<input type="hidden" name="flag" value="${flag}"/>
		<input type="hidden" name="pageNum" value="${order.pageNum}"/>
        <input type="hidden" name="pageSize" value="${order.pageSize}"/>
			<div class="searh searh1 integral">
				<div class="quota">
					<div class="quota_title">我的账户余额</div>
					<p>
						<label>余额：</label><span class="orange">${balance.balance}</span>元
					</p>
					<p>
						<a class="layui-btn layui-btn-primary layui-btn-mini quota_a">提现</a>
						<a class="layui-btn layui-btn-primary layui-btn-mini quota_a">明细</a>
						<a class="layui-btn layui-btn-primary layui-btn-mini quota_a">绑定银行卡</a>
					</p>
				</div>
			</div>
			<div id="quota_" class="layui-form <c:if test='${flag == 0}'>quota_</c:if>">
				<table class="layui-table">
					<thead>
						<tr>
							<th>日期</th>
							<th>操作</th>
							<th>金额</th>
							<th>交易类型</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${bills.list}" var="bill" varStatus="status">
						<tr>
							<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${bill.createDate}" /></td>
							<td>
	                            <c:if test="${bill.billType == 1}">支付佣金</c:if>
								<c:if test="${bill.billType == 2}">订单入账</c:if>
								<c:if test="${bill.billType == 4}">余额提现</c:if>
                            </td>
							<td>${bill.money}</td>
							<td>
								<c:if test="${bill.tradeMode == 0}">账户余额</c:if>
								<c:if test="${bill.tradeMode == 1}">信用额度</c:if>
	                            <c:if test="${bill.tradeMode == 2}">微信</c:if>
								<c:if test="${bill.tradeMode == 3}">支付宝</c:if>
								<c:if test="${bill.tradeMode == 4}">银联</c:if>
                            </td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				<tags:page pages="${bills}"/>
			</div>
		</form>
		</main>
	</div>
</section>
<script type="text/javascript">
/***额度查询***/
$('.quota_a').click(function(){
	var flag = $('[name=flag]').val();
	if(flag==0){
		$('#quota_').css('display','block');
		$('[name=flag]').val(1);
	}
	if(flag==1){
		$('#quota_').css('display','none');
		$('[name=flag]').val(0);
	}
})
//加载表单
layui.use(['form','element','laydate'], function() {
	
	//执行laydate实例
	var laydate = layui.laydate;
	
	/* //日期时间选择器
	laydate.render({
		elem: '#id'
		,type: 'datetime'
		,theme: '#0071bf'
	});  */
	
    var form = layui.form;
    
  	//监听提交
    form.on('submit(formDemo)', function(data){
      //layer.msg(JSON.stringify(data.field));
      return true;
    });
  	
    //自定义验证规则
    form.verify({
        //只能输入数字
        integer:function (value,item) {
            if(!/^[0-9]*$/.exec(value)){
                return "只能输入数字";
            }
        }

    });
});
</script>
</body>
</html>