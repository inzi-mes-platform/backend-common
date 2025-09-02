package com.inzi.mes.be.common.material.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.inzi.mes.be.framework.persist.Rangeable;
import com.inzi.mes.be.framework.persist.RangeableRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MaterialRangeableRepository 
	extends SimpleJpaRepository<MaterialEntity, String> 
	implements RangeableRepository<MaterialEntity, String> {

	@Autowired
	public MaterialRangeableRepository(EntityManager entityManager) {
		super(JpaEntityInformationSupport.getEntityInformation(MaterialEntity.class, entityManager), entityManager);
	}
	
	@Override
	public List<MaterialEntity> findAllByRange(Specification<MaterialEntity> spec, Rangeable rangeable) {
		log.debug("");
		TypedQuery<MaterialEntity> query = getQuery(spec, getDomainClass(), rangeable.getSort());

		query.setFirstResult(rangeable.getStart());
		query.setMaxResults(rangeable.getLimit());

		return query.getResultList();
	}
}
