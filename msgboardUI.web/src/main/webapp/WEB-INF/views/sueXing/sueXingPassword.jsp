<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8,IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=0">
<link rel="shortcut icon" href="resources/msgboardImages/favicon.ico" type="image/x-icon" />
<title>苏州轨道交通登陆注册页面</title>
<link href="resources/css/common/Wopop_files/style_log.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="resources/css/common/Wopop_files/style.css">
<script type="text/javascript" src="resources/js/commonJs/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="resources/admin/js/common.js"></script>
<script type="text/javascript" src="resources/js/common/basic.js"></script>
<script type="text/javascript" src="resources/js/common/md5.js"></script>
<link rel="stylesheet"
	href="resources/bootstrap/Font-Awesome-3.2.1/css/font-awesome.min.css">
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<!--<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>-->
<!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
<!-- 警告：通过 file:// 协议（就是直接将 html 页面拖拽到浏览器中）访问页面时 Respond.js 不起作用 -->
<!--[if lt IE 9]>
		<script src="resources/bootstrap/js/respond.min.js"></script>
		<script src="resources/bootstrap/js/html5shiv.min.js"></script>
	<![endif] -->
</head>
<script type="text/javascript">
	var pwdReg = /^[0-9a-zA-Z_-]{6,40}$/;//6位以上的数字、字母或字符
	var phoneReg = /^1[3456789]\d{9}$/;
	var usernameReg = /^[A-Za-z0-9\u4e00-\u9fa5]+$/;
	var time;
	var time2;
	$(function() {
		var biaozhi = ${biaozhi};
		if (biaozhi) {
			if (biaozhi == "2") {
				openRegister();
			}
		}
		if (isError()) {
			$('#loginForm .error').show();
		} else {
			$('#loginForm .error').hide();
		}
	});
	function isError() {
		var url = location.search; //获取url中"?"符后的字串
		if (url.indexOf("?") != -1) { //判断是否有参数
			var str = url.substr(1); //从第一个字符开始 因为第0个是?号 获取所有除问号的所有符串
			strs = str.split("="); //用等号进行分隔 （因为知道只有一个参数 所以直接用等号进分隔 如果有多个参数 要用&号分隔 再用等号进行分隔）
			if (strs[0] == 'error')
				return true;
			else
				return false;
		}
		return false;
	}

	function returnBack() {
		window.location.reload();
	}

	function forgotPwd() {
		//        $('#login_model input[name="phone"]').val("");
		$('#login_model input[name="pwd"]').val("");
		if (time2) {
			clearInterval(time2);
		}
		$("#forgetYzm").removeAttr("disabled");
		$("#forgetYzm").html("获取验证码");
		$("#login_model").css("display", "none");
		$("#forget_model").css("display", "block")
	}

	function openRegister() {
		//        $('#login_model input[name="phone"]').val("");
		//         $('#login_model input[name="pwd"]').val("");
		//         if(time){
		//         	clearInterval(time);
		//         }
		//       	$("#registerYzm").removeAttr("disabled");
		//       	$("#registerYzm").html("获取验证码");
		$("#login_model").css("display", "none");
		$("#register_model").css("display", "block")
	}

	function login() {
		var username = $('#login_model input[name="username"]').val();
		var password = $('#login_model input[name="password"]').val();
		if (!username) {
			alert("用户名不能为空!")
			return;
		}
		if (!password) {
			alert("密码不能为空!");
			return;
		}
		$('#login_model input[name="password"]').val(hex_md5(password));
		$('#loginForm').submit();
	}
	//TODO
	function registe() {
		$('#reminder').html("");
		$('#reminder02').html("");
		$('#usernamereminder').html("");
		$('#phoneReminder').html("");
		var phone = $('#register_model input[name="phone"]').val();
		var pwd = $('#register_model input[name="pwd"]').val();
		var mutiPwd = $('#register_model input[name="mutiPwd"]').val();
		var validate = $('#register_model input[name="validateCode"]').val();
		var username = $('#register_model input[name=username]').val();
		var list = ["红帽子.jpg","蓝帽子.jpg","绿帽子.jpg","line1.jpg","line2.jpg","line3.jpg","line4.jpg"];
		var a = parseInt((list.length)*Math.random());
		
		if (!usernameReg.test(username)) {
			alert("用户名仅限输入中文英文和数字组合");
			$('#register_model input[name="username"]').val("");
			return;
		} else if (username.length > 20) {
			alert("用户名长度不超过20字");
			$('#register_model input[name="username"]').val("");
			return;
		} else if ($.trim(phone) == "") {
			alert("手机号不能为空!");
			return;
		} else if (!phoneReg.test(phone)) {
			alert("手机号码格式输入不正确!");
			$('#register_model input[name="phone"]').val("");
			return;
		} else if ($.trim(pwd) == "") {
			alert("请输入注册密码!");
			return;
		} else if (!pwdReg.test(pwd)) {
			alert("密码请输入6~40位的数字、字母或字符");
			$('#register_model input[name="pwd"]').val("");
			return;
		} else if ($.trim(mutiPwd) == "") {
			alert("请输入确认密码!");
			return;
		} else if (pwd !== mutiPwd) {
			alert("两次密码输入不一致!");
			$('#register_model input[name="mutiPwd"]').val("");
			return;
		}
		$.ajax({
			url : 'person/person_register',
			type : "POST",
			data : {
				phone : phone,
				pwd : hex_md5(pwd),
				username : username,
				validateCode : validate,
				headPortraits : list[a]
			},
			success : function(data) {
				alert("注册成功!");
				window.location.href="mobile_home";
			},
			error : function(data) {
				AjaxErrorHandler(data);
			}
		})
	}

	function gotoPersonCenter() {
		$('#reminder03').html("");
		$('#reminder04').html("");
		var phone = $('#forget_model input[name="phone"]').val();
		var validate = $('#forget_model input[name="validate"]').val();
		if (phone.trim() == "") {
			alert("手机号不能为空!");
			return;
		} else if (!phoneReg.test(phone)) {
			$('#reminder03').html("手机号输入不正确");
			$('#forget_model input[name="phone"]').val("");
			return;
		} else if (validate.trim() == "") {
			alert("请输入验证码!");
			return;
		}

		$.ajax({
			url : 'person/go_to_personCenter',
			type : "POST",
			dataType : "json",
			data : {
				phone : phone,
				validate : validate
			},
			success : function(data) {
				location.href = "person?id=" + data.id;
			},
			error : function(data) {
				AjaxErrorHandler(data);
			}
		});
	}
	//获取验证码
	$(function() {
		var ordertime = 60; //设置再次发送验证码等待时间
		var timeleft = ordertime;
		var ordertime02 = 60; //设置再次发送验证码等待时间
		var timeleft02 = ordertime02;
		var btn = $("#registerYzm");
		var btn02 = $("#forgetYzm");
		var phone = $("#register_model input[name='phone']");
		var phone02 = $("#forget_model input[name='phone']");
		var reg = /^1[0-9]{10}$/; //电话号码的正则匹配式

		phone.keyup(function() {
			if (phoneReg.test(phone.val())) {
				btn.removeAttr("disabled") //当号码符合规则后发送验证码按钮可点击
			} else {
				btn.attr("disabled", true)
			}
		})

		phone02.keyup(function() {
			if (phoneReg.test(phone02.val())) {
				btn02.removeAttr("disabled") //当号码符合规则后发送验证码按钮可点击
			} else {
				btn02.attr("disabled", true)
			}
		})

		//计时函数
		function timeCount() {
			timeleft -= 1
			if (timeleft > 0) {
				btn.html(timeleft + " 秒后重发");
				time = setTimeout(timeCount, 1000)
			} else {
				btn.html("重新发送");
				timeleft = ordertime //重置等待时间
				btn.removeAttr("disabled");
			}
		}

		function timeCount02() {
			timeleft02 -= 1
			if (timeleft02 > 0) {
				btn02.html(timeleft02 + " 秒后重发");
				time2 = setTimeout(timeCount02, 1000)
			} else {
				btn02.html("重新发送");
				timeleft02 = ordertime02 //重置等待时间
				btn02.removeAttr("disabled");
			}
		}

		//事件处理函数
		btn.click(function() {
			phoneReg.test(phone.val())
			$(this).attr("disabled", true); //防止多次点击
			//此处可添加 ajax请求 向后台发送 获取验证码请求
			timeCount(this);
			//ajax请求发送验证码到手机
			$.ajax({
				type : "POST", //用POST方式传输
				dataType : "text", //数据格式:JSON
				url : '', //目标地址
				data : "phone=" + phone, //post携带数据
				success : function() {

				}, //请求成功时执行的函数
				error : function() {
					alert("发送验证码发生错误!");
				} //请求错误时的处理函数
			});
		})

		btn02.click(function() {
			phoneReg.test(phone02.val())
			$(this).attr("disabled", true); //防止多次点击
			//此处可添加 ajax请求 向后台发送 获取验证码请求
			timeCount02(this);
			//ajax请求发送验证码到手机
			$.ajax({
				type : "POST", //用POST方式传输
				dataType : "text", //数据格式:JSON
				url : '', //目标地址
				data : "phone=" + phone, //post携带数据
				success : function() {

				}, //请求成功时执行的函数
				error : function() {
					alert("发送验证码发生错误!");
				} //请求错误时的处理函数
			});
		})
	});
</script>
<body class="login">
	<div class="login_m">
		<div>
			<div class="login_logo">
				<img src="resources/msgboardImages/login/公众留言板.png" width="250"
					height="60">
			</div>
			<%--登陆页面--%>
			<div class="login_boder">
				<div class="login_padding" id="login_model"
					style="margin-bottom: 20px">
					<form action="login" method="POST" id="loginForm">
						<h1 class="login-head">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</h1>
						<!--                 <h2>用户名</h2> -->
						<label>
							<div style="position: relative">
								<i class="icon-fixed-width icon-login"></i> <input type="text"
									name="username" class="txt_input txt_input2"
									placeholder="请输入用户名">
							</div>

						</label>
						<!--                 <h2>密码</h2> -->
						<label>
							<div style="position: relative">
								<i class="icon-fixed-width icon-login"></i> <input
									type="password" name="password" class="txt_input  txt_input2"
									placeholder="请输入密码">
							</div>

						</label> <label class="error" style="display: none; color: #e2161d">用户或密码错误</label>
						<!--                 <p class="forgot"><a onclick="forgotPwd()">忘记密码?</a></p> -->
						<div class="rem_sub" style="margin-top: 40px;">
							<div>
								<button class="sub_button" onclick="login()" style="cursor:pointer">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;陆</button>
								<div style="margin-top: 10px; font-size: 14px;">
									还没有账号?<a id="register" onclick="openRegister()" style="color: #2096c5;cursor: pointer;">注册账号</a>
								</div>
							</div>
						</div>
					</form>
					<div>
						温馨提示：已通过苏e行登录过留言板的用户，修改过密码后，可使用其账号登录，无需重新注册。
					</div>
				</div>

				<%--注册--%>
				<div class="login_padding" id="register_model" style="display: none">
					<h1 class="login-head">注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;册</h1>
					<!--              	<span id="usernamereminder" style="color: red ;font-weight: bold;margin-left: 5px"></span> -->
					<label>
						<div style="position: relative">
							<i class="icon-fixed-width icon-login"></i> <input type="text"
								name="username" class="txt_input" placeholder="请输入用户名">
						</div>
					</label>
					<!--                 <span id="phoneReminder" style="color: red ;font-weight: bold;margin-left: 5px"></span> -->
					<label>
						<div style="position: relative">
							<i class="icon-fixed-width icon-login"></i> <input type="text"
								name="phone" class="txt_input" placeholder="请输入手机号">
						</div>
					</label>
					<!--                 <span id="reminder" style="color: red ;font-weight: bold;margin-left: 5px"></span> -->
					<label>
						<div style="position: relative">
							<i class="icon-fixed-width icon-login"></i> <input
								type="password" name="pwd" class="txt_input" placeholder="请输入密码">
						</div>
					</label>
					<!--                 <span id="reminder02" style="color: red ;font-weight: bold;margin-left: 5px"></span> -->
					<label>
						<div style="position: relative">
							<i class="icon-fixed-width icon-login"></i> <input
								type="password" name="mutiPwd" class="txt_input"
								placeholder="请再输一次密码">
						</div>
					</label> <label style="float: left; margin-top: 15px;"> <input
						type="text" placeholder="验证码" id="validateCode" class="txt_input"
						name="validateCode"
						style="margin-top: 10px; width: 100px; padding: 5px 0px;">
						<div style="height: 38px; float: right; margin-left: 50px;">
							<img id="randomImage" style="height: 100%; width: 100px;"
								src="message/validateCode.do" height="27" alt="点击刷新验证码"
								onclick="changeCode();" class="yzmimg">
						</div>
					</label>
			  	    <div class="agreement">
						  <a class="agreement_1" href="agreement" target="_blank" style="color:#8590A6"> 注册即代表同意《苏州轨道交通用户协议》</a>
					</div>
					<div class="rem_sub" style="margin-bottom: 8px; margin-top: 40px">
					<!--  <div class="rem_sub_l" >
						  注册即代表同意 <a class="rem_sub_l_1" href="" target="_blank">《苏州轨道交通用户协议》</a>
						</div>-->	
				<!-- <label> -->		
							<button class="sub_button" name="register" 
								style="margin-bottom: 10px;cursor:pointer;" onclick="registe()">注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;册</button>
							<button class="sub_button" style="cursor:pointer" name="return" onclick="returnBack()">返&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;回</button>
						
						<!-- </label> -->
					</div>
				</div>

				<%--忘记密码--%>
				<div id="forget_model" class="login_padding" style="display: none">
					<h1 style="text-align: center; color: #0E89CF">修改密码</h1>
					<p class="forget_model_h2">
						手机号<span id="reminder03"
							style="color: red; font-weight: bold; margin-left: 5px"></span>
					</p>
					<label> <input type="text" name="phone"
						class="txt_input txt_input2">
					</label>

					<!--                 <p class="forget_model_h2">验证码<span id="reminder04" -->
					<!--                                                     style="color: red ;font-weight: bold;margin-left: 5px"></span></p> -->
					<!--                 <label> -->
					<!--                     <input type="text" name="validate" class="txt_input txt_input2" style="width:50%"> -->
					<!--                     <button id="forgetYzm" style="height:36px;border-radius: 5px;border:none">获取验证码</button> -->
					<!--                 </label> -->

					<div class="rem_sub" style="margin-bottom: 8px">
						<div class="rem_sub_l"></div>
						<label> <input type="button" class="sub_buttons"
							name="Retrievenow" value="确定" style="opacity: 0.7;"
							onclick="gotoPersonCenter()"> <input type="button"
							class="sub_button" name="return" value="返回" style="opacity: 0.7;">
						</label>
					</div>
				</div>
			</div>
			<!--login_boder end-->
		</div>
	</div>
	<!--login_m end-->
</body>
</html>