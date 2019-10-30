<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>留言发布 - 广济南社区</title>
	<meta http-equiv="X-UA-Compatible" content="IE=8,IE=edge,chrome=1">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=0">
	<link rel="shortcut icon" href="resources/msgboardImages/favicon.ico" type="image/x-icon"/>
	<!-- Bootstrap -->
	<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="resources/layui/css/layui.css" rel="stylesheet">
	<link rel="stylesheet" href="resources/css/common/style.css">
	<link rel="stylesheet" href="resources/easyUI/themes/metro-blue/easyui.css"/>
	<link rel="stylesheet" href="resources/css/message/messageRelease.css">
	<link href="resources/css/common/mobile-menu.css" rel="stylesheet">
	<link rel="stylesheet" href="resources/css/common/common.css">
	<script type="text/javascript" src="resources/js/commonJs/jquery-1.8.3.min.js"></script>
	<script src="resources/bootstrap/js/bootstrap.min.js"></script>
	<script src="resources/easyUI/jquery.easyui.min.js"></script>
	<script src="resources/layui/layui.js"></script>
	<script src="resources/layui/layui.all.js"></script>
	<script src="resources/js/message/messageRelease.js"></script>
	<script src="resources/js/common/common.js"></script>
	<script src="resources/js/common/mobile-menu.js"></script>
	<link rel="stylesheet" href="resources/bootstrap/Font-Awesome-3.2.1/css/font-awesome.min.css">
	<script type="text/javascript" src="resources/js/common/basic.js"></script>
	<script type="text/javascript" src="resources/lrz.all.bundle/lrz.all.bundle.js"></script>
</head>
<style type="text/css">
	 @media only screen and (max-width: 990px) {
	 .mn-box {
	 	float: left;
/* 	    border: solid #ccc 1px; */
	    width: 100%;
	    background: #fff;
	    margin-top: 15px;
	    margin-bottom: 15px;
	    padding: 0px 5% 0 5%;
	    }
	 .box-message {
	    display: block;
	    padding: 0px 0px 20px 0px;
	    text-align: left;
		}
	 } 
</style>

<!-- 手机端多图片上传样式 -->
<!-- <style type="text/css">
        .uploader-list {
            margin-left: -15px;
        }
 
        .uploader-list .info {
            position: relative;
            margin-top: -25px;
            background-color: black;
            color: white;
            filter: alpha(Opacity=80);
            -moz-opacity: 0.5;
            opacity: 0.5;
            width: 100px;
            height: 25px;
            text-align: center;
            display: none;
        }
 
        .uploader-list .handle {
            position: relative;
            background-color: black;
            color: white;
            filter: alpha(Opacity=80);
            -moz-opacity: 0.5;
            opacity: 0.5;
            width: 100px;
            text-align: right;
            height: 25px;
            margin-bottom: -25px;
            display: none;
        }
 
        .uploader-list .handle span {
            margin-top: 5px;
            margin-right: 5px;
        }
 
        .uploader-list .handle span:hover {
            cursor: pointer;
        }
 
        .uploader-list .file-iteme {
            margin: 12px 0 0 15px;
            padding: 1px;
            float: left;
        }
</style> -->
<style type="text/css">
	@media only screen and (min-width: 992px) {
		.tele_style {
			width:10%;
			text-align: center
		}
		.teleinput_style {
		width:40%
		}
	} 
	
</style>
<jsp:include page="../../common/head.jsp"></jsp:include>
<jsp:include page="../../common/mobileHead.jsp"></jsp:include>
<script type="text/javascript">
function closeMainImg(a){
	var imgId = $(a).next().val();
	 $.ajax({
        type:"POST",
        url:'message/delPic.do?id='+imgId,
        success:function(data){
        	$(a).parent().remove();
        }
     });
}
function saveImage(){
	var num = $(".imgUpload").length;
	if(num>4){
		alert("已达最大上传数量限制!");
		return;
	}else{
		var dom = document.getElementById("file1").files[0];
		debugger;
		var fileSize = dom.size/(1024*1024);
    	if(fileSize>10){
    		alert("请上传不大于10M的图片");
    		return false;
    	}
    	var file = $("#file1").val();
    	var point = file.lastIndexOf(".");
    	var fileName = file.substr(point);
    	if((fileName != ".jpg") && (fileName != ".jpeg") && (fileName != ".png") && (fileName != ".Jpg") && (fileName != ".JPG") && (fileName != ".Jpeg") && (fileName != ".JPEG") && (fileName != ".PNG")){
    		alert("请上传正确的图片格式");
    		return false;
    	}
        lrz(dom,{width: 640,quality:0.6})
        .then(function (rst) {
        	$("#linshi").val(rst.base64);
        	$("#fileName").val(dom.name);
        	$('#linshiForm').form('submit',{
                url: 'message/uploadImage.do',
                onSubmit: function(){
                    return $(this).form('validate');
                },
                success: function(data){
             	   var data = eval('('+data+')');
 	         	   if(data.error != null) {
 	         		   alert(data.error);
 	         		  if(!!window.ActiveXObject || "ActiveXObject" in window) {    // 此处判断是否是IE
 	         			    $('#file1').replaceWith($('#file1').clone(true));
 	         			} else {
 	         			    $('#file1').val('');
 	         			}
 	         		   return;
 	         	   }
          		   var father = $("#imageDiv");
              			   var son = '<div class="imgUpload" style="position: relative;float:left">'
              			   			+'<img class="closeImg" src="resources/msgboardImages/关闭.png" style="width:30px;height:30px;position:absolute;left:45px;z-index:999;" onclick="closeMainImg(this)">'
              				   		+'<input id="imgId" type="hidden" value="'+data.id+'">'
              			   			+'<div style="height:60px;width:60px;float:left;border-radius:5px;margin-top:16px;margin-right:15px;">'
//              			 			+'<a href="resources/upload/'+data.address+'" target="_blank">'
 									+'<a>'
              			   			+'<img src="resources/upload/'+data.address+'" style="width:100%; height:100%;border-radius:5px">'
             			 			+'</a>'
              			   			+'</div>'
             			 			+'</div>';
 	 				father.append(son);
 	                }
    		});
        })
	}
}
// function saveImage(){
// 	var num = $(".imgUpload").length;
// 	if(num>4){
// 		alert("已达最大上传数量限制!");
// 		return;
// 	}else{
// 		 $('#form1').form('submit',{
// 	            url: 'message/uploadImage.do',
// // 	            type:"POST",
// 	            onSubmit: function(){
// 	            	var fileSize = $("#file1")[0].files[0].size/(1024*1024);
// 	            	if(fileSize>10){
// 	            		alert("请上传不大于10M的图片");
// 	            		return false;
// 	            	}
// 	            	var file = $("#file1").val();
// 	            	var point = file.lastIndexOf(".");
// 	            	var fileName = file.substr(point);
// 	            	if((fileName != ".jpg") && (fileName != ".jpeg") && (fileName != ".png") && (fileName != ".Jpg") && (fileName != ".JPG") && (fileName != ".Jpeg") && (fileName != ".JPEG") && (fileName != ".PNG")){
// 	            		alert("请上传正确的图片格式");
// 	            		return false;
// 	            	}
// 	                return $(this).form('validate');
// 	            },
// 	            success: function(data){
// 	         	   var data = eval('('+data+')');
// 	         	   if(data.error != null) {
// 	         		   alert(data.error);
// 	         		  if(!!window.ActiveXObject || "ActiveXObject" in window) {    // 此处判断是否是IE
// 	         			    $('#file1').replaceWith($('#file1').clone(true));
// 	         			} else {
// 	         			    $('#file1').val('');
// 	         			}
// 	         		   return;
// 	         	   }
//          		   var father = $("#imageDiv");
//              			   var son = '<div class="imgUpload" style="position: relative;float:left">'
//              			   			+'<img class="closeImg" src="resources/msgboardImages/关闭.png" style="width:30px;height:30px;position:absolute;left:45px;z-index:999;" onclick="closeMainImg(this)">'
//              				   		+'<input id="imgId" type="hidden" value="'+data.id+'">'
//              			   			+'<div style="height:60px;width:60px;float:left;border-radius:5px;margin-top:16px;margin-right:15px;">'
// //             			 			+'<a href="resources/upload/'+data.address+'" target="_blank">'
// 									+'<a>'
//              			   			+'<img src="resources/upload/'+data.address+'" style="width:100%; height:100%;border-radius:5px">'
//             			 			+'</a>'
//              			   			+'</div>'
//             			 			+'</div>';
// 	 				father.append(son);
// 	                }
// 	        });
// 		}
//    }
</script>
<script type="text/javascript">
	$(function(){
		$("#file1").on("change",function(){
			saveImage();
		});
	})
</script>
<input type="hidden" id="userId" value="${person.id }">
<input type="hidden" id="phoneSec" value="${person.id }">
<input type="hidden" id="userNameState" value="${userNameState }">
<body>
<!-- <div style="margin-bottom:15px"> -->
<div class="container theme-showcase mine-main-container" role="main">
	<div class="mn-page-header">
		<div class="mn-page-header-title">
			<span class=tit5>留言发布</span>
		</div>
		<div class="mn-page-header-crumbs"></div>
	</div>
	<div class="mn-box" style="border:none">
        <div class="box-message">
            <div class="send">
                <div class="sendbox">
                	<div style="margin-top:20px">
                		<h2>留言信息</h2>
                		<div class="div-line"></div>
                	</div>
                    <form action="/service/guide/message.html#" id="myform" method="post" class="messages">
							<div style="overflow: hidden">
								<div class="messlist div-title msg-text col-xs-12 col-sm-2 col-md-1 text-left">
		                        	<i class="icon-fixed-width icon-type"></i>标题
		                        </div>
								<div class="messlist col-xs-12 col-sm-4 col-md-7 text-left">
		                        	<input type="text" id="email" maxlength="50">
<!-- 		                        	<div class="reminder" style="font-size: 13px;">留言标题长度不超过50字</div> -->
		                        </div>
		                        <div class="messlist msg-text div-title col-xs-12 col-sm-2 col-md-1 text-left" style="line-height:28px">
		                        	<i class="icon-fixed-width icon-type"></i>类型
		                        </div>
		                        <div class="messlist col-xs-12 col-sm-4 col-md-3 text-left">
		                        	<select name="attribute1" style="height: 28px;width:100%;border: #ccc 1px solid;">
											<option value="" selected="selected">请选择</option>
											<option value="咨询">咨询</option>
											<option value="建议">建议</option>
											<option value="举报">举报</option>
											<option value="投诉">投诉</option>
											<option value="报修">报修</option>
											<option value="求助">求助</option>
											<option value="灌水">灌水</option>
											<option value="其他">其他</option>
									</select> 
		                        </div>
	                        </div>
	                        <div class="messlist div-title msg-text col-xs-12 col-sm-12 col-md-1 text-left">
                        		<i class="icon-fixed-width icon-type"></i>内容
	                        </div>
	                        <div class="messlist col-xs-12 col-sm-12 col-md-11 text-left">
	                        <!--placeholder="为了便于我们更好地为您服务，请详细叙述您的具体诉求，或者描述事件发生的具体时间、地点、经过及涉及人员"-->
	                        	<textarea style="width:100%" id="content" ></textarea>
<!-- 	                        	<p  id="show" style="margin: 0px">&nbsp;您还可以输入：<span id="sid">2000</span>&nbsp;&nbsp;字</p> -->
	                        </div>
	                        <div class="messlist div-title msg-text col-xs-12 col-sm-2 col-md-1 text-left" style="padding-right:0px">
	                        	<i class="icon-fixed-width icon-type"></i>提交人
	                        </div>
	                        <div class="messlist msg-value col-xs-12 col-sm-4 col-md-5 text-left">
	                        	<input  type="text"  disabled="disabled" style="color:#898a8c;width:100%" value="${person.userName }" id="userName" maxlength="20">
	                        </div>
	                        <div class="messlist div-title msg-text col-xs-12 col-sm-2 text-left tele_style" style="padding-right:0px">
	                        	<i class="icon-fixed-width icon-type"></i>联系方式
	                        </div>
	                        <div class="messlist msg-value col-xs-12 col-sm-4 teleinput_style text-left">
	                        	<input onkeyup="checkPhone();" type="text" value="${person.phone }" id="phone" maxlength="50">
<!-- 	                        	<div class="reminder" style="font-size: 13px;margin-top:5px">手机号为必填项,默认为注册手机号,可以修改</div> -->
	                        </div>
<!-- 	                        <div class="messlist div-title msg-text col-xs-12 col-sm-2 col-md-1 text-left" style="padding-right: 0px"> -->
<!-- 	                        	<i class="icon-fixed-width icon-type"></i>验证码 -->
<!-- 	                        </div> -->
<!-- 	                        <div class="messlist col-xs-12 col-sm-10 col-md-11 text-left"> -->
<!-- 	                        	<input type="text" style="width:100px" placeholder="验证码" id="validateCode" name="validateCode"> -->
<!-- 	                            <img style="margin-left:20px" id="randomImage" src="message/validateCode.do" height="27" alt="点击刷新验证码" onclick="changeCode();" class="yzmimg"> -->
<!-- 	                        </div> -->
	                        <div class="messlist reminder col-xs-12 col-sm-12 col-md-12 text-left" style="font-size: 15px;margin-top: 10px;">
                    			<span class="text-red">温馨提示&nbsp;:&nbsp;</span>您可在此针对苏州轨道交通规划、建设、运营、资源开发、物业保证及企业管理等工作，进行咨询、建议、投诉、举报及求助。为提高处理效率，请详细描述您的具体诉求，可能涉及的时间、地点及人员等信息。提交时请留下联系电话，便于我们核实情况。标题长度不超过50字，内容长度不超过2000字。感谢您的理解与支持。
                    		</div>
                        </div>
                    </form>
                </div>
                <div class="sendbox" style="margin-top:20px">
                	<div>
                		<h2>附件</h2>
                		<div class="div-line"></div>
                	</div>
                	<div class="messlist  col-xs-12 col-sm-12 col-md-12 text-left" style="padding-left: 15px;overflow: hidden">
                    	<div id="imageDiv" style="float:left">
                    	</div>
                    	<div id="test1" class="img-upload">
			  				<img src="resources/msgboardImages/上传图片.png" style="width:100%;height:100%;float:left">
			  				<form id="form1" method="post" enctype="multipart/form-data">
								<input accept="image/*" id="file1" name="myfiles"  type="file"  style="display: block;position: absolute;width: 60px;height: 60px;font-size: 100px;opacity: 0;filter:alpha(opacity=0);float:left;padding-left:0px;z-index:999">
                   			</form>
                   			<form id="linshiForm" method="POST" enctype="multipart/form-data">
                   				<input type="hidden" id="linshi" name="linshi">
                   				<input type="hidden" id="fileName" name="fileName">
                   			</form>
			  			</div>
                    </div>
                    <!--手机端多图片上传 -->
<!--                     <div class="hidden-md hidden-lg"> -->
<!-- 						<div class="layui-upload"> -->
<!-- 						  <button type="button" class="layui-btn" id="test2">上传</button>  -->
<!-- 						  <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;"> -->
<!-- 						    预览图： -->
<!-- 						    <div class="layui-upload-list uploader-list" id="uploader-list" style="overflow:hidden"></div> -->
<!-- 						 </blockquote> -->
<!-- 						</div> -->
<!--                     </div> -->
                   <div class="messlist reminder col-xs-12 col-sm-12 col-md-12 text-left" style="font-size: 15px">
               			<span class="text-red">温馨提示&nbsp;:&nbsp;</span>支持.jpg .jpeg .png格式照片,最多五张。
               		</div>
               	  	<div class="messlist row" style="margin-top:15px;padding: 0px">
						<div class="col-sm-3 col-md-5 text-left"></div>
                        <div class="col-xs-12 col-sm-5 col-md-2 text-center" style="padding: 0px;margin-top: 25px;">
<!--                             <input type="button" style="width:100%;background:#7B0002;color:#fff;border:0px" value="提交" onclick="messageSend()"> -->
                            <button style="width:100%" class="btn btn-large btn-primary" onclick="messageSend()">提&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
   </div>
</div>
	<jsp:include page="../../common/foot.jsp"></jsp:include>
</body>
<!-- 多图片上传勿删 -->
<!-- <script> 
// $(function(){
// 	layui.use('upload', function(){
// 		  var $ = layui.jquery,
// 		  upload = layui.upload;
// 		  //多图片上传
// 		  upload.render({
// 		    elem: '#test2'
// 		    ,url: 'message/uploadImage.do'
// 		    ,multiple: true,
// 		    field:'myfiles'
// 		    ,done: function(res){
// 		    	$('#uploader-list').append(
// 	                    '<div id="" class="file-iteme">' +
// 	                    '<div class="handle"><span class="glyphicon glyphicon-trash"></span></div>' +
// 	                    '<img style="width: 100px;height: 100px;" src="resources/upload/'+ res.address +'">' +
// 	                    '</div>'
// 	                );
// 		    	}
// 		  });
// 		});
// 		 $(document).on("mouseenter mouseleave", ".file-iteme", function(event){
// 	        if(event.type === "mouseenter"){
// 	            //鼠标悬浮
// 	            $(this).children(".info").fadeIn("fast");
// 	            $(this).children(".handle").fadeIn("fast");
// 	        }else if(event.type === "mouseleave") {
// 	            //鼠标离开
// 	            $(this).children(".info").hide();
// 	            $(this).children(".handle").hide();
// 	        }
// 	    });
// 		 $(document).on("click", ".file-iteme .handle", function(event){
// 	         $(this).parent().remove();  
// 	     });
// })
</script> -->
</html>