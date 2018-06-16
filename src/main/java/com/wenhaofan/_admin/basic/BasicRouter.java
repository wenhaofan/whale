package com.wenhaofan._admin.basic;

import com.wenhaofan.common.controller.BaseController;

/**
 * 首页信息配置的控制器
 * @author 范文皓
 *
 */
public class BasicRouter extends BaseController {
	
	public void index() {
		render("sysConfig.html");
	}
	
	
}
