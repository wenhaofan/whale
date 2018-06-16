package com.wenhaofan.article;

import com.jfinal.kit.Ret;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.Article;

public class ArticleApi extends BaseController{

	private ArticleService service=ArticleService.me;
	
	
	public void list() {
		Integer[] cids=new Integer[5];
		for(int i=0,size=5;i<size;i++) {
			if(getParaToInt(i)!=null) {
				cids[i]=getParaToInt(i);
			}
			
		}
		Integer pageNum = getParaToInt("p");
		Integer limit=getParaToInt("limit", 10);
		
		renderJson(Ret.ok("articlePage", service.page(pageNum, limit, cids)));
	}
	
	
	public void index() {
		Integer articleId=getParaToInt(0);
		Article article=service.getArticleById(articleId);
		renderJson(Ret.ok("article", article).toJson());
	}
	
}
