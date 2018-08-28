package com.wenhaofan._admin.statistic;

import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;

public class StatisticCommentService {

	public Ret commentNum(Date gmtStart,Date gmtEnd) {
		if(gmtStart==null) {
			gmtStart=new Date();
		}
		SqlPara sql=Db.getSqlPara("statistic.commentNum", Kv.by("gmtStart", gmtStart).set("gmtEnd", gmtEnd));
		
		Integer articleNum=Db.queryInt(sql.getSql(),sql.getPara());
		
		return Ret.ok("count", articleNum);
	}
}
