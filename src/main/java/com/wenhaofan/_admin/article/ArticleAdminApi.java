package com.wenhaofan._admin.article;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.kit.Ret;
import com.wenhaofan.common.model.entity.Article;
import com.wenhaofan.common.model.entity.Meta;

/**
 * 文章后台管理的控制器
 * 
 * @author fwh
 *
 */
public class ArticleAdminApi extends BaseController {

	private ArticleAdminService articleService = ArticleAdminService.me;

	public void list() {
		Article article = getModel(Article.class, "", true);
		Integer metaid = getParaToInt("categoryId");
		Integer pageNum = getParaToInt("page");
		Integer limit = getParaToInt("limit");
		Page<Article> articlePage = articleService.listArticle(article, metaid, pageNum, limit);
		Ret ret = Ret.ok().set("code", 0).set("data", articlePage.getList()).set("count", articlePage.getTotalRow());
		renderJson(ret.toJson());
	
	}

	/**
	 * 执行文章添加
	 * 
	 * @throws Exception
	 */

	public void add() {
		Article article = getModel(Article.class, "", true);
		List<Meta> tags=getModelList(Meta.class, "tag");
		List<Meta> categorys=getModelList(Meta.class, "category");
		articleService.saveOrUpdate(article, tags, categorys);
		renderJson(Ret.ok("添加成功!").set("article", article).toJson());
	}

	/**
	 * 删除文章
	 */
	public void remove() {
		Integer id =getParaToInt(0);
		renderJson(articleService.deleteArticle(id).toJson());;
	}

	/**
	 * 恢复成功
	 */
	public void recover() {
		Integer id = getParaToInt("d");
		renderJson(articleService.recoverArticle(id).toJson());;
	}

	/**
	 * 获取需要更新的文章id并跳转至修改页面
	 */

	
	public void update() {
		renderJson(articleService.getArticleById(getParaToInt(0)));
	}
	/**
	 * 执行更新操作
	 */

	public void doUpdate() {
		Article article = getModel(Article.class, "", true);
		List<Meta> tags=getModelList(Meta.class, "tag");
		List<Meta> categorys=getModelList(Meta.class, "category");
		articleService.updateArticle(article, tags, categorys);
		renderJson(Ret.ok());
	}

}
