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
    </style>
</head>
<body class="no-skin" style="background-color:#FFF;">
<div class="main-container ace-save-state" id="main-container" style="height:100%; overflow:hidden;">
    <div class="main-content" style="margin-top:18px;">
        <div class="main-content-inner" style="height:97%">
            <table id="dataGridTableCheck"></table>
            <div id="dataGridTableButtonsCheck" class="datagrid-toolbar-style">
                <span class="datagrid-toolbar-span"><i class="icon-fixed-width"></i>关键字</span>
                <input id="keyword" class="textbox" style="vertical-align: middle;"/>
                <span class="datagrid-toolbar-span"><i class="icon-fixed-width"></i>审核状态</span>
                	<select id="faqsStatus" style="width:100px" class="easyui-combobox">
                		<option value="">全部</option>
                		<option value="0">未处理</option>
                		<option value="1">审核不通过</option>
                		<option value="2">审核通过</option>
                	</select>
                <a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="searchByQuerysCheck();"><i class="icon-fixed-width"></i>搜索</a>
                <a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="checkFaqs(2);"><i class="icon-fixed-width"></i>审核通过</a>
                <a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="checkFaqs(1);"><i class="icon-fixed-width"></i>审核不通过</a>
            </div>
        </div>
    </div>
</div>
<!-- basic scripts -->
<!-- <script src="resources/assets/js/jquery-1.11.3.min.js"></script> -->
<script src="resources/easyUI/jquery-1.8.0.min.js"></script>
<script src="resources/easyUI/jquery.easyui.min.js"></script>
<script src="resources/easyUI/locale/easyui-lang-zh_CN.js"></script>
<script src="resources/js/editor/kindeditor.js"></script>
<script src="resources/admin/js/faqs/faqsList.js"></script>
<script src="resources/admin/js/common.js"></script>
</body>
</html>