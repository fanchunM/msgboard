package com.mine.product.szmtr.msgboard.faqs.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mine.base.sc.user.dto.UserDto;
import com.mine.base.sc.user.service.IDictionaryService;
import com.mine.base.sc.user.service.IUserService;
import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.faqs.dto.FaqsDto;
import com.mine.product.szmtr.msgboard.faqs.dto.FaqsThemeDto;
import com.mine.product.szmtr.msgboard.faqs.service.IFaqsService;
import com.mine.product.szmtr.msgboard.message.dto.SysDictionaryDto;
import com.mine.product.szmtr.msgboard.message.service.IsysDictionaryService;
import com.vgtech.platform.common.utility.VGUtility;
import com.vgtech.platform.common.web.dto.CommonComboDto;
import com.vgtech.platform.common.web.dto.LoginUserDto;

@Controller
@RequestMapping(value = "/admin/")
@SessionAttributes(value = {"loginUserDto"})
public class FaqsAdminController {
	private static final Logger logger = LoggerFactory.getLogger(FaqsAdminController.class);
	
	@Autowired
	private IFaqsService faqsService;
	@Autowired
	private IDictionaryService dictService;
	@Autowired
    private IUserService userService;
	@Autowired
    private IsysDictionaryService sysDictionaryService;
	@PostMapping(value = "faqs/faqsList")
    @ResponseBody
    public Map<String, Object> faqsList(
    		@RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "20") String rows,
            Integer faqsStatus,String keyword)
    {
		logger.info("Get FaqsModel By Query For DataGrid");
		Map<String, Object> params = new HashMap<String, Object>();
		PageableDto pageable = new PageableDto(Integer.parseInt(page), Integer.parseInt(rows));
		
		String hql = "from FaqsModel f where 1=1 ";
		
		if (!VGUtility.isEmpty(keyword)) {
			hql += " and (f.question like :question or f.answers like :answers)";
			params.put("question", "%"+keyword+"%");
			params.put("answers", "%"+keyword+"%");
		}
		if (!VGUtility.isEmpty(faqsStatus)) {
			hql += " and f.faqsStatus = :faqsStatus ";
			if(faqsStatus==0) {
				params.put("faqsStatus", IFaqsService.FAQSSTATUS.未处理);
			}
			if(faqsStatus==1) {
				params.put("faqsStatus", IFaqsService.FAQSSTATUS.审核不通过);
			}
			if(faqsStatus==2) {
				params.put("faqsStatus", IFaqsService.FAQSSTATUS.审核通过);
			}
		}
		
		hql += " order by f.createTimestamp desc";
		PageDto<FaqsDto> pageDto = faqsService.getFaqs(hql, params, pageable);
		Map<String, Object> result = new HashMap<String, Object>();
		
		
		List<FaqsDto> faqsDtoList = pageDto.getRowData();
		for(FaqsDto faqs:faqsDtoList) {
			//获取主题关联
			List<FaqsThemeDto> themeDtoList =faqsService.findFaqsThemeByFaqsId(faqs.getId());
			if(themeDtoList!=null) {
				StringBuffer buffer= null; 
				for(FaqsThemeDto themeDto:themeDtoList) {
					if(!VGUtility.isEmpty(themeDto.getId())) {
						SysDictionaryDto sd = sysDictionaryService.getThemeById(themeDto.getThemeId());
					//	DictionaryCommonCodeDto	dccd = dictService.getCommonCodeById(themeDto.getThemeId());
						if(buffer == null) buffer = new StringBuffer(sd.getChsName());
						else buffer.append(";").append(sd.getChsName());
					}
				}
				faqs.setMainText(buffer == null ? "" : buffer.toString());
			}
			//通过personId获取创建人名字
			if(!VGUtility.isEmpty(faqs.getPersonId())) {
				UserDto userDto = userService.getUserById(faqs.getPersonId());
				faqs.setPersonName(userDto.getChsName());
			}
			//通过ApprivalPersonId获取审核人名字
			if(!VGUtility.isEmpty(faqs.getApprivalPersonId())) {
				UserDto userDto = userService.getUserById(faqs.getApprivalPersonId());
				faqs.setApprivalPersonName(userDto.getChsName());
			}
		}
		
		result.put("rows", pageDto.getRowData());
		result.put("total", pageDto.getTotalCount());
		
        return result;
    }
	
	@PostMapping(value = "faqs/deleteFaqs")
    @ResponseBody
    public String deleteFaqs(@RequestParam(required=true) String id){
		logger.info("delete Faqs By Id id="+id);
		faqsService.deleteFaqs(id);
		return "ok";
	}
	
	@PostMapping(value = "faqs/checkFaqs")
    @ResponseBody
    public String checkFaqs(@ModelAttribute("loginUserDto") LoginUserDto loginUserDto,
    		                @RequestParam(required=true) String id,
    						@RequestParam(required=true) Integer type){
		logger.info("Check Faqs By Id id="+id);
		String loginUserId =loginUserDto.getLoginedUserId();
		FaqsDto dto = faqsService.getFaqsById(id);
		dto.setApprivalPersonId(loginUserId);
		if(type == 2) {
			dto.setFaqsStatus(IFaqsService.FAQSSTATUS.审核通过);
		}
		if(type == 1) {
			dto.setFaqsStatus(IFaqsService.FAQSSTATUS.审核不通过);
		}
		faqsService.updateFaqs(dto);
		return "ok";
	}
	@PostMapping(value = "faqs/saveFaqs")
    @ResponseBody
    public String saveFaqs(@RequestParam(required=false) String id,
    		              @RequestParam(required=false) String question,
    		              @RequestParam(required=false) String answers,
    		              @ModelAttribute("loginUserDto") LoginUserDto loginUserDto){
		logger.info("Save or Update Faqs");
		FaqsDto dto = new FaqsDto();
		dto.setId(id);
		dto.setAnswers(answers);
		dto.setQuestion(question);
		String loginUserId =loginUserDto.getLoginedUserId();
		if(!VGUtility.isEmpty(dto.getId())) {
			logger.info("Update Faqs");
			FaqsDto faqsDto = faqsService.getFaqsById(dto.getId());
			faqsDto.setQuestion(dto.getQuestion());
			faqsDto.setAnswers(dto.getAnswers());
			faqsDto.setPersonId(loginUserId);
			faqsDto.setFaqsStatus(IFaqsService.FAQSSTATUS.未处理);
			faqsDto.setApprivalPersonId("");
			FaqsDto updateDto = faqsService.updateFaqs(faqsDto);
		}else {
			logger.info("Save Faqs");
			dto.setFaqsStatus(IFaqsService.FAQSSTATUS.未处理);
			dto.setHits(0);
			if(VGUtility.isEmpty(dto.getOrderIndex())) {
				dto.setOrderIndex(0);
			}
			dto.setPersonId(loginUserId);
			FaqsDto faqsDto = faqsService.createFaqs(dto);
		}
		
		return "ok";
	}
	
	
	
	/**
	 * 
	 * @author 何森
	 *@date 2018年11月13日上午9:54:25
	 *@return List<CommonComboDto>
	 *@Description:相关主题关联
	 */
	  @GetMapping(value = "faqs/get_main_keyword_forcombo")
	  @ResponseBody
	  public List<CommonComboDto> commonCodeForCombo() {
		  List<SysDictionaryDto> resultList = new ArrayList<SysDictionaryDto>();
		  String sql = "select * from DictionaryCommonCode order by serialNumber";
		  List<SysDictionaryDto> dictDtoList = sysDictionaryService.findAllDictionaryCommonCodeSql(sql);
		  for(SysDictionaryDto dto:dictDtoList) {
			  if(VGUtility.isEmpty(dto.getParentThemeId())){
				  if(sysDictionaryService.getThemeByParentThemeId(dto.getId()).size()>0) {
					  resultList.add(dto);
				  }
			  }
		  }
	      List<CommonComboDto> comboDtoList = convertDict(resultList);
	       for (SysDictionaryDto dictDto : resultList) {
	    	   //List<SysDictionaryDto> sysDto = sysDictionaryService.getThemeByParentThemeId(dictDto.getId());
	    	   Map<String, Object> params = new HashMap<String, Object>();
	    	   String hql = "from DictionaryCommonCode  where parentThemeId= :parentThemeId order by serialNumber";
		       params.put("parentThemeId",dictDto.getId());
	    	   PageDto<SysDictionaryDto> dictPageDto = sysDictionaryService.getDictionaryCommonCode(hql, params, null);
	    	   List<SysDictionaryDto> sysDto = dictPageDto.getRowData();
	           for (CommonComboDto comboDto : comboDtoList) {
	               if (comboDto.getId().equals(dictDto.getId()))
	                   comboDto.setChildren(convertDict(sysDto));
	           }
	       }
	       return comboDtoList;
	  }
	    
	 private List<CommonComboDto> convertDict(List<SysDictionaryDto> dictDtoList) {
	     List<CommonComboDto> resultList = new ArrayList<CommonComboDto>();
	     for (SysDictionaryDto tempDto : dictDtoList) {
	         CommonComboDto dto = new CommonComboDto();
	         dto.setId(tempDto.getId());
	         dto.setText(tempDto.getChsName());
	         dto.setValue(tempDto.getId());
	         resultList.add(dto);
	     }
	     	return resultList;
	 }
	
	/**
	 * 
	* @author 何森
	* @date 2018年12月3日下午2:36:35
	* @Description:添加主题关联        
	* @return ResponseEntity<String>    
	*
	 */
	@GetMapping(value = "faqs/saveTheme")
	@ResponseBody
	public ResponseEntity<String> saveTheme(@RequestParam String faqsId, 
											@RequestParam  String themeId) {
		logger.info("Link Faqs With Theme Where FaqsId = "+faqsId+" And ThemeId = "+themeId);
		faqsService.saveThemeByFaqsIdAndThemeId(faqsId, themeId);
		return new ResponseEntity<String>("{\"success\":true}", HttpStatus.OK);
	}
	
	
	/**
	 * 
	* @author 何森
	* @date 2018年12月3日下午3:12:05
	* @Description:根据faqsId查询主题关联内容          
	* @return List<FaqsThemeDto>    
	*
	 */
	@PostMapping(value = "faqs/findFaqsThemeByFaqsId")
	@ResponseBody
	public List<FaqsThemeDto> findFaqsThemeByMessageId(String faqsId){
		List<FaqsThemeDto> listDto = faqsService.findFaqsThemeByFaqsId(faqsId);
		for(FaqsThemeDto themeDto:listDto) {
			SysDictionaryDto sd = sysDictionaryService.getThemeById(themeDto.getThemeId());
			themeDto.setThemeName(sd.getChsName());
		}
		return listDto;
	}
	
	
	/**
	 * 
	* @author 何森
	* @date 2018年12月3日下午3:21:25
	* @Description: 根据id删除主题内容       
	* @return String    
	*
	 */
	@PostMapping(value = "faqs/deleteMainText")
	@ResponseBody
	public String deleteThemeById(String id){
		faqsService.deleteFaqsThemeById(id);
		return "ok";
	}
}
