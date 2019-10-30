<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" isELIgnored="false"%>
<html lang="zh-CN">
	<!-- <script src="resources/easyUI/jquery.easyui.min.js"></script> -->
<script src="resources/admin/js/message/messageDetail.js"></script>
<script type="text/javascript">
$(function() {
	var messageType = $('#messageType').val();
	//如果通过评论管理模块访问该页面
	if(messageType){
		//隐藏操作按钮<审核通过><审核不通过><主题关联><推送至公众服务平台>
		$('#Concealment1').css("display","none");
		//隐藏快速回复
		$('#Concealment2').css("display","none");
		//隐藏评论审核
		$('#Concealment3').css("display","none");
		//将回复文本设置为不可修改
		$('#replyText').prop("readonly",true);
		//隐藏回复的保存按钮
		$('#dlg-buttons').css("display","none");
		//将备注文本设置为不可修改
		$('#deptName').prop("readonly",true);
		//隐藏主题关联按钮
		$('#ConcealmentTheme').css("display","none");
		//隐藏备注
		$("#ConcealmentDeptName").css("display","none");
		//隐藏保存按钮（备注）
		$("#ConcealmentKeep").css("display","none");
	}
});
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
<input id="messageType" value="${messageType }" type="hidden"/>
<input id="approvalStatusValue" type="hidden" value="${approvalStatusValue}"/>
	<div id="createPaymentRequestDialog" title="主题关联" class="easyui-dialog" data-options="width:550,height:400,closed:true,buttons:'#createPaymentRequestDialogButtons'">
		<input name="messageId" type="hidden"/>
		<input id="dReplyStatus" type="hidden" value="${dReplyStatus}"/>
		<input id="dApprovalStatus" type="hidden" value="${dApprovalStatus}"/>
		<input id="viewStatus" type="hidden" value="${viewStatus}"/>
		<div style="padding: 18px">
		<div class="row">
			<div class="col-md-11 form-level text-center" style="margin-bottom:10px">
				<span class="datagrid-toolbar-span"><i class="icon-fixed-width"></i>选择添加主题内容</span>
				<input id="mainKeyWordId" name="mainKeyWordId" class="easyui-combotree" style="vertical-align: middle;">
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

	<div data-options="region:'center',border:false" title="" style="overflow-x: hidden;">
	<div style="margin-left: 10px">
	<form id="fm" method="post">
		<input name="messageId" type="hidden" value="${messageDto.id}"/>
	<!-- <div class="row" style="margin-top:20px">
			<div class="col-md-12 text-center form-level"><div class="datagrid-toolbar-span-reply"><i class="icon-fixed-width"></i>&emsp;标&emsp;&emsp;题</div><div class="datagrid-toolbar-span-reply-value">${messageDto.title}</div></div>
		</div> --> 
		<div class="row" style="margin-top:20px">
  			<div class="col-md-3 text-center form-level"><div class="datagrid-toolbar-span-reply"><i class="icon-fixed-width"></i>&emsp;类&emsp;&emsp;型</div><div class="datagrid-toolbar-span-reply-value">${messageDto.messageCategory}</div></div>
  			<div class="col-md-3 text-center form-level"><div class="datagrid-toolbar-span-reply"><i class="icon-fixed-width"></i>&emsp;时&emsp;&emsp;间</div><div class="datagrid-toolbar-span-reply-value">${messageDto.createTimestamp}</div></div>
  			<div class="col-md-3 text-center form-level"><div class="datagrid-toolbar-span-reply"><i class="icon-fixed-width"></i>&emsp;姓&emsp;&emsp;名</div><div class="datagrid-toolbar-span-reply-value">${messageDto.personName}</div></div>
			<div class="col-md-3 text-center form-level"><div class="datagrid-toolbar-span-reply"><i class="icon-fixed-width"></i>&emsp;手机号码</div><div class="datagrid-toolbar-span-reply-value">${messageDto.phone}</div></div>
		</div>
		<div class="row" style="margin-top:20px">
			<div class="col-md-9 text-center form-level"><div class="datagrid-toolbar-span-reply"><i class="icon-fixed-width"></i>&emsp;标&emsp;&emsp;题</div><div class="datagrid-toolbar-span-reply-value">${messageDto.title}</div></div>
			<div class="col-md-3 text-center form-level"><div class="datagrid-toolbar-span-reply"><i class="icon-fixed-width"></i>&emsp;处办部门</div><div class="datagrid-toolbar-span-reply-value" id="messageDtoDeptName">${messageDto.deptName}</div></div>
		</div>
		<div class="row" style="margin-top:20px">
			<div class="col-md-12 text-center form-level">
				<div class="datagrid-toolbar-span-reply"><i class="icon-fixed-width"></i>&emsp;留言内容</div>
				<div class="datagrid-toolbar-span-reply-value" style="width:85%">
					<textarea readonly style="text-align:left;font-size: 14;width:100%" rows="5"
						class="easyui-validatebox" required="true">${messageDto.commentStr }
					</textarea>
				</div>
			</div>
		</div>
		<div class="row" style="margin-top:20px">
			<div class="col-md-12 text-center form-level">
				<div class="datagrid-toolbar-span-reply"><i class="icon-fixed-width"></i>&emsp;附加图片</div>
				<div id="imageDiv" style="overflow: hidden;padding:0px 10px;display:none">
			    </div>
			</div>
		</div>
		
		<div class="row" style="margin-top:20px">
		   <div class="col-md-12  form-level">
		      <div class="datagrid-toolbar-span-reply"><i class="icon-fixed-width"></i>&emsp;操&emsp;&emsp;作</div> 
		   	     <div style="width: fit-content;float:left;margin-top: 5px;margin-left: 5px;font-size:14px;padding: 3px 8px;" id="themeStr">${messageDto.themeStr}</div>
			 <a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="themeAssociation('${messageDto.id}')" style="margin-left:10px;" id="ConcealmentTheme">主题关联</a>&emsp;
			   <span style="font-size: 16px;" id="ConcealmentDeptName">备注：</span><input name="deptName" id="deptName" type="text" value="${messageDto.deptName}" style="vertical-align: middle;">
			  <a href="javascript:void(0)" id="ConcealmentKeep" class="easyui-linkbutton a-button" style="margin-right:10px; margin-top: -2px;" onclick="saveDeptName()">保存</a> 
		   </div>
		</div>
		
		<div class="row" style="margin: 30px 0px;" id="Concealment1">
			<div class="col-md-12 text-center form-level">
<!-- 				<div class="datagrid-toolbar-span-reply"><i class="icon-fixed-width"></i>&emsp;操&emsp;&emsp;作</div> -->
<!-- 				<div  class="datagrid-toolbar-span-reply-value" style="width:85%"> -->
				  <a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="checkMessageDetail(2);" id="agreeButton"><i class="icon-fixed-width"></i>审核通过</a>
	              <a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="checkMessageDetail(1);" id="disAgreeButton"><i class="icon-fixed-width"></i>审核不通过</a>
<!--		      <a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="themeAssociation('${messageDto.id}')">主题关联</a>-->
		          <a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="sendToPsp('${messageDto.id}')">推送至公众平台</a>
<!-- 				</div>	 -->
			</div>
		</div>
		
		<div class="row" style="margin-top:20px" id="Concealment2">
			<div class="col-md-12 text-center form-level">
				<div class="datagrid-toolbar-span-reply"><i class="icon-fixed-width"></i>&emsp;快捷回复</div>
				<div class="datagrid-toolbar-span-reply-value">
					<input id="quickReply"  class="easyui-combobox" data-options="textField:'value1',url:'admin/message/quick_reply',method:'GET',mode:'remote'">
				</div>
			</div>
		</div>
		
<!--  	<div class="row" style="margin-top:20px">
			<div class="col-md-12 text-center form-level">
				<div class="datagrid-toolbar-span-reply"><i class="icon-fixed-width"></i>&emsp;备&emsp;&emsp;注</div>
				<div class="datagrid-toolbar-span-reply-value">
					 <input name="deptName" id="deptName" class="textbox" value="${messageDto.deptName}">
			    </div>
			</div>
		</div>-->	
		
		<div class="row" style="margin-top:20px">
			<div class="col-md-12 text-center form-level">
				<div class="datagrid-toolbar-span-reply"><i class="icon-fixed-width"></i>&emsp;回复内容</div>
				<div class="datagrid-toolbar-span-reply-value" style="width:85%">
					<textarea id="replyText" name="replyText" style="text-align:left;font-size: 14;width: 100%" rows="5" 
					class="easyui-validatebox" required="true">${messageDto.reply }</textarea>
				</div>
			</div>
			<div id="dlg-buttons" region="south" border="false" style="text-align:center;height:30px;line-height:30px; ">
        		<a href="javascript:void(0)" class="easyui-linkbutton a-button" style="margin-right:10px; margin-top: 10px;" onclick="saveReply()"><i class="icon-fixed-width"></i>发布</a>
    		</div>
		</div>
		</form>
		 <div class="row" style="margin-top:50px" id="Concealment3">
	    	<div class="col-md-12 text-center form-level">
	    		<div class="datagrid-toolbar-span-reply"><i class="icon-fixed-width"></i>&emsp;评&emsp;&emsp;论</div>
	    		<div class="datagrid-toolbar-span-reply-value" style="width:85%">
    				<table id="commentDataGridTable" style="height:600px"></table>
	    		</div>
	    	</div>
	    	
	    	<div id="commentDataGridTableButtons" class="datagrid-toolbar-style">
		        <a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="checkComment(2);"><i class="icon-fixed-width"></i>审核通过</a>
		        <a href="javascript:void(0);" class="easyui-linkbutton a-button" onclick="checkComment(1);"><i class="icon-fixed-width"></i>审核不通过</a>
		    </div>
    	</div>
	</div>
	</div>
	
</div>	

<!-- 评论详情 -->
<div id="commentDetailDialog" title="评论详情" class="easyui-dialog" data-options="width:400,closed:true,buttons:'#commentDetailDialogButtons'">
    <div style="margin:10px auto;text-align: center;">
    	<label style="width:80px;float:left">&nbsp;提出人</label>
       <input id="personName" name="personName" class="easyui-text" style="color:#565050;width:220px;border:1px solid #ccc" readonly=readonly/>
    </div>
    <div style="margin:10px auto;text-align: center;">
    	<label style="width:80px;float:left">内&nbsp;&nbsp;容</label>
       <textarea id="commentStr" class="easyui-text"  style="color:#565050;width:220px;height:300px" readonly=readonly></textarea>
    </div>
<!--     <div style="margin:10px auto;text-align: center;"> -->
<!--     	<label style="width:100px">审核状态</label> -->
<!--        <input id="approvalStatus" class="easyui-text" style="width:200px" readonly=readonly/> -->
<!--     </div> -->
    <div style="margin:10px auto;text-align: center;">
    	<label style="width:80px;float:left">创建时间</label>
       <input id="createTimestamp" class="easyui-text"  style="color:#565050;width:220px;border:1px solid #ccc" readonly=readonly/>
    </div>
</div>
<!--  <div id="commentDetailDialogButtons">
    <a href="javascript:void(0);" class="easyui-linkbutton" onClick="$('#commentDetailDialog').dialog('close');">关闭</a>
     <a href="javascript:void(0);" class="easyui-linkbutton" onClick="submitSendPsp();">确定</a>
</div>-->
<style>
	.form-level{
		font-weight: 400;
    	font-size: 14px;
	}
	.form-value{
		font-size: 14px;
    	color: gray;
	}
	.row{
		margin-top: 5px;
	}
	.btn-disable{
		background: gray;
   		border: 1px solid gray;
	}
</style>
</html>

	