$(function(){
	var Height = $('body').height()+50;
	parent.$('.pla_iframe').css('height', Height);
	$(window).resize(function(){
		var Height = $('body').height()+50;
        parent.$('.pla_iframe').css('height', Height);
	})
})


