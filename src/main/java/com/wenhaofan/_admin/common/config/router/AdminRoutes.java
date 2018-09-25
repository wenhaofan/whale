package com.wenhaofan._admin.common.config.router;

import com.jfinal.config.Routes;
import com.wenhaofan._admin.article.ArticleAdminApi;
import com.wenhaofan._admin.article.ArticleAdminController;
import com.wenhaofan._admin.blogroll.AdminBlogrollApi;
import com.wenhaofan._admin.comment.AdminCommentApi;
import com.wenhaofan._admin.comment.AdminCommentController;
import com.wenhaofan._admin.common.config.interceptor.AdminIndexInterceptor;
import com.wenhaofan._admin.common.config.interceptor.SysLogInterceptor;
import com.wenhaofan._admin.config.AdminAdvancedConfigController;
import com.wenhaofan._admin.config.AdminBaiduConfigApi;
import com.wenhaofan._admin.config.AdminConfigApi;
import com.wenhaofan._admin.config.AdminConfigController;
import com.wenhaofan._admin.disk.DiskApi;
import com.wenhaofan._admin.disk.DiskController;
import com.wenhaofan._admin.diy.action.DiyActionAdminController;
import com.wenhaofan._admin.diy.assets.AssetsAdminController;
import com.wenhaofan._admin.diy.html.DiyAdminController;
import com.wenhaofan._admin.index.IndexAdminController;
import com.wenhaofan._admin.meta.AdminMetaApi;
import com.wenhaofan._admin.meta.CategoryRouter;
import com.wenhaofan._admin.metaweblog.AdminMetaWeblogConfigApi;
import com.wenhaofan._admin.nav.AdminNavApi;
import com.wenhaofan._admin.nav.AdminNavController;
import com.wenhaofan._admin.statistic.StatisticApi;
import com.wenhaofan._admin.statistic.StatisticController;
import com.wenhaofan._admin.sysLog.AdminSysLogApi;
import com.wenhaofan._admin.themes.AdminThemesApi;
import com.wenhaofan._admin.themes.AdminThemesController;
import com.wenhaofan._admin.user.AdminUserApi;
import com.wenhaofan._admin.user.AdminUserController;
import com.wenhaofan.common.interceptor.ExceptionInterceptor;
import com.wenhaofan.common.uplod.FileUploadApi;

/**
 * 后端路由配置
 * @author fwh
 *
 */
public class AdminRoutes extends Routes {
	
	@Override
	public void config() { 
	   addInterceptor(new AdminIndexInterceptor());
	   addInterceptor(new ExceptionInterceptor());
	   addInterceptor(new SysLogInterceptor());
	   setBaseViewPath("/_view/admin/autumn/");
	   
	   add("/admin/api/themes",AdminThemesApi.class);
	   add("/admin/api/user",AdminUserApi.class);
	   add("/admin/api/nav", AdminNavApi.class);
	   add("/admin/api/upload",FileUploadApi.class);
	   add("/admin/api/meta",AdminMetaApi.class);
	   add("/admin/api/article",ArticleAdminApi.class);
 
	   add("/admin/api/statistic", StatisticApi.class);
	   add("/admin/api/disk",DiskApi.class);
	   add("/admin/api/comment",AdminCommentApi.class);
	   add("/admin/api/metaConfig",AdminMetaWeblogConfigApi.class);
	   add("/admin/api/baiduConfig",AdminBaiduConfigApi.class);
	   add("/admin/api/config", AdminConfigApi.class);
	   add("/admin/api/blogroll", AdminBlogrollApi.class);
	  add("/admin/api/sysLog", AdminSysLogApi.class);
	   
	   add("/admin/advancedConfig",AdminAdvancedConfigController.class,"/");
	   add("/admin/config", AdminConfigController.class,"/");
	   add("/admin/meta", CategoryRouter.class,"/");
	   add("/admin/article", ArticleAdminController.class,"/");
	   add("/admin",IndexAdminController.class,"/");
 
	 
	   add("/admin/diy/html",DiyAdminController.class,"/diy/");
	   add("/admin/diy/action", DiyActionAdminController.class,"/");
	   add("/admin/diy/assets", AssetsAdminController.class,"/");
	   add("/admin/statistic",StatisticController.class,"/");
	   add("/admin/disk", DiskController.class, "/");
	   add("/admin/comment", AdminCommentController.class, "/");
	   add("/admin/nav", AdminNavController.class, "/");
	   add("/admin/user",AdminUserController.class,"/");
	   add("/admin/themes",AdminThemesController.class,"/");
	}

}
