package com.inzi.mes.be.common.auth;

import com.inzi.mes.be.framework.MesGeneralRuntimeException;

public class ExpiredRefreshTokenException extends MesGeneralRuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ExpiredRefreshTokenException() {
		super();
	}
	
	public ExpiredRefreshTokenException(String message) {
		super(message);
	}
}
