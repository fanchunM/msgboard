$(function(){
	var id = $("#groupUserId").val();
	//查询权限组名的当前所有用户
	$('#users').datagrid({
		url: 'sys/getUsersByGroupId?id='+id,
		method: 'POST',
//        height: 250,
        width: 500,
        striped: true,
        rowNumbers: true,
        singleSelect: false,
        toolbar: '#addUser',
        loadMsg: '程序处理中，请稍等...',
        columns:[[
        {field:'id',title:'操作', formatter: function(value){
           var deleteUser = '<a href="javascript:void(0);" onClick="deleteUser(\''+value+'\');">删除</a>';
            return deleteUser;
            }},
         {field: 'userName',title: '用户名'},
         {field: 'chsName',title: '中文名'},
        ]]
    }); 
})





//删除用户 未完成
function deleteUser(userId){
	var id = $("#groupUserId").val();
    $.messager.confirm('确定', '确定要删除吗?', function(r) {
        if (r) {
            $.ajax({
                url: 'sys/deleteUsersByGroupId',
                type: 'POST',
                data: {id:id,userId:userId},
                success: function () {
                    $.messager.alert('提示', '删除成功!', 'info',function() {
                    	$('#users').datagrid('reload');
                    });
                },
                error: function (data) {
                    AjaxErrorHandler(data);
                }
            });
        }
    });
}

//向权限组添加用户
$(function(){
	var id = $("#groupUserId").val();
	$("#addUser").combobox({
        onSelect: function (record) {
        	var userId = record.id;
        	$.ajax({
                url: 'sys/addNewUser',
                type: 'POST',
                data: {id: id,userId:userId},
                success: function () {
                	$('#users').datagrid('reload');
                },
                error: function (data) {
                    AjaxErrorHandler(data);
                }
            });
        }
    })
	})
	
	//关闭窗口
	function closeWindow(){
	$('#yh').dialog('close');
}