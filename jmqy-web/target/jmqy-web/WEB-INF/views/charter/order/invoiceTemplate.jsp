<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>申请发票</title>
<style>
  table tr{
    line-height: 50px;
  }
  table td{
  	color:#999;
  }
  table td:nth-child(odd){
   text-align:right;
   color:#333;
  }
</style>	
</head>
<body class="window">
	<form id="invoiceForm" class="layui-form" action="" lay-filter="invoiceForm">
	<input type="hidden" name="orderCode" value="${order.orderCode}"/>
	<input type="hidden" name="invoiceAmount" value="${order.amount}"/>
        <table>
	        <tr>
	          <td>发票抬头：</td>
	          <td><input type="text" class="layui-input" name="invoiceTitle" id="invoiceTitle" lay-verify="required" value="${invoice.invoiceTitle}"/></td>
	        </tr>
	        <tr>
	          <td>纳税人识别号：</td>
	          <td><input type="text" class="layui-input" name="taxpayerNumber" id="taxpayerNumber" lay-verify="required" value="${invoice.taxpayerNumber}"/></td>
	        </tr>
	        <tr>
	          <td>发票内容：</td>
	          <td><input type="text" class="layui-input" name="invoiceContent" id="invoiceContent" lay-verify="required" maxlength="100" value="${invoice.invoiceContent}"/></td>
	        </tr>
	        <tr>
	          <td>电子邮件：</td>
	          <td><input type="email" class="layui-input" name="email" id="email" lay-verify="required|email" value="${invoice.email}"/></td>
				<td><input type="hidden" class="layui-input" name="id" id="id"  value="${invoice.id}"/></td>
	        </tr>

	        <tr>
	        	<td colspan="2">
	        		<a href="javascript:invoiceSubmit();" class="layui-btn layui-btn-danger" lay-submit>提交</a>
	        		<a href="javascript:invoiceClose();" class="layui-btn layui-btn-primary">取消</a>
	        	</td>
	        </tr>
        </table>
      </form>
	<script type="text/javascript">



      /*  layui.use('form', function() {
            var form = layui.form;

            form.on('radio(invoiceType)', function(data){

            });

        })
*/
        function  type(value) {


        }


		//校验
	    layui.use(['form'], function() {
	        var form = layui.form;
	        //监听提交
	        form.on('submit(invoiceForm)', function(data){
	            return false;
	        });
	    });
		function invoiceClose(){
			parent.layer.closeAll();
		}
		function invoiceSubmit() {

            var id =$("[name='id']").val();
            var actionType;
            //actionType 1:修改 2：新增
            if(id!=null && id !=''){
                actionType=1;

			}else{
                actionType=2;

			}

	        var invoiceTitle =$("[name='invoiceTitle']").val();
	        var email =$("[name='email']").val();
            var invoiceContent =$("[name='invoiceContent']").val();
            var taxpayerNumber =$("[name='taxpayerNumber']").val();
	        var dataJson = {"invoiceTitle":invoiceTitle,"email":email,"invoiceContent":invoiceContent,"taxpayerNumber":taxpayerNumber,"actionType":actionType};
	        var content = '确定提交吗？';
	        layer.confirm(content, {icon: 3, title:'提示'}, function(index){
	            $('.layui-layer-btn0').css('pointer-events','none');
	            $.ajax({
	                type:"post",
	                url:"${ctx}/charter/invoice/saveInvoiceTemplate",
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
	                            	parent.layer.closeAll();
	                            	parent.location.reload();
	                            },
	                        });
	                    }else{
	                        layer.open({
	                            icon: 2,
	                            title: '信息',
	                            skin: 'layer-ext-myskin',
	                            shade: 0, //不显示遮罩
	                            content: "操作失败！",
	                        });
	                    }
	                }
	            });
	        });
	    }
	</script>
</body>
</html>