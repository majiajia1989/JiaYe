<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="community" value="${sessionScope.CONTEXT_APP.community}" />
<c:set var="corpId" value='<%=request.getAttribute("corpId")%>' />
<c:set var="communityId"
	value='<%=request.getAttribute("communityId")%>' />
<c:set var="houseData" value='<%=request.getAttribute("houseData")%>' />
<c:set var="houseRoomData"
	value='<%=request.getAttribute("houseRoomData")%>' />
<c:set var="consumeTemplateData"
	value='<%=request.getAttribute("consumeTemplateData")%>' />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>电费信息</title>
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
						<i class="fa fa-reorder"></i>电费信息
					</div>
					<div class="actions">
						<button id="data_table_new_btn" class="btn iframe-btn"
							data-toggle="modal" data-target="#modalQuery">
							<i class="fa fa-search"></i>&nbsp;查&nbsp;询
						</button>
						<button id="data_table_new_btn" class="btn iframe-btn"
							data-toggle="modal" data-target="#modalImport">
							<i class="fa fa-sign-in"></i>&nbsp;导&nbsp;入
						</button>
						<button id="data_table_new_btn" class="btn iframe-btn"
							data-toggle="modal" data-target="#modalSendWeixin">
							<i class="fa fa-envelope"></i>发送微信
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
								<th></th>
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

		<div id="modalQuery" class="modal fade" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"></button>
						<h4 class="modal-title">
							<i class="fa fa-search"></i>&nbsp;查询条件
						</h4>
					</div>
					<div class="modal-body">
						<form class="form-validate  form-horizontal"
							action="${ctx}/consumeInfo/importConsumeInfo" method="post"
							enctype="multipart/form-data" id="formQuery">
							<input type="hidden" id="queryCommunityId"
								name="queryCommunityId" value="${communityId}" /> <input
								type="hidden" id="queryConsumeTypeId" name="queryConsumeTypeId"
								value="1" /> <input type="hidden" id="queryPayNumber"
								name="queryPayNumber" value="" />
							<div class="form-body">
								<div class="row">
									<div class="form-group">
										<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
										<label class="control-label  col-md-2">年月</label>
										<div class="col-md-8">
											<div class="col-md-6"
												style="padding-left: 0px; padding-right: 5px;">
												<select class="form-control dropdown-select " id="queryYear"
													name="queryYear">
													<option value="2010">2010年</option>
													<option value="2011">2011年</option>
													<option value="2012">2012年</option>
													<option value="2013">2013年</option>
													<option value="2014">2014年</option>
													<option value="2015">2015年</option>
													<option value="2016">2016年</option>
													<option value="2017">2017年</option>
													<option value="2018">2018年</option>
													<option value="2019">2019年</option>
													<option value="2020">2020年</option>
												</select>
											</div>
											<div class=" col-md-6"
												style="padding-left: 5px; padding-right: 0px;">
												<select class="form-control dropdown-select" id="queryMonth"
													name="queryMonth"><option value="1">一月</option>
													<option value="2">二月</option>
													<option value="3">三月</option>
													<option value="4">四月</option>
													<option value="5">五月</option>
													<option value="6">六月</option>
													<option value="7">七月</option>
													<option value="8">八月</option>
													<option value="9">九月</option>
													<option value="10">十月</option>
													<option value="11">十一月</option>
													<option value="12">十二月</option>
												</select>
											</div>
										</div>
									</div>
									<div class="form-group">
										<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
										<label class="control-label  col-md-2">楼号</label>
										<div class="col-md-8">
											<select class="form-control dropdown-select "
												id="queryHouseId" name="queryHouseId">
												<option value="-1">全部</option>
												<c:forEach items="${houseData}" var="houseInfo">
													<option value="${houseInfo.id}">${houseInfo.name}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="form-group ">
										<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
										<label class="control-label  col-md-2">门牌号</label>
										<div class="col-md-8">
											<select class="form-control dropdown-select"
												id="queryHouseRoomId" name="queryHouseRoomId">
												<option value="-1">全部</option>
												<c:forEach items="${houseRoomData}" var="houseRoomInfo">
													<option value="${houseRoomInfo.id}">${houseRoomInfo.name}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary"
							onclick="Electricity.submitQueryForm();">
							<i class="fa fa-search"></i>&nbsp;查&nbsp;询
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
						<h4 class="modal-title">
							<i class="fa fa-sign-in"></i>&nbsp;导入
						</h4>
					</div>
					<div class="modal-body">
						<form class="form-validate  form-horizontal"
							action="${ctx}/consumeInfo/importConsumeInfo" method="post"
							enctype="multipart/form-data" id="formImport">
							<input type="hidden" id="importCommunityId"
								name="importCommunityId" value="${communityId}" /> <input
								type="hidden" id="importConsumeTypeId"
								name="importConsumeTypeId" value="1" />

							<div class="form-body">
								<div class="row">
									<div class="form-group">
										<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
										<label class="control-label  col-md-2">选择年月</label>
										<div class="col-md-8">
											<div class="col-md-6"
												style="padding-left: 0px; padding-right: 5px;">
												<select class="form-control dropdown-select "
													id="importYear" name="importYear">
													<option value="2010">2010年</option>
													<option value="2011">2011年</option>
													<option value="2012">2012年</option>
													<option value="2013">2013年</option>
													<option value="2014">2014年</option>
													<option value="2015">2015年</option>
													<option value="2016">2016年</option>
													<option value="2017">2017年</option>
													<option value="2018">2018年</option>
													<option value="2019">2019年</option>
													<option value="2020">2020年</option>
												</select>
											</div>
											<div class=" col-md-6"
												style="padding-left: 5px; padding-right: 0px;">
												<select class="form-control dropdown-select"
													id="importMonth" name="importMonth"><option
														value="1">一月</option>
													<option value="2">二月</option>
													<option value="3">三月</option>
													<option value="4">四月</option>
													<option value="5">五月</option>
													<option value="6">六月</option>
													<option value="7">七月</option>
													<option value="8">八月</option>
													<option value="9">九月</option>
													<option value="10">十月</option>
													<option value="11">十一月</option>
													<option value="12">十二月</option>
												</select>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label  col-md-2">上传文件</label>
										<div class="col-md-8">
											<input type="file" id="importFile" name="importFile">
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary"
							onclick="Electricity.submitImportForm();">
							<i class="fa fa-sign-in"></i>&nbsp;导&nbsp;入
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<i class="fa fa-times"></i>&nbsp;关&nbsp;闭
						</button>
					</div>
				</div>
			</div>
		</div>
		<div id="modalSendWeixin" class="modal fade" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"></button>
						<h4 class="modal-title">
							<i class="fa fa-envelope"></i>&nbsp;发送微信
						</h4>
					</div>
					<div class="modal-body">
						<form class="form-validate  form-horizontal"
							action="${ctx}/consumeInfo/sendConsume" method="post"
							enctype="multipart/form-data" id="formSend">
							<input type="hidden" id="sendCommunityId" name="sendCommunityId"
								value="${communityId}" /> <input type="hidden"
								id="sendConsumeTypeId" name="sendConsumeTypeId" value="1" /> <input
								type="hidden" id="sendPayNumber" name="sendPayNumber" value="" />
							<input type="hidden" id="sendYear" name="sendYear" value="" /> <input
								type="hidden" id="sendMonth" name="sendMonth" value="" /> <input
								type="hidden" id="sendHouseId" name="sendHouseId" value="" /> <input
								type="hidden" id="sendHouseRoomId" name="sendHouseRoomId"
								value="" /><input type="hidden" id="sendTitle" name="sendTitle"
								value="电费通知单" />
							<div class="form-body">
								<div class="row">
									<div class="form-body">
										<div class="form-group">
											<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
											<label class="control-label  col-md-2">选择模板</label>
											<div class="col-md-8">
												<select class="form-control dropdown-select"
													id="sendConsumeTemplate" name="sendConsumeTemplate">
													<c:set var="i" value='0' />
													<c:forEach items="${consumeTemplateData}"
														var="consumeTemplateInfo">
														<c:set var="i" value="${i + 1}"></c:set>
														<option value="${consumeTemplateInfo.id}"
															<c:if test="${i==1}">
																selected="true"
															</c:if>
															tag="${consumeTemplateInfo.content}">${consumeTemplateInfo.title}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="form-group">
											<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
											<label class="control-label  col-md-2">模板预览</label>
											<div class="col-md-8">
												<textarea class="form-control required" path="sendContent"
													rows="6" placeholder="发送内容" id="sendContent"
													name="sendContent" readonly="true"></textarea>
											</div>
											<label class="control-label"><span class="required">*</span></label>
											<errors cssClass="help-block" element="span"
												path="sendContent" />
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer" style="overflow: visible">
						<button type="button" class="btn btn-primary"
							onclick="Electricity.submitSendForm();">
							<i class="fa fa-envelope"></i>&nbsp;发&nbsp;送
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
			src="<c:url value='/assets/scripts/pages/electricity.js'/>"></script>
		<!-- END PAGE LEVEL SCRIPTS -->
		<script>
			$(function() {
				var infoMsg = '${infoMsg}';
				if (infoMsg.length > 0) {
					toastr.warning(infoMsg, '提示');
				}
				Electricity.init("<c:url value='/' />");
			});
		</script>
	</c:if>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>