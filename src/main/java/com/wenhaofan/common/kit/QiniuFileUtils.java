package com.wenhaofan.common.kit;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;


/**
 * 处理上传文件至七牛云的类
 * @author fwh
 *
 */
public class QiniuFileUtils {

	public static String  ak="-J6wMUs4suf31Rr3mOpI9gSALJT8S3d4Y8q6YlPs";
	public static String  sk="x2A_diIcaZ-Uiv4E66w5eO-_F8CUc2QQUOMuBXHc";
	public static String  bucket="blog";
	public static String  qiniuUrl="http://pd6htjig8.bkt.clouddn.com";
	/**
	 * 上传文件工具类 
	 * @param filePath 上传的文件的路径
	 * @param fileName 上传的文件的名称
	 * @param qiniuName 在七牛云上保存的名称,如果为空则随机命名
	 * @return
	 */
	public static Ret uploadFile(String filePath,String fileName,String qiniuName) {
		Ret ret=uploadFile(new File(filePath+File.separator+fileName), qiniuName, ak ,sk,bucket);
		if(ret.isFail()) {
			return ret;
		}
		return ret.set("qiniuurl", qiniuUrl).set("url",qiniuUrl+"/"+ret.getStr("qiniuFileName"));
	}
	//http://p2lno0vsw.bkt.clouddn.com+"/"+lemonim-avatar.jpg
	public static void main(String[] args) {
		System.out.println(uploadFile("H:\\Eclipse\\CompanyWork\\lemonim2\\src\\main\\webapp\\layui\\images","0.jpg","lemonim-avatar.jpg"));
	}
	
	@SuppressWarnings("unused")
	private static String gnerateFileName(String fileType) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String strDate=sdf.format(new Date());
		//生成当前时间+uuid作为文件名称  
		String uuid =strDate+UUID.randomUUID().toString().replaceAll("-","");  
		String fileName=strDate+uuid+"."+fileType;
		return fileName;
	}
	
	/**
	 * 上传文件至七牛云
	 * @param file 需要上传的文件
	 * @param fileName 保存在七牛云的文件的文件名
	 * @return true为成功  false为失败 
	 */
	public static Ret uploadFile(File file, String fileName,String ak,String sk,String bucket) {
		
		String localFilePath = file.getAbsolutePath();
		// 如果未指定文件名则从file中获取文件名
		String key = null;
		
		if (fileName != null) {
			key = fileName;
		}else{
			key=file.getName();
		}
		
		return uploadFile(ak,sk,bucket,localFilePath,key);
	}


	/**
	 * 上传文件至七牛云
	 * @param accessKey 七牛云的ak
	 * @param secretKey 七牛云的sk
	 * @param bucket 七牛云空间名称
	 * @param localPath 上传文件的本地路径
	 * @param key 该文件在七牛云空间保存的名称
	 * @return true为成功上传，false为上传失败
	 */
	public static   Ret uploadFile(String accessKey,String secretKey,String bucket,String localPath,String key){
		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.autoZone());
		UploadManager uploadManager = new UploadManager(cfg);

		
		// ...生成上传凭证，然后准备上传
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);

		try {
			  uploadManager.put(localPath, key, upToken);
			return Ret.ok("qiniuFileName", key);
		} catch (QiniuException ex) {
			ex.printStackTrace();
		}

		return Ret.fail();
	}


	
	
}
