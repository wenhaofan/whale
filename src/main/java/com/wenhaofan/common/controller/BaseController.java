package com.wenhaofan.common.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.wenhaofan.agentUser.AgentUserService;
import com.wenhaofan.article.ArticleService;
import com.wenhaofan.comment.CommentService;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.model.entity.AgentUser;
import com.wenhaofan.common.model.entity.User;
import com.wenhaofan.login.LoginService;

public class BaseController extends Controller{
	
	private User loginUser=null;
	
	@Inject
	public AgentUserService agentUserService;
	@Inject
	public ArticleService articleService;
	@Inject
	public CommentService commentService;
	
	public AgentUser getAgentUser() {
		AgentUser agentUser=null;
		
		String cookie=getCookie(AgentUserService.AGENT_USER_COOKIE_KEY);
		
		if(StrKit.notBlank(cookie)) {
			agentUser=agentUserService.get(cookie);
			if(agentUser!=null) {
				return agentUser;
			}
		}
		cookie=StrKit.getRandomUUID();
		if(getLoginUser()!=null) {
			agentUser=new AgentUser();
			agentUser.setId(0);
			agentUser.setName("admin");
		}else {
			agentUser=new AgentUser();
			agentUser.setCookie(cookie);
		}
		agentUserService.save(agentUser);
		//设置cookie
		setCookie(AgentUserService.AGENT_USER_COOKIE_KEY,cookie,AgentUserService.AGENT_USER_COOKIE_AGE);
		return agentUser;
		 
	}
	
	public boolean isAjax() {
		String requestType = getRequest().getHeader("X-Requested-With");
		return "XMLHttpRequest".equals(requestType);
	}
	
	public User getLoginUser() {
		
		if(loginUser!=null) {
			return loginUser;
		}
		
		String sessionId=getCookie(LoginService.sessionIdName);
		
		if(sessionId!=null) {
			//通过sessionId从缓存中获取登录用户
			User loginUser=LoginService.me.getUserWithSessionId(sessionId);
			//如果依然为空则从数据库中寻找有效的登录用户
			if(loginUser==null) {
				loginUser=LoginService.me.loginWithSessionId(sessionId);
			}
			
			if(loginUser!=null) {
				setAttr(LoginService.loginUserKey, loginUser);
		
			}else {
				//为空则表示cookie无用，删之
				removeCookie(LoginService.sessionIdName);
				renderError(404);
			}
		}
		
		
		return getAttr(LoginService.loginUserKey);
	}
	
	public boolean isLogin() {
		return getLoginUser()!=null;
	}
	
	public boolean notLogin() {
		return !isLogin();
	}
	
	public <T> List<T> getModelList(Class<T> modelClass,String modelName,boolean skipConvertError){
		Pattern p = Pattern.compile(modelName + "\\[\\d+\\].[a-zA-z0-9]+"); 
         Map<String, String[]> parasMap = getRequest().getParameterMap();       
         String paraKey;        
         Set<String> modelPrefix = new HashSet<String>();        
         for (Entry<String, String[]> e : parasMap.entrySet()) {
            paraKey = e.getKey();           
            if(p.matcher(paraKey).find()){
                modelPrefix.add(paraKey.split("\\.")[0]);
            }
        }
        List<T> resultList = new ArrayList<T>();        
        for (String modelName2 : modelPrefix) {
            resultList.add(getModel(modelClass,modelName2,skipConvertError));
        } 
        return resultList;
    }
    
	public <T> List<T> getModelList(Class<T> modelClass,String modelName){
		boolean skipConvertError=false;
		Pattern p = Pattern.compile(modelName + "\\[\\d+\\].[a-zA-z0-9]+"); 
         Map<String, String[]> parasMap = getRequest().getParameterMap();       
         String paraKey;        
         Set<String> modelPrefix = new HashSet<String>();        
         for (Entry<String, String[]> e : parasMap.entrySet()) {
            paraKey = e.getKey();           
            if(p.matcher(paraKey).find()){
                modelPrefix.add(paraKey.split("\\.")[0]);
            }
        }
        List<T> resultList = new ArrayList<T>();        
        for (String modelName2 : modelPrefix) {
            resultList.add(getModel(modelClass,modelName2,skipConvertError));
        } 
        return resultList;
    }
}
