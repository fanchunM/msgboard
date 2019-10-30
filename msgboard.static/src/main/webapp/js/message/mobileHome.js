var list;
var mainKeyWord;
var cateLevelOne = null;
var cateLevelTwo = null;
$(function(){
	//主题分类查询留言
	$.ajax({
		url:'message/get_main_keyword_forcombo',
		type:'GET',
		dataType:'json',
		success:function(data){
			mainKeyWord = data;
			$('.searchCategory .cateLevelOne').html('');
			$('.searchCategory .cateLevelOne').html('<a id="-" index="-" href="javascript:void(0);"><span class="searchListName">全部</span></a>');
			for(var i=0; i<data.length; i++){
				var temp = '<a id="'+data[i].id+'" index="'+i+'" href="javascript:void(0);"><span class="searchListName">'+data[i].name+'</span></a>';
				$('.searchCategory .cateLevelOne').append(temp);
			}
			$(".searchCategory .cateLevelOne a").on("click", function () {
				cateLevelOne = null;
				cateLevelTwo = null;
				var index = $(this).attr('index');
			   	$(".searchCategory .cateLevelOne a").removeClass('active');
			   	$('.searchCategory .cateLevelTwo').html('');
			   	cateLevelOne = index;
		        $(this).addClass('active');
		        if(index=='-') return;	//选择全部
		        
		        //子集
		        var data = mainKeyWord[index];
		        if(data.children != null){
		        	for(var j =0;j<data.children.length;j++){
		        		var temp = '<a id="'+data.children[j].id+'" index="'+j+'" href="javascript:void(0);"><span class="searchListName">'+data.children[j].name+'</span></a>';
						$('.searchCategory .cateLevelTwo').append(temp);
		        	}
		        	 $(".searchCategory .cateLevelTwo a").on("click", function () {
		        		cateLevelTwo = null;
				       	$(".searchCategory .cateLevelTwo a").removeClass('active');
				       	cateLevelTwo = $(this).attr('index');
				        $(this).addClass('active');
				     });
		        }
		    });
		},
		error:function(data){
			AjaxErrorHandler(data);
		}
	});
	// 搜索
	$('#showSearch').on('click', function(){
		showSearchDialog();
	});
	//关闭搜索
	$(".search_container .back").on("click", function () {
		closeSearchDialog();
     });
	//清空关键词
     $(".searbox .off").on("click", function () {
        $('.searbox input').val('');
        $(".searbox .off").hide();
     })
     //显示清空按钮
     $('.searbox input').on('input propertychange', function() {
    	 if($(this).val()==""){
    		 $(".searbox .off").hide();
    	 } else {
    		 $(".searbox .off").show();
    	 }
	 });
     //提交搜索
     $('.searchSubmit').on("click", function () {
    	 submitSearch();
     });
 	 $('.searchCancel').on("click", function () {
 		closeSearchDialog();
     });
 	 $('.searchBtn').on("click", function () {
 		submitSearch();
     });
});

//打开搜索界面
function showSearchDialog() {
	$(".content_container").hide();
	$(".search_container").show();
}
//关闭搜索界面
function closeSearchDialog() {
	$(".search_container").hide();
    $(".content_container").show();
}
//提交搜索
function submitSearch() {
	list=[];
	if(cateLevelTwo!=null) {
	 list.push(mainKeyWord[cateLevelOne].children[cateLevelTwo].id);
	}
	else if(cateLevelOne!=null) {
	 if(cateLevelOne == '-') list=null;
	 else {
		 var data = mainKeyWord[cateLevelOne];
	  		 var children = "";
	        if(data.children != null){
	       	for(var j =0;j<data.children.length;j++){
	       		if(j==0) children = data.children[j].id;
	       		else children = children + ',' + data.children[j].id;
	       	}
	        }
	        list.push(children);
	 }
	}
	getMessage(1,null);
	closeSearchDialog();
}

//便民问答
//function getFaqs(pageNum, pageSize){
//	var keyword = $("#keyword").val();
//	var pageSize = pageSize?pageSize:5;
//	var faqsListInfo = $("#faqsListInfo");
//	faqsListInfo.empty(); 
//	$.ajax({
//		url:'faqs/faqsList',
//		type:"GET",
//		data:{
//			page: pageNum,
//			rows: pageSize,
//			q: keyword
//		},
//		success:function(data){
//			var faqs = data.rows;
//			for(var i=0;i<faqs.length;i++){
//				var list = '<li class="li" style="">'
//					+'<a href="faqsDetail?id='+faqs[i].id+'">'+faqs[i].question+'</a></li>';
//			faqsListInfo.append(list);
//			}
//		},
//		error:function(data){
//			AjaxErrorHandler(data);
//		}
//	})
//}

//权威发布
//function getNews(pageNum, pageSize){
//	var keyword = $("#keyword").val();
//	var pageSize = pageSize?pageSize:5;
//	var newListInfo = $("#newListInfo");
//	newListInfo.empty(); 
//	$.ajax({
//		url:'news/newsList',
//		type:"GET",
//		data:{
//			page: pageNum,
//			rows: pageSize,
//			q: keyword
//		},
//		success:function(data){
//			var news = data.rows;
//			for(var i=0;i<news.length;i++){
//				if(news[i].linkURL==null){
//					var list = '<li class="li mn-red-box-content-ellipsis" style="">'
//					+'<a href="newsDetail?id='+news[i].id+'">'+news[i].title+'</a></li>';
//					
//				}else{
//					var list = '<li class="li" style="">'
//					+'<a href=//'+news[i].linkURL+'>'+news[i].title+'</a></li>';
//				}
//			newListInfo.append(list);
//		}
//	},
//	error:function(data){
//		AjaxErrorHandler(data);
//	}
//	})
//}
//进入留言详情
function goMsgDetail(id){
	window.location.href="mobile_message_detail?id="+id;
}
//获得留言列表
function getMessage(pageNum, pageSize){
		var keyword = $("#keyword").val();
		var pageSize = pageSize?pageSize:20;
		if(list==null||list.length==0) list = null;
		var meaasgeInfo = $("#messageInfo");
		meaasgeInfo.empty();
		$.ajax({
			url:'message/messageList',
			type:"GET",
			data:"page="+pageNum+"&rows="+pageSize+"&q="+keyword+"&list="+list,
			success:function(data){
				var totalPage = Math.ceil(data.total/pageSize);
				var pageNum = parseInt(data.pageNum);
				var messages = data.rows;
				createPageHTML(totalPage, pageNum);
				for(var i=0;i<messages.length;i++){            
					var  timeStamp = messages[i].createTimestamp;
					var createTimestamp = timeStamp.substring(0,10);
					var list = '<div class="msg-mn-box" onclick="goMsgDetail(\''+messages[i].id+'\')">'
							+'<div><div class="head-image"><img src="resources/msgboardImages/headPortraits/'+messages[i].headPortraits+'" style="width:100%; height:100%"></div><div style="overflow:hidden"><p class="mn-box-content-ellipsis" >'+messages[i].title+'</p>'
							+'<div class="row msg-mn-fuzhu">'
							+(messages[i].personName.length>7?('<div class="mn-box-content-ellipsis msg-mn-son">'+messages[i].personName.substring(0,6)+'...</div>'):('<div class="mn-box-content-ellipsis msg-mn-son">'+messages[i].personName+'</div>'))
							+'<div class="msg-mn-son">'+createTimestamp+'</div>'
							+'<div class="msg-mn-son">'+messages[i].commentNumber+'条评论</div></div></div></div>'
							+'<div class="mn-box-content-ellipsis-2 msg-mn-content">'
							+ messages[i].commentStr
							+'</div><div>'
						meaasgeInfo.append(list);
				}
//				$.cookie('pageNum', pageNum, { expires: 7, path: '/' });
//				getInitialPosition();
		},
		error:function(data){
			AjaxErrorHandler(data);
		}
		});
		$('html , body').animate({scrollTop: 0},'slow');
		var search = $("#search");
		var themeDiv = $("#themeDivide");
		search.css("display","none");
		themeDiv.css("display","none");
		$("#outer").css("display","none");
	}
$(function(){
	getMessage(1,20);
//	var pageNum = $.cookie('pageNum');
//	if(pageNum!=1){
//		var meaasgeInfo = $("#messageInfo");
//		meaasgeInfo.empty();
//		getMessage(pageNum,20);
//	}
	//权威发布
//	getNews(1, 5);
	//常见问答
//	getFaqs(1, 5);
	$("#backTop").remove();
})