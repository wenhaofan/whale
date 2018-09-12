package com.wenhaofan._admin.config;

import com.jfinal.kit.Ret;
import com.wenhaofan.common._config.BlogContext;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.model.entity.Config;

public class BasicConfigService {

	@Inject
	private Config dao;
	
	public Config get() {
		return dao.findFirst("select * from config");
	}
	
	public Ret addOrUpdate(Config config) {
		if(config.getId()!=null) {
			config.update();
		}else {
			config.save();
		}
		
		BlogContext.reset(config);
		
		return Ret.ok();
	}
}
