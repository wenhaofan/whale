#sql("listArticle")
	select 
		id,
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
			and id not in (
						#for(article:articles)
							#(article.id) 
							#if(for.last!=true)
							,
							#end
						#end)
			#end
		and state=1
#end



