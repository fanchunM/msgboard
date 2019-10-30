package com.mine.product.szmtr.msgboard.message.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mine.product.szmtr.msgboard.message.dao.IImageDao;
import com.mine.product.szmtr.msgboard.message.dto.ImageDto;
import com.mine.product.szmtr.msgboard.message.model.Image;
import com.vgtech.platform.common.utility.VGUtility;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ImageService implements IImageService{
	
	private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

	@Autowired
	private IImageDao imageDao;
	@Override
	public ImageDto createImageModel(ImageDto imageDto) {
		return convertToDto(imageDao.save(convertToModel(imageDto)));
	}

	@Override
	public void updateImage(String messageId,String imageId) {
		logger.info("MessageId ="+messageId +" And ImageId="+imageId);
		if(VGUtility.isEmpty(imageId))
			throw new RuntimeException("图片id为空!");
		Optional<Image> imageOption = imageDao.findById(imageId);
		if(imageOption.isPresent()) {
			Image image = imageOption.get();
			image.setParentId(messageId);
			imageDao.save(image);
		}
	}
	

	private ImageDto convertToDto(Image model) {
		ImageDto dto = new ImageDto();
		dto.setId(model.getId());
		if(!VGUtility.isEmpty(model.getAddress())) {
			dto.setAddress(model.getAddress());
		}
		if(!VGUtility.isEmpty(model.getImageName())) {
			dto.setImageName(model.getImageName());
		}
		if(!VGUtility.isEmpty(model.getParentId())) {
			dto.setParentId(model.getParentId());
		}
		dto.setCreateTimestamp(VGUtility.toDateStr(model.getCreateTimestamp(), "yyyy-MM-dd HH:mm:ss"));
		return dto;
	}
	
	private Image convertToModel(ImageDto imageDto) {
		Image image = new Image();
		if(!VGUtility.isEmpty(imageDto)) {
			if(!VGUtility.isEmpty(imageDto.getId())) {
				image.setId(imageDto.getId());
			}
			image.setAddress(imageDto.getAddress());
			image.setImageName(imageDto.getImageName());
			image.setParentId(imageDto.getParentId());
		}
		return image;
	}

	@Override
	public List<ImageDto> getPicsByParentId(String id) {
		List<Image> images = imageDao.getImageByParentId(id);
		List<ImageDto> pics = new ArrayList<ImageDto>();
		if(!VGUtility.isEmpty(images)&&images.size()>0) {
			for(Image pic : images) {
				ImageDto dto = convertToDto(pic);
				pics.add(dto);
			}
		}
		return pics;
	}

	@Override
	public void deletePicsByParentId(String id) {
		imageDao.deleteImageByParentId(id);
	}

	@Override
	public List<ImageDto> getMessageImages(String messageId) {
		if(VGUtility.isEmpty(messageId))
			throw new RuntimeException("留言Id为空!");
		List<ImageDto> dtoList = new ArrayList<ImageDto>();
		List<Image> modelList = imageDao.findByParentId(messageId);
		for(Image model : modelList) {
			dtoList.add(convertToDto(model));
		}
		return dtoList;
	}
}
