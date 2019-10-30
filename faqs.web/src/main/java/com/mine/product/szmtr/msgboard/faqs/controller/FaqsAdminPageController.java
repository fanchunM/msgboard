package com.mine.product.szmtr.msgboard.faqs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import com.mine.product.szmtr.msgboard.faqs.dto.FaqsDto;
import com.mine.product.szmtr.msgboard.faqs.service.IFaqsService;

@Controller
public class FaqsAdminPageController {
	private static final Logger logger = LoggerFactory.getLogger(FaqsAdminPageController.class);
	
	@Autowired
	private IFaqsService faqsService;
	
	
	@GetMapping("/faqsList")
	public String enterFaqsListPage() {
		logger.info("Enter faqsList Page");
		return "admin/faqs/faqsList";
	}
	
	@GetMapping("/faqsCheck")
	public String enterFaqsCheckPage() {
		logger.info("Enter faqsCheck Page");
		return "admin/faqs/faqsCheck";
	}
	
	@GetMapping("/toAddFaqs")
	public String toAddFaqsPage() {
		logger.info("Enter Add Faqs Page");
		return "admin/faqs/addFaqs";
	}
	
	@GetMapping("/toUpdateFaqs")
	public String toUpdateFaqsPage(ModelMap modelMap,String id) {
		
		FaqsDto faqsDto = faqsService.getFaqsById(id);
		modelMap.put("faqsDto", faqsDto);
		logger.info("Enter Update Faqs Page");
		
		return "admin/faqs/addFaqs";
	}
	
	/**
	 * 
	* @author 何森
	* @date 2018年12月13日下午3:27:54
	* @Description: 跳转问答详情页       
	* @return String    
	*
	 */
	@GetMapping("/toDetails")
	public String totoDetails(ModelMap modelMap,String id) {
		FaqsDto faqsDto = faqsService.getFaqsById(id);
		modelMap.put("faqsDto", faqsDto);
		logger.info("Enter Details Faqs Page");
		return "admin/faqs/detailsFaqs";
	}
	
}
