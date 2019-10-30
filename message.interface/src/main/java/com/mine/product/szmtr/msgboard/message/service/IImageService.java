package com.mine.product.szmtr.msgboard.message.service;

import java.util.List;

import com.mine.product.szmtr.msgboard.message.dto.ImageDto;

public interface IImageService {
	/**
	 * 留言上传图片
	 */
	ImageDto createImageModel(ImageDto imageDto);
	
	void updateImage(String messageId,String imageId);
	
	List<ImageDto> getPicsByParentId(String id);
	
	void deletePicsByParentId(String id);
	List<ImageDto> getMessageImages(String messageId);
}
