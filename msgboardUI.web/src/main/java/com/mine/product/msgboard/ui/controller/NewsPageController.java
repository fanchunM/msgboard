package com.mine.product.msgboard.ui.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mine.product.msgboard.ui.util.SpringSecureUserInfo;
import com.mine.product.msgboard.ui.util.SpringSecurityUtil;
import com.mine.product.szmtr.msgboard.news.dto.NewsDto;
import com.mine.product.szmtr.msgboard.news.service.INewsService;
import com.mine.product.szmtr.msgboard.person.dto.PersonDto;
import com.mine.product.szmtr.msgboard.person.service.IPersonService;
import com.vgtech.platform.common.utility.VGUtility;

@Controller
public class NewsPageController {
	private static final Logger logger = LoggerFactory.getLogger(NewsPageController.class);
	@Autowired
	private IPersonService personService;
	@Autowired
	private INewsService newsService;
	
	@GetMapping("/news")
	public String enterNewsPage(ModelMap modelMap,HttpSession session, Authentication authentication) {
		logger.info("Enter News Page");
		if(!VGUtility.isEmpty(authentication)) {
			modelMap.addAttribute("person", ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo());
		}
		return "news/news";
	}
	@GetMapping("/newsDetail")
	public String enterNewsDetailPage(@RequestParam String id,
			                          @RequestParam(required=false) String state,
									  ModelMap modelMap,
									  HttpSession session, 
									  Authentication authentication) {
		logger.info("Enter News Detail Page");
		if(!VGUtility.isEmpty(authentication)) {
			modelMap.addAttribute("person", ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo());
		}
		NewsDto dto = newsService.getNewsById(id);
		if(dto.getNewsStatus()==INewsService.NEWS_STATUS.未处理&&VGUtility.isEmpty(state)) {
			throw new RuntimeException("页面不存在！");
		}
		//跳转页面增加点击量
		dto.setHits(dto.getHits()+1);
		NewsDto resultDto = newsService.createNews(dto);
		modelMap.addAttribute("newsDto", resultDto);
		return "news/newsDetail";
	}
	
	/**
	 * 
	* @author 何森
	* @date 2019年1月16日下午2:30:09
	* @Description:跳转至手机权威发布页面        
	* @return String    
	*
	 */
	@GetMapping("/mobileNews")
	public String enterMobileNewsPage(ModelMap modelMap,HttpSession session, Authentication authentication) {
		logger.info("Enter mobileNews Page");
		if(!VGUtility.isEmpty(authentication)) {
			modelMap.addAttribute("person", ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo());
		}
		return "news/mobileNews";
	}
}
