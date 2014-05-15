<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="community" value="${sessionScope.CONTEXT_APP.community}" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>查看通知内容</title>
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
	href="<c:url value='/assets/plugins/uniform/css/uniform.default.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/themes/default.css'/>" id="style_color" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/plugins/kindeditor/plugins/code/prettify.js'/>" />
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
<!-- END THEME STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/pages/public.css'/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value='/assets/css/pages/editNotice.css'/>" />
<!-- END PAGE LEVEL STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="editNotice">
	<c:if test="${empty community}">
	<div class="col-md-12 public-prompt">
		<img src="<c:url value='/assets/img/omg.png'/>" alt="" />
	</div>
	</c:if>
	<c:if test="${!empty community}">
	<div class="col-md-12 ">
		<div class="portlet box iframe-box">
			<div class="portlet-title col-md-12">
				<div class="caption col-md-2">
					<i class="fa fa-edit"></i>查看通知内容
				</div>

				<div class="actions">
						<button type="button" class="btn default btn-return"
							onclick="Javascript:{window.location='${ctx}/community/notices';}">
							<i class="fa fa-chevron-left"></i>&nbsp;返&nbsp;回
						</button>
				</div>
			</div>
			<div class="portlet-body form">
				<f:form class="form-horizontal"	commandName="noticeCommand">

					<div class="form-body">
						
						<div class="form-group">
							<label class="col-md-2 control-label">标题:</label>
							<div class="col-md-8">
								<f:input class="form-control required" placeholder="" disabled="true"
									type="text" path="title" name="title" id="title" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">通知详细内容:</label>
							<label class="col-md-8 " style="padding:6px 20px;">${noticeCommand.content}
							</label>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">图片:</label>
							<div class="col-md-8">
								<img  src="${noticeCommand.pictureUrl}">
							</div>
						</div>						
						<div class="form-group">
							<label class="col-md-2 control-label">描述:</label>
							<div class="col-md-8">
								<f:textarea class="form-control" placeholder=""
									type="text" path="description" name="description" disabled="true"
									id="description" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-2 control-label">通知类型:</label>
							<div class="col-md-8 radio-list">
								<label class="radio-inline"> <input type="radio"
									name="type" value="0" disabled="true"
									<c:out value="${noticeCommand.type==0?'checked':''}"/>>公共通知
								</label> <label class="radio-inline"> <input type="radio"
									name="type" value="1" disabled="true"
									<c:out value="${noticeCommand.type==1?'checked':''}"/>>私有通知
								</label>
							</div>
						</div>
					</div>
				</f:form>
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
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/kindeditor/kindeditor.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/kindeditor/lang/zh_CN.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/kindeditor/plugins/code/prettify.js'/>"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/data-tables/jquery.dataTables.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/data-tables/DT_bootstrap.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/scripts/app.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/scripts/pages/noticeDetail.js'/>"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<script>
		$(function() {
			NoticeDetail.init("<c:url value='/' />");
		});
	</script>
	<!-- END JAVASCRIPTS -->
	</c:if>
</body>
<!-- END BODY -->
</html>