package com.wenhaofan._admin.seo;

import java.util.List;

import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.kit.Ret;
import com.wenhaofan.common.model.entity.BaiduSeoConfig;
import com.wenhaofan.common.model.entity.MetaweblogConfig;

public class AdminSeoApi extends BaseController {
	@Inject
	private BaiduSeoService baiduSeoService;
	@Inject
	private MetaweblogService metaweblogService;
	
	public void mList() {
		List<MetaweblogConfig> configs=metaweblogService.list();
		renderJson(Ret.ok().set("code", 0).set("data", configs));
	}
	
	public void bList() {
		List<BaiduSeoConfig> configs=baiduSeoService.list();
		renderJson(Ret.ok().set("code", 0).set("data", configs));
	}
	
	public void mconfigAdd() {
		MetaweblogConfig config=getModel(MetaweblogConfig.class,"",true);
		renderJson(metaweblogService.add(config));
	}
	
	public void mconfigDelete() {
		renderJson(metaweblogService.delete(getParaToInt()));
	}
	
	public void mconfigUpdate() {
		MetaweblogConfig config=getModel(MetaweblogConfig.class,"",true);
		renderJson(metaweblogService.update(config));
	}
	
	public void bconfigAdd() {
		BaiduSeoConfig config=getModel(BaiduSeoConfig.class,"",true);
		renderJson(baiduSeoService.add(config));
	}
	
	public void bconfigDelete() {
		renderJson(baiduSeoService.delete(getParaToInt()));
	}
	
	public void bconfigUpdate() {
		BaiduSeoConfig config=getModel(BaiduSeoConfig.class,"",true);
		renderJson(baiduSeoService.update(config));
	}
}
