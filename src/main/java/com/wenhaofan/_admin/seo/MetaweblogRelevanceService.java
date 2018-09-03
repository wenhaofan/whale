package com.wenhaofan._admin.seo;

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
	
	public MetaweblogRelevance get(Integer articleId,Integer configId) {
		return dao.findFirst("select * from metaweblog_relevance where articleId=? and metaweblogId=?",articleId,configId);
	}
}
