package com.inzi.mes.be.common.auth.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.inzi.mes.be.common.auth.AuthService;
import com.inzi.mes.be.common.auth.ExpiredRefreshTokenException;
import com.inzi.mes.be.common.auth.InvalidTokenException;
import com.inzi.mes.be.common.auth.LoginRequest;
import com.inzi.mes.be.common.auth.NoMatchedRemoteAddressException;
import com.inzi.mes.be.common.auth.SecurityUser;
import com.inzi.mes.be.common.user.UserInfo;
import com.inzi.mes.be.common.user.UserService;
import com.inzi.mes.be.framework.MesGeneralException;
import com.inzi.mes.be.framework.security.JwtRefreshTokenEntity;
import com.inzi.mes.be.framework.security.JwtRefreshTokenRepository;
import com.inzi.mes.be.framework.security.JwTokenInfo;
import com.inzi.mes.be.framework.security.JwTokenProvider;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwTokenProvider jwTokenProvider;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtRefreshTokenRepository jwRefreshTokenRepository;

	@Override
	@Transactional
	public SecurityUser login(LoginRequest loginRequest) {
		log.info("");

		Authentication authentication = this.authenticate(loginRequest.getId(), loginRequest.getPassword());
		JwTokenInfo tokenInfo=this.jwTokenProvider.createTokens(authentication);
		JwtRefreshTokenEntity tokenEntity = new JwtRefreshTokenEntity();
		tokenEntity.setAccessToken(tokenInfo.getAccessToken());;
		tokenEntity.setUserId(loginRequest.getId());
		tokenEntity.setRefreshToken(tokenInfo.getRefreshToken());
		tokenEntity.setRemoteAddress(loginRequest.getRemoteAddress());
		tokenEntity.setTimeToLive(tokenInfo.getRefreshTokenExpiredTime().getTime());

		jwRefreshTokenRepository.save(tokenEntity);
		
		UserInfo uInfo = (UserInfo) authentication.getDetails();

		SecurityUser suser = new SecurityUser(uInfo);
		suser.setJwTokenInfo(tokenInfo);

		uInfo.setLogined(true);
		uInfo.setLoginedIp(loginRequest.getRemoteAddress());
		try {
			userService.updateUser(uInfo, "LOGIN");
		} catch (MesGeneralException e) {
			e.printStackTrace();
		}

		return suser;

	}

	@Override
	@Transactional
	public SecurityUser reissue(String remoteAddress, String accessToken, String refreshToken) {
		log.info("");
		try {
			boolean valid=jwTokenProvider.validateToken(accessToken, refreshToken, remoteAddress, true);
			if(!valid) {
				throw new InvalidTokenException("Invalid refresh token.");
			}
		} catch (ExpiredJwtException e) {
			log.info("", e);
			throw e;
		} catch (NoMatchedRemoteAddressException e) {
			log.info("");
			throw e;
		}

		// refresh token rotation 기법적용
		Optional<JwtRefreshTokenEntity> optional = jwRefreshTokenRepository.findById(accessToken);
		if (optional.isEmpty() || !refreshToken.equals(optional.get().getRefreshToken())) {
			throw new InvalidTokenException("Different refresh token.");
	    }

		JwtRefreshTokenEntity refreshTokenEntity = optional.get();

		if(new Date().getTime()>refreshTokenEntity.getTimeToLive()) {
			throw new ExpiredRefreshTokenException("Expired refresh token. New user login is required.");
		}

		// create new token (access / refresh)
		UserInfo uInfo = userService.findUserById(refreshTokenEntity.getUserId());
		UsernamePasswordAuthenticationToken authenticationToken = 
        		new UsernamePasswordAuthenticationToken(uInfo.getId(), uInfo.getPassword(), uInfo.getAuthorities());
		JwTokenInfo tokenInfo=this.jwTokenProvider.createTokens(authenticationToken);
		
		// delete existing legacy refresh token
		jwRefreshTokenRepository.delete(refreshTokenEntity);
		
		JwtRefreshTokenEntity newTokenEntity = new JwtRefreshTokenEntity();
		newTokenEntity.setAccessToken(tokenInfo.getAccessToken());
		newTokenEntity.setRefreshToken(tokenInfo.getRefreshToken());
		newTokenEntity.setUserId(uInfo.getId());
		newTokenEntity.setRemoteAddress(remoteAddress);
		newTokenEntity.setTimeToLive(tokenInfo.getRefreshTokenExpiredTime().getTime());

		// save new refresh token
		jwRefreshTokenRepository.save(newTokenEntity);

		SecurityUser suser = new SecurityUser(uInfo);
		suser.setJwTokenInfo(tokenInfo);

		return suser;
	}

	@Override
	@Transactional
	public void logout(String userId, String remoteAddress, String accessToken, String refreshToken) {
		log.info("");
		// check validation
		try {
			boolean valid=jwTokenProvider.validateToken(accessToken, refreshToken, remoteAddress, true);
			if(!valid) {
				throw new InvalidTokenException("Invalid refresh token.");
			}
		} catch (ExpiredRefreshTokenException t) {
			throw new InvalidTokenException("");
		}

		Optional<JwtRefreshTokenEntity> optional = jwRefreshTokenRepository.findById(accessToken);
		// delete refresh token information
		jwRefreshTokenRepository.deleteById(accessToken);
		if (optional.isEmpty() || !refreshToken.equals(optional.get().getRefreshToken())) {
			throw new InvalidTokenException("Different refresh token.");
		}

		// update user information
		try {
			UserInfo uInfo = userService.findUserById(userId);
			uInfo.setLogined(false);
			uInfo.setLoginedIp("");
			userService.updateUser(uInfo, "LOGOUT");
		} catch (MesGeneralException e) {
			e.printStackTrace();
		}
	}
	
	private Authentication authenticate(String userId, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = 
        		new UsernamePasswordAuthenticationToken(userId, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        log.info("authentication: {}", authentication);
        return authentication;
	}
}
