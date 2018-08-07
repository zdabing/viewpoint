$(document).ready(function() {
	$("#spot-popover").hide();
	$(".footer-mask").hide();
	$("#spot").on("tap",function(){
	  $("#spot-popover").toggle();
	  $(".footer-mask").toggle();
	});
	$(".footer-mask").on("tap",function(e){
		$("#spot-popover").hide();
		$(".footer-mask").hide();
	});
	$("#footer .normal").on("tap",function(e){
		$("#spot-popover").hide();
		$(".footer-mask").hide();
	});
	$("#footer .item").on("tap",function(e){
		$(this).addClass('active').siblings('.item').removeClass('active');
	});
});
	