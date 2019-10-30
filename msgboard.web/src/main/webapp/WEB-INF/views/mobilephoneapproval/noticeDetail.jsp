<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>公众留言板审核</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<script type="text/javascript">
	var ctxMobile = '${ctxMobile}';
	var ctxjt = '${ctxjt}';
	var ip = '${ip}';
	//webview(snap)会调用该方法处理回退
	function doCallBack() {
		document.getElementById("procID").click();
	}
</script>
<link rel="stylesheet" href="resources/css/common/common.css">
<link rel="stylesheet" href="resources/mobilePhoneApproval/layui/css/layui.css" media="all" />
<link rel="stylesheet" href="resources/mobilePhoneApproval/css/global.css" media="all" />
<link rel="stylesheet" href="resources/mobilePhoneApproval/css/mobile_gufeng.css" media="all" />
</head>
<body>
<input type="hidden" id="messageId" value="${dto.id }">
	<div class="main layui-clear">
		<div class="fly-panel" pad20>
			<div style="position: relative;">
				<img src="resources/msgboardImages/te-1.png" alt="" style="height: 35px">
				<div style="position: absolute; top: 15%; left: 8%;">
					<span
						style="color: white; font-size: 16px; font-family: JetLinkMediumOl117e27b9131c864;">公众留言板审批</span>
				</div>
			</div>
			<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
				<ul class="layui-tab-title">
					<li class="layui-this" style="width:50%">基本信息</li>
<!-- 				    <li id="zhengwenTAB">正文</li> -->
<!-- 										<li>关联公文</li> -->
					<li style="width:50%">附件</li>
				</ul>
				<div class="layui-tab-content">
					<div class="layui-tab-item layui-show" id="jibenInfo">
						<div class="layui-row">
							<div class="layui-col-xs6 border-title">
								<c:if test="${type eq 'comment' }">
									<label class="layui-form-label-left">编号</label> 
									<label class="layui-form-label-left content-color" id="detail-title">
										${dto.serialNumber }
									</label>
								</c:if>
								<c:if test="${type eq null}">
									<label class="layui-form-label-left">标题</label> 
									<label class="layui-form-label-left content-color" id="detail-title">
										${dto.title }
									</label>
								</c:if>
								
							</div>
							<div class="layui-col-xs6 ">
								<label class="layui-form-label-left">发起时间</label> 
								<label class="layui-form-label-left content-color" id="create-time">
									${dto.createTimestamp }
								</label>
								<c:if test="${dto.themeStr ne null}">
								<label class="layui-form-label-left content-color">
									${dto.themeStr }
								</label>
								</c:if>
							</div>
						</div>
						<hr class="">

						<hr class="">
						<div class="layui-row">
							<div class="layui-col-xs6 border-title">
								<label class="layui-form-label-left">报告人</label> 
								<label class="layui-form-label-left content-color" id="create-person">
									${dto.personName }
								</label>
							</div>
							<div class="layui-col-xs6 ">
								<label class="layui-form-label-left">审核状态</label> 
								<label class="layui-form-label-left content-color" id="current-act">
									<c:if test="${dto.approvalStatus eq '未处理' }">未处理</c:if>
									<c:if test="${dto.approvalStatus eq '审核不通过' }">审核不通过</c:if>
									<c:if test="${dto.approvalStatus eq '审核通过' }">审核通过</c:if>
								</label>
							</div>
						</div>
						<hr>
						<div class="layui-row">
							<div class="layui-col-xs12 ">
								<label class="layui-form-label-left">内容</label> 
								<label class="layui-form-label-left content-color" id="content">
									${dto.commentStr } 
								</label>
								
							</div>
						</div>
						<c:if test="${type eq null}">
							<div class="layui-row">
								<div class="layui-col-xs12 ">
									<label class="layui-form-label-left">回复</label> 
									<label class="layui-form-label-left content-color">
										<textarea id="reply" style="font-size:16px;padding:5px;color:#999;border: #ccc 1px solid;width: 100%;height: 100px;">${dto.reply }</textarea>
									</label>
								</div>
							</div>
							<div class="layui-collapse" lay-filter="test" style="border: 0px;">
								<div class="layui-colla-item">
									<div class="layui-colla-title" style="height: 30px; line-height: 30px; padding: 0 5px 0 35px; font-size: 12px; font-weight: 400; color: #c6ae6c;">留言评论</div>
									<div class="layui-colla-content ">
										<ul class="layui-timeline" id="comment">
										</ul>
									</div>
								</div>
							</div>
						</c:if>
						<hr>
						<c:if test="${type eq 'comment'}">
							<div class="layui-collapse" lay-filter="test" style="border: 0px;">
								<div class="layui-colla-item">
									<div class="layui-colla-title" style="height: 30px; line-height: 30px; padding: 0 5px 0 35px; font-size: 12px; font-weight: 400; color: #c6ae6c;">留言详情</div>
									<div class="layui-colla-content layui-show">
<!-- 										<ul class="layui-timeline" id="comment"> -->
<!-- 										</ul> -->
										<div style="overflow:hidden">
											<div style="color:#a51010;font-size:17px">标题</div>
											<div style="color:#009688" class="layui-timeline-title">${dto.parentTitle }</div>
										</div>
										<div style="overflow:hidden">
											<div style="color:#a51010;font-size:17px">内容</div>
											<div style="color:#009688" class="layui-timeline-title">${dto.parentcommentStr }</div>
										</div>
									</div>
								</div>
							</div>
						</c:if>
							<hr class="hr-height">
							<hr class="">
							<div id="button">
								<div class="layui-row" >
									<div class="layui-form-item submitBtn">
										<button id="agree" style="padding: 0px;width: 100px;" class="layui-btn">
					           				<i class="layui-icon">&#xe605;</i> 审批通过
					                	</button>
									</div>
								</div>
								<div class="layui-row">
					            	<div class="layui-form-item submitBtn" id="rollBack">
						 				<button id="disagree" style="padding: 0px;width: 100px;" class="layui-btn">
											<i class="layui-icon">&#x1006;</i>审批不通过
										</button>
									</div>
								</div>
								<c:if test="${type eq null}">
								<div class="layui-row">
					            	<div class="layui-form-item submitBtn" id="rollBack">
						 				<button id="save" style="padding: 0px;width: 100px;" class="layui-btn">
											<i class="layui-icon">&#xe605;</i>发布
										</button>
									</div>
								</div>
							</c:if>
							</div>
					</div>
<!-- 					正文 -->
<!-- 					<div class="layui-tab-item" id="zhengwen">  -->
<!-- 						<ul class="fly-list" id="mainBody"> -->
<!-- 						</ul> -->
<!-- 					</div> -->
					<!-- 附件 -->
					<div class="layui-tab-item" id="fujian" style="overflow: hidden;padding:10px">
						<div id="imageDiv"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="resources/mobilePhoneApproval/layui/layui.js"></script>
	<script type="text/javascript" src="resources/mobilePhoneApproval/js/noticeDetail.js"></script>
</body>
</html>