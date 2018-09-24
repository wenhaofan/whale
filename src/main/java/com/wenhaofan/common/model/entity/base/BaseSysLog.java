package com.wenhaofan.common.model.entity.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSysLog<M extends BaseSysLog<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setAction(java.lang.String action) {
		set("action", action);
		return (M)this;
	}
	
	public java.lang.String getAction() {
		return getStr("action");
	}

	public M setContent(java.lang.String content) {
		set("content", content);
		return (M)this;
	}
	
	public java.lang.String getContent() {
		return getStr("content");
	}

	public M setData(java.lang.String data) {
		set("data", data);
		return (M)this;
	}
	
	public java.lang.String getData() {
		return getStr("data");
	}

	public M setGmtCreate(java.util.Date gmtCreate) {
		set("gmtCreate", gmtCreate);
		return (M)this;
	}
	
	public java.util.Date getGmtCreate() {
		return get("gmtCreate");
	}

	public M setIp(java.lang.String ip) {
		set("ip", ip);
		return (M)this;
	}
	
	public java.lang.String getIp() {
		return getStr("ip");
	}

	public M setUserId(java.lang.Integer userId) {
		set("userId", userId);
		return (M)this;
	}
	
	public java.lang.Integer getUserId() {
		return getInt("userId");
	}

	public M setLevel(java.lang.Integer level) {
		set("level", level);
		return (M)this;
	}
	
	public java.lang.Integer getLevel() {
		return getInt("level");
	}

}
