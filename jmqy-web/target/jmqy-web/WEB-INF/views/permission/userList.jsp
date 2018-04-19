<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>权限管理</title>
    <style type="text/css">
    .button_left{
    	margin-top: 0px;
    }
    </style>
    <script>
        function examine(userId, companyName) {
            parent.layer.open({
                type: 2,
                title: "【"+companyName+'】审核信息',
                shadeClose: true,
                shade: 0.1,
                btn: ['同意', '拒绝'],
                btn1: function(index, layero){
                    var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
                    iframe.doExamine(0,2);
                },
                btn2: function(index, layero){
                    var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
                    iframe.doExamine(2,null);
                    return false;
                },
                skin: "layui-layer-molv",
                area: ["750px", "500px"],
                content: "${ctx}/permission/examine?id="+userId
            })
        }
    </script>
</head>
<body>
<form id="searchForm" action="${ctx}/permission/userList" method="post" class="layui-form">
    <input type="hidden" name="pageNum" value="${user.pageNum}"/>
    <input type="hidden" name="pageSize" value="${user.pageSize}"/>
    <div class="button_left">
        <label class="layui-form-label">类别:</label>
        <div class="layui-input-block">
            <input type="radio" name="companyType" value="1" title="个人用户" <c:if test="${user.companyType == 1}">checked</c:if> lay-filter="companyType">
            <input type="radio" name="companyType" value="2" title="包车企业 " <c:if test="${user.companyType == 2}">checked</c:if> lay-filter="companyType">
            <input type="radio" name="companyType" value="3" title="运输企业" <c:if test="${user.companyType == 3}">checked</c:if> lay-filter="companyType">
        </div>
    </div>
    <div class="button_right">
        <input type="text" name="param" class="layui-input" placeholder="请输入关键字" value="${user.param}">
        <button id="submitBtn" class="layui-btn layui-btn-small">搜索</button>
    </div>

    <c:if test="${user.companyType == 1}">

        <div class="cl"></div>
        <table class="layui-table">
            <colgroup>
                <col width="60">
                <col width="300">
                <col width="250">
                <col width="250">
                <col width="250">
                <col>
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>帐号</th>
                <th>用户名称</th>
                <th>手机号</th>
                <th>优惠率</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users.list}" var="user" varStatus="vs">
                <tr>
                    <td>${vs.index+1}</td>
                    <td>${user.userAccount}</td>
                    <td>${user.userName}</td>
                    <td>${user.userPhone}</td>
                    <td>${user.coefficient}</td>
                    <td>
                        <c:if test="${user.userStatus == 0}">启用</c:if>
                        <c:if test="${user.userStatus == 1}">禁用</c:if>
                        <c:if test="${user.userStatus == 2}">不通过</c:if>
                    </td>
                    <td style="white-space: nowrap;">
                        <c:if test="${user.companyType == 1 && user.companyStatus == 2}">
                            <a href="javascript:;" class="layui-btn layui-btn-mini layui-btn-disabled">不通过</a>
                        </c:if>
                        <c:if test="${user.companyType == 1 && (user.companyStatus == -1 || user.companyStatus == 2)}">
                            <a href="javascript:;" class="layui-btn layui-btn-danger layui-btn-mini" onclick="examine('${user.id}', '${user.companyName}');">企业审核</a>
                        </c:if>
                        <c:if test="${user.companyStatus != 1}">
                            <a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini plat_a"
                               onclick="fun_plat('${user.roleId}','${user.companyId}','${user.companyType}','${user.companyName}','${ctx}/permission/setPer');">设定权限</a>
                        </c:if>
                        <c:if test="${user.companyType == 1 || user.companyType == 2}">
                            <a href="javascript:setDiscount1('${user.companyCode}','${user.companyName}','${ctx}/rule/setDiscount');" class="layui-btn layui-btn-mini layui-btn-mini">优惠率</a>
                        </c:if>
                        <c:if test="${user.userStatus == 0}"><a href="javascript:updateById('${user.id}',1);" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-remove"></i> 禁用</a></c:if>
                        <c:if test="${user.userStatus == 1}"><a href="javascript:updateById('${user.id}',0);" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-remove"></i> 启用</a></c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <tags:page pages="${users}"/>


    </c:if>
    <c:if test="${user.companyType == 2}">

        <div class="cl"></div>
        <table class="layui-table">
            <colgroup>
                <col width="60">
                <col width="300">
                <col width="250">
                <col width="250">
                <col width="250">
                <col>
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>企业名称</th>
                <th>申请用户账号</th>
                <th>申请用户手机</th>
                <th>优惠率</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users.list}" var="user" varStatus="vs">
                <tr>
                    <td>${vs.index+1}</td>
                    <td>${user.companyName}</td>
                    <td>${user.userAccount}</td>
                    <td>${user.userPhone}</td>
                    <td>${user.coefficient}</td>
                    <td>
                        <c:if test="${user.companyStatus == 0}">启用</c:if>
                        <c:if test="${user.companyStatus == 1}">禁用</c:if>
                        <c:if test="${user.companyStatus == 2}">不通过</c:if>
                    </td>
                    <td style="white-space: nowrap;">
                        <c:if test="${user.companyType == 1 && user.companyStatus == 2}">
                            <a href="javascript:;" class="layui-btn layui-btn-mini layui-btn-disabled">不通过</a>
                        </c:if>
                        <c:if test="${user.companyType == 1 && (user.companyStatus == -1 || user.companyStatus == 2)}">
                            <a href="javascript:;" class="layui-btn layui-btn-danger layui-btn-mini" onclick="examine('${user.id}', '${user.companyName}');">企业审核</a>
                        </c:if>
                        <c:if test="${user.companyStatus != 1}">
                            <a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini plat_a"
                               onclick="fun_plat('${user.roleId}','${user.companyId}','${user.companyType}','${user.companyName}','${ctx}/permission/setPer');">设定权限</a>
                        </c:if>
                        <c:if test="${user.companyType == 1 || user.companyType == 2}">
                            <a href="javascript:setDiscount1('${user.companyCode}','${user.companyName}','${ctx}/rule/setDiscount');" class="layui-btn layui-btn-mini layui-btn-mini">优惠率</a>
                            <c:if test="${user.companyStatus == 0}"><a href="javascript:updateCharteredById('${user.companyCode}',1);" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-remove"></i> 禁用</a></c:if>
                            <c:if test="${user.companyStatus == 1}"><a href="javascript:updateCharteredById('${user.companyCode}',0);" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-remove"></i> 启用</a></c:if>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <tags:page pages="${users}"/>

    </c:if>
    <c:if test="${user.companyType == 3}">

        <div class="cl"></div>
        <table class="layui-table">
            <colgroup>
                <col width="60">
                <col width="300">
                <col width="250">
                <col width="250">
                <col width="250">
                <col>
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>账号</th>
                <th>企业名称</th>
                <th>单位地址</th>
                <th>负责人</th>
                <th>负责人手机</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users.list}" var="user" varStatus="vs">
                <tr>
                    <td>${vs.index+1}</td>
                    <td>${user.userAccount}</td>
                    <td><a onclick="setCompany('${user.companyCode}','3','${user.companyId}');" style="color: #e37502;">${user.companyName}</a></td>
                    <td>${user.address}</td>
                    <td>${user.userName}</td>
                    <td>${user.userPhone}</td>
                    <td>
                        <c:if test="${user.companyStatus == 0}">启用</c:if>
                        <c:if test="${user.companyStatus == 1}">禁用</c:if>
                        <c:if test="${user.companyStatus == 2}">不通过</c:if>
                    </td>
                    <td style="white-space: nowrap;">
                        <c:if test="${user.companyType == 1 && user.companyStatus == 2}">
                            <a href="javascript:;" class="layui-btn layui-btn-mini layui-btn-disabled">不通过</a>

                        </c:if>
                        <c:if test="${user.companyType == 1 && (user.companyStatus == -1 || user.companyStatus == 2)}">
                            <a href="javascript:;" class="layui-btn layui-btn-danger layui-btn-mini" onclick="examine('${user.id}', '${user.companyName}');">企业审核</a>
                        </c:if>
                        <c:if test="${user.companyStatus != 1}">
                            <a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-mini plat_a"
                               onclick="fun_plat('${user.roleId}','${user.companyId}','${user.companyType}','${user.companyName}','${ctx}/permission/setPer');">设定权限</a>
                               </c:if>
                        <c:if test="${user.companyType == 3}">
                            <c:if test="${user.companyStatus == 0}"><a href="javascript:updateCharteredById('${user.companyCode}',1);" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-remove"></i> 禁用</a></c:if>
                            <c:if test="${user.companyStatus == 1}"><a href="javascript:updateCharteredById('${user.companyCode}',0);" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-remove"></i> 启用</a></c:if>
                        </c:if>
                        <c:if test="${user.companyType == 1 || user.companyType == 2}">
                            <a href="javascript:setDiscount1('${user.companyCode}','${user.companyName}','${ctx}/rule/setDiscount');" class="layui-btn layui-btn-mini layui-btn-mini">优惠率</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <tags:page pages="${users}"/>

    </c:if>

</form>
<script type="text/javascript">

//单独设置运输企业的车辆和司机的禁用

function setCompany(companyCode,type,id){



    layer.open({
        type: 2,
        title: '司机车辆禁用',
        shadeClose: true,
        shade: 0.1,
        area: ['1000px', '500px'],
        skin: "layui-layer-molv",
        content:"${ctx}/permission/setType?companyCode="+companyCode+"&type="+type+"&companyId="+id
    })
}

//提交搜索表单
function selfSubmit() {
	$("#searchForm").submit();
}
//加载表单

var radiovaLue;
layui.use(['form','element','laydate'], function() {

	var form = layui.form;

	form.on('radio(companyType)', function(data){
		  //console.log(data.elem); //得到radio原始DOM对象
		  //console.log(data.value); //被点击的radio的value值
        var type = '${user.companyType}';
        if(type != data.value){
            $("[name='pageNum']").val(1);//第一页
        }
		  $('#submitBtn').click();

	});
});


function setDiscount1(companyCode, companyName, url){

    parent.layer.open({
        type: 2,
        title: '设置【'+companyName+'】优惠率',
        shadeClose: true,
        shade: 0.1,
        btn: ['确定', '取消'],
        yes: function(index, layero){

            var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
            iframe.$("#submit_btn").click();
        },
        skin: "layui-layer-molv",
        area: ['500px','250px'],
        content: url+"?companyCode="+companyCode,
        end: function () {
            $('#submitBtn').click();
        }

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
                            $('#submitBtn').click();
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


//包车方企业禁用

/*
* status:
* 0:启用 1:禁用 2:不通过
*/
function updateCharteredById(companyCode, status){


    var title = (status == 0?'启用':'禁用');
    parent.layer.confirm('确定'+title+'这个用户么?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type: "POST",
            url: "${ctx}/permission/chartered",
            data: {'companyCode': companyCode, 'status': status},
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
                            $('#submitBtn').click();
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