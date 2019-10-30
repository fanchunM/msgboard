package com.mine.product.szmtr.msgboard.person.service;

import java.awt.Cursor;

import com.mine.product.szmtr.msgboard.person.dto.CursorDto;

public interface ICursorService {
    //创建绑定用户
	public CursorDto createCursor(CursorDto cursorDto);
	//通过微信openId查询用户
	public CursorDto findUserByOpenId(String openId);
}
