package com.wenhaofan.index;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.wenhaofan.article.ArticleService;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.Article;
import com.wenhaofan.common.model.entity.Meta;
import com.wenhaofan.meta.MetaService;
import com.wenhaofan.meta.MetaTypeEnum;

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
		Integer cid=getParaToInt();
		Integer pageNum = getParaToInt("p",1);
		Integer limit=getParaToInt("limit", 10);
		Page<Article> articlePage=articleService.page(pageNum, limit,cid);
	
		List<Meta> metaList=metaService.listMeta(MetaTypeEnum.CATEGORY.toString());
		
		setAttr("categorys", metaList);
		setAttr("articlePage",articlePage);
		setAttr("cid", cid);
		render("index.html");
	}


	public void page() {
		Integer[] cids=new Integer[5];
		for(int i=0,size=5;i<size;i++) {
			if(getParaToInt(i)!=null) {
				cids[i]=getParaToInt(i);
			}
		}
		Integer pageNum = getParaToInt("p",1);
		Integer limit=getParaToInt("limit", 16);
		Page<Article> articlePage=articleService.page(pageNum, limit, cids);
		
		setAttr("articlePage",articlePage);
		setAttr("cids", cids);
		render("index.html");
	}
	
	public void profiles() {
		render("profiles/profiles.html");
	}

}
