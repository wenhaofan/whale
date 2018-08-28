package com.wenhaofan._admin.statistic;

import java.util.Date;

import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;

public class StatisticApi extends BaseController{

	@Inject
	private StatisticAccessLogService statisticAccessLogService;
	
	@Inject
	private StatisticArticleService statisticArticleService;
	@Inject
	private StatisticCommentService statisticCommentService;
	public void statisticsAccessNumDays() {
		renderJson(statisticAccessLogService.accessNum(getParaToInt()));
	}
	/**
	 * 获取热门文章
	 */
	public void hotArticle() {
		renderJson(statisticArticleService.hotArticle(getParaToInt()));
	}
	/**
	 * 根据时间区间统计文章数
	 */
	public void articleNum() {
		Date gmtStart=getParaToDate("gmtStart");
		Date gmtEnd=getParaToDate("gmtEnd");
		
		renderJson(statisticArticleService.articleNum(gmtStart, gmtEnd));
	}
	/**
	 * 根据时间获取评论数
	 */
	public void commentNum() {
		Date gmtStart=getParaToDate("gmtStart");
		Date gmtEnd=getParaToDate("gmtEnd");
		renderJson(statisticCommentService.commentNum(gmtStart, gmtEnd));
	}
	
	
}
