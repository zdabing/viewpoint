$(document).ready(function() {

	// $(".popup_msg").hide();
	
	$(".popup_suc").hide();
	// $(".mymask").hide();

	// 报名信息弹窗
	// $("#baoming").on('tap',function(e){
	//   $(".popup_msg").show();
	//   $(".mymask").show();
	//   // e.stopPropagation();
	//  });
	// $("#popup_msg .cancel").on('tap',function(e){
	//   $("#popup_msg").hide();
	//   $(".mymask").hide();
	// });

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

	 // 遮罩关闭事件
	// $(".mymask").on('tap',function(e){
	//   $(".mymask").hide();
	//   $(".mypopup").hide();
	//   e.stopPropagation();
	// });

});