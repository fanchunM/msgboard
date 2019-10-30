package com.mine.product.szmtr.msgboard.message.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mine.product.szmtr.msgboard.message.model.Image;

public interface IImageDao extends JpaRepository<Image, String>{
	public List<Image> getImageByParentId(String id);
	void deleteImageByParentId(String id);
	List<Image> findByParentId(String messageId);
}
