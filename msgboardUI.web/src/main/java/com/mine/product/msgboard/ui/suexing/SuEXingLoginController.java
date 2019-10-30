package com.mine.product.msgboard.ui.suexing;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mine.product.msgboard.ui.controller.MessagePageController;
import com.mine.product.msgboard.ui.util.SpringSecureUserInfo;
import com.mine.product.szmtr.msgboard.person.dto.PersonCursorDto;
import com.mine.product.szmtr.msgboard.person.dto.PersonDto;
import com.mine.product.szmtr.msgboard.person.service.IPersonCursorService;
import com.mine.product.szmtr.msgboard.person.service.IPersonService;
import com.vgtech.platform.common.utility.VGUtility;
/**
 * 
 * <一句话功能简述>
 *  苏E行授权登录
 * @author  何森
 * @version  [V1.00, 2019年4月17日]
 * @see  [相关类/方法]
 * @since V1.00
 */
@Controller
@SessionAttributes(value = {"loginUserDto"})
public class SuEXingLoginController
{
    private static final Logger log = LoggerFactory.getLogger(SuEXingLoginController.class);
    @Autowired
    private IPersonService personService;
    @Autowired
    protected AuthenticationManager authenticationManager;
    @Autowired
    private IPersonCursorService psersonCursorService;
    
    /**
     * 
     * <一句话功能简述>苏E行访问留言板中转页面
     * <功能详细描述>通过接口获取苏e行用户数据
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/suexing")
    public String suEXingSkip() {
        log.info("------------进入苏E行访问留言板中转页面（用来获取苏e行用户信息）------------");
        return "suEXingSkip";
    }
    
    
    /**
     * 
     * <一句话功能简述>苏E行访问登录留言板，同时实现自动注册功能
     * <功能详细描述>参数cursorId，phone为苏e行提供数据，cursorId为用户标识，phone为用户手机号
     * @author  何森
     * @param returnUrl
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/suexingLogin")
    public String homePage(@RequestParam("userId")String cursorId,
                            @RequestParam("phone")String phone,
                            HttpServletRequest request,
                            ModelMap modelMap
                            ){
        log.info("苏e行用户 id="+cursorId+",phone="+phone );
        if(VGUtility.isEmpty(cursorId)||("undefined").equals(cursorId)) {
            throw new RuntimeException("用户未登录！"); 
        }
        //查询关联表是否为空
        PersonCursorDto personCursorDto = psersonCursorService.findByCursorId(cursorId);
        //personCursorDto不为空，自动登录留言板主页
        if(personCursorDto!=null) {
            PersonDto psersonDto = personService.getOneById(personCursorDto.getPersonId());
            String userName=psersonDto.getUserName();
            String password = psersonDto.getPassword();
            //苏e行老用户登录，修改手机号
            personService.changePhoneForSuEXing(phone, psersonDto.getId());
            //绑定用户session
            this.bindingSession(request,userName, password);
            //获取配置文件域名地址
//            Properties p = this.getUrl();
//            String mobileHomePage = p.getProperty("mobileHomePage").replaceAll("\\s*","");
           return this.enterMobileHomePage(modelMap, userName, request);
        //personCursorDto为空，注册并关联
        }else {
          //根据苏E行用户手机号查询
            PersonDto personDto = personService.getByPhone(phone);
            //手机号不存在
            if(VGUtility.isEmpty(personDto)) {
                PersonDto validateDto = new PersonDto();
                String userName = null;
                String password = null;
                do
                {
                   //生成随机用户名密码
                   userName = this.createRandomNumberAndEnglish();
                   password = this.createRandomNumberAndEnglish();
                   //验证随机生成的用户名是否存在
                   validateDto = personService.getByUserName(userName);
                } while (!VGUtility.isEmpty(validateDto));
                //生成随机头像
                String[] headImage = {"红帽子.jpg","蓝帽子.jpg","绿帽子.jpg","line1.jpg","line2.jpg","line3.jpg","line4.jpg"};
                int number = (int)((headImage.length)*Math.random());
                //创建用户
                personService.createPerson(phone, password, userName, this.getClientIp(request),headImage[number]);
                //绑定关联表
                PersonCursorDto newPersonCursorDto = new PersonCursorDto();
                newPersonCursorDto.setCursorId(cursorId);//苏E行用户标识
                newPersonCursorDto.setType("苏e行");
                newPersonCursorDto.setPhoneState("0");
                newPersonCursorDto.setUserNameState("0");
                newPersonCursorDto.setPassWordState("0");
                newPersonCursorDto.setNickNameState("0");
                newPersonCursorDto.setPersonId(personService.getByUserName(userName).getId());//PersonId
                psersonCursorService.createPsersonCursor(newPersonCursorDto);
                //绑定用户session
                this.bindingSession(request, userName, password);
                //获取配置文件域名地址
//                Properties p = this.getUrl();
//                String mobileHomePage = p.getProperty("mobileHomePage").replaceAll("\\s*","");
                return  this.enterMobileHomePage(modelMap, userName, request);
            //手机号存在，自动登录已绑定账号    
            }else {
                //绑定关联表
                PersonCursorDto newPersonCursorDto = new PersonCursorDto();
                newPersonCursorDto.setCursorId(cursorId);//苏E行用户标识
                newPersonCursorDto.setType("苏e行");
                newPersonCursorDto.setPhoneState("1");
                newPersonCursorDto.setNickNameState("1");
                newPersonCursorDto.setUserNameState("1");
                newPersonCursorDto.setPassWordState("1");
                newPersonCursorDto.setPersonId(personDto.getId());//PersonId
                psersonCursorService.createPsersonCursor(newPersonCursorDto);
                //绑定用户session
                this.bindingSession(request, personDto.getUserName(),personDto.getPassword());
                //获取配置文件域名地址
//                Properties p = this.getUrl();
//                String mobileHomePage = p.getProperty("mobileHomePage").replaceAll("\\s*",""); 
                return this.enterMobileHomePage(modelMap, personDto.getUserName(), request);
            }
        }
    }
    
    /**
     * 何森
     * <一句话功能简述>生成随机用户名，数字+字母组合，长度随机4-8
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String createRandomNumberAndEnglish() {
        String val = "SUE-";
        int n = (int)(Math.random()*4)+4;
        Random random = new Random();
        for ( int i = 0; i < n; i++ )
        {
            String str = random.nextInt( 2 ) % 2 == 0 ? "num" : "char";
            if ( "char".equalsIgnoreCase( str ) )
            { // 产生字母
                int nextInt = random.nextInt( 2 ) % 2 == 0 ? 65 : 97;
                val += (char) ( nextInt + random.nextInt( 26 ) );
            }
            else if ( "num".equalsIgnoreCase( str ) )
            { // 产生数字
                val += String.valueOf( random.nextInt( 10 ) );
            }
        }
        return val;
    }
    
    /**
     * 何森
     * <一句话功能简述>获取客户端用户IP地址
     * <功能详细描述>
     * @param request
     * @return
     * @see [类、类#方法、类#成员]
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
    
  //获取配置文件信息
    private Properties getUrl() {
        Resource res1 = new ClassPathResource("config/suexing.properties");
        Properties p = new Properties();
        try {
            p.load(res1.getInputStream());
            res1.getInputStream().close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return p;
    }
    
    /**
     * 何森
     * <一句话功能简述>绑定用户session
     * <功能详细描述>
     * @param request
     * @param userName
     * @param password
     * @see [类、类#方法、类#成员]
     */
    private void bindingSession(HttpServletRequest request,String userName,String password) {
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(userName,password);
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser=authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }
    
    /**
     * 
     * <一句话功能简述>自动登录到手机页面
     * <功能详细描述>备注，该功能修改时，ui.controller中MessagePageController的enterMobileHomePage方法注意下，根据业务需要是否需要修改
     * @param modelMap
     * @param authentication
     * @param request
     * @return
     * @see [类、类#方法、类#成员]
     */
   public String enterMobileHomePage(ModelMap modelMap,String userName,HttpServletRequest request) {
        log.info("Enter mobileHome Page");
        if(!VGUtility.isEmpty(userName)) {
            PersonDto newPersonDto = personService.getByUserName(userName);
            modelMap.addAttribute("person", newPersonDto);
            //更改用户登录IP地址
            personService.changeRecentLoginIP(newPersonDto.getId(),this.getClientIp(request));
            //通过用户id查询关联表是否为null(注：返回personCursorDtoList是因为一个账号可同时关联微信Id和苏e行Id,不止一条数据)
            List<PersonCursorDto> personCursorDtoList = psersonCursorService.findListByPersonId(newPersonDto.getId());
            if(!VGUtility.isEmpty(personCursorDtoList)) {
                for(PersonCursorDto personCursorDto:personCursorDtoList) {
                    if(personCursorDto!=null&&personCursorDto.getPhoneState()!=null&&personCursorDto.getType()!=null) {
                        //若关联类型是‘苏e行’并且手机号状态为1（状态1为手机号之前被注册过）
                        if(personCursorDto.getPhoneState().equals("1")&&personCursorDto.getType().equals("苏e行")) {
                            modelMap.addAttribute("phoneState",personCursorDto.getPhoneState());
                            personCursorDto.setPhoneState("0");
                            //提示后，将状态改为‘0’，下次登录不再提示
                            psersonCursorService.updatePersonCursor(personCursorDto);
                        }
                    }
                }
            }
        }
        modelMap.addAttribute("isMobileHome", true);
        return "message/mobileHome";
    }
}
