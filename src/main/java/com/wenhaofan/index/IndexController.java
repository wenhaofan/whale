package com.wenhaofan.index;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.wenhaofan.article.ArticleService;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.Article;
import com.wenhaofan.meta.MetaService;
import com.wenhaofan.user.UserService;

/**
 * 网站首页控制器
 * 
 * @author fwh
 *
 */
@Before(IndexSeoInterceptor.class)
public class IndexController extends BaseController {

 
	@Inject
	private ArticleService articleService;
	
	@Inject
	private MetaService metaService;
	@Inject
	private UserService userService;
	
	public void index() {
		Integer pageNum = getParaToInt(0,1);
		Integer limit=getParaToInt("limit", 12);
		Page<Article> articlePage=articleService.page(pageNum, limit,null,false);
		List<Article> topArticleList=articleService.listTop();
 
		setAttr("articlePage",articlePage);
		setAttr("currentPageNum", pageNum);
		setAttr("articleTopList", topArticleList);
		render("index.html");
	}

 
	public void profiles() {
		render("profiles/profiles.html");
	}

	public void aboutMe() {
		render("about.html");
	}

}
