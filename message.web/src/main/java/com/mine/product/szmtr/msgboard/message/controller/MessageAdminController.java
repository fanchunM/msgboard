package com.mine.product.szmtr.msgboard.message.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mine.base.sc.user.dto.DeptDto;
import com.mine.base.sc.user.dto.DictionaryCommonCodeDto;
import com.mine.base.sc.user.dto.UserDto;
import com.mine.base.sc.user.service.IDictionaryService;
import com.mine.base.sc.user.service.IUserService;
import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.message.dto.ImageDto;
import com.mine.product.szmtr.msgboard.message.dto.MessageDto;
import com.mine.product.szmtr.msgboard.message.dto.SysDictionaryDto;
import com.mine.product.szmtr.msgboard.message.dto.ThemeDto;
import com.mine.product.szmtr.msgboard.message.service.IImageService;
import com.mine.product.szmtr.msgboard.message.service.IMessageService;
import com.mine.product.szmtr.msgboard.message.service.IsysDictionaryService;
import com.mine.product.szmtr.msgboard.message.webservice.Exception_Exception;
import com.mine.product.szmtr.msgboard.message.webservice.IMsgWebService;
import com.mine.product.szmtr.msgboard.message.webservice.MsgHandler.Arg0;
import com.mine.product.szmtr.msgboard.message.webservice.MsgWebService;
import com.mine.product.szmtr.msgboard.notify.dto.BaseSysMsgDto;
import com.mine.product.szmtr.msgboard.notify.service.INotifyService;
import com.mine.product.szmtr.msgboard.person.dto.PersonDto;
import com.mine.product.szmtr.msgboard.person.service.IPersonService;
import com.vgtech.platform.common.utility.VGUtility;
import com.vgtech.platform.common.web.dto.CommonComboDto;
import com.vgtech.platform.common.web.dto.LoginUserDto;

@Controller
@RequestMapping(value = "/admin/")
@SessionAttributes(value = {"loginUserDto"})
public class MessageAdminController {
	private static final Logger logger = LoggerFactory.getLogger(MessageAdminController.class);
	
	@Autowired
	private IMessageService messageService;
	@Autowired
	private IDictionaryService dictService;
	@Autowired
	private IImageService imageService;
	@Autowired
	private INotifyService notifyService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IsysDictionaryService sysDictionaryService;
	@Autowired
	private IPersonService personService;
	
	@PostMapping(value = "message/messageList")
    @ResponseBody
    public Map<String, Object> messageList(
    		@RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "20") String rows,
            @RequestParam(required=false) String done,
            @RequestParam(required=false) String keyword,
            @RequestParam(required=false) Integer approvalStatus,
            @RequestParam(required=false) Integer replyStatus,
            @RequestParam(required=false) Integer orderStatus,
            @RequestParam(required=false) Integer displayStatus,
            @RequestParam(required=false) Integer messageEnable,
            @RequestParam(required=false) Integer viewStatus,
            @RequestParam(required=false) Integer keyTypes,
            @RequestParam(required=false) String startTime,
            @RequestParam(required=false) String finshTime) throws ParseException
    {
		logger.info("Get Message Model By Query For DataGrid");
		Map<String, Object> params = new HashMap<String, Object>();
		PageableDto pageable = new PageableDto(Integer.parseInt(page), Integer.parseInt(rows));
		String hql = "from Message m where 1=1";
		//如果关键字类型不为空
		if(!VGUtility.isEmpty(keyTypes)) {
			//如果关键字不为空
			if(!VGUtility.isEmpty(keyword)) {
				//关键字类型为标题
				if(keyTypes==0) {
					hql += " and m.title like :title ";
					params.put("title", "%"+keyword+"%");
				//关键字类型为提出人
				}else if(keyTypes==1) {
					//通过提出人名字查询personId
					PersonDto personDto = personService.getByUserName(keyword);
					if(personDto!=null) {
						hql += " and m.personId =:personId";
						params.put("personId", personDto.getId());
					}else {
						hql += " and m.personId =:personId";
						String p = " ";
						params.put("personId",p);
					}
				//关键字类型为手机号
				}else if(keyTypes==2) {
					hql += " and m.phone =:phone";
					params.put("phone", keyword);
				}
			}
		} else {
			if(!VGUtility.isEmpty(keyword)) {
				hql += " and m.title like :title ";
				params.put("title", "%"+keyword+"%");
			}
		}
		if(!VGUtility.isEmpty(startTime)&&!VGUtility.isEmpty(finshTime)) {
		    //将String类型时间转换为Timestamp类型
			Timestamp newStartTime = Timestamp.valueOf(startTime);
			Timestamp newFinshTime = Timestamp.valueOf(finshTime);
		    hql += " and m.createTimestamp>=:newStartTime and m.createTimestamp<=:newFinshTime";
		    params.put("newStartTime",newStartTime);
		    params.put("newFinshTime",newFinshTime);
		}
		if(!VGUtility.isEmpty(done)) {
			hql += " and m.approvalStatus <> :approvalStatus ";
			params.put("approvalStatus", IMessageService.APPROVAL_STATUS.未处理);
		}else {
			if (!VGUtility.isEmpty(approvalStatus)) {
				hql += " and m.approvalStatus = :approvalStatus ";
				if(approvalStatus==0) {
					params.put("approvalStatus", IMessageService.APPROVAL_STATUS.未处理);
				}
				if(approvalStatus==1) {
					params.put("approvalStatus", IMessageService.APPROVAL_STATUS.审核不通过);
				}
				if(approvalStatus==2) {
					params.put("approvalStatus", IMessageService.APPROVAL_STATUS.审核通过);
				}
			}
		}
		
		if (!VGUtility.isEmpty(replyStatus)) {
			hql += " and m.replyStatus = :replyStatus ";
			if(replyStatus==0) {
				params.put("replyStatus", IMessageService.REPLY_STATUS.未答复);
			}
			if(replyStatus==1) {
				params.put("replyStatus", IMessageService.REPLY_STATUS.已答复);
			}
		}
		if (!VGUtility.isEmpty(orderStatus)) {
			hql += " and m.orderStatus = :orderStatus ";
			if(orderStatus==0) {
				params.put("orderStatus", IMessageService.ORDER_STATUS.草稿);
			}
			if(orderStatus==1) {
				params.put("orderStatus", IMessageService.ORDER_STATUS.已发送);
			}
			if(orderStatus==2) {
				params.put("orderStatus", IMessageService.ORDER_STATUS.已处理);
			}
		}
		if (!VGUtility.isEmpty(displayStatus)) {
			hql += " and m.displayStatus = :displayStatus ";
			if(displayStatus==0) {
				params.put("displayStatus", IMessageService.DISPLAY_STATUS.隐藏);
			}
			if(displayStatus==1) {
				params.put("displayStatus", IMessageService.DISPLAY_STATUS.显示);
			}
		}
		if(!VGUtility.isEmpty(viewStatus)) {
			hql +=" and m.viewStatus = : viewStatus";
			params.put("viewStatus",IMessageService.VIEW_STAUS.未读取);
			
		}
		
		if (!VGUtility.isEmpty(messageEnable)) {
			hql += " and m.messageEnable = :messageEnable ";
//			if(messageEnable==0) {
//				params.put("messageEnable", IMessageService.ENABLE_STAUS.使用中);
//			}
			if(messageEnable==1) {
				params.put("messageEnable", IMessageService.ENABLE_STAUS.已删除);
			}
		}else {
			hql += " and m.messageEnable = :messageEnable ";
			params.put("messageEnable", IMessageService.ENABLE_STAUS.使用中);
		}
		
		hql += " and m.msgType=0 order by m.createTimestamp desc";
		PageDto<MessageDto> pageDto = messageService.getMessages(hql, params, pageable);
		Map<String, Object> result = new HashMap<String, Object>();
		List<MessageDto> messageDtoList = pageDto.getRowData();
		for(MessageDto message:messageDtoList) {
		
			List<ThemeDto> themeDtoList =messageService.findMessageThemeByMessageId(message.getId());
			if(themeDtoList!=null) {
				StringBuffer buffer= null;
				StringBuffer bufferId= null;
				for(ThemeDto themeDto:themeDtoList) {
					SysDictionaryDto sd = sysDictionaryService.getThemeById(themeDto.getThemeId());
//					DictionaryCommonCodeDto	dccd = dictService.getCommonCodeById(themeDto.getThemeId());
					if(buffer == null) buffer = new StringBuffer(sd.getChsName());
					else buffer.append(";").append(sd.getChsName());
					if(bufferId == null) bufferId = new StringBuffer(sd.getId());
					else bufferId.append("-").append(sd.getId());
				}
				//拼接主题分类Id
				message.setMainTextId(bufferId == null ? "" : bufferId.toString());
				//拼接主题分类name
				message.setMainText(buffer == null ? "" : buffer.toString());
			}
			//如果viewStatus不为空，则改变当前留言VIEW_STAUS的状态为已读取（改功能用于后台右下角弹窗提示消息）
			if(!VGUtility.isEmpty(viewStatus)) {
				MessageDto dto = messageService.getMessageById(message.getId());
				dto.setViewStatus(IMessageService.VIEW_STAUS .已读取);
				messageService.updateMessage(dto);
			}
			//通过ApprivalPersonId获取审核人名字
			if(!VGUtility.isEmpty(message.getApprivalPersonId())) {
				UserDto userDto = userService.getUserById(message.getApprivalPersonId());
				message.setApprivalPersonName(userDto.getChsName());
			}
		}
		result.put("rows", pageDto.getRowData());
		result.put("total", pageDto.getTotalCount());
        return result;
    }
	
	@PostMapping(value = "message/deleteMessage")
    @ResponseBody
    public String deleteMessage(@RequestParam(required=true) String id,
    		                    @RequestParam(required=true) String	deleteReasonText,
    		                    @ModelAttribute("loginUserDto") LoginUserDto loginUserDto){
		logger.info("delete Message By Id id="+id+"deleteReasonText="+deleteReasonText);
		MessageDto dto = messageService.getMessageById(id);
		String sedUserId = loginUserDto.getLoginedUserId();
		String receiveUserId = messageService.getMessageById(id).getPersonId();
		BaseSysMsgDto sysMsgDto = new BaseSysMsgDto();
		Date date = new Date();
		sysMsgDto.setCreateTimestampObj(date);
		sysMsgDto.setApprSendStatus(0);
		sysMsgDto.setContentText("您的留言被删除了,请注意查收!");
		sysMsgDto.setReceiveUserId(receiveUserId);
		sysMsgDto.setSendUserId(sedUserId);
		sysMsgDto.setMessageId(id);
		sysMsgDto.setDeleteReasonText(deleteReasonText);
		notifyService.createSysMsg(sysMsgDto);
		dto.setMessageEnable(IMessageService.ENABLE_STAUS.已删除);
		messageService.updateMessage(dto);
		return "ok";
	}
	
	@PostMapping(value = "message/checkMessage")
    @ResponseBody
    public String checkMessage(@RequestParam(required=true) String id,
    		@RequestParam(required=true) Integer type,
    		@ModelAttribute("loginUserDto") LoginUserDto loginUserDto){
		logger.info("Check Message By Id id="+id);
		MessageDto dto = messageService.getMessageById(id);
		String loginUserId =loginUserDto.getLoginedUserId();
		dto.setApprivalPersonId(loginUserId);
		if(type == 1) {
			dto.setApprovalStatus(IMessageService.APPROVAL_STATUS.审核不通过);
			dto.setViewStatus(IMessageService.VIEW_STAUS.已读取);
		}
		if(type == 2) {
			dto.setApprovalStatus(IMessageService.APPROVAL_STATUS.审核通过);
			dto.setViewStatus(IMessageService.VIEW_STAUS.已读取);
			//判断其是否为留言,是留言则发站内消息,评论不发
			if(dto.getMsgType() == IMessageService.MESSAGE_TYPE.留言) {
				String sedUserId = loginUserDto.getLoginedUserId();
				String receiveUserId = messageService.getMessageById(id).getPersonId();
				BaseSysMsgDto sysMsgDto = new BaseSysMsgDto();
				Date date = new Date();
				sysMsgDto.setCreateTimestampObj(date);
				sysMsgDto.setApprSendStatus(0);
				sysMsgDto.setContentText("您的留言已审核,请注意查收!");
				sysMsgDto.setReceiveUserId(receiveUserId);
				sysMsgDto.setSendUserId(sedUserId);
				sysMsgDto.setMessageId(id);
				notifyService.createSysMsg(sysMsgDto);
			}
		}
		messageService.updateMessage(dto);
		return "ok";
	}
	
	@PostMapping(value = "message/displayMessage")
    @ResponseBody
    public String displayMessage(@RequestParam(required=true) String id,
    		@RequestParam(required=true) Integer type){
		logger.info("Display Message By Id id="+id);
		MessageDto dto = messageService.getMessageById(id);
		if(type == 0) {
			dto.setDisplayStatus(IMessageService.DISPLAY_STATUS.隐藏);
		}
		if(type == 1) {
			dto.setDisplayStatus(IMessageService.DISPLAY_STATUS.显示);
		}
		messageService.updateMessage(dto);
		return "ok";
	}
	
	@PostMapping(value = "message/saveReply")
    @ResponseBody
    public String saveReply(
    		@RequestParam(required=true)String messageId,
    		@RequestParam(required=true)String replyText,
    		@ModelAttribute("loginUserDto") LoginUserDto loginUserDto){
		logger.info("Save or Update Reply");
		MessageDto dto = messageService.getReplyByMessageId(messageId);
		if(dto!=null) {
			dto.setCommentStr(replyText);
			messageService.updateMessage(dto);
		}else {
			MessageDto replyDto = new MessageDto();
			replyDto.setMsgType(IMessageService.MESSAGE_TYPE.回复);
			replyDto.setParentId(messageId);
			replyDto.setCommentStr(replyText);
			messageService.createMessage(replyDto);
		}
		MessageDto msgDto = messageService.getMessageById(messageId);
		msgDto.setReplyStatus(IMessageService.REPLY_STATUS.已答复);
		messageService.updateMessage(msgDto);
		String sedUserId = loginUserDto.getLoginedUserId();
		String receiveUserId = messageService.getMessageById(messageId).getPersonId();
		BaseSysMsgDto sysMsgDto = new BaseSysMsgDto();
		Date date = new Date();
		sysMsgDto.setCreateTimestampObj(date);
		sysMsgDto.setApprSendStatus(0);
		sysMsgDto.setContentText("您的留言已回复,请注意查收!");
		sysMsgDto.setReceiveUserId(receiveUserId);
		sysMsgDto.setSendUserId(sedUserId);
		sysMsgDto.setMessageId(messageId);
		notifyService.createSysMsg(sysMsgDto);
		return "ok";
	}
	
	/*
	 * 管理员通过messageId获取评论
	 */
	@PostMapping(value = "message/commentList")
    @ResponseBody
    public Map<String, Object> commentList(
    		@RequestParam(required=false) String messageId,
    		@RequestParam(required=false) String approvalStatus,
    		@RequestParam(required=false) String done,
    		@RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "20") String rows,
            @RequestParam(required=false) String keyword,
            @RequestParam(required=false) String messageEnable) {
		Map<String, Object> params = new HashMap<String, Object>();
		PageableDto pageable = new PageableDto(Integer.parseInt(page), Integer.parseInt(rows));
		StringBuffer sb = new StringBuffer();
		sb.append("from Message m where 1=1");
		sb.append(" and m.msgType = :msgType");
		params.put("msgType", IMessageService.MESSAGE_TYPE.评论);
		if(!VGUtility.isEmpty(messageId)) {
			sb.append(" and m.parent.id = :parent");
			params.put("parent", messageId);
		}
		
		
		if(!VGUtility.isEmpty(done)) {
			sb.append(" and m.approvalStatus <> :approvalStatus ");
			params.put("approvalStatus", IMessageService.APPROVAL_STATUS.未处理);
		}else {
			if(!VGUtility.isEmpty(approvalStatus) && !approvalStatus.trim().equals("")) {
				sb.append(" and m.approvalStatus = :approvalStatus");
				if(approvalStatus.equals("0") ) {
					params.put("approvalStatus", IMessageService.APPROVAL_STATUS.未处理);
				}
				if(approvalStatus.equals("1") ) {
					params.put("approvalStatus", IMessageService.APPROVAL_STATUS.审核不通过);
				}
				if(approvalStatus.equals("2") ) {
					params.put("approvalStatus", IMessageService.APPROVAL_STATUS.审核通过);
				}
			}
		}
		if(!VGUtility.isEmpty(keyword)) {
			sb.append(" and m.commentStr like :keyword");
			params.put("keyword", "%"+keyword+"%");
		}
		sb.append(" and m.messageEnable = :messageEnable order by m.createTimestamp desc");
		if(!VGUtility.isEmpty(messageEnable)) {
			//查询已删除评论（回收站）
			if(messageEnable.equals("1")) {
				params.put("messageEnable", IMessageService.ENABLE_STAUS.已删除);
			}
		}else {
			params.put("messageEnable", IMessageService.ENABLE_STAUS.使用中);
		}
		PageDto<MessageDto> pageDto = messageService.getMessages(sb.toString(), params, pageable);
		List<MessageDto> messageDtoList = pageDto.getRowData();
		//遍历所有评论，根据父级ID查询留言标题
		for(MessageDto dto :messageDtoList) {
			if(!VGUtility.isEmpty(dto.getParentId())) {
			 MessageDto newMessageDto = messageService.getMessageById(dto.getParentId());
			 dto.setTitle(newMessageDto.getTitle());
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", pageDto.getRowData());
		result.put("total", pageDto.getTotalCount());
		result.put("pageNum", page);
        return result;
    }
	/*
	 * 管理员通过messageId获取评论
	 */
	@PostMapping(value = "message/commentListAll")
    @ResponseBody
    public Map<String, Object> commentListAll(@RequestParam(required=true) String messageId) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("from Message m where 1=1");
		sb.append(" and m.msgType = :msgType");
		params.put("msgType", IMessageService.MESSAGE_TYPE.评论);
		sb.append(" and m.parent.id = :parent");
		params.put("parent", messageId);
		sb.append(" and m.messageEnable = :messageEnable");
		params.put("messageEnable", IMessageService.ENABLE_STAUS.使用中);
		PageDto<MessageDto> pageDto = messageService.getMessages(sb.toString(), params, null);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", pageDto.getRowData());
		result.put("total", pageDto.getTotalCount());
        return result;
    }
	
	
	/**
	 * 
	 * @author 何森
	 *@date 2018年11月13日上午8:54:25
	 *@return List<CommonComboDto>
	 *@Description:删除相关主题关联
	 */
	@PostMapping(value = "message/deleteComment")
    @ResponseBody
    public String deleteComment(@RequestParam(required=true) String id){
		logger.info("delete Message By Id id="+id);
		MessageDto dto = messageService.getMessageById(id);
		dto.setMessageEnable(IMessageService.ENABLE_STAUS.已删除);
		messageService.updateMessage(dto);
		return "ok";
	}
	
	/**
	 * 
	 * @author 何森
	 *@date 2018年11月13日上午9:54:25
	 *@return List<CommonComboDto>
	 *@Description:查询所有相关主题关联
	 */
	@GetMapping(value = "message/get_main_keyword_forcombo")
	@ResponseBody
	public List<CommonComboDto> commonCodeForCombo() {
		List<SysDictionaryDto> resultList = new ArrayList<SysDictionaryDto>();
		String sql = "select * from DictionaryCommonCode order by serialNumber";
		List<SysDictionaryDto> dictDtoList = sysDictionaryService.findAllDictionaryCommonCodeSql(sql);
		for (SysDictionaryDto dto : dictDtoList) {
			if (VGUtility.isEmpty(dto.getParentThemeId())) {
				if (sysDictionaryService.getThemeByParentThemeId(dto.getId()).size() > 0) {
					resultList.add(dto);

				}

			}
		}
		List<CommonComboDto> comboDtoList = convertDict(resultList);
		for (SysDictionaryDto dictDto : resultList) {
			 //List<SysDictionaryDto> sysDto = sysDictionaryService.getThemeByParentThemeId(dictDto.getId());
			   Map<String, Object> params = new HashMap<String, Object>();
	    	   String hql = "from DictionaryCommonCode  where parentThemeId= :parentThemeId order by serialNumber";
		       params.put("parentThemeId",dictDto.getId());
	    	   PageDto<SysDictionaryDto> dictPageDto = sysDictionaryService.getDictionaryCommonCode(hql, params, null);
	    	   List<SysDictionaryDto> sysDto = dictPageDto.getRowData();
				for (CommonComboDto comboDto : comboDtoList) {
					if (comboDto.getId().equals(dictDto.getId()))
						comboDto.setChildren(convertDict(sysDto));
				}
		}
		return comboDtoList;
	}

	 private List<CommonComboDto> convertDict(List<SysDictionaryDto> dictDtoList) {
	     List<CommonComboDto> resultList = new ArrayList<CommonComboDto>();
	     for (SysDictionaryDto tempDto : dictDtoList) {
	         CommonComboDto dto = new CommonComboDto();
	         dto.setId(tempDto.getId());
	         dto.setText(tempDto.getChsName());
	         dto.setValue(tempDto.getId());
	         resultList.add(dto);
	     }
	     	return resultList;
	 }
	
	
	/**
	 * @author 何森
	 *@date 2018年11月13日下午4:35:58
	 *@param type
	 *@param userId
	 *@return List<CommonComboDto>
	 *@Description:快速回复
	 */
	@GetMapping(value = "message/quick_reply")
	@ResponseBody
	public List<CommonComboDto> quickReplyForCombo() {
		 List<DictionaryCommonCodeDto> dictDtoList = dictService.getCommonCodeByType("MSG-REPLY");
		 List<CommonComboDto> resultList = convertQuickReply(dictDtoList);
		 return resultList;
	}
	
	private List<CommonComboDto> convertQuickReply(List<DictionaryCommonCodeDto> dictDtoList) {
	     List<CommonComboDto> resultList = new ArrayList<CommonComboDto>();
	     for (DictionaryCommonCodeDto tempDto : dictDtoList) {
	         CommonComboDto dto = new CommonComboDto();
	         dto.setId(tempDto.getId());
	         dto.setText(tempDto.getChsName());
	         dto.setValue(tempDto.getId());
	         dto.setValue1(tempDto.getEngName());
	         resultList.add(dto);
	     }
	     	return resultList;
	 }
	/**
	 * 
	 * @author 何森
	 *@date 2018年11月13日上午11:14:57
	 *@param messageId
	 *@param themeId
	 *@Description: 保存留言相关主题
	 */
	@GetMapping(value = "message/saveTheme")
	@ResponseBody
	public MessageDto saveTheme(@RequestParam String messageId, 
											@RequestParam  String themeId) {
		logger.info("Link Message With Theme Where MessageId = "+messageId+" And ThemeId = "+themeId);
		messageService.saveThemeByMessageIdAndThemeId(messageId,themeId);
		MessageDto dto = messageService.getMessageById(messageId);
		//return new ResponseEntity<String>("{\"success\":true}", HttpStatus.OK);
		return dto;
	}
	
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月27日上午10:16:55
	* @Description:查询未回复留言数量        
	* @return long    
	*
	 */
	@PostMapping(value = "message/unAnsweredMessageNums")
	@ResponseBody
	public Map<String, Object> unAnsweredMessageNums() {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Message m where 1=1";
		hql += " and m.replyStatus = :replyStatus ";
		params.put("replyStatus", IMessageService.REPLY_STATUS.未答复);
		hql += " and messageEnable=0 and m.msgType=0 order by m.createTimestamp desc";
		PageDto<MessageDto> pageDto = messageService.getMessages(hql,params,null);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", pageDto.getRowData());
		return result;
	}
	
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月27日上午10:37:21
	* @Description: 查询未审核留言数量       
	* @return long    
	*
	 */
	@PostMapping(value = "message/uncheckedMessageNums")
	@ResponseBody
	public Map<String, Object> uncheckedMessageNums() {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Message m where 1=1";
		hql += " and m.approvalStatus = :approvalStatus ";
		params.put("approvalStatus", IMessageService.APPROVAL_STATUS.未处理);
		hql += " and messageEnable=0 and m.msgType=0 order by m.createTimestamp desc";
		PageDto<MessageDto> pageDto = messageService.getMessages(hql,params,null);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", pageDto.getRowData());
		return result;
	}
	
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月27日下午2:26:29
	* @Description:系统收到新留言及评论会以弹窗显示     
	* @return Map<String,Object>    
	*
	 */
	@PostMapping(value = "message/messagePopUpPrompt")
	@ResponseBody
	public Map<String, Object> messagePopUpPrompt(@RequestParam(defaultValue = "1") String page,
                                     				@RequestParam(defaultValue = "20") String rows) {
		Map<String, Object> params = new HashMap<String, Object>();
		PageableDto pageable = new PageableDto(Integer.parseInt(page), Integer.parseInt(rows));
		String hql = " from Message m where 1=1";
		hql +=" and m.viewStatus=0";
		hql += " and m.messageEnable=0 and m.msgType=0 order by m.createTimestamp desc";
		PageDto<MessageDto> pageDto = messageService.getMessages(hql,params,pageable);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", pageDto.getRowData());
		return result;
	}
	
	/**
	 * 
	* @author 何森
	* @date 2018年12月3日上午9:24:10
	* @Description:根据messageId查询主题关联内容        
	* @return List<ThemeDto>    
	*
	 */
	@PostMapping(value = "message/findMessageThemeByMessageId")
	@ResponseBody
	public List<ThemeDto> findMessageThemeByMessageId(String messageId){
		List<ThemeDto> listDto = messageService.findMessageThemeByMessageId(messageId);
		return listDto;
	}
	
	/**
	 * 
	* @author 何森
	* @date 2018年12月3日下午1:43:33
	* @Description:根据themeId删除主题内容        
	* @return String    
	*
	 */
	@PostMapping(value = "message/deleteMainText")
	@ResponseBody
	public MessageDto deleteThemeById(String id,String messageId){
		messageService.deleteThemeById(id);
		MessageDto dto = messageService.getMessageById(messageId);
		return dto;
	}
	
	@GetMapping(value="message/get_message_images")
	@ResponseBody
	public List<ImageDto> getMessageImages(@RequestParam String messageId){
		logger.info("Get Message Images Where messageId="+messageId);
		return imageService.getMessageImages(messageId);
	}
	
	@GetMapping(value="dept_forcombo")
	@ResponseBody
	public List<CommonComboDto> getDeptForCombobox(@RequestParam(defaultValue="") String q){
		logger.info("Get Dept Where q="+q);
		q = VGUtility.isEmpty(q)?q:q.trim();
        List<DeptDto> finalDtoList = new ArrayList<DeptDto>();

        List<DeptDto> jtList = new ArrayList<DeptDto>();
        List<DeptDto> otherList = new ArrayList<DeptDto>();
        List<DeptDto> jsList = new ArrayList<DeptDto>();
        List<DeptDto> yyList = new ArrayList<DeptDto>();

        List<DeptDto> dtoListLv1 = userService.getDeptModelByParentDeptId(null);
        List<DeptDto> dtoListLv2 = userService.getDeptModelByParentDeptId(dtoListLv1.get(0).getId());
        for (DeptDto deptDto2: dtoListLv2) {
            if(deptDto2.getPropertyMap().get("Status").equals("disabled"))
                continue;
            String deptName = deptDto2.getDeptName();
            if("运营分公司".equals(deptName) || "建设分公司".equals(deptName) || "集团总部".equals(deptName)){
                List<DeptDto> dtoListLv3 = new ArrayList<DeptDto>();

                List<DeptDto> tempDtoListLv3 = userService.getDeptModelByParentDeptId(deptDto2.getId());
                for (DeptDto deptDto3: tempDtoListLv3) {
                    if(VGUtility.isEmpty(deptDto3.getPropertyMap()))
                        continue;
                    if(VGUtility.isEmpty(deptDto3.getPropertyMap().get("Status")))
                        continue;
                    if(deptDto3.getPropertyMap().get("Status").equals("disabled"))
                        continue;
                    String dept3Name = deptDto3.getDeptName();
                    if("运营分公司".equals(deptName))
                        if(Pattern.matches("运营.*", dept3Name))
                            dept3Name = dept3Name.substring(2,dept3Name.length());

                    String tempDept3Name = deptName + "." + dept3Name;
                    if(Pattern.matches(".*" + q + ".*", tempDept3Name)) {
                        deptDto3.setDeptName(tempDept3Name);
                        dtoListLv3.add(deptDto3);
                    }
                }

                if("运营分公司".equals(deptName))
                    yyList.addAll(dtoListLv3);
                if("建设分公司".equals(deptName))
                    jsList.addAll(dtoListLv3);
                if("集团总部".equals(deptName))
                    jtList.addAll(dtoListLv3);
            } else {
                if(Pattern.matches(".*" + q + ".*", deptDto2.getDeptName()))
                    otherList.add(deptDto2);
            }
        }

        finalDtoList.addAll(jtList);
        finalDtoList.addAll(otherList);
        finalDtoList.addAll(jsList);
        finalDtoList.addAll(yyList);
        List<CommonComboDto> commonComboDtoList = new ArrayList<CommonComboDto>();
        for(DeptDto deptDto : finalDtoList) {
        	CommonComboDto commonComboDto = new CommonComboDto();
        	commonComboDto.setId(deptDto.getId());
        	commonComboDto.setValue(deptDto.getId());
        	commonComboDto.setText(deptDto.getDeptName());
        	commonComboDtoList.add(commonComboDto);
        }
		return commonComboDtoList;
	}
	/**
	 * 推送至公众服务平台
	 * @throws Exception_Exception 
	 */
	@PostMapping(value = "send_psp")
	@ResponseBody
	public ResponseEntity<String> senToPsp(@ModelAttribute("loginUserDto") LoginUserDto loginUserDto, @RequestParam String messageId, @RequestParam String deptId,
											@RequestParam String exterFeedTimeStr, @RequestParam String interFeedTimeStr){
		//webservice接口
//		String url = "http://localhost:8080/services/msgservice?wsdl";
		DeptDto deptDto = userService.getDeptById(deptId);
		if(VGUtility.isEmpty(deptDto))
			throw new RuntimeException("该部门不存在,请重新选择");
		MessageDto messageDto = messageService.getMessageById(messageId);
		Map<String,String> map = new HashMap<String,String>();
	
	 	if (VGUtility.isEmpty(exterFeedTimeStr))
            throw new RuntimeException("外部反馈时间不能为空！");
        Date exterFeedTimeObj = VGUtility.toDateObj(exterFeedTimeStr, "yyyy-MM-dd HH:mm:ss");
        if (VGUtility.isEmpty(exterFeedTimeObj))
            throw new RuntimeException("外部反馈时间格式不正确");

        if (VGUtility.isEmpty(interFeedTimeStr))
            throw new RuntimeException("内部反馈时间不能为空！");
        Date interFeedTimeObj = VGUtility.toDateObj(interFeedTimeStr, "yyyy-MM-dd HH:mm:ss");
        if (VGUtility.isEmpty(interFeedTimeObj))
            throw new RuntimeException("外部反馈时间格式不正确");
        //外部反馈时限
        map.put("exterFeedTimeStr", exterFeedTimeStr);
        //内部反馈时限
        map.put("interFeedTimeStr", interFeedTimeStr);
		//留言Id
		map.put("messageId", messageId);
		//标题
		map.put("title", messageDto.getTitle());
		//内容
		map.put("commentStr", messageDto.getCommentStr());
		//诉求人
		map.put("complainant", loginUserDto.getLoginedUserName());
		//处办部门
		map.put("handSuggDeptId", deptId);
		//设置工单创建人
		map.put("createUserId", loginUserDto.getLoginedUserId());
		//获取主关键词
		List<ThemeDto> themeDtoList =messageService.findMessageThemeByMessageId(messageId);
//		if(VGUtility.isEmpty(themeDtoList) || themeDtoList.size()<1)
//			throw new RuntimeException("请选择主题分类!");
//		else map.put("mainKeyWordId", themeDtoList.get(0).getThemeId());
		logger.info("messageId:"+map.get("messageId")+"title:"+map.get("title")+"commentStr:"+map.get("commentStr")+"complainant:"+map.get("complainant")+"handSuggDeptId:"+map.get("handSuggDeptId")+"createUserId:"+map.get("createUserId")+"mainKeyWordId:"+map.get("mainKeyWordId")+"exterFeedTimeStr"+exterFeedTimeStr+"interFeedTimeStr"+interFeedTimeStr);
		MsgWebService service = new MsgWebService();
		IMsgWebService client = service.getMsgWebServiceImplPort();
		try {
			String result = client.msgHandler(map);
			if(result.equals("success")) {
				messageDto.setStatus("1");
				messageService.updateMessage(messageDto);
			}
		} catch (Exception_Exception e) {
				logger.info("推送失败原因:"+e);
				messageDto.setStatus("2");
				messageService.updateMessage(messageDto);
				throw new RuntimeException(e);
		}
		return new ResponseEntity<String>("{\"success\":true}", HttpStatus.OK);
	}
	
	
	/**
	 * 
	* @author 何森
	* @date 2019年1月3日下午1:54:02
	* @Description:待审批评论留言        
	* @return Map<String,Object>    
	*
	 */
	@PostMapping(value = "message/commentMessagesApproval")
    @ResponseBody
    public Map<String, Object> commentMessagesApproval(
    		@RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "20") String rows)
    {
		logger.info("Get Message Model By Query For DataGrid");
		Map<String, Object> params = new HashMap<String, Object>();
		PageableDto pageable = new PageableDto(Integer.parseInt(page), Integer.parseInt(rows));
		String hql = "from Message m where 1=1";
		hql += " and messageEnable=0 and m.msgType=0 order by m.createTimestamp desc";
		PageDto<MessageDto> pageDto = messageService.getMessages(hql, params, pageable);
		List<MessageDto> list = new ArrayList<MessageDto>();
		PageDto<MessageDto> newPageDto = new PageDto<MessageDto>(0, list);
		Map<String, Object> result = new HashMap<String, Object>();
		List<MessageDto> messageDtoList = pageDto.getRowData();
		for(MessageDto message:messageDtoList) {
			List<ThemeDto> themeDtoList =messageService.findMessageThemeByMessageId(message.getId());
			if(themeDtoList!=null) {
				StringBuffer buffer= null;
				StringBuffer bufferId= null;
				for(ThemeDto themeDto:themeDtoList) {
					if(!VGUtility.isEmpty(themeDto.getId())) {
						SysDictionaryDto sd = sysDictionaryService.getThemeById(themeDto.getThemeId());
						if(buffer == null) buffer = new StringBuffer(sd.getChsName());
						else buffer.append(";").append(sd.getChsName());
						if(bufferId == null) bufferId = new StringBuffer(sd.getId());
						else bufferId.append("-").append(sd.getId());
					}
				}
				//拼接主题分类Id
				message.setMainTextId(bufferId == null ? "" : bufferId.toString());
				//拼接主题分类name
				message.setMainText(buffer == null ? "" : buffer.toString());
			}
				//遍历留言，如果dto的待留言评论数量不为0，将留言信息放入result
				MessageDto dto = messageService.getMessageById(message.getId());
				if(dto.getCommentNeedNumber()>0) {
					list.add(dto);
					newPageDto.setRowData(list);
			}
				//通过ApprivalPersonId获取审核人名字
				if(!VGUtility.isEmpty(dto.getApprivalPersonId())) {
					UserDto userDto = userService.getUserById(dto.getApprivalPersonId());
					dto.setApprivalPersonName(userDto.getChsName());
				}
		}
		result.put("total", list.size());
		result.put("rows",newPageDto.getRowData());
        return result;
    }
	
	/**
	 * 
	* @author 何森
	* @date 2019年2月26日下午5:24:36
	* @Description:保存备注（处办部门）        
	* @return String
	 */
	@PostMapping(value = "message/saveDeptName")
    @ResponseBody
    public String saveDeptName(@RequestParam(required=true) String id,
    		@RequestParam(required=true) String deptName,
    		@ModelAttribute("loginUserDto") LoginUserDto loginUserDto){
		logger.info("Save  DeptName By Id id="+id+"deptName="+deptName);
		MessageDto dto = messageService.getMessageById(id);
		dto.setDeptName(deptName);
		messageService.updateMessage(dto);
		return "ok";
	}
}
