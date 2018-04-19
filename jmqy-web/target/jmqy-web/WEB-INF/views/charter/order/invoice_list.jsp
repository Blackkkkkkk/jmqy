<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>个人中心</title>
	<style type="text/css">
		.button_left label{float: left;}
		.button_left span{float: left;}

		.kdspan{
			float:right;
			background:#76838f;
			color:#fff;
			padding:4px 10px;
		}
		.lookp{
			float:right;
			color:#38baf4;
			margin-bottom:10px;
		}

	</style>
</head>
<body>
<section>
	<div class="container">
		<jsp:include page="/WEB-INF/layouts/left_menu.jsp"/>
		<main>
			<div class="position_all">
				<div class="position">您所在的位置：首页 -> 个人中心 -> 申请发票</div>
			</div>
			<div class="info">
				<div id="tab1" class="layui-tab layui-tab-card">
					<ul class="layui-tab-title">
						<li class="layui-this">可开具发票的订单</li>
						<li>收件地址管理</li>
					</ul>
					<div class="layui-tab-content" >
						<div class="layui-tab-item layui-show">
							<form id="searchForm" action="${ctx}/charter/order/invoiceList" method="post">
								<input type="hidden" name="pageNum" value="${order.pageNum}"/>
								<input type="hidden" name="pageSize" value="${order.pageSize}"/>
								<div class="button_left">
								</div>
								<div class="button_right">
									<div class="layui-inline">
										<label class="layui-form-label">出发日期：</label>
										<div class="layui-input-inline" style="top: 5px;">
											<input class="layui-input search_text input_date" id="beginTime" name="beginTime" type="text"
												   value='<fmt:formatDate value="${order.beginTime}" pattern="yyyy-MM-dd"/>'
												   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})"/>_
										</div>
										<div class="layui-input-inline" style="top: 5px;">
											<input class="layui-input search_text input_date" id="endTime" name="endTime" type="text"
												   value='<fmt:formatDate value="${order.endTime}" pattern="yyyy-MM-dd"/>'
												   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginTime\')}'})"/>
										</div>
										<div class="layui-inline">
											<label class="layui-form-label">筛选条件：</label>
											<div class="layui-inline john_1" style="top: 10px;">
												<select name="orderStatus" id="orderStatus" lay-verify="required">
													<option value="3" <c:if test="${order.orderStatus == 3}"> selected </c:if>>邮寄中</option>
													<option value="2" <c:if test="${order.orderStatus == 2}"> selected </c:if>>已通过</option>
													<option value="1" <c:if test="${order.orderStatus == 1}"> selected </c:if>>已申请</option>
													<option value="0" <c:if test="${order.orderStatus == 0}"> selected </c:if>>未申请</option>
													<option value=""  <c:if test="${order.orderStatus == '' || order.orderStatus == null}"> selected </c:if>>全部订单</option>
												</select>
											</div>

										</div>
										<div class="layui-inline">
											<a class="layui-btn layui-btn-danger layui-btn-small" href="#" onclick="submitCharterForm();">搜索</a>
										</div>
										<div class="layui-inline">
											<a class="layui-btn layui-btn-danger layui-btn-small" href="javascript:query1();">自定义发票模板</a>
										</div>
									</div>
									<div class="cl"></div>
									<div class="layui-form">
										<c:forEach items="${orders.list}" var="ord">
											<table class="layui-table" lay-even="" lay-skin="nob" style="border: 1px solid #CCC;">
												<colgroup>
													<col width="250">
													<col width="250">
													<col width="100">
													<col width="100">
													<col width="200"/>
												</colgroup>
												<thead>
												<tr>
													<th colspan="5">
														<span><a href="javascript:query('${ord.orderCode}',1);" style="color: orange;">订单号：${ord.orderCode}</a></span>
														<c:if test="${ord.recordStatus == 0 }">
															<a href="javascript:orderCharterDel('${ctx}','${ord.orderCode}');">删除订单</a>
														</c:if>
														<c:if test="${ord.recordStatus == 1 }">
															<a href="#" style="color: red;">已删除</a>
														</c:if>
													</th>

												</tr>
												</thead>
												<tbody>
												<tr>
													<td>${ord.startPoint} — ${ord.endPoint}</td>
													<td><fmt:formatDate value="${ord.placeTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
													<td><span class="orange"><b>
								      <c:choose>
										  <c:when test="${ord.amount != null}">￥${ord.amount}</c:when>
										  <c:when test="${ord.amount == null}">￥0</c:when>
									  </c:choose>
								      </b></span>
													</td>
													<td>
														<c:choose>
															<c:when test="${ord.status == -4}">已取消</c:when>
															<c:when test="${ord.status == -3}">被拒绝</c:when>
															<c:when test="${ord.status == -2}">待匹配</c:when>
															<c:when test="${ord.status == -1}">匹配中</c:when>
															<c:when test="${ord.status == 0}">待接受</c:when>
															<c:when test="${ord.status == 1}">等待上车</c:when>
															<c:when test="${ord.status == 2}">在途</c:when>
															<c:when test="${ord.status == 3}">已完成</c:when>
														</c:choose>
													</td>
													<td>
														<c:if test="${ord.status == 3 && ord.recordStatus == 0 && ord.invoiceId == null}">
															<c:choose>
																<c:when test="${ord.orderStatus == 1}">
																	<a href="javascript:detil('${ord.orderCode}',1);" class="layui-btn layui-btn-normal layui-btn-mini">发票申请审批中</a>
																</c:when>
																<c:when test="${ord.orderStatus == 2}">
																	<a href="javascript:detil('${ord.orderCode}',2);" class="layui-btn layui-btn-normal layui-btn-mini">已通过</a>
																</c:when>
																<c:when test="${ord.orderStatus == 3}">
																	<a href="javascript:detil('${ord.orderCode}',3);" class="layui-btn layui-btn-normal layui-btn-mini">邮寄中</a>
																</c:when>
																<c:otherwise>
																	<c:if test="${ord.paymentStatus !=1 and ord.paymentType !=2}">
																		<a href="javascript:query('${ord.orderCode}',4);" class="layui-btn layui-btn-normal layui-btn-mini">开发票</a>
																	</c:if>
																	</c:otherwise>
															</c:choose>

														</c:if>
													</td>
												</tr>
												</tbody>
											</table>
										</c:forEach>
									</div>
								</div>
							</form>
							<div class="layui-form" id="demo7"></div>
							<tags:page pages="${orders}"/>
						</div>
						<div id="tab2" class="layui-tab-item">
							<a href="javascript:;" class="layui-btn layui-btn-smal invoice_a1" onclick="addAddress(1);">添加收货地址</a> 收件地址最多添加五条
							<table class="layui-table" lay-even="" lay-skin="nob">
								<thead>
								<tr>
									<th>收件人</th>
									<th>详细地址</th>
									<th>联系方式</th>
									<th>操作</th>
								</tr>
								</thead>
								<tbody>
								<c:forEach items="${requestScope.addressList}" var="list"  varStatus="status"  begin="0"    end="4">
									<tr>
										<td>${list.recipient}</td>
										<td>${list.address}</td>
										<td>${list.contactWay}</td>
										<td><a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini invoice_a1" onclick="addAddress(2,'${list.id}');">修改</a>
											<a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini"  onclick="del(3,'${list.id}');">删除</a><a href="javascript:;" class="layui-btn layui-btn-normal layui-btn-mini" onclick="del(4,'${list.id}');">
												<c:choose>
													<c:when test="${list.status == 1}">默认地址</c:when>
													<c:when test="${list.status == 0}">设置为默认地址</c:when>
												</c:choose>
											</a></td>
									</tr>
								</c:forEach>

								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</main>
	</div>
</section>
<script type="text/javascript">
	//添加收货地址
	//actionType： 1、新增  2、修改  3、删除 4、设为默认地址
	function addAddress(actionType,id) {

	    var num; //判定条件，是否满足5条；
		var url; //
		var msg;

	    if(actionType=='1'){
                 num = ${fn:length(requestScope.addressList)}
				 url='${ctx}/charter/invoice/Address/addAddress?actionType='+actionType;
				 msg='添加成功！'
		}else{
	        num = 3;//只要小于5便可
            url='${ctx}/charter/invoice/Address/addAddress?actionType='+actionType+'&id='+id;
            msg='修改成功！'
        }


		if(num>=5){
            layer.msg('收货地址只能保存5条！', {
                icon: 1,
                time: 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function(){
                //do something
            });
        }else{
        layer.open({
            type: 2,
            title: '添加收货地址',
            shadeClose: true,
            shade: 0.5,
            btn: ['保存', '取消'],
            yes: function(index, layero){
               var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
				//    var iframeWin = $("div.layui-layer-content > iframe")[0].contentWindow;
				    iframeWin.sub();//执行iframe页的方法：

                layer.closeAll();

                layer.msg(msg, {
                    icon: 1,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                }, function(){

					location.reload('${ctx}/charter/order/invoiceList?actionType='+actionType);
                });
            },
            area: ['450px','500px'],
            content: url
        })
		}
    }



	$(document).ready(function () {
    //  alert('${requestScope.tab}')
    })

	function detil(ordercode,orderStatus) {


        layer.open({
            type: 2,
            title: '查看发票信息',
            shadeClose: true,
            shade: 0.5,
            area: ['950px','500px'],
            content: '${ctx}/charter/invoice/detil?orderCode='+ordercode+'&orderStatus='+orderStatus
        })
    }

    //查询
    function submitCharterForm(){

        var beginTime = $("#test1").val();
        var endTime = $("#test2").val();
        if((beginTime == null || '' == beginTime) && (endTime != null && '' != endTime)){
            layer.tips('开始日期不能为空！', '#beginTime', {tips: [3, '#78BA32']});
            return false;
        }
        $("#searchForm").submit();
    }
    function query(orderCode,type){
        var width ="900px";
        var height = "600px;"
        var title ="订单详情";
        if(type ==1){
            height = "350px;"
            title ="订单详情";
        }else if(type ==3){
            height = "560px;"
            title ="订单投诉";
        }
        layer.open({
            type: 2,
            title: title,
            shadeClose: true,
            shade: 0.5,
            yes: function(index, layero){
                var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
                iframeWin.laySubmit();//执行iframe页的方法：
            },
            area: [width,height],
            content: '${ctx}/charter/order/query?orderCode='+orderCode+'&type='+type+'&invoice=1'
        })
    }
//保存发票模板
    function query1(){
        var width ="500px";
        var height = "500px;"
        var title ="自定义发票模板";
        layer.open({
            type: 2,
            title: title,
            shadeClose: true,
            shade: 0.5,
            yes: function(index, layero){
                var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
                iframeWin.laySubmit();//执行iframe页的方法：
            },
            area: [width,height],
            content: '${ctx}/charter/order/InvoiceTemplate'
        })
    }





    //删除数据
    function del(actionType,id){

         var msg;
           if(actionType=='3'){
               msg='删除成功！'
		   }else{
               msg='设置默认地址成功！'
		   }
        $.ajax({
            type:"post",
            url:"${ctx}/charter/invoice/Address/ActionAddress",
            data:{"actionType":actionType,"id":id},
            success:function(data) {
				if(data.state=='success'){

                    layer.msg(msg, {
                        icon: 1,
                        time: 1000 //2秒关闭（如果不配置，默认是3秒）
                    }, function(){
                        location.reload('${ctx}/charter/order/invoiceList?actionType='+actionType);
                       // window.location.href='${ctx}/charter/order/invoiceList?actionType=2'
                    });
                }
            }
        })
    }

</script>
</body>
</html>