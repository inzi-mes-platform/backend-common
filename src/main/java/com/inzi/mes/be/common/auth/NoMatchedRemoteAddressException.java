package com.inzi.mes.be.common.auth;

import com.inzi.mes.be.framework.MesGeneralRuntimeException;

public class NoMatchedRemoteAddressException extends MesGeneralRuntimeException {

	private static final long serialVersionUID = 1L;

	public NoMatchedRemoteAddressException() {
		super();
	}
	
	public NoMatchedRemoteAddressException(String message) {
		super(message);
	}
}
