package com.wenhaofan.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.meta.MetaTypeEnum;

public class FrontInterceptor implements Interceptor{

	@Override
	public void intercept(Invocation inv) {
		BaseController c=(BaseController) inv.getController();
		if(c.isAjax()) {
			inv.invoke();
			return ;
		}
		
		c.setAttr("agentUser", c.getAgentUser());
		c.setAttr("hotArticles",c.articleService.listHost(4));
		c.setAttr("tags",c.metaService.listMeta(MetaTypeEnum.TAG.toString()));
		c.setAttr("keyword", c.getPara("keyword"));
		c.setAttr("user", c.getLoginUser());
		c.setAttr("adminUser", c.userService.getAdminUser());
		c.setAttr("basicConfig", c.basicConfigService.get());
		c.setAttr("navs", c.navService.list());
		c.setAttr("recentArticles", c.articleService.listRecent());
		c.setAttr("recentComments", c.commentService.listRecent());
		inv.invoke();
	}

}
