package com.mine.product.szmtr.msgboard.faqs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.faqs.dao.IFaqsDao;
import com.mine.product.szmtr.msgboard.faqs.dao.IFaqsThemeDao;
import com.mine.product.szmtr.msgboard.faqs.dto.FaqsDto;
import com.mine.product.szmtr.msgboard.faqs.dto.FaqsThemeDto;
import com.mine.product.szmtr.msgboard.faqs.model.FaqsModel;
import com.mine.product.szmtr.msgboard.faqs.model.FaqsTheme;
import com.vgtech.platform.common.utility.VGUtility;
/**
 * 便民问答
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FaqsService implements IFaqsService {
	
	private static final Logger logger = LoggerFactory.getLogger(FaqsService.class);

	@Autowired
	private IFaqsDao faqsDao;
	@Autowired
	private IFaqsThemeDao faqsThemeDao;
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public FaqsDto createFaqs(FaqsDto dto) {
		if(VGUtility.isEmpty(dto)) throw new RuntimeException("FaqsDto is null!");
		FaqsModel model = new FaqsModel();
		model.setId(dto.getId());
		model.setQuestion(dto.getQuestion());
		model.setAnswers(dto.getAnswers());
		model.setOrderIndex(dto.getOrderIndex());
		model.setHits(dto.getHits());
		model.setFaqsStatus(dto.getFaqsStatus());
		model.setApprivalPersonId(dto.getApprivalPersonId());
		model.setPersonId(dto.getPersonId());
		
		FaqsModel modelNew = faqsDao.save(model);
		return convert(modelNew);
	}

	@Override
	public void deleteFaqs(String id) {
		if (VGUtility.isEmpty(id)) throw new RuntimeException("Id is null!");
		faqsDao.deleteById(id);
	}

	@Override
	public FaqsDto updateFaqs(FaqsDto dto) {
		if(VGUtility.isEmpty(dto)) throw new RuntimeException("FaqsDto is null!");
		if(VGUtility.isEmpty(dto.getId())) throw new RuntimeException("Id is null!");
		Optional<FaqsModel> modelOpt = faqsDao.findById(dto.getId());
		if(modelOpt.isPresent()) {
			FaqsModel model = modelOpt.get();
			model.setId(dto.getId());
			model.setQuestion(dto.getQuestion());
			model.setAnswers(dto.getAnswers());
			model.setOrderIndex(dto.getOrderIndex());
			model.setHits(dto.getHits());
			model.setFaqsStatus(dto.getFaqsStatus());
			model.setApprivalPersonId(dto.getApprivalPersonId());
			model.setPersonId(dto.getPersonId());
			//model.setPersonName("");
			
			//model.setCreateTimestamp(VGUtility.toDateStr(dto.getCreateTimestamp(), "yyyy-MM-dd HH:mm:ss"));
			//model.setLastUpdateTimestamp(VGUtility.toDateStr(dto.getLastUpdateTimestamp(),"yyyy-MM-dd HH:mm:ss"));
			
			faqsDao.save(model);
			return convert(model);
		} else 
			throw new RuntimeException("Can not found result!");
	}
	
	@Override
	public PageDto<FaqsDto> getFaqs(String hql, Map<String, Object> params, PageableDto pageable) {
		return getFaqs(hql, "select count(id) " + hql, params, pageable);
	}
	
	@Override
	public PageDto<FaqsDto> getFaqs(String hql, String countHql, Map<String, Object> params, PageableDto pageable) {
		TypedQuery<FaqsModel> query = entityManager.createQuery(hql, FaqsModel.class);
		TypedQuery<Long> countQuery = entityManager.createQuery("select count(id) " + hql, Long.class);
		for(Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
			countQuery.setParameter(entry.getKey(), entry.getValue());
		}
		if (!VGUtility.isEmpty(pageable))
			query.setFirstResult((pageable.getPage() - 1) * pageable.getSize()).setMaxResults(pageable.getSize());
		List<FaqsModel> modelList = query.getResultList();
		
		List<FaqsDto> resultList = new ArrayList<FaqsDto>();
		for (FaqsModel model : modelList) {
			resultList.add(convert(model));
		}
		return new PageDto<FaqsDto>(countQuery.getSingleResult(), resultList);
	}
	
	@Override
	public FaqsDto getFaqsById(String id) {
		if(VGUtility.isEmpty(id)) throw new RuntimeException("Id is null!");
		Optional<FaqsModel> modelOpt = faqsDao.findById(id);
		if(modelOpt.isPresent()) {
			FaqsModel model = modelOpt.get();
			return convert(model);
		} else 
			throw new RuntimeException("Can not found result!");
	}
	
	private FaqsDto convert(FaqsModel model) {
		FaqsDto dto = new FaqsDto();
		dto.setId(model.getId());
		dto.setQuestion(model.getQuestion());
		dto.setAnswers(model.getAnswers());
		dto.setOrderIndex(model.getOrderIndex());
		dto.setHits(model.getHits());
		dto.setFaqsStatus(model.getFaqsStatus());
		dto.setApprivalPersonId(model.getApprivalPersonId());
		dto.setPersonId(model.getPersonId());
		dto.setPersonName("");
		
		dto.setCreateTimestamp(VGUtility.toDateStr(model.getCreateTimestamp(), "yyyy-MM-dd HH:mm:ss"));
		dto.setLastUpdateTimestamp(VGUtility.toDateStr(model.getLastUpdateTimestamp(),"yyyy-MM-dd HH:mm:ss"));
		
		dto.setId(model.getId());
		return dto;
	}

	@Override
	public void saveThemeByFaqsIdAndThemeId(String faqsId, String themeId) {
		// 先查看是否存在关联主题分类
		List<FaqsTheme> faqsThemeList = faqsThemeDao.findAllByFaqsIdAndThemeId(faqsId,themeId);
		//如果不存在，添加
		if(null==faqsThemeList || faqsThemeList.size() == 0) {
			FaqsTheme ft = new FaqsTheme();
			ft.setFaqsId(faqsId);
			ft.setThemeId(themeId);
			faqsThemeDao.save(ft);
		}
	}

	@Override
	public List<FaqsThemeDto> findFaqsThemeByFaqsId(String faqsId) {
		List<FaqsTheme> themeList = faqsThemeDao.findAllByFaqsId(faqsId);
		List<FaqsThemeDto> themeDtoList = new ArrayList<FaqsThemeDto>();
		if(!VGUtility.isEmpty(themeList)) {
			for(FaqsTheme fh:themeList) {
				FaqsThemeDto faqsthemeDto = new FaqsThemeDto();
				faqsthemeDto.setFaqsId(fh.getFaqsId());
				faqsthemeDto.setId(fh.getId());
				faqsthemeDto.setThemeId(fh.getThemeId());
				themeDtoList.add(faqsthemeDto);
			}
			return themeDtoList;
		}
		return null;
	}

	@Override
	public void deleteFaqsThemeById(String id) {
		faqsThemeDao.deleteById(id);
	}

	@Override
	public List<FaqsDto> getRelativeFaqs(String sql, Map<String, Object> params, String topNo) {
		Session session = entityManager.unwrap(org.hibernate.Session.class);
		Query query = session.createNativeQuery(sql.toString(),FaqsModel.class);
		for(Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(),entry.getValue());
		}
		query.setMaxResults(Integer.parseInt(topNo));
		List<FaqsModel> list = (ArrayList<FaqsModel>)query.getResultList();
		List<FaqsDto> dtos = new ArrayList<FaqsDto>();
		for(FaqsModel data: list) {
			dtos.add(convert(data));
		}
		return dtos;
	}
}
