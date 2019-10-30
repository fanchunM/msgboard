package com.mine.product.szmtr.msgboard.notify.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mine.base.sc.user.dto.UserDto;
import com.mine.base.sc.user.service.IUserService;
import com.mine.platform.common.dto.PageDto;
import com.mine.product.szmtr.msgboard.message.service.IMessageService;
import com.mine.product.szmtr.msgboard.notify.dao.IBaseSysMsgDao;
import com.mine.product.szmtr.msgboard.notify.dto.BaseSysMsgDto;
import com.mine.product.szmtr.msgboard.notify.model.BaseSysMsgModel;
import com.vgtech.platform.common.utility.VGUtility;
/**
 * 消息中心
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NotifyService implements INotifyService {
	
	private static final Logger logger = LoggerFactory.getLogger(NotifyService.class);

	@Autowired
	private IBaseSysMsgDao sysMsgDao;
	@Autowired
	private IUserService userService;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private IMessageService messageService;
	
	/**
	 * 获取用户消息
	 * @param criteria {"by":"菜鸟教程", "title":"MongoDB 教程"}
	 * @param page 
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageDto<BaseSysMsgDto> getSysMsgListByUserIdForDataGrid(String criteria, int page, int pageSize) {
        logger.info("Get BaseSysMsgModel List By Logined User Id For DataGrid");
        
        BasicQuery query = null;
        if(!VGUtility.isEmpty(criteria)&&!criteria.equals("")) {
        	query = new BasicQuery(criteria);
        	query.with(new Sort(Direction.DESC,"createTimestamp"));
        } else {
        	query = new BasicQuery("{}");
        }
        
        long totalCount = mongoTemplate.count(query, BaseSysMsgModel.class);
        
        if(page != 0 && pageSize != 0) {
        	query.skip(pageSize*(page-1));
        	query.limit(pageSize);
        }
        List<BaseSysMsgModel> list = mongoTemplate.find(query, BaseSysMsgModel.class);
        
        List<BaseSysMsgDto> dtoList = new ArrayList<BaseSysMsgDto>();
        for(BaseSysMsgModel model: list) {
        	dtoList.add(convert(model));
        }
        
        return new PageDto<BaseSysMsgDto>(totalCount, dtoList);
    }

    private BaseSysMsgDto convert(BaseSysMsgModel model) {
        BaseSysMsgDto dto = new BaseSysMsgDto();
        dto.setId(model.getId());
        dto.setSendUserId(model.getSendUserId());
        UserDto userDto = userService.getUserById(model.getSendUserId());
        if(!VGUtility.isEmpty(userDto))
            dto.setSendUserStr(userDto.getChsName());

        dto.setReceiveUserId(model.getReceiveUserId());
        userDto = userService.getUserById(model.getReceiveUserId());
        if(!VGUtility.isEmpty(userDto))
            dto.setReceiveUserStr(userDto.getChsName());
        
        if(!VGUtility.isEmpty(model.getMessageId())) {
        	dto.setMessageId(model.getMessageId());
        	String messageTitle = messageService.getMessageById(model.getMessageId()).getTitle();
        	dto.setMessageTitle(messageTitle);
        }
        dto.setReadTimeObj(model.getReadTime());
        dto.setDeleteTimeObj(model.getDeleteTime());
        dto.setContentText(model.getContentText());
        dto.setApprSendStatus(model.getAppSendStatus());
        dto.setCreateTimestampObj(model.getCreateTimestamp());
        dto.setCreateTimestampStr(VGUtility.toDateStr(model.getCreateTimestamp(), "yyyy-MM-dd HH:mm:ss"));
        dto.setLastUpdateTimestampObj(model.getLastUpdateTimestamp());
        dto.setDeleteReasonText(model.getDeleteReasonText());
        return dto;
    }

    /**
     * 创建消息
     * @param sysMsgDto 填写内容SendUserId、ReceiveUserId、ContentText
     */
    @Override
    public void createSysMsg(BaseSysMsgDto sysMsgDto) {
        BaseSysMsgModel model = new BaseSysMsgModel();
        model.setSendUserId(sysMsgDto.getSendUserId());
        model.setReceiveUserId(sysMsgDto.getReceiveUserId());
        model.setContentText(sysMsgDto.getContentText());
        model.setMessageId(sysMsgDto.getMessageId());
        model.setCreateTimestamp(sysMsgDto.getCreateTimestampObj());
        model.setDeleteReasonText(sysMsgDto.getDeleteReasonText());
        sysMsgDao.save(model);
    }

    /**
     * 更新消息状态
     * @param idList
     * @param type 0：已读，1-删除
     */
    @Override
    public void updateSysMsg(List<String> idList, int type) {
        Date time = new Date();
        if(idList.size() > 0){
            for(String id:idList){
                Optional<BaseSysMsgModel> modelOpt = sysMsgDao.findById(id);
                if(modelOpt.isPresent()) {
                	BaseSysMsgModel model = modelOpt.get();
					if (type == 0) {
						if (VGUtility.isEmpty(model.getReadTime()))
							model.setReadTime(time);
					}
					if (type == 1)
						model.setDeleteTime(time);
					sysMsgDao.save(model);
                }
            }
        }
    }
}
