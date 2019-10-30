//重置密码
function resetPwd(id){
    $.messager.confirm('确定', '确定重置密码吗?', function(r) {
        if (r) {
            $.ajax({
                url: 'admin/person/resetpwd',
                type: 'POST',
                data: {id: id,pwd:hex_md5('000000')},
                success: function () {
                    $.messager.alert('提示', '重置成功!', 'info',function() {
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

//查看
function look(id){
	$.ajax({
		url:'admin/person/get_one_person',
		type:'GET',
		data:{id:id},
		dataType:'json',
		success:function(data){
			$('#personDetailDialog input[name="userName"]').val(data.userName);
			$('#personDetailDialog input[name="nickName"]').val(data.nickName);
			$('#personDetailDialog input[name="phone"]').val(data.phone);
			$('#personDetailDialog input[name="gender"]').val(data.gender);
			$('#personDetailDialog input[name="messageNum"]').val(data.messageNum);
			$('#personDetailDialog input[name="createTime"]').val(data.createTimestamp);
		},
		error:function(data){
			AjaxErrorHandler(data);
		}
		
	})
	$('#personDetailDialog').dialog('open');
}

$(function(){
	//查询用户信息
	$('#dataGridTable').datagrid({
        url: 'admin/person/get_person_datagrid',
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
        	 var look = '<a href="javascript:void(0);" onClick="look(\''+value+'\');">查看</a>';
        	 var reset = '<a href="javascript:void(0);" onClick="resetPwd(\''+value+'\');">重置密码</a>';
             var returnStr = look+'&nbsp;'+reset;
             return returnStr;
            }},
            {field: 'userName',title: '用户名'},
            {field: 'phone',title: '手机号'},
//            {field: 'nickName',title: '昵称'},
            {field: 'gender',title: '性别'},
            {field: 'messageNum',title: '留言数量'},
            {field: 'weixinId',title: '微信账号ID'},
            {field: 'sueXingId',title: '苏e行账号ID'},
            {field: 'createTimestamp',title: '注册时间'},
            {field: 'registeredIP',title: '注册IP地址'},
            {field: 'recentLoginIP',title: '最近登录IP地址'},
        ]]
    });	
});