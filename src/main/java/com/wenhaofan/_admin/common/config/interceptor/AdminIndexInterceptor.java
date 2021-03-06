package com.wenhaofan._admin.common.config.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.wenhaofan._admin.user.AdminUserService;
import com.wenhaofan.common.aop.AopFactory;
import com.wenhaofan.common.kit.StrKit;
import com.wenhaofan.common.model.entity.User;
import com.wenhaofan.common.service.IndexService;


public class AdminIndexInterceptor implements Interceptor{

	IndexService service=IndexService.me;
	
	@Override
	public void intercept(Invocation inv) {
 
		inv.invoke();
		
		Controller c=inv.getController();
		
		boolean isPjax = "true".equalsIgnoreCase(c.getHeader("X-PJAX"));
		 
		if(isPjax) {
			return;
		}
		
		AdminUserService adminUserService=AopFactory.getInject(AdminUserService.class);
		User adminUser=adminUserService.getAdminUser();

		String requestUrl=c.getRequest().getRequestURI();
		
		c.setAttr("requestUrl", requestUrl);
		c.setAttr("adminUser", adminUser);
		
		String viewPath=((Controller)inv.getTarget()).getViewPath();
		
		if(c.getRender()!=null) {
			viewPath+=c.getRender().getView();
		}
		
		if(StrKit.notBlank(viewPath)&&viewPath.endsWith(".html")) {
			c.setAttr("includeUrl", viewPath);
			c.render("/_view/admin/autumn/index.html");
		}
		
	}

}
