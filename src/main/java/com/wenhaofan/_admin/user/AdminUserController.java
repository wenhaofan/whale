package com.wenhaofan._admin.user;

import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;

public class AdminUserController extends BaseController {

	@Inject
	private AdminUserService service;
	
	
	public void edit() {
		setAttr("user", service.getAdminUser());
		render("blogrollInfo.html");
	}
}
