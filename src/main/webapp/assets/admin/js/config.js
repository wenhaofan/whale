$(function(){
	
	//避免pjax重复加载js导致事件重复绑定
	if (typeof (adminConfigIsBind) != "undefined") {
	    return;
	}   
	adminConfigIsBind=true;
	
	$("#addBlogroll").click(function(){
		editBlogroll({});
	})
	
	$("body").on("click",".update-blogroll",function(){
		var id=$(this).data("id");
		updateBlogroll(id);
	})
	$("body").on("click",".delete-blogroll",function(){
		var id=$(this).data("id");
		deleteBlogroll(id);
	})
	
})

function deleteBlogroll(id){
	fl.alertConfirm({title:"确认删除?",then:function(){
		fl.ajax({
			url:"/admin/api/blogroll/remove/"+id,
			success:function(data){
				fl.alertOkAndReload("删除成功！");
			}
		})
	}})
}

function editBlogroll(data){
	var content=template("tpl-edit-blogroll",data);
	
	var width=520;
	if($(window).width()<768){
		width=350;
	}
	
  	layerIndex=layer.open({
  		title:"新增",
  		area: [width+'px', '280px'],
	  	  type: 1, 
	  	  content: content //这里content是一个普通的String
  	});
}
/**
 * 获取友链信息,并打开修改框
 */
function updateBlogroll(id){
	fl.ajax({
		url:"/admin/api/blogroll/get/"+id,
		type:"post",
		success:function(data){
			editBlogroll(data.blogroll);
		}
	})
}


function setBasicForm(){
	fl.ajax({
		url:"/admin/api/config",
		dataType:"json",
		success:function(data){
			var config=data.config;
			$(".ico-img").attr("src",(config.ico&&config.ico.length>0)?config.ico:'/favicon.ico')
			$(".logo-img").attr("src",(config.logo&&config.logo.length>0)?config.logo:'/assets/images/logo.png')
			form.val("editConfig",config)
		}
	})
}
function editConfig(data){
	fl.ajax({
		url:"/admin/api/config/edit",
		data:data,
		type:"post",
		success:function(data){
			fl.alertOk({title:"修改成功！"});
		}
	})
}
function doEditBlogroll(data){
	fl.ajax({
		url:"/admin/api/blogroll/saveOrUpdate",
		data:data,
		type:"post",
		success:function(data){
			fl.alertOkAndReload("操作成功！");
		}
	})
}
 
layui.use(['form', 'laytpl','table','upload'],function(){
	form=layui.form;
	upload=layui.upload;
	form.render();
   
	form.on("submit(editConfig)",function(data){
		
		var $form=$(data.form);
		var $input=$form.find("div.layui-form-item").find("input[name='isAuditComment']");
		if($input.length>0){
			if(!data.field.isAuditComment){
				data.field.isAuditComment=0;
			}
		}
		editConfig(data.field);
		return false;
	})

	form.on("submit(editBlogroll)",function(data){
		doEditBlogroll(data.field);
	})
	
	 table = layui.table;
	 table.render({
		 elem: '#blogrolls'
	    ,url:'/admin/api/blogroll/list'
	    ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
	    ,cols: [[
	    	{field:'id', sort: true,width:60,title:"id"}
	      ,{field:'title',title:"标题" }  
	      ,{field:'url',title:"网址" }
	      ,{field:'sort',title:"排序",width:80}
	      ,{templet:'#tpl-blogroll-operate',title:"操作",width:190}
	    ]]
	  });

	 //执行实例
	  upload.render({
		  acceptMime:'image/ico'
			    ,exts: 'ico'
		,field:"upfile",
	    elem: '.upload-ico' //绑定元素
	    ,url: '/admin/api/upload/ico' //上传接口
	  
	    ,done: function(res){
	      	$(".ico-img").attr("src",res.info.url);
	      	$("input[name='ico']").val(res.info.url);
	    },error: function(){
	      fl.alertErro("上传失败！");
	    }
	  });
	  upload.render({
		field:"upfile",
	    elem: '.upload-logo' //绑定元素
	    ,url: '/admin/api/upload/logo' //上传接口
	    ,done: function(res){
	      	$(".logo-img").attr("src",res.info.url);
	      	$("input[name='logo']").val(res.info.url);
	    },error: function(){
	      fl.alertErro("上传失败！");
	    }
	  });
	  setBasicForm();
}) 