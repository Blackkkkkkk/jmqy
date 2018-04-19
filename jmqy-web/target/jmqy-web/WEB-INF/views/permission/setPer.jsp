<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>权限设置</title>
    <style>
        body{
            min-width: 0;
        }
        li{
            display: inline;
            float: left;
        }
        .layui-table{
            border: none;
        }
        .layui-table thead tr{
            background: none
        }
        .layui-table tr th{
            border: none;
            padding-left: 50px;
        }
        .layui-table tr td{
            border: none;
            padding-left: 100px;
        }
        .layui-table span{
            position: relative;
            top: 3px;
        }
    </style>
</head>
<body>
<div>
    <input type="hidden" name="roleId" value="${user.roleId}"/>
    <input type="hidden" name="companyId" value="${user.companyId}"/>
    <ul <c:if test="${fn:length(menus)*233<900}">style="width:${fn:length(menus)*233}px; margin-left: auto; margin-right: auto;"</c:if>>
    <c:forEach items="${menus}" var="menu">
        <li>
            <table class="layui-form layui-table">
                <thead>
                    <tr>
                        <th>
                            <input name="" lay-skin="primary" lay-filter="allChoose" type="checkbox"
                                   <c:if test="${fn:contains(menuIds, menu.id)}">checked</c:if> value="${menu.id}">
                            <span>&nbsp;${menu.menuItem}</span>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${fn:length(menu.childMenus) > 0}">
                        <c:forEach items="${menu.childMenus}" var="childMenu">
                            <tr>
                                <td>
                                    <input class="aaa1" name="singleCheck" lay-skin="primary" lay-filter="singleChoose" type="checkbox"
                                           <c:if test="${fn:contains(menuIds, childMenu.id)}">checked</c:if> value="${childMenu.id}">
                                    <span>&nbsp;${childMenu.menuItem}</span>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
        </li>
    </c:forEach>
    </ul>
</div>
<div style="display: none;">
    <tags:page pages="${pageInfo}"/>
</div>
<script>
    function setPer() {
        var roleId = $("[name='roleId']").val();
        var companyId = $("[name='companyId']").val();
        var ids = getCheckedIdArray().join();
        var dataJson = {"roleId": roleId, "companyId": companyId, "ids": ids};
        var content = '确定提交吗？';
        parent.layer.confirm(content, {icon: 3, title:'提示'}, function(index){
            $('.layui-layer-btn0').css('pointer-events','none');
            $.ajax({
                type:"post",
                url:"${ctx}/permission/doSetPer",
                dataType:"json",
                data:dataJson,
                success:function(data) {
                    if(data.state){
                        layer.open({
                            icon: 1,
                            title: '信息',
                            skin: 'layer-ext-myskin',
                            shade: 0, //不显示遮罩
                            content: "操作成功！",
                            yes: function(){
                            	parent.$("#searchForm").submit();
                                parent.layer.closeAll();
                            },
                        });
                    }else{
                        layer.open({
                            icon: 2,
                            title: '信息',
                            skin: 'layer-ext-myskin',
                            shade: 0, //不显示遮罩
                            content: "操作失败！",
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
</script>
</body>
</html>