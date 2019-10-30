$(function(){
	$('#synchronizationThemeTable').datagrid({
	        url: 'findFatherSynchronizationTheme',
	        method: 'POST',
	        fit: true,
	        striped: true,
	        rowNumbers: true,
	        singleSelect: false,
	        pagination: true,
	        pageSize: 20,
	        toolbar: '#synchronizationThemeTableButtons',
	        loadMsg: '程序处理中，请稍等...',
	        columns:[[
	            {field: 'id',title: '操作',formatter:function(value,row){
	            	var openThemeDialog = '<a href="javascript:void(0);" onClick="openThemeDialog(\''+value+'\');">子主题</a>';
	            	var updateTheme = ' <a href="javascript:void(0);" onClick="openUpdateThemeDialog(\''+value+'\',\''+row.chsName+'\',1,\''+row.serialNumber+'\');">修改</a>';
	            	var deleteTheme = ' <a href="javascript:void(0);" onClick="deleteTheme(\''+value+'\');">删除</a>';
	            	return openThemeDialog+updateTheme+deleteTheme;
	            }},
	        	{field: 'chsName',title: '主题内容'},
	        	{field: 'serialNumber',title: '主题序号'},
//	            {field: 'parentThemeName',title: '父级主题',formatter:function(value){
//	            	if(value==null){
//	            		return "无";
//	            	}else{
//	            		return value;
//	            	}
//	            }},
	            
	        ]]
	    });
	});

//将base中的数据同步保存到本地数据库
function saveSynchronizationThemeInformation(){
	$.messager.confirm('确定', '确定要同步主题吗?', function(r) {
        if (r) {
            $.ajax({
                url: 'saveSynchronizationTheme',
                type: 'POST',
                success: function () {
                    $.messager.alert('提示', '同步成功!', 'info',function() {
                        $('#synchronizationThemeTable').datagrid('reload');
                    });
                },
                error: function (data) {
                    AjaxErrorHandler(data);
                }
            });
        }
    });
}

function openThemeDialog(value){
	$('#addThemeDialog').form('clear');
	$("#fatherId").val(value);
	$("#fatherThemeCombo").combobox("reload");
	$('#addThemeDialog').dialog('open');
	$('#sonDatagrid').datagrid({
		url: 'sys/get_son_datagrid_byfather',
		method: 'POST',
		queryParams:{fatherId:value},
		height:200,
        width: 350,
        striped: true,
        rowNumbers: true,
        singleSelect: false,
   //     toolbar: '#mainKeyWordId',
        loadMsg: '程序处理中，请稍等...',
        columns:[[
        {field:'id',title:'操作', formatter: function(value,row){
           var deleteTheme = '<a href="javascript:void(0);" onClick="deleteTheme(\''+value+'\');">删除</a>';
           var updateTheme = ' <a href="javascript:void(0);" onClick="openUpdateThemeDialog(\''+value+'\',\''+row.chsName+'\',2,\''+row.serialNumber+'\');">修改</a>'; 
           return deleteTheme+updateTheme;
            }},
         {field: 'chsName',title: '主题内容'},
         {field: 'serialNumber',title: '主题序号'},
        ]]
    });	
}

function openFatherThemeDialog(){
	$('#addFatherThemeDialog').form('clear');
	$('#addFatherThemeDialog').dialog('open');
}


function addTheme(type){
	if(type == 1){
		var chsName = $("#addFatherThemeDialog input[name='themeName']").val();
		var serialNumber = $("#addFatherThemeDialog input[name='serialNumber']").val();
		//正则主题序号是否是数字
		var regex=/^[0-9]*$/;
		if($.trim(chsName)==""){
			alert('请输入主题名称');
			return;
		}
		if($.trim(serialNumber)==""){
			alert('请输入主题序号');
			return;
		}else if(!regex.test(serialNumber)){
			alert('请输入合法的数字');
			return;
		}
		$.ajax({
			url:'sys/addTheme',
			type:'POST',
			dataType:'json',
			contentType:'application/json',
			data:JSON.stringify({
				chsName:chsName,
				serialNumber:serialNumber,
			}),
			success:function(data){
				$.messager.alert('提示','新建成功!','info',function(){
					$("#synchronizationThemeTable").datagrid("reload");
					$("#addFatherThemeDialog").dialog("close");
				});
			},
			error:function(data){
				AjaxErrorHandler(data);
			}
		})
	}
	if(type == 2){
		//正则主题序号是否是数字
		var regex=/^[0-9]*$/;
		var serialNumber = $("#addThemeDialog input[name='serialNumber']").val();
		var chsName = $("#addThemeDialog input[name='themeName']").val();
		var parentThemeId = $("#fatherId").val();
		if($.trim(chsName)==""){
			alert('请输入主题名称');
			return;
		}
		if($.trim(serialNumber)==""){
			alert('请输入主题序号');
			return;
		}else if(!regex.test(serialNumber)){
			alert('请输入合法的数字');
			return;
		}
		$.ajax({
			url:'sys/addTheme',
			type:'POST',
			dataType:'json',
			contentType:'application/json',
			data:JSON.stringify({
				chsName:chsName,
				parentThemeId:parentThemeId,
				serialNumber:serialNumber
			}),
			success:function(data){
				$.messager.alert('提示','新建成功!','info',function(){
					$("#sonDatagrid").datagrid("reload");
				});
			},
			error:function(data){
				AjaxErrorHandler(data);
			}
		})
	}

}
function deleteTheme(value){
	$.messager.alert("提示","是否要删除此条数据?","info",function(){
		$.ajax({
			url:'sys/delete_theme',
			type:'POST',
			data:{id:value},
			success:function(data){
				$('#synchronizationThemeTable').datagrid('reload');
				$('#sonDatagrid').datagrid('reload');
			},
			error:function(data){
				AjaxErrorHandler(data);
			}
		})
	})
}
function openUpdateThemeDialog(value,chsName,type,serialNumber){
	$("#updateThemeDialog input[name='typeTheme']").val(type);
	$("#updateThemeDialog input[name='themeId']").val(value);
	$("#updateThemeDialog input[name='themeName']").val(chsName);
	$("#updateThemeDialog input[name='serialNumber']").val(serialNumber);
	$("#updateThemeDialog").dialog("open");
}

function updateTheme(){
	var type = $("#updateThemeDialog input[name='typeTheme']").val();
	var themeId = $("#updateThemeDialog input[name='themeId']").val();
	var themeName = $("#updateThemeDialog input[name='themeName']").val();
	var serialNumber = $("#updateThemeDialog input[name='serialNumber']").val();
	var regex=/^[0-9]*$/;
	if($.trim(themeName) == ""){
		alert("请输入主题名称!");
		return;
	}else if($.trim(serialNumber)==""){
		alert('请输入主题序号');
		return;
	}else if(!regex.test(serialNumber)){
		alert('请输入合法的数字');
		return;
	}else{
		$.ajax({
			url:'sys/update_theme',
			type:'POST',
			contentType:'application/json',
			data:JSON.stringify({id:themeId,chsName:themeName,serialNumber:serialNumber}),
			dataType:'json',
			success:function(data){
				$.messager.alert("提示","修改成功!","info",function(){
					if(type == "1"){
						$('#synchronizationThemeTable').datagrid('reload');
						$("#updateThemeDialog").dialog("close");
					}else if(type == "2"){
						$("#sonDatagrid").datagrid("reload");
						$("#updateThemeDialog").dialog("close");
					}
				});
			},
			error:function(data){
				AjaxErrorHandler(data);
			}
		})
	}
}

