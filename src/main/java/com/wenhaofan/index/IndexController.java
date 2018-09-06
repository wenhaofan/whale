package com.wenhaofan.index;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.wenhaofan.article.ArticleService;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.Article;
import com.wenhaofan.meta.MetaService;

/**
 * 网站首页控制器
 * 
 * @author fwh
 *
 */
@Before(IndexSeoInterceptor.class)
public class IndexController extends BaseController {

	@Inject
	private IndexService service;
	
	@Inject
	private ArticleService articleService;
	
	@Inject
	private MetaService metaService;
	
	public void index() {
		Integer cid=getParaToInt("c",null);
		Integer pageNum = getParaToInt("p",1);
		Integer limit=getParaToInt("limit", 12);
		Page<Article> articlePage=articleService.page(pageNum, limit,cid);
 
		setAttr("articlePage",articlePage);
		setAttr("cid", cid);
		setAttr("currentPageNum", pageNum);
		render("index.html");
	}

 
	public void profiles() {
		render("profiles/profiles.html");
	}
 

}
