package com.mine.product.msgboard.ui.controller;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import com.mine.product.msgboard.ui.util.SpringSecureUserInfo;
import com.vgtech.platform.common.utility.VGUtility;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PersonPageController {
	private static final Logger logger = LoggerFactory.getLogger(PersonPageController.class);
	
	@Autowired
	protected AuthenticationManager authenticationManager;
	
	
	@GetMapping("/person")
	public String enterPerson(ModelMap modelMap, @RequestParam(required = false) String action, HttpSession session, Authentication authentication) {
		logger.info("Enter Person Page");
		if(!VGUtility.isEmpty(authentication)) {
			modelMap.addAttribute("person", ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo());
		}
		if (!VGUtility.isEmpty(action)) {
			modelMap.addAttribute("info", "您的密码强度偏低，请设置8-40位的数字，英文字母或者英文符号组合");
		}
		return "person/person";
	}
}
