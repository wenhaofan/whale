$(function(){
	$("body").on("click",".privacy",function(){
		var content=$(this).data("content");
		var text=$(this).text();
		
		$(this).text(content);
		$(this).data("content",text);
	})
	$("body").on("click",".mupdate",function(){
		$.ajax({
			url:"/admin/api/metaConfig/mget/"+$(this).data("id"),
			dataType:'json',
			success:function(data){
				if(fl.isOk(data)){
					renderMEdit(data.config);
				}
			}
		})
 
	})
	$("body").on("click",".bupdate",function(){
		$.ajax({
			url:"/admin/api/baiduConfig/bget/"+$(this).data("id"),
			dataType:'json',
			success:function(data){
				if(fl.isOk(data)){
					renderBEdit(data.config);
				}
			}
		}) 
	})
	
	$("body").on("click",".madd",function(){
		renderMEdit({});
	})
	$("body").on("click",".badd",function(){
		renderBEdit({});
	})
	
	var deleteMid;
	
	$("body").on("click",".mdelete",function(){
		deleteMid=$(this).data("id");
		fl.alertConfirm({title:"是否确认删除？",then:function(){
			$.ajax({
				url:"/admin/api/metaConfig/mconfigDelete/"+deleteMid,
				success:function(data){
					if(fl.isOk(data)){
						fl.alertOkAndReload();
					}
				}
			})
		}})
		
	})
	var deleteBid;
	$("body").on("click",".bdelete",function(){
		deleteBid=$(this).data("id");
		fl.alertConfirm({title:"是否确认删除？",then:function(){
			$.ajax({
				url:"/admin/api/baiduConfig/bconfigDelete/"+deleteBid,
				success:function(data){
					if(fl.isOk(data)){
						fl.alertOkAndReload();
					}
				}
			})
  
		}});
	})
})

 
 
function renderMEdit(data){
	var content=template("tpl-medit",data);
	var width=520;
	if($(window).width()<768){
		width=350;
	}
  	layerIndex=layer.open({
  		title:"新增",
  		area: [width+'px', '350px'],
	  	  type: 1, 
	  	  content: content //这里content是一个普通的String
  	});
}
function renderBEdit(data){
	var content=template("tpl-bedit",data);
	var width=520;
	if($(window).width()<768){
		width=350;
	}
  	layerIndex=layer.open({
  		title:"新增",
  		area: [width+'px', '230px'],
	  	  type: 1, 
	  	  content: content //这里content是一个普通的String
  	});
}

function medit(data){
	$.ajax({
		url:"/admin/api/metaConfig/mconfigEdit",
		data:data,
		type:"post",
		success:function(data){
			if(fl.isOk(data)){
				fl.alertOkAndReload();
			}
		}
	})
}
function bedit(data){
	$.ajax({
		url:"/admin/api/baiduConfig/bconfigEdit",
		data:data,
		type:"post",
		success:function(data){
			if(fl.isOk(data)){
				fl.alertOkAndReload();
			}
		}
	})
}

layui.use(['form','table','laytpl'],function(){
	form=layui.form;
	table=layui.table;
	
	form.render();
	
	table.render({
		elem: '#metaweblog'
	   ,url:'/admin/api/metaConfig/mList'
	   ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
	   ,cols: [[
	   	{field:'id', sort: true,width:60,title:"id"}
	     ,{field:'website',title:"网站",width:100}
	     ,{field:'url',title:"接口路径"}
	     ,{field:'userName',title:"账号",width:100}
	     ,{templet:'#tpl-mprivacy',title:"密码",width:150}
	     ,{templet:'#tpl-moperation',title:"操作",width:150}
	   ]]
	 });
	
	table.render({
		  elem: '#baiduseo'
		   ,url:'/admin/api/baiduConfig/bList'
		   ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
		   ,cols: [[
		   	 {field:'id', sort: true,width:60,title:"id"}
		     ,{field:'site',title:"网站" }
		     ,{templet:'#tpl-bprivacy',title:"token",width:200}
		     ,{templet:'#tpl-boperation',title:"操作",width:150}
		]]
	});
	
	form.on("submit(editMconfig)",function(data){
		medit(data.field);
	})
	form.on("submit(editBconfig)",function(data){
		bedit(data.field);
	})
	
	form.on("submit(pushLinks)",function(data){
		pushLinks(data.field);
	})
 
 
}) 

function pushLinks(data){
	$.ajax({
		data:data,
		url:"/admin/api/baiduConfig/pushBaiduLinks",
		type:"post",
		success:function(data){
			if(fl.isOk(data)){
				fl.alertOk({title:"提交成功！"});
			}
		}
	})
}