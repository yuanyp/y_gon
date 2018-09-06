$(function (){
	$('#myModal').modal('show');
	
	$("#login").click(function (){
    	login();
    });
});

function login(){
	var postData = {name:$("#name").val(),pass:$("#pass").val()};
	$.post("login",postData,function (data){
        if(data.code != 0){
            alert(data.error_msg);
        }else{
        	window.location.href="index";
		}
    },"json");
    
}