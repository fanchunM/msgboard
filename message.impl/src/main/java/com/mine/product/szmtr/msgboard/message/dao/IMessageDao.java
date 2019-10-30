package com.mine.product.szmtr.msgboard.message.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mine.product.szmtr.msgboard.message.model.Message;
import com.mine.product.szmtr.msgboard.message.service.IMessageService.MESSAGE_TYPE;

public interface IMessageDao extends JpaRepository<Message, String>{
	
	public Message getMessageByParentAndMsgType(Message parent,MESSAGE_TYPE msgType);
	
	public List<Message> getMessagesByParentAndMsgType(Message parent,MESSAGE_TYPE msgType);

	public List<Message> findByPersonId(String userId);
}



