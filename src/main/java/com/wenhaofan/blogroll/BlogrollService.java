package com.wenhaofan.blogroll;
/**
* @author 作者:范文皓
* @createDate 创建时间：2018年9月14日 下午2:44:05
*/

import java.util.List;

import com.wenhaofan.common._config.BlogContext;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.model.entity.Blogroll;

public class BlogrollService {

	@Inject
	private Blogroll dao;
	
	public List<Blogroll> list(){
		return dao.findByCache(BlogContext.CacheNameEnum.BLOGROLL.name(), "list", "select * from blogroll order by sort desc");
	}
}
