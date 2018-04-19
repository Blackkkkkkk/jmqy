<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    <link rel="stylesheet" type="text/css" href="../images/weuix.min.css">
    <link rel="stylesheet" type="text/css" href="../images/weui2.css">
    <link rel="stylesheet" type="text/css" href="../images/weui3.css">
    <script src="../images/jquery-3.2.1.min.js"></script>
    <script src="../images/zepto.min.js"></script>
    <script src="../images/picker.js"></script>

    <link rel="stylesheet" href="../images/style.css">
    <style>
        body{
            background:#eeeeee !important;
        }
        .hist li{
            padding:20px;
            background:#fff;
            margin-top:0 !important;
            margin-bottom:10px !important;
        }
    </style>

    <title></title>
</head>
<body ontouchstart  class="page-bg">
<!--<ul class="prolist hist">
    <li>
        <img src="../images/pro.jpg" alt="">
        <div class="li_content">
            15座中巴<br/>
            <abbr>江门 - 开平</abbr>
        </div><span class="red">￥520 元</span>
        <div class="cl"></div>
    </li>
</ul>
-->
<div class="evatin">
    <div class="page-bd">
        <div class="weui-flex">
            <div class="weui-flex-item">
                <form action="${ctx}/order/evaluateList">
                    <div class="page-hd">
                        <div class="pf"><label>司机评分：</label><div id='fen' class="weui_cells_title">1分</div></div>
                        <div class="cl"></div>
                        <div  id="MarkOne" class="weui-rater">
                            <a  data-num = "0" class="weui-rater-box checked"> <span class="weui-rater-inner">★</span> </a>
                            <a  data-num = "1" class="weui-rater-box" > <span class="weui-rater-inner">★</span> </a>
                            <a  data-num = "2" class="weui-rater-box" > <span class="weui-rater-inner">★</span> </a>
                            <a  data-num = "3" class="weui-rater-box" > <span class="weui-rater-inner">★</span> </a>
                            <a  data-num = "4" class="weui-rater-box" > <span class="weui-rater-inner">★</span> </a>
                        </div>

                        <div class="borderBottom"></div>
                        <br/>
                        <textarea <c:if test="${not empty order.evaluateId}">readonly</c:if> id="textarea" class="weui_textarea" placeholder="您对司机的评价" rows="5">${evaluate.toDriverContent}</textarea>
                        <br/><br/>
                        <div class="pf"><label>车辆评分：</label><div id='fen1' class="weui_cells_title" style="float: left">1分</div></div>
                        <div class="cl"></div>
                        <div  id="MarkTwo" class="weui-rater">
                            <a data-num = "0" class="weui-rater-box checked"> <span class="weui-rater-inner">★</span> </a>
                            <a data-num = "1" class="weui-rater-box"> <span class="weui-rater-inner">★</span> </a>
                            <a data-num = "2" class="weui-rater-box"> <span class="weui-rater-inner">★</span> </a>
                            <a data-num = "3" class="weui-rater-box"> <span class="weui-rater-inner">★</span> </a>
                            <a data-num = "4" class="weui-rater-box"> <span class="weui-rater-inner">★</span> </a>
                        </div>

                        <div class="borderBottom"></div>
                        <br/>
                        <textarea  <c:if test="${not empty order.evaluateId}">readonly</c:if> id="textarea2" class="weui_textarea" placeholder="您对车辆的评价" rows="5">${evaluate.toCarContent}</textarea>
                        <br/><br/>
                        <input type="text" name="提交" value="提交" id="" class="weui_btn bg-orange" onclick="Pay();"></input>
                    </div>

                </form>

            </div>


        </div>

    </div>

</div>

</body>
</html>
<script src="../images/weui.john.js"></script>
<script src="../images/john.js"></script>

<script>

    function Pay(){
        if('${order.evaluateId}' != null &&'${order.evaluateId}' !='') {
           alert("查看评价页面不能修改！")
        }else{

        var toDriverScore = ($("#fen").html()).substring(0,1);
        var toCarScore    = ($("#fen1").html()).substring(0,1)
        var toDriverContent =  $("#textarea").val();
        var toCarContent =  $("#textarea2").val();

        var dataJson = {"orderCode": '${order.orderCode}', "toDriverScore": toDriverScore, "toDriverContent": toDriverContent,
            "toCarScore": toCarScore,"toCarContent":toCarContent};


        $.ajax({
            type:"post",
            url:"${ctx}/order/saveEvaluate",
            contentType : "application/x-www-form-urlencoded",
            dataType:'json',
            data:dataJson,
            success:function(data) {
                if(data.state){
                    alert("评价成功")
                    window.location.href='${ctx}/user/list1/3';
                }
            }



            })
        }
    }


    $(function () {
        if('${evaluate}' != null){
            var toDriverScore = '${evaluate.toDriverScore}';
            var toCarScore = '${evaluate.toCarScore}';



            $('#MarkOne').find('a').each(function(index){
                if(index<toDriverScore){
                    $(this).attr("class","weui-rater-box checked");
                    $(this).removeAttr("onclick")
                 //   $(this).attr("style","readonly:readonly;");
                }else{
                    $(this).attr("class","weui-rater-box");
                }
            });

            $('#MarkTwo').find('a').each(function(index){
                if(index<toCarScore){
                    $(this).attr("class","weui-rater-box checked");
                }else{
                    $(this).attr("class","weui-rater-box");
                }
            });

        /*    $('#MarkOne').find('a').each(function(index){
                if(5-index <= toDriverScore){
                    $(this).text('★');
                }else{
                    $(this).text('☆');
                }
            })
            $('#MarkTwo').find('span').each(function(index){
                if(5-index <= toCarScore){
                    $(this).text('★');
                }else{
                    $(this).text('☆');
                }
            })*/
        }
    })




</script>
