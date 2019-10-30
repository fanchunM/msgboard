<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=8,IE=edge,chrome=1" >
    <meta charset="utf-8"/>
    <title>公众留言板系统</title>
    <meta name="description" content="overview &amp; stats"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="resources/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="resources/assets/font-awesome/4.5.0/css/font-awesome.min.css"/>
    <!-- page specific plugin styles -->
    <!-- text fonts -->
    <link rel="stylesheet" href="resources/assets/css/fonts.googleapis.com.css"/>
    <!-- ace styles -->
    <link rel="stylesheet" href="resources/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/>
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="resources/assets/css/ace-part2.min.css" class="ace-main-stylesheet"/>
    <![endif]-->
    <link rel="stylesheet" href="resources/assets/css/ace-skins.min.css"/>
    <link rel="stylesheet" href="resources/assets/css/ace-rtl.min.css"/>
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="assets/css/ace-ie.min.css"/>
    <![endif]-->
     <!-- ace settings handler -->
    <script src="resources/assets/js/ace-extra.min.js"></script>
    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->
    <!--[if lte IE 8]>
    <script src="resources/assets/js/html5shiv.min.js"></script>
    <script src="resources/assets/js/respond.min.js"></script>
    <![endif]-->
    <script src="resources/assets/js/ace-extra.min.js"></script>
    <script src="resources/admin/js/common.js"></script>
    <script src="resources/js/common/common.js"></script>
</head>
<body class="no-skin">
<div id="navbar" class="navbar navbar-default ace-save-state navbar-fixed-top">
	
    <div class="navbar-container ace-save-state" id="navbar-container">
        <div class="navbar-header pull-left">
            <img src="resources/assets/images/header_h1.png" style="height: 40px;margin-top: 8px;" onclick="window.location.href='/main'"/>
        </div>
        <div class="navbar-buttons navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
             <c:forEach items="${menuMsgDtoList}" var="obj">
           	  <c:forEach items="${obj.subItem}" var="sub">
              <c:if test="${sub.title=='留言管理'}">
                <li class="light-blue dropdown-modal">
                    <a href="javascript:void(0);" onclick="openUrlOrderPage('留言管理', null, null, 'messageList?replyStatus=0');">
                        <i class="ace-icon fa fa-envelope-o"></i>
                        <span>待回复留言数量：</span><span style="padding: 2px 6px;color:#f5f5f5;background-color:#fb0303; border-radius:10px;" id="hfNums"></span>
                    </a>
                </li>
                <li class="light-blue dropdown-modal">
                    <a href="javascript:void(0);" onclick="openUrlOrderPage('留言管理', null, null, 'messageList?approvalStatus=0');">
                        <i class="ace-icon fa fa-bell icon-animated-bell"></i>
                        <span id="PopupPrompt">待审核留言数量：</span><span style="padding: 2px 6px;color:#f5f5f5;background-color:#f16b2b; border-radius:10px;" id="shNums"></span>
                    </a>
                </li>
              </c:if>
              </c:forEach> 
			</c:forEach> 
                <li class="light-blue dropdown-modal">
                    <form id="logoutForm" style="display: none"></form>
                    <a href="javascript:void(0);" onclick="LogOff();">
                        <i class="ace-icon fa fa-power-off"></i>
                        <span>注销</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="main-container ace-save-state" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.loadState('main-container')
        } catch (e) {}
    </script>
    <div id="sidebar" class="sidebar responsive ace-save-state sidebar-fixed">
        <script type="text/javascript">
            try {
                ace.settings.loadState('sidebar')
            } catch (e) {}
        </script>
        <ul class="nav nav-list">
            <li class="active">
                <a><i class="fa fa-user-circle-o" aria-hidden="true"></i>
                	<c:if test="${loginUserDto ne null}">
   						<span>欢迎您：${loginUserDto.loginedChsName}</span>
   					</c:if>
				</a>
                <b class="arrow"></b>
             <c:forEach items="${menuMsgDtoList}" var="obj">
             <li class="">
               	<a href="javascript:void(0);" class="dropdown-toggle">
               		<i class="menu-icon+ ${obj.icon}" aria-hidden="true"></i>
                      <span class="menu-text">${obj.title}</span>
                    <b class="arrow fa fa-angle-down"></b>
                </a>
                <ul class="submenu nav-hide" style="display: none;">
                    <c:forEach items="${obj.subItem}" var="sub">
                    	<li class="" style="background-color: #2B3D53">
	                        <a href="javascript:void(0);" onclick="openUrlOrderPage('${sub.title}', null, null,'${sub.address}');">
	                    	     <i class="menu-icon + ${sub.icon}" aria-hidden="true"></i>
	                              <span class="menu-text">${sub.title}</span>
	                           </a>
	                 	</li>
	                 </c:forEach>
                 </ul>
              </li>  
              </c:forEach>  
            </li>
  <!--      <li class="">
                <a href="javascript:void(0);" class="dropdown-toggle">
                	<i class="menu-icon fa fa-user-circle" aria-hidden="true"></i>
                    <span class="menu-text">会员管理</span>
                </a>
            </li>
            <li class="">
                <a href="javascript:void(0);" class="dropdown-toggle">
                	<i class="menu-icon fa fa-newspaper-o" aria-hidden="true"></i>
                    <span class="menu-text">新闻管理</span>
                    <b class="arrow fa fa-angle-down"></b>
                </a>
                <ul class="submenu nav-hide" style="display: none;">
	                <li class="" style="background-color: #2B3D53">
	                    <a href="javascript:void(0);" onclick="openUrlOrderPage('新闻创建/修改', null, null, 'newsList');">
	                    	<i class="menu-icon fa fa-pencil" aria-hidden="true"></i>
	                        <span class="menu-text">新闻创建/修改</span>
	                    </a>
	                    <a href="javascript:void(0);" onclick="openUrlOrderPage('新闻审核', null, null, 'newsCheck');">
	                    	<i class="menu-icon fa fa-hand-paper-o" aria-hidden="true"></i>
	                        <span class="menu-text">新闻审核</span>
	                    </a>
	                </li>
                </ul>
            </li>
            <li class="">
                <a href="javascript:void(0);" class="dropdown-toggle" onclick="openUrlOrderPage('留言管理', null, null, 'messageList');">
                	<i class="menu-icon fa fa-commenting" aria-hidden="true"></i>
                    <span class="menu-text">留言管理</span>
                </a>
            </li>
            <li class="">
                <a href="javascript:void(0);" class="dropdown-toggle">
                	<i class="menu-icon fa fa-hashtag" aria-hidden="true"></i>
                    <span class="menu-text">话题管理</span>
                </a>
            </li>
            <li class="">
            	<a href="javascript:void(0);" class="dropdown-toggle">
                	<i class="menu-icon fa fa-newspaper-o" aria-hidden="true"></i>
                    <span class="menu-text">便民问答管理</span>
                    <b class="arrow fa fa-angle-down"></b>
                </a>
                <ul class="submenu nav-hide" style="display: none;">
	                <li class="" style="background-color: #2B3D53">
	                    <a>
	                    <a href="javascript:void(0);" onclick="openUrlOrderPage('便民问答创建/修改', null, null, 'faqsList');">
	                    	<i class="menu-icon fa fa-pencil" aria-hidden="true"></i>
	                        <span class="menu-text">便民问答创建/修改</span>
	                    </a>
	                    <a href="javascript:void(0);" onclick="openUrlOrderPage('便民问答审核', null, null, 'faqsCheck');">
	                    	<i class="menu-icon fa fa-hand-paper-o" aria-hidden="true"></i>
	                        <span class="menu-text">便民问答审核</span>
	                    </a>
	                </li>
                </ul>
            </li>
            --> 
<!--             系统管理 -->
<!--  	<li class="">
                <a href="javascript:void(0);" class="dropdown-toggle">
                	<i class="menu-icon fa fa-newspaper-o" aria-hidden="true"></i>
                    <span class="menu-text">系统管理</span>
                    <b class="arrow fa fa-angle-down"></b>
                </a>
                <ul class="submenu nav-hide" style="display: none;">
	                <li class="" style="background-color: #2B3D53">
	                    <a href="javascript:void(0);" onclick="openUrlOrderPage('用户管理', null, null, 'newsList');">
	                    	<i class="menu-icon fa fa-pencil" aria-hidden="true"></i>
	                        <span class="menu-text">用户管理</span>
	                    </a>
	                    <a href="javascript:void(0);" onclick="openUrlOrderPage('角色管理', null, null, 'newsCheck');">
	                    	<i class="menu-icon fa fa-hand-paper-o" aria-hidden="true"></i>
	                        <span class="menu-text">角色管理</span>
	                    </a>
	                    <a href="javascript:void(0);" onclick="openUrlOrderPage('权限管理', null, null, 'newsCheck');">
	                    	<i class="menu-icon fa fa-hand-paper-o" aria-hidden="true"></i>
	                        <span class="menu-text">权限管理</span>
	                    </a>
	                    <a href="javascript:void(0);" onclick="openUrlOrderPage('数据字典', null, null, 'newsCheck');">
	                    	<i class="menu-icon fa fa-hand-paper-o" aria-hidden="true"></i>
	                        <span class="menu-text">数据字典</span>
	                    </a>
	                    <a href="javascript:void(0);" onclick="openUrlOrderPage('答复模板', null, null, 'newsCheck');">
	                    	<i class="menu-icon fa fa-hand-paper-o" aria-hidden="true"></i>
	                        <span class="menu-text">答复模板</span>
	                    </a>
	                </li>
                </ul>
            </li>
          -->
        </ul><!-- /.nav-list -->
        <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
            <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
        </div>
    </div>
    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">
                <div class="page-header" style="position: absolute;left: 20px;right: 20px; padding-bottom: 2px;">
                    <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                        <ul class="breadcrumb">
                            <li><i class="ace-icon fa fa-home home-icon"></i></li>
                            <li id="gpsName1" class="active">首页</li>
                        </ul>
                    </div>
                </div><!-- /.page-header -->
                <div style="height: 100%;padding-top: 50px;">
                    <iframe id="content_frame" class="embed-responsive-item" src="homeLinks" style="width: 100%;height: 100%;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe>
               		 <p></p>
                </div>
            </div><!-- /.page-content -->
        </div>
    </div><!-- /.main-content -->
</div><!-- /.main-container -->

<!-- basic scripts -->
<!--[if !IE]> -->
<script src="resources/assets/js/jquery-2.1.4.min.js"></script>
<!-- <![endif]-->
<!--[if IE]>
<script src="resources/assets/js/jquery-1.11.3.min.js"></script>
<![endif]-->
<script src="resources/layui/layui.all.js"></script>
<script src="resources/assets/js/bootstrap.min.js"></script>
<!-- ace scripts -->
<script src="resources/assets/js/ace-elements.min.js"></script>
<script src="resources/assets/js/ace.min.js"></script>
<script src="resources/admin/js/main.js"></script>
</body>
</html>