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
	
	public void get() {
		renderJson(Ret.ok("disk", service.get(getParaToInt())));
	}
	
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
		service.createFolder(disk);
		renderJson(Ret.ok("disk",disk));
	}
	
	public void remove() {
		service.remove(getParaToInt());
		renderJson(Ret.ok());
	}

	public void update() {
		Disk disk=getModel(Disk.class,"",true);
		renderJson(Ret.ok("disk", service.update(disk)	));
 	}
	
	public void listFolderChain() {
		Integer folderId=getParaToInt();
		renderJson(Ret.ok("diskList", service.listFolderChain(folderId)));
	}
}
