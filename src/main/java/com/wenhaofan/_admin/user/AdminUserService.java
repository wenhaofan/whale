package com.wenhaofan._admin.user;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.ehcache.CacheKit;
import com.wenhaofan.common._config.BlogContext;
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
	
 
	public Ret update(User user) {
		user.setId(getAdminUser().getId());
		user.update();
		CacheKit.remove(BlogContext.CacheNameEnum.BLOGGER.name(),  "getAdminUser");
		return Ret.ok();
	}
	
	public User getAdminUser() {
		User user=dao.findFirstByCache(BlogContext.CacheNameEnum.BLOGGER.name(), "getAdminUser", "select * from user");
		return user.setPwd("");
	}
	
}
