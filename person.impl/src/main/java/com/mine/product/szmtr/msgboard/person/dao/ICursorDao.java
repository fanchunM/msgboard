package com.mine.product.szmtr.msgboard.person.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mine.product.szmtr.msgboard.person.model.Cursor;



public interface ICursorDao extends JpaRepository<Cursor, String>{
	//通过微信openId获取cursor表中的用户信息，openId对于微信公众平台是唯一的
	Optional<Cursor> findByOpenId(String openId);
}
