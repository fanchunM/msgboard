package com.mine.product.szmtr.msgboard.message.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mine.base.sc.user.service.IDictionaryService;
import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.message.dao.IImageDao;
import com.mine.product.szmtr.msgboard.message.dao.IMessageDao;
import com.mine.product.szmtr.msgboard.message.dao.IMessageLikeDao;
import com.mine.product.szmtr.msgboard.message.dao.IThemeDao;
import com.mine.product.szmtr.msgboard.message.dto.ImageDto;
import com.mine.product.szmtr.msgboard.message.dto.MessageDto;
import com.mine.product.szmtr.msgboard.message.dto.SysDictionaryDto;
import com.mine.product.szmtr.msgboard.message.dto.ThemeDto;
import com.mine.product.szmtr.msgboard.message.model.Image;
import com.mine.product.szmtr.msgboard.message.model.Message;
import com.mine.product.szmtr.msgboard.message.model.Theme;
import com.mine.product.szmtr.msgboard.person.service.IPersonService;
import com.vgtech.platform.common.utility.VGUtility;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class MessageService implements IMessageService{

	private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

	@Autowired
	private IMessageDao messageDao;
	@Autowired
	private IImageDao imageDao;
	@Autowired
	private IMessageLikeDao messageLikeDao;
	@Autowired
	private IPersonService personService;
	@Autowired
	private IThemeDao themeDao;
	@Autowired
	private IDictionaryService dictService;
	@Autowired
	private IsysDictionaryService syService;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public MessageDto createMessage(MessageDto dto) {
		if(VGUtility.isEmpty(dto)) throw new RuntimeException("MessageDto is null!");
		Message model = new Message();
		if(!VGUtility.isEmpty(dto.getParentId())) {
			model.setParent(VGUtility.isEmpty(messageDao.getOne(dto.getParentId()))?null:messageDao.getOne(dto.getParentId()));
		}
		model.setMsgType(dto.getMsgType());
		model.setMessageNo(dto.getMessageNo());
		model.setMessageCategory(dto.getMessageCategory());
		model.setTitle(dto.getTitle());
		model.setPersonId(VGUtility.isEmpty(dto.getPersonId())?"":dto.getPersonId());
		model.setIpAddress(dto.getIpAddress());
		model.setCommentStr(dto.getCommentStr());
		model.setPhone(dto.getPhone());
		model.setDeptId(dto.getDeptId());
		model.setGradeValue(dto.getGradeValue());
		model.setMessageLike(dto.getMessageLike());
		model.setApprovalStatus(dto.getApprovalStatus());
		model.setReplyStatus(dto.getReplyStatus());
		model.setOrderStatus(dto.getOrderStatus());
		model.setDisplayStatus(dto.getDisplayStatus());
		model.setViewStatus(dto.getViewStatus());
		model.setMessageEnable(dto.getMessageEnable());
		model.setMessageCategory(dto.getMessageCategory());
		model.setStatus(VGUtility.toInteger(dto.getStatus()));
		model.setSerialNumber(dto.getSerialNumber());
		Message modelNew = messageDao.save(model);
		return convert(modelNew);
	}

	@Override
	public void deleteMessage(String id) {
		if (VGUtility.isEmpty(id)) throw new RuntimeException("Id is null!");
		messageDao.deleteById(id);
	}

	@Override
	public MessageDto updateMessage(MessageDto dto) {
		if(VGUtility.isEmpty(dto)) throw new RuntimeException("MessageDto is null!");
		if(VGUtility.isEmpty(dto.getId())) throw new RuntimeException("Id is null!");
		Optional<Message> modelOpt = messageDao.findById(dto.getId());
		if(modelOpt.isPresent()) {
			Message model = modelOpt.get();
			model.setId(dto.getId());
			model.setParent(VGUtility.isEmpty(messageDao.getOne(dto.getParentId()))?null:messageDao.getOne(dto.getParentId()));
			model.setMsgType(dto.getMsgType());
			model.setMessageNo(dto.getMessageNo());
			model.setMessageCategory(dto.getMessageCategory());
			model.setTitle(dto.getTitle());
			model.setPersonId(VGUtility.isEmpty(dto.getPersonId())?"":dto.getPersonId());
			model.setIpAddress(dto.getIpAddress());
			model.setCommentStr(dto.getCommentStr());
			model.setDeptId(dto.getDeptId());
			model.setGradeValue(dto.getGradeValue());
			model.setMessageLike(dto.getMessageLike());
			model.setApprovalStatus(dto.getApprovalStatus());
			model.setReplyStatus(dto.getReplyStatus());
			model.setOrderStatus(dto.getOrderStatus());
			model.setDisplayStatus(dto.getDisplayStatus());
			model.setViewStatus(dto.getViewStatus());
			model.setApprivalPersonId(dto.getApprivalPersonId());
			model.setDeptName(dto.getDeptName());
			if(!VGUtility.isEmpty(dto.getStatus())) {
				model.setStatus(VGUtility.toInteger(dto.getStatus()));
			}
			model.setMessageEnable(dto.getMessageEnable());
			model.setSerialNumber(dto.getSerialNumber());
			messageDao.save(model);
			return convert(model);
		} else 
			throw new RuntimeException("Can not found result!");
	}

	@Override
	public MessageDto getMessageById(String id) {
		if(VGUtility.isEmpty(id)) throw new RuntimeException("Id is null!");
		Optional<Message> modelOpt = messageDao.findById(id);
		if(modelOpt.isPresent()) {
			Message model = modelOpt.get();
			return convert(model);
		} else {
			return null;
		}
	}
	
	private MessageDto convert(Message model) {
		MessageDto dto = new MessageDto();
		
		dto.setId(model.getId());
		dto.setParentId(VGUtility.isEmpty(model.getParent())?"":model.getParent().getId());
		dto.setMsgType(model.getMsgType());
		dto.setMessageNo(model.getMessageNo());
		dto.setMessageCategory(model.getMessageCategory());
//		dto.setMessageCategoryName(dictService.getCommonCodeById(model.getMessageCategory()).getChsName());
		dto.setTitle(model.getTitle());
		dto.setPersonId(VGUtility.isEmpty(model.getPersonId())?"":model.getPersonId());
		dto.setPersonName(VGUtility.isEmpty(model.getPersonId())?"":
			VGUtility.isEmpty(personService.getOneById(model.getPersonId()).getNickName())?"":personService.getOneById(model.getPersonId()).getNickName());
		if(!VGUtility.isEmpty(model.getPersonId())) 
			dto.setHeadPortraits(personService.getOneById(model.getPersonId()).getHeadPortraits());
		dto.setIpAddress(model.getIpAddress());
		dto.setCommentStr(model.getCommentStr());
		dto.setDeptId(model.getDeptId());
		dto.setPhone(model.getPhone());
		dto.setGradeValue(model.getGradeValue());
		dto.setMessageLike(model.getMessageLike());
		dto.setApprovalStatus(model.getApprovalStatus());
		if(!VGUtility.isEmpty(model.getApprovalStatus())) {
			dto.setApprovalStatusStr(model.getApprovalStatus().name());
		}
		dto.setReplyStatus(model.getReplyStatus());
		if(!VGUtility.isEmpty(model.getReplyStatus())) {
			dto.setReplyStatusStr(model.getReplyStatus().name());
		}
		dto.setOrderStatus(model.getOrderStatus());
		dto.setDisplayStatus(model.getDisplayStatus());
		dto.setMessageEnable(model.getMessageEnable());
		dto.setViewStatus(model.getViewStatus());
		dto.setApprivalPersonId(model.getApprivalPersonId());
		if(!VGUtility.isEmpty(this.getReplyByMessageId(model.getId()))) {
			dto.setReply(this.getReplyByMessageId(model.getId()).getCommentStr());
		}
		long count = this.getMessageComment(model.getId(),"3");
		dto.setCommentNumber(count);
		if(!VGUtility.isEmpty(model.getStatus())) {
			dto.setStatus(String.valueOf(model.getStatus()));
		}
		dto.setCommentNeedNumber( this.getMessageComment(model.getId(),"1"));
		dto.setCreateTimestamp(VGUtility.toDateStr(model.getCreateTimestamp(), "yyyy-MM-dd  HH:mm"));
		dto.setLastUpdateTimestamp(VGUtility.toDateStr(model.getLastUpdateTimestamp(),"yyyy-MM-dd HH:mm"));
		dto.setSerialNumber(model.getSerialNumber());
		dto.setDeptName(model.getDeptName());
		//获取留言的主题分类
		List<Theme> themeList = themeDao.findAllByMessageId(model.getId());
		StringBuffer buffer = new StringBuffer("");
		if(!VGUtility.isEmpty(themeList) && themeList.size()>0) {
			for(Theme themeModel : themeList) {
				SysDictionaryDto sysDto = syService.getSysDictionaryById(themeModel.getThemeId());
				String sonStr = sysDto.getChsName();
				String fatherStr = syService.getSysDictionaryById(sysDto.getParentThemeId()).getChsName();
				buffer.append(fatherStr+">"+sonStr+";");
			}
			buffer.deleteCharAt(buffer.length()-1);
			dto.setThemeStr(buffer.toString());
		}
		return dto;
	}

	@Override
	public PageDto<MessageDto> getMessages(String hql, Map<String, Object> params, PageableDto pageable) {
		return getMessages(hql, "select count(id) " + hql, params, pageable);
	}

	@Override
	public PageDto<MessageDto> getMessages(String hql, String countHql, Map<String, Object> params, PageableDto pageable) {
		TypedQuery<Message> query = entityManager.createQuery(hql, Message.class);
		TypedQuery<Long> countQuery = entityManager.createQuery("select count(id) " + hql, Long.class);
		for(Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
			countQuery.setParameter(entry.getKey(), entry.getValue());
		}
		if (!VGUtility.isEmpty(pageable))
			query.setFirstResult((pageable.getPage() - 1) * pageable.getSize()).setMaxResults(pageable.getSize());
		List<Message> modelList = query.getResultList();
		
		List<MessageDto> resultList = new ArrayList<MessageDto>();
		for (Message model : modelList) {
			resultList.add(convert(model));
		}
		return new PageDto<MessageDto>(countQuery.getSingleResult(), resultList);
	}
	
	@Override
	public List<MessageDto> getRelativeMessages(String sql, Map<String, Object> params,String topNo) {
		Session session = entityManager.unwrap(org.hibernate.Session.class);
		Query query = session.createNativeQuery(sql.toString(),Message.class);
		for(Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(),entry.getValue());
		}
		query.setMaxResults(Integer.parseInt(topNo));
		List<Message> list = (ArrayList<Message>)query.getResultList();
		List<MessageDto> dtos = new ArrayList<MessageDto>();
		for(Message data: list) {
			dtos.add(convert(data));
		}
		return dtos;
	}

	@Override
	public MessageDto getReplyByMessageId(String id) {
		Message msg = new Message();
		msg.setId(id);
		Message reply = messageDao.getMessageByParentAndMsgType(msg,IMessageService.MESSAGE_TYPE.回复);
		if (!VGUtility.isEmpty(reply)) {
			return convert(reply);
		}else {
			return null;
		}
		
	}

	@Override
	public void submitComments(String userId, String messageId, String comments) {
		Message model = messageDao.getOne(messageId);
		if(VGUtility.isEmpty(model))
			throw new RuntimeException("该留言不存在!");
		Message message = new Message();
		message.setPersonId(userId);
		message.setParent(model);
		message.setCommentStr(comments);
		message.setMsgType(IMessageService.MESSAGE_TYPE.评论);
		message.setMessageEnable(IMessageService.ENABLE_STAUS.使用中);
		message.setApprovalStatus(IMessageService.APPROVAL_STATUS.未处理);
		message.setSerialNumber(this.CreateCommentPipelineNumber());
		messageDao.save(message);
	}

	@Override
	public MessageDto judgeIfComments(String userId, String messageId) {
		if(VGUtility.isEmpty(userId))
			throw new RuntimeException("UserId Is Null");
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Message m where m.parent.id =:messageId and m.personId =:userId";
		params.put("messageId",messageId);
		params.put("userId",userId);
		TypedQuery<Message> query = entityManager.createQuery(hql, Message.class);
		for(Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		List<Message> modelList = query.getResultList();
		if(!VGUtility.isEmpty(modelList) && modelList.size()>0)
			return convert(modelList.get(0));
		else return null;
	}
	
	@Override
	public PageDto<MessageDto> getMessageByUserId(String hql, Map<String, Object> params, PageableDto pageableDto) {
		return getMessages(hql, "select count(id) " + hql, params, pageableDto);
	}
	
	@Override
	public void saveThemeByMessageIdAndThemeId(String messageId,String themeId) {
			List<Theme> themeList = themeDao.findAllByMessageIdAndThemeId(messageId, themeId);
			if(null==themeList||themeList.size()==0) {
				Theme th = new Theme();
				th.setMessageId(messageId);
				th.setThemeId(themeId);
				themeDao.save(th);
			}
	}

	@Override
	public List<ThemeDto> findMessageThemeByMessageId(String messageId) {
		List<Theme> themeList = themeDao.findAllByMessageId(messageId);
		List<ThemeDto> themeDtoList = new ArrayList<ThemeDto>();
		if(!VGUtility.isEmpty(themeList)) {
			for(Theme th:themeList) {
				ThemeDto themeDto = new ThemeDto();
				themeDto.setMessageId(th.getMessageId());
				themeDto.setId(th.getId());
				themeDto.setThemeId(th.getThemeId());
				themeDto.setThemeName(syService.getThemeById(th.getThemeId()).getChsName());
				themeDtoList.add(themeDto);
			}
			return themeDtoList;
		}
		return null;
	}

	@Override
	public void deleteThemeById(String id) {
		themeDao.deleteById(id);
	}

	@Override
	public long getMessageCountByThemeId(String themeId) {
		String hql = "select count(id) from Message m where m.id in (select t.messageId from Theme t where t.themeId=:themeId) and m.approvalStatus = :approvalStatus and m.messageEnable = :messageEnable";
		Map<String, Object> params = new HashMap<>();
		params.put("themeId", themeId);
		params.put("approvalStatus", IMessageService.APPROVAL_STATUS.审核通过);
	    params.put("messageEnable", IMessageService.ENABLE_STAUS.使用中);
		TypedQuery<Long> countQuery = entityManager.createQuery(hql, Long.class);
		for(Map.Entry<String, Object> entry : params.entrySet()) {
			countQuery.setParameter(entry.getKey(), entry.getValue());
		}
		return countQuery.getSingleResult();
	}
	@Override
	public List<MessageDto> getMessageByThemeId(String themeId) {
		String hql = "from Message m where m.id in (select t.messageId from Theme t where t.themeId=:themeId) and m.approvalStatus = :approvalStatus and m.messageEnable = :messageEnable";
		Map<String, Object> params = new HashMap<>();
		params.put("themeId", themeId);
		params.put("approvalStatus", IMessageService.APPROVAL_STATUS.审核通过);
	    params.put("messageEnable", IMessageService.ENABLE_STAUS.使用中);
		TypedQuery<Message> query = entityManager.createQuery(hql, Message.class);
		for(Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		List<MessageDto> dtoList = new ArrayList<>();
		List<Message> modelList = query.getResultList();
		for(Message m :modelList) {
			dtoList.add(convert(m));
		}
		return dtoList;
	}

	@Override
	public long getMessageComment(String messageId,String type) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql;
		//状态3查询留言评论数量
		if(type=="3") {
			 hql =  "select count(id) from Message m where 1=1 and m.msgType = :msgType and m.parent.id = :parent and m.messageEnable = :messageEnable";
		}else {
			 hql = "select count(id) from Message m where 1=1 and m.msgType = :msgType and m.parent.id = :parent and m.approvalStatus = :approvalStatus and m.messageEnable = :messageEnable";
		}
		
		params.put("msgType", IMessageService.MESSAGE_TYPE.评论);
		params.put("parent", messageId);
		if(type == "0") {
			params.put("approvalStatus", IMessageService.APPROVAL_STATUS.审核通过);
		}else if(type == "1") {
			params.put("approvalStatus", IMessageService.APPROVAL_STATUS.未处理);
		}else if(type == "2") {
			params.put("approvalStatus", IMessageService.APPROVAL_STATUS.审核不通过);
		}
	    params.put("messageEnable", IMessageService.ENABLE_STAUS.使用中);
	    TypedQuery<Long> countQuery = entityManager.createQuery(hql, Long.class);
	    for(Map.Entry<String, Object> entry : params.entrySet()) {
			countQuery.setParameter(entry.getKey(), entry.getValue());
		}
	    return countQuery.getSingleResult();
	}

	@Override
	public String CreateMessagePipelineNumber() {
		synchronized(this) { 
		Map<String, Object> params = new HashMap<String, Object>();
		//获取当前时间毫秒
		long nowTime =System.currentTimeMillis();
		//根据当前时间毫秒获取今日凌晨0点毫秒数
		long todayStartTime = nowTime - (nowTime + TimeZone.getDefault().getRawOffset())% (1000*3600*24);
		//将今日凌晨0点毫秒数转换为date类型
		Date zeroPointDate = new Date(todayStartTime);
		//查询创建时间大于今日凌晨0点的留言数量
		String hql = "select count(id) from Message m where 1=1 and m.msgType = :msgType and m.createTimestamp>= :zeroPointDate";
		params.put("msgType", IMessageService.MESSAGE_TYPE.留言);
		params.put("zeroPointDate", zeroPointDate);
		TypedQuery<Long> countQuery = entityManager.createQuery(hql,Long.class);
		for(Map.Entry<String, Object> entry : params.entrySet()) {
			countQuery.setParameter(entry.getKey(), entry.getValue());
		}
		//留言数量赋值
		long messageNums = countQuery.getSingleResult();
		//创建留言编号
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		//时间
		String dateString = formatter.format(currentTime);
		//流水号
		String numbers = "";
		//如果留言数量小于1，流水号为0001
		if(messageNums<1) {
			numbers="0001";
		}else {
			numbers=String.valueOf(messageNums+1);
			if(numbers.length()==1) {
				numbers = "000"+numbers;
			}else if(numbers.length()==2) {
				numbers = "00"+numbers;
			}else if(numbers.length()==3) {
				numbers = "0"+numbers;
			}else if(numbers.length()==4) {
				numbers = String.valueOf(messageNums+1);
			}
		}
		return "L"+dateString+numbers;
		} 
	}

	//创建评论流水号
	private String CreateCommentPipelineNumber() {
		synchronized(this) { 
			Map<String, Object> params = new HashMap<String, Object>();
			//获取当前时间毫秒
			long nowTime =System.currentTimeMillis();
			//根据当前时间毫秒获取今日凌晨0点毫秒数
			long todayStartTime = nowTime - (nowTime + TimeZone.getDefault().getRawOffset())% (1000*3600*24);
			//将今日凌晨0点毫秒数转换为date类型
			Date zeroPointDate = new Date(todayStartTime);
			//查询创建时间大于今日凌晨0点的评论数量
			String hql = "select count(id) from Message m where 1=1 and m.msgType = :msgType and m.createTimestamp>= :zeroPointDate";
			params.put("msgType", IMessageService.MESSAGE_TYPE.评论);
			params.put("zeroPointDate", zeroPointDate);
			TypedQuery<Long> countQuery = entityManager.createQuery(hql,Long.class);
			for(Map.Entry<String, Object> entry : params.entrySet()) {
				countQuery.setParameter(entry.getKey(), entry.getValue());
			}
			//评论数量赋值
			long messageNums = countQuery.getSingleResult();
			//创建评论编号
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			//时间
			String dateString = formatter.format(currentTime);
			//流水号
			String numbers = "";
			//如果评论数量小于1，流水号为0001
			if(messageNums<1) {
				numbers="0001";
			}else {
				numbers=String.valueOf(messageNums+1);
				if(numbers.length()==1) {
					numbers = "000"+numbers;
				}else if(numbers.length()==2) {
					numbers = "00"+numbers;
				}else if(numbers.length()==3) {
					numbers = "0"+numbers;
				}else if(numbers.length()==4) {
					numbers = String.valueOf(messageNums+1);
				}
			}
			return "P"+dateString+numbers;
			} 
	}
}
