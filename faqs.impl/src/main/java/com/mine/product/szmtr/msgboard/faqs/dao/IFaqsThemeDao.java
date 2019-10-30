package com.mine.product.szmtr.msgboard.faqs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mine.product.szmtr.msgboard.faqs.model.FaqsTheme;

public interface IFaqsThemeDao extends JpaRepository<FaqsTheme, String>{
	List<FaqsTheme> findAllByFaqsId(String faqsId);
	List<FaqsTheme> findAllByFaqsIdAndThemeId(String faqsId,String themeId);
	List<FaqsTheme> findByThemeId(String themeId);
}
