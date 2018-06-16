package com.wenhaofan._admin.meta;

import com.wenhaofan.common.controller.BaseController;

public class CategoryRouter extends BaseController{
	/**
	 * 查询所有的分类信息
	 */
	public void list() {
		render("meta_list.html");
	}
}
