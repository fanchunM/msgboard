package com.mine.product.msgboard.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mine.product.msgboard.ui.util.IPUtils;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mine.product.msgboard.ui.util.SpringSecureUserInfo;
import com.mine.product.msgboard.ui.util.SpringSecurityUtil;
import com.mine.product.szmtr.msgboard.message.dto.ImageDto;
import com.mine.product.szmtr.msgboard.message.dto.MessageDto;
import com.mine.product.szmtr.msgboard.message.service.IMessageService;
import com.mine.product.szmtr.msgboard.message.service.IMessageService.APPROVAL_STATUS;
import com.mine.product.szmtr.msgboard.person.dto.CursorDto;
import com.mine.product.szmtr.msgboard.person.dto.PersonCursorDto;
import com.mine.product.szmtr.msgboard.person.dto.PersonDto;
import com.mine.product.szmtr.msgboard.person.service.ICursorService;
import com.mine.product.szmtr.msgboard.person.service.IPersonCursorService;
import com.mine.product.szmtr.msgboard.person.service.IPersonService;
import com.vgtech.platform.common.utility.VGUtility;

@Controller
public class MessagePageController {
	private static final Logger logger = LoggerFactory.getLogger(MessagePageController.class);
	@Autowired
	private IPersonService personService;
	@Autowired
	private IMessageService messageService;
	@Autowired
	private ICursorService cursorService;
	@Autowired
	private IPersonCursorService psersonCursorService;

	private static final String pwdReg = "/^(?=.*\\d)(?=.*[a-zA-Z]).{8,40}$/";

	@GetMapping("/")
	public String enterDefaultPage(ModelMap modelMap,HttpSession session, HttpServletRequest request, Authentication authentication) {
		logger.info("Enter Default Page");
		request.getSession().removeAttribute("error");
		if(!VGUtility.isEmpty(authentication)) {
			modelMap.addAttribute("person", ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo());
			PersonDto personDto = ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo();
		    //添加登陆记录
            String clientIp = IPUtils.getClientIp(request);
            personService.addLoginHistory(personDto.getUserName(), 1, clientIp);
            if (!VGUtility.isEmpty(request.getSession().getAttribute("pwdStrong"))) {
                return "redirect:/person?action=changePwd";
            }
			personService.changeRecentLoginIP(personDto.getId(),IPUtils.getClientIp(request));
		}
//		//登录成功后，像cursor表中添加微信用户信息
//		HttpSession wxsession = request.getSession();
//		CursorDto cursorDto =(CursorDto)wxsession.getAttribute("CursorDto");
//		if(cursorDto!=null) {
//			cursorDto = cursorService.createCursor(cursorDto);
//			//登录成功后，像personcursor表中添加关联id
//			PersonDto p = ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo();
//			PersonCursorDto personCursorDto = new PersonCursorDto();
//			personCursorDto.setCursorId(cursorDto.getId());	//微信Id
//			personCursorDto.setPersonId(p.getId());			//PersonId
//			psersonCursorService.createPsersonCursor(personCursorDto);
//		}
//		//删除session
//		session.removeAttribute("CursorDto");
		linkPersonCursor(session, request, authentication);
		return "redirect:/home";
	}
	@GetMapping("/message")
	public String enterMessagePage() {
		logger.info("Enter Message Page");
		return "message/message";
	}
	@GetMapping("/home")
	public String enterHomePage(ModelMap modelMap,HttpSession session, Authentication authentication,HttpServletRequest request) {
		logger.info("Enter Home Page");
		if (!VGUtility.isEmpty(request.getSession().getAttribute("pwdStrong"))) {
			return "redirect:/person?action=changePwd";
		}
		if(!VGUtility.isEmpty(authentication)) {
			PersonDto personDto = ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo();
			String pwd = personDto.getPassword();
			modelMap.addAttribute("person", personDto);
			personService.changeRecentLoginIP(personDto.getId(),IPUtils.getClientIp(request));
		}
		modelMap.addAttribute("ifHome", true);
		return "message/home";
	}
	/**
	 * 
	 * <一句话功能简述>
	 * <功能详细描述>备注，该功能修改时，ui.SuEXing中enterMobileHomePage方法注意下，根据业务需要是否需要修改
	 * @param modelMap
	 * @param authentication
	 * @param request
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping("/mobile_home")
	public String enterMobileHomePage(ModelMap modelMap,Authentication authentication,HttpServletRequest request) {
		logger.info("Enter mobileHome Page");
		if (!VGUtility.isEmpty(request.getSession().getAttribute("pwdStrong"))) {
			return "redirect:/person?action=changePwd";
		}
		if(!VGUtility.isEmpty(authentication)) {
			PersonDto personDto = ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo();
			String pwd = personDto.getPassword();
			modelMap.addAttribute("person", personDto);
			//更改用户登录IP地址
            personService.changeRecentLoginIP(personDto.getId(),IPUtils.getClientIp(request));
            //通过用户id查询关联表是否为null(注：返回personCursorDtoList是因为一个账号可同时关联微信Id和苏e行Id,不止一条数据)
            List<PersonCursorDto> personCursorDtoList = psersonCursorService.findListByPersonId(personDto.getId());
            if(!VGUtility.isEmpty(personCursorDtoList)) {
                for(PersonCursorDto personCursorDto:personCursorDtoList) {
                    if(personCursorDto!=null&&personCursorDto.getPhoneState()!=null&&personCursorDto.getType()!=null) {
                        //若关联类型是‘苏e行’并且手机号状态为1（状态1为手机号之前被注册过）
                        if(personCursorDto.getPhoneState().equals("1")&&personCursorDto.getType().equals("苏e行")) {
                            modelMap.addAttribute("phoneState",personCursorDto.getPhoneState());
                            personCursorDto.setPhoneState("0");
                            //提示后，将状态改为‘0’，下次登录不再提示
                            psersonCursorService.updatePersonCursor(personCursorDto);
                        }
                    }
                }
            }
		}
		modelMap.addAttribute("isMobileHome", true);
		return "message/mobileHome";
	}
	@RequestMapping("/messageDetail")
	public String enterMessageDetailPage(@RequestParam String id, HttpServletRequest request, ModelMap modelMap, Authentication authentication) {
		logger.info("Enter MessageDetail Page Where messageId="+id);
		if (!VGUtility.isEmpty(request.getSession().getAttribute("pwdStrong"))) {
			return "redirect:/person?action=changePwd";
		}
		if(!VGUtility.isEmpty(id)) {
			MessageDto messageDto = messageService.getMessageById(id);
			/*if(messageDto.getApprovalStatus()==IMessageService.APPROVAL_STATUS.审核不通过) {
				throw new RuntimeException("页面不存在！");
			}*/
			/*if(messageDto.getMessageEnable()==IMessageService.ENABLE_STAUS.已删除) {
				throw new RuntimeException("页面不存在！");
			}*/
			if(!VGUtility.isEmpty(messageDto)) {
				MessageDto rDto = messageService.getReplyByMessageId(id);
				long count = messageService.getMessageComment(id,"0");
				messageDto.setCommentNumber(count);
				if(!VGUtility.isEmpty(rDto)) {
					messageDto.setReply(rDto.getCommentStr());
				}
				modelMap.addAttribute("messageDto", messageDto);
			}
		}
		if(!VGUtility.isEmpty(authentication)) {
			PersonDto personDto = ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo();
			String pwd = personDto.getPassword();
			modelMap.addAttribute("person", personDto);
			//通过用户id查询关联表是否为null(注：返回personCursorDtoList是因为一个账号可同时关联微信Id和苏e行Id,不止一条数据)
            List<PersonCursorDto> personCursorDtoList = psersonCursorService.findListByPersonId(personDto.getId());
            if(!VGUtility.isEmpty(personCursorDtoList)) {
                for(PersonCursorDto personCursorDto:personCursorDtoList) {
                    if(personCursorDto!=null&&personCursorDto.getUserNameState()!=null&&personCursorDto.getType()!=null) {
                      //若关联类型是‘苏e行’并且昵称状态为0（状态0为系统自动注册账户名）
                      if(("0").equals(personCursorDto.getUserNameState())&&("苏e行").equals(personCursorDto.getType())) {
                          modelMap.addAttribute("userNameState",personCursorDto.getUserNameState());
                      }
                    }
                }
            }
		}
		return "message/messageDetail";
	}
	
	@RequestMapping("/goDetail")
	public String goDetailPage(@RequestParam String id,HttpSession session, ModelMap modelMap, Authentication authentication, HttpServletRequest request) {
		logger.info("Enter MessageDetail Page Where messageId="+id);
		if (!VGUtility.isEmpty(request.getSession().getAttribute("pwdStrong"))) {
			return "redirect:/person?action=changePwd";
		}
		if(!VGUtility.isEmpty(id)) {
			MessageDto messageDto = messageService.getMessageById(id);
			if(!VGUtility.isEmpty(messageDto)) {
				MessageDto rDto = messageService.getReplyByMessageId(id);
				if(!VGUtility.isEmpty(rDto)) {
					messageDto.setReply(rDto.getCommentStr());
				}
				modelMap.addAttribute("messageDto", messageDto);
			}
		}
		if(!VGUtility.isEmpty(authentication)) {
			PersonDto personDto = ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo();
			String pwd = personDto.getPassword();
			modelMap.addAttribute("person", personDto);
		}
		linkPersonCursor(session, request, authentication);
		return "message/messageDetail";
	}
	
	@GetMapping("/message_reminder")
	public String enterMessageReminderPage(ModelMap modelMap,HttpSession session, Authentication authentication, HttpServletRequest request) {
		logger.info("Enter Message Reminder Page");
		if (!VGUtility.isEmpty(request.getSession().getAttribute("pwdStrong"))) {
			return "redirect:/person?action=changePwd";
		}
		if(!VGUtility.isEmpty(authentication)) {
			PersonDto personDto = ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo();
			String pwd = personDto.getPassword();
			modelMap.addAttribute("person", personDto);
		}
		linkPersonCursor(session, request, authentication);
		return "message/messageReminder";
	}
	@GetMapping("/mobile_message_detail")
	public String enterMobileMessageDetailPage(@RequestParam String id, HttpSession session,  ModelMap modelMap, HttpServletRequest request, Authentication authentication) {
		logger.info("Enter Mobile MessageDetail Page Where messageId="+id);
		if (!VGUtility.isEmpty(request.getSession().getAttribute("pwdStrong"))) {
			return "redirect:/person?action=changePwd";
		}
		if(!VGUtility.isEmpty(id)) {
			MessageDto messageDto = messageService.getMessageById(id);
			if(!VGUtility.isEmpty(messageDto)) {
				MessageDto rDto = messageService.getReplyByMessageId(id);
				long count = messageService.getMessageComment(id,"0");
				messageDto.setCommentNumber(count);
				if(!VGUtility.isEmpty(rDto)) {
					messageDto.setReply(rDto.getCommentStr());
				}
				modelMap.addAttribute("messageDto", messageDto);
			}
		}
		if(!VGUtility.isEmpty(authentication)) {
			PersonDto personDto = ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo();
			String pwd = personDto.getPassword();
			modelMap.addAttribute("person", personDto);
			//通过用户id查询关联表是否为null(注：返回personCursorDtoList是因为一个账号可同时关联微信Id和苏e行Id,不止一条数据)
            List<PersonCursorDto> personCursorDtoList = psersonCursorService.findListByPersonId(personDto.getId());
            if(!VGUtility.isEmpty(personCursorDtoList)) {
                for(PersonCursorDto personCursorDto:personCursorDtoList) {
                    if(personCursorDto!=null&&personCursorDto.getUserNameState()!=null&&personCursorDto.getType()!=null) {
                      //若关联类型是‘苏e行’并且昵称状态为0（状态0为系统自动注册账户名）
                      if(("0").equals(personCursorDto.getUserNameState())&&("苏e行").equals(personCursorDto.getType())) {
                          modelMap.addAttribute("userNameState",personCursorDto.getUserNameState());
                      }
                    }
                }
            }
		}
		return "message/mobileMessageDetail";
	}
	
	@GetMapping("/messageRelease")
	public String enterMessageReleasePage(HttpSession session,ModelMap modelMap, HttpServletRequest request, Authentication authentication) {
		logger.info("Enter MessageRelease Page");
		if (!VGUtility.isEmpty(request.getSession().getAttribute("pwdStrong"))) {
			return "redirect:/person?action=changePwd";
		}
		request.getSession().removeAttribute("addPic");
		List<ImageDto> imageDtoList = new ArrayList<ImageDto>();
		request.getSession().setAttribute("addPic",imageDtoList);
		if(!VGUtility.isEmpty(authentication)) {
			PersonDto personDto = ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo();
			String pwd = personDto.getPassword();
			modelMap.addAttribute("person", personDto);
			//通过用户id查询关联表是否为null(注：返回personCursorDtoList是因为一个账号可同时关联微信Id和苏e行Id,不止一条数据)
            List<PersonCursorDto> personCursorDtoList = psersonCursorService.findListByPersonId(personDto.getId());
            if(!VGUtility.isEmpty(personCursorDtoList)) {
                for(PersonCursorDto personCursorDto:personCursorDtoList) {
                    if(personCursorDto!=null&&personCursorDto.getUserNameState()!=null&&personCursorDto.getType()!=null) {
                      //若关联类型是‘苏e行’并且昵称状态为0（状态0为系统自动注册账户名）
                      if(("0").equals(personCursorDto.getUserNameState())&&("苏e行").equals(personCursorDto.getType())) {
                          modelMap.addAttribute("userNameState",personCursorDto.getUserNameState());
                      }
                    }
                }
            }
		}
		linkPersonCursor(session, request, authentication);
		return "message/messageRelease";
	}
	
	@GetMapping(value = "/toAddImage.do")
	public String toAddImage(){
		logger.info("Enter AddImage Page");
		return "message/addImage";		
	}
	//查看图片
	@GetMapping(value = "/lookPic.do")
	public String lookPic(HttpServletRequest request,String id) throws Exception {
		List<ImageDto> is = (List<ImageDto>) request.getSession().getAttribute("addPic");
		for(ImageDto p : is){
			if(p.getId().equals(id)){
				request.setAttribute("address",p.getAddress());
			}
		}
		//UploadPhoto photo = uploadPhotoService.selectByPrimaryKey(id);
		return "message/pic";
	}
	//绑定微信用户关联表及创建微信用户信息表
	private void linkPersonCursor(HttpSession session, HttpServletRequest request, Authentication authentication) {
	    //判断当前页面是否存在用户
	    if(!VGUtility.isEmpty(authentication)) {
	      //登录成功后，像cursor表中添加微信用户信息
	        HttpSession wxsession = request.getSession();
	        CursorDto cursorDto =(CursorDto)wxsession.getAttribute("CursorDto");
	        if(cursorDto!=null) {
	            //判断微信用户表中是否存在当前登录用户的OpenId，如果没有则添加关联及微信用户表
	            if(cursorService.findUserByOpenId(cursorDto.getOpenId())==null){
	                cursorDto = cursorService.createCursor(cursorDto);
	                //登录成功后，像personcursor表中添加关联id
	                PersonDto p = ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo();
	                PersonCursorDto personCursorDto = new PersonCursorDto();
	                personCursorDto.setCursorId(cursorDto.getId()); //微信Id
	                personCursorDto.setPersonId(p.getId());         //PersonId
	                psersonCursorService.createPsersonCursor(personCursorDto); 
	            }
	        }
	        //删除session
	        session.removeAttribute("CursorDto");
        }
	}
}
