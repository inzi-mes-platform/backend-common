package com.inzi.mes.be.common.material.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository 
	extends JpaRepository<MaterialEntity, String>, JpaSpecificationExecutor<MaterialEntity> {

}
