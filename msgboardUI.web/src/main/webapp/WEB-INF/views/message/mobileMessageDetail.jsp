<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>留言详情 - 广济南社区</title>
<meta http-equiv="X-UA-Compatible" content="IE=8,IE=edge,chrome=1" >
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=0">
<!-- Bootstrap -->
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/commonJs/jquery-1.8.3.min.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="resources/css/common/style.css">
<link rel="stylesheet" href="resources/css/common/common.css">
<link href="resources/css/common/mobile-menu.css" rel="stylesheet">
<link href="resources/css/message/mobileHome.css" rel="stylesheet">
<link href="resources/css/message/mobileMessageDetail.css" rel="stylesheet">
<link rel="stylesheet" href="resources/bootstrap/Font-Awesome-3.2.1/css/font-awesome.min.css">
<script src="resources/js/common/mobile-menu.js"></script>
<script src="resources/js/common/common.js"></script>
<script src="resources/js/message/messageDetail.js"></script>
<script type="text/javascript" src="resources/js/common/basic.js"></script>
<script src="resources/layui/layui.all.js"></script>
</head>
<input id="userId" type="hidden" value="${person.id }">
<input id="messageId" type="hidden" value="${messageDto.id }">
<input type="hidden" id="userNameState" value="${userNameState }">
<script type="text/javascript">
	var messageId = $("#messageId").val();
	if(isMobile == false) window.location.href="messageDetail?id="+messageId;
</script>
<jsp:include page="../../common/mobileHead.jsp"></jsp:include>
<body >
	<div style="overflow: hidden">
				<div class="content-font" style="border-bottom:1px solid #ccc">
						<div style="overflow: hidden;margin-top:10px">
						<!--  <p style="color: #ffffff;float: left;background-color: #A82F44;font-size: 0.9em;border-radius: 2px;padding: 0px 2px;" >提出者</p> -->
						</div>
						<div style="margin-top: 10px;font-size:18px;line-height:1.4;"><strong>[${messageDto.messageCategory }]${messageDto.title }</strong></div>
						<div style="overflow: hidden;margin:2px 0px">
							<div class="head-image">
								<img src="resources/msgboardImages/headPortraits/${messageDto.headPortraits }" style="width:100%;height:100%">
							</div>
						
							<div>
								<div style="overflow:hidden;">
									<c:choose>
										<c:when test="${fn:length(messageDto.personName)>8 }">
											<div style="line-height: 20px;height: 20px;padding: 0px 2px;font-size: 13px;color:#C9C9C9;float:left;">${fn:substring(messageDto.personName,0,7) }...</div>
										</c:when>
										<c:otherwise>
											<div style="line-height: 20px;height: 20px;padding: 0px 2px;font-size: 13px;color:#C9C9C9;float:left;">${messageDto.personName }</div>
										</c:otherwise>
									</c:choose>
									<div style="line-height: 20px;height: 20px;padding: 0px 2px;color:#C9C9C9;font-size: 13px;float:left">发布于${messageDto.createTimestamp }</div>	
								</div>
	<!-- 							<div style="padding:0 2px; margin:0 3px; font-size: 13px; color:#f1eded;float:left">|</div> -->
								<div style="padding: 0px 2px;color:#C9C9C9;font-size: 13px;margin-top:5px">${messageDto.themeStr }</div>
							</div>
						</div>
					<div style="margin-bottom: 5px;line-height: 1.6;white-space: pre-line;">${messageDto.commentStr }</div>
					<div id="imageDiv" style="overflow: hidden;display:none">
					</div>
				</div>
				<div style="width:100%;padding:5px 0;overflow: hidden;">
					<div style="height:30px;width:2px;background-color: red;float:left;margin-left: 5px;"></div>
					<div class="offical-reply">官方回复</div>
					<div style="float:right;height:30px;line-height:30px">
						<c:if test="${messageDto.approvalStatusStr eq '审核通过' }">
							<c:if test="${messageDto.replyStatusStr eq '已答复' }">
								<div class="label label-success" style="padding: 5px;margin-right: 5px;">已答复</div>
							</c:if>
							<c:if test="${messageDto.replyStatusStr eq '未答复' }">
								<div class="label label-info" style="padding: 5px;margin-right: 5px;">处理中</div>
							</c:if>
						</c:if>
						<c:if test="${messageDto.approvalStatusStr eq '未处理' }">
							<div class="label label-default" style="padding: 5px;margin-right: 5px;">未关注</div>
						</c:if>
					</div>
				</div>
				<div class="content-font" style="border-bottom:1px solid #ccc">
					<div style="margin-bottom: 5px;margin-top:10px;line-height:1.6;">
			<!--  		<span class="content-logo-red">答复</span>-->	
						<c:if test="${messageDto.reply ne null }">
							${messageDto.reply }
						</c:if> 
						<c:if test="${messageDto.reply eq null }">
							您反映的事宜我们正在处理中，将会在5个工作日之内给予答复，谢谢。
						</c:if> 
					</div>
					<c:if test="${messageDto.reply ne null }">
					<div class="row" style="margin-bottom: 10px;margin-top:15px">
<!-- 						<div class="col-xs-6 col-sm-6 text-center" ><span style="border-radius: 10px;border:1px solid #ccc;padding:0px 20px"><i id="shoucang" class="icon-star-empty" style="margin-right:5px"></i>12</span></div> -->
						<div id="zan" class="col-xs-12 col-sm-12 text-center" >
<%-- 							<span style="color:#C9C9C9;font-size: 13px;">${messageDto.createTimestamp }</span> --%>
							<span style="font-size: 20px;border-radius: 10px;border:1px solid #ccc;padding:3px 20px;"><i class="icon-thumbs-up" style="margin-right:5px"></i><span id="zanCount">${messageDto.messageLike }</span></span>
						</div>
					</div>
					</c:if>
				</div>
				
				<div style="width:100%;padding:5px 0;overflow: hidden;padding-right: 20px;">
					<div style="height:30px;width:2px;background-color: red;float:left;margin-left: 5px;"></div>
					<div class="offical-reply">留言评论</div>
					<p style="float:right"><span style="color:red ;font-weight: bold;">${messageDto.commentNumber }</span>条评论</p>
				</div>
				
				<div id="MessageDetailInfo" style="position: relative;line-height:1.6;" class="content-font">
<!-- 				留言评论 -->
<!-- 					<div style="border-bottom: 1px solid #ccc;overflow: hidden;margin-top:10px;margin-bottom:10px"> -->
<!-- 						<div style="position: absolute;"> -->
<!-- 							<img src="resources/msgboardImages/默认头像.png" style="width:48px;height:48px"> -->
<!-- 						</div> -->
<!-- 						<div style="margin-left:0px;margin-bottom:10px"> -->
<!-- 							<p style="margin:0px 0px 5px 0px;color:#ccc"><span>小妞妞</span><span style="margin-left:15px">2018-06-25 12:22:26</span></p> -->
<!-- 							<div style=""> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
				</div>
				<!-- 分页 -->
				<div class="content-font" style="overflow: hidden;">
					<ul class="pages" style="margin:0">
						<li class="L mn-page" style="list-style-type:none">
							
						</li>
						<li class="R"></li>
					</ul>
				</div>
<!-- 				<div class="content-font" style="background-color: #F1F1F1;text-align: center;letter-spacing:3px;margin-bottom:5px"> -->
<!-- 					查看更多评论 -->
<!-- 				</div> -->
			<!--相关留言 -->
			<div style="width:100%;padding:5px 0;overflow: hidden;">
				<div style="height:30px;width:2px;background-color: red;float:left;margin-left: 5px;"></div>
				<div class="offical-reply">相关留言</div>
			</div>
			<div class="mn-box-content" style="margin-top:0px">
				<div class="content-font" style="margin-top:0px;overflow: hidden;min-height: 19px;">
					<ul id="relatedMessageInfo">
					</ul>
				</div>
			</div>
			<!--相关问答 -->
			<div style="width:100%;padding:5px 0;overflow: hidden;">
				<div style="height:30px;width:2px;background-color: red;margin-left: 5px;float:left"></div>
				<div class="offical-reply">相关问答</div>
			</div>
			<div class="mn-box-content" style="margin-top:0px;margin-bottom:50px;">
				<div class="content-font" style="margin-top:0px;overflow: hidden;min-height: 19px;">
					<ul id="relatedFaqsInfo">
					</ul>
				</div>
			</div>
			<c:if test="${person.id eq null}">
				<div style="position: fixed; background:#f3f3f3;z-index: 999;width:100%;bottom:0px;padding:10px 5px">
					<div style="margin-top:5px;margin-bottom:10px;text-align:center">
						如需评论请先<a onclick="goLogin()">登陆或注册</a>
					</div>
				</div>
			</c:if>
			<c:if test="${person.id ne null}">
				<div style="position: fixed; background:#fff;z-index: 999;width:100%;bottom:0px;padding:10px 5px;border-top: 1px solid #e8e8e8;">
					<div style="position: relative;">
						<div style="margin-right:50px">
							<textarea id="comments" placeholder="输入评论..."  style="height:28px;resize:none;border:0px;outline: none;width:100%;padding:3px 5px" onFocus="changeHeigher();"></textarea>
						</div>
						<div onclick="submitComments();" class="comment-button" style="background-color: #fff;color: #9ebff3;border:0px;">评论</div>
					</div>
				</div>
			</c:if>
</div>


</body>
<%-- <jsp:include page="../../common/foot.jsp"></jsp:include> --%>
</html>