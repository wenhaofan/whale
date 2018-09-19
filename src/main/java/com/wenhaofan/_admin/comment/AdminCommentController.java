package com.wenhaofan._admin.comment;


import com.wenhaofan._admin.article.AdminArticleService;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;

public class AdminCommentController extends BaseController {

	@Inject
	private AdminArticleService articleService;
	
	public void index() {
		setAttr("articles", articleService.listSimpleArtilce());
		render("comments.html");
	}
	
	
}
