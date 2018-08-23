package com.wenhaofan.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

public class PajaxInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		Controller c=inv.getController();
		String pjax=c.getPara("pjax");
		c.setAttr("isPjax", StrKit.notNull(pjax));
	}

}
