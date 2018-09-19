package com.wenhaofan;

import java.io.IOException;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import com.wenhaofan.common._config.BlogContext;
import com.wenhaofan.common.lucene.IKAnalyzer;
 

public class IKAnalyzerTest {

	public static void main(String[] args) throws IOException {
		System.out.println(BlogContext.CacheNameEnum.ARTICLE.name());
		try {
			IKAnalyzer ikAnalyzer = new IKAnalyzer(false);
			TokenStream ts = ikAnalyzer.tokenStream("f", "\"加大Eclipse运行可用最大内存数 \\r\\n\" + \r\n" + 
					"						\"\\r\\n\" + \r\n" + 
					"						\" 具体操作: 修改位于eclipse目录下的eclipse.ini, 将-Xmx512m调高, 我的改成了-Xmx768m   效果: 不祥. 减少Eclipse启动后自动启动的插件 \\r\\n\" + \r\n" + 
					"						\"  具体操作: 在Preferences -> General -> StartUp and Shutdown: 将除Plug-ins activated on startup以外的项目有节选的去掉（比如Mylyn等没用到,就去掉了）\\r\\n\" + \r\n" + 
					"						\"  效果: 启动Eclipse后,会有Initialing Java Tools的滚动条,会发现快了很多.\\r\\n\" + \r\n" + 
					"						\"\\r\\n\" + \r\n" + 
					"						\"减少编译需要验证的项目,提升编译速度 \\r\\n\" + \r\n" + 
					"						\"  具体操作: 在Preferences -> Validataion 将无关的Validator去掉, 比如: 我就将和我无关的JPA, JSP, WS 都去掉了.\\r\\n\" + \r\n" + 
					"						\"  效果: 编译项目时,Eclipse跑的Validator项目少了, 确实快了.\\r\\n\" + \r\n" + 
					"						\"\\r\\n\" + \r\n" + 
					"						\"关掉自动编译 \\r\\n\" + \r\n" + 
					"						\"  具体操作: Project -> Build Automatically\\r\\n\" + \r\n" + 
					"						\"  效果: 在代码修改保存后,不会启动自动编译.\\r\\n\" + \r\n" + 
					"						\"\\r\\n\" + \r\n" + 
					"						\"在Clean的时候,要注意选项 \\r\\n\" + \r\n" + 
					"						\"  具体操作: Project -> Clean\\r\\n\" + \r\n" + 
					"						\"  注意: 在最下面有: Build the entire workspace 和 Build Only the selected Projects\\r\\n\" + \r\n" + 
					"						\"要根据自己情况勾选, 因为是默认选择编译整个工作区.\\r\\n\" + \r\n" + 
					"						\"\\r\\n\" + \r\n" + 
					"						\"显示内存使用情况（可手动GC） \\r\\n\" + \r\n" + 
					"						\"  具体操作：Preference -> General -> Show heep status\\r\\n\" + \r\n" + 
					"						\"\\r\\n\" + \r\n" + 
					"						\"保存自己的Perspective \\r\\n\" + \r\n" + 
					"						\"  具体操作：1. Window -> Save Perspective As\\r\\n\" + \r\n" + 
					"						\"            2. Preference -> Perspective -> Make Default 将自己刚刚创建的Perspective 或 自己常用的 设置成默认\\r\\n\" + \r\n" + 
					"						\"\\r\\n\" + \r\n" + 
					"						\"关闭Server的自动发布 \\r\\n\" + \r\n" + 
					"						\"  具体操作：Server -> Publishing -> Never publish automatically\\r\\n\" + \r\n" + 
					"						\"\\r\\n\" + \r\n" + 
					"						\"轻手轻脚 \\r\\n\" + \r\n" + 
					"						\"  慢慢操作, 莫急, 机器卡住了耐心等就好.\"");
			ts.addAttribute(CharTermAttribute.class);
			ts.reset();
			while (ts.incrementToken()) {
				System.out.println(ts.getAttribute(CharTermAttribute.class));
			}
			ts.close();
			ikAnalyzer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}