<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>计费设置</title>
    <style>
        .title {
            border-bottom: 1px solid #0683b1;
            line-height: 35px;
            font-size: 18px;
            color: #0683b1;
            padding-left: 10px;
        }

        .coe-box {
            width: 855px;
            /*height:228px;*/
            border: 1px solid #ddd;
        }

        .coe-box th {
            background: #eee;
        }

        .coe-box tbody {
            /*height:100px;*/
            overflow-y: scroll;
        }

        .coe-table-content {
            height: 100px;
            overflow-y: scroll;
            display: block;
        }

        .coe-table-content td {
            border-left: 1px solid #ddd;
            border-bottom: 1px solid #ddd;
        }

        .coe-table-content td:nth-child(1) {
            border-left: none;
        }

        .coe-table-content tr:last-child td {
            /*border-bottom:none;*/

        }

        .coe-table-title th {
            position: relative;
        }

        .coe-box td, .coe-box th {
            text-align: center;
            padding: 5px 10px;
        }

        .coe-box1 {
            margin: 20px;
        }

        .coe-table-foot {
            background: #eee;
        }

        .coe-table-foot input {
            width: 100px;
        }

        .coe-table-content input {
            border: none;
            width: 75px;
        }

        .coe-box .layui-form-select input {
            width: 120px;
            height: 22px;
        }

        .coe-box .layui-form-select {
            width: 120px;
        }
    </style>
</head>
<body>
<input type="hidden" id="id" value="${rule.id}"/>
<a href="javascript:back();" class="layui-btn layui-btn-mini layui-btn-normal" style="float:right;margin-right: 40px;">
    返 回 </a>
<div class="title">[${rule.ruleValue}]按公里数计费</div>
<table class="coe-box1">
    <tr style="line-height: 45px">
        <c:forEach items="${rules}" var="r" varStatus="vs">
            <c:if test="${r.ruleType==14}">
                <td valign="top"><b style="margin-left: 30px;">包车天数系数：</b></td>
                <td><input id="${r.id}" value="${r.coefficient}" type="text"/><a
                        href="javascript:updatePrice('${r.id}');" class="layui-btn layui-btn-mini"
                        style="margin-left: 10px;">确认修改</a></td>
            </c:if>
        </c:forEach>
    </tr>
</table>
<table class="layui-form coe-box1">
    <tr>
        <td valign="top" align="right"><b>车辆类型系数(座)：</b></td>
        <td>
            <div class="coe-box">
                <table class="coe-table coe-table-title">
                    <colgroup>
                        <col width="200">
                        <col width="50">
                        <col width="200">
                        <col width="300">
                        <col width="300">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>客座数</th>
                        <th>—</th>
                        <th>客座数</th>
                        <th>系数</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <table class="coe-table coe-table-content">
                    <colgroup>
                        <col width="200">
                        <col width="50">
                        <col width="200">
                        <col width="300">
                        <col width="300">
                    </colgroup>
                    <tbody>
                    <c:forEach items="${rules}" var="r" varStatus="vs">
                        <c:if test="${r.ruleType==3}">
                            <tr id="${r.id}">
                                <td><input id="defaultRange_${r.id}" type="text" class="coe-input"
                                           value="${r.defaultRange}"></td>
                                <td>—</td>
                                <td><input id="toRange_${r.id}" type="text" class="coe-input" value="${r.toRange}"></td>
                                <td><input id="coefficient_${r.id}" type="text" class="coe-input"
                                           value="${r.coefficient}"></td>
                                <td>
                                    <a href="javascript:del('${r.id}');" class="layui-btn layui-btn-mini">删除</a>
                                    <a href="javascript:update('${r.id}');" class="layui-btn layui-btn-mini">确认修改</a>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
                <table class="coe-table coe-table-foot">
                    <colgroup>
                        <col width="170">
                        <col width="10">
                        <col width="180">
                        <col width="220">
                        <col width="300">
                    </colgroup>
                    <tbody>
                    <tr>
                        <td><input class="input1" type="text"></td>
                        <td>—</td>
                        <td><input class="input2" type="text"></td>
                        <td><input class="input3" type="text"></td>
                        <td>
                            <a href="javascript:;" class="layui-btn layui-btn-mini layui-btn-danger"
                               onclick="scrollBottom(this,3)">添加</a>
                            <!-- <a href="" class="layui-btn layui-btn-mini layui-btn-danger">批量确认</a> -->
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

        </td>
    </tr>
    <tr style="height: 10px;"></tr>
    <tr>
        <td valign="top" align="right"><b>车辆新旧系数(年)：</b></td>
        <td>
            <div class="coe-box">
                <table class="coe-table coe-table-title">
                    <colgroup>
                        <col width="200">
                        <col width="50">
                        <col width="200">
                        <col width="300">
                        <col width="300">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>年限</th>
                        <th>—</th>
                        <th>年限</th>
                        <th>系数</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <table class="coe-table coe-table-content">
                    <colgroup>
                        <col width="200">
                        <col width="50">
                        <col width="200">
                        <col width="300">
                        <col width="300">
                    </colgroup>
                    <tbody>
                    <c:forEach items="${rules}" var="r" varStatus="vs">
                        <c:if test="${r.ruleType==4}">
                            <tr id="${r.id}">
                                <td><input id="defaultRange_${r.id}" type="text" class="coe-input"
                                           value="${r.defaultRange}"></td>
                                <td>—</td>
                                <td><input id="toRange_${r.id}" type="text" class="coe-input" value="${r.toRange}"></td>
                                <td><input id="coefficient_${r.id}" type="text" class="coe-input"
                                           value="${r.coefficient}"></td>
                                <td>
                                    <a href="javascript:del('${r.id}');" class="layui-btn layui-btn-mini">删除</a>
                                    <a href="javascript:update('${r.id}');" class="layui-btn layui-btn-mini">确认修改</a>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
                <table class="coe-table coe-table-foot">
                    <colgroup>
                        <col width="170">
                        <col width="10">
                        <col width="180">
                        <col width="220">
                        <col width="300">
                    </colgroup>
                    <tbody>
                    <tr>
                        <td><input class="input1" type="text"></td>
                        <td>—</td>
                        <td><input class="input2" type="text"></td>
                        <td><input class="input3" type="text"></td>
                        <td>
                            <a href="javascript:;" class="layui-btn layui-btn-mini layui-btn-danger"
                               onclick="scrollBottom(this,4)">添加</a>
                            <!-- <a href="" class="layui-btn layui-btn-mini layui-btn-danger">批量确认</a> -->
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

        </td>
    </tr>
    <tr style="height: 10px;"></tr>
    <tr>
        <td valign="top" align="right"><b>包车时长系数(时)：</b></td>
        <td>
            <div class="coe-box">
                <table class="coe-table coe-table-title">
                    <colgroup>
                        <col width="200">
                        <col width="50">
                        <col width="200">
                        <col width="300">
                        <col width="300">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>小时</th>
                        <th>—</th>
                        <th>小时</th>
                        <th>系数</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <table class="coe-table coe-table-content">
                    <colgroup>
                        <col width="200">
                        <col width="50">
                        <col width="200">
                        <col width="300">
                        <col width="300">
                    </colgroup>
                    <tbody>
                    <c:forEach items="${rules}" var="r" varStatus="vs">
                        <c:if test="${r.ruleType==5}">
                            <tr id="${r.id}">
                                <td><input id="defaultRange_${r.id}" type="text" class="coe-input"
                                           value="${r.defaultRange}"></td>
                                <td>—</td>
                                <td><input id="toRange_${r.id}" type="text" class="coe-input" value="${r.toRange}"></td>
                                <td><input id="coefficient_${r.id}" type="text" class="coe-input"
                                           value="${r.coefficient}"></td>
                                <td>
                                    <a href="javascript:del('${r.id}');" class="layui-btn layui-btn-mini">删除</a>
                                    <a href="javascript:update('${r.id}');" class="layui-btn layui-btn-mini">确认修改</a>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
                <table class="coe-table coe-table-foot">
                    <colgroup>
                        <col width="170">
                        <col width="10">
                        <col width="180">
                        <col width="220">
                        <col width="300">
                    </colgroup>
                    <tbody>
                    <tr>
                        <td><input class="input1" type="text"></td>
                        <td>—</td>
                        <td><input class="input2" type="text"></td>
                        <td><input class="input3" type="text"></td>
                        <td>
                            <a href="javascript:;" class="layui-btn layui-btn-mini layui-btn-danger"
                               onclick="scrollBottom(this,5)">添加</a>
                            <!-- <a href="" class="layui-btn layui-btn-mini layui-btn-danger">批量确认</a> -->
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

        </td>
    </tr>

    <tr style="height: 10px;"></tr>
    <tr>
        <td valign="top" align="right"><b>里程单价系数(公里)：</b></td>
        <td>
            <div class="coe-box">
                <table class="coe-table coe-table-title">
                    <colgroup>
                        <col width="200">
                        <col width="50">
                        <col width="200">
                        <col width="300">
                        <col width="300">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>公里</th>
                        <th>—</th>
                        <th>公里</th>
                        <th>单价</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <table class="coe-table coe-table-content">
                    <colgroup>
                        <col width="200">
                        <col width="50">
                        <col width="200">
                        <col width="300">
                        <col width="300">
                    </colgroup>
                    <tbody>
                    <c:forEach items="${rules}" var="r" varStatus="vs">
                        <c:if test="${r.ruleType==2}">
                            <tr id="${r.id}">
                                <td><input id="defaultRange_${r.id}" type="text" class="coe-input"
                                           value="${r.defaultRange}"></td>
                                <td>—</td>
                                <td><input id="toRange_${r.id}" type="text" class="coe-input" value="${r.toRange}"></td>
                                <td><input id="coefficient_${r.id}" type="text" class="coe-input"
                                           value="${r.coefficient}"></td>
                                <td>
                                    <a href="javascript:del('${r.id}');" class="layui-btn layui-btn-mini">删除</a>
                                    <a href="javascript:update('${r.id}');" class="layui-btn layui-btn-mini">确认修改</a>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
                <table class="coe-table coe-table-foot">
                    <colgroup>
                        <col width="170">
                        <col width="10">
                        <col width="180">
                        <col width="220">
                        <col width="300">
                    </colgroup>
                    <tbody>
                    <tr>
                        <td><input class="input1" type="text"></td>
                        <td>—</td>
                        <td><input class="input2" type="text"></td>
                        <td><input class="input3" type="text"></td>
                        <td>
                            <a href="javascript:;" class="layui-btn layui-btn-mini layui-btn-danger"
                               onclick="scrollBottom(this,2)">添加</a>
                            <!-- <a href="" class="layui-btn layui-btn-mini layui-btn-danger">批量确认</a> -->
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

        </td>
    </tr>

    <!-- 途经里程额单价-->

    <tr style="height: 10px;"></tr>
    <tr>
        <td valign="top" align="right"><b>途经里程额单价系数(公里)：</b></td>
        <td>
            <div class="coe-box">
                <table class="coe-table coe-table-title">
                    <colgroup>
                        <col width="200">
                        <col width="50">
                        <col width="200">
                        <col width="300">
                        <col width="300">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>公里</th>
                        <th>—</th>
                        <th>公里</th>
                        <th>单价</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <table class="coe-table coe-table-content">
                    <colgroup>
                        <col width="200">
                        <col width="50">
                        <col width="200">
                        <col width="300">
                        <col width="300">
                    </colgroup>
                    <tbody>
                    <c:forEach items="${rules}" var="r" varStatus="vs">
                        <c:if test="${r.ruleType==15}">
                            <tr id="${r.id}">
                                <td><input id="defaultRange_${r.id}" type="text" class="coe-input"
                                           value="${r.defaultRange}"></td>
                                <td>—</td>
                                <td><input id="toRange_${r.id}" type="text" class="coe-input" value="${r.toRange}"></td>
                                <td><input id="coefficient_${r.id}" type="text" class="coe-input"
                                           value="${r.coefficient}"></td>
                                <td>
                                    <a href="javascript:del('${r.id}');" class="layui-btn layui-btn-mini">删除</a>
                                    <a href="javascript:update('${r.id}');" class="layui-btn layui-btn-mini">确认修改</a>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
                <table class="coe-table coe-table-foot">
                    <colgroup>
                        <col width="170">
                        <col width="10">
                        <col width="180">
                        <col width="220">
                        <col width="300">
                    </colgroup>
                    <tbody>
                    <tr>
                        <td><input class="input1" type="text"></td>
                        <td>—</td>
                        <td><input class="input2" type="text"></td>
                        <td><input class="input3" type="text"></td>
                        <td>
                            <a href="javascript:;" class="layui-btn layui-btn-mini layui-btn-danger"
                               onclick="scrollBottom(this,15)">添加</a>
                            <!-- <a href="" class="layui-btn layui-btn-mini layui-btn-danger">批量确认</a> -->
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

        </td>
    </tr>

    <tr style="height: 10px;"></tr>
    <tr>
        <td valign="top" align="right"><b>车辆供求系数(%)：</b></td>
        <td>
            <div class="coe-box">
                <table class="coe-table coe-table-title">
                    <colgroup>
                        <col width="200">
                        <col width="50">
                        <col width="200">
                        <col width="300">
                        <col width="300">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>供求</th>
                        <th>—</th>
                        <th>供求</th>
                        <th>系数</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <table class="coe-table coe-table-content">
                    <colgroup>
                        <col width="200">
                        <col width="50">
                        <col width="200">
                        <col width="300">
                        <col width="300">
                    </colgroup>
                    <tbody>
                    <c:forEach items="${rules}" var="r" varStatus="vs">
                        <c:if test="${r.ruleType==6}">
                            <tr id="${r.id}">
                                <td><input id="defaultRange_${r.id}" type="text" class="coe-input"
                                           value="${r.defaultRange}"></td>
                                <td>—</td>
                                <td><input id="toRange_${r.id}" type="text" class="coe-input" value="${r.toRange}"></td>
                                <td><input id="coefficient_${r.id}" type="text" class="coe-input"
                                           value="${r.coefficient}"></td>
                                <td>
                                    <a href="javascript:del('${r.id}');" class="layui-btn layui-btn-mini">删除</a>
                                    <a href="javascript:update('${r.id}');" class="layui-btn layui-btn-mini">确认修改</a>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
                <table class="coe-table coe-table-foot">
                    <colgroup>
                        <col width="170">
                        <col width="10">
                        <col width="180">
                        <col width="220">
                        <col width="300">
                    </colgroup>
                    <tbody>
                    <tr>
                        <td><input class="input1" type="text"></td>
                        <td>—</td>
                        <td><input class="input2" type="text"></td>
                        <td><input class="input3" type="text"></td>
                        <td>
                            <a href="javascript:;" class="layui-btn layui-btn-mini layui-btn-danger"
                               onclick="scrollBottom(this,6)">添加</a>
                            <!-- <a href="" class="layui-btn layui-btn-mini layui-btn-danger">批量确认</a> -->
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

        </td>
    </tr>


    <tr style="height: 10px;"></tr>
    <tr>
        <td valign="top" align="right"><b>目的地系数值：</b></td>
        <td>
            <div class="coe-box">
                <table class="coe-table coe-table-title">
                    <colgroup>
                        <col width="300">
                        <col width="150">
                        <col width="300">

                        <col width="300">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>地区</th>
                        <th>—</th>
                        <th>系数值</th>

                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <table class="coe-table coe-table-content">
                    <colgroup>
                        <col width="300">
                        <col width="150">
                        <col width="300">

                        <col width="300">
                    </colgroup>
                    <tbody>
                    <c:forEach items="${rules}" var="r" varStatus="vs">
                        <c:if test="${r.ruleType==10}">
                            <tr id="${r.id}">
                                <td><input id="endplance_${r.id}" type="text" class="coe-input" value="${r.endPlance}">
                                </td>
                                <td>—</td>
                                <td><input id="coefficient_${r.id}" type="text" class="coe-input"
                                           value="${r.coefficient}"></td>
                                <td>
                                    <a href="javascript:del('${r.id}');" class="layui-btn layui-btn-mini">删除</a>
                                    <a href="javascript:update('${r.id}');" class="layui-btn layui-btn-mini">确认修改</a>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
                <table class="coe-table coe-table-foot">
                    <div class="layui-form">
                        <colgroup>
                            <col width="200">
                            <col width="10">
                            <col width="180">
                            <col width="10">
                            <col width="220">
                            <col width="300">
                            <col width="300">
                        </colgroup>
                        <tbody>
                        <tr>
                            <td>
                                <select name="quiz1" lay-filter="quiz1_select" style="width: 100px;">
                                    <option value="">请选择省</option>
                                </select>
                            </td>
                            <td>—</td>
                            <td>
                                <select name="quiz2" lay-filter="quiz2_select" style="width: 100px;">
                                    <option value="">请选择市</option>
                                </select>
                            </td>
                            <td>—</td>
                            <td>
                                <select name="quiz3" class="input12">
                                    <option value="">请选择县/区</option>
                                </select>
                            </td>
                            <td>系数值： <input class="input3" type="text"></td>
                            <td>
                                <a href="javascript:;" class="layui-btn layui-btn-mini layui-btn-danger"
                                   onclick="scrollBottom(this,10)">添加</a>
                                <!-- <a href="" class="layui-btn layui-btn-mini layui-btn-danger">批量确认</a> -->
                            </td>
                        </tr>
                        </tbody>
                    </div>
                </table>
            </div>

        </td>
    </tr>
</table>

<div class="title">[${rule.ruleValue}]按天数计费</div>
<table class="coe-box1">
    <tr>
        <td valign="top" align="right"><b>客座数系数(元/天)：</b></td>
        <td>
            <div class="coe-box">
                <table class="coe-table coe-table-title">
                    <colgroup>
                        <col width="200">
                        <col width="50">
                        <col width="200">
                        <col width="300">
                        <col width="300">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>客座数</th>
                        <th>—</th>
                        <th>客座数</th>
                        <th>系数</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <table class="coe-table coe-table-content">
                    <colgroup>
                        <col width="200">
                        <col width="50">
                        <col width="200">
                        <col width="300">
                        <col width="300">
                    </colgroup>
                    <tbody>
                    <c:forEach items="${rules}" var="r" varStatus="vs">
                        <c:if test="${r.ruleType==8}">
                            <tr id="${r.id}">
                                <td><input id="defaultRange_${r.id}" type="text" class="coe-input"
                                           value="${r.defaultRange}"></td>
                                <td>—</td>
                                <td><input id="toRange_${r.id}" type="text" class="coe-input" value="${r.toRange}"></td>
                                <td><input id="coefficient_${r.id}" type="text" class="coe-input"
                                           value="${r.coefficient}"></td>
                                <td>
                                    <a href="javascript:del('${r.id}');" class="layui-btn layui-btn-mini">删除</a>
                                    <a href="javascript:update('${r.id}');" class="layui-btn layui-btn-mini">确认修改</a>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
                <table class="coe-table coe-table-foot">
                    <colgroup>
                        <col width="170">
                        <col width="10">
                        <col width="180">
                        <col width="220">
                        <col width="300">
                    </colgroup>
                    <tbody>
                    <tr>
                        <td><input class="input1" type="text"></td>
                        <td>—</td>
                        <td><input class="input2" type="text"></td>
                        <td><input class="input3" type="text"></td>
                        <td>
                            <a href="javascript:;" class="layui-btn layui-btn-mini layui-btn-danger"
                               onclick="scrollBottom(this,8)">添加</a>
                            <!-- <a href="" class="layui-btn layui-btn-mini layui-btn-danger">批量确认</a> -->
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </td>
    </tr>
</table>
</form>
<script>


    layui.use(['form'], function () {
        var form = layui.form;

        //初始化
        init_province(form);
        //下拉
        form.on('select(quiz1_select)', function (data) {

            init_city(form, data.value, 'quiz2');
            init_area(form, data.value, 'quiz3');
        })
        form.on('select(quiz2_select)', function (data) {
            init_area(form, data.value, 'quiz3');
        })

    });

    function init_province(form) {
        $.ajax({
            type: "post",
            url: "${ctx}/area/province",
            dataType: "json",
            success: function (data) {
                if (data.state) {
                    var provinceList = eval(data.provinceList);
                    if (provinceList != null && provinceList.length > 0) {
                        var html = '<option value="">请选择省</option>';
                        for (var i = 0; i < provinceList.length; i++) {
                            var province = provinceList[i];
                            var id = province.id;
                            var name = province.name;
                            html += '<option value="' + id + '">' + name + '</option>';
                        }
                        $("[name='quiz1']").html(html);
                        $("[name='quiz4']").html(html);
                        form.render('select');
                    }
                } else {
                    var html = '<option value="">请选择省</option>';
                    $("[name='quiz1']").html(html);
                    $("[name='quiz4']").html(html);
                    form.render('select');
                }
            }
        });
    }

    function init_city(form, id, obj) {
        $.ajax({
            type: "post",
            url: "${ctx}/area/city",
            dataType: "json",
            data: {"parentId": id},
            success: function (data) {
                if (data.state) {
                    var cityList = eval(data.cityList);
                    if (cityList != null && cityList.length > 0) {
                        var html = '<option value="">请选择市</option>';
                        for (var i = 0; i < cityList.length; i++) {
                            var city = cityList[i];
                            var id = city.cityId;
                            var name = city.cityName;
                            html += '<option value="' + id + '">' + name + '</option>';
                        }
                        $("[name='" + obj + "']").html(html);
                        form.render('select');
                    }
                } else {
                    var html = '<option value="">请选择市</option>';
                    $("[name='" + obj + "']").html(html);
                    form.render('select');
                }
            }
        });
    }

    function init_area(form, id, obj) {
        $.ajax({
            type: "post",
            url: "${ctx}/area/area",
            dataType: "json",
            data: {"parentId": id},
            success: function (data) {
                if (data.state) {
                    var areaList = eval(data.areaList);
                    if (areaList != null && areaList.length > 0) {
                        var html = '<option value="">请选择县/区</option>';
                        for (var i = 0; i < areaList.length; i++) {
                            var area = areaList[i];
                            var id = area.cityId;
                            //  var id = area.areaName
                            var name = area.areaName;
                            html += '<option value="' + name + '">' + name + '</option>';
                        }
                        $("[name='" + obj + "']").html(html);
                        form.render('select');
                    }
                } else {
                    var html = '<option value="">请选择市</option>';
                    $("[name='" + obj + "']").html(html);
                    form.render('select');
                }
            }
        });
    }


    function updatePrice(id) {
        var coefficient = $('#' + id).val();
        if (coefficient == null || coefficient == '') {
            $('#' + id).focus();
            layer.msg('数值不能为空', {icon: 2, time: 1000}, function () {
            });
            return false;
        }
        $.ajax({
            type: "post",
            url: "${ctx}/rule/doSetRule",
            dataType: "json",
            data: {"id": id, "coefficient": coefficient},
            success: function (data) {
                if (data.state) {
                    layer.msg('设置成功', {icon: 1, time: 1000}, function () {
                    });
                } else {
                    layer.msg('设置失败', {icon: 2, time: 1000}, function () {
                    });
                }
            }
        });
    }

    function update(id) {
        var defaultRange = $('#defaultRange_' + id).val();
        var toRange = $('#toRange_' + id).val();
        var coefficient = $('#coefficient_' + id).val();
        if (defaultRange == '') {
            $('#defaultRange_' + id).focus();
            layer.msg('最小值不能为空', {icon: 2, time: 1000}, function () {
            });
            return false;
        }
        if (toRange == '') {
            $('#toRange_' + id).focus();
            layer.msg('最大值不能为空', {icon: 2, time: 1000}, function () {
            });
            return false;
        }
        if (coefficient == '') {
            $('#coefficient_' + id).focus();
            layer.msg('系数不能为空', {icon: 2, time: 1000}, function () {
            });
            return false;
        }
        $.ajax({
            type: "post",
            url: "${ctx}/rule/doSetRule",
            dataType: "json",
            data: {"id": id, "defaultRange": defaultRange, "toRange": toRange, "coefficient": coefficient},
            success: function (data) {
                if (data.state) {
                    layer.msg('设置成功', {icon: 1, time: 1000}, function () {
                    });
                } else {
                    layer.msg('设置失败', {icon: 2, time: 1000}, function () {
                    });
                }
            }
        });
    }

    function del(id) {
        $.ajax({
            type: "post",
            url: "${ctx}/rule/delete",
            dataType: "json",
            data: {"id": id},
            success: function (data) {
                if (data.state) {
                    layer.msg('删除成功', {icon: 1, time: 1000}, function () {
                        $('#' + id).remove();
                    });
                } else {
                    layer.msg('删除失败', {icon: 2, time: 1000}, function () {
                    });
                }
            }
        });
    }

    function scrollBottom(elem, type) {
        if (type == '10') {
            var dom = $(elem).parents('.coe-table-foot').prev('.layui-form').prev('.coe-table-content');
        } else {
            var dom = $(elem).parents('.coe-table-foot').prev('.coe-table-content');
        }
        console.log(dom)
        var cloneDom = $(elem).parent().parent();
        var s1 = cloneDom.find('.input1').val(),
            s2 = cloneDom.find('.input2').val(),
            s3 = cloneDom.find('.input3').val();
        var s4 = $("[name='quiz3']").val();
        if (s1 == '') {
            cloneDom.find('.input1').focus();
            layer.msg('最小值不能为空', {icon: 2, time: 1000}, function () {
            });
            return false;
        }
        if (s2 == '') {
            cloneDom.find('.input2').focus();
            layer.msg('最大值不能为空', {icon: 2, time: 1000}, function () {
            });
            return false;
        }
        if (s3 == '') {
            cloneDom.find('.input3').focus();
            layer.msg('系数不能为空', {icon: 2, time: 1000}, function () {
            });
            return false;
        }
        if (s4 == '') {

            // cloneDom.find('.input12').focus();
            if (type == '10') {
                layer.msg('地点不能为空不能为空', {icon: 2, time: 1000}, function () {
                });
                return false;
            }


        }
        if (s1 != '' && s2 != '' && s3 != '') {


            $.ajax({
                type: "post",
                url: "${ctx}/rule/save",
                dataType: "json",
                data: {
                    "parentId": $('#id').val(),
                    "defaultRange": s1,
                    "toRange": s2,
                    "coefficient": s3,
                    "ruleType": type,
                    "endPlance": s4
                },
                success: function (data) {

                    if (data.state) {
                        if (type == '10') {

                            var rule = data.rule;

                            var string = '<tr><td><input id="endplance_' + rule.id + '" type="text" class="coe-input" value="' + s4 + '"></td><td>—</td>'
                                + '<td><input id="coefficient_' + rule.id + '" type="text" class="coe-input" value="' + s3 + '"></td>'
                                + '<td><a href="javascript:del(\'' + rule.id + '\');" class="layui-btn layui-btn-mini">删除</a>'
                                + '<a href="javascript:update(\'' + rule.id + '\');" class="layui-btn layui-btn-mini">确认修改</a></td></tr>';
                            dom.find('tbody').after(string)
                            dom.scrollTop($('.coe-table-content')[0].scrollHeight);
                            layer.msg('添加成功', {icon: 1, time: 1000}, function () {
                                $("[name='quiz3']").val('');
                                cloneDom.find('.input3').val('');
                            });

                        } else {
                            var rule = data.rule;
                            var string = '<tr><td><input id="defaultRange_' + rule.id + '" type="text" class="coe-input" value="' + s1 + '"></td><td>—</td>'
                                + '<td><input id="toRange_' + rule.id + '" type="text" class="coe-input" value="' + s2 + '"></td>'
                                + '<td><input id="coefficient_' + rule.id + '" type="text" class="coe-input" value="' + s3 + '"></td>'
                                + '<td><a href="javascript:del(\'' + rule.id + '\');" class="layui-btn layui-btn-mini">删除</a>'
                                + '<a href="javascript:update(\'' + rule.id + '\');" class="layui-btn layui-btn-mini">确认修改</a></td></tr>';
                            dom.find('tbody').after(string)
                            dom.scrollTop($('.coe-table-content')[0].scrollHeight);
                            layer.msg('添加成功', {icon: 1, time: 1000}, function () {
                                cloneDom.find('.input1').val(''),
                                    cloneDom.find('.input2').val(''),
                                    cloneDom.find('.input3').val('');
                            });
                        }
                    } else {
                        layer.msg('添加失败', {icon: 2, time: 1000}, function () {
                        });
                    }
                }
            });
        }
    }


    function back() {
        window.location.href = '${ctx}/rule/ruleList';
    }
</script>
</body>
</html>