package com.wenhaofan.user;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.interceptor.LoginInterceptor;
import com.wenhaofan.index.IndexSeoInterceptor;
/**
 * 后台页面控制器
 * @author fwh
 *
 */
@Clear(LoginInterceptor.class)
@Before(IndexSeoInterceptor.class)
public class LoginController extends BaseController{

	public void index(){
		if(isLogin()) {
			redirect("/admin");
			return;
		}
		
		render("login.html");
	}

	public void logout() {
		removeCookie(LoginService.sessionIdName);
		redirect("/");
	}
	
}

