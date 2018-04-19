<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>交易分析</title>
    <style>
        .table_css{
            width: 97%;
            margin-left: auto;
            margin-right: auto;
        }
        .th_css{
            width: 25%;
            text-align: center !important;
            background-color: #f2f2f2;
        }
        .td_css{
            width: 25%;
        }
        #main{
            width: 90%;
            height: 500px;
            margin-left: auto;
            margin-right: auto;
            margin-top: 50px;
        }
    </style>
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
</head>
<body>
<form id="searchForm" action="" method="post" class="layui-form">
    <div class="cl"></div>
    <table class="layui-table table_css">
        <tbody>
            <tr>
                <th class="th_css">上周成交量</th>
                <td class="td_css">176</td>
                <th class="th_css">上月成交量</th>
                <td class="td_css">704</td>
            </tr>
            <tr>
                <th class="th_css">本周成交量</th>
                <td class="td_css">175</td>
                <th class="th_css">本月成交量</th>
                <td class="td_css">700</td>
            </tr>
            <tr>
                <th class="th_css">本周预计成交量</th>
                <td class="td_css">200</td>
                <th class="th_css">本月预计成交量</th>
                <td class="td_css">800</td>
            </tr>
        </tbody>
    </table>
</form>
<div id="main"></div>
</body>
</html>