package com.wenhaofan._admin.sysConfig;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.wenhaofan._admin.user.AdminUserService;
import com.wenhaofan.common._config.BlogConfig;
import com.wenhaofan.common._config.BlogContext;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.User;

/**
 * 完成项目部署的控制器
 * 
 * @author fwh
 *
 */
@Clear()

public class SysConfigurationController extends BaseController{

	private AdminUserService service = AdminUserService.me;

	public void index() {

		if (BlogContext.IS_INIT) {
			renderError(404);
			return;
		}

		String user = getPara("user");
		String passWord = getPara("pwd");
		String jdbcUrl = getPara("jdbcUrl");
		
		if(StrKit.isBlank(user)||StrKit.isBlank(passWord)) {
			setAttr("step",1);
			render("/_view/back/init.html");
			return;
		}
		
		boolean isSuccess=false;
		String mes=null;
		int step=1;

		try {
			//isSuccess=BlogConfig.createDb(jdbcUrl, user, passWord,false);
			if(isSuccess) {
				BlogContext.IS_INIT = true;
				if (service.isExistAdmin()) {
					redirect("/");
					return;
				}
				
				step=2;
			}else {
				mes="账号密码错误!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess=false;
			step=1;
			mes="出bug了!";
			render("/_view/back/init.html");
			return;
		}
		
		setAttr("step", step);
		setAttr("mes", mes);
		render("/_view/back/init.html");
	}

	public void initAdminUser() {
		User user = getModel(User.class, "", true);
		user.setAge(18);
		user.setLevel(1);
		user.setName("admin");
		user.setSex(1);
		user.setUkPhone("admin");
		user.setUkEmail("admin@qq.com");
		service.saveUser(user);
		redirect("/admin");
	}
}
