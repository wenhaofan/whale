package com.wenhaofan.common._config;

import com.jfinal.kit.PropKit;
import com.wenhaofan.common.model.entity.Config;

public class BlogContext {
	
	public static  EmailConfig emailConfig;
	public static Config basicConfig;
	
	public static String getProjectPath(){
		return PropKit.get("projectPath");
	}
	
	public static void reset(Config config) {
		emailConfig=new BlogContext().new EmailConfig(config);
		basicConfig=config;
	}
	
	public class EmailConfig{
		Config config;
		
		public EmailConfig(Config config) {
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
