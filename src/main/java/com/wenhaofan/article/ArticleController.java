package com.wenhaofan.article;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.interceptor.LoginInterceptor;
import com.wenhaofan.common.model.entity.Article;
import com.wenhaofan.common.model.entity.Meta;
import com.wenhaofan.meta.MetaService;
/**
 * 首页文章控制器
 * @author fwh
 *
 */
@Clear(LoginInterceptor.class)
@Before(ArticleSeo.class)
public class ArticleController extends BaseController{

	private ArticleService service=ArticleService.me;
	private MetaService metaService=MetaService.me;
	
	public void index(){
		String identify=getPara();
		Article article=service.getArticle(identify);
		if(article==null) {
			redirect("/");
			return;
		}
		List<Meta> categorys=metaService.listByCId(article.getPkId(), "category");
		List<Meta> tags=metaService.listByCId(article.getPkId(), "tag");
		setAttr("categorys",categorys);
		setAttr("tags",tags);
		setAttr("article", article);
		setAttr("identify", identify);
		setAttr("is_post", true);
		render("post.html");
	}
}
