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
    <link rel="stylesheet" type="text/css" href="${ctx}/images/weuix.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/images/weui2.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/images/weui3.css">
    <script src="${ctx}/images/jquery-3.2.1.min.js"></script>
    <script src="${ctx}/images/zepto.min.js"></script>
    <script src="${ctx}/images/picker.js"></script>
    <link rel="stylesheet" href="${ctx}/images/style.css">
    <title></title>


</head>


<body ontouchstart  class="page-bg">
<input type="hidden" id="codeEncoder" name="codeEncoder"/>

<input type="hidden" id="username" name="username" value="${usermessage.phone}"/>

<div class="weui_msg_img hide" id='msg4'>
    <div class="weui_msg_com"><div onclick="$('#msg4').fadeOut();" class="weui_msg_close"><i class="icon icon-95"></i></div>
        <div class="weui_msg_comment">
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">手机号</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" type="text"  placeholder="请输入手机号" name="phone"/>
                </div>
            </div>

            <div class="weui_cell weui_vcode">
                <div class="weui_cell_hd"><label class="weui_label">验证码</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" type="text" placeholder="请输入验证码"    name="phonecode"/>
                </div>
                <div class="weui_cell_ft">
                    <input id="getcode" class="weui-vcode-btn" type="button" value="获取验证码" onclick="getcode(this)" >
                </div>
            </div>
            <p class="sib"><a onclick="login()" class="weui_btn weui_btn_primary">登录</a></p>


        </div></div></div>
<!--搜索路线-->

<div>
 <c:forEach items="${sessionScope.matchs}" var="matchs">
        <hr class="hr">
        <div class="page-bd">
            <div class="weui-flex">
                <div class="weui-flex-item">
                    <div class="title"><h3>${matchs.carType}</h3></div>
                    <form action="">
                        <ul class="prolist">

                            <c:forEach items="${matchs.cars}" var="roleId" varStatus="vs">
                            <ul class="prolist">
                                <li>
                                    <h4>&nbsp;<span>订单：<i class="orange">${roleId.scores}</i></span><span>评论数：<i class="orange">${roleId.scoreSum}</i></span></h4>
                                    <div class="check"><input type="checkbox" name="matchs" id="checkbox"  value="${roleId.carCode},${roleId.prices},<c:choose><c:when test="${roleId.type==1}">0</c:when><c:when test="${roleId.type==2}">${roleId.orderCode}</c:when></c:choose>" onchange='checkChange()'></div>
                                        <img src="${ctx}/images/pro.jpg" alt="">
                                        <div class="li_content">
                                                ${matchs.carType}<br/>
                                            <abbr></abbr>
                                        </div><span class="red"> <c:choose><c:when test="${roleId.prices>0}">￥${roleId.prices}元</c:when><c:when test="${roleId.prices==null || roleId.prices=='' }">￥0元</c:when></c:choose></span></a>
                                </li>
                            </ul>
                        </ul>
                    </form>
                    </c:forEach>

                </div>


            </div>
        </div>
    </c:forEach>
</div>


<!--推荐路线-->

<div>
<c:forEach items="${sessionScope.carsList}" var="matchs">
    <hr class="hr">
    <div class="page-bd">
    <div class="weui-flex">
    <div class="weui-flex-item">
    <div class="title"><h3>${matchs.carType} 【推荐车辆】</h3></div>
        <form action="">
            <ul class="prolist">
                <ul class="prolist">
                    <li>
                        <h4>&nbsp;<span>订单：<i class="orange">${matchs.scores}</i></span><span>评论数：<i class="orange">${matchs.scoreSum}</i></span></h4>
                        <div class="check"><input type="checkbox" name="matchs" id="checkbox"  value="${matchs.carCode},${matchs.prices},<c:choose><c:when test="${matchs.type==1}">0</c:when><c:when test="${matchs.type==2}">${matchs.orderCode}</c:when></c:choose>" onchange='checkChange()'></div>
                        <a href="order.html">
                            <img src="${ctx}/images/pro.jpg" alt="">
                            <div class="li_content">
                                    ${matchs.carType}<br/>
                                <abbr></abbr>
                            </div><span class="red">￥${matchs.prices} 元</span></a>
                    </li>
                </ul>
            </ul>
        </form>


</div>

</div>
</div>
</c:forEach>
<div class="heji">
    <form action="${ctx}/order/order" id="myForm" method="post" onsubmit="return toVaild(false,1)">
        <input type="hidden" id="carCode" name="carCode" value="${order.carCode}">
        <input type="hidden" id="prices" name="prices" value="${order.carCode}">
        <input type="hidden" id="carAmounts" name="prices" value="${order.carAmounts}">
        <input type="hidden" id="carTypes" name="prices" value="${order.carTypes}">
        <input type="hidden" id="amount" name="amount" value="">
        <input type="hidden" id="backOrderCode" name="backOrderCode" value="${order.backOrderCode}">
        合计：<span id="totalPrices">
        <c:choose><c:when test="${order.amount>0}">￥${order.amount}</c:when><c:when test="${order.amount == null || order.amount =='' }">￥0</c:when></c:choose>
    </span>
        <input type="submit"  value="立即预定">
    </form>
</div>
</body>
</html>
<script src="${ctx}/images/weui.john.js"></script>
<script src="${ctx}/images/john.js"></script>
<script type="text/javascript">

    var wait=60;
    var interValObj;



    //判断用户是否登录
    function toVaild() {


        if(!$("input[type='checkbox']").is(':checked')){
            alert("请选择车辆！")
            return false;
        }

        if($('#username').val()==null || $('#username').val()=='') {
            alert("您还未登录")
            $('#msg4').fadeIn();
            return false ;
        }

        var str=($('#carAmounts').val()).trim();
        var strs= new Array(); //定义一数组
        strs=str.split(","); //字符分割
        var num=0 ;
        for (i=0;i<strs.length;i++ )
        {
            if(strs[i]!='') {
                num = parseInt(num)+parseInt(strs[i])
            }
        }
        var gnl;
        var carCode= new Array();
        carCode=($('#carCode').val()).split(",");
        if(((carCode.length)-1)<num){
            gnl=confirm("您尚有"+(parseInt(num)-parseInt((carCode.length)-1))+"辆车没有匹配到,我们将通知运输方进行车辆的补给，确定预定已选的车辆吗？");
        }else {
            gnl = confirm("确定预定所选车辆么？");
        }




        if (gnl==true){
            return true;
        }else{
            return false;
        }

        return true;
    }
    $(document).ready(function() {
        if('${usermessage.phone}'.length<11){

        }
        if('${order.tripType}' == 1 && '${order.endPoint}' != null && '${order.endPoint}' != ''){

            var dataJson = "${sessionScope.order}";

           seLines(dataJson);
        }

    })


    //check 选择事件
    function checkChange() {

        var arr=document.getElementsByName("matchs");
        var totalPrices = 0;
        var carCode = '';
        var backOrderCode = '';
        var prices = '';
        for(i=0;i<arr.length;i++){

            if(arr[i].checked){

                var arrs = arr[i].value.split(',');

                if(arrs[1]>0) {
                    totalPrices += parseFloat(arrs[1]);
                }
                carCode +=  arrs[0]+',';

                if(arrs[1]==''){
                    prices += '0.0,';
                }else{
                    prices += arrs[1]+',';
                }

                backOrderCode += arrs[2]+',';
            }

            $('#totalPrices').html('总价格：￥'+(parseInt(totalPrices)+parseInt( '${order.amount}')));
            $('#carCode').val(carCode);
            $('#backOrderCode').val(backOrderCode);
            $('#prices').val(prices);
            $('#amount').val((parseInt(totalPrices)+parseInt('${order.amount}')));
           // $('#amount').val(totalPrices);

        }
    }



    $(document).ready(function(){


    });
    //没有精准车辆或者线路推荐的情况下搜索上车时间当天的空城单
    function seLines(dataJson){

        $.ajax({
            type:"post",
            url:"${ctx}/search/searLines",
            dataType:"json",
            data:dataJson,
            success:function(data) {

            }
        });
    }

    function login(){

        var phone = $("[name='phone']").val();
        var phonecode = $("[name='phonecode']").val();

        if (phone != null && '' != phone && phonecode != null && '' != phonecode) {

            if((/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(phone))&&(/^\d{6}\b/.test(phonecode))){
                var codeEncoder = $("[name='codeEncoder']").val();
                $.ajax({
                    type:"post",
                    dataType:"json",
                    url:"${ctx}/login/checkCode2",
                    data:{"phone":phone,"codeEncoder":codeEncoder,"phonecode":phonecode},
                    success: function(data) {
                        if(!data.state){
                            alert("验证码错误或超时!")

                        }else{
                            $('#msg4').fadeOut();
                            $("#username").val("true")
                            $("#myForm").submit();

                        }
                    }
                });


            }else{
                alert("请输入正确的手机密码和6位纯数字的验证码");
            }


        }else{
            alert("手机号码和验证码不能为空");
        }


    }



    function getcode(){
        var phone = $("[name='phone']").val();
        if(phone != null && '' != phone){
            if((/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(phone))){
                $("#getcode").css("background", "#c0c0c0");
                $("#getcode").attr("value","倒计时" + wait + "秒");
                $("#getcode").attr("disabled", "true");
                interValObj = window.setInterval(setRemainTime, 1000);
                $.ajax({
                    type:"post",
                    dataType:"json",
                    url:"${ctx}/login/getSms",
                    data:{"phone":phone},
                    success: function(data) {
                        if(!data.state){
                            window.clearInterval(interValObj);//停止计时器
                            $("#getcode").css("background", "");
                            $("#getcode").removeAttr("disabled");//启用按钮
                            $("#getcode").val("获取验证码");
                        }else{
                            $('#codeEncoder').val(data.code);
                        }
                    }
                });

            }else{

                alert("请输入正确的手机号码");
            }
        }else{
            alert("请输入手机号码");
        }

    }



    function setRemainTime() {
        if (wait == 0) {
            window.clearInterval(interValObj);//停止计时器
            $("#getcode").css("background", "");
            $("#getcode").removeAttr("disabled");//启用按钮
            $("#getcode").val("获取验证码");
            wait = 60;
        }else {
            wait--;
            $("#getcode").val("倒计时" + wait + "秒");
        }
    }




</script>


