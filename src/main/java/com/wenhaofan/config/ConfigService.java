package com.wenhaofan.config;

import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.model.entity.Config;

public class ConfigService {

	@Inject
	private Config dao;
	
	public Config get() {
		Config config= dao.findFirst("select * from  config order by gmtCreate desc limit 1");
		return config==null?new Config():config;
	}
}
