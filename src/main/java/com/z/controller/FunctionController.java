/**
 * 
 */
package com.z.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.z.constant.ResultType;
import com.z.domain.Result;
import com.z.repository.mongodb.entity.JokeEntity;
import com.z.repository.mongodb.entity.UserEntity;
import com.z.service.CacheService;
import com.z.service.DaoService;
import com.z.service.SessionService;

/**
 * @Desc
 * @author wewenge.yan
 * @Date 2016年11月25日
 * @ClassName LotterySsqController
 */
/**
 * @author yanwenge
 */
@Controller
@RequestMapping(value = { "function" })
public class FunctionController {
	@Autowired
	private SessionService sessionService;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private DaoService daoService;

	@RequestMapping(value = { "mark" })
	public @ResponseBody Result<Integer> mark(HttpServletRequest request, String id, String action) {
		Result<Integer> result = new Result<Integer>();
		String username = sessionService.getUsername(request);
		if (StringUtils.isEmpty(username)) {
			result.setResultStatusAndMsg(ResultType.NOT_LOGIN, null);
			return result;
		}
		if ("favor".equals(action)) {
			UserEntity user = daoService.queryById(username, UserEntity.class);
			String bookmarks = user.getBookmarks();
			bookmarks = addBookMarker(bookmarks, id);
			user.setBookmarks(bookmarks);
			user.setMarkCount(bookmarks.split(",").length);
			daoService.save(user);
		}
		JokeEntity joke = daoService.queryById(id, JokeEntity.class);
		String praisers = joke.getPraisers();
		String belittlers = joke.getBelittlers();
		if (praisers != null && praisers.contains(username) || belittlers != null && belittlers.contains(username)) {
			result.setResultStatusAndMsg(ResultType.REPEAT_OPERATE, null);
			return result;
		}
		int data = 0;
		if ("praise".equals(action)) {
			praisers = addBookMarker(praisers, username);
			joke.setPraisers(praisers);
			data = praisers.split(",").length;
			joke.setPraiseCount(data);
		} else if ("belittle".equals(action)) {
			belittlers = addBookMarker(belittlers, username);
			joke.setBelittlers(belittlers);
			data = belittlers.split(",").length;
			joke.setBelittleCount(data);
		}
		daoService.save(joke);
		result.setData(data);
		return result;
	}

	/**
	 * @param bookmark
	 * @param bookmarks
	 * @return
	 */
	private String addBookMarker(String bookmarks, String bookmark) {
		if (StringUtils.isEmpty(bookmarks)) {
			bookmarks = bookmark;
		} else if (!bookmarks.contains(bookmark)) {
			bookmarks = bookmarks + "," + bookmark;
		}
		return bookmarks;
	}
}
