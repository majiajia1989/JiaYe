<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<meta charset="utf-8" />
	<!-- BEGIN GLOBAL MANDATORY STYLES -->          
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/font-awesome/css/font-awesome.min.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/bootstrap/css/bootstrap.min.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/uniform/css/uniform.default.css'/>" />
	<!-- END GLOBAL MANDATORY STYLES -->
	<!-- BEGIN PAGE LEVEL STYLES --> 
	<link rel="stylesheet" type="text/css" href="<c:url value='/assets/plugins/select2/select2_metro.css'/>" />
	<!-- END PAGE LEVEL SCRIPTS -->
	<!-- BEGIN THEME STYLES --> 
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style-wechat.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style-responsive.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/plugins.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/pages/valid.css'/>" />
	<!-- END THEME STYLES -->
</head>
 
<body>
<f:form class="valid-form form-validate" action="${ctx}/auth/valid" method="post" commandName="validUser">
    <div class="form-body">
    	<h5>Spring MVC JSR303 @Valid example</h5>
	    <div class="form-group <f:errors path="name">has-error</f:errors>">
	    	<div class="input-group">
				<label class="input-group-addon">用户名</label>
				<f:input class="form-control required" autocomplete="off" placeholder="用户名" path="name" />
				<i class="glyphicon glyphicon-user input-group-addon" ></i>
			</div>
			<f:errors cssClass="help-block" element="span"  path="name" />
		</div>
		<div class="form-group <f:errors path="email">has-error</f:errors>">
	    	<div class="input-group">
				<label class="input-group-addon">Email&nbsp;</label>
				<f:input class="form-control required" autocomplete="off" placeholder="Email" path="email" />
			</div>
			<f:errors cssClass="help-block" element="span"  path="email"/>
		</div>
		<div class="form-group <f:errors path="mobilePhone">has-error</f:errors>">
	    	<div class="input-group">
				<label class="input-group-addon">MobilePhone&nbsp;</label>
				<f:input class="form-control required" autocomplete="off" placeholder="Email" path="mobilePhone" />
			</div>
			<f:errors cssClass="help-block" element="span"  path="mobilePhone"/>
		</div>
		<div class="form-group <f:errors path="remark">has-error</f:errors>">
	    	<div class="input-group">
				<label class="input-group-addon">&nbsp;&nbsp;备注&nbsp;</label>
				<f:textarea  class="form-control required" placeholder="remark" path="remark"/>
			</div>
			<f:errors cssClass="help-block" element="span"  path="remark"/>
		</div>
		<div class="form-group">
				<button class="btn btn-primary" type="submit">Valid</button>
		</div>
	</div>
</f:form>
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
	<script type="text/javascript" src="<c:url value='/assets/scripts/valid.js'/>"></script>      
	<!-- END PAGE LEVEL SCRIPTS --> 
	<script>
		$(function() {
		  Valid.init();
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
</html>