#sql("lastNextArticle")
	select 
		*
	from
		article
	where
		1=1
		#@valid("")
		and id in (
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
	 where
	 
	 1=1

	#for(metaId:metaIds)
		  and  
			id in(
		 		select
					cid 
				from
					relevancy
				where
					mid=#(metaId)
			)
 	#end
	and
		state=1
#end

