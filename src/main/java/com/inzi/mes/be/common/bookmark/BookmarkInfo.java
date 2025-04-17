package com.inzi.mes.be.common.bookmark;

import java.util.Map;

import com.inzi.mes.be.framework.AbstractInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BookmarkInfo extends AbstractInfo {
	
	private String userId;
	
	private String name;

	private String pathName;
	
	private Map<String, String> state;
	
	private String description;

	@Override
	public String toPlainText() {
		return null;
	}
}
