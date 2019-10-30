package com.mine.product.szmtr.msgboard.person.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mine.product.szmtr.msgboard.person.dao.IUserManageDao;
import com.mine.product.szmtr.msgboard.person.dto.UserManageDto;
import com.mine.product.szmtr.msgboard.person.model.UserManage;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserManageService implements IUserManageService{
	private static final Logger logger = LoggerFactory.getLogger(UserManageService.class);
	
	@Autowired
	private IUserManageDao userManageDao;
	@Override
	public void addUserByUserId(String userId) {
		UserManage user = new UserManage();
		user.setUserId(userId);
		userManageDao.save(user);
	}

	@Override
	public void deleteUserByUserId(String userId) {
		userManageDao.deleteByUserId(userId);
	}

	@Override
	public UserManageDto findUserByUserId(String userId) {
		Optional<UserManage> op = userManageDao.findByUserId(userId);
		if(op.isPresent()) {
			UserManageDto user = new UserManageDto();
			UserManage um = op.get();
			user.setuId(um.getUserId());
			user.setId(um.getId());
			return user;
		}
		return null;
	}
	
}
