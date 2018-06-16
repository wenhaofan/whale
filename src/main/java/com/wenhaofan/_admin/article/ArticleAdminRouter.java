package com.wenhaofan._admin.article;

import com.jfinal.core.Controller;

public class ArticleAdminRouter extends Controller{
	/**
	 * 分页查询
	 */
	public void list() {
		render("article_list.html");
	}
	
	/**
	 * 跳转进文章编辑页面
	 */
	public void edit() {
		setAttr("pkId", getPara(0));
		render("article_edit.html");
	}
	

}
