package com.wenhaofan._admin.blogroll;

import java.util.List;

import com.jfinal.kit.Ret;
import com.wenhaofan.common.annotation.SysLog;
import com.wenhaofan.common.aop.Inject;

/**
* @author 作者:范文皓
* @createDate 创建时间：2018年9月11日 下午6:19:37
*/

import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.Blogroll;

public class AdminBlogrollApi extends BaseController {
	
	@Inject
	private AdminBlogrollService blogrollService;
	
	public void get() {
		Blogroll blogroll=blogrollService.getBlogrollById(getParaToInt());
		renderJson(Ret.ok("blogroll", blogroll));
	}
	
	public void list() {
		List<Blogroll> blogrolls=blogrollService.listBlogroll();
		renderJson( Ret.ok("code", 0).set("data",blogrolls));
	}
	
	@SysLog(value="编辑友链",action="blogroll")
	public void saveOrUpdate(){
		Blogroll blogroll=getModel(Blogroll.class,"",true);
		blogrollService.saveOrUpdate(blogroll);
		renderJson(Ret.ok());
	}
	
	@SysLog(value="删除友链",action="blogroll")
	public void remove(){
		Integer id=getParaToInt(0);
		blogrollService.remove(id);
		renderJson(Ret.ok());
	}
	
 
}
