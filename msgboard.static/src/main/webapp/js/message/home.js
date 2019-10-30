var list;
var pageNum=1;
$(function(){
	$.ajax({
		url:'message/get_main_keyword_forcombo',
		type:'GET',
		async:false, 
		dataType:'json',
		success:function(data){
			layui.use(['tree', 'layer'], function(){
				  var layer = layui.layer,
				  $ = layui.jquery; 
				  layui.tree({
				    elem: '#subject',
				    skin: 'subject',
				    target: '_blank',
				    click: function(item){
						list=[];
	     				list.push(item.id);
				    	if(item.children != null){
				    		for(var i = 0; i<item.children.length; i++){
				    			list.push(item.children[i].id);
				    		}
				    	}
				    	$("#keyword").val("");
				    	getMessage(1,20);
				    },
				    nodes: data
				  });
			});
			$("#subject").append('<li class=""><i class="layui-icon layui-tree-spread iconfont" style="color:#E0E0E0;">&#xe672;</i><a href="javascript:;" onclick="selectAllSubject(this);"><cite>全部主题</cite></a></li>');
		},
		error:function(data){
			AjaxErrorHandler(data);
		}
	});
	
})
//选择所有主题
function selectAllSubject(target) {
	list=null;
 	$('.layui-tree li').removeClass('active');
    $(target).parent().addClass('active');
	getMessage(1,20);
}

//常见问答
function getFaqs(pageNum, pageSize){
	var keyword = $("#keyword").val();
	var pageSize = pageSize?pageSize:6;
	var faqsListInfo = $("#faqsListInfo");
	faqsListInfo.empty(); 
	$.ajax({
		url:'faqs/faqsList',
		type:"GET",
		data:{
			page: pageNum,
			rows: pageSize,
			q: keyword
		},
	success:function(data){
		var faqs = data.rows;
		for(var i=0;i<faqs.length;i++){
			var list = '<li class="quanwei-li ">'
			+'<a class="ellipsis" href="faqsDetail?id='+faqs[i].id+'" target="_blank">'+faqs[i].question
			+'</a>'
			+'</li>';
		faqsListInfo.append(list);
		}
	},
	error:function(data){
		AjaxErrorHandler(data);
	}
	})
}

//权威发布
function getNews(pageNum, pageSize){
	var keyword = $("#keyword").val();
	var pageSize = pageSize?pageSize:6;
	var newListInfo = $("#newListInfo");
	newListInfo.empty(); 
	$.ajax({
		url:'news/newsList',
		type:"GET",
		data:{
			page: pageNum,
			rows: pageSize,
			q: keyword
		},
	success:function(data){
		var news = data.rows;
		for(var i=0;i<news.length;i++){
			if(news[i].linkURL==null){
				var list = '<li class="quanwei-li ">'
					+'<a class="ellipsis" href="newsDetail?id='+news[i].id+'" target="_blank">'+news[i].title
					+'</a>'
					+'</li>';
			}else{
				var list = '<li class="quanwei-li ">'
					+'<a class="ellipsis" href=//'+news[i].linkURL+' target="_blank">'+news[i].title
					+'</a>'
					+'</li>';
			}
		newListInfo.append(list);
		}
	},
	error:function(data){
		AjaxErrorHandler(data);
	}
	});
}
	
//按钮查询留言,先清空list
function getMessageForSearch(){
	list=null;
	getMessage(1);
}
	
//搜索留言
function getMessage(pageNum, pageSize){
	var keyword = $("#keyword").val();
	if($.trim(keyword) == ""){
		$("#msgBeizhu").hide();
	}else{
		$("#msgBeizhu").html('为您找到"'+keyword+'"相关结果如下:');
		$("#msgBeizhu").show();
	}
	var pageSize = pageSize?pageSize:20;
	list = list?list:null;
	var meaasgeInfo = $("#messageInfo");
	meaasgeInfo.empty();
	$.ajax({
		url:'message/messageList',
		type:"GET",
		ansy:false,
		data:"page="+pageNum+"&rows="+pageSize+"&q="+keyword+"&list="+list,
		success:function(data){
			var totalPage = Math.ceil(data.total/pageSize);
			var pageNum = parseInt(data.pageNum);
			var messages = data.rows;
			createPageHTML(totalPage, pageNum);
			meaasgeInfo.empty();
			for(var i=0;i<messages.length;i++){
				var messageId = messages[i].id;
				var messageStatus = "";
				if(messages[i].approvalStatus.toString() == "未处理"){
					//未关注
					messageStatus = '<span class="label label-default">未关注</span>';
				}else if(messages[i].approvalStatus.toString() == "审核通过"){
					if(messages[i].replyStatus.toString() == "未答复"){
						//已关注
						messageStatus = '<span class="label label-info">处理中</span>';
					}else if(messages[i].replyStatus.toString() == "已答复"){
						//已答复
						messageStatus = '<span class="label label-success">已答复</span>';
					}
				}
				var personName = (messages[i].personName==null||messages[i].personName.length==0)?'':messages[i].personName;
				var timeStamp = (messages[i].createTimestamp==null||messages[i].createTimestamp.length==0)?'':messages[i].createTimestamp;
				var createTimestamp = timeStamp.substring(0,10);
				var list = '<div class="msg-list-box">'
					+'<div class="head-image"><img src="resources/msgboardImages/headPortraits/'+messages[i].headPortraits+'" style="width:100%; height:100%"></div>'
					+'<div class="msg-list-content">';
					if(messages[i].title.length>35){
						list += '<a href="messageDetail?id='+messages[i].id+'" target="_blank" style="font-size:16px"><div class="msg-list-title">'+messages[i].title.substring(0,35)+'...'+(messages[i].ifHasAttach == true?'<img src="resources/msgboardImages/附件.png" style="width:20px;height:20px;margin-left:8px">':'')+'</div></a>';
					}else{
						list += '<a href="messageDetail?id='+messages[i].id+'" target="_blank" style="font-size:16px"><div class="msg-list-title">'+messages[i].title+(messages[i].ifHasAttach == true?'<img src="resources/msgboardImages/附件.png" style="width:20px;height:20px;margin-left:8px">':'')+'</div></a>';
					}
					var type = messages[i].messageCategory;
					var typeStr = '<span class="label msg-list-category msg-type-zixun">'+messages[i].messageCategory+'</span>';
					if(type == "咨询"){
						typeStr = '<span class="label msg-list-category msg-type-zixun">'+messages[i].messageCategory+'</span>';
					}else if(type == "建议"){
						typeStr = '<span class="label msg-list-category msg-type-jianyi">'+messages[i].messageCategory+'</span>';
					}else if(type == "举报"){
						typeStr = '<span class="label msg-list-category msg-type-jubao">'+messages[i].messageCategory+'</span>';
					}else if(type == "投诉"){
						typeStr = '<span class="label msg-list-category msg-type-tousu">'+messages[i].messageCategory+'</span>';
					}else if(type == "报修"){
						typeStr = '<span class="label msg-list-category msg-type-baoxiu">'+messages[i].messageCategory+'</span>';
					}else if(type == "求助"){
						typeStr = '<span class="label msg-list-category msg-type-qiuzhu">'+messages[i].messageCategory+'</span>';
					}else if(type == "灌水"){
						typeStr = '<span class="label msg-list-category msg-type-guanshui">'+messages[i].messageCategory+'</span>';
					}else if(type == "其他"){
						typeStr = '<span class="label msg-list-category msg-type-qita">'+messages[i].messageCategory+'</span>';
					}
					
					list += '<div class="msg-list-others">'
							+typeStr
							+'<div class="mn-box-content-ellipsis msg-list-name">'+personName+'</div><div class="vertical-line">|</div>'
							+'<div class="mn-box-content-ellipsis msg-list-time">'+createTimestamp+'</div><div class="vertical-line">|</div>'
							+'<div class="mn-box-content-ellipsis msg-list-reply">'+messages[i].commentNumber+'条评论</div>'
						+'</div>'
					+'</div>'
					+'<div class="msg-list-status">'
						+ messageStatus
					+'</div>'
				+'</div>';
					meaasgeInfo.append(list);
				}
			 $('html , body').animate({scrollTop: 0},'slow');
			},
			error:function(data){
				AjaxErrorHandler(data);
			}
		});
	
}
//查询评论
function getMessageDetail(messageId,pageNum, pageSize){
	var pageSize = pageSize?pageSize:6;
	$.ajax({
		url:'message/commentList',
		type:"POST",
		ansy:false,
		data:{
			messageId: messageId,
			page: pageNum,
			rows: pageSize
		},
	success:function(data){
			//$(".commentNums").html("<i>●&nbsp;</i>"+data.rows.length+"条评论");
	},
	error:function(data){
		AjaxErrorHandler(data);
	}
	})
}

function getPre(){
	
}

function getNext(){
	
}


$(function(){
	getMessage(1,null);
	//权威发布
	getNews(1, 5);
	//常见问答
	getFaqs(1, 5);
});



/**
 * 留言列表创建分页
 * @param _nPageCount 页数
 * @param _nCurrIndex 当前页数
 * @returns
 */
function createPageHTML(_nPageCount, _nCurrIndex, _pageDom, _pageClickName){
	var $page = (_pageDom==null||_pageDom==undefined)?$('.mn-page'):_pageDom;
	var pageClickName = (_pageClickName==null||_pageClickName==undefined)?'getMessage':_pageClickName;
	$page.html('');
	if(_nPageCount == null || _nPageCount<=1){
		return;
	}
	var nCurrIndex = _nCurrIndex || 0;
	if(nCurrIndex == 0)
	{
		$page.append("<a><span class=\"f\">&laquo;</span>上一页</a>&nbsp;");
		$page.append("<span class='current'>1</span>&nbsp;");
	}
	else
	{
		var prePageNum = nCurrIndex-1;
		if (prePageNum == 0) {
			$page.append("<a href=\"javascript:void(0);\" onclick=\""+pageClickName+"(1,null)\"><span class=\"f\">&laquo;</span>上一页</a>&nbsp;");
			$page.append("<span class='current'>1</span>&nbsp;");
		} else {
			$page.append("<a href=\"javascript:void(0);\" onclick=\""+pageClickName+"("+prePageNum+",null)\"><span class=\"f\">&laquo;</span>上一页</a>&nbsp;");
			$page.append("<a href=\"javascript:void(0);\" onclick=\""+pageClickName+"(1,null)\">1</a>&nbsp;");
		}
	}
	var startNum;
	var endNum;
	if (nCurrIndex -2 >1)
		startNum = nCurrIndex -2;
	else
		startNum = 1;
	if (nCurrIndex + 3 < _nPageCount)
		endNum = nCurrIndex + 3;
	else
		endNum = _nPageCount-1;
		if(startNum >1)
			$page.append("...&nbsp;");
	for(var i = startNum; i<endNum; i++){
		if(nCurrIndex == (i+1))
			$page.append("<span class='current'>"+(i+1) + "</span>&nbsp;");
		else
			$page.append("<a href=\"javascript:void(0);\" onclick=\""+pageClickName+"("+(i+1)+",null)\">"+(i+1)+"</a>&nbsp;");
	}
	if(endNum <_nPageCount-1)
		$page.append("...&nbsp;");
	var nextPageNum = nCurrIndex+1;
	var lastPageNum = _nPageCount-1;
	if(nCurrIndex == _nPageCount)
	{
		$page.append("<span class='current'>"+_nPageCount+"</span>&nbsp;");
		$page.append("<a>下一页<span class=\"f\">&raquo;</span></a>&nbsp;");
	}
	else{
		$page.append("<a href=\"javascript:void(0);\" onclick=\""+pageClickName+"("+(lastPageNum+1)+",null)\">"+_nPageCount+"</a>&nbsp;");
		$page.append("<a <a href=\"javascript:void(0);\" onclick=\""+pageClickName+"("+nextPageNum+",null)\">下一页<span class=\"f\">&raquo;</span></a>&nbsp;");
	}
}