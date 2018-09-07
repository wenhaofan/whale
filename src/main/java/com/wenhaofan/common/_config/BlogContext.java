package com.wenhaofan.common._config;

import com.jfinal.kit.PropKit;
import com.wenhaofan.common.model.entity.BasicConfig;

public class BlogContext {
	
	public static  EmailConfig emailConfig;
	public static BasicConfig basicConfig;
	
	public static String getProjectPath(){
		return PropKit.get("projectPath");
	}
	
	public static void reset(BasicConfig config) {
		emailConfig=new BlogContext().new EmailConfig(config);
		basicConfig=config;
	}
	
	public class EmailConfig{
		BasicConfig config;
		
		public EmailConfig(BasicConfig config) {
			this.config=config;
		}
		
		public String getEmailServer() {
			return config.getEmailServer();
		}
		public String getEmailPassword() {
			return config.getEmailPassword();
		}
		
		 
		
		public String getFromEmail() {
			return config.getFromEmail();
		}
	}
	
	 
	
}
