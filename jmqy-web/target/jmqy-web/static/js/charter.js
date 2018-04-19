//删除订单
function orderCharterDel(ctx,orderCode){
	var content = '确定删除吗？';
	var dataJson = {"orderCode": orderCode};
	layer.confirm(content, {icon: 3, title:'提示'}, function(index){
		$('.layui-layer-btn0').css('pointer-events','none');
	    $.ajax({
	        type:"post",
	        url:ctx+"/charter/order/deleteOrder",
	        dataType:"json",
	        data:dataJson,
	        success:function(data) {
	           if(data.state){
	              layer.open({
	              icon: 1,
	              title: '信息',
	              skin: 'layer-ext-myskin',
	              shade: 0, //不显示遮罩
	              content: "删除成功！",
	              yes: function(){
	                    location.reload();
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
	                location.reload();
	             },
	           });
	        }
	      }
	   });
	});
}

//删除订单
function orderCharterCancel(index,ctx,orderCode,payType){
	var content = '确定取消吗？';
	if(index == 1){
		content = '确定重新发布吗？';
	}else if(index ==2){
		content = '确定取消吗？';
	}
	var dataJson = {"orderCode": orderCode,"payType":payType};
	layer.confirm(content, {icon: 3, title:'提示'}, function(index){
		$('.layui-layer-btn0').css('pointer-events','none');
	    $.ajax({
	        type:"post",
	        url:ctx+"/charter/order/cancelOrder",
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
	                   location.reload();
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
	                location.reload();
	             },
	           });
	        }
	      }
	   });
	});
}



//订单退款
function refund(ctx,orderCode,amount){

    var dataJson = {"orderCode": orderCode};
    var result = check(dataJson, ctx+'/charter/order/chatRefundMoney');

    var content = '当前需要扣除手续费'+result+',确定取消吗？';


    var dataJson = {"orderCode": orderCode,"amount":amount};
    layer.confirm(content, {icon: 3, title:'提示'}, function(index){
        $('.layui-layer-btn0').css('pointer-events','none');
        $.ajax({
            type:"post",
            url:ctx+"/charter/order/orderRefund",
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
                            location.reload();
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
                            location.reload();
                        },
                    });
                }
            }
        });
    });
}



//点击查看订单提醒
function updateRemind(url,dataJson){
    $.ajax({
        type:"post",
        url:url,
        dataType:"json",
        data:dataJson,
        success:function(data) {
      }
   });
}