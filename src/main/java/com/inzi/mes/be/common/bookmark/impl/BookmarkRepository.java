package com.inzi.mes.be.common.bookmark.impl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookmarkRepository 
	extends JpaRepository<BookmarkEntity, BookmarkEntityId>, JpaSpecificationExecutor<BookmarkEntity> {

	@Query("SELECT bookmark FROM BookmarkEntity AS bookmark where bookmark.entityId.userId = :userId")
	public List<BookmarkEntity> findByUserId(@Param("userId") String userId);
}
