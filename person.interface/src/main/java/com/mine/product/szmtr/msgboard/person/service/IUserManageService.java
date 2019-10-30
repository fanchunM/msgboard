package com.mine.product.szmtr.msgboard.person.service;

import com.mine.product.szmtr.msgboard.person.dto.UserManageDto;

public interface IUserManageService {
	//通过userId向userManage添加用户
	public void addUserByUserId(String userId);
	//通过userId将userManage中的用户删除
	public void deleteUserByUserId(String userId);
	/**  
	 * 通过userId查询UserManage中的用户信息
	* @author 何森
	* @date 2018年11月29日上午11:12:52
	* @Description:        
	* @return UserManageDto    
	*/  
	public UserManageDto findUserByUserId(String userId);
}
