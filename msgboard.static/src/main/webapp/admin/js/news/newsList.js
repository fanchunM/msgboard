$(function(){
	$('#dataGridTable').datagrid({
	        url: 'admin/news/newsList',
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
	                 
	                var updateNews = '<a href="javascript:void(0);" onClick="updateNews(\''+value+'\');">修改</a>';
	                var deleteNews = '<a href="javascript:void(0);" onClick="deleteNews(\''+value+'\');">删除</a>';
	                
	                var returnStr = updateNews+'&nbsp;'+deleteNews;
	                return returnStr;
	            }},
	            {field: 'title',title: '新闻标题'},
	            {field: 'subTitle',title: '新闻副标题'},
	            {field: 'author',title: '作者'},
	            {field: 'deptName',title: '部门'},
	            {field: 'publishTime',title: '发布日期'},
//	            {field: 'newsContent',title: '新闻内容'},
	            {field: 'newsDesc',title: '新闻介绍'},
	            {field: 'newsStatus',title: '审核状态'},
	            {field: 'personName',title: '创建人'},
	            {field: 'apprivalPersonName',title: '审核人'},
	            {field: 'linkURL',title: '外链'},
	            {field: 'createTimestamp',title: '创建时间'},
	            {field: 'lastUpdateTimestamp',title: '修改时间'}
	        ]]
	    });
	$('#dataGridTableCheck').datagrid({
        url: 'admin/news/newsList',
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
                var lookNews = '<a  href="'+row.frontUrl+'newsDetail?id='+value+'&state='+'1'+'" target="_blank">查看新闻</a>';
                var returnStr = lookNews;
                return returnStr;
            }},
            {field: 'title',title: '新闻标题'},
            {field: 'subTitle',title: '新闻副标题'},
            {field: 'author',title: '作者'},
            {field: 'deptName',title: '部门'},
            {field: 'publishTime',title: '发布日期'},
    //      {field: 'newsContent',title: '新闻内容'},
            {field: 'newsDesc',title: '新闻介绍'},
            {field: 'newsStatus',title: '审核状态'},
            {field: 'personName',title: '创建人'},
            {field: 'apprivalPersonName',title: '审核人'},
            {field: 'linkURL',title: '外链'},
            {field: 'createTimestamp',title: '创建时间'},
            {field: 'lastUpdateTimestamp',title: '修改时间'}
        ]]
    });
	
	$('#picdg').datagrid({  
 	    title:'图片列表',  
 	    iconCls:'icon-edit',//图标  
 	    //fitColumns:true,//自适应
 	    //fit: true,//自动大小 
 	    width: '1000',  
 	    //height: 500,  
 	    nowrap: false,  
 	    striped: true,  
 	    border: true,  
 	    collapsible:false,//是否可折叠的  
 	    url:'admin/news/getPics',   
 	    //sortName: 'code',  
 	    //sortOrder: 'desc',  
 	    remoteSort:false,   
 	    idField:'id', //设置行ID为对象的UID 
 	    singleSelect:false,//是否单选  
 	    pagination:false,//分页控件  
 	    rownumbers:true,//行号  
 	    loadMsg:'数据装载中......',
 	    toolbar: '#pictb',
 	    columns:[[  
 	              {field:'imageName',title:'图片名称',width:350,align:'center' 
 	              },
 	              /*{field:'createTimestamp',title:'创建时间',width:150,align:'center' 
 	              },*/
 	            {field:'id',title:'操作',width:200,align:'center', 
 	             	formatter:function(value,row,index){
 	             		var btn1 = '<a style="color:blue" class="editcls" onclick="lookPic(\''+value+'\')" href="javascript:void(0)"></a>'; 
 	             		var btn2 = '<a style="color:blue" class="editcls2" onclick="delPic(\''+value+'\')" href="javascript:void(0)"></a>'; 
	             			return btn1+btn2;
 	               	}	 
 	             }
 	         ]],  
 	     onLoadSuccess:function(){  
 	        $('#picdg').datagrid('uncheckAll'); 
 	    	var s= $('#picdg').datagrid('getPanel');
 			var rows = s.find('tr.datagrid-row td[field!=ck]');
 			rows.unbind('click').bind('click',function(e){
 				return false;
 			});
 			$('.editcls').linkbutton({text:'查看图片',plain:true,iconCls:'icon-search'});
 			$('.editcls2').linkbutton({text:'删除',plain:true,iconCls:'icon-cancel'});
 	    } ,
 	    checkOnSelect : false
 	});	
	
});

function searchByQuerys(){
    $('#dataGridTable').datagrid('load',{
    	keyword: $('#keyword').val(),
    	newsStatus: $('#newsStatus').combobox('getValue')
    })
}
function searchByQuerysCheck(){
    $('#dataGridTableCheck').datagrid('load',{
    	keyword: $('#keyword').val(),
    	newsStatus: $('#newsStatus').combobox('getValue')
    })
}

function deleteNews(id){
    $.messager.confirm('确定', '确定要删除吗?', function(r) {
        if (r) {
            $.ajax({
                url: 'admin/news/deleteNews',
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

function checkNews(type){
	var rows = $('#dataGridTableCheck').datagrid('getSelections');
    if(rows.length==0){
 		$.messager.alert('提示','请选择新闻！','info');
 		return;
 	}
    if (rows){
       $.messager.confirm('确认','是否确认审核这些数据?',function(r){
       if(r){
      	    for(var i=0;i<rows.length;i++){ 
	            $.ajax({
	               type:"POST",
	               url:'admin/news/checkNews?type='+type+'&id='+rows[i].id,
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

function addTextNews(){
	$('<div/>').dialog({   
	    title: '创建文本新闻',
	    id:'dd',
	   	    width: 900,   
	   	    height: 600,
	   	    closed: false,  
	    href: 'toAddTextNews',   
	    modal: true,
   	    buttons :'#bb' ,
   	    //maximizable:true,
   		maximized:true,
	    onLoad:function() {
	    	 var options = { 
	  			   width : '100%',
	  			   height:'520px',
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
	    	 neditor= KindEditor.create('textarea[name="newsContent"]',options); 
	    },
	    onBeforeClose:function() {
	    	KindEditor.remove('textarea[name="newsContent"]');
		},
		onClose : function() {
			$(this).dialog('destroy');
		}
	}); 
}

function addPicNews(){
	$('<div/>').dialog({   
   	    title: '创建图片新闻',
   	    id:'dd',
   	    width: 650,   
   	    height: 480,
   	    closed: false,   
   	    href: 'toAddPicNews',   
   	    modal: true,
   	    maximized:true,
   	    buttons :'#bb' ,
			onClose : function() {
			$(this).dialog('destroy');
		}
   });
}

function addLinkNews(){
	$('<div/>').dialog({   
   	    title: '创建外链新闻',
   	    id:'dd',
   	    width: 650,   
   	    height: 480,
   	    closed: false,   
   	    href: 'toAddLinkNews',   
   	    modal: true,
   	    maximized:true,
   	    buttons :'#bb' ,
			onClose : function() {
			$(this).dialog('destroy');
		}
   });
}

function saveNews(){
	
	var nId = $.trim($('#nId').val());
	
    $('#fm').form('submit',{
        url: 'admin/news/saveNews',
        type:"POST",
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
        	if(nId==''){
        		$.messager.alert('通知','添加成功','info',function(){
        			//alert('添加成功');
            		$('#dd').dialog('close');
                 	$('#dataGridTable').datagrid('reload');
            	}); 
        	}else{
        		$.messager.alert('通知','修改成功','info',function(){
        		//	alert('修改成功');
            		$('#dd').dialog('close');
                 	$('#dataGridTable').datagrid('reload');
            	}); 
        	}
        }
    });
}
//var neditor = null;
function updateNews(id){
	$('<div/>').dialog({   
		title: '修改新闻',
		id:'dd',
		width: 650,   
		height: 480,
		closed: false,   
		href: 'toUpdateNews?id='+id,   
		modal: true,
		maximized:true,
		buttons :'#bb' ,
		onLoad:function() {
	    	 var options = { 
	    		   width : '100%',
		  		   height:'520px',
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
	    	 neditor= KindEditor.create('textarea[name="newsContent"]',options); 
	    },
	    onBeforeClose:function() {
	    	KindEditor.remove('textarea[name="newsContent"]');
		},
		onClose : function() {
			$(this).dialog('destroy');
		}
	});
}

function addPic(){
    $('<div/>').dialog({   
   	    title: '添加图片',
   	    id:'pp',
   	    width: 400,   
   	    height: 150,
   	    closed: false,   
   	    href: 'toAddPic',   
   	    modal: true,
   	    buttons :'#bb' ,
			onClose : function() {
				$(this).dialog('destroy');
			}
   });   
}

function lookPic(id){
	 $('<div/>').dialog({   
	    title: '查看图片',
	    id:'pp',
	    width: 600,   
	    height: 450,
	    closed: false,   
	    href: 'lookNewsPic?id='+id,   
	    modal: true,
	    buttons :'#bb' ,
		onClose : function() {
			$(this).dialog('destroy');
		}
});   
}

function delPic(id){
	 $.messager.confirm('确认','是否删除该图片?',function(r){
   	 if (r){
  	         $.ajax({
  	            type:"POST",
  	            url:'admin/news/delPic?id='+id,
  	            success:function(msg){
  	            	//var result = eval('('+msg+')');
  	            $.messager.alert('通知','删除成功！','info');
       		 	$('#picdg').datagrid('reload');
  	            }
  	         });
   	 }
   });
}
function savePic(){
    $('#form1').form('submit',{
        url: 'admin/news/uploadImage',
        type:"POST",
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            if(result=='1'){
            	//$.messager.alert('通知','请选择上传图片','info');
            	alert('请选择上传图片');
            }else {
            	//$.messager.alert('通知','图片添加成功','info');
            	alert('图片添加成功');
            		$('#picdg').datagrid('reload');
                  	 $('#pp').dialog('close');
            }
        }
    });
}


