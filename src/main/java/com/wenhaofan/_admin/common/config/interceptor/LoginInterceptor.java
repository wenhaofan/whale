package com.wenhaofan._admin.common.config.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.Ret;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.User;

public class LoginInterceptor implements Interceptor{

	/**
	 * 后台权限验证,未登录则不能访问
	 */
	public void intercept(Invocation inv) {
		
		BaseController c=(BaseController)inv.getController();
	
	    User user=c.getLoginUser();
		
		if(user!=null) {
			inv.invoke();
		}else {
			String actionKey=inv.getActionKey();
			//只有登陆了才能访问后台
			if(!actionKey.contains("/admin")) {
				inv.invoke();
				return;
			}
			
			if(c.isAjax()) {
				c.renderJson(Ret.fail("msg", "请登录后访问！"));
			}else {
				c.renderError(404);
			}
			
		}
		
		
	}

	
}
