package com.inzi.mes.be.common.bookmark;

import java.util.List;

import com.inzi.mes.be.framework.Condition;
import com.inzi.mes.be.framework.MesGeneralException;
import com.inzi.mes.be.framework.persist.PageResponse;

public interface BookmarkService {

	public abstract void addBookmark(BookmarkInfo bookmarkInfo) throws MesGeneralException;
	
	public abstract void updateBookmark(BookmarkInfo bookmarkInfo) throws MesGeneralException;
	
	public abstract void deleteBookmark(String userId, String name) throws MesGeneralException;
	
	public abstract void deleteBookmark(String userId, List<String> names) throws MesGeneralException;
	
	public abstract PageResponse<BookmarkInfo> searchBookmark(Condition condition) throws MesGeneralException;
	
	public abstract List<BookmarkInfo> searchBookmarkByUserId(String userId) throws MesGeneralException;
}
