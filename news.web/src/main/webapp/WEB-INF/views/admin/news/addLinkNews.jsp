<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" isELIgnored="false"%>
<%-- <%@ include file="/WEB-INF/jsp/include.jsp"%> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style type="text/css">
	.row{
		margin:0px;
	}
</style>
<div class="easyui-layout" data-options="fit:true,border:false" >
<div data-options="region:'center',border:false" title="" style="width: auto;padding:10px;margin-bottom:4px;border:0px solid #ccc;">
        <form id="fm" method="post" novalidate  >
        <input type="hidden" id="nId" name="id" value="${newsDto.id }">
            <div class="row" style="margin-top: 5px">
				<div class="col-md-1 text-center">
					<div class="datagrid-toolbar-span-reply-textNews">新闻标题</div>
				</div>
				<div class="col-md-5 text-left">
					<input name="title" value="${newsDto.title }"
						class="easyui-validatebox" style="width: 100%" required="true"
						data-options="required:true">
				</div>
				<div class="col-md-1 text-center">
					<div class="datagrid-toolbar-span-reply-textNews">新闻副标题</div>
				</div>
				<div class="col-md-5 text-left">
					<input name="subTitle" value="${newsDto.subTitle }"
						style="width: 100%" class="easyui-validatebox" />
				</div>
			</div>
              <!--<div style="margin-top: 5px">
                <label>话　　题:</label>
                <input type="button" value="添加话题">
                <!-- <input name="newsKeyword1" class="easyui-validatebox"  style="width:15%" />
                <input name="newsKeyword2" class="easyui-validatebox"  style="width:15%" />
                <input name="newsKeyword3" class="easyui-validatebox"  style="width:15%" /> -->
                <!-- <label>缩略图:</label>
                <input name="webNewspic" id="webNewspic" type="text" style="width:24%" readonly>
                <a href="#" class="easyui-linkbutton" onclick="openUpload()">上传图片</a> 
              </div> -->     
             <div class="row" style="margin-top: 5px">
				<div class="col-md-1 text-center">
					<div class="datagrid-toolbar-span-reply-textNews">作 者</div>
				</div>
				<div class="col-md-2 text-center">
					<input name="author" value="${newsDto.author }" style="width: 100%">
				</div>
				<div class="col-md-1 text-center">
					<div class="datagrid-toolbar-span-reply-textNews">部 门</div>
				</div>
				<div class="col-md-2 text-center">
					<input name="deptName" value="${newsDto.deptName }"
						style="width: 100%">
				</div>
				<div class="col-md-1 text-center">
					<div class="datagrid-toolbar-span-reply-textNews">栏目置顶</div>
				</div>
				<div class="col-md-2 text-center">
					<c:if test="${newsDto.id != null}">
		               	 &nbsp;&nbsp;
		                 <input type="radio" name="topStatus" value="1"
							<c:if test="${newsDto.topStatus==true }">checked</c:if>>有
		                 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
		                 <input type="radio" name="topStatus" value="0"
							<c:if test="${newsDto.topStatus==false }">checked</c:if>>无
			             </c:if>
					<c:if test="${newsDto.id == null}">
			                	&nbsp;&nbsp;
			             <input type="radio" name="topStatus" value="1">有
			            &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			             <input type="radio" name="topStatus" value="0" checked>无
		             </c:if>
				</div>
				<div class="col-md-1 text-center">
					<div class="datagrid-toolbar-span-reply-textNews">发布日期</div>
				</div>
				<div class="col-md-2 text-center">
					<input name="publishTime" id="publishTime"
						class="easyui-datetimebox" style="width: 160px;" value="${pTime }"
						data-options="required:true">
				</div>
			</div>
              
            <div class="row" style="margin-top: 5px">
				<div class="col-md-1 text-center">
					<div class="datagrid-toolbar-span-reply-textNews">新闻外链</div>
				</div>
				<div class="col-md-11 text-center">
					<input name="linkURL" value="${newsDto.linkURL }" style="width:100%" class="easyui-validatebox" required="true">
				</div>
			</div>
        </form>
   </div>
    <div data-options="region:'south',border:false" style="text-align:center;height: 30px">
        <a href="javascript:void(0)" class="easyui-linkbutton a-button" onclick="saveNews()"><i class="icon-fixed-width"></i>保存</a>
        <!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"onclick="clearForm()">重置</a> -->
    </div>
</div>  
    <%-- 
    <script type="text/javascript">
    
    
    
     var editor = null;
     //初始化表格   
     $(function(){
    	// $('#createTime').datetimebox('setValue', '9999');
    	 
    
     });
        
        function saveUser(){
            $('#fm').form('submit',{
                url: '<%=basePath %>/news/create.do',
                onSubmit: function(){
                    return $(this).form('validate');
                },
                success: function(result){
                	$.messager.alert('My Title','新闻创建成功','info'); 
                    var result = eval('('+result+')');
                    if (result.errorMsg){
                        $.messager.show({
                            title: 'Error',
                            msg: result.errorMsg
                        });
                    } else {
                    	
                    	parent.$('#dg').datagrid('reload');
                     	$('#dd').dialog('close');
                    }
                }
            });
        }
       
       
        function clearForm(){
            $('#fm').form('clear');
           
        	
        }
        
        function openUpload(){
      	  $('<div/>').dialog({   
          	    title: '图片上传',
          	    id:'dd2',
          	    width: 520,   
          	    height: 360,
          	    closed: false,   
          	    href: '<%=basePath%>/news/preUploadpic.do',   
          	    modal: true,
          	    buttons :'#bb' ,
      			onClose : function() {
      				$(this).dialog('destroy');
      			}
          	});   
        }
 
    </script>
  --%>