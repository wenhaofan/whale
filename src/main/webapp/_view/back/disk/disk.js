function clickFolder(folderId,folderName){
	
	changeMenuNav(folderId,folderName);
	
	listDiskItem({parentId:folderId});
}

function queryFolderNav (folderId){
	$.ajax({
		url:"/admin/api/disk/listFolderChain/"+folderId,
		dataType:"json",
		success:function(data){
			if(fl.isOk(data)){
				var diskList=data.diskList;
				for(var i = diskList.length-1; i>=0; i--){
					changeMenuNav(diskList[i].id, diskList[i].name);
				}
			}
		}
	})
}

function changeMenuNav(folderId,folderName){
	if($(".folder-goback").hasClass("hide")){
		renderMenuNav(folderId);
	}else{
		convertMenuNav();
	}
	$(".folder-nva").append("<span data-id='"+folderId+"'>"+folderName+"</span>");
}

function convertMenuNav(){
	//获取最后一个节点的名字和文件夹id
	var $lastA=$(".folder-nva span:last");
	if($lastA.length>0){
		var preId=$lastA.data("id");
		var preName=$lastA.text();
		var menuItem=template("tpl-folder-nav-item",{name:preName,id:preId});
		var $menuItem=$(menuItem);
		$lastA.replaceWith($menuItem);
	}
}

function renderMenuNav(folderId){
	$(".folder-goback").removeClass("hide")
	$(".folder-goback").data("id",folderId);
	$(".folder-nva").removeClass("hide");
	$(".folder-nva").append("<span style='color: #c5d8f3;padding: 0 5px;line-height: 16px;'>&gt;</span>");
	$(".folder-all-info").addClass("hide");
}
function goBackFolder(){
	var $lastA=$(".folder-nva a:last");
	$lastA.trigger("click");
}

function deleteNext(folderId){
	var $obj=$(".folder-nva a[data-id='"+folderId+"']");
	if($obj.length==0){
		return;
	}
	
	var $nextObj=$obj.next();
	
	while($nextObj.length>0){
		$nextObj.remove();
		$nextObj=$obj.next();
	}
}

function convertToSpan(obj){
	var folderId=$(obj).data("id");
	var folderName=$(obj).text();
	var $span=$("<span data-id='"+folderId+"'>"+folderName+"</span>");
	$(obj).replaceWith($span);
}

function addEmptyInfo(){
	$(".disk-content").append('<div style="background: url(/assets/disk/empty.png) no-repeat scroll center 0 transparent;padding-top: 146px;width: 488px;position: absolute;left: 50%;top: 50%;text-align: center;margin: -40px 0 0 -244px;"><p style="    display: inline-block;text-align: center;width: 300px;">暂无文件</p></div>')
}

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
		    var stateObject = {};
 
		    var currentUrl=window.location.href;
		    var strArr=currentUrl.split("?");
		    if(strArr.length!=0){
		    	currentUrl=strArr[0];
		    }
		    currentUrl+="?p="+currentFolderId; 
		    history.pushState(stateObject, document.title, currentUrl);
			if(data.state=="ok"){
				var folderNum=0;
				$.each(data.list,function(){
					if(this.type=="folder"){
						addFileItem(this);
						folderNum++;
					}	
				})
				$(".disk-folder-num").text(folderNum);
				var fileNum=0;
				$.each(data.list,function(){
					if(this.type!="folder"){
						addFileItem(this);
						fileNum++;
					}	
				})
				$(".disk-file-num").text(fileNum);
				
				if((folderNum+fileNum)==0){
					addEmptyInfo();
				}
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
	var $folder= $(template("tpl-folder-add-item",{}));
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

function download(id){
	$.ajax({
		url:"/admin/api/disk/get/"+id,
		dataType:"json",
		success:function(data){
			if(fl.isOk(data)){
				 
		       var $eleForm = $("<form method='get'></form>");

	            $eleForm.attr("action",data.disk.url);

	            $(document.body).append($eleForm);

	            //提交表单，实现下载
	            $eleForm.submit();
			}
		}
	})
}