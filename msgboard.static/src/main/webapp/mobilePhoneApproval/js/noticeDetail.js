layui.config({
	base : "js/"
}).use(['form','layer','jquery','element'],function(){
	var form = layui.form,
	layer = layui.layer,
	$ = layui.jquery;

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
	
	var messageId = $("#messageId").val();
	getComment(1,null);
	/**
	 * 获取图片
	 */
	$.ajax({
		url:"admin/message/get_message_images",
		type:"GET",
		data:{messageId:messageId},
		async : true,
	    dataType : 'json',
		success: function(data){
			 $('#imageDiv') .html('');
			 for(var i = 0;i<data.length;i++){
				 var list = '<img src="resources/upload/'+data[i].address+'" style="margin-top:10px;margin-right: 10px;float:left;width:50px;height:50px" onclick="javascript:window.open(this.src);">'
				 $('#imageDiv').append(list);
			 }
		},
		error:function(){
			top.layer.msg("获取附件有误!");
		}
	});
	/**
	 * 获取评论
	 */
	function getComment(pageNum, pageSize){
		
		var pageSize = pageSize?pageSize:1;
		$.ajax({
			url:"admin/message/commentListAll",
			type:"POST",
			data:{messageId:messageId},
			async : true,
		    dataType : 'json',
			success: function(data){
				var comment = $("#comment");
				var data = data.rows;
				for(var i = 0;i<data.length;i++){
					var list = '<li class="layui-timeline-item">'
						+'<i class="layui-icon layui-timeline-axis" style="font-size: 18px; color: #FF5722;">&#xe658;</i>'
						+'<div class="layui-timeline-content layui-text">'
						+'<div class="layui-timeline-title">'
						+'<font color="#009688">'+data[i].commentStr+'</font><br/></div><p>'
						+'评论时间：'+data[i].createTimestamp+'<br/>'
						+'评论人：'+data[i].personName
						+'</p></div></li>';
					comment.append(list);
				}
			},
			error:function(){
				top.layer.msg("获取评论有误!");
			}
		});
	}
	function save(){
		var replyText = $("#reply").val();
		if($.trim(replyText)==""){
			alert("请输入答复内容!");
			return;
		}
		
		$.ajax({
			 url: 'admin/message/saveReply',
			 type:"POST",
			 data:{messageId:messageId,replyText:replyText},
			 success:function(data){
				 window.location.reload();
			 },
			 error:function(data){
				 alert("保存失败!");
			 }
		})
		
	}
	//审批通过
	function agree(){
		var c = confirm("是否确认审批通过?");
		if(c == true){
			$.ajax({
	              type:"POST",
	              url:'admin/message/checkMessage?type=2'+'&id='+messageId,
	              success:function(msg){
	            	  window.location.reload();
	              }
	           });
		}else {
			return;
		}
		  
	}
	//审批不通过
	function disAgree(){
		var c = confirm("是否确认审批不通过?");
		if(c == true){
			$.ajax({
	              type:"POST",
	              url:'admin/message/checkMessage?type=1'+'&id='+messageId,
	              success:function(msg){
	            	  window.location.reload();
	              }
	           });
		}else{
			return;
		}
	}
	
	$("#save").click(function(){
		save();
	})
	$("#agree").click(function(){
		agree();
	})
	$("#disagree").click(function(){
		disAgree();
	})
})
