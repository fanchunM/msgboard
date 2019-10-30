<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" isELIgnored="false"%>
<!-- <script src="resources/easyUI/jquery.easyui.min.js"></script> -->

<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
	<div style="margin-left: 10px">
	<form id="fm" method="post">
	<input type="hidden" id="fId" name="fId" value="${Group.id }">
			<div class="fitem" style="margin-top: 30px;text-align: center;">
                 <span>权限组名</span>   <input style="text-align:left;font-size: 14; vertical-align: middle;" rows="5" value="${Group.name }" id="group"  name="group" class="textbox">
           </div>
    </form>
	</div>
	</div>
	<div id="dlg-buttons" region="south" border="false" style="text-align:center;height:30px;line-height:30px;">
        <a href="javascript:void(0)" class="easyui-linkbutton a-button" onclick="savePermissionGroup()">保存</a>
    </div>
</div>	
	