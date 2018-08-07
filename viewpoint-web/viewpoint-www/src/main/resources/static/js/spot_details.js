$(document).ready(function() {
	$(".exhibits .mui-card .box .dt p").each(function(){
	  var str=$(this).html();
	  var len=50;
	  if(str.length>len){
	    str=str.substring(0,len)+"...";
	    $(this).html(str);
	  }
	});
	$(".wenwu .mylist .box .dt p").each(function(){
	  var str=$(this).html();
	  var len=50;
	  if(str.length>len){
	    str=str.substring(0,len)+"...";
	    $(this).html(str);
	  }
	});
});