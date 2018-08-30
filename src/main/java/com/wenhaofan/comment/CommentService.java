package com.wenhaofan.comment;

import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wenhaofan.agentUser.AgentUserService;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.exception.MsgException;
import com.wenhaofan.common.model.entity.AgentUser;
import com.wenhaofan.common.model.entity.Comment;

public class CommentService {

	@Inject
	private static  Comment dao;
	@Inject
	private static AgentUserService agentUserService;
	
	public void save(Comment comment,String cookie) {
		if(cookie==null) {
			throw new MsgException("什么鬼！");
		}
		
		if(StrKit.isBlank(comment.getContent())&&comment.getContent().length()>512) {
			throw new MsgException("评论内容需大于0且小于等于512字！");
		}
		
		if(StrKit.isBlank(comment.getEmail())) {
			throw new MsgException("填个邮箱吧！");
		}
		 
		if(StrKit.isBlank(comment.getName())) {
			throw new MsgException("请留下你的姓名！");
		}
		
		if(StrKit.isBlank(comment.getIdentify())) {
			throw new MsgException("你评论的是哪个文章啊！");
		}
		
		if(comment.getParentId()==null) {
			comment.setParentId(0);
		}
		
		AgentUser agentUser=agentUserService.get(cookie);

		if(agentUser==null) {
			throw new MsgException("系统繁忙,请刷新后再试！");
		}
		agentUser.setEmail(comment.getEmail());
		agentUser.setName(comment.getName());
		agentUser.setWebsite(comment.getWebsite());
		agentUser.update();
		
		comment.setUserId(agentUser.getId());
		//向被回复人发送通知邮件
		sendReplyEmail(comment);
		
		
		
		comment.save();
	}


	private void sendReplyEmail(Comment comment) {
		if(comment.getParentId()!=null) {
			Comment replyComment=this.get(comment.getParentId());
			if(replyComment!=null) {
				Integer userId=replyComment.getUserId();
				AgentUser replayAgentUser=agentUserService.get(userId);
				if(replayAgentUser!=null) {
					//发送通知邮件
				}
			}
		}
	}
	
	
	public Page<Comment> page(Integer pageNum,Integer pageSize,String identify){
		SqlPara sql=dao.getSqlPara("comment.page", identify);
		return dao.paginate(pageNum, pageSize,sql);
	}
	
	public Comment get(Integer id) {
		return dao.findById(id);
	}
	
	public List<Comment> listRecent(){
		 List<Comment> comments=dao.find("select * from comment order by gmtCreate desc limit 6");
		 for(Comment c:comments) {
			 Integer beforeRow=Db.queryInt("select count(id) from comment where identify= ? and gmtCreate > ?",c.getIdentify(),c.getGmtCreate());
			 c.setPageNum(beforeRow/6);
		 }
		 return comments;
	}
}
