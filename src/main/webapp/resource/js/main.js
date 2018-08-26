$(function (){
	$("#key_opt").click(key_opt);
});

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