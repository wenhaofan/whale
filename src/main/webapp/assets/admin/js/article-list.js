$.fn.serializeJson = function()   
{   
   var o = {};   
   var a = this.serializeArray();   
   $.each(a, function() {   
       if (o[this.name]) {   
           if (!o[this.name].push) {   
               o[this.name] = [o[this.name]];   
           }   
           o[this.name].push(this.value || '');   
       } else {   
           o[this.name] = this.value || '';   
       }   
   });   
   return o;   
}; 

var form;
var table;
layui.use([ 'table', 'form' ], function() {
	table = layui.table;
	laytpl = layui.laytpl;
	form = layui.form;
	
	form.on('select(category-select)', function(data) {
		querylist($(".layui-form").serializeJson());
	});
	form.on('select(state-select)', function(data) {
		querylist($(".layui-form").serializeJson())
	});
	
	renderArticles();
	initCategorySelect();
});

function renderArticles(){
	table.render({
		page:{count:80,limit:10}
		,url:'/admin/api/article/list' 
	    ,elem: '#articles'
	    ,page: true //开启分页
	    ,cols: [[ //表头
	    	{field:'id', sort: true,title:"ID"}
	      ,{field:'title',minWidth:150,title:"标题"}
	      ,{field:'pv', width:100,sort: true,title:"阅读量"}
	      ,{field:'state',templet:'#state-tpl',title:"状态"}
	      ,{field:'isOriginal',templet:'#original-tpl',title:"原创"}
	      ,{field:'identify',title:"访问路径"}
	      ,{templet:'#operation-tpl', title:"操作"}
	    ]]
	 });
}

$(function() {
 
	$("body").on("click", ".article-update", function() {
		var id = $(this).attr("data-id");
		window.location.href = "/admin/article/edit/" + id;
	})
	
	
	$("body").on("click", ".article-remove", function() {
		var id = $(this).attr("data-id");
		fl.alertConfirm({title:"是否确认废弃？",then:function(){
			fl.ajax({
				url : "/admin/api/article/remove/"+id,
				success : function(data) {
					fl.alertOkAndReload(data.msg);
					 
				}
			})
		}})
	})
	$("body").on("click", ".article-delete", function() {
		var id = $(this).attr("data-id");
		fl.alertConfirm({title:"是否确认删除？",text:"注意：删除后将不能恢复！",then:function(){
			fl.ajax({
				url : "/admin/api/article/delete/"+id,
				success : function(data) {
					fl.alertOkAndReload(data.msg)
				}
			})
		}})
	})
	$("body").on("click", ".article-recover", function() {
		var id = $(this).attr("data-id");
		fl.alertConfirm({title:"是否确认恢复？",then:function(){
			fl.ajax({
				url : "/admin/api/article/recover/"+id,
				success : function(data) {
					fl.alertOkAndReload(data.msg)
				}
			})
		}})
	})
})

function querylist(data) {
	table.reload('articles', {
		url : '/admin/api/article/list',
		where :data
	// 设定异步数据接口的额外参数
	});
}

/** 初始化分类多选 */
function initCategorySelect() {
	var initCategorys = {
		type:"category",
		callback : function(categorys) {
			var html = template("option-tpl", {
				"categorys" : categorys
			});
			$("#category-select").html(html);
			form.render('select');
		}
	}
	metaUtils.listMeta( initCategorys);
}


$(document).ready(function() {
	$("body").on("click",".article-push",function(){
		fl.ajax({
			url:"/admin/api/article/asyncMetaWeblog/"+$(this).data("id"),
			success:function(data){
				fl.alertOk({});
			}
		})
	})
	$("body").on("click",".article-preview",function(){
		 window.open("/article/"+$(this).data("identify"));
	})
	$("body").on("click",".createIndex",function(){
		fl.alertConfirm({title:"是否确认重置索引？",text:"执行过程中搜索服务将暂时不能使用，文章越多时间越长。",then:function(){
			fl.ajax({
				url:"/admin/api/article/createIndex",
				success:function(data){
					fl.alertOk({title:"重置成功！"});
				}
			})
		}}) 
	})
})
