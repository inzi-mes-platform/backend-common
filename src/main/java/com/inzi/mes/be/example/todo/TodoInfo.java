package com.inzi.mes.be.example.todo;

import java.util.Date;

import com.inzi.mes.be.framework.AbstractInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TodoInfo extends AbstractInfo {
	
	private Long id;
	
	private String name;
	
	private String assignee;
	
	private Boolean cleared;
	
	private String description;
	
	private Date timestamp;

	@Override
	public String toPlainText() {
		StringBuilder sb=new StringBuilder();
		sb.append("ID="+this.getId()+"\n");
		sb.append("NAME="+this.getName()+"\n");
		sb.append("ASSIGNEE="+this.getAssignee()+"\n");
		sb.append("CLEARED="+this.getCleared()+"\n");
		sb.append("DESCRIPTION="+this.getDescription()+"\n");
		sb.append("TIMESTAMP="+sdf.format(this.getTimestamp())+"\n");
		sb.append("CREATED_BY="+this.getCreatedBy()+"\n");
		sb.append("CREATED_DATE="+this.getCreatedDateAsString()+"\n");
		sb.append("UPDATED_BY="+this.getUpdatedBy()+"\n");
		sb.append("UPDATED_DATE="+this.getUpdatedDateAsString());

		return sb.toString();
	}
}
