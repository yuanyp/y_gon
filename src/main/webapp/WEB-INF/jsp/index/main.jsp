<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>main</title>
    <link rel="stylesheet" href="resource/plugin/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="resource/css/common.css">
    <script src="resource/plugin/jquery/jquery.min.js"></script>
    <script src="resource/plugin/bootstrap/js/bootstrap.js"></script>
    <script src="resource/plugin/cocoKeyboard.js"></script>
    <script src="resource/js/main.js"></script>
</head>
<body>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">${sessionScope._userinfo.userName}，欢迎使用</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li class="active" id="chognzhi"><a href="#" id="open_ys">充值</a></li>
                <li><a class="white">服务到期时间：${sessionScope._userinfo.expiresDate}</a></li>
            </ul>
            <ul class="nav navbar-nav logout">
                <li id="logout"><a href="#" class="white">退出</a></li>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>