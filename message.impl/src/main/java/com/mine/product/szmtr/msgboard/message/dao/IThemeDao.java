package com.mine.product.szmtr.msgboard.message.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mine.product.szmtr.msgboard.message.model.Theme;

public interface IThemeDao extends JpaRepository<Theme, String>{
	Theme findByMessageId(String messageId);
	List<Theme> findByThemeId(String themeId);
	List<Theme> findAllByMessageId(String messageId);
	List<Theme> findAllByMessageIdAndThemeId(String messageId,String themeId);
}
