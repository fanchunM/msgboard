<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>广济南社区</title>
	<meta http-equiv="X-UA-Compatible" content="IE=8,IE=edge,chrome=1" >
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=0">
	<!-- Bootstrap -->
	<script src="resources/bootstrap/js/jquery-1.12.0.min.js"></script>
	<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="resources/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="resources/js/commonJs/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="resources/jquery-cookie/jquery.cookie.js"></script>
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
// 	$(function(){
// 		getInitialPosition();
// 	})
// 	function getInitialPosition(){
// 		$(function() {
// 			var str = window.location.href;
// 			str = str.substring(str.lastIndexOf("/"));
// 			if ($.cookie(str)) {//$.cookie()：取cookie；
// 				$("html,body").animate({
// 					scrollTop : $.cookie(str)
// 				}, 1000);
// 			} else {
// 			}
// 		})
// 		$(window).scroll(function() {//滚动时，将滚动条的高度记录到cookie中
// 			var str = window.location.href;
// 			str = str.substring(str.lastIndexOf("/"));
// 			var top = $(document).scrollTop();
// 			$.cookie(str, top, {
// 				path : '/'
// 			});//保存到cookie中
// 			return $.cookie(str);
// 		})
// 	}
	
</script>
<jsp:include page="../../common/mobileHead.jsp"></jsp:include>
<input id="userId" type="hidden" value="${person.id }">
<input id="phoneState" type="hidden" value="${phoneState }">
<body>
	<div class="content_container">
		<!-- 搜索按钮 -->
<!-- 		<div style="position: fixed; bottom:90px;right:30px;z-index: 999"> -->
<!-- 			<a id="showSearch"><img src="resources/msgboardImages/放大镜.png" style="width: 40px; height:40px;"></a> -->
<!-- 		</div> -->
		<div style="position: fixed; bottom:30px;right:30px;z-index: 999">
			<a href="messageRelease"><img src="resources/msgboardImages/release.png" style="width: 40px; height:40px;"></a>
		</div>
		<div>
			<!-- 留言 -->
			<div id="messageInfo" class="mn-padding-box">
			</div>
			<!-- 分页 -->
			<div class="mn-padding-box" style="margin-bottom: 5px;overflow: hidden;">
<!-- 				<ul class="pages"> -->
<!-- 					<li class="L mn-page" style="list-style-type:none"> -->
		
<!-- 					</li> -->
<!-- 					<li class="R"></li> -->
<!-- 				</ul> -->
			  <ul class="pager">
<!-- 			    <li><a href="#">上一页</a></li> -->
<!-- 			    <li><a href="#">下一页</a></li> -->
			  </ul>
			</div>
 	    	<!-- 权威发布 -->
<!-- 			<div class="mn-padding-box">
				<div class="mn-box-content">
					<div style="padding: 5px">
						<p class="tit3" style="border-bottom: 1px solid #ccc;padding-left: 10px;">权威发布</p>
					</div>
					<div class="tab_Contentbox" style="margin-bottom:20px">
			            <ul id="newListInfo" class="list_01 clearfix">
						</ul>
						<div style="line-height: 30px;text-align: right;font-size: 9pt;overflow: hidden;right: 15px;"><a href="news?id=${ person.id}" target="_blank">More...</a></div>
	        		</div>
				</div>
			</div>
			-->
			<!-- 常见问答 -->
<!--  		<div class="mn-padding-box">
				<div class="mn-box-content">
					<div style="padding: 5px">
						<p class="tit3" style="border-bottom: 1px solid #ccc;padding-left: 10px;">便民问答</p>
					</div>
					<div class="tab_Contentbox" style="margin-bottom:20px">
			            <ul id="faqsListInfo" class="list_01 clearfix mn-red-box-content-ellipsis">
						</ul>
						<div style="line-height: 30px;text-align: right;font-size: 9pt;overflow: hidden;right: 15px;"><a href="faqs?id=${ person.id}" target="_blank">More...</a></div>
	        		</div>
				</div>
			</div>
			-->	
			<!-- 常用功能 -->
<!--  		<div class="mn-padding-box">
				<div class="mn-box-content">
					<div style="padding: 5px">
						<p class="tit3" style="border-bottom: 1px solid #ccc;padding-left: 10px;">常用功能</p>
					</div>
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
			-->	
			<!-- 注意事项 -->
<!--  	 <div class="mn-padding-box">
				<div class="mn-box-content">
					<div style="padding: 5px">
						<p class="tit3" style="border-bottom: 1px solid #ccc;padding-left: 10px;">注意事项</p>
					</div>
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
			-->
		</div>
		<div id="outer" style="display:none;position: fixed;top: 0px;left: 0px;width: 100%;height: 100%;background: rgb(0, 0, 0);opacity: 0.3;z-index: 888;"></div>
<%-- 		<jsp:include page="../../common/foot.jsp"></jsp:include> --%>
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
<script type="text/javascript">
	var phoneState = $("#phoneState").val();
	if(phoneState=='1'){
		alert("提示：该手机号曾被人注册使用过，如非本人注册使用，请修改密码！");
		window.location.href="person";
	}
</script>
</html>