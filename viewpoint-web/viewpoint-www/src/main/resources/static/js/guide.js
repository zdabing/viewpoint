$(document).ready(function() {
	$(".g-popup .dt .tt p").each(function(){
	  var str=$(this).html();
	  var len=70;
	  if(str.length>len){
	    str=str.substring(0,len)+"...";
	    $(this).html(str);
	  }
	});
	$(".jieshuo").on('tap',function(){
	  var audio=$(this).parent().siblings('audio')[0];
	  var text=$(this).find('span').text();
	  if (text=='解说') {
	    audio.play();
	    $(this).find('span').text('暂停');
	  }else{
	    audio.pause();
	     $(this).find('span').text('解说');
	  }
	});
});