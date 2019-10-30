/**
 * ajax抛出异常
 * @param data
 * @param callback
 * @returns
 */
function AjaxErrorHandler(data, callback) {
    try {
        var result = $.parseJSON(data.responseText);
        showAlert(result.errorMessage, function(){
            if(callback) callback();
        });
    } catch (e) {
        showAlert("未知错误", function(){
            if(callback) callback();
        });
    }
}

function showAlert(content,callback) {
	if(content != "未知错误"){
		 alert(content);
	}
    if(callback) callback();
}
/**
 * 是不是移动端
 * @param url
 * @returns
 */
function isMobile() {
	var browser={
        versions:function(){
            var u = navigator.userAgent, app = navigator.appVersion;
            return {
                trident: u.indexOf("Trident") > -1, //IE内核
                presto: u.indexOf("Presto") > -1, //opera内核
                webKit: u.indexOf("AppleWebKit") > -1, //苹果、谷歌内核
                gecko: u.indexOf("Gecko") > -1 && u.indexOf("KHTML") == -1, //火狐内核
                mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //是否为移动终端
                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                android: u.indexOf("Android") > -1, //android终端或者uc浏览器
                iPhone: u.indexOf("iPhone") > -1, //是否为iPhone或者QQHD浏览器
                iPad: u.indexOf("iPad") > -1, //是否iPad
                webApp: u.indexOf("Safari") == -1 //是否web应该程序，没有头部与底部
            };
        }(),
        language:(navigator.browserLanguage || navigator.language).toLowerCase()
    }
    if(browser.versions.android || browser.versions.iPhone || browser.versions.iPad){
    	return true;
    }else{
    	return false;
    }
}

/**
 * 留言列表创建分页
 * @param _nPageCount 页数
 * @param _nCurrIndex 当前页数
 * @returns
 */
//function createPageHTML(_nPageCount, _nCurrIndex, _pageDom, _pageClickName){
//	var $page = (_pageDom==null||_pageDom==undefined)?$('.mn-page'):_pageDom;
//	var pageClickName = (_pageClickName==null||_pageClickName==undefined)?'getMessage':_pageClickName;
//	$page.html('');
//	if(_nPageCount == null || _nPageCount<=1){
//		return;
//	}
//	var nCurrIndex = _nCurrIndex || 0;
//	if(nCurrIndex == 0)
//	{
//		$page.append("<a><span class=\"f\">&laquo;</span>上一页</a>&nbsp;");
//		$page.append("<span class='current'>1</span>&nbsp;");
//	}
//	else
//	{
//		var prePageNum = nCurrIndex-1;
//		if (prePageNum == 0) {
//			$page.append("<a href=\"javascript:void(0);\" onclick=\""+pageClickName+"(1,null)\"><span class=\"f\">&laquo;</span>上一页</a>&nbsp;");
//			$page.append("<span class='current'>1</span>&nbsp;");
//		} else {
//			$page.append("<a href=\"javascript:void(0);\" onclick=\""+pageClickName+"("+prePageNum+",null)\"><span class=\"f\">&laquo;</span>上一页</a>&nbsp;");
//			$page.append("<a href=\"javascript:void(0);\" onclick=\""+pageClickName+"(1,null)\">1</a>&nbsp;");
//		}
//	}
//	var startNum;
//	var endNum;
//	if (nCurrIndex -2 >1)
//		startNum = nCurrIndex -2;
//	else
//		startNum = 1;
//	if (nCurrIndex + 3 < _nPageCount)
//		endNum = nCurrIndex + 3;
//	else
//		endNum = _nPageCount-1;
//		if(startNum >1)
//			$page.append("...&nbsp;");
//	for(var i = startNum; i<endNum; i++){
//		if(nCurrIndex == (i+1))
//			$page.append("<span class='current'>"+(i+1) + "</span>&nbsp;");
//		else
//			$page.append("<a href=\"javascript:void(0);\" onclick=\""+pageClickName+"("+(i+1)+",null)\">"+(i+1)+"</a>&nbsp;");
//	}
//	if(endNum <_nPageCount-1)
//		$page.append("...&nbsp;");
//	var nextPageNum = nCurrIndex+1;
//	var lastPageNum = _nPageCount-1;
//	if(nCurrIndex == _nPageCount)
//	{
//		$page.append("<span class='current'>"+_nPageCount+"</span>&nbsp;");
//		$page.append("<a>下一页<span class=\"f\">&raquo;</span></a>&nbsp;");
//	}
//	else{
//		$page.append("<a href=\"javascript:void(0);\" onclick=\""+pageClickName+"("+(lastPageNum+1)+",null)\">"+_nPageCount+"</a>&nbsp;");
//		$page.append("<a <a href=\"javascript:void(0);\" onclick=\""+pageClickName+"("+nextPageNum+",null)\">下一页<span class=\"f\">&raquo;</span></a>&nbsp;");
//	}
//}
function createPageHTML(_nPageCount, _nCurrIndex){
	$('.pager').html('');
	if(_nPageCount == null || _nPageCount<=1){
		return;
	}
	if(_nCurrIndex == 1){
		$('.pager').append('<li style="margin-right: 20px;"><a style="color:#337ab7; font-weight: 700;">上一页</a></li><li><a style="color:#337ab7; font-weight: 700;" onclick=\"getMessage('+(_nCurrIndex+1)+',null)\">下一页</a></li>');
	}else if(_nCurrIndex == _nPageCount){
		$('.pager').append('<li style="margin-right: 20px;"><a style="color:#337ab7; font-weight: 700;" onclick=\"getMessage('+(_nCurrIndex-1)+',null)\">上一页</a></li><li><a style="color:#337ab7; font-weight: 700;">下一页</a></li>');
	}else {
		$('.pager').append('<li style="margin-right: 20px;"><a style="color:#337ab7; font-weight: 700;" onclick=\"getMessage('+(_nCurrIndex-1)+',null)\">上一页</a></li><li><a style="color:#337ab7; font-weight: 700;" onclick=\"getMessage('+(_nCurrIndex+1)+',null)\">下一页</a></li>');
	}
}

/**
 * 评论列表创建分页
 * @param _nPageCount 页数
 * @param _nCurrIndex 当前页数
 * @returns
 */
function createCommentPageHTML(_nPageCount, _nCurrIndex){
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
			$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getMessageDetail(1,null)\"><span class=\"f\">&laquo;</span>上一页</a>&nbsp;");
			$('.mn-page').append("<span class='current'>1</span>&nbsp;");
		} else {
			$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getMessageDetail("+prePageNum+",null)\"><span class=\"f\">&laquo;</span>上一页</a>&nbsp;");
			$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getMessageDetail(1,null)\">1</a>&nbsp;");
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
			$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getMessageDetail("+(i+1)+",null)\">"+(i+1)+"</a>&nbsp;");
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
		$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getMessageDetail("+(lastPageNum+1)+",null)\">"+_nPageCount+"</a>&nbsp;");
		$('.mn-page').append("<a <a href=\"javascript:void(0);\" onclick=\"getMessageDetail("+nextPageNum+",null)\">下一页<span class=\"f\">&raquo;</span></a>&nbsp;");
	}
}

/**
 * 消息提醒创建分页
 * @param _nPageCount 页数
 * @param _nCurrIndex 当前页数
 * @returns
 */
function createReminderPageHTML(_nPageCount, _nCurrIndex){
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
			$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getReminder(1,null)\"><span class=\"f\">&laquo;</span>上一页</a>&nbsp;");
			$('.mn-page').append("<span class='current'>1</span>&nbsp;");
		} else {
			$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getReminder("+prePageNum+",null)\"><span class=\"f\">&laquo;</span>上一页</a>&nbsp;");
			$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getReminder(1,null)\">1</a>&nbsp;");
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
			$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getReminder("+(i+1)+",null)\">"+(i+1)+"</a>&nbsp;");
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
		$('.mn-page').append("<a href=\"javascript:void(0);\" onclick=\"getReminder("+(lastPageNum+1)+",null)\">"+_nPageCount+"</a>&nbsp;");
		$('.mn-page').append("<a <a href=\"javascript:void(0);\" onclick=\"getReminder("+nextPageNum+",null)\">下一页<span class=\"f\">&raquo;</span></a>&nbsp;");
	}
}