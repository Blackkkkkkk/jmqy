<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>个人中心</title>
</head>
<body>
<section>
    <div class="container">
    <jsp:include page="/WEB-INF/layouts/left_menu.jsp"/>
        <main>
            <div class="position_all">
                <div class="position">您所在的位置：首页 -> 个人中心 -> 发布订单</div>
            </div>
            <form action="">
                <div class="searh searh1">
                    <label>起始点：</label>
                    <div class="john_">
                        <p>
                            <input class="search_text" type="text">
                            <i class="icon-plus-sign icon-add"></i>
                        </p>

                        <div class="add_input">
                            <input placeholder="途经地点" class="search_text" type="text">
                            <i class="icon-minus-sign icon-move"></i>
                        </div>
                    </div>
                    <div class="div_input">
                        <label>目的地：</label><input class="search_text" type="text">
                    </div>
                    <div class="div_input">
                        <label>包车行程：</label><input name="ss" class="rad1" type="radio" checked=""> 单程 &nbsp;&nbsp;<input name="ss" class="rad2" type="radio"> 往返
                    </div>
                    <p>
                        <label>上车时间：</label><input class="layui-input search_text input_date" id="test5"  type="text">
                    </p>
                    <p class="shuang">
                        <label>返程时间：</label><input class="layui-input search_text input_date" id="test4"  type="text" >
                    </p>
                    <div class="div_input">
                        <label>包车天数：</label><input class="layui-input search_text" type="text">
                    </div>
                </div>
                <div class="searh searh2">
                    <div class="div_input div_dashed">
                        <label>人数：</label><input class="layui-input search_text" type="text">&nbsp;
                    </div>
                    <div class="div_input div_dashed">
                        <label>附加选项：</label>
                        <input type="checkbox"> 不限 &nbsp;&nbsp;&nbsp;<input type="checkbox"> 餐费 &nbsp;&nbsp;&nbsp;<input type="checkbox"> 住宿费 &nbsp;&nbsp;&nbsp;<input type="checkbox"> 高速路费 &nbsp;&nbsp;&nbsp;<input type="checkbox"> 保险 &nbsp;&nbsp;&nbsp;<input type="checkbox"> 水
                    </div>
                    <div class="div_input">
                        <label>包车类型：</label>
                        <input type="checkbox"> 不限 &nbsp;&nbsp;&nbsp;<input type="checkbox"> 旅游包车 &nbsp;&nbsp;&nbsp;<input type="checkbox"> 企业包车
                    </div>
                </div>
                <div class="searh searh2">
                    <div class="div_input">
                        <button class="layui-btn layui-btn-danger" style="float:right;margin-bottom:20px;">发布</button>
                        <div class="cl"></div>
                    </div>
                </div>
            </form>
        </main>
    </div>
</section>
</body>
</html>