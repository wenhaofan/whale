package com.wenhaofan._admin.common.config.interceptor;

import java.lang.reflect.Method;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.wenhaofan._admin.common.annotation.SysLog;
import com.wenhaofan._admin.common.log.SysLogHelper;
import com.wenhaofan._admin.common.log.SysLogLevelEnum;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.kit.IpKit;

public class SysLogInterceptor implements   Interceptor {

	@SuppressWarnings("unchecked")
	@Override
	public void intercept(Invocation inv) {
		inv.invoke();
		
		Method method=inv.getMethod();
		
		SysLog sysLog=method.getAnnotation(SysLog.class);
		
		if(sysLog==null) {
			return;
		}
		
		BaseController c=(BaseController)inv.getController();
		
		
		String content=sysLog.value();
		String action=sysLog.action();
		String ip=null;
		String data=null;
		Integer userId=null;
		Kv dataMap=Kv.create();
		
		if(inv.isActionInvocation()) {
			
			ip=IpKit.getRealIp(c.getRequest());
			
			if(!inv.getControllerKey().contains("upload")) {
				dataMap.putAll(c.getParaMap());
			}
			userId=c.getLoginUser().getId();
		}else {
			dataMap.set("methodArgs", inv.getArgs());
		}
		data=dataMap.toJson();
		
		if(StrKit.isBlank(c.getPara("isAuto"))) {
			SysLogHelper.addSysLog(content, data, action, ip,userId,SysLogLevelEnum.INFO.getValue());
		}
	}

}
