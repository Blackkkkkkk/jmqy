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
	          <td>发票类型：</td>
	          <td><!--<input type="radio"  name="invoiceType" title="电子发票" value="1"  lay-filter="invoiceType" />--><input type="radio" name="invoiceType" checked="checked" title="纸质增值税普票" value="2"  lay-filter="invoiceType" /><input type="radio" name="invoiceType" title="纸质增值税专票" value="3" lay-filter="invoiceType"/></td>
	        </tr>
	        <tr>
	          <td>抬头类型：</td>
	          <td><input type="radio" name="titleCategory" title="公司抬头" value="1" checked="checked"/><input type="radio" name="titleCategory" title="个人/非企业单位" value="2"/></td>
	        </tr>
	        <tr>
	          <td>发票抬头：</td>
	          <td><input type="text" class="layui-input" name="invoiceTitle" id="invoiceTitle" lay-verify="required" value="${mapTem.invoiceTitle}"/></td>
	        </tr>
	        <tr>
	          <td>纳税人识别号：</td>
	          <td><input type="text" class="layui-input" name="taxpayerNumber" id="taxpayerNumber" lay-verify="required" value="${mapTem.taxpayerNumber}"/></td>
	        </tr>
	        <tr>
	          <td>发票内容：</td>
	          <td><input type="text" class="layui-input" name="invoiceContent" id="invoiceContent" lay-verify="required" maxlength="100" value="${mapTem.invoiceContent}"/></td>
	        </tr>
	        <tr>
	          <td>电子邮件：</td>
	          <td><input type="email" class="layui-input" name="email" id="email" lay-verify="required|email" value="${mapTem.email}"/></td>
	        </tr>
	        <tr id="addressTr">
	          <td>地址：</td>
	          <td><input type="text" class="layui-input" name="address" id="address" value="${address.address}"  lay-verify="required"/></td>
	        </tr>
			<tr id="recipientTr">
				<td>收件人：</td>
				<td><input type="text" class="layui-input" name="recipient" id="recipient" value="${address.recipient}" lay-verify="required"/></td>
			</tr>
			<tr id="contactWayTr" >
				<td>联系方式：</td>
				<td><input type="text" class="layui-input" name="contactWay" id="contactWay" value="${address.contactWay}" lay-verify="required"/></td>
			</tr>
	        <tr>
	          <td>发票金额：</td>
	          <td><span class="orange"><b>${order.amount}</b></span>元</td>
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

		$(document).ready(function () {
         console.log('${requestScope.mapTem}')
        })

        layui.use('form', function() {
            var form = layui.form;

            form.on('radio(invoiceType)', function(data){
              //  console.log(data.elem); //得到radio原始DOM对象
             //   console.log(data.value); //被点击的radio的value值
                    $('#contactWayTr').attr("style","display:none;");
				if(data.value=='1'){
                    $('#contactWayTr').attr("style","display:none;");
                    $('#recipientTr').attr("style","display:none;");
                    $('#addressTr').attr("style","display:none;");
				}else{
                    $('#contactWayTr').attr("style","");
                    $('#recipientTr').attr("style","");
                    $('#addressTr').attr("style","");
				}
            });

        })

        function  type(value) {

		    alert(value)
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




			var orderCode =$("[name='orderCode']").val();
	        var invoiceAmount =$("[name='invoiceAmount']").val();
            var taxpayerNumber =$("[name='taxpayerNumber']").val();
            var invoiceContent =$("[name='invoiceContent']").val();
	        var invoiceType =$("[name='invoiceType']:checked").val();
	        var titleCategory =$("[name='titleCategory']:checked").val();
	        var invoiceTitle =$("[name='invoiceTitle']").val();
	        var email =$("[name='email']").val();
	        var address =$("[name='address']").val();
            var recipient =$("[name='recipient']").val();
            var contactWay =$("[name='contactWay']").val();

            if(invoiceType==1){
                address='';
                recipient='';
                contactWay='';
			}
	        var dataJson = {"orderCode": orderCode,"taxpayerNumber":taxpayerNumber,"invoiceContent":invoiceContent, "invoiceAmount": invoiceAmount, "invoiceType": invoiceType,
	        		"titleCategory": titleCategory,"invoiceTitle":invoiceTitle,"email":email,"address":address,"recipient":recipient,"contactWay":contactWay};
	        var content = '确定提交吗？';
	        layer.confirm(content, {icon: 3, title:'提示'}, function(index){
	            $('.layui-layer-btn0').css('pointer-events','none');
	            $.ajax({
	                type:"post",
	                url:"${ctx}/charter/invoice/saveInvoice",
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