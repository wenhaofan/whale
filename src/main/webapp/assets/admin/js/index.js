var fl=new $.fl();
 
$(function(){
	$(".menu-tree li").each(function(){
		if($(this).find("a").attr("href")=="#(requestUrl)"){
			$(this).addClass("layui-this")
		}
	})
	$(".menu-tree li").click(function(){
		$(".menu-tree li").each(function(){
			$(this).removeClass("layui-this")
		})
		$(this).addClass("layui-this");
	})
	$(document).on('pjax:start', function() { NProgress.start(); });
	$(document).on('pjax:end',   function() { NProgress.done();  });
 	$(document).pjax('a[pjax]', '.layui-fluid');
	
	$(".layui-toggle-menu").click(function(){
		if($(this).find("i").hasClass("layui-icon-spread-left")){
			$(this).find("i").removeClass("layui-icon-spread-left");
			$(this).find("i").addClass("layui-icon-shrink-right");
			$(".layui-side").show();
			$(".layui-body").css("left","200px");
		}else{
			$(this).find("i").addClass("layui-icon-spread-left");
			$(this).find("i").removeClass("layui-icon-shrink-right");
			$(".layui-side").hide();
			$(".layui-body").css("left","0");
		}
	})
	
	$(window).resize(function(){
		if($(window).width()<=768){
			$(".layui-toggle-menu i").addClass("layui-icon-spread-left");
			$(".layui-toggle-menu i").removeClass("layui-icon-spread-right");
			$(".layui-side").hide();
			$(".layui-body").css("left",0);
		}
	})

})


$(function(){
	if($(window).width()<=768){
		$(".layui-toggle-menu i").addClass("layui-icon-spread-left");
		$(".layui-side").hide();
		$(".layui-body").css("left",0);
	}else{
		$(".layui-toggle-menu i").addClass("layui-icon-shrink-right");
		$(".layui-side").show();
	}
})

var updatePwdIndex;
layui.use(['form','layer'],function(){
	form=layui.form;
	layer=layui.layer;
	
	  //监听提交
	  form.on('submit(updatePwd)', function(data){
		 
		   $.ajax({
			   url:"/admin/api/user/editPwd",
			   data:data.field,
			   type:"post",
			   success:function(data){
				   if(fl.isOk(data)){
					   fl.alertOk({title:"密码修改成功！"});
					   layer.close(updatePwdIndex);
				   }
			   }
		   })
	  	 return false;
	  });
	  
	  //监听提交
	  form.on('submit(updateInfo)', function(data){
		 
		   $.ajax({
			   url:"/admin/api/user/editInfo",
			   data:data.field,
			   type:"post",
			   success:function(data){
				   if(fl.isOk(data)){
					   fl.alertOk({title:"修改成功！"});
					   layer.close(updatePwdIndex);
				   }
			   }
		   })
	  	 return false;
	  });
})
function updatePwd(){
	updatePwdIndex=layer.open({
  		title:"修改密码",
  		area: ['340px', '215px'],
	  	type: 1, 
	  	content: $("#tpl-update-pwd").html(),
	  	success:function(){
	  		form.render();
	  	}
  	});
}
function updateInfo(){
	updatePwdIndex=layer.open({
  		title:"修改个人信息",
  		area: ['340px', '265px'],
	  	type: 1, 
	  	content: $("#tpl-update-info").html(),
	  	success:function(){
	  		form.render();
	  	}
  	});
}
layui.use('element', function(){
  var element = layui.element;
  
  
});

$(function(){
	$("#updatePwd").click(function(){
		updatePwd();
	});
	$("#updateInfo").click(function(){
		updateInfo();
	});
	$(".layui-layout-right").click(function(){
		$(this).trigger("mouseover");
	})
})