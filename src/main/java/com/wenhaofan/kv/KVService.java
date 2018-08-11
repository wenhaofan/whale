package com.wenhaofan.kv;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wenhaofan.common.exception.MsgException;
import com.wenhaofan.common.kit.Arraykit;
import com.wenhaofan.common.kit.StrKit;
import com.wenhaofan.common.model.entity.Kv;

public class KVService {

	private static Kv dao=new Kv();
	public static KVService me=new KVService();
	
	public void  saveOrUpdateList(List<Kv> kvs) {
		Kv kv=null;
		for (int i = 0,size = kvs.size();i<size; i++) {
			kv=kvs.get(i);
			saveOrUpdate(kv);
		}
	}
	
	/**
	 * 添加一条记录
	 * @param kv
	 */
	public void saveOrUpdate(Kv kv) {
		
		if(StrKit.isBlank(kv.getK())) {
			throw new MsgException("k值不能为空！");
		}
		if(StrKit.isBlank(kv.getV())) {
			throw new MsgException("v值不能为空！");
		}
		
		Kv oldKv=get(kv);
		
		if(oldKv!=null) {
			kv.setId(oldKv.getId());
			kv.update();
		}else {
			kv.save();
		}
	}
	
	public Kv get(Kv kv) {
		com.jfinal.kit.Kv data=com.jfinal.kit.Kv.create().set("kv",kv);
		SqlPara sql=dao.getSqlPara("adminKv.get", data);
		return dao.findFirst(sql);
	}
	
	public List<Kv> listKvByType(String type){
		
		return dao.find("select * from kv where type = ?",type);
	}
	
	/**
	 * 获取多个k对应的值
	 * @param ks
	 * @return
	 */
	public List<Kv> listKv(String... ks){
		
		if(Arraykit.isBlank(ks)) {
			throw new MsgException("v值不能为空！");
		}
		
		List<Kv> kvs=new ArrayList<>();
		Kv kv=null;
		for (int i = 0,size = ks.length;i<size; i++) {
			kv=getKV(ks[i]);
			if(kv==null) {
				continue;
			}
			kvs.add(kv);
		}
		return kvs;
	}
	
	public Kv getKV(String k) {
		if(StrKit.isBlank(k)) {
			throw new MsgException("k值不能为空！");
		}
		return dao.findFirst("select * from kv where k=?",k);
	}
	
	public void delete(String k) {
		if(StrKit.notBlank(k)) {
			throw new MsgException("k值不能为空！");
		}
		
		Db.update("delete from kv where k=?",k);
	}
}
