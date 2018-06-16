
var categoryId;
var layer ;
var pageNum=1;
layui.use(['layer','laypage'], function(){
	layer=layui.layer;
	
});


function renderIndexPage(count,limit,curr){
	layui.use(['laypage'],function(){
		var laypage = layui.laypage;
		  //执行一个laypage实例
		  laypage.render({
		    elem: 'index-page' //注意，这里的 test1 是 ID，不用加 # 号
		    ,count: count,//数据总数
		    limit:limit,//每页记录数
		    curr:curr,
		    theme:"page",
		    jump: function(obj, first){
			    //obj包含了当前分页的所有参数，比如：
			    pageNum=obj.curr;
			    //首次不执行
			    if(!first){
			    	listArticle(categoryId,setArticles);
			    }
			  }
		  });
		  

	})
}


function setArticleTags(){
	$(".article-tags").each(function(){
		var articleId=$(this).attr("data-id");
		
		var initTags={
			callback:function(tags){
				var tagsStr="";
				$.each(tags,function(index,item){
					tagsStr+='<a href="javascript:void(0)">'+item.mname+'</a>';
					if(tags.length>index+1){
						tagsStr+=",";
					}
				})
				this.target.html(tagsStr);
			},
			target:$(this)
		}
		
		listMetaByArticleId(articleId,"tag",initTags);
	})
}
function setArticleCategorys(){
	$(".article-categorys").each(function(){
		var initTags={
			callback:function(tags){
				var tagsStr="";
				$.each(tags,function(index,item){
					tagsStr+=' '+item.mname+' ';
					if(tags.length>index+1){
						tagsStr+=",";
					}
				})
				
				if(tagsStr==""){
				}else{
					this.target.html("暂无分类");
					this.target.html("分类:"+tagsStr);
				}
				
			},
			target:$(this),
			type:"category",
			articleId:$(this).attr("data-id")
		}
		
		metaUtils.listMetaByArticleId(initTags);
	})
}

var initTags={
	callback:function(tags){
		setTags(tags);
	}
}

function setTags(tags){
	var $tags=$("#tags");
	var $tag;
	$.each(tags,function(index,item){
		$tag=$(template("tpl-tag",{"tag":item}));
		$tags.append($tag);
	})
}


$(function(){
	$("body").on("click",".header-categry",function(){
		categoryId=$(this).attr("data-id");
		$("#categoryId").val(categoryId);
		var msgIndex=layer.msg('加载中', {
			  icon: 16
			  ,shade: 0.01,
			  time:false
		});
		categoryId=$("#categoryId").val();
		var renderArticles={
				categoryId:categoryId,
				msgIndex:msgIndex,
				pageNum:1,
				limit:10,
				callback:function(data){
					setArticles(data,this.msgIndex);
				}
		}
		
		listArticle(renderArticles);
	})
})

function listArticle(paras){
	var categoryId=paras.categoryId.length==0?0:paras.categoryId;
	$.ajax({
		url:"/api/article/list/"+categoryId+"?p="+paras.pageNum+"&limit="+paras.limit,
		dataType:"json",
		success:function(data){
			if(fl.isOk(data)){
				paras.callback(data,paras);
			}
			
		}
	})
}

function setArticles(data,msgIndex){
	var $article;
	$(".post-lists-body").empty();
	$.each(data.articlePage.list,function(index,item){
		$article=$(template("tpl-article",{"article":item}))
		$(".post-lists-body").append($article);
	})
	if(msgIndex!=undefined){
		layer.close(msgIndex);
	}
	
	var page=data.articlePage;
	renderIndexPage(page.totalRow,page.pageSize,page.pageNumber);
	//setArticleTags();
	setArticleCategorys();
}

/**
 * 调整底部的高度
 * */
function adjustFooter(){
	var clientHeight=document.documentElement.clientHeight;
	var footerTop=$("footer").offset().top;
	var footerHeight=$("footer").height();
	var footerTop=$("footer").attr("margin-top");
	if(footerTop==undefined){
		footerTop=0;
	}
	
	var differ=(footerTop+footerHeight)-clientHeight;
	
	if(differ>0){
		var height=clientHeight-footerTop-footerHeight-3;
		$("footer").css("margin-top",(-height)+"px");
	}else{
		$("footer").css("margin-top",0);
	}
	
	
}

window.addEventListener("load",function(){
	
	categoryId=$("#categoryId").val();
	var initArticls={
			callback:function(data){
				setArticles(data);
			},
			pageNum:1,
			categoryId:categoryId,
			limit:8
	}
	
	//listArticle(initArticls);
	
	//listMeta("tag",initTags);
})

