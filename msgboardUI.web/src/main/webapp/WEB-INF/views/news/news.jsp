<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>权威发布 - 广济南社区</title>
	<meta http-equiv="X-UA-Compatible" content="IE=8,IE=edge,chrome=1" >
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=0">
	<link rel="shortcut icon" href="resources/msgboardImages/favicon.ico" type="image/x-icon" />
	<!-- Bootstrap -->
	<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="resources/css/common/style.css">
	<link href="resources/css/common/mobile-menu.css" rel="stylesheet">
	<link rel="stylesheet" href="resources/css/news/news.css">
	<link rel="stylesheet" href="resources/css/common/common.css">
	<script type="text/javascript" src="resources/js/commonJs/jquery-1.8.3.min.js"></script>
	<script src="resources/js/common/mobile-menu.js"></script>
	<script src="resources/bootstrap/js/bootstrap.min.js"></script>
	<script src="resources/admin/js/common.js"></script>
</head>
 <jsp:include page="../../common/head.jsp"></jsp:include>
 <jsp:include page="../../common/mobileHead.jsp"></jsp:include>
 <script type="text/javascript">
function createPageHTML(_nPageCount, _nCurrIndex){
	$('.mn-page').html('');
	if(_nPageCount == null || _nPageCount<=1){
		return;
	}
	var nCurrIndex = _nCurrIndex || 0;
	if(nCurrIndex == 0)
	{
		$('.mn-page').append("<a><span class=\"f\">&laquo;</span>上一页</a>&nbsp;");
		$('.mn-page').append("<span class='current'>1</span>&nbsp;");
	}
	else
	{
		var prePageNum = nCurrIndex-1;
		if (prePageNum == 0) {
			$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getNews(1)\"><span class=\"f\">&laquo;</span>上一页</a>&nbsp;");
			$('.mn-page').append("<span class='current'>1</span>&nbsp;");
		} else {
			$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getNews("+prePageNum+")\"><span class=\"f\">&laquo;</span>上一页</a>&nbsp;");
			$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getNews(1)\">1</a>&nbsp;");
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
			$('.mn-page').append("...&nbsp;");
	for(var i = startNum; i<endNum; i++){
		if(nCurrIndex == (i+1))
			$('.mn-page').append("<span class='current'>"+(i+1) + "</span>&nbsp;");
		else
			$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getNews("+(i+1)+")\">"+(i+1)+"</a>&nbsp;");
	}
	if(endNum <_nPageCount-1)
		$('.mn-page').append("...&nbsp;");
	var nextPageNum = nCurrIndex+1;
	var lastPageNum = _nPageCount-1;
	if(nCurrIndex == _nPageCount)
	{
		$('.mn-page').append("<span class='current'>"+_nPageCount+"</span>&nbsp;");
		$('.mn-page').append("<a>下一页<span class=\"f\">&raquo;</span></a>&nbsp;");
	}
	else{
		$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getNews("+(lastPageNum+1)+")\">"+_nPageCount+"</a>&nbsp;");
		$('.mn-page').append("<a <a href=\"javascript:void(0);\" onclick=\"getNews("+nextPageNum+")\">下一页<span class=\"f\">&raquo;</span></a>&nbsp;");
	}
}
</script>
<input id="userId" type="hidden" value="${person.id }">
 <body>
 <div>
 	<div class="container theme-showcase mine-main-container" role="main">
		<div class="mn-page-header">
			<div class="mn-page-header-title">
				<span class=tit5>权威发布</span>
			</div>
			<div class="mn-page-header-crumbs"></div>
		</div>
		
		<div class="mn-box">
			<div>
		  		<ul id="newsInfo" class="mn-box-ul row ">
			  	</ul>
				 	<div style="text-align: center">
			  		<ul class="pages clearfix">
						<li class="L mn-page" style="list-style-type:none">
		
						</li>
						<li class="R"></li>
					</ul>
			  	</div>
			</div>
		</div>
	</div>
 </div>
 <jsp:include page="../../common/foot.jsp"></jsp:include>
 </body>
 <script type="text/javascript">
 function getNews(pageNum, pageSize){
	 var pageSize = pageSize?pageSize:20;
	 var newsInfo = $("#newsInfo");
	 newsInfo.empty();
	 $.ajax({
			url:"news/newsList",
			type:"GET",
			dataType:"json",
			data:{page:pageNum,rows:pageSize},
			success:function(data){
				var totalPage = Math.ceil(data.total/pageSize);
				var pageNum = parseInt(data.pageNum);
				var news = data.rows;
				createPageHTML(totalPage, pageNum);
				for(var i=0;i<news.length;i++){
					if(news[i].linkURL == null){
						var list = '<li class="li">'
							+'<a class="col-xs-12 col-sm-8 col-md-8 mn-red-box-content-ellipsis" href="newsDetail?id='+news[i].id+'" title="详情" target="_blank">'
							+news[i].title
							+'</a>'
							+'<div class="col-xs-12 col-sm-4 col-md-4 text-right">'+news[i].createTimestamp+'</div>'
							+'</li>'
							+'<div class="h_6"></div>';
					}else{
						var list = '<li class="li">'
							+'<a class="col-xs-12 col-sm-8 col-md-8 mn-red-box-content-ellipsis" href=//'+news[i].linkURL+' title="详情" target="_blank">'
							+news[i].title
							+'</a>'
							+'<div class="col-xs-12 col-sm-4 col-md-4 text-right">'+news[i].createTimestamp+'</div>'
							+'</li>'
							+'<div class="h_6"></div>';
					}
					newsInfo.append(list);
					if(parseInt((i+1)/5) == (i+1)/5){
						newsInfo.append('<div class="h_1"></div>');
					}
				}
			},
			error:function(data){
				AjaxErrorHandler(data);
			}
		}) 
 }
 $(function(){
	 getNews(1,20)
 });
 </script>
 </html>
