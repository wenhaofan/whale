var mditor, htmlEditor;


var markdownUtil=new $.markdownUtil();

var attach_url = $('#attach_url').val();

Dropzone.autoDiscover = false;

function getContent(){
	var fmtType= $('#fmtType').val();
	var content = fmtType == 'markdown' ? mditor.value : htmlEditor.summernote('code');
	if(fmtType == 'markdown'){
	    content=markdownUtil.toHtml(content);
	}
	return content;
}

function setSelectedTag(data){
    var tags=$("#tags").val();
    if(notNull(tags)){
    	var tagArr=tags.split(",");
    	$.each(tagArr,function(index,item){
    		data.push({name:"tag["+index+"].mname",value:item});
    		data.push({name:"tag["+index+"].type",value:"tag"});
    	})
    }
}
function setSelectedCategory(data){
    var categoryIds=$("#categoryIds").val();
    if(notNull(categoryIds)){
    	var categoryArr=categoryIds.split(",");
    	$.each(categoryArr,function(index,item){
    		data.push({name:"category["+index+"].mname",value:item});
    		data.push({name:"category["+index+"].type",value:"category"});
    	})
    }
}

function saveArticle(paras){
   $.ajax({
	   url:"/admin/api/article/add",
	   type:"post",
	   data:paras.fdata,
	   success:function(data){
		   if(fl.isOk(data)){
			   paras.success(data);
		   }
	   }
   })
}

$(document).ready(function () {
	//保存草稿
	$("#draft").click(function(){
		var content =getContent();
	    var title = $('#articleForm input[name=title]').val();
	   
	    if (title == '') {
	        fl.alertWarn('标题不能为空');
	        return;
	    } 
	    
	    var categoryIds= $('#multiple-sel').val();
	    $("#articleForm #categoryIds").val(categoryIds);
	    $("#content-editor").val(content);
	    $('#state').val(0);

	    var fdata= $("#articleForm").serializeArray();
	    
	    setSelectedTag(fdata);
	    
	    setSelectedCategory(fdata);
	    
	    saveArticle({fdata:fdata,success:function(data){
	    	  fl.alertOk({});
			  $("#id").val(data.article.id);
	    }});
	});
	
	
	//保存并发布
	$("#subArticle").click(function(){
		var content = getContent();
	    var title = $('#articleForm input[name=title]').val();
	    if (title == '') {
	        fl.alertWarn('标题不能为空');
	        return;
	    }
	    if (content == '') {
	    	fl.alertWarn('请输入文章内容');
	        return;
	    }
	    var categoryIds= $('#multiple-sel').val();
	    if(categoryIds.length==0){
	    	fl.alertWarn('请选择分类');
	        return;
	    }
	 
	    $("#articleForm #categoryIds").val(categoryIds);
	    $("#content-editor").val(content);
	    $('#state').val(1);
	    
	    var fdata= $("#articleForm").serializeArray();
	    setSelectedTag(fdata);
	    setSelectedCategory(fdata);
	    
	    saveArticle({fdata:fdata,success:function(data){
	    	  fl.alertOk({
				   text:data.msg,
				   then:function(data){
					   setTimeout(() => {
						window.location.href="/admin/article/list";
					}, 500);
				   }
			   })
	    }});
	})
	
    mditor = window.mditor = Mditor.fromTextarea(document.getElementById('md-editor'));
    // 富文本编辑器
    htmlEditor = $('.summernote').summernote({
        lang: 'zh-CN',
        height: 340,
        placeholder: '',
        //上传图片的接口
        callbacks:{
            onImageUpload: function(files) {
            	
            	 var uploadUtil=new $.uploadUtil();
                 uploadUtil.setUploadServerUrl("/admin/api/upload/article");
                 uploadUtil.uploadFile({
                	file:files[0],
                	success:function(data){
            		   if(fl.isOk(data)){
                      	 var url =data.info.url;
                          console.log('url =>' + url);
                          htmlEditor.summernote('insertImage', url);
                      } else {
                          fl.alertError(result.msg || '图片上传失败');
                      }
                	}
                });
               
            }
        }
    });


	$('.modal').on('shown.bs.modal', function (e) {  
        // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零  
        $(this).css('display', 'block');  
       var windowHeight= $(window).height();
       var modalHeight2=$('.modal').height();
        var modalHeight= windowHeight/ 2 - modalHeight2 / 2;  
        $(this).find('.modal-dialog').css({  
            'margin-top': modalHeight  
        });  
    });  
    
    var fmtType = $('#fmtType').val();
    // 富文本编辑器
    if (fmtType != 'markdown') {
        var this_ = $('#switch-btn');
        mditor.value = '';
        $('#md-container').hide();
        $('#html-container').show();
        this_.text('切换为Markdown编辑器');
        this_.attr('type', 'texteditor');
    } else {
        var this_ = $('#switch-btn');
        $('#html-container').hide();
        $('#md-container').show();
        $('#fmtType').val('markdown');
        this_.attr('type', 'markdown');
        this_.text('切换为富文本编辑器');
        htmlEditor.summernote("code", "");
    }

    /*
     * 切换编辑器
     * */
    $('#switch-btn').click(function () {
        var type = $('#fmtType').val();
        var this_ = $(this);
        if (type == 'markdown') {
            // 切换为富文本编辑器
            if($('#md-container .markdown-body').html().length > 0){
                $('#html-container .note-editable').empty().html($('#md-container .markdown-body').html());
                $('#html-container .note-placeholder').hide();

            }
            mditor.value = '';
            $('#md-container').hide();
            $('#html-container').show();
            this_.text('切换为Markdown编辑器');
            $('#fmtType').val('html');
        } else {
            // 切换为markdown编辑器
            if($('#html-container .note-editable').html().length > 0){
                mditor.value = '';
                mditor.value = toMarkdown($('#html-container .note-editable').html());
            }
            $('#html-container').hide();
            $('#md-container').show();
            $('#fmtType').val('markdown');
            this_.text('切换为富文本编辑器');
            htmlEditor.summernote("code", "");
        }
    });
  
    $('.toggle').toggles({
        on: true,
        text: {
            on: '开启',
            off: '关闭'
        }
    });

    $('.select-original').toggles({
    	 on: true,
         text: {
             on: '原创',
             off: '转载'
         }
    })
    


    var thumbdropzone = $('.dropzone');

    // 缩略图上传
    $("#dropzone").dropzone({
        url: "/admin/api/upload/thumb",
        paramName:"upfile",
        filesizeBase:1024,//定义字节算法 默认1000
        maxFilesize: '10', //MB
        fallback:function(){
            fl.alertError('暂不支持您的浏览器上传!');
        },
        acceptedFiles: 'image/*',
        dictFileTooBig:'您的文件超过10MB!',
        dictInvalidInputType:'不支持您上传的类型',
        init: function() {
            this.on('success', function (files, result) {
                if(fl.isOk(result)){
                    var url =result.info.url;
                    thumbdropzone.css('background-image', 'url('+ url +')');
                    thumbdropzone.css('background-size', 'cover');
                    $('.dz-image').hide();
                    $('#thumbImg').val(url);
                }
            });
            this.on('error', function (a, errorMessage, result) {
                if(!result.success && result.msg){
                    tale.alertError(result.msg || '缩略图上传失败');
                }
            });
        }
    });
});

$(function(){
	window.addEventListener("load",function(){
		
		 $("#multiple-sel").select2({
		        width: '100%'
		 });	  // Tags Input
	    $('#tags').tagsInput({
	        width: '100%',
	        height: '35px',
	        defaultText: '请输入文章标签'
	    });

	    tagInit();
		 
		var isUpdate=notNull($("#id").val());
		
		if(isUpdate){
			$(".page-title").text("修改文章");
		 
		}else{
			$(".page-title").text("添加文章");
		  
		}
		
	
	})
})

 

function allow_comment(obj) {
    var this_ = $(obj);
    var on = this_.attr('on');
    if (on == 'true') {
        this_.attr('on', 'false');
        $('#allowComment').val('false');
    } else {
        this_.attr('on', 'true');
        $('#allowComment').val('true');
    }
}

function allow_ping(obj) {
    var this_ = $(obj);
    var on = this_.attr('on');
    if (on == 'true') {
        this_.attr('on', 'false');
        $('#allowPing').val('false');
    } else {
        this_.attr('on', 'true');
        $('#allowPing').val('true');
    }
}


function allow_feed(obj) {
    var this_ = $(obj);
    var on = this_.attr('on');
    if (on == 'true') {
        this_.attr('on', 'false');
        $('#allowFeed').val('false');
    } else {
        this_.attr('on', 'true');
        $('#allowFeed').val('true');
    }
}

function add_thumbimg(obj) {
    var this_ = $(obj);
    var on = this_.attr('on');
    console.log(on);
    if (on == 'true') {
        this_.attr('on', 'false');
        $('#dropzone-container').addClass('hide');
        $('#thumbImg').val('');
    } else {
        this_.attr('on', 'true');
        $('#dropzone-container').removeClass('hide');
        $('#dropzone-container').show();
    }
}
