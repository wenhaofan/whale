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
import com.wenhaofan.meta.MetaTypeEnum;
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
	
	public void category() {
		Integer cid=getParaToInt(1);
		if(cid==null) {
			forwardAction("/");
			return;
		}
		Integer pageNum = getParaToInt(0,1);
		Integer limit=getParaToInt("limit", 12);
		Page<Article> articlePage=articleService.page(pageNum, limit,cid,null);
		List<Meta> tags=metaService.listMeta(MetaTypeEnum.TAG.toString());
		
		setAttr("tag", metaService.get(cid));
		setAttr("articlePage",articlePage);
		setAttr("currentPageNum", pageNum);
		setAttr("tags", tags);
		render("category.html");
	}
	
	
	public void index(){
		String identify=getPara();
		Article article=service.getArticle(identify);
		if(article==null) {
			renderError(404);
			return;
		}
	
		articleService.addReadNum(identify);
		List<Meta> categorys=metaService.listByCId(article.getId(), "category");
		List<Meta> atags=metaService.listByCId(article.getId(), "tag");
		Page<Comment> comments=commentService.page(getParaToInt("p",1),2, article.getIdentify());
		List<Article> hotArticles=articleService.listHost(6);
		List<Meta> tags=metaService.listMeta(MetaTypeEnum.TAG.toString());
		
		List<Article> lastNextArticle=articleService.lastNextArticle(article);
		
		setAttr("lastNextArticle", lastNextArticle);
		setAttr("tags", tags);
		setAttr("hotArticles",hotArticles);
		setAttr("agentUser", getAgentUser());
		setAttr("comments", comments);
		setAttr("acategorys",categorys);
		setAttr("atags",atags);
		setAttr("article", article);
		setAttr("identify", identify);
		setAttr("isPost", true);
		render("info.html");
	}
}
