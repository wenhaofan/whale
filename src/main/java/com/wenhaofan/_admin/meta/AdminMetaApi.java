package com.wenhaofan._admin.meta;

import com.jfinal.kit.Ret;
import com.wenhaofan.common.annotation.SysLog;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.model.entity.Meta;

/**
 * 分类管理的控制器
 * 
 * @author fwh
 *
 */
public class AdminMetaApi  extends BaseController {

	@Inject
	private AdminMetaService metaService;
	
	@SysLog(value="删除分类或标签",action="meta")
	public void remove() {
		Integer id = getParaToInt(0);
		Meta meta=new Meta();
		meta.setId(id);
		metaService.delete(meta);
		renderJson(Ret.ok());
	}
	
	@SysLog(value="编辑分类或标签",action="meta")
	public void update() {
		Meta meta=getModel(Meta.class,"",true);
		metaService.saveOrUpdate(meta);
		renderJson(Ret.ok().toJson());
	}
	
	@SysLog(value="添加分类或标签",action="meta")
	public void add() {
		Meta meta=getModel(Meta.class,"",true);
		metaService.saveOrUpdate(meta);
		renderJson(Ret.ok().toJson());
	}

}
