package com.wenhaofan.common.uplod;


import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;
import com.wenhaofan.common.model.dto.FileUploadInfo;


/**
 * 文件上传控制器
 * @author fwh
 *
 *
 */
public class FileUploadApi extends Controller {

	private UploadService service=UploadService.me;

	/**
	 * 文件上传处理
	 */
	public void index() {
		String uploadType=getPara(0);
		UploadFile	uf = getFile("upfile", UploadService.tempPath);
		FileUploadInfo info=service.fileUpload(uploadType, uf);
		//隐藏绝对路径
		info.setAbsolutePath("");
		renderJson(Ret.ok("info", info));
	}
}



