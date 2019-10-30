package com.mine.product.szmtr.msgboard.message.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mine.product.szmtr.msgboard.message.dto.MessageDto;
import com.mine.product.szmtr.msgboard.message.dto.SysDictionaryDto;
import com.mine.product.szmtr.msgboard.message.dto.ThemeDto;
import com.mine.product.szmtr.msgboard.message.service.IMessageService;
import com.mine.product.szmtr.msgboard.message.service.IMessageService.APPROVAL_STATUS;
import com.mine.product.szmtr.msgboard.message.service.IsysDictionaryService;
import com.vgtech.platform.common.utility.VGUtility;

@Controller
public class MeaasgeAdminPageController {
	private static final Logger logger = LoggerFactory.getLogger(MeaasgeAdminPageController.class);
	
	@Autowired
	private IMessageService messageService;
	@Autowired
	private IsysDictionaryService sysDictionaryService;
	
	@GetMapping("/messageList")
	public String enterMessageListPage(Integer approvalStatus,Integer replyStatus,Integer viewStatus,ModelMap modelMap) {
		logger.info("Enter MessageList Page");
		modelMap.put("dReplyStatus", replyStatus);
		modelMap.put("dApprovalStatus",approvalStatus);
		modelMap.put("viewStatus",viewStatus);
		return "admin/message/messageList";
	}
	
	@GetMapping("/toMessageDetail")
	public String toReplyPage(ModelMap modelMap,String id,String messageType) {
		modelMap.put("messageType",messageType);
		MessageDto dto = messageService.getMessageById(id);
		if(dto.getMsgType()==IMessageService.MESSAGE_TYPE.评论) {
			MessageDto	newDto = messageService.getMessageById(dto.getParentId());
			modelMap.put("messageDto", newDto);
			MessageDto reply = messageService.getReplyByMessageId(dto.getParentId());
			if(!VGUtility.isEmpty(reply)) {
				modelMap.put("replyDto", reply);
			}
		}else {
			modelMap.put("messageDto", dto);
			if(APPROVAL_STATUS.未处理.name().equals(dto.getApprovalStatus().name())) {
				modelMap.put("approvalStatusValue",APPROVAL_STATUS.未处理.name());
			}else {
				modelMap.put("approvalStatusValue", APPROVAL_STATUS.审核通过.name().equals(dto.getApprovalStatus().name()));
			}
			MessageDto reply = messageService.getReplyByMessageId(id);
			if(!VGUtility.isEmpty(reply)) {
				modelMap.put("replyDto", reply);
			}
		}
		
		logger.info("Enter messageDetail Page");
		return "admin/message/messageDetail";
	}
	
	@GetMapping("/commentList")
	public String enterCommentListPage() {
		logger.info("Enter comment List Page");
		return "admin/message/commentList";
	}
}
