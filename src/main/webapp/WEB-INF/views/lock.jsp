<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="user" value="${sessionScope.CONTEXT_APP.user}" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<meta charset="utf-8" />
	<title>用户登录</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<meta name="MobileOptimized" content="320">
	<!-- BEGIN GLOBAL MANDATORY STYLES -->          
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/font-awesome/css/font-awesome.min.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/bootstrap/css/bootstrap.min.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/uniform/css/uniform.default.css'/>" />
	<!-- END GLOBAL MANDATORY STYLES -->
	<!-- BEGIN THEME STYLES --> 
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style-wechat.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style-responsive.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/plugins.css'/>" />
	<!-- END THEME STYLES -->
	<!-- BEGIN PAGE LEVEL STYLES --> 
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/pages/lock.css'/>" />
	<!-- END PAGE LEVEL STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="lock">
	<!-- BEGIN LOGO -->
	<div class="logo">
		<img src="<c:url value='/assets/img/logo-big.png'/>" alt="" /> 
	</div>
	<!-- END LOGO -->
	<!-- BEGIN LOGIN -->
	<div class="content">
		<!-- BEGIN LOGIN FORM -->
		<f:form class="login-form form-validate" action="${ctx}/lock" method="post" commandName="loginCommand">
			<h3 class="form-title"><i class="glyphicon glyphicon-user"> </i><span class="username"> ${user.name} </span></h3>
			<p>用户已锁定</p>
			<c:if test="${!empty errMsg}">
			<div class="alert alert-danger">
				<button class="close" data-close="alert"></button>
				<span>${errMsg}</span>
			</div>
			</c:if>
			<div class="form-group <f:errors path="password">has-error</f:errors>">
				<label class="control-label visible-ie8 visible-ie9">密码</label>
				<div class="input-icon">
					<i class="glyphicon glyphicon-lock"></i>
					<input class="form-control placeholder-no-fix  required" type="password" autocomplete="off" placeholder="密码" name="password" />
				</div>
				<f:errors path="password" cssClass="help-block" element="span"/>
			</div>
			<div class="form-actions">
				<button type="submit" class="btn blue pull-right">
				登 录&nbsp; <i class="m-icon-swapright m-icon-white"></i>
				</button>            
			</div>
			<div class="reglogin">
				<p>更换用户?请<a href="${ctx}/auth/login"  >重新登录</a></p>
			</div>
		</f:form>
		<!-- END LOGIN FORM -->
	</div>
	<!-- END LOGIN -->
	<!-- BEGIN COPYRIGHT -->
	<div class="copyright">
	</div>
	<!-- END COPYRIGHT -->
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
	<!-- END PAGE LEVEL PLUGINS -->

	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script type="text/javascript" src="<c:url value='/assets/scripts/app.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/scripts/pages/lock.js'/>"></script>      
	<!-- END PAGE LEVEL SCRIPTS --> 
	<script>
		$(function() {
		  Lock.init("<c:url value='/' />");
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>