package com.wenhaofan.comment;


import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.wenhaofan.article.ArticleService;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.AgentUser;
import com.wenhaofan.common.model.entity.Article;
import com.wenhaofan.common.model.entity.Comment;

public class CommentApi extends BaseController {

	@Inject
	private CommentService service;
	
	@Inject
	private ArticleService articleService;
	
	public void save() {
		Comment comment=getModel(Comment.class,"",true);
		AgentUser agentUser=getAgentUser();
		service.save(comment,agentUser.getCookie());
		renderJson(Ret.ok());
	}

	public void page() {
		Article article=articleService.getArticle(getPara("articleId"));
		//如果遇到爬虫抓取则返回
		Page<Comment> commentPage=service.page(getParaToInt("p",1), getParaToInt("s",6), getPara("articleId"));
		renderJson(Ret.ok("page", commentPage));
	}

}
