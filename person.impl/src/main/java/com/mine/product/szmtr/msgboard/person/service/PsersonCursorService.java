package com.mine.product.szmtr.msgboard.person.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mine.product.szmtr.msgboard.message.model.Message;
import com.mine.product.szmtr.msgboard.person.dao.IPersonCursorDao;
import com.mine.product.szmtr.msgboard.person.dto.PersonCursorDto;
import com.mine.product.szmtr.msgboard.person.model.PersonCursor;
import com.vgtech.platform.common.utility.VGUtility;



@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PsersonCursorService implements IPersonCursorService{
	private static final Logger logger = LoggerFactory.getLogger(PsersonCursorService.class);
	
	@Autowired
	private IPersonCursorDao personCursorDao;
	
	@Override
	public void createPsersonCursor(PersonCursorDto dto) {
		PersonCursor personCursor = new PersonCursor();
		personCursor.setCursorId(dto.getCursorId());
		personCursor.setType(dto.getType());
		personCursor.setPersonId(dto.getPersonId());
		personCursor.setPhoneState(dto.getPhoneState());
		personCursor.setNickNameState(dto.getNickNameState());
		personCursor.setUserNameState(dto.getUserNameState());
		personCursor.setPassWordState(dto.getPassWordState());
		personCursorDao.save(personCursor);
	}

	@Override
	public PersonCursorDto findByCursorId(String cursorId) {
		PersonCursor op = personCursorDao.findByCursorId(cursorId);
		if(!VGUtility.isEmpty(op)) {
		    return convertToDto(op);
		}
		return null;
	}

    @Override
    public PersonCursorDto findByPersonId(String personId)
    {
        PersonCursor op = personCursorDao.findByPersonId(personId);
        if(!VGUtility.isEmpty(op)) {
            return convertToDto(op);
        }
        return null;
    }

    @Override
    public PersonCursorDto updatePersonCursor(PersonCursorDto personCursorDto)
    {
        if(VGUtility.isEmpty(personCursorDto)) throw new RuntimeException("PersonCursorDto is null!");
        if(VGUtility.isEmpty(personCursorDto.getId())) throw new RuntimeException("Id is null!");
        Optional<PersonCursor> op = personCursorDao.findById(personCursorDto.getId());
        if(op.isPresent()) {
            PersonCursor model = op.get();
            model.setCursorId(personCursorDto.getCursorId());
            model.setId(personCursorDto.getId());
            model.setType(personCursorDto.getType());
            model.setPhoneState(personCursorDto.getPhoneState());
            model.setNickNameState(personCursorDto.getNickNameState());
            model.setPersonId(personCursorDto.getPersonId());
            model.setUserNameState(personCursorDto.getUserNameState());
            model.setPassWordState(personCursorDto.getPassWordState());
            personCursorDao.save(model);
            return convertToDto(model);
        }else {
            throw new RuntimeException("Can not found result!");
        }
    }
    
    //将model转为dto
    private PersonCursorDto convertToDto(PersonCursor personCursor) {
        PersonCursorDto dto = new PersonCursorDto();
        dto.setId(personCursor.getId());
        dto.setCursorId(personCursor.getCursorId());
        dto.setType(personCursor.getType());
        dto.setPhoneState(personCursor.getPhoneState());
        dto.setNickNameState(personCursor.getNickNameState());
        dto.setPersonId(personCursor.getPersonId());
        dto.setUserNameState(personCursor.getUserNameState());
        dto.setPassWordState(personCursor.getPassWordState());
        return dto;
    }

    @Override
    public List<PersonCursorDto> findListByPersonId(String personId)
    {
        List<PersonCursorDto> personCursorDtoList = new ArrayList<PersonCursorDto>();
        List<PersonCursor> personCursorList = personCursorDao.findListByPersonId(personId);
        if(!VGUtility.isEmpty(personCursorList)) {
            for(PersonCursor personCursor:personCursorList) {
                PersonCursorDto personCursorDto = null;
                if(!VGUtility.isEmpty(personCursor)) {
                    personCursorDto = convertToDto(personCursor);
                }
                personCursorDtoList.add(personCursorDto);
            }
            return personCursorDtoList;
        }
        return null;
    }
}
