package com.wenhaofan._admin.article;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.wenhaofan.common.annotation.SysLog;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.kit.Ret;
import com.wenhaofan.common.model.entity.Article;
import com.wenhaofan.common.model.entity.Meta;
import com.wenhaofan.common.model.entity.User;

/**
 * 文章后台管理的控制器
 * 
 * @author fwh
 *
 */
public class ArticleAdminApi extends BaseController {

	@Inject
	private AdminArticleService articleService;
	@Inject
	private AdminArticleLuceneIndexes luceneIndexes;
	
	@SysLog(value="重置文章索引",action="article")
	public void createIndex() {
		luceneIndexes.resetArticleIndexes();
		renderJson(Ret.ok());
	}
	
	public void list() {
		Article article = getModel(Article.class, "", true);
		Integer metaid = getParaToInt("categoryId");
		Integer pageNum = getParaToInt("page");
		Integer limit = getParaToInt("limit");
		Page<Article> articlePage = articleService.page(article, metaid, pageNum, limit);
		Ret ret = Ret.ok().set("code", 0).set("data", articlePage.getList()).set("count", articlePage.getTotalRow());
		renderJson(ret.toJson());
	}

	public void asyncMetaWeblog() {
		Integer id=getParaToInt();
		renderJson(articleService.asyncMetaWeblog(id));
	}
	
	/**
	 * 执行文章编辑
	 * 
	 * @throws Exception
	 */
	@SysLog(value="编辑文章",action="article")
	public void edit() {
		Article article = getModel(Article.class, "", true);
		List<Meta> tags=getModelList(Meta.class, "tag");
		List<Meta> categorys=getModelList(Meta.class, "category");
		User user=getLoginUser();
		article.setUserId(user.getId());
		articleService.saveOrUpdate(article, tags, categorys);
	
		renderJson(Ret.ok("添加成功!").set("article", article).toJson());
	}

 
	@SysLog(value="废弃文章",action="article")
	public void remove() {
		Integer id =getParaToInt(0);
		renderJson(articleService.remove(id).toJson());;
	}

	public void delete() {
		Integer id =getParaToInt(0);
		renderJson(articleService.delete(id).toJson());;
	}
	 
	@SysLog(value="恢复文章",action="article")
	public void recover() {
		Integer id = getParaToInt(0);
		renderJson(articleService.recover(id));;
	}

	/**
	 * 获取文章信息
	 */
	public void get() {
		renderJson(Ret.ok("article", articleService.get(getParaToInt(0))));
	}
 
	/**
	 * 获取浏览量最多的前几条
	 */
	public void listHot() {
		renderJson(Ret.ok("list", articleService.listHot(getParaToInt("num",8))));
	}
	
	
}
