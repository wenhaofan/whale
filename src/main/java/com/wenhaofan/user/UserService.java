package com.wenhaofan.user;

import com.wenhaofan.common._config.BlogContext;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.model.entity.User;

public class UserService {

	@Inject
	public User dao;
	
	public User getAdminUser() {
		User user=dao.findFirstByCache(BlogContext.CacheNameEnum.BLOGGER.name(), "getAdminUser", "select * from user");
		return user.setPwd("");
	}
	
}
