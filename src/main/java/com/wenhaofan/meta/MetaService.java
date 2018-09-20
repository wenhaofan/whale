package com.wenhaofan.meta;

import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.exception.MsgException;
import com.wenhaofan.common.model.entity.Meta;
import com.wenhaofan.common.model.entity.Relevancy;

public class MetaService {

	@Inject
	private static Meta mdao;
	@Inject
	private static Relevancy rdao;


	public List<Meta> listByCId(Integer id, String type) {
		if (id == null) {
			throw new MsgException("内容id不能为空！");
		}
		Kv kv=Kv.create().set("cid", id).set("type", type);
		SqlPara sql=mdao.getSqlPara("meta.listByArticleId",kv);
		return mdao.find(sql);
	}

	/**
	 * 根据类型查询项目列表
	 *
	 * @param type
	 *            类型，tag or category
	 */
	public List<Meta> listMeta(String type) {
		if (StrKit.notBlank(type)) {
			SqlPara sql=mdao.getSqlPara("meta.list",Kv.by("type", type));
			return mdao.find(sql);
		}
		return null;
	}

	


	
	/**
	 * 根据类型和名字查询项
	 *
	 * @param type
	 *            类型，tag or category
	 * @param name
	 *            类型名
	 */
	public Meta getMetaAndCount(String type, String name) {
		if (StrKit.notBlank(type, name)) {
			String sql = "select a.*, count(b.id) as count from meta a left join `relevancy` b on a.id = b.cid "
					+ "where a.type = ? and a.mname = ?";
			return mdao.findFirst(sql);
		}
		return null;
	}


	public Meta get(Integer id) {
		return mdao.findById(id);
	}
}
