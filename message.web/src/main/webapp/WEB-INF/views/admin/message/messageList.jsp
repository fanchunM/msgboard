<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
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
<!--     <link rel="stylesheet" href="resources/easyUI/themes/icon.css" /> -->
    <!-- inline styles related to this page -->
    <!-- ace settings handler -->
    <script src="resources/assets/js/ace-extra.min.js"></script>
    <link rel="stylesheet" href="resources/admin/css/commonForAdmin.css" />
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
<div id="createPaymentRequestDialog" title="主题关联" class="easyui-dialog" data-options="width:550,height:400,closed:true,buttons:'#createPaymentRequestDialogButtons'">
		<input name="messageId" type="hidden"/>
		<input id="dReplyStatus" type="hidden" value="${dReplyStatus}"/>
		<input id="dApprovalStatus" type="hidden" value="${dApprovalStatus}"/>
		<input id="viewStatus" type="hidden" value="${viewStatus}"/>
		<div style="padding: 18px">
		<div class="row">
			<div class="col-md-11 form-level text-center" style="margin-bottom:10px">
				<span class="datagrid-toolbar-span"><i class="icon-fixed-width"></i>选择添加主题内容</span>
				<input id="mainKeyWordId" name="mainKeyWordId" class="easyui-combotree">
			</div>
	    </div>
	    <div class="row">
	    	<div class="col-md-11 form-level">
	    		<table id="messageTheme"></table>
	    	</div>
	    </div>
	   </div>
		
	</div>
	<div id="createPaymentRequestDialogButtons">
		<a href="javascript:void(0);" class="easyui-linkbutton a-button" onClick="$('#createPaymentRequestDialog').dialog('close');">关闭</a>
	</div>
    <div class="main-content" style="margin-top:18px;">
        <div class="main-content-inner" style="height:97%">
            <input id="statusStr" type="hidden" th:value="${statusStr}"/>
            <table id="dataGridTable"></table>
            <div id="dataGridTableButtons" class="datagrid-toolbar-style">
            	<span class="datagrid-toolbar-span"><i class="icon-fixed-width"></i>类型</span>
               	<select id="keyTypes" style="width:60px" class="easyui-combobox">
               		<option value="">无</option>
               		<option value="0">标题</option>
               		<option value="1">提出人</option>
               		<option value="2">手机号</option>
               	</select>
                <span class="datagrid-toolbar-span"><i class="icon-fixed-width"></i>关键字</span>
                <input id="keyword" class="textbox" style="vertical-align: middle;width: 100px;"/>
                <span class="datagrid-toolbar-span"><i class="icon-fixed-width"></i>审核状态</span>
               	<select id="approvalStatus" style="width:83px" class="easyui-combobox">
               		<option value="">全部</option>
               		<option value="0">未处理</option>
               		<option value="1">审核不通过</option>
               		<option value="2">审核通过</option>
               	</select>
               	<span class="datagrid-toolbar-span"><i class="icon-fixed-width"></i>回复状态</span>
               	<select id="replyStatus" style="width:60px" class="easyui-combobox">
               		<option value="">全部</option>
               		<option value="0">未答复</option>
               		<option value="1">已答复</option>
               	</select>
     <!--          <div style="margin-top: 6px;">-->   
					<span class="datagrid-toolbar-span"><i class="icon-calendar"> </i>起始</span>
					<input name="publishTime" id="startTime" data-options = "onShowPanel:function(){$(this).datetimebox('spinner').timespinner('setValue','00:00:00')},required:true"
						class="easyui-datetimebox" style="width: 90px;">
					<span class="datagrid-toolbar-span"><i class="icon-calendar"> </i>结束</span>
					<input name="publishTime" id="finshTime" data-options = "onShowPanel:function(){$(this).datetimebox('spinner').timespinner('setValue','00:00:00')},required:true"
						class="easyui-datetimebox" style="width: 90px;">
					<a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="searchByQuerys();"><i class="icon-fixed-width"></i>搜索</a>	
<!-- 					<a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="checkMessage(2);" style="margin-left: 0px;"><i class="icon-fixed-width"></i>审核通过</a> -->
<!--                 	<a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="checkMessage(1);" style="margin-left: 0px;"><i class="icon-fixed-width"></i>审核不通过</a> -->
<!--                 	<a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="commentMessagesApproval();" style="margin-left: 0px;"><i class="icon-fixed-width"></i>待审批评论留言</a>  -->
	<!--  			</div> --> 
               	<div style="overflow: hidden; margin-top: 12px; margin-bottom: 6px;">
               		<div style="float: right;">
               	    	<a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="searchByQuerys(2);"><i class="icon-fixed-width"></i> 回收站</a> 
               			<a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="searchByQuerys(0);"><i class="icon-fixed-width"></i> 待答复留言</a> 
               			<a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="searchByQuerys(1);"><i class="icon-fixed-width"></i> 待审批留言</a> 
                		<a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="commentMessagesApproval();"><i class="icon-fixed-width"></i> 待审批评论留言</a> 
                		<a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="checkMessage(1);"><i class="icon-fixed-width"></i> 审核不通过</a>
            			<a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="checkMessage(2);"><i class="icon-fixed-width"></i> 审核通过</a>
           			</div>
           		</div> 
            </div>
        </div>
    </div>
</div><!-- /.main-container -->
<!--抽查-->
<!--<div id="randomInspDialog" title="抽查工单" class="easyui-dialog" data-options="height:400, width:800, closed:true">
    <table id="randomInspDataGridTable"></table>
</div>-->
<!-- 处办部门 -->
<div id="deptDialog" title="推送至公众服务平台" class="easyui-dialog" data-options="width:400,closed:true,buttons:'#deptDialogButtons'">
<input id="messageIdInput" type="hidden">
    <div style="margin:10px auto;text-align: center;">
    	<label>处办部门</label>
       <input id="sponsorDeptIdList" name="sponsorDeptIdList" class="easyui-combobox" data-options="url:'admin/dept_forcombo', mode:'remote', method:'GET'" style="width:200px"/>
    </div>
    <div style="margin:10px auto;text-align: center;">
    	<label>反馈时限</label>
       <input id="exterFeedTimeStr" class="easyui-datetimebox" data-options="showSeconds:true" style="width:200px"/>
    </div>
    <div style="margin:10px auto;text-align: center;">
    	<label>处办时限</label>
       <input id="interFeedTimeStr" class="easyui-datetimebox" data-options="showSeconds:true" style="width:200px"/>
    </div>
</div>
<div id="deptDialogButtons">
    <a href="javascript:void(0);" class="easyui-linkbutton" onClick="$('#deptDialog').dialog('close');">关闭</a>
    <a href="javascript:void(0);" class="easyui-linkbutton" onClick="submitSendPsp();">确定</a>
</div>

<!--删除原因-->
<div id="deleteReasonDialog" title="删除原因" class="easyui-dialog" data-options="width:400,closed:true,buttons:'#deleteReasonDialogButtons'">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
	<div style="margin-left: 10px">
			<input type="hidden" id="deleteReasonmessageId">
           <div class="fitem">
               <label style="width: 200px;float: left">删除原因:</label>
               <textarea  style="font-size: 14;width: 95%;height: 120px;" rows="5" id="deleteReasonText">
           		</textarea>
           </div>
	</div>
	</div>
	<div id="dlg-buttons" region="south" border="false" style="text-align:center;height:30px;line-height:30px;margin-top: 10px;">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveDeleteReason()"><i class="icon-fixed-width"></i>提交</a>
    </div>
</div>
<!-- basic scripts -->
<!-- <script src="resources/assets/js/jquery-1.11.3.min.js"></script> -->
<script src="resources/easyUI/jquery-1.8.0.min.js"></script>
<script src="resources/easyUI/jquery.easyui.min.js"></script>
<script src="resources/easyUI/locale/easyui-lang-zh_CN.js"></script>
<script src="resources/admin/js/message/messageList.js"></script>
<script src="resources/admin/js/common.js"></script>
</body>
</html>