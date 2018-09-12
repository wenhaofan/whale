package com.wenhaofan.common._config.back;

import com.jfinal.config.Routes;
import com.wenhaofan._admin.article.ArticleAdminApi;
import com.wenhaofan._admin.article.ArticleAdminController;
import com.wenhaofan._admin.basic.BasicAdminApi;
import com.wenhaofan._admin.basic.BasicRouter;
import com.wenhaofan._admin.basic.KvController;
import com.wenhaofan._admin.blogroll.AdminBlogrollApi;
import com.wenhaofan._admin.comment.AdminCommentApi;
import com.wenhaofan._admin.comment.AdminCommentController;
import com.wenhaofan._admin.config.AdminAdvancedConfigController;
import com.wenhaofan._admin.config.AdminBaiduConfigApi;
import com.wenhaofan._admin.config.AdminConfigApi;
import com.wenhaofan._admin.config.AdminConfigController;
import com.wenhaofan._admin.config.AdminMetaWeblogConfigApi;
import com.wenhaofan._admin.disk.DiskApi;
import com.wenhaofan._admin.disk.DiskController;
import com.wenhaofan._admin.diy.action.DiyActionAdminController;
import com.wenhaofan._admin.diy.assets.AssetsAdminController;
import com.wenhaofan._admin.diy.html.DiyAdminController;
import com.wenhaofan._admin.index.IndexAdminController;
import com.wenhaofan._admin.meta.CategoryRouter;
import com.wenhaofan._admin.meta.MetaAdminApi;
import com.wenhaofan._admin.nav.NavApi;
import com.wenhaofan._admin.nav.NavController;
import com.wenhaofan._admin.statistic.StatisticApi;
import com.wenhaofan._admin.statistic.StatisticController;
import com.wenhaofan._admin.user.AdminUserApi;
import com.wenhaofan._admin.user.AdminUserController;
import com.wenhaofan.common.interceptor.AdminIndexInterceptor;
import com.wenhaofan.common.interceptor.ExceptionInterceptor;
import com.wenhaofan.common.uplod.FileUploadApi;

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
 
	   add("/admin/api/user",AdminUserApi.class,"/");
	   add("/admin/api/nav", NavApi.class, "/");
	   add("/admin/api/upload",FileUploadApi.class,"/");
	   add("/admin/api/kv", KvController.class,"/");
	   add("/admin/api/meta",MetaAdminApi.class);
	   add("/admin/api/blogger", KvController.class,"/");
	   add("/admin/api/article",ArticleAdminApi.class,"/article/");
	   add("/admin/api/basic",BasicAdminApi.class);
	   add("/admin/api/statistic", StatisticApi.class,"");
	   add("/admin/api/disk",DiskApi.class);
	   add("/admin/api/comment",AdminCommentApi.class);
	   add("/admin/api/metaConfig",AdminMetaWeblogConfigApi.class);
	   add("/admin/api/baiduConfig",AdminBaiduConfigApi.class);
	   add("/admin/api/config", AdminConfigApi.class);
	   add("/admin/api/blogroll", AdminBlogrollApi.class);
	 
	   add("/admin/advancedConfig",AdminAdvancedConfigController.class,"/");
	   add("/admin/config", AdminConfigController.class,"/");
	   add("/admin/meta", CategoryRouter.class,"/meta/");
	   add("/admin/article", ArticleAdminController.class,"/article/");
 
	   add("/admin/basic",BasicRouter.class,"/basic/");
	   add("/admin/bloger",KvController.class,"/basic/");
	   add("/admin/diy/html",DiyAdminController.class,"/diy/");
	   add("/admin/diy/action", DiyActionAdminController.class,"/diy/");
	   add("/admin/diy/assets", AssetsAdminController.class,"/diy/");
	   add("/admin/statistic",StatisticController.class,"/statistic/");
	   add("/admin/disk", DiskController.class, "/disk/");
	   add("/admin/comment", AdminCommentController.class, "/");
	   add("/admin/nav", NavController.class, "/");
	   add("/admin/user",AdminUserController.class,"/");
	}

}
