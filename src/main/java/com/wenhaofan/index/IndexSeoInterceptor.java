package com.wenhaofan.index;

import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.wenhaofan.common.interceptor.BaseSeoInterceptor;
import com.wenhaofan.common.model.entity.BasicConfig;

public class IndexSeoInterceptor extends BaseSeoInterceptor {

	@Override
	public void indexSeo(Invocation inv) {
		Controller c=inv.getController();
		BasicConfig config=c.getAttr("basicConfig");
		setSeoKeyWords(c,config.getKeywords());
		setSeoTitle(c,config.getTitle());
		setSeoDescr(c, config.getDescription());
		
	}

	@Override
	public void otherSeo(Invocation inv) {
		
	}
}
