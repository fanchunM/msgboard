package com.mine.product.szmtr.msgboard.message.service;



import java.util.List;
import java.util.Map;

import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.message.dto.DictionaryCommonCodeDto;
import com.mine.product.szmtr.msgboard.message.dto.SysDictionaryDto;
import com.vgtech.platform.common.web.dto.CommonComboDto;
/**
 * //对本地同步主题数据库信息进行查、改
 * @author 何森
 *
 */
public interface IsysDictionaryService {
	SysDictionaryDto updateDictionaryCommonCode(SysDictionaryDto dto);
	List<SysDictionaryDto> findAllDictionaryCommonCode();
	PageDto<SysDictionaryDto> getDictionaryCommonCode(String hql, Map<String, Object> params, PageableDto pageable);
	PageDto<SysDictionaryDto> getDictionaryCommonCode(String hql, String countHql, Map<String, Object> params, PageableDto pageable);
	List<CommonComboDto> getFatherTheme();
	SysDictionaryDto getThemeById(String id);
	void addTheme(SysDictionaryDto dto);
	List<SysDictionaryDto> getThemeByParentThemeId(String parentId);
	void deleteTheme(String id);
	void updateTheme(SysDictionaryDto dto);
	SysDictionaryDto getSysDictionaryById(String id);
	//通过sql语句查询所有主题
	List<SysDictionaryDto> findAllDictionaryCommonCodeSql(String sql);
}
