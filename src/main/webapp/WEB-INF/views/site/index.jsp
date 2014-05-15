<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="user" value="${sessionScope.CONTEXT_APP.user}" />
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
<title>main</title>
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
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/pages/public.css' />" />
<!-- END THEME STYLES -->
</head>
<!-- BEGIN BODY -->
<body>
	<div class="function-page" style="margin:20px;">
	    <div class="function-list">
	        <div class="function-img function-mrgin-right10">
	        <img src="<c:url value='/assets/img/manage.png'/>" />
	        </div>
	        <div class="function-img function-mrgin-left10">
	        <img src="<c:url value='/assets/img/ShoppingMall.png'/>" />
	        </div>
	    </div>
	    <div class="function-list">
	        <div class="function-img function-mrgin-right10">
	            <img src="<c:url value='/assets/img/message.png'/>" />
	        </div>
	        <div class="function-img function-mrgin-left10">
	            <div class="function-img-top">
	            <img src="<c:url value='/assets/img/bbs.png'/>" />
	            </div>
	            <div class="function-img-bottom">
	            <img src="<c:url value='/assets/img/owner.png'/>" />
	            </div>
	        </div>
	    </div>
	    <div class="all-bottom">
				<div class="all-bottom-left"><img src="<c:url value='/assets/img/return.png'/>" /></div>
				<div class="all-bottom-middle"></div>
				<div class="all-bottom-right"><img src="<c:url value='/assets/img/refresh.png'/>" /></div>
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
