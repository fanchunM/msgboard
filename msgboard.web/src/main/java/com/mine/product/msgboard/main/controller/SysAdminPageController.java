package com.mine.product.msgboard.main.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mine.base.sc.user.dto.DictionaryCommonCodeDto;
import com.mine.base.sc.user.service.IDictionaryService;
import com.mine.base.sc.user.service.IPermissionService;
import com.vgtech.platform.common.web.dto.LoginUserDto;



@Controller
@SessionAttributes(value = {"loginUserDto"})
public class SysAdminPageController {
	private static final Logger logger = LoggerFactory.getLogger(SysAdminPageController.class);
	
	
	@Autowired
	private IDictionaryService dictService;
	@Autowired
    private IPermissionService permissionService;
	/**
	 * 
	* @author 何森
	* @date 2018年11月20日下午4:49:02
	* @Description: 权限管理页面     
	* @return String    
	 */
	@GetMapping("permissionManage")
	public String enterPermissionManagePage() {
		logger.info("Enter permissionManage Page");
		return "admin/permissionManage/permissionManage";
	}
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月22日下午3:27:54
	* @Description: 跳转添加权限管理组页面       
	* @return String    
	*
	 */
	@GetMapping("addPermissionGroup")
	public String addPermissionGroup() {
		logger.info("Enter addPermissionGroup Page");
		return "admin/permissionManage/addPermissionGroup";
	}
	
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月22日下午5:00:11
	* @Description: 跳转权限组用户页面       
	* @return String    
	*
	 */
	@GetMapping("permissionUser")
	public String permissionUser(ModelMap modelMap,String id) {
		logger.info("Enter permissionUser Page");
		modelMap.put("GroupUser",permissionService.getPermissonGroupById(id));
		return "admin/permissionManage/permissionUser";
	}
	/**
	 * 
	* @author 何森
	* @date 2018年11月21日上午10:34:03
	* @Description: 跳转快速回复页面       
	* @return String    
	*
	 */
	@GetMapping("replyManage")
	public String enterquickReply() {
		logger.info("Enter 	replyManage Page");
		return "admin/quicklyReply/quickReply";
	}
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月21日下午2:59:23
	* @Description: 跳转快速回复修改页面       
	* @return String    
	*
	 */
	@GetMapping("updatereply")
	public String toUpdateReplyPage(ModelMap modelMap,String id) {
		logger.info("Enter Update Reply Page");
		DictionaryCommonCodeDto dto = dictService.getCommonCodeById(id);
		modelMap.put("replyDto", dto);
		return "admin/quicklyReply/addReply";
	}
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月21日下午3:37:09
	* @Description: 跳转快速回复添加页面       
	* @return String    
	*
	 */
	@GetMapping("/addReply")
	public String toAddReplyPage() {
		logger.info("Enter Add Reply Page");
		return "admin/quicklyReply/addReply";
	}
	
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月27日下午6:37:51
	* @Description: 跳转至用户管理页面       
	* @return String    
	*
	 */
	@GetMapping("userManage")
	public String userManage() {
		logger.info("Enter userManage Page");
		return "admin/userManage/userManage";
	}
	
	/**
	 * 
	* @author 何森
	* @date 2018年12月22日上午10:36:56
	* @Description:跳转至同步主题管理页面        
	* @return String    
	*
	 */
	@GetMapping("synchronizationTheme")
	public String synchronizationTheme() {
		logger.info("Enter synchronizationTheme Page");
		return "admin/synchronizationTheme/synchronizationTheme";
	}
	
	/**
	 * 
	* @author 李一豪
	* @date 2019年1月8日下午4:20:01
	* @Description:获取当前权限组所有权限        
	* @return String    
	*
	 */
	@GetMapping("sys_get_user_permission")
	public String getUserPermission(@RequestParam String userId,
									@RequestParam String userName,
									ModelMap modelMap) {
		logger.info("Enter getUserPermission Page");
		modelMap.addAttribute("meunMap", permissionService.getMenuFilterByPermissionForUser("MSG_WEB",userId));
		modelMap.addAttribute("permissionMap", permissionService.getPermissionListFilterByUser("MSG_WEB",userId));
		modelMap.addAttribute("userId", userId);
		modelMap.addAttribute("userName", userName);
		modelMap.addAttribute("sysMark", "MSG_WEB");
		return "admin/permissionManage/addUserPermission";
	}
}

