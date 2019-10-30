<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta name="keywords" content="">
	<meta name="description" content="">
</head>
<script type="text/javascript">
    $(function () {
        showScroll();
        function showScroll() {
            $(window).scroll(function () {
                var scrollValue = $(window).scrollTop();
                scrollValue > 150 ? $('#backTop').css("display","block") : $('#backTop').css("display","none");
            });
        }
    });
</script>
<body>
<div class="hidden-md hidden-lg">
<div id="backTop" style="position: fixed;bottom:30px;right: 30px;z-index:9999;display:none">
	<a href="#" id="toTop"><img src="resources/msgboardImages/backtop.png" style="width: 40px; height:40px;"></a>
</div>
<div class="index_pagetop">
        <!--页头开始-->
        <div class="mn-topbar-home">
            <a href="mobile_home" title="返回首页" style="padding:9px; display:block;"><img src="resources/images/home.png" alt="返回首页" border="0" width="32px" height="32px"></a>
        </div>
        <div class="mn-topbar-logo">
            <p><a href="javascript:void(0);"><img border="0" alt="" src="resources/msgboardImages/login/公众留言版(2).png"></a></p>
        </div>
        <div class="mn-topbar-menu">
            <a href="javascript:void(0);"><img src="resources/images/mainmenutags.jpg" border="0" alt=""></a>
        </div>
        <div class="mn-topbar-menu-list" style="display: none;">
            <ul class="mn-topbar-menu-ul">
            	<li><a href="http://www.sz-mtr.com">地铁官网</a></li>
                <li><a href="messageRelease">发布留言</a></li>
                <c:if test="${isMobileHome eq true }">
                	<li><a onclick="showSearchDialog();">搜索留言</a></li>
                </c:if>
                <li><a href="news">权威发布</a></li>
                <li><a href="faqs">便民问答</a></li>
               <!--   
                <li>
                	<a href="mobileCommonFunction">常用功能</a>
                </li>
                <li>
                	<a href="mobileAttention">注意事项</a>
                </li>
                -->
                <li><a href="message_reminder">消息提醒</a></li>
               
                <c:if test="${person != null }">
	                <li><a href="person">${person.nickName }</a></li>
	               	<li><a href="logout">登出</a></li>
                </c:if>
                <c:if test="${person == null }">
                	<li><a href="login_page?biaozhi=1">登录</a></li>
	                <li><a href="login_page?biaozhi=2">注册</a></li>
                </c:if>
                <div class="clear"></div>
            </ul>
        </div>
        <div class="clearAll"></div>
    </div>
</div>

<div class="search_container" style="display:none;">
		<header class="header">
			<div class="left">
				<a href="javascript:void(0);" class="back"><i class="fa fa-angle-left" aria-hidden="true"></i></a>
			</div>
			<div class="cent">
				<span>留言搜索</span>
			</div>
		</header>
		<div class="search">
			<div class="searbox flexbox">
				<div class="ipt" id="searchMessage">
					<input id="keyword" type="search" name="q" value="" placeholder="请输入关键词" autocomplete="off">
					<a href="javascript:void(0);" class="off" style="display: none;"></a>
				</div>
				<a href="javascript:void(0);" class="btn searchBtn"><i></i></a>
			</div>
		</div>
		<div class="searchCategory" style="display: block;">
			<h3>
				<i class="fa fa-align-left" aria-hidden="true"></i><span>主题分类</span>
			</h3>
			<div class="cont clearfix cateLevelOne">
			</div>
			<div class="splitLine" style="margin:0px 10px;"></div>
			<div class="cont clearfix cateLevelTwo">
			</div>
			<div class="clearBtn"><a class="searchSubmit" href="javascript:void(0);">确定</a><a class="searchCancel" href="javascript:void(0);">关闭</a></div>
		</div>
	</div>

</body>