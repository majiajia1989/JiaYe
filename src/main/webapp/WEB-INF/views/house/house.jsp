<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="corpId" value='<%=request.getAttribute("corpId")%>' />
<c:set var="community" value="${sessionScope.CONTEXT_APP.community}" />
<c:set var="communityId"
	value='<%=request.getAttribute("communityId")%>' />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>楼号信息</title>
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
<!-- END THEME STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/themes/default.css' />"
	id="style_color" />
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
						<i class="fa fa-reorder"></i>楼号信息
					</div>
					<div class="actions">
						<button id="data_table_new_btn" class="btn iframe-btn"
							onclick="House.addInfo();">
							<i class="fa fa-plus"></i>&nbsp;新&nbsp;建
						</button>
						<button id="data_table_new_btn" class="btn iframe-btn"
							data-toggle="modal" data-target="#modalImport">
							<i class="fa fa-sign-in"></i>&nbsp;导&nbsp;入
						</button>
					</div>
				</div>
				<div class="portlet-body">
					<table
						class="col-md-12 table table-striped table-hover table-bordered"
						id="dtList" name="dtList">
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

		<div id="modalHouse" class="modal fade" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"></button>
						<h4 id="modalHouseTitle" class="modal-title">楼号设置</h4>
					</div>
					<div class="modal-body">
						<f:form class="form-validate  form-horizontal"
							action="${ctx}/house/house" method="post"
							commandName="saveHouseCommand" id="editForm">
							<input type="hidden" id="corpId" name="corpId" value="${corpId}" />
							<input type="hidden" id="communityId" name="communityId"
								value="${communityId}" />
							<input type="hidden" id="houseId" name="houseId"
								value="${houseId}" />
							<div class="form-body">
								<div
									class="form-group <f:errors path="hoses">has-error</f:errors>">
									<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
									<label class="control-label  col-md-2">楼号</label>
									<div class="col-md-8">
										<f:input class="form-control required" placeholder="楼号"
											path="hoses" />
									</div>
									<label class="control-label"><span class="required">*</span></label>
									<f:errors cssClass="help-block" element="span" path="hoses" />
								</div>
							</div>
						</f:form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary"
							onclick="House.submitForm();">
							<i class="fa fa-check"></i>&nbsp;保&nbsp;存
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<i class="fa fa-times"></i>&nbsp;关&nbsp;闭
						</button>
					</div>
				</div>
			</div>
		</div>

		<div id="modalImport" class="modal fade" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"></button>
						<h4 class="modal-title">导入</h4>
					</div>
					<div class="modal-body">
						<form class="form-validate  form-horizontal"
							action="${ctx}/house/importHouse" method="post"
							enctype="multipart/form-data" id="formImport">
							<input type="hidden" id="importCommunityId"
								name="importCommunityId" value="${communityId}" />
							<div class="form-body">
								<div class="form-group">
									<div class="col-md-10">
										<input type="file" id="importFile" name="importFile">
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary"
							onclick="House.submitImportForm();">
							<i class="fa fa-sign-in"></i>&nbsp;导&nbsp;入
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<i class="fa fa-times"></i>&nbsp;关&nbsp;闭
						</button>
					</div>
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
		<!-- END PAGE LEVEL PLUGINS -->
		<!-- BEGIN PAGE LEVEL SCRIPTS -->
		<script type="text/javascript"
			src="<c:url value='/assets/scripts/app.js'/>"></script>
		<script type="text/javascript"
			src="<c:url value='/assets/scripts/pages/house.js'/>"></script>
		<!-- END PAGE LEVEL SCRIPTS -->
		<script>
			$(function() {
				var infoMsg = '${infoMsg}';
				if (infoMsg.length > 0) {
					toastr.warning(infoMsg, '提示');
				}
				House.init("<c:url value='/' />");
			});
		</script>
	</c:if>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>