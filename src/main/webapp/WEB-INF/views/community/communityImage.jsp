<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="community" value="${sessionScope.CONTEXT_APP.community}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>小区图片</title>
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
<!-- END THEME STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
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
	<!-- table 内容区域开始 -->
	<div class="col-md-12">
			<div class="portlet box iframe-box ">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-reorder"></i>小区图片
				</div>
				<div class="actions">
					<a id="servicePhone" class="btn iframe-btn"
								href="${ctx}/community/addCommunityImage" >
								<i class="fa fa-plus"></i>&nbsp;新&nbsp;建
					</a>
				</div>
			</div>
			<div class="portlet-body">
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
	<!-- table 内容区域结束 -->

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
		src="<c:url value='/assets/plugins/data-tables/jquery.dataTables.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/data-tables/DT_bootstrap.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/plugins/bootbox/bootbox.min.js'/>"></script>	
		<script type="text/javascript"
			src="<c:url value='/assets/scripts/app.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/scripts/pages/communityImage.js'/>"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<script>
		$(function() {
			CommunityImage.init("<c:url value='/' />");
		});
	</script>
	</c:if>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>