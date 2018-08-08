jQuery.extend({
	uploadUtil:function(){
	}
});

/**
 * 上传file form
 */
$.uploadUtil.prototype.uploadFileForm=function(paras){
	  if(this.uploadServerUrl==undefined){
			throw "upload.js->uploadServerUrl not defined"
	  }
	 
	  $.ajax({
	      url:this.uploadServerUrl,     //上传图片请求的路径
	      method:'POST',            //方法
	      data:paras.dataForm,                 //数据
	      processData: false,        //告诉jQuery不要加工数据
	      dataType:'json',
	      contentType: false,   
	      success: function(data) {
	      	paras.success(data);
	      },
	      error:function(data){
	      	if(paras.erro|| typeof paras.error  === "function"){
			  paras.erro(data);
	      	}
	      }
  });
}
/**
 * 上传js file文件
 */
$.uploadUtil.prototype.uploadFile= function (paras) {
	var dataForm=new FormData();
	var file=paras.file;
	
	var resultFile;
	//如果是jquery对象 且类型为input file 则抛出异常
	if(this.isJquery(file)&&this.isFileInput(file)){
		resultFile=$(file).get(0).files[0];
	}else{
		resultFile=file;
	}
	dataForm.append('upfile',paras.file);
	paras.dataForm=dataForm;
	this.uploadFileForm(paras);
}
/**
 * 判断是否是file input
 */
$.uploadUtil.prototype.isFileInput=function(obj){
	var tagName=$(obj)[0].tagName;
	return tagName=="INPUT"&&obj.attr("type").toLowerCase=="file";
}
/**
 * 判断是否是jquery对象
 */
$.uploadUtil.prototype.isJquery=function(obj){
	return obj instanceof jQuery;
}

$.uploadUtil.prototype.uploadServerUrl=undefined;
/**
 * 设置上传路径
 */
$.uploadUtil.prototype.setUploadServerUrl=function(uploadServerUrl){
	this.uploadServerUrl=uploadServerUrl;
}

/**
 * 获取上传路径
 */
$.uploadUtil.prototype.getUploadServerUrl=function(){
	return this.uploadServerUrl;
}