package com.wenhaofan._admin.disk;

public enum DiskType {
	FOLDER,JPG,ZIP,PNG,TXT,HTML,JAVA,MP4,MP3,AVI;
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}
