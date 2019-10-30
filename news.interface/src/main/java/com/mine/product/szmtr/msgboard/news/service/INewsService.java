package com.mine.product.szmtr.msgboard.news.service;

import java.util.Map;

import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.news.dto.NewsDto;

public interface INewsService {

	/**
	 * 新闻审核状态：未处理, 审核不通过, 审核通过
	 */
	public static enum NEWS_STATUS {
		未处理, 审核不通过, 审核通过
	}
	
	NewsDto createNews(NewsDto dto);
	void deleteNews(String id);
	NewsDto updateNews(NewsDto dto);
	NewsDto getNewsById(String id);
	
	PageDto<NewsDto> getNews(String hql, Map<String, Object> params, PageableDto pageable,String url);
	PageDto<NewsDto> getNews(String hql, String countHql, Map<String, Object> params, PageableDto pageable,String url);
	
}
