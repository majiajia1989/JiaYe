<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="houseInfo" value='<%=request.getAttribute("houseInfo")%>' />
<c:set var="community" value="${sessionScope.CONTEXT_APP.community}" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>业主微信管理</title>
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
	href="<c:url value='/assets/plugins/bootstrap-toastr/toastr.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/themes/default.css'/>" id="style_color" />
<!-- END THEME STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/pages/public.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/pages/sendNotice.css'/>" />
<!-- END PAGE LEVEL STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
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
						<i class="fa fa-reorder"></i>业主微信管理
					</div>
				</div>
				<div class="portlet-body">
					<div class="form-group complaints">
						<div class="pull-left">
							<label class="control-label pull-left">楼号:</label>
							<div class="pull-left">
								<select class="border-grey textwidth" id="house-select"></select>
							</div>
						</div>
						<div class="pull-left">
							<label class="control-label pull-left" style="margin-left: 20px;">单元号:</label>
							<div class="pull-left">
								<select class="border-grey textwidth" id="houseUnit-select">
								</select>
							</div>
						</div>

						<div class="pull-right">
							<label class="pull-left control-label">是否登记电话:</label>
							<div class="pull-left">
								<select class="border-grey textwidth" id="mobilephone-select">
									<option value="0">不限</option>
									<option value="1">是</option>
									<option value="2">否</option>
								</select>
							</div>
							<label class="pull-left control-label" style="margin-left: 20px;">是否有车:</label>
							<div class="pull-left">
								<select class="border-grey textwidth" id="car-select">
									<option value="0">不限</option>
									<option value="1">是</option>
									<option value="2">否</option>
								</select>
							</div>
							<label class="pull-left control-label" style="margin-left: 20px;">关键字:</label>
							<div class="pull-left">
								<input type="text" class="form-control" name="keyword"
									id="keyword" placeholder="输入昵称..." />
							</div>
							<button class="pull-left btn iframe-btn" id="search-btn">
								<i class="fa fa-search"></i>&nbsp;查&nbsp;询
							</button>
						</div>
						<div style="clear: both;"></div>
					</div>
					<div>
						<c:if test="${!empty errMsg}">
							<input type="hidden" id="err" value="${errMsg}"/> 
						</c:if>
						<c:if test="${!empty infoMsg}">
							<input type="hidden" id="info" value="${infoMsg}"/> 
						</c:if>
					</div>
					<table class="table table-striped table-bordered table-hover"
						id="data_table">
						<thead>
							<tr role="row">
								<th />
								<th />
								<th />
								<th />
								<th />
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>


		<div class="modal fade bootstrap-dialog type-primary "
			id="response-modal" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="bootstrap-dialog-title">发送微信</h4>
					</div>
					<div class="modal-body">
						<f:form action="${ctx}/community/sendWeixin" id="repsonse-form"
							class="form-validate form-horizontal" method="post"
							commandName="sendWeixinCommand">
							<f:input class="form-control" type="hidden" path="openid"
								name="openid" />

							<div class="form-body">
								<div
									class="form-group <f:errors path="content">has-error</f:errors>">
									<label class="control-label col-md-2">发送内容</label>
									<div class="col-md-8">
										<f:textarea class="form-control required" path="content"
											rows="6" placeholder="发送内容"></f:textarea>
										<f:errors cssClass="help-block" element="span" path="content" />
									</div>
									<label class="control-label"><span class="required">*</span></label>
								</div>
							</div>
						</f:form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary"
							id="response-submit-btn">
							<i class="fa fa-check"></i>&nbsp;确&nbsp;定
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<i class="fa fa-times"></i>&nbsp;关&nbsp;闭
						</button>

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
			src="<c:url value='/assets/plugins/bootstrap-modal/js/bootstrap-modal.js'/>"></script>
		<script type="text/javascript"
			src="<c:url value='/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js'/>"></script>
		<script type="text/javascript"
			src="<c:url value='/assets/plugins/bootstrap-dialog/js/bootstrap-dialog.js'/>"></script>
		<script type="text/javascript"
			src="<c:url value='/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js'/>"></script>
		<script type="text/javascript" charset="UTF-8"
			src="<c:url value='/assets/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js'/>"></script>
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
			src="<c:url value='/assets/plugins/bootbox/bootbox.min.js'/>"></script>
		<script type="text/javascript"
			src="<c:url value='/assets/plugins/bootstrap-toastr/toastr.min.js'/>"></script>
		<script type="text/javascript"
			src="<c:url value='/assets/scripts/app.js'/>"></script>
		<script type="text/javascript"
			src="<c:url value='/assets/scripts/pages/ownerWeixins.js'/>"></script>
		<!-- END PAGE LEVEL SCRIPTS -->
		<script>
		$(function() {
			OwnerWeixins.init("<c:url value='/' />",${houseInfo});
		});
	</script>
		<!-- END JAVASCRIPTS -->
	</c:if>
</body>
<!-- END BODY -->
</html>