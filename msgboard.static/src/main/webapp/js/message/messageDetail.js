var usernameReg = /^[A-Za-z0-9\u4e00-\u9fa5]+$/;
$(function(){
	$("#backTop").remove();
	var userId = $("#userId").val();
	var messageId = $("#messageId").val();
	if(userId != "" && userId != null){
		$.ajax({
			url:'message/if_click_like',
			type:'GET',
			data:{
				messageId:messageId
			},
			success:function(data){
				if(data != ""){
					$("#zan i").css("color","#E10601");
				}
			},
			error:function(data){
				AjaxErrorHandler(data);
			}
		})
	}
	$.ajax({
		url : "message/get_message_images",
		type : "GET",
		dataType:"json",
		data : {messageId:messageId},
		async:false, 
		success:function(data){
			var imageDiv = $("#imageDiv");
			if(isMobile() == true){
				for(var i =0;i<data.length;i++){
					var image = '<div style="position: relative;float:left;height:95px;width:95px;margin-bottom:10px;margin-right:15px">'
						+'<a href="resources/upload/'+data[i].address+'">'
						+'<img src="resources/upload/'+data[i].address+'" style="width:100%;height:100%">'
						+'</a>'
						+'</div>'
					imageDiv.append(image);
				}
			}else{
				for(var i =0;i<data.length;i++){
					var image = '<div style="position: relative;float:left;height:100px;width:100px;margin-bottom:10px;margin-right:15px">'
						+'<img class="imageBigger" src="resources/upload/'+data[i].address+'" style="width:100%;height:100%">'
						+'</div>'
					imageDiv.append(image);
				}
			}
			imageDiv.css("display","block");
		},
		error:function(data){
			AjaxErrorHandler(data);
		}
	});
	
	$("#zan").click(function(){
		if(userId == ""|| userId == null){
			alert("请先登录用户!");
			return;
		}else{
			$.ajax({
				url:'message/get_Like',
				type:'POST',
				data:{
					messageId:messageId
				},
				success:function(data){
					$("#zan i").css("color","#E10601")
					var zanCount = $("#zanCount").html();
					$("#zanCount").html(parseInt(zanCount)+1);
				},
				error:function(data){
					return;
				}
			})
		}
	});
	
	$(".imageBigger").click(function(){
		var path = $(this).attr("src");
		$("#imageBiger").append('<img src=\"'+path+'\" style="height:100%">');
		$("#imageBiger").css("display","block");
		$("#backDro").css("display","block");
	});
	//收藏
//	$("#shoucang").click(function(){
//		$("#shoucang").css("color","#E10601")
//	})
	
//	点击放大图片
	
	$("body").on("touchmove", function(e) {
		changeLower();
	});
})

//焦点评论textarea触发样式事件
function changeHeigher(){
	$("#comments").css("height","100px");
}

function changeLower(){
	$("#comments").css("height","28px");
	var comments = document.getElementById("comments");
	comments.blur();
}

function hideDiv(){
	$("#imageBiger").empty();
	$("#imageBiger").css("display","none");
	$("#backDro").css("display","none");
}


$(function(){ 
		getMessageDetail(1,null);
		getRelatedMessage();
		getRelatedFaqs();
	});

//修改用户名
function updateUserName(newText){
	$.ajax({
		url:"person/changeUserName",
		type:"GET",
		data:{userName:newText},
		dataType : 'json',
		success:function(data){
			alert("修改用户名成功！");
			$("#userNameState").val("1");
		},
		error:function(data){
			AjaxErrorHandler(data);
		}
	})
}
//提交评论
function submitComments(){
	var messageId = $("#messageId").val();
//	var userId = $("#userId").val();
	var comments = $("#comments").val().replace(/[\r\n]/g,"");
	var userNameState = $("#userNameState").val();
	//验证是否为系统随机用户名
	if(userNameState=='0'){
		layer.prompt({id: 'userNameInput',title: '请输入新的用户名', formType: 3,
			success: function(layero, index){
				$('#userNameInput .userNameTooltip').html('');
			    $('#userNameInput').prepend('<div class="userNameTooltip">您是第一次评论，尚未设置用户名。您的用户名将显示在公开网络，为了保护您的隐私，提升网站整体的友好度，请您先设置用户名。</div>');
			    $('#userNameInput .userNameTooltip').css('width','230px');
			    $('#userNameInput .userNameTooltip').css('margin-bottom','10px');
			}}, function(text,index){
			var newText = $.trim(text);
			if(newText==""){
				alert("新用户名不能为空！");
				return;
			}else if(newText.length>20){
				alert("用户名长度不超过20字");
				return;
			}else if(!usernameReg.test(newText)){
				alert("用户名仅限输入中文英文和数字组合");
				return;
			}
			  layer.close(index);
			  // 修改用户名
			  updateUserName(newText);
//			  $("#userNameState").val("1");
		});
		return;
	}
	if(comments.trim() == ""){
		alert("评论不能为空!");
		return;
	}
	if(comments.length>1000){
		alert("请输入一千字以内的评论!");
		return;
	}
	$.ajax({
		url:"message/submitComments",
		type:"POST",
		data:{messageId:messageId,comments:comments},
		success:function(data){
			alert("您的评论将会在审核之后发布。我们对您发表评论的观点和立场，不会做任何的限制。但如您的评论含有对他人嘲讽、侮辱、谩骂及其它不宜公开的内容，将不会被审核通过！");
			$("#comments").val("");
			getMessageDetail();
		},
		error:function(data){
			AjaxErrorHandler(data);
		}
		
	});
}
	
//获取相关问答
function getRelatedFaqs(){
	var relatedFaqsInfo = $("#relatedFaqsInfo");
	var messageId = $("#messageId").val();
	relatedFaqsInfo.empty();
	$.ajax({
		url:'faqs/getRelatedFaqs',
		type:"POST",
		dataType:'json',
		data:{
			messageId:messageId
		},
	    success:function(data){
	    	if(isMobile() == true){
	    		for(var i=0;i<data.length;i++){
	    			var list = '<li class="quanwei-li">'
	    			+'<a class="ellipsis" href="faqsDetail?id='+data[i].id+'">'+data[i].question 
	    			+'</a>'
	    			+'</li>';
	    			relatedFaqsInfo.append(list);
	    		}
	    	}else{
	    		for(var i=0;i<data.length;i++){
	    			var list = '<li class="quanwei-li ">'
	    			+'<a class="ellipsis" href="faqsDetail?id='+data[i].id+'" target="_blank">'+data[i].question 
	    			+'</a>'
	    			+'</li>';
	    			relatedFaqsInfo.append(list);
	    		}
	    	}

	},
	error:function(data){
		AjaxErrorHandler(data);
	}
	})
}
	
//获取相关留言
function getRelatedMessage(){
	var relatedMessageInfo = $("#relatedMessageInfo");
	var messageId = $("#messageId").val();
	relatedMessageInfo.empty();
	$.ajax({
		url:'message/getRelatedMessage',
		type:"POST",
		dataType:'json',
		data:{
			messageId:messageId
		},
	    success:function(data){
	    if(isMobile() == true){
	    	for(var i=0;i<data.length;i++){
				var list = '<li class="quanwei-li">'
				+'<a class="ellipsis" href="mobile_message_detail?id='+data[i].id+'">'+data[i].title 
				+'</a>'
				+'</li>';
			relatedMessageInfo.append(list);
			}
	    }else{
	    	for(var i=0;i<data.length;i++){
				var list = '<li class="quanwei-li ">'
				+'<a class="ellipsis" href="messageDetail?id='+data[i].id+'" target="_blank">'+data[i].title 
				+'</a>'
				+'</li>';
			relatedMessageInfo.append(list);
			}
	    }
		
	},
	error:function(data){
		AjaxErrorHandler(data);
	}
	})
}

	
//查询评论
function getMessageDetail(pageNum, pageSize){
	var messageId = $("#messageId").val();
	var MessageDetailInfo = $("#MessageDetailInfo");
	var pageSize = pageSize?pageSize:10;
	MessageDetailInfo.empty(); 
	$.ajax({
		url:'message/commentList',
		type:"POST",
		ansy:false,
		data:{
			messageId: messageId,
			page: pageNum,
			rows: pageSize,
		},
	success:function(data){
		var messageDetail = data.rows;
	    var totalPage = Math.ceil(data.total/pageSize);
		var pageNum = parseInt(data.pageNum); 
		createCommentPageHTML(totalPage,pageNum);
		if(isMobile() == true){
			for(var i=0;i<messageDetail.length;i++){
				var list =	(((messageDetail.length-1) == i)?('<div style="overflow: hidden;margin-top:5px;margin-bottom:5px">'):('<div style="border-bottom: 1px solid #ccc;overflow: hidden;margin-top:5px;margin-bottom:5px">'))
							+'<div style="margin-left:0px;">'
							+'<div class="head-image"><img src="resources/msgboardImages/headPortraits/'+messageDetail[i].headPortraits+'" style="width:100%;height:100%"></div>'
							+(messageDetail[i].personName.length>7?('<div class="base-info"><div style="float:left;">'+messageDetail[i].personName.substring(0,6)+'...</div>'+'<div style="float:right;">'+messageDetail[i].floorNum+'楼</div>'+'</div>'):('<div class="base-info"><div style="float:left;">'+messageDetail[i].personName+'</div>'+'<div style="float:right;">'+messageDetail[i].floorNum+'楼</div>'+'</div>'))
							+'<div class="base-info">'+messageDetail[i].createTimestamp+'</div>'
							+'</div>'
							+'<div>'+messageDetail[i].commentStr+'</div></div></div>';
				MessageDetailInfo.append(list);
			}
		}else{
			for(var i=0;i<messageDetail.length;i++){
				var list ='<div class="detail-reply">'
						+'<div class="comment-font"><div class="head-image"><img src="resources/msgboardImages/headPortraits/'+messageDetail[i].headPortraits+'" style="width:100%;height:100%"></div><div style="height:40px;">'
							+'<div style="height:20px;">'+messageDetail[i].personName
							+'<span style="float:right">'+messageDetail[i].floorNum+'楼</span>'
							+'</div>'
							+'<div style="height:20px;">'+messageDetail[i].createTimestamp+'</div>'
							
						+'</div></div>'
						+'<div class="detail-reply-content">'+messageDetail[i].commentStr+'</div>'
				    +'</div>';
			   MessageDetailInfo.append(list);
			}
		}
	},
	error:function(data){
		AjaxErrorHandler(data);
	}
	})
}
//go to 登陆页面
function goLogin(){
	var messageId = $("#messageId").val();
	location.href="goDetail?id="+messageId;
}

