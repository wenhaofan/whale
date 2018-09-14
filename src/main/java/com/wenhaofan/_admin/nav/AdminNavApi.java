package com.wenhaofan._admin.nav;

import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.kit.Ret;
import com.wenhaofan.common.model.entity.Nav;
import com.wenhaofan.nav.NavService;

public class AdminNavApi extends BaseController{

	@Inject
	private AdminNavService  service;
	@Inject
	private NavService frontService;
	
	public void list() {
		Ret ret = Ret.ok().set("code", 0).set("data", frontService.list());
		renderJson(ret.toJson());
	}
	
	public void get() {
		Nav nav=service.get(getParaToInt());
		renderJson(Ret.ok("nav", nav));
	}
	
	public void delete() {
		renderJson(service.delete(getParaToInt("toId")));
	}
	
	public void update() {
		Nav nav=getBean(Nav.class,"",true);
		renderJson(service.update(nav));
	}
	
	public void add() {
		Nav nav =getModel(Nav.class,"",true);
		renderJson(service.add(nav));
	}
	
}
