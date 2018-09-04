package com.wenhaofan._admin.seo;

import com.jfinal.kit.Ret;
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
		return Ret.ok();
	}
}
