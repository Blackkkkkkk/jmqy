
    var swiper = new Swiper('.swiper-container-v', {
        pagination: '.swiper-pagination-v',  //绑定翻页导航
        direction: 'vertical',
        slidesPerView: 1,

        paginationClickable: true,         //设定可以点击翻页导航进行翻页
        mousewheelControl: true,
        onInit: function(swiperh){      //Swiper2.x的初始化是onFirstInit
               swiperAnimateCache(swiperh);    //隐藏动画元素
               swiperAnimate(swiperh);         //初始化完成开始动画

            },
        onSlideChangeEnd: function(swiperh){

                swiperAnimate(swiperh);       //每个slide切换结束时也运行当前slide动画

             }
    });
	$('.col-md-4').hover(function(){
		$(this).find('.col-bg').slideDown();
		$(this).find('a.show_a').show('solw');
	},function(){
		$(this).find('.col-bg').stop().slideUp();
		$(this).find('a.show_a').stop().hide('solw');
	})
$('.col-md-4-info').eq(0).click(function(){
  window.open("",'_parent');
})
$('.col-md-4-info').eq(1).click(function(){
  window.open("",'_parent');
})
$('.col-md-4-info').eq(2).click(function(){
  window.open("",'_parent');
})
$('.col-md-4-info').eq(3).click(function(){
  window.open("",'_parent');
})

$('.john-tab .john-tab-title li').click(function(){
  $(this).siblings().removeClass('john-this');
  $(this).addClass('john-this');
  $(this).parent().next('.john-tab-content').children('.john-tab-item').removeClass('john-tab-show');
  $(this).parent().next('.john-tab-content').children('.john-tab-item').eq($(this).index()).addClass('john-tab-show');
})
    /*****banner*****/
    setInterval(function(){
        $('#banner_lun li').eq(2).animate({
            'opacity':'0'
        },2000,function(){
            $('#banner_lun li').css('opacity','1')
            $('#banner_lun li').eq(0).before( $('#banner_lun li').eq($('#banner_lun li').length - 1))
        })
    },7000)