var browser={  
	versions:function(){  
	   var u = navigator.userAgent, app = navigator.appVersion;  
	   return {  
			trident: u.indexOf("Trident") > -1, //IE内核  
			presto: u.indexOf("Presto") > -1, //opera内核  
			webKit: u.indexOf("AppleWebKit") > -1, //苹果、谷歌内核  
			gecko: u.indexOf("Gecko") > -1 && u.indexOf("KHTML") == -1, //火狐内核  
			mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //是否为移动终端  
			ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端  
           	android: u.indexOf("Android") > -1, //android终端或者uc浏览器
           	iPhone: u.indexOf("iPhone") > -1, //是否为iPhone或者QQHD浏览器
			iPad: u.indexOf("iPad") > -1, //是否iPad  
			webApp: u.indexOf("Safari") == -1 //是否web应该程序，没有头部与底部  
		};  
	 }(),  
	 language:(navigator.browserLanguage || navigator.language).toLowerCase()  
}  
if(browser.versions.android || browser.versions.iPhone || browser.versions.iPad){
	//window.location.href="/mobileIndex.html";  
}else{  
	window.location.href="/"  
}
$(function(){
	/*图片轮播*/
	$('#owl-demo').owlCarousel({
		items: 1,
		singleItem: true,
		navigation: false,
		autoPlay: true,
		navigationText: ["上一个","下一个"],
		responsiveRefreshRate:200,
		stopOnHover: true
	});
	/*$('#resource-owl').owlCarousel({
		items: 2,
		singleItem: false,
		autoPlay: true,
		center: true,
		navigation: true
	});
	$('#wuye-owl').owlCarousel({
		items: 2,
		singleItem: false,
		autoPlay: true,
		center: true,
		navigation: true
	});*/
	$('#travel-owl').owlCarousel({
		items: 2,
		singleItem: false,
		autoPlay: true,
		center: true,
		navigation: false
	});

	$("#openWindow").click(function(e){  
		var fromId = $("#fromstationid").val();
		var toId = $("#tostationid").val();
		if(typeof(fromId)!="undefined" && typeof(toId)!="undefined" && fromId != toId ){
			window.open("/service/guide/search.html?"+fromId+"="+toId);  
		}
	});  
});