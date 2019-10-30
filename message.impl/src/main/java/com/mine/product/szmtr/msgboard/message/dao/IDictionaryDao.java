package com.mine.product.szmtr.msgboard.message.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mine.product.szmtr.msgboard.message.model.DictionaryCommonCode;

public interface IDictionaryDao extends JpaRepository<DictionaryCommonCode, String>{
//	public DictionaryCommonCode findByBaseId(String baseId);
	//通过父级编码查找
//	public List<DictionaryCommonCode> findByParentDictCommonCodeId(String baseId);
	public List<DictionaryCommonCode> findByParentThemeId(String parentId);
}
