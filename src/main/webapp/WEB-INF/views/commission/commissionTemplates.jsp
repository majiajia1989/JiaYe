<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="community" value="${sessionScope.CONTEXT_APP.community}" />
<!DOCTYPE html>
<html lang="en" class="no-js">
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>代办事务模版管理</title>
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
	href="<c:url value='/assets/plugins/bootstrap-toastr/toastr.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/bootstrap-modal/css/bootstrap-modal.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/bootstrap-datepicker/css/datepicker.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/uniform/css/uniform.default.css'/>" />
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
	href="<c:url value='/assets/plugins/data-tables/DT_bootstrap.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/themes/default.css'/>" id="style_color" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/pages/public.css'/>" />

<!-- END PAGE LEVEL STYLES -->
</head>
<body>
<c:if test="${empty community}">
	<div class="col-md-12 public-prompt">
		<img src="<c:url value='/assets/img/omg.png'/>" alt="" />
	</div>
	</c:if>
	<c:if test="${!empty community}">
	<div class="col-md-12 ">
		<div class="portlet box iframe-box ">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-reorder"></i>常用模版管理
				</div>
				<div class="actions">
					<a id="data_table_new_btn" class="btn iframe-btn"
						data-toggle="modal"
						href="javascript:CommissionTemplates.openTemplateModal();"><i
						class="fa fa-plus"></i>&nbsp;新&nbsp;建</a>
					<button type="button" class="btn iframe-btn btn-return"
						onclick="Javascript:{window.location='${ctx}/commission/commissionTypes';}">
						<i class="fa fa-chevron-left"></i>&nbsp;返&nbsp;回
					</button>
				</div>

			</div>
			<div class="portlet-body">
				<table class="table table-striped table-hover table-bordered"
					id="commissiontemplate_table">

					<thead>
						<tr>
							<th></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					</tbody>

				</table>
			</div>
		</div>
	</div>

	<div id="template_modal"
		class="modal fade bootstrap-dialog type-primary " tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="bootstrap-dialog-title">添加常用模版</h4>
				</div>
				<div class="modal-body">
					<f:form class="form-validate  form-horizontal" id="template-form"
						action="${ctx}/commission/saveCommissionTemplate" method="post"
						commandName="commissionTemplateCommand">
						<f:input class="form-control" type="hidden" path="type_id"
							name="type_id" />
							<f:input class="form-control" type="hidden" path="id"
							name="id" />
						<div class="form-body">
							<div
								class="form-group <f:errors path="content">has-error</f:errors>">
								<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
								<label class="control-label  col-md-2">常用模版</label>
								<div class="col-md-8">
									<f:input class="form-control required" placeholder="常用模版"
										id="input_template" path="content" />
								</div>
								<label class="control-label"><span class="required">*</span></label>
								<f:errors cssClass="help-block" element="span" path="content" />
							</div>
						</div>
					</f:form>
				</div>
				<div class="modal-footer">
					<button class="btn iframe-btn" type="button"
						id="template-submit-btn">
						<i class="fa fa-check"></i>&nbsp;保&nbsp;存
					</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<i class="fa fa-times"></i>&nbsp;关&nbsp;闭
					</button>

				</div>
			</div>
		</div>
	</div>
	<div id="deleteTemplate-modal"
		class="modal fade bootstrap-dialog type-primary " tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="bootstrap-dialog-title">提示</h4>
				</div>
				<div class="modal-body" style="height:50px;">
					<label class="control-label  col-md-8" >确定删除吗</label>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn iframe-btn"
						id="deleteTemplate-btn">
						<i class="fa fa-check"></i>&nbsp;确&nbsp;定
					</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<i class="fa fa-times"></i>&nbsp;关&nbsp;闭
					</button>

				</div>
			</div>
		</div>
	</div>
	<!-- END COPYRIGHT -->
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
		src="<c:url value='/assets/plugins/data-tables/jquery.dataTables.js'/>"></script>

	<script type="text/javascript"
		src="<c:url value='/assets/plugins/data-tables/DT_bootstrap.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/bootstrap-modal/js/bootstrap-modal.js'/>"></script>

	<script type="text/javascript"
		src="<c:url value='/assets/plugins/bootbox/bootbox.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/bootstrap-toastr/toastr.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/scripts/app.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/scripts/pages/commissionTemplates.js'/>"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<script>
		$(function() {
			CommissionTemplates.init("<c:url value='/' />");
		});
	</script>
	<!-- END JAVASCRIPTS -->
	</c:if>
</body>
<!-- END BODY -->
</body>
</html>