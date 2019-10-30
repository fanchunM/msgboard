package com.mine.product.szmtr.msgboard.message.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mine.product.szmtr.msgboard.message.dao.IMessageDao;
import com.mine.product.szmtr.msgboard.message.dao.IMessageLikeDao;
import com.mine.product.szmtr.msgboard.message.dto.MessageLikeDto;
import com.mine.product.szmtr.msgboard.message.model.Message;
import com.mine.product.szmtr.msgboard.message.model.MessageLike;
import com.vgtech.platform.common.utility.VGUtility;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class MessageLikeService implements IMessageLikeService{

	private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

	@Autowired
	private IMessageLikeDao messageLikeDao;
	@Autowired
	private IMessageDao messageDao;
	@Override
	public void clickLike(String userId, String messageId) {
		logger.info("Click Like Where userId="+userId+"And messageId = "+messageId);
		if(!VGUtility.isEmpty(messageId) && !VGUtility.isEmpty(userId)) {
			Optional<Message> modelOpt = messageDao.findById(messageId);
			if(modelOpt.isPresent()) {
				Message model = modelOpt.get();
				MessageLike messageLikeModel = messageLikeDao.getByMessageAndPersonId(model, userId);
				if(!VGUtility.isEmpty(messageLikeModel))
					throw new RuntimeException("该用户已经点过赞!");
				model.setMessageLike(model.getMessageLike()+1);
				Message modelNew = messageDao.save(model);
				MessageLike messageLike = new MessageLike();
				messageLike.setPersonId(userId);
				messageLike.setMessage(modelNew);
				messageLikeDao.save(messageLike);
			}
		}
	}

	@Override
	public MessageLikeDto getOneMessageLike(String userId, String messageId) {
		logger.info("Get One Like Where userId="+userId+"And messageId = "+messageId);
		Optional<Message> modelOpt = messageDao.findById(messageId);
		Message	model = modelOpt.get();
		MessageLike likeModel = messageLikeDao.getByMessageAndPersonId(model, userId);
		if(!VGUtility.isEmpty(likeModel)) {
			return convertToDto(likeModel);
		}else {
			return null;
		}
	}
	/**
	 * convert
	 * @param model
	 * @return
	 */
	private MessageLikeDto convertToDto(MessageLike model) {
		MessageLikeDto dto = new MessageLikeDto();
		if(!VGUtility.isEmpty(model.getId())) {
			dto.setId(model.getId());
		}
		dto.setPersonId(model.getPersonId());
		dto.setMessageId(model.getMessage().getId());
		return dto;
	}
}
