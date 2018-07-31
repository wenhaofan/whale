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
});

$(function() {
	$("body").on("click", ".article-update", function() {
		var id = $(this).attr("data-id");
		window.location.href = "/admin/article/edit/" + id;
	})
	$("body").on("click", ".article-remove", function() {
		var id = $(this).attr("data-id");
		fl.alertConfirm({title:"是否确认删除？",then:function(){
			$.ajax({
				url : "/admin/api/article/remove/"+id,
				success : function(data) {
					if (fl.isOk(data)) {
						fl.alertOkAndReload(data.msg)
					}
				}
			})
		}})
	})
	
	$("body").on("click", ".article-recover", function() {
		var id = $(this).attr("data-id");
		fl.alertConfirm({title:"是否确认恢复？",then:function(){
			$.ajax({
				url : "/admin/api/article/recover/"+id,
				success : function(data) {
					if (fl.isOk(data)) {
						fl.alertOkAndReload(data.msg)
					}
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

window.addEventListener("load", function() {
	initCategorySelect();
})
