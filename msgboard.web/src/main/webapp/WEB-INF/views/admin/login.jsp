<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>后台登陆界面</title>
    <meta name="description" content="overview &amp; stats"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <!-- bootstrap & fontawesome -->
<!--     <link rel="stylesheet" href="resources/assets/css/bootstrap.min.css"/> -->
<!--     <link rel="stylesheet" href="resources/assets/font-awesome/4.5.0/css/font-awesome.min.css"/> -->
    <!-- page specific plugin styles -->
    <!-- text fonts -->
<!--     <link rel="stylesheet" href="resources/assets/css/fonts.googleapis.com.css"/> -->
    <!-- ace styles -->
<!--     <link rel="stylesheet" href="resources/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/> -->
<!--     <link rel="stylesheet" href="resources/assets/css/ace-skins.min.css"/> -->
<!--     <link rel="stylesheet" href="resources/assets/css/ace-rtl.min.css"/> -->
<script type="text/javascript" src="resources/js/commonJs/jquery-1.8.3.min.js"></script>
    <!-- ace settings handler -->
<!--     <script src="resources/assets/js/ace-extra.min.js"></script> -->
<!--     <script src="resources/admin/js/common.js"></script> -->
<!--     <script src="resources/admin/js/main.js"></script> -->
</head>
<script type="text/javascript">
	function submit(){
		var userName = $("#userName").val();
		var pwd = $("#pwd").val();
		if(userName == ''){
			alert("用户名不能为空");
			return;
		}else if(pwd == ''){
			alert("密码不能为空");
			return;
		}
		$("#loginAdmin").submit();
	}
</script>
<body>
<div>
<form action="admin_login" method="POST" id="loginAdmin">
	<label>
		用户名
	</label>
	<input id="userName" name="userName" type="text">
	<label>
		密码
	</label>
	<input id="pwd" name="pwd" type="password">
	<input type="button" value="提交" onclick="submit()">
</form>
	
</div>
</body>
</html>