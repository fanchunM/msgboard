/**
 * 
 */
package com.mine.product.msgboard.ui.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mine.product.szmtr.msgboard.person.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.vgtech.platform.common.utility.VGUtility;

/**
 * @author 李一豪
 *
 */
@Component(value="expaiSuccessHandler")
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private IPersonService personService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)throws IOException, ServletException {
		String userName = ((SpringSecureUserInfo) auth.getPrincipal()).getUserInfo().getUserName();
		personService.updateLoginHistoryIfUse(userName);
		String messageUrl = request.getParameter("messageUrl");
        if (!VGUtility.isEmpty(messageUrl)) {
        	request.getRequestDispatcher(messageUrl).forward(request, response);
        }else{
            super.onAuthenticationSuccess(request,response,auth);
        }
	}
}
