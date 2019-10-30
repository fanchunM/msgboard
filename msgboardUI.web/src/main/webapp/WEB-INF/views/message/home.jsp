<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>广济南社区 - 苏州轨道交通公众交流平台</title>
	<meta http-equiv="X-UA-Compatible" content="IE=8,IE=edge,chrome=1" >
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="shortcut icon" href="resources/msgboardImages/favicon.ico" type="image/x-icon" />
	<meta name="keywords" content="广济南社区,苏州轨道交通,苏州地铁,公众交流,乘客留言,Suzhou Rail Tansit">
	<meta name="description" content="广济南社区是苏州轨道交通为广大市民乘客打造的一个互动交流平台。市民乘客可以针对苏州轨道交通规划、建设、运营、资源开发及物业保障等方面工作，提出相关的咨询、建议及投诉等问题。">
	<!-- Bootstrap -->
	<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="resources/layui/css/layui.css" rel="stylesheet">
	<link href="resources/layui/css/modules/layui-icon-extend/iconfont.css" rel="stylesheet">
	<link rel="stylesheet" href="resources/css/common/common.css">
	<link rel="stylesheet" href="resources/css/message/home.css">
	<link rel="stylesheet" href="resources/bootstrap/Font-Awesome-3.2.1/css/font-awesome.min.css">
	<script type="text/javascript" src="resources/js/commonJs/jquery-1.8.3.min.js"></script>
	<script src="resources/bootstrap/js/bootstrap.min.js"></script>
	<script src="resources/admin/js/common.js"></script>
	<script src="resources/layui/layui.js"></script>
	<script src="resources/js/common/common.js"></script>
	<script src="resources/js/message/home.js"></script>
	<script type="text/javascript">
		if(isMobile()) window.location.href="mobile_home";
	</script>
</head>
<body class="mine-msgboard">
<jsp:include page="../../common/head.jsp"></jsp:include>
<input id="userId" type="hidden" value="${person.id }">
<div class="mine-main-container w1200">
	<div class="border-div theme-div" style="margin-bottom: 20px;">
		<div class="border-title">主题分类</div>
		<ul id="subject">
		</ul>
	</div>
	<div class="msg-div">
		<div id="msgBeizhu" style="width:748px;color:#ccc;font-size: 13px;margin-bottom:5px;display:none"></div>
		<div id="messageInfo" class="border-div msg-list-container">
		</div>
		<div>
	    <ul class="pages"> 
   			  	    <li class="L mn-page" style="list-style-type:none"> 
 				</li> 
 				<li class="R"></li> 
 			</ul> 
  	<!-- 	    <ul class="pager"> 
   			    <li><a href="#">上一页</a></li> 
			    <li><a href="#">下一页</a></li>  
			  </ul> -->
		</div>
	</div>
	
	<div class="other-div" style="margin-bottom:10px">
		<div class="border-div" style="position:relative">
			<div class="border-title">权威发布</div>
			<div class="border-content"> 
				<ul id="newListInfo">
					
				</ul>
			</div>
			<div class="look-more">
				<a href="news?id=${ person.id}"><p style="margin:8px;color:#A9A9A9">查看更多&nbsp;></p></a>
			</div>
		</div>
		<div class="border-div" style="margin-top:10px;position:relative">
			<div class="border-title">便民问答</div>
			<div class="border-content"> 
				<ul id="faqsListInfo">
					
				</ul>
			</div>
			<div class="look-more">
				<a href="faqs"><p style="margin:8px;color:#A9A9A9">查看更多&nbsp;></p></a>
			</div>
		</div>
		<div class="border-div changyong" style="margin-top:10px">
			<div class="border-title">常用功能</div>
			<div style="overflow:hidden;margin-top:10px">
				<a href="http://www.sz-mtr.com/service/guide/map/" target=_blank>
					<div style="float:left;text-align:center;width:33.3%">
						<img class="image-changyong" src="resources/msgboardImages/changyong/线路.png"><br/>
						<span class="span-changyong">线路图</span>
					</div>
				</a>
				<a href="http://www.sz-mtr.com/service/guide/time/" target=_blank>
					<div style="float:left;text-align:center;width:33.3%">
					<img class="image-changyong" src="resources/msgboardImages/changyong/时钟.png"><br/>
					<span class="span-changyong">时刻表</span>
				</div>
				</a>
				<a href="http://www.sz-mtr.com/service/ServeYou/LostAndFound/" target=_blank>
					<div style="float:left;text-align:center;width:33.3%">
						<img class="image-changyong" src="resources/msgboardImages/changyong/失物招领.png"><br/>
						<span class="span-changyong">失物招领</span>
					</div>
				</a>
			</div>
			<div style="overflow:hidden;margin-bottom:15px;margin-top:20px;">
				<a href="http://www.sz-mtr.com/service/information/delay/" target=_blank>
					<div style="float:left;text-align:center;width:33.3%">
						<img class="image-changyong" src="resources/msgboardImages/changyong/延误时钟.png"><br/>
						<span class="span-changyong">延误告知</span>
					</div>
				</a>
				<a href="http://www.sz-mtr.com/tender/" target=_blank>
					<div style="float:left;text-align:center;width:33.3%">
						<img class="image-changyong" src="resources/msgboardImages/changyong/招标.png"><br/>
						<span class="span-changyong">招标信息</span>
					</div>
				</a>
				<a href="http://hire.sz-mtr.com:8888/hire/hireNetPortal/search_zp_position.do?b_query=link&operate=init&hireChannel=02&cms_chl_no=50&menuType=0&chl_id=50" target=_blank>
					<div style="float:left;text-align:center;width:33.3%">
						<img class="image-changyong" src="resources/msgboardImages/changyong/人才.png"><br/>
						<span class="span-changyong">人才招聘</span>
					</div>
				</a>
			</div>
		</div>
<!-- 		<div class="border-div" style="margin-top:10px"> -->
<!-- 			<div class="border-title">注意事项</div> -->
<!-- 			<div class="border-content">  -->
<!-- 				<ul style="margin-bottom:10px"> -->
<!-- 					<li class="quanwei-li "> -->
<%-- 						<a href="attention?userId=${person.id }&type=1" target=_blank> --%>
<!-- 							<div class="ellipsis"> -->
<!-- 								用户协议 -->
<!-- 							</div> -->
<!-- 						</a> -->
<!-- 					</li> -->
<!-- 					<li class="quanwei-li "> -->
<%-- 						<a href="attention?userId=${person.id }&type=2" target=_blank> --%>
<!-- 							<div class="ellipsis"> -->
<!-- 								倡议书 -->
<!-- 							</div> -->
<!-- 						</a> -->
<!-- 					</li> -->
<!-- 					<li class="quanwei-li "> -->
<%-- 						<a href="attention?userId=${person.id }&type=3" target=_blank> --%>
<!-- 							<div class="ellipsis"> -->
<!-- 								常见问题 -->
<!-- 							</div> -->
<!-- 						</a> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
<!-- 		</div> -->
	</div>
</div>
<jsp:include page="../../common/foot.jsp"></jsp:include>
</body>
</html>