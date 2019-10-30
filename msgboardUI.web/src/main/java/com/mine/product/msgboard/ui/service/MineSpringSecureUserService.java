package com.mine.product.msgboard.ui.service;

import com.mine.product.msgboard.ui.util.SpringSecureUserInfo;
import com.mine.product.szmtr.msgboard.person.dto.PersonDto;
import com.mine.product.szmtr.msgboard.person.service.IPersonService;
import com.vgtech.platform.common.utility.VGUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service(value = "mineSpringSecureUserService")
public class MineSpringSecureUserService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(MineSpringSecureUserService.class);
	@Autowired
	private IPersonService personService;
	/**
	 * 获取系统中的用户放到框架中验证
	 */
	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        PersonDto userInfo = personService.getByUserName(s);
		if(VGUtility.isEmpty(userInfo)) {
			throw new InternalAuthenticationServiceException("用户名不存在");
		}
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new SpringSecureUserInfo(userInfo, "USER", "ADMIN");
//        return User.withUsername(userInfo.getUserName())
//        		.password(encoder.encode(userInfo.getPassword()))
//        		.roles("USER", "ADMIN")
//        		.build();
    }
}