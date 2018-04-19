<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>个人中心</title>
</head>
<body>
<section>
    <div class="container">
        <aside>
            <div class="aside_title">${asideTitle}</div>
            <div class="aside_menu">
                <ul>
                    <c:forEach items="${menus}" var="menu" varStatus="vs">
                        <li>
                            <a href="javascript:;">
                                ${menu.menuItem}<i class="icon-sort-down"></i>
                            </a>
                        </li>
                        <c:if test="${fn:length(menu.childMenus) > 0}">
                            <dl>
                                <c:forEach items="${menu.childMenus}" var="childMenu">
                                    <dd id="${childMenu.menuItem}" style="cursor: pointer;" onclick="left_forward('${childMenu.menuPath}', '${childMenu.menuItem}');">
                                        <a href="javascript:;">${childMenu.menuItem}</a><span class='red'></span>
                                    </dd>
                                </c:forEach>
                            </dl>
                        </c:if>
                    </c:forEach>
                </ul>
            </div>
        </aside>
        <main style="margin-left: 6px;">
            <div class="position_all">
                <div class="position">您所在的位置：首页 -> 个人中心 -> <span>${menu.menuItem}</span></div>
            </div>
            <iframe class="pla_iframe" width="100%" src="${ctx}/${menu.menuPath}" frameborder="0" style="overflow: hidden;"></iframe>
        </main>
    </div>
</section>
<script>
    $(document).ready(function() {
        init_css();
    });
    //初始化样式
    function init_css() {
        var position = $(".position").text();
        if(position != null && '' != position){
            position = position.substring((position.lastIndexOf('->')+2), position.length);
            position = position.replace(" ","");
            $('aside div ul dl dd').css("background","");
            $('aside div ul dl dd a').css("color","");
            $("#"+position).css("background","#ececec");
            $("#"+position+" a").css("color","#2577e3");
        }
        var pendAmount = $("#未接订单 a").text();
        if(pendAmount != null && '' != pendAmount){
            statistics(pendAmount, "${ctx}/transport/order/statisticsPend");
        }
        var certAmount = $("#证件提醒 a").text();
        if(certAmount != null && '' != certAmount){
            statistics(certAmount, "${ctx}/transport/certificate/statisticsDue");
        }
        $('aside div ul li').click(function(){
            if($(this).find('i').attr('class') == 'icon-sort-down'){
                $(this).find('i').attr('class','icon-sort-up');
            }else{
                $(this).find('i').attr('class','icon-sort-down');
            }
        })
    }
    //数量统计
    function statistics(id, url){
        $.ajax({
            type: "POST",
            url: url,
            data: {},
            dataType: "json",
            success: function(data){
                $('#'+id+" span").text("("+data+")");
            }
        });
    }
    //跳转
    function left_forward(url, name) {
        if(url != null && '' != url && 'undefined' != url){
            $('.pla_iframe').attr('src','${ctx}/'+url);
            $(".position span").text(name);
            init_css();
        }
    }
    //刷新
    function refresh() {
        $('.pla_iframe').attr('src',$('.pla_iframe').attr('src'));
    }
</script>
</body>
</html>