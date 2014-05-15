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
<title>事务回复汇总</title>
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
	href="<c:url value='/assets/plugins/bootstrap-modal/css/bootstrap-modal.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/bootstrap-toastr/toastr.css'/>" />
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
<!-- END THEME STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/pages/public.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/pages/commission.css'/>" />

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
						<i class="fa fa-reorder"></i>事务回复汇总
					</div>

					<div class="actions">
						<a id="data_table_new_btn" class="btn iframe-btn"
							data-toggle="modal" href="#"
							onclick="javascript:HistoryReply.openReplyModal();"><i
							class="fa fa-reply"></i>&nbsp;回&nbsp;复</a>
						<button type="button" class="btn iframe-btn btn-return"
							onclick="Javascript:{window.location='${ctx}/commission/commissions';}">
							<i class="fa fa-chevron-left"></i>&nbsp;返&nbsp;回
						</button>
					</div>
				</div>
				<div class="portlet-body">
					<table class="table table-striped table-hover table-bordered"
						id="data_table">
						<thead>
							<tr>
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

		<div id="reply_modal"
			class="modal fade bootstrap-dialog type-primary " tabindex="-1"
			role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="bootstrap-dialog-title">回复</h4>
					</div>
					<div class="modal-body">
						<f:form action="${ctx}/commission/commissionResponse"
							id="repsonse-form" class="form-validate form-horizontal"
							method="post" commandName="commissionResponseCommand">
							<input type="hidden" value="true" name="fromReply"/>
							<f:input class="form-control" type="hidden" path="commissionID"
								name="commissionID" />
							<f:input class="form-control" type="hidden"
								path="commissionContent" id="commissionContent"
								name="commissionContent" />
							<f:input class="form-control" type="hidden"
								path="commissionCreator" name="commissionCreator"
								id="commissionCreator" />
							<div class="form-body">
								<div class="form-group">
									<label class="control-label  col-md-2">代办用户:</label>
									<div class="col-md-8 margin-top9" id="commission-creator">

									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">代办内容:</label>
									<div class="col-md-8 complaint-content-scroll"
										id="commission-content"></div>
								</div>
								<div
									class="form-group <f:errors path="content">has-error</f:errors>">
									<label class="control-label col-md-2">回复内容</label>
									<div class="col-md-8">
										<f:textarea class="form-control required" path="content"
											rows="6" placeholder="回复内容"></f:textarea>
										<f:errors cssClass="help-block" element="span" path="content" />
									</div>
									<label class="control-label"><span class="required">*</span></label>
								</div>
							</div>
						</f:form>
					</div>
					<div class="modal-footer">
						<button class="btn iframe-btn" type="button"
							id="response-submit-btn">
							<i class="fa fa-check"></i>&nbsp;保&nbsp;存
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<i class="fa fa-times"></i>&nbsp;关&nbsp;闭
						</button>

					</div>
				</div>
			</div>
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
			src="<c:url value='/assets/scripts/pages/commissionHistoryReply.js'/>"></script>
		<!-- END PAGE LEVEL SCRIPTS -->
		<script>
			$(function() {
				HistoryReply.init("<c:url value='/' />");
			});
		</script>
		<!-- END JAVASCRIPTS -->
	</c:if>
</body>
<!-- END BODY -->
</body>
</html>