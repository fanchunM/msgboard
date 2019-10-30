package com.mine.product.szmtr.msgboard.person.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.message.dto.MessageDto;
import com.mine.product.szmtr.msgboard.message.service.IMessageService;
import com.mine.product.szmtr.msgboard.person.dto.PersonCursorDto;
import com.mine.product.szmtr.msgboard.person.dto.PersonDto;
import com.mine.product.szmtr.msgboard.person.service.IPersonCursorService;
import com.mine.product.szmtr.msgboard.person.service.IPersonService;
import com.vgtech.platform.common.utility.VGUtility;


@Controller
@RequestMapping(value = "/admin/")
@SessionAttributes(value = {"loginUserDto"})
public class PersonAdminController {
	private static final Logger logger = LoggerFactory.getLogger(PersonAdminController.class);
	@Autowired
	private IPersonService personService;
	@Autowired
	private IMessageService messageService;
	@Autowired
    private IPersonCursorService psersonCursorService;
	@PostMapping(value="person/get_person_datagrid")
	@ResponseBody
	public Map<String, Object> getPersonForDatagrid(
			@RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "20") String rows,
            @RequestParam(required = false) String q){
		logger.info("Get Person For Datagrid where q = "+q);
		Map<String, Object> params = new HashMap<String, Object>();
		PageableDto pageable = new PageableDto(Integer.parseInt(page), Integer.parseInt(rows));
		String hql = "from Person p where 1 = 1";
		if(!VGUtility.isEmpty(q)) {
			hql += " and p.userName like :q";
			params.put("q", "%"+q+"%");
		}
		hql += " order by p.createTimestamp desc";
		PageDto<PersonDto> pageDto = personService.getPersons(hql, params, pageable);
		List<PersonDto> personDtoList = pageDto.getRowData();
		for(PersonDto personDto:personDtoList) {
		    List<PersonCursorDto> personCursorDtoList = psersonCursorService.findListByPersonId(personDto.getId());
		    if(!VGUtility.isEmpty(personCursorDtoList)) {
		        for(PersonCursorDto personCursorDto:personCursorDtoList) {
		            if(personCursorDto.getType()!=null&&personCursorDto.getType().equals("苏e行")) {
		                personDto.setSueXingId(personCursorDto.getCursorId());
		            }else if (personCursorDto.getType()==null) {
		                personDto.setWeixinId(personCursorDto.getCursorId());
		            }
		        }
		    }
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", pageDto.getTotalCount());
		result.put("rows", pageDto.getRowData());
		return result;
	}
	@PostMapping(value="person/resetpwd")
	@ResponseBody
	public ResponseEntity<String> resetPwd(@RequestParam String id, @RequestParam String pwd){
		logger.info("Reset Pwd Where Id = "+id+" And Pwd = "+pwd);
		personService.resetPwd(id, pwd);
		return new ResponseEntity<String>("{\"success\":true}", HttpStatus.OK);
	}
	
	@GetMapping(value="person/get_one_person")
	@ResponseBody
	public PersonDto getOnePerson(@RequestParam String id){
		logger.info("Get One Person Where Id ="+id);
		PersonDto personDto = personService.getOneById(id);
		String hql1 = "from Message m where 1=1 and m.msgType = 0 and m.personId =:userId";
		Map<String, Object> params1 = new HashMap<String, Object>();
		params1.put("userId", personDto.getId());
		PageDto<MessageDto> pageDto = messageService.getMessageByUserId(hql1, params1, null);
		personDto.setMessageNum(pageDto.getTotalCount());
		return personDto;
	}
}
