package com.mine.product.szmtr.msgboard.news.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mine.product.szmtr.msgboard.message.dto.ImageDto;
import com.mine.product.szmtr.msgboard.message.service.IImageService;
import com.mine.product.szmtr.msgboard.news.dto.NewsDto;
import com.mine.product.szmtr.msgboard.news.service.INewsService;
import com.mine.product.szmtr.msgboard.person.dto.PersonDto;
import com.mine.product.szmtr.msgboard.person.service.IPersonService;
import com.vgtech.platform.common.utility.VGUtility;

@Controller
public class NewsAdminPageController {
	private static final Logger logger = LoggerFactory.getLogger(NewsAdminPageController.class);
	
	@Autowired
	private INewsService newsService;
	@Autowired
	private IImageService imageService;
	@Autowired
	private IPersonService personService;
	
	@GetMapping("/newsList")
	public String enterFaqsListPage() {
		logger.info("Enter newsList Page");
		return "admin/news/newsList";
	}
	
	@GetMapping("/newsCheck")
	public String enterFaqsCheckPage() {
		logger.info("Enter newsCheck Page");
		return "admin/news/newsCheck";
	}
	
	@GetMapping("/toAddTextNews")
	public String toAddTextNews(ModelMap modelMap,HttpServletRequest request) {
		logger.info("Enter Add Text News Page");
		request.getSession().removeAttribute("pics");
		String pTime = VGUtility.toDateStr(new Date(), "yyyy-MM-dd HH:mm:ss");
		modelMap.put("pTime", pTime);
		return "admin/news/addTextNews";
	}
	
	@GetMapping("/toAddPicNews")
	public String toAddPicNews(HttpServletRequest request,ModelMap modelMap) {
		logger.info("Enter Add Pic News Page");
		String pTime = VGUtility.toDateStr(new Date(), "yyyy-MM-dd HH:mm:ss");
		modelMap.put("pTime", pTime);
		request.getSession().removeAttribute("pics");
		List<ImageDto> images = new ArrayList<ImageDto>();
		request.getSession().setAttribute("pics", images);
		return "admin/news/addPicNews";
	}
	
	@GetMapping("/toAddLinkNews")
	public String toAddLinkNews(ModelMap modelMap,HttpServletRequest request) {
		logger.info("Enter Add Link News Page");
		request.getSession().removeAttribute("pics");
		String pTime = VGUtility.toDateStr(new Date(), "yyyy-MM-dd HH:mm:ss");
		modelMap.put("pTime", pTime);
		return "admin/news/addLinkNews";
	}
	
	@GetMapping("/toUpdateNews")
	public String toUpdateNewsPage(ModelMap modelMap,String id,HttpServletRequest request) {
		logger.info("Enter Update news Page");
		
		NewsDto newsDto = newsService.getNewsById(id);
		modelMap.put("newsDto", newsDto);
		String pTime = VGUtility.toDateStr(new Date(), "yyyy-MM-dd HH:mm:ss");
		modelMap.put("pTime", pTime);
		if(!VGUtility.isEmpty(newsDto.getLinkURL())) {
			return "admin/news/addLinkNews";
		}else {
			if(!VGUtility.isEmpty(newsDto.getNewsContent())) {
				return "admin/news/addTextNews";
			}else {
				List<ImageDto> images = imageService.getPicsByParentId(id);
				request.getSession().setAttribute("pics",images);
				return "admin/news/addPicNews";
			}
		}
	}
	
	@GetMapping("/toAddPic")
	public  String toAddPic(HttpServletRequest request){
		return "admin/news/addPic";		
	}
	
	@GetMapping(value = "/lookNewsPic")
	public String lookNewsPic(HttpServletRequest request,String id) throws Exception {
		List<ImageDto> is = (List<ImageDto>) request.getSession().getAttribute("pics");
		for(ImageDto p : is){
			if(p.getId().equals(id)){
				request.setAttribute("address",p.getAddress());
			}
		}
		return "admin/news/pic";
	}
	
	/**
	 * 
	* @author 何森
	* @date 2018年12月14日上午10:53:59
	* @Description: 跳转至新闻详情页面       
	* @return String    
	*
	 */
	@GetMapping("/newsDetail")
	public String enterNewsDetailPage(@RequestParam String id, ModelMap modelMap) {
		logger.info("Enter News Detail Page");
		NewsDto dto = newsService.getNewsById(id);
		modelMap.addAttribute("newsDto",dto);
		return "admin/news/newsDetail";
	}
}
