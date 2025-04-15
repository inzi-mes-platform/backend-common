package com.inzi.mes.be.common.user;

import java.util.Date;

import com.inzi.mes.be.framework.AbstractInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
public class UserHistoryInfo extends AbstractInfo {

	private Long seqNo;

	private String userId;

	private String oper;

	private Date timestamp;

	private String ipAddress;

	private String remark;
	
	public UserHistoryInfo() {
		super();
	}

	@Override
	public String toPlainText() {
		StringBuilder sb = new StringBuilder();
		return sb.toString();
	}
}
