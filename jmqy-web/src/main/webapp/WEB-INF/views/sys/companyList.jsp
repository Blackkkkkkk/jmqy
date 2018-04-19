<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>运输企业管理</title>
</head>
<body>
<form id="searchForm" action="${ctx}/sys/company/list" method="post" class="layui-form">
    <input type="hidden" name="pageNum" value="${company.pageNum}"/>
    <input type="hidden" name="pageSize" value="${company.pageSize}"/>
    <div class="button_left">
        <a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini car_a" onclick="add_company()"><i class="icon-plus-sign"></i> 新增企业</a>
    </div>
    <div class="button_right">
        <input type="text" name="param" class="layui-input" placeholder="请输入关键字" value="${company.param}">
        <button class="layui-btn layui-btn-small">搜索</button>
    </div>
    <div class="cl"></div>
    <table class="layui-table">
        <colgroup>
            <col width="60">
            <col width="200">
            <col width="180">
            <col width="400">
            <col width="150">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>序号</th>
            <th>企业名称</th>
            <th>企业主账号</th>
            <th>企业地址</th>
            <th>企业工号前缀</th>
            <th>注册时间</th>
            <th>操作</th>
            <th>设置附加项</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pageInfo.list}" var="company" varStatus="status">
            <tr>
                <td>${status.index+1}</td>
                <td><a href="javascript:;"  onclick="detail('${company.companyCode}');" style="color: #e37502;">${company.companyName}</a></td>
                <td>${company.userAccount}</td>
                <td>${company.companyAddress}</td>
                <td><input type="text" id="${company.id}" class="layui-input" value="${company.jobNumPre}" maxlength="3"></td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${company.registerDate}"/></td>
                <td>
                    <a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini plat2_a" onclick="update_('${company.id}');">修改</a>
                </td>
                <td>
                    <a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini plat2_a" onclick="update_price('${company.companyCode}');">设置</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <tags:page pages="${pageInfo}"/>
</form>
<script>
    function add_company() {
        parent.layer.open({
            type: 2,
            title: '新增运输企业',
            shadeClose: true,
            shade: 0.1,
            btn: ['确定', '重置'],
            yes: function(index, layero){
                var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
                iframe.$('.sign-next').click();
            },
            btn2: function (index, layero) {
                var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
                iframe.$('.layui-btn-primary').click();
                return false;
            },
            skin: "layui-layer-molv",
            area: ["860px", "70%"],
            content: '${ctx}/sys/company/form'
        })
    }
    function update_(id) {
        var jobNum = $("#"+id).val();
        if(!/(^[0-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.exec(jobNum)){
            layer.msg("请输入正确的数字！", {icon: 5}, function () {
                parent.refresh();
            });
        }else{
            if(jobNum.length < 3){
                layer.msg("请输入三位数字！", {icon: 5}, function () {
                    parent.refresh();
                });
            }else{
                $.ajax({
                    type:"post",
                    url:"${ctx}/sys/company/set",
                    dataType:"json",
                    data:{"id": id, "jobNumPre": jobNum},
                    success:function(data) {
                        if(data.state){
                            layer.open({
                                icon: 1,
                                title: '信息',
                                skin: 'layer-ext-myskin',
                                shade: 0, //不显示遮罩
                                content: "设置成功！",
                                yes: function(){
                                    parent.refresh();
                                },
                            });
                        }else{
                            var msg = data.msg;
                            layer.open({
                                icon: 2,
                                title: '信息',
                                skin: 'layer-ext-myskin',
                                shade: 0, //不显示遮罩
                                content: msg,
                                yes: function(){
                                    parent.refresh();
                                },
                            });
                        }
                    }
                });
            }
        }
    }

    function update_price(value){
       // var title = (id==''?'新增用户':'修改【'+name+'】用户');
        layer.open({
            type: 2,
            title: "设置价格",
            shadeClose: true,
            shade: 0.1,
            btn: ['提交', '取消'],
            yes: function(index, layero){


                var iframeWin = window[layero.find('iframe')[0]['name']];
                iframeWin.a();

               // var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
               // iframe.$('#submit_btn').submit();//执行iframe页的方法：

              //  iframe.a();
            },
            skin: "layui-layer-molv",
            area: ['650px','450px'],
            content: '${ctx}/sys/company/PriceSet?companyCode='+value
        })
    }


    function detail(companyCode){


        top.layer.open({
            type: 2,
            title: '运输方详情',
            shadeClose: true,
            shade: 0.5,
            area: ['90%', '600px'],
            content: "${ctx}/sys/company/detail?companyCode=" + companyCode
        })

    }

</script>
</body>
</html>