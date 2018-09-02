package com.wenhaofan._admin.seo;

import java.util.List;

import com.jfinal.kit.Ret;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.BaiduSeoConfig;
import com.wenhaofan.common.model.entity.MetaweblogConfig;

public class AdminSeoApi extends BaseController {
	@Inject
	private BaiduSeoService baiduSeoService;
	@Inject
	private MetaweblogService metaweblogService;
	
	/**
	 * 根据id获取
	 */
	public void bget() {
		renderJson(Ret.ok("config", baiduSeoService.get(getParaToInt())));
	}
	
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
	
	public void bList() {
		List<BaiduSeoConfig> configs=baiduSeoService.list();
		renderJson(Ret.ok().set("code", 0).set("data", configs));
	}
	/**
	 * 修改或添加
	 */
	public void mconfigEdit() {
		MetaweblogConfig config=getModel(MetaweblogConfig.class,"",true);
		renderJson(metaweblogService.updateOrAdd(config));
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
	
	public void mconfigDelete() {
		renderJson(metaweblogService.delete(getParaToInt()));
	}
}
