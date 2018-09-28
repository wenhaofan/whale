package com.wenhaofan._admin.sysLog;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.model.entity.SysLog;

public class AdminSysLogService {

	@Inject
	private SysLog dao;
	
	public Page<SysLog> listRecent(Integer pageNum,Integer pageSize){
		SqlPara sqlPara=dao.getSqlPara("sysLog.listRecent");
		return dao.paginate(pageNum, pageSize,sqlPara);
	}
	
	public Page<SysLog> page(Integer pageNum,Integer limit,QuerySysLog query){
		SqlPara sqlPara=dao.getSqlPara("sysLog.page", Kv.by("query", query));
		return dao.paginate(pageNum, limit, sqlPara);
	}
}
