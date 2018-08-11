package com.wenhaofan._admin.disk;

import java.util.List;

import com.jfinal.kit.Ret;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.Disk;
import com.wenhaofan.common.model.entity.QueryDisk;

public class DiskApi extends BaseController{

	@Inject
	private DiskService service;
	
	
	public void index() {
		QueryDisk query=getBean(QueryDisk.class,"",true);
		
		List<Disk> diskList=service.list(query);
		
		renderJson(Ret.ok("diskList", diskList));
	}
	
	public void upload() {
		
	}
	
	public void createFolder() {
		Disk disk=getModel(Disk.class,"",true);
		service.save(disk);
		renderJson(Ret.ok("disk",disk));
	}
	
	public void remove() {
		service.remove(getParaToInt());
		renderJson(Ret.ok());
	}

	
}
