<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>个人中心</title>
<meta http-equiv="X-UA-Compatible" content="IE=8,IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=0">
<link rel="shortcut icon" href="resources/msgboardImages/favicon.ico" type="image/x-icon" />
<!-- Bootstrap -->
<script src="resources/bootstrap/js/jquery-1.12.0.min.js"></script>
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="resources/css/common/style.css">
<link href="resources/css/common/mobile-menu.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/common/common.css">
<link rel="stylesheet"
	href="resources/bootstrap/Font-Awesome-3.2.1/css/font-awesome.min.css">
<link rel="stylesheet"
	href="resources/assets/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/person/person.css">
<link rel="stylesheet" href="resources/Jcrop/css/jquery.Jcrop.css">
<script type="text/javascript" src="resources/Jcrop/js/jquery.min.js"></script>
<script type="text/javascript" src="resources/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript" src="resources/layui/layui.js"></script>
<script type="text/javascript" src="resources/js/commonJs/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="resources/js/common/mobile-menu.js"></script>
<script type="text/javascript" src="resources/js/person/person.js"></script>
<script type="text/javascript" src="resources/js/common/common.js"></script>
<script type="text/javascript" src="resources/Jcrop/js/jquery.Jcrop.js"></script>
<script type="text/javascript" src="resources/js/common/md5.js"></script>
<!-- <script type="text/javascript" src="resources/js/person/head.js"></script> -->
<script type="text/javascript" src="resources/lrz.all.bundle/lrz.all.bundle.js"></script>
</head>
<jsp:include page="../../common/head.jsp"></jsp:include>
<jsp:include page="../../common/mobileHead.jsp"></jsp:include>
<input id="userId" type="hidden" value="${person.id }">
<body>
	<div class="container theme-showcase mine-main-container" role="main"
		style="position: static; overflow: -webkit-paged-x">
		<div class="mn-page-header">
			<div class="mn-page-header-title">
				<span class=tit5>个人中心</span>
			</div>
			<div class="mn-page-header-crumbs"></div>
		</div>
		<div class="top-div">
			<div class="mn-person-menu-big border-div theme-div">
				<div class="border-title">个人中心</div>
				<div class="border-content">
					<ul id="newListInfo nav nav-tabs">
						<li class="quanwei-li tabs active"><a data-toggle="tab"
							class="ellipsis" href="#first"><i class="fa fa-vcard-o"
								aria-hidden="true"></i>资料设置</a></li>
						<li class="quanwei-li tabs"><a data-toggle="tab"
							class="ellipsis" href="#second"><i class="fa fa-envelope"
								aria-hidden="true"></i>我的消息</a></li>
						<li class="quanwei-li tabs"><a data-toggle="tab"
							class="ellipsis" href="#third"><i class="fa fa-tasks"
								aria-hidden="true"></i>我的留言</a></li>
					</ul>
				</div>
			</div>
			<div class="mn-person-menu-small daohang-div">
				<div style="height: 100%; margin: 0 auto">
					<ul class="nav nav-tabs" style="height: 100%; width: 100%;">
						<li class="tabs active" style="width: 33.33%"><a
							href="#first" data-toggle="tab"> <span
								style="font-size: 15px; font-weight: blod">个人中心</span>
						</a></li>
						<li class="tabs" style="width: 33.33%"><a href="#second"
							data-toggle="tab"> <span
								style="font-size: 15px; font-weight: blod">我的消息</span>
						</a></li>
						<li class="tabs" style="width: 33.33%"><a href="#third"
							data-toggle="tab"> <span
								style="font-size: 15px; font-weight: blod">我的留言</span>
						</a></li>
					</ul>
				</div>
			</div>
			<div class="mn-box tab-content">
				<!--个人资料 -->
				<div id="first" class="tab-pane fade in active">
					<c:if test="${info ne null}">
						<div style="font-family: fantasy;margin-top: 10px;color: red;text-align: center;font-size: 18px;">
							${info}
						</div>
					</c:if>
					<div style="padding: 15px;">
						<!--修改用户名 -->
						<div style="margin-bottom: 20px;">
							<div
								style="background-color: white; border: 1px solid #ccc;; padding: 0px 10px; overflow: hidden; height: 40px; line-height: 40px">
								<ul>
									<li style="float: left; width: 22%"><span>用户名</span></li>
									<li style="float: left; width: 50%; color: #808080"><span>${person.userName }</span></li>
									<li
										style="float: left; width: 28%; text-align: right; color: #337AB7"><span
										onclick="closeTag(1);" style="cursor: pointer;">修改>></span></li>
								</ul>
							</div>
							<div id="tag1"
								style="border: 1px solid #ccc; background-color: white; padding: 20px; border-top: none; overflow: hidden; display: none">
								<div style="overflow: hidden">
									<div class="col-xs-5 col-sm-4 col-md-3 text-right">现用户名</div>
									<div class="col-xs-7 col-sm-4 col-md-3 text-left">${person.userName }</div>
									<div class="col-sm-4 col-md-6 text-center"></div>
								</div>
								<div style="margin-top: 20px; overflow: hidden">
									<div class="col-xs-5 col-sm-4 col-md-3 text-right">新用户名</div>
									<div class="col-xs-7 col-sm-4 col-md-3 text-left">
										<input id="userName" type="text" class="form-control"
											style="display: inline">
									</div>
									<div class="col-sm-4 col-md-6 text-center"></div>
								</div>
								<div style="margin-top: 20px; overflow: hidden">
									<div class="col-xs-6 col-sm-4 col-md-4 text-right">
										<button class="btn btn-large btn-primary"
											onclick="changeUserName()">提交</button>
									</div>
									<div class="col-xs-6 col-sm-4 col-md-4 text-left">
										<button onclick="closeTag(1);"
											class="btn btn-large btn-primary">关闭</button>
									</div>
									<div class="col-sm-4 col-md-4 text-center"></div>
								</div>
							</div>
						</div>
						<!--修改密码 -->
						<div >
							<div
								style="background-color: white; border: 1px solid #ccc;; padding: 0px 10px; overflow: hidden; height: 40px; line-height: 40px">
								<ul>
									<li style="float: left; width: 22%"><span>密&nbsp;&nbsp;&nbsp;码</span></li>
<%-- 									<li style="float: left; width: 50%; color: #808080"><span>${person.userName }</span></li> --%>
									<li
										style="float: left; width: 78%; text-align: right; color: #337AB7"><span
										onclick="closeTag(3);" style="cursor: pointer;">修改>></span></li>
								</ul>
							</div>
							<div id="tag3"
								style="border: 1px solid #ccc; background-color: white; border-top: none; padding: 20px; overflow: hidden; display: none">
<!-- 								<div style="overflow: hidden"> -->
<!-- 									<div class="col-xs-5 col-sm-4 col-md-3 text-right">原密码</div> -->
<!-- 									<div class="col-xs-7 col-sm-4 col-md-3 text-left"> -->
<!-- 										<input id="oldPwd" type="password" class="form-control" -->
<!-- 											style="display: inline"> -->
<!-- 									</div> -->
<!-- 									<div class="col-sm-4 col-md-6 text-center"></div> -->
<!-- 								</div> -->
								<div style="margin-top: 20px; overflow: hidden">
									<div class="col-xs-5 col-sm-4 col-md-3 text-right">新密码</div>
									<div class="col-xs-7 col-sm-4 col-md-3 text-left">
										<input id="newPwd" type="password" class="form-control"
											style="display: inline">
									</div>
									<div class="col-sm-4 col-md-6 text-center"></div>
								</div>
								<div style="margin-top: 20px; overflow: hidden">
									<div class="col-xs-5 col-sm-4 col-md-3 text-right">确认密码</div>
									<div class="col-xs-7 col-sm-4 col-md-3 text-left">
										<input id="mutiNewPwd" type="password" class="form-control"
											style="display: inline">
									</div>
									<div class="col-sm-4 col-md-6 text-center"></div>
								</div>
								<div style="margin-top: 20px; overflow: hidden">
									<div class="col-xs-6 col-sm-4 col-md-4 text-right">
										<button class="btn btn-large btn-primary"
											onclick="changePwd()">提交</button>
									</div>
									<div class="col-xs-6 col-sm-4 col-md-4 text-left">
										<button onclick="closeTag(3);"
											class="btn btn-large btn-primary">关闭</button>
									</div>
									<div class="col-sm-4 col-md-4 text-center"></div>
								</div>
							</div>
						</div>
						<!--修改手机号 -->
						<div>
							<div
								style="background-color: white; border: 1px solid #ccc; padding: 0px 10px; overflow: hidden; height: 40px; margin-top: 20px; line-height: 40px">
								<ul>
									<li style="float: left; width: 22%"><span>手机号</span></li>
									<li style="float: left; width: 50%; color: #808080"><span>${person.phone }</span></li>
									<li
										style="float: left; width: 28%; text-align: right; color: #337AB7"><span
										onclick="closeTag(2);" style="cursor: pointer;">更换>></span></li>
								</ul>
							</div>
							<div id="tag2"
								style="border: 1px solid #ccc; background-color: white; border-top: none; padding: 20px; overflow: hidden; display: none">
								<div style="overflow: hidden">
									<div class="col-xs-5 col-sm-4 col-md-3 text-right">原号码</div>
									<div class="col-xs-7 col-sm-4 col-md-3 text-left">${person.phone }</div>
									<div class="col-sm-4 col-md-6 text-center"></div>
								</div>
								<div style="margin-top: 20px; overflow: hidden">
									<div class="col-xs-5 col-sm-4 col-md-3 text-right">新号码</div>
									<div class="col-xs-7 col-sm-4 col-md-3 text-left">
										<input id="phone" type="text" class="form-control"
											style="display: inline">
									</div>
									<div class="col-sm-4 col-md-6 text-center"></div>
								</div>
								<!-- 								<div style="margin-top:20px;overflow: hidden"> -->
								<!-- 									<div class="col-xs-5 col-sm-4 col-md-3 text-left"> -->
								<!-- 										<button class="btn btn-default" id="registerYzm">获取验证码</button> -->
								<!-- 									</div> -->
								<!-- 									<div class="col-xs-7 col-sm-4 col-md-3 text-left"> -->
								<!-- 										<input name="validate" class="form-control" style="width:65px;display:inline"> -->
								<!-- 									</div> -->
								<!-- 									<div class="col-sm-4 col-md-6 text-center"></div> -->
								<!-- 								</div> -->
								<div style="margin-top: 20px; overflow: hidden">
									<div class="col-xs-6 col-sm-4 col-md-4 text-right">
										<button class="btn btn-large btn-primary"
											onclick="changePhone()">提交</button>
									</div>
									<div class="col-xs-6 col-sm-4 col-md-4 text-left">
										<button onclick="closeTag(2);"
											class="btn btn-large btn-primary">关闭</button>
									</div>
									<div class="col-sm-4 col-md-4 text-center"></div>
								</div>
							</div>
						</div>
						<!--修改性别 -->
						<div>
							<div
								style="background-color: white; border: 1px solid #ccc;; padding: 0px 10px; overflow: hidden; height: 40px; margin-top: 20px; line-height: 40px">
								<ul>
									<li style="float: left; width: 22%"><span>性&nbsp;&nbsp;&nbsp;别</span></li>
									<li style="float: left; width: 50%; color: #808080"><span>
											<c:if test="${person.gender eq '未知' }">未知</c:if> <c:if
												test="${person.gender eq '男' }">男</c:if> <c:if
												test="${person.gender eq '女' }">女</c:if>
									</span></li>
									<li
										style="float: left; width: 28%; text-align: right; color: #337AB7"><span
										onclick="closeTag(4);" style="cursor: pointer;">修改>></span></li>
								</ul>
							</div>
							<div id="tag4"
								style="border: 1px solid #ccc; background-color: white; border-top: none; padding: 20px; overflow: hidden; display: none">
								<div style="overflow: hidden">
									<div class="col-xs-6 col-sm-4 col-md-3 text-right">未知</div>
									<div class="col-xs-6 col-sm-4 col-md-3"
										style="margin-top: -3px" class="radio">
										<input name="sex" type="radio" value="0"
											${person.gender eq '未知'?"checked":""}>
									</div>
									<div class="col-sm-4 col-md-6 text-center"></div>
								</div>
								<div style="margin-top: 20px; overflow: hidden">
									<div class="col-xs-6 col-sm-4 col-md-3 text-right">男</div>
									<div class="col-xs-6 col-sm-4 col-md-3"
										style="margin-top: -3px" class="radio">
										<input name="sex" type="radio" value="1"
											${person.gender eq '男'?"checked":""}>
									</div>
									<div class="col-sm-4 col-md-6 text-center"></div>
								</div>
								<div style="margin-top: 20px; overflow: hidden">
									<div class="col-xs-6 col-sm-4 col-md-3 text-right">女</div>
									<div class="col-xs-6 col-sm-4 col-md-3"
										style="margin-top: -3px" class="radio">
										<input name="sex" type="radio" value="2"
											${person.gender eq '女'?"checked":""}>
									</div>
									<div class="col-sm-4 col-md-6 text-center"></div>
								</div>
								<div style="margin-top: 20px; overflow: hidden">
									<div class="col-xs-6 col-sm-4 col-md-4 text-right">
										<button class="btn btn-large btn-primary"
											onclick="changeSex()">提交</button>
									</div>
									<div class="col-xs-6 col-sm-4 col-md-4 text-left">
										<button onclick="closeTag(4);"
											class="btn btn-large btn-primary">关闭</button>
									</div>
									<div class="col-sm-4 col-md-4 text-center"></div>
								</div>
							</div>
						</div>

						<!--选择头像 -->
											<div>
												<div style="background-color: white;border:1px solid #ccc;;padding:0px 10px;overflow: hidden;height:40px;margin-top:20px;line-height: 40px">
													<ul>
														<li style="float:left;width:22%"><span>头&nbsp;&nbsp;&nbsp;像</span></li>
														<li style="float:left;width:78%;text-align:right;color:#337AB7"><span onclick="closeTag(5);" style="cursor: pointer;">更换>></span></li>
													</ul>
												</div>
												<div id="tag5" style="border:1px solid #ccc;background-color: white;border-top: none;padding:20px;overflow: hidden;display:none">
													<input id="headPortraits" type="hidden" value="${person.headPortraits }">
													<div style="overflow: hidden">
														<div class="head-images" image-name="weizhi.png"><img src="resources/msgboardImages/headPortraits/weizhi.png" style="width:100%;height:100%"></div>
														<div class="head-images" image-name="nan.png"><img src="resources/msgboardImages/headPortraits/nan.png" style="width:100%;height:100%"></div>
														<div class="head-images" image-name="nv.png"><img src="resources/msgboardImages/headPortraits/nv.png" style="width:100%;height:100%"></div>
														<div class="head-images" image-name="line1.jpg"><img src="resources/msgboardImages/headPortraits/line1.jpg" style="width:100%;height:100%"></div>
														<div class="head-images" image-name="line2.jpg"><img src="resources/msgboardImages/headPortraits/line2.jpg" style="width:100%;height:100%"></div>
														<div class="head-images" image-name="line3.jpg"><img src="resources/msgboardImages/headPortraits/line3.jpg" style="width:100%;height:100%"></div>
														<div class="head-images" image-name="line4.jpg"><img src="resources/msgboardImages/headPortraits/line4.jpg" style="width:100%;height:100%"></div>
<!-- 														<div class="head-images" image-name="qiuxiang.jpg"><img src="resources/msgboardImages/headPortraits/qiuxiang.jpg" style="width:100%;height:100%"></div> -->
<!-- 														<div class="head-images" image-name="tangbohu.jpg"><img src="resources/msgboardImages/headPortraits/tangbohu.jpg" style="width:100%;height:100%"></div> -->
<!-- 														<div class="head-images" image-name="wenzhengming.jpg"><img src="resources/msgboardImages/headPortraits/wenzhengming.jpg" style="width:100%;height:100%"></div> -->
<!-- 														<div class="head-images" image-name="wuzixu.jpg"><img src="resources/msgboardImages/headPortraits/wuzixu.jpg" style="width:100%;height:100%"></div> -->
<!-- 														<div class="head-images" image-name="xuzhenqing.jpg"><img src="resources/msgboardImages/headPortraits/xuzhenqing.jpg" style="width:100%;height:100%"></div> -->
<!-- 														<div class="head-images" image-name="zhuzhishan.jpg"><img src="resources/msgboardImages/headPortraits/zhuzhishan.jpg" style="width:100%;height:100%"></div> -->
														<div class="head-images" image-name="红帽子.jpg"><img src="resources/msgboardImages/headPortraits/红帽子.jpg" style="width:100%;height:100%"></div>
														<div class="head-images" image-name="蓝帽子.jpg"><img src="resources/msgboardImages/headPortraits/蓝帽子.jpg" style="width:100%;height:100%"></div>
														<div class="head-images" image-name="绿帽子.jpg"><img src="resources/msgboardImages/headPortraits/绿帽子.jpg" style="width:100%;height:100%"></div>
													</div>
													<div style="margin-top:20px;overflow: hidden">
														<div class="col-xs-6 col-sm-4 col-md-4 text-right">
															<button class="btn btn-large btn-primary" onclick="changeHead()">提交</button>
														</div>
														<div class="col-xs-6 col-sm-4 col-md-4 text-left">
															<button onclick="closeTag(5);" class="btn btn-large btn-primary">关闭</button>
														</div>
														<div class="col-sm-4 col-md-4 text-center"></div>
													</div>
												</div>
											</div>
						<!-- 上传头像 -->
<!-- 上传自定义头像 -->
<!-- 						<div> -->
<!-- 							<div -->
<!-- 								style="background-color: white; border: 1px solid #ccc;; padding: 0px 10px; overflow: hidden; height: 40px; margin-top: 20px; line-height: 40px"> -->
<!-- 								<ul> -->
<!-- 									<li style="float: left; width: 22%"><span>头&nbsp;&nbsp;&nbsp;像</span></li> -->
<!-- 									<li -->
<!-- 										style="float: left; width: 78%; text-align: right; color: #337AB7"><span -->
<!-- 										onclick="closeTag(5);" style="cursor: pointer;">上传>></span></li> -->
<!-- 								</ul> -->
<!-- 							</div> -->
<!-- 							<div id="tag5" -->
<!-- 								style="border: 1px solid #ccc; background-color: white; border-top: none; padding: 20px; overflow: hidden; display: none"> -->
<!-- 								<div style="display: block; width: 100%;text-align: -webkit-center;"> -->
<!-- 									<img id="headResult" alt="头像" style="width: 100%; height: 100%"> -->
<!-- 									<div style="margin-top: 40px; height: 122px; position: relative;" id="previewImage_box2"> -->
<!-- 										<div style="border-radius:100%;overflow: hidden;left:0px;right:0px;width: 100px;height: 100px;" id ="image_crop_preview_div2"> -->
<!-- 												<img id="previewImage2" alt="预览" src="resources/msgboardImages/headPortraits/wenzhengming.jpg" style="width:122px; height:122px;"> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 								<div style="height: 30px; float: left; border-radius: 5px; margin-top: 15px"> -->
<!-- 									<div style="padding: 0px 5px; border-radius: 5px; color: #fff; background-color: #3385FF; float: left; height: 30px; line-height: 30px;">选择头像 -->
<!-- 									</div> -->
<!-- 									<form id="headForm" method="POST" enctype="multipart/form-data"> -->
<!-- 										<input id="headPic" type="file" accept="image/*" -->
<!-- 											style="display: block; position: absolute; width: 74px; height: 30px; font-size: 100px; opacity: 0; filter: alpha(opacity = 0); float: left; padding-left: 0px; z-index: 999"> -->
<!-- 									</form> -->
<!-- 								</div> -->
								
<!-- 								<div style="height: 30px; float: left; border-radius: 5px; margin-top: 15px"> -->
<!-- 									<div style="padding: 0px 5px; border-radius: 5px; color: #fff; background-color: #3385FF; float: left;  height: 30px; line-height: 30px; margin-left: 20px;" onclick="uploadHead();">上传头像 -->
<!-- 									</div> -->
<!-- 									<form id="baseHeadForm" method="post" enctype="multipart/form-data"> -->
<!-- 										<input type="hidden" id="base64" name="base64"> -->
<!-- 										<input type="hidden" id="width" name="width"> -->
<!-- 										<input type="hidden" id="height" name="height"> -->
<!-- 										<input type="hidden" id="x" name="x"> -->
<!-- 										<input type="hidden" id="y" name="y"> -->
<!-- 									</form> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 上传自定义头像 -->
					</div>
				</div>
				<!-- 				我的系统消息 -->
				<div id="second" class="tab-pane fade">
					<jsp:include page="../../common/commonMessageReminder.jsp"></jsp:include>
				</div>
				<!-- 				我的留言 -->
				<div id="third" class="tab-pane fade" style="color: white">
					<div id="messageInfo" class="border-div msg-list-container">
					</div>
					<div>
						<ul class="pages">
							<li class="L mn-page" style="list-style-type: none"></li>
							<li class="R"></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<jsp:include page="../../common/foot.jsp"></jsp:include>
</html>