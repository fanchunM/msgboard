<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>注意事项</title>
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
<script type="text/javascript">
	if(!isMobile()) window.location.href = "home";
</script>
<jsp:include page="../../common/mobileHead.jsp"></jsp:include>
<input id="userId" type="hidden" value="${person.id }">
<body>
	<div class="container theme-showcase mine-main-container" role="main">
		<!-- 注意事项 -->
		<div class="mn-page-header">
			<div class="mn-page-header-title">
				<span class=tit5>注意事项</span>
			</div>
			<div class="mn-page-header-crumbs"></div>
		</div>
		<div class="mn-box">
			<div class="mn-box-content">
				<div class="tab_Contentbox" style="margin-bottom:20px">
		            <ul id="warning" class="list_01 clearfix" style="margin-bottom: 10px;">
						<li class="li mn-box-content-ellipsis">
							<a href="attention?type=1">
                                                                                                                苏州市轨道交通集团有限公司公众留言板系统网站服务条款			
                           	</a>
						</li>
						<li class="li mn-box-content-ellipsis">
							<a href="attention?type=2">
									关于轨道交通S1号线何时开工关于轨道交通S1号线何时开工关于轨道交通S1号线何时开工关于轨道交通S1号线何时开工关于轨道交通S1号线何时开工
							</a>
						</li>
						<li class="li mn-box-content-ellipsis">
							<a href="attention?type=3">
									关于轨道交通S1号线何时开工关于轨道交通S1号线何时开工关于轨道交通S1号线何时开工关于轨道交通S1号线何时开工关于轨道交通S1号线何时开工
							</a>
						</li>
						<li class="li mn-box-content-ellipsis">
							<a href="attention?type=4">
									关于轨道交通S1号线何时开工关于轨道交通S1号线何时开工关于轨道交通S1号线何时开工关于轨道交通S1号线何时开工关于轨道交通S1号线何时开工
							</a>
						</li>
						<li class="li mn-box-content-ellipsis">
							<a href="attention?type=5">
									关于轨道交通S1号线何时开工关于轨道交通S1号线何时开工关于轨道交通S1号线何时开工关于轨道交通S1号线何时开工关于轨道交通S1号线何时开工
							</a>
						</li>
					</ul>
        		</div>
			</div>
		</div>
	</div>
	<jsp:include page="../../common/foot.jsp"></jsp:include>
</body>
</html>