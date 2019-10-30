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
<title>广济南社区</title>
<link href="resources/css/common/Wopop_files/style_log.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="resources/js/commonJs/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="resources/js/sueXing/sueXing.js"></script
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
$(document).ready(function() {
    //进入页面时先调用所需获取数据的方法
     getUserInfoForH5();
    /**
     * 若进入页面时就需要获取用户信息才可以进行下一步操作的，请使用定时器来检查你所需的信息是否已经被写入widnow.nativeData内。若
     */
    //定时器
    var checkResult = setInterval(function() {
        if (window.nativeData.userId != undefined) {
            window.clearInterval(checkResult);
            window.location.href="suexingLogin?userId="+window.nativeData.userId+"&phone="+window.nativeData.phone;
            //若有token，执行下一步操作（建议使用函数）
            return false;
        }
    }, 300);
});
</script>
<body>
</body>
</html>