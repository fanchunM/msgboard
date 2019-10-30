package com.mine.product.szmtr.msgboard.person.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mine.product.szmtr.msgboard.person.dao.ICursorDao;
import com.mine.product.szmtr.msgboard.person.dto.CursorDto;
import com.mine.product.szmtr.msgboard.person.model.Cursor;
import com.vgtech.platform.common.utility.VGUtility;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CursorService implements ICursorService{
	private static final Logger logger = LoggerFactory.getLogger(CursorService.class);

	@Autowired
	private ICursorDao cursorDao;
	@Override
	public CursorDto createCursor(CursorDto cursorDto) {
		if(VGUtility.isEmpty(cursorDto)) throw new RuntimeException("CursorDto is null!");
		Cursor cursor = new Cursor();
		cursor.setOpenId(cursorDto.getOpenId());
		cursor.setHeadImgUrl(cursorDto.getHeadImgUrl());
		cursor.setType(cursorDto.getType());
		cursor.setSex(String.valueOf(cursorDto.getSex()));
		cursor.setUnionId(cursorDto.getUnionId());
		cursor.setNickName(cursorDto.getNickName());
		Cursor save = cursorDao.save(cursor);
		cursorDto.setId(save.getId());
		return cursorDto;
	}
	
	
	private CursorDto convert(Cursor cursor) {
		CursorDto cursorDto = new CursorDto();
		cursorDto.setId(cursor.getId());
		cursorDto.setOpenId(cursor.getOpenId());
		cursorDto.setHeadImgUrl(cursor.getHeadImgUrl());
		cursorDto.setType(cursor.getType());
		cursorDto.setSex(Integer.parseInt(cursor.getSex()));
		cursorDto.setUnionId(cursor.getUnionId());
		cursorDto.setNickName(cursor.getNickName());
		cursorDto.setCreateTimestamp(VGUtility.toDateStr(cursor.getCreateTimestamp(), "yyyy-MM-dd HH:mm:ss"));
		cursorDto.setLastUpdateTimestamp(VGUtility.toDateStr(cursor.getLastUpdateTimestamp(),"yyyy-MM-dd HH:mm:ss"));
		return cursorDto;
	}


	@Override
	public CursorDto findUserByOpenId(String openId) {
		Optional<Cursor> op = cursorDao.findByOpenId(openId);
		if(op.isPresent()) {
			CursorDto dto = new CursorDto();
			Cursor cs = op.get();
			dto.setId(cs.getId());
			dto.setOpenId(cs.getOpenId());
			dto.setUnionId(cs.getUnionId());
			dto.setNickName(cs.getNickName());
			dto.setSex(Integer.parseInt(cs.getSex()));
			dto.setHeadImgUrl(cs.getHeadImgUrl());
			return dto;
		}
		return null;
	}
}
