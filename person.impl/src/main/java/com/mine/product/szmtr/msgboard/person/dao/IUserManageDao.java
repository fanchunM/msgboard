package com.mine.product.szmtr.msgboard.person.dao;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mine.product.szmtr.msgboard.person.model.UserManage;

public interface IUserManageDao extends JpaRepository<UserManage, String>{

	void deleteByUserId(String userId);

	Optional<UserManage> findByUserId(String userId);

}
