package com.wenhaofan.common.lucene;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.jfinal.kit.PathKit;
import com.wenhaofan.common.model.entity.Article;

/**
 * @author 作者:范文皓
 * @createDate 创建时间：2018年9月10日 下午6:18:27
 */
public class LuceneHelper {

	public static String indexDir = PathKit.getWebRootPath() + "/lucene";

	/**
	 * 获取索引存放目录
	 * 
	 * @return
	 * @throws IOException
	 */
	public Directory getDirectory() throws IOException {
		return FSDirectory.open(Paths.get(indexDir));
	}

	public Analyzer getAnalyzer() {
		return new IKAnalyzer(true);
	}

	/**
	 * 获取操作索引的流
	 * 
	 * @return
	 */
	public IndexWriter getIndexWriter() {
		// 索引库的位置
		Directory directory = null;
		Analyzer analyzer = getAnalyzer();

		IndexWriterConfig config = new IndexWriterConfig(analyzer);

		try {
			directory = getDirectory();
			return new IndexWriter(directory, config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建索引
	 * 
	 * @param id
	 * @param content
	 * @throws IOException
	 */
	public void createIndexs(List<Document> cocuments) {
		cocuments.forEach((document) -> {
			createIndex(document);
		});
	}

	/**
	 * 创建索引
	 * 
	 * @param id
	 * @param content
	 * @throws IOException
	 */
	public long createIndex(Document document) {

		// 将document放到documents集合中

		IndexWriter writer = null;
		long count = 0;
		try {
			writer = getIndexWriter();
			count = writer.addDocument(document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return count;
	}

	/**
	 * 
	 * @param queryStr 查询语句
	 * @param size     查询数
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public List<Article> readerIndex(String queryStr, Integer size) {
 
		QueryParser queryParser = new MultiFieldQueryParser(new String[]{"title","content"},getAnalyzer());
		// 创建QueryParser对象，并指定要查询的索引域及分词对象
		//QueryParser queryParser = new QueryParser(strs[0], new IKAnalyzer());
		// 创建Query对象，指定查询条件
		Query query = null;

		// 指定要查询的lucene索引目录,必须与存储的索引目录一致
		Directory directory = null;
		// 通过指定的查询目录文件地址创建IndexReader流对象
		IndexReader reader = null;
		// 执行查询语句，获取返回的TopDocs对象,size为返回多少条记录
		TopDocs search = null;

		// 通过IndexReader流对象创建IndexSearcher

		IndexSearcher searcher = null;

		try {
			query = queryParser.parse(queryStr);
			directory = getDirectory();
			reader = DirectoryReader.open(directory);
			searcher = new IndexSearcher(reader);
			search = searcher.search(query, size);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return convertToArticles(query, reader, search, searcher);
	}

	private List<Article> convertToArticles(Query query, IndexReader reader, TopDocs search, IndexSearcher searcher) {
		// 获取查询到的数据
		ScoreDoc[] scoreDocs = search.scoreDocs;

		List<Article> articles = new ArrayList<>();

		QueryScorer scorer = new QueryScorer(query);
		SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter("<B>", "</B>");// 设定高亮显示的格式<B>keyword</B>,此为默认的格式
		Highlighter highlighter = new Highlighter(simpleHtmlFormatter, scorer);
		highlighter.setTextFragmenter(new SimpleFragmenter(100));// 设置每次返回的字符数

		Article article = null;
		String content = null;
		String title = null;
		String id = null;
		try {
			for (ScoreDoc doc : scoreDocs) {

				Document document = null;

				document = searcher.doc(doc.doc);
				content = highlighter.getBestFragment(getAnalyzer(), "content", document.get("content"));
				title = highlighter.getBestFragment(getAnalyzer(), "title", document.get("title"));
				id = document.get("id");
				article = new Article();
				article.setContent(content);
				article.setId(Integer.parseInt(id));
				article.setTitle(title);
				articles.add(article);

				System.out.println(id);

			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidTokenOffsetsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return articles;
	}

	/**
	 * 根据查询条件删除lucene索引及文档数据
	 * 
	 * @param key
	 * @param value
	 */
	public void deleteIndex(String key, String value) {

		// 通过指定的索引目录地址、配置对象创建IndexWriter流对象
		IndexWriter writer = getIndexWriter();
 
		try {
			long count=writer.deleteDocuments(new Term(key, value));
			writer.commit();
			writer.flush();
			writer.flushNextBuffer();
			writer.forceMergeDeletes();
			System.out.println(count);
			//writer.deleteAll();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 清空索引及存档的所有数据
		 
	}

	/**
	 * 更新索引
	 * 
	 * @param key
	 * @param value
	 * @param document
	 */
	public void updateIndex(String key, String value, Document document) {

		// 通过配置、目录参数创建流对象
		IndexWriter writer = getIndexWriter();

		// 创建document对象

		// 将新添加的doc数据替换索引中已经有的id为2的数据
		// 执行步骤如下：查询数据--》查询到或未查询到--》替换/添加doc数据
		try {
			writer.updateDocument(new Term(key, value), document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// 关闭流

	}
}
