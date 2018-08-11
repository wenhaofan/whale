package com.wenhaofan.article;

import java.util.List;

import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.wenhaofan.common.interceptor.BaseSeoInterceptor;
import com.wenhaofan.common.model.entity.Article;
import com.wenhaofan.common.model.entity.Meta;

public class ArticleSeo extends BaseSeoInterceptor {

	@Override
	public void indexSeo(Invocation inv) {
		
	}

	@Override
	public void otherSeo(Invocation inv) {
		Controller c=inv.getController();
		Article article = c.getAttr("article");

		List<Meta> tags=c.getAttr("tags");
		List<Meta> categorys=c.getAttr("categorys");
		
		setSeoTitle(c, article.getTitle());
		setSeoKeyWords(c, keywords(tags,categorys,article.getTitle()));
		setSeoDescr(c, article.getTitle());
	}

	public String keywords(List<Meta> tags,List<Meta> categorys,String title) {
		StringBuilder sb=new StringBuilder();
		for(Meta meta:tags) {
			sb.append(meta.getName()+",");
		}
		for(Meta meta:categorys) {
			sb.append(meta.getName()+",");
		}
		sb.append(title);
		return sb.toString();
	}
}