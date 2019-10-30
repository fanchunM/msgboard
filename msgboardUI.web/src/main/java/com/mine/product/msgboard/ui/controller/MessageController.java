package com.mine.product.msgboard.ui.controller;

import com.mine.base.sc.user.service.IDictionaryService;
import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.msgboard.ui.util.IPUtils;
import com.mine.product.msgboard.ui.util.SpringSecureUserInfo;
import com.mine.product.szmtr.msgboard.message.dto.ImageDto;
import com.mine.product.szmtr.msgboard.message.dto.MessageDto;
import com.mine.product.szmtr.msgboard.message.dto.MessageLikeDto;
import com.mine.product.szmtr.msgboard.message.dto.SysDictionaryDto;
import com.mine.product.szmtr.msgboard.message.service.IImageService;
import com.mine.product.szmtr.msgboard.message.service.IMessageLikeService;
import com.mine.product.szmtr.msgboard.message.service.IMessageService;
import com.mine.product.szmtr.msgboard.message.service.IsysDictionaryService;
import com.mine.product.szmtr.msgboard.message.util.RandomCode;
import com.mine.product.szmtr.msgboard.person.dto.PersonDto;
import com.vgtech.platform.common.utility.VGUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
@Controller
public class MessageController {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	@Autowired
	private IMessageService messageService;
	@Autowired
	private IImageService imageService;
	@Autowired
	private IMessageLikeService messageLikeService;
	@Autowired
	private IDictionaryService dictService;
	@Autowired
	private IsysDictionaryService sysDictionaryService;
	private static List<Map<String,Object>> mainKeyword = null;//主题分类缓存
	private static long mainKeywordCreateTime = 0;//主题分类缓存时间
	@GetMapping(value = "message/messageList")
    @ResponseBody
    public Map<String, Object> messageList(
    		@RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "20") String rows,
            @RequestParam(required=false) String q,
            @RequestParam(required=false) List<String> list)
    {
		if(!list.get(0).equals("null")) {
			logger.info("Get Message By ThemeId where Size = " + list.size());
		}
        List<MessageDto> messageDtoList = new ArrayList<MessageDto>();

		Map<String, Object> params = new HashMap<String, Object>();
		PageableDto pageable = new PageableDto(Integer.parseInt(page), Integer.parseInt(rows));
		String hql = "from Message m where 1=1 ";
		
		if (!VGUtility.isEmpty(q) && !q.trim().equals("")) {
			hql += " and m.title like :title ";
			params.put("title", "%"+q+"%");
		}else if(!list.get(0).equals("null")) {
			hql += " and m.id in (select t.messageId from Theme t where t.themeId in (:list))";
			 params.put("list", list);
		}
		
		hql += " and m.approvalStatus=2 and m.messageEnable = 0 and m.msgType = 0 order by m.createTimestamp desc";
		
		PageDto<MessageDto> pageDto = messageService.getMessages(hql, params, pageable);
		System.out.println(pageDto.getTotalCount()+"--"+pageDto.getRowData().size());
		//PageableDto pageable = new PageableDto(page, rows);
		List<MessageDto> resultDtoList = pageDto.getRowData();
		for(MessageDto messageDto : resultDtoList) {
			List<ImageDto> messageImages = imageService.getMessageImages(messageDto.getId());
			long count = messageService.getMessageComment(messageDto.getId(), "0");
			messageDto.setCommentNumber(count);
			if(!VGUtility.isEmpty(messageImages) && messageImages.size()>0)
				messageDto.setIfHasAttach(true); else messageDto.setIfHasAttach(false);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", resultDtoList);
		result.put("total", pageDto.getTotalCount());
		result.put("pageNum", page);
        return result;
    }
	
	@GetMapping(value = "message/getMessageById")
    @ResponseBody
    public MessageDto getMessageById(
            @RequestParam(required=true) String id)
    {
		MessageDto dto = messageService.getMessageById(id);
		MessageDto rDto = messageService.getReplyByMessageId(id);
		dto.setReplyDto(rDto);
		return dto;
    }
	
	@RequestMapping(value="message/uploadImage.do", produces = "text/html;charset=UTF-8" ,method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String uploadImage(@RequestParam/*("myfiles")MultipartFile[] fs*/String linshi, @RequestParam String fileName, HttpServletRequest request, Authentication authentication) throws Exception{
		logger.info("fileName = "+fileName);
		if(VGUtility.isEmpty(authentication)) {
			throw new RuntimeException("请先登录用户!");
		}
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
 		logger.info(path);
 		File dirPath = new File(path);
 	      if(!dirPath.exists()) {
 	        dirPath.mkdir();
 	    }
		String resultName = "";
		
//		for(MultipartFile myfile : fs){
//			logger.info("图片大小==============="+myfile.getSize());
//			System.out.println(myfile.isEmpty());
//			if(myfile.isEmpty()){
//				throw new RuntimeException("请选择上传文件!");
//			}else{
//		        if(myfile.getSize() > 10*1024*1024) {
//		        	logger.info("图片大小==============="+myfile.getSize());
//					return "{\"error\":\"请上传不大于5M的图片\"}";
//				}else {
					String currentDate=VGUtility.toDateStr(new Date(), "yyyyMMddHHmmss");
					resultName = currentDate+"_"+fileName;
//					FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(path, fileName));
//				}
//			}
//		}
		String[] data = linshi.split(",");
		this.decodeBase64ToImage(data[1],path,resultName);		
		Object attribute = request.getSession().getAttribute("addPic");
		List<ImageDto> is = new ArrayList<ImageDto>();
		if(attribute != null) is = (List<ImageDto>) attribute;
		
		ImageDto imageDto = new ImageDto();
		imageDto.setImageName(resultName);
		imageDto.setAddress(resultName);
		imageDto.setId(UUID.randomUUID().toString());
		is.add(imageDto);
		request.getSession().setAttribute("addPic",is);
		logger.info("{\"id\":"+imageDto.getId()+",\"address\":"+imageDto.getAddress()+"}");
		return "{\"id\":\""+imageDto.getId()+"\",\"address\":\""+imageDto.getAddress()+"\"}";
	}
	 /**
	   * 将Base64位编码的图片进行解码，并保存到指定目录
	   * 
	   * @param base64
	   *base64编码的图片信息
	   * @return
	 * @throws Exception 
	   */
	  private void decodeBase64ToImage(String base64, String path,String imgName) throws Exception {
	    try {
			byte[] decoderBytes = Base64.getDecoder().decode(base64);  
	    	FileOutputStream write = new FileOutputStream(path+"\\"+imgName);
	      write.write(decoderBytes);
	      write.close();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
	
//	@RequestMapping(value="message/uploadImage.do", produces = "text/html;charset=UTF-8" ,method={RequestMethod.GET, RequestMethod.POST})
//	@ResponseBody
//	public String uploadImage(@RequestParam("myfiles") MultipartFile[] fs, HttpServletRequest request) throws Exception{
//		logger.info("Enter here");
//		Resource res1 = new ClassPathResource("config/file.properties");
//		Properties p = new Properties();
//		try {
//			p.load(res1.getInputStream());
//			res1.getInputStream().close();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
// 	   
//		//获得路径
// 		String path=p.getProperty("root");
// 		logger.info(path);
// 		File dirPath = new File(path);
// 	      if(!dirPath.exists()) {
// 	        dirPath.mkdir();
// 	    }
//		String fileName = "";
//		for(MultipartFile myfile : fs){
//			System.out.println(myfile.isEmpty());
//			if(myfile.isEmpty()){
//				throw new RuntimeException("请选择上传文件!");
//			}else{
//		        if(myfile.getSize() > 10*1024*1024) {
//		        	logger.info("图片大小==============="+myfile.getSize());
//					return "{\"error\":\"请上传不大于5M的图片\"}";
//				}else {
//					String currentDate=VGUtility.toDateStr(new Date(), "yyyyMMddHHmmss");
//					fileName=currentDate+"_"+myfile.getOriginalFilename();
//					FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(path, fileName));
//				}
//			}
//		}
//		Object attribute = request.getSession().getAttribute("addPic");
//		List<ImageDto> is = new ArrayList<ImageDto>();
//		if(attribute != null) is = (List<ImageDto>) attribute;
//		
//		ImageDto imageDto = new ImageDto();
//		imageDto.setImageName(fileName);
//		imageDto.setAddress(fileName);
//		imageDto.setId(UUID.randomUUID().toString());
//		is.add(imageDto);
//		request.getSession().setAttribute("addPic",is);
//		logger.info("{\"id\":"+imageDto.getId()+",\"address\":"+imageDto.getAddress()+"}");
//		return "{\"id\":\""+imageDto.getId()+"\",\"address\":\""+imageDto.getAddress()+"\"}";
//	}
	
	
	/*
	 * 图片质量
	 */
	 private static double getAccuracy(long size) {
	        double accuracy;
	        if (size < 5*1024) {
	            accuracy = 1;
	        }else {
	            accuracy = 0.95;
	        }
	        return accuracy;
	 }
	@GetMapping(value = "message/lookPic.do")
	@ResponseBody
	public ResponseEntity<String> lookPic(HttpServletRequest request,String id) throws Exception {
		List<ImageDto> is = (List<ImageDto>) request.getSession().getAttribute("addPic");
		for(ImageDto p : is){
			if(p.getId().equals(id)){
				request.setAttribute("address",p.getAddress());
				
			}
		}
		//UploadPhoto photo = uploadPhotoService.selectByPrimaryKey(id);
		
		return new ResponseEntity<String>("{\"success\":true}", HttpStatus.OK); 
	}
	
	@RequestMapping(value = "message/getPic.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> getPic(HttpServletRequest request)throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		List<ImageDto> is = (List<ImageDto>) request.getSession().getAttribute("addPic");
		result.put("rows", is);
		result.put("total", 0);
		return result;
	}
	@RequestMapping(value = "message/delPic.do", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<ImageDto> delPic(HttpServletRequest request,String id) throws Exception {
		List<ImageDto> is = (List<ImageDto>) request.getSession().getAttribute("addPic");
		Iterator<ImageDto> iterator = is.iterator();
        while(iterator.hasNext()){
        	ImageDto temp = iterator.next();
            if(temp.getId().equals(id)){
            	iterator.remove();
			}
        }
        request.getSession().setAttribute("addPic",is);
		return is;
	}
	@PostMapping(value="message/createMessage")
	@ResponseBody
	public MessageDto createMessage(@RequestBody MessageDto  dto, HttpServletRequest  request, Authentication authentication){
		logger.info("Create Meaager Where Title ="+dto.getTitle());
		if(VGUtility.isEmpty(authentication)) throw new RuntimeException("请先登陆用户!");
		PersonDto person = ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo();
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH), 0, 0, 0);
		Date beginTime = calendar.getTime();
		calendar.set(calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH)+1, 0, 0, 0);
		Date endTime = calendar.getTime();
		logger.info("BeginTime ="+VGUtility.toDateStr(beginTime, "yyyy-MM-dd HH:mm:ss")+" And EndTime ="+VGUtility.toDateStr(endTime, "yyyy-MM-dd HH:mm:ss"));
		String hql = "from Message m where m.createTimestamp >= :beginTime and m.createTimestamp <= :endTime and m.personId =:personId and m.msgType = 0";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		params.put("personId", person.getId());
		PageDto<MessageDto> messages = messageService.getMessages(hql, params, null);
		if(messages.getTotalCount()>10) throw new RuntimeException("今日留言数量已达上限!");
		String validateCode = (String)request.getSession().getAttribute("randomCode");
//		if(!validateCode.toUpperCase().equals(dto.getValidateCode().toUpperCase()))
//			throw new RuntimeException("验证码错误!请重新输入!");
		List<ImageDto> is = (List<ImageDto>) request.getSession().getAttribute("addPic");
		dto.setMessageEnable(IMessageService.ENABLE_STAUS.使用中);
		dto.setDisplayStatus(IMessageService.DISPLAY_STATUS.隐藏);
		dto.setMsgType(IMessageService.MESSAGE_TYPE.留言);
		dto.setApprovalStatus(IMessageService.APPROVAL_STATUS.未处理);
		dto.setReplyStatus(IMessageService.REPLY_STATUS.未答复);
		dto.setViewStatus(IMessageService.VIEW_STAUS.未读取);
		dto.setStatus("0");
		//获取用户IP地址
		dto.setIpAddress(IPUtils.getClientIp(request));
		//留言编号（L+时间+流水号）
		dto.setSerialNumber(messageService.CreateMessagePipelineNumber());
		MessageDto messageDto = messageService.createMessage(dto);
		if(!VGUtility.isEmpty(is)) {
			Iterator<ImageDto> iterator = is.iterator();
			 while(iterator.hasNext()){
		        	ImageDto temp = iterator.next();
		        	temp.setId(null);
		        	temp.setParentId(messageDto.getId());
		        	imageService.createImageModel(temp);
		            iterator.remove();
		        }
		}
		return messageDto; 
	}
	@PostMapping(value = "message/get_Like")
    @ResponseBody
	public ResponseEntity<String> getLikeClick(@RequestParam String messageId,Authentication authentication) {
		logger.info("Click Like Where userId = "+((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId()+"And MessageId ="+messageId);
		messageLikeService.clickLike(((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId(), messageId);
		return new ResponseEntity<String>("{\"success\":true}", HttpStatus.OK);
	}
	
	@GetMapping(value="message/if_click_like")
	@ResponseBody
	public MessageLikeDto getIfHasClick(@RequestParam String messageId,Authentication authentication) {
		logger.info("Get Result If Click Like Where userId = "+((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId() +"And MessageId ="+messageId);
		MessageLikeDto dto = messageLikeService.getOneMessageLike(((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId(), messageId);
//		if(VGUtility.isEmpty(dto))
//			throw new RuntimeException("未点赞!");
		return dto;
	}
	
	@PostMapping(value="message/submitComments")
	@ResponseBody
	public ResponseEntity<String> submitComments(Authentication authentication,@RequestParam String messageId, @RequestParam String comments){
		if(VGUtility.isEmpty(((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId()))
			throw new RuntimeException("请先登录!");
		/*if(!VGUtility.isEmpty(messageService.judgeIfComments(userId, messageId)))
			throw new RuntimeException("您已经留过言,请不要重复留言");*/
		messageService.submitComments(((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId(), messageId, comments);
		return new ResponseEntity<String>("{\"success\":true}", HttpStatus.OK);
	}
	/*
	 * 获取验证码
	 */
	@RequestMapping({ "message/validateCode.do" })
	public void validateCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		RandomCode.drawImage(req, resp);
	}
	
	/**
	 * 
	 * @author 何森
	 *@date 2018年11月15日上午9:45:04
	 *@param messageId
	 *@param page
	 *@param rows
	 *@return Map<String, Object>
	 *@Description: 客户端通过留言ID获取评论
	 */
	@PostMapping(value = "message/commentList")
    @ResponseBody
    public Map<String, Object> commentList(
    		@RequestParam(required=true) String messageId,
    		@RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "20") String rows) {
		Map<String, Object> params = new HashMap<String, Object>();
		PageableDto pageable = new PageableDto(Integer.parseInt(page), Integer.parseInt(rows));
		StringBuffer sb = new StringBuffer();
		sb.append("from Message m where 1=1");
		sb.append(" and m.msgType = :msgType");
		params.put("msgType", IMessageService.MESSAGE_TYPE.评论);
		sb.append(" and m.parent.id = :parent");
		params.put("parent", messageId);
		sb.append(" and m.approvalStatus = :approvalStatus");
		params.put("approvalStatus", IMessageService.APPROVAL_STATUS.审核通过);
		sb.append(" and m.messageEnable = :messageEnable");
	    params.put("messageEnable", IMessageService.ENABLE_STAUS.使用中);
	    sb.append(" order by m.createTimestamp");
		PageDto<MessageDto> pageDto = messageService.getMessages(sb.toString(), params, pageable);
		//查询所有留言数量
		//long allMessage = messageService.getMessages(sb.toString(), params, null).getTotalCount();
		List<MessageDto> resultDtoList = pageDto.getRowData();
		int i =1;
		for(MessageDto resultDto : resultDtoList) {
			resultDto.setFloorNum((VGUtility.toInteger(page)-1)*VGUtility.toInteger(rows)+i);
			i++;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", resultDtoList);
		result.put("total", pageDto.getTotalCount());
		result.put("pageNum", page);
        return result;
    }
	/*
	 * 留言详情页获得图片
	 */
	@GetMapping(value="message/get_message_images")
	@ResponseBody
	public List<ImageDto> getMessageImages(@RequestParam String messageId){
		logger.info("Get Message Images Where messageId="+messageId);
		return imageService.getMessageImages(messageId);
	}
	
	/*
	 * 获取用户的留言
	 */
	@GetMapping(value="message/getMessageByUserId")
	@ResponseBody
	public ModelMap getMessagesByUserId(
//			@RequestParam(required = true) String userId,
			Authentication authentication,
			@RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "20") String rows){
		logger.info("Get Messages Where UserId = " + ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId());
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Message m where 1=1 and m.msgType = 0";
		if(!VGUtility.isEmpty(((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId())) {
			hql += " and m.personId =: userId order by m.createTimestamp desc";
			params.put("userId", ((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId());
		}
		PageableDto pageable = new PageableDto(Integer.parseInt(page), Integer.parseInt(rows));
		PageDto<MessageDto> pageDto = messageService.getMessageByUserId(hql, params, pageable);
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("rows", pageDto.getRowData());
		modelMap.addAttribute("total", pageDto.getTotalCount());
		modelMap.addAttribute("pageNum", page);
		return modelMap;
	}
	
	/*
	 *主题分类 
	 */
	  @GetMapping(value = "message/get_main_keyword_forcombo")
	  @ResponseBody
	  public List<Map<String,Object>> commonCodeForCombo() {
		  //缓存主题分类
		  if(!VGUtility.isEmpty(mainKeyword)) {
			  if(((System.currentTimeMillis()-mainKeywordCreateTime)/1000) < 3600*24) {
				  return mainKeyword;
			  }else {
				  List<SysDictionaryDto> resultList = new ArrayList<>();
			      //List<SysDictionaryDto> dictDtoList = sysDictionaryService.findAllDictionaryCommonCode();
				  String sql = "select * from DictionaryCommonCode order by serialNumber";
				  List<SysDictionaryDto> dictDtoList = sysDictionaryService.findAllDictionaryCommonCodeSql(sql);
			       for(SysDictionaryDto d : dictDtoList) {
			    	   if(VGUtility.isEmpty(d.getParentThemeId()) && sysDictionaryService.getThemeByParentThemeId(d.getId()).size()>0) {
			    		   resultList.add(d);
			    	   }
			       }
			       List<Map<String, Object>> comboDtoList = convertDict(resultList);
			       for (SysDictionaryDto dictDto : resultList) {
			    	   Map<String, Object> params = new HashMap<String, Object>();
			    	   String hql = "from DictionaryCommonCode  where parentThemeId= :parentThemeId order by serialNumber";
				       params.put("parentThemeId",dictDto.getId());
			    	   PageDto<SysDictionaryDto> dictPageDto = sysDictionaryService.getDictionaryCommonCode(hql, params, null);
			           for (Map<String, Object> mapOne : comboDtoList) {
			               if (mapOne.get("id").equals(dictDto.getId())) {
			            	   mapOne.put("children", convertDict(dictPageDto.getRowData()));
			            	   List<String> idList = new ArrayList<>();
			            	   //统计子项目中所有的相关留言数量
			            	   for(SysDictionaryDto d : dictPageDto.getRowData()) {
			            		   List<MessageDto> dtoList = messageService.getMessageByThemeId(d.getId());
			            		   if(idList.size()<1) {
			            			   for(MessageDto msgDto : dtoList) {
			            				   idList.add(msgDto.getId());
			            			   }
			            		   }else {
			            			   for(MessageDto msgDto : dtoList) {
			            				   if(!idList.contains(msgDto.getId())) {
			            					   idList.add(msgDto.getId());
			            				   }
			            			   }
			            		   }
			            	   }
			            	   //重新赋值
			            	   
			            	   mapOne.put("name", mapOne.get("name").toString().split("\\(")[0]+(idList.size()==0?"":"("+idList.size()+")"));
			               }
			           }
			       }
			       mainKeyword = comboDtoList;
			       mainKeywordCreateTime = System.currentTimeMillis();
			       return mainKeyword;
			  }
		  }else {
			  List<SysDictionaryDto> resultList = new ArrayList<>();
		      //List<SysDictionaryDto> dictDtoList = sysDictionaryService.findAllDictionaryCommonCode();
			  String sql = "select * from DictionaryCommonCode order by serialNumber";
			  List<SysDictionaryDto> dictDtoList = sysDictionaryService.findAllDictionaryCommonCodeSql(sql);
		       for(SysDictionaryDto d : dictDtoList) {
		    	   if(VGUtility.isEmpty(d.getParentThemeId()) && sysDictionaryService.getThemeByParentThemeId(d.getId()).size()>0) {
		    		   resultList.add(d);
		    	   }
		       }
		       List<Map<String, Object>> comboDtoList = convertDict(resultList);
		       for (SysDictionaryDto dictDto : resultList) {
		    	   Map<String, Object> params = new HashMap<String, Object>();
		    	   String hql = "from DictionaryCommonCode  where parentThemeId= :parentThemeId order by serialNumber";
			       params.put("parentThemeId",dictDto.getId());
		    	   PageDto<SysDictionaryDto> dictPageDto = sysDictionaryService.getDictionaryCommonCode(hql, params, null);
		           for (Map<String, Object> mapOne : comboDtoList) {
		               if (mapOne.get("id").equals(dictDto.getId())) {
		            	   mapOne.put("children", convertDict(dictPageDto.getRowData()));
		            	   List<String> idList = new ArrayList<>();
		            	   //统计子项目中所有的相关留言数量
		            	   for(SysDictionaryDto d : dictPageDto.getRowData()) {
		            		   List<MessageDto> dtoList = messageService.getMessageByThemeId(d.getId());
		            		   if(idList.size()<1) {
		            			   for(MessageDto msgDto : dtoList) {
		            				   idList.add(msgDto.getId());
		            			   }
		            		   }else {
		            			   for(MessageDto msgDto : dtoList) {
		            				   if(!idList.contains(msgDto.getId())) {
		            					   idList.add(msgDto.getId());
		            				   }
		            			   }
		            		   }
		            	   }
		            	   //重新赋值
		            	   
		            	   mapOne.put("name", mapOne.get("name").toString().split("\\(")[0]+(idList.size()==0?"":"("+idList.size()+")"));
		               }
		           }
		       }
		       mainKeyword = comboDtoList;
		       mainKeywordCreateTime = System.currentTimeMillis();
		       return mainKeyword;
		  }
	  }
	    
	 private List<Map<String, Object>> convertDict(List<SysDictionaryDto> dictDtoList) {
		 List<Map<String, Object>> mapList = new ArrayList<>();
	     for (SysDictionaryDto tempDto : dictDtoList) {
	    	 Map<String, Object> map = new HashMap<>();
	    	 map.put("id", tempDto.getId());
	    	 if(messageService.getMessageCountByThemeId(tempDto.getId())<1) {
	    		 map.put("name", tempDto.getChsName());
	    	 }else {
		    	 map.put("name", tempDto.getChsName()+"("+messageService.getMessageCountByThemeId(tempDto.getId())+")");
	    	 }
	    	 map.put("spread", true);
	    	 mapList.add(map);
	     }
	     return mapList;
	 }
	 
	 /**
	  * 
	 * @author 何森
	 * @date 2018年12月4日上午11:19:34
	 * @Description:根据留言id查询相关留言        
	 * @return List<MessageDto>    
	 *
	  */
	 @PostMapping(value = "message/getRelatedMessage")
	 @ResponseBody
	 public List<MessageDto> relatedMessageByMessageId(String messageId){
		 Map<String, Object> params = new HashMap<String, Object>();
		 StringBuffer sql = new StringBuffer();
		 sql.append("select m.* from Message m,(select messageId,count(t.themeId) themeCont from Theme t  where t.themeId in" + 
					" (select themeId from Theme where messageId= :messageId)" +" and t.messageId <>:messageId"
					+" group by messageId ) n  where  m.id=n.messageId");
		 params.put("messageId",messageId);
		 sql.append(" and m.approvalStatus = :approvalStatus");
		 //2的状态为审核通过
		 params.put("approvalStatus","2");
		 sql.append(" and m.messageEnable = :messageEnable order by themeCont desc");
		 //0的状态为使用中
		 params.put("messageEnable","0");
		 List<MessageDto> list = messageService.getRelativeMessages(sql.toString(),params,"5");
		 return list;
	 }
	 /*
	  * 主题分类
	  */
//	 @GetMapping(value="message/get_Message_theme")
//	 @ResponseBody
//	 public List<MessageDto> getMessageByTheme(@RequestParam("list") List<String> list,
//			 	@RequestParam(defaultValue = "1") String page,
//	            @RequestParam(defaultValue = "20") String rows){
//		 logger.info("Get Message By ThemeId where Size = " + list.size());
//		 String hql = "from Message m where m.id in (select t.messageId from Theme t where t.themeId in (:list)) and m.approvalStatus=2 and m.messageEnable = 0 and m.msgType = 0 order by m.createTimestamp desc";
//		 Map<String, Object> params = new HashMap<String, Object>();
//		 params.put("list", list);
//		 return messageService.getMessageByThemeId(hql, params, new PageableDto(VGUtility.toInteger(page), VGUtility.toInteger(rows))).getRowData();
//	 }
	/*
	 * 获取留言类型的combobox
	 */
//	 @GetMapping(value="message/get_message_type")
//	 @ResponseBody
//	 public List<CommonComboDto> getMessageType(){
//		 List<DictionaryCommonCodeDto> dictDtoList =  dictService.getCommonCodeByType("PSP-ORDER-TYPE");
//		 List<CommonComboDto> comboDtoList = new ArrayList<CommonComboDto>();
//		 for(DictionaryCommonCodeDto dto : dictDtoList) {
//			 CommonComboDto commonComboDto = new CommonComboDto();
//			 commonComboDto.setValue(dto.getId());
//			 commonComboDto.setText(dto.getChsName());
//			 comboDtoList.add(commonComboDto);
//		 }
//		return comboDtoList;
//	 }
	 
	@PostMapping("message/add_pwd_strong")
	@ResponseBody
	public void addSeesionPwdStrong(HttpServletRequest request) {
		request.getSession().setAttribute("pwdStrong", false);
	}

	@PostMapping("message/remove_pwd_strong")
	@ResponseBody
	public void removeSeesionPwdStrong(HttpServletRequest request) {
		request.getSession().removeAttribute("pwdStrong");
	}
}
