package com.wenhaofan._admin.disk;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.upload.UploadFile;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.kit.QiniuFileUtils;
import com.wenhaofan.common.kit.Ret;
import com.wenhaofan.common.model.entity.Disk;
import com.wenhaofan.common.model.entity.QueryDisk;

public class DiskService {

	@Inject
	private Disk dao;
	
	public void save(Disk disk) {
		if(disk.getParentId()==null) disk.setParentId(0);
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
	
	/**
	 * 获取上传的文件名称,避免重名 如果重名则添加(num)
	 * @param fileName
	 * @return
	 */
	public String getFileName(String fileName,Integer parentId) {
		
		String[] arr=fileName.split("\\.");
		
		String temp=arr.length>=2?arr[0]:fileName;
		
		
		SqlPara sql=dao.getSqlPara("disk.check", Kv.by("fileName", temp).set("parentId", parentId));
		List<Disk> list=dao.find(sql);
		
		if(list.isEmpty()) {
			return fileName;
		}
		
		
		
		if(arr.length>=2) {
			return arr[0]+"("+list.size()+")."+arr[arr.length-1];
		}
		
		return fileName+"("+list.size()+")";
	}
	
	
	private static final String basePath = "/upload/";
 

	
	public Disk upload(UploadFile uf,Integer parentId) {
 
		String fileName=getFileName(uf.getFileName(),parentId);
		
		String relativePath=basePath+File.separator+"disk";
		String absolutePath= PathKit.getWebRootPath()+relativePath;
		 
		saveOriginalFileToTargetFile(uf.getFile(),absolutePath,fileName);
	
		Disk disk=new Disk();
		disk.setName(fileName);
		disk.setGmtCreate(new Date());
		
		String[] arr=fileName.split(".");
		if(arr.length>=2) {
			disk.setType(arr[arr.length-1]);
		}
		
		//上传至七牛云
		Ret ret=QiniuFileUtils.uploadFile(absolutePath, fileName, fileName);
		String url=ret.getStr("url");
		disk.setUrl(url);
		disk.setParentId(parentId);
		disk.setSize(uf.getFile().length());
		disk.save();
		return disk;
	}
	
	private void saveOriginalFileToTargetFile(File originalFile, String targetFilePath,String targetFileName) {
		File file=new File(targetFilePath);
		if(!file.exists()) {
			file.mkdirs();
		}
		saveOriginalFileToTargetFile(originalFile, targetFilePath+File.separator+targetFileName);
		originalFile.delete();
	}

	/**
	 * 目前使用 File.renameTo(targetFileName) 的方式保存到目标文件，
	 * 如果 linux 下不支持，或者将来在 linux 下要跨磁盘保存，则需要
	 * 改成 copy 文件内容的方式并删除原来文件的方式来保存
	 */
	private void saveOriginalFileToTargetFile(File originalFile, String targetFile) {
		originalFile.renameTo(new File(targetFile));
	}

}
