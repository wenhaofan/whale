package com.wenhaofan._admin.common.log;

import com.wenhaofan.common.model.entity.SysLog;

/**
* @author 作者:范文皓
* @createDate 创建时间：2018年9月4日 下午4:29:36
*/
public class SysLogHelper {
	
	public static void addSysLog(String content,String action,String data) {
		addSysLog(content, data, action, null,null,SysLogLevelEnum.INFO.getValue());
	}
	
	public static void addSysLog(String content,String action,String data,Integer level) {
		addSysLog(content, data, action, null,null,level);
	}
	
	public static void addSysLog(String content,String data,
			String action,String ip,Integer userId,Integer level) {
		SysLog sysLog=new SysLog();
		sysLog.setContent(content);
		sysLog.setAction(action);
		sysLog.setData(data);
		sysLog.setIp(ip);
		sysLog.setUserId(userId);
		sysLog.setLevel(level);
		
		sysLog.save();
	}
}
