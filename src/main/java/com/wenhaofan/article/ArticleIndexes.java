package com.wenhaofan.article;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;

import com.jfinal.log.Log;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.kit.EmailKit;
import com.wenhaofan.common.lucene.LuceneHelper;
import com.wenhaofan.common.model.entity.Article;

public class ArticleIndexes {
	
	private static final Log log = Log.getLog(EmailKit.class);
	
	@Inject
	private LuceneHelper luceneHelper;
	
	public void add(Article article) {
		Document document = getDocument(article);
		 
		long count=luceneHelper.createIndex(document);
	 
		if(count==0) {
			log.error("引索创建失败！id="+article.getId());
		}
	}

	private Document getDocument(Article article) {
		Document document = new Document();

		Field  idField = new StoredField("id",article.getId());
		Field  contentField = new TextField("content", article.getContent(), Store.YES);
		Field  titleField = new TextField("title", article.getTitle(), Store.YES);
		document.add(idField);
		document.add(contentField);
		document.add(titleField);
		return document;
	}
	
	public void delete(Integer id) {
		luceneHelper.deleteIndex("id", id.toString());
	}
	
	public void update(Article article) {
		luceneHelper.updateIndex("id", article.getId().toString(), getDocument(article));
	}
	
	 
}
