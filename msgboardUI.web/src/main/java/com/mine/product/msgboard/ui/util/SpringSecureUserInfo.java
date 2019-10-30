package com.mine.product.msgboard.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mine.product.szmtr.msgboard.person.dto.PersonDto;

public class SpringSecureUserInfo implements UserDetails {
	private static final long serialVersionUID = -8637932892938363269L;
	
	private PersonDto userInfo;
	private List<SimpleGrantedAuthority> authList;
	
	public SpringSecureUserInfo(PersonDto userInfo, String ... auths) {
		this.userInfo = userInfo;
		authList = new ArrayList<SimpleGrantedAuthority>();
		for(String auth: auths)
			authList.add(new SimpleGrantedAuthority("ROLE_"+auth));
	}
	
	public void updateUserInfo(PersonDto userInfo) {
		this.userInfo = userInfo;
	}
	
	public void updateAuths(String ... auths) {
		authList = new ArrayList<SimpleGrantedAuthority>();
		for(String auth: auths)
			authList.add(new SimpleGrantedAuthority("ROLE_"+auth));
	}
	
	public PersonDto getUserInfo() {
		return userInfo;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authList;
	}

	@Override
	public String getPassword() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return encoder.encode(userInfo.getPassword());
	}

	@Override
	public String getUsername() {
		return userInfo.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
