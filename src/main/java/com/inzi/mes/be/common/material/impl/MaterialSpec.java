package com.inzi.mes.be.common.material.impl;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.inzi.mes.be.framework.Condition.CondType;
import com.inzi.mes.be.framework.persist.SearchSpec;

public class MaterialSpec extends SearchSpec<MaterialEntity> {
	
	public Specification<MaterialEntity> withCode(CondType condType, String code) {
		return super.with("code", condType, code);
	}

	public Specification<MaterialEntity> withName(CondType condType, String name) {
		return super.with("name", condType, name);
	}

	public Specification<MaterialEntity> withCategory(CondType condType, String category) {
		return super.with("category", condType, category);
	}

	public Specification<MaterialEntity> withCategories(CondType condType, List<String> categories) {
		return super.with("category", condType, categories);
	}

	public Specification<MaterialEntity> withEnabled(CondType condType, Boolean enabled) {
		return super.with("enabled", condType, enabled);
	}
}
