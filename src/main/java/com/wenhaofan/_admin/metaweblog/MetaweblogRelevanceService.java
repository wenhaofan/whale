package com.wenhaofan._admin.metaweblog;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.model.entity.MetaweblogRelevance;

public class MetaweblogRelevanceService {

	@Inject
	private MetaweblogRelevance dao;
	
	public void add(String postId,Integer configId,Integer articleId) {
		MetaweblogRelevance relevance=new MetaweblogRelevance();
		relevance.setMetaweblogId(configId);
		relevance.setPostId(postId);
		relevance.setArticleId(articleId);
		relevance.save();
	}
	
	public MetaweblogRelevance get(Integer articleId,Integer metaweblogId) {
		SqlPara sql=dao.getSqlPara("metaweblog_relevance.get", Kv.by("articleId", articleId).set("metaweblogId", metaweblogId));
		return dao.findFirst(sql);
	}
}
