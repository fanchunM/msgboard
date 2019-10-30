package com.mine.product.szmtr.msgboard.person.service;

import java.util.List;

import com.mine.product.szmtr.msgboard.person.dto.PersonCursorDto;

public interface IPersonCursorService {

	public void createPsersonCursor(PersonCursorDto dto);
	
	public PersonCursorDto findByCursorId(String cursorId);
	
	public PersonCursorDto findByPersonId(String personId);
	
	public PersonCursorDto updatePersonCursor(PersonCursorDto personCursorDto);
	//返回personCursorDtoList是因为一个账号可同时关联微信Id和苏e行Id,不止一条数据
	public List<PersonCursorDto> findListByPersonId(String personId);
}
