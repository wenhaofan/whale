package com.wenhaofan._admin.disk;

import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.model.entity.Disk;
import com.wenhaofan.common.model.entity.QueryDisk;

public class DiskService {

	@Inject
	private Disk dao;
	
	public void save(Disk disk) {
		disk.save();
	}
	
	public List<Disk> list(QueryDisk query){
		SqlPara sql=dao.getSqlPara("disk.list", Kv.by("query",query));
		return dao.find(sql);
	}
	/**
	 * 假删除
	 * @param pkId
	 */
	public void remove(Integer pkId) {
		dao.findById(pkId).setState(0);
	}
}
