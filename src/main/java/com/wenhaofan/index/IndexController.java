package com.wenhaofan.index;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Page;
import com.wenhaofan.article.ArticleService;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.interceptor.LoginInterceptor;
import com.wenhaofan.common.model.entity.Article;

/**
 * 网站首页控制器
 * 
 * @author fwh
 *
 */
@Clear(LoginInterceptor.class)
@Before(IndexSeoInterceptor.class)
public class IndexController extends BaseController {

	IndexService service = IndexService.me;

	
	ArticleService articleService=ArticleService.me;
	
	public void index() {
		Integer[] cids=new Integer[5];
		try {
			for(int i=0,size=5;i<size;i++) {
				if(getParaToInt(i)!=null) {
					cids[i]=getParaToInt(i);
				}
			}
		} catch (Exception e) {
			
		}
		Integer pageNum = getParaToInt("p",1);
		Integer limit=getParaToInt("limit", 10);
		Page<Article> articlePage=articleService.page(pageNum, limit, cids);
		setAttr("articlePage",articlePage);
		setAttr("cids", cids);
		render("/index.html");
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
