package com.inzi.mes.be.common.bookmark.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.inzi.mes.be.common.bookmark.BookmarkInfo;
import com.inzi.mes.be.common.bookmark.BookmarkService;
import com.inzi.mes.be.framework.Condition;
import com.inzi.mes.be.framework.Condition.CondType;
import com.inzi.mes.be.framework.Condition.ConjType;
import com.inzi.mes.be.framework.Condition.PageInfo;
import com.inzi.mes.be.framework.Condition.SortDirection;
import com.inzi.mes.be.framework.MesGeneralException;
import com.inzi.mes.be.framework.persist.PageResponse;
import com.inzi.mes.be.framework.persist.PersistentService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookmarkServiceImpl implements BookmarkService, PersistentService<BookmarkInfo, BookmarkEntity> {
	
	@Autowired
	private BookmarkRepository bookmarkRepository;

	@Override
	@Transactional
	public void addBookmark(BookmarkInfo bookmarkInfo) throws MesGeneralException {
		log.debug("");
		BookmarkEntity entity=new BookmarkEntity();
		entity.from(bookmarkInfo);
		bookmarkRepository.save(entity);
	}

	@Override
	@Transactional
	public void updateBookmark(BookmarkInfo bookmark) throws MesGeneralException {
		log.debug("");
		Optional<BookmarkEntity> option=
				bookmarkRepository.findById(new BookmarkEntityId(bookmark.getUserId(), bookmark.getName()));
		if(!option.isEmpty()) {
			BookmarkEntity entity=option.get();
			entity.from(bookmark);
			bookmarkRepository.save(entity);
		}
	}

	@Override
	@Transactional
	public void deleteBookmark(String userId, String name) throws MesGeneralException {
		log.debug("");
		bookmarkRepository.deleteById(new BookmarkEntityId(userId, name));
	}
	
	@Override
	@Transactional
	public void deleteBookmark(String userId, List<String> names) throws MesGeneralException {
		log.debug("");
		BookmarkSpecs bookmarkSpecs=new BookmarkSpecs();
		Condition cond = new Condition();
		cond.addCondition("userId", CondType.EQUALS, null, userId);
		cond.addCondition("name", CondType.EQUALS, ConjType.AND, cond);
		Specification<BookmarkEntity> specification=bookmarkSpecs.createSpecification(cond);
		bookmarkRepository.delete(specification);
	}
	
	private Page<BookmarkEntity> findToPage(Specification<BookmarkEntity> specification, PageInfo pageInfo) {
		log.debug("");
		if(pageInfo==null) return null;
		
		Sort.Direction dir=pageInfo.getSortDirection()==SortDirection.ASCENDING?Sort.Direction.ASC:Sort.Direction.DESC;
		PageRequest prequest=PageRequest.of(pageInfo.getPage(), pageInfo.getSize(), dir, pageInfo.getSortBy());
		return bookmarkRepository.findAll(specification, prequest);
	}

	@Override
	@Transactional
	public PageResponse<BookmarkInfo> searchBookmark(Condition condition) throws MesGeneralException {
		log.debug("");
		BookmarkSpecs bookmarkSpecs=new BookmarkSpecs();
		List<BookmarkEntity> bookmarkEntityList=null;
		Specification<BookmarkEntity> specification=bookmarkSpecs.createSpecification(condition);
		
		PageResponse<BookmarkInfo> rpage=new PageResponse<>();
		if(condition.getPageInfo()==null) {
			bookmarkEntityList=bookmarkRepository.findAll(specification);
			rpage.setTotalPages(bookmarkEntityList.size());
			rpage.setPageInfo(null);
		} else {
			PageInfo pInfo=condition.getPageInfo();
			Page<BookmarkEntity> page=findToPage(specification, pInfo);
			bookmarkEntityList=page.getContent();
			rpage.setTotalRowsCount(page.getTotalElements());
			rpage.setTotalPages(page.getTotalPages());
			rpage.setPageInfo(pInfo);

		}
		rpage.setRows(infos(bookmarkEntityList));
		return rpage;
	}

	@Override
	@Transactional
	public List<BookmarkInfo> searchBookmarkByUserId(String userId) throws MesGeneralException {
		log.debug("");
		return infos(bookmarkRepository.findByUserId(userId));
	}
}
