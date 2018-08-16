<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/home.css" type="text/css" media="screen" />
<link rel="apple-touch-icon" href="img/appicon.png">
<link rel="shortcut icon" href="img/favicon.ico" />
<title>首页</title>
</head>
<body>
<div id="container" class="newlook">
	<div id="header">
		<a href="/Fanfou/home.jsp"><p id="logo"></p></a>
		<div id="navigation">
			<span><a href="">首页</a></span>
			<span><a href="">我的空间</a></span>
			<span><a href="">私信</a></span>
			<span><a href="">找人</a></span>
			<span><a href="">随便看看</a></span>
			<span><a href="">搜索</a></span>
			<span><a href="">设置</a></span>
			<span><a href="">退出</a></span>
			
		</div>
	</div>
	<div id="core">
		
			<div id="main">
				<div id="sysnotice">
					x个人申请关注你，去看看是谁
				</div>
				<div id="statusUpdate">
					<form action="statusUpdate.action" method="post">
						<h2 class="slogan">你在做什么</h2>
						<textarea name="content" id="status"></textarea>
						
					</form>
				</div>
				<div id="content">
					<div id="timeline-notification">
						<a>新增x条最新消息，点击查看</a>
					</div>
					<div id="stream">
						
					</div>
				</div>
			</div>
			<div id="sidebar">
				
			</div>
		
	</div>
	<div id="footer" class="ui-roundedbox"></div>
</div>
</body>
</html>