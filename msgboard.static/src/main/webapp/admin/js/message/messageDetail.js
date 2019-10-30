$(function(){
	var messageId = $("#fm input[name='messageId']").val();
	$.ajax({
		url : "admin/message/get_message_images",
		type : "GET",
		data : {messageId:messageId},
		success:function(data){
			var imageDiv = $("#imageDiv");
			for(var i =0;i<data.length;i++){
				var image = '<div style="position: relative;float:left;height:100px;width:100px;margin-bottom:10px;margin-right:15px">'
					+'<img src="resources/upload/'+data[i].address+'" style="width:100%;height:100%" onclick="javascript:window.open(this.src);">'
					+'</div>';
				imageDiv.append(image);
			}
			imageDiv.css("display","block");
		},
		error:function(data){
			AjaxErrorHandler(data);
		}
	});
	
	$('#commentDataGridTable').datagrid({
        url: 'admin/message/commentList?messageId='+$('#fm input[name=messageId]').val(),
        method: 'POST',
        height: 255,
        striped: true,
        rowNumbers: true,
        singleSelect: false,
        pagination: true,
        pageSize: 5,
        pageList: [5,10,15,20],
        toolbar: '#commentDataGridTableButtons',
        loadMsg: '程序处理中，请稍等...',
        columns:[[
        	{field:'ck',title:'选择',checkbox:true,width:50,align:'center'},
            {field:'id',title:'操作', formatter: function(value,row){
            	return '<a href="javascript:void(0);" onClick="deleteComment(\''+value+'\');">删除</a>  '+
            	'<a href="javascript:void(0);" onClick="getCommentDetail(\''+row.personName+'\',\''+row.commentStr+'\',\''+row.approvalStatus+'\',\''+row.createTimestamp+'\');">查看</a>  ';
            }},
            {field: 'serialNumber',title:'评论编号'},
            //     {field: 'msgType',title: '信息类型'},
                 {field: 'personName',title: '提出人'},
                 {field: 'commentStr',title: '内容',width:200},
                 {field: 'approvalStatus',title: '审核状态'},
                 {field: 'createTimestamp',title: '创建时间'},
        ]]
    });
	
	// 设置审批按钮
	setApprovalBtns();
})

// 设置审批按钮
function setApprovalBtns() {
	$('#agreeButton').linkbutton();
	$('#disAgreeButton').linkbutton();
	$('#agreeButton').removeClass('btn-disable');
	$('#disAgreeButton').removeClass('btn-disable');
	if($('#approvalStatusValue').val() == 'true') {
		$('#agreeButton').linkbutton('disable');
		$('#disAgreeButton').linkbutton('enable');
		$('#agreeButton').addClass('btn-disable');
	} else if($('#approvalStatusValue').val() == 'false'){
		$('#agreeButton').linkbutton('enable');
		$('#disAgreeButton').linkbutton('disable');
		$('#disAgreeButton').addClass('btn-disable');
	} else if($('#approvalStatusValue').val() == '未处理'){
		$('#agreeButton').linkbutton('enable');
		$('#disAgreeButton').linkbutton('enable');
	}
}

//查看评论详情
function getCommentDetail(personName,commentStr,approvalStatus,createTimestamp){
	$("#commentDetailDialog").dialog("open");
//	$("#approvalStatus").val(approvalStatus);
	$("#personName").val(personName);
	$("#commentStr").val(commentStr);
	$("#createTimestamp").val(createTimestamp);
}

//保存备注
function saveDeptName(){
	var messageId = $("#fm input[name='messageId']").val();
	var deptName = $("#fm input[name='deptName']").val();
//	if($.trim(deptName)==""){
//		 $.messager.alert('提示','请填写备注！','info'); 
//		 return;
//	}
	if(deptName.length >= 15){
		$.messager.alert('提示','字数不超过14个字！','info',function(){
			return;
		});
	}else{
		$.ajax({
	           type:"POST",
	           url:'admin/message/saveDeptName',
	           data:{
	        	   id:messageId,
	        	   deptName:deptName
	           },
	           success:function(msg){
	                $.messager.alert('提示','保存成功！','info',function(){
	                	$('#dataGridTable').datagrid('reload');
	                	$("#messageDtoDeptName").html(deptName);
	                }); 
	           }
	     });
	}
}
//审核留言
function checkMessageDetail(type){
	var messageId = $("#fm input[name='messageId']").val();
       $.messager.confirm('确认','是否确认审核这些数据?',function(r){
    	   if(r){
	            $.ajax({
	               type:"POST",
	               url:'admin/message/checkMessage?type='+type+'&id='+messageId,
	               success:function(msg){
	                    $('#dataGridTable').datagrid('reload');
	                    if(type==1) {//不同意
	                    	$('#approvalStatusValue').val('false');
	                    } else if(type==2){//同意
	                    	$('#approvalStatusValue').val('true');
	                    }
	                    setApprovalBtns();
	               }
	            });
            $.messager.alert('提示','审核成功！','info'); 
      	  }
      });
}

// 审核评论
function checkComment(type){
	var rows = $('#commentDataGridTable').datagrid('getSelections');
    if(rows.length==0){
 		$.messager.alert('提示','请选择评论！','info');
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
	                    $('#commentDataGridTable').datagrid('reload');
	               }
	            });
           	}
            $.messager.alert('提示','审核成功！','info'); 
      	 }
      });
   }
}



//删除评论
function deleteComment(id){
    $.messager.confirm('确定', '确定要删除吗?', function(r) {
        if (r) {
            $.ajax({
                url: 'admin/message/deleteComment',
                type: 'POST',
                data: {id: id},
                success: function () {
                    $.messager.alert('提示', '删除成功!', 'info',function() {
                        $('#commentDataGridTable').datagrid('reload');
                    });
                },
                error: function (data) {
                    AjaxErrorHandler(data);
                }
            });
        }
    });
}

//添加快速回复
$(function(){
	$("#quickReply").combobox({
        onSelect: function (record) {
            $("#replyText").val(record.text);
        }
    })
	})
	
	
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
	         {field: 'themeName',title: '主题内容'}
	        ]]
	    });	
}

//根据themeId删除主题内容
function deleteMainText(id){
	var messageId = $("#fm input[name='messageId']").val();
	$.messager.confirm('确定', '确定要删除吗?', function(r) {
        if (r) {
            $.ajax({
                url: 'admin/message/deleteMainText',
                type: 'POST',
                data: {id:id,messageId:messageId},
                success: function (data) {
                    $.messager.alert('提示', '删除成功!', 'info',function() {
                    	$('#messageTheme').datagrid('reload');
                        $('#dataGridTable').datagrid('reload');
                    });
                    $("#themeStr").html(data.themeStr);
                },
                error: function (data) {
                    AjaxErrorHandler(data);
                }
            });
        }
    });
}

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
	                   $("#themeStr").html(data.themeStr);
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


//推送至公众平台
function sendToPsp(id){
	$.ajax({
		url:'admin/message/findMessageThemeByMessageId',
		type:'POST',
		data:{messageId:id},
		success:function(data){
			debugger;
			if(data==""){
				alert("请先关联主题分类!");
				return;
			}
			$("#messageIdInput").val(id);
			$('#deptDialog').dialog("open");
		}
	});
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
