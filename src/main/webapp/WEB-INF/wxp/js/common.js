// nav animation
$(function() {

	var initFunc = function(){
		var windowH = $(window).height();
		var windowW = $(window).width();

		// input text
		var inputContW = $(".userInputCont .inputTypeCont").width();
		var inputW = inputContW - 5 -50 -25; //inputTitle border-radius
		$(".userInputCont .inputTypeCont input.commonInput").css({"width":inputW+"px"});

		// input select
		var inputselectW = inputContW - 5 -50 -25;
		$(".userInputCont .inputTypeCont select").css({"width":inputW+"px"});

		// inputFunc
		var comInputFuncW = inputW - 50 - 10 - 5;
			$(".inputTypeCont .commonInputFunc").css({"width":comInputFuncW+"px"});
	};

	initFunc();

	$(window).resize(function(){
		initFunc();
	});

	$(".headerNavIcon").click(function(){
		$(this).toggleClass("headerNavIconClick headerNavIconOut");
		
		// modify headerNavIcon span color
		if ($(".headerNavCont").css('display') != 'none' ){
			$(".headerNavIcon span").css({"background":"#333333"});
		} else{
			$(".headerNavIcon span").css({"background":"#ffffff"});
		}

		$(".headerNavCont").slideToggle(250);
	
	});

	$(".headerNavCont a").each(function( index ) {
		$( this ).css({'animation-delay': (index/10)+'s'});
	});

});