<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>角色管理</title>
</head>
<body>
<form id="searchForm" action="${ctx}/sys/role/list" method="post" class="layui-form">
    <input type="hidden" name="pageNum" value="${role.pageNum}"/>
    <input type="hidden" name="pageSize" value="${role.pageSize}"/>
    <div class="button_left">
        <a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini car_a" onclick="update('', '')"><i class="icon-plus-sign"></i> 新增</a>
    </div>
    <div class="button_right">
        <input type="text" name="roleName" class="layui-input" placeholder="请输入关键字" value="${role.roleName}">
        <button class="layui-btn layui-btn-small">搜索</button>
    </div>
    <div class="cl"></div>
    <table class="layui-table">
        <colgroup>
            <col width="60">
            <col width="200">
            <col width="290">
            <col width="280">
            <col width="200">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>序号</th>
            <th>角色名称</th>
            <th>角色描述</th>
            <th>备注</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${roles.list}" var="role" varStatus="status">
            <tr>
                <td>${status.index+1}</td>
                <td>${role.roleName}</td>
                <td>${role.roleDescribe}</td>
                <td>${role.remark}</td>
                <td>
                    <c:if test="${role.status == 0}">启用</c:if>
                    <c:if test="${role.status == 1}">禁用</c:if>
                </td>
                <td style="white-space: nowrap;">
                    <a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini caradmin_a" onclick="update('${role.id}', '${role.roleName}')"><i class="icon-edit"></i> 修改</a>
                    <a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini caradmin_a" onclick="authorize('${role.id}','${role.roleDescribe}')"><i class="icon-edit"></i> 授权</a>
                    <c:if test="${role.status == 0}">
                        <a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini" onclick="updateById('${role.id}',1)"><i class="icon-remove"></i> 禁用</a>
                    </c:if>
                    <c:if test="${role.status == 1}">
                        <a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini" onclick="updateById('${role.id}',0)"><i class="icon-remove"></i> 启用</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <tags:page pages="${roles}"/>
</form>
<script>
    /*****、新增、修改*****/
    function update(id, name){
        var title = (id==''?'新增角色':'修改【'+name+'】角色');
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
            content: '${ctx}/sys/role/form?id='+id
        })
    }
    /****给角色授权页面*****/
    function authorize(id, roleName){
        parent.layer.open({
            type: 2,
            title: '给角色【'+roleName+'】分配菜单',
            shadeClose: true,
            shade: 0.1,
            btn: ['确定', '取消'],
            yes: function(index, layero){
                var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
                iframe.setPer();
            },
            skin: "layui-layer-molv",
            area: ["900px", "500px"],
            content: '${ctx}/sys/authorize?id='+id
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
                url: "${ctx}/sys/role/updateById",
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