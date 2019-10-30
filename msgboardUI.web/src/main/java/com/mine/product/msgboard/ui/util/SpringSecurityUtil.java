package com.mine.product.msgboard.ui.util;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.vgtech.platform.common.utility.VGUtility;

public class SpringSecurityUtil {
	public static String currentUser(HttpSession session) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
        //SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        if(VGUtility.isEmpty(securityContext)) return null; 
        try {
        	return ((UserDetails)securityContext.getAuthentication().getPrincipal()).getUsername();
		} catch (Exception e) {
			return null;
		}
    }
}
