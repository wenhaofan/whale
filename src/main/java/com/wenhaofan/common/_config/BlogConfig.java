package com.wenhaofan.common._config;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.template.Engine;
import com.jfinal.template.source.FileSourceFactory;
import com.wenhaofan.common._config.back.BackRoutes;
import com.wenhaofan.common._config.front.FrontRoutes;
import com.wenhaofan.common.interceptor.InitInterceptor;
import com.wenhaofan.common.interceptor.LoginInterceptor;
import com.wenhaofan.common.model.entity._MappingKit;

/**
 * 博客的配置文件
 * 
 * @author fwh
 *
 */
public class BlogConfig extends JFinalConfig {

	public static Prop p = loadConfig();

	public static Plugins plugins = null;


	@Override
	public void configConstant(Constants me) {
		
	

		me.setDevMode(true);

		me.setError404View("/_view/error/404.html");
	}


	@Override
	public void configRoute(Routes me) {
		// 配置路由
		me.add(new BackRoutes());
		me.add(new FrontRoutes());

	}

	private static Prop loadConfig() {
		try {
			// 优先加载生产环境配置文件
			return PropKit.use("blog_config.txt");
		} catch (Exception e) {
			// 找不到生产环境配置文件，再去找开发环境配置文件
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
		// TODO Auto-generated method stub
		//me.addDirective("basePath", BasePath.class);
		
		// pinhsu组件
//		me.addSharedFunction("/_view/templates/pinghsu/partial/footer.html");
//		me.addSharedFunction("/_view/templates/pinghsu/partial/header.html");
//		me.addSharedFunction("/_view/templates/pinghsu/partial/comments.html");
//		me.addSharedFunction("/_view/templates/pinghsu/partial/social.html");
		
		
		// 前端显示页面的公共页面组件
//		me.addSharedFunction("_view/common/header.html");
//		me.addSharedFunction("_view/common/footer.html");
//		
		//me.addSharedFunction("_view/common/aside.html");
		//me.addSharedFunction("_view/common/_paginate.html");
		me.addSharedFunction("_view/common/validate.html");
		me.addSharedFunction("_view/common/jquery.html");
		me.addSharedFunction("_view/common/bootstrap.html");
		me.addSharedFunction("_view/common/layui.html");
		//目录树
		me.addSharedFunction("_view/common/ueditor.html");
		//me.addSharedFunction("_view/common/menuTree.html");
		
		me.setSourceFactory(new FileSourceFactory());
	}

	@Override
	public void configPlugin(Plugins me) {
		plugins = me;
		boolean dev=PropKit.getBoolean("devMode");
		
		String url=PropKit.get("jdbcUrl");
		String userName= PropKit.get("userName");
		String pwd= PropKit.get("pwd");
		if(!dev) {
			createDb(url,userName,pwd,dev);
		}else {
			connectDb(userName, pwd);
		}
	
	    me.add(new EhCachePlugin());
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// 全局配置拦截器
		me.add(new InitInterceptor());
		me.add(new LoginInterceptor());
		me.add(new SessionInViewInterceptor());
	
	}

	@Override
	public void configHandler(Handlers me) {

	}
	
	public static boolean createDb(String jdbcUrl, String user, String passWord,boolean dev) {
		//String resourseUrl=PropKit.get("jdbcUrlResource");

		if (StrKit.isBlank(jdbcUrl)) {
			jdbcUrl = PropKit.get("jdbcUrlResource");
		}
		
		boolean isValid=chekcDbInfo(jdbcUrl,user,passWord);
		
		if(!isValid) {
			return isValid;
		}
		
		DruidPlugin dp = null;
	
		dp =getDruidPlugin(jdbcUrl, user, passWord);
		
		
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		dp.start();
		plugins.add(arp);
		arp.start();
		
		arp.setShowSql(true);

		if(!dev) {
			_MappingKit.createAll();
		}
		
		
		DbKit.removeConfig("main");
		
	
		if(!dev) {
			connectDb(user,passWord);
		}
		
	
		return true;
	}


	/**
	 * 连接数据库   
	 * @param user
	 * @param pwd
	 */
	public static void connectDb(String user,String pwd) {
		String jdbcUrl=PropKit.get("jdbcUrl");
		
		DruidPlugin dp = getDruidPlugin(jdbcUrl, user, pwd);
		
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		dp.start();
		
		BlogConfig.plugins.add(dp);
		
		arp.setBaseSqlTemplatePath(PathKit.getRootClassPath() + "/sql");
		arp.addSqlTemplate("all_sqls.sql");
		arp.setShowSql(true);
		_MappingKit.mapping(arp);
		
		arp.start();
	}
	
	public static boolean chekcDbInfo(String url,String user,String pwd) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			DriverManager.getConnection(url, user, pwd);
		} catch (SQLException e) {
		
			return false;
		} catch (ClassNotFoundException e) {

			return false;
		}
		
		return true;
	}
	
	
}

