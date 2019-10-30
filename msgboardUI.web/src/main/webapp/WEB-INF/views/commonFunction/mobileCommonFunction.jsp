<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>常用功能</title>
	<meta http-equiv="X-UA-Compatible" content="IE=8,IE=edge,chrome=1" >
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=0">
	<!-- Bootstrap -->
	<script src="resources/bootstrap/js/jquery-1.12.0.min.js"></script>
	<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="resources/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="resources/js/commonJs/jquery-1.8.3.min.js"></script>
	<link rel="stylesheet" href="resources/css/common/style.css">
	<link rel="stylesheet" href="resources/css/common/common.css">
	<link rel="stylesheet" href="resources/assets/font-awesome/4.5.0/css/font-awesome.min.css">
	<script src="resources/js/common/mobile-menu.js"></script>
	<script src="resources/admin/js/common.js"></script>
	<script src="resources/js/common/common.js"></script>
	<script src="resources/js/message/mobileHome.js"></script>
	<link href="resources/css/common/mobile-menu.css" rel="stylesheet">
	<link href="resources/css/message/mobileHome.css" rel="stylesheet">
	<link rel="stylesheet" href="resources/bootstrap/Font-Awesome-3.2.1/css/font-awesome.min.css">
</head>
<jsp:include page="../../common/mobileHead.jsp"></jsp:include>
<input id="userId" type="hidden" value="${person.id }">
<body>
	<div class="container theme-showcase mine-main-container" role="main">
		<!-- 常用功能 -->
		<div class="mn-page-header">
			<div class="mn-page-header-title">
				<span class=tit5>常用功能</span>
			</div>
			<div class="mn-page-header-crumbs"></div>
		</div>
		<div class="mn-box">
			<div id="changyong" class="tab_Contentbox" style="margin-bottom:20px;overflow: hidden;">
				<div style="overflow:hidden;margin-top:10px;">
					<a href="http://www.sz-mtr.com/service/guide/map/">
						<div style="float:left;text-align:center;width:33.3%">
							<img  src="resources/msgboardImages/线路.png"><br/>
							<span>线路图</span>
						</div>
					</a>
					<a href="http://www.sz-mtr.com/service/guide/time/">
						<div style="float:left;text-align:center;width:33.3%">
						<img src="resources/msgboardImages/时钟.png"><br/>
						<span>时刻表</span>
					</div>
					</a>
					<a href="http://www.sz-mtr.com/service/ServeYou/LostAndFound/">
						<div style="float:left;text-align:center;width:33.3%">
							<img src="resources/msgboardImages/失物招领.png"><br/>
							<span>失物招领</span>
						</div>
					</a>
				</div>
				<div style="overflow:hidden;margin-bottom:5px;margin-top:10px;margin-bottom:10px">
					<a href="http://www.sz-mtr.com/service/information/delay/">
						<div style="float:left;text-align:center;width:33.3%">
							<img src="resources/msgboardImages/延误时钟.png"><br/>
							<span>延误告知</span>
						</div>
					</a>
					<a href="http://www.sz-mtr.com/tender/">
						<div style="float:left;text-align:center;width:33.3%">
							<img src="resources/msgboardImages/招标.png"><br/>
							<span>招标信息</span>
						</div>
					</a>
					<a href="http://hire.sz-mtr.com:8888/hire/hireNetPortal/search_zp_position.do?b_query=link&operate=init&hireChannel=02&cms_chl_no=50&menuType=0&chl_id=50">
						<div style="float:left;text-align:center;width:33.3%">
							<img src="resources/msgboardImages/人才.png"><br/>
							<span>人才招聘</span>
						</div>
					</a>
				</div>
       		</div>
		</div>
	</div>
	<jsp:include page="../../common/foot.jsp"></jsp:include>
</body>
</html>