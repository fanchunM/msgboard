package com.mine.product.msgboard.mobilephoneapproval;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.alibaba.fastjson.JSONObject;
import com.mine.base.sc.user.dto.UserDto;
import com.mine.base.sc.user.service.IUserService;
import com.mine.product.msgboard.util.HttpRequestUtils;
import com.mine.product.szmtr.msgboard.message.dto.MessageDto;
import com.mine.product.szmtr.msgboard.message.service.IMessageService;
import com.vgtech.platform.common.utility.VGUtility;
import com.vgtech.platform.common.web.dto.LoginUserDto;

@Controller
@SessionAttributes(value = { "loginUserDto" })
public class ApprovalPageController {
	private static final Logger logger = LoggerFactory.getLogger(ApprovalPageController.class);

	//测试
//	public static final String APP_ID = "68ce69533ae8490cacf95e1c1fb3d2cb"; 
//	public static final String SECRET_KEY = "f9e9b44a-a7b3-449a-8b9d-1dfa2429ca34"; 
//	public static final String ACCESSTOKEN_URL = "http://10.10.11.119/co/oapi/gettoken"; 
//	public static final String USERINFO_URL = "http://10.10.11.119/co/oapi/getuserinfo"; 
	
	//正式
	public static final String APP_ID = "65198b5292d64ea69a952908c679f9d0"; 
	public static final String SECRET_KEY = "b9f49741-c358-4aa0-8bcf-3d2a783046b3"; 
	public static final String ACCESSTOKEN_URL = "http://10.10.10.178:8095/co/oapi/gettoken"; 
	public static final String USERINFO_URL = "http://10.10.10.178:8095/co/oapi/getuserinfo"; 
	
	@Autowired
	private IMessageService messageService;
	@Autowired 
    private IUserService userService;
	
	@GetMapping("/mobile_login")
	public String dologin(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		logger.info("Enter dologin Page");
		String loginId = "";
		if (StringUtils.isEmpty(loginId)) {
            String code = request.getParameter("code");// 从URL中获取SNAP传递的code参数
            if (!StringUtils.isEmpty(code)) {
                try {
                	loginId = getUserInfo(code);// 获取用户信息
                } catch (Exception e) {
                	loginId = String.valueOf(request.getSession().getAttribute("loginId"));
                }
            }
        }
		if(loginId==null || loginId.length()==0) {
        	modelMap.put("errorMessage","暂未登录");
    		return "mobilephoneapproval/error";
        }
		UserDto userDto = userService.getUserByUserName(loginId);
		LoginUserDto loginUserDto = new LoginUserDto();
		loginUserDto.setLoginedUserId(userDto.getId());
        loginUserDto.setLoginedUserName(userDto.getUserName());
        loginUserDto.setLoginedChsName(userDto.getChsName());
		modelMap.addAttribute("loginUserDto", loginUserDto);
		return "redirect:newDaiBanListPage";
	}
	
	@GetMapping("/newDaiBanListPage")
	public String newDaiBanListPage(@ModelAttribute("LoginUserInfo") LoginUserDto user, ModelMap modelMap) {
		logger.info("Enter newDaiBanListPage Page");
		return "mobilephoneapproval/newDaiBanListPage";
	}
	
	@GetMapping("/dologin_page")
	public String loginPage(ModelMap modelMap) {
		logger.info("Enter loginPage Page");
		modelMap.put("errorMessage","暂未登录");
		return "mobilephoneapproval/error";
	}
	@GetMapping("/noticeDetail")
	public String loginPage(@ModelAttribute ("loginUserDto") LoginUserDto loginUserDto, @RequestParam String id,@RequestParam(required = false) String type, ModelMap modelMap) {
		logger.info("Enter noticeDetail Page");
		MessageDto messageDto = messageService.getMessageById(id);
		modelMap.put("dto",messageDto);
		if(!VGUtility.isEmpty(type)) {
			MessageDto partneDto = messageService.getMessageById(messageDto.getParentId());
			messageDto.setParentTitle(partneDto.getTitle());
			messageDto.setParentcommentStr(partneDto.getCommentStr());
			modelMap.put("type", "comment");
		}
		return "mobilephoneapproval/noticeDetail";
	}
	/*
	 * 获取用户信息
	 */
	private String getUserInfo(String code) {
		// 获取access token
		String param = "appid=" + APP_ID + "&secret=" + SECRET_KEY;
		String tockenStr = HttpRequestUtils.sendGet(ACCESSTOKEN_URL, param);
		JSONObject jsObj = JSONObject.parseObject(tockenStr);
		String accessToken = (String) jsObj.get("access_token");
		
		param = "access_token="+accessToken+"&code="+code;
		logger.info("sendGet---------------start");
		String resultStr = HttpRequestUtils.sendGet(USERINFO_URL, param);
		logger.info("sendGet------end---------jsobj---------------start"+resultStr);
		JSONObject jsobj = JSONObject.parseObject(resultStr);
		logger.info(jsobj.getString("userid")+"------------------------"+(jsobj.getString("userid")==null));
		return jsobj.getString("userid");
	}
	
//	@GetMapping("/mobile_login")
//	public String dologin(@ModelAttribute ("loginUserDto") LoginUserDto loginUserDto,ModelMap modelMap) {
//		logger.info("Enter dologin Page Where userId = "+loginUserDto.getLoginedUserId());
//		modelMap.addAttribute("userId", loginUserDto.getLoginedUserId());
//		return "mobilephoneapproval/newDaiBanListPage";
//	}
//	
//	@GetMapping("/dologin_page")
//	public String loginPage(ModelMap modelMap) {
//		logger.info("Enter loginPage Page");
//		modelMap.put("errorMessage","暂未登录");
//		return "mobilephoneapproval/error";
//	}
//	@GetMapping("/noticeDetail")
//	public String loginPage(@RequestParam String id,@RequestParam(required = false) String type, ModelMap modelMap) {
//		logger.info("Enter noticeDetail Page");
//		MessageDto messageDto = messageService.getMessageById(id);
//		if(!VGUtility.isEmpty(type)) {
//			MessageDto partneDto = messageService.getMessageById(messageDto.getParentId());
//			messageDto.setParentTitle(partneDto.getTitle());
//			messageDto.setParentcommentStr(partneDto.getCommentStr());
//			modelMap.put("type", "comment");
//		}
//		modelMap.put("dto",messageDto);
//		return "mobilephoneapproval/noticeDetail";
//	}
}
