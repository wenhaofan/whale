package com.wenhaofan.meta;

import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.exception.MsgException;
import com.wenhaofan.common.kit.ListKit;
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

	
	public Meta saveOrUpdate(Meta meta) {
		if (meta == null) {
			throw new MsgException("资源不能为空！");
		}
		if (StrKit.isBlank(meta.getName())) {
			throw new MsgException("数据name不能为空！");
		}
		if (StrKit.isBlank(meta.getType())) {
			throw new MsgException("数据type不能为空！");
		}

		if (meta.getId() == null) {
			meta.save();
			return meta;
		} else {
			meta.update();
			return mdao.findById(meta.getId());
		}

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

	/**
	 * 根据类型和名称获取资源
	 * 
	 * @param type
	 * @param name
	 * @return
	 */
	public Meta getMeta(Meta meta) {

		if (meta.getId() != null) {
			return mdao.findById(meta.getId());
		}

		if (StrKit.notBlank(meta.getType(), meta.getName())) {
			String sql = "select  *  from meta where type = ? and mname = ?";
			return mdao.findFirst(sql, meta.getType(), meta.getName());
		}
		return null;
	}

	/**
	 * 保存多条数据
	 * 
	 * @param metas
	 * @param cid
	 */
	public void saveMetas(List<Meta> metas, Integer cid) {
		if (null == cid) {
			throw new MsgException("数据关联id不能为空!");
		}

		if (ListKit.isBlank(metas)) {
			throw new MsgException("数据不能为空!");
		}

		for (int i = 0, size = metas.size(); i < size; i++) {
			save(metas.get(i));
			saveRelevancy(cid, metas.get(i));
		}

	}

	/**
	 * 保存资源
	 * 
	 * @param meta
	 */
	public void save(Meta meta) {
		if (StrKit.isBlank(meta.getName())) {
			throw new MsgException("数据name不能为空！");
		}
		if (StrKit.isBlank(meta.getType())) {
			throw new MsgException("数据type不能为空！");
		}

		Meta oldMeta = getMeta(meta);

		if (oldMeta != null) {
			meta.setId(oldMeta.getId());
			return;
		}

		if (!meta.save()) {
			throw new MsgException("保存失败！");
		}
	}

	/**
	 * 添加资源和内容的关联
	 * 
	 * @param cid
	 * @param meta
	 */
	public void saveRelevancy(Integer cid, Meta meta) {
		// 获取资源id
		Integer mid = meta.getId();
		// 如果资源id不为null则保存该资源和cid的关联
		if (mid != null) {

			Integer count = rdao.findFirst("select count(id) as count from relevancy where mid=? and cid=?", mid, cid)
					.getInt("count");
			// 不存在关联才创建关联
			if (count == 0) {
				Relevancy rel = new Relevancy();
				rel.setCid(cid);
				rel.setMid(mid);
				rel.save();
			}
		}
	}

	/**
	 * 删除
	 * 
	 * @param meta
	 */
	public void delete(Meta meta) {
		if (meta == null) {
			throw new MsgException("资源不能为空！");
		}
		meta = getMeta(meta);

		if (meta == null) {
			throw new MsgException("资源不存在！");
		}

		mdao.deleteById(meta.getId());
	}

	/**
	 * 删除数据 以及和数据的关联,删除数据关联暂未实现
	 * 
	 * @param mid
	 * @param meta
	 */
	public void delete(Integer cid, Meta meta) {

		if (cid == null) {
			throw new MsgException("内容id不能为空！");
		}

		if (meta == null) {
			throw new MsgException("资源为空！");
		}

		meta = getMeta(meta);

		if (meta == null) {
			throw new MsgException("资源不存在！");
		}

		// 首先删除资源
		delete(meta);
		// 然后删除资源和内容的关联

		Db.update("delete from relevancy where mid= ?", meta.getId());
	}

	/**
	 * 删除和素材的关联
	 * 
	 * @param mid
	 * @param meta
	 */
	public void deleteRelevancy(Integer cid) {
		if (cid == null) {
			throw new MsgException("内容id不能为空！");
		}
		// 删除资源和内容的关联
		Db.update("delete from relevancy where cid= ?  ", cid);
	}

	public Meta get(Integer id) {
		return mdao.findById(id);
	}
}
