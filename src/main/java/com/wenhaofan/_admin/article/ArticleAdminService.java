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
import com.wenhaofan.article.ArticleService;
import com.wenhaofan.common.exception.MsgException;
import com.wenhaofan.common.kit.ListKit;
import com.wenhaofan.common.kit.StrKit;
import com.wenhaofan.common.model.entity.Article;
import com.wenhaofan.common.model.entity.Meta;
import com.wenhaofan.meta.MetaService;

/**
 * 文章service实现类
 * 
 * @author fwh
 *
 */
public class ArticleAdminService {
	
	public static final ArticleAdminService me=new ArticleAdminService();
	
	private Article  dao=new Article().dao();

	private MetaService mservice=MetaService.me;
	
	private static ArticleService articleService=ArticleService.me;
	
	
	public void saveOrUpdate(Article article,List<Meta> tags,List<Meta> categorys) {
		if(article.getPkId()==null) {
			saveArticle(article, tags, categorys);
		}else {
			updateArticle(article, tags, categorys);
		}
	}
	
	/**
	 * 添加一篇文章
	 * @param article
	 * @param categoryIds
	 */
	public void saveArticle(Article article,List<Meta> tags,List<Meta> categorys)  {
		
		if(ListKit.isBlank(categorys)) {
			new MsgException("分类不能为空！");
		}
		if(StrKit.isBlank(article.getTitle())) {
			new MsgException("标题不能为空！");
		}
		if(StrKit.isBlank(article.getContent())) {
			new MsgException("内容不能为空！");
		}
		
		String identify=article.getIdentify();
		
	
		if(StrKit.notBlank(identify)) {
			Article tempArticle=articleService.getArticle(identify);
			if(tempArticle!=null) {
				new MsgException("路径已存在！");
			}
		}else {
			//默认路径为创建时间
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			article.setIdentify(sdf.format(new Date()));
		}
		
		article.setGmtCreate(new Date());
		article.save();
		
		int articleId=article.getPkId();
		
		mservice.saveMetas(tags, articleId);
		mservice.saveMetas(categorys, articleId);
	}

	public void updateArticle(Article article,List<Meta> tags,List<Meta> categorys) {
		
		String identify=article.getIdentify();
		
		if(StrKit.notBlank(identify)) {
			Article tempArticle=articleService.getArticle(identify);
			if(tempArticle!=null&&tempArticle.getPkId().equals(article.getPkId())) {
				new MsgException("路径已存在！");
			}
		}else {
			//默认路径为创建时间
			Article tempArticle=article.findById(article.getPkId());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			sdf.format(tempArticle.getGmtCreate());
			article.setIdentify(identify);
		}
		
		article.update();
		
		int articleId=article.getPkId();
		
		//删除文章的关联
		mservice.deleteRelevancy(articleId);
		
		mservice.saveMetas(tags, articleId);
		mservice.saveMetas(categorys, articleId);
	}

	
	public void removeArticleCategorys(Integer articleId) {
		Db.update("delete from  articleCategory where articleId=?", articleId);
	}
	
	public void removeArticle(Article article) {
		article.delete();
	}

	
	public Ret deleteArticle(Integer pkId) {
		Article article=new Article();
		article.setPkId(pkId);
		article.setState(Article.STATE_DISCARD);
		return article.update()?Ret.ok():Ret.fail();
	}
	public Ret recoverArticle(Integer pkId) {
		Article article=new Article();
		article.setPkId(pkId);
		article.setState(Article.STATE_PUBLISH);
		return article.update()?Ret.ok():Ret.fail();
	}
		
	public Article getArticleById(int articleId) {
		Article article= dao.findById(articleId);
		return article;
	}

	

	
	public Page<Article> listArticle(Article article, Integer metaId, int pageNumber, int pageSize) {

		Kv kv=Kv.by("mid", metaId).set("article", article);
		
		SqlPara sql=dao.getSqlPara("adminArticle.listArticle", kv);
		
		Page<Article> articlePage = dao.paginate(pageNumber, pageSize,sql);
		
		return articlePage;
	}



	
	public int countArticle(Article article) {
		List<Article> articles = article.find("select count(pkId) as count from article where state=?",
				PropKit.get("is_valid"));

		if (articles != null && articles.size() > 0) {
			return articles.get(0).getInt("count");
		}
		return 0;
	}

	
	

		
}
