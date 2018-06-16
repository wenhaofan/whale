package com.wenhaofan.common.model.dto;

/**
 * 保存文件上传后的信息
 * @author Lemon
 *
 */
public class FileUploadInfo {
	private String relativePath;
	private String fileName;
	private String absolutePath;
	
	public String getAbsolutePath() {
		return absolutePath;
	}
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	public String getRelativePath() {
		return relativePath;
	}
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	
}
