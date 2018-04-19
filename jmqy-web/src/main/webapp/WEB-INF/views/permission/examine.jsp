<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>企业审核</title>
<style>
body{
    min-width: 0;
}
.plat_box{
    margin-top: 50px;
}
.plat_box input{
    width: 235px;
}
.layui-form-label{
    width: 80px;
}
</style>
</head>
<body>
<div class="plat_box">
    <form id="inputForm" action="" class="layui-form" lay-filter="inputForm">
        <input type="hidden" id="id" name="id" value="${user.id}">
        <input type="hidden" id="companyId" name="companyId" value="${user.companyId}">
        <div class="layui-inline">
            <label class="layui-form-label">企业名称：</label>
            <div class="layui-input-inline" style="float: left;">
                <input type="text" value="${user.companyName}" class="layui-input" readonly="readonly">
            </div>
            <label class="layui-form-label">企业类型：</label>
            <div class="layui-input-inline" style="float: left;">
                <c:if test="${user.companyType == 0}">
                    <input type="text" value="平台管理" class="layui-input" readonly="readonly">
                </c:if>
                <c:if test="${user.companyType == 1}">
                    <input type="text" value="个人租车" class="layui-input" readonly="readonly">
                </c:if>
                <c:if test="${user.companyType == 2}">
                    <input type="text" value="包车企业" class="layui-input" readonly="readonly">
                </c:if>
                <c:if test="${user.companyType == 3}">
                    <input type="text" value="运输企业" class="layui-input" readonly="readonly">
                </c:if>
            </div>
        </div>
        <br/><br/>
        <div class="layui-inline">
            <label class="layui-form-label">企业账号：</label>
            <div class="layui-input-inline" style="float: left;">
                <input type="text" value="${user.userAccount}" class="layui-input" readonly="readonly">
            </div>
            <label class="layui-form-label">用户名称：</label>
            <div class="layui-input-inline" style="float: left;">
                <input type="text" value="${user.userName}" class="layui-input" readonly="readonly">
            </div>
        </div>
        <br/><br/>
        <div class="layui-inline">
            <label class="layui-form-label">手机号：</label>
            <div class="layui-input-inline" style="float: left;">
                <input type="text" value="${user.phone}" class="layui-input" readonly="readonly">
            </div>
            <%-- <label class="layui-form-label">性别：</label>
            <div class="layui-input-inline" style="float: left;">
                <c:if test="${user.sex == 1}">
                    <input type="text" value="男" class="layui-input" readonly="readonly">
                </c:if>
                <c:if test="${user.sex == 0}">
                    <input type="text" value="女" class="layui-input" readonly="readonly">
                </c:if>
            </div> --%>
            <label class="layui-form-label">身份证号：</label>
            <div class="layui-input-inline" style="float: left;">
                <input type="text" value="${user.idCard}" class="layui-input" readonly="readonly">
            </div>
        </div>
        <br/><br/>
        <div class="layui-inline">
            <label class="layui-form-label">邮箱：</label>
            <div class="layui-input-inline" style="float: left;">
                <input type="text" value="${user.email}" class="layui-input" readonly="readonly">
            </div>
            <label class="layui-form-label">营业执照：</label>
            <div class="layui-input-inline" style="float: left;">
                <img class="img-mini zheng_a" src="
				<c:if test="${not empty company.businessPic}">${ctx}${company.businessPic}</c:if>
				<c:if test="${empty company.businessPic}">${ctx}/static/images/zhen.jpg</c:if>
				" onclick="fun_zheng(this);" alt="正面照" id="businessPicImg">
            </div>
        </div>
        <br/><br/>
        <div class="layui-inline">
            <label class="layui-form-label">企业地址：</label>
            <div class="layui-input-inline" style="float: left;">
                <input type="text" value="${user.address}" class="layui-input" readonly="readonly">
            </div>
            <label class="layui-form-label">注册时间：</label>
            <div class="layui-input-inline" style="float: left;">
                <input type="text" value="<fmt:formatDate value="${user.registerDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" class="layui-input" readonly="readonly">
            </div>
        </div>
        <br/><br/>
    </form>
</div>
<script>
function doExamine(status, type) {
    var id = $("#id").val();
    var companyId = $("#companyId").val();
    var content = '确定'+((status==0)?'同意':'拒绝')+'该用户的申请？';
    parent.layer.confirm(content, {icon: 3, title:'提示'}, function(index){
        $('.layui-layer-btn0').css('pointer-events','none');
        $.ajax({
            type:"post",
            url:"${ctx}/permission/doExamine",
            dataType:"json",
            data:{"id": id, "companyId": companyId, "companyStatus": status, "companyType": type},
            success:function(data) {
                if(data.state){
                    layer.open({
                        icon: 1,
                        title: '信息',
                        skin: 'layer-ext-myskin',
                        shade: 0, //不显示遮罩
                        content: "审核成功！",
                        yes: function(){
                            parent.layer.closeAll();
                            parent.refresh();
                        },
                    });
                }else{
                    layer.open({
                        icon: 2,
                        title: '信息',
                        skin: 'layer-ext-myskin',
                        shade: 0, //不显示遮罩
                        content: "审核失败！",
                        yes: function(){
                            layer.closeAll();
                        },
                    });
                }
            }
        });
        parent.layer.close(index);
        //向服务端发送操作指令
    });
}

//图片放大
function fun_zheng(obj){
	  parent.layer.open({
	    type: 2,
	    title: false,
	    shadeClose: false,
	    shade: 0,
	    shift:-1,
	    area: ['570px','390px'],
	    content: obj.src
	  })
}

function openWin(obj){
	var img = $(obj);
	var winHeight = screen.availHeight;
	var winWidth = screen.availWidth;
	window.open(img.attr('src'),"_blank","left="+(winWidth-img.width())/2+",top="+(winHeight-img.height())/2+",toolbar=no,location=no,height="+img.height()+",width="+img.width());
}
</script>
</body>
</html>