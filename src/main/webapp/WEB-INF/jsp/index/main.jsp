<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>main</title>
    <link rel="stylesheet" href="resource/plugin/bootstrap/css/bootstrap.min.css">
    <script src="resource/plugin/bootstrap/js/bootstrap.js"></script>
    <script src="resource/plugin/jquery/jquery.min.js"></script>
    <style>
        .fakeimg {
            height: 200px;
            background: #aaa;
        }
    </style>
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
            <a class="navbar-brand" href="#">远程操作</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">刷新桌面</a></li>
                <li><a href="#">页面 2</a></li>
                <li><a href="#">页面 3</a></li>
            </ul>
        </div>
    </div>
</nav>

<div>
    <img src="resource/images/desktop.png" width="1366" height="768"/>
</div>

</body>
</html>