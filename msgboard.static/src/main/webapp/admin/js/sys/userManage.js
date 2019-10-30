$(function(){
	//查询用户信息
	$('#dataGridTable').datagrid({
        url: 'sys/getUserManageList',
        method: 'POST',
        toolbar: '#dataGridTableButtons',
        fit: true,
        striped: true,
        rowNumbers: true,
        singleSelect: false,
        pagination: true,
        pageSize: 20,
        loadMsg: '程序处理中，请稍等...',
        columns:[[
        {field:'id',title:'操作', formatter: function(value,row){
        	 var start = '<a href="javascript:void(0);" onClick="start(\''+row.uId+'\');">启用</a>';
             var prohibit = '<a href="javascript:void(0);" onClick="prohibit(\''+row.uId+'\');">禁用</a>';
             var returnStr = start+'&nbsp;'+prohibit;
             return returnStr;
            }},
            {field: 'userName',title: '用户名'},
            {field: 'chsName',title: '中文名'},
            {field: 'state',title: '状态'},
        ]]
    });	
});


//用户启用
function start(userId){
    $.messager.confirm('确定', '确定要启用吗?', function(r) {
        if (r) {
            $.ajax({
                url: 'sys/startUser',
                type: 'POST',
                data: {userId:userId},
                success: function () {
                    $.messager.alert('提示', '启用成功!', 'info',function() {
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



//用户禁用
function prohibit(userId){
    $.messager.confirm('确定', '确定要禁用吗?', function(r) {
        if (r) {
            $.ajax({
                url: 'sys/prohibitUser',
                type: 'POST',
                data: {userId:userId},
                success: function () {
                    $.messager.alert('提示', '禁用成功!', 'info',function() {
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