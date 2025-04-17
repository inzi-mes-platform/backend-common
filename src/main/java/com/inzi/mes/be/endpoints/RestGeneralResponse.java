package com.inzi.mes.be.endpoints;

import org.springframework.http.HttpStatus;

import io.micrometer.common.lang.Nullable;

public record RestGeneralResponse<T>(
		@Nullable
		HttpStatus httpStatus,
		boolean success,
		@Nullable
		T payload,
		@Nullable
		String failCode,
		@Nullable
		String failMessage
) {
	public static <T> RestGeneralResponse<T> ok() {
		return new RestGeneralResponse<>(HttpStatus.OK, true, null, null, null);
	}
	
	public static <T> RestGeneralResponse<T> ok(@Nullable final T data) {
		return new RestGeneralResponse<>(HttpStatus.OK, true, data, null, null);
	}
	
	public static <T> RestGeneralResponse<T> fail(final String failCode, final String failMessage) {
        return new RestGeneralResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, false, null, failCode, failMessage);
    }
	
	public static <T> RestGeneralResponse<T> fail(String failMessage) {
        return new RestGeneralResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, false, null, "000000", failMessage);
    }
	
	public static <T> RestGeneralResponse<T> fail(final HttpStatus httpStatus, final String failCode, final String failMessage) {
        return new RestGeneralResponse<>(httpStatus, false, null, failCode, failMessage);
    }
}
