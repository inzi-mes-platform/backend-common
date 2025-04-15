package com.inzi.mes.be.example.todo.impl;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.inzi.mes.be.framework.Condition.CondType;
import com.inzi.mes.be.framework.persist.SearchSpec;

public class TodoSpecs extends SearchSpec<TodoEntity> {

	public Specification<TodoEntity> withName(CondType condType, String name) {
		return super.with("name", condType, name);
	}
	
	public Specification<TodoEntity> withNames(CondType condType, List<String> names) {
		return super.with("name", condType, names);
	}
	
	public Specification<TodoEntity> withAssignee(CondType condType, String asignee) {
		return super.with("assignee", condType, asignee);
	}
	
	public Specification<TodoEntity> withAssignees(CondType condType, List<String> asignees) {
		return super.with("assignee", condType, asignees);
	}
	
	public Specification<TodoEntity> withCleared(CondType condType, Boolean cleared) {
		return super.with("cleared", condType, cleared);
	}
	
	public Specification<TodoEntity> withDescription(CondType condType, String description) {
		return super.with("description", condType, description);
	}
	
	public Specification<TodoEntity> withTimestamp(CondType condType, String timestamp) {
		return super.with("timestamp", condType, timestamp);
	}

	public Specification<TodoEntity> withCreatedBy(CondType condType, String createdBy) {
		return super.with("createdBy", condType, createdBy);
	}
	
	public Specification<TodoEntity> withCreatedDate(CondType condType, String createdDate) {
		return super.with("createdDate", condType, createdDate);
	}
	
	public Specification<TodoEntity> withUpdatedBy(CondType condType, String updatedBy) {
		return super.with("updatedBy", condType, updatedBy);
	}
	
	public Specification<TodoEntity> withUpdatedDate(CondType condType, String updatedDate) {
		return super.with("updatedDate", condType, updatedDate);
	}
}
