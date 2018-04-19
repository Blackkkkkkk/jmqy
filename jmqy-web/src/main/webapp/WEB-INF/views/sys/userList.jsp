<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>用户管理</title>
</head>
<body>
<form id="searchForm" action="${ctx}/sys/user/list" method="post" class="layui-form">
    <input type="hidden" name="pageNum" value="${user.pageNum}"/>
    <input type="hidden" name="pageSize" value="${user.pageSize}"/>
    <div class="button_left">
        <a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini car_a" onclick="update('', '','insert')"><i class="icon-plus-sign"></i> 新增</a>
    </div>
    <div class="button_right">
        <input type="text" name="userName" class="layui-input" placeholder="请输入关键字" value="${user.userName}">
        <button class="layui-btn layui-btn-small">搜索</button>
    </div>
    <div class="cl"></div>
    <table class="layui-table">
        <colgroup>
            <col width="60">
            <col width="160">
            <col width="160">
            <col width="110">
            <col width="210">
            <col width="300">
            <col width="110">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>序号</th>
            <th>姓名</th>
            <th>账号</th>
            <th>性别</th>
            <th>手机号</th>
            <th>身份证</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users.list}" var="user" varStatus="status">
            <tr>
                <td>${status.index+1}</td>
                <td>${user.userName}</td>
                <td>${user.userAccount}</td>
                <td>
                    <c:if test="${user.sex == 0}">女</c:if>
                    <c:if test="${user.sex == 1}">男</c:if>
                </td>
                <td>${user.phone}</td>
                <td>${user.idCard}</td>
                <td>
                    <c:if test="${user.status == 0}">启用</c:if>
                    <c:if test="${user.status == 1}">禁用</c:if>
                    <c:if test="${user.status == 2}">不通过</c:if>
                </td>
                <td style="white-space: nowrap;">
                    <a href="javascript:update('${user.id}','${user.userName}','update');" class="layui-btn layui-btn-primary layui-btn-mini caradmin_a"><i class="icon-edit"></i> 修改</a>
                    <c:if test="${user.status == 0}"><a href="javascript:updateById('${user.id}',1);" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-remove"></i> 禁用</a></c:if>
                    <c:if test="${user.status == 1}"><a href="javascript:updateById('${user.id}',0);" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-remove"></i> 启用</a></c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <tags:page pages="${users}"/>
</form>
<script>
    /*****、新增、修改*****/
    function update(id, name,type){

        var title = (id==''?'新增用户':'修改【'+name+'】用户');
        parent.layer.open({
            type: 2,
            title: title,
            shadeClose: true,
            shade: 0.1,
            btn: ['提交', '取消'],
            yes: function(index, layero){
                var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
                iframe.$('#submit_btn').submit();//执行iframe页的方法：
            },
            skin: "layui-layer-molv",
            area: ['650px','450px'],
            content: '${ctx}/sys/user/form?id='+id
        })
    }

    /*
     * status:
     * 0:启用 1:禁用 2:不通过
     */
    function updateById(id, status){
        var title = (status == 0?'启用':'禁用');
        parent.layer.confirm('确定'+title+'这个用户么?', {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type: "POST",
                url: "${ctx}/sys/user/updateById",
                data: {'id': id, 'status': status},
                dataType: "json",
                success: function(data){
                    if(data > 0){
                        parent.layer.open({
                            icon: 1,
                            title: '信息',
                            skin: 'layer-ext-myskin',
                            shade: 0, //不显示遮罩
                            content: "操作成功！",
                            yes: function(){
                                parent.layer.closeAll();
                                parent.refresh();
                            },
                        });
                    }else{
                        parent.layer.open({
                            icon: 2,
                            title: '信息',
                            skin: 'layer-ext-myskin',
                            shade: 0, //不显示遮罩
                            content: "操作失败！"
                        });
                    }
                }
            });
        });
    }
</script>
</body>
</html>