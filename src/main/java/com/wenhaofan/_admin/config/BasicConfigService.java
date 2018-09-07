package com.wenhaofan._admin.config;

import com.jfinal.kit.Ret;
import com.wenhaofan.common._config.BlogContext;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.model.entity.BasicConfig;

public class BasicConfigService {

	@Inject
	private BasicConfig dao;
	
	public BasicConfig get() {
		return dao.findFirst("select * from basic_config");
	}
	
	public Ret addOrUpdate(BasicConfig config) {
		if(config.getId()!=null) {
			config.update();
		}else {
			config.save();
		}
		
		BlogContext.reset(config);
		
		return Ret.ok();
	}
}
