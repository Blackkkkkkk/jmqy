/**时间日期**/
layui.use('laydate', function(){
  var laydate = layui.laydate;
  var myDate = new Date().toLocaleDateString();
    laydate.render({
    elem: '#test5'
    ,type: 'date'
    ,min: myDate
    ,value: new Date()
  });
  laydate.render({
    elem: '#test4'
    ,type: 'datetime'
    ,theme: '#0071bf'
    ,value: new Date()
  });
  laydate.render({
	 elem: '#test6'
	 ,type: 'datetime'
	 ,theme: '#0071bf'
	 ,min: myDate
	 ,value: new Date()
  });
  laydate.render({
	 elem: '#test7'
	 ,type: 'datetime'
	 ,theme: '#0071bf'
      ,min: myDate
      ,value: new Date()
  });
  laydate.render({
	  elem: '#test8'
	 ,type: 'datetime'
	 ,theme: '#0071bf'
	 ,value: new Date()
  });
   laydate.render({
    elem: '#test1'
    ,theme: '#0071bf'
    ,value: new Date()
  });
    laydate.render({
    elem: '#test2'
    ,theme: '#0071bf'
    ,value: new Date()
  });
});
/***banner轮播****/
layui.use(['carousel', 'form'], function(){
  var carousel = layui.carousel
  ,form = layui.form;
  
  //改变下时间间隔、动画类型、高度
  carousel.render({
    elem: '#banner'
    ,interval: 5000
    // ,anim: 'fade'
    ,height: '475px'
    ,width: '100%'
  });
  var $ = layui.$, active = {
    set: function(othis){
      var THIS = 'layui-bg-normal'
      ,key = othis.data('key')
      ,options = {};
      
      othis.css('background-color', '#5FB878').siblings().removeAttr('style'); 
      options[key] = othis.data('value');
      ins3.reload(options);
    }
  };
  
  //监听开关
  form.on('switch(autoplay)', function(){
    ins3.reload({
      autoplay: this.checked
    });
  });
  
  $('.demoSet').on('keyup', function(){
    var value = this.value
    ,options = {};
    if(!/^\d+$/.test(value)) return;
    
    options[this.name] = value;
    ins3.reload(options);
  });
  
  //其它示例
  $('.demoTest .layui-btn').on('click', function(){
    var othis = $(this), type = othis.data('type');
    active[type] ? active[type].call(this, othis) : '';
  });
});
/**表格**/
layui.use('form', function(){
  var $ = layui.jquery, form = layui.form;
  form.on('checkbox(allChoose)', function(data){
    var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
    child.each(function(index, item){
      item.checked = data.elem.checked;
    });
    form.render('checkbox');
  });
  
});
/***搜索选项***/
layui.use('form',function(){
  var form = layui.form;
  form.on('select(sel1)',function(date){
    $('.sel2').show('solw');
  })

})
/***导航栏***/
$('nav .menu li').click(function(){
    $('nav .menu li').removeClass('menu_action')
    $(this).addClass('menu_action')
})
/***司机管理***/
function fun_dri(){
  layer.open({
    type: 2,
    title: '添加修改',
    shadeClose: true,
    shade: 0.5,
    area: ['950px', '280px'],
    content: 'driver-1.html' //iframe的url
  });
}
$('.driver_a').click(fun_dri)

/***旅游包车，企业包车，私人包车****/
function fun_car(){
  layer.open({
    type: 2,
    title: '添加修改',
    shadeClose: true,
    shade: 0.5,
    area: ['950px','500px'],
    content: 'tourism-1.html'
  })
}
$('.tourism_a').click(fun_car)

/*****行程管理*****/
function fun_trip(){
  layer.open({
    type: 2,
    title: '添加修改',
    shadeClose: true,
    shade: 0.5,
    area: ['950px','500px'],
    content: 'trip-1.html'
  })
}
$('.trip_a').click(fun_trip)

/*****投诉回复*****/
function fun_compl(){
  layer.open({
    type: 2,
    title: '回复',
    shadeClose: true,
    shade: 0.5,
    area: ['950px','500px'],
    content: 'complaint-1.html'
  })
}
$('.compl_a').click(fun_compl)

/*****订单详情*****/
function fun_details(){
  layer.open({
    type: 2,
    title: '订单详情',
    shadeClose: true,
    shade: 0.5,
    area: ['950px','230px'],
    content: 'details-1.html'
  })
}
$('.details_a').click(fun_details)
/*****修改订单*****/
function fun_modify(){
  layer.open({
    type: 2,
    title: '修改订单',
    shadeClose: true,
    shade: 0.5,
    btn: ['提交', '取消'],
    yes: function(index, layero){
      //按钮【按钮一】的回调
    },
    btn2: function(index, layero){
      //按钮【按钮二】的回调
    },
    area: ['1000px','530px'],
    content: 'modify-1.html'
  })
}
$('.modify_a').click(fun_modify)
/*****历史订单*****/
function fun_history(){
  layer.open({
    type: 2,
    title: '发布订单',
    shadeClose: true,
    btn: ['提交', '取消'],
    yes: function(index, layero){
      //按钮【按钮一】的回调
    },
    btn2: function(index, layero){
      //按钮【按钮二】的回调
    },
    shade: 0.5,
    area: ['1000px','530px'],
    content: 'history-1.html'
  })
}
$('.history_a').click(fun_history)
/*****投诉处理*****/
function fun_complaint(){
  layer.open({
    type: 2,
    title: '投诉',
    shadeClose: true,
    shade: 0.5,
    btn: ['提交', '取消'],
    yes: function(index, layero){
      //按钮【按钮一】的回调
    },
    btn2: function(index, layero){
      //按钮【按钮二】的回调
    },
    area: ['1000px','380px'],
    content: 'complaint-1.html'
  })
}
$('.complaint_a').click(fun_complaint)
/*****发票*****/
function fun_invoice(){
  layer.open({
    type: 2,
    title: '申请发票',
    shadeClose: true,
    shade: 0.5,
    btn: ['确定', '取消'],
    yes: function(index, layero){
      //按钮【按钮一】的回调
    },
    btn2: function(index, layero){
      //按钮【按钮二】的回调
    },
    area: ['366px','530px'],
    content: 'invoice-1.html'
  })
}
$('.invoice_a').click(fun_invoice)
/**权限管理**/
function fun_plat(roleId, companyId, type, name, url){
  name = (type==0?'平台方':(type==1?'个人包主':(type==2?'包车企业':'运输企业')))+"【"+name+"】";
  parent.layer.open({
    type: 2,
    title: name+'设定权限',
    shadeClose: true,
    shade: 0.1,
    btn: ['确定', '取消'],
    yes: function(index, layero){
        var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
        iframe.setPer();
    },
    skin: "layui-layer-molv",
    area: ["900px", "500px"],
    content: url+"?roleId="+roleId+"&companyId="+companyId+"&companyType="+type
  })
}
/***登录***/
function fun_loging(url){
  parent.layer.open({
    type: 2,
    title: '登录',
    shade: 0.5,
    resize: false,
    area: ['500px','450px'],
    content: url
  })
}

$('#dl_show').click(function(){
    $(this).next('dl').slideDown();
    return false;
})

$('#dl_show').next('dl').find('dd').click(function(){
    $(this).parent().hide();
    $('#dl_show').val($(this).text());
    $('#role').val($(this).attr('id'));
})

$('#dl_show').focus(function(){
    $(this).next('dl').slideDown();
    return false;
})

/***信用管理***/
function fun_plat2(ids, companyName, url){
    parent.layer.open({
        type: 2,
        title: '调整【'+companyName+'】信用额度',
        shadeClose: true,
        shade: 0.1,
        btn: ['确定', '取消'],
        yes: function(index, layero){
            var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
            iframe.$("#submit_btn").click();
        },
        skin: "layui-layer-molv",
        area: ['500px','400px'],
        content: url+"?userId="+ids
    })
}
/***包车方优惠设置***/
function setDiscount(companyCode, companyName, url){


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
        content: url+"?companyCode="+companyCode

    })
}
/**订单管理**/
function fun_plat3(){
  layer.open({
    type: 2,
    title: '用户提醒',
    shadeClose: true,
    shade: 0,
    btn: ['发送', '取消'],
    yes: function(index, layero){
      //按钮【按钮一】的回调
    },
    btn2: function(index, layero){
      //按钮【按钮二】的回调
    },
    area: ['500px','390px'],
    content: 'plat-3.html'
  })
}
$('.plat3_a').click(fun_plat3)
/**积分管理**/
function fun_plat4(userId, companyName, url){
    parent.layer.open({
        type: 2,
        title: '【'+companyName+'】积分奖励',
        shadeClose: true,
        shade: 0,
        btn: ['确定', '取消'],
        yes: function(index, layero){
            var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
            iframe.$("#submit_btn").click();
        },
        skin: "layui-layer-molv",
        area: ['600px','450px'],
        content: url+"?userId="+userId
    })
}
/**过期提醒**/
function fun_plat4a(){
  layer.open({
    type: 2,
    title: '过期提醒',
    shadeClose: true,
    shade: 0,
    btn: ['确定', '取消'],
    yes: function(index, layero){
      //按钮【按钮一】的回调
    },
    btn2: function(index, layero){
      //按钮【按钮二】的回调
    },
    area: ['530px','500px'],
    scrollbar: false,
    content: 'plata-4.html'
  })
}
$('.plat4a_a').click(fun_plat4a)
/**财务管理**/
function fun_plat5(){
  layer.open({
    type: 2,
    title: '修改订金',
    shadeClose: true,
    shade: 0,
    btn: ['确定', '取消'],
    yes: function(index, layero){
      //按钮【按钮一】的回调
    },
    btn2: function(index, layero){
      //按钮【按钮二】的回调
    },
    area: ['330px','200px'],
    content: 'plat-5.html'
  })
}
$('.plat5_a').click(fun_plat5)

function fun_plat5a(){
  layer.open({
    type: 2,
    title: '提醒包车方交付定金',
    shadeClose: true,
    shade: 0,
    btn: ['确定', '取消'],
    yes: function(index, layero){
      //按钮【按钮一】的回调
    },
    btn2: function(index, layero){
      //按钮【按钮二】的回调
    },
    area: ['530px','400px'],
    content: 'plata-5.html'
  })
}
$('.plat5a_a').click(fun_plat5a)

/**评分/投诉**/
function fun_plat6(){
  layer.open({
    type: 2,
    title: '详情',
    shadeClose: true,
    shade: 0,
    btn: ['确定', '取消'],
    yes: function(index, layero){
      //按钮【按钮一】的回调
    },
    btn2: function(index, layero){
      //按钮【按钮二】的回调
    },
    area: ['1000px','300px'],
    content: 'plat-6.html'
  })
}
$('.plat6_a').click(fun_plat6)

function fun_plat6a(){
  layer.open({
    type: 2,
    title: '提醒企业',
    shadeClose: true,
    shade: 0,
    btn: ['发送', '取消'],
    yes: function(index, layero){
      //按钮【按钮一】的回调
    },
    btn2: function(index, layero){
      //按钮【按钮二】的回调
    },
    area: ['530px','400px'],
    content: 'plata-6.html'
  })
}
$('.plat6a_a').click(fun_plat6a)
/***地区系数管理***/
function fun_plat7(id, url){
    parent.layer.open({
        type: 2,
        title: '地区系数',
        shadeClose: true,
        shade: 0.1,
        btn: ['确定', '取消'],
        yes: function(index, layero){
            var iframe = window.top[layero.find('iframe')[0]['name']];//获取iframeWin对象
            iframe.$("#submit_btn").click();
        },
        skin: "layui-layer-molv",
        area: ['500px','450px'],
        content: url+"?id="+id
    })
}
/*****评分评论*****/
function fun_fen(){
  layer.open({
    type: 2,
    title: '用户评价',
    shadeClose: true,
    shade: 0.5,
    area: ['950px','500px'],
    content: 'fen.html'
  })
}
$('.fen_a').click(fun_fen)
//查看车辆照片
function carImg(url){
	 layer.open({
	    type: 2,
	    title: '车辆照片',
	    shadeClose: true,
	    shade: 0.5,
	    area: ['650px','500px'],
	    content: url
	 })
}
/***选项卡***/
layui.use('element', function(){
  var $ = layui.jquery
  ,element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块

  //触发事件
  var active = {

    tabAdd: function(){

      //新增一个Tab项
      element.tabAdd('demo', {
        title: '新选项'+ (Math.random()*1000|0) //用于演示
        ,content: '内容'+ (Math.random()*1000|0)
        ,id: new Date().getTime() //实际使用一般是规定好的id，这里以时间戳模拟下
      })
    }
    ,tabDelete: function(othis){
      //删除指定Tab项
      element.tabDelete('demo', '44'); //删除：“商品管理”


      othis.addClass('layui-btn-disabled');
    }
    ,tabChange: function(){
      //切换到指定Tab项
      element.tabChange('demo', '22'); //切换到：用户管理
    }
  };

 $('.site-demo-active').on('click', function(){
    var othis = $(this), type = othis.data('type');
    active[type] ? active[type].call(this, othis) : '';
  });

  //Hash地址的定位
  var layid = location.hash.replace(/^#test=/, '');
  element.tabChange('test', layid);

  element.on('tab(test)', function(elem){
    location.hash = 'test='+ $(this).attr('lay-id');
  });

});