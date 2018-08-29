#sql("listArticle")
	select
		id,
		title,
		content,
		gmtCreate,
		gmtModified,
		pv,
		thumbImg,
		state,
		identify,
		isOriginal
	from 
		article 
	where 1=1 
	
	#if(article.state!=null&&article.state!=-1)
		 and state=#para(article.state)
	#else if(article.state!=-1)
		and state=1
	#end
	
	#@queryMeta()
	
	
	order by gmtCreate desc
#end