package com.wenhaofan._admin.article;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexOptions;

import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.kit.EmailKit;
import com.wenhaofan.common.lucene.LuceneHelper;
import com.wenhaofan.common.model.entity.Article;
import com.wenhaofan.common.safe.JsoupFilter;

public class AdminArticleLuceneIndexes {
	
	private static final Log log = Log.getLog(EmailKit.class);
 
	
	@Inject
	private AdminArticleService adminArticleService;
	
	public void resetArticleIndexes() {
		deleteAll();
		addAll();
	}
	
	public void deleteAll() {
		LuceneHelper.single().deleteAll();
	}
	
	public void addAll() {
		List<Article> articleList=adminArticleService.listAll(1);
		articleList.forEach((article)->{
			if(StrKit.notBlank(article.getContent())) {
				add(article);
			}
		});
	}
	
	/**
	 * 添加索引
	 * @param article
	 */
	public void add(Article article) {
		//过滤html标签
		String content=JsoupFilter.filterArticleContent(article.getContent());
		article.setContent(content);
		
		Document document = getDocument(article);
		 
		long count=LuceneHelper.single().createIndex(document);
	 
		if(count==0) {
			log.error("引索创建失败！id="+article.getId());
		}
	}

	private Document getDocument(Article article) {
		Document document = new Document();
	    FieldType type = new FieldType();
	    type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
	    type.setTokenized(false);
	    type.setStored(true);

	    Field idField=new Field("id", String.valueOf(article.getId()), type);
		Field  contentField = new TextField("content", article.getContent(), Store.YES);
		Field  titleField = new TextField("title", article.getTitle(), Store.YES);
		document.add(idField);
		document.add(contentField);
		document.add(titleField);
		return document;
	}
	
	public void delete(Integer id) {
		LuceneHelper.single().deleteIndex("id", id.toString());
	}
	
	public void update(Article article) {
		LuceneHelper.single().updateIndex("id", article.getId().toString(), getDocument(article));
	}
	
	 
}
