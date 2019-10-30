package com.mine.product.szmtr.msgboard.notify.service;

import java.util.List;

import com.mine.platform.common.dto.PageDto;
import com.mine.product.szmtr.msgboard.notify.dto.BaseSysMsgDto;

public interface INotifyService {
	 /**
     * 更新消息状态
     * @param idList
     * @param type 0：已读，1-删除
     */
	void updateSysMsg(List<String> idList, int type);
    /**
     * 创建消息
     * @param sysMsgDto
     */
	void createSysMsg(BaseSysMsgDto sysMsgDto);
	/**
	 * 获取用户消息
	 * @param criteria {"by":"菜鸟教程", "title":"MongoDB 教程"}
	 * @param page 
	 * @param pageSize
	 * @return
	 */
	PageDto<BaseSysMsgDto> getSysMsgListByUserIdForDataGrid(String criteria, int page, int pageSize);
	
}
