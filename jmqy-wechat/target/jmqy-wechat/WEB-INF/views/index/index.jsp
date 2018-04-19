<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <%--<link rel="stylesheet" type="text/css" href="${ctx}/images/weuix.min.css">--%>
    <script src="${ctx}/images/jquery-3.2.1.min.js"></script>
    <%--<script src="${ctx}/images/zepto.min.js"></script>--%>
    <link rel="stylesheet" href="${ctx}/images/style.css">
    <title></title>
</head>
<body ontouchstart  class="page-bg">
<img width="100%" src="${ctx}/images/banner.jpg">

<div class="john-row">
    <div class="col-md-5">

        <a href="search.html"><img class="pic_height" width="100%" src="../images/pic1.jpg" alt=""></a>

    </div>
    <div class="col-md-7">
        <div class="john-row layui-row-margin0">
            <!--

            旅游包车
            -->
            <div class="col-md-6"><a class="bga2dbf8 height50" href="">旅游包车</a></div>
            <div class="col-md-6"><a class="bga2dbf8 height50" href="">通勤包车</a></div>
            <div class="col-md-12"><a class="bga2dbf8 margintop5 height50" href="${ctx}/search/wechatapp">汽车租赁</a></div>
        </div>
    </div>
</div>
<div class="john-row">
    <div class="col-md-5">
        <a href=""><img width="100%" src="../images/pic2.jpg" alt=""></a>

    </div>
    <div class="col-md-7">
        <div class="col-md-6"><a class="fff2d7 height50" href="">线路调查</a></div>
        <div class="col-md-6"><a class="fff2d7 height50" href="">线路管理</a></div>
        <div class="col-md-6"><a class="fff2d7 margintop5 height50" href="">定制售票</a></div>
        <div class="col-md-6"><a class="fff2d7 margintop5 height50" href="">乘客评价</a></div>
    </div>
</div>
<div class="john-row">
    <div class="col-md-5">
        <a href=""><img width="100%" src="../images/pic3.jpg" alt=""></a>

    </div>
    <div class="col-md-7">
        <div class="col-md-12"><a class="f3e7d9 height50" href="">客运班车售票</a></div>
        <div class="col-md-12"><a class="f3e7d9 margintop5 height50" href="">江汽运售票</a></div>
    </div>
</div>
<div class="john-row">
    <div class="col-md-5">
        <a href=""><img width="100%" src="../images/pic4.jpg" alt=""></a>

    </div>
    <div class="col-md-7">
        <div class="col-md-12"><a class="e8f3fd height50" href="">网约车</a></div>
        <div class="col-md-12"><a class="e8f3fd margintop5 height50" href="">车站接送约车</a></div>
    </div>
</div>
<div class="john-row layui-row-margin0"">
    <div class="col-md-5">
        <div class="col-md-12"><a class="e4fff2 margintop5 height50" style="margin-left:0px;" href="">周边游</a></div>

    </div>
    <div class="col-md-7">
        <div class="col-md-12"><a class="e4fff2 margintop5  height50" href="">网上商城</a></div>

    </div>
</div>

<%

   //String value= (String)session.getAttribute("phone");
  //  System.out.println(usermessage.getPhone());
%>
</body>
</html>
<script src="${ctx}/images/layui.john.js"></script>
<script src="${ctx}/images/john.js"></script>
<script type="text/javascript">


    window.onload=function () {


        var iscookie=document.cookie.indexOf("firstVisit="); //得到分割的cookie名值
        if(iscookie==-1) { //判断cookie是否存在
            document.cookie ="firstVisit = 1";
            window.location.reload(); //跳转网页
        }

    }

</script>

