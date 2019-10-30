<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>${messageDto.title } - 留言详情 - 广济南社区</title>
	<meta http-equiv="X-UA-Compatible" content="IE=8,IE=edge,chrome=1" >
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="shortcut icon" href="resources/msgboardImages/favicon.ico" type="image/x-icon" />
	<!-- Bootstrap -->
	<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="resources/css/common/style.css">
	<link rel="stylesheet" href="resources/css/common/common.css">
	<link rel="stylesheet" href="resources/css/message/msgDetail.css">
	<script type="text/javascript" src="resources/js/commonJs/jquery-1.8.3.min.js"></script>
	<script src="resources/bootstrap/js/bootstrap.min.js"></script>
	<script src="resources/js/common/common.js"></script>
	<script src="resources/layui/layui.all.js"></script>
	<input id="userId" type="hidden" value="${person.id }">
	<input id="messageId" type="hidden" value="${messageDto.id }"> 
	<script type="text/javascript">
		var messageId = $("#messageId").val();
		if(isMobile() == true) window.location.href="mobile_message_detail?id="+messageId;
	</script>
	<script src="resources/js/message/messageDetail.js"></script>
	<script type="text/javascript" src="resources/js/common/basic.js"></script>
	<link rel="stylesheet" href="resources/bootstrap/Font-Awesome-3.2.1/css/font-awesome.min.css">
</head>
<input type="hidden" id="userNameState" value="${userNameState }">
<style type="text/css">
	.modal-backdrop{
	 position: fixed; top: 0; right: 0; bottom: 0; left: 0; background-color: #000; opacity: 0.8; z-index: 800;
	 }
</style>
 <jsp:include page="../../common/head.jsp"></jsp:include>
<body>
	<div class="mine-main-container w1200">
		<div class="detail-div">
			<div class="border-div">
				<div class="detail-info" style="margin-top:20px">
				<c:if test="${messageDto.messageCategory eq '咨询'}"><div class="detail-title">[咨询]${messageDto.title }</div></c:if>
				<c:if test="${messageDto.messageCategory eq '建议'}"><div class="detail-title">[建议]${messageDto.title }</div></c:if>
				<c:if test="${messageDto.messageCategory eq '举报'}"><div class="detail-title">[举报]${messageDto.title }</div></c:if>
				<c:if test="${messageDto.messageCategory eq '投诉'}"><div class="detail-title">[投诉]${messageDto.title }</div></c:if>
				<c:if test="${messageDto.messageCategory eq '报修'}"><div class="detail-title">[报修]${messageDto.title }</div></c:if>
				<c:if test="${messageDto.messageCategory eq '求助'}"><div class="detail-title">[求助]${messageDto.title }</div></c:if>
				<c:if test="${messageDto.messageCategory eq '灌水'}"><div class="detail-title">[灌水]${messageDto.title }</div></c:if>
				<c:if test="${messageDto.messageCategory eq '其他'}"><div class="detail-title">[其他]${messageDto.title }</div></c:if>
					<div class="detail-others">
<%-- 						<c:if test="${messageDto.messageCategory eq '咨询'}"><span class="label msg-type-zixun msg-list-category">${messageDto.messageCategory }</span></c:if> --%>
<%-- 						<c:if test="${messageDto.messageCategory eq '建议'}"><span class="label msg-type-jianyi msg-list-category">${messageDto.messageCategory }</span></c:if> --%>
<%-- 						<c:if test="${messageDto.messageCategory eq '举报'}"><span class="label msg-type-jubao msg-list-category">${messageDto.messageCategory }</span></c:if> --%>
<%-- 						<c:if test="${messageDto.messageCategory eq '投诉'}"><span class="label msg-type-tousu msg-list-category">${messageDto.messageCategory }</span></c:if> --%>
<%-- 						<c:if test="${messageDto.messageCategory eq '报修'}"><span class="label msg-type-baoxiu msg-list-category">${messageDto.messageCategory }</span></c:if> --%>
<%-- 						<c:if test="${messageDto.messageCategory eq '求助'}"><span class="label msg-type-qiuzhu msg-list-category">${messageDto.messageCategory }</span></c:if> --%>
<%-- 						<c:if test="${messageDto.messageCategory eq '其他'}"><span class="label msg-type-qita msg-list-category">${messageDto.messageCategory }</span></c:if> --%>
<!-- 						<i class="icon-fixed-width"></i> -->
<!-- 						<i class="icon-fixed-width"></i> -->
						<div class="head-image"><img src="resources/msgboardImages/headPortraits/${messageDto.headPortraits }" style="width:100%; height:100%"></div>
                        <div style="height: 40px;line-height: 40px;">
                        	<span class="msg-list-name">${messageDto.personName }</span>
							<span class="msg-list-time">发布于&nbsp;${messageDto.createTimestamp }</span>
							<c:if test="${messageDto.themeStr ne null}">
								<span style="color:#f1eded;margin:0 5px">|</span>
								<span class="msg-list-theme">${messageDto.themeStr }</span>
							</c:if>
                        </div>
					</div>
				</div>
				<div class="detail-content">${messageDto.commentStr }</div>
				<div id="imageDiv" style="overflow: hidden;padding:0px 10px;display:none" >
				</div>
			</div>
     		<div class="border-div">
				<div class="border-title-2">
					答复意见
				</div>
				<div class="detail-content"><c:if test="${messageDto.reply ne null }">${messageDto.reply }</c:if><c:if test="${messageDto.reply eq null }">您反映的事宜我们正在处理中，将会在5个工作日之内给予答复，谢谢。</c:if></div>
				<c:if test="${messageDto.reply ne null }"> 
				<div class="detail-status">
					<div id="zan" class="detail-status-up">
						<i class="icon-thumbs-up"></i>有用(<span id="zanCount">${messageDto.messageLike }</span>)
					</div>
<!-- 				<div style="line-height:30px;float:right;margin-right:30px"> -->
<!-- 					<i id="shoucang" class="icon-star-empty" style="margin-right:5px"></i>收藏 -->
<!-- 				</div> -->
				</div>
				</c:if>
			</div> 
			<div class="border-div">
			    <div class="border-title-2">
					<span class="border-title-left">评论</span>
					<span class="border-title-right" id="commentNums" style="float:right;">共${messageDto.commentNumber }条</span>
				</div>
				<div id="MessageDetailInfo"></div>
				<div>
					<ul class="pages">
						<li class="L mn-page" style="list-style-type:none">
						</li>
						<li class="R"></li>
					</ul>
				</div>
			</div>                                
			<div class="border-div">
				<div class="border-title-2">添加评论</div>
				<div style="text-align: center;margin-top:10px">
					<c:if test="${person.id eq null}">
						<div style="margin-top:5px;margin-bottom:10px">
							用户未登录请先<a onclick="goLogin()">登陆或注册</a>
						</div>
					</c:if>
					<c:if test="${person.id ne null}">
						<textarea id="comments" placeholder="请输入1000字以内的评论"></textarea>
						<div style="text-align: center;margin-bottom: 10px;margin-top: 5px">
							<button  onclick="submitComments()" class="btn btn-primary" style="width:100px;">提&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交</button>
						</div>
					</c:if>
				</div>
				
			</div>
		</div>
		<div class="other-div" style="margin-bottom:10px">
			<div class="border-div" style="position:relative">
				<div class="border-title">相关问答</div>
				<div class="border-content"> 
					<ul id="relatedFaqsInfo">
						
					</ul>
				</div>
			</div>
			<div class="border-div" style="margin-top:10px;position:relative">
				<div class="border-title">相关留言</div>
				<div class="border-content"> 
					<ul id="relatedMessageInfo">
					</ul>
				</div>
			</div>
		</div>
		<!--图片详情 -->
		<div id="imageBiger" class="bigger-image" onclick="hideDiv()">
		</div>
		<div id="backDro" class="modal-backdrop" style="display:none">
			<div style="text-align: right;margin-top: 70px;" onclick="hideDiv();"><img style='width:50px;margin-right:10px;' src='resources/msgboardImages/close.png'></div>
		</div>
	</div>
	
	
	<jsp:include page="../../common/foot.jsp"></jsp:include>
</body>
<script type="text/javascript">
function createPageHTML(_nPageCount, _nCurrIndex){
	$('.mn-page').html('');
	if(_nPageCount == null || _nPageCount<=1){
		return;
	}
	var nCurrIndex = _nCurrIndex || 0;
	if(nCurrIndex == 0)
	{
		$('.mn-page').append("<a><span class=\"f\">&laquo;</span>上一页</a>&nbsp;");
		$('.mn-page').append("<span class='current'>1</span>&nbsp;");
	}
	else
	{
		var prePageNum = nCurrIndex-1;
		if (prePageNum == 0) {
			$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getMessage(1)\"><span class=\"f\">&laquo;</span>上一页</a>&nbsp;");
			$('.mn-page').append("<span class='current'>1</span>&nbsp;");
		} else {
			$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getMessage("+prePageNum+")\"><span class=\"f\">&laquo;</span>上一页</a>&nbsp;");
			$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getMessage(1)\">1</a>&nbsp;");
		}
	}
	var startNum;
	var endNum;
	if (nCurrIndex -2 >1)
		startNum = nCurrIndex -2;
	else
		startNum = 1;
	if (nCurrIndex + 3 < _nPageCount)
		endNum = nCurrIndex + 3;
	else
		endNum = _nPageCount-1;
		if(startNum >1)
			$('.mn-page').append("...&nbsp;");
	for(var i = startNum; i<endNum; i++){
		if(nCurrIndex == (i+1))
			$('.mn-page').append("<span class='current'>"+(i+1) + "</span>&nbsp;");
		else
			$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getMessage("+(i+1)+")\">"+(i+1)+"</a>&nbsp;");
	}
	if(endNum <_nPageCount-1)
		$('.mn-page').append("...&nbsp;");
	var nextPageNum = nCurrIndex+1;
	var lastPageNum = _nPageCount-1;
	if(nCurrIndex == _nPageCount)
	{
		$('.mn-page').append("<span class='current'>"+_nPageCount+"</span>&nbsp;");
		$('.mn-page').append("<a>下一页<span class=\"f\">&raquo;</span></a>&nbsp;");
	}
	else{
		$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getMessage("+(lastPageNum+1)+")\">"+_nPageCount+"</a>&nbsp;");
		$('.mn-page').append("<a <a href=\"javascript:void(0);\" onclick=\"getMessage("+nextPageNum+")\">下一页<span class=\"f\">&raquo;</span></a>&nbsp;");
	}
}
</script>
</html>