<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>车辆状态</title>
</head>
<body>
	<section>
		<div class="container">
			<jsp:include page="/WEB-INF/layouts/left_menu.jsp" />
			<main>
			<div class="position_all">
				<div class="position">您所在的位置：首页 -> 管理中心 -> 车辆状态</div>
			</div>
			<div class="info">
				<form action="">
					<div class="button_right">
						<input type="text" class="layui-input" placeholder="请输入关键字">
						<a class="layui-btn layui-btn-danger layui-btn-small" href="">搜索</a>
					</div>
					<div class="cl"></div>

					<div class="layui-form">
						<table class="layui-table">

							<thead>
								<tr>
									<th>车牌号</th>
									<th>司机</th>
									<th>起终点</th>
									<th>执行状态</th>
									<th>开始时间</th>
									<th>结束时间</th>
									<th>定位</th>

								</tr>
							</thead>
							<tbody>
								<tr>
									<td>粤A 888888</td>
									<td>吴某某</td>
									<td>开平 — 恩平</td>
									<td>前往上车点</td>
									<td>2017-08-28 12:00:00</td>
									<td>2017-08-28 12:00:00</td>
									<td><a href=""><span class="orange">获取</span></a></td>
								</tr>
								<tr>
									<td>粤A 888888</td>
									<td>吴某某</td>
									<td>开平 — 恩平</td>
									<td>前往目的地</td>
									<td>2017-08-28 12:00:00</td>
									<td>2017-08-28 12:00:00</td>
									<td><a href=""><span class="orange">获取</span></a></td>
								</tr>
								<tr>
									<td>粤A 888888</td>
									<td>吴某某</td>
									<td>开平 — 恩平</td>
									<td>返程中</td>
									<td>2017-08-28 12:00:00</td>
									<td>2017-08-28 12:00:00</td>
									<td><a href=""><span class="orange">获取</span></a></td>
								</tr>
							</tbody>
						</table>
					</div>
				</form>
				<div id="demo7"></div>
			</div>
			</main>
		</div>
	</section>
	<script type="text/javascript">
		//文档加载完毕执行
		$(function() {
			var message = '${message}';
			if (message != null && "" != message) {
				var msg = message.split("@");
				setTimeout(function() {
					layer.alert(msg[0], {
						icon : msg[1]
					});
				}, 500);
			}
		});
		//提交表单
		function selfSubmit() {
			$("#searchForm").submit();
		}

		//搜索
		function selfSearch() {
			$("[name='pageNum']").val(1);//第一页
			selfSubmit();
		}

		/*****、新增、修改/车辆*****/
		function update(carNum) {
			layer.open({
				type : 2,
				title : '车辆添加修改',
				shadeClose : true,
				shade : 0.5,
				btn : [ '提交', '取消' ],
				yes : function(index, layero) {
					var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
					iframeWin.laySubmit();//执行iframe页的方法：
				},
				btn2 : function(index, layero) {
					selfSearch();
				},
				cancel : function(index, layero) {
					selfSearch();
				},
				area : [ '650px', '600px' ],
				content : '${ctx}/transport/car/form?carNum=' + carNum
			})
		}

		/*
		 * status:
		 * 0:启用，1:冻结
		 */
		function updateById(id, status) {
			var typeStr = status == 0 ? '启用' : '冻结';
			layer.confirm('真的' + typeStr + '这辆车辆么?', {
				icon : 3,
				title : '提示'
			}, function(index) {
				$.ajax({
					type : "POST",
					url : "${ctx}/transport/car/updateById",
					data : {
						'id' : id,
						'status' : status
					},
					dataType : "json",
					success : function(data) {
						var msg = '';
						var icon = 1;
						if (data < 0) {
							msg = '失败，系统错误！';
							icon = 2;//失败
						} else {
							if (data == 0) {
								msg = '失败';
							} else {
								msg = '成功';
							}
						}
						layer.msg(typeStr + msg, {
							time : 1000,
							skin : 'layui-layer-molv',
							area : '20px',
							icon : icon
						}, function() {
							selfSubmit();
						});
					}
				});
				layer.close(index);
				//向服务端发送操作指令
			});

		}
	</script>
</body>
</html>