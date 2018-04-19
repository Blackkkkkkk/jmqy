<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>新增运输企业</title>
    <style>
        body{
            min-width: 0;
        }
        .layui-input-inline{
            width: 300px !important;
        }
        .upload{
            width: auto !important;
        }
        .upload i{
            border: none !important;
        }
    </style>
</head>
<body>
<div style="margin-top: 30px;">
    <form id="inputForm" class="layui-form" action="${ctx}/sys/company/init" method="post" lay-filter="inputForm" enctype="multipart/form-data">
        <div class="sign1">
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="red">*</span> 企业名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="companyName" placeholder="请输入企业名称" class="layui-input" lay-verify="required">
                </div>
                <label class="layui-form-label"><span class="red">*</span> 工号前缀</label>
                <div class="layui-input-inline">
                    <input type="text" name="jobNumPre" placeholder="请输入三位企业工号前缀(数字)" class="layui-input" lay-verify="required|jobNum">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="red">*</span> 账号</label>
                <div class="layui-input-inline">
                    <input type="text" name="userAccount" placeholder="请输入登录账号" class="layui-input" lay-verify="required|loginName">
                </div>
                <label class="layui-form-label"><span class="red">*</span> 手机号</label>
                <div class="layui-input-inline">
                    <input type="text" name="phone" class="layui-input" placeholder="请输入手机号" lay-verify="required|phone|checkPhone">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="red">*</span> 企业负责人</label>
                <div class="layui-input-inline">
                    <input type="text" name="userName" placeholder="请输入昵称" class="layui-input" lay-verify="required|userName">
                </div>
                <label class="layui-form-label"><span class="red">*</span> 性别</label>
                <div class="layui-input-inline">
                    <input name="sex" type="radio" value="1" checked="checked" title="男"/>
                    <input name="sex" type="radio" value="0" title="女"/>
                </div>
                <div class="layui-input-inline" style="display: none">
                    <select name="roleName" lay-verify="required" lay-filter="roleName">
                        <option value="transport_admin" selected="selected">运输企业</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="red">*</span> 密码</label>
                <div class="layui-input-inline">
                    <input type="password" name="userPassword" placeholder="密码不能少于6位数" class="layui-input" lay-verify="required|length">
                </div>
                <label class="layui-form-label"><span class="red">*</span> 确认密码</label>
                <div class="layui-input-inline">
                    <input type="password" name="newPassword" placeholder="密码不能少于6位数" class="layui-input" lay-verify="required|length|equals">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="red">*</span> 邮箱</label>
                <div class="layui-input-inline">
                    <input type="text" name="email" placeholder="请输入邮箱" class="layui-input" lay-verify="required|email">
                </div>
                <label class="layui-form-label"><span class="red">*</span> 身份证</label>
                <div class="layui-input-inline">
                    <input type="text" name="idCard" placeholder="请输入身份证" class="layui-input" lay-verify="required|identity">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="red">*</span> 单位地址</label>
                <textarea name="address" class="layui-textarea" placeholder="请输入单位地址" style="width: 720px; overflow: auto" lay-verify="required"></textarea>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="red">*</span> 营业执照</label>
                <div class="layui-upload">
                    <button class="layui-btn upload" type="button"><i class="layui-icon"></i>上传图片</button>
                </div>
                <div class="layui-upload-list" id="img" lay-verify="img"></div>
            </div>
        </div>
        <div class="center" style="display: none;">
            <button href="javascript:;" class="layui-btn layui-btn-normal sign-next" lay-filter="inputForm" lay-submit>提&nbsp;&nbsp;交</button>
            <button class="layui-btn layui-btn-primary" href="javascript:;">重&nbsp;&nbsp;置</button>
        </div>
    </form>
</div>
<div class="cl"></div>
<script>
    //文档加载完毕执行
    $(function() {
        var message = '${message}';
        if(message != null && "" != message){
            var msg = message.split("@");
            setTimeout(function(){
                layer.open({
                    icon: msg[1],
                    title: '信息',
                    skin: 'layer-ext-myskin',
                    shade: 0, //不显示遮罩
                    content: "&nbsp;&nbsp;"+msg[0]+"&nbsp;&nbsp;",
                    yes: function(){
                        parent.layer.closeAll();
                        parent.refresh();
                    },
                });
            }, 500);
        }
    });
    //校验
    layui.use(['form','upload'], function() {
        var form = layui.form;
        var upload = layui.upload;
        //监听提交
        form.on('submit(inputForm)', function(data){
            var img = $("#img").html();
                if(!(img != null && "" != img)){
                layer.msg("请上传营业执照！", {icon: 5}, function () {});
                return false;
            }
            return true;
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
            checkPhone: function (value,item) {
                var dataJson = {"userAccount":value};
                var result = check(dataJson, '${ctx}/register/checkLogin');
                if(!result){
                    return "手机号已被注册！";
                }
            },
            length: function (value, item) {
                if(value.length < 6){
                    return "密码不能少于6位！";
                }
            },
            equals: function (value,item) {
                if( $("[name='userPassword']").val()!=value)
                    return "两次密码不一致！";
            },
            jobNum: function (value,item) {
                if(!/(^[0-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.exec(value)){
                    return "请输入正确的数字！";
                }
                if(value.length < 3){
                    return "请输入三位数字！";
                }
                var dataJson = {"jobNum":value};
                var result = check(dataJson, '${ctx}/sys/checkJobNum');
                if(!result){
                    return "企业工号前缀已存在！";
                }
            }
        });
        //图片上传
        upload.render({
            elem: '.upload',
            url: '',
            auto: false,
            bindAction: 'submit',
            choose: function(obj){
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result){
                    $('#img').html('<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img" style="width: 350px; height: 200px;">')
                });
            }
         });
    });
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
        $("#img").text("");
    })
</script>
</body>
</html>