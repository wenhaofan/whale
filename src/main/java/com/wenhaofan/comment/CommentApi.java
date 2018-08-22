package com.wenhaofan.comment;


import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.AgentUser;
import com.wenhaofan.common.model.entity.Comment;

public class CommentApi extends BaseController {

	@Inject
	private CommentService service;
	
	public void save() {
		Comment comment=getModel(Comment.class,"",true);
		AgentUser agentUser=getAgentUser();
		service.save(comment,agentUser.getCookie());
		renderJson(Ret.ok());
	}

	public void page() {
		Page<Comment> commentPage=service.page(getParaToInt("p",1), getParaToInt("s",10), getPara("identify"));
		renderJson(Ret.ok("page", commentPage));
	}

}
