package com.mine.product.szmtr.msgboard.message.service;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.message.dao.IMessageDao;
import com.mine.product.szmtr.msgboard.message.dao.ITopicDao;
import com.mine.product.szmtr.msgboard.message.dao.ITopicVsMsgDao;
import com.mine.product.szmtr.msgboard.message.dto.MessageDto;
import com.mine.product.szmtr.msgboard.message.dto.TopicVsMsgDto;
import com.mine.product.szmtr.msgboard.message.model.Message;
import com.mine.product.szmtr.msgboard.message.model.MessageLike;
import com.mine.product.szmtr.msgboard.message.model.TopicVsMsg;
import com.vgtech.platform.common.utility.VGUtility;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TopicVsMsgService implements ITopicVsMsgService{

	private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

	@Autowired
	private ITopicVsMsgDao topicVsMsgDao;
	@Autowired
	private ITopicDao topicDao;
	@Autowired
	private IMessageDao messageDao;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public TopicVsMsgDto createTopicVsMsg(TopicVsMsgDto dto) {
		if(VGUtility.isEmpty(dto)) throw new RuntimeException("TopicVsMsgDto is null!");
		TopicVsMsg model = new TopicVsMsg();
		model.setTopic(topicDao.getOne(dto.getTopicId()));
		model.setMessage(messageDao.getOne(dto.getMessageId()));
		topicVsMsgDao.save(model);
		return convert(model);
	}

	@Override
	public void deleteTopicVsMsg(String id) {
		if (VGUtility.isEmpty(id)) throw new RuntimeException("Id is null!");
		topicVsMsgDao.deleteById(id);
	}

	@Override
	public TopicVsMsgDto updateTopicVsMsg(TopicVsMsgDto dto) {
		if(VGUtility.isEmpty(dto)) throw new RuntimeException("TopicVsMsgDto is null!");
		if(VGUtility.isEmpty(dto.getId())) throw new RuntimeException("Id is null!");
		Optional<TopicVsMsg> modelOpt = topicVsMsgDao.findById(dto.getId());
		if(modelOpt.isPresent()) {
			TopicVsMsg model = modelOpt.get();
			model.setTopic(topicDao.getOne(dto.getTopicId()));
			model.setMessage(messageDao.getOne(dto.getMessageId()));
			topicVsMsgDao.save(model);
			return convert(model);
		} else 
			throw new RuntimeException("Can not found result!");
	}

	@Override
	public PageDto<TopicVsMsgDto> getTopicVsMsgs(Object expression, PageableDto pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TopicVsMsgDto getTopicVsMsgById(String id) {
		if(VGUtility.isEmpty(id)) throw new RuntimeException("Id is null!");
		Optional<TopicVsMsg> modelOpt = topicVsMsgDao.findById(id);
		if(modelOpt.isPresent()) {
			TopicVsMsg model = modelOpt.get();
			return convert(model);
		} else 
			throw new RuntimeException("Can not found result!");
	}
	
	private TopicVsMsgDto convert(TopicVsMsg model) {
		TopicVsMsgDto dto = new TopicVsMsgDto();
		dto.setTopicId(model.getTopic().getId());
		dto.setMessageId(model.getMessage().getId());
		dto.setCreateTimestamp(model.getCreateTimestamp());
		dto.setLastUpdateTimestamp(model.getLastUpdateTimestamp());
		dto.setId(model.getId());
		return dto;
	}
}
