package com.wenhaofan._admin.disk;

import java.util.List;

import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;
import com.wenhaofan._admin.common.annotation.SysLog;
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
	
	@SysLog(value="文件系统上传",action="disk")
	public void upload() {
		UploadFile	uploadFile = getFile("upfile", UploadService.tempPath);
		Integer parentId=getParaToInt("parentId",0);
		Disk disk=service.upload(uploadFile, parentId);
		renderJson(Ret.ok("disk", disk));
	}
	
	@SysLog(value="创建文件夹",action="disk")
	public void createFolder() {
		Disk disk=getModel(Disk.class,"",true);
		disk.setType(DiskType.FOLDER.toString());
		service.createFolder(disk);
		renderJson(Ret.ok("disk",disk));
	}
	
	@SysLog(value="删除文件夹或文件",action="disk")
	public void remove() {
		service.remove(getParaToInt());
		renderJson(Ret.ok());
	}
	
	@SysLog(value="更新文件夹或文件",action="disk")
	public void update() {
		Disk disk=getModel(Disk.class,"",true);
		renderJson(Ret.ok("disk", service.update(disk)	));
 	}
	
	public void listFolderChain() {
		Integer folderId=getParaToInt();
		renderJson(Ret.ok("diskList", service.listFolderChain(folderId)));
	}
}
