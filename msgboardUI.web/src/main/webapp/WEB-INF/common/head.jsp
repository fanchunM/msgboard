<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<!--<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>-->
<!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
<!-- 警告：通过 file:// 协议（就是直接将 html 页面拖拽到浏览器中）访问页面时 Respond.js 不起作用 -->
<!--[if lt IE 9]>
<script src="resources/bootstrap/js/respond.min.js"></script>
<script src="resources/bootstrap/js/html5shiv.min.js"></script>
<![endif] -->
</head>
<body>
<div class="hidden-xs hidden-sm">
	<div id="header">
		<div class="w1200">
			<ul class="toptools" style="padding-left:0px">
				<li class="left" style="width:200px;cursor:pointer;"  onclick="window.location.href='home'">
					<img src="resources/msgboardImages/login/公众留言版.png" width="180" >
				</li>
				<c:if test="${ifHome == true}">
					<li class="left">
					<div class="input-group searchBox">
						<input id="keyword" type="text" class="form-control input-lg" style="border-color:#e4e4e4">	
						<span style="border-radius: 0px;color:#999999" class="input-group-addon btn" onclick="getMessageForSearch()">搜索</span>
					</div>
				</li>
				</c:if>
				<c:if test="${person == null }">
					<li class="right">
						 &nbsp;<a href="login_page?biaozhi=1">登录</a><!--  &nbsp;&nbsp;
						<a href="/msgboard/login_page?biaozhi=2">注册</a>  -->
					</li>
				</c:if>
				<c:if test="${person != null }">
					<li class="right">&nbsp;<a href="logout">登出</a>&nbsp;</li>
					<c:if test="${person.nickName != null }">
						<c:choose>
							<c:when test="${fn:length(person.nickName)>8}">
								<li class="right">&nbsp;<a href="person">${fn:substring(person.nickName,0,8)}...</a>&nbsp;|</li>
							</c:when>
							<c:otherwise>
								<li class="right">&nbsp;<a href="person">${person.nickName }</a>&nbsp;|</li>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${person.nickName == null }">
						<li class="right">&nbsp;<a href="person">${person.phone }</a>&nbsp;|</li>
					</c:if>
				</c:if>
				<li class="right">&nbsp;<a href="message_reminder">消息提醒</a>&nbsp;|</li>
				<li class="right">&nbsp;<a href="messageRelease">发布留言</a>&nbsp;|</li>
				<li class="right">&nbsp;<a href="http://www.sz-mtr.com" target="_blank">地铁官网</a>&nbsp;|</li>
				<li class="right"><a href="home">首页</a>&nbsp;|</li>
			</ul>
		</div>
	</div>
</div>
</body>
</html>
