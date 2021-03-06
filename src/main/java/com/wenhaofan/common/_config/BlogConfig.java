package com.wenhaofan.common._config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.template.Engine;
import com.jfinal.template.source.FileSourceFactory;
import com.mysql.jdbc.Connection;
import com.wenhaofan._admin.article.AdminArticleLuceneIndexes;
import com.wenhaofan._admin.common.config.interceptor.LoginInterceptor;
import com.wenhaofan._admin.common.config.router.AdminRoutes;
import com.wenhaofan.common.aop.AopFactory;
import com.wenhaofan.common.handle.BasePathHandler;
import com.wenhaofan.common.interceptor.AccessLogInterceptor;
import com.wenhaofan.common.model.entity.Config;
import com.wenhaofan.common.model.entity._MappingKit;
import com.wenhaofan.config.ConfigService;

/**
 * 博客的配置文件
 * 
 * @author fwh
 *
 */
public class BlogConfig extends JFinalConfig {

	public static Prop p = loadConfig();

	public static Plugins plugins = null;

	public static FrontRoutes frontRoutes;
	
	private WallFilter wallFilter;
	@Override
	public void configConstant(Constants me) {
		me.setDevMode(p.getBoolean("devMode", false));
		me.setControllerFactory(new AopFactory());
		me.setJsonFactory(new MixedJsonFactory());
		me.setError404View("/_view/error/404.html");
	}


	@Override
	public void configRoute(Routes me) {
		// 配置路由
		me.add(new AdminRoutes());
		frontRoutes=new FrontRoutes();
		me.add(frontRoutes);
	}

	private static Prop loadConfig() {
		try {
			BlogContext.CONFIG_FILE_NAME="dev_blog_config.txt";
			return PropKit.use("dev_blog_config.txt");
		} catch (Exception e) {
			BlogContext.CONFIG_FILE_NAME="blog_config.txt";
			return PropKit.use("blog_config.txt");
		}
		
	}

	/**
	 * 抽取成独立的方法，例于 _Generator 中重用该方法，减少代码冗余
	 */
	public static DruidPlugin getDruidPlugin() {
		return new DruidPlugin(p.get("jdbcUrl"), p.get("userName"), p.get("pwd").trim());
	}

	public static DruidPlugin getDruidPlugin(String jdbcUrl, String user, String passWord) {
		return new DruidPlugin(jdbcUrl, user, passWord);
	}

	@Override
	public void configEngine(Engine me) {
		me.addSharedFunction("_view/common/jquery.html");
		me.addSharedFunction("_view/common/bootstrap.html");
		me.addSharedFunction("_view/common/layui.html");
		me.setSourceFactory(new FileSourceFactory());
	}

	@Override
	public void configPlugin(Plugins me) {
		plugins = me;

	    DruidPlugin druidPlugin = getDruidPlugin();
	    wallFilter = new WallFilter();              // 加强数据库安全
	    wallFilter.setDbType("mysql");
	    druidPlugin.addFilter(wallFilter);
	    druidPlugin.addFilter(new StatFilter());    // 添加 StatFilter 才会有统计数据
	    me.add(druidPlugin);
	    
	    ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
	    arp.setTransactionLevel(Connection.TRANSACTION_READ_COMMITTED);
	    _MappingKit.mapping(arp);
	    // 强制指定复合主键的次序，避免不同的开发环境生成在 _MappingKit 中的复合主键次序不相同
	    arp.setPrimaryKey("document", "mainMenu,subMenu");
	    me.add(arp);
        arp.setShowSql(p.getBoolean("devMode", false));
 
    	arp.setBaseSqlTemplatePath(PathKit.getRootClassPath() + "/sql");
        arp.addSqlTemplate("all_sqls.sql");
        
	    me.add(new EhCachePlugin());
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// 全局配置拦截器
		//me.add(new InitInterceptor());
		me.add(new LoginInterceptor());
		me.add(new SessionInViewInterceptor());
		me.add(new AccessLogInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
		me.add(new BasePathHandler("basePath"));
	}


	@Override
	public void afterJFinalStart() {
		// TODO Auto-generated method stub
		super.afterJFinalStart();
		
		ConfigService configService=AopFactory.getInject(ConfigService.class);
		AdminArticleLuceneIndexes  articleLucene=AopFactory.getInject(AdminArticleLuceneIndexes.class);
		Config  config=configService.get();
		BlogContext.reset(config);
		articleLucene.resetArticleIndexes();
	}
	
}

