package com.mine.product.szmtr.msgboard.message.service;

import java.util.List;
import java.util.Map;

import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.message.dto.MessageDto;
import com.mine.product.szmtr.msgboard.message.dto.ThemeDto;


public interface IMessageService {
	/**
	 * 审核状态：未处理, 审核不通过, 审核通过
	 */
	public static enum APPROVAL_STATUS {
		未处理, 审核不通过, 审核通过
	}
	/**
	 * 回复状态：未回复, 已回复
	 */
	public static enum REPLY_STATUS {
		未答复, 已答复
	}
	/**
	 * 工单状态：草稿, 已发送, 已处理
	 */
	public static enum ORDER_STATUS {
		草稿, 已发送, 已处理
	}
	/**
	 * 显示状态：隐藏, 显示
	 */
	public static enum DISPLAY_STATUS {
		隐藏, 显示
	}
	
	/**
	 * 信息类型：留言,评论,回复
	 */
	public static enum MESSAGE_TYPE {
		留言,评论,回复
	}
	
	/**
	 * 删除状态：使用中,已删除
	 */
	public static enum ENABLE_STAUS {
		使用中,已删除
	}
	/**
	 * 查看状态：未查看，已查看
	 *
	 */
	public static enum VIEW_STAUS{
		未读取,已读取
	}
	
	//-留言相关接口
	MessageDto createMessage(MessageDto dto);
	void deleteMessage(String id);
	MessageDto updateMessage(MessageDto dto);
	MessageDto getMessageById(String id);
	
	MessageDto getReplyByMessageId(String id);
	
	/**
	 * 分页查询
	 * @param hql 查询实体  类似: from Person where id = :persons
	 * @param params 过滤参数{persons:''}
	 * @param pageable 分页（可以为空）
	 * @return
	 */
	PageDto<MessageDto> getMessages(String hql, Map<String, Object> params, PageableDto pageable);
	/**
	 * 分页查询
	 * @param hql 查询实体 类似：from Person where id = :persons
	 * @param countHql 查询数量 类似：select count(id) from Person where id = :persons
	 * @param params 过滤参数 {persons:''}
	 * @param pageable 分页（可以为空）
	 * @return
	 */
	PageDto<MessageDto> getMessages(String hql, String countHql, Map<String, Object> params, PageableDto pageable);
	
	//-话题相关接口
	//提交留言评论
	void submitComments(String userId,String messageId, String comments);
	//判断用户是否评论过某条留言
	MessageDto judgeIfComments(String userId, String messageId);
	//根据用户查询留言
	PageDto<MessageDto> getMessageByUserId(String hql, Map<String, Object> params, PageableDto pageableDto);
	//添加相关主题
	void saveThemeByMessageIdAndThemeId(String messageId,String themeId);
	//根据留言id查询留言主题
	List<ThemeDto> findMessageThemeByMessageId(String messageId);
	//根据主题id删除留言主题
    void deleteThemeById(String id);
    //获取和主题相关联的留言的数量（过滤出在使用中，审核通过的留言）
    long getMessageCountByThemeId(String themeId);
	//获取相关问答
	List<MessageDto> getRelativeMessages(String sql, Map<String, Object> params, String topNo);
	//获取留言评论个数
	long getMessageComment(String messageId,String type);
	//根据主题id获取留言
	List<MessageDto> getMessageByThemeId(String themeId);
	//创建留言流水号
	String CreateMessagePipelineNumber();
}
