$(function(){
	var Height = $(window).height()-148;
	var A_height = $(window).height() - 100;
	$('aside,section,.aside_right').css('height', A_height);
	$('section').width($('body').width() - 150)
	$('.pla_iframe').css('height', Height);
	$('.pla_list').css('height', A_height-32);
	$(window).resize(function(){
		var Height = $(window).height()-148;
		$('.pla_iframe').css('height', Height);
		var A_height = $(window).height() - 100;
		$('aside,section,.aside_right').css('height', A_height);
		$('.pla_list').css('height', A_height-32);
		if($('.aside_right').css('display') == 'none'){
            $('section').width($('body').width() - 150)
		}else{
            $('section').width($('body').width() - 300)
		}

	})
	$('aside ul li').click(function(){
		$(this).siblings().removeClass('pla_list_action');
		$(this).addClass('pla_list_action');
		if($(this).text().trim() == '统计报表' || $(this).text().trim() == '平台设置' || $(this).text().trim() == '账号管理' || $(this).text().trim() == '财务管理' || $(this).text().trim() == '评价处理' ){
            $('section').width($('body').width() - 300);
            $('.aside_right').css('display','block')
		}else{
            $('section').width($('body').width() - 150);
            $('.aside_right').css('display','none');
            $('.pla_dl2 dd').css('background','none');
		};
	    if($(this).find('i').html() == ''){
            $('aside ul li').next('dl').hide();
            if($(this).find('i').attr('class') == 'icon-caret-left'){
                $('aside ul li i').attr('class','icon-caret-left');
                $(this).find('i').attr('class','icon-caret-right')
                $(this).next('dl').show()
            }else{
                $(this).find('i').attr('class','icon-caret-left')
                $(this).next('dl').hide();
                $('section').width($('body').width() - 150);
                $('.aside_right').css('display','none');
            }
        }else{
            $('aside ul li').next('dl').hide();
            $('aside ul li i').attr('class','icon-caret-left');
        };
	});
	$('.pla_dl2 dd').click(function(){
		$('.pla_dl2 dd').css('background','none');
		$(this).css('background','#fff')
	})
})


