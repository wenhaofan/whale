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
	private ArticleLuceneIndexes articleLuceneIndexes;
	
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
		
		Meta tag=metaService.get(cid);
		
		setAttr("tag", tag);
		setAttr("articlePage",articlePage);
		setAttr("currentPageNum", pageNum);
	 
		render("list.html");
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
 
		List<Article> lastNextArticle=articleService.lastNextArticle(article);
		setAttr("lastNextArticle", lastNextArticle);
 
		List<Article> aboutArticles=articleService.about(article, 10);
		 
		setAttr("aboutArticles", aboutArticles);
		setAttr("comments", comments);
		setAttr("acategorys",categorys);
		setAttr("atags",atags);
		setAttr("article", article);
		setAttr("identify", identify);
 
		render("info.html");
	}
	
	public void search() {
		
		String queryStr=getPara("keyword");
		Integer pageNum=getParaToInt(0,1);
		Integer pageSize= getParaToInt(1,10);
		Page<Article>   articlePage=articleLuceneIndexes.search(queryStr,pageNum,pageSize);
 
		setAttr("articlePage",articlePage);
		setAttr("currentPageNum", pageNum);
		
		render("list.html");
	}
}
