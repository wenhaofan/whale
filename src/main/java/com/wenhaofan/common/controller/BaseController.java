package com.wenhaofan.common.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import com.jfinal.core.Controller;
import com.wenhaofan.common.model.entity.User;
import com.wenhaofan.login.LoginService;

public class BaseController extends Controller{
	
	private User loginUser=null;
	
	public boolean isAjax() {
		String requestType = getRequest().getHeader("X-Requested-With");
		return "XMLHttpRequest".equals(requestType);
	}
	
	public User getLogUser() {
		if(loginUser==null) {
			loginUser=getAttr(LoginService.loginUserKey);
		}
		return loginUser;
	}
	
	public boolean isLogin() {
		return getLogUser()!=null;
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
