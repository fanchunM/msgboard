<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>公众留言板审核</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<script type="text/javascript">
	var ctxMobile = '${ctxMobile}';
	var ctxjt = '${ctxjt}';
	//通知webview(snap)执行我们自己的退回逻辑。
	//WebSnapBridgeCordova.judgeGoBack(true);
	//webview(snap)会调用该方法处理回退
	function doCallBack() {
		/*
		 * @param flag 0:执行退回操作 1:不执行退回操作 2:关闭应用页面 
		 */
		//WebSnapBridgeCordova.webviewGoBack(2);
	}
</script>
<script src="resources/admin/js/common.js"></script>
<link rel="stylesheet" href="resources/mobilePhoneApproval/layui/css/layui.css" media="all" />
<link rel="stylesheet" href="resources/mobilePhoneApproval/css/global.css" media="all" />
<link rel="stylesheet" href="resources/mobilePhoneApproval/css/mobile_gufeng.css" media="all" />
</head>
<style type="text/css">
.li-b {
	border: 1px solid #c9c9c9;
	background: url(resources/msgboardImages/b.png) no-repeat bottom #FFF;
	border-radius: 10px;
	padding: 1em 1em 0.2em 1em;
	margin-bottom: 1em;
	box-shadow: 1px 2px 3px #a9b7b7;
}

.todo-theme {
	color: #FF5722;
	font-size: 20px;
	font-weight: bolder;
	padding-left: 0.2em;
	padding-top: 0.4em;
}

.todo-con {
	font-size: 15px;
	color: #2F4056;
	padding-top: 0.5em;
}

.src-con {
	font-size: 12px;
	color: #5FB878;
}

.base-info {
	color: #009688;
	font-size: 12px;
}
</style>
<body style="position: absolute; top: 0px; left: 0px; right: 0px; bottom: 0px; padding: 0; margin: 0;">
<%-- 	<input type="hidden" value="${userId}" id="userId"> --%>
	<div class="main layui-clear">
		<div class="wrap">
			<div class="fly-panel" style="background-color: #EEEEEE;" pad20>
				<div class="layui-form-item">
					<div class="layui-row">
						<div class="layui-col-xs8">
						    <input type="text" id="keyword"  placeholder="请输入关键词" autocomplete="off" class="layui-input" style="width:90%;">			
						</div>
						<div class="layui-col-xs4">
							<button class="layui-btn" lay-filter="search" lay-submit>
								<i class="layui-icon">&#xe615;</i> 搜索
							</button>						
						</div>
					</div> 
				</div>
				<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief"
					style="background-color: #EEEEEE;">
					<ul class="layui-tab-title" style="text-align: center">
						<li lay-id="todo" class="layui-this">待办留言</li>
						<li lay-id="done">已办留言</li>
						<li lay-id="commentTodo">待办评论</li>
						<li lay-id="commentDone">已办评论</li>
					</ul>
					<div class="layui-tab-content">
						<!--待办留言 -->
						<div class="layui-tab-item layui-show" id="todoInfo">
							<div id="todoInfoNull"></div>
							<div>	
								<ul class="fly-list" id="todoList" style="background-color: #EEEEEE;"></ul>
								<hr class="">
								<div id="moreDoInfo" class="layui-form-item"
									style="text-align: center">
									<button class="layui-btn" lay-filter="nextLinks" lay-submit>更多信息</button>
								</div>
							</div>						
						</div>
						<!-- 已办留言 -->
						<div class="layui-tab-item" id="doneInfo">
							<div id="doneInfoNull"></div>
							<div>	
								<ul class="fly-list" id="doneList" style="background-color: #EEEEEE;"></ul>
								<hr class="">
								<div id="moreDoneInfo" class="layui-form-item"
									style="text-align: center">
									<button class="layui-btn" lay-filter="forbitLinks" lay-submit>
										更多信息</button>
								</div>
							</div>							
						</div>
						<!--待办评论 -->
						<div class="layui-tab-item" id="commentTodoInfo">
							<div id="doneInfoNull"></div>
							<div>	
								<ul class="fly-list" id="commentTodoList" style="background-color: #EEEEEE;"></ul>
								<hr class="">
								<div id="moreDoneInfo" class="layui-form-item"
									style="text-align: center">
									<button class="layui-btn" lay-filter="nextLinksComments" lay-submit>
										更多信息</button>
								</div>
							</div>							
						</div>
						<!--已办评论 -->
						<div class="layui-tab-item" id="commentTodoInfo">
							<div id="doneInfoNull"></div>
							<div>	
								<ul class="fly-list" id="commentDoneList" style="background-color: #EEEEEE;"></ul>
								<hr class="">
								<div id="moreDoneInfo" class="layui-form-item"
									style="text-align: center">
									<button class="layui-btn" lay-filter="forbitLinksComments" lay-submit>
										更多信息</button>
								</div>
							</div>							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="resources/mobilePhoneApproval/layui/layui.js"></script>
	<script type="text/javascript" src="resources/mobilePhoneApproval/js/newDaiBanListPage.js"></script>
</body>
</html>