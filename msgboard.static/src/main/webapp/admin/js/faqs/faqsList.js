$(function(){
	$('#dataGridTable').datagrid({
        url: 'admin/faqs/faqsList',
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
            	var details = '<a href="javascript:void(0);" onClick="details(\''+value+'\');">查看详情</a>';
            	var themeAssociation = '<a href="javascript:void(0);" onClick="themeAssociation(\''+value+'\');">主题关联</a>';
                var updateFaqs = '<a href="javascript:void(0);" onClick="updateFaqs(\''+value+'\');">修改</a>';
                var deleteFaqs = '<a href="javascript:void(0);" onClick="deleteFaqs(\''+value+'\');">删除</a>';
                return details+'&nbsp;'+themeAssociation+'&nbsp;'+updateFaqs+"&nbsp&nbsp"+deleteFaqs;
            }},
            {field: 'mainText',title: '主题关联'},
            {field: 'question',title: '问题',width:200},
       //     {field: 'answers',title: '答案',width:340},
            /*{field: 'hits',title: '点击量'},*/
            {field: 'faqsStatus',title: '审核状态'},
            {field: 'apprivalPersonName',title: '审核人'},
            {field: 'personName',title: '创建人'},
            {field: 'createTimestamp',title: '创建时间'},
            {field: 'lastUpdateTimestamp',title: '修改时间'}
        ]]
    });
	
	$('#dataGridTableCheck').datagrid({
        url: 'admin/faqs/faqsList',
        method: 'POST',
        fit: true,
        striped: true,
        rowNumbers: true,
        singleSelect: false,
        pagination: true,
        pageSize: 20,
        toolbar: '#dataGridTableButtonsCheck',
        loadMsg: '程序处理中，请稍等...',
        columns:[[
        	{field:'ck',title:'选择',checkbox:true,width:50,align:'center'},
        	{field:'id',title:'操作', formatter: function(value,row){
            	var details = '<a href="javascript:void(0);" onClick="details(\''+value+'\');">查看详情</a>';
                return details;
            }},
        	{field: 'mainText',title: '主题关联'},
            {field: 'question',title: '问题',width:200},
        //    {field: 'answers',title: '答案',width:340},
            /*{field: 'hits',title: '点击量'},*/
            {field: 'faqsStatus',title: '审核状态'},
            {field: 'apprivalPersonName',title: '审核人'},
            {field: 'personName',title: '创建人'},
            {field: 'createTimestamp',title: '创建时间'},
            {field: 'lastUpdateTimestamp',title: '修改时间'}
        ]]
    });
});

function searchByQuerys(){
    $('#dataGridTable').datagrid('load',{
    	keyword: $('#keyword').val(),
    	faqsStatus: $('#faqsStatus').combobox('getValue')
    });
}

function searchByQuerysCheck(){
    $('#dataGridTableCheck').datagrid('load',{
    	keyword: $('#keyword').val(),
    	faqsStatus: $('#faqsStatus').combobox('getValue')
    });
}

function saveFaqs(){
	var fId = $.trim($('#fId').val());
	var px = $.trim($('#px').val());
	var loginUserId = $.trim($('#loginUserId').val());
    $('#addFaqsForm').form('submit',{
        url: 'admin/faqs/saveFaqs',
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
        }
    });
}

function deleteFaqs(id){
    $.messager.confirm('确定', '确定要删除吗?', function(r) {
        if (r) {
            $.ajax({
                url: 'admin/faqs/deleteFaqs',
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

function checkFaqs(type){
	var rows = $('#dataGridTableCheck').datagrid('getSelections');
    if(rows.length==0){
 		$.messager.alert('提示','请选择问答！','info');
 		return;
 	}
    if (rows){
       $.messager.confirm('确认','是否确认审核这些数据?',function(r){
       if(r){
      	    for(var i=0;i<rows.length;i++){ 
	            $.ajax({
	               type:"POST",
	               url:'admin/faqs/checkFaqs?type='+type+'&id='+rows[i].id,
	               success:function(msg){
	                    $('#dataGridTableCheck').datagrid('reload');
	               }
	            });
           	}
            $.messager.alert('提示','审核成功！','info'); 
      	 }
      });
   }
}


function addFaqs(){
	 $('<div/>').dialog({   
   	    title: '添加问答',
   	    id:'wd',
   	    width: 1000,   
   	    height: 500,
   	    closed: false,   
   	    href: 'toAddFaqs',   
   	    modal: true,
   	    buttons :'#bb' ,
   	        onLoad:function(){
   	        	var options = { 
   		  			   width : '95%',
   		  			   height:'300px',
   		  		       themeType : 'default',    
   		  		       allowFileManager:true,  
   		  		       allowFileManager:false, 
   		  		       allowMediaUpload:true, 
   		  		       allowFlashUpload:false,  
   		  		       uploadJson:'admin/news/upload.do;jsessionid='+$('#sessionUID').val(),          
   		  		       fileManagerJson:'admin/news/fileManager.do',    
   		  		       afterCreate:function(){ //加载完成后改变皮肤  
   		  		    	 var color = $('.panel-header').css('background-color');  
   	   	  		           $('.ke-toolbar').css('background-color',color);
   		  		       
   		  		          },
   		  		       afterBlur: function(){
   		  		    	   this.sync();
   		  		    	   }    
   		  		         
   		  		   };  
   	        	KindEditor.create('textarea[name="answers"]',options); 
   	        },
   	        onBeforeClose:function() {
   	        	KindEditor.remove('textarea[name="answers"]'); 
 		   },
			onClose : function() {
				$(this).dialog('destroy');
			}
   });
}

function updateFaqs(id){
	 $('<div/>').dialog({   
  	    title: '修改问答',
  	    id:'wd',
  	    width: 1000,
  	    height: 500,
  	    closed: false,   
  	    href: 'toUpdateFaqs?id='+id,   
  	    modal: true,
  	    buttons :'#bb' ,
  	    onLoad:function(){
  	    	var options = { 
		  			   width : '95%',
		  			   height:'300px',
		  		       themeType : 'default',    
		  		       allowFileManager:true,  
		  		       allowFileManager:false, 
		  		       allowMediaUpload:true, 
		  		       allowFlashUpload:false,  
		  		       uploadJson:'admin/news/upload.do;jsessionid='+$('#sessionUID').val(),          
		  		       fileManagerJson:'admin/news/fileManager.do',    
		  		       afterCreate:function(){ //加载完成后改变皮肤  
		  		    	 var color = $('.panel-header').css('background-color');  
	   	  		           $('.ke-toolbar').css('background-color',color);
		  		       
		  		          },
		  		       afterBlur: function(){
		  		    	   this.sync();
		  		    	   }    
		  		         
		  		   };  
	        KindEditor.create('textarea[name="answers"]',options);  
	    },
	    onBeforeClose:function() {
	        KindEditor.remove('textarea[name="answers"]');  
	    },
		onClose : function() {
			$(this).dialog('destroy');
		}
  });
}
//查看便民问答详情
function details(id){
	 $('<div/>').dialog({   
	   	    title: '查看详情',
	   	    id:'wd',
	   	    width: 1000,   
	   	    height: 500,
	   	    closed: false,   
	   	    href: 'toDetails?id='+id,   
	   	    modal: true,
	   	    buttons :'#bb' ,
	   	        onLoad:function(){
	   	        	var options = { 
	   		  			   width : '95%',
	   		  			   height:'300px',
	   		  		       themeType : 'default',    
	   		  		       allowFileManager:true,  
	   		  		       allowFileManager:false, 
	   		  		       allowMediaUpload:true, 
	   		  		       allowFlashUpload:false,  
	   		  		       uploadJson:'admin/news/upload.do;jsessionid='+$('#sessionUID').val(),          
	   		  		       fileManagerJson:'admin/news/fileManager.do',    
	   		  		       afterCreate:function(){ //加载完成后改变皮肤  
	   		  		    	 var color = $('.panel-header').css('background-color');  
	   	   	  		           $('.ke-toolbar').css('background-color',color);
	   		  		       
	   		  		          },
	   		  		       afterBlur: function(){
	   		  		    	   this.sync();
	   		  		    	   }    
	   		  		         
	   		  		   };  
	   	        	KindEditor.create('textarea[name="answers"]',options); 
	   	        },
	   	        onBeforeClose:function() {
	   	        	KindEditor.remove('textarea[name="answers"]'); 
	 		   },
				onClose : function() {
					$(this).dialog('destroy');
				}
	   });
}


//打开主题关联窗口
function themeAssociation(id){
	 $('#createPaymentRequestDialog').dialog('center').dialog('open');
	 $("#createPaymentRequestDialog input[name='faqsId']").val(id);
	//查询当前留言所有相关主题
		$('#faqsTheme').datagrid({
			url: 'admin/faqs/findFaqsThemeByFaqsId?faqsId='+id,
			method: 'POST',
	        height: 250,
	        width: 500,
	        striped: true,
	        rowNumbers: true,
	        singleSelect: false,
	        //toolbar: '#mainKeyWordId',
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
	$.messager.confirm('确定', '确定要删除吗?', function(r) {
        if (r) {
            $.ajax({
                url: 'admin/faqs/deleteMainText',
                type: 'POST',
                data: {id:id},
                success: function () {
                    $.messager.alert('提示', '删除成功!', 'info',function() {
                    	$('#faqsTheme').datagrid('reload');
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


//加载关联主题信息
$(function(){
	$('#mainKeyWordId').combotree({
	        url:'admin/faqs/get_main_keyword_forcombo',
	        method:'GET',
	        onSelect: function (node) {
	            if (!$("#mainKeyWordId").combotree("tree").tree('isLeaf', node.target)) {
	                $('#mainKeyWordId').combotree("tree").tree('expand', node.target);
	                throw "Stop Event";
	            }
	         var faqsId = $("#createPaymentRequestDialog input[name='faqsId']").val();
	       	 //主题id
	       	 var mainKeyWordId = node.id;
	       	 $.ajax({
	       		   url: 'admin/faqs/saveTheme',
	                  type: 'GET',
	                  data: {faqsId:faqsId,themeId:mainKeyWordId},
	                  dataType:'json',
	                  success: function (data) {
	               	   		$('#faqsTheme').datagrid('reload');
	                        $('#dataGridTable').datagrid('reload');
	               	
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