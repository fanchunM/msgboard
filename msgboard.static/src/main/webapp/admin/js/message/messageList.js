$(function(){
	//点击待回复留言，待审核留言时进行取值。传到queryParams参数中进行过滤查询
	var dReplyStatus = $("#dReplyStatus").val();
	var dApprovalStatus = $("#dApprovalStatus").val();
	var viewStatus = $("#viewStatus").val();
	if(viewStatus!=""){
		$('#approvalStatus').combobox('setValue', '0'); 
	}
	if(dReplyStatus!=""){
		$('#replyStatus').combobox('setValue', '0'); 
	}
	if(dApprovalStatus!=""){
		$('#approvalStatus').combobox('setValue', '0'); 
	}
	$('#dataGridTable').datagrid({
	        url: 'admin/message/messageList',
	        //dReplyStatus待回复状态，dApprovalStatus待审核状态。
	        queryParams:{
	        	replyStatus:dReplyStatus,
	        	approvalStatus:dApprovalStatus,
	        	viewStatus:viewStatus
    	    },
	        method: 'POST',
	        fit: true,
	        striped: true,
	        rowNumbers: true,
	        singleSelect: false,
	        pagination: true,
	        pageSize: 20,
	        pageNumber:1,
	        toolbar: '#dataGridTableButtons',
	        loadMsg: '程序处理中，请稍等...',
	        columns:[[
	        	{field:'ck',title:'选择',checkbox:true,width:50,align:'center'},
//	            {field: 'mainTextId',title: '主题关联Id'},
	            {field: 'serialNumber',title:'留言编号'},
	            {field: 'mainText',title: '主题关联'},
	  //          {field: 'msgType',title: '信息类型'},
	            {field: 'messageCategory',title: '类型'},
	            {field: 'title',title: '标题',width:150,formatter:function(value,row){
	            	return '<span style="cursor:pointer;color: #337ab7;"  onClick="messageDetail(\''+row.id+'\');">'+value+'</span>';
	            }},
	            {field: 'commentStr',title: '内容',width:340,formatter:function(value,row){
	            	return '<span title="'+value+'" style="cursor:pointer;color: #337ab7;" onClick="messageDetail(\''+row.id+'\');">'+value+'</span>';
	            }},
	            {field: 'approvalStatus',title: '审核状态'},
	            {field: 'replyStatus',title: '回复状态'},
	            {field: 'deptName',title: '处办部门'},
	            {field: 'personName',title: '提出人'},
	            {field: 'phone',title: '手机号'},
	            {field: 'ipAddress',title: 'IP地址'},
	   //         {field: 'deptId',title: '处办部门'},
	  //         {field: 'gradeValue',title: '评分'},
	            {field: 'messageLike',title: '点赞数'},
	            {field: 'commentNumber',title: '评论数'},
	            /*{field: 'orderStatus',title: '工单状态'},
	            {field: 'displayStatus',title: '显示状态'},
	            {field: 'messageEnable',title: '删除状态'},*/
	            {field: 'status',title: '推送状态',formatter: function(value,row){
	            		if(value == "0"){
	            			return "未推送"
	            		}else if(value == "1"){
	            			return "已推送";
	            		}else if(value == "2"){
	            			return "推送失败";
	            		}else{
	            			return "已回复";
	            		}
	            	}
	            },
	            {field: 'createTimestamp',title: '创建时间'},
	            {field: 'lastUpdateTimestamp',title: '修改时间'},
	            {field: 'apprivalPersonName',title: '审核人'},
	            {field:'id',title:'操作', formatter: function(value,row){
		            //    var themeAssociation = '<a href="javascript:void(0);" onClick="themeAssociation(\''+value+'\');">主题关联</a>';
		            //    var messageDetail = '<a href="javascript:void(0);" onClick="messageDetail(\''+value+'\');">留言详情</a>';
		                var deleteMessage = '<a href="javascript:void(0);" onClick="deleteMessage(\''+value+'\');">删除</a>';
		            //    var lookReview = '<a href="javascript:void(0);" onClick="lookReview(\''+value+'\');">查看评论</a>';
		           //     var sendToPsp = '<a href="javascript:void(0);" onClick="sendToPsp(\''+row.id+'\',\''+row.mainText+'\');">推送至公众平台</a>';
		                if(row.status == "0" || row.status == "2"){
		                	var returnStr = deleteMessage;
		                }else{
		                	var returnStr = deleteMessage;
		                }
	                		 
		                return returnStr;
		                
		         }}
	        ]]
	    });
	
	
	
});

//推送至公众平台
function sendToPsp(id,mainText){
	if(mainText == ""){
		alert("请先关联主题分类!");
		return;
	}
	$("#messageIdInput").val(id);
	
	$('#deptDialog').dialog("open");
}

function submitSendPsp(){
	var messageId = $("#messageIdInput").val();
	var deptId = $("#sponsorDeptIdList").combobox("getValue");
	var exterFeedTimeStr = $("#exterFeedTimeStr").datebox('getText');
	var interFeedTimeStr = $("#interFeedTimeStr").datebox('getText');
	if(deptId == ""){
		alert("请选择部门!");
		return;
	}
	$.ajax({
		url:'admin/send_psp',
		type:'POST',
		data:{messageId:messageId,deptId:deptId,exterFeedTimeStr:exterFeedTimeStr,interFeedTimeStr:interFeedTimeStr},
		success:function(data){
			alert("推送成功!");
			$('#deptDialog').dialog("close");
			$('#dataGridTable').datagrid("reload");
			$("#sponsorDeptIdList").combobox("setValue","");
			$("#exterFeedTimeStr").datebox("setText","");
			$("#interFeedTimeStr").datebox("setText","");
		},
		error:function(data){
			AjaxErrorHandler(data);
		}
	});
	
}



//过滤留言状态
function searchByQuerys(type){
	if(type==null){
		var keyword = $('#keyword').val();
    	var approvalStatus= $('#approvalStatus').combobox('getValue');
    	var replyStatus= $('#replyStatus').combobox('getValue');
    	var keyTypes = $('#keyTypes').combobox('getValue');
    	var startTime = $('#startTime').datetimebox("getValue");
    	var finshTime = $('#finshTime').datetimebox("getValue");
    	if(keyTypes!=""&&keyword==""){
    		alert("请填写关键字！");
    		return;
    	}
    	if(keyTypes==""&&keyword!=""){
    		alert("请填写关键字类型！");
    		return;
    	}
    	if(startTime!=""&&finshTime==""){
    		alert("请选择结束日期！");
    		return;
    	}
    	if(finshTime!=""&&startTime==""){
    		alert("请选择起始日期！");
    		return;
    	}
    	//对时间格式进行正则判断
    	var regex=/^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1])) (?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]:[0-5][0-9]$/;
    	if(startTime!=""||finshTime!=""){
    		if(!regex.test(startTime)||!regex.test(finshTime)){
        	    alert("格式不正确！请输入正确的时间格式，如：2019-01-01 08:08:00");
        	    return;
        	}
    	}
	}else{
		if(type=="0"){
			var replyStatus = 0;
		}else if(type=="1"){
			var approvalStatus = 0;
		}else if(type=="2"){
			var messageEnable = 1;
		}
	}
    	$('#dataGridTable').datagrid({
	        url: 'admin/message/messageList',
	        queryParams:{
	        	replyStatus:replyStatus,
	        	keyword:keyword,
	        	approvalStatus:approvalStatus,
	        	keyTypes:keyTypes,
	        	startTime:startTime,
	        	finshTime:finshTime,
	        	messageEnable:messageEnable
    	    },
	        method: 'POST',
	        fit: true,
	        striped: true,
	        rowNumbers: true,
	        singleSelect: false,
	        pagination: true,
	        pageSize: 20,
	        pageNumber:1,
	        toolbar: '#dataGridTableButtons',
	        loadMsg: '程序处理中，请稍等...',
	        columns:[[
	        	{field:'ck',title:'选择',checkbox:true,width:50,align:'center'},
//	            {field: 'mainTextId',title: '主题关联Id'},
	            {field: 'serialNumber',title:'留言编号'},
	            {field: 'mainText',title: '主题关联'},
	  //          {field: 'msgType',title: '信息类型'},
	            {field: 'messageCategory',title: '类型'},
	            {field: 'title',title: '标题',width:150,formatter:function(value,row){
	            	return '<span style="cursor:pointer;color: #337ab7;"  onClick="messageDetail(\''+row.id+'\');">'+value+'</span>';
	            }},
	            {field: 'commentStr',title: '内容',width:340,formatter:function(value,row){
	            	return '<span title="'+value+'" style="cursor:pointer;color: #337ab7;" onClick="messageDetail(\''+row.id+'\');">'+value+'</span>';
	            }},
	            {field: 'approvalStatus',title: '审核状态'},
	            {field: 'replyStatus',title: '回复状态'},
	            {field: 'deptName',title: '处办部门'},
	            {field: 'personName',title: '提出人'},
	            {field: 'phone',title: '手机号'},
	            {field: 'ipAddress',title: 'IP地址'},
	   //         {field: 'deptId',title: '处办部门'},
	  //         {field: 'gradeValue',title: '评分'},
	            {field: 'messageLike',title: '点赞数'},
	            {field: 'commentNumber',title: '评论数'},
	//            {field: 'orderStatus',title: '工单状态'},
	//            {field: 'displayStatus',title: '显示状态'},
	//            {field: 'messageEnable',title: '删除状态'},
	            {field: 'status',title: '推送状态',formatter: function(value,row){
	            		if(value == "0"){
	            			return "未推送"
	            		}else if(value == "1"){
	            			return "已推送";
	            		}else if(value == "2"){
	            			return "推送失败";
	            		}else{
	            			return "已回复";
	            		}
	            	}
	            },
	            {field: 'createTimestamp',title: '创建时间'},
	            {field: 'lastUpdateTimestamp',title: '修改时间'},
	            {field: 'apprivalPersonName',title: '审核人'},
	            {field:'id',title:'操作', formatter: function(value,row){
		            //    var themeAssociation = '<a href="javascript:void(0);" onClick="themeAssociation(\''+value+'\');">主题关联</a>';
		            //    var messageDetail = '<a href="javascript:void(0);" onClick="messageDetail(\''+value+'\');">留言详情</a>';
		                var deleteMessage = '<a href="javascript:void(0);" onClick="deleteMessage(\''+value+'\');">删除</a>';
		            //    var lookReview = '<a href="javascript:void(0);" onClick="lookReview(\''+value+'\');">查看评论</a>';
		           //     var sendToPsp = '<a href="javascript:void(0);" onClick="sendToPsp(\''+row.id+'\',\''+row.mainText+'\');">推送至公众平台</a>';
		                if(row.status == "0" || row.status == "2"){
		                	var returnStr = deleteMessage;
		                }else{
		                	var returnStr = deleteMessage;
		                }
		                if(row.messageEnable=="已删除"){
		            		  $('td[field="id"]').hide();
		            		  return null;
		            	}	 
		                return returnStr;
		                
		         }}
	        ]]
	    });
}

function saveReply(){
	var replyText = $.trim($('#replyText').val());
	if(replyText==''){
		$.messager.alert('通知','回复不能为空！','info');
		return;
	}
    $('#fm').form('submit',{
        url: 'admin/message/saveReply',
        type:"POST",
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
        	$.messager.alert('通知','发布成功','info',function(){
        		$('#hf').dialog('close');
             	$('#dataGridTable').datagrid('reload');
        	}); 
        }
    });
}

//删除留言
function deleteMessage(id){
	$("#deleteReasonText").val("");
	//打开删除留言原因框
	$("#deleteReasonmessageId").val(id);
	$('#deleteReasonDialog').dialog("open");
}

//保存删除原因
function saveDeleteReason(){
	var deleteReasonText = $("#deleteReasonText").val();
	var id = $("#deleteReasonmessageId").val();
	if(deleteReasonText==''||deleteReasonText==' '){
		$.messager.alert('通知','回复不能为空！','info');
		return;
	}
	//是否确认删除
	$.messager.confirm('确定', '确定要删除吗?', function(r) {
        if (r) {
            $.ajax({
                url: 'admin/message/deleteMessage',
                type: 'POST',
                data: {id: id,deleteReasonText:deleteReasonText},
                success: function () {
                    $.messager.alert('提示', '删除成功!', 'info',function() {
                        $('#dataGridTable').datagrid('reload');
                        $('#deleteReasonDialog').dialog("close");
                    });
                },
                error: function (data) {
                    AjaxErrorHandler(data);
                }
            });
        }
    });
}

function checkMessage(type){
	var rows = $('#dataGridTable').datagrid('getSelections');
    if(rows.length==0){
 		$.messager.alert('提示','请选择留言！','info');
 		return;
 	}
    if (rows){
       $.messager.confirm('确认','是否确认审核这些数据?',function(r){
       if(r){
      	    for(var i=0;i<rows.length;i++){ 
	            $.ajax({
	               type:"POST",
	               url:'admin/message/checkMessage?type='+type+'&id='+rows[i].id,
	               success:function(msg){
	                    $('#dataGridTable').datagrid('reload');
	               }
	            });
           	}
            $.messager.alert('提示','审核成功！','info'); 
      	 }
      });
   
   }
}


function displayMessage(type){
	var name = "";
	if(type==0){
		name = "隐藏";
	}else{
		name = "显示";
	}
	var rows = $('#dataGridTable').datagrid('getSelections');
    if(rows.length==0){
 		$.messager.alert('提示','请选择留言！','info');
 		return;
 	}
	$.messager.confirm('确认','是否确认'+name+'这些数据?',function(r){
	       if(r){
	      	    for(var i=0;i<rows.length;i++){ 
		            $.ajax({
		               type:"POST",
		               url:'admin/message/displayMessage?type='+type+'&id='+rows[i].id,
		               success:function(msg){
		                    $('#dataGridTable').datagrid('reload');
		               }
		            });
	           	}
	            $.messager.alert('提示','留言'+name+'成功！','info'); 
	      	 }
	});
}

//留言详情
function messageDetail(id){
	 $('<div/>').dialog({   
   	    title: '留言详情',
   	    id:'hf',
   	    width: 1100,   
   	    height: 500,
   	    closed: false, 
   	    maximized:true,
   	    href: 'toMessageDetail?id='+id,   
   	    modal: true,
   	    buttons :'#bb' ,
			onClose : function() {
			$(this).dialog('destroy');
		}
   });
	
}

//打开主题关联窗口
function themeAssociation(id){
	 $('#createPaymentRequestDialog').dialog('center').dialog('open');
	 $("#createPaymentRequestDialog input[name='messageId']").val(id);
	  
	
		//查询当前留言所有相关主题
		$('#messageTheme').datagrid({
			url: 'admin/message/findMessageThemeByMessageId?messageId='+id,
			method: 'POST',
	        height: 250,
	        width: 500,
	        striped: true,
	        rowNumbers: true,
	        singleSelect: false,
	   //     toolbar: '#mainKeyWordId',
	        loadMsg: '程序处理中，请稍等...',
	        columns:[[
	        {field:'id',title:'操作', formatter: function(value,row){
	           var deleteMainText = '<a href="javascript:void(0);" onClick="deleteMainText(\''+value+'\');">删除</a>';
	            return deleteMainText;
	            }},
	         {field: 'mainText',title: '主题内容'}
	        ]]
	    });	
}

//根据themeId删除主题内容
function deleteMainText(id){
	$.messager.confirm('确定', '确定要删除吗?', function(r) {
        if (r) {
            $.ajax({
                url: 'admin/message/deleteMainText',
                type: 'POST',
                data: {id:id},
                success: function () {
                    $.messager.alert('提示', '删除成功!', 'info',function() {
                    	$('#messageTheme').datagrid('reload');
                        $('#dataGridTable').datagrid('reload');
                    });
                },
                error: function (data) {
                    AjaxErrorHandler(data);
                }
            });
        }
    });
}

//待审批评论留言
function commentMessagesApproval(){
    $('#dataGridTable').datagrid({
        url: 'admin/message/commentMessagesApproval',
        method: 'POST',
        fit: true,
        striped: true,
        rowNumbers: true,
        singleSelect: false,
        pagination: true,
        pageSize: 20,
        pageNumber:1,
        toolbar: '#dataGridTableButtons',
        loadMsg: '程序处理中，请稍等...',
        columns:[[
        	{field:'ck',title:'选择',checkbox:true,width:50,align:'center'},
//          {field: 'mainTextId',title: '主题关联Id'},
            {field: 'serialNumber',title:'留言编号'},
            {field: 'mainText',title: '主题关联'},
    //        {field: 'msgType',title: '信息类型'},
            {field: 'messageCategory',title: '类型'},
            {field: 'title',title: '标题',width:150,formatter:function(value,row){
            	return '<span style="cursor:pointer;color: #337ab7;"  onClick="messageDetail(\''+row.id+'\');">'+value+'</span>';
            }},
            {field: 'commentStr',title: '内容',width:340,formatter:function(value,row){
            	return '<span title="'+value+'" style="cursor:pointer;color: #337ab7;" onClick="messageDetail(\''+row.id+'\');">'+value+'</span>';
            }},
            {field: 'approvalStatus',title: '审核状态'},
            {field: 'replyStatus',title: '回复状态'},
            {field: 'deptName',title: '处办部门'},
            {field: 'personName',title: '提出人'},
            {field: 'phone',title: '手机号'},
            {field: 'ipAddress',title: 'IP地址'},
   //         {field: 'deptId',title: '处办部门'},
  //         {field: 'gradeValue',title: '评分'},
            {field: 'messageLike',title: '点赞数'},
            {field: 'commentNumber',title: '评论数'},
//            {field: 'orderStatus',title: '工单状态'},
//            {field: 'displayStatus',title: '显示状态'},
//            {field: 'messageEnable',title: '删除状态'},
            {field: 'status',title: '推送状态',formatter: function(value,row){
            		if(value == "0"){
            			return "未推送"
            		}else if(value == "1"){
            			return "已推送";
            		}else if(value == "2"){
            			return "推送失败";
            		}else{
            			return "已回复";
            		}
            	}
            },
            {field: 'createTimestamp',title: '创建时间'},
            {field: 'lastUpdateTimestamp',title: '修改时间'},
            {field: 'apprivalPersonName',title: '审核人'},
            {field:'id',title:'操作', formatter: function(value,row){
	            //    var themeAssociation = '<a href="javascript:void(0);" onClick="themeAssociation(\''+value+'\');">主题关联</a>';
	            //    var messageDetail = '<a href="javascript:void(0);" onClick="messageDetail(\''+value+'\');">留言详情</a>';
	                var deleteMessage = '<a href="javascript:void(0);" onClick="deleteMessage(\''+value+'\');">删除</a>';
	            //    var lookReview = '<a href="javascript:void(0);" onClick="lookReview(\''+value+'\');">查看评论</a>';
	           //     var sendToPsp = '<a href="javascript:void(0);" onClick="sendToPsp(\''+row.id+'\',\''+row.mainText+'\');">推送至公众平台</a>';
	                if(row.status == "0" || row.status == "2"){
	                	var returnStr = deleteMessage;
	                }else{
	                	var returnStr = deleteMessage;
	                }
                		 
	                return returnStr;
	                
	        }}
        ]]
    });
}

/*//提交主题关联
function createPaymentRequestDialogSubmit(){
	 var messageId = $("#createPaymentRequestDialog input[name='messageId']").val();
	 //主题id
	 var mainKeyWordId =  $("#mainKeyWordId").combotree('getValue');
	 $.ajax({
		   url: 'admin/message/saveTheme',
           type: 'GET',
           data: {messageId:messageId,themeId:mainKeyWordId},
           dataType:'json',
           success: function (data) {
        	   $.messager.alert("提示","关联成功！","info",function(){
        		   $('#createPaymentRequestDialog').dialog('close');
        	})
        	$('#dataGridTable').datagrid('reload');
           },
           error:function(data){
        	   AjaxErrorHandler(data);
           }
	 })
}*/

//加载关联主题信息
$(function(){
	$('#mainKeyWordId').combotree({
	        url:'admin/message/get_main_keyword_forcombo',
	        method:'GET',
	        onSelect: function (node) {
	            if (!$("#mainKeyWordId").combotree("tree").tree('isLeaf', node.target)) {
	                $('#mainKeyWordId').combotree("tree").tree('expand', node.target);
	                throw "Stop Event";
	            }
	            var messageId = $("#createPaymentRequestDialog input[name='messageId']").val();
	            var mainKeyWordId = node.id;
	            $.ajax({
	     		   url: 'admin/message/saveTheme',
	                type: 'GET',
	                data: {messageId:messageId,themeId:mainKeyWordId},
	                dataType:'json',
	                success: function (data) {
	             	   $('#messageTheme').datagrid('reload');
	                   $('#dataGridTable').datagrid('reload');
	                },
	                error:function(data){
	             	   AjaxErrorHandler(data);
	                }
	     	 })
	        },
	        onLoadSuccess: function () {
	            $("#mainKeyWordId").combotree('tree').tree('collapseAll');
	        }
	    });
})