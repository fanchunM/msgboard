package com.mine.product.szmtr.msgboard.news.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.message.dto.ImageDto;
import com.mine.product.szmtr.msgboard.message.service.IImageService;
import com.mine.product.szmtr.msgboard.news.dao.INewsDao;
import com.mine.product.szmtr.msgboard.news.dto.NewsDto;
import com.mine.product.szmtr.msgboard.news.model.News;
import com.vgtech.platform.common.utility.VGUtility;
/**
 * 权威发布
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NewsService implements INewsService {
	
	private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

	@Autowired
	private INewsDao newsDao;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private IImageService imageService;
	
	
	@Override
	public NewsDto createNews(NewsDto dto) {
		if(VGUtility.isEmpty(dto)) throw new RuntimeException("newsDto is null!");
		News model = new News();
		
		model.setId(dto.getId());
		model.setTitle(dto.getTitle());
		model.setSubTitle(dto.getSubTitle());
		model.setKeyword(dto.getKeyword());
		model.setAuthor(dto.getAuthor());
		model.setDeptName(dto.getDeptName());
		model.setTopStatus(dto.isTopStatus());
		model.setPublishTime(VGUtility.toDateObj(dto.getPublishTime(), "yyyy-MM-dd HH:mm:ss"));
		model.setNewsContent(dto.getNewsContent());
		model.setNewsDesc(dto.getNewsDesc());
		model.setHits(dto.getHits());
		model.setNewsStatus(dto.getNewsStatus());
		model.setPersonId(dto.getPersonId());
		model.setLinkURL(dto.getLinkURL());
		model.setCreateTimestamp(VGUtility.toDateObj(dto.getCreateTimestamp(), "yyyy-MM-dd HH:mm:ss"));
		model.setApprivalPersonId(dto.getApprivalPersonId());
		News modelNew = newsDao.save(model);
		return convert(modelNew);
	}

	@Override
	public void deleteNews(String id) {
		if (VGUtility.isEmpty(id)) throw new RuntimeException("Id is null!");
		newsDao.deleteById(id);
	}

	@Override
	public NewsDto updateNews(NewsDto dto) {
		if(VGUtility.isEmpty(dto)) throw new RuntimeException("News is null!");
		if(VGUtility.isEmpty(dto.getId())) throw new RuntimeException("Id is null!");
		Optional<News> modelOpt = newsDao.findById(dto.getId());
		if(modelOpt.isPresent()) {
			News model = modelOpt.get();
			
			model.setId(dto.getId());
			model.setTitle(dto.getTitle());
			model.setSubTitle(dto.getSubTitle());
			model.setKeyword(dto.getKeyword());
			model.setAuthor(dto.getAuthor());
			model.setDeptName(dto.getDeptName());
			model.setTopStatus(dto.isTopStatus());
			model.setPublishTime(VGUtility.toDateObj(dto.getPublishTime(), "yyyy-MM-dd HH:mm:ss"));
			model.setNewsContent(dto.getNewsContent());
			model.setNewsDesc(dto.getNewsDesc());
			model.setHits(dto.getHits());
			model.setNewsStatus(dto.getNewsStatus());
			model.setPersonId(dto.getPersonId());
			model.setLinkURL(dto.getLinkURL());
			model.setApprivalPersonId(dto.getApprivalPersonId());
			newsDao.save(model);
			return convert(model);
		} else 
			throw new RuntimeException("Can not found result!");
	}
	
	@Override
	public PageDto<NewsDto> getNews(String hql, Map<String, Object> params, PageableDto pageable,String url) {
		return getNews(hql, "select count(id) " + hql, params, pageable,url);
	}
	
	@Override
	public PageDto<NewsDto> getNews(String hql, String countHql, Map<String, Object> params, PageableDto pageable,String url) {
		TypedQuery<News> query = entityManager.createQuery(hql, News.class);
		TypedQuery<Long> countQuery = entityManager.createQuery("select count(id) " + hql, Long.class);
		for(Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
			countQuery.setParameter(entry.getKey(), entry.getValue());
		}
		if (!VGUtility.isEmpty(pageable))
			query.setFirstResult((pageable.getPage() - 1) * pageable.getSize()).setMaxResults(pageable.getSize());
		List<News> modelList = query.getResultList();
		
		List<NewsDto> resultList = new ArrayList<NewsDto>();
		for (News model : modelList) {
			NewsDto newsDto = convert(model);
			if(!VGUtility.isEmpty(url)) {
				newsDto.setFrontUrl(url);
			}
			resultList.add(newsDto);
		}
		return new PageDto<NewsDto>(countQuery.getSingleResult(), resultList);
	}
	
	@Override
	public NewsDto getNewsById(String id) {
		if(VGUtility.isEmpty(id)) throw new RuntimeException("Id is null!");
		Optional<News> modelOpt = newsDao.findById(id);
		if(modelOpt.isPresent()) {
			News model = modelOpt.get();
			return convert(model);
		} else 
			throw new RuntimeException("Can not found result!");
	}
	
	private NewsDto convert(News model) {
		NewsDto dto = new NewsDto();
		dto.setId(model.getId());
		dto.setTitle(model.getTitle());
		dto.setSubTitle(model.getSubTitle());
		dto.setKeyword(model.getKeyword());
		dto.setAuthor(model.getAuthor());
		dto.setDeptName(model.getDeptName());
		dto.setTopStatus(model.isTopStatus());
		dto.setPublishTime(VGUtility.toDateStr(model.getPublishTime(), "yyyy-MM-dd HH:mm:ss"));
		dto.setNewsContent(model.getNewsContent());
		dto.setNewsDesc(model.getNewsDesc());
		dto.setHits(model.getHits());
		dto.setNewsStatus(model.getNewsStatus());
		dto.setPersonId(model.getPersonId());
		dto.setLinkURL(model.getLinkURL());
		dto.setApprivalPersonId(model.getApprivalPersonId());
		List<ImageDto> imageDtoList = imageService.getPicsByParentId(model.getId());
		if(!VGUtility.isEmpty(imageDtoList) && imageDtoList.size()>0) {
			dto.setNewsImageList(imageDtoList);
		}
		dto.setCreateTimestamp(VGUtility.toDateStr(model.getCreateTimestamp(), "yyyy-MM-dd HH:mm:ss"));
		dto.setLastUpdateTimestamp(VGUtility.toDateStr(model.getLastUpdateTimestamp(),"yyyy-MM-dd HH:mm:ss"));
		dto.setId(model.getId());
		return dto;
	}
}
