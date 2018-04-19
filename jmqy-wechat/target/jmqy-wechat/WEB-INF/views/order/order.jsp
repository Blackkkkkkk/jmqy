<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
    <link rel="stylesheet" href="../images/style.css">
    <script src='http://res.wx.qq.com/open/js/jweixin-1.2.0.js'></script>
    <title></title>
</head>
<body ontouchstart  class="page-bg">

<style>

    #div4 {
        position: absolute;
        left: 50%;
        top: 50%;
        width: 600px;
        height: 600px;
        margin-left: -100px;
        margin-top: -50px;
    }
    .john-table{
        width:100%;
    }
    .john-table td:nth-child(even){
        text-align:right;
    }

</style>


<div id="main">
<div class="page-hd">
    <h1 class="page-hd-title" style="font-size: 25px">
        订单信息
    </h1>
</div>
<div class="weui-form-preview">
    <div class="weui-form-preview-hd">
        <label class="weui-form-preview-label" >付款金额</label>
        <em class="weui-form-preview-value">¥${order.amount}</em>
    </div>
    <div class="weui-form-preview-bd">

        <table class="john-table">
            <tr>
                <td><label class="weui-form-preview-label">上车点</label></td>
                <td> <span class="weui-form-preview-value">${order.startPoint}</span></td>
            </tr>
            <tr>
                <td> <label class="weui-form-preview-label">途经点</label></td>
                <td><span class="weui-form-preview-value">开平、台山</span></td>
            </tr>
            <tr>
                <td><label class="weui-form-preview-label">下车点</label></td>
                <td> <span class="weui-form-preview-value">${order.endPoint}</span></td>
            </tr>
            <tr>
                <td><label class="weui-form-preview-label">包车行程</label></td>
                <td><span class="weui-form-preview-value">
                     <c:if test="${order.tripType==1}">单程</c:if>
                     <c:if test="${order.tripType==2}">往返</c:if>
            </tr>
            <tr>
                <td><label class="weui-form-preview-label">附加项</label></td>
                <td><span class="weui-form-preview-value">${order.additional}</span></td>
            </tr>
            <tr>
                <td><label class="weui-form-preview-label">上车时间</label></td>
                <td><span class="weui-form-preview-value"><fmt:formatDate value="${order.boardingTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                    <!-- <span class="weui-form-preview-value">${order.boardingTime}</span> --></td>
            </tr>
            <tr>
                <td><label class="weui-form-preview-label">返程时间</label></td>
                <td><span class="weui-form-preview-value">
                    <fmt:formatDate value="${order.trackBoardTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                    </td>
            </tr>
        </table>


    </div>
    <hr color="#eee">
    <div class="page-bd">
        <div class="weui-flex padtb0">
            <div class="weui_cells weui_cells_form margtop0">
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">联系人</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" type="text"  value="${order.linkMan}"/>
                    </div>
                </div>
                <div class="borderBottom"></div>
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">联系电话</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" type="text"  value="${order.linkPhone}" />
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="weui-form-preview-ft">
        <a class="weui-form-preview-btn weui-form-preview-btn-primary" onclick="toUrl()">支付</a><!-- 原来的支付-->
        <p class="time2">剩余支付时间 <strong id="minute_show">30分</strong><strong id="second_show">00秒</strong>.</p>
    </div>
</div>
</div>

<div  id="div4" style="display:none">
    <a style='left:50%' > 扫码二维码支付</a>
    <!--  <img id="myimg" src="">-->
    <!--
    <iframe id="myiframe" name="myiframe" src="http://219.130.135.53:8090/WXImage.ASPX?orderid=${order.bigOrderCode}&size=200&money=1" scrolling="no" frameborder=no width="200px" scrolling="no" height=200px></iframe>


   <iframe id="myiframe" name="myiframe"  src="http://mobile.jmqyjt.com/jmqy_pay/DH_Pay.aspx?ORDERID=${order.bigOrderCode}&PAYMONEY=0.01&TITLE=马上走" scrolling="no" frameborder=no width="200px" scrolling="no" height=200px></iframe>
  -->
</div>







</body>
</html>
<script src="../images/weui.john.js"></script>
<script src="../images/john.js"></script>

<script type="text/javascript">

    // 调用扫一扫

    function toUrl() {
       window.location.href='http://mobile.jmqyjt.com/jmqy_pay/DH_Pay.aspx?ORDERID=${order.bigOrderCode}&PAYMONEY=0.01&TITLE=yiyue'
    }



 /*   var div=document.querySelector('div');
    var data;//接受配置信息，后台给的



    var getWxConfig=function(){
        $.post("${ctx}/order/getScanQRCode",{},function(da){data=da;},"json");
    }
    getWxConfig();
    function a(){
           console.log(data)
        wx.config({
            debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            //                                debug : true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId : data.appID, // 必填，公众号的唯一标识
            timestamp : data.timestamp, // 必填，生成签名的时间戳
            nonceStr : data.nonceStr, // 必填，生成签名的随机串
            signature : data.signature,// 必填，签名，见附录1
            jsApiList : ['checkJsApi', 'scanQRCode'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });
        wx.ready(function() {
            alert("1")
            wx.scanQRCode({
                needResult : 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                scanType : [ "qrCode"], // 可以指定扫二维码还是一维码，默认二者都有
                success : function(res) {
                  //  var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                    //alert(result)
                   // window.location.href = result;//因为我这边是扫描后有个链接，然后跳转到该页面
                }
            });

        });
    }

*/
     var t;

    //定时器调用函数，1秒请求一次后台，查看是否支付成功
    function  GetRe() {

        clearInterval(t);

        $.ajax({
            type:"post",
            url:"${ctx}/order/NewPay",
            contentType : "application/x-www-form-urlencoded",
            dataType:'json',
            data:{order:'${order.bigOrderCode}',money:'${order.amount}'},
            success:function(data) {


                if(null != data.re&&data.re!="F"&&data.re!=""){
                 //   clearInterval(GetRe())
                     alert("支付成功")
                    window.location.href='${ctx}/user/list1/-2,-1,0,3';

                }

            }
        })




    }

    //调用对方的接口
    function Pay1(){

        $("#div4").attr("style","display:block;")
        $("#main").attr("style","display:none;")
       //  alert('${order.bigOrderCode}')
        alert($("#myiframe").attr("src"))
        console.log($("#myiframe").attr("src"))

       /* $.ajax({
            type:"post",
            url:"${ctx}/order/getImgSrc",
            contentType : "application/x-www-form-urlencoded",
            dataType:'json',
            data:{order:'${order.bigOrderCode}',money:1},
            success:function(data) {
                var src = "http://219.130.135.53:8090/"
                src = src+data.src

                $("#myimg").attr('src',src);

            }
        })
*/
         t=setInterval("GetRe()", 1000)

    }
    //原本的方式
    function Pay() {

        var iHeight = $(window).height();
       /* var iWidth = $(document).height();
        var iTop = (window.screen.availHeight-30-iHeight)/2;       //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-10-iWidth)/2;

       */
     //   window.location.href='${ctx}/pay/scanPay?bigOrderCode=${order.bigOrderCode}&id='+10000*Math.random();

        $.ajax({
            type:"post",
            url:"${ctx}/order/saveReleaseDetail",
            dataType:"json",

            success:function(data) {
                alert(data.state);
                if(data.state == 1){
                    alert("操作成功1")
                }else if(data.state ==2){
                    alert("操作成功2")
                    wechatPay("${order.bigOrderCode}");

                }else if(data.state == 3){
                    alert("信用额度不够!");

                }else if(data.state == 99){
                    alert("操作失败！")

                }
            }

        });


    }

    function wechatPay(bigOrderCode){
        alert(bigOrderCode);
        var width ="400px";
        var height = "300px;"
        var title ="订单支付";

        window.location.href='${ctx}/pay/scanPay?bigOrderCode=${order.bigOrderCode}&id='+10000*Math.random();


    }



    var intDiff = parseInt(300);//倒计时总秒数量
    function timer(intDiff){
        window.setInterval(function(){
            var day=0,
                hour=0,
                minute=0,
                second=0;//时间默认值
            if(intDiff > 0){
                day = Math.floor(intDiff / (60 * 60 * 24));
                hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
                minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
                second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
            }
            if (minute <= 9) minute = '0' + minute;
            if (second <= 9) second = '0' + second;
            $('#minute_show').html('<s></s>'+minute+'分');
            $('#second_show').html('<s></s>'+second+'秒');
            if(intDiff == 0){
                $("#payBtn").attr("disabled", true);
                $("#payBtn").css("background-color","#CCC");
                clearInterval(timer);
                layer.alert('订单支付超时！', {icon: 0}, function(){
                    //超时对应操作
                    window.location.replace('${ctx}/order/pushOrder?orderCode='+$("[name='orderCode']").val());
                });
            }
            intDiff--;
        }, 1000)
    }
    $(function(){
        timer(intDiff);
    });



</script>
