<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>首页</title>

</head>
<body>
<main>
    <aside>
        <div class="pla_box">
            <div class="pla_title"><h1><i class="icon-th-list"></i></h1></div>
            <ul class="pla_list" style="font-size: 13px;overflow: auto;">
                <c:forEach items="${menus}" var="menu" varStatus="vs">
                    <li <c:if test="${vs.index==0}">class="pla_list_action"</c:if>>
                        <a href="javascript:;" onclick="iframe('${menu.menuPath}', '${menu.menuItem}');">
                             ${menu.menuItem}<c:if test="${fn:length(menu.childMenus) > 0}"> <i class="icon-caret-left"></i></c:if>
                        </a>
                    </li>
                    <c:if test="${fn:length(menu.childMenus) > 0}">
                        <dl class="pla_hide pla_dl2">
                            <c:forEach items="${menu.childMenus}" var="childMenu">
                                <dd><a href="javascript:;" onclick="iframe('${childMenu.menuPath}', '${childMenu.menuItem}');">${childMenu.menuItem}</a></dd>
                            </c:forEach>
                        </dl>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
    </aside>
    <div class="aside_right">
    </div>
    <section>
        <div class="pla_box">
            <div class="pla_title2">
                <div class="pla_position">
                    <i class="icon-caret-right"></i> 当前位置：<span>权限管理</span>
                </div>
                <iframe class="pla_iframe" width="100%" src="${ctx}/${menu.menuPath}" frameborder="0" style="overflow: auto;"></iframe>
            </div>
        </div>
    </section>
</main>
<script>

    function iframe(url, menuName) {

        if(url != null && '' != url){
            $('.pla_iframe').attr('src','${ctx}/'+url);
            $(".pla_position span").text(menuName);
        }
    }
    function refresh() {
        $('.pla_iframe').attr('src',$('.pla_iframe').attr('src'));
    }
</script>
</body>
</html>