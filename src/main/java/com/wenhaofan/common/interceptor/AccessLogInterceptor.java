package com.wenhaofan.common.interceptor;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.wenhaofan.common.aop.AopFactory;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.kit.IpKit;
import com.wenhaofan.common.log.AccessLogService;
import com.wenhaofan.common.model.entity.AccessLog;
import com.wenhaofan.common.model.entity.AgentUser;

public class AccessLogInterceptor implements Interceptor {

	private static int maxOutputLengthOfParaValue = 512;
	
	private static AccessLogService service=AopFactory.getInject(AccessLogService.class);
	
	
	@Override
	public void intercept(Invocation inv) {
		BaseController c=(BaseController) inv.getController();
		if(c.isAjax()) {
			inv.invoke();
			return;
		}
		
		String referer=c.getRequest().getHeader("Referer");
		String ip=IpKit.getRealIp(c.getRequest());
		String target = c.getRequest().getRequestURI();
		String info=getLog(c,target,inv.getActionKey());
		String userAgent=c.getRequest().getHeader("User-Agent");
		
		//获取标识
		AgentUser agentUser=c.getAgentUser();
		
		AccessLog log = new AccessLog();
		log.setUserAgent(userAgent);
		log.setGmtCreate(new Date());
		log.setInfo(info);
		log.setIp(ip);
		log.setReferer(referer);
		log.setTarget(target);
		log.setCookie(agentUser.getCookie());
		log.setAgentUserId(agentUser.getId());
		service.add(log);
		
		inv.invoke();
	}

	public String getLog(Controller controller,String target,String actionKey) {
		StringBuilder sb = new StringBuilder();
		sb.append("Url         : ").append(controller.getRequest().getMethod()).append(" ").append(target).append("\n");
		Class<? extends Controller> cc=controller.getClass();
	
		sb.append("Controller  : ").append(cc.getName()).append(".(").append(cc.getSimpleName()).append(".java:1)");
		sb.append("\nMethod      : ").append(actionKey).append("\n");
		
		String urlParas = controller.getPara();
		if (urlParas != null) {
			sb.append("UrlPara     : ").append(urlParas).append("\n");
		}
		
 
		
		// print all parameters
		HttpServletRequest request = controller.getRequest();
		Enumeration<String> e = request.getParameterNames();
		if (e.hasMoreElements()) {
			sb.append("Parameter   : ");
			while (e.hasMoreElements()) {
				String name = e.nextElement();
				String[] values = request.getParameterValues(name);
				if (values.length == 1) {
					sb.append(name).append("=");
					if (values[0] != null && values[0].length() > maxOutputLengthOfParaValue) {
						sb.append(values[0].substring(0, maxOutputLengthOfParaValue)).append("...");
					} else {
						sb.append(values[0]);
					}
				}
				else {
					sb.append(name).append("[]={");
					for (int i=0; i<values.length; i++) {
						if (i > 0)
							sb.append(",");
						sb.append(values[i]);
					}
					sb.append("}");
				}
				sb.append("  ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
