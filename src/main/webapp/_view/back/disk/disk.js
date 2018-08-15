function listDiskItem(query){
	$(".disk-content").empty();
	if(!query){
		query={};
	}
	currentFolderId=query.parentId;
	$.ajax({
		url:"/admin/api/disk/list/",
		data:query,
		dataType:"json",
		success:function(data){
			if(data.state=="ok"){
				$.each(data.list,function(){
					addFileItem(this);
				})
			}else{
				console.log("erro----加载失败");
			}
		}
	})
}

function addFileItem(paras){
	
	var type=getType(paras.type);
	paras.type=type;
	
	var $diskItem=$(template("tpl-disk-item",paras));
	if(paras.sort!=null&&paras.sort=="first"){
		var $temp=$(".disk-content .disk-item:first-child");
		if($temp.length==0){
			$(".disk-content").append($diskItem);
		}else{
			$temp.before($diskItem);
		}
		
	}else{
		var $temp=$(".disk-content .disk-item:last-child").after();
		if($temp.length==0){
			$(".disk-content").append($diskItem);
		}else{
			$temp.after($diskItem);
		}
	}
}

function createFolderItem(){
	var $folder= $(template("tpl-file-edit-item",{}));
	var $firstDiskItem=$(".disk-content .disk-item:first-child");
	if($firstDiskItem.length==0){
		$(".disk-content").append($folder);
	}else{
		$firstDiskItem.before($folder);
	}
}

function createFolder(paras){
	paras.parentId=currentFolderId;
	$.ajax({
		url:"/admin/api/disk/createFolder",
		data:paras,
		dataType:"json",
		success:function(data){
			if(data.state=="ok"){
				data.disk.sort="first";
				addFileItem(data.disk);
			}else{
				console.log("erro----文件创建失败！");
			}
		}
	})
}
var thumbImg={img:["png","jpg"],apk:"apk",code:["java","js","css","python","php","vue","class"],miss:null,exe:"exe", folder:"folder", html:"html"
	, music:"music", pdf:"pdf", txt:"txt", video:"video",word:"word",zip:"zip"}
	
function getType(type){
 	for (key in thumbImg) {
 
        
        if(key==type){
        	return key;
        }
        
        if(isArray(thumbImg[key])){
        	for(j = 0,len=thumbImg[key].length; j < len; j++) {
        		if(thumbImg[key][j]==type){
        			return key;
        		}
       		 }
        	 
        }
    }
 	
 	return "miss";
}

 

function isArray(obj){
	return Object.prototype.toString.call(obj)  === '[object Array]';
}

function isJsonObj(str) {
	if(typeof(str) == "object" &&Object.prototype.toString.call(str).toLowerCase() == "[object object]" && !str.length){
		 return true;
     }
	 return false;
}
	
$(function(){
	$(".disk-create-folder").click(function(){
		createFolderItem();
	})
	
	$(".disk-content").on("click",".confirm-create-folder",function(){
		var folderName=$(this).prev().val();
		createFolder({name:folderName});
		$(this).parent().parent().remove();
	})
	$(".disk-content").on("click",".disk-folder",function(){
		var id=$(this).data("id");
		var name=$(this).find(".file-name").text();
		clickFolder(id,name);
	})
	
	$("body").on("change",".disk-upload-file",function(){
		var that=this;
		uploadUtil.uploadFile({data:{"parentId":currentFolderId},file:$(this),success:function(data){
			alert("上传成功！")
			
			addFileItem(data.disk);
			
			$(that).replaceWith('<input type="file" class=" disk-upload-file " style="display:none;">');
		}})
	})
	
	$(".disk-upload-button").click(function(){
		$(".disk-upload-file").trigger("click");
	})
	
	
})