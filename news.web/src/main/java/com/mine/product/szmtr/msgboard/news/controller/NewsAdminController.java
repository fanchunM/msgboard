package com.mine.product.szmtr.msgboard.news.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.mine.base.sc.user.dto.UserDto;
import com.mine.base.sc.user.service.IUserService;
import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.message.dto.ImageDto;
import com.mine.product.szmtr.msgboard.message.service.IImageService;
import com.mine.product.szmtr.msgboard.message.service.IMessageService;
import com.mine.product.szmtr.msgboard.news.dto.NewsDto;
import com.mine.product.szmtr.msgboard.news.service.INewsService;
import com.vgtech.platform.common.utility.VGUtility;
import com.vgtech.platform.common.web.dto.LoginUserDto;

@Controller
@RequestMapping(value = "/admin/")
@SessionAttributes(value = {"loginUserDto"})
public class NewsAdminController {
	private static final Logger logger = LoggerFactory.getLogger(NewsAdminController.class);
	
	@Autowired
	private INewsService newsService;
	@Autowired
	private IImageService imageService;
	@Autowired
    private IUserService userService;
	
	@PostMapping(value = "news/newsList")
    @ResponseBody
    public Map<String, Object> newsList(
    		@RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "20") String rows,
            String keyword,Integer newsStatus)
    {
		Resource res1 = new ClassPathResource("config.properties");
		Properties p = new Properties();
		try {
			p.load(res1.getInputStream());
			res1.getInputStream().close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//获取配置文件域名地址
		String frontUrl = p.getProperty("front_url").replaceAll("\\s*", "");
		
		logger.info("Get News By Query For DataGrid");
		Map<String, Object> params = new HashMap<String, Object>();
		PageableDto pageable = new PageableDto(Integer.parseInt(page), Integer.parseInt(rows));
		
		String hql = "from News n where 1=1 ";
		
		if (!VGUtility.isEmpty(keyword)) {
			hql += " and (n.title like :title or n.subTitle like :subTitle "
					+ "or n.newsContent like :newsContent or n.newsDesc like :newsDesc)";
			params.put("title", "%"+keyword+"%");
			params.put("subTitle", "%"+keyword+"%");
			params.put("newsContent", "%"+keyword+"%");
			params.put("newsDesc", "%"+keyword+"%");
		}
		
		if (!VGUtility.isEmpty(newsStatus)) {
			hql += " and n.newsStatus = :newsStatus ";
			if(newsStatus==0) {
				params.put("newsStatus", INewsService.NEWS_STATUS.未处理);
			}
			if(newsStatus==1) {
				params.put("newsStatus", INewsService.NEWS_STATUS.审核不通过);
			}
			if(newsStatus==2) {
				params.put("newsStatus", INewsService.NEWS_STATUS.审核通过);
			}
		}
		hql += " order by n.createTimestamp desc";
		PageDto<NewsDto> pageDto = newsService.getNews(hql, params, pageable,frontUrl);
		List<NewsDto> newsDtoList= pageDto.getRowData();
		for(NewsDto news:newsDtoList) {
			//通过personId获取创建人名字
			if(!VGUtility.isEmpty(news.getPersonId())) {
				UserDto userDto = userService.getUserById(news.getPersonId());
				news.setPersonName(userDto.getChsName());
			}
			//通过ApprivalPersonId获取审核人名字
			if(!VGUtility.isEmpty(news.getApprivalPersonId())) {
				UserDto userDto = userService.getUserById(news.getApprivalPersonId());
				news.setApprivalPersonName(userDto.getChsName());
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", pageDto.getRowData());
		result.put("total", pageDto.getTotalCount());
        return result;
    }
	
	@PostMapping(value = "news/deleteNews")
    @ResponseBody
    public String deleteNews(@RequestParam(required=true) String id){
		logger.info("delete News By Id id="+id);
		newsService.deleteNews(id);
		return "ok";
	}
	
	@PostMapping(value = "news/checkNews")
    @ResponseBody
    public String checkFaqs(@RequestParam(required=true) String id,
    		@RequestParam(required=true) Integer type,
    		@ModelAttribute("loginUserDto") LoginUserDto loginUserDto){
		logger.info("Check News By Id id="+id);
		NewsDto dto = newsService.getNewsById(id);
		//获取当前登录用户id，存入审核人id
		String loginUserId =loginUserDto.getLoginedUserId();
		dto.setApprivalPersonId(loginUserId);
		if(type == 2) {
			dto.setNewsStatus(INewsService.NEWS_STATUS.审核通过);
		}
		if(type == 1) {
			dto.setNewsStatus(INewsService.NEWS_STATUS.审核不通过);
		}
		newsService.updateNews(dto);
		return "ok";
	}
	@PostMapping(value = "news/saveNews")
    @ResponseBody
    public String saveNews(NewsDto dto,
    						HttpServletRequest request,
    						@ModelAttribute("loginUserDto") LoginUserDto loginUserDto){
		logger.info("Save or Update News Where Id = "+dto.getId());
		String loginUserId =loginUserDto.getLoginedUserId();
		if(!VGUtility.isEmpty(dto.getId())) {
			logger.info("Update News");
			NewsDto newsDto = newsService.getNewsById(dto.getId());
			newsDto.setPersonId(loginUserId);
			newsDto.setNewsStatus(INewsService.NEWS_STATUS.未处理);
			newsDto.setTitle(dto.getTitle());
			newsDto.setSubTitle(dto.getSubTitle());
			newsDto.setAuthor(dto.getAuthor());
			newsDto.setDeptName(dto.getDeptName());
			newsDto.setTopStatus(dto.isTopStatus());
			newsDto.setPublishTime(dto.getPublishTime());
			newsDto.setNewsContent(dto.getNewsContent());
			newsDto.setNewsDesc(dto.getNewsDesc());
			newsDto.setApprivalPersonId("");
			NewsDto updateDto = newsService.updateNews(newsDto);
			
			imageService.deletePicsByParentId(newsDto.getId());
			
			List<ImageDto> images = (List<ImageDto>) request.getSession().getAttribute("pics");
			if(!VGUtility.isEmpty(images)&&images.size()>0){
				for(ImageDto img : images) {
					img.setId(null);
					img.setParentId(updateDto.getId());
					ImageDto i = imageService.createImageModel(img);
				}
			}
		}else {
			logger.info("Save News");
			dto.setNewsStatus(INewsService.NEWS_STATUS.未处理);
			dto.setPersonId(loginUserId);
			NewsDto newsDto = newsService.createNews(dto);
			System.out.println(newsDto.getId()+"-------");
			List<ImageDto> images = (List<ImageDto>) request.getSession().getAttribute("pics");
			if(!VGUtility.isEmpty(images)&&images.size()>0){
				for(ImageDto img : images) {
					img.setId(null);
					img.setParentId(newsDto.getId());
					ImageDto i = imageService.createImageModel(img);
				}
			}
		}
		return "ok";
	}
	
	@PostMapping(value = "news/getPics")
    @ResponseBody
    public Map<String, Object> getPics(HttpServletRequest request,String id){
		logger.info("Get News Pics");
		Map<String, Object> result = new HashMap<String, Object>();
		List<ImageDto> images = (List<ImageDto>) request.getSession().getAttribute("pics");
		result.put("rows", images);
		result.put("total", 0);
		return result;
	}
	
	@PostMapping(value="news/uploadImage")
	public @ResponseBody String uploadImage(@RequestParam("myfiles") MultipartFile[] fs, HttpServletRequest request
			,String pName) throws Exception{
		logger.info("Upload News Pics");
		Resource res1 = new ClassPathResource("config/file.properties");
		Properties p = new Properties();
		try {
			p.load(res1.getInputStream());
			res1.getInputStream().close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
 	   
		//获得路径
 		String path=p.getProperty("root");
 		File dirPath = new File(path);
 	      if(!dirPath.exists()) {
 	        dirPath.mkdir();
 	    }
		String fileName = "";
		for(MultipartFile myfile : fs){
			if(myfile.isEmpty()){
				return "1"; 
			}else{
				String currentDate=VGUtility.toDateStr(new Date(), "yyyyMMddHHmmss");
				fileName=currentDate+"_"+myfile.getOriginalFilename();
				FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(path, fileName));
			}
		}
		List<ImageDto> is = (List<ImageDto>) request.getSession().getAttribute("pics");
		
		ImageDto imageDto = new ImageDto();
		imageDto.setId(UUID.randomUUID().toString());
		imageDto.setImageName(pName);
		imageDto.setAddress(fileName);
//		imageDto.setParentId();
		//uploadPhotoService.insertSelective(photo);
//		ImageDto dto = imageService.createImageModel(imageDto);
		is.add(imageDto);
		request.getSession().setAttribute("pics",is);
		
		return fileName;
	}
		
	@RequestMapping(value = "news/delPic", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String delPic(HttpServletRequest request,String id) throws Exception {
		logger.info("Delete News Pics");
		List<ImageDto> is = (List<ImageDto>) request.getSession().getAttribute("pics");
		Iterator<ImageDto> iterator = is.iterator();
        while(iterator.hasNext()){
        	ImageDto temp = iterator.next();
            if(temp.getId().equals(id)){
            	iterator.remove();
			}
        }
		return "ok";
	}

	@RequestMapping(value = "/news/upload", method = { RequestMethod.POST })
	public @ResponseBody
	Map<String, Object> uploadPic(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>(2);
		// 读取配置文件
		Resource res1 = new ClassPathResource("config/file.properties");

		Properties p = new Properties();
		try {
			p.load(res1.getInputStream());
			res1.getInputStream().close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile mFile = (CommonsMultipartFile) multipartRequest.getFile("imgFile");
		String str = "true";

		// 获得路径
		String path = p.getProperty("root");
		File dirPath = new File(path);
		if (!dirPath.exists()) {
			dirPath.mkdir();
		}

		String oldName = null;
		String newName = null;
		String currentDate =  VGUtility.toDateStr(new Date(), "yyyyMMddHHmmss");
		if (!mFile.isEmpty()) {
			String newfileName1 = currentDate + "_" + mFile.getOriginalFilename();
			FileCopyUtils.copy(mFile.getBytes(), new File(path + File.separator + newfileName1));

			oldName = mFile.getOriginalFilename();
			newName = newfileName1;

		}
		String saveUrl = "resources/upload";
		result.put("error", 0);
		result.put("url", saveUrl + "/" + newName);

		return result;
	}

	@RequestMapping(value = "/news/fileManager", method = { RequestMethod.GET })
	public @ResponseBody
	Map<String, Object> fileManager(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>(2);

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile mFile = (CommonsMultipartFile) multipartRequest.getFile("imgFile");
		String str = "true";

		Resource res1 = new ClassPathResource("config/file.properties");
		Properties p = new Properties();
		try {
			p.load(res1.getInputStream());
			res1.getInputStream().close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 获得路径
		String path = p.getProperty("root");
		File dirPath = new File(path);
		if (!dirPath.exists()) {
			dirPath.mkdir();
		}

		String oldName = null;
		String newName = null;
		String currentDate =  VGUtility.toDateStr(new Date(), "yyyyMMddHHmmss");
		if (!mFile.isEmpty()) {
			String newfileName1 = currentDate + "_" + mFile.getOriginalFilename();
			FileCopyUtils.copy(mFile.getBytes(), new File(path + File.separator + newfileName1));
			oldName = mFile.getOriginalFilename();
			newName = newfileName1;
		}
		String saveUrl = "resources/upload";
		result.put("error", 0);
		result.put("url", saveUrl + "/" + newName);

		return result;
	}
}
