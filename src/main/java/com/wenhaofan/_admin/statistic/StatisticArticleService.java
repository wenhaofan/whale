package com.wenhaofan._admin.statistic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.model.entity.Article;

public class StatisticArticleService {

	@Inject
	private Article dao;

	public Ret hotArticle(Integer size) {
		SqlPara sql=Db.getSqlPara("statistic.hotArticle", Kv.by("size", size));
		List<Article> articleList=dao.find(sql);
		
		List<String> titleList=new ArrayList<>();
		List<Integer> pvList=new ArrayList<>();
		
		for(Article article:articleList) {
			titleList.add(article.getTitle());
			pvList.add(article.getPv());
		}
		return Ret.ok("titleList", titleList).set("pvList", pvList);
	}
	
	public Ret articleNum(Date gmtStart,Date gmtEnd) {
		if(gmtStart==null) {
			gmtStart=new Date();
		}
		SqlPara sql=Db.getSqlPara("statistic.articleNum", Kv.by("gmtStart", gmtStart).set("gmtEnd", gmtEnd));
		
		Integer articleNum=Db.queryInt(sql.getSql(),sql.getPara());
		
		return Ret.ok("articleNum", articleNum);
	}
	
	
}
