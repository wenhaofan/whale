package com.wenhaofan._admin.comment;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.Comment;

public class AdminCommentService extends BaseController {

	@Inject
	private Comment dao;
	
	public Page<Comment> page(Integer pageNumber,Integer pageSize,QueryComment query) {	
		SqlPara sqlPara=dao.getSqlPara("comment.page", Kv.by("query", query));
		return dao.paginate(pageNumber, pageSize, sqlPara);
	}
	
}
