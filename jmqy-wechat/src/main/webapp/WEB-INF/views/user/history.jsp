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
    <script src="${ctx}/images/iscroll.js"></script>

    <link rel="stylesheet" href="${ctx}/images/style.css">
    <title></title>
    <script type="text/javascript">



        $(document).ready(function(){


        });

    function toUrl(bigOrderCode,money) {

        window.location.href='http://mobile.jmqyjt.com/jmqy_pay/DH_Pay.aspx?ORDERID='+bigOrderCode+'&PAYMONEY=0.01&TITLE=yiyue'
    }


    function  CancelOrder(a) {



        var url = window.location.href;
        var loc = url.substring(url.lastIndexOf('/')+1, url.length);



        $.ajax({
            type:"post",
            url:"${ctx}/order/cancelOrder",
            contentType : "application/x-www-form-urlencoded",
            dataType:'json',
            data:{orderCode:a},
            success:function(data) {
                if(data.state){
                    alert("取消成功！")
                    window.location.href='${ctx}/user/list1/'+loc;
                }
            }
        })
    }

    var t;
    //评价
    function evaluate11(value,action) {

        if(action==2){

            window.location.href='${ctx}/order/evaluation?orderCode='+value+'&action='+action;
        }else{
            window.location.href='${ctx}/order/evaluation?orderCode='+value;
        }
    }

    // a是bigcode b是金额
    function Pay(a,b){


        $("#div4").attr("style","display:block;")
        $("#main").attr("style","display:none;")


        $.ajax({
            type:"post",
            url:"${ctx}/order/getImgSrc",
            contentType : "application/x-www-form-urlencoded",
            dataType:'json',
            data:{order:a,money:1},
            success:function(data) {

                var src = "http://219.130.135.53:8090/"
                src = src+data.src
                $("#myimg").attr('src',src);

            }
        })


        var t=window.setInterval(function()
        {
            GetRe(a,b);
        }, 1000);






    }


    //定时器调用函数，1秒请求一次后台，查看是否支付成功
    function  GetRe(a,b) {

        clearInterval(t);
        $.ajax({
            type:"post",
            url:"${ctx}/order/NewPay",
            contentType : "application/x-www-form-urlencoded",
            dataType:'json',
            data:{order:a,money:b},
            success:function(data) {

                //alert(data.re)
                if(null != data.re&&data.re!="F"&&data.re!=""){
                    //   clearInterval(GetRe())
                    alert("支付成功")
                    window.location.href='${ctx}/user/list1/-2,-1,0,3';

                }

            }
        })

        //  window.setInterval("GetRe()", 1000)


    }





    $(function(){


        //根据URL为a标签赋值
        var href = window.location.href
        var num = window.location.href.lastIndexOf('/') + 1
        href = href.substring(num);
        if (href == 0) {                            //待上车
            $("#a3").attr("style", "color:#090");
            $("#a3").find("img").attr('src','${ctx}/images/ico3.png')
        } else if (href == -1) {                   //待支付
            $("#a2").attr("style", "color:#090");
            $("#a2").find("img").attr('src','${ctx}/images/ico2.png')
        } else if (href == 3) {                    //待评价
            $("#a4").attr("style", "color:#090");
            $("#a4").find("img").attr('src','${ctx}/images/ico4.png')
        }else if(href == -4){
            $("#a5").attr("style", "color:#090");  //已经取消
            $("#a5").find("img").attr('src','${ctx}/images/ico5.png')
        }
        else {
            $("#a1").attr("style", "color:#090"); //全部
            $("#a1").find("img").attr('src','${ctx}/images/ico1.png')
        }

       /* TagNav('#tagnav',{
            type: 'scrollToFirst',
        });
        $('.weui_tab').tab({
            defaultIndex: 0,
            activeClass:'weui_bar_item_on',
            onToggle:function(index){
                if(index>0){
                    alert(index)
                }
            }
        });*/

        $('.searchbar_wrap').searchBar({
            cancelText:"取消",
            searchText:'关键字',
            onfocus: function (value) {

            },
            onblur:function(value) {

            },
            oninput: function(value) {

            },
            onsubmit:function(value){
            },
            oncancel:function(){

            },

            onclear:function(){

            }
        });
    });


    function query(or) {
        window.location.href='${ctx}/user/orderdetails?orderCode='+or
    }

    </script>
</head>
<body ontouchstart  class="page-bg">

<div id="main">

<div id="tagnav" class="weui-navigator weui-navigator-wrapper">
    <ul class="weui-navigator-list">
        <li><a id="a1" href="${ctx}/user/list1/-2,-1,0,3">
            <img src="${ctx}/images/ico1h.png" alt=""><br/>全部</a></li>
        <li><a id="a2" href="${ctx}/user/list1/-1"><img src="${ctx}/images/ico2h.png" alt=""><br/>待支付</a></li>
        <li><a id="a3" href="${ctx}/user/list1/0"><img src="${ctx}/images/ico3h.png" alt=""><br/>待上车</a></li>
        <li><a id="a4" href="${ctx}/user/list1/3"><img src="${ctx}/images/ico4h.png" alt=""><br/>待评价</a></li>
        <li><a id="a5" href="${ctx}/user/list1/-4"><img src="${ctx}/images/ico5h.png" alt=""><br/>已取消</a></li>
    </ul>
</div>
    <%--<script>--%>
        <%--$('.weui-navigator-list li').click(function(){--%>
            <%--$(this).find('img').attr('src','${ctx}/images/ico'+$(this).index()+'.png')--%>
        <%--})--%>
    <%--</script>--%>
<div class="searchbar_wrap" style="height:40px;">
</div>




<div class="page-bd">
    <div class="weui-flex">
        <div class="weui-flex-item">
            <form action="">
                <ul class="prolist hist">
                    <c:forEach items="${sessionScope.orders.list}" var="orders">

                        <li>
                            <h4><a href="javascript:query('${orders.orderCode}');" style="color: orange;">订单号：${orders.orderCode}</a></h4>
                            <img src="${ctx}/images/pro.jpg" alt="">
                            <div class="li_content">
                                15座中巴<br/>
                                <abbr>江门 - 开平</abbr>
                            </div><span class="red">
                            <c:if test="${orders.prices != null}">
                                ￥${orders.prices}
                            </c:if>
                            <c:if test="${orders.prices == null}">
                                ￥0
                            </c:if>

    </span>
                            <div class="cl" style="text-align:right"> <fmt:formatDate value="${orders.boardingTime}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
                            <div class="borderBottom margtop10"></div>
                            <div class="button">
                               <!--status状态为3的时候 -->
                                <c:if test="${orders.status == 3}">
                                    <a class="weui_btn weui_btn_inline weui_btn_mini bg-orange-b">已完成</a>
                                   <c:if test="${ not empty orders.evaluateId}">
                                        <a href="javascript:;" class="weui_btn weui_btn_inline weui_btn_mini weui_btn_default" onclick="evaluate11('${orders.orderCode}',2);">查看评价</a>
                                    </c:if>
                                    <c:if test="${ empty orders.evaluateId}">
                                        <a href="javascript:;" class="weui_btn weui_btn_inline weui_btn_mini weui_btn_default" onclick="evaluate11('${orders.orderCode}',null);">评价</a>
                                    </c:if>

                                </c:if>

                                <c:if test="${orders.status == -1}">
                                    <a class="weui_btn weui_btn_inline weui_btn_mini bg-orange-b" onclick="toUrl('${orders.bigOrderCode}','${orders.amount}');">立即支付</a>
                                    <c:if test="${orders.paymentStatus == 1}">
                                        <a href="javascript:;" class="weui_btn weui_btn_inline weui_btn_mini weui_btn_default" onclick="CancelOrder('${orders.orderCode}');">取消订单</a>
                                    </c:if>
                                </c:if>

                                <!--status状态为0的时候 -->
                                <c:if test="${orders.status == 0}">
                                    <a class="weui_btn weui_btn_inline weui_btn_mini bg-orange-b">待上车</a>
                                  <!--  <a href="javascript:;" class="weui_btn weui_btn_inline weui_btn_mini weui_btn_default" onclick="evaluate11('${orders.orderCode}');">评价</a> -->
                                </c:if>

                                <!--status状态为-4的时候 -->
                                <c:if test="${orders.status == -4}">
                                    <a class="weui_btn weui_btn_inline weui_btn_mini bg-orange-b">已取消</a>
                                </c:if>


                                <!--status状态为-2的时候 -->
                                <c:if test="${orders.status == -2}">
                                     <c:if test="${orders.paymentStatus == 1}">
                                        <a href="javascript:;" class="weui_btn weui_btn_inline weui_btn_mini weui_btn_default" onclick="CancelOrder('${orders.orderCode}');">取消订单</a>
                                    </c:if>
                                    <c:if test="${orders.paymentStatus == 2}">
                                    </c:if>
                                    <c:if test="${orders.paymentStatus == 1}">
                                        <a  class="weui_btn weui_btn_inline weui_btn_mini weui_btn_default" href="${ctx}/order/pushOrder?orderCode=${orders.orderCode}" >修改订单</a> </c:if>
                                    <c:if test="${orders.paymentStatus == 2}">
                                    </c:if>
                                    <c:if test="${orders.paymentStatus == 1}">
                                        <a class="weui_btn weui_btn_inline weui_btn_mini bg-orange-b" onclick="toUrl('${orders.bigOrderCode}','${orders.amount}');">立即支付</a>
                                    </c:if>
                                    <c:if test="${orders.paymentStatus == 2}">
                                        <a class="weui_btn weui_btn_inline weui_btn_mini bg-orange-b">已支付</a>
                                    </c:if>
                                </c:if>

                                <!--status状态为1的时候 -->
                                <c:if test="${orders.status == 1}">
                                    <c:if test="${orders.paymentStatus == 1}">
                                        <a  class="weui_btn weui_btn_inline weui_btn_mini weui_btn_default" onclick="CancelOrder('${orders.orderCode}');">取消订单</a>
                                    </c:if>
                                    <c:if test="${orders.paymentStatus == 2}">
                                    </c:if>
                                    <c:if test="${orders.paymentStatus == 1}">
                                        <a  class="weui_btn weui_btn_inline weui_btn_mini weui_btn_default"  href="${ctx}/order/pushOrder?orderCode=${orders.orderCode}">修改订单</a> </c:if>
                                    <c:if test="${orders.paymentStatus == 2}">
                                    </c:if>
                                    <c:if test="${orders.paymentStatus == 1}">
                                        <a class="weui_btn weui_btn_inline weui_btn_mini bg-orange-b" onclick="toUrl('${orders.bigOrderCode}','${orders.amount}');">立即支付</a>
                                    </c:if>
                                    <c:if test="${orders.paymentStatus == 2}">
                                        <a class="weui_btn weui_btn_inline weui_btn_mini bg-orange-b">已支付</a>
                                    </c:if>
                                </c:if>
                            </div>
                        </li>

                    </c:forEach>
                </ul>
            </form>
        </div>
    </div>
</div>
</div>

<div  id="div4" style="display:none">
    <a style='left:50%' > 扫码二维码支付</a>
    <img id="myimg" src="">
    <!--  <iframe id="myiframe" name="myiframe" src="http://219.130.135.53:8090/WXImage.ASPX?orderid=${order.bigOrderCode}&size=200&money=1" scrolling="no" frameborder=no width="200px" scrolling="no" height=200px></iframe>
  -->
</div>
</body>
</html>
<script src="${ctx}/images/weui.john.js"></script>
<script src="${ctx}/images/john.js"></script>


