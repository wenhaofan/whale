package com.wenhaofan._admin.seo;

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
import com.wenhaofan.common.model.entity.BaiduSeoConfig;
import com.wenhaofan.common.model.entity.Meta;
import com.wenhaofan.common.model.entity.MetaweblogConfig;

public class MetaweblogService {

	@Inject
	private MetaweblogConfig dao;
	
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
	
	public Ret pushNewPostMetaweblog(String title,String content,List<Meta> tags) {
		List<MetaweblogConfig> configs= list();
		Ret result=null;
 
		Integer successCount=0;
		Integer failCount=0;
		
		Kv successKv=Kv.create();
		Kv failKv=Kv.create();
		for(MetaweblogConfig config:configs) {
			result=pushNewPost(config.getUrl(),config.getUserName(),config.getPassword(),title,content,tags);
			if(result.isOk()) {
				successCount++;
				successKv.set(config.getWebsite(), config);
			}else {
				failCount++;
				failKv.set(config.getWebsite(), config);
			}
		}
		
		return Ret.ok("success",successKv).set("fail", failKv);
	}
	
	public Ret pushNewPost(String url,String account,String password,String title,String content,List<Meta> tags) {
		StringBuilder sb=new StringBuilder();
		 
		tags.forEach(new Consumer<Meta>() {
		    @Override
		    public void accept(Meta item) {
		    	sb.append(","+item.getName());
		    }
		});
		
		return pushNewPost(url,account,password,title,content,sb.toString());
	}
	public Ret pushNewPost(String url,String account,String password,String title,String content,String keywords) {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		try {
			config.setServerURL(new URL(url));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
 
		Map<String, String> m = new HashMap<String, String>();
		m.put("title", title);
		m.put("mt_keywords", keywords);
		m.put("description", content);
		 
		Object[] params = new Object[]{"default", account,password, m, true};
		 
 
		try {
			 client.execute("metaWeblog.newPost", params);
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Ret.fail();
		}
		
		return Ret.ok();
	}
	
}
