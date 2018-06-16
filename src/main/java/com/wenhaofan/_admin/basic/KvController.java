package com.wenhaofan._admin.basic;

import java.util.List;

import com.jfinal.kit.Ret;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.Kv;
import com.wenhaofan.common.model.entity.KvTypes;
import com.wenhaofan.kv.KVService;

/**
 * 博主信息管理的类
 * @author fwh
 *
 */
public class KvController extends BaseController{

	private static KVService kvService=KVService.me;
	
	public void index() {
		String type=getPara(0);
		List<Kv> kvs=kvService.listKvByType(type);
		renderJson(Ret.ok("kvs", kvs).toJson());
	}
	
	public void save() {
		List<Kv> kvs=getModelList(Kv.class,"",true);
		kvService.saveOrUpdateList(kvs);
		renderJson(Ret.ok());
	}
	
}
