var phoneReg = /^1[34578]\d{9}$/;
var usernameReg = /^[A-Za-z0-9\u4e00-\u9fa5]+$/;
function checkPhone() {
	$('#phone').val($('#phone').val().replace(/[^\d]/g,''));
	if($('#phone').val().length > 20) {
		alert('手机号码不能超过20');
		$('#phone').val($('#phone').val().substring(0,20));
		return;
	}
}

//修改用户名
function updateUserName(newText){
	$.ajax({
		url:"person/changeUserName",
		type:"GET",
		data:{userName:newText},
		dataType : 'json',
		success:function(data){
			alert("修改用户名成功！");
			$("#userName").val(data.userName);
		},
		error:function(data){
			AjaxErrorHandler(data);
		}
	})
}

function html2Escape(sHtml) {
	  var temp = document.createElement("div");
	  (temp.textContent != null) ? (temp.textContent = sHtml) : (temp.innerText = sHtml);
	  var output = temp.innerHTML;
	  temp = null;
	  return output;
	}
//发送留言
function messageSend(){
	var userNameState = $("#userNameState").val();
    var phone = document.getElementById("phone").value;
    var email = html2Escape(document.getElementById("email").value);
    var content = html2Escape(document.getElementById("content").value);
//    var code = document.getElementById("validateCode").value;
    // TODO 验证码
    var code ="1234";
    var attribute1=document.getElementsByName("attribute1")[0].value;
    var userId = $("#userId").val();
    	
    if(attribute1 ==""){
        alert('请选择类型，谢谢！');
        return false;
	}

//    if(name=="" || name=="公开页面仅会显示姓氏")
//    {
//        alert('请填写姓名，谢谢！');
//        return false;
//    }
    if(!phoneReg.test(phone)){
    	alert("请填写正确的手机号!");
    	return;
    }
    if(phone=="" || phone=="仅管理员可见，公开页面不显示")
    {
        phone = $("#phoneSec").val();
    }
    if(email=="" || email=="留言标题")
    {
        alert('请填写留言标题，谢谢！');
        return false;
    }
    if(email.length>50){
    	alert('请输入50字以内的标题');
    	return false;
    }
    if(content=="" || content=="为了便于我们更好地为您服务，请详细叙述您的具体诉求，或者描述事件发生的具体时间、地点、经过及涉及人员")
    {
        alert('请填写留言内容，谢谢！');
        return false;
    }

    if(code=="" || code=="验证码")
    {
        alert('请填写验证码，谢谢！');
        return false;
    }
    if(userNameState=='0'){
		layer.prompt({id: 'userNameInput',title: '请输入新的用户名', formType: 3,
			success: function(layero, index){
				$('#userNameInput .userNameTooltip').html('');
			    $('#userNameInput').prepend('<div class="userNameTooltip">您是第一次留言，尚未设置用户名。您的用户名将显示在公开网络，为了保护您的隐私，提升网站整体的友好度，请您先设置用户名。</div>');
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
			  $("#userNameState").val("1");
		});
		return;
	}
 // TODO 验证码验证
//    if($('#validateCode').val() == ''
//        || !$('#validateCode').val().match('^[a-zA-Z0-9]{4}$')) {
//        alert('验证码输入错误');
//        return false;
//    }


//    inputForm['messageName']=name;
//    inputForm['messageText']=content;
//    inputForm['phoneNumber']=phone;
//    inputForm['email']=email;
//    inputForm['attribute1']=attribute1;
    $.ajax({
        url : 'message/createMessage',
        type : 'POST',
        data :JSON.stringify({
        	phone:phone,
        	commentStr:content,
        	title:email,
        	messageCategory:attribute1,
        	personId:userId,
        	validateCode:code
        }),
        contentType:"application/json",
        dataType : 'json',
        async:false, 
        success : function(data) {
            if(data.errorMsg){
                alert('保存失败');

            }else{
            		$('#myform')[0].reset();
            		$('#form1')[0].reset();
     				$('#sid').html('2000');
                    alert("您的留言我们已收到并记录，感谢您的反馈。我们会在五个工作日之内给您答复，请您耐心等待。");
                    window.location.reload();
                    //跳转留言详情页面
                    openWin("messageDetail?id="+data.id);
            	}
            },
        error : function(data) {
            AjaxErrorHandler(data);
        }
    });
}

//提交留言成功后跳转留言详情页面
function openWin(url) {
    $('body').append('<a href="'+url+'" id="openWin"></a>');
    document.getElementById("openWin").click();//点击事件
    $('#openWin').remove();
}

$(function(){
	$.fn.InputLetter = function(options) {
		var defaults = {			
			len : 200,
			contentId: "content",
			showId : "show"
		};
		var options = $.extend(defaults,options);	
		var $input = $(this);		
		var $show = $("#"+options.showId);
		var $content = $("#"+options.contentId);
		$show.html(options.len);
		$input.live("keydown keyup blur",function(e){						
		  	var content =$content.val();
			var length = content.length;
			var result = options.len - length;
			if (result >= 0){
				$show.html(result);
			}else{
				$content.val(content.substring(0,options.len));
			}
		});	
	}
});
$(function(){
//	$.ajax({
//		url:'message/get_message_type',
//		type:'GET',
//		success:function(data){
//			var selectList = $("#myform select[name='attribute1']");
//			selectList.append('<option value="" selected="selected">请选择</option>');
//			for(var i = 0; i<data.length; i++){
//				selectList.append('<option value="'+data[i].value+'">'+data[i].text+'</option>')
//			}
//		},
//		error:function(data){
//			AjaxErrorHandler(data);
//		}
//	});
	$("#content").InputLetter({					       
		len : 2000,//限制输入的字符个数
		contentId: "content",
		showId : "sid"//显示剩余字符文本标签的ID
	});
});