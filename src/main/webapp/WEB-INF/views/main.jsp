<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="user" value="${sessionScope.CONTEXT_APP.user}" />
<c:set var="community" value="${sessionScope.CONTEXT_APP.community}" />
<c:set var="menu_h" value="${sessionScope.CONTEXT_APP.menu_h.children}" />
<c:set var="menu_v" value="${sessionScope.CONTEXT_APP.menu_v.children}" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>管理后台</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
<base target="mainFrame">
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/font-awesome/css/font-awesome.min.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/bootstrap/css/bootstrap.min.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/uniform/css/uniform.default.css' />" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN THEME STYLES -->
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style-wechat.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style-responsive.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/plugins.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/themes/default.css' />" id="style_color" />
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/pages/main.css' />" />
<!-- END THEME STYLES -->
</head>
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<div class="header navbar navbar-inverse navbar-fixed-top">
		<!-- BEGIN TOP NAVIGATION BAR -->
		<div class="header-inner">
			<!-- BEGIN LOGO -->
			<a class="navbar-brand" href="<c:url value='/main'/>"> <img src="<c:url value='/assets/img/logo.png'/>" alt="logo" class="img-responsive" />
			</a>
			<!-- END LOGO -->
			<!-- BEGIN HORIZANTAL MENU -->
			<div class="hor-menu hidden-sm hidden-xs">
				<ul class="nav navbar-nav">
					<c:forEach items="${menu_h}" var="subMenu">
						<c:choose>
							<c:when test="${subMenu.parent==false}">
								<li><a href="${ctx}${subMenu.url}">${subMenu.name}</a></li>
							</c:when>
							<c:when test="${subMenu.parent==true}">
								<li>
									<a data-toggle="dropdown" data-hover="dropdown" data-close-others="true" href=""> ${subMenu.name} <i class="fa fa-angle-down"> </i></a>
									<ul class="dropdown-menu">
										<c:forEach items="${subMenu.children}" var="menuItem">
											<li><a href="${ctx}${menuItem.url}"> ${menuItem.name} </a></li>
										</c:forEach>
									</ul>
								</li>
							</c:when>
						</c:choose>
					</c:forEach>
					<c:if test="${!empty community}">
					<li><a href="javascript:void(0)">${community.name}</a></li>
					</c:if>
				</ul>
			</div>
			<!-- END HORIZANTAL MENU -->
			<!-- BEGIN RESPONSIVE MENU TOGGLER -->
			<a href="javascript:;" class="sidebar-toggler navbar-toggle"> <img src="<c:url value='/assets/img/menu-toggler.png'/>" alt="" />
			</a>
			<!-- END RESPONSIVE MENU TOGGLER -->
			<!-- BEGIN TOP NAVIGATION MENU -->
			<ul class="nav navbar-nav pull-right">
				<!-- BEGIN THEME DROPDOWN -->
				<li class="dropdown"><a href="#" class="dropdown-toggle" title="颜色选择" data-toggle="dropdown" data-hover="dropdown" data-close-others="true"> <i
						class="fa fa-cog"> </i><span class="username">换肤</span> <i class="fa fa-angle-down"> </i>
				</a>
					<ul class="dropdown-menu theme-menu">
						<li>
							<p>系统可选择颜色</p>
						</li>
						<li class="theme-colors">
							<ul>
								<li class="color-black current color-default" data-style="default"></li>
								<li class="color-blue" data-style="blue"></li>
								<li class="color-brown" data-style="brown"></li>
								<li class="color-purple" data-style="purple"></li>
								<li class="color-grey" data-style="grey"></li>
								<li class="color-white color-light" data-style="light"></li>
							</ul>
						</li>
					</ul></li>
				<!-- END THEME DROPDOWN -->
				<!-- BEGIN USER LOGIN DROPDOWN -->
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true"> <i
						class="fa fa-user"> </i> <span class="username"> ${user.name} </span> <i class="fa fa-angle-down"> </i>
				</a>
					<ul class="dropdown-menu">
						<li><a href="${ctx}/user/loginLog"> <i class="fa fa-calendar"> </i>登录日志
						</a></li>
						<li><a href="${ctx}/user/changePassword"> <i class="fa fa-key">修改密码</i> 
						</a></li>
						<li><a href="javascript:;" id="trigger_fullscreen"> <i class="fa fa-move"> </i> 全屏模式
						</a></li>
						<li><a href="<c:url value='/lock'/>"> <i class="fa fa-lock"> </i> 锁屏
						</a></li>
						<li><a href="<c:url value='/auth/logout'/>"> <i class="fa fa-key"> </i> 安全退出
						</a></li>
					</ul></li>
				<!-- END USER LOGIN DROPDOWN -->
			</ul>
			<!-- END TOP NAVIGATION MENU -->
		</div>
		<!-- END TOP NAVIGATION BAR -->
	</div>
	<!-- END HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN HORIZONTAL MENU PAGE SIDEBAR1 -->
		<div class="page-sidebar navbar-collapse collapse">
			<ul class="page-sidebar-menu">
				<c:set var="first" value="true" />
				<c:forEach items="${menu_v}" var="subMenu">
					<c:choose>
						<c:when test="${first==true}">
							<c:set var="first" value="false" />
							<li class="start active"><a href="javascript:;"> <i class="${subMenu.icon}"> </i> <span class="title"> ${subMenu.name} </span> <span
									class="arrow"> </span> <span class="selected"> </span>
							</a>
								<ul class="sub-menu">
									<li><c:forEach items="${subMenu.children}" var="menuItem">
											<a href="${ctx}${menuItem.url}"> <i class="${subMenu.icon}"> </i> <span class="title"> ${menuItem.name} </span>
											</a>
										</c:forEach></li>
								</ul></li>
						</c:when>
						<c:when test="${first==false}">
							<li><a href="javascript:;"> <i class="${subMenu.icon}"> </i> <span class="title"> ${subMenu.name} </span> <span class="arrow"> </span> <span>
								</span>
							</a>
								<ul class="sub-menu">
									<li><c:forEach items="${subMenu.children}" var="menuItem">
											<a href="${ctx}${menuItem.url}"> <i class="${subMenu.icon}"> </i> <span class="title"> ${menuItem.name} </span>
											</a>
										</c:forEach></li>
								</ul></li>
						</c:when>
					</c:choose>
				</c:forEach>
			</ul>
		</div>
		<!-- END BEGIN HORIZONTAL MENU PAGE SIDEBAR -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<div class="workspace">
			<iframe frameborder="0" id="mainFrame" name="mainFrame" src="${ctx}/user/info"></iframe>
			</div>
		</div>
		<!-- END PAGE -->
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<div class="footer">
		<div class="footer-inner"><center>2014 &copy; power by huaxia.</center></div>
		<div class="footer-tools">
			<span class="go-top"> <i class="fa fa-angle-up"> </i>
			</span>
		</div>
	</div>
	<!-- END FOOTER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--
	[if lt IE 9]> <script type="text/javascript" src="<c:url value='/assets/plugins/excanvas.min.js'/>"></script> <script type="text/javascript" src="<c:url value='/assets/plugins/respond.min.js'/>"></script> <![endif]
-->
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery-1.10.2.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery-migrate-1.2.1.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/bootstrap/js/bootstrap.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery.blockui.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery.cookie.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/uniform/jquery.uniform.min.js'/>">
		
	</script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script type="text/javascript" src="<c:url value='/assets/scripts/app.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/scripts/pages/main.js'/>">
		
	</script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<script type="text/javascript">
		$(function() {
			Main.init();
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>
