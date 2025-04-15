package com.inzi.mes.be.example.todo.impl;

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

@Slf4j
@Repository
public class TodoRangeRepository extends SimpleJpaRepository<TodoEntity, Long> implements RangeableRepository<TodoEntity, Long> {
	
	@Autowired
	public TodoRangeRepository(EntityManager entityManager) {
		super(JpaEntityInformationSupport.getEntityInformation(TodoEntity.class, entityManager), entityManager);
	}

	@Override
	public List<TodoEntity> findAllByRange(Specification<TodoEntity> spec, Rangeable rangeable) {
		log.debug("");
		
		TypedQuery<TodoEntity> query = getQuery(spec, getDomainClass(), rangeable.getSort());

		query.setFirstResult(rangeable.getStart());
		query.setMaxResults(rangeable.getLimit());

		return query.getResultList();

	}
}
