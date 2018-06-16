package com.wenhaofan.article;

import java.util.List;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
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

public class ArticleController extends Controller{

	private ArticleService service=ArticleService.me;
	private MetaService metaService=MetaService.me;
	
	public void index(){
		Integer articleId=getParaToInt(0);
		Article article=service.getArticleById(articleId);
		List<Meta> categorys=metaService.listByCId(article.getPkId(), "category");
		
		setAttr("categorys",categorys);
		setAttr("article", article);
		setAttr("id", articleId);
		setAttr("is_post", true);
		render("post.html");
	}
	

	/**
	 * 增加阅读数量,待完成
	 */
	public void addReadNum(){
		service.addReadNum(getParaToInt(0));;
		Ret json=new Ret().setOk();
		renderJson(json.toJson());
	}
	
	
}
