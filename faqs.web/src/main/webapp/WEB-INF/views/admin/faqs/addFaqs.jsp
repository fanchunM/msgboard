<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" isELIgnored="false"%>
<!-- <script src="resources/easyUI/jquery.easyui.min.js"></script> -->

<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
	<div style="margin-left: 10px">
	<form id="addFaqsForm" method="post" novalidate>
			<%-- 
			<div class="fitem">
			排序：<input type="text" id="px" name="orderIndex" value="${faqsDto.orderIndex}" class="textbox">
			</div>
			<br> 
			--%>
			<input type="hidden" id="fId" name="id" value="${faqsDto.id }">
           <div class="fitem">
               <label style="width: 200px;float: left">问题:</label>
               <input class="textbox" style="font-size: 14px;width: 95%;line-height:10px;" rows="5" id="question" class="easyui-validatebox" name="question" value="${faqsDto.question }">
           </div>
           <div class="fitem">
               <label>解答:</label>
               <textarea style="text-align:left;font-size: 14;width: 610px" rows="5" cols="80" id="answers" name="answers" >${faqsDto.answers }</textarea>
           </div>
    </form>
	</div>
	</div>
	<div id="dlg-buttons" region="south" border="false" style="text-align:center;height:30px;line-height:30px;">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveFaqs()"><i class="icon-fixed-width"></i>保存</a>
    </div>
</div>	
	