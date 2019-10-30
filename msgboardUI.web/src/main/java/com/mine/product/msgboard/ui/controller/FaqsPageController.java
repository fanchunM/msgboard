package com.mine.product.msgboard.ui.controller;

import java.util.List;

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
import com.mine.product.szmtr.msgboard.faqs.dto.FaqsDto;
import com.mine.product.szmtr.msgboard.faqs.dto.FaqsThemeDto;
import com.mine.product.szmtr.msgboard.faqs.service.IFaqsService;
import com.mine.product.szmtr.msgboard.message.dto.SysDictionaryDto;
import com.mine.product.szmtr.msgboard.message.service.IsysDictionaryService;
import com.mine.product.szmtr.msgboard.person.dto.PersonDto;
import com.mine.product.szmtr.msgboard.person.service.IPersonService;
import com.vgtech.platform.common.utility.VGUtility;


@Controller
public class FaqsPageController {
	private static final Logger logger = LoggerFactory.getLogger(FaqsPageController.class);
	@Autowired
	private IPersonService personService;
	@Autowired
	private IFaqsService faqsService;
	@Autowired
	private IsysDictionaryService syService;
	
	@GetMapping("/faqs")
	public String enterFaqsPage(ModelMap modelMap,HttpSession session, Authentication authentication) {
		logger.info("Enter Faqs Page");
		if(!VGUtility.isEmpty(authentication)) {
			modelMap.addAttribute("person", ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo());
		}
		return "faqs/faqs";
	}
	@GetMapping("/faqsDetail")
	public String enterFaqsDetailPage(@RequestParam String id,ModelMap modelMap,HttpSession session, Authentication authentication) {
		logger.info("Enter Faqs Detail Page");
		if(!VGUtility.isEmpty(authentication)) {
			modelMap.addAttribute("person", ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo());
		}
		FaqsDto dto = faqsService.getFaqsById(id);
		if(dto.getFaqsStatus()==IFaqsService.FAQSSTATUS.未处理) {
			throw new RuntimeException("页面不存在！");
		}
		//获取留言的主题分类
		List<FaqsThemeDto> themeList = faqsService.findFaqsThemeByFaqsId(id);
		StringBuffer buffer = new StringBuffer("");
		if(!VGUtility.isEmpty(themeList) && themeList.size()>0) {
			for(FaqsThemeDto faqsThemeDto : themeList) {
				SysDictionaryDto sysDto = syService.getSysDictionaryById(faqsThemeDto.getThemeId());
				String sonStr = sysDto.getChsName();
				String fatherStr = syService.getSysDictionaryById(sysDto.getParentThemeId()).getChsName();
				buffer.append(fatherStr+">"+sonStr+";");
			}
			buffer.deleteCharAt(buffer.length()-1);
			dto.setThemeStr(buffer.toString());
		}
		modelMap.addAttribute("faqsDto", dto);
		return "faqs/faqsDetail";
	}
	
	/**
	 * 
	* @author 何森
	* @date 2019年1月16日下午2:40:09
	* @Description:跳转至手机便民问答页面        
	* @return String    
	*
	 */
//	@GetMapping("/mobileFaqs")
//	public String enterMobileFaqsPage(ModelMap modelMap,HttpSession session, Authentication authentication) {
//		logger.info("Enter mobileFaqs Page");
//		if(!VGUtility.isEmpty(authentication)) {
//			modelMap.addAttribute("person", ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo());
//		}
//		return "faqs/mobileFaqs";
//	}
}
