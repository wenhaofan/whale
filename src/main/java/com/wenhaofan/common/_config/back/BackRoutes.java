package com.wenhaofan.common._config.back;

import com.jfinal.config.Routes;
import com.wenhaofan._admin.article.ArticleAdminApi;
import com.wenhaofan._admin.article.ArticleAdminRouter;
import com.wenhaofan._admin.basic.BasicAdminApi;
import com.wenhaofan._admin.basic.BasicRouter;
import com.wenhaofan._admin.basic.KvController;
import com.wenhaofan._admin.blogroll.BlogrollController;
import com.wenhaofan._admin.diy.action.DiyActionAdminController;
import com.wenhaofan._admin.diy.assets.AssetsAdminController;
import com.wenhaofan._admin.diy.html.DiyAdminController;
import com.wenhaofan._admin.index.IndexAdminController;
import com.wenhaofan._admin.meta.CategoryRouter;
import com.wenhaofan._admin.meta.MetaAdminApi;
import com.wenhaofan._admin.sysConfig.SysConfigurationController;
import com.wenhaofan._admin.user.AdminUserController;
import com.wenhaofan.common.interceptor.AdminIndexInterceptor;
import com.wenhaofan.common.interceptor.ExceptionInterceptor;
import com.wenhaofan.common.uplod.FileUploadApi;
import com.wenhaofan.login.LoginController;

/**
 * 后端路由配置
 * @author fwh
 *
 */
public class BackRoutes extends Routes {
	
	@Override
	public void config() { 
	   addInterceptor(new AdminIndexInterceptor());
	   addInterceptor(new ExceptionInterceptor());
	   setBaseViewPath("/_view/back");
	   add("/admin",IndexAdminController.class,"/");
	   add("/login",LoginController.class,"/");
	   add("/admin/user",AdminUserController.class,"/");
	   
	   add("/admin/api/upload",FileUploadApi.class,"/");
	   add("/admin/api/kv", KvController.class,"/");
	   add("/admin/api/meta",MetaAdminApi.class);
	   add("/admin/api/blogger", KvController.class,"/");
	   add("/admin/api/article",ArticleAdminApi.class,"/article/");
	   add("/admin/api/basic",BasicAdminApi.class);
	   add("/admin/meta", CategoryRouter.class,"/meta/");
	   add("/admin/article", ArticleAdminRouter.class,"/article/");
	   
	   add("/admin/blogroll", BlogrollController.class,"/blogroll/");
	
	   add("/admin/basic",BasicRouter.class,"/basic/");
	   add("/admin/fisrt/init",SysConfigurationController.class,"/");
	   add("/admin/bloger",KvController.class,"/basic/");
	   add("/admin/diy/html",DiyAdminController.class,"/diy/");
	   add("/admin/diy/action", DiyActionAdminController.class,"/diy/");
	   add("/admin/diy/assets", AssetsAdminController.class,"/diy/");
	}

}
