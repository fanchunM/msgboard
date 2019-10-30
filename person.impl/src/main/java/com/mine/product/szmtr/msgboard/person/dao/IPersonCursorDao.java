package com.mine.product.szmtr.msgboard.person.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mine.product.szmtr.msgboard.person.model.PersonCursor;

public interface IPersonCursorDao extends JpaRepository<PersonCursor, String>{
	PersonCursor findByCursorId(String cursorId);
	PersonCursor findByPersonId(String personId);
	List<PersonCursor> findListByPersonId(String personId);
}
