package com.wenhaofan._admin.disk;

import java.util.List;

import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.Disk;
import com.wenhaofan.common.model.entity.QueryDisk;
import com.wenhaofan.common.uplod.UploadService;

public class DiskApi extends BaseController{

	@Inject
	private DiskService service;
	
	
	public void list() {
		QueryDisk query=getBean(QueryDisk.class,"",true);
		
		List<Disk> diskList=service.list(query);
		
		renderJson(Ret.ok("list", diskList));
	}
	
	public void upload() {
		UploadFile	uploadFile = getFile("upfile", UploadService.tempPath);
		Integer parentId=getParaToInt("parentId",0);
		Disk disk=service.upload(uploadFile, parentId);
		renderJson(Ret.ok("disk", disk));
	}
	
	public void createFolder() {
		Disk disk=getModel(Disk.class,"",true);
		disk.setType(DiskType.FOLDER.toString());
		service.save(disk);
		
		renderJson(Ret.ok("disk",disk));
	}
	
	public void remove() {
		service.remove(getParaToInt());
		renderJson(Ret.ok());
	}

	
	
}
