package com.wenhaofan.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.wenhaofan._admin.user.AdminUserService;
import com.wenhaofan.common.aop.AopControllerFactory;
import com.wenhaofan.common.model.entity.User;
import com.wenhaofan.common.service.IndexService;


public class AdminIndexInterceptor implements Interceptor{

	IndexService service=IndexService.me;
	
	
	
	@Override
	public void intercept(Invocation inv) {
 
		inv.invoke();
		
		Controller c=inv.getController();
		
		String viewPath=((Controller)inv.getTarget()).getViewPath();
		
		viewPath+=c.getRender().getView();
		
		AdminUserService adminUserService=AopControllerFactory.getInject(AdminUserService.class);
		User adminUser=adminUserService.getAdminUser();
		
		c.setAttr("adminUser", adminUser);
		String requestUrl=c.getRequest().getRequestURI();
		c.setAttr("requestUrl", requestUrl);
		
		if(viewPath.endsWith(".html")) {
			c.setAttr("includeUrl", viewPath);
			c.render("/_view/back/index.html");
		}
		
	}

}
