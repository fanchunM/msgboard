package com.mine.product.szmtr.msgboard.person.service;

import java.util.List;
import java.util.Map;

import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.person.dto.LoginHistoryDto;
import com.mine.product.szmtr.msgboard.person.dto.PersonDto;

public interface IPersonService {
	
	/**
	 * 性别：未知,男,女
	 */
	public static enum GENDER{
		未知,男,女
	}
	
	public static enum PERSONSTATUS {
		停用,启用
	}
	
	public void createPerson(String phone, String pwd, String validate,String registeredIP, String headPortraits);
	public PersonDto loginPerson(String phone, String pwd);
	public PersonDto getByUserName(String userName);
	public PersonDto getOneById(String id);
	public PersonDto forgetPwdLoginByValidate(String phone, String validate);
	public PersonDto changeNickName(String userId, String nickName);
	public PersonDto changePhone(String userId, String phone);
	public PersonDto changePwd(String userId,String newPwd);
	public PersonDto changeSex(String userId, String sex);
	public PageDto<PersonDto> getPersons(String hql, Map<String, Object> params, PageableDto pageable);
	public PageDto<PersonDto> getPersons(String hql, String countHql, Map<String, Object> params, PageableDto pageable);
	public void resetPwd(String id, String pwd);
	public PersonDto changeRegisteredIP(String userId, String registeredIP);
	public PersonDto changeRecentLoginIP(String userId, String recentLoginIP);
	public PersonDto changeHead(String userId, String headPortraits);
//	public PersonDto changeUpLoadHeadImage(String userId, String upLoadHeadImage);
	//通过手机号查找用户信息
	public PersonDto getByPhone(String phone);
	//修改用户名
	public PersonDto changeUserName(String userId, String userName);
	/**
	 * @param userId
	 * @param phone
	 * @param personId
	 * @return
	 */
	public PersonDto changePhoneForSuEXing(String phone,String personId);

	void addLoginHistory(String userName, int status, String ip);
	List<LoginHistoryDto> getLoginHistoryByQuery(String userName, String beginDate, String endDate, int status, int ifUse);
	void updateLoginHistoryIfUse(String userName);
	boolean loginJudge(String userName);
}
