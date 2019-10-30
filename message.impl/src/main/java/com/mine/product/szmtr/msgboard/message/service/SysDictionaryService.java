package com.mine.product.szmtr.msgboard.message.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.faqs.dao.IFaqsThemeDao;
import com.mine.product.szmtr.msgboard.faqs.model.FaqsTheme;
import com.mine.product.szmtr.msgboard.message.dao.IDictionaryDao;
import com.mine.product.szmtr.msgboard.message.dao.IThemeDao;
import com.mine.product.szmtr.msgboard.message.dto.DictionaryCommonCodeDto;
import com.mine.product.szmtr.msgboard.message.dto.MessageDto;
import com.mine.product.szmtr.msgboard.message.dto.SysDictionaryDto;
import com.mine.product.szmtr.msgboard.message.model.DictionaryCommonCode;
import com.mine.product.szmtr.msgboard.message.model.Message;
import com.mine.product.szmtr.msgboard.message.model.Theme;
import com.vgtech.platform.common.utility.VGUtility;
import com.vgtech.platform.common.web.dto.CommonComboDto;
/**
 * //对本地同步主题数据库信息进行查、改
 * @author 何森
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class SysDictionaryService implements IsysDictionaryService{
 
	private static final Logger logger = LoggerFactory.getLogger(SysDictionaryService.class);
	@Autowired
	private IDictionaryDao dictionaryDao;
	@Autowired
	private IThemeDao themeDao;
	@Autowired
	private IFaqsThemeDao faqsThemeDao;
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public SysDictionaryDto updateDictionaryCommonCode(SysDictionaryDto dto) {
//		if(VGUtility.isEmpty(dto)) throw new RuntimeException("DictionaryCommonCodeDto is null!");
//		if(VGUtility.isEmpty(dto.getBaseId())) throw new RuntimeException("DictionaryCommonCodeDtoBaseId is null!");
//		DictionaryCommonCode modelOpt=dictionaryDao.findByBaseId(dto.getBaseId());
//		if(modelOpt!=null) {
//			modelOpt.setBaseId(dto.getBaseId());
//			modelOpt.setCode(dto.getCode());
//			modelOpt.setParentDictCommonCodeId(dto.getParentDictCommonCodeId());
//			modelOpt.setChsName(dto.getChsName());
//			dictionaryDao.save(modelOpt);
			//转换model为dto
//			return convert(modelOpt);
//		}else {
			return null;
//		}
//		
	}
	
	/**
	 * 
	* @author 何森
	* @date 2018年12月21日下午5:10:48
	* @Description:转换model为dto        
	* @return DictionaryCommonCodeDto    
	*
	 */
	private SysDictionaryDto convert(DictionaryCommonCode model) {
		SysDictionaryDto dto = new SysDictionaryDto();
		dto.setId(model.getId());
		dto.setChsName(model.getChsName());
		dto.setSerialNumber(model.getSerialNumber());
		dto.setCreateTimestamp(VGUtility.toDateStr(model.getCreateTimestamp(),"yyyy-MM-dd HH:mm:ss"));
		dto.setLastUpdateTimestamp(VGUtility.toDateStr(model.getLastUpdateTimestamp(),"yyyy-MM-dd HH:mm:ss"));
		if(!VGUtility.isEmpty(model.getParentThemeId())) {
			dto.setParentThemeId(model.getParentThemeId());
			dto.setParentThemeName(dictionaryDao.findById(model.getParentThemeId()).get().getChsName());
		}
		return dto;
	}

	@Override
	public List<SysDictionaryDto> findAllDictionaryCommonCode() {
	    List<DictionaryCommonCode> commonCodeList = dictionaryDao.findAll();
	    List<SysDictionaryDto> dtoList =new ArrayList<SysDictionaryDto>();
	    if(!VGUtility.isEmpty(commonCodeList)) {
	    	for(DictionaryCommonCode model:commonCodeList ) {
	    		SysDictionaryDto dto = new SysDictionaryDto();
	    		dto.setId(model.getId());
	    		dto.setChsName(model.getChsName());
	    		if(!VGUtility.isEmpty(model.getParentThemeId())) {
	    			dto.setParentThemeId(model.getParentThemeId());
	    			dto.setParentThemeName(dictionaryDao.findById(model.getParentThemeId()).get().getChsName());
	    		}
	    		dtoList.add(dto);
	    	}
	    }
		return dtoList;
	}

	@Override
	public PageDto<SysDictionaryDto> getDictionaryCommonCode(String hql, Map<String, Object> params,
			PageableDto pageable) {
		return getDictionaryCommonCode(hql, "select count(id) " + hql, params, pageable);
	}

	@Override
	public PageDto<SysDictionaryDto> getDictionaryCommonCode(String hql, String countHql, Map<String, Object> params,
			PageableDto pageable) {
		TypedQuery<DictionaryCommonCode> query = entityManager.createQuery(hql, DictionaryCommonCode.class);
		TypedQuery<Long> countQuery = entityManager.createQuery("select count(id) " + hql, Long.class);
		for(Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
			countQuery.setParameter(entry.getKey(), entry.getValue());
		}
		if (!VGUtility.isEmpty(pageable))
			query.setFirstResult((pageable.getPage() - 1) * pageable.getSize()).setMaxResults(pageable.getSize());
		List<DictionaryCommonCode> modelList = query.getResultList();
		
		List<SysDictionaryDto> resultList = new ArrayList<SysDictionaryDto>();
		for (DictionaryCommonCode model : modelList) {
			resultList.add(convert(model));
		}
		return new PageDto<SysDictionaryDto>(countQuery.getSingleResult(), resultList);
	}

	@Override
	public List<CommonComboDto> getFatherTheme() {
		List<DictionaryCommonCode> modelList = dictionaryDao.findAll();
		List<CommonComboDto> comboList = new ArrayList<CommonComboDto>();
		System.out.println(modelList.size());
		for(DictionaryCommonCode model :modelList) {
			if(VGUtility.isEmpty(dictionaryDao.findById(model.getId()).get().getParentThemeId())) {
				CommonComboDto CommonComboDto = new CommonComboDto();
				CommonComboDto.setValue(model.getId());
				CommonComboDto.setText(model.getChsName());
				comboList.add(CommonComboDto);
			}
		}
		return comboList;
	}

	@Override
	public void addTheme(SysDictionaryDto dto) {
		DictionaryCommonCode model = new DictionaryCommonCode();
		model.setSerialNumber(dto.getSerialNumber());
		model.setChsName(dto.getChsName());
		if(!VGUtility.isEmpty(dto.getParentThemeId())) {
			if(dictionaryDao.findById(dto.getParentThemeId()).isPresent() == false)
				throw new RuntimeException("该父级主题不存在,请重新选择!");
			model.setParentThemeId(dto.getParentThemeId());
		}
		dictionaryDao.save(model);
	}

	@Override
	public SysDictionaryDto getThemeById(String id) {
		SysDictionaryDto sysDictionaryDto = new SysDictionaryDto();
		if(!VGUtility.isEmpty(id)) {
			DictionaryCommonCode model = dictionaryDao.findById(id).get();
			sysDictionaryDto = convert(model);
		}
		return sysDictionaryDto;
	}

	@Override
	public List<SysDictionaryDto> getThemeByParentThemeId(String parentId) {
		if(!VGUtility.isEmpty(parentId)) {
			List<DictionaryCommonCode> modelList = dictionaryDao.findByParentThemeId(parentId); 
			List<SysDictionaryDto> dtoList = new ArrayList<SysDictionaryDto>();
			for(DictionaryCommonCode model :modelList) {
				dtoList.add(convert(model));
			}
			return dtoList;
		}else {
			return null;
		}
	}

	@Override
	public void deleteTheme(String id) {
		List<SysDictionaryDto> dtoList = this.getThemeByParentThemeId(id);
		if(!VGUtility.isEmpty(dtoList) && dtoList.size()>0) {
			for(SysDictionaryDto dto : dtoList) {
				List<Theme> themeList = themeDao.findByThemeId(dto.getId());
				if(!VGUtility.isEmpty(themeList) && themeList.size()>0) {
					for(Theme theme : themeList) {
						themeDao.delete(theme);
					}
				}
				List<FaqsTheme> faqsThemeList = faqsThemeDao.findByThemeId(dto.getId());
				if(!VGUtility.isEmpty(faqsThemeList) && faqsThemeList.size()>0) {
					for(FaqsTheme faqsTheme : faqsThemeList) {
						faqsThemeDao.delete(faqsTheme);
					}
				}
				dictionaryDao.deleteById(dto.getId());
			}
		}
		List<Theme> themeList = themeDao.findByThemeId(id);
		if(!VGUtility.isEmpty(themeList) && themeList.size()>0) {
			for(Theme theme : themeList) {
				themeDao.delete(theme);
			}
		}
		List<FaqsTheme> faqsThemeList = faqsThemeDao.findByThemeId(id);
		if(!VGUtility.isEmpty(faqsThemeList) && faqsThemeList.size()>0) {
			for(FaqsTheme faqsTheme : faqsThemeList) {
				faqsThemeDao.delete(faqsTheme);
			}
		}
		dictionaryDao.deleteById(id);
	}

	@Override
	public void updateTheme(SysDictionaryDto dto) {
		DictionaryCommonCode model = dictionaryDao.findById(dto.getId()).get();
		model.setChsName(dto.getChsName());
		model.setSerialNumber(dto.getSerialNumber());
		dictionaryDao.save(model);
	}

	@Override
	public SysDictionaryDto getSysDictionaryById(String id) {
		SysDictionaryDto dto = convert(dictionaryDao.findById(id).get());
		return dto;
	}

	@Override
	public List<SysDictionaryDto> findAllDictionaryCommonCodeSql(String sql) {
		Session session = entityManager.unwrap(org.hibernate.Session.class);
		Query query = session.createNativeQuery(sql.toString(),DictionaryCommonCode.class);
		List<DictionaryCommonCode> list = (ArrayList<DictionaryCommonCode>)query.getResultList();
		List<SysDictionaryDto> dtos = new ArrayList<SysDictionaryDto>();
		for(DictionaryCommonCode data: list) {
			dtos.add(convert(data));
		}
		return dtos;
	}
}
