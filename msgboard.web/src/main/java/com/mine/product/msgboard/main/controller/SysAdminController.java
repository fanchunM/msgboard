package com.mine.product.msgboard.main.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mine.base.sc.user.dto.DictionaryCommonCodeDto;
import com.mine.base.sc.user.dto.PermissionGroupDto;
import com.mine.base.sc.user.dto.PermissionItemDto;
import com.mine.base.sc.user.dto.UserDto;
import com.mine.base.sc.user.service.IDictionaryService;
import com.mine.base.sc.user.service.IPermissionService;
import com.mine.base.sc.user.service.IUserService;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.message.dto.SysDictionaryDto;
import com.mine.product.szmtr.msgboard.message.service.IsysDictionaryService;
import com.mine.product.szmtr.msgboard.person.dto.UserManageDto;
import com.mine.product.szmtr.msgboard.person.service.IUserManageService;
import com.vgtech.platform.common.utility.VGUtility;
import com.vgtech.platform.common.web.dto.CommonComboDto;
import com.vgtech.platform.common.web.dto.PageDto;




@Controller
public class SysAdminController {
	private static final Logger logger = LoggerFactory.getLogger(SysAdminController.class);
	
	
	@Autowired
	private IDictionaryService dictService;
	@Autowired
    private IPermissionService permissionService;
	@Autowired
    private IUserService userService;
	@Autowired
	private IUserManageService userManageService;
	@Autowired
	private IsysDictionaryService sysDictionaryService;
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月21日上午9:51:21
	* @Description:查询权限管理組     
	* @return Map<String,Object>    
	*
	 */
	@PostMapping(value = "sys/permissionList")
    @ResponseBody
    public List<Map<String, String>> permissionList(
    		@RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "20") String rows)
    {
		return permissionService.getAllPertmissionGroupDatagrid("MSG_WEB", 0, 0).getRows();
    }
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月26日上午9:37:56
	* @Description:通过权限组id获取所有用户        
	* @return List<Map<String,String>>    
	*
	 */
	@PostMapping(value = "sys/getUsersByGroupId")
    @ResponseBody
    public List<UserDto> getUsersByGroupId(@RequestParam String id )
    {	
		List<Map<String, String>> usersList = permissionService.getAllGroupUserDatagrid(id);
		List<UserDto> newList = new ArrayList<UserDto>();
		for(Map user:usersList) {
			String userName=(String) user.get("userName");
			UserDto userDto = userService.getUserByUserName(userName);
			newList.add(userDto);
		}
		return newList;
    }
	
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月26日上午11:20:42
	* @Description:向权限组添加用户        
	* @return String    
	*
	 */
	@PostMapping(value = "sys/addNewUser")
    @ResponseBody
    public String  addNewUser(@RequestParam String id,
    						@RequestParam String userId)
    {
		permissionService.addGroupUser(id,userId);
		return "ok";
    }
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月26日上午10:57:51
	* @Description: 通过权限组id和用户id删除当前用户      
	* @return String    
	*未完成
	 */
	@PostMapping(value = "sys/deleteUsersByGroupId")
    @ResponseBody
    public String deleteUsersByGroupId(@RequestParam String id,
    									@RequestParam String userId)
    {
		 permissionService.deleteGroupUserById(id,userId);
		 return "ok";
    }
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月22日下午4:12:01
	* @Description:添加权限管理组        
	* @return String    
	*
	 */
	@PostMapping(value = "sys/savePermissionGroup")
    @ResponseBody
    public String updatePermissionGroup(@RequestParam(required=true) String group)
    {
			PermissionGroupDto pd = new PermissionGroupDto();
			pd.setName(group);
			pd.setSysMark("MSG_WEB");
			permissionService.addPermisssionGroup(pd);
			return "ok";
		
		
    }
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月22日下午4:05:03
	* @Description: 删除权限管理组       
	* @return String    
	*
	 */
	@PostMapping(value = "sys/deletePermissionGroup")
    @ResponseBody
	 public String deletePermissionGroup(@RequestParam String id)
	    {
			permissionService.deletePermissionGroupById(id);
			return "ok";
	    }
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月22日下午5:17:18
	* @Description:查询权限组所有用户        
	* @return String    
	*
	 */
	@GetMapping(value = "sys/getPermissionUsers")
	@ResponseBody
    public List<CommonComboDto> getPermissionUsers(@RequestParam(defaultValue = "")String q) {
	logger.info("Get PermissionUsers", q);
	PageDto<UserDto> uList = userService.getUserByNameLike("{$or:[{'userName':{$regex:'" + q + "'}}, {'chsName': {$regex: '" + q + "'}}]}", 1, 20);
	List<CommonComboDto> resultList = new ArrayList<CommonComboDto>();
	for (UserDto userDto : uList.getRows()) {
	    CommonComboDto combo = new CommonComboDto();
	    combo.setId(userDto.getId());
	    combo.setValue(userDto.getId());
	    combo.setText(userDto.getUserName()+"（"+userDto.getChsName()+"）");
	    resultList.add(combo);
	}
	return resultList;
    }
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月21日上午11:20:09
	* @Description: 快速回复查询
	* @return List<CommonComboDto>    
	*
	 */
	@PostMapping(value = "sys/quickReplyList")
	@ResponseBody
	public List<CommonComboDto> quickReplyForCombo() {
		 List<DictionaryCommonCodeDto> dictDtoList = dictService.getCommonCodeByType("MSG-REPLY");
		 List<CommonComboDto> resultList = convertDict(dictDtoList);
		 return resultList;
	}
	
	private List<CommonComboDto> convertDict(List<DictionaryCommonCodeDto> dictDtoList) {
	     List<CommonComboDto> resultList = new ArrayList<CommonComboDto>();
	     for (DictionaryCommonCodeDto tempDto : dictDtoList) {
	         CommonComboDto dto = new CommonComboDto();
	         dto.setId(tempDto.getId());
	         dto.setText(tempDto.getChsName());
	         dto.setValue(tempDto.getId());
	         dto.setValue1(tempDto.getEngName());
	         resultList.add(dto);
	     }
	     	return resultList;
	 }
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月21日上午10:37:45
	* @Description: 保存快速回复内容     
	* @return void    
	*
	 */
	@PostMapping(value = "sys/saveQuickReply")
	@ResponseBody
    public String saveQuickReply(@RequestParam(required=false)String fId,
    								@RequestParam(required=true)String engName,
    								@RequestParam(required=true)String chsName)
    {
		logger.info("Save or Update quickReply");
		// 获取快速回复
		if(!VGUtility.isEmpty(fId)) {
			// 更新
			DictionaryCommonCodeDto commonCodeById = dictService.getCommonCodeById(fId);
			logger.info("Update quickReply");
			commonCodeById.setChsName(chsName);
			commonCodeById.setEngName(engName);
			dictService.updateCommonCode(commonCodeById);
		
		} else{
			logger.info("Save quickReply");
			// 新增
			DictionaryCommonCodeDto commonCodeById = new DictionaryCommonCodeDto();
			List<CommonComboDto> dictTypeDtoList = dictService.getCommonCodeTypeForCombobox("MSG-REPLY");
			if(null == dictTypeDtoList || dictTypeDtoList.size() != 1) {
				throw new RuntimeException("快速回复类型[MSG-REPLY]没有创建，请联系管理员！");
			}
			CommonComboDto commonComboDto = dictTypeDtoList.get(0);
			String typeId = commonComboDto.getValue();
			String typeName = commonComboDto.getText();
			commonCodeById.setTypeId(typeId);
			commonCodeById.setTypeName(typeName);
			commonCodeById.setChsName(chsName);
			commonCodeById.setEngName(engName);
			commonCodeById.setCode(" ");
			dictService.addCommonCode(commonCodeById);
		}
			return "ok";
    }
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月21日下午2:20:29
	* @Description:  删除快速回复      
	* @return void    
	*
	 */
	@PostMapping(value = "sys/deleteQuickReply")
	@ResponseBody
    public String deleteQuickReply(@RequestParam String id){
		logger.info("delete QuickReply By Id id="+id);
		dictService.deleteCommonCode(id);
		return "ok";
    }
	
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月28日下午4:13:51
	* @Description: 查询所有用户<用户管理页面>       
	* @return List<UserManageDto>    
	*
	 */
	@PostMapping(value = "sys/getUserManageList")
	@ResponseBody
    public Map<String, Object> getUserManageList(@RequestParam(defaultValue = "")String q,
    		@RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "20") String rows) {
	logger.info("Get getUserManageList", q);
	PageDto<UserDto> uList = userService.getUserByNameLike("{$or:[{'userName':{$regex:'" + q + "'}}, {'chsName': {$regex: '" + q + "'}}]}", VGUtility.toInteger(page), VGUtility.toInteger(rows));
	List<UserManageDto> resultList = new ArrayList<UserManageDto>();
	for (UserDto userDto : uList.getRows()) {
		//根据用户id去查询用户管理表中是否有这个对象 
		UserManageDto umd = userManageService.findUserByUserId(userDto.getId());
		//将所有结果set进新的UserManageDto中，用于页面展示
		UserManageDto users = new UserManageDto();
		//如果为空，则为启用。反之
		if(null==umd){
			users.setuId(userDto.getId());
			users.setUserName(userDto.getUserName());
			users.setChsName(userDto.getChsName());
			users.setState("已启用");
		}else{
			users.setuId(userDto.getId());
			users.setUserName(userDto.getUserName());
			users.setChsName(userDto.getChsName());
			users.setState("已禁用");
		}
		resultList.add(users);
	}
	Map<String, Object> result = new HashMap<String, Object>();
	result.put("rows", resultList);
	result.put("total", uList.getTotal());
	return result;
    }
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月28日下午5:24:42
	* @Description: 禁用用户<用户管理>       
	* @return String    
	*
	 */
	@PostMapping(value = "sys/prohibitUser")
	@ResponseBody
    public String prohibitUser(@RequestParam String userId){
		logger.info("delete prohibitUser By userId="+userId);
		//每开启一个用户，先查询userManage中是否存在当前数据，若为空，添加。反之抛异常
		UserManageDto user=userManageService.findUserByUserId(userId);
		if(null==user) {
			userManageService.addUserByUserId(userId);
		}else {
			throw new RuntimeException("用户已禁用，请重新操作！");
		}
		return "ok";
    }
	
	
	/**
	 * 
	* @author 何森
	* @date 2018年11月29日上午10:31:17
	* @Description:   启用用户<用户管理>       
	* @return String    
	*
	 */
	@PostMapping(value = "sys/startUser")
	@ResponseBody
	public String startUser(@RequestParam String userId){
		logger.info("delete startUser By Id id="+userId);
		//每禁用一个用户，先查询userManage中是否存在当前数据，若为空，删除。反之抛异常
		UserManageDto user=userManageService.findUserByUserId(userId);
		if(null!=user) {
			userManageService.deleteUserByUserId(userId);
		}else {
			throw new RuntimeException("用户已启用，请重新操作！");
		}
		return "ok";
    }
	@GetMapping(value="sys/get_user_permission")
	@ResponseBody
	public List<PermissionItemDto> getUserPermission(){
		
		return null;
	}
	
	@PostMapping("sys/update_user_permision_value")
    @ResponseBody
    public ResponseEntity<String> updateUserPermissionValue(@RequestParam String userId,
	    @RequestParam String sysMark,
	    @RequestParam String menuPermissionValues,
	    @RequestParam String itemPermissionValues) {
	logger.info("Update User Who id[{}]  Permission Info MenuPermissionValues[{}] ItemPermissionValues[{}]", userId, menuPermissionValues, itemPermissionValues);
	permissionService.updatePermission(userId, 1, sysMark, menuPermissionValues);
	permissionService.updatePermission(userId, 2, sysMark, itemPermissionValues);
	return new ResponseEntity<String>("{\"success\":true}", HttpStatus.OK);
    }
	
	
	@GetMapping(value="sys/getFatherTheme")
	@ResponseBody
	public List<CommonComboDto> getFatherTheme(){
		logger.info("Enter Here!");
		return sysDictionaryService.getFatherTheme();
	}
	/**
	* @author 李一豪
	* @date 2019年2月20日上午10:07:00
	* @Description:新增主题   
	* @return List<CommonComboDto>
	 */
	@PostMapping(value="sys/addTheme")
	@ResponseBody
	public ResponseEntity<String> addTheme(@RequestBody SysDictionaryDto dto){
		sysDictionaryService.addTheme(dto);
		return new ResponseEntity<String>("{\"success\":true}", HttpStatus.OK);
	}
	
	
	/**
	 * 
	* @author 李一豪
	* @date 2019年2月21日上午11:05:52
	* @Description:根绝父级主题获取子类主题        
	* @return ResponseEntity<String>
	 */
	@PostMapping(value="sys/get_son_datagrid_byfather")
	@ResponseBody
//	public List<SysDictionaryDto> getSonDatagridByFatherId(@RequestParam String fatherId){
//		return sysDictionaryService.getThemeByParentThemeId(fatherId);
//	}
	public Map<String, Object> findAllSynchronizationTheme(@RequestParam String fatherId) {
		String hql ="from DictionaryCommonCode d where 1=1 and d.parentThemeId is not null"+(VGUtility.isEmpty(fatherId)?"":" and d.parentThemeId =:fatherId")+" order by d.serialNumber";
		Map<String,Object> param = new HashMap<String,Object>();
	    param.put("fatherId",fatherId);
		com.mine.platform.common.dto.PageDto<SysDictionaryDto> sdd = sysDictionaryService.getDictionaryCommonCode(hql,param,null);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", sdd.getTotalCount());
		result.put("rows", sdd.getRowData());
		return result;
	}
	
	@PostMapping(value="sys/delete_theme")
	@ResponseBody
	public String deleteTheme(@RequestParam String id){
		sysDictionaryService.deleteTheme(id);
		return "ok";
	}
	
	@PostMapping(value="sys/update_theme")
	@ResponseBody
	public ResponseEntity<String> updateTheme(@RequestBody SysDictionaryDto dto){
		logger.info("Update DictionaryCommonCode Where id = "+dto.getId());
		sysDictionaryService.updateTheme(dto);
		return new ResponseEntity<String>("{\"success\":true}", HttpStatus.OK);
	}
	
}

