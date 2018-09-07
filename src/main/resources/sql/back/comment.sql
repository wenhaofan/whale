#sql("page")
	select
		*
	from
		comment
	#if(isAduit!=null)
	where
		 isAduit=#para(isAduit)
	#end
	
	order by gmtCreate desc
#end