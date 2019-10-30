package com.mine.product.msgboard.main.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mine.base.sc.user.dto.DictionaryCommonCodeDto;
import com.mine.base.sc.user.service.IDictionaryService;
import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.message.dto.SysDictionaryDto;
import com.mine.product.szmtr.msgboard.message.service.IsysDictionaryService;
import com.vgtech.platform.common.utility.VGUtility;


@Controller
public class SynchronizationThemeController {

	private static final Logger logger = LoggerFactory.getLogger(SynchronizationThemeController.class);
	
	@Autowired
	private IDictionaryService dictService;//base主题关联的接口
	@Autowired
	private IsysDictionaryService sysDictionaryService;//本地主题关联接口
	/**
	 * 
	* @author 何森
	* @date 2018年12月22日上午11:14:28
	* @Description:将base中的数据同步保存到本地数据库
	* @return List<CommonComboDto>    
	*
	 */
	@PostMapping(value = "saveSynchronizationTheme")
	@ResponseBody
	public String synchronizationTheme(){
//		//对base进行查询所有的数据
//		List<DictionaryCommonCodeDto> dictDtoList = dictService.getCommonCodeByType("PSP-MAIN-KEYWORD");
//		  for(DictionaryCommonCodeDto dto:dictDtoList) {
//			  //过滤三级菜单
//			  if(!VGUtility.isEmpty(dto.getParentDictCommonCodeId())) {
//				  DictionaryCommonCodeDto twoLevelDto = dictService.getCommonCodeById(dto.getParentDictCommonCodeId());
//				  if(!VGUtility.isEmpty(twoLevelDto.getParentDictCommonCodeId())) {
//					  DictionaryCommonCodeDto threeLevelModle = dictService.getCommonCodeById(twoLevelDto.getParentDictCommonCodeId());
//					  	if(!VGUtility.isEmpty(threeLevelModle)) {
//					  		continue;
//					  	}
//				  }
//			  }
//			  SysDictionaryDto modelDto = new SysDictionaryDto();
//			  modelDto.setBaseId(dto.getId());
//			  modelDto.setCode(dto.getCode());
//			  modelDto.setParentDictCommonCodeId(dto.getParentDictCommonCodeId());
//			  modelDto.setChsName(dto.getChsName());
//			  //通过baseId查询本地主题内容表中是否存在当前数据
//			  SysDictionaryDto modle = sysDictionaryService.findSysDictionaryDtoByBaseId(dto.getId());
//			  if(modle!=null) {
//				  //不为空修改
//				  sysDictionaryService.updateDictionaryCommonCode(modelDto);
//			  }else {
//				  //为空就添加
//				  sysDictionaryService.createDictionaryCommonCode(modelDto);
//			  }
//		  }
	     return "ok";
    }
	
	/**
	 * 
	* @author 何森
	* @date 2018年12月22日下午2:18:42
	* @Description:从本地数据库查询主题信息        
	* @return List<SysDictionaryDto>    
	*
	 */
	@PostMapping(value = "findAllSynchronizationTheme")
	@ResponseBody
	public Map<String, Object> findAllSynchronizationTheme(
			@RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "20") String rows,
            @RequestParam(required = false) String q
			) {
		String hql ="from DictionaryCommonCode d where 1=1"+(VGUtility.isEmpty(q)?"":" and d.chsName like:q")+" order by d.createTimestamp";
		PageableDto pageable = new PageableDto(Integer.parseInt(page), Integer.parseInt(rows));
		Map<String,Object> param = new HashMap<String,Object>();
		if(!VGUtility.isEmpty(q)) {
			param.put("q", "%"+q+"%");
		}
		PageDto<SysDictionaryDto> sdd = sysDictionaryService.getDictionaryCommonCode(hql,param,pageable);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", sdd.getTotalCount());
		result.put("rows", sdd.getRowData());
		return result;
	}
	@PostMapping(value = "findFatherSynchronizationTheme")
	@ResponseBody
	public Map<String, Object> findFatherSynchronizationTheme(
			@RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "20") String rows,
            @RequestParam(required = false) String q
			) {
		String hql ="from DictionaryCommonCode d where 1=1 and d.parentThemeId is null"+(VGUtility.isEmpty(q)?"":" and d.chsName like:q")+" order by d.serialNumber";
		PageableDto pageable = new PageableDto(Integer.parseInt(page), Integer.parseInt(rows));
		Map<String,Object> param = new HashMap<String,Object>();
		if(!VGUtility.isEmpty(q)) {
			param.put("q", "%"+q+"%");
		}
		PageDto<SysDictionaryDto> sdd = sysDictionaryService.getDictionaryCommonCode(hql,param,pageable);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", sdd.getTotalCount());
		result.put("rows", sdd.getRowData());
		return result;
	}
}
