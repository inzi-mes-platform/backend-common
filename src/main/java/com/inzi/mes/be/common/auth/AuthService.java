package com.inzi.mes.be.common.auth;

public interface AuthService {

	public SecurityUser login(LoginRequest loginRequest);
	
	public SecurityUser reissue(String remoteAddress, String refreshToken);
	
	public void logout(String userId, String remoteAddress, String accessToken);
}
