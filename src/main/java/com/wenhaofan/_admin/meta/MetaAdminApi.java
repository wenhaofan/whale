package com.wenhaofan._admin.meta;

import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.wenhaofan.common.model.entity.Meta;
import com.wenhaofan.meta.MetaService;

/**
 * 分类管理的控制器
 * 
 * @author fwh
 *
 */
public class MetaAdminApi extends Controller {

	private MetaService metaService=MetaService.me;
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
