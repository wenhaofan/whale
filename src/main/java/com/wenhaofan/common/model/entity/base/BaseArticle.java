package com.wenhaofan.common.model.entity.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseArticle<M extends BaseArticle<M>> extends Model<M> implements IBean {

	public M setPkId(java.lang.Integer pkId) {
		set("pkId", pkId);
		return (M)this;
	}
	
	public java.lang.Integer getPkId() {
		return getInt("pkId");
	}

	public M setTitle(java.lang.String title) {
		set("title", title);
		return (M)this;
	}
	
	public java.lang.String getTitle() {
		return getStr("title");
	}

	public M setContent(java.lang.String content) {
		set("content", content);
		return (M)this;
	}
	
	public java.lang.String getContent() {
		return getStr("content");
	}

	public M setHtmlTitle(java.lang.String htmlTitle) {
		set("htmlTitle", htmlTitle);
		return (M)this;
	}
	
	public java.lang.String getHtmlTitle() {
		return getStr("htmlTitle");
	}

	public M setGmtCreate(java.util.Date gmtCreate) {
		set("gmtCreate", gmtCreate);
		return (M)this;
	}
	
	public java.util.Date getGmtCreate() {
		return get("gmtCreate");
	}

	public M setPv(java.lang.Integer pv) {
		set("pv", pv);
		return (M)this;
	}
	
	public java.lang.Integer getPv() {
		return getInt("pv");
	}

	public M setThumbImg(java.lang.String thumbImg) {
		set("thumbImg", thumbImg);
		return (M)this;
	}
	
	public java.lang.String getThumbImg() {
		return getStr("thumbImg");
	}

	public M setState(java.lang.Integer state) {
		set("state", state);
		return (M)this;
	}
	
	public java.lang.Integer getState() {
		return getInt("state");
	}

	public M setIsOriginal(java.lang.Integer isOriginal) {
		set("isOriginal", isOriginal);
		return (M)this;
	}
	
	public java.lang.Integer getIsOriginal() {
		return getInt("isOriginal");
	}

	public M setIsTop(java.lang.Integer isTop) {
		set("isTop", isTop);
		return (M)this;
	}
	
	public java.lang.Integer getIsTop() {
		return getInt("isTop");
	}

	public M setGmtModified(java.util.Date gmtModified) {
		set("gmtModified", gmtModified);
		return (M)this;
	}
	
	public java.util.Date getGmtModified() {
		return get("gmtModified");
	}

	public M setIdentify(java.lang.String identify) {
		set("identify", identify);
		return (M)this;
	}
	
	public java.lang.String getIdentify() {
		return getStr("identify");
	}

}
