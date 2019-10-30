<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" isELIgnored="false"%>
<!-- <script src="resources/easyUI/jquery.easyui.min.js"></script> -->
<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow-x: hidden;">
	<div style="margin-left: 10px">
		<input id="groupUserId" type="hidden" value="${GroupUser.id}"/>
		<div class="row">
			<div class="col-md-11 form-level">选择添加用户：
				<input id="addUser"  class="easyui-combobox" data-options="textField:'text',url:'sys/getPermissionUsers',method:'GET',mode:'remote'" >
			</div>
	    </div>
	    <div class="row">
	    	<div class="col-md-11 form-level">
	    		<table id="users"></table>
	    	</div>
	    </div>
	</div>
	</div>
	<div id="dlg-buttons" region="south" border="false" style="text-align:right;height:30px;line-height:30px; ">
        <a  class="easyui-linkbutton" style="margin-right:10px;"  onclick="closeWindow()">关闭</a>
    </div>
</div>	
<script src="resources/admin/js/sys/permissionUser.js"></script>
<style>
	.form-level{
		font-weight: 400;
    	font-size: 14px;
	}
	.form-value{
		font-size: 14px;
    	color: gray;
	}
	.row{
		margin-top: 5px;
	}
</style>
	