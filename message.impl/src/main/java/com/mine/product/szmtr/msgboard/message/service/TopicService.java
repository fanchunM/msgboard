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
import com.mine.product.szmtr.msgboard.message.dao.ITopicDao;
import com.mine.product.szmtr.msgboard.message.dto.TopicDto;
import com.mine.product.szmtr.msgboard.message.model.Topic;
import com.vgtech.platform.common.utility.VGUtility;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TopicService implements ITopicService{

	private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

	@Autowired
	private ITopicDao topicDao;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public TopicDto createTopic(TopicDto dto) {
		if(VGUtility.isEmpty(dto)) throw new RuntimeException("Topic is null!");
		Topic model = new Topic();
		model.setTitle(dto.getTitle());
		model.setDescStr(dto.getDescStr());
		model.setUserId(dto.getUserId());
		model.setHotStatus(dto.isHotStatus());
		model.setOrderIndex(dto.getOrderIndex());
		topicDao.save(model);
		return convert(model);
	}

	@Override
	public void deleteTopic(String id) {
		if (VGUtility.isEmpty(id)) throw new RuntimeException("Id is null!");
		topicDao.deleteById(id);
	}

	@Override
	public TopicDto updateTopic(TopicDto dto) {
		if(VGUtility.isEmpty(dto)) throw new RuntimeException("FaqsDto is null!");
		if(VGUtility.isEmpty(dto.getId())) throw new RuntimeException("Id is null!");
		Optional<Topic> modelOpt = topicDao.findById(dto.getId());
		if(modelOpt.isPresent()) {
			Topic model = modelOpt.get();
			model.setTitle(dto.getTitle());
			model.setDescStr(dto.getDescStr());
			model.setUserId(dto.getUserId());
			model.setHotStatus(dto.isHotStatus());
			model.setOrderIndex(dto.getOrderIndex());
			topicDao.save(model);
			return convert(model);
		} else 
			throw new RuntimeException("Can not found result!");
	}

	@Override
	public PageDto<TopicDto> getTopics(Object expression, PageableDto pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TopicDto getTopicById(String id) {
		if(VGUtility.isEmpty(id)) throw new RuntimeException("Id is null!");
		Optional<Topic> modelOpt = topicDao.findById(id);
		if(modelOpt.isPresent()) {
			Topic model = modelOpt.get();
			return convert(model);
		} else 
			throw new RuntimeException("Can not found result!");
	}
	
	private TopicDto convert(Topic model) {
		TopicDto dto = new TopicDto();
		model.setTitle(dto.getTitle());
		model.setDescStr(dto.getDescStr());
		model.setUserId(dto.getUserId());
		model.setHotStatus(dto.isHotStatus());
		model.setOrderIndex(dto.getOrderIndex());
		dto.setCreateTimestamp(model.getCreateTimestamp());
		dto.setLastUpdateTimestamp(model.getLastUpdateTimestamp());
		dto.setId(model.getId());
		return dto;
	}
}
