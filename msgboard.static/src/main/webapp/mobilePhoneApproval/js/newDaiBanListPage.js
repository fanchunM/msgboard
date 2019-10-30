layui.config({
	base : "js/"
}).use(['form','layer','jquery','element'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		$ = layui.jquery;
	$(".rtime").each(function(){
		var timeStr = $(this).html();
	    $(this).html(timeStr.substring(0,22).replace('T'," "));
	});
	
	var element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
	var forwardStr={"35":"getFWNGPage","56":"getFWNGPage","57":"getFWNGPage","71":"getFWNGPage","76":"getFWNGPage","122":"getFWNGPage","131":"getFWNGPage"
				,"113":"getNoticeSendPage","114":"getNoticeSendPage","112":"getNoticeSendPage","110":"getNoticeSendPage","111":"getNoticeSendPage","115":"getNoticeSendPage","136":"getNoticeSendPage"
				,"116":"getNoticeRecivePage","117":"getNoticeRecivePage","118":"getNoticeRecivePage","119":"getNoticeRecivePage","120":"getNoticeRecivePage","121":"getNoticeRecivePage","137":"getNoticeRecivePage"
				,"34":"getReciveMsgPage","58":"getReciveMsgPage","59":"getReciveMsgPage","60":"getReciveMsgPage","72":"getReciveMsgPage","75":"getReciveMsgPage","84":"getReciveMsgPage","132":"getReciveMsgPage"
				,"32":"getWorkConnectRecivePage","33":"getWorkConnectFilePage","61":"getWorkConnectRecivePage","62":"getWorkConnectRecivePage","66":"getWorkConnectFilePage","69":"getWorkConnectRecivePage","70":"getWorkConnectRecivePage","73":"getWorkConnectRecivePage","85":"getWorkConnectRecivePage","134":"getWorkConnectRecivePage"
				,"87":"getWorkConnectFilePage","90":"getWorkConnectFilePage","91":"getWorkConnectFilePage","92":"getWorkConnectFilePage","93":"getWorkConnectFilePage","133":"getWorkConnectFilePage"
				,"5":"getBGYPSLPage","6":"getBGYPSLPage","42":"getBGYPSGPage","82":"getBGYPSLPage"
				,"7":"getMTNPage","8":"getMTNPage","9":"getMTNPage","74":"getMTNPage","89":"getMTNPage","106":"getMTNPage","135":"getMTNPage"
				,"77":"getDuctApproval","95":"getElCenterReq","96":"getElCenterReq","97":"getElCenterReq","98":"getElCenterReq","99":"getElCenterReq","100":"getElCenterReq"
				,"201":"getStaffQuit","202":"getStaffQuit","139":"getStaffQuit","141":"getStaffQuit"
				,"203":"getStaffWork","204":"getStaffWork","140":"getStaffWork","142":"getStaffWork"
				,"205":"getStaffDuty","37":"getLetterPage"
		};
	var pageNum = 1;//待办留言
	var pageSize = 5;//待办留言
	var donePageNum = 1;//已办留言
	var donePageSize = 5;//已办留言
	var commentPageNum = 1;//待办评论
	var commentPageSize = 5;//待办评论
	var commentDonePageNum = 1;//已办评论
	var commentDonePageSize = 5;//已办评论
	var keyword;
	/*
	 * 点击待办事件
	 */
	$("body").on("click","#todoList li",function(){
		var _this = $(this);
		_this.css("background-color","#DDDDDD");
		var id = _this.attr("messageId");
		var url = "noticeDetail?id="+id;			
		window.location.href = url;
	});
	/*
	 * 点击已办事件
	 */
	$("body").on("click","#doneList li",function(){
		var _this = $(this);
		_this.css("background-color","#DDDDDD");
		var id = _this.attr("messageId");
		var url = "noticeDetail?id="+id;			
		window.location.href = url;
	});
	/*
	 * 点击待办评论事件
	 */
	$("body").on("click","#commentTodoList li",function(){
		var _this = $(this);
		_this.css("background-color","#DDDDDD");
		var id = _this.attr("messageId");
		var url = "noticeDetail?id="+id+"&type=comment";			
		window.location.href = url;
	});
	/*
	 * 点击已办评论事件
	 */
	$("body").on("click","#commentDoneList li",function(){
		var _this = $(this);
		_this.css("background-color","#DDDDDD");
		var id = _this.attr("messageId");
		var url = "noticeDetail?id="+id+"&type=comment";			
		window.location.href = url;
	});
	
	$(document).ready(function(){	
		//待办
	    QueryTodolist();
	}); 
	
	/*
	 * 下一页按钮
	 */
 	form.on("submit(nextLinks)",function(data){
 	   //待办
 		pageNum = pageNum+1;
 		QueryTodolist();
	})
	
	$(document).ready(function(){
		//已办
	    QueryDonelist();
	}); 
	
	/*
	 * 已办下一页按钮
	 */
 	form.on("submit(forbitLinks)",function(data){
	    //已办
 		donePageNum = donePageNum+1;
 		QueryDonelist();
	})
	
		
	$(document).ready(function(){
		//待办评论
		QueryCommentTodolist();
	}); 
	
	/*
	 * 待办评论下一页按钮
	 */
 	form.on("submit(nextLinksComments)",function(data){
	    //待办
 		commentPageNum = commentPageNum+1;
 		QueryCommentTodolist();
	})
	
	$(document).ready(function(){
		//已办评论
		QueryCommentDonelist();
	}); 
	
	/*
	 * 已办评论下一页按钮
	 */
 	form.on("submit(forbitLinksComments)",function(data){
	    //已办评论
 		commentDonePageNum = commentDonePageNum+1;
 		QueryCommentDonelist();
	})
	
	/*
	 * 模糊搜索
	 */
 	form.on("submit(search)",function(data){
		$.each($(".layui-tab-title li[lay-id]"), function() {
			if ($(this).attr("lay-id") == 'todo' && $(this).attr("class") == 'layui-this') {
				keyword = $("#keyword").val();
				// 待办
				$('#todoList').empty();
				pageNum = 1;
				QueryTodolist();
			} else if($(this).attr("lay-id") == 'done' && $(this).attr("class") == 'layui-this'){
				keyword = $("#keyword").val();
				// 已办
				$('#doneList').empty();
				donePageNum = 1;
				QueryDonelist();
			}else if($(this).attr("lay-id") == 'commentTodo' && $(this).attr("class") == 'layui-this'){
				keyword = $("#keyword").val();
				//待办评论
				$('#commentTodoList').empty();
				commentPageNum = 1;
				QueryCommentTodolist();
			}else if($(this).attr("lay-id") == 'commentDone' && $(this).attr("class") == 'layui-this'){
				keyword = $("#keyword").val();
				// 已办评论
				$('#commentDoneList').empty();
				commentDonePageNum = 1;
				QueryCommentDonelist();
			}
		})				
	})
	// 获取待办
	function QueryTodolist(){
 		 // 待办
 	     $.ajax({
 	    	 url: "admin/message/messageList", 
 	         type: "POST", 
 	         data: {approvalStatus:0,page:pageNum,rows:pageSize,keyword:keyword},
 	         dataType: 'json',      
 	         success: function (data) {
 	        	var data = data.rows;
 	            var temp="";
 	            for(var i=0;i<data.length;i++){
 					temp='<li class="li-b daibanlist" style="cursor: pointer;" messageId='+data[i].id+'>'
 						+'<div class="layui-row">'
 						+'	<div class="layui-row">'
 						+'		<div class="layui-col-xs2">'
 						+'			<img alt=""'
 						+'				src="resources/msgboardImages/qing.jpg" />'
 						+'		</div>'
 						+'		<div class="layui-col-xs10 todo-theme">&nbsp;&nbsp;'+data[i].title+'</div>'
 						+'	</div>'
 						+'	<div id="todoContent" class="todo-con">'+data[i].commentStr+'</div>'
 						+'	<div class="layui-row base-info">'
 						+'		<div class="layui-col-xs5">'+data[i].personName+'</div>'
 						+'		<div class="layui-col-xs7" style="text-align: right;">'+data[i].createTimestamp+'</div>'
 						+'	</div>'
 						+'	<hr>'
 						+'	<div style="text-align: right; color: #999;">详情>></div>'
 						+'</div>'
 						+'</li>'
 						$('#todoList').append(temp);
 	            	}
 	         },
 	        error: function(data) {
	        	 AjaxErrorHandler(data);
	         },
 	     });
 	}
 	
 	//获取已办
 	function QueryDonelist(){
 		//已办
 	     $.ajax({
 	    	 url: "admin/message/messageList", 
 	         type: "POST", 
 	         data: {done:"done",page:donePageNum,rows:donePageSize,keyword:keyword},
 	         dataType: 'json',      
 	         success: function (data) {
 	            var data=data.rows;
 	            var temp="";
 	           
 	            for(var i=0;i<data.length;i++){
 					temp='<li class="li-b daibanlist" style="cursor: pointer;" messageId='+data[i].id+'>'
 						+'<div class="layui-row">'
 						+'	<div class="layui-row">'
 						+'		<div class="layui-col-xs2">'
 						+'			<img alt=""'
 						+'				src="resources/msgboardImages/recivemsg.png" />'
 						+'		</div>'
 						+'		<div class="layui-col-xs10 todo-theme">&nbsp;&nbsp;'+data[i].title+'</div>'
 						+'	</div>'
 						+'	<div id="todoContent" class="todo-con">'+data[i].commentStr+'</div>'
 						+'	<div class="layui-row base-info">'
 						+'		<div class="layui-col-xs5">'+data[i].personName+'</div>'
 						+'		<div class="layui-col-xs7" style="text-align: right;">'+data[i].createTimestamp+'</div>'
 						+'	</div>'
 						+'	<hr>'
 						+'	<div style="text-align: right; color: #999;">详情>></div>'
 						+'</div>'
 						+'</li>'
 						$('#doneList').append(temp);
 	            }
 	         },
 	        error: function(data) {
	             AjaxErrorHandler(data);
	         },
 	     });
 	}
 	
	// 获取待办评论
	function QueryCommentTodolist(){
 		 // 待办评论
 	     $.ajax({
 	    	 url: "admin/message/commentList", 
 	         type: "POST", 
 	         data: {approvalStatus:0,page:commentPageNum,rows:commentPageSize,keyword:keyword},
 	         dataType: 'json',      
 	         success: function (data) {
 	        	var data = data.rows;
 	            var temp="";
 	            for(var i=0;i<data.length;i++){
 					temp='<li class="li-b daibanlist" style="cursor: pointer;" messageId='+data[i].id+'>'
 						+'<div class="layui-row">'
 						+'	<div class="layui-row">'
 						+'		<div class="layui-col-xs2">'
 						+'			<img alt=""'
 						+'				src="resources/msgboardImages/qing.jpg" />'
 						+'		</div>'
 						+'		<div class="layui-col-xs10 todo-theme">&nbsp;&nbsp;'+data[i].serialNumber+'</div>'
 						+'	</div>'
 						+'	<div id="todoContent" class="todo-con">'+(data[i].commentStr.length>20?(data[i].commentStr.substr(0,50)+'...'):data[i].commentStr)+'</div>'
 						+'	<div class="layui-row base-info">'
 						+'		<div class="layui-col-xs5">'+data[i].personName+'</div>'
 						+'		<div class="layui-col-xs7" style="text-align: right;">'+data[i].createTimestamp+'</div>'
 						+'	</div>'
 						+'	<hr>'
 						+'	<div style="text-align: right; color: #999;">详情>></div>'
 						+'</div>'
 						+'</li>'
 						$('#commentTodoList').append(temp);
 	            	}
 	         },
 	        error: function(data) {
	        	 AjaxErrorHandler(data);
	         },
 	     });
 	}
 	
 	//获取已办评论
 	function QueryCommentDonelist(){
 		//已办
 	     $.ajax({
 	    	 url: "admin/message/commentList", 
 	         type: "POST", 
 	         data: {done:"done",page:commentDonePageNum,rows:commentDonePageSize,keyword:keyword},
 	         dataType: 'json',      
 	         success: function (data) {
 	            var data=data.rows;
 	            var temp="";
 	           
 	            for(var i=0;i<data.length;i++){
 					temp='<li class="li-b daibanlist" style="cursor: pointer;" messageId='+data[i].id+'>'
 						+'<div class="layui-row">'
 						+'	<div class="layui-row">'
 						+'		<div class="layui-col-xs2">'
 						+'			<img alt=""'
 						+'				src="resources/msgboardImages/recivemsg.png" />'
 						+'		</div>'
 						+'		<div class="layui-col-xs10 todo-theme">&nbsp;&nbsp;'+data[i].serialNumber+'</div>'
 						+'	</div>'
 						+'	<div id="todoContent" class="todo-con">'+(data[i].commentStr.length>20?(data[i].commentStr.substr(0,100)+'...'):data[i].commentStr)+'</div>'
 						+'	<div class="layui-row base-info">'
 						+'		<div class="layui-col-xs5">'+data[i].personName+'</div>'
 						+'		<div class="layui-col-xs7" style="text-align: right;">'+data[i].createTimestamp+'</div>'
 						+'	</div>'
 						+'	<hr>'
 						+'	<div style="text-align: right; color: #999;">详情>></div>'
 						+'</div>'
 						+'</li>'
 						$('#commentDoneList').append(temp);
 	            }
 	         },
 	        error: function(data) {
	             AjaxErrorHandler(data);
	         },
 	     });
 	}
})

