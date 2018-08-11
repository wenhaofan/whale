package com.wenhaofan._admin.article;

import java.util.List;

import com.jfinal.core.Controller;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.Article;
import com.wenhaofan.common.model.entity.Meta;
import com.wenhaofan.meta.MetaService;
import com.wenhaofan.meta.MetaTyoeEnum;

public class ArticleAdminController  extends BaseController{
	
	@Inject
	private AdminArticleService service; 

	@Inject
	private MetaService mService;
	
	/**
	 * 分页查询
	 */
	public void list() {
		render("article_list.html");
	}
	
	/**
	 * 跳转进文章编辑页面
	 */
	public void edit() {
		Integer id=getParaToInt();
		//所有的分类
		setAttr("allCategory", mService.listMeta(MetaTyoeEnum.CATEGORY.toString()));
		if(id==null) {
			render("article_edit.html");
			return;
		}
		
		Article article=service.get(id);
		setAttr("article", article);
		
		 
		//文章关联的分类和标签
		setAttr("aCategorys", service.listCategory(id));
		
		List<Meta> aTag=service.listTag(id);
		
		setAttr("aTags", aTag);
		
		render("article_edit.html");
	}
	

}