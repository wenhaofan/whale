package com.wenhaofan.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class InitInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {

		//如果没有部署项目 则转发至部署页面
//		if(!BlogContext.IS_INIT) {
//			inv.getController().setAttr("step", 1);
//			inv.getController().render("/_view/back/init.html");
//			return;
//		}

		
		inv.invoke();
	}

}
