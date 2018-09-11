package com.wenhaofan._admin.blogroll;

import com.wenhaofan.common.controller.BaseController;
/**
 * 友情链接控制器
 * @author 范文皓
 *
 */

public class BlogrollController extends BaseController {

 
	public void index(){
	 
		render("blogroll_list.html");
	}
	
	
}
