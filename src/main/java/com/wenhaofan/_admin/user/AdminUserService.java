package com.wenhaofan._admin.user;

import com.jfinal.kit.Ret;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.kit.StrKit;
import com.wenhaofan.common.model.entity.User;

public class AdminUserService {

	@Inject
	private User dao;
	
	public Ret editPassword(User user,String oladpassword,String newPassword) {
		user=dao.findById(user.getId());
		if(!StrKit.equals(oladpassword, user.getPwd())) {
			return Ret.fail("msg", "原密码错误！");
		}
		user.setPwd(newPassword);
		user.update();
		return Ret.ok("msg", "密码修改成功！");
	}
	
 
}
