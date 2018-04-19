<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>添加司机</title>
</head>
<body class="window">
<div class="layui-form" style="margin-top: 10px;">
    <form id="saveOrUpdate" class="layui-form" action="${ctx}/transport/driver/saveOrUpdate" method="post"
          enctype="multipart/form-data">
        <input type="hidden" id="id" name="id" value="${driver.id}"/>
        <input type="hidden" id="code" name="code" value="${driver.code}"/>
        <input type="hidden" id="companyId" name="companyId" value="${companyId}"/>
        <input type="hidden" id="changeType" name="changeType" value="${changeType}"/>
        <input type="hidden" id="jobNumPre" name="jobNumPre" value="${company.jobNumPre}"/>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">姓名:</label>
                <div class="layui-input-inline">
                    <input name="name" lay-verify="required" autocomplete="off" value="${driver.name}" placeholder="姓名"
                           class="layui-input" type="text" lay-filter="prog" onchange="a(this.value);">
                </div>
                <%-- <label class="layui-form-label">出生年月:</label>
                <div class="layui-input-inline">
                    <input type="text" name="birthDate" id="birthDate" lay-verify="" placeholder="出生年月" autocomplete="off" class="layui-input input_date"
                    onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d %H:%m:%s'})"
                    value="<fmt:formatDate type='date' pattern='yyyy-MM-dd' value='${driver.birthDate}'/>">
                </div> --%>
                <label class="layui-form-label">司机状态</label>
                <div class="layui-input-inline">
                    <select name="status" lay-verify="required" lay-search="">
                        <option value="0" <c:if test="${driver.status == 0}">selected="selected"</c:if>>在职</option>
                        <option value="1" <c:if test="${driver.status == 1}">selected="selected"</c:if>>休假</option>
                        <option value="2" <c:if test="${driver.status == 2}">selected="selected"</c:if>>离职</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item" pane="">
            <label class="layui-form-label">性别：</label>
            <div class="layui-input-block">
                <input name="sex" value="男" title="男"
                       <c:if test="${empty driver.sex || driver.sex == '男'}">checked="checked"</c:if> type="radio">
                <input name="sex" value="女" title="女"
                       <c:if test="${driver.sex == '女'}">checked="checked"</c:if> type="radio">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">企业工号:</label>
                <div class="layui-input-inline" style="width: 50px;">
                    <input name="jobNum" autocomplete="off" value="${company.jobNumPre}" class="layui-input" type="text"
                           disabled/>
                </div>
                <div class="layui-input-inline" style="width: 130px;">
                    <input name="jobNum" lay-verify="required|number|jobNumUnique" placeholder="4位编号" autocomplete="off"
                           value='<c:if test="${not empty driver.jobNum}">${fn:substring(driver.jobNum,3,7)}</c:if>'
                           class="layui-input" type="text">
                </div>
                <label class="layui-form-label">手机号码:</label>
                <div class="layui-input-inline">
                    <input type="text" name="phone" id="phone" value="${driver.phone}" lay-verify="phone"
                           placeholder="手机号码" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">身份证号:</label>
                <div class="layui-input-inline" style="width: 200px;">
                    <input type="text" name="idCard" id="idCard" value="${driver.idCard}"
                           lay-verify="required<c:if test='${empty driver.id}'>|idCardUnique</c:if>" placeholder="身份证号"
                           autocomplete="off" class="layui-input"/>
                </div>
                <div class="layui-input-inline">
                    <img class="img-mini zheng_a" src="
					<c:if test="${not empty driver.idCardPros}">${ctx}${driver.idCardPros}</c:if>
					<c:if test="${empty driver.idCardPros}">${ctx}/static/images/zhen.jpg</c:if>
					" onclick="fun_zheng(this);" alt="正面照" id="idCardProsImg">
                    <div class="upload">
                        <i class="icon-zoom-in zheng_a"
                           onclick="fun_zheng(document.getElementById('idCardProsImg'));"></i>
                        <i class="icon-cloud-upload" style="cursor:pointer;">
                            <input id="idCardPros" name="idCardPros" type="hidden" value="${driver.idCardPros}"/>
                            <input type="file" class="upload-button" id="idCardProsFile" name="idCardProsFile"
                                   onchange="previewImg(this);"/>
                        </i>
                    </div>
                    <img class="img-mini zheng_a" src="
					<c:if test="${not empty driver.idCardCons}">${ctx}${driver.idCardCons}</c:if>
					<c:if test="${empty driver.idCardCons}">${ctx}/static/images/fan.jpg</c:if>
					" onclick="fun_zheng(this);" alt="反面照" id="idCardConsImg">
                    <div class="upload">
                        <i class="icon-zoom-in zheng_a"
                           onclick="fun_zheng(document.getElementById('idCardConsImg'));"></i>
                        <i class="icon-cloud-upload" style="cursor:pointer;">
                            <input id="idCardCons" name="idCardCons" type="hidden" value="${driver.idCardCons}"/>
                            <input type="file" class="upload-button" id="idCardConsFile" name="idCardConsFile"
                                   onchange="previewImg(this);"/>
                        </i>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">从业资格证:</label>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="text" name="workLicenseEffect" id="workLicenseEffect" placeholder="生效日期"
                           autocomplete="off" class="layui-input input_date"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'workLicenseInvalid\')}'})"
                           value="<fmt:formatDate type='date' pattern='yyyy-MM-dd' value='${driver.workLicenseEffect}'/>">
                </div>
                <div class="layui-form-mid">-</div>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="text" name="workLicenseInvalid" id="workLicenseInvalid" placeholder="截止日期"
                           autocomplete="off" class="layui-input input_date"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'workLicenseEffect\')}'})"
                           value="<fmt:formatDate type='date' pattern='yyyy-MM-dd' value='${driver.workLicenseInvalid}'/>">
                </div>
                <div class="layui-input-inline">
                    <img class="img-mini zheng_a" src="
					<c:if test="${not empty driver.workLicense}">${ctx}${driver.workLicense}</c:if>
					<c:if test="${empty driver.workLicense}">${ctx}/static/images/zhen.jpg</c:if>
					" onclick="fun_zheng(this);" alt="正面照" id="workLicenseImg">
                    <div class="upload">
                        <i class="icon-zoom-in zheng_a"
                           onclick="fun_zheng(document.getElementById('workLicenseImg'));"></i>
                        <i class="icon-cloud-upload" style="cursor:pointer;">
                            <input id="workLicense" name="workLicense" type="hidden" value="${driver.workLicense}"/>
                            <input type="file" class="upload-button" id="workLicenseFile" name="workLicenseFile"
                                   onchange="previewImg(this);"/>
                        </i>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">驾驶证:</label>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="text" name="drivingLicenseEffect" id="drivingLicenseEffect" placeholder="生效日期"
                           autocomplete="off" class="layui-input input_date"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'drivingLicenseInvalid\')}'})"
                           value="<fmt:formatDate type='date' pattern='yyyy-MM-dd' value='${driver.drivingLicenseEffect}'/>">
                </div>
                <div class="layui-form-mid">-</div>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="text" name="drivingLicenseInvalid" id="drivingLicenseInvalid" placeholder="截止日期"
                           autocomplete="off" class="layui-input input_date"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'drivingLicenseEffect\')}'})"
                           value="<fmt:formatDate type='date' pattern='yyyy-MM-dd' value='${driver.drivingLicenseInvalid}'/>">
                </div>
                <div class="layui-input-inline">
                    <img class="img-mini zheng_a" src="
					<c:if test="${not empty driver.drivingLicense}">${ctx}${driver.drivingLicense}</c:if>
					<c:if test="${empty driver.drivingLicense}">${ctx}/static/images/zhen.jpg</c:if>
					" onclick="fun_zheng(this);" alt="正面照" id="drivingLicenseImg">
                    <div class="upload">
                        <i class="icon-zoom-in zheng_a"
                           onclick="fun_zheng(document.getElementById('drivingLicenseImg'));"></i>
                        <i class="icon-cloud-upload" style="cursor:pointer;">
                            <input id="drivingLicense" name="drivingLicense" type="hidden"
                                   value="${driver.drivingLicense}"/>
                            <input type="file" class="upload-button" id="drivingLicenseFile" name="drivingLicenseFile"
                                   onchange="previewImg(this);"/>
                        </i>
                    </div>
                </div>
            </div>
        </div>
        <%-- <div class="layui-inline">
            <label class="layui-form-label">司机状态</label>
            <div class="layui-input-inline">
                <select name="status" lay-verify="required" lay-search="">
                    <option value="0" <c:if test="${driver.status == 0}">selected = "selected"</c:if>>在职</option>
                    <option value="1" <c:if test="${driver.status == 1}">selected = "selected"</c:if>>休假</option>
                    <option value="2" <c:if test="${driver.status == 2}">selected = "selected"</c:if>>离职</option>
                </select>
          </div>
           </div> --%>
        <button class="layui-btn layui-btn-danger" style="float:right;display: none;" lay-submit lay-filter="submitBtn"
                id="submitBtn">发布
        </button>

        <div class="layui-progress" lay-showPercent="yes">
            <div class="layui-progress-bar" lay-percent="40%"></div>
        </div>

    </form>
</div>
<script type="text/javascript">
    //文档加载完毕执行
    $(function () {

        var message = '${message}';
        if (message != null && "" != message) {
            var msg = message.split("@");
            setTimeout(function () {
                layer.msg(msg[0], {icon: msg[1], time: 1000}, function () {
                    parent.selfSubmit();
                });
            }, 100);
        }
    });

    //预览图片
    function previewImg(obj) {
        var url = getFileUrl(obj.id);
        $('#' + obj.id.replace('File', 'Img')).attr('src', url);
    }

    //提交表单
    function laySubmit() {
        $('#submitBtn').click();
    }

    //图片放大
    function fun_zheng(obj) {
        alert(obj.src)
        parent.layer.open({
            type: 2,
            title: false,
            shadeClose: false,
            shade: 0,
            shift: -1,
            area: ['570px', '390px'],
            content: obj.src
        })
    }

    function a(value) {


        //  element.render('progress', 'prog');
    }


    layui.use('element', function () {
        //实例化element
        var element = layui.element;
        //初始化动态元素，一些动态生成的元素如果不设置初始化，将不会有默认的动态效果
        element.init();
//        console.log(element)

        element.progress('prog', '50%');

    });



    //加载表单
    layui.use(['form', 'element', 'laydate'], function () {


        element = layui.element;
          console.log(element)

        /* var laydate = layui.laydate;
        //执行laydate实例
        laydate.render({
          elem: '#drivingLicenseEffect' //指定元素
        });
        //日期时间选择器
        laydate.render({
         elem: '#drivingLicenseInvalid'
        });
        laydate.render({
          elem: '#birthDate' //指定元素
        });
        //日期时间选择器
        laydate.render({
         elem: '#workLicenseEffect'
        });
        laydate.render({
          elem: '#workLicenseInvalid' //指定元素
        }); */

        var form = layui.form;
//        console.log(form)

        //监听提交
        form.on('submit(submitBtn)', function (data) {
            //layer.msg(JSON.stringify(data.field));
            if ($('#idCardPros').val() == '' && $('#idCardProsFile').val() == '') {
                layer.msg('身份证正面照不能为空！', {icon: 2, time: 1000});
                return false;
            }
            if ($('#idCardCons').val() == '' && $('#idCardConsFile').val() == '') {
                layer.msg('身份证反面照不能为空！', {icon: 2, time: 1000});
                return false;
            }
            if ($('#workLicense').val() == '' && $('#workLicenseFile').val() == '') {
                layer.msg('上岗证正面照不能为空！', {icon: 2, time: 1000});
                return false;
            }
            if ($('#drivingLicense').val() == '' && $('#drivingLicenseFile').val() == '') {
                layer.msg('驾驶证正面照不能为空！', {icon: 2, time: 1000});
                return false;
            }
            $('#jobNum').val('${company.jobNumPre}' + $('#jobNum').val());
            return true;
        });

        //自定义验证规则
        form.verify({
            idCardUnique: function (value, item) {
                if (value != '' && value != null) {
                    var dataJson = {'code': $('#code').val(), 'idCard': value};
                    var result = check(dataJson, '${ctx}/transport/driver/checkUnique');
                    var changeType= '${company.changeType}';
                    if(!changeType==2) {
                        if (!result) {
                            return "已经有司机使用了此身份证号码";
                        }
                    }
                }
            },
            jobNumUnique: function (value, item) {
                if (value != '' && value != null) {
                    if (value.length != 4) {
                        return "企业工号必须为四位数字";
                    }
                    var dataJson = {'code': $('#code').val(), 'jobNum': ('${company.jobNumPre}' + value)};
                    var result = check(dataJson, '${ctx}/transport/driver/checkUnique');
                    var changeType= '${company.changeType}';
                    if(!changeType==2){
                        if (!result) {
                            return "已经有司机使用了此工号";
                        }
                    }
                }
            }
        });
    });

    function check(dataJson, url) {
        var result = true;
        $.ajax({
            async: false,
            type: "post",
            url: url,
            dataType: "json",
            data: dataJson,
            success: function (data) {
                result = data;
            }
        });
        return result;
    }


</script>
</body>
</html>