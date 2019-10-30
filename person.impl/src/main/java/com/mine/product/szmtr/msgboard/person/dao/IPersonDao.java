package com.mine.product.szmtr.msgboard.person.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mine.product.szmtr.msgboard.person.model.Person;

public interface IPersonDao extends JpaRepository<Person, String>{
	public Person findByPhone(String phone);
	public Person findByUserNameAndPassword(String userName, String password);
	public Person getById(String id);
	public Person findByPhoneAndValidateCode(String phone, String validateCode);
	public Person findByUserName(String userName);
	public Person findByNickName(String nickName);
}
