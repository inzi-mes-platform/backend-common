package com.inzi.mes.be.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inzi.mes.be.common.bookmark.BookmarkInfo;
import com.inzi.mes.be.common.bookmark.BookmarkService;
import com.inzi.mes.be.framework.Condition;
import com.inzi.mes.be.framework.MesGeneralException;
import com.inzi.mes.be.framework.persist.PageResponse;

import io.jsonwebtoken.lang.Arrays;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/api/v1/bookmark")
@Slf4j
public class BookmarkController {

	@Autowired
	private BookmarkService bookmarkService;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RestGeneralResponse<String> addBookmark(@RequestBody BookmarkInfo bookmark) {
		log.debug("");
		try {
			bookmarkService.addBookmark(bookmark);
			return RestGeneralResponse.ok();
		} catch (MesGeneralException e) {
			return RestGeneralResponse.fail(e.getErrorCode(), e.getMessage());
		}
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RestGeneralResponse<String> updateBookmark(@RequestBody BookmarkInfo bookmark) {
		log.debug("");
		try {
			bookmarkService.updateBookmark(bookmark);
			return RestGeneralResponse.ok();
		} catch (MesGeneralException e) {
			return RestGeneralResponse.fail(e.getErrorCode(), e.getMessage());
		}
	}
	
	@RequestMapping(value="/delete/{userId}/{name}", method=RequestMethod.POST)
	public RestGeneralResponse<String> deleteBookmark(@PathVariable("userId") String userId, @PathVariable("name") String name) {
		log.debug("");
		try {
			bookmarkService.deleteBookmark(userId, name);
			return RestGeneralResponse.ok();
		} catch (MesGeneralException e) {
			return RestGeneralResponse.fail(e.getErrorCode(), e.getMessage());
		}
	}
	
	@RequestMapping(value="/delete/{userId}", method=RequestMethod.GET)
	public RestGeneralResponse<String> deleteBookmarks(@PathVariable("userId") String userId, @RequestParam("names") String[] names) {
		log.debug("");
		try {
			bookmarkService.deleteBookmark(userId, Arrays.asList(names));
			return RestGeneralResponse.ok();
		} catch (MesGeneralException e) {
			return RestGeneralResponse.fail(e.getErrorCode(), e.getMessage());
		}
	}
	
	@RequestMapping(value="/search/byUser/{userId}", method=RequestMethod.GET)
	@ResponseBody
	public RestGeneralResponse<List<BookmarkInfo>> searchBookmarkByUserId(@PathVariable("userId") String userId) {
		log.debug("");
		try {
			List<BookmarkInfo> bookmarks=bookmarkService.searchBookmarkByUserId(userId);
			return RestGeneralResponse.ok(bookmarks);
		} catch (MesGeneralException e) {
			return RestGeneralResponse.fail(e.getErrorCode(), e.getMessage());
		}
	}
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
	@ResponseBody
	public RestGeneralResponse<PageResponse<BookmarkInfo>> searchBookmark(@RequestBody Condition cond) {
		log.debug("");
		try {
			PageResponse<BookmarkInfo> bookmarks=bookmarkService.searchBookmark(cond);
			return RestGeneralResponse.ok(bookmarks);
		} catch (MesGeneralException e) {
			return RestGeneralResponse.fail(e.getErrorCode(), e.getMessage());
		}
	}
}
