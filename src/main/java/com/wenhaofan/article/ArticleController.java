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
		String identify=getPara();
		Article article=service.getArticle(identify);
		if(article==null) {
			redirect("/");
			return;
		}
		List<Meta> categorys=metaService.listByCId(article.getPkId(), "category");
		setAttr("categorys",categorys);
		setAttr("article", article);
		setAttr("identify", identify);
		setAttr("is_post", true);
		render("post.html");
	}
	


	
	
}
