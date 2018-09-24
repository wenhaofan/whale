package com.wenhaofan._admin.config;

import java.util.List;

import com.jfinal.kit.Ret;
import com.wenhaofan._admin.common.annotation.SysLog;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.BaiduSeoConfig;

public class AdminBaiduConfigApi extends BaseController {
	@Inject
	private AdminBaiduSeoService baiduSeoService;
	/**
	 * 根据id获取
	 */
	public void bget() {
		renderJson(Ret.ok("config", baiduSeoService.get(getParaToInt())));
	}
	
	public void bList() {
		List<BaiduSeoConfig> configs=baiduSeoService.list();
		renderJson(Ret.ok().set("code", 0).set("data", configs));
	}
	
	@SysLog(value="编辑百度接口配置",action="config")
	public void bconfigEdit() {
		BaiduSeoConfig config=getModel(BaiduSeoConfig.class,"",true);
		renderJson(baiduSeoService.updateOrAdd(config));
	}
 
 
	@SysLog(value="删除百度接口配置",action="config")
	public void bconfigDelete() {
		renderJson(baiduSeoService.delete(getParaToInt()));
	}
	
	@SysLog(value="调用百度推送接口",action="push")
	public void pushBaiduLinks() {
		String links=getPara("links");
		baiduSeoService.pushLink(links);
		renderJson(Ret.ok());
	}
}
