$(document).ready(function() {
	$(".popup_suc").hide();
	// 报名成功弹窗
	$("#submit").on('tap',function(e){
	  $(".popup_suc").show();
	});
	$("#popup_suc .close").on('tap',function(e){
	  $("#popup_suc").hide();
	});
	$("#popup_msg-screen").on('tap',function(e){
	  $("#popup_suc").hide();
	});
});