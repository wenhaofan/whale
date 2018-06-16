function getArticle(cal){
	$.ajax({
		url:"/api/article/"+cal.id,
		dataType:"json",
		success:function(data){
			if(fl.isOk(data)){
				cal.callback(data.article);
			}
		}
	})
}

