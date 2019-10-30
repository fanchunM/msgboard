package com.mine.product.msgboard.ui.service;

import com.mine.product.msgboard.ui.util.IPUtils;
import com.mine.product.szmtr.msgboard.person.dto.PersonDto;
import com.mine.product.szmtr.msgboard.person.service.IPersonService;
import com.vgtech.platform.common.utility.VGUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;

public class LoginUserDetailsServiceImpl implements LoginUserDetailsService {

    @Autowired
    private IPersonService personService;

    /**
     * 进行登录验证
     */
    @Override
    public UserDetails loadUserByUsername(String username, String password) throws UsernameNotFoundException {
        boolean result = this.validate(username, password);
        if (!result) {
            return null;
        }
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        LoginUserDetailsImpl user = new LoginUserDetailsImpl(username, password, authorities);
        return user;
    }

    /**
     * 在此处验证
     * @param username
     * @param password
     * @return
     */
    private boolean validate(String username, String password) {
        PersonDto person = personService.getByUserName(username);
        if (!VGUtility.isEmpty(person)) {
            if (person.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

}
