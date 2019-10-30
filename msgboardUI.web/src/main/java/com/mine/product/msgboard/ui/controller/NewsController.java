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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.news.dto.NewsDto;
import com.mine.product.szmtr.msgboard.news.service.INewsService;
import com.vgtech.platform.common.utility.VGUtility;

@Controller
public class NewsController {
	private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
	
	@Autowired
	private INewsService newsService;

	@GetMapping(value = "news/newsList")
    @ResponseBody
    public Map<String, Object> newsList(
    		@RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "20") String rows,
            @RequestParam(required=false) String q)
    {
        List<NewsDto> messageDtoList = new ArrayList<NewsDto>();

		Map<String, Object> params = new HashMap<String, Object>();
		PageableDto pageable = new PageableDto(Integer.parseInt(page), Integer.parseInt(rows));
		
		String hql = "from News n where 1=1 ";
		
		if (!VGUtility.isEmpty(q)) {
			hql += " and (n.title like :title or n.subTitle like :subTitle "
					+ "or n.newsContent like :newsContent or n.newsDesc like :newsDesc)";
			params.put("title", "%"+q+"%");
			params.put("subTitle", "%"+q+"%");
			params.put("newsContent", "%"+q+"%");
			params.put("newsDesc", "%"+q+"%");
		}
		
		hql += " and newsStatus=2 order by n.createTimestamp desc";
		
		PageDto<NewsDto> pageDto = newsService.getNews(hql, params, pageable,null);
		//PageableDto pageable = new PageableDto(page, rows);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", pageDto.getRowData());
		result.put("total", pageDto.getTotalCount());
		result.put("pageNum", page);
        return result;
    }
	
	@GetMapping(value = "news/getNewsById")
    @ResponseBody
    public NewsDto getNewsById(
            @RequestParam(required=true) String id)
    {
		NewsDto dto = newsService.getNewsById(id);
		
		return dto;
    }
}
