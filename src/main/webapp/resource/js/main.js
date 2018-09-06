var timer = null;
$(function (){
	$.ajaxSetup({
	    async : false
	});
	$("#demo").click(function () {
        timer && clearTimeout(timer);
        timer = setTimeout(function (){
            post_mouse_opt(0)
		},300);
    });
    $("#demo").dblclick(function () {
        timer && clearTimeout(timer);
        post_mouse_opt(1);
    });
    
    var d = false;
    $("#open_ys").click(function () {
		var postData = {"x_y":"287,14","l":false,"d":d};//右键
    	mouse_opt(postData,100);
    	postData = {"x_y":"315,168","l":true,"d":d};//左键
    	mouse_opt(postData,100);
    });
    $("#open_app").click(function () {
    	var postData = {"x_y":"1275,1020","l":true,"d":d};
    	mouse_opt(postData,100);
    });
    $("#show_desktop").click(function () {
    	var postData = {"x_y":"1275,1020","l":true,"d":d};
    	mouse_opt(postData,100);
    });
    $("#open_help").click(function () {
    	var postData = {"x_y":"1275,1020","l":false,"d":d};//右键
    	mouse_opt(postData,100);
    	postData = {"x_y":"1275,1020","l":true,"d":d};//左键
    	mouse_opt(postData,100);
    });
    $("#help_start").click(function () {
    	var postData = {"x_y":"1275,1020","l":true,"d":d};
    	mouse_opt(postData,100);
    });
    $("#close_ys").click(function () {
    	var postData = {"x_y":"1275,1020","l":true,"d":d};
    	mouse_opt(postData,100);
    });
    $("#close_help").click(function () {
    	var postData = {"x_y":"1275,1020","l":true,"d":d};
    	mouse_opt(postData,100);
    });
    
    bind_key_opt();
});
function bind_key_opt(){
	$("#key_opt").click(function (){
    	$('#myModal').modal('show');
    	$("#myModal input").val("");
    });
	$("#key_1").cocoKeyboard();
	$("#key_2").cocoKeyboard();
	$("#key_opt_submit").click(function (){
		var key_1 = $("#key_1").val();
		if(key_1){
			var key_2 = $("#key_2").val();
			key_opt({"key_1":key_1,"key_2":key_2});
		}
	});
}
/**
 * 0:左键；1:左键双击；2：右键
 * @param type
 */
function post_mouse_opt(type){
	var x_y = $("#mp_x").text() + "," + $("#mp_y").text();
	var l = true;
	if(type == 0){
		l = true;
	}else if(type == 2){
		l = false;
	}
	var d = false;
	if(type == 1){
		d = true;
	}
	var postData = {"x_y":x_y,"l":l,"d":d};
	mouse_opt(postData,0,true);
}
function mouse_opt(postData,delay,refresh){
	if(!delay){
		delay = 0;
	}
	setTimeout(function (){
		$.post("mouse_opt",postData,function (data){
	        if(data.code != 0){
	            alert(data.error_msg);
	        }else{
	        	if(refresh){
	        		refresh_desk();
	        	}
			}
	    },"json");
	}, delay);
}
function refresh_desk(){
	setTimeout(function (){
		$.post("refresh",function (data){
	        if(data.code != 0){
	            alert(data.error_msg);
	        }else{
	        	var $img = $("#demo").find("img");
	        	var src = "resource/images/desktop.png";
	            $img.attr("src",src + "?_t=" + new Date().getTime());
			}
	    },"json");
	}, 1500);
    
}
function whichButton(event)
{
    var btnNum = event.button;
    if (btnNum==2){
        post_mouse_opt(2);
    }
}
function getX(obj){
    var parObj=obj;
    var left=obj.offsetLeft;
    while(parObj=parObj.offsetParent){
        left+=parObj.offsetLeft;
    }
    return left;
}

function getY(obj){
    var parObj=obj;
    var top=obj.offsetTop;
    while(parObj = parObj.offsetParent){
        top+=parObj.offsetTop;
    }
    return top;
}

function DisplayCoord(event){
    var top,left,oDiv;
    oDiv=document.getElementById("demo");
    top=getY(oDiv);
    left=getX(oDiv);
    document.getElementById("mp_x").innerHTML = (event.clientX-left+document.documentElement.scrollLeft)  -2;
    document.getElementById("mp_y").innerHTML = (event.clientY-top+document.documentElement.scrollTop) -2;
}
/**
 * 键盘操作
 * @returns
 */
function key_opt(postData){
	$.post("key_opt",postData,function (data){
		if(data.code != 0){
            alert(data.error_msg);
        }
	},"json");
}