#sql("page")
	select
		*
	from
		comment
	where
	 	identify=#para(0)
	and
	 	parentId=0
	 order by gmtCreate desc
#end