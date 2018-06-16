package com.wenhaofan.index;

import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wenhaofan.common.model.entity.Article;
import com.wenhaofan.common.safe.JsoupFilter;

public class IndexService {

	public static IndexService me = new IndexService();

	private Article dao = new Article().dao();

	/**
	 * 分页通过分类id查询
	 * 
	 * @param categoryId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Article> pageArticle(Integer categoryId, List<Article> notIn, int pageNum, int pageSize) {
		Kv kv = Kv.by("categoryId", categoryId)

				.set("articles", notIn);
		SqlPara sqlPara = dao.getSqlPara("index.listArticle", kv);
		Page<Article> articlePage = dao.paginate(pageNum, pageSize, sqlPara);

		JsoupFilter.filterArticleList(articlePage.getList(), 20, 150);
		return articlePage;
	}

}
