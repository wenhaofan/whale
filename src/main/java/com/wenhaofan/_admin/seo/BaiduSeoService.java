package com.wenhaofan._admin.seo;

import java.util.List;

import com.jfinal.kit.HttpKit;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.model.entity.BaiduSeoConfig;

public class BaiduSeoService {

	@Inject
	private BaiduSeoConfig dao;
	
	public List<BaiduSeoConfig> list(){
		return dao.find("select * from baidu_seo_config");
	}
	
	public void pushLink(String link) {
		pushLink(list(),link);
	}
	
	@SuppressWarnings("unchecked")
	public void pushLink(String link,String site,String token) {
		String url="http://data.zz.baidu.com/urls?site=wenhaofan.com&token=kZ4mRWVQGEsoJvbz";
		Kv headers=Kv.by("User-Agent", "curl/7.12.1 ").set("Host", "data.zz.baidu.com").set("Content-Type", "text/plain").set("Content-Length", "83");
		HttpKit.post(url, "http://wenhaofan.com/article/20180618180327\nhttp://wenhaofan.com/article/fast-reboot-tomcat",headers);
		//待加日志
	}
	
	public void pushLink(List<BaiduSeoConfig> configs,String link) {
		configs.stream().forEach((BaiduSeoConfig config)->{
			try {
				pushLink(link,config.getSite(),config.getToken());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}); 
	}
	
	public Ret add(BaiduSeoConfig config) {
		config.save();
		return Ret.ok();
	}
	
	public Ret delete(Integer toId) {
		dao.deleteById(toId);
		return Ret.ok();
	}
	
	public Ret update(BaiduSeoConfig config) {
		config.update();
		return Ret.ok();
	}
}
