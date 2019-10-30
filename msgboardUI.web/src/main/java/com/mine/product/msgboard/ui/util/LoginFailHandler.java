package com.mine.product.msgboard.ui.util;

import com.vgtech.platform.common.utility.VGUtility;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @title: LoginFailHandler
 * @description:
 * @auther: yeaho_lee
 * @version: 1.0
 * @create 2019/9/11 17:01
 */
@Component(value="expaiFailHandler")
public class LoginFailHandler extends ExceptionMappingAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String message = exception.getMessage().trim();
        if (!"Bad credentials".equals(message.trim())) {
            if (VGUtility.isNumber(message)) {
                super.setDefaultFailureUrl("/login_page?error=" +message);
            } else {
                super.setDefaultFailureUrl("/login_page?error=userIsNotFound");
            }
        } else {
            super.setDefaultFailureUrl("/login_page?error");
        }
        response.setCharacterEncoding("UTF-8");
        super.onAuthenticationFailure(request, response, exception);
    }
}
