package com.wenhaofan.profiles;

import com.jfinal.aop.Clear;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.interceptor.LoginInterceptor;
/**
 * 个人信息首页
 * @author fwh
 *
 */
@Clear(LoginInterceptor.class)
public class ProfilesController extends BaseController{

	public void index(){
		render("/WEB-INF/front/profiles/profiles.html");
	}
}
