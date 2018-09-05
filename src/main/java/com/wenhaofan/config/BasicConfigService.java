package com.wenhaofan.config;

import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.model.entity.BasicConfig;

public class BasicConfigService {

	@Inject
	private BasicConfig dao;
	
	public BasicConfig get() {
		BasicConfig config= dao.findFirst("select * from basic_config order by gmtCreate desc limit 1");
		return config==null?new BasicConfig():config;
	}
}
