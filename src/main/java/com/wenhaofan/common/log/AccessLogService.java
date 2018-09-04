package com.wenhaofan.common.log;

import com.wenhaofan.common.model.entity.AccessLog;

public class AccessLogService {

	
	public static AccessLogService me=new AccessLogService();

	
	public void add(AccessLog log) {
		log.save();
	}
}
