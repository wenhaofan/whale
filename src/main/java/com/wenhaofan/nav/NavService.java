package com.wenhaofan.nav;

import java.util.List;

import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.model.entity.Nav;

public class NavService {

	@Inject
	private Nav dao;
	
	public List<Nav> list(){
		return dao.find("select * from nav order by sort desc");
	}
}
