package com.wenhaofan._admin.blogroll;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.ehcache.CacheKit;
import com.wenhaofan.common._config.BlogContext;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.model.entity.Blogroll;

/**
 * 友情链接的实现类
 * @author fwh
 *
 */
public class BlogrollService{

 
	@Inject
	private  Blogroll dao;
	
	public void saveOrUpdate(Blogroll blogroll) {
		if(blogroll.getId()!=null) {
			blogroll.update();
		}else{
			blogroll.save();
		}
		//移除缓存
		CacheKit.remove(BlogContext.CacheNameEnum.BLOGROLL.name(), "list");
	}

	public void remove(Integer id) {
		dao.deleteById(id);
		//移除缓存
		CacheKit.remove(BlogContext.CacheNameEnum.BLOGROLL.name(), "list");
	}

	public void update(Blogroll blogroll) {
		blogroll.update();
	}

	public List<Blogroll> listBlogroll() {
		// TODO Auto-generated method stub
		return dao.find("select * from blogroll order by sort desc");
	}

	
	public Blogroll getBlogrollById(Integer id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	
	public Integer getMaxPriority() {
		Integer ukPriority=Db.queryInt("select   ukPriority   from  blogroll  order   by   ukPriority   desc   limit   1");
		if(ukPriority==null) {
			ukPriority=0;
		}
		return ukPriority;
	}
}
