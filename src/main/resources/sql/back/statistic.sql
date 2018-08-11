#sql("statisticsNum")
	select 
		DATE_FORMAT(a.gmtcreate,'%m-%d') as gmtcreate,ifnull(b.count,0) as count
	from (
	    SELECT 
	    	curdate() as gmtcreate
	    #for(i=1;i<days;i++)
	      union all
	   	  SELECT date_sub(curdate(), interval #(i) day) as gmtcreate
	    #end
	) a left join (
	  select  DATE_FORMAT(gmtCreate,'%Y%m%d') as datetime, count(distinct cookie) as count ,cookie
	  from access_log
	  group by DATE_FORMAT(gmtCreate,'%Y%m%d')
	) b on a.gmtcreate = b.datetime
	order by UNIX_TIMESTAMP(a.gmtcreate)
#end

#sql("hotArticle")
	select
		title,
		pv,
		pkId
	from
		article
		order by pv desc limit 0,#(size)
#end

#sql("articleNum")
	select
		count(pkId)
	from
		article
	#if(gmtEnd!=null)

	where
		gmtCreate >= #para(gmtStart)
	and
		gmtCreate <= #para(gmtEnd)
	#end
#end