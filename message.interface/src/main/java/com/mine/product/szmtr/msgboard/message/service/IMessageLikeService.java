package com.mine.product.szmtr.msgboard.message.service;

import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.message.dto.MessageLikeDto;

public interface IMessageLikeService {
	void clickLike(String userId,String messageId);
	MessageLikeDto getOneMessageLike(String userId,String messageId);
}
