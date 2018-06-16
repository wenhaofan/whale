package com.wenhaofan.util;

import java.util.Date;

public class SMS {

	private Integer type;
	private String status;
	private boolean isRead;
	private String address;
	private Date date;
	private String body;
	
	
	
	
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "SMS [type=" + type + ", status=" + status + ", isRead=" + isRead + ", address=" + address + ", date="
				+ date + ", body=" + body + "]";
	}

	
	
}
