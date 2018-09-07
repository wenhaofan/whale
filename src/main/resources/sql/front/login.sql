#sql("login")
	select 
		* 
	from
		user
	where
		account=#para(account)
	and
		pwd=#para(pwd)
		
		limit 1
#end