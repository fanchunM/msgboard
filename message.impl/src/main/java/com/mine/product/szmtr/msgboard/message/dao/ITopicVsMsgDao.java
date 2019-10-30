package com.mine.product.szmtr.msgboard.message.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mine.product.szmtr.msgboard.message.model.TopicVsMsg;

public interface ITopicVsMsgDao extends JpaRepository<TopicVsMsg, String>{

}
