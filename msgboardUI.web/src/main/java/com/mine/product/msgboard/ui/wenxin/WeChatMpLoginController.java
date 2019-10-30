package com.mine.product.msgboard.ui.wenxin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mine.product.szmtr.msgboard.person.dto.CursorDto;
import com.mine.product.szmtr.msgboard.person.dto.PersonCursorDto;
import com.mine.product.szmtr.msgboard.person.dto.PersonDto;
import com.mine.product.szmtr.msgboard.person.service.ICursorService;
import com.mine.product.szmtr.msgboard.person.service.IPersonCursorService;
import com.mine.product.szmtr.msgboard.person.service.IPersonService;
import com.vgtech.platform.common.utility.VGUtility;
import com.vgtech.platform.common.web.dto.LoginUserDto;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
 

/**
 * @description: 微信网页授权获取用户信息（微信公众号）
 * @author: 何森
 * @create: 2018-12-10 09:35
 **/
@Controller
@RequestMapping("/wechat")
@SessionAttributes(value = {"loginUserDto"})
public class WeChatMpLoginController {
		private static final Logger log = LoggerFactory.getLogger(WeChatMpLoginController.class);
	    @Autowired
	    private WxMpService wxMpService;
	    @Autowired
	    private IPersonService personService;
	    @Autowired
	    private ICursorService cursorService;
	    @Autowired
	    private IPersonCursorService psersonCursorService;
	    @Autowired
	    protected AuthenticationManager authenticationManager;
	    /**
	     * 获取code参数
	     * 何森
	     * @param returnUrl 需要跳转的url
	     * @return
	     * @throws UnsupportedEncodingException 
	     */
	    @GetMapping("/authorize")
	    public String authorize(@RequestParam("returnUrl") String returnUrl)  {
	    	Resource res1 = new ClassPathResource("config/weixin.properties");
			Properties p = new Properties();
			try {
				p.load(res1.getInputStream());
				res1.getInputStream().close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			//获取配置文件域名地址
			String realmName = p.getProperty("realmName").replaceAll("\\s*", "");
	        // 将回调地址硬编码在这里 
	        String url = realmName+"/wechat/userInfo";
	        // 获取微信返回的重定向url                
	        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url,WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
	        log.info("【微信网页授权】获取code，redirectUrl = {}", redirectUrl); 
	        return "redirect:"+redirectUrl;
	    }

	    /**
	     * 使用code参数换取access_token信息，并获取到openid
	     * 何森
	     * @param code
	     * @param returnUrl
	     * @return
	     */
	    @GetMapping("/userInfo")
	    public String userInfo(@RequestParam("code") String code,
	    		               @RequestParam("state") String returnUrl,
	    		               HttpServletRequest request) {
	    	 WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
	         try {
	             wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
	             //获取当前登录用户信息
	             WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
	             log.info("【微信网页授权】获取OpenId= {}",wxMpUser.getOpenId()); 
	             CursorDto dto = new CursorDto();
	             dto.setOpenId(wxMpUser.getOpenId());
	             dto.setHeadImgUrl(wxMpUser.getHeadImgUrl());
	             dto.setSex(wxMpUser.getSex());
	             dto.setUnionId(wxMpUser.getUnionId());
	             dto.setNickName(wxMpUser.getNickname());
	             //通过微信openId查询cursor表中是否存在用户信息
	             CursorDto cursorDto=cursorService.findUserByOpenId(wxMpUser.getOpenId());
	             //用户信息不存在跳转至登录页面
	             if(cursorDto==null) {
	            	//获取登录页面路径
	         		String registrationPage=this.getUrl().getProperty("weixinRegistrationPage").replaceAll("\\s*", "");
	         		log.info("【微信网页授权】Enter WeiXinRegistrationPage",registrationPage); 
	         		HttpSession session = request.getSession();
	         		session.setAttribute("CursorDto",dto);
	         		return "redirect:"+registrationPage;
	         	 //用户信息存在，用户自动登录
	             }else {
	            	 PersonCursorDto personCursorDto = psersonCursorService.findByCursorId(cursorDto.getId());
	            	 // 没有关联上用户
            		 PersonDto psersonDto = personService.getOneById(personCursorDto.getPersonId());
            		 String userName=psersonDto.getUserName();
            		 String password = psersonDto.getPassword();
            		 UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(userName,password);
            		 token.setDetails(new WebAuthenticationDetails(request));
            		 Authentication authenticatedUser=authenticationManager.authenticate(token);
            		 SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
            		 request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
	            	 String homePage = this.getUrl().getProperty("weixinHomePage").replaceAll("\\s*", "");
	            	 log.info("【微信网页授权】Enter WeiXinHomePage",homePage); 
	                 return "redirect:"+homePage;
	             }
	         } catch (WxErrorException e) {
	             e.printStackTrace();
	         }
	         
	        // 重定向到访问页面
  	        return "ok";
	    }
	    
	    
	    
	    
	    
	    //获取配置文件信息
	    private Properties getUrl() {
	    	Resource res1 = new ClassPathResource("config/weixin.properties");
     		Properties p = new Properties();
     		try {
     			p.load(res1.getInputStream());
     			res1.getInputStream().close();
     		} catch (IOException e1) {
     			e1.printStackTrace();
     		}
     		return p;
	    }
}
