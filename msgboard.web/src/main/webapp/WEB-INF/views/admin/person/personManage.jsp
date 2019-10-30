<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <title></title>
    <meta name="description" content="Static &amp; Dynamic Tables" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="resources/assets/css/bootstrap.min.css" />
    <!-- page specific plugin styles -->
    <!-- ace styles -->
    <link rel="stylesheet" href="resources/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="resources/assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
    <![endif]-->
    <link rel="stylesheet" href="resources/assets/css/ace-skins.min.css" />
    <link rel="stylesheet" href="resources/assets/css/ace-rtl.min.css" />
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="resources/assets/css/ace-ie.min.css" />
    <![endif]-->
    <link rel="stylesheet" href="resources/bootstrap/Font-Awesome-3.2.1/css/font-awesome.min.css" />
    <link rel="stylesheet" href="resources/easyUI/themes/metro-blue/easyui.css" />
    <link rel="stylesheet" href="resources/easyUI/themes/icon.css" />
    <link rel="stylesheet" href="resources/admin/css/commonForAdmin.css" />
    <!-- inline styles related to this page -->
    <!-- ace settings handler -->
    <script src="resources/assets/js/ace-extra.min.js"></script>
    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->
    <!--[if lte IE 8]>
    <script src="resources/assets/js/html5shiv.min.js"></script>
    <script src="resources/assets/js/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="resources/js/common/md5.js"></script>
    <style type="text/css">
        td{ vertical-align:middle !important;}
        .gray{ background-color:#F8F8F8;}
        .row td{ padding:5px 8px !important; min-height:35px; vertical-align:middle; font-size:14px;}
        .row input,.row select,.row textarea{ border-width:1px !important;margin:1px 0px;}
    </style>
</head>
<body class="no-skin" style="background-color:#FFF;">
<div class="main-container ace-save-state" id="main-container" style="height:100%; overflow:hidden;">
    <div class="main-content" style="margin-top:18px;">
        <div class="main-content-inner" style="height:97%">
            <table id="dataGridTable"></table>
            <div id="dataGridTableButtons" class="datagrid-toolbar-style">
            	<span class="datagrid-toolbar-span"><i class="icon-fixed-width"></i>用户名</span>
				<input id="searchFilterText" class="textbox" style="vertical-align: middle;"/>
				<a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="$('#dataGridTable').datagrid('load', {q:$('#searchFilterText').val().trim()})"><i class="icon-fixed-width"></i>搜索</a>
			</div>
        </div>
    </div>
</div>
<div id="personDetailDialog" title="用户详情" class="easyui-dialog" data-options="width:550,height:400,closed:true,buttons:'#personDetailDialogButtons'">
	<div style="text-align: center">
		<div style="margin-top:20px;overflow:hidden">
			<span class="datagrid-toolbar-span-reply-person"><i class="icon-fixed-width"></i>&nbsp;用户名称</span><input class="textbox" readonly="readonly" name="userName">
		</div>
		<div style="margin-top:20px;overflow:hidden">
			<span class="datagrid-toolbar-span-reply-person"><i class="icon-fixed-width"></i>&nbsp;手机号码</span><input class="textbox" readonly="readonly" name="phone">
		</div>
<!-- 		<div style="margin-top:20px;overflow:hidden"> -->
<!-- 			<span class="datagrid-toolbar-span-reply-person"><i class="icon-fixed-width"></i>&nbsp;昵&emsp;&emsp;称</span><input class="textbox" readonly="readonly" name="nickName"> -->
<!-- 		</div> -->
		<div style="margin-top:20px;overflow:hidden">
			<span class="datagrid-toolbar-span-reply-person"><i class="icon-fixed-width"></i>&nbsp;性&emsp;&emsp;别</span><input class="textbox" readonly="readonly" name="gender">
		</div>
		<div style="margin-top:20px;overflow:hidden">
			<span class="datagrid-toolbar-span-reply-person"><i class="icon-fixed-width"></i>&nbsp;留言数量</span><input class="textbox" readonly="readonly" name="messageNum">
		</div>
		<div style="margin-top:20px;overflow:hidden">
			<span class="datagrid-toolbar-span-reply-person"><i class="icon-fixed-width"></i>&nbsp;微信账号ID</span><input class="textbox" readonly="readonly" name="messageNum">
		</div>
		<div style="margin-top:20px;overflow:hidden">
			<span class="datagrid-toolbar-span-reply-person"><i class="icon-fixed-width"></i>&nbsp;苏e行账号ID</span><input class="textbox" readonly="readonly" name="messageNum">
		</div>
		<div style="margin-top:20px;overflow:hidden">
			<span class="datagrid-toolbar-span-reply-person"><i class="icon-fixed-width"></i>&nbsp;注册时间</span><input class="textbox" readonly="readonly" name="createTime">
		</div>
	</div>
</div>
<div id="personDetailDialogButtons">
	<a href="javascript:void(0);" class="easyui-linkbutton" onClick="$('#personDetailDialog').dialog('close');">关闭</a>
</div>
<!-- basic scripts -->
<!-- <script src="resources/assets/js/jquery-1.11.3.min.js"></script> -->
<script src="resources/easyUI/jquery-1.8.0.min.js"></script>
<script src="resources/easyUI/jquery.easyui.min.js"></script>
<script src="resources/easyUI/locale/easyui-lang-zh_CN.js"></script>
<script src="resources/admin/js/person/personManage.js"></script>
<script src="resources/admin/js/common.js"></script>
</body>
</html>