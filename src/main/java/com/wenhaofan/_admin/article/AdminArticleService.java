package com.wenhaofan._admin.article;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wenhaofan._admin.config.BaiduSeoService;
import com.wenhaofan._admin.config.MetaweblogService;
import com.wenhaofan.article.ArticleService;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.exception.MsgException;
import com.wenhaofan.common.kit.ListKit;
import com.wenhaofan.common.kit.StrKit;
import com.wenhaofan.common.model.entity.Article;
import com.wenhaofan.common.model.entity.Meta;
import com.wenhaofan.meta.MetaService;
import com.wenhaofan.meta.MetaTypeEnum;

/**
 * 文章service实现类
 * 
 * @author fwh
 *
 */
public class AdminArticleService {
	@Inject
	private Article  dao;
	@Inject
	private MetaService mservice;
	@Inject
	private  ArticleService articleService;
	@Inject
	private BaiduSeoService baiduSeoService;
	@Inject
	private MetaweblogService metaweblogService;

	/**
	 * 将指定文章推送至其他网站
	 * @param id
	 * @return
	 */
	public Ret asyncMetaWeblog(Integer id) {
		List<Meta> tags=listTag(id);
		Article article=dao.findById(id);
		new Thread(()-> {
			//向其他论坛推送
			System.err.println(metaweblogService.pushNewPostMetaweblog(article, tags));
		}).start();
		return Ret.ok();
	}
	
	public List<Meta> listCategory(Integer id){
		return mservice.listByCId(id,MetaTypeEnum.CATEGORY.toString());
	}
	
	public List<Meta> listTag(Integer id){
		return mservice.listByCId(id,MetaTypeEnum.TAG.toString());
	}
	
	public void saveOrUpdate(Article article,List<Meta> tags,List<Meta> categorys) {
		if(article.getId()==null) {
			save(article, tags, categorys);
		}else {
			update(article, tags, categorys);
		}
		
 
		new Thread(()-> {
			//向百度推送该文章
			baiduSeoService.pushLink(article.getUrl());
			
		}).start();;

	}
	
	
	/**
	 * 添加一篇文章
	 * @param article
	 * @param categoryIds
	 */
	public void save(Article article,List<Meta> tags,List<Meta> categorys)  {
	  
		String identify=article.getIdentify();
		
	
		if(StrKit.notBlank(identify)) {
			Article tempArticle=articleService.getArticle(identify);
			if(tempArticle!=null&&!identify.equals(tempArticle.getIdentify())) {
				throw 	new MsgException("路径已存在！");
			}
		}else {
			//默认路径为创建时间
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			article.setIdentify(sdf.format(new Date()));
		}
		
		article.setGmtCreate(new Date());
		article.save();
		
		int articleId=article.getId();
		
		if(ListKit.notBlank(categorys)) {
			mservice.saveMetas(categorys, articleId);
		}
		if(ListKit.notBlank(tags)) {
			mservice.saveMetas(tags, articleId);
		}

	}

	public void update(Article article,List<Meta> tags,List<Meta> categorys) {
		
		String identify=article.getIdentify();
		
		if(StrKit.notBlank(identify)) {
			Article tempArticle=articleService.getArticle(identify);
			if(tempArticle!=null&&tempArticle.getId().equals(article.getId())) {
				new MsgException("路径已存在！");
			}
		}else {
			//默认路径为创建时间
			Article tempArticle=article.findById(article.getId());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			identify=sdf.format(tempArticle.getGmtCreate());
			article.setIdentify(identify);
		}
		
		article.update();
		
		int articleId=article.getId();
		
		//删除文章的关联
		mservice.deleteRelevancy(articleId);
		
		
		if(ListKit.notBlank(categorys)) {
			mservice.saveMetas(categorys, articleId);
		}
		if(ListKit.notBlank(tags)) {
			mservice.saveMetas(tags, articleId);
		}
		
	}

	
	public void removeCategorys(Integer articleId) {
		Db.update("delete from  articleCategory where articleId=?", articleId);
	}
	
	/**
	 * 真删除
	 * @param article
	 */
	public void remove(Article article) {
		article.delete();
		//删除文章的关联
		mservice.deleteRelevancy(article.getId());
	}

	
	public Ret delete(Integer id) {
		Article article=new Article();
		article.setId(id);
		article.setState(Article.STATE_DISCARD);
		return article.update()?Ret.ok():Ret.fail();
	}
	/**
	 * 从删除状态修改为发布状态
	 * @param id
	 * @return
	 */
	public Ret recover(Integer id) {
		Article article=new Article();
		article.setId(id);
		article.setState(Article.STATE_PUBLISH);
		return article.update()?Ret.ok():Ret.fail();
	}
		
	public Article get(int articleId) {
		Article article= dao.findById(articleId);
		return article;
	}

	

	
	public Page<Article> page(Article article, Integer metaId, int pageNumber, int pageSize) {

		Kv kv=Kv.by("mid", metaId).set("article", article);
		
		SqlPara sql=dao.getSqlPara("adminArticle.listArticle", kv);
		
		Page<Article> articlePage = dao.paginate(pageNumber, pageSize,sql);
		
		return articlePage;
	}



	
	public int count(Article article) {
		List<Article> articles = article.find("select count(id) as count from article where state=?",
				PropKit.get("is_valid"));

		if (articles != null && articles.size() > 0) {
			return articles.get(0).getInt("count");
		}
		return 0;
	}

	
	

		
}
