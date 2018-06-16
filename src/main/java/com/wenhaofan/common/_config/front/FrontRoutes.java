package com.wenhaofan.common._config.front;

import com.jfinal.config.Routes;
import com.wenhaofan.article.ArticleApi;
import com.wenhaofan.article.ArticleController;
import com.wenhaofan.common.interceptor.ExceptionInterceptor;
import com.wenhaofan.index.IndexController;
import com.wenhaofan.meta.MetaApi;

/**
 * 前端路由配置
 * @author fwh
 *
 */
public class FrontRoutes extends Routes{

	@Override
	public void config() {
		addInterceptor(new ExceptionInterceptor());
		String pinghsuFront="/_view/templates/enjoy/";
		setBaseViewPath(pinghsuFront);
		add("/article",ArticleController.class);
		add("/api/meta", MetaApi.class,"/");
		add("/api/article",ArticleApi.class,"/");
		add("/",IndexController.class);
	}

}
