<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="user" value="${sessionScope.CONTEXT_APP.user}" />
<c:set var="menu_h" value="${sessionScope.CONTEXT_APP.menu_h.children}" />
<c:set var="menu_v" value="${sessionScope.CONTEXT_APP.menu_v.children}" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>main</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
<base target="mainFrame">
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/font-awesome/css/font-awesome.min.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/bootstrap/css/bootstrap.min.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/plugins/uniform/css/uniform.default.css' />" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN THEME STYLES -->
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style-wechat.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/style-responsive.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/plugins.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/assets/css/pages/public.css' />" />
<!-- END THEME STYLES -->
</head>
<!-- BEGIN BODY -->
<body>
	<div class="col-md-12 ">
		<div class="portlet-body form">
				<form class="form-horizontal" role="form">
					<div class="col-md-12"><h3 class-="page-title">小区信息设置</h3></div>
					<div class="form-body">
						<div class="form-group">
							<label class="col-md-1 control-label"><span class="required">*</span></label>
							<label class="col-md-2 control-label">小区名称：</label>
							<div class="col-md-6">
								<input type="text" class="form-control" placeholder="Enter text">
							</div>
						</div>
							<div class="form-group">
								<label class="col-md-1 control-label"><span class="required">*</span></label>
								<label class="col-md-2 control-label">设置楼号信息：</label>
								<div class="col-md-2">
									<select class="form-control">
										<option>河南</option>
										<option>山西</option>			
									</select>
								</div>
								<div class="col-md-2">
									<select class="form-control">
										<option>河南</option>
										<option>山西</option>			
									</select>
								</div>
								<div class="col-md-2">
									<select class="form-control">
										<option>河南</option>
										<option>山西</option>			
									</select>
								</div>
							</div>
							<div class="form-group ">
								<label class="col-md-1 control-label"><span class="required">*</span></label>
								<label class="col-md-2 control-label">设置单元号信息：</label>
								<div class="col-md-2">
									<select class="form-control">
										<option>河南</option>
										<option>山西</option>			
									</select>
								</div>
								<div class="col-md-2">
									<select class="form-control">
										<option>河南</option>
										<option>山西</option>			
									</select>
								</div>
								<div class="col-md-2">
									<select class="form-control">
										<option>河南</option>
										<option>山西</option>			
									</select>
								</div>
								<div class="col-md-2">
									<button type="button" class="btn green">添 加</button>
								</div>
							</div>
						</div>																																																											
					<div class="col-md-12"><h3 class-="page-title">导入门牌号信息</h3></div>
					<div class="form-body">																															
						<div class="form-group">
							<label class="col-md-2 control-label">单条录入：</label>
							<div class="col-md-5">
								<button type="button" class="btn green">录入信息</button>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">批量添加：</label>
							<div class="col-md-2">
								<button type="button" class="btn green">下载模板</button>
							</div>
							<div class="col-md-2">
								<button type="button" class="btn green">上传信息</button>
							</div>
						</div>																							
					</div>
				</form>
			</div>
	</div>
	
	<!-- END FOOTER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--
	[if lt IE 9]> <script type="text/javascript" src="<c:url value='/assets/plugins/excanvas.min.js'/>"></script> <script type="text/javascript" src="<c:url value='/assets/plugins/respond.min.js'/>"></script> <![endif]
-->
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery-1.10.2.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery-migrate-1.2.1.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/bootstrap/js/bootstrap.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery.blockui.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/jquery.cookie.min.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/plugins/uniform/jquery.uniform.min.js'/>">
		
	</script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script type="text/javascript" src="<c:url value='/assets/scripts/app.js'/>">
		
	</script>
	<script type="text/javascript" src="<c:url value='/assets/scripts/pages/main.js'/>">
		
	</script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<script type="text/javascript">
		$(function() {
			Main.init();
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>
