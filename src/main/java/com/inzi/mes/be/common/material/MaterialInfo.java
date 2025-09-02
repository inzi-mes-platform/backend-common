package com.inzi.mes.be.common.material;

import com.inzi.mes.be.framework.AbstractInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MaterialInfo extends AbstractInfo {
	
	private String code;
	
	private String category;
	
	private String name;
	
	private String specJson;
	
	private Float currentStock;
	
	private Float safetyStock;
	
	private String unit;
	
	private Float unitPrice;
	
	private Boolean enabled;
	
	private String description;
	
	@Override
	public String toPlainText() {
		return null;
	}

}
