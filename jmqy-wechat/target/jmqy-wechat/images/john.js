$(function(){
	var Height = ( $('.pic_height').height() - 5 )/ 2 ;
	$('.height50').css({
		'height': Height,
		'line-height' : Height + 'px'
	});
	$(window).resize(function(){
		var Height = ( $('.pic_height').height() - 5 )/ 2 ;
		$('.height50').css({
			'height': Height,
			'line-height' : Height + 'px'
		});
	})
	/**增减途经点**/

	$("body").undelegate(); // 解除之前的绑定
	$("body").delegate("i.icon-move","click",function(){  // 重新绑定
        alert("1")
        $(this).parent().parent().remove();
     });
	$("body").delegate('.turnright2','click',function(){
        $(this).parent().remove();

        var viaPoint = '';
        $("[name='viaPoints']").each(function(index,element){
            if($(this).val() != null && $(this).val() != ''){
                viaPoint += $(this).val()+'@'+$(this).next().val()+','+$(this).next().next().val()+';';
            }
        });
        $('#viaPoint').val(viaPoint);
    })


    $('.updown').click(function(){

        var startPoint = $("#startPoint").val();
        var startLng = $("#startLng").val();
        var startLat = $("#startLat").val();
        var startArea = $("#startArea").val();
        var startCity = $("#startCity").val();

        $("#startPoint").val( $("#endPoint").val());
        $("#startLng").val($("#endLng").val());
        $("#startLat").val($("#endLat").val());
        $("#startArea").val($("#endArea").val());
        $("#startCity").val($("#endCity").val());

        $("#endPoint").val(startPoint)
        $("#endLng").val(startLng)
        $("#endLat").val(startLat)
        $("#endArea").val(startArea)
        $("#endCity").val(startCity)

    })



	$('.add-two').click(function(){
        var array=$("[name='viaPoints']");
        var num = array.length+1;
        var dome_=$('<div class="item-right-top">' +
            '途经：<input placeholder="您希望经过的途经站点" type="text" class="john-input ms4"  name="viaPoints" onclick="NewWindow(this.id)" type="text" id="viaPoints'+num+'">' +
            '<input type="hidden" id="viaLngs'+num+'"  value="" name="viaLngs"/>' +
            '<input type="hidden" id="viaLats'+num+'"  value="" name="viaLats"/>' +
            '<input type="hidden" id="viaAreas'+num+'"  value="" name="viaAreas"/>' +
            '<span class="turnright2"></span>' +
            '</div>')
		// var dome_=$('<div class="div_input"><label>途经点：</label> <input placeholder="途经地点" class="search_text" type="text" id="viaPoints" name="viaPoints" onclick="NewWindow(this.id)"><input  type="hidden" id="viaLngs" name="viaLngs"><input  type="hidden" id="viaLats" name="viaLats"><input type="hidden" id="viaAreas" name="viaAreas"><i class="icon icon-122 icon-move"></i></div>');
		// $(this).parent().after(dome_);
        $('div.add').after(dome_);
	})
	$('.chexing i.icon-add').unbind();
	$('.chexing i.icon-add').click(function(){
        var dome_=$('<div class="item1 chexing-all">' +
            '<div class="item1-left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车型：</div>' +
            '<div class="item1-right chexing">' +
            '<input type="text" placeholder="选择车型" class="d2" type="text" name="carType" value="小车4座">' +
            '<span>x</span><input class="d3" type="text" name="carAmount" id="carAmount" value="1">' +
            '<i class="icon-move"><img src="../images/move.jpg" alt=""></i>' +
            '</div>' +
            '</div>')
		// var dome_=$('<div class="div_input"><label>车型：</label><div class="search_text carxing">' +
         //    '<input class="search_text d2" type="text" name="carType" value="小车4座"><span>x</span>' +
         //    '<input class="search_text" type="text" name="carAmount" id="carAmount" value="1"></div>' +
         //    '<i class="icon icon-122 icon-move"></i></div>');
		$(this).parent().parent().after(dome_);
		se()
	})
	se()
	 function se(){$(".d2").select({
        title: "选择车型",
        items: [
          {
            title: "小车4座",
            value: "小车4座",
          },
          {
            title: "小车6座",
            value: "小车6座",
          },
          {
            title: "中巴14座",
            value: "中巴14座",
          },
          {
            title: "中巴19座",
            value: "中巴19座",
          },
          {
            title: "大巴49座",
            value: "大巴49座",
          },
          {
            title: "大巴50座",
            value: "大巴50座",
          }
            ,
            {
                title: "大巴51座",
                value: "大巴51座",
            }
        ],
        onChange: function(d) {
         $.alert("你选择了"+d.values);
        }
      });
	}
	/*****往返*****/
	$('.rad2').click(function(){
		$('.shuang').show('solw');
	})
	$('.rad1').click(function(){
		$('.shuang').hide('solw');
	})
})
