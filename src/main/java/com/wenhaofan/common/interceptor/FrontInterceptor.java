package com.wenhaofan.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.wenhaofan.article.ArticleService;
import com.wenhaofan.blogroll.BlogrollService;
import com.wenhaofan.comment.CommentService;
import com.wenhaofan.common._config.BlogContext;
import com.wenhaofan.common.aop.AopFactory;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.meta.MetaService;
import com.wenhaofan.meta.MetaTypeEnum;
import com.wenhaofan.nav.NavService;

public class FrontInterceptor implements Interceptor{

	@Override
	public void intercept(Invocation inv) {
		BaseController c=(BaseController) inv.getController();
		if(c.isAjax()) {
			inv.invoke();
			return ;
		}
		c.setAttr("agentUser", c.getAgentUser());
		c.setAttr("hotArticles",getArticleService().listHost(4));
		c.setAttr("tags",getMetaService().listMeta(MetaTypeEnum.TAG.toString()));
		c.setAttr("keyword", c.getPara("keyword"));
		c.setAttr("adminUser", c.userService.getAdminUser());
		c.setAttr("config", BlogContext.config);
		c.setAttr("navs", getNavService().list());
		c.setAttr("recentArticles", getArticleService().listRecent());
		c.setAttr("recentComments", getCommentService().listRecent());
		c.setAttr("blogrolls", getBlogrollService().list());
		inv.invoke();
	}

	public BlogrollService getBlogrollService() {
		return AopFactory.getInject(BlogrollService.class);
	}
	
	
	public ArticleService getArticleService() {
		return AopFactory.getInject(ArticleService.class);
	}
	
	public MetaService getMetaService() {
		return AopFactory.getInject(MetaService.class);
	}
	
	public NavService getNavService() {
		return AopFactory.getInject(NavService.class);
	}
	
	public CommentService getCommentService() {
		return AopFactory.getInject(CommentService.class);
	}
}
