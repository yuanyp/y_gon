var timer = null;
$(function (){
	$("#key_opt").click(key_opt);
	$("#demo").click(function () {
        timer && clearTimeout(timer);
        timer = setTimeout(function (){
            post_mouse_opt(0)
		},300);
    });
    $("#demo").dblclick(function () {
        timer && clearTimeout(timer);
        post_mouse_opt(1)
    });
});
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
    $.post("mouse_opt",postData,function (data){
        if(data.code != 0){
            alert(data.error_msg);
        }else{
            refresh_desk();
		}
    },"json");
}
function refresh_desk(){
    $.post("refresh",function (data){
        if(data.code != 0){
            alert(data.error_msg);
        }else{
        	var $img = $("#demo").find("img");
        	var src = "resource/images/desktop.png";
            $img.attr("src",src + "?_t=" + new Date().getTime());
		}
    },"json");
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
function key_opt(e){
	$.post("key_opt",function (data){
		if(data.code == 0){
			alert("操作成功");	
		}
	},"json");
}