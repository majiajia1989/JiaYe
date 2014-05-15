<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<meta charset="utf-8" />
	<title>请登录</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<meta name="MobileOptimized" content="320">
	<!-- BEGIN GLOBAL MANDATORY STYLES -->          
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/font-awesome/css/font-awesome.min.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/bootstrap/css/bootstrap.min.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/uniform/css/uniform.default.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/kindeditor/themes/default/default.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/kindeditor/plugins/code/prettify.js'/>" />
	<!-- END GLOBAL MANDATORY STYLES -->
	<!-- BEGIN THEME STYLES --> 
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style-wechat.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style-responsive.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/plugins.css'/>" />
	<!-- END THEME STYLES -->
	<!-- BEGIN PAGE LEVEL STYLES --> 
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/pages/kindeditor-demo.css'/>" />
	<!-- END PAGE LEVEL STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>
	<form class="demo-form form-horizontal form-validate" name="example" method="post" action="${ctx}/kindeditor/demo">
		<div class="form-body">
			<div class="form-group">
				<label class="col-md-3 control-label">Text</label>
				<div class="col-md-9">
					<input class="form-control required" placeholder="Enter text" type="text" name="key">
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-3 control-label">封面图片</label>
				<div class="col-md-9">
					<img id="thumb_img" src="" style="max-height:100px; display:none;">
					<input id="thumb" type="text" name="picurl" class="form-control required" readonly="readonly">
					<a href="#" id="insertimage" >insertimage</a>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-3 control-label">图文详细页内容1</label>
				<div class="col-md-9">
					<textarea name="content" class="form-control required"></textarea>
				</div>
			</div>
			<div class="form-actions fluid">
				<div class="col-md-offset-3 col-md-9">
					<button type="submit" class="btn green">提交内容</button>
					<button type="button" class="btn default">Cancel</button>                              
				</div>
			</div>
		</div>
	</form>
<%
out.println("Protocol: " + request.getProtocol() + "<br>");
out.println("Scheme: " + request.getScheme() + "<br>");
out.println("Server Name: " + request.getServerName() + "<br>" );
out.println("Server Port: " + request.getServerPort() + "<br>");
out.println("Protocol: " + request.getProtocol() + "<br>");
out.println("Server Info: " + getServletConfig().getServletContext().getServerInfo() + "<br>");
out.println("Remote Addr: " + request.getRemoteAddr() + "<br>");
out.println("Remote Host: " + request.getRemoteHost() + "<br>");
out.println("Character Encoding: " + request.getCharacterEncoding() + "<br>");
out.println("Content Length: " + request.getContentLength() + "<br>");
out.println("Content Type: "+ request.getContentType() + "<br>");
out.println("Auth Type: " + request.getAuthType() + "<br>");
out.println("HTTP Method: " + request.getMethod() + "<br>");
out.println("Path Info: " + request.getPathInfo() + "<br>");
out.println("Path Trans: " + request.getPathTranslated() + "<br>");
out.println("Query String: " + request.getQueryString() + "<br>");
out.println("Remote User: " + request.getRemoteUser() + "<br>");
out.println("Session Id: " + request.getRequestedSessionId() + "<br>");
out.println("Request Context Path: " + request.getContextPath() + "<br>");
out.println("Request URI:" +request.getRequestURI()+ "<br>");
out.println("Request URL:" +request.getRequestURL() + "<br>");
out.println("Servlet Path: " + request.getServletPath() + "<br>");
out.println("Accept: " + request.getHeader("Accept") + "<br>");
out.println("Host: " + request.getHeader("Host") + "<br>");      
out.println("Referer : " + request.getHeader("Referer") + "<br>");      
out.println("Accept-Language : " + request.getHeader("Accept-Language") + "<br>");      
out.println("Accept-Encoding : " + request.getHeader("Accept-Encoding") + "<br>");      
out.println("User-Agent : " + request.getHeader("User-Agent") + "<br>");      
out.println("Connection : " + request.getHeader("Connection") + "<br>");      
out.println("Cookie : " + request.getHeader("Cookie") + "<br>");      
out.println("Created : " + session.getCreationTime() + "<br>");      
out.println("LastAccessed : " + session.getLastAccessedTime() + "<br>");        
%>
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->   
	<!--[if lt IE 9]>
	<script type="text/javascript" src="<c:url value='/assets/assets/plugins/respond.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/excanvas.min.js'/>"></script> 
	<![endif]-->   
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery-1.10.2.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery-migrate-1.2.1.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/bootstrap/js/bootstrap.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js'/>" ></script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery.blockui.min.js'/>"></script>  
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery.cookie.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/uniform/jquery.uniform.min.js'/>" ></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery-validation/dist/jquery.validate.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/backstretch/jquery.backstretch.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/select2/select2.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/kindeditor/kindeditor.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/kindeditor/lang/zh_CN.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/kindeditor/plugins/code/prettify.js'/>"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script type="text/javascript" src="<c:url value='/assets/scripts/app.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/scripts/pages/kindeditor-demo.js'/>"></script>      
	<!-- END PAGE LEVEL SCRIPTS --> 
	<script>
		$(function() {
		  Demo.init();
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>