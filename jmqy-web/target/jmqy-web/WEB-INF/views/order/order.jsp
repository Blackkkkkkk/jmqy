<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>历史订单</title>
</head>
<body>
<section>
    <div class="container">
    <jsp:include page="/WEB-INF/layouts/left_menu.jsp"/>
        <main>
            <div class="position_all">
                <div class="position">您所在的位置：首页 -> 个人中心 -> 历史订单</div>
            </div>
            <div class="position_all search_hosi">
                <form class="layui-form" action="">
                    <label>出发日期：</label><input class="layui-input search_text input_date" id="test1"  type="text"> <span>—</span> <input class="layui-input search_text input_date" id="test2"  type="text">
                    <label>订单状态：</label>
                    <div class="layui-inlilne">
                        <select name="modules" lay-verify="required" lay-search="">
                            <option value="请选择"></option>
                            <option value="0">全部</option>
                            <option value="1">未完成</option>
                            <option value="2">已完成</option>
                        </select>
                    </div>
                    <button class="layui-btn layui-btn-primary layui-btn-small">查询</button>
                </form>
            </div>
            <div class="info info-padding0">
                <table class="layui-table" lay-even="" lay-skin="nob">
                    <colgroup>
                        <col width="250">
                        <col width="250">
                        <col width="150">
                        <col width="150">
                        <col>
                    </colgroup>
                    <thead>
                    <tr>
                        <th colspan="5">
                            订单号：<span>20170830120000332</span>
                            <a href="">删除订单</a>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>江门 — 开平</td>
                        <td>2017-08-30 12:00:00</td>
                        <td><span class="orange"><b>￥300</b></span></td>
                        <td>已完成
                        </td>
                        <td><a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini details_a">订单详情</a><a href="javascript:;" class="layui-btn layui-btn-normal layui-btn-mini history_a">重新发布</a><a href="javascript:;" class="layui-btn layui-btn-danger layui-btn-mini complaint_a">投诉</a></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="info info-padding0">
                <table class="layui-table" lay-even="" lay-skin="nob">
                    <colgroup>
                        <col width="250">
                        <col width="250">
                        <col width="150">
                        <col width="150">
                        <col>
                    </colgroup>
                    <thead>
                    <tr>
                        <th colspan="5">
                            订单号：<span>20170830120000332</span>
                            <a href="">删除订单</a>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>江门 — 开平</td>
                        <td>2017-08-30 12:00:00</td>
                        <td><span class="orange"><b>￥300</b></span></td>
                        <td>已完成
                        </td>
                        <td><a href="" class="layui-btn layui-btn-primary layui-btn-mini">订单详情</a><a href="" class="layui-btn layui-btn-normal layui-btn-mini">重新发布</a><a href="" class="layui-btn layui-btn-danger layui-btn-mini">投诉</a></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="info info-padding0">
                <table class="layui-table" lay-even="" lay-skin="nob">
                    <colgroup>
                        <col width="250">
                        <col width="250">
                        <col width="150">
                        <col width="150">
                        <col>
                    </colgroup>
                    <thead>
                    <tr>
                        <th colspan="5">
                            订单号：<span>20170830120000332</span>
                            <a href="">删除订单</a>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>江门 — 开平</td>
                        <td>2017-08-30 12:00:00</td>
                        <td><span class="orange"><b>￥300</b></span></td>
                        <td>已完成
                        </td>
                        <td><a href="" class="layui-btn layui-btn-primary layui-btn-mini">订单详情</a><a href="" class="layui-btn layui-btn-normal layui-btn-mini">重新发布</a><a href="" class="layui-btn layui-btn-danger layui-btn-mini">投诉</a></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="info info-padding0">
                <table class="layui-table" lay-even="" lay-skin="nob">
                    <colgroup>
                        <col width="250">
                        <col width="250">
                        <col width="150">
                        <col width="150">
                        <col>
                    </colgroup>
                    <thead>
                    <tr>
                        <th colspan="5">
                            订单号：<span>20170830120000332</span>
                            <a href="">删除订单</a>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>江门 — 开平</td>
                        <td>2017-08-30 12:00:00</td>
                        <td><span class="orange"><b>￥300</b></span></td>
                        <td>已完成
                        </td>
                        <td><a href="" class="layui-btn layui-btn-primary layui-btn-mini">订单详情</a><a href="" class="layui-btn layui-btn-normal layui-btn-mini">重新发布</a><a href="" class="layui-btn layui-btn-danger layui-btn-mini">投诉</a></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="info info-padding0">
                <table class="layui-table" lay-even="" lay-skin="nob">
                    <colgroup>
                        <col width="250">
                        <col width="250">
                        <col width="150">
                        <col width="150">
                        <col>
                    </colgroup>
                    <thead>
                    <tr>
                        <th colspan="5">
                            订单号：<span>20170830120000332</span>
                            <a href="">删除订单</a>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>江门 — 开平</td>
                        <td>2017-08-30 12:00:00</td>
                        <td><span class="orange"><b>￥300</b></span></td>
                        <td>已完成
                        </td>
                        <td><a href="" class="layui-btn layui-btn-primary layui-btn-mini">订单详情</a><a href="" class="layui-btn layui-btn-normal layui-btn-mini">重新发布</a><a href="" class="layui-btn layui-btn-danger layui-btn-mini">投诉</a></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="info info-padding0">
                <table class="layui-table" lay-even="" lay-skin="nob">
                    <colgroup>
                        <col width="250">
                        <col width="250">
                        <col width="150">
                        <col width="150">
                        <col>
                    </colgroup>
                    <thead>
                    <tr>
                        <th colspan="5">
                            订单号：<span>20170830120000332</span>
                            <a href="">删除订单</a>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>江门 — 开平</td>
                        <td>2017-08-30 12:00:00</td>
                        <td><span class="orange"><b>￥300</b></span></td>
                        <td>已完成
                        </td>
                        <td><a href="" class="layui-btn layui-btn-primary layui-btn-mini">订单详情</a><a href="" class="layui-btn layui-btn-normal layui-btn-mini">重新发布</a><a href="" class="layui-btn layui-btn-danger layui-btn-mini">投诉</a></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="info info-padding0">
                <table class="layui-table" lay-even="" lay-skin="nob">
                    <colgroup>
                        <col width="250">
                        <col width="250">
                        <col width="150">
                        <col width="150">
                        <col>
                    </colgroup>
                    <thead>
                    <tr>
                        <th colspan="5">
                            订单号：<span>20170830120000332</span>
                            <a href="">删除订单</a>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>江门 — 开平</td>
                        <td>2017-08-30 12:00:00</td>
                        <td><span class="orange"><b>￥300</b></span></td>
                        <td>已完成
                        </td>
                        <td><a href="" class="layui-btn layui-btn-primary layui-btn-mini">订单详情</a><a href="" class="layui-btn layui-btn-normal layui-btn-mini">重新发布</a><a href="" class="layui-btn layui-btn-danger layui-btn-mini">投诉</a></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div id="demo7"></div>
        </main>
    </div>
</section>
</body>
</html>