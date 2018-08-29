package com.wenhaofan.article;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.wenhaofan.comment.CommentService;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.Article;
import com.wenhaofan.common.model.entity.Comment;
import com.wenhaofan.common.model.entity.Meta;
import com.wenhaofan.meta.MetaService;
/**
 * 首页文章控制器
 * @author fwh
 *
 */
@Before(ArticleSeo.class)
public class ArticleController extends BaseController{

	@Inject
	private ArticleService service;
	@Inject
	private MetaService metaService;
	
	@Inject
	private CommentService commentService;
	
	public void index(){
		String identify=getPara();
		Article article=service.getArticle(identify);
		if(article==null) {
			redirect("/");
			return;
		}
		
		List<Meta> categorys=metaService.listByCId(article.getId(), "category");
		List<Meta> tags=metaService.listByCId(article.getId(), "tag");
		Page<Comment> comments=commentService.page(getParaToInt("p",1), 6, article.getIdentify());
		 
		setAttr("agentUser", getAgentUser());
		setAttr("comments", comments);
		setAttr("categorys",categorys);
		setAttr("tags",tags);
		setAttr("article", article);
		setAttr("identify", identify);
		setAttr("isPost", true);
		render("post.html");
	}
}
