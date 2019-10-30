package com.mine.product.szmtr.msgboard.faqs.service;

import java.util.List;
import java.util.Map;
import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.faqs.dto.FaqsDto;
import com.mine.product.szmtr.msgboard.faqs.dto.FaqsThemeDto;

public interface IFaqsService {
	/**
	 * 便民问答:未激活,已激活
	 */
	public static enum FAQSSTATUS{
		未处理, 审核不通过, 审核通过
	}
	
	FaqsDto createFaqs(FaqsDto dto);
	void deleteFaqs(String id);
	FaqsDto updateFaqs(FaqsDto dto);
	FaqsDto getFaqsById(String id);
	/**
	 * 分页查询
	 * @param hql 查询实体  类似: from Person where id = :persons
	 * @param params 过滤参数{persons:''}
	 * @param pageable 分页（可以为空）
	 * @return
	 */
	PageDto<FaqsDto> getFaqs(String hql, Map<String, Object> params, PageableDto pageable);
	/**
	 * 分页查询
	 * @param hql 查询实体 类似：from Person where id = :persons
	 * @param countHql 查询数量 类似：select count(id) from Person where id = :persons
	 * @param params 过滤参数 {persons:''}
	 * @param pageable 分页（可以为空）
	 * @return
	 */
	PageDto<FaqsDto> getFaqs(String hql, String countHql, Map<String, Object> params, PageableDto pageable);
	//添加相关主题
	void saveThemeByFaqsIdAndThemeId(String faqsId,String themeId);
	//根据问答id查询问答主题
	List<FaqsThemeDto> findFaqsThemeByFaqsId(String faqsId);
	//根据主题id删除问答主题
	void deleteFaqsThemeById(String id);
	//获取相关问答
	List<FaqsDto> getRelativeFaqs(String hql, Map<String, Object> params, String topNo);
}
