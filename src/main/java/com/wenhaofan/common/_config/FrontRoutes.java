package com.wenhaofan.common._config;

import com.jfinal.config.Routes;
import com.wenhaofan.article.ArticleApi;
import com.wenhaofan.article.ArticleController;
import com.wenhaofan.comment.CommentApi;
import com.wenhaofan.common.interceptor.ExceptionInterceptor;
import com.wenhaofan.common.interceptor.FrontInterceptor;
import com.wenhaofan.common.interceptor.ThemesInterceptor;
import com.wenhaofan.index.IndexController;
import com.wenhaofan.meta.MetaApi;
import com.wenhaofan.user.LoginApi;
import com.wenhaofan.user.LoginController;

/**
 * 前端路由配置
 * @author fwh
 *
 */
public class FrontRoutes extends Routes{

	@Override
	public void config() {
		addInterceptor(new ThemesInterceptor());
		addInterceptor(new ExceptionInterceptor());
		addInterceptor(new FrontInterceptor());
		String pinghsuFront="/_view/templates/default/";
		setBaseViewPath(pinghsuFront);
		add("/api/login", LoginApi.class,"/");
		add("/login",LoginController.class,"/");
		add("/article",ArticleController.class,"/");
		add("/api/meta", MetaApi.class,"/");
		add("/api/article",ArticleApi.class,"/");
		add("/comment", CommentApi.class);
		add("/",IndexController.class,"/");
	}

}
