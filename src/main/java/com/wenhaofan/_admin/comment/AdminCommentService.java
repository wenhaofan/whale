package com.wenhaofan._admin.comment;

import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.AgentUser;
import com.wenhaofan.common.model.entity.Comment;

public class AdminCommentService extends BaseController {

	@Inject
	private Comment dao;
	
	public Page<Comment> page(Integer pageNumber,Integer pageSize,Boolean isAduit) {	
		SqlPara sqlPara=dao.getSqlPara("adminComment.page", Kv.by("isAduit", isAduit));
		Page<Comment> page= dao.paginate(pageNumber, pageSize, sqlPara);
		
		 for(Comment c:page.getList()) {
			 Integer beforeRow=Db.queryInt("select count(id) from comment where identify= ? and gmtCreate > ?",c.getIdentify(),c.getGmtCreate());
			 c.setPageNum(beforeRow/6);
		 }
		 return page;
	}
	
	public Ret reply(Integer toId,String content,AgentUser user) {
		
		Comment toComment = dao.findById(toId);
		
		Comment comment=new Comment();
		comment.setContent(content);
		comment.setEmail(user.getEmail());
		comment.setIdentify(toComment.getIdentify());
		comment.setName(user.getName());
		comment.setUserId(user.getId());
		comment.setWebsite(user.getWebsite());
		comment.setParentId(toId);
		comment.setToUserId(toComment.getUserId());
		
		comment.save();
		
		//发送被回复邮件
		
		return Ret.ok();
	}
	
	public Ret delete(Integer id) {
		dao.findById(id).delete();
		return Ret.ok();
	}
	
	public Ret aduit(Integer id,boolean aduit) {
		dao.findById(id).setIsAduit(aduit).update();
		return Ret.ok();
	}
	
}
