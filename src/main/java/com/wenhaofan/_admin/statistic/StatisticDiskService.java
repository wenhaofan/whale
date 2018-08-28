package com.wenhaofan._admin.statistic;

import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;

public class StatisticDiskService {

	public Ret commentNum(Date gmtStart,Date gmtEnd) {
		if(gmtStart==null) {
			gmtStart=new Date();
		}
		SqlPara sql=Db.getSqlPara("statistic.diskNum", Kv.by("gmtStart", gmtStart).set("gmtEnd", gmtEnd));
		
		Integer num=Db.queryInt(sql.getSql(),sql.getPara());
		
		return Ret.ok("count", num);
	}
}
