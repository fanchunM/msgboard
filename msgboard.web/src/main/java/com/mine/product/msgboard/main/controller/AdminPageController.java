package com.mine.product.msgboard.main.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.mine.base.sc.user.dto.MenuDto;
import com.mine.base.sc.user.dto.UserDto;
import com.mine.base.sc.user.service.IPermissionService;
import com.mine.base.sc.user.service.IUserService;
import com.mine.product.szmtr.msgboard.person.dto.UserManageDto;
import com.mine.product.szmtr.msgboard.person.service.IUserManageService;
import com.vgtech.platform.common.utility.VGUtility;
import com.vgtech.platform.common.web.dto.LoginUserDto;

@Controller
@SessionAttributes(value = {"loginUserDto"})
public class AdminPageController {
	private static final Logger logger = LoggerFactory.getLogger(AdminPageController.class);
	
	@Autowired 
    private IUserService userService;
	@Autowired
    private IPermissionService permissionService;
	@Autowired
	private IUserManageService userManageService;
	
	@GetMapping("/login_page")
	public String enterLoginPage() {
		logger.info("Enter login Page");
		return "admin/login";
	}
	
	@GetMapping("/")
	public String szmtrLogin() {
		logger.info("Enter szmtr Page");
		return "login";
	}
	
	@PostMapping(value="loginWithoutPsd")
	public String loginWithoutPsd(@RequestParam String UserAccount, ModelMap modelMap,HttpServletRequest request){
		logger.info("Login Where Admin Name = "+UserAccount);
		UserDto userDto = userService.getUserByUserName(UserAccount);
		if(VGUtility.isEmpty(userDto))
			throw new RuntimeException("用户或者密码不正确!");
		//通过用户id查找用户是否被禁用(存在就禁用)
		UserManageDto userManageDto = userManageService.findUserByUserId(userDto.getId());
		if(!VGUtility.isEmpty(userManageDto)) {
			throw new RuntimeException("用户已被禁用，请联系管理员!");
		}
		//存储用户session
		LoginUserDto loginUserDto = new LoginUserDto();
		loginUserDto.setLoginedUserId(userDto.getId());
        loginUserDto.setLoginedUserName(userDto.getUserName());
        loginUserDto.setLoginedChsName(userDto.getChsName());
        modelMap.addAttribute("loginUserDto", loginUserDto);
        HttpSession session = request.getSession();
        session.setAttribute("loginUserId",userDto.getId());
        //查询该用户下面的权限
        List<MenuDto> menuMsgDtoList = permissionService.getMenuListByUser(userDto.getId(), "MSG_WEB");
        modelMap.addAttribute("menuMsgDtoList", menuMsgDtoList);
        return "redirect:/main";
	}
	
	@GetMapping("/main")
	public String enterMainPage(@ModelAttribute("loginUserDto") LoginUserDto loginUserDto, ModelMap modelMap) {
		logger.info("Enter Main Page");
		//查询该用户下面的权限
        List<MenuDto> menuMsgDtoList = permissionService.getMenuListByUser(loginUserDto.getLoginedUserId(), "MSG_WEB");
        modelMap.addAttribute("menuMsgDtoList", menuMsgDtoList);
		return "admin/main";
	}
	
	@PostMapping(value="admin_login")
	public String adminLogin(@RequestParam String userName, @RequestParam String pwd, ModelMap modelMap,HttpServletRequest request){
		logger.info("Login Where Admin Name = "+userName+" And pwd = "+pwd);
		UserDto userDto = userService.doLogin(userName, pwd);
		if(VGUtility.isEmpty(userDto))
			throw new RuntimeException("用户或者密码不正确!");
		//通过用户id查找用户是否被禁用(存在就禁用)
		UserManageDto userManageDto = userManageService.findUserByUserId(userDto.getId());
		if(!VGUtility.isEmpty(userManageDto)) {
			throw new RuntimeException("用户已被禁用，请联系管理员!");
		}
		//存储用户session
		LoginUserDto loginUserDto = new LoginUserDto();
		loginUserDto.setLoginedUserId(userDto.getId());
        loginUserDto.setLoginedUserName(userDto.getUserName());
        loginUserDto.setLoginedChsName(userDto.getChsName());
        modelMap.addAttribute("loginUserDto", loginUserDto);
        HttpSession session = request.getSession();
        session.setAttribute("loginUserId",userDto.getId());
        //查询该用户下面的权限
        List<MenuDto> menuMsgDtoList = permissionService.getMenuListByUser(userDto.getId(), "MSG_WEB");
        modelMap.addAttribute("menuMsgDtoList", menuMsgDtoList);
        
        return "admin/main";
	}
	
	/**
	 * 
	* @author 何森
	* @date 2019年1月2日下午2:55:12
	* @Description:注销        
	* @return String    
	*
	 */
	@GetMapping(value="LogOff")
	public String LogOff(HttpSession session,SessionStatus sessionStatus) {
		logger.info("Enter login Page(LogOff)");
		session.removeAttribute("loginUserDto");
		sessionStatus.setComplete();
		return "redirect:login_page";
	}
	
	/**
	 * 
	* @author 何森
	* @date 2019年1月7日下午7:17:22
	* @Description:首页链接        
	* @return String    
	*
	 */
	@GetMapping("/homeLinks")
	public String enterhomeLinksPage() {
		logger.info("Enter homeLinks Page");
		return "admin/homeLinks";
	}
}

