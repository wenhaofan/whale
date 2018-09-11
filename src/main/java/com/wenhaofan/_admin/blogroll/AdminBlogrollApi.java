package com.wenhaofan._admin.blogroll;

import java.util.List;

import com.jfinal.kit.Ret;
import com.wenhaofan.common.aop.Inject;

/**
* @author 作者:范文皓
* @createDate 创建时间：2018年9月11日 下午6:19:37
*/

import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.Blogroll;

public class AdminBlogrollApi extends BaseController {
	
	@Inject
	private BlogrollService blogrollService;
	
	public void list() {
		List<Blogroll> blogrolls=blogrollService.listBlogroll();
		renderJson( Ret.ok("code", 0).set("data",blogrolls));
	}
	
	public void add(){
		Blogroll blogroll=getModel(Blogroll.class,"",true);
		blogrollService.save(blogroll);
		renderJson(Ret.ok());
	}
	
	public void remove(){
		Integer id=getParaToInt(0);
 
		blogrollService.remove(id);
		renderJson(Ret.ok());
	}
	
	public void update() {
		Blogroll blogroll=getModel(Blogroll.class,"",true);
		blogrollService.update(blogroll);
		renderJson(Ret.ok());
	}
	
}
