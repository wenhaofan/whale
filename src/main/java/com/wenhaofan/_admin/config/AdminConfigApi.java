package com.wenhaofan._admin.config;

import com.jfinal.kit.Ret;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.Config;

public class AdminConfigApi  extends BaseController {

	@Inject
	private AdminConfigService service;
	
	public void index() {
		renderJson(Ret.ok("config", service.get()));
	}
	
	public void edit() {
		renderJson(service.addOrUpdate(getBean(Config.class,"",true)));
	}
}
