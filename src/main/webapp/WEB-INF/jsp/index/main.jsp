<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>main</title>
    <link rel="stylesheet" href="resource/plugin/bootstrap/css/bootstrap.min.css">
    <script src="resource/plugin/jquery/jquery.min.js"></script>
    <script src="resource/plugin/bootstrap/js/bootstrap.js"></script>
    <script src="resource/plugin/cocoKeyboard.js"></script>
    <script src="resource/js/main.js"></script>
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
                <li class="active" id="mouse_opt"><a href="#" id="open_ys">打开模拟器</a></li>
                <li><a href="#" id="open_app">打开APP</a></li>
                <li><a href="#" id="show_desktop">显示桌面</a></li>
                <li><a href="#" id="open_help">打开辅助</a></li>
                <li><a href="#" id="help_start">开始</a></li>
                <li><a href="#" id="close_ys">关闭模拟器</a></li>
                <li><a href="#" id="close_help">关闭辅助</a></li>
                <li>
	                <a href="#" id="key_opt" class="dropdown-toggle" data-toggle="dropdown">键盘操作
	                    <b class="caret"></b>
	                </a>
	               <!--  <ul class="dropdown-menu">
	                    <li><a href="#" id="simple_key_opt">单个按键</a></li>
	                    <li class="divider"></li>
	                    <li><a href="#" id="group_key_opt">组合按键</a></li>
	                </ul> -->
                </li>
            </ul>
        </div>
    </div>
</nav>

当前鼠标坐标为：
X：<span id="mp_x"></span>
Y：<span id="mp_y"></span>

<div id="demo" onmousemove="DisplayCoord(event)" onmousedown="whichButton(event)">
    <img src="resource/images/desktop.png"/>
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					按键
				</h4>
			</div>
			<div class="modal-body">
				<div class="input-group">
					<span class="input-group-addon">按键1</span>
					<input type="text" id="key_1" class="form-control" placeholder="光标移动到输入框内，然后按下想要按下的键">
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">按键2</span>
					<input type="text" id="key_2" class="form-control" placeholder="光标移动到输入框内，然后按下想要按下的键">
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button id="key_opt_submit" type="button" class="btn btn-primary">
					提交
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
</body>
</html>