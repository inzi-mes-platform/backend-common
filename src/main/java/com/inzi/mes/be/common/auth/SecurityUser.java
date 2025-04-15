package com.inzi.mes.be.common.auth;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.inzi.mes.be.common.user.UserInfo;
import com.inzi.mes.be.framework.security.JwTokenInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SecurityUser extends User {

	private static final long serialVersionUID = 1L;
	
	private JwTokenInfo jwTokenInfo;
	
	private UserInfo userInfo;
	
	public SecurityUser(UserInfo userInfo) {
		super(
			userInfo.getId(), 
			"{noop}"+userInfo.getPassword(),
			AuthorityUtils.createAuthorityList(userInfo.getUserRoles().get(0).toString())
		);
		this.userInfo=userInfo;
	}
}
