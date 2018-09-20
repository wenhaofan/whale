package com.wenhaofan._admin.config;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wenhaofan.common._config.BlogContext;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.model.entity.Config;

public class AdminConfigService {

	@Inject
	private Config dao;
	
	public Config get() {
		SqlPara sqlPara=dao.getSqlPara("adminConfig.get");
		return dao.findFirst(sqlPara);
	}
	
	public Ret addOrUpdate(Config config) {
		if(config.getId()!=null) {
			config.update();
		}else {
			config.save();
		}
		BlogContext.reset(get());
		
		return Ret.ok();
	}
	 
}
