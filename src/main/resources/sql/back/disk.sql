#sql("list")

	select
		*
	from
		disk
	#if(query!=null)
	where
		1=1
		#if(query.state!=null)
			and
				state=#para(query.state)
		#end
		
		#if(query.parentId!=null)
			and
				state=#para(query.parentId)
		#end
		
		#if(query.order!=null)
			order by #para(query.order) #para(query.orderType) 
		#else if(query.isSizeOrder)
			order by size #para(query.orderType) 
		#end
	#end

#end