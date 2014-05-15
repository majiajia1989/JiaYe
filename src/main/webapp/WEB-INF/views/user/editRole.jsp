<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>用户组设置</title>
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
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/data-tables/DT_bootstrap.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/bootstrap-toastr/toastr.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/jstree/dist/themes/default/style.min.css'/>" />
<!-- END THEME STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/pages/public.css'/>" />
<!-- END PAGE LEVEL STYLES -->
</head>
<body>
	<div class="col-md-12 ">
		<div class="portlet box iframe-box ">
			<div class="portlet-title">
				<div class="caption">
					<c:if test="${roleCommand.role_id>0}">
						<i class="fa fa-edit"></i>&nbsp;修改用户组</c:if>
					<c:if test="${roleCommand.role_id==-1}">
						<i class="fa fa-plus"></i>&nbsp;添加用户组</c:if>

				</div>
				<div class="actions">
					<a id="data_table_new_btn" class="btn iframe-btn btn-return"
						href="${ctx}/role/role"><i class="fa fa-chevron-left"></i>&nbsp;返&nbsp;回</a>
				</div>
			</div>
			<div class="portlet-body form">
				<f:form class="community-form form-validate  form-horizontal"
					action="${ctx}/role/editRole" method="post"
					commandName="roleCommand" id="editForm">
					<input type="hidden" id="role_id" name="role_id"
						value="${roleCommand.role_id}" />
					<input type="hidden" id="roleResources" name="roleResources"
						value="${roleCommand.roleResources}" />
					<div class="form-body">
						<div class="form-group <f:errors path="name">has-error</f:errors>">
							<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
							<label class="control-label  col-md-1">名称</label>
							<div class="col-md-9">
								<f:input class="form-control required" type="text"
									autocomplete="off" placeholder="名称" path="name" />
							</div>
							<label class="control-label"><span class="required">*</span></label>
							<f:errors cssClass="help-block" element="span" path="name" />
						</div>
						<div class="form-group">
							<label class="control-label  col-md-1">权限</label>
							<div class="col-md-9">
								<div id="resourceTree" name="resourceTree"
									class="tree tree-plus-minus tree-solid-line tree-unselectable">
									<div class="tree-folder" style="display: none;">
										<div class="tree-folder-header">
											<i class="fa fa-folder"></i>
											<div class="tree-folder-name"></div>
										</div>
										<div class="tree-folder-content"></div>
										<div class="tree-loader" style="display: none"></div>
									</div>
									<div class="tree-item" style="display: none;">
										<i class="tree-dot"></i>
										<div class="tree-item-name"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="form-actions fluid">
						<div class="col-md-5 col-md-offset-2 pull-left">
							<button class="btn iframe-btn" type="submit"
								onclick='EditRole.submitForm();'>
								<i class="fa fa-check"></i>&nbsp;保&nbsp;存
							</button>
						</div>
					</div>
				</f:form>
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
		src="<c:url value='/assets/plugins/select2/select2.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/data-tables/jquery.dataTables.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/data-tables/DT_bootstrap.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/bootbox/bootbox.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/bootstrap-toastr/toastr.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/jstree/dist/jstree.js'/>"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script type="text/javascript"
		src="<c:url value='/assets/scripts/app.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/scripts/pages/editRole.js'/>"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<script>
		$(function() {
			var infoMsg = '${infoMsg}';
			if (infoMsg.length > 0) {
				toastr.warning(infoMsg, '提示');
			}
			EditRole.init("<c:url value='/' />");
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>