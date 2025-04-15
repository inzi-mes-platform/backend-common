package com.inzi.mes.be.common.auth;

import com.inzi.mes.be.framework.MesGeneralRuntimeException;

public class InvalidTokenException extends MesGeneralRuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidTokenException() {
		super();
	}
	
	public InvalidTokenException(String message) {
		super(message);
	}
}
