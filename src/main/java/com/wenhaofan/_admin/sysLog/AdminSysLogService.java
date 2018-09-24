package com.wenhaofan._admin.sysLog;

import com.jfinal.plugin.activerecord.Page;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.model.entity.SysLog;

public class AdminSysLogService {

	@Inject
	private SysLog dao;
	
	public Page<SysLog> listRecent(Integer pageNum,Integer pageSize){
		return dao.paginate(pageNum, pageSize, "select * from sys_log ","order by gmtCreate");
	}
}
