package com.wenhaofan._admin.config;

import com.jfinal.kit.Ret;
import com.wenhaofan._admin.common.annotation.SysLog;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.Config;

public class AdminConfigApi  extends BaseController {

	@Inject
	private AdminConfigService service;
	
	public void index() {
		renderJson(Ret.ok("config", service.get()));
	}
	
	@SysLog(value="编辑系统配置",action="config")
	public void edit() {
		renderJson(service.addOrUpdate(getBean(Config.class,"",true)));
	}
}
