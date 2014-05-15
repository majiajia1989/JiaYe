<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="userCommand"
	value='<%=request.getAttribute("userCommand")%>' />
<c:set var="corpRoles" value='<%=request.getAttribute("corpRoles")%>' />

<c:set var="user_id" value="-1" />
<c:set var="corp_id" value="-1" />
<c:set var="name" value="" />
<c:set var="email" value="" />
<c:set var="address" value="" />
<c:set var="mobilePhone" value="" />
<c:set var="qq" value="" />

<c:if test="${!empty userCommand}">
	<c:set var="user_id" value="${userCommand.user_id}" />
	<c:set var="corp_id" value="${userCommand.corp_id}" />
	<c:set var="name" value="${userCommand.name}" />
	<c:set var="email" value="${userCommand.email}" />
	<c:set var="address" value="${userCommand.address}" />
	<c:set var="mobilePhone" value="${userCommand.mobilePhone}" />
	<c:set var="qq" value="${userCommand.qq}" />
</c:if>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>用户信息设置</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/font-awesome/css/font-awesome.min.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/bootstrap/css/bootstrap.min.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/themes/default.css' />"
	id="style_color" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN THEME STYLES -->
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/style-wechat.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/style.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/style-responsive.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/plugins.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/plugins.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/select2/select2_metro.css'/>" />
<!-- END THEME STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/style-wechat.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/style.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/style-responsive.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/plugins.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/pages/public.css'/>" />
<!-- END PAGE LEVEL STYLES -->
</head>
<body>
	<div class="col-md-12 ">
		<div class="portlet box iframe-box ">
			<div class="portlet-title col-md-12">
				<div class="caption">
					<c:if test="${user_id>0}">
						<i class="fa fa-edit"></i>&nbsp;修改用户</c:if>
					<c:if test="${user_id==-1}">
						<i class="fa fa-plus"></i>&nbsp;添加用户</c:if>
				</div>
				<div class="actions">
					<a id="data_table_new_btn" class="btn iframe-btn btn-return"
						href="${ctx}/user/user"><i class="fa fa-chevron-left"></i> 返 回 </a>
				</div>
			</div>
			<div class="portlet-body form">
				<!-- 内容区域开始-->
				<f:form class="corp-form form-validate  form-horizontal"
					action="${ctx}/user/saveUser" method="post"
					commandName="userCommand">

					<input type="hidden" id="user_id" name="user_id" value="${user_id}" />
					<input type="hidden" id="corp_id" name="corp_id" value="${corp_id}" />
					<input type="hidden" id="type" name="type" value="-1" />
					<input type="hidden" id="corpName" name="corpName" value="-1" />

					<div class="form-body">
						<h4 style="height: 20px">请输入登录信息</h4>
						<div class="form-group <f:errors path="name">has-error</f:errors>">
							<label class="control-label  col-md-2">用户名</label>
							<div class="col-md-8">
								<f:input class="form-control required" placeholder="用户名"
									path="name" />
							</div>
							<label class="control-label"><span class="required">*</span></label>
							<f:errors path="name" cssClass="help-block" element="span" />
						</div>
						<div
							class="form-group <f:errors path="password">has-error</f:errors>">
							<label class="control-label  col-md-2">密码</label>
							<div class="col-md-8">
								<f:password class="form-control required" autocomplete="off"
									id="registe_password" placeholder="密码" path="password" />
							</div>
							<label class="control-label"><span class="required">*</span></label>
							<f:errors path="password" cssClass="help-block" element="span" />
						</div>
						<div
							class="form-group <f:errors path="confirmPass">has-error</f:errors>">
							<label class="control-label  col-md-2">确认密码</label>
							<div class="controls">
								<div class="col-md-8">
									<f:password class="form-control required" equalTo="#registe_password" autocomplete="off"
										placeholder="确认密码" path="confirmPass" />
								</div>
							</div>
							<label class="control-label"><span class="required">*</span></label>
							<f:errors path="confirmPass" cssClass="help-block" element="span" />
						</div>
						<h4 style="height: 20px">请输入注册信息</h4>
						<div class="form-group ">
							<label class="control-label  col-md-2">用户组</label>
							<div class="col-md-8">
								<select class="form-control dropdown-select " id="role_id"
									name="role_id">
									<c:forEach items="${corpRoles}" var="roleInfo">
										<option value="${roleInfo.id}">${roleInfo.name}</option>
									</c:forEach>
								</select>
							</div>
							<label class="control-label"><span class="required">*</span></label>
						</div>
						<div
							class="form-group <f:errors path="email">has-error</f:errors>">
							<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
							<label class="control-label  col-md-2">电子邮件</label>
							<div class="col-md-8">
								<f:input class="form-control required email" placeholder="电子邮件"
									path="email" />
							</div>
							<label class="control-label"><span class="required">*</span></label>
							<f:errors cssClass="help-block" element="span" path="email" />
						</div>
						<div
							class="form-group <f:errors path="address">has-error</f:errors>">
							<label class="control-label  col-md-2">联系地址</label>
							<div class="col-md-8">
								<f:input class="form-control placeholder-no-fix required"
									placeholder="联系地址" path="address" />
							</div>
							<label class="control-label"><span class="required">*</span></label>
							<f:errors path="address" cssClass="help-block" element="span" />
						</div>
						<div
							class="form-group <f:errors path="mobilePhone">has-error</f:errors>">
							<label class="control-label  col-md-2">联系电话</label>
							<div class="col-md-8">
								<f:input class="form-control required" placeholder="联系电话"
									path="mobilePhone" />
							</div>
							<label class="control-label"><span class="required">*</span></label>
							<f:errors path="qq" cssClass="help-block" element="mobilePhone" />
						</div>
						<div class="form-group <f:errors path="qq">has-error</f:errors>">
							<label class="control-label  col-md-2">QQ</label>
							<div class="col-md-8">
								<f:input class="form-control" placeholder="QQ" path="qq" />
							</div>
							<f:errors path="qq" cssClass="help-block" element="span" />
						</div>
					</div>
					<div class="form-actions fluid">
						<div class="col-md-5 col-md-offset-2 pull-left">
							<button class="btn iframe-btn" type="submit">
								<i class="fa fa-check"></i>&nbsp;保&nbsp;存
							</button>
						</div>
					</div>
				</f:form>
				<!--内容区域结束-->
			</div>
		</div>
	</div>
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
	<script type="text/javascript" src="<c:url value='/assets/assets/plugins/respond.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/excanvas.min.js'/>"></script> 
	<![endif]-->
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/jquery-1.10.2.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/jquery-migrate-1.2.1.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/bootstrap/js/bootstrap.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/jquery.blockui.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/jquery.cookie.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/uniform/jquery.uniform.min.js'/>"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/jquery-validation/dist/jquery.validate.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/backstretch/jquery.backstretch.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/select2/select2.min.js'/>"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script type="text/javascript"
		src="<c:url value='/assets/scripts/app.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/scripts/pages/editUser.js'/>"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<script>
		$(function() {
			var infoMsg = '${infoMsg}';
			if (infoMsg.length > 0) {
				toastr.warning(infoMsg, '提示');
			}
			EditUser.init("<c:url value='/' />");
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>