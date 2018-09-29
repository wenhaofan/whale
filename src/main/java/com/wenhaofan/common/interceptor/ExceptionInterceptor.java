package com.wenhaofan.common.interceptor;

import java.lang.reflect.Method;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.log.Log;
import com.wenhaofan.common.annotation.SysLog;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.exception.MsgException;
import com.wenhaofan.common.kit.IpKit;
import com.wenhaofan.common.log.SysLogActionEnum;
import com.wenhaofan.common.log.SysLogHelper;
import com.wenhaofan.common.log.SysLogLevelEnum;

public class ExceptionInterceptor implements Interceptor {

	private static Log log=Log.getLog(ExceptionInterceptor.class);
	

	@Override
	public void intercept(Invocation inv) {
		try {
			inv.invoke();
		} catch (MsgException e) {
			inv.getController().renderJson(Ret.fail("msg", e.getMessage()));
			e.printStackTrace();
			log.info(e.getMessage(), e);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
	
			saveExceptionLog(inv, e);
			if(isAjaxRequest(inv)) {
				inv.getController().renderJson(Ret.fail("msg", "出bug了！"));
			}else {
				inv.getController().renderError(404);
			}
		}
	
	}

	@SuppressWarnings("unchecked")
	private void saveExceptionLog(Invocation inv, Exception e) {
		BaseController c=(BaseController)inv.getController();
		String content=e.getMessage();
		String action=SysLogActionEnum.OTHER.getName();
		String ip=null;
		String data=null;
		Integer userId=null;
		String url=null;
		Kv dataMap=Kv.create();
		
		if(inv.isActionInvocation()) {
			ip=IpKit.getRealIp(c.getRequest());
			if(!inv.getControllerKey().contains("upload")) {
				dataMap.putAll(c.getParaMap());
			}
			userId=c.getLoginUser().getId();
			url=c.getRequest().getRequestURL().toString();
		}else {
			dataMap.set("methodArgs", inv.getArgs());
		}
		data=dataMap.toJson();
		SysLogHelper.addSysLog(content, data, action, ip,url,userId,SysLogLevelEnum.INFO.getValue());
	}

	public boolean isAjaxRequest(Invocation inv) {
		return "XMLHttpRequest".equalsIgnoreCase(inv.getController().getRequest().getHeader("X-Requested-With"));
	}
}
