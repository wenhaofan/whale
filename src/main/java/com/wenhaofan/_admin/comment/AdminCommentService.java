package com.wenhaofan._admin.comment;

import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wenhaofan.comment.CommentService;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.AgentUser;
import com.wenhaofan.common.model.entity.Comment;

public class AdminCommentService extends BaseController {

	@Inject
	private Comment dao;
	
	@Inject
	private CommentService frontCommentService;
 
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
		comment.setIsAduit(true);
		comment.save();
	
		new Thread(()-> {
			frontCommentService.sendReplyEmail(comment);
		}).start();
		//向被回复人发送通知邮件

		return Ret.ok();
	}
	
	
	
	
	public Ret delete(Integer id) {
		dao.findById(id).delete();
		return Ret.ok();
	}
	
	public Ret aduit(Integer id,boolean aduit) {
		Comment comment=dao.findById(id);
		comment.setIsAduit(aduit).update();
		
		if(comment.getParentId()==null) {
			return Ret.ok();
		}

		new Thread(()-> {
			frontCommentService.sendReplyEmail(comment);
		}).start();
		
		return Ret.ok();
	}
	
	public void setInPageNum(Comment c) {
		 Integer beforeRow=Db.queryInt("select count(id) from comment where identify= ? and gmtCreate > ?",c.getIdentify(),c.getGmtCreate());
		 c.setPageNum(beforeRow/6);
	}
	public Comment get(Integer id) {
		return dao.findById(id);
	}
}
