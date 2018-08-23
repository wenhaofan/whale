package com.wenhaofan.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.wenhaofan.common.controller.BaseController;

public class FrontInterceptor implements Interceptor{

	@Override
	public void intercept(Invocation inv) {
		BaseController c=(BaseController) inv.getController();
		if(c.isAjax()) {
			inv.invoke();
			return ;
		}
		c.setAttr("recentArticles", c.articleService.listRecent());
		c.setAttr("recentComments", c.commentService.listRecent());
		inv.invoke();
	}

}
