<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" isELIgnored="false"%>
<!-- <script src="resources/easyUI/jquery.easyui.min.js"></script> -->

<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
	<div style="margin-left: 10px">
	<form id="fm" method="post">
			<%-- 
			<div class="fitem">
			排序：<input type="text" id="px" name="orderIndex" value="${faqsDto.orderIndex}" class="textbox">
			</div>
			<br> 
			--%>
			<input type="hidden" id="fId" name="fId" value="${replyDto.id }">
			<div class="fitem">
               <label>回复标题:</label>
               <textarea style="text-align:left;font-size: 14;width: 610px" rows="5" id="engName" name="engName" class="easyui-validatebox" required="true">${replyDto.engName }</textarea>
           </div>
           <div class="fitem">
               <label style="width: 200px;float: left">回复内容:</label>
               <textarea style="text-align:left;font-size: 14;width: 610px" rows="5" id="chsName" name="chsName" class="easyui-validatebox" required="true">${replyDto.chsName }</textarea>
           </div>
    </form>
	</div>
	</div>
	<div id="dlg-buttons" region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
        <a href="javascript:void(0)" class="easyui-linkbutton a-button" onclick="saveReply()"><i class="icon-fixed-width"></i>保存</a>
    </div>
</div>	
	