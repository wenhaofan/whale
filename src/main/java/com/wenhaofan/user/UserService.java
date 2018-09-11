package com.wenhaofan.user;

import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.model.entity.User;

public class UserService {

	@Inject
	public User dao;
	
	public User getAdminUser() {
		User user= dao.findFirst("select * from user");
		return user.setPwd("");
				
	}
	
}
