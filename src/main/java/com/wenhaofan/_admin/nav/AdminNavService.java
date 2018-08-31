package com.wenhaofan._admin.nav;

import com.jfinal.kit.Ret;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.model.entity.Nav;

public class AdminNavService {

	@Inject
	private Nav dao;
	
	public Ret add(Nav nav) {
		nav.save();
		return Ret.ok();
	}
	
	public Ret delete(Integer toId) {
		dao.deleteById(toId);
		return Ret.ok();
	}
	
	public Ret update(Nav nav) {
		nav.update();
		return Ret.ok();
	}
	
	public Nav get(Integer id) {
		return dao.findById(id);
	}
}
