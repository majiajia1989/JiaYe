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
	<title>用户注册</title>
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
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/pages/registe.css'/>" />
	<!-- END PAGE LEVEL STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="registe">
	<!-- BEGIN LOGO -->
	<div class="logo">
		<img src="<c:url value='/assets/img/wechat_test.jpeg'/>"  width=200 alt="" /> 
	</div>
	<!-- END LOGO -->
	<!-- BEGIN LOGIN -->
	<div class="content">
		<!-- BEGIN REGISTRATION FORM -->
		<f:form class="registe-form form-validate" action="${ctx}/auth/registe" method="post" commandName="registeCommand">
			<h3 class="form-title">请输入注册信息</h3>
			<c:if test="${!empty errMsg}">
			<div class="alert alert-danger">
				<button class="close" data-close="alert"></button>
				<span>${errMsg}</span>
			</div>
			</c:if>
			<div class="form-group <f:errors path="email">has-error</f:errors>">
				<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
				<label class="control-label visible-ie8 visible-ie9"> 电子邮件</label>
				<div class="input-icon">
					<i class="fa fa-envelope"></i>
					<f:input class="form-control placeholder-no-fix required email" placeholder="电子邮件" path="email"/>
				</div>
				<f:errors cssClass="help-block" element="span"  path="email" />
			</div>
			<div class="form-group <f:errors path="address">has-error</f:errors>">
				<label class="control-label visible-ie8 visible-ie9">联系地址</label>
				<div class="input-icon">
					<i class="fa fa-check"></i>
					<f:input class="form-control placeholder-no-fix required" placeholder="联系地址" path="address"/>
				</div>
				<f:errors path="qq" cssClass="help-block" element="span"/>
			</div>
			<div class="form-group <f:errors path="mobilePhone">has-error</f:errors>">
				<label class="control-label visible-ie8 visible-ie9">联系电话</label>
				<div class="input-icon">
					<i class="fa fa-font"></i>
					<f:input class="form-control placeholder-no-fix required" placeholder="联系电话" path="mobilePhone"/>
				</div>
				<f:errors path="qq" cssClass="help-block" element="mobilePhone"/>
			</div>
			<div class="form-group <f:errors path="qq">has-error</f:errors>">
				<label class="control-label visible-ie8 visible-ie9">QQ</label>
				<div class="input-icon">
					<i class="fa fa-location-arrow"></i>
					<f:input class="form-control placeholder-no-fix required" placeholder="QQ" path="qq"/>
				</div>
				<f:errors path="qq" cssClass="help-block" element="span"/>
			</div>
			<p>请输入登录信息:</p>
			<div class="form-group <f:errors path="userName">has-error</f:errors>">
				<label class="control-label visible-ie8 visible-ie9">用户名</label>
				<div class="input-icon">
					<i class="fa fa-user"></i>
					<f:input class="form-control placeholder-no-fix required" autocomplete="off" placeholder="用户名" path="userName"/>
				</div>
				<f:errors path="userName" cssClass="help-block" element="span"/>
			</div>
			<div class="form-group <f:errors path="password">has-error</f:errors>">
				<label class="control-label visible-ie8 visible-ie9">密码</label>
				<div class="input-icon">
					<i class="fa fa-lock"></i>
					<f:password class="form-control placeholder-no-fix required" autocomplete="off" id="registe_password" placeholder="密码" path="password"/>
				</div>
				<f:errors path="password1" cssClass="help-block" element="span"/>
			</div>
			<div class="form-group <f:errors path="password">has-error</f:errors>">
				<label class="control-label visible-ie8 visible-ie9">确认密码</label>
				<div class="controls">
					<div class="input-icon">
						<i class="fa fa-check"></i>
						<f:password  class="form-control placeholder-no-fix required" autocomplete="off" placeholder="确认密码" path="password1" />
					</div>
				</div>
				<f:errors path="password1" cssClass="help-block" element="span"/>
			</div>
			<div class="form-group <f:errors path="captcha">has-error</f:errors>">
				<label class="control-label visible-ie8 visible-ie9">验证码</label>
				<div class="input-icon">
					<i class="glyphicon glyphicon-pencil"></i>
					<f:input class="form-control placeholder-no-fix  required" autocomplete="off" placeholder="验证码" path="captcha"/>
				</div>
				<f:errors path="captcha" cssClass="help-block" element="span"/>
			</div>
			<div class="form-actions">
				<button id="registe-back-btn" type="button" class="btn">
				<i class="m-icon-swapleft"></i>  返 回
				</button>
				<button type="submit" id="registe-submit-btn" class="btn blue pull-right">
				注 册 <i class="m-icon-swapright m-icon-white"></i>
				</button>            
			</div>
		</f:form>
		<!-- END REGISTRATION FORM -->
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
	<script type="text/javascript" src="<c:url value='/assets/scripts/pages/registe.js'/>"></script>      
	<!-- END PAGE LEVEL SCRIPTS --> 
	<script>
		$(function() {
		  Registe.init("<c:url value='/' />");
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>