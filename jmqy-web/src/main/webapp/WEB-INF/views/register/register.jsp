<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>用户注册</title>
</head>
<body>
<div class="container">
    <div class="position_all" style="margin-top: 15px;">
        <div class="position">您所在的位置：首页 -> 注册</div>
    </div>
    <div class="info sign" style="width:1180px; min-height: 600px;">
        <br/>
        <div class="buzou"></div>
        <ol>
            <li>填写信息</li>
            <li>完成注册</li>
        </ol>
        <input type="hidden" id="codeEncoder" name="codeEncoder"/>
        <form id="inputForm" class="layui-form" action="${ctx}/register/init" method="post" lay-filter="inputForm">
            <div class="sign_all">
                <div class="sign1">
                    <div class="layui-form-item">
                        <label class="layui-form-label"><span class="red">*</span> 账号</label>
                        <div class="layui-input-block">
                            <input type="text" name="userAccount" placeholder="请输入登录账号" class="layui-input" lay-verify="required|loginName">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><span class="red">*</span> 用户名</label>
                        <div class="layui-input-block">
                            <input type="text" name="userName" placeholder="请输入昵称" class="layui-input" lay-verify="required|userName">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><span class="red">*</span> 性别</label>
                        <div class="layui-input-block">
                            <input name="sex" type="radio" value="1" checked="checked" title="男"/>
                            <input name="sex" type="radio" value="0" title="女"/>
                        </div>
                    </div>
                    <div class="layui-form-item" style="display: none;">
                        <label class="layui-form-label"><span class="red">*</span> 类型</label>
                        <div class="layui-input-block">
                            <select name="roleName" lay-verify="required" lay-filter="roleName">
                                <option value="charter" selected="selected">个人用户</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><span class="red">*</span> 密码</label>
                        <div class="layui-input-block">
                            <input type="password" name="userPassword" placeholder="密码不能少于6位数" class="layui-input" lay-verify="required|length">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><span class="red">*</span> 确认密码</label>
                        <div class="layui-input-block">
                            <input type="password" name="newPassword" placeholder="密码不能少于6位数" class="layui-input" lay-verify="required|length|equals">
                        </div>
                    </div>
                    <!-- <div class="layui-form-item">
                        <label class="layui-form-label"><span class="red">*</span> 邮箱</label>
                        <div class="layui-input-block">
                            <input type="text" name="email" placeholder="请输入邮箱" class="layui-input" lay-verify="required|email">
                        </div>
                    </div> -->
                    <div class="layui-form-item">
                        <label class="layui-form-label"><span class="red">*</span> 手机号</label>
                        <div class="layui-input-inline" style="width:321px;">
                            <input type="text" name="phone" class="layui-input" placeholder="请输入手机号" lay-verify="required|phone|checkPhone">
                        </div>
                         <div class="layui-input-inline" style="width:auto;margin-right:0px;">
                            <input class="layui-btn code_btn" type="button" value="获取验证码" lay-verify="required">
                        </div>
                    </div>
                     <div class="layui-form-item">
                        <label class="layui-form-label"><span class="red">*</span> 验证码</label>
                        <div class="layui-input-block">
                            <input type="text" name="code" placeholder="请输入验证码" class="layui-input" lay-verify="required|checkCode">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><span class="red">*</span> 身份证</label>
                        <div class="layui-input-block">
                            <input type="text" name="idCard" placeholder="请输入身份证" class="layui-input" lay-verify="required|identity">
                        </div>
                    </div>
                    <div id="company_div" style="display: none;">
                        <div class="layui-form-item">
                            <label class="layui-form-label"><span class="red">*</span> 单位名称</label>
                            <div class="layui-input-block">
                                <input type="text" name="companyName" placeholder="请输入单位名称" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label"><span class="red">*</span> 单位地址</label>
                            <div class="layui-input-block">
                                <input type="text" name="address" placeholder="请输入单位地址" class="layui-input">
                            </div>
                        </div>
                    </div>
                    <div class="center" style="margin-left: 60px; margin-top: 50px;">
                        <a href="javascript:;" class="layui-btn layui-btn-normal sign-next" lay-filter="inputForm" lay-submit>提&nbsp;&nbsp;交</a>
                        <a class="layui-btn layui-btn-primary" href="javascript:;">重&nbsp;&nbsp;置</a>
                    </div>
                </div>
                <div class="sign3" style="margin-top: 100px; margin-left: 80px;">
                    <table>
                        <tr>
                            <td><i class="layui-icon" style="font-size: 40px; color: green; margin-right:20px;">&#x1005;</i></td>
                            <td>
                                <p>注册成功! 您的用户名: <span class="red login_name"></span></p>
                                <p><span class="orange">10</span>秒钟后自动返回到首页!</p>
                                <p><a href="${ctx}/index" style="color: #20526a">立即返回网站首页</a></p>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </form>
        <br/><br/>
    </div>
</div>
<br/><br/>
<div class="cl"></div>
<script>
    //校验
    layui.use(['form'], function() {
        var form = layui.form;
        //监听提交
        form.on('submit(inputForm)', function(data){
            //var dataJson = JSON.stringify(data.field);
            submit_(data.field);
            return false;
        });
        //监听下拉
        form.on('select(roleName)', function(data){
            if(data.value.indexOf("admin") != -1){
                $("#company_div").slideDown();
                $("[name='Address']").attr('lay-verify','required');
                $("[name='companyName']").attr('lay-verify','required');
            }else{
                $("#company_div").slideUp();
                $("[name='Address']").removeAttr('lay-verify');
                $("[name='companyName']").removeAttr('lay-verify');
            }
        });
        //自定义验证规则
        form.verify({
            loginName: function (value,item) {
                var dataJson = {"userAccount":value};
                var result = check(dataJson, '${ctx}/register/checkLogin');
                if(!result){
                    return "账号已被注册！";
                }
            },
            checkCode: function (value,item) {

                var phone = $("[name='phone']").val()
                var codeEncoder = $("[name='codeEncoder']").val()
                var dataJson = {"code":value, "phone":phone, "codeEncoder":codeEncoder};
                var result = check(dataJson, '${ctx}/register/checkCode');
                if(!result.state){
                    return "验证码错误或超时！";
                }
            },
            checkPhone: function (value,item) {
                var dataJson = {"userAccount":value};
                var result = check(dataJson, '${ctx}/register/checkLogin');
                if(!result){
                    return "手机号已被注册！";
                }
            },
            length: function (value, item) {
                if(value.length < 6){
                    return "密码不能少于6位";
                }
            },
            equals: function (value,item) {
                if( $("[name='userPassword']").val()!=value)
                    return "两次密码不一致！";
            }
        });
    });
    function submit_(dataJson) {
        $('.layui-layer-btn0').css('pointer-events','none');
        $.ajax({
            type:"post",
            url:"${ctx}/register/init",
            dataType:"json",
            data:dataJson,
            success:function(data) {
                if(data.state){
                    var role = data.role;
                    var name = data.name;
                    if(role.indexOf('admin') != -1){
                        $(".login_name").text(name+', 请耐心等待平台审核并及时查验! ');
                    }else{
                        $(".login_name").text(name);
                    }
                    $('.sign3').css('display','block');
                    $('.sign1').css('display','none');
                    $('.buzou').css('background','url(${ctx}/static/images/reg_lc3.png)');
                    var time3 = 10;
                    var timer3 = setInterval(function(){
                        if(time3 <= 10){
                            time3 = time3 - 1;
                            $('.sign3 span.orange').text(time3);
                            if(time3 == 0){
                                window.open('${ctx}/index','_self');
                                clearInterval(timer3)
                            }
                        }
                    },1000)
                }else{
                    layer.msg("注册失败！", {icon: 5});
                }
            }
        });
    }
    function check(dataJson, url) {
        var result = true;
        $.ajax({
            async:false,
            type:"post",
            url:url,
            dataType:"json",
            data:dataJson,
            success:function(data) {
                result = data.state;
            }
        });
        return result;
    }
    $('.layui-btn-primary').click(function () {
        $("#inputForm")[0].reset();
    })
    var wait=60;
    var interValObj;
    $('.code_btn').click(function () {
        var phone = $("[name='phone']").val();
        if(phone != null && '' != phone){
            if((/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(phone))){
                $('.code_btn').attr("disabled", "true");
                $('.code_btn').css("background", "#c0c0c0");
                $('.code_btn').val("倒计时" + wait + "秒");
                interValObj = window.setInterval(setRemainTime, 1000);
                $.ajax({
                    type:"post",
                    dataType:"json",
                    url:"${ctx}/register/getSms",
                    data:{"phone":phone},
                    success: function(data) {
                        if(!data.state){
                            window.clearInterval(interValObj);//停止计时器
                            $('.code_btn').css("background", "");
                            $('.code_btn').removeAttr("disabled");//启用按钮
                            $('.code_btn').val("获取验证码");
                        }else{
                            $('#codeEncoder').val(data.code);
                        }
                    }
                });
            }else{
                layer.msg("请输入正确的手机号码", {icon: 5});
            }
        }
    })
    function setRemainTime() {
        if (wait == 0) {
            window.clearInterval(interValObj);//停止计时器
            $('.code_btn').css("background", "");
            $('.code_btn').removeAttr("disabled");//启用按钮
            $('.code_btn').val("获取验证码");
            wait = 60;
        }else {
            wait--;
            $('.code_btn').val("倒计时" + wait + "秒");
        }
    }
</script>
</body>
</html>