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
    <link rel="stylesheet" href="resources/bootstrap/Font-Awesome-3.2.1/css/font-awesome.min.css">
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
    <style type="text/css">
        td{ vertical-align:middle !important;}
        .gray{ background-color:#F8F8F8;}
        .row td{ padding:5px 8px !important; min-height:35px; vertical-align:middle; font-size:14px;}
        .row input,.row select,.row textarea{ border-width:1px !important;margin:1px 0px;}
        .datagrid {
        	margin:0 auto
        }
    </style>
</head>
<body class="no-skin" style="background-color:#FFF;">
<div class="main-container ace-save-state" id="main-container" style="height:100%; overflow:hidden;">
    <div class="main-content" style="margin-top:18px;">
        <div class="main-content-inner" style="height:97%">
            <table id="synchronizationThemeTable"></table>
            <div id="synchronizationThemeTableButtons" class="datagrid-toolbar-style">
<!--                 <a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="saveSynchronizationThemeInformation();"><i class="icon-fixed-width"></i>一键同步主题</a> -->
            	<a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="openFatherThemeDialog();"><i class="icon-fixed-width"></i>添加父级主题</a>
            </div>
        </div>
    </div>
</div><!-- /.main-container -->
<!-- 添加子主题 -->
<div id="addThemeDialog" title="新增子主题" class="easyui-dialog" data-options="width:650,height:450,closed:true,buttons:'#addThemeDialogButtons'">
	<input id="fatherId" type="hidden">
	<div style="text-align: center">
	    <div style="margin-top:10px;overflow:hidden">
			<span class="datagrid-toolbar-span-reply-person"><i class="icon-fixed-width"></i>&nbsp;主题序号</span><input class="textbox" name="serialNumber" style="vertical-align: middle;line-height:10px;width:175px;"/>
		</div>
		<div style="margin-top:10px;overflow:hidden">
			<span class="datagrid-toolbar-span-reply-person"><i class="icon-fixed-width"></i>&nbsp;主题名称</span><input class="textbox" name="themeName" style="vertical-align: middle;line-height:10px;width:175px;"/>
		</div>
<!--  	<div style="margin-top:10px;overflow:hidden">
			<span class="datagrid-toolbar-span-reply-person"><i class="icon-fixed-width"></i>&nbsp;父级主题</span><input id="fatherThemeCombo" class="easyui-combobox" style="width:175px" data-options="url:'sys/getFatherTheme',method:'GET'" name="fatherTheme">
		</div>-->	
		<div style="margin-top:10px">
			<a href="javascript:void(0);" style="width:100px" class="easyui-linkbutton" onClick="addTheme(2);">添加</a>
		</div>
	</div>
	<div style="margin-top:10px">
		<table id="sonDatagrid"></table>
	</div>
	
</div>
<div id="addThemeDialogButtons">
	<a href="javascript:void(0);" class="easyui-linkbutton" onClick="$('#addThemeDialog').dialog('close');">关闭</a>
	
</div>
<!-- 添加父主题 -->
<div id="addFatherThemeDialog" title="新增主题分类" class="easyui-dialog" data-options="width:500,height:150,closed:true,buttons:'#addFatherThemeDialogButtons'">
	<div style="text-align: center">
		<div style="margin-top:10px;overflow:hidden">
			<span class="datagrid-toolbar-span-reply-person"><i class="icon-fixed-width"></i>&nbsp;主题序号</span><input class="textbox" name="serialNumber" style="vertical-align: middle;line-height:10px;"/>
		</div>
		<div style="margin-top:10px;overflow:hidden">
			<span class="datagrid-toolbar-span-reply-person"><i class="icon-fixed-width"></i>&nbsp;主题名称</span><input class="textbox" name="themeName" style="vertical-align: middle;line-height:10px;"/>
		</div>
	</div>
</div>
<div id="addFatherThemeDialogButtons">
	<a href="javascript:void(0);" class="easyui-linkbutton" onClick="$('#addFatherThemeDialog').dialog('close');">关闭</a>
	<a href="javascript:void(0);" class="easyui-linkbutton" onClick="addTheme(1);">保存</a>
</div>
<!-- 修改主题 -->
<div id="updateThemeDialog" title="修改主题" class="easyui-dialog" data-options="width:500,height:200,closed:true,buttons:'#updateThemeDialogButtons'">
	<div style="text-align: center">
	    <div style="margin-top:10px;overflow:hidden">
			<span class="datagrid-toolbar-span-reply-person"><i class="icon-fixed-width"></i>&nbsp;主题序号</span><input class="textbox" name="serialNumber" style="vertical-align: middle;line-height:10px;"/>
		</div>
		<div style="margin-top:10px;overflow:hidden">
		<input type="hidden" name="typeTheme">
		<input type="hidden" name="themeId">
			<span class="datagrid-toolbar-span-reply-person"><i class="icon-fixed-width"></i>&nbsp;主题名称</span><input class="textbox" name="themeName" style="vertical-align: middle;line-height:10px;"/>
		</div>
	</div>
</div>
<div id="updateThemeDialogButtons">
	<a href="javascript:void(0);" class="easyui-linkbutton" onClick="$('#updateThemeDialog').dialog('close');">关闭</a>
	<a href="javascript:void(0);" class="easyui-linkbutton" onClick="updateTheme();">保存</a>
</div>
<!-- basic scripts -->
<!-- <script src="resources/assets/js/jquery-1.11.3.min.js"></script> -->
<script src="resources/easyUI/jquery-1.8.0.min.js"></script>
<script src="resources/easyUI/jquery.easyui.min.js"></script>
<script src="resources/easyUI/locale/easyui-lang-zh_CN.js"></script>
<script src="resources/admin/js/common.js"></script>
<script src="resources/admin/js/sys/synchronizationTheme.js"></script>
</body>
</html>