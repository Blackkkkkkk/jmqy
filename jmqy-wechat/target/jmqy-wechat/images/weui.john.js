$(function(){
	/****时间****/
	var year = new Date().getFullYear();
	var mon = ( new Date().getMonth() ) + 1;
	var day = new Date().getDate();
	var min = year + '-' + mon + '-' + day ;
	var max = year + '-' + ( mon + 3 ) + '-' + day ;

	$("#time").datetimePicker({title:"选择日期时间",min: min, max:max});
	$("#time1").datetimePicker({title:"选择日期时间",min: min, max:max});
    /***评分***/
    var arr = ["1分","2分","3分","4分","5分"];
    var num = -1;
    $(".weui-rater a").click(function(){
        var thisL = $(this).index();
        for(var i = 0;i < thisL;i++){
            // $(".weui-rater a").eq(i).addClass('checked');
            $(this).parent().children('a').eq(i).addClass('checked');
        }
        for(var i = thisL; i < 5;i++){
            $(this).parent().children('a').eq(i).removeClass('checked');
        }
        $(this).addClass('checked');



		if($(this).parent().attr('id') == 'MarkTwo'){
            var thisL = $(this).index();
            $("#fen1").html(arr[thisL]);
            $(this).addClass('checked');
            num = thisL+1;
            console.log(num);
		}else{
            var thisL = $(this).index();
            $("#fen").html(arr[thisL]);
            $(this).addClass('checked');
            num = thisL+1;
            console.log(num);
		}

    })
    // $(".weui-rater a").mouseout(function(){
    //     var thisL = $(this).index();
    //     for(var i = thisL; i < 5;i++){
    //         $(this).parent().children('a').eq(i).removeClass('checked');
    //     }
    // })
    // $(".weui-rater").mouseout(function(){
    //
    //     for(var i = 0; i < num;i++){
    //         $(this).parent().children('a').eq(i).addClass('checked');
    //     }
    // })
    // $(".weui-rater a").click(function(){
    //     var thisL = $(this).index();
    //     $("#fen").html(arr[thisL]);
    //     $(this).addClass('checked');
    //     num = thisL+1;
    //     console.log(num);
    // })
});