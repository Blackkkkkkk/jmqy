$(function(){
    /**提交订单**/
    $('.show_table').click(function(){
        $('.hide_table').toggle();
    })
    /**增减途经点**//*
	$("body").undelegate(); // 解除之前的绑定
	$("body").delegate("i.icon-move","click",function(){  // 重新绑定
        $(this).parents('.remove_input').remove();
     });
	$('.searh1 i.icon-add').click(function(){
		var dome_=$('<div class="div_input remove_input"><label>途经点：</label><div class="john_" > <div class="add_input"><input class="search_text" type="text"><i class="icon-minus-sign icon-move"></i></div></div></div>');
		$(this).parent().after(dome_);
	})*/
    /*****往返*****/
    $('.rad2').click(function(){
        $('.shuang').show('solw');
    })
    $('.rad1').click(function(){
        $('.shuang').hide('solw');
    })
    $('.btn_df span:eq(0)').click(function(){
        $(this).siblings().removeClass('btn_df_active');
        $(this).addClass('btn_df_active');
        $('.search_time_2').hide('slow');
        $('.btn_df .rad1').prop('checked',true);
    })
    $('.btn_df span:eq(1)').click(function(){
        $(this).siblings().removeClass('btn_df_active');
        $(this).addClass('btn_df_active');
        $('.search_time_2').show('slow');
        $('.btn_df .rad2').prop('checked',true);
    })

    /**左边菜单**/
    // $('aside div ul li').click(function(){
    // 	$(this).next('dl').slideToggle();
    // 	if($(this).find('i').attr('class') == 'icon-sort-down'){
    // 		$(this).find('i').attr('class','icon-sort-up')
    // 	}else{
    // 		$(this).find('i').attr('class','icon-sort-down')
    // 	}
    // })
    /***评价打分***/
    $('.eva_show').click(function(){
        $(this).parent().parent().next('.eva_hide').show();
    })
    $('.eva_close').click(function(){
        $(this).parents('.eva_hide').hide();
    })
    /****首页*****/
    $('.chartered').hover(function(){
        $(this).find('dl').slideDown();
    },function(){
        $(this).find('dl').stop().slideUp();
    });
    $('.detail-title li').click(function(){
        $(this).parent().find('li').removeClass('ac');
        $(this).addClass('ac');
        var NB = $(this).index();
        $(this).parents('.index-info-detail').find('.title_show').find('ul').css('display','none');
        $(this).parents('.index-info-detail').find('.title_show').find('ul').eq(NB).css('display','block');
    })
    /***滚动导航固定***/
    $(window).scroll(function(){
        var NB = $(window).scrollTop();
        if(NB > 86){
            $('.nav_t').css('display','block')
            $('nav').css({
                'position': 'fixed',
                'top': 0,
                'box-shadow':'0px 0px 5px #666',
                'z-index':'999999999999'
            })
        }else{
            $('.nav_t').css('display','none')
            $('nav').css({
                'position': 'static',
                'top': 0,
                'box-shadow':'none',
                'z-index':'999999999999'
            })
        }
    })
    /****footer***/
    setTimeout(function(){
        if($(window).height() > $('body').height()){
            $('footer').css({
                position:'fixed',
                bottom : 0,
                width: '100%',
                left:0
            })
        }else{
            $('footer').css({
                position:'static'
            })
        }
    },500 )

    $(window).resize(function(){
        if($(window).height() > $('body').height()){
            $('footer').css({
                position:'fixed',
                bottom : 0,
                width: '100%',
                left:0
            })
        }else{
            $('footer').css({
                position:'static'
            })
        }
    })
})

/**
 * 排序
 * sortType : 排序列名
 * order ：排序　(升序或降序)
 * */
function sort(boj) {
    var order = $("#order").val();
    if(order == "DESC"){
        order = "ASC";
    }else{
        order = "DESC";
    }
    $("#order").val(order);
    $("#sortType").val(boj.id);
    $("#searchForm").submit();
}
//去服务器校验
function check(dataJson, url) {
    var result = true;
    $.ajax({
        async:false,
        type:"post",
        url:url,
        dataType:"json",
        data:dataJson,
        success:function(data) {
            result = data;
        }
    });
    return result;
}
/*****评分评论*****/
function fun_fen(dataJson, url){
    layer.open({
        type: 2,
        title: '用户评价',
        shadeClose: true,
        shade: 0.5,
        area: ['950px','500px'],
        content: url
    })
}
//秒转化成时分
function secondFormat(result) {
    var h = Math.floor(result/3600%24);
    var m = Math.floor(result/60%60);
    if (h < 1) {
        return result = m + "分钟";
    } else {
        return result = h + "小时" + m + "分钟";
    }
}
/**
 * 从 file 域获取 本地图片 url
 */
function getFileUrl(sourceId) {
    var url;
    if (navigator.userAgent.indexOf("MSIE")>=1) { // IE
        url = document.getElementById(sourceId).value;
    } else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox
        url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
    } else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome
        url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
    }
    return url;
}