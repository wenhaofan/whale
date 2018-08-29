package com.wenhaofan.common.model.entity.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseUser<M extends BaseUser<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public M setPwd(java.lang.String pwd) {
		set("pwd", pwd);
		return (M)this;
	}
	
	public java.lang.String getPwd() {
		return getStr("pwd");
	}

	public M setAge(java.lang.Integer age) {
		set("age", age);
		return (M)this;
	}
	
	public java.lang.Integer getAge() {
		return getInt("age");
	}

	public M setSex(java.lang.Boolean sex) {
		set("sex", sex);
		return (M)this;
	}
	
	public java.lang.Boolean getSex() {
		return get("sex");
	}

	public M setUkPhone(java.lang.String ukPhone) {
		set("ukPhone", ukPhone);
		return (M)this;
	}
	
	public java.lang.String getUkPhone() {
		return getStr("ukPhone");
	}

	public M setUkEmail(java.lang.String ukEmail) {
		set("ukEmail", ukEmail);
		return (M)this;
	}
	
	public java.lang.String getUkEmail() {
		return getStr("ukEmail");
	}

	public M setLevel(java.lang.Integer level) {
		set("level", level);
		return (M)this;
	}
	
	public java.lang.Integer getLevel() {
		return getInt("level");
	}

	public M setIsValid(java.lang.Integer isValid) {
		set("isValid", isValid);
		return (M)this;
	}
	
	public java.lang.Integer getIsValid() {
		return getInt("isValid");
	}

	public M setUkAccount(java.lang.String ukAccount) {
		set("ukAccount", ukAccount);
		return (M)this;
	}
	
	public java.lang.String getUkAccount() {
		return getStr("ukAccount");
	}

	public M setRegisterTime(java.util.Date registerTime) {
		set("registerTime", registerTime);
		return (M)this;
	}
	
	public java.util.Date getRegisterTime() {
		return get("registerTime");
	}

}
