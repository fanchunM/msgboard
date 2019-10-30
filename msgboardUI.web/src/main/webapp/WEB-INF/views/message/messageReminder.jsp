<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>消息提醒 - 广济南社区</title>
<meta http-equiv="X-UA-Compatible" content="IE=8,IE=edge,chrome=1" >
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=0">
<link rel="shortcut icon" href="resources/msgboardImages/favicon.ico" type="image/x-icon" />
<!-- Bootstrap -->
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/common/style.css">
<link href="resources/css/common/mobile-menu.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/common/common.css">
<link rel="stylesheet" href="resources/assets/font-awesome/4.5.0/css/font-awesome.min.css">
<script type="text/javascript" src="resources/js/commonJs/jquery-1.8.3.min.js"></script>
<script src="resources/js/common/mobile-menu.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
<script src="resources/js/common/common.js"></script>
</head>
<jsp:include page="../../common/head.jsp"></jsp:include>
<jsp:include page="../../common/mobileHead.jsp"></jsp:include>
<body>
<input id="userId" type="hidden" value="${person.id }">
 	<div class="container theme-showcase mine-main-container" role="main">
		<div class="mn-page-header">
			<div class="mn-page-header-title">
				<span class=tit5>消息提醒</span>
			</div>
			<div class="mn-page-header-crumbs"></div>
		</div>
		<div class="mn-box">
			<jsp:include page="../../common/commonMessageReminder.jsp"></jsp:include>
		</div>
	</div>
	<jsp:include page="../../common/foot.jsp"></jsp:include>
</body>
</html>