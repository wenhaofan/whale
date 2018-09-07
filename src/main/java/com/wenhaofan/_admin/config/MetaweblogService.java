package com.wenhaofan._admin.config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.model.entity.Article;
import com.wenhaofan.common.model.entity.Meta;
import com.wenhaofan.common.model.entity.MetaweblogConfig;
import com.wenhaofan.common.model.entity.MetaweblogRelevance;

public class MetaweblogService {

	@Inject
	private MetaweblogConfig dao;
	@Inject
	private MetaweblogRelevanceService relevanceService;
	
	public MetaweblogConfig get(Integer id) {
		return dao.findById(id);
	}
	
	
	public Ret updateOrAdd(MetaweblogConfig config) {
		if(config.getId()!=null) {
			config.update();
		}else {
			config.save();
		}
		
		return Ret.ok();
	} 
	
	public Ret delete(Integer toId) {
		dao.deleteById(toId);
		return Ret.ok();
	}
	
 
	
	public List<MetaweblogConfig> list(){
		return dao.find("select * from metaweblog_config");
	}
	
	public Ret pushNewPostMetaweblog(Article article,List<Meta> tags) {
		List<MetaweblogConfig> configs= list();
		Ret result=null;
 
		Integer successCount=0;
		Integer failCount=0;
		
		Kv successKv=Kv.create();
		Kv failKv=Kv.create();
		for(MetaweblogConfig config:configs) {
			result=pushNewPost(config,article,tags);
			String type=result.getStr("type");
			if(result.isOk()) {
				successCount++;
				successKv.set(config.getWebsite(), config).set("type", type);
			}else {
				failCount++;
				failKv.set(config.getWebsite(), config).set("type", type);
			}
		}
		
		return Ret.ok("success",successKv).set("fail", failKv);
	}
	
	public Ret pushNewPost(MetaweblogConfig config,Article article,List<Meta> tags) {
		StringBuilder sb=new StringBuilder();
		 
		tags.forEach(new Consumer<Meta>() {
		    @Override
		    public void accept(Meta item) {
		    	sb.append(","+item.getName());
		    }
		});
		
		return pushPost(config,article,sb.toString());
	}
	
	public Ret pushPost(MetaweblogConfig mconfig,Article article,String keywords) {
		MetaweblogRelevance  metaweblogRelevance =relevanceService.get(article.getId(),mconfig.getId());
		
		if(metaweblogRelevance!=null) {
			return editPost(mconfig, article, keywords,metaweblogRelevance);
		}else {
			return pushNewPost(mconfig, article, keywords);
		}
		
	}
	
	public Ret pushNewPost(MetaweblogConfig mconfig,Article article,String keywords) {
 
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		try {
			config.setServerURL(new URL(mconfig.getUrl()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
 
		Map<String, String> m = new HashMap<String, String>();
		m.put("title", article.getTitle());
		m.put("mt_keywords", keywords);
		m.put("description", signContent(article.getContent(),article.getUrl()));
		 
		Object[] params = new Object[]{"default", mconfig.getUserName(),mconfig.getPassword(), m, true};
		 
	
		try {
			String postId= client.execute("metaWeblog.newPost", params).toString();
			relevanceService.add(postId, mconfig.getId(), article.getId());
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Ret.fail();
		}
		
		return Ret.ok("type","newPost");
	}
	
	public Ret editPost(MetaweblogConfig mconfig,Article article,String keywords,MetaweblogRelevance  metaweblogRelevance) {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		try {
			config.setServerURL(new URL(mconfig.getUrl()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
 
		Map<String, String> m = new HashMap<String, String>();
		m.put("title", article.getTitle());
		m.put("mt_keywords", keywords);
		m.put("description", signContent(article.getContent(),article.getUrl()));
		 
		Object[] params = new Object[]{metaweblogRelevance.getPostId(), mconfig.getUserName(),mconfig.getPassword(), m, true};
		 
		try {
			String postId= client.execute("metaWeblog.editPost", params).toString();
			System.out.println(postId);
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Ret.fail();
		}
		
		return Ret.ok("type","editPost");
	}
	
	public String signContent(String content,String link){
		return content+"<br><a href='"+link+"'>原文地址:"+link+"</a>";
	}
	
}
