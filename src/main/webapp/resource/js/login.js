$(function () {
    $('#myModal').modal('show');

    $("#login").click(function () {
        login();
    });
    $("#pass").keypress(function (e) {
        if (e.which == 13) {
            login();
        }
    });
});

function login() {
    var postData = {name: $("#name").val(), pass: $("#pass").val()};
    if (postData.name && postData.pass) {
        postData.pass = hex_md5(postData.pass);
        $.post("login", postData, function (data) {
            if (data.code != 0) {
                alert(data.error_msg);
            } else {
                window.location.href = "index";
            }
        }, "json");
    } else {
        alert("请输入账号和密码");
    }
}