package com.mine.product.szmtr.msgboard.message.service;

import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.message.dto.TopicVsMsgDto;

public interface ITopicVsMsgService {
	TopicVsMsgDto createTopicVsMsg(TopicVsMsgDto dto);
	void deleteTopicVsMsg(String id);
	TopicVsMsgDto updateTopicVsMsg(TopicVsMsgDto dto);
	PageDto<TopicVsMsgDto> getTopicVsMsgs(Object expression, PageableDto pageable);
	TopicVsMsgDto getTopicVsMsgById(String id);
}
