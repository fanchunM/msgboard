package com.mine.product.szmtr.msgboard.message.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mine.product.szmtr.msgboard.message.model.Message;
import com.mine.product.szmtr.msgboard.message.model.MessageLike;

public interface IMessageLikeDao extends JpaRepository<MessageLike, String>{
	MessageLike getByMessageAndPersonId(Message message, String userId);
}
