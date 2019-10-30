package com.mine.product.msgboard.ui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.faqs.dto.FaqsDto;
import com.mine.product.szmtr.msgboard.faqs.dto.FaqsThemeDto;
import com.mine.product.szmtr.msgboard.faqs.service.IFaqsService;
import com.mine.product.szmtr.msgboard.message.dto.MessageDto;
import com.mine.product.szmtr.msgboard.message.dto.SysDictionaryDto;
import com.mine.product.szmtr.msgboard.message.model.Theme;
import com.mine.product.szmtr.msgboard.message.service.IsysDictionaryService;
import com.vgtech.platform.common.utility.VGUtility;


@Controller
public class FaqsController {
	private static final Logger logger = LoggerFactory.getLogger(FaqsController.class);
	
	@Autowired
	private IFaqsService faqsService;
	@Autowired
	private IsysDictionaryService syService;

	@GetMapping(value = "faqs/faqsList")
    @ResponseBody
    public Map<String, Object> faqsList(
    		@RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "20") String rows,
            @RequestParam(required=false) String q)
    {
        List<FaqsDto> faqsDtoList = new ArrayList<FaqsDto>();

		Map<String, Object> params = new HashMap<String, Object>();
		PageableDto pageable = new PageableDto(Integer.parseInt(page), Integer.parseInt(rows));
		
		String hql = "from FaqsModel f where 1=1 ";
		
		if (!VGUtility.isEmpty(q)) {
			hql += " and (f.question like :question or f.answers like :answers)";
			params.put("question", "%"+q+"%");
			params.put("answers", "%"+q+"%");
		}
		hql += " and faqsStatus=2 order by f.createTimestamp desc";
		PageDto<FaqsDto> pageDto = faqsService.getFaqs(hql, params, pageable);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", pageDto.getRowData());
		result.put("total", pageDto.getTotalCount());
		result.put("pageNum", page);
        return result;
    }
	
	@GetMapping(value = "faqs/getFaqsById")
    @ResponseBody
    public FaqsDto getFaqsById(
            @RequestParam(required=true) String id)
    {
		FaqsDto dto = faqsService.getFaqsById(id);
		return dto;
    }
	
	
	/**
	 * 
	* @author 何森
	* @date 2018年12月5日下午5:03:14
	* @Description:查询相关问答        
	* @return List<FaqsDto>    
	*
	 */
	@PostMapping(value = "faqs/getRelatedFaqs")
	 @ResponseBody
	 public List<FaqsDto> relatedMessageByMessageId(String messageId){
		 Map<String, Object> params = new HashMap<String, Object>();
		 StringBuffer sql = new StringBuffer();
		 sql.append("select m.* from FaqsModel m,(select faqsId,count(t.themeId) themeCont from FaqsTheme t  where t.themeId in" + 
					" (select themeId from Theme where messageId= :messageId)" + 
					" group by faqsId) n  where  m.id=n.faqsId");
		 params.put("messageId",messageId);
		 sql.append(" and m.faqsStatus = :faqsStatus order by themeCont desc");
		 //2的状态为审核通过的
		 params.put("faqsStatus","2");
		 List<FaqsDto> list = faqsService.getRelativeFaqs(sql.toString(),params,"5");
		 return list;
	 }
}
