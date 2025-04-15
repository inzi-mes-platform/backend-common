package com.inzi.mes.be.common.user;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.inzi.mes.be.framework.MesGeneralException;

public interface UserService {

	public abstract void recordUserHistory(UserHistoryInfo uhInfo);

	public abstract void requestUserRegistration(UserInfo uInfo) throws MesGeneralException;
	
	public abstract void approveUserRegistration(String userId) throws MesGeneralException;
	
	public abstract void releaseUserRegistration(String userId) throws MesGeneralException;
	
	public abstract void updateUser(UserInfo uInfo, String operation) throws MesGeneralException;

	public abstract UserInfo findUserById(String id) throws UsernameNotFoundException;
	
	public abstract UserInfo findUserByEmail(String email) throws UsernameNotFoundException;
	
	public abstract void deleteUser(String userId) throws MesGeneralException;
	
	public abstract boolean checkIfUserIdExist(String userId);
}
