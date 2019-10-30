$(function(){
	$('#dataGridTable').datagrid({
	        url: 'sys/quickReplyList',
	        method: 'POST',
	        fit: true,
	        striped: true,
	        rowNumbers: true,
	        singleSelect: false,
	        pagination: true,
	        pageSize: 20,
	        toolbar: '#dataGridTableButtons',
	        loadMsg: '程序处理中，请稍等...',
	        columns:[[
	            {field:'id',title:'操作', formatter: function(value,row){
	                var updatereply = '<a href="javascript:void(0);" onClick="updatereply(\''+value+'\');">修改</a>';
	                var deletereply = '<a href="javascript:void(0);" onClick="deletereply(\''+value+'\');">删除</a>';
	                var returnStr = updatereply+'&nbsp;'+deletereply;
	                return returnStr;
	            }},
	            {field: 'value1',title: '回复标题'},
	            {field: 'text',title: '回复内容'},
	        ]]
	    });
	});



function deletereply(id){
    $.messager.confirm('确定', '确定要删除吗?', function(r) {
        if (r) {
            $.ajax({
                url: 'sys/deleteQuickReply',
                type: 'POST',
                data: {id:id},
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



function updatereply(id){
	$('<div/>').dialog({   
  	    title: '修改快速回复',
  	    id:'wd',
  	    width: 650,   
  	    height: 480,
  	    closed: false,   
  	    href: 'updatereply?id='+id,   
  	    modal: true,
  	    buttons :'#bb' ,
			onClose : function() {
			$(this).dialog('destroy');
		}
  });
}

function addQucikReply(){
	 $('<div/>').dialog({   
  	    title: '添加快速回复',
  	    id:'wd',
  	    width: 650,   
  	    height: 480,
  	    closed: false,   
  	    href: 'addReply',   
  	    modal: true,
  	    buttons :'#bb' ,
			onClose : function() {
			$(this).dialog('destroy');
		}
  });
}

function saveReply(){
	var fId = $.trim($('#fId').val());
	var engName = $.trim($('#engName').val());
	var chsName = $.trim($('#chsName').val());
	if(engName==""){
		alert("请填写回复标题！");
		return;
	}
	if(chsName==""){
		alert("请填写回复内容！");
		return;
	}
	$('#fm').form('submit',{
        url: 'sys/saveQuickReply',
        type:"POST",
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
        	if(fId==''){
        		$.messager.alert('通知','添加成功','info',function(){
            		$('#wd').dialog('close');
                 	$('#dataGridTable').datagrid('reload');
            	}); 
        	}else{
        		$.messager.alert('通知','修改成功','info',function(){
            		$('#wd').dialog('close');
                 	$('#dataGridTable').datagrid('reload');
            	}); 
        	}
        },
        error:function(result){
        	AjaxErrorHandler(result);
        }
    });
}


