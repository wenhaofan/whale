package com.wenhaofan._admin.comment;

import com.jfinal.plugin.activerecord.Page;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.kit.Ret;
import com.wenhaofan.common.model.entity.Comment;

public class AdminCommentApi  extends BaseController{

	@Inject
	private AdminCommentService service;
	
	
	public void count() {
		QueryComment query=getBean(QueryComment.class,"",true);
		
		Page<Comment> page=service.page(getParaToInt("p", 1), getParaToInt("s",10), query);

		Ret ret = Ret.ok().set("code", 0).set("data", page.getList()).set("count", page.getTotalRow());
		renderJson(ret.toJson());
	}
	
	public void reply() {
		
	}
	
	public void delete() {
		
	}
	
	public void aduit() {
		 
	}
}
