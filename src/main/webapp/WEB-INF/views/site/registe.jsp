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
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN THEME STYLES -->
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/pages/public.css' />" />
<!-- END THEME STYLES -->
<meta content="application/xhtml+xml;charset=UTF-8" http-equiv="Content-Type">
<meta content="no-cache,must-revalidate" http-equiv="Cache-Control">
<meta content="no-cache" http-equiv="pragma">
<meta content="0" http-equiv="expires">
<meta content="telephone=no, address=no" name="format-detection">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta name="apple-mobile-web-app-capable" content="yes" />
<!-- apple devices fullscreen -->
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />

</head>
<!-- BEGIN BODY -->
<body class="margin-top-10" style="text-align:center;">
	<div class="row" style="margin:0 auto;position:relative;text-align:left;">
		<div><h3 class-="page-title">注册信息</h3></div>
			<div>
				<div class="margin-top-5">
					<div class="width30 pull-left text-right">
						<span><span class="required">*</span></span>
						<span>省 份:</span>
					</div>
					<div class="max-width pull-left">
						<select class="select-width">
							<option value="inner">河南</option>
							<option value="outer">河北</option>
						</select>
					</div>
				</div>
				<div class="margin-top-5">
					<div class="width30 pull-left text-right">
						<span><span class="required">*</span></span>
						<span>城 市:</span>
					</div>
					<div class="max-width pull-left">
						<select class="select-width">
							<option value="inner">河南</option>
							<option value="outer">河北</option>
						</select>
					</div>
				</div>
				<div class="margin-top-5">
					<div class="width30 pull-left text-right">
						<span><span class="required">*</span></span>
						<span>县 区:</span>
					</div>
					<div class="max-width pull-left">
						<select class="select-width">
							<option value="inner">河南</option>
							<option value="outer">河北</option>
						</select>
					</div>
				</div>
				<div class="margin-top-5">
					<div class="width30 pull-left text-right">
						<span><span class="required">*</span></span>
						<span>小区名称:</span>
					</div>
					<div class="max-width pull-left">
						<select class="select-width">
							<option value="inner">河南</option>
							<option value="outer">河北</option>
						</select>
					</div>
				</div>
				<div class="margin-top-5">
					<div class="width30 pull-left text-right">
						<span><span class="required">*</span></span>
						<span>楼 号:</span>
					</div>
					<div class="max-width pull-left">
						<select class="select-width">
							<option value="inner">河南</option>
							<option value="outer">河北</option>
						</select>
					</div>
				</div>
				<div class="margin-top-5">
					<div class="width30 pull-left text-right">
						<span><span class="required">*</span></span>
						<span>单元号:</span>
					</div>
					<div class="max-width pull-left">
						<select class="select-width">
							<option value="inner">河南</option>
							<option value="outer">河北</option>
						</select>
					</div>
				</div>
				<div class="margin-top-5">
					<div class="width30 pull-left text-right">
						<span><span class="required">*</span></span>
						<span>门牌号:</span>
					</div>
					<div class="max-width pull-left">
						<select class="select-width">
							<option value="inner">河南</option>
							<option value="outer">河北</option>
						</select>
					</div>
				</div>
				<div class="margin-top-5">
					<div class="width30 pull-left text-right">
						<span><span class="required">*</span></span>
						<span>手机号:</span>
					</div>
					<div class="max-width pull-left">
						<select class="select-width">
							<option value="inner">河南</option>
							<option value="outer">河北</option>
						</select>
					</div>
				</div>
				<div class="margin-top-5">	
					<div class="width30 pull-left text-right">手机号:</div>
					<div class="max-width pull-left">
						<input class="required select-width" type="text" name="" />
					</div>
				</div>
			</div>
		<div class="col-md-12 all-bottom">
			<div class="col-md-2 all-bottom-left"></div>
			<div class="col-md-8 all-bottom-middle"></div>
			<div class="col-md-2 all-bottom-right"><img src="<c:url value='/assets/img/advancing.png'/>" /></div>
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
