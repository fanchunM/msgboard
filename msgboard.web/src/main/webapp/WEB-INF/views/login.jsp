<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="EIAC.EAC.SSO.AppSSOBLL,EIAC.EAC.SSO.ReadConfig,java.util.ResourceBundle"%>
<%@page import="org.slf4j.Logger"%>
<%@page import="org.slf4j.LoggerFactory"%>

<html>
<body>
	<%
		Logger logger = LoggerFactory.getLogger("Login");
		String requestMethod = request.getMethod();
		requestMethod = requestMethod.toUpperCase();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		//发送到EAC验证
		String posturl = "";
		AppSSOBLL app = new AppSSOBLL();
		String IASID = "";
		String IASKey = "";
		String TimeStamp = "";
		String ReturnURL = "http://"+request.getServerName()+":8999"+request.getContextPath()+"/loginWithoutPsd";//request.getRequestURL().toString();//post 提交之后，认证系统返回的url
		String SSOURL = "";
		String HECURL = "";
		String UserAccount = "";
		String Result = "";
		String ErrorDescription = "";
		String Authenticator = "";
		if(request.getParameter("UserAccount") != null) UserAccount = request.getParameter("UserAccount");
		if(request.getParameter("Result") != null) Result = request.getParameter("Result");
		if(request.getParameter("ErrorDescription") != null) ErrorDescription = request.getParameter("ErrorDescription");
		if(request.getParameter("Authenticator") != null) Authenticator = request.getParameter("Authenticator");
		if(request.getParameter("TimeStamp") != null)TimeStamp = request.getParameter("TimeStamp");
		//取时间
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date currentTime = new java.util.Date();//得到当前系统时间
		String str_date1 = formatter.format(currentTime); //将日期时间格式化
		
		if(TimeStamp == "") TimeStamp = str_date1.toString(); 
		      
		//读配置文件
		
		EIAC.EAC.SSO.ReadConfig rd = new EIAC.EAC.SSO.ReadConfig();
		IASKey = rd.getString("IASKey").toString();
		IASID = rd.getString("IASID").toString();
		SSOURL = rd.getString("PostUrl").toString();

		//得到post的html 
		if(UserAccount==null||UserAccount=="") {         
			posturl = app.PostString1(IASID, TimeStamp, ReturnURL, null, SSOURL, IASKey); 			
			out.print(posturl);    //post to EAC(SSO)
		}
		//结束properties
         
		//接收从EAC返回的
		if(UserAccount != null && UserAccount != "") {
			if(!Result.equals("0")) {
				out.print("Result验证不成功！"); 
				out.print("IASID:"+IASID+", TimeStamp:"+TimeStamp+", UserAccount:"+UserAccount+", Result:"+Result+", ErrorDescription:"+ErrorDescription+", Authenticator:"+Authenticator);
				return;
			}
     
			if(app.ValidateFromEAC(IASID, TimeStamp, UserAccount, Result, ErrorDescription, Authenticator)) {
				//to the protected page
				//set cookies
				//Cookie nameCookie = new Cookie("userName", UserAccount);
				//response.addCookie(nameCookie);
				//Cookie webMemberCookie = new Cookie("webMember", "on");
				logger.info("验证成功"); 
				HttpSession httpsession = request.getSession();
				httpsession.setAttribute("userName", UserAccount);
				response.sendRedirect("http://"+request.getServerName()+":8999"+request.getContextPath()+"/loginWithoutPsd");
			} else {
				//to do 自身登陆
				logger.info("验证失败"); 
				response.sendRedirect("http://"+request.getServerName()+":8999"+request.getContextPath()+"/login_page");
			}
		}
         
		//接收从
    %>
    
	<script type="text/javascript">
	//注销的方法
		function SysLogOff() {
			var signOutUrl = '/'
			if (confirm("您确认要注销办公自动化系统吗？")) {
				try {
					document.execCommand("ClearAuthenticationCache");
				}
				catch (e) {
					//
				}
				finally {
					window.location.href = signOutUrl;
				}
			}
		}
	</script>
</body>
</html>
