<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>新闻详情</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" >
	<!-- Bootstrap -->
	<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="resources/css/common/style.css">
	<link href="resources/css/common/mobile-menu.css" rel="stylesheet">
	<link rel="stylesheet" href="resources/css/news/newsDetail.css">
	<link rel="stylesheet" href="resources/css/common/common.css">
	<link rel="stylesheet" href="resources/admin/css/commonForAdmin.css" />
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

<script type="text/javascript">
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
 <body>

 <div class="hidden-md hidden-lg">
 	<div style="margin-top: 60px"></div>
 </div>
 <div>
	 <div class="container theme-showcase mine-main-container" role="main">
	 	<div class="mn-box" style="min-height: -webkit-fill-available">
			<div class="txt_title1" >${ newsDto.title}</div>				
				<div  class="txt_subtitle" >${ newsDto.subTitle}</div>
				<div  class="txt_subtitle1">
					时间：${ newsDto.publishTime}&nbsp;&nbsp;
				<!--  	来源：<a href="#" target=_blank>苏州轨道交通</a>&nbsp;&nbsp;-->
                <!--  	 阅读量：<span id="hits">${ newsDto.hits}</span>次-->
				</div>
				<div class="txt1">
					<c:if test="${newsDto.newsDesc ne null}">
					 	新闻说明：${newsDto.newsDesc}	
					</c:if>
				</div>
				<div class="txt1">
				   <c:if test="${ newsDto.newsContent ne null}">				
					       新闻内容：  ${ newsDto.newsContent}
				   </c:if> 
				</div>
				<div class="txt1">
				   <c:if test="${ newsDto.newsImageList ne null}">
				              图片：
					 <div id="owl-carousel" class="owl-carousel">
						 <c:forEach items="${ newsDto.newsImageList}" var ="newsImage">
							<div class="item">
							   <div class="images-wrapper">
								  <a href="javascript:void(0);" onclick="javascript:window.open(this.src);"><img src="resources/upload/${ newsImage.address}"/></a>
							   <div class="images-content"> ${ newsDto.newsDesc}</div>
							   </div>
						   </div>
						 </c:forEach>
					</div>
				   </c:if>
				</div>
				<div class="txt1">
				   <c:if test="${newsDto.linkURL ne null}">
				              新闻外链：
				   		<a href=//${newsDto.linkURL} target="_blank">${newsDto.linkURL}</a> （点击外链进行查看）
				   </c:if>
				</div>
<!-- 					<div  class="hidden-xs hidden-sm"> -->
<!-- 					<p>&nbsp;</p> -->
<%-- 					<canvas id="Canvas"></canvas> --%>
					
<!-- 					<div id="div_div" style="margin:0px auto;"> -->
<!-- 						<div id="qr_container" style="margin:10px; position:relative;color:#666">扫一扫在手机打开当前页</div> -->
<!-- 					</div> -->
<!-- 					<p>&nbsp;</p> -->
<!-- 					</div> -->
		</div>
	</div>
 </div>

 </body>
 </html>