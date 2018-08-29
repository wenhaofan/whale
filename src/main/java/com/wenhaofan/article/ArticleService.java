package com.wenhaofan.article;

import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.kit.Arraykit;
import com.wenhaofan.common.model.entity.Article;
import com.wenhaofan.common.model.entity.Meta;
import com.wenhaofan.common.safe.JsoupFilter;
import com.wenhaofan.meta.MetaService;
import com.wenhaofan.meta.MetaTypeEnum;


public class ArticleService {
 
	@Inject
	private Article dao;
	 
	@Inject
	private MetaService metaService;
	/**
	 * 分页查询文章
	 * @param pageNumber
	 * @param pageSize
	 * @param metas
	 * @return
	 */
	public Page<Article> page(Integer pageNumber,Integer pageSize,Integer... metaIds){
		List<Integer> temp=Arraykit.remove(metaIds, 0);
		SqlPara sqlPara=dao.getSqlPara("article.page", Kv.create().set("metaIds", temp));
		Page<Article> page= dao.paginate(pageNumber, pageSize,sqlPara);
		JsoupFilter.filterArticleList(page.getList(), 30, 200);
		
		for(Article article:page.getList()) {
			List<Meta> metas=metaService.listByCId(article.getId(), MetaTypeEnum.CATEGORY.toString());
			article.setMetas(metas);
		}

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
		SqlPara sql=dao.getSqlPara("article.lastNextArticle", article.getId());
		List<Article> articles= dao.find(sql);
		
		if(articles==null||articles.isEmpty()) {
			articles.add(article);
			articles.add(article);
		}else if (articles.size()==1) {
			articles.add(articles.get(0));
		}
		
		return articles;
	}
	
	
	public void addReadNum(Integer id){
		Db.update("update article set pv=pv+1 where id="+id);
	}

	public List<Article> listRecent(){
		return dao.find("select title,identify from article where state=1 order by gmtCreate desc limit 6");
	}
}
