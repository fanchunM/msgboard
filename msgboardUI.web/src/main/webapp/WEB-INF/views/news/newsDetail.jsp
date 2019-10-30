<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>${ newsDto.title} - 权威发布 - 广济南社区</title>
	<meta http-equiv="X-UA-Compatible" content="IE=8,IE=edge,chrome=1" >
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=0">
	<link rel="shortcut icon" href="resources/msgboardImages/favicon.ico" type="image/x-icon" />
	<!-- Bootstrap -->
	<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="resources/css/common/style.css">
	<link href="resources/css/common/mobile-menu.css" rel="stylesheet">
	<link rel="stylesheet" href="resources/css/news/newsDetail.css">
	<link rel="stylesheet" href="resources/css/common/common.css">
	<script type="text/javascript" src="resources/js/commonJs/jquery-1.8.3.min.js"></script>
	<script src="resources/js/common/mobile-menu.js"></script>
	<script src="resources/bootstrap/js/bootstrap.min.js"></script>
	<!--owl.carousel-->
	<link rel="stylesheet" href="resources/css/common/owl.carousel.css" />
	<script type="text/javascript" src="resources/js/common/owl.carousel.js"></script>
</head>
<style type="text/css">
	#owl-carousel.owl-carousel img{width: 100%;padding:0px;}
	#owl-carousel.owl-carousel .owl-item{padding: 5px;}
       #owl-carousel .images-wrapper{width: 100%;height:100%;}
       #owl-carousel .images-content{}
       .owl-buttons div{position:absolute;top:50%;width:25px;height:40px;margin-top:-20px;margin-left:5px;margin-right:5px;text-indent:-9999px;}
       .owl-prev{left:0;background-image:url(resources/msgboardImages/common/bg17.png);background-size:30px 40px;background-repeat:no-repeat;}
       .owl-next{right:0;background-image:url(resources/msgboardImages/common/bg18.png);background-size:30px 40px;background-repeat:no-repeat;}
       .owl-prev:hover{background-image:url(resources/msgboardImages/common/bg19.png);}
       .owl-next:hover{background-image:url(resources/msgboardImages/common/bg20.png);}
</style>
 <body>
 <jsp:include page="../../common/head.jsp"></jsp:include>
 <jsp:include page="../../common/mobileHead.jsp"></jsp:include>
 <div>
	 <div class="container theme-showcase mine-main-container" role="main">
	 	<div class="mn-box" style="min-height: -webkit-fill-available">
			<div class="txt_title1" >${ newsDto.title}</div>				
				<div  class="txt_subtitle" >${ newsDto.subTitle}</div>
				<div  class="txt_subtitle1">
					${ newsDto.publishTime}
<%-- 					时间：${ newsDto.publishTime}&nbsp;&nbsp; --%>
<!-- 					来源：<a >苏州轨道交通</a>&nbsp;&nbsp; -->
<%--                 	 阅读量：<span id="hits">${ newsDto.hits}</span>次 --%>
				</div>
				<div class="txt1">
					   ${ newsDto.newsContent}
				</div>
				
				<c:if test="${ newsDto.newsImageList ne null}">
					<div id="owl-carousel" class="owl-carousel">
						<c:forEach items="${ newsDto.newsImageList}" var ="newsImage">
							<div class="item">
							<div class="images-wrapper">
								<a href="javascript:void(0);"><img src="resources/upload/${ newsImage.address}"/></a>
								<div class="images-content"> ${ newsDto.newsDesc}</div>
							</div>
						</div>
						</c:forEach>
					</div>
				</c:if>
					<div  class="hidden-xs hidden-sm" style="text-align: center;">
					<p>&nbsp;</p>
					<canvas id="Canvas"></canvas>
					
					<div id="div_div" style="margin:0px auto;">
						<div id="qr_container" style="margin:10px; position:relative;color:#666">扫一扫在手机打开当前页</div>
					</div>
					<p>&nbsp;</p>
					</div>
		</div>
	</div>
 </div>
 <jsp:include page="../../common/foot.jsp"></jsp:include>
 </body>
 <script type="text/javascript">
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
				android: u.indexOf("Android") > -1 , //android终端或者uc浏览器  
				iPhone: u.indexOf("iPhone") > -1 , //是否为iPhone或者QQHD浏览器  
				iPad: u.indexOf("iPad") > -1, //是否iPad  
				webApp: u.indexOf("Safari") == -1 //是否web应该程序，没有头部与底部  
			};  
		 }(),  
		 language:(navigator.browserLanguage || navigator.language).toLowerCase()  
	}  
	if(browser.versions.android || browser.versions.iPhone || browser.versions.iPad){  
		$('.txt1 img').each(function() {
			$(this).css('width', '100%');
			$(this).css('height', '100%');
			// 去掉父级的样式
			$(this).parent().attr('style','');
			$(this).parent().parent().attr('style','');
		});
	}
	$(function(){
		$('#owl-carousel').owlCarousel({
			items: 1,
			navigation: true,
			navigationText: ["上一个","下一个"],
			autoPlay: true,
			stopOnHover: true
		}).hover(function(){
			$('.owl-buttons').show();
		}, function(){
			$('.owl-buttons').hide();
		});
	});
 $(function(){
		$('#owl-carousel').owlCarousel({
			items: 1,
			navigation: true,
			navigationText: ["上一个","下一个"],
			autoPlay: true,
			stopOnHover: true
		}).hover(function(){
			$('.owl-buttons').show();
		}, function(){
			$('.owl-buttons').hide();
		});
	});
 </script>
<script src="resources/js/news/qrcode.js" type="text/javascript"></script>
<script src="resources/js/news/jquery.qrcode.js" type="text/javascript"></script>
 </html>