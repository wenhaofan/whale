package com.wenhaofan._admin.meta;

import com.jfinal.kit.Ret;
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
	/**
	 * 删除
	 */

	public void remove() {
		Integer id = getParaToInt(0);
		Meta meta=new Meta();
		meta.setId(id);
		metaService.delete(meta);
		renderJson(Ret.ok());
	}

	public void update() {
		Meta meta=getModel(Meta.class,"",true);
		metaService.saveOrUpdate(meta);
		renderJson(Ret.ok().toJson());
	}

	/**
	 * 执行添加操作的控制器
	 */
	public void add() {
		Meta meta=getModel(Meta.class,"",true);
		metaService.saveOrUpdate(meta);
		renderJson(Ret.ok().toJson());
	}

}
