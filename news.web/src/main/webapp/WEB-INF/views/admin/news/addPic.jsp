<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" isELIgnored="false"%>
<!-- <link rel="stylesheet" href="resources/easyUI/themes/metro-blue/easyui.css" /> -->
<!-- <script type="text/javascript" src="resources/js/commonJs/jquery-1.8.3.min.js"></script> -->
<script src="resources/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript">

 
</script>

<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form1" method="post" enctype="multipart/form-data">
			<table class="formitem" style="margin-top:5px">
 			 <tr >
			 	<td>图片名称：</td>
			 	<td><input name="pName" class="easyui-validatebox" data-options="required:true" />
			 	</td>
			 </tr>
			 <tr >
			 	<td colspan="2"><input id="file1" type="file" name="myfiles">
			 	</td>
			</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons" region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
        <a href="javascript:void(0)" class="easyui-linkbutton a-button" onclick="savePic()"><i class="icon-fixed-width"></i>确定</a>
    </div>
</div>