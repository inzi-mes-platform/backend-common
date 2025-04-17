package com.inzi.mes.be.common.bookmark.impl;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.inzi.mes.be.framework.Condition.CondType;
import com.inzi.mes.be.framework.persist.SearchSpec;

public class BookmarkSpecs extends SearchSpec<BookmarkEntity> {

	public Specification<BookmarkEntity> withUserId(CondType condType, String userId) {
		return super.with("userId", condType, userId);
	}
	
	public Specification<BookmarkEntity> withUserIds(CondType condType, List<String> withUserIds) {
		return super.with("withUserId", condType, withUserIds);
	}
	
	public Specification<BookmarkEntity> withName(CondType condType, String name) {
		return super.with("name", condType, name);
	}
	
	public Specification<BookmarkEntity> withNames(CondType condType, List<String> names) {
		return super.with("name", condType, names);
	}
	
	public Specification<BookmarkEntity> withPathName(CondType condType, String pathName) {
		return super.with("pathName", condType, pathName);
	}
	
	public Specification<BookmarkEntity> withPathNames(CondType condType, List<String> pathNames) {
		return super.with("pathName", condType, pathNames);
	}
	
	public Specification<BookmarkEntity> withDescription(CondType condType, String description) {
		return super.with("description", condType, description);
	}

	public Specification<BookmarkEntity> withCreatedBy(CondType condType, String createdBy) {
		return super.with("createdBy", condType, createdBy);
	}
	
	public Specification<BookmarkEntity> withCreatedDate(CondType condType, String createdDate) {
		return super.with("createdDate", condType, createdDate);
	}
	
	public Specification<BookmarkEntity> withUpdatedBy(CondType condType, String updatedBy) {
		return super.with("updatedBy", condType, updatedBy);
	}
	
	public Specification<BookmarkEntity> withUpdatedDate(CondType condType, String updatedDate) {
		return super.with("updatedDate", condType, updatedDate);
	}
}
