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
<title>常用电话</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
<!-- BEGIN GLOBAL MANDATORY STYLES -->
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
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/themes/default.css' />" id="style_color" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN THEME STYLES -->
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/bootstrap-toastr/toastr.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/style-wechat.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/style.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/style-responsive.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/plugins.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/bootstrap-toastr/toastr.css'/>" />
<!-- END THEME STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/data-tables/DT_bootstrap.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/pages/public.css'/>" />
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
					<i class="fa fa-reorder"></i>常用电话
				</div>
				<div class="actions">
					<button id="servicePhone" class="btn iframe-btn"
								onclick="javascript:ServicePhone.response(-1)" >
								<i class="fa fa-plus"></i>&nbsp;新&nbsp;建
					</button>
				</div>
			</div>
			<div class="portlet-body">
					<table class="table table-striped table-hover table-bordered"
						id="servicePhone_Table">

						<thead>
							<tr>
								<th></th>
								<th></th>
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
		
	<div class="modal fade bootstrap-dialog type-primary "
		id="modalServicePhone" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 id="tt" class="bootstrap-dialog-title">常用号码</h4>
				</div>
				<div class="modal-body">
				<f:form action="${ctx}/community/editServicePhone" id="response-form" name="response-form"
					class="editServicePhone-form form-validate form-horizontal" method="post"
					commandName="servicePhoneCommand">
					<f:input class="form-control" type="hidden" path="id" name="id" />

					<div class="form-body">
						<div
							class="form-group <f:errors path="title">has-error</f:errors>"
							id="title-form-group">
							<label class="control-label col-md-2">名称</label>
							<div class="col-md-8">
								<f:input class="form-control required" type="text" autocomplete="off"
									placeholder="名称" path="title" name="title" />
								<f:errors cssClass="help-block" element="span" path="title" />									
							</div>
							<label class="control-label"><span class="required">*</span></label>
						</div>
						<div
							class="form-group <f:errors path="phone">has-error</f:errors>"
							id="phone-form-group">
							<label class="control-label col-md-2">电话</label>
							<div class="col-md-8">
								<f:input class="form-control required mobile_Phone" type="text" autocomplete="off"
									placeholder="电话号码" path="phone" name="phone" />
								<f:errors cssClass="help-block" element="span" path="phone" />									
							</div>
							<label class="control-label"><span class="required">*</span></label>
						</div>
						<div
							class="form-group <f:errors path="description">has-error</f:errors>"
							id="description-form-group">
							<label class="control-label col-md-2">描述</label>
							<div class="col-md-8">
								<f:textarea class="form-control" rows="3" maxlength="1000" autocomplete="off" type="text"
									placeholder="服务内容" path="description" name="description" />					
							</div>
							<f:errors cssClass="help-block" element="span" path="description" />
						</div>

					</div>
				</f:form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="response-submit-btn"><i
								class="fa fa-check"></i>&nbsp;保&nbsp;存</button>				
					<button type="button" class="btn btn-default" data-dismiss="modal"><i
								class="fa fa-times"></i>&nbsp;关&nbsp;闭</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

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
		src="<c:url value='/assets/plugins/bootstrap-dialog/js/bootstrap-dialog.min.js'/>"></script>
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
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/bootstrap-toastr/toastr.min.js'/>"></script>		
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/data-tables/jquery.dataTables.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/data-tables/DT_bootstrap.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/bootbox/bootbox.min.js'/>"></script>		
	<script type="text/javascript"
		src="<c:url value='/assets/scripts/app.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/scripts/pages/servicePhone.js'/>"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<script>
		$(function() {
			ServicePhone.init("<c:url value='/' />");
			<c:if test="${!empty errMsg}">toastr.warning('${errMsg}', '提示');</c:if>
		});
		
	</script>
	</c:if>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</body>
</html>