// var pwdReg = /^[0-9a-zA-Z_-]{6,40}$/;//6位以上的数字、字母或字符
var pwdReg = /^(?=.*\d)(?=.*[a-zA-Z]).{8,40}$/;//8-40位以上的数字字母或者英文字符组合
var phoneReg = /^1[34578]\d{9}$/;
var usernameReg = /^[A-Za-z0-9\u4e00-\u9fa5]+$/;
//修改用户名
function changeUserName(){
	var userName = $("#userName").val();
	if(userName.trim()==""){
		alert("用户名不能为空!");
		return;
	}else if(!usernameReg.test(userName)){
		alert("用户名仅限输入中文英文和数字组合");
		return;
	}else if(userName.length>20){
		alert("用户名长度不超过20字");
		return;
	}
	
	$.ajax({
		url:"person/changeUserName",
		type:"GET",
		data:{userName:userName},
		success:function(data){
			alert("修改用户名成功!");
			location.href="person";
		},
		error:function(data){
			AjaxErrorHandler(data);
		}
	})
}

function changePhone(){
	var phone = $("#phone").val();
	if(phone.trim()==""){
		alert("手机号不能为空!");
		return;
	}else if(!phoneReg.test(phone)){
		alert("请输入正确的手机号!");
		return;
	}
	$.ajax({
		url:"person/change_phone",
		type:"POST",
		data:{phone:phone},
		success:function(data){
			alert("修改手机号成功!");
			location.href="person";
		},
		error:function(data){
			AjaxErrorHandler(data);
		}
	})
}

function changePwd(){
	var oldPwd = $("#oldPwd").val();
	var newPwd = $("#newPwd").val();
	var mutiNewPwd = $("#mutiNewPwd").val();
	if(newPwd.trim()==""){
		alert("新密码不能为空!");
		return;
	}else if(!pwdReg.test(newPwd)){
		alert("新密码请输入8-40位以上的数字，英文字母和英文字符的混合!");
		return;
	}else if(mutiNewPwd.trim()==""){
		alert("请输入确认密码!");
		return;
	}else if(newPwd != mutiNewPwd){
		alert("两次输入的密码不一致,请重新输入!");
		return;
	}
	$.ajax({
		url:"person/change_pwd",
		type:"POST",
		data:{newPwd:hex_md5(newPwd),mutiNewPwd:hex_md5(mutiNewPwd)},
		success:function(data){
			alert("修改密码成功!请重新登陆");
			location.href="logout";
		},
		error:function(data){
			AjaxErrorHandler(data);
		}
	})
}

function changeSex(){
	var sex = $('#tag4 input[name="sex"]:checked').val();
	$.ajax({
		url:"person/change_sex",
		type:"POST",
		data:{sex:sex},
		success:function(data){
			location.href="person";
		},
		error:function(data){
			AjaxErrorHandler(data);
		}
	})
}

function changeHead(){
	var headAddress = $("#headPortraits").val();
	$.ajax({
		url:"person/change_head",
		type:"POST",
		data:{headPortraits:headAddress},
		success:function(data){
			location.href="person";
		},
		error:function(data){
			AjaxErrorHandler(data);
		}
	})
}

function closeTag(i){
	var tag =$("#tag"+i);
	if(tag.css("display")=="none"){
		for(var j = 1; j <= 4; j++){
			$("#tag"+j).css("display","none");
		}
		tag.css("display","block");
	}else{
		tag.css("display","none");
	}
}

//搜索留言
function getMessage(pageNum, pageSize){
	var keyword = '';
	var pageNum = pageNum?pageNum:1;
	var pageSize = pageSize?pageSize:20;
	var meaasgeInfo = $("#messageInfo");
	meaasgeInfo.empty();
	$.ajax({
		url:'message/getMessageByUserId',
		type:"GET",
		ansy:false,
		data:"page="+pageNum+"&rows="+pageSize+"&q="+keyword,
		success:function(data){
			var totalPage = Math.ceil(data.total/pageSize);
			var pageNum = parseInt(data.pageNum);
			var messages = data.rows;
			createPageHTML(totalPage, pageNum, $('#third .mn-page'), 'getMessage');
			for(var i=0;i<messages.length;i++){
				var messageId = messages[i].id;
				var messageStatus = "";
				if(messages[i].approvalStatus.toString() == "未处理"){
					//未关注
					messageStatus = '<span class="label label-default">未关注</span>';
				}else if(messages[i].approvalStatus.toString() == "审核通过"){
					if(messages[i].replyStatus.toString() == "未答复"){
						//已关注
						messageStatus = '<span class="label label-info">处理中</span>';
					}else if(messages[i].replyStatus.toString() == "已答复"){
						//已答复
						messageStatus = '<span class="label label-success">已答复</span>';
					}
				}else if(messages[i].approvalStatus.toString() == "审核不通过"){
					if(messages[i].replyStatus.toString() == "未答复"){
						//已关注
						messageStatus = '<span class="label label-info">已关注</span>';
					}else if(messages[i].replyStatus.toString() == "已答复"){
						//已答复
						messageStatus = '<span class="label label-success">已答复</span>';
					}
				}
				var personName = (messages[i].personName==null||messages[i].personName.length==0)?'':messages[i].personName;
				var createTimestamp = (messages[i].createTimestamp==null||messages[i].createTimestamp.length==0)?'':messages[i].createTimestamp;
				var list = '<a href="messageDetail?id='+messages[i].id+'" target="_blank" style="color:#333">'
				+'<div class="msg-list-box">'
					+'<div class="msg-list-content">';
					if(messages[i].title.length>20){
						list += '<div class="msg-list-title"></i>'+messages[i].title.substring(0,35)+'...'+'</div>';
					}else{
						list += '<div class="msg-list-title">'+messages[i].title+'</div>';
					}
					var type = messages[i].messageCategory;
					var typeStr = '<span class="label msg-list-category msg-type-zixun">'+messages[i].messageCategory+'</span>';
					if(type == "咨询"){
						typeStr = '<span class="label msg-list-category msg-type-zixun">'+messages[i].messageCategory+'</span>';
					}else if(type == "建议"){
						typeStr = '<span class="label msg-list-category msg-type-jianyi">'+messages[i].messageCategory+'</span>';
					}else if(type == "举报"){
						typeStr = '<span class="label msg-list-category msg-type-jubao">'+messages[i].messageCategory+'</span>';
					}else if(type == "投诉"){
						typeStr = '<span class="label msg-list-category msg-type-tousu">'+messages[i].messageCategory+'</span>';
					}else if(type == "报修"){
						typeStr = '<span class="label msg-list-category msg-type-baoxiu">'+messages[i].messageCategory+'</span>';
					}else if(type == "求助"){
						typeStr = '<span class="label msg-list-category msg-type-qiuzhu">'+messages[i].messageCategory+'</span>';
					}else if(type == "灌水"){
						typeStr = '<span class="label msg-list-category msg-type-guanshui">'+messages[i].messageCategory+'</span>';
					}else if(type == "其他"){
						typeStr = '<span class="label msg-list-category msg-type-qita">'+messages[i].messageCategory+'</span>';
					}
					
					list += '<div class="msg-list-others">'
									+typeStr
									/*+'<div class="mn-box-content-ellipsis msg-list-name"><i class="icon-fixed-width"></i>'+personName+'</div>'*/
									+'<div class="mn-box-content-ellipsis msg-list-time">'+createTimestamp+'</div><div class="vertical-line">|</div>'
									+'<div class="mn-box-content-ellipsis msg-list-reply">'+messages[i].commentNumber+'条评论</div>'
								+'</div>'
							+'</div>'
							+'<div class="msg-list-status">'
								+ messageStatus
							+'</div>'
						+'</div></a>';
					meaasgeInfo.append(list);
				}
			},
			error:function(data){
				AjaxErrorHandler(data);
			}
		});
	
}

$(function(){
	getMessage();
	//绑定头像选择事件
	$(".head-images").each(function(){
		$(this).click(function(){
			var headName = $(this).attr("image-name");
			$("#headPortraits").val(headName);
			$(".head-images").removeClass("image-selected");
			$(this).addClass("image-selected");
		})
	});
	$(".head-images").each(function(){
		var headName = $(this).attr("image-name");
		var inputName = $("#headPortraits").val();
		if(headName == inputName){
			$(this).addClass("image-selected");
		}
	});
})