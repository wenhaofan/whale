package com.wenhaofan._admin.metaweblog;

import java.util.List;

import com.jfinal.kit.Ret;
import com.wenhaofan.common.annotation.SysLog;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.MetaweblogConfig;

public class AdminMetaWeblogConfigApi extends BaseController {
	
	@Inject
	private MetaweblogHelper metaweblogService;
	
	
	public void mget() {
		renderJson(Ret.ok("config", metaweblogService.get(getParaToInt())));
	}
	/**
	 * 获取所有记录
	 */
	public void mList() {
		List<MetaweblogConfig> configs=metaweblogService.list();
		renderJson(Ret.ok().set("code", 0).set("data", configs));
	}
	@SysLog(value="编辑metaweblog配置",action="meta")
	public void mconfigEdit() {
		MetaweblogConfig config=getModel(MetaweblogConfig.class,"",true);
		renderJson(metaweblogService.updateOrAdd(config));
	}
	@SysLog(value="删除metaweblog配置",action="meta")
	public void mconfigDelete() {
		renderJson(metaweblogService.delete(getParaToInt()));
	}
}
