<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>车辆维护</title>
	<style>
		.button_right input{
			width: 150px;
		}
		.layui-form-label{
			padding: 9px 12px;
		}
		.layui-form-select .layui-edge{
			margin-top: 12px;
		}
	</style>
</head>
<body>
<section>
	<div class="container">
	<jsp:include page="/WEB-INF/layouts/left_menu.jsp"/>
		<main>
		<div class="position_all">
			<div class="position">您所在的位置：首页 -> 管理中心 -> 车辆维护</div>
		</div>
		<div class="info">
			<form id="searchForm" action="${ctx}/transport/carTravel/list" method="post" class="layui-form">
                <input type="hidden" name="pageNum" value="${carTravel.pageNum}"/>
                <input type="hidden" name="pageSize" value="${carTravel.pageSize}"/>
                <input type="hidden" id="order" name="order" value="${carTravel.order}"/>
				<input type="hidden" id="sortType" name="sortType" value="${carTravel.sortType}"/>

				<!-- 模糊查询字段-->
				<input type="hidden" id="searchType" name="searchType" value="0">
				<input type="hidden" id="carNum" name="carNum" value="${carTravel.carNum}">
				<input type="hidden" id="carState" name="carState" value="${carTravel.carState}">
				<div class="button_left">
					<a href="javascript:update('');" class="layui-btn layui-btn-primary layui-btn-mini car_a"><i class="icon-plus-sign"></i> 新增</a>
				</div>
				<div class="button_right">
					<div class="layui-inline">
						<label class="layui-form-label">维护时间:</label>
						<div class="layui-input-inline" style="top: 5px;">
							<input class="layui-input search_text input_date" id="beginTime" name="beginTime" type="text" value='${carTravel.beginTime}'
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>_
						</div>
						<div class="layui-input-inline" style="top: 5px;">
							<input class="layui-input search_text input_date" id="endTimes" name="endTimes" type="text" value='${carTravel.endTimes}'
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						</div>
					</div>

					<div class="layui-inline">
						<label class="layui-form-label">查询内容选项:</label>
						<div class="layui-input-inline" style="top: 5px;">
						<select name="dim" id="dim" lay-filter="dim1" >
							<option value="0" <c:if test="${carTravel.carNum != null and carTravel.carNum !=''}"> selected="selected"</c:if>>车牌号码</option>
							<!--<option value="1" <c:if test="${carTravel.driverName != null and carTravel.driverName !=''}"> selected="selected"</c:if>>司机姓名</option> -->
							<option value="2" <c:if test="${carTravel.carState != null and carTravel.carState !=''}"> selected="selected"</c:if>>车辆状况</option>
						</select>
						</div>
					</div>
					<div class="layui-inline">
						<input class="search_text" id="dimValue" name="dimValue"  type="text"  style="cursor: pointer;" placeholder="输入查询内容"/>
						<a class="layui-btn layui-btn-danger layui-btn-small" href="javascript:selfSubmit();">搜索</a>
					</div>
				</div>
				<div class="cl"></div>
				<div class="layui-form">
					<table class="layui-table">
						<colgroup>
							<col width="50">
							<col width="150">
							<col width="150">
							<col width="200">
							<col width="200">
							<col width="200">
						</colgroup>
						<thead>
							<tr>
								<th><input name="" lay-skin="primary" lay-filter="allChoose" type="checkbox"></th>
								<th><a id="c.car_num" onclick="sort(this);" style="cursor: pointer;">车牌号</a></th>
						       <!--  <th>司机</th> -->
						        <th><a id="car_state" onclick="sort(this);" style="cursor: pointer;">车辆状况</a></th>
						        <th><a id="start_time" onclick="sort(this);" style="cursor: pointer;">开始时间</a></th>
						        <th><a id="end_time" onclick="sort(this);" style="cursor: pointer;">结束时间</a></th>
						        <th>操作</th>
							</tr>
						</thead>
						<tbody>
                           <c:forEach items="${carTravels.list}" var="carTravel" varStatus="status">
                               <tr>
                                   <td><input name="" lay-skin="primary" type="checkbox"></td>
                                   <td>${carTravel.carNum}</td>
                                 <%--   <td>${carTravel.driverName}</td> --%>
                                   <td>
	                                   	<c:if test="${carTravel.carState == 1}">保养</c:if>
	                                   	<c:if test="${carTravel.carState == 2}">维修</c:if>
	                                   	<c:if test="${carTravel.carState == 3}">事故</c:if>
									    <c:if test="${carTravel.carState == 4}">自运营</c:if>
                                   </td>
                                   <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${carTravel.startTime}" /></td>
                                   <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${carTravel.endTime}" /></td>
                                   <td>
	                                   	<a href="javascript:update('${carTravel.id}');" class="layui-btn layui-btn-primary layui-btn-mini caradmin_a"><i class="icon-edit"></i> 修改</a>
	                                   	<a href="javascript:updateById('${carTravel.id}',1);" class="layui-btn layui-btn-primary layui-btn-mini"><i class="icon-remove"></i> 删除</a>
                                   </td>
                               </tr>
                           </c:forEach>
						</tbody>
					</table>
				</div>
			</form>
		</div>
        <tags:page pages="${carTravels}"/>
		</main>
	</div>
</section>
<script type="text/javascript">
//文档加载完毕执行
$(function() {
	var message = '${message}';
	if(message != null && "" != message){
		var msg = message.split("@");
		setTimeout(function(){
			layer.alert(msg[0], {icon: msg[1]});
		}, 500);


	}
});

//提交搜索表单

//搜索框的状态记忆
var selectValue = $("#dim").val();
if(selectValue==null && selectValue==''){
    selectValue='0';
}


layui.use('form', function() {

    var form = layui.form;

    //各种基于事件的操作，下面会有进一步介绍
    form.on('select(dim1)', function(data){
        selectValue=data.value; //得到被选中的值
    });
})


//提交搜索表单
function selfSubmit() {

    if($("#dimValue").val() == null || $("#dimValue").val()==''){

        $("#searchType").val('0');
    }else{
        $("#searchType").val("1");
        var setValue = $("#dimValue").val();

        if(selectValue =='0'){
            set('carNum',setValue);
        }else if(selectValue =='1'){
            set('driverName',setValue);
        }else if(selectValue =='2'){
            var num = 0;
            if(setValue.indexOf("保养")!=-1){
                num = 1;
            }else if(setValue.indexOf("维修")!=-1){
                num = 2;
            }else if(setValue.indexOf("事故")!=-1){
                num = 3;
            }
            set('carState',num);
        }
    }
    $("#pageNum").val("1");
    $("#pageSize").val("10");

	$("#searchForm").submit();
}


function  set(id,value) {
    $("#carNum").val('');
    $("#driverName").val('');
    $("#carState").val('');

    $("#"+id).val(value);
}


//跳到第一页
function selfSearch() {
	$("[name='pageNum']").val(1);//第一页
	selfSubmit();
}

/*****、新增、修改/行程*****/
function update(id){
  layer.open({
    type: 2,
    title: '行程添加修改',
    shadeClose: true,
    shade: 0.5,
    btn: ['提交', '取消'],
    yes: function(index, layero){
    	 var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
    	 iframeWin.laySubmit();//执行iframe页的方法：
        // layer.close(index)
    },
    btn2: function(index, layero){
    	selfSearch();
    },
    cancel: function(index, layero){ 
    	selfSearch();
    },
    area: ['950px','420px'],
    content: '${ctx}/transport/carTravel/form?id='+id
  })
}

/*
* status:
* 0在职，1休假，2离职
* recordStatus:
* 0正常，1删除，
*/
function updateById(id, recordStatus){
	var typeStr = '删除';
    layer.confirm('真的'+typeStr+'这个数据么?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type: "POST",
            url: "${ctx}/transport/carTravel/updateById",
            data: {'id': id, 'recordStatus': recordStatus},
            dataType: "json",
            success: function(data){
            	var msg = '';
            	var icon = 1;
            	if(data < 0){
            		msg = '失败，系统错误！';
            		icon = 2;//失败
            	}else{
            		if(data == 0){
            			msg = '失败';
            		}else{
            			msg = '成功';
            		}
            	}
                layer.msg(typeStr+msg,{
                    time: 1000,
                    skin: 'layui-layer-molv',
                    area: '20px',
                    icon: icon
                }, function(){
                	selfSubmit();
                });
            }
        });
        layer.close(index);
        //向服务端发送操作指令
    });

}
</script>
</body>
</html>