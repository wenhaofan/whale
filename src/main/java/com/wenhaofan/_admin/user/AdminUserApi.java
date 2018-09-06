package com.wenhaofan._admin.user;

import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;

public class AdminUserApi  extends BaseController{

	@Inject
	private AdminUserService service;
	
	public void editPwd() {

		renderJson(service.editPassword(getLoginUser(), getPara("oldPwd"), getPara("newPwd")));
	}
	
}
