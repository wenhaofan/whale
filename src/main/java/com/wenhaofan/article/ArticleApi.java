package com.wenhaofan.article;

import com.jfinal.kit.Ret;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.Article;

public class ArticleApi extends BaseController{

	@Inject
	private ArticleService service;
	
	//通过标识获取文章信息
	public void index() {
		String identify=getPara();
		Article article=service.getArticle(identify);
		renderJson(Ret.ok("article", article).toJson());
	}
	
	/**
	 * 根据标签或分类id进行查询
	 */
	public void list() {
		Integer cid=getParaToInt();
		Integer pageNum = getParaToInt("p");
		Integer limit=getParaToInt("limit", 10);
		renderJson(Ret.ok("articlePage", service.page(pageNum, limit, cid,null)));
	}
 
	/**
	 * 增加阅读数量 
	 */
	public void addReadNum(){
		service.addReadNum(getPara());;
		renderJson(Ret.ok());
	}
	
}
