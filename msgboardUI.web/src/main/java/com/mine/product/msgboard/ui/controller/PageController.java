package com.mine.product.msgboard.ui.controller;

import com.mine.product.szmtr.msgboard.person.service.IPersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.mine.product.szmtr.msgboard.person.dto.CursorDto;
import com.vgtech.platform.common.utility.VGUtility;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Properties;

@Controller
public class PageController {
	private static final Logger logger = LoggerFactory.getLogger(PageController.class);
	@Autowired
	private IPersonService personService;
	@RequestMapping(value = "/login_page", method = {RequestMethod.POST,RequestMethod.GET})
	public String enterLoginPage(@RequestParam(defaultValue="1") String biaozhi, ModelMap modelMap) {
		modelMap.addAttribute("biaozhi", biaozhi);
		return "loginPage";
	}
	@GetMapping("/loginTest")
	public String enterLoginTestPage() {
		logger.info("Enter Login Page");
		return "loginTest";
	}
	
	/**
	 * 
	* @author 何森
	* @date 2019年1月15日下午4:38:07
	* @Description: 跳转用户协议页面       
	* @return String    
	*
	 */
	@GetMapping("/agreement")
	public String enterAgreementPage() {
		logger.info("Enter Login Page");
		return "agreement";
	}
}
