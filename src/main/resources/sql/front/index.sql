#sql("listArticle")
	select 
		pkId,
		title,
		content,
		gmtModified,
		readNum,
		thumbImg,
		gmtCreate,
		identify
	from
		article
	where
		1=1

		#if(articles!=null&&!articles.isEmpty())

		and pkId not in (
					#for(article:articles)
						#(article.pkId) 
						#if(for.last!=true)
						,
						#end
					#end)
		#end
		and state=1
#end



