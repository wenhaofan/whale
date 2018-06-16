package com.wenhaofan._admin.basic;

import java.util.List;

import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.kit.Ret;
import com.wenhaofan.common.model.entity.Kv;
import com.wenhaofan.common.model.entity.KvTypes;
import com.wenhaofan.kv.KVService;

public class BasicAdminApi extends BaseController {
	private static KVService kvService=KVService.me;
	
	public void index() {
		List<Kv> kvs=kvService.listKvByType(KvTypes.BASIC.toString());
		renderJson(Ret.ok("kvs", kvs).toJson());
	}
	
	public void addAndUpdate() {
		List<Kv> kvs=getModelList(Kv.class, "",true);
		
		kvService.saveOrUpdateList(kvs);
		
		renderJson(Ret.ok());
	}
}
