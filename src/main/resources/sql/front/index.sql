#sql("listTopArticle")
	select
		 pkId,title,gmtCreate,readNum,content,thumbImg
	from
		 article 
	where 
		isTop = #para(top) 
	and 
		state = #para(state)

	
	order by gmtCreate desc 
	
	#@page()
#end


#sql("listArticle")
	select 
		pkId,
		title,
		content,
		gmtModified,
		readNum,
		thumbImg,
		gmtCreate
	from
		article
	where
		1=1
	
		#@valid("")
		#if(articles!=null&&!articles.isEmpty())

		and pkId not in (
					#for(article:articles)
						#(article.pkId) 
						#if(for.last!=true)
						,
						#end
					#end)
		#end

#end



