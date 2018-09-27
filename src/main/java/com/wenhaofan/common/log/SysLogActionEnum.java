package com.wenhaofan.common.log;

public enum SysLogActionEnum {
	/**
	 * 默认
	 */
	DEFAULT("defalut"),
	/**
	 * 文章
	 */
	ARTICLE("article"),
	/**
	 * 文章标签分类
	 */
	META("meta"),
	/**
	 * 上传
	 */
	UPLOAD("upload"),
	/**
	 * 用户
	 */
	USER("user"),
	/**
	 * 配置
	 */
	CONFIG("config"),
	/**
	 * 包含 metaweblog,baidu 推送
	 */
	PUSH("push");
	private String name;

	
	
	private SysLogActionEnum(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	 
	
	
}
