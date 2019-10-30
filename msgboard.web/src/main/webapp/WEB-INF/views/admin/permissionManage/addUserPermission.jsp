<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <title></title>
    <meta name="description" content="Static &amp; Dynamic Tables" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="resources/assets/css/bootstrap.min.css" />
    <!-- page specific plugin styles -->
    <!-- ace styles -->
    <link rel="stylesheet" href="resources/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="resources/assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
    <![endif]-->
    <link rel="stylesheet" href="resources/assets/css/ace-skins.min.css" />
    <link rel="stylesheet" href="resources/assets/css/ace-rtl.min.css" />
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="resources/assets/css/ace-ie.min.css" />
    <![endif]-->
    <link rel="stylesheet" href="resources/bootstrap/Font-Awesome-3.2.1/css/font-awesome.min.css">
    <link rel="stylesheet" href="resources/easyUI/themes/metro-blue/easyui.css" />
    <link rel="stylesheet" href="resources/easyUI/themes/icon.css" />
    <link rel="stylesheet" href="resources/admin/css/commonForAdmin.css" />
    <!-- inline styles related to this page -->
    <!-- ace settings handler -->
    <script src="resources/assets/js/ace-extra.min.js"></script>
    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->
    <!--[if lte IE 8]>
    <script src="resources/assets/js/html5shiv.min.js"></script>
    <script src="resources/assets/js/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
        td{ vertical-align:middle !important;}
        .gray{ background-color:#F8F8F8;}
        .row td{ padding:5px 8px !important; min-height:35px; vertical-align:middle; font-size:14px;}
        .row input,.row select,.row textarea{ border-width:1px !important;margin:1px 0px;}
    </style>
</head>
<style>
    .checkbox, .checkbox + .checkbox {
        margin-top: 10px;
        margin-bottom: 10px;
    }
	.input-style {
		margin-top:5px
	}
    body {
        width: 1000px;
        margin: 0px auto;
        padding-top: 5px;
    }
    .panel-head{
    	text-align: center;
    	font-size: 18px;
    }
    .panel-body{
    	text-align: center;
    	font-size: 15px;
    	overflow:hidden
    }
</style>

<body class="no-skin" style="background-color:#FFF;">
<!--<input id="userId" type="hidden" value=th:text="${userId}"/>-->
<input id="userId" type="hidden" value="${userId }"/>
<input id="sysMark" type="hidden" value="${sysMark }"/>
<div style="text-align: center">
    <h2>权限组${userName}的权限</h2>
</div>
<c:forEach items="${meunMap}" var="entry">
<div name="menuPermissionDiv">
    <div class="panel panel-primary">
        <div class="panel-heading panel-head">菜单</div>
        <div class="panel-body container-fluid">
        	<c:forEach items="${entry.value}" var="titleMap">
                <div class="row" style="margin-top:10px">
                    <div class="col-sm-12 col-md-12 col-lg-12">父菜单-${titleMap.key}</div>
                </div>
                <div class="row" style="margin-top:10px">
                	<c:forEach items="${titleMap.value}" var="dto">
                        <div class="col-sm-4 col-md-3 col-lg-2 checkbox">
                            <label>
                            <c:if test="${dto.selected eq true}">
                            	<input style="margin-top:5px" type="checkbox" checked="checked" value="${dto.dto.id}"/>${dto.dto.title}
                            </c:if>
                            <c:if test="${dto.selected eq false}">
                            	<input style="margin-top:5px" type="checkbox" value="${dto.dto.id}"/>${dto.dto.title}
                            </c:if>
                            </label>
                        </div>
                    </c:forEach>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</c:forEach>
<c:forEach items="${permissionMap}" var="entry">
<div name="itemPermissionDiv">
    <div class="panel panel-primary">
        <div class="panel-heading">权限-${entry.key}</div>
        <div class="panel-body container-fluid">
            <div class="row">
            	<c:forEach items="${entry.value}" var="dto">
                    <div class="col-sm-4 col-md-3 col-lg-2 checkbox">
                        <label>
                        <c:if test="${dto.selected eq true}">
                        	<input style="margin-top:5px" type="checkbox" checked="checked" value="${dto.id}"/>${dto.chsName}
                        </c:if>
                        <c:if test="${dto.selected eq false}">
                        	<input style="margin-top:5px" type="checkbox" value="${dto.id}"/>${dto.chsName}
                        </c:if>
                        </label>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</c:forEach>
<div style="margin-top:20px;text-align: center">
	<button style="border-radius: 5px" type="button" class="btn btn-primary" onclick="userPermissionSubmit();">提交</button>
</div>
<script type="text/javascript">
function userPermissionSubmit(){
    var userId = $('#userId').val();
    var sysMark = $('#sysMark').val();
    var menuPermissionList = '';
    $('div[name="menuPermissionDiv"] input:checkbox').each(function(index, element) {
        if($(element).is(':checked')){
            menuPermissionList+=$(element).attr('value')+',';
        }
    });
    var itemPermissionList = '';
    $('div[name="itemPermissionDiv"] input:checkbox').each(function(index, element) {
	if ($(element).is(':checked')) {
	    itemPermissionList += $(element).attr('value') + ',';
	}
    });
    
    $.ajax({
        url: 'sys/update_user_permision_value',
        type: 'POST',
        data: {
            userId: userId,
            sysMark: sysMark,
            menuPermissionValues: menuPermissionList,
            itemPermissionValues: itemPermissionList
        },
        success: function(data){
            alert('保存成功！');
        },
        error: function(data){
//             var result = $.parseJSON(data.responseText);
            alert('获取数据失败');
        }
    });
}
</script>
<script src="resources/easyUI/jquery-1.8.0.min.js"></script>
</body>