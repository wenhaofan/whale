 
package com.wenhaofan.common.kit;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.jfinal.log.Log;
import com.wenhaofan.common._config.BlogContext;

/**
 * 邮件发送工具类
 */
public class EmailKit {
	
	private static final Log log = Log.getLog(EmailKit.class);
 
	public static String sendEmail(String emailServer, String fromEmail, String password, String toEmail, String title, String content) {
		
		// 获取系统属性
		Properties properties = System.getProperties();

		// 设置邮件服务器
		properties.setProperty("mail.smtp.host", emailServer);

		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.port", "465");
		
		//SSLSocketFactory类的端口
		
		properties.put("mail.smtp.auth", "true");
 
		// 获取默认session对象
		Session session = Session.getInstance(properties, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password); // 发件人邮件用户名、密码
			}
		});
		 
		// 创建默认的 MimeMessage 对象
		MimeMessage message = new MimeMessage(session);

		// Set From: 头部头字段
		try {
			message.setFrom(new InternetAddress(fromEmail));
			// Set To: 头部头字段
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

			// Set Subject: 头部头字段
			message.setSubject(title);

			// 设置消息体
			
			BodyPart html = new MimeBodyPart();
			Multipart mainPart = new MimeMultipart();
			// 设置HTML内容
			html.setContent(content, "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			// 将MiniMultipart对象设置为邮件内容
			message.setContent(mainPart);
			// 发送消息
			Transport.send(message);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return "fail";
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return "fail";
		}
		
		return "ok";
		
	}
	
	public  static void sendEmail(String toEmail,String title,String content) {
		String result=sendEmail(BlogContext.emailConfig.getEmailServer(), BlogContext.emailConfig.getFromEmail(), BlogContext.emailConfig.getEmailPassword()
				, toEmail, title, content);
		System.out.println(result);
	}

 
	
 
	
	public static void main(String[] args) {
		String ret = sendEmail(
				"abc.com",              // 邮件发送服务器地址
				"no-reply@abc.com",		// 发件邮箱
				null,					// 发件邮箱密码
				"test@test.com",		// 收件地址
				"邮件标题",              // 邮件标题
				"content");				// 邮件内容
		System.out.println("发送返回值: " + ret);
	}
}
		
		
	
	


