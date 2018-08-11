package com.wenhaofan._admin.statistic;

import java.util.Date;

import com.wenhaofan.common.aop.Inject;
import com.wenhaofan.common.controller.BaseController;

public class StatisticApi extends BaseController{

	@Inject
	private StatisticAccessLogService statisticAccessLogService;
	
	@Inject
	private StatisticArticleService statisticArticleService;
	
	public void statisticsAccessNumDays() {
		renderJson(statisticAccessLogService.accessNum(getParaToInt()));
	}
	
	public void hotArticle() {
		renderJson(statisticArticleService.hotArticle(getParaToInt()));
	}
	
	public void articleNum() {
		Date gmtStart=getParaToDate("gmtStart");
		Date gmtEnd=getParaToDate("gmtEnd");
		
		renderJson(statisticArticleService.articleNum(gmtStart, gmtEnd));
	}
}
