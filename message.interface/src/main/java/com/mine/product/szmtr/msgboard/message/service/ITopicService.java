package com.mine.product.szmtr.msgboard.message.service;

import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.message.dto.TopicDto;

public interface ITopicService {
	TopicDto createTopic(TopicDto dto);
	void deleteTopic(String id);
	TopicDto updateTopic(TopicDto dto);
	PageDto<TopicDto> getTopics(Object expression, PageableDto pageable);
	TopicDto getTopicById(String id);
}
