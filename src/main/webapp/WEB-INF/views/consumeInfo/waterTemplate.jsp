<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="community" value="${sessionScope.CONTEXT_APP.community}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>水费模板信息</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/font-awesome/css/font-awesome.min.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/bootstrap/css/bootstrap.css'/>" />
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
						<i class="fa fa-reorder"></i>水费模板信息
					</div>
					<div class="actions">
						<button id="data_table_new_btn" class="btn iframe-btn"
							data-toggle="modal" onclick="WaterTemplate.addInfo();">
							<i class="fa fa-plus"></i>&nbsp;新&nbsp;建
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

		<div id="modalEdit" class="modal fade" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"></button>
						<h4 id="modalEditTitle" class="modal-title">模板设置</h4>
					</div>
					<div class="modal-body">
						<f:form class="form-validate  form-horizontal"
							action="${ctx}/consumeTemplate/saveConsumeTemplate" method="post"
							commandName="consumeTemplateCommand" id="editForm">
							<f:input type="hidden" id="id" name="id" path="id" />
							<f:input type="hidden" id="consumeType" name="consumeType"
								path="consumeType" />
							<div class="form-body">
								<div class="row">
									<div class="col-md-10">
										<div
											class="form-group <f:errors path="title">has-error</f:errors>">
											<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
											<label class="control-label  col-md-2">模板标题</label>
											<div class="col-md-9">
												<f:input class="form-control required" placeholder="模板标题"
													path="title" />
											</div>
											<label class="control-label"><span class="required">*</span></label>
											<f:errors cssClass="help-block" element="span" path="title" />
										</div>
										<div
											class="form-group <f:errors path="content">has-error</f:errors>">
											<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
											<label class="control-label  col-md-2">模板内容</label>
											<div class="col-md-9">
												<f:textarea type="text" class="form-control required"
													style="height:247px;"  placeholder="模板内容" path="content"></f:textarea>
											</div>
											<label class="control-label"><span class="required">*</span></label>
											<f:errors cssClass="help-block" element="span" path="content" />
										</div>
									</div>
									<div class="col-md-2">
										<div class="btn-group dropdown open"
											style="margin-right: 5px;">
											<button type="button" class="btn btn-primary dropdown-toggle"
												data-toggle="dropdown">
												替换项<i class="fa fa-angle-down"></i>
											</button>
											<ul class="dropdown-menu" role="menu" align="left"
												aria-labelledby="btnGroupVerticalDrop5"
												style="width: 79px; min-width: 79px; font-size: 12px; display: block;">
												<li><a style="line-height: 8px;"
													onclick="WaterTemplate.insertFiled('年份')">年份</a></li>
												<li><a style="line-height: 8px;"
													onclick="WaterTemplate.insertFiled('月份')">月份</a></li>
												<li><a style="line-height: 8px;"
													onclick="WaterTemplate.insertFiled('小区名称')">小区名称</a></li>
												<li><a style="line-height: 8px;"
													onclick="WaterTemplate.insertFiled('楼号')">楼号</a></li>
												<li><a style="line-height: 8px;"
													onclick="WaterTemplate.insertFiled('单元')">单元</a></li>
												<li><a style="line-height: 8px;"
													onclick="WaterTemplate.insertFiled('楼层')">楼层</a></li>
												<li><a style="line-height: 8px;"
													onclick="WaterTemplate.insertFiled('门牌号')">门牌号</a></li>
												<li><a style="line-height: 8px;"
													onclick="WaterTemplate.insertFiled('户号')">户号</a></li>
												<li><a style="line-height: 8px;"
													onclick="WaterTemplate.insertFiled('抄表时间')">抄表时间</a></li>
												<li><a style="line-height: 8px;"
													onclick="WaterTemplate.insertFiled('上期表数')">上期表数</a></li>
												<li><a style="line-height: 8px;"
													onclick="WaterTemplate.insertFiled('本期表数')">本期表数</a></li>
												<li><a style="line-height: 8px;"
													onclick="WaterTemplate.insertFiled('本期用量')">本期用量</a></li>
												<li><a style="line-height: 8px;"
													onclick="WaterTemplate.insertFiled('应缴金额')">应缴金额</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</f:form>
					</div>
					<div class="modal-footer" style="overflow: visible">
						<button type="button" class="btn btn-primary"
							onclick="WaterTemplate.submitForm();">
							<i class="fa fa-check"></i>&nbsp;保&nbsp;存
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
			src="<c:url value='/assets/scripts/pages/waterTemplate.js'/>"></script>
		<!-- END PAGE LEVEL SCRIPTS -->
		<script>
			$(function() {
				var infoMsg = '${infoMsg}';
				if (infoMsg.length > 0) {
					toastr.warning(infoMsg, '提示');
				}
				WaterTemplate.init("<c:url value='/' />");
			});
		</script>
	</c:if>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>