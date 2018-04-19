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
    <script src="${ctx}/images/jquery-3.2.1.min.js"></script>
    <script src="${ctx}/images/zepto.min.js"></script>
    <link rel="stylesheet" href="${ctx}/images/style.css">
    <title></title>
    <style>
        body{
            background:#f3f2f2 !important;
        }
    </style>
    <script>
        $(function(){

            $('#ms4').click(function(){
                $('#msg4').fadeIn();
            })
        });

    </script>
</head>
<body ontouchstart  class="page-bg">
<br/><br/><br/><br/>
<div class="user">
    <!-- href="${ctx}/login/LoginAgain"-->
    <a href="${ctx}/user/history" class="weui_btn weui_btn_default">我的订单</a>
    <a  onclick="ChangeNum()" class="weui_btn weui_btn_default">更换手机号码</a>
    <a href="javascript:;" onclick="deSession()" id='ms4' class="weui_btn weui_btn_default">登录弹窗</a>
</div>  <br/><br/><br/>
<p class="sib"><a  onclick="deSession()" href="${ctx}/login" class="weui_btn weui_btn_default">退出</a></p>

<div class="weui_msg_img hide" id='msg4'>
    <div class="weui_msg_com"><div onclick="$('#msg4').fadeOut();" class="weui_msg_close"><i class="icon icon-95"></i></div>
        <div class="weui_msg_comment">
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">手机号${usermessage.phone}</label></div>
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






</body>
</html>
<script src="${ctx}/images/layui.john.js"></script>
<script src="${ctx}/images/john.js"></script>


<script type="text/javascript">

    var wait=60;
    var interValObj;
    //获取验证码
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
    //登录
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
                            //  alert( "${usermessage.phone}")
                            window.location.href = "${ctx}/login/index";
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

    //倒计时
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

    //更换手机号重新登录
    function ChangeNum() {

        deSession();
        window.location.href = "${ctx}/login";

    }
    //删除session
    function deSession(){
        <%
           session.invalidate();
        %>
    }
    
</script>

