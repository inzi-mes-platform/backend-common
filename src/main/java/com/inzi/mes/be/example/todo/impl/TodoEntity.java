package com.inzi.mes.be.example.todo.impl;

import java.util.Date;

import com.inzi.mes.be.example.todo.TodoInfo;
import com.inzi.mes.be.framework.persist.PersistentEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@Builder
@Entity
@Table(name="TODO")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@Slf4j
public class TodoEntity extends PersistentEntity<TodoInfo> {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="NAME", unique=true, nullable=false)
	private String name;
	
	@Column(name="ASSIGNEE")
	private String assignee;
	
	@Column(name="CLEARED")
	private Boolean cleared;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="TIMESTAMP")
	private Date timestamp;
	
	public TodoEntity() {
		super();
	}
	
	@Override
	public TodoInfo info() {
		TodoInfo info=new TodoInfo();
		info.setId(id);
		info.setName(name);
		info.setAssignee(assignee);
		info.setCleared(cleared);
		info.setDescription(description);
		info.setTimestamp(timestamp);
		
		info.setCreatedBy("SYSTEM");
		info.setCreatedDate(new Date());
		info.setUpdatedBy("SYSTEM");
		info.setUpdatedDate(new Date());
		return info;
	}

	@Override
	public void from(TodoInfo info) {
		this.setId(info.getId());
		this.setName(info.getName());
		this.setAssignee(info.getAssignee());
		this.setCleared(info.getCleared());
		this.setDescription(info.getDescription());
		this.setTimestamp(info.getTimestamp());
		
		this.setCreatedBy(info.getCreatedBy());
		this.setCreatedDate(info.getCreatedDate());
		this.setUpdatedBy(info.getUpdatedBy());
		this.setUpdatedDate(info.getUpdatedDate());
	}

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
