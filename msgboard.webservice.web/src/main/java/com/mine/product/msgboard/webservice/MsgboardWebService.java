package com.mine.product.msgboard.webservice;
import java.util.Map;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mine.product.szmtr.msgboard.message.dto.MessageDto;
import com.mine.product.szmtr.msgboard.message.service.IMessageService;
import com.vgtech.platform.common.utility.VGUtility;


/**
 * webservice 
 */
@WebService
public class MsgboardWebService implements IMsgboardWebservice{
	private static final Logger logger = LoggerFactory.getLogger(MsgboardWebService.class);
	@Autowired
	private IMessageService messageService;
	@Override
	public String getReplyFromPsp(Map<String, String> map) {
		logger.info(map.get("messageId")+"=="+map.get("commentStr"));
		String messageId = map.get("messageId");
		MessageDto messageDto = messageService.getMessageById(messageId);
		messageDto.setStatus("3");
		MessageDto replyDto = new MessageDto();
		replyDto.setMsgType(IMessageService.MESSAGE_TYPE.回复);
		replyDto.setParentId(messageId);
		replyDto.setCommentStr(map.get("commentStr"));
		MessageDto dto = messageService.getReplyByMessageId(messageId);
		if(!VGUtility.isEmpty(dto)) {
			replyDto.setId(dto.getId());
			messageService.updateMessage(replyDto);
		}else {
			messageService.createMessage(replyDto);
		}
		messageDto.setReplyStatus(IMessageService.REPLY_STATUS.已答复);
		messageService.updateMessage(messageDto);
		return "success";
	}
}
