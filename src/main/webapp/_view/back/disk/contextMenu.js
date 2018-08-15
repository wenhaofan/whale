var contextMenu= (function(){
    return {
    	menuItemMap:{},
    	menuMap:{},
    	on:function(selector,paras){
    	   var that=this;
    	   if(!paras){
    		   throw  new Error( 'paras can not be bull' );
    	   }
    	   //绑定右键点击事件,右键生成目录
		   $('body').on("contextmenu",selector, function (e){
			  var id= $(this).attr("id");
			   that.menuItemMap[id]=this;
			   that.gen(this,e,paras);
			   return false;
           });
    	},
        gen: function (clickObj,e,paras) {
        	var that=this;
        	var menuId=$(clickObj).attr("menu-id");
        	var $menu=$("#"+menuId);
        	if($menu.length==0){
        		 menuId="context-menu"+this.getRandomStr();
        		 $menu = $('<div class="context-menu" id="'+menuId+'" ><ul></ul></div>');
                 for (var i = 0, l = paras.length; i < l; i++) {
                 	var $li=this.getItem(paras[i]);
                 	$menu.find("ul").append($li);
                 }
                 $(clickObj).attr("menu-id",menuId);
        	}
        	l = ($(document).width() - e.clientX) < $menu.width() ? (e.clientX - $menu.width()) : e.clientX;
            t = ($(document).height() - e.clientY) < $menu.height() ? (e.clientY - $menu.height()) : e.clientY;
          	$menu.css({left: l,top: t}).show();
          	$menu.appendTo("body");
          	
		},getItem(paras){
			//为每一个目录生成一个随机id
			var id="contextMenuLi"+this.getRandomStr();
		
			var $li='<li><a href="javascript:void(0)" id="'+id+'">'+paras.text+'</a></li>';
			//遍历绑定事件
			var events=paras.events;
			
			var that=this;
	    	for (var key in events) {
	    		var method=events[key];
    			$('body').on(key,"#"+id, function (){
    				method(that.menuItemMap[id],$(this));
    			});
	    	}
	    	
		    return $li;
		},getRandomStr(){
			return Math.random().toString(36).substr(2);
		}
	}})();

contextMenu.on(".disk-item",[
	{
		events:{
			"click":function(){
				alert("打开");
			}
		},text:"打开"
	},{
		events:{
			"click":function(){
				alert("下载");
			}
		},text:"下载"
	}
]);
//取消右键
$('html').on('contextmenu', function (){return false;}).click(function(){
	$('.context-menu').hide();
});
