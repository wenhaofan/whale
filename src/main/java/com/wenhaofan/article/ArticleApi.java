package com.wenhaofan.article;

import com.jfinal.kit.Ret;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.Article;

public class ArticleApi extends BaseController{

	private ArticleService service=ArticleService.me;
	
	//通过标识获取文章信息
	public void index() {
		String identify=getPara();
		Article article=service.getArticle(identify);
		renderJson(Ret.ok("article", article).toJson());
	}
	
	/**
	 * 根据多个分类 标签id进行查询
	 */
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
	
	/**
	 * 通过id获取文章信息
	 */
	public void getById() {
		renderJson(Ret.ok("article", service.getArticle(getParaToInt(0))));
	}
	
	/**
	 * 增加阅读数量,待完成
	 */
	public void addReadNum(){
		service.addReadNum(getParaToInt());;
		renderJson(Ret.ok());
	}
	
}
