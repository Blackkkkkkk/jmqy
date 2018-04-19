<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>评价分析</title>
<style>
table tr td:nth-child(odd) {
	background: #eee;
	width: 25%;
	text-align: center;
	line-height: 35px;
}

table tr td:nth-child(even) {
	width: 25%;
	padding-left: 20px;
	line-height: 35px;
}
#main{
    width: 98%;
    height: 400px;
    margin-left: auto;
    margin-right: auto;
    margin-top: 50px;
}
</style>
</head>
<body>
	<section>
		<div class="container">
			<jsp:include page="/WEB-INF/layouts/left_menu.jsp" />
			<main>
			<div class="position_all">
				<div class="position">您所在的位置：首页 -> 管理中心 -> 统计分析</div>
			</div>
			<div class="info">
				<table border="1" bordercolor="#ccc" width="100%">
					<tr>
						<td>上周成交量</td>
						<td>888</td>
						<td>上月成交量</td>
						<td>888888</td>
					</tr>
					<tr>
						<td>本周成交量</td>
						<td>888</td>
						<td>本月成交量</td>
						<td>888888</td>
					</tr>
					<tr>
						<td>本周预计成交量</td>
						<td>999</td>
						<td>本月预计成交量</td>
						<td>99999</td>
					</tr>
				</table>
				<div id="main"></div>
			</div>
			</main>
		</div>
	</section>
<script>
       water_level();
       function water_level() {
           // 路径配置
           require.config({
               paths : {
                   echarts : 'http://echarts.baidu.com/build/dist'
               }
           });

           // 使用
           require([ 'echarts', 'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
               'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
           ], function(ec) {
               // 基于准备好的dom，初始化echarts图表
               var myChart = ec.init(document.getElementById('main'));

               option = {
                   title : {
                       text : '最近30天交易趋势图'
                   },
                   tooltip : {
                       trigger : 'axis'
                   },
                   legend : {
                       data : [ '取消', '订单' ]
                   },
                   toolbox : {
                       show : true,
                       feature : {
                           mark : {
                               show : true
                           },
                           dataView : {
                               show : true,
                               readOnly : false
                           },
                           magicType : {
                               show : true,
                               type : [ 'line', 'bar', 'stack', 'tiled' ]
                           },
                           restore : {
                               show : true
                           },
                           saveAsImage : {
                               show : true
                           }
                       }
                   },
                   calculable : false,
                   grid : {
                       show : true,
                       borderWidth : '0'//边框宽度
                   },
                   xAxis : [
                       {
                           name : '日期(日)',
                           type : 'category',
                           splitLine : {//去除网格线
                               show : false
                           },
                           boundaryGap : false,
                           data : [ '2017-08-01', '2017-08-05', '2017-08-10', '2017-08-15', '2017-08-20', '2017-08-25', '2017-08-31' ]
                       }, ],
                   yAxis : [ {
                       name : '数量(个)',
                       type : 'value',
                       splitLine : {//去除网格线
                           show : false
                       },
                   } ],
                   series : [ {
                       name : '取消',
                       type : 'line',
                       smooth : true,
                       itemStyle : {
                           normal : {
                               areaStyle : {
                                   type : 'default'
                               }
                           }
                       },
                       data : [ 5, 2, 19, 3, 1, 7, 11 ]
                   },{
                       name : '订单',
                       type : 'line',
                       smooth : true,
                       itemStyle : {
                           normal : {
                               areaStyle : {
                                   type : 'default'
                               }
                           }
                       },
                       data : [ 80, 40, 121, 250, 100, 95, 18 ]
                   }]
               };

               // 为echarts对象加载数据
               myChart.setOption(option);
           });
       }

   </script>
</body>
</html>