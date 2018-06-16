#sql("lastNextArticle")
	select 
		*
	from
		article
	where
		1=1
		#@valid("")
		and pkId in (
			select 
				articleId
			from
				articleCategory
			where
				categoryId in (
						select
							categoryId 
						from 
							articleCategory
						where 
							articleId=#para(0)
						)
			GROUP BY articleId
				)
	order by readNum desc
	limit 2
#end

#sql("page")
	select 
		* 
	from 
		article 
	 
		#for(metaId:metaIds)
				#(for.first?' where ':' and ')
				pkId in(
			 		select
						cid 
					from
						relevancy
					where
						mid=#(metaId)
					)
	 	#end
#end

