package com.wenhaofan._admin.config;

import java.util.List;

import com.jfinal.kit.Ret;
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
	public void bconfigEdit() {
		BaiduSeoConfig config=getModel(BaiduSeoConfig.class,"",true);
		renderJson(baiduSeoService.updateOrAdd(config));
	}
 
	/**
	 * 根据id删除
	 */
	public void bconfigDelete() {
		renderJson(baiduSeoService.delete(getParaToInt()));
	}
	public void pushBaiduLinks() {
		String links=getPara("links");
		baiduSeoService.pushLink(links);
		renderJson(Ret.ok());
	}
}
