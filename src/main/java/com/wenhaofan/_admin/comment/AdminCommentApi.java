package com.wenhaofan._admin.comment;

import com.jfinal.plugin.activerecord.Page;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.kit.Ret;
import com.wenhaofan.common.model.entity.Comment;

public class AdminCommentApi  extends BaseController{

	@Inject
	private AdminCommentService service;
	
	
	public void page() {
		QueryComment query=getBean(QueryComment.class,"",true);
		Integer pageNum = getParaToInt("page");
		Integer limit = getParaToInt("limit");
		Page<Comment> page=service.page(pageNum, limit, query);
		Ret ret = Ret.ok().set("code", 0).set("data", page.getList()).set("count", page.getTotalRow());
		renderJson(ret);
	}
	
	public void reply() {
		renderJson(service.reply(getParaToInt("toId"), getPara("content"), getAgentUser()));
	}
	
	public void delete() {
		renderJson(service.delete(getParaToInt("toId")));
	}
	
	public void aduit() {
		renderJson(service.aduit(getParaToInt("toId"),getParaToBoolean("aduit")));
	}
	
}
