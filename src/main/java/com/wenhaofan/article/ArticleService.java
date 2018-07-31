package com.wenhaofan.article;

import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wenhaofan.common.kit.Arraykit;
import com.wenhaofan.common.model.entity.Article;
import com.wenhaofan.common.safe.JsoupFilter;


public class ArticleService {
	public static final ArticleService me=new ArticleService();
	private Article dao=new Article().dao();
	 
	/**
	 * 分页查询文章
	 * @param pageNumber
	 * @param pageSize
	 * @param metas
	 * @return
	 */
	public Page<Article> page(Integer pageNumber,Integer pageSize,Integer... metas){
		List<Integer> temp=Arraykit.remove(metas, 0);
		SqlPara sqlPara=dao.getSqlPara("article.page", Kv.create().set("metaIds", temp));
		Page<Article> page= dao.paginate(pageNumber, pageSize,sqlPara);
		JsoupFilter.filterArticleList(page.getList(), 30, 200);
		return page;
	}
	
	/**
	 * 根据标识去获取
	 * @param identify
	 * @return
	 */
	public Article getArticle(String  identify) {
		Article article= dao.findFirst("select * from article where identify =? ",identify);
		return article;
	}
	
	/**
	 * 根据id获取文章
	 * @param id
	 * @return
	 */
	public Article getArticle(Integer id) {
		return dao.findById(id);
	}
	
	/**
	 * 获取一篇文章的上一篇和下一篇
	 * @param article
	 * @return
	 */
	public List<Article> lastNextArticle(Article article){
		SqlPara sql=dao.getSqlPara("article.lastNextArticle", article.getPkId());
		List<Article> articles= dao.find(sql);
		
		if(articles==null||articles.isEmpty()) {
			articles.add(article);
			articles.add(article);
		}else if (articles.size()==1) {
			articles.add(articles.get(0));
		}
		
		return articles;
	}
	
	
	public void addReadNum(Integer pkId){
		Db.update("update article set readNum=readNum+1 where pkId="+pkId);
	}

}
