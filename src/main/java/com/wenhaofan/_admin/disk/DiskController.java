package com.wenhaofan._admin.disk;

import com.wenhaofan.common.controller.BaseController;

public class DiskController extends BaseController {

	public void index() {
		setAttr("folderId", getParaToInt("p",0));
		render("disk.html");
	}
	
}
