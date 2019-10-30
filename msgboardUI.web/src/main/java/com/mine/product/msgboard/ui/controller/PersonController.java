package com.mine.product.msgboard.ui.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.mine.product.msgboard.ui.util.BASE64DecodedMultipartFile;
import com.mine.product.msgboard.ui.util.SpringSecureUserInfo;
import com.mine.product.szmtr.msgboard.person.dto.CursorDto;
import com.mine.product.szmtr.msgboard.person.dto.PersonCursorDto;
import com.mine.product.szmtr.msgboard.person.dto.PersonDto;
import com.mine.product.szmtr.msgboard.person.dto.UserLoginDto;
import com.mine.product.szmtr.msgboard.person.service.ICursorService;
import com.mine.product.szmtr.msgboard.person.service.IPersonCursorService;
import com.mine.product.szmtr.msgboard.person.service.IPersonService;
import com.vgtech.platform.common.utility.VGUtility;

import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping(value = "/person/")
public class PersonController {
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired
	private IPersonService personService;
	@Autowired
	private ICursorService cursorService;
	@Autowired
	private IPersonCursorService psersonCursorService;
	@Autowired
	protected AuthenticationManager authenticationManager;
	
	/**
	 * 
	* @author 何森
	* @date 2019年1月15日上午10:19:47
	* @Description: 注册成功后，自动登录主页       
	* @return String    
	*
	 */
	@PostMapping(value="person_register")
	@ResponseBody
	public ResponseEntity<String> registerPerson (@RequestParam String phone, 
								@RequestParam String pwd,
								@RequestParam String username, 
								@RequestParam String validateCode,
								@RequestParam String headPortraits,
								HttpServletRequest  request,
								HttpSession session,
								ModelMap modelMap){
		logger.info("Create New Person Where Phone = "+ phone +" And Pwd = "+ pwd /*+" And Validate = "+validate*/);
		String validate = (String)request.getSession().getAttribute("randomCode");
		if(!validateCode.toUpperCase().equals(validate.toUpperCase()))
			throw new RuntimeException("验证码错误!请重新输入!");
		if(username.trim() == "")
			throw new RuntimeException("请输入用户名!");
		if(phone.trim() == "")
			throw new RuntimeException("请输入手机号!");
		if(pwd.trim() == "")
			throw new RuntimeException("请输入密码!");
		if(validate.trim() == "")
			throw new RuntimeException("请输入验证码!");
		//注册成功时，获取注册用户IP地址并保存<this.getClientIp(request)>
		personService.createPerson(phone, pwd, username,this.getClientIp(request),headPortraits);
		//注册成功后绑定session
		UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(username,pwd);
		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser=authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
		//注册成功后，像cursor表中添加微信用户信息
		HttpSession wxsession = request.getSession();
		CursorDto cursorDto =(CursorDto)wxsession.getAttribute("CursorDto");
		if(cursorDto!=null) {
			cursorDto = cursorService.createCursor(cursorDto);
			//注册成功后，像personcursor表中添加关联id
			PersonDto p = ((SpringSecureUserInfo)authenticatedUser.getPrincipal()).getUserInfo();
			PersonCursorDto personCursorDto = new PersonCursorDto();
			personCursorDto.setCursorId(cursorDto.getId());	//微信Id
			personCursorDto.setPersonId(p.getId());			//PersonId
			psersonCursorService.createPsersonCursor(personCursorDto);
		}
		//删除session
		session.removeAttribute("CursorDto");
		if(!VGUtility.isEmpty(authenticatedUser)) {
			modelMap.addAttribute("person", ((SpringSecureUserInfo)authenticatedUser.getPrincipal()).getUserInfo());
		}
		return new ResponseEntity<String>("{\"success\":true}", HttpStatus.OK);
	}
	
	@PostMapping(value="go_to_personCenter")
	@ResponseBody
	public PersonDto goToPersonCenter(@RequestParam String phone, @RequestParam String validate) {
		logger.info("Forget Pwd And By Validate Login To Person Center");
		if(phone.trim() == "")
			throw new RuntimeException("请输入手机号!");
		if(validate.trim() == "") 
			throw new RuntimeException("请输入验证码!");
		return personService.forgetPwdLoginByValidate(phone,validate);
	}
	/**
	 * 
	* @author 李一豪
	* @date 2018年12月5日下午2:22:54
	* @Description:修改昵称      
	* @return PersonDto
	* 备注：该功能已不需要（更改：何森）
	 */
	@PostMapping(value="change_nickName")
	@ResponseBody
	public PersonDto changeNickName(@RequestParam String nickName, Authentication authentication) {
		logger.info("Change NickName Where UserId = "+((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId()+" And NickName = "+nickName);
		if(VGUtility.isEmpty(authentication)) throw new RuntimeException("请先登录！");
		PersonDto personDto = personService.changeNickName(((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId(), nickName);
		((SpringSecureUserInfo)authentication.getPrincipal()).updateUserInfo(personDto);
		return personDto;
	}
	/**
	 * 
	* @author 李一豪
	* @date 2018年12月5日下午2:41:39
	* @Description:修改手机号码     
	* @return PersonDto
	 */
	@PostMapping(value="change_phone")
	@ResponseBody
	public PersonDto changePhone(@RequestParam String phone, Authentication authentication) {
		logger.info("Change NickName Where UserId = "+((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId()+" And Phone = "+phone);
		if(VGUtility.isEmpty(authentication)) throw new RuntimeException("请先登录！");
		PersonDto personDto = personService.changePhone(((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId(), phone);
		((SpringSecureUserInfo)authentication.getPrincipal()).updateUserInfo(personDto);
		return personDto;
	}
	/**
	 * 
	* @author 李一豪
	* @date 2018年12月5日下午3:26:49
	* @Description:修改密码
	* @return PersonDto
	 */
	@PostMapping(value="change_pwd")
	@ResponseBody
	public PersonDto changePwd(@RequestParam String newPwd, @RequestParam String mutiNewPwd, Authentication authentication) {
		logger.info("Change NickName Where UserId = "+((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId()+"And newPwd = "+newPwd+" And mutiNewPwd = " +mutiNewPwd);
		if(!newPwd.equals(mutiNewPwd))
			throw new RuntimeException("两次密码输入不一致,请重新输入!");
		if(VGUtility.isEmpty(authentication)) throw new RuntimeException("请先登录！");
		PersonDto personDto = personService.changePwd(((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId(),newPwd);
		((SpringSecureUserInfo)authentication.getPrincipal()).updateUserInfo(personDto);
		return personDto;
	}
	/**
	 * 
	* @author 李一豪
	* @date 2018年12月5日下午4:01:46
	* @Description:修改性别        
	* @return PersonDto
	 */
	@PostMapping(value="change_sex")
	@ResponseBody
	public PersonDto changeSex(@RequestParam String sex, Authentication authentication) {
		logger.info("Change NickName Where UserId = "+((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId()+" And sex = "+sex);
		if(VGUtility.isEmpty(authentication)) throw new RuntimeException("请先登录！");
		PersonDto personDto = personService.changeSex(((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId(), sex);
		((SpringSecureUserInfo)authentication.getPrincipal()).updateUserInfo(personDto);
		return personDto;
	}
	
	/**
	 * 何森
	 * <一句话功能简述>修改用户名
	 * <功能详细描述>
	 * @param sex
	 * @param authentication
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
    @GetMapping(value="changeUserName")
    @ResponseBody
    public PersonDto changeUserName(@RequestParam String userName, Authentication authentication) {
        logger.info("Change UserName Where UserId = "+((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId()+" And userName = "+userName);
        if(VGUtility.isEmpty(authentication)) throw new RuntimeException("请先登录！");
        //修改用户名
        PersonDto personDto = personService.changeUserName(((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId(), userName);
        //修改昵称（该字段，个人中心已经不显示，但页面获取留言人、评论人时，还需要通过该字段去获取。故修改用户名同时修改昵称）
        personDto = personService.changeNickName(((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId(), userName);
        ((SpringSecureUserInfo)authentication.getPrincipal()).updateUserInfo(personDto);
//        //通过userId查询当前用户关联表信息
//        PersonCursorDto personCursorDto =psersonCursorService.findByPersonId(((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId());
//        //若关联表不为空并且关联类型为苏e行，用户状态为0，则修改用户状态为1
//        if(!VGUtility.isEmpty(personCursorDto)) {
//            if(("0").equals(personCursorDto.getUserNameState())&&("苏e行").equals(personCursorDto.getType())) {
//                personCursorDto.setUserNameState("1");
//                psersonCursorService.updatePersonCursor(personCursorDto);
//            }
//        }
      //通过用户id查询关联表是否为null(注：返回personCursorDtoList是因为一个账号可同时关联微信Id和苏e行Id,不止一条数据)
        List<PersonCursorDto> personCursorDtoList = psersonCursorService.findListByPersonId(((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId());
        if(!VGUtility.isEmpty(personCursorDtoList)) {
            for(PersonCursorDto personCursorDto:personCursorDtoList) {
                if(personCursorDto!=null&&personCursorDto.getUserNameState()!=null&&personCursorDto.getType()!=null) {
                    if(("0").equals(personCursorDto.getUserNameState())&&("苏e行").equals(personCursorDto.getType())) {
                        personCursorDto.setUserNameState("1");
                        psersonCursorService.updatePersonCursor(personCursorDto);
                    }
                }
            }
        }
        return personDto;
    }
	/**
	 * 
	* @author 何森
	* @date 2019年3月6日上午10:34:10
	* @Description:获取客户端用户IP地址        
	* @return String
	 */
	private String getClientIp(HttpServletRequest request) {
		 String ip = request.getHeader("x-forwarded-for"); 
	        System.out.println("x-forwarded-for ip: " + ip);
	        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {  
	            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
	            if( ip.indexOf(",")!=-1 ){
	                ip = ip.split(",")[0];
	            }
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("Proxy-Client-IP");  
	            System.out.println("Proxy-Client-IP ip: " + ip);
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("WL-Proxy-Client-IP");  
	            System.out.println("WL-Proxy-Client-IP ip: " + ip);
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("HTTP_CLIENT_IP");  
	            System.out.println("HTTP_CLIENT_IP ip: " + ip);
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("X-Real-IP");  
	            System.out.println("X-Real-IP ip: " + ip);
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getRemoteAddr();  
	            System.out.println("getRemoteAddr ip: " + ip);
	        } 
	        System.out.println("获取客户端ip: " + ip);
	        return ip;  
	}
	/**
	 * 更换头像
	 */
	@PostMapping(value="change_head")
	@ResponseBody
	public PersonDto changeHead(@RequestParam String headPortraits, Authentication authentication) {
		logger.info("Change Head-Image Where name = "+headPortraits);
		if(headPortraits == "" || VGUtility.isEmpty(headPortraits))
			throw new RuntimeException("请选择头像！");
		if(VGUtility.isEmpty(authentication)) throw new RuntimeException("请先登录！");
		PersonDto personDto = personService.changeHead(((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId(), headPortraits);
		((SpringSecureUserInfo)authentication.getPrincipal()).updateUserInfo(personDto);
		return personDto;
	}
	
	
	/**
     * 
    * @author 何森
    * @date 2019年3月20日上午10:10:10
    * @Description:上传自定义头像
    * @return PersonDto
	 * @throws IOException 
     */
//	@PostMapping(value="uploadHeadImage")
//    @ResponseBody
//	public PersonDto uploadHeadImage(@RequestParam String base64,
//	                                 @RequestParam String width,
//	                                 @RequestParam String height,
//	                                 @RequestParam String x,
//	                                 @RequestParam String y,
//	                                  Authentication authentication) throws IOException {
//        logger.info("upload Head-Image  base64 = "+base64+" width="+width+" height="+height+" x="+x+" y="+y);
//        if(base64 == "" || VGUtility.isEmpty(base64))
//            throw new RuntimeException("请选择头像！");
//        if(VGUtility.isEmpty(authentication)) throw new RuntimeException("请先登录！");
//        PersonDto personDto = personService.getOneById(((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId());
//        Map<String,String> error = new HashMap<String,String>();//错误
//        int _width = 0;//裁剪框的宽度
//        int _height=0;//裁剪框的高度 
//        int _x = 0;//相对于裁剪图片x左边  
//        int _y = 0;//相对于裁剪图片y左边 
//        //将头像宽高，x、y坐标轴进行转成Integer类型
//        if(width != null && !"".equals(width.trim())){
//                    String [] number = width.trim().split("\\.");
//                    String a =number[0];
//                    System.out.println("======================="+a);
//                    _width = Integer.parseInt(a);
//        }
//        if(height != null && !"".equals(height.trim())){
//                    String [] number = height.trim().split("\\.");
//                    _height = Integer.parseInt(number[0]);
//        }
//        
//        if(x != null && !"".equals(x.trim())){
//                    _x = (int)Double.parseDouble(x.trim());
//        }
//        if(y != null && !"".equals(y.trim())){
//                    _y = (int)Double.parseDouble(y.trim());
//        }
//     //   System.out.println(_width+_height+x+y);
//        //头像base64编码截取并解码
//        String[] data = base64.split(",");
//        //将base64码转为byte
//        byte[] decoderBytes = Base64.getDecoder().decode(data[1]);
//        MultipartFile imageFile = BASE64DecodedMultipartFile.base64ToMultipart(base64);
//        //将byte转为字节流
//        InputStream is = new ByteArrayInputStream(decoderBytes);
//        BufferedImage bufferImage = null;
//        try
//        {
//            bufferImage = ImageIO.read(is);
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        //获取图片的宽和高  
//        int srcWidth = bufferImage.getWidth();  
//        int srcHeight = bufferImage.getHeight();
//        //构建用户截取头像
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
//        BufferedImage newBufferImage = this.createImage(imageFile.getInputStream(), _x, _y, _width, _height, srcWidth, srcHeight);
//        try
//        {
//            ImageIO.write(newBufferImage, null, baos);
//            //转换成字节
//            byte[] bytes = baos.toByteArray();
//            //转码为base64，并修改用户上传头像
//            String newBase64=Base64.getEncoder().encodeToString(bytes);
//            personDto = personService.changeUpLoadHeadImage(((SpringSecureUserInfo)authentication.getPrincipal()).getUserInfo().getId(), newBase64);
//            ((SpringSecureUserInfo)authentication.getPrincipal()).updateUserInfo(personDto);
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        return personDto;
//    }
	
	/**
	 * 
	 * <一句话功能简述>构建用户截取头像
	 * @author 何森
	 * @param sourceInputStream
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param scaleWidth
	 * @param scaleHeight
	 */
//	private BufferedImage createImage(InputStream sourceInputStream,int x,int y,int width,int height,int scaleWidth,int scaleHeight) {
//	    BufferedImage bImage =null;
//        try{
//            bImage= Thumbnails.of(sourceInputStream)
//            .sourceRegion(x, y, width, height)//指定坐标(0, 0)和(400, 400)区域
//            .size(scaleWidth,scaleHeight)
//            .asBufferedImage();
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//        return bImage;
//	}

	@PostMapping("add_login_history")
	@ResponseBody
	public void addLoginHistory(@RequestParam String userName, @RequestParam String status, HttpServletRequest request) {
		String clientIp = this.getClientIp(request);
		personService.addLoginHistory(userName,	Integer.valueOf(status), clientIp);
	}
}
