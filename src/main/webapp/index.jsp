<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="resource/plugin/bootstrap/css/bootstrap.min.css">
    <script src="resource/plugin/jquery/jquery.min.js"></script>
    <script src="resource/plugin/bootstrap/js/bootstrap.js"></script>
	<script src="resource/plugin/md5.js"></script>
    <script src="resource/js/login.js"></script>
<title>login</title>
</head>
<body>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					登录
				</h4>
			</div>
			<div class="modal-body">
				<div class="input-group">
					<span class="input-group-addon">账号</span>
					<input type="text" id="name" class="form-control" placeholder="请输入账号">
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">密码</span>
					<input type="password" id="pass" class="form-control" placeholder="请输入密码">
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button id="login" type="button" class="btn btn-primary">
					登录
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
</body>
</html>