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
	<title>修改密码</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<meta name="MobileOptimized" content="320">
	<!-- BEGIN GLOBAL MANDATORY STYLES -->          
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/font-awesome/css/font-awesome.min.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/bootstrap/css/bootstrap.min.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/uniform/css/uniform.default.css'/>" />
	<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/themes/default.css'/>" id="style_color"/>
	<!-- END GLOBAL MANDATORY STYLES -->
	<!-- BEGIN THEME STYLES --> 
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style-wechat.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style-responsive.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/plugins.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/pages/public.css'/>" />
	<!-- END THEME STYLES -->
	<!-- BEGIN PAGE LEVEL STYLES --> 
	<!-- END PAGE LEVEL STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>
	<!-- BEGIN LOGIN -->
	<div class="col-md-12">
		<div class="portlet box iframe-box ">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-edit"></i>修改密码
				</div>
			</div>
			<div class="portlet-body form">
		<!-- BEGIN REGISTRATION FORM -->
		<f:form class="changePassword-form form-horizontal form-validate" role="form" action="${ctx}/user/changePassword" method="post" commandName="changePasswordCommand">
			<div class="form-body">
			<div class="form-group <f:errors path="oldPassword">has-error</f:errors>">
				<label class="col-md-2 control-label">旧密码</label>
				<div class="col-md-5">
					<f:password class="form-control placeholder-no-fix required" autocomplete="off" id="oldPassword" placeholder="旧密码" path="oldPassword"/>
					<f:errors path="oldPassword" cssClass="help-block" element="span"/>
				</div>
			</div>
			<div class="form-group <f:errors path="newPassword">has-error</f:errors>">
				<label class="control-label col-md-2">新密码</label>
				<div class="col-md-5">
					<f:password class="form-control placeholder-no-fix required" autocomplete="off" id="newPassword" placeholder="新密码" path="newPassword" name="newPassword"/>
					<f:errors path="newPassword" cssClass="help-block" element="span"/>
				</div>
			</div>
			<div class="form-group row <f:errors path="confirmPassword">has-error</f:errors>">
				<label class="control-label col-md-2">确认密码</label>
				<div class="controls">
					<div class="col-md-5">						
						<f:password  class="form-control placeholder-no-fix required" equalTo="#newPassword" autocomplete="off" placeholder="确认密码" path="confirmPassword"/>
						<f:errors path="confirmPassword" cssClass="help-block" element="span"/>
					</div>
				</div>
			</div>
			</div>
			<div class="form-actions fluid">
				<div class="col-md-offset-2 col-md-5 pull-left">
					<button type="submit" id="submit-btn" class="btn iframe-btn"><i class="fa fa-check"></i>&nbsp;保&nbsp;存</button>
				</div>
			</div>
		</f:form>
		<!-- END REGISTRATION FORM -->
	</div>
	</div>
	</div>
<!-- END LOGIN -->
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
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery-validation/dist/jquery.metadata.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/backstretch/jquery.backstretch.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/select2/select2.min.js'/>"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script type="text/javascript" src="<c:url value='/assets/scripts/app.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/scripts/pages/changePassword.js'/>"></script>      
	<!-- END PAGE LEVEL SCRIPTS --> 
	<script>
		$(function() {
			var infoMsg = '${successMsg}';
			if (infoMsg.length > 0) {
				toastr.warning(infoMsg, '提示');
			}
		  ChangePassword.init("<c:url value='/' />");
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>