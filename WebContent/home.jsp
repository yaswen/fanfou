<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/home.css" type="text/css"
	media="screen" />
<link rel="apple-touch-icon" href="img/appicon.png">
<link rel="shortcut icon" href="img/favicon.ico" />
<title>首页</title>
<script type="text/javascript" src="JS/jquery-1.7.2.min.js"></script>


</head>
<body>
	<div id="container" class="newlook">
		<div id="header">
			<a href="/Fanfou/home.jsp"><p id="logo"></p></a>
			<div id="navigation">
				<span><a href="">首页</a></span> <span><a href="">我的空间</a></span> <span><a
					href="">私信</a></span> <span><a href="">找人</a></span> <span><a
					href="">随便看看</a></span> <span><a href="">搜索</a></span> <span><a
					href="">设置</a></span> <span><a href="">退出</a></span>

			</div>
		</div>
		<div id="core">

			<div id="main">
				<div id="sysnotice">x个人申请关注你，去看看是谁</div>
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
						<div class="fan">
							<a href="/用户id" title="昵称" class="avatar">
								<img src="http://s3.meituan.net/v1/mss_3d027b52ec5a4d589e68050845611e68/avatar/s0/01/16/6a.jpg?1449550694"  alt="昵称" />
							</a>
							
								<a href="/用户id" class="author">昵称</a>
								<span id="消息id" class="content">消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容</span>
								<span class="stamp">
									<a href="/statuses/消息id" class="time" title="消息时间" stime="消息显示时间">x分钟前</a>
									<span class="method">消息来源</span>
								</span>
								<span class="op">
									<a href="/回复链接" class="reply" title="回复昵称">回复昵称</a>
									<a href="/收藏链接" class="post_act share" title="添加到我的收藏">收藏</a>
									<a href="/转发链接" class="repost" title="转发">转发</a>
								</span>
							
						</div>
						<div class="fan">
							<a href="/用户id" title="昵称" class="avatar">
								<img src="http://s3.meituan.net/v1/mss_3d027b52ec5a4d589e68050845611e68/avatar/s0/01/16/6a.jpg?1449550694"  alt="昵称" />
							</a>
							
								<a href="/用户id" class="author">昵称</a>
								<span id="消息id" class="content">消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容</span>
								<span class="stamp">
									<a href="/statuses/消息id" class="time" title="消息时间" stime="消息显示时间">x分钟前</a>
									<span class="method">消息来源</span>
								</span>
								<span class="op">
									<a href="/回复链接" class="reply" title="回复昵称">回复昵称</a>
									<a href="/收藏链接" class="post_act share" title="添加到我的收藏">收藏</a>
									<a href="/转发链接" class="repost" title="转发">转发</a>
								</span>
							
						</div>
					</div>
				</div>
			</div>
			<div id="sidebar">111</div>
			<div class="clear"></div>
		</div>
		<div id="footer" class="ui-roundedbox"></div>
	</div>
	<script type="text/javascript">
		//判断控制页面初始时左右的高度一致 
		//载入页面结束以后，将sidebar高度与主页高度设为一致

		var hl = $("#main").outerHeight(); //获取左侧left层的高度 
		var hr = $("#sidebar").outerHeight(); //获取右侧right层的高度  
		var mh = Math.max(hl, hr); //比较hl与hr的高度，并将最大值赋给变量mh
		$("#main").height(mh); //将left层高度设为最大高度mh  
		$("#sidebar").height(mh); //将right层高度设为最大高度
	</script>
</body>
</html>