function getReminder(pageNum, pageSize){
	var userId = $("#userId").val();
	var pageSize = pageSize?pageSize:20;
	var reminderInfo = $("#reminderInfoContent");
	reminderInfo.empty();
	$.ajax({
		url:'notify/notifyList',
		type:'GET',
		data:{
			userId: userId,
			page: pageNum,
			rows: pageSize
		},
		success:function(data){ 
			var totalPage = Math.ceil(data.total/pageSize);
			var pageNum = parseInt(data.pageNum);
			var reminder = data.rows;
			createPageHTML(totalPage, pageNum, $('#reminderInfo .mn-page'), 'getReminder');
			for(var i =0; i<reminder.length;i++){
			     var messageStatus = "";
				 if(reminder[i].contentText=="您的留言已审核,请注意查收!"){
					messageStatus = '<span class="label label-info">已审核</span>';
				}else if(reminder[i].contentText=="您的留言已回复,请注意查收!"){
					messageStatus = '<span class="label label-success">已答复</span>';
				}else if(reminder[i].contentText=="您的留言被删除了,请注意查收!"){
					messageStatus = '<span class="label label-delete">已删除</span>';
				}   
				var title = '';
				var list='<div class="panel panel-primary">'
						+'<div class="panel-heading" style="padding: 5px 5px; border-color:#cdcdcd"><i class="fa fa-volume-up" aria-hidden="true"></i><span class="panel-title">系统提醒</span></div>'
						+'<div class="panel-body"><div>'+reminder[i].contentText+'<span style="float:right;font-size:80%">'+ messageStatus+'</span></div>';
				if(reminder[i].messageTitle.length>20){
					title = '<div style="overflow:hidden"><div style="float:left;color:#ccc">标题:'+reminder[i].messageTitle.substring(0,19)+'....'+'</div>';
				}else{
					title = '<div style="overflow:hidden"><div style="float:left;color:#ccc">标题:'+reminder[i].messageTitle+'</div>';
				}
				var createTimestampStr = (reminder[i].createTimestampStr==null)?'':reminder[i].createTimestampStr;
				var deleteReasonText = (reminder[i].deleteReasonText==null?'':reminder[i].deleteReasonText);
				var temp = '<div style="float:right">'
								+'<a href="messageDetail?id='+reminder[i].messageId+'" target="_blank">详情>></a>'
							+'</div></div>';
				if(deleteReasonText != ''){
					temp += '<div style="overflow:hidden"><div style="float:left;color:#ccc">删除原因：'+deleteReasonText+'</div></div>';
				}
				temp += '<div style="float:left;color:#ccc">'+createTimestampStr+'</div></div></div>';
				list += (title+temp);
				reminderInfo.append(list);		
			}
		},
		error:function(data){
			AjaxErrorHandler(data);
		}
	})
}
	$(function(){
		getReminder(1,null);
	})
	