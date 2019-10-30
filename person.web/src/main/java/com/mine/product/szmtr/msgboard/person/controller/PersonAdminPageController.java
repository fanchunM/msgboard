package com.mine.product.szmtr.msgboard.person.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PersonAdminPageController {
	private static final Logger logger = LoggerFactory.getLogger(PersonAdminPageController.class);
	
	@GetMapping("/person")
	public String enterPersonPage() {
		logger.info("Enter Person Page");
		return "admin/person/personManage";
	}
}
