package com.mine.product.msgboard.ui.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.mine.product.msgboard.ui.util.SpringSecureUserInfo;
import com.mine.product.msgboard.ui.util.SpringSecurityUtil;
import com.mine.product.szmtr.msgboard.person.dto.PersonDto;
import com.mine.product.szmtr.msgboard.person.service.IPersonService;
import com.vgtech.platform.common.utility.VGUtility;

@Controller
public class AttentionsPageController {
	private static final Logger logger = LoggerFactory.getLogger(AttentionsPageController.class);
	@Autowired
	private IPersonService personService;
	@GetMapping(value="/attention")
	public String enter1Attention(ModelMap modelMap,String type,HttpSession session, Authentication authentication) {
		logger.info("Enter type Attention Page");
		if(!VGUtility.isEmpty(authentication)) {
			modelMap.addAttribute("person", ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo());
		}
		return "attention/"+type;
	}
	
	/**
	 * 
	* @author 何森
	* @date 2019年1月16日下午3:00:29
	* @Description:跳转至手机常用功能页面        
	* @return String    
	*
	 */
	@GetMapping("/mobileCommonFunction")
	public String enterMobileCommonFunction(ModelMap modelMap,HttpSession session, Authentication authentication) {
		logger.info("Enter type mobileCommonFunction Page");
		if(!VGUtility.isEmpty(authentication)) {
			modelMap.addAttribute("person", ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo());
		}
		return "commonFunction/mobileCommonFunction";
	}
	
	/**
	 * 
	* @author 何森
	* @date 2019年1月16日下午3:13:04
	* @Description: 跳转至手机注意事项页面          
	* @return String    
	*
	 */
	@GetMapping("/mobileAttention")
	public String enterMobileAttention(ModelMap modelMap,HttpSession session, Authentication authentication) {
		logger.info("Enter type mobileAttention Page");
		if(!VGUtility.isEmpty(authentication)) {
			modelMap.addAttribute("person", ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo());
		}
		return "attention/mobileAttentions";
	}
	
}
