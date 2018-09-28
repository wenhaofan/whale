package com.wenhaofan._admin.themes;

import com.jfinal.kit.Ret;
import com.wenhaofan.common._config.BlogContext;
import com.wenhaofan.common.annotation.SysLog;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.kit.PropertyKit;

/**
* @author 作者:范文皓
* @createDate 创建时间：2018年9月13日 下午1:15:02
*/
public class AdminThemesApi extends BaseController {

	@SysLog(value="切换主题",action="udpate")
	public void change() {
		String themeName=getPara();
		BlogContext.setTheme(themeName);
		PropertyKit.updateValue(BlogContext.CONFIG_FILE_NAME, "theme", themeName);
		renderJson(Ret.ok());
	}
	
}
