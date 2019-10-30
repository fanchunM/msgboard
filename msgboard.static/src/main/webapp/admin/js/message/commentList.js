//查看评论详情
function getCommentDetail(personName, commentStr, approvalStatus, createTimestamp){
	$("#commentDetailDialog input[name='personName']").val(personName);
	$("#commentDetailDialog textarea[name='commentStr']").val(commentStr);
	$("#commentDetailDialog input[name='approvalStatus']").val(approvalStatus);
	$("#commentDetailDialog input[name='createTimestamp']").val(createTimestamp);
	$("#commentDetailDialog").dialog('open');
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

//审核评论
function checkComment(type){
	var rows = $('#dataGridTable').datagrid('getSelections');
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
	                    $('#dataGridTable').datagrid('reload');
	               }
	            });
           	}
            $.messager.alert('提示','审核成功！','info'); 
      	 }
      });
   }
}

$(function(){
	$('#dataGridTable').datagrid({
        url: 'admin/message/commentList',
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
            {field: 'serialNumber',title:'评论编号'},
       //     {field: 'msgType',title: '信息类型'},
            {field: 'personName',title: '提出人'},
            {field: 'title',title: '留言标题'},
            {field: 'commentStr',title: '内容',width:670, formatter: function (value) {
                return "<span title='" + value + "'>" + value + "</span>";
            }},
            {field: 'approvalStatus',title: '审核状态'},
            {field: 'createTimestamp',title: '创建时间'},
            {field:'id',title:'操作', formatter: function(value,row){
            	var CommentDetail = '<a href="javascript:void(0);" onClick="getCommentDetail(\''+row.personName+'\',\''+row.commentStr+'\',\''+row.approvalStatus+'\',\''+row.createTimestamp+'\');">查看</a>&nbsp;';
            	var relatedMessage = '<a href="javascript:void(0);" onClick="relatedMessage(\''+value+'\');">相关留言</a>';
            	var deleteComment = '&nbsp;<a href="javascript:void(0);" onClick="deleteComment(\''+value+'\');">删除</a>';
            	if(row.messageEnable=="已删除"){
          		  return CommentDetail+relatedMessage;
          	    }else{
            		return CommentDetail+relatedMessage+deleteComment;
            	}
            }},
        ]]
    });
})

//留言详情
function relatedMessage(id){
	 $('<div/>').dialog({   
   	    title: '相关留言',
   	    id:'hf',
   	    width: 1100,   
   	    height: 500,
   	    closed: false, 
   	    maximized:true,
   	    href: 'toMessageDetail?id='+id+'&messageType=1',   
   	    modal: true,
   	    buttons :'#bb' ,
			onClose : function() {
			$(this).dialog('destroy');
		}
   });
	
}