package com.mine.product.msgboard.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mine.platform.common.dto.PageDto;
import com.mine.product.msgboard.ui.util.SpringSecureUserInfo;
import com.mine.product.szmtr.msgboard.notify.dto.BaseSysMsgDto;
import com.mine.product.szmtr.msgboard.notify.service.INotifyService;
import com.vgtech.platform.common.utility.VGUtility;

@Controller
public class NotifyController {
	
	private static final Logger logger = LoggerFactory.getLogger(NotifyController.class);
	@Autowired
	private INotifyService notifyService;
	
	@GetMapping(value = "notify/notifyList")
    @ResponseBody
    public Map<String, Object> notifyList(
    		Authentication authentication,
    		@RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "20") String rows,
            @RequestParam(required=false) String q) {
		String criteria = null;
		if(!VGUtility.isEmpty(authentication)) {
			criteria = "{\"receiveUserId\":\""+((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId()+"\"}";
		}
		PageDto<BaseSysMsgDto> pageDto = notifyService.getSysMsgListByUserIdForDataGrid(criteria, Integer.parseInt(page), Integer.parseInt(rows));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", pageDto.getRowData());
		result.put("total", pageDto.getTotalCount());
		result.put("pageNum", page);
        return result;
    }
	
	@PostMapping(value="notify/createNotify")
	@ResponseBody
	public ResponseEntity<String> createNotify(String sendUserId, String receiveUserId, String contentText, HttpServletRequest  request){
		logger.info("Create Notify Where Title ="+contentText);
		BaseSysMsgDto dto = new BaseSysMsgDto();
		dto.setContentText(contentText);
		dto.setReceiveUserId(receiveUserId);
		dto.setSendUserId(sendUserId);
		notifyService.createSysMsg(dto);
		return new ResponseEntity<String>("{\"success\":true}", HttpStatus.OK); 
	}
	
	@PostMapping(value="notify/updateNotify")
	@ResponseBody
	public ResponseEntity<String> updateNotify(@RequestParam String idString, @RequestParam int type){
		logger.info("Update Notify Where idString ="+idString+"type="+type);
		String[] split = idString.split(",");
		List<String> idList = new ArrayList<String>(Arrays.asList(split));
		notifyService.updateSysMsg(idList, type);
		return new ResponseEntity<String>("{\"success\":true}", HttpStatus.OK); 
	}
}
