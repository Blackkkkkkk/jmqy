<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>

    <title>线路管理</title>
</head>
<body>
<section>
    <div class="container">
        <jsp:include page="/WEB-INF/layouts/left_menu.jsp"/>
        <main>
            <div class="position_all">
                <div class="position">您所在的位置：首页 -> 管理中心 -> 线路管理</div>
            </div>

            <div class="info">
                <form class="layui-form" action="">


                    <div class="button_right john-edge">

                        <div class="layui-inline">
                            <input type="text" id="test6" class="layui-input input_date" placeholder="选择开始时间">
                            <input type="text" id="tiime_john" class="layui-input input_date" placeholder="选择结束时间">
                            <input type="text" class="layui-input" placeholder="请输入车号">
                            <input type="text" class="layui-input" placeholder="请输入司机姓名">
                            <input type="text" class="layui-input" placeholder="请输入关键字" style="width: 130px;">
                            <a class="layui-btn layui-btn-danger layui-btn-small" href="">搜索</a>
                        </div>


                    </div>
                    <div class="cl"></div>

                    <div class="layui-form">
                        <table class="layui-table">

                            <thead>
                            <tr>

                                <th>订单号</th>
                                <th>路线</th>
                                <th>开始时间</th>
                                <th>结束时间</th>
                                <th>司机</th>
                                <th>车牌号</th>
                                <th>订单状态</th>
                                <th>线路偏差</th>
                                <th>审核状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td><a href="javascript:;" class="details_a">2017082612120000332</a></td>
                                <td>文雅苑-江门第一中学</td>
                                <td>2018-01-03 12:00:00</td>
                                <td>2018-01-03 12:00:00</td>
                                <td>张小小</td>
                                <td>粤A12345</td>
                                <td>完成</td>
                                <td>是</td>
                                <td>已审核</td>
                                <td><a href="${ctx}/transport/route/routeDetail?orderCode=DH-201801311737000_01" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-edit"></i> 查看</a></td>
                            </tr>
                            <tr>
                                <td><a href="javascript:;" class="details_a">DH-201801311737000_01</a></td>
                                <td>文雅苑-江门第一中学</td>
                                <td>2018-01-03 12:00:00</td>
                                <td>2018-01-03 12:00:00</td>
                                <td>张小小</td>
                                <td>粤A12345</td>
                                <td>完成</td>
                                <td>是</td>
                                <td>已审核</td>
                                <td><a href="line-1.html" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-edit"></i> 查看</a></td>
                            </tr>
                            <tr>
                                <td><a href="javascript:;" class="details_a">2017082612120000332</a></td>
                                <td>文雅苑-江门第一中学</td>
                                <td>2018-01-03 12:00:00</td>
                                <td>2018-01-03 12:00:00</td>
                                <td>张小小</td>
                                <td>粤A12345</td>
                                <td>完成</td>
                                <td>是</td>
                                <td>已审核</td>
                                <td><a href="line-1.html" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-edit"></i> 查看</a></td>
                            </tr>
                            <tr>
                                <td><a href="javascript:;" class="details_a">2017082612120000332</a></td>
                                <td>文雅苑-江门第一中学</td>
                                <td>2018-01-03 12:00:00</td>
                                <td>2018-01-03 12:00:00</td>
                                <td>张小小</td>
                                <td>粤A12345</td>
                                <td>完成</td>
                                <td>是</td>
                                <td>已审核</td>
                                <td><a href="line-1.html" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-edit"></i> 查看</a></td>
                            </tr>
                            <tr>
                                <td><a href="javascript:;" class="details_a">2017082612120000332</a></td>
                                <td>文雅苑-江门第一中学</td>
                                <td>2018-01-03 12:00:00</td>
                                <td>2018-01-03 12:00:00</td>
                                <td>张小小</td>
                                <td>粤A12345</td>
                                <td>完成</td>
                                <td>是</td>
                                <td>已审核</td>
                                <td><a href="line-1.html" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-edit"></i> 查看</a></td>
                            </tr>
                            <tr>
                                <td><a href="javascript:;" class="details_a">2017082612120000332</a></td>
                                <td>文雅苑-江门第一中学</td>
                                <td>2018-01-03 12:00:00</td>
                                <td>2018-01-03 12:00:00</td>
                                <td>张小小</td>
                                <td>粤A12345</td>
                                <td>完成</td>
                                <td>是</td>
                                <td>已审核</td>
                                <td><a href="line-1.html" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-edit"></i> 查看</a></td>
                            </tr>
                            <tr>
                                <td><a href="javascript:;" class="details_a">2017082612120000332</a></td>
                                <td>文雅苑-江门第一中学</td>
                                <td>2018-01-03 12:00:00</td>
                                <td>2018-01-03 12:00:00</td>
                                <td>张小小</td>
                                <td>粤A12345</td>
                                <td>完成</td>
                                <td>是</td>
                                <td>已审核</td>
                                <td><a href="line-1.html" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-edit"></i> 查看</a></td>
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

</body>
</html>
