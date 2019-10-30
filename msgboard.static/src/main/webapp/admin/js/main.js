$(function(){
	//加载留言数量和审核数量
	setTimeoutunAnsweredMessageNums();
	uncheckedMessageNums();
	//根据用户权限加载桌面右下角留言未审核弹窗
	var PopupPrompt = document.getElementById('PopupPrompt');
	if(PopupPrompt!=null){
		//调用弹窗定时器
		setIntervalPopupPrompt();
	}
	//设置一个定时器用于加载未回复留言数量，每10秒刷新一次
	setInterval(function(){
		var page=1;
		var rows=20;
		$.ajax({
			url:'admin/message/unAnsweredMessageNums',
	        dataType:'json',
	        type:"POST",
	        date:{page:page,rows:rows},
	        success: function (data) {
	        	var nums=data.rows.length;
	            $("#hfNums").html(nums);
	         }
		});
	},10000);
	
	//设置一个定时器用于加载未审核留言数量，每10秒刷新一次
	setInterval(function(){
		var page=1;
		var rows=20;
		$.ajax({
			url:'admin/message/uncheckedMessageNums',
	        dataType:'json',
	        type:"POST",
	        date:{page:page,rows:rows},
	        success: function (data) {
	        	var nums = data.rows.length;
	            $("#shNums").html(nums);
	         }
		});
	},10000);
	
})

//页面加载时立即显示未回复留言数量(一次性定时器)
function setTimeoutunAnsweredMessageNums(){
	setTimeout(function(){
		var page=1;
		var rows=20;
		$.ajax({
			url:'admin/message/unAnsweredMessageNums',
	        dataType:'json',
	        type:"POST",
	        date:{page:page,rows:rows},
	        success: function (data) {
	        	var nums=data.rows.length;
	            $("#hfNums").html(nums);
	         }
		});
	},1)
}
//页面加载时立即显示未审核留言数量(一次性定时器)
function uncheckedMessageNums(){
	setTimeout(function(){
		var page=1;
		var rows=20;
		$.ajax({
			url:'admin/message/uncheckedMessageNums',
	        dataType:'json',
	        type:"POST",
	        date:{page:page,rows:rows},
	        success: function (data) {
	        	var nums = data.rows.length;
	            $("#shNums").html(nums);
	         }
		});
	},1);
}

//设置一个定时器用于加载系统收到新留言会以桌面右下角弹窗显示，每30秒刷新一次
function setIntervalPopupPrompt(){
	setInterval(function(){
		var page=1;
		var rows=20;
		$.ajax({
			url:'admin/message/messagePopUpPrompt',
	        dataType:'json',
	        type:"POST",
	        date:{page:page,rows:rows},
	        success: function (data) {
	        	var nums = data.rows.length;
	        	if(nums!=0){
	        		layer.open({
		        		  title: '消息提示',
		        		  offset: 'rb',
		        		  time: 8000,
		        		  shade:false,
		        		  content: '<div>您有'+nums+'条新的留言!</div>',
		        		  btn:['查 看']
		        		  ,yes: function(){
		        			  openUrlOrderPage('留言管理',null,null,'messageList?viewStatus=0');
		        		  },
		        		});     
	        	}
	        }
		});
	},30000);
} 

function openUrlOrderPage(attr1, attr2, statusStr, url) {
    showHeaderTitle(attr1, attr2);
    if (!url){
        $('#content_frame').attr('src', '/order_datagrid?statusStr=' + statusStr);
    } else{
    	$('#content_frame').attr('src', url);
    }
    	
}

function showHeaderTitle(attr1, attr2, attr3) {
    if (!attr3 && !attr2) {
        $('#breadcrumbs').html('<ul id="navigation" class="breadcrumb"><li><i class="ace-icon fa fa-home home-icon"></i></li>' +
            '<li class="active">' + attr1 + '</li></ul>');
    }
    else if (!attr3) {
        $('#breadcrumbs').html('<ul id="navigation" class="breadcrumb"><li><i class="ace-icon fa fa-home home-icon"></i></li>' +
            '<li class="active">' + attr1 + '</li><li class="active">' + attr2 + '</li></ul>');
    }
    else {
        $('#breadcrumbs').html('<ul id="navigation" class="breadcrumb"><li><i class="ace-icon fa fa-home home-icon"></i></li>' +
            '<li class="active">' + attr1 + '</li><li class="active">' + attr2 + '</li><li class="active">' + attr3 + '</li></ul>');
    }
}

function openUrlOrderPage02(attr1, attr2, attr3, url) {
    showHeaderTitle(attr1, attr2, attr3);
    $('#content_frame').attr('src', url);
}


function refreshcount() {
    $.ajax({
        url: 'psp/order_todo_datagrid',
        dataType: 'json',
        type: "GET",
        success: function (data) {
            if (data.length > 0) {
                $("#mytodo").html(data.length);
            } else {
                $("#mytodo").html("");
            }
        },
        error: function (data) {
            AjaxErrorHandler(data);
        }
    });
}
//注销用户
function LogOff() {
	var signOutUrl = '/msgboard.admin';
	if (confirm("您确认要注销当前登录账户吗？")) {
		try {
			document.execCommand("ClearAuthenticationCache");
		}
		catch (e) {
			//
		}
		finally {
			window.location.href = signOutUrl;
		}
	}
}

/*function LogOff() {
    if (confirm("您确认要注销吗？")) {
    	location.href="LogOff";
    }
}*/

//window.onload = function () {
//    $.ajax({
//        url: 'psp/order_todo_datagrid',
//        dataType: 'json',
//        type: "GET",
//        success: function (data) {
//            if (data.length > 0) {
//                $("#mytodo").html(data.length);
//            }
//        }
//    });
//
//    $.ajax({
//        url: 'psp/sys_msg_datagrid',
//        dataType: 'json',
//        type: "POST",
//        data:{
//            isPage: false
//        },
//        success: function (data) {
//            if (data.rows.length > 0) {
//                $("#sysMsg").html(data.rows.length);
//            }
//        }
//    });
//
//    $.ajax({
//        url: 'psp/warning_order_datagrid',
//        dataType: 'json',
//        type: "GET",
//        success: function (data) {
//            if (data.length > 0) {
//                $("#warning").html(data.length);
//            }
//        }
//    });
//}


