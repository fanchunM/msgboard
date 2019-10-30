package com.mine.product.szmtr.msgboard.notify.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mine.product.szmtr.msgboard.notify.dto.BaseSysMsgDto;
import com.mine.product.szmtr.msgboard.notify.model.BaseSysMsgModel;

@Repository
public interface IBaseSysMsgDao extends MongoRepository<BaseSysMsgModel, String> {
    List<BaseSysMsgDto> findByReceiveUserIdAndDeleteTimeIsNull(String userId);
    List<BaseSysMsgDto> findByDeleteTimeIsNotNullAndReadTimeIsNotNullAndAppSendStatusNot(int AppSendStatus);
}
