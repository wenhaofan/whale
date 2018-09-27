package com.wenhaofan._admin.sysLog;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.SysLog;

public class AdminSysLogApi extends BaseController {

	@Inject
	private AdminSysLogService service;
	
	public void listRecent() {
		Page<SysLog> logPage=service.listRecent(getParaToInt("page", 1), getParaToInt("limit",8));
		renderJson(Ret.ok("logPage", logPage));
	}
	
	public void page() {
		Integer pageNum = getParaToInt("page",1);
		Integer limit = getParaToInt("limit",10);
		Page<SysLog> sysLogPage=service.page(pageNum, limit, getBean(QuerySysLog.class,"",true));
		Ret ret = Ret.ok().set("code", 0).set("data", sysLogPage.getList()).set("count", sysLogPage.getTotalRow());
		renderJson(ret.toJson());
	}
	
}
