package com.wenhaofan.user;

import com.jfinal.aop.Clear;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.wenhaofan.agentUser.AgentUserService;
import com.wenhaofan.common.controller.BaseController;
import com.wenhaofan.common.kit.IpKit;

/**
* @author 作者:Lemon
* @createDate 创建时间：2018年9月6日 下午12:38:51
*/
@Clear
public class LoginApi extends BaseController {

	private LoginService loginService=LoginService.me;

	
	public void index() {
		if(isLogin()) {
			renderJson(Ret.ok());
		}
		String account=getPara("ac");
		String pwd=getPara("pwd");
		String ip=IpKit.getRealIp(getRequest());
		
		boolean isKepp=getParaToBoolean("k",false);
		
		Ret ret=loginService.login(account,pwd,isKepp,ip);
		
		if(ret.isOk()) {
			String sessionId=ret.getStr(LoginService.sessionIdName);
			int maxAge=ret.getInt("cookieMaxAge");
			setAttr(LoginService.loginUserKey,ret.get(LoginService.loginUserKey));
			setCookie(LoginService.sessionIdName,sessionId, maxAge,"/",PropKit.get("domain"),true);
			removeCookie(AgentUserService.AGENT_USER_COOKIE_KEY);
			renderJson(Ret.ok());
			return ;
		}
		
		renderJson(ret);	
	}
	
	
}
