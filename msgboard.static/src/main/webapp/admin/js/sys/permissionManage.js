$(function(){
	//查询所有权限组名
	$('#dataGridTable').datagrid({
        url: 'sys/permissionList',
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
                var updateGruop = '<a href="javascript:void(0);" onClick="updateGruop(\''+value+'\',\''+row.name+'\');">修改权限</a>';
                var deleteGroup = '<a href="javascript:void(0);" onClick="deleteGroup(\''+value+'\');">删除</a>';
                var permissionUser = '<a href="javascript:void(0);" onClick="permissionUser(\''+value+'\');">用户</a>';
                return updateGruop+"&nbsp&nbsp"+deleteGroup+"&nbsp&nbsp"+permissionUser;
            }},
            {field: 'name',title: '权限组名'},
        ]]
    });
});



//添加权限组
function savePermissionGroup(){
	var fId = $.trim($('#fId').val());
	var group = $.trim($('#group').val());
	if(group==""){
		alert("请输入权限组名！");
		return;
	}
	$('#fm').form('submit',{
        url: 'sys/savePermissionGroup',
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

function deleteGroup(id){
    $.messager.confirm('确定', '确定要删除吗?', function(r) {
        if (r) {
            $.ajax({
                url: 'sys/deletePermissionGroup',
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

//跳转添加权限组页面
function addPermissionGroup(){
	 $('<div/>').dialog({   
   	    title: '添加权限组',
   	    id:'wd',
   	    width: 400,   
   	    height: 150,
   	    closed: false,   
   	    href: 'addPermissionGroup',   
   	    modal: true,
   	    buttons :'#bb' ,
			onClose : function() {
			$(this).dialog('destroy');
		}
   });
}

//跳转修改权限组页面
function updateGruop(id,name){
//	window.location.href ="http://gf.dev.mine.com:8080/base-web/permission_info_manager/MSG_WEB?userId="+id+"&userName="+name;
	window.open("sys_get_user_permission?userId="+id+"&userName="+name);
}

  //跳转权限组用户页面
  function permissionUser(id){
		 $('<div/>').dialog({   
	  	    title: '权限组用户管理',
	  	    id:'yh',
	  	    width: 550,   
	  	    height: 400,
	  	    closed: false,   
	  	    href: 'permissionUser?id='+id,   
	  	    modal: true,
	  	    buttons :'#bb' ,
				onClose : function() {
				$(this).dialog('destroy');
			}
	  });
		
}
  

