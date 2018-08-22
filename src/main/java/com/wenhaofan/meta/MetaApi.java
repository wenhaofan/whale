package com.wenhaofan.meta;

import com.jfinal.kit.Ret;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;

public class MetaApi extends BaseController{
	@Inject
	private static MetaService mservice;
	
	public void list() {
		String type=getPara(0);
		renderJson(Ret.ok("metas",mservice.listMeta(type)));
	}

	public void article() {
		renderJson(Ret.ok("metas",mservice.listByCId(getParaToInt(1),getPara(0))));
	}
}
