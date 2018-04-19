<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>系统设置</title>
<style>
.searh label{
  width:150px;
}
.searh .search_text{
  width:300px;
  border-radius:4px;
}
.searh1 {
  background: #fff;
  overflow: visible;
}
.searh_box{
  height:504px !important;
}
.search_text{
	padding:0 10px;
}
.input_date{
	width:302px !important;
	padding-left:30px;
}
.searh .search_text {
  width: 280px;
}

.title {
	border-bottom: 1px solid #0683b1;
	line-height: 35px;
	font-size: 18px;
	color: #0683b1;
	padding-left: 10px;
}

.coe-box {
	width: 855px;
	/*height:228px;*/
	border: 1px solid #ddd;
}

.coe-box th {
	background: #eee;
}

.coe-box tbody {
	/*height:100px;*/
	overflow-y: scroll;
}

.coe-table-content {
	height: 100px;
	overflow-y: scroll;
	display: block;
}

.coe-table-content td {
	border-left: 1px solid #ddd;
	border-bottom: 1px solid #ddd;
}

.coe-table-content td:nth-child(1) {
	border-left: none;
}

.coe-table-content tr:last-child td {
	/*border-bottom:none;*/

}

.coe-table-title th {
	position: relative;
}

.coe-box td, .coe-box th {
	text-align: center;
	padding: 5px 10px;
}

.coe-box1 {
	margin: 20px;
}

.coe-table-foot {
	background: #eee;
}

.coe-table-foot input {
	width: 100px;
}

.coe-table-content input {
	border: none;
	width: 75px;
}
.coe-box .layui-form-select input{
	width:120px;
	height:22px;
}
.coe-box .layui-form-select{
	width:120px;
}


</style>
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=1w2xcsGWIGbPyhIngYF2uBK0"></script>
<script type="text/javascript" src="https://api.map.baidu.com/getscript?v=2.0&ak=1w2xcsGWIGbPyhIngYF2uBK0&services=&t=20140411133140"></script>
</head>
<body>
<section>
	<div class="container">
		<jsp:include page="/WEB-INF/layouts/left_menu.jsp" />
		<main>
		<div class="position_all">
			<div class="position">您所在的位置：首页 -> 管理中心 -> 系统设置</div>
		</div>
		<form id="form" class="layui-form" action="" method="post">
		<input type="hidden" id="id" name="id" value="${backOrder.id}"/>
		<input type="hidden" name="type" value="${backOrder.type}"/>
		<div class="searh searh1 padding10">
			<table class="layui-form coe-box1">
			<tr style="height: 10px;"></tr>
				<td valign="top" align="left"><b>空接单单价系数(公里)：</b></td>
			<tr>
				<td>
					<div class="coe-box">
						<table class="coe-table coe-table-title">
							<colgroup>
								<col width="200">
								<col width="50">
								<col width="200">
								<col width="300">
								<col width="300">
							</colgroup>
							<thead>
							<tr>
								<th>公里</th>
								<th>—</th>
								<th>公里</th>
								<th>单价</th>
								<th>操作</th>
							</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						<table class="coe-table coe-table-content">
							<colgroup>
								<col width="200">
								<col width="50">
								<col width="200">
								<col width="300">
								<col width="300">
							</colgroup>
							<tbody>
							<c:forEach items="${rules}" var="r" varStatus="vs">
								<c:if test="${r.ruleType==16}">
									<tr id="${r.id}">
										<td><input id="defaultRange_${r.id}" type="text" class="coe-input" value="${r.defaultRange}"></td>
										<td>—</td>
										<td><input id="toRange_${r.id}" type="text" class="coe-input" value="${r.toRange}"></td>
										<td><input id="coefficient_${r.id}" type="text" class="coe-input" value="${r.coefficient}"></td>
										<td>
											<a href="javascript:del('${r.id}');" class="layui-btn layui-btn-mini">删除</a>
											<a href="javascript:update('${r.id}');" class="layui-btn layui-btn-mini">确认修改</a>
										</td>
									</tr>
								</c:if>
							</c:forEach>
							</tbody>
						</table>
						<table class="coe-table coe-table-foot">
							<colgroup>
								<col width="170">
								<col width="10">
								<col width="180">
								<col width="220">
								<col width="300">
							</colgroup>
							<tbody>
							<tr>
								<td><input class="input1" type="text"></td>
								<td>—</td>
								<td><input class="input2" type="text"></td>
								<td><input class="input3" type="text"></td>
								<td>
									<a href="javascript:;" class="layui-btn layui-btn-mini layui-btn-danger"onclick="scrollBottom(this,16)">添加</a>
									<!-- <a href="" class="layui-btn layui-btn-mini layui-btn-danger">批量确认</a> -->
								</td>
							</tr>
							</tbody>
						</table>
					</div>

				</td>
			</tr>
			</table>
		</div>
		</form>
		</main>
	</div>
</section>
<script type="text/javascript">

    function del(id){
        $.ajax({
            type:"post",
            url:"${ctx}/rule/delete",
            dataType:"json",
            data:{"id": id},
            success:function(data) {
                if(data.state){
                    layer.msg('删除成功', {icon: 1,time: 1000}, function(){
                        $('#'+id).remove();
                    });
                }else{
                    layer.msg('删除失败', {icon: 2,time: 1000}, function(){
                    });
                }
            }
        });
    }

    function update(id){
        var defaultRange = $('#defaultRange_'+id).val();
        var toRange = $('#toRange_'+id).val();
        var coefficient = $('#coefficient_'+id).val();
        if(defaultRange == ''){
            $('#defaultRange_'+id).focus();
            layer.msg('最小值不能为空', {icon: 2,time: 1000}, function(){
            });
            return false;
        }
        if(toRange == ''){
            $('#toRange_'+id).focus();
            layer.msg('最大值不能为空', {icon: 2,time: 1000}, function(){
            });
            return false;
        }
        if(coefficient == ''){
            $('#coefficient_'+id).focus();
            layer.msg('系数不能为空', {icon: 2,time: 1000}, function(){
            });
            return false;
        }
        $.ajax({
            type:"post",
            url:"${ctx}/rule/doSetRule",
            dataType:"json",
            data:{"id": id, "defaultRange": defaultRange, "toRange": toRange, "coefficient": coefficient},
            success:function(data) {
                if(data.state){
                    layer.msg('设置成功', {icon: 1,time: 1000}, function(){
                    });
                }else{
                    layer.msg('设置失败', {icon: 2,time: 1000}, function(){
                    });
                }
            }
        });
    }


    function scrollBottom(elem,type){

		var dom = $(elem).parents('.coe-table-foot').prev('.coe-table-content');

        console.log(dom)
        var cloneDom = $(elem).parent().parent();
        var s1 = cloneDom.find('.input1').val(),
            s2 = cloneDom.find('.input2').val(),
            s3 = cloneDom.find('.input3').val();
        var s4 = $("[name='quiz3']").val();
        if(s1 == ''){
            cloneDom.find('.input1').focus();
            layer.msg('最小值不能为空', {icon: 2,time: 1000}, function(){
            });
            return false;
        }
        if(s2 == ''){
            cloneDom.find('.input2').focus();
            layer.msg('最大值不能为空', {icon: 2,time: 1000}, function(){
            });
            return false;
        }
        if(s3 == ''){
            cloneDom.find('.input3').focus();
            layer.msg('系数不能为空', {icon: 2,time: 1000}, function(){
            });
            return false;
        }
        if(s4 == ''){
        }
        if(s1 != '' && s2 != '' && s3 != '' ){
            $.ajax({
                type:"post",
                url:"${ctx}/rule/save",
                dataType:"json",
                data:{"parentId": $('#id').val(), "defaultRange": s1, "toRange": s2, "coefficient": s3, "ruleType": type,"endPlance":s4},
                success:function(data) {

                    if(data.state){
                            var rule = data.rule;
                            var string = '<tr><td><input id="defaultRange_' + rule.id + '" type="text" class="coe-input" value="' + s1 + '"></td><td>—</td>'
                                + '<td><input id="toRange_' + rule.id + '" type="text" class="coe-input" value="' + s2 + '"></td>'
                                + '<td><input id="coefficient_' + rule.id + '" type="text" class="coe-input" value="' + s3 + '"></td>'
                                + '<td><a href="javascript:del(\'' + rule.id + '\');" class="layui-btn layui-btn-mini">删除</a>'
                                + '<a href="javascript:update(\'' + rule.id + '\');" class="layui-btn layui-btn-mini">确认修改</a></td></tr>';
                            dom.find('tbody').after(string)
                            dom.scrollTop($('.coe-table-content')[0].scrollHeight);
                            layer.msg('添加成功', {icon: 1, time: 1000}, function () {
                                cloneDom.find('.input1').val(''),
                                    cloneDom.find('.input2').val(''),
                                    cloneDom.find('.input3').val('');
                            });
                    }else{
                        layer.msg('添加失败', {icon: 2,time: 1000}, function(){
                        });
                    }
                }
            });
        }
    }

</script>
</body>
</html>