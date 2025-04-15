package com.inzi.mes.be.common.auth;

import com.inzi.mes.be.common.user.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

	private Boolean result;
	
	private UserInfo userInfo;
}
