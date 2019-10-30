<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${faqsDto.question } - 便民问答 - 广济南社区</title>
<meta http-equiv="X-UA-Compatible" content="IE=8,IE=edge,chrome=1" >
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=0">
<link rel="shortcut icon" href="resources/msgboardImages/favicon.ico" type="image/x-icon" />
<!-- Bootstrap -->
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/common/style.css">
<link rel="stylesheet" href="resources/css/common/common.css">
<link href="resources/css/common/mobile-menu.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/faqs/faqsDetail.css">
<script type="text/javascript" src="resources/js/commonJs/jquery-1.8.3.min.js"></script>
<script src="resources/js/common/mobile-menu.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<jsp:include page="../../common/head.jsp"></jsp:include>
<jsp:include page="../../common/mobileHead.jsp"></jsp:include>
<body>
<div>
 	<div class="container theme-showcase mine-main-container" role="main">
		<div class="mn-page-header">
			<div class="mn-page-header-title">
				<span class=tit5>便民问答</span>
			</div>
			<div class="mn-page-header-crumbs"></div>
		</div>
		
		<div class="mn-box">
			<div>
				<div class="col-xs-12 col-sm-12 col-md-12 text-left txt_subtitle4" style="font-size:16px;color:#a5a1a1">
					<img src="resources/msgboardImages/问.png" style="width:25px;height:25px">
					问题
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 text-left txt1">
					${faqsDto.question }
				</div>
	<!--  		<div class="col-xs-12 col-sm-12 col-md-12 text-right date-style">
					提问时间:<span>${faqsDto.createTimestamp }</span>
				</div>-->
				<div class="col-xs-12 col-sm-12 col-md-12 text-right" style="margin-bottom:0px;border-bottom: 1px dashed #ccc;width: 100%;padding: 12px 0;">
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 text-left txt_subtitle4" style="margin-top:25px;font-size:16px;color:#a5a1a1">
					<img src="resources/msgboardImages/回答.png" style="width:30px;height:30px">
					回答
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 text-left txt1">
				${faqsDto.answers }
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 text-right date-style" style="margin-bottom:25px">
					主题分类:${faqsDto.themeStr }
				</div>
	<!--			<div class="col-xs-12 col-sm-12 col-md-12 text-right date-style" style="margin-bottom:25px">
					回答时间:<span>${faqsDto.lastUpdateTimestamp }</span>
				</div> -->
			</div>
		</div>
	</div>
</div>
</body>
<jsp:include page="../../common/foot.jsp"></jsp:include>
</html>